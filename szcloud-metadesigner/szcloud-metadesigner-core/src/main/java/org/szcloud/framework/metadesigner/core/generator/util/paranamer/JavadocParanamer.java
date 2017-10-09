package org.szcloud.framework.metadesigner.core.generator.util.paranamer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JavadocParanamer implements Paranamer {

	private static final String IE =
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)";

	private static final ParameterNamesNotFoundException CLASS_NOT_SUPPORTED =
			new ParameterNamesNotFoundException("class not supported");

	/** In the case of an archive, this stores the path up to the base of the Javadocs */
	private String base = null;
	private final boolean isArchive;
	private final boolean isDirectory;

	private final boolean isURI;

	/**
	 * Regardless of the implementation, this stores the base location of the remote or
	 * local file or directory.
	 */
	private final URI location;

	/** The packages which are supported by this instance. Contains Strings */
	private final Set<String> packages = new HashSet<String>();

	/**
	 * Construct a Javadoc reading implementation of {@link Paranamer} using a local
	 * directory or zip archive as a source.
	 * 
	 * @param archiveOrDirectory
	 *            either a zip archive of Javadocs or the base directory of Javadocs.
	 * @throws IOException
	 *             if there was an error when reading from either the archive or the
	 *             package-list file.
	 * @throws FileNotFoundException
	 *             if the archive, directory or <code>package-list</code> file does not
	 *             exist.
	 * @throws NullPointerException
	 *             if any parameter is null
	 * @throws IllegalArgumentException
	 *             If the given parameter is not a file or directory or if it is a file
	 *             but not a javadoc zip archive.
	 */
	public JavadocParanamer(File archiveOrDirectory) throws IOException {
		if (archiveOrDirectory == null)
			throw new NullPointerException();

		if (!archiveOrDirectory.exists())
			throw new FileNotFoundException(
				archiveOrDirectory.getAbsolutePath());

		isURI = false;
		location = archiveOrDirectory.toURI();

		if (archiveOrDirectory.isDirectory()) {
			// is a directory
			isArchive = false;
			isDirectory = true;
			// check that "package-list" exists
			File dir = archiveOrDirectory;
			File packageList =
					new File(dir.getAbsolutePath() + "/package-list");
			if (!packageList.isFile())
				throw new FileNotFoundException("No package-list found at "
						+ dir.getAbsolutePath()
						+ ". Not a valid Javadoc directory.");
			// it appear to be a valid Javadoc directory
			FileInputStream input = new FileInputStream(packageList);
			try {
				String packageListString = streamToString(input);
				parsePackageList(packageListString);
			} finally {
				input.close();
			}
		} else if (archiveOrDirectory.isFile()) {
			// is a file
			isArchive = true;
			isDirectory = false;
			File archive = archiveOrDirectory;
			if (!archive.getAbsolutePath().toLowerCase().endsWith(".zip"))
				throw new IllegalArgumentException(archive.getAbsolutePath()
						+ " is not a zip file.");
			// check that a "package-list" exists somewhere in the archive
			ZipFile zip = new ZipFile(archive);
			try {
				// we need to check for a file named "package-list".
				// There may be multiple files in the archive
				// but we cannot use ZipFile.getEntry for suffix names
				// so we have to look through all the entries.
				// We then pick the largest file.
				Enumeration<? extends ZipEntry> entries = zip.entries();
				// grr... http://javablog.co.uk/2007/11/25/enumeration-and-iterable
				// Set<ZipEntry>
				SortedMap<Long, ZipEntry> packageLists = new TreeMap<Long, ZipEntry>();
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					String name = entry.getName();
					if (name.endsWith("package-list")) {
						Long size = entry.getSize();
						packageLists.put(size, entry);
					}
				}
				if (packageLists.size() == 0)
					throw new FileNotFoundException(
						"no package-list found in archive");

				// pick the largest package-list file, it's most likely the one we want
				ZipEntry entry = packageLists.get(packageLists.lastKey());
				String name = entry.getName();
				base =
						name.substring(0, name.length()
								- "package-list".length());
				InputStream input = zip.getInputStream(entry);
				try {
					String packageListString = streamToString(input);
					parsePackageList(packageListString);
				} finally {
					input.close();
				}
			} finally {
				zip.close();
			}
		} else
			throw new IllegalArgumentException(
				archiveOrDirectory.getAbsolutePath()
						+ " is neither a directory nor a file.");
	}

	/**
	 * @param url The URL of the JavaDoc
	 * @throws IOException
	 *             if there was a problem connecting to the remote Javadocs
	 * @throws FileNotFoundException
	 *             if the url does not have a <code>/package-list</code>
	 * @throws NullPointerException
	 *             if any parameter is null
	 */
	public JavadocParanamer(URL url) throws IOException {
		if (url == null)
			throw new NullPointerException();

		isArchive = false;
		isDirectory = false;
		isURI = true;
		try {
			location = new URI(url.toString());
		} catch (URISyntaxException e) {
			throw new IOException(e.getMessage());
		}

		// check the package-list
		URL packageListURL = new URL(url.toString() + "/package-list");
		InputStream input = urlToInputStream(packageListURL);
		try {
			String packageList = streamToString(input);
			parsePackageList(packageList);
		} finally {
			input.close();
		}
	}

    public String[] lookupParameterNames(AccessibleObject methodOrConstructor) {
        return lookupParameterNames(methodOrConstructor, true);
    }

    public String[] lookupParameterNames(AccessibleObject methodOrConstructor, boolean throwExceptionIfMissing) {
		if (methodOrConstructor == null)
			throw new NullPointerException();

		Class<?> klass;
		String name;
		Class<?>[] types;

		if (methodOrConstructor instanceof Constructor<?>) {
			Constructor<?> constructor = (Constructor<?>) methodOrConstructor;
			klass = constructor.getDeclaringClass();
			name = constructor.getName();
			types = constructor.getParameterTypes();
		} else if (methodOrConstructor instanceof Method) {
			Method method = (Method) methodOrConstructor;
			klass = method.getDeclaringClass();
			name = method.getName();
			types = method.getParameterTypes();
		} else
			throw new IllegalArgumentException();

		// quick check to see if we support the package
		if (!packages.contains(klass.getPackage().getName()))
			throw CLASS_NOT_SUPPORTED;

		try {
			String[] names = getParameterNames(klass, name, types);
			if (names == null) {
                if (throwExceptionIfMissing) {
                    throw new ParameterNamesNotFoundException(
					    methodOrConstructor.toString());
                } else {
                    return Paranamer.EMPTY_NAMES;
                }
            }
            return names;
		} catch (IOException e) {
            if (throwExceptionIfMissing) {
                throw new ParameterNamesNotFoundException(
	    			methodOrConstructor.toString() + " due to an I/O error: "
		    				+ e.getMessage());
            } else {
                return Paranamer.EMPTY_NAMES;
            }
        }
	}

	// throws CLASS_NOT_SUPPORTED if the class file is not found in the javadocs
	// return null if the parameter names were not found
	@SuppressWarnings("resource")
	private String[] getParameterNames(Class<?> klass,
			String constructorOrMethodName, Class<?>[] types) throws IOException {
		// silly request for names of a parameterless method/constructor!
		if ((types != null) && (types.length == 0))
			return new String[0];

		String path = getCanonicalName(klass).replace('.', '/');
		if (isArchive) {
			ZipFile archive = new ZipFile(new File(location));
			ZipEntry entry = archive.getEntry(base + path + ".html");
			if (entry == null)
				throw CLASS_NOT_SUPPORTED;
			InputStream input = archive.getInputStream(entry);
			return getParameterNames2(input, constructorOrMethodName, types);
		} else if (isDirectory) {
			File file = new File(location.getPath() + "/" + path + ".html");
			if (!file.isFile())
				throw CLASS_NOT_SUPPORTED;
			FileInputStream input = new FileInputStream(file);
			return getParameterNames2(input, constructorOrMethodName, types);
		} else if (isURI) {
			try {
				URL url = new URL(location.toString() + "/" + path + ".html");
				InputStream input = urlToInputStream(url);
				return getParameterNames2(input, constructorOrMethodName, types);
			} catch (FileNotFoundException e) {
				throw CLASS_NOT_SUPPORTED;
			}
		}
		throw new RuntimeException(
			"bug in JavadocParanamer. Should not reach here.");
	}

	/*
	 * Parse the Javadoc String and return the parameter names for the given constructor
	 * or method. Return null if no method/constructor is found. Note that types will
	 * never have length zero... we already deal with that situation higher up in the
	 * chain. Don't forget to close the input!
	 */
	private String[] getParameterNames2(InputStream input,
			String constructorOrMethodName, Class<?>[] types) throws IOException {
		String javadoc = streamToString(input);
		input.close();

		// String we're looking for is like
		// 
		// NAME="constructorOrMethodName(obj.ClassName, ...)"...noise...
		// <DT><B>Parameters:</B><DD><CODE>parameter_name_1</CODE>...noise...
		// <DD><CODE>parameter_name_2</CODE>...noise...
		// ...
		// <DD><CODE>parameter_name_N</CODE>...noise...
		// 
		// We cannot rely on the Parameters line existing as it depends on the author
		// having correctly marked-up their code. The NAME element is auto-generated
		// and should be checked for aggressively.
		// 
		// Also note that Javadoc parameter names may differ from the names in the source.

		// we don't have Pattern/Matcher :-(
		StringBuffer regex = new StringBuffer();
		regex.append("NAME=\"");
		regex.append(constructorOrMethodName);
		// quotes needed to escape array brackets
		regex.append("\\(\\Q");
		for (int i = 0; i < types.length; i++) {
			if (i != 0)
				regex.append(", ");
			// canonical name deals with arrays
			regex.append(getCanonicalName(types[i]));
		}
		regex.append("\\E\\)\"");

		Pattern pattern = Pattern.compile(regex.toString());
		Matcher matcher = pattern.matcher(javadoc);
		if (!matcher.find())
			// not found
			return Paranamer.EMPTY_NAMES;

		// found it. Lookup the parameter names.
		String[] names = new String[types.length];
		// now we're sure we have the right method, find the parameter names!
		String regexParams = "<DD><CODE>([^<]*)</CODE>";
		Pattern patternParams = Pattern.compile(regexParams);
		int start = matcher.end();
		Matcher matcherParams = patternParams.matcher(javadoc);
		for (int i = 0; i < types.length; i++) {
			boolean find = matcherParams.find(start);
			if (!find)
				return Paranamer.EMPTY_NAMES;
			start = matcherParams.end();
			names[i] = matcherParams.group(1);
		}
		return names;
	}

	// doesn't support names of nested classes
	private String getCanonicalName(Class<?> klass) {
		if (klass.isArray())
			return getCanonicalName(klass.getComponentType()) + "[]";

		return klass.getName();
	}

	// storing the list of packages that we support is very lightweight
	private void parsePackageList(String packageList) throws IOException {
		StringReader reader = new StringReader(packageList);
		BufferedReader breader = new BufferedReader(reader);
		String line;
		while ((line = breader.readLine()) != null) {
			packages.add(line);
		}
	}

	// read an InputStream into a UTF-8 String
	private String streamToString(InputStream input) throws IOException {
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// this should never happen
			reader = new InputStreamReader(input);
		}
		BufferedReader breader = new BufferedReader(reader);
		String line;
		StringBuffer builder = new StringBuffer();
		while ((line = breader.readLine()) != null) {
			builder.append(line);
			builder.append("\n");
		}
		return builder.toString();
	}

	private InputStream urlToInputStream(URL url) throws IOException {
		URLConnection conn = url.openConnection();
		// pretend to be IE6
		conn.setRequestProperty("User-Agent", IE);
		// allow both GZip and Deflate (ZLib) encodings
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		conn.connect();
		String encoding = conn.getContentEncoding();
		if ((encoding != null) && encoding.equalsIgnoreCase("gzip"))
			return new GZIPInputStream(conn.getInputStream());
		else if ((encoding != null) && encoding.equalsIgnoreCase("deflate"))
			return new InflaterInputStream(conn.getInputStream(), new Inflater(
				true));
		else
			return conn.getInputStream();
	}

}
