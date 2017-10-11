package cn.org.awcp.core.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cn.org.awcp.core.utils.constants.SessionContants;

public abstract class Tools {

	// ================================开始：时间工具类=================================//
	public static final String HHMM = "HHmm";
	public static final String DDMMMYY = "ddMMMyy";
	public static final String DD_MMMYYYY_HHMM = "ddMMMyyyy HHmm";
	public static final String DD_MMMYYYY_HH_MM = "ddMMMyyyy HH:mm";
	public static final String DD_MMMYYYY_HH_MM_SS = "ddMMMyyyy HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY2MM2DD = "yyyy/MM/dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String HH_MM = "HH:mm";
	public static final String YYYY_MM_DD2HH_mm = "yyyy-MM-dd-HH-mm";
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

	/**
	 * 解析日期<br>
	 * 支持格式：<br>
	 * generate by: vakin jiang at 2012-3-1
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		SimpleDateFormat format = null;
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}

		String _dateStr = dateStr.trim();
		try {
			if (_dateStr.matches("\\d{1,2}[A-Z]{3}")) {
				_dateStr = _dateStr
						+ (Calendar.getInstance().get(Calendar.YEAR) - 2000);
			}
			// 01OCT12
			if (_dateStr.matches("\\d{1,2}[A-Z]{3}\\d{2}")) {
				format = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);
			} else if (_dateStr.matches("\\d{1,2}[A-Z]{3}\\d{4}.*")) {// 01OCT2012
				// ,01OCT2012
				// 1224,01OCT2012
				// 12:24
				_dateStr = _dateStr.replaceAll("[^0-9A-Z]", "")
						.concat("000000").substring(0, 15);
				format = new SimpleDateFormat("ddMMMyyyyHHmmss", Locale.ENGLISH);
			} else {
				StringBuffer sb = new StringBuffer(_dateStr);
				String[] tempArr = _dateStr.split("\\s+");
				tempArr = tempArr[0].split("-|\\/");
				if (tempArr.length == 3) {
					if (tempArr[1].length() == 1) {
						sb.insert(5, "0");
					}
					if (tempArr[2].length() == 1) {
						sb.insert(8, "0");
					}
				}
				_dateStr = sb.append("000000").toString()
						.replaceAll("[^0-9]", "").substring(0, 14);
				if (_dateStr.matches("\\d{14}")) {
					format = new SimpleDateFormat("yyyyMMddHHmmss");
				}
			}

			Date date = format.parse(_dateStr);
			return date;
		} catch (Exception e) {
			throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
		}
	}

	/**
	 * 解析日期字符串转化成日期格式<br>
	 * generate by: vakin jiang at 2012-3-1
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		try {
			SimpleDateFormat format = null;
			if (StringUtils.isBlank(dateStr)) {
				return null;
			}

			if (StringUtils.isNotBlank(pattern)) {
				format = new SimpleDateFormat(pattern);
				return format.parse(dateStr);
			}
			return parseDate(dateStr);
		} catch (Exception e) {
			throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
		}
	}

	/**
	 * 获取一天开始时间<br>
	 * generate by: vakin jiang at 2011-12-23
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		String format = DateFormatUtils.format(date, YYYY_MM_DD);
		return parseDate(format.concat(" 00:00:00"));
	}

	/**
	 * 获取一天结束时间<br>
	 * generate by: vakin jiang at 2011-12-23
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		String format = DateFormatUtils.format(date, YYYY_MM_DD);
		return parseDate(format.concat(" 23:59:59"));
	}

	/**
	 * 时间戳格式转换为日期（年月日）格式<br>
	 * generate by: vakin jiang at 2011-12-23
	 * 
	 * @param date
	 * @return
	 */
	public static Date timestamp2Date(Date date) {
		return formatDate(date, YYYY_MM_DD);
	}

