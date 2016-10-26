package com.lemonjiang.secure;

/**
 * Base64
 */
public class Base64 {

	/**
	 * 加密
	 * 
	 * @param source
	 *            加密字符串
	 * @return
	 */
	public static String encode(String source) {
		byte[] encode = android.util.Base64.encode(source.getBytes(),
				android.util.Base64.DEFAULT);
		return new String(encode);
	}

	/**
	 * 解密
	 * 
	 * @param source
	 *            解密字符串
	 */
	public static String decode(String source) {
		byte[] decode = android.util.Base64.decode(source,
				android.util.Base64.DEFAULT);
		return new String(decode);
	}
}
