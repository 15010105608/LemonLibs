package com.lemonjiang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 属性文件工具类
 * */
public class PreferencesUtil {

	private SharedPreferences mPreferences;

	/**
	 * 属性文件工具类
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            名称
	 * @param isWrite
	 *            是否可写
	 */
	public PreferencesUtil(Context context, String name) {
		mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	/**
	 * 根据键获取值
	 * 
	 * @param key
	 *            键
	 * @param defValue
	 *            默认值
	 * @return 值
	 */
	public String get(String key, String defValue) {
		return mPreferences.getString(key, defValue);
	}

	/**
	 * 保存键值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return 是否保存成功
	 */
	public boolean save(String key, String value) {
		Editor editor = mPreferences.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * 获取属性文件
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            名称
	 * @return
	 */
	public static SharedPreferences getInstance(Context context, String name) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}
}