	/**
	 * 时间戳格式转换为日期（年月日）格式<br>
	 * generate by: vakin jiang at 2011-12-23
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate2YYYY_MM_DD(Date date) {
		return format(date, YYYY_MM_DD);
	}

	/**
	 * 格式化日期格式为：ddMMMyy<br>
	 * generate by: vakin jiang at 2012-10-17
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String format2ddMMMyy(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMMyy",
				Locale.ENGLISH);
		return format.format(date).toUpperCase();
	}

	/**
	 * 格式化日期格式为：ddMMMyy<br>
	 * generate by: vakin jiang at 2012-10-17
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String format2ddMMMyy(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMMyy",
				Locale.ENGLISH);
		return format.format(parseDate(dateStr)).toUpperCase();
	}

	/**
	 * 格式化日期字符串<br>
	 * generate by: vakin jiang at 2012-3-7
	 * 
	 * @param dateStr
	 * @param patterns
	 * @return
	 */
	public static String formatDateStr(String dateStr, String... patterns) {
		String pattern = YYYY_MM_DD_HH_MM_SS;
		if (patterns != null && patterns.length > 0
				&& StringUtils.isNotBlank(patterns[0])) {
			pattern = patterns[0];
		}
		return DateFormatUtils.format(parseDate(dateStr), pattern);
	}

	/**
	 * 格式化日期为日期字符串<br>
	 * generate by: vakin jiang at 2012-3-7
	 * 
	 * @param orig
	 * @param patterns
	 * @return
	 */
	public static String format(Date date, String... patterns) {
		if (date == null)
			return "";
		String pattern = YYYY_MM_DD_HH_MM_SS;
		if (patterns != null && patterns.length > 0
				&& StringUtils.isNotBlank(patterns[0])) {
			pattern = patterns[0];
		}
		return DateFormatUtils.format(date, pattern);
	}

	public static String format2YYYY_MM_DD(Date date) {
		return format(date, YYYY_MM_DD);
	}

	/**
	 * 格式化日期为指定格式<br>
	 * generate by: vakin jiang at 2012-3-7
	 * 
	 * @param orig
	 * @param patterns
	 * @return
	 */
	public static Date formatDate(Date orig, String... patterns) {
		String pattern = YYYY_MM_DD_HH_MM_SS;
		if (patterns != null && patterns.length > 0
				&& StringUtils.isNotBlank(patterns[0])) {
			pattern = patterns[0];
		}
		return parseDate(DateFormatUtils.format(orig, pattern));
	}

	// ================================结束：时间工具类=================================//

