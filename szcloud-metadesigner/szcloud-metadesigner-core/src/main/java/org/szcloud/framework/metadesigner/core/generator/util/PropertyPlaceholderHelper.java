package org.szcloud.framework.metadesigner.core.generator.util;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class PropertyPlaceholderHelper {

	private final String placeholderPrefix;

	private final String placeholderSuffix;

	private final String valueSeparator;

	private final boolean ignoreUnresolvablePlaceholders;

	/**
	 * Creates a new <code>PropertyPlaceholderHelper</code> that uses the supplied prefix and suffix.
	 * Unresolvable placeholders are ignored.
	 * @param placeholderPrefix the prefix that denotes the start of a placeholder.
	 * @param placeholderSuffix the suffix that denotes the end of a placeholder.
	 */
	public PropertyPlaceholderHelper(String placeholderPrefix, String placeholderSuffix) {
		this(placeholderPrefix, placeholderSuffix, null, true);
	}

	/**
	 * Creates a new <code>PropertyPlaceholderHelper</code> that uses the supplied prefix and suffix.
	 * @param placeholderPrefix the prefix that denotes the start of a placeholder.
	 * @param placeholderSuffix the suffix that denotes the end of a placeholder.
	 * @param ignoreUnresolvablePlaceholders indicates whether unresolvable placeholders should be ignored
	 * (<code>true</code>) or cause an exception (<code>false</code>).
	 */
	public PropertyPlaceholderHelper(String placeholderPrefix, String placeholderSuffix,
			String valueSeparator, boolean ignoreUnresolvablePlaceholders) {
		if(placeholderPrefix == null) throw new IllegalArgumentException("placeholderPrefix must not be null");
		if(placeholderSuffix == null) throw new IllegalArgumentException("placeholderSuffix must not be null");
		this.placeholderPrefix = placeholderPrefix;
		this.placeholderSuffix = placeholderSuffix;
		this.valueSeparator = valueSeparator;
		this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
	}

	/**
	 * Replaces all placeholders of format <code>${name}</code> with the corresponding property
	 * from the supplied {@link Properties}.
	 * @param value the value containing the placeholders to be replaced.
	 * @param properties the <code>Properties</code> to use for replacement.
	 * @return the supplied value with placeholders replaced inline.
	 */
	public String replacePlaceholders(String value, final Properties properties) {
		if(properties == null) throw new IllegalArgumentException("Argument 'properties' must not be null.");
		return replacePlaceholders(value, new PlaceholderResolver() {
			public String resolvePlaceholder(String placeholderName) {
				return properties.getProperty(placeholderName);
			}
		});
	}

	/**
	 * Replaces all placeholders of format <code>${name}</code> with the value returned from the supplied
	 * {@link PlaceholderResolver}.
	 * @param value the value containing the placeholders to be replaced.
	 * @param placeholderResolver the <code>PlaceholderResolver</code> to use for replacement.
	 * @return the supplied value with placeholders replaced inline.
	 */
	public String replacePlaceholders(String value, PlaceholderResolver placeholderResolver) {
		if(value == null) throw new IllegalArgumentException("Argument 'value' must not be null.");
		return parseStringValue(value, placeholderResolver, new HashSet<String>());
	}

	protected String parseStringValue(
			String strVal, PlaceholderResolver placeholderResolver, Set<String> visitedPlaceholders) {
		StringBuilder buf = new StringBuilder(strVal);

		int startIndex = strVal.indexOf(this.placeholderPrefix);
		while (startIndex != -1) {
			int endIndex = findPlaceholderEndIndex(buf, startIndex);
			if (endIndex != -1) {
				String placeholder = buf.substring(startIndex + this.placeholderPrefix.length(), endIndex);
				if (!visitedPlaceholders.add(placeholder)) {
					throw new IllegalArgumentException(
							"Circular placeholder reference '" + placeholder + "' in property definitions");
				}
				// Recursive invocation, parsing placeholders contained in the placeholder key.
				placeholder = parseStringValue(placeholder, placeholderResolver, visitedPlaceholders);

				// Now obtain the value for the fully resolved key...
				String propVal = placeholderResolver.resolvePlaceholder(placeholder);
				if (propVal == null && this.valueSeparator != null) {
					int separatorIndex = placeholder.indexOf(this.valueSeparator);
					if (separatorIndex != -1) {
						String actualPlaceholder = placeholder.substring(0, separatorIndex);
						String defaultValue = placeholder.substring(separatorIndex + this.valueSeparator.length());
						propVal = placeholderResolver.resolvePlaceholder(actualPlaceholder);
						if (propVal == null) {
							propVal = defaultValue;
						}
					}
				}
				if (propVal != null) {
					propVal = parseStringValue(propVal, placeholderResolver, visitedPlaceholders);
					buf.replace(startIndex, endIndex + this.placeholderSuffix.length(), propVal);

					GLogger.trace("Resolved placeholder '" + placeholder + "'");

					startIndex = buf.indexOf(this.placeholderPrefix, startIndex + propVal.length());
				}
				else if (this.ignoreUnresolvablePlaceholders) {
					startIndex = buf.indexOf(this.placeholderPrefix, endIndex + this.placeholderSuffix.length());
				}
				else {
					throw new IllegalArgumentException("Could not resolve placeholder '" + placeholder + "'");
				}
				visitedPlaceholders.remove(placeholder);
			}
			else {
				startIndex = -1;
			}
		}
		return buf.toString();
	}

	private int findPlaceholderEndIndex(CharSequence buf, int startIndex) {
		int index = startIndex + this.placeholderPrefix.length();
		int withinNestedPlaceholder = 0;
		while (index < buf.length()) {
			if (StringHelper.substringMatch(buf, index, this.placeholderSuffix)) {
				if (withinNestedPlaceholder > 0) {
					withinNestedPlaceholder--;
					index = index + this.placeholderPrefix.length() - 1;
				}
				else {
					return index;
				}
			}
			else if (StringHelper.substringMatch(buf, index, this.placeholderPrefix)) {
				withinNestedPlaceholder++;
				index = index + this.placeholderPrefix.length();
			}
			else {
				index++;
			}
		}
		return -1;
	}


	/**
	 * Strategy interface used to resolve replacement values for placeholders contained in Strings.
	 * @see PropertyPlaceholderHelper
	 */
	public static interface PlaceholderResolver {

		/**
		 * Resolves the supplied placeholder name into the replacement value.
		 * @param placeholderName the name of the placeholder to resolve.
		 * @return the replacement value or <code>null</code> if no replacement is to be made.
		 */
		String resolvePlaceholder(String placeholderName);
	}

	public static class PropertyPlaceholderConfigurerResolver implements PlaceholderResolver {
		private final Properties props;
		public PropertyPlaceholderConfigurerResolver(Properties props) {
			this.props = props;
		}
		public String resolvePlaceholder(String placeholderName) {
			String value = props.getProperty(placeholderName);
			if(value == null) {
				value = System.getProperty(placeholderName);
				if(value == null) {
					if(placeholderName.startsWith("env.")) {
						value = System.getenv(placeholderName.substring("env.".length()));
					}
				}
			}
			return value;
		}
	}
}
