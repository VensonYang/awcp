package org.szcloud.framework.formdesigner.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
	
	public static String getCurrentPath(){	
		return null;
	}
	
	/**
	 * 读取文本文件内容
	 * @param file
	 * @return
	 */
	public static String readConent(File file){
		Long fileLengthLong = file.length();
		byte[] fileContent = new byte[fileLengthLong.intValue()];
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		    inputStream.read(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return  new String(fileContent);
	}
}
