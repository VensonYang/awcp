package BP.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileAccess {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileAccess.class);

	public static boolean Move(File srcFile, String destPath) {
		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

		return success;
	}

	public static boolean Move(String srcFile, String destPath) {
		// File (or directory) to be moved
		File file = new File(srcFile);

		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = file.renameTo(new File(dir, file.getName()));

		return success;
	}

	public static void Copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					logger.debug(bytesum + "");
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			logger.debug("error  ");
			e.printStackTrace();
		}
	}

	public static void Copy(File oldfile, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			// File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					logger.debug(bytesum + "");
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			logger.debug("error  ");
			e.printStackTrace();
		}
	}

	public static void Copy(InputStream inStream, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			FileOutputStream fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				logger.debug(bytesum + "");
				fs.write(buffer, 0, byteread);
			}
			fs.close();
		} catch (Exception e) {
			logger.debug("error  ");
			e.printStackTrace();
		}
	}

	public static void copyFolder(File src, File dest) {
		try {
			if (src.isDirectory()) {
				if (!dest.exists()) {
					dest.mkdirs();
				}
				String files[] = src.list();
				for (String file : files) {
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					// 递归复制
					copyFolder(srcFile, destFile);
				}
			} else {
				InputStream in = new FileInputStream(src);
				OutputStream out = new FileOutputStream(dest);

				byte[] buffer = new byte[1024];

				int length;

				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFileByBytes(String fileName) {
		int len = 0;
		String line = null;
		StringBuffer str = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			;
			while ((line = in.readLine()) != null) {
				if (len != 0) {// 处理换行符的问题
					str.append("\r\n" + line);
				} else {
					str.append(line);
				}
				len++;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	/*
	 * Java文件操作 获取文件扩展名
	 *
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 *
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
}