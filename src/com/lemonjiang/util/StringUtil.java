package com.lemonjiang.util;

public class StringUtil {

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	/**
	 * byte[]转换成16进制值字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byteArr2HexString2(byte[] b) throws Exception {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * byte[]转换成16进制值字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byteArr2HexString(byte[] b) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = b.length; i < len; i++) {
			String stmp = Integer.toHexString(b[i] & 0xff);
			if (stmp.length() == 1)
				sb.append("0" + stmp);
			else
				sb.append(stmp);
		}
		return sb.toString();
	}

	/**
	 * 16进制值字符串转换成byte[]
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexString2ByteArr(String hexString) throws Exception {
		if (hexString.length() % 2 == 1)
			return null;
		byte[] arrB = hexString.getBytes();
		int len = arrB.length;
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 转换int 转换错误时返回-1
	 * 
	 * @param str
	 * @return
	 */
	public static int getInt(String str) {
		int a = -1;
		try {
			a = Integer.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * 转换long 转换错误时返回-1
	 * 
	 * @param str
	 * @return
	 */
	public static long getLong(String str) {
		long a = -1;
		try {
			a = Long.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	

	public static String subString(String str, int start) {
		String str1 = "";
		try {
			str1 = str.substring(start);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str1;
	}

	public static String subString(String str, int start, int end) {
		String str1 = "";
		try {
			str1 = str.substring(start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str1;
	}
}