	// =================================开始：加密================================//
	/**
	 * @param inStr
	 *            加密前的字符串
	 * @return 加密后的结果
	 */
	public static String encrypt(String inStr) {
		MessageDigest md = null;// 加密算法
		String out = null;// 加密后的字符串
		try {
			md = MessageDigest.getInstance("MD5");// 定义加密算法
			byte[] digest = md.digest(inStr.getBytes());// 用MD5进行加密
			out = byte2hex(digest);// 转码
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return out;
	}

	/**
	 * 本方法实现转码
	 * 
	 * @param b
	 *            转码前的数组
	 * @return 转码后的String
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	// ===============================结束：加密=======================================//

	// ===============================开始：文件操作====================================//
	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * 
	 * @param directory
	 *            要清空的目录
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
	 * @since 0.1
	 */
	public static boolean emptyDirectory(File f) {
		@SuppressWarnings("unused")
		boolean result = false;
		if (f != null) {
			if (f.isDirectory()) {
				File[] entries = f.listFiles();
				for (int i = 0; i < entries.length; i++) {
					if (!entries[i].delete()) {
						result = false;
					}
				}
			} else if (f.exists()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * 
	 * @param directoryName
	 *            要清空的目录的目录名
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean emptyDirectory(String directoryName) {
		File dir = new File(directoryName);
		if (dir.exists()) {
			return emptyDirectory(dir);
		} else {
			return true;
		}
	}

	/**
	 * 迭代删除删除指定目录及其中的所有内容。
	 * 
	 * @param dirName
	 *            要删除的目录的目录名或文件全路径。
	 * @return 删除成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean deleteFile(String dirName) {
		return deleteFile(new File(dirName));
	}

	/**
	 * 删除指定目录及其中的所有内容。
	 * 
	 * @param dir
	 *            要删除的目录
	 * @return 删除成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean deleteFile(File dir) {
		if ((dir == null) || (!dir.exists()))
			return true;
		if (dir.isFile()) {
			return dir.delete();
		}

		File[] entries = dir.listFiles();
		int sz = entries.length;

		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteFile(entries[i])) {
					return false;
				}
			} else {
				if (!entries[i].delete()) {
					return false;
				}
			}
		}

		return dir.delete();
	}

	/**
	 * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
	 * 其实就是将路径中的"\"全部换为"/"，因为在某些情况下我们转换为这种方式比较方便，
	 * 某中程度上说"/"比"\"更适合作为路径分隔符，而且DOS/Windows也将它当作路径分隔符。
	 * 
	 * @param filePath
	 *            转换前的路径
	 * @return 转换后的路径
	 * @since 0.4
	 */
	public static String toUNIXpath(String filePath) {
		return filePath.replace('\\', '/');
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 * @since 0.5
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param file
	 *            文件
	 * @return 文件名中的类型部分
	 * @since 0.5
	 */
	public static String getFileType(File file) {
		return getTypePart(file.getName());
	}

	/**
	 * 得到文件名。 实际上就是得到文件名中最后一个路径分隔符后面的部分。
	 * 
	 * @param filePath
	 *            带路径的文件名，可是相对路径或绝对路径
	 * @return 文件名中最后一个路径分隔符后面的部分
	 * @since 0.5
	 */
	public static String getFileName(String filePath) {
		if (filePath == null)
			return null;
		String unixPath = toUNIXpath(filePath);
		if (unixPath.lastIndexOf('/') != -1) {
			return unixPath.substring(unixPath.lastIndexOf('/') + 1);
		} else {
			return unixPath;
		}
	}

	/**
	 * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
	 * 
	 * @param pathName
	 *            目录名
	 * @param fileName
	 *            文件名
	 * @return 得到文件名相对于目录名的相对路径，目录下不存在该文件时返回文件名
	 * @since 0.5
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}

	public static String getFileContent(String fileName) {
		StringBuilder sb = new StringBuilder();
		File file = new File(fileName);
		if (file.isFile() && file.exists() && getFileType(file).equalsIgnoreCase("txt")) {
			String encoding = "GBK";
			BufferedReader br = null;
			try {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
				br = new BufferedReader(read);// 构造一个BufferedReader类来读取文件
				String s = null;
				while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
					if(sb.length() != 0) {
						sb.append("\n");
					}
					sb.append(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sb.toString();
	}
	
	public static String getFileContent(InputStream is, String fileName, String encoding) {
		StringBuilder sb = new StringBuilder();
		if (is != null && getTypePart(fileName).equalsIgnoreCase("txt")) {
			if(org.apache.commons.lang3.StringUtils.isBlank(encoding)) {
				encoding = "UTF-8";
			}
			BufferedReader br = null;
			try {
				InputStreamReader read = new InputStreamReader(is, encoding);//考虑到编码格式
				br = new BufferedReader(read);// 构造一个BufferedReader类来读取文件
				String s = null;
				while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
					if(sb.length() != 0) {
						sb.append("\n");
					}
					sb.append(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sb.toString();
	}

	// ==============================结束：文件操作=================================//

	// ==============================开始：shiro session操作======================//
	/**
	 * 获取当前受shiro控制的Session
	 * 
	 * @return
	 */
	public static Session getCurrentSession() {
		Subject subject = SecurityUtils.getSubject();
		return subject.getSession();
	}

	/**
	 * 从受shiro控制的session中获取对象
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObjectFromSession(String key) {
		Session session = getCurrentSession();
		return session.getAttribute(key);
	}

	/**
	 * 往session中存入对象
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void addObjectToSession(Object key, Object value) {
		Session session = getCurrentSession();
		session.setAttribute(key, value);
	}

	/**
	 * 清除session中的值
	 */
	public static void clearSession() {
		Session session = getCurrentSession();
		session.removeAttribute(SessionContants.CURRENT_USER);
		session.removeAttribute(SessionContants.CURRENT_RESOURCES);
		session.removeAttribute(SessionContants.CURRENT_ROLES);
		session.removeAttribute(SessionContants.CURRENT_SYSTEM);
		session.removeAttribute(SessionContants.CURRENT_USER_GROUP);
	}

	// ==============================结束：shiro
	// session操作========================================//

	// ==============================开始：xml操作=================================================//
	/**
	 * 从文件名初始化，如初始化成功返回ROOT，否则返回NULL
	 * 
	 * @param xmlSource
	 */
	static public Document parseXml(String xmlSource) throws Exception {
		if (xmlSource == null)
			return null;
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(xmlSource);
		return parseXml(in);
	}

	/**
	 * 从文件名初始化，如初始化成功返回ROOT，否则返回NULL
	 * 
	 * @param xmlSource
	 */
	static public Document parseXml(byte[] xmlData) throws Exception {
		if (xmlData == null)
			return null;

		java.io.ByteArrayInputStream in = new ByteArrayInputStream(xmlData);
		return parseXml(in);
	}

	/**
	 * 从XML文件输入流初始化，如初始化成功返回true，否则返回false
	 */
	static public Document parseXml(File xmlFile) throws Exception {
		if (xmlFile == null || !xmlFile.exists())
			return null;
		Document root = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setValidation(false);
			root = saxReader.read(xmlFile);
			return root;
		} catch (Exception e) {
			System.err.println("解析XML输入流出现异常，请检查XML输入流是否正确:"
					+ xmlFile.getName());
			throw e;
		}
	}

	/**
	 * 从XML文件输入流初始化，如初始化成功返回true，否则返回false
	 */
	static public Document parseXml(InputStream in) throws Exception {
		if (in == null)
			return null;
		Document root = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setValidation(false);
			root = saxReader.read(in);
			return root;
		} catch (Exception e) {
			System.err.println("解析XML输入流出现异常，请检查XML输入流是否正确:" + in);
			throw e;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception e) {
				}
		}
	}

	/**
	 * 检查某路径的节点配置是否存在
	 * 
	 * @param xql
	 * @return
	 */
	static public boolean isNodeExists(Node startNode, String xql) {
		if (startNode == null || xql == null) {
			return false;
		}

		Node node = startNode.selectSingleNode(xql);
		return (node != null);
	}

	/**
	 * 根据相对节点，获取配置项值。
	 * 
	 * @param startNode
	 *            相对节点
	 * @param xql
	 *            相对startNode表示的配置项路径
	 */
	static public String getString(Node startNode, String xql) {
		if (startNode == null || xql == null) {
			return null;
		}

		Node node = startNode.selectSingleNode(xql);
		if (node != null) {
			return node.getText();
		} else {
			return null;
		}
	}

	/**
	 * 获取配置项
	 * 
	 * @param xql
	 * @return
	 */
	static public String getString(Node startNode, String xql, String def) {
		if (startNode == null || xql == null) {
			return def;
		}

		Node node = startNode.selectSingleNode(xql);
		if (node != null) {
			return node.getText();
		} else {
			return def;
		}
	}

	/**
	 * 获取int类型的配置项，如该项不存在或不能转为int，则返回默认值。
	 *
	 * @param xql
	 *            XPATH表示的配置项路径
	 * @param def
	 *            默认值
	 * @return
	 */
	static public int getInt(Node startNode, String xql, int def) {
		String nValue = getString(startNode, xql);
		try {
			return Integer.parseInt(nValue);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 获取float类型的配置项，如该项不存在或不能转为float，则返回默认值。
	 *
	 * @param xql
	 *            XPATH表示的配置项路径
	 * @param def
	 *            默认值
	 * @return
	 */
	static public float getFloat(Node startNode, String xql, float def) {
		String nValue = getString(startNode, xql);
		try {
			return Float.parseFloat(nValue);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 获取long类型的配置项，如该项不存在或不能转为long，则返回默认值。
	 *
	 * @param xql
	 *            XPATH表示的配置项路径
	 * @param def
	 *            默认值
	 * @return
	 */
	static public long getLong(Node startNode, String xql, long def) {
		String nValue = getString(startNode, xql);
		try {
			return Long.parseLong(nValue);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 输出节点的内容并关闭输出流
	 * 
	 * @param root
	 *            Node
	 * @param out
	 *            OutputStream
	 * @param indent
	 *            boolean 是否要输出空格
	 * @param newLine
	 *            boolean 是否要换行
	 */
	static public void printAndClose(Document document, OutputStream out,
			boolean indent, boolean newLine) {
		org.dom4j.io.XMLWriter writer = null;
		try {
			org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat
					.createPrettyPrint();
			format.setEncoding("GBK");
			format.setIndent(indent);
			format.setNewlines(newLine);
			// 以下两行设为TRUE则没有<?xml version="1.0" encoding="GBK"?>
			format.setOmitEncoding(false);
			format.setSuppressDeclaration(false);

			writer = new org.dom4j.io.XMLWriter(out, format);
			writer.write(document);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
				}
		}
	}

	/**
	 * 输出节点的内容并关闭输出流
	 * 
	 * @param root
	 *            Node
	 * @param out
	 *            OutputStream
	 */
	static public void printAndClose(Document document, OutputStream out) {
		org.dom4j.io.XMLWriter writer = null;
		try {
			org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat
					.createPrettyPrint();
			format.setEncoding("GBK");
			format.setTrimText(true);
			format.setIndent(true);
			format.setNewlines(true);
			// 以下两行设为TRUE则没有<?xml version="1.0" encoding="GBK"?>
			format.setOmitEncoding(false);
			format.setSuppressDeclaration(false);
			writer = new org.dom4j.io.XMLWriter(out, format);
			writer.write(document);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
				}
		}
	}

	/**
	 * 输出节点的内容
	 * 
	 * @param root
	 *            Node
	 * @param out
	 *            OutputStream
	 */
	static public void print(Document document, OutputStream out) {
		org.dom4j.io.XMLWriter writer = null;
		org.dom4j.io.OutputFormat format = new org.dom4j.io.OutputFormat();
		format.setEncoding("GBK");
		format.setTrimText(true);
		format.setIndent(false);
		// 以下两行设为TRUE则没有<?xml version="1.0" encoding="GBK"?>
		format.setOmitEncoding(false);
		format.setSuppressDeclaration(false);
		try {
			writer = new org.dom4j.io.XMLWriter(out, format);
			writer.write(document);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 输出节点的内容
	 * 
	 * @param root
	 *            Node
	 * @param out
	 *            OutputStream
	 */
	static public void print(Document document, OutputStream out, String charSet) {
		org.dom4j.io.XMLWriter writer = null;
		org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat
				.createPrettyPrint();
		format.setNewlines(true);
		format.setEncoding(charSet);
		format.setTrimText(true);
		format.setIndent(true);
		// 以下两行设为TRUE则没有<?xml version="1.0" encoding="GBK"?>
		format.setOmitEncoding(false);
		format.setSuppressDeclaration(false);

		try {
			writer = new org.dom4j.io.XMLWriter(out, format);
			writer.write(document);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
			} catch (Exception e) {
			}
		}
	}

	static public void main(String[] args) {
		try {
			Document document = org.dom4j.DocumentHelper.createDocument();// .parseText("<?xml version=\"1.0\" encoding=\"GBK\"?><system/>")
																			// ;
			org.dom4j.Element root = document.addElement("system");

			java.util.Properties properties = System.getProperties();
			for (Enumeration<?> elements = properties.propertyNames(); 
					elements.hasMoreElements();) {
				String name = (String) elements.nextElement();
				String value = properties.getProperty(name);
				org.dom4j.Element element = root.addElement("property");
				element.addAttribute("name", name);
				element.addText(value);
			}
			root.addElement("tree").addAttribute("text", "测试")
					.addAttribute("src", "./admin/createMenu.do?pId=12");
			Tools.printAndClose(document, System.out, true, true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	static private Document createDocument() {
		try {
			Document document = org.dom4j.DocumentHelper.createDocument();// .parseText("<?xml version=\"1.0\" encoding=\"GBK\"?><system/>")
																			// ;
			org.dom4j.Element root = document.addElement("system");

			java.util.Properties properties = System.getProperties();
			for (Enumeration<?> elements = properties.propertyNames(); elements
					.hasMoreElements();) {
				String name = (String) elements.nextElement();
				String value = properties.getProperty(name);
				org.dom4j.Element element = root.addElement("property");
				element.addAttribute("name", name);
				element.addText(value);
			}
			root.addElement("tree").addAttribute("text", "测试")
					.addAttribute("src", "./admin/createMenu.do?pId=12");

			return document;
		} catch (Exception ex) {
			return null;
		}
	}
	// ===============================结束：xml操作================================================//
}
