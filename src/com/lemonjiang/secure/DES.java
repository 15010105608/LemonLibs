package com.lemonjiang.secure;

import android.annotation.SuppressLint;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.lemonjiang.util.StringUtil;

public class DES {
	private final static String IV = "12345678";
	private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * 加密
	 * 
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @param source
	 *            加密字符串
	 * @return
	 */
	public static String encode(String key, String source) {
		String rs = null;
		try {
			rs = StringUtil.byteArr2HexString(encrypt(key, source.getBytes()));
		} catch (Exception e) {
		}
		return rs;
	}

	/**
	 * 解密
	 * 
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @param source
	 *            解密字符串
	 * @return
	 */
	public static String decode(String key, String source) {
		String rs = null;
		try {
			rs = new String(decrypt(key, StringUtil.hexString2ByteArr(source)));
		} catch (Exception e) {
		}
		return rs;
	}

	/**
	 * 加密
	 * 
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @param data
	 *            加密字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	@SuppressLint("TrulyRandom")
	public static byte[] encrypt(String key, byte[] data) throws Exception {
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(data);
	}

	/**
	 * DES算法，解密
	 * 
	 * @param key
	 *            解密私钥，长度不能够小于8位
	 * @param data
	 *            解密字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public static byte[] decrypt(String key, byte[] data) throws Exception {
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		return cipher.doFinal(data);
	}
}
