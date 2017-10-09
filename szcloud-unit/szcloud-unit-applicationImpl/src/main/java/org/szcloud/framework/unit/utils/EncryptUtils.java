package org.szcloud.framework.unit.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
* @ClassName: EncryptUtils 
* @Description: 加密，解密工具
* @author ljw 
* @date 2014年9月3日 下午4:00:18 
*
 */
public class EncryptUtils {
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
}
