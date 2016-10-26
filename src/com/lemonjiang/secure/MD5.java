package com.lemonjiang.secure;


import java.security.MessageDigest;

import com.lemonjiang.util.StringUtil;

/**
 * MD5
 */
public class MD5 {

	/**
	 * MD5加密
	 * 
	 * @param source
	 *            加密字符串
	 * @return
	 */
	public static String encode(String source) {
		String rs = null;
		try {
			rs = StringUtil.byteArr2HexString2(MessageDigest.getInstance("MD5")
					.digest(source.getBytes("UTF-8")));

		} catch (Exception e) {
		}
		return rs;
	}
}
