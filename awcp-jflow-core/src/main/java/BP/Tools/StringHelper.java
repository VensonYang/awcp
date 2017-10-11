package BP.Tools;

import java.util.LinkedList;
import java.util.List;

//----------------------------------------------------------------------------------------
//	Copyright ? 2006 - 2010 Tangible Software Solutions Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class is used to simulate some .NET string functions in Java.
//----------------------------------------------------------------------------------------
public final class StringHelper {
	// ------------------------------------------------------------------------------------
	// This method replaces the .NET static string method 'IsNullOrEmpty'.
	// ------------------------------------------------------------------------------------
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.equals("") || "null".equals(string);
	}

	// ------------------------------------------------------------------------------------
	// This method replaces the .NET static string method 'Join' (2 parameter
	// version).
	// ------------------------------------------------------------------------------------
	public static String join(String separator, String[] stringarray) {
		if (stringarray == null)
			return null;
		else
			return join(separator, stringarray, 0, stringarray.length);
	}

	// ------------------------------------------------------------------------------------
	// This method replaces the .NET static string method 'Join' (4 parameter
	// version).
	// ------------------------------------------------------------------------------------
	public static String join(String separator, String[] stringarray,
			int startindex, int count) {
		String result = "";

		if (stringarray == null)
			return null;

		for (int index = startindex; index < stringarray.length
				&& index - startindex < count; index++) {
			if (separator != null && index > startindex)
				result += separator;

			if (stringarray[index] != null)
				result += stringarray[index];
		}

		return result;
	}

	// ------------------------------------------------------------------------------------
	// This method replaces the .NET static string method 'TrimEnd'.
	// ------------------------------------------------------------------------------------
	public static String trimEnd(String string, Character... charsToTrim) {
		if (string == null || charsToTrim == null)
			return string;

		int lengthToKeep = string.length();
		for (int index = string.length() - 1; index >= 0; index--) {
			boolean removeChar = false;
			if (charsToTrim.length == 0) {
				if (Character.isWhitespace(string.charAt(index))) {
					lengthToKeep = index;
					removeChar = true;
				}
			} else {
				for (int trimCharIndex = 0; trimCharIndex < charsToTrim.length; trimCharIndex++) {
					if (string.charAt(index) == charsToTrim[trimCharIndex]) {
						lengthToKeep = index;
						removeChar = true;
						break;
					}
				}
			}
			if (!removeChar)
				break;
		}
		return string.substring(0, lengthToKeep);
	}

	// ------------------------------------------------------------------------------------
	// This method replaces the .NET static string method 'TrimStart'.
	// ------------------------------------------------------------------------------------
	public static String trimStart(String string, Character... charsToTrim) {
		if (string == null || charsToTrim == null)
			return string;

		int startingIndex = 0;
		for (int index = 0; index < string.length(); index++) {
			boolean removeChar = false;
			if (charsToTrim.length == 0) {
				if (Character.isWhitespace(string.charAt(index))) {
					startingIndex = index + 1;
					removeChar = true;
				}
			} else {
				for (int trimCharIndex = 0; trimCharIndex < charsToTrim.length; trimCharIndex++) {
					if (string.charAt(index) == charsToTrim[trimCharIndex]) {
						startingIndex = index + 1;
						removeChar = true;
						break;
					}
				}
			}
			if (!removeChar)
				break;
		}
		return string.substring(startingIndex);
	}

	// ------------------------------------------------------------------------------------
	// This method replaces the .NET static string method 'Trim' when arguments
	// are used.
	// ------------------------------------------------------------------------------------
	public static String trim(String string, Character... charsToTrim) {
		return trimEnd(trimStart(string, charsToTrim), charsToTrim);
	}

	// ------------------------------------------------------------------------------------
	// This method is used for string equality comparisons when the option
	// 'Use helper 'stringsEqual' method to handle null strings' is selected
	// (The Java String 'equals' method can't be called on a null instance).
	// ------------------------------------------------------------------------------------
	public static boolean stringsEqual(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		else
			return s1 != null && s1.equals(s2);
	}
	
	/**
	 * 判断是否是空字符串 null和"" null返回result,否则返回字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String isEmpty(String s, String result) {
		if (s != null && !s.equals("") && !s.equals("null")) {
			return s;
		}
		return result;
	}
	public static String stringFill(String source, int fillLength, char fillChar, boolean isLeftFill) {
	    if (source == null || source.length() >= fillLength) return source;
	     
	    StringBuilder result = new StringBuilder(fillLength);
	    int len = fillLength - source.length();
	    if (isLeftFill) {
	        for (; len > 0; len--) {
	            result.append(fillChar);
	        }
	        result.append(source);
	    } else {
	        result.append(source);
	        for (; len > 0; len--) {
	            result.append(fillChar);
	        }
	    }
	    return result.toString();
	}
	 
	public static String stringFill2(String source, int fillLength, char fillChar, boolean isLeftFill) {
	    if (source == null || source.length() >= fillLength) return source;
	     
	    char[] c = new char[fillLength];
	    char[] s = source.toCharArray();
	    int len = s.length;
	    if(isLeftFill){
	        int fl = fillLength - len;
	        for(int i = 0; i<fl; i++){
	            c[i] = fillChar;
	        }
	        System.arraycopy(s, 0, c, fl, len);
	    }else{
	        System.arraycopy(s, 0, c, 0, len);
	        for(int i = len; i<fillLength; i++){
	            c[i] = fillChar;
	        }
	    }
	    return String.valueOf(c);
	}
	
	/**
	 * 将字符串按指定的"分割符"分割成字符串数组(接受连续出现分割符部分) Take a String which is a delimited
	 * list and convert it to a String array.
	 * @param s
	 *            String
	 * @param delim
	 *            delim (this will not be returned)
	 * @return an array of the tokens in the list
	 *         split("a;b;c;;")={'a','b','c','',''} split(" a;b;c; ; ")={'
	 *         a','b','c',' ',' '}
	 */
	public static String[] split(String s, String delim) {
		if (s == null) {
			return new String[0];
		}
		if (delim == null) {
			return new String[] { s };
		}

		List l = new LinkedList();
		int pos = 0;
		int delPos = 0;
		while ((delPos = s.indexOf(delim, pos)) != -1) {
			l.add(s.substring(pos, delPos));
			pos = delPos + delim.length();
		}
		if (pos <= s.length()) {
			// add rest of String
			l.add(s.substring(pos));
		}
		return (String[]) l.toArray(new String[l.size()]);
	}
	
	/**
	 * 将字符串按指定的"分割符"分割成数值数组
	 * 
	 * @param s
	 * @param delim
	 * @return
	 */
	public static int[] splitToIntArray(String s, String delim) {
		String[] stringValueArray = split(s, delim);
		int[] intValueArray = new int[stringValueArray.length];
		for (int i = 0; i < intValueArray.length; i++) {
			intValueArray[i] = Integer.parseInt(stringValueArray[i]);
		}
		return intValueArray;
	}
}