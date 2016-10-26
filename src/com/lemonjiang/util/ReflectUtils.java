package com.lemonjiang.util;

import java.lang.reflect.Field;

import android.text.TextUtils;

/**
 * 反射工具类
 * 
 * @author xumingming
 *
 */
public final class ReflectUtils {

	private ReflectUtils() {
		super();
	}

	public static boolean isString(Class<?> clazz) {
		return clazz == String.class;
	}

	public static boolean isInteger(Class<?> clazz) {
		return clazz == Integer.TYPE || clazz == Integer.class;
	}

	public static boolean isLong(Class<?> clazz) {
		return clazz == Long.TYPE || clazz == Long.class;
	}

	public static boolean isShort(Class<?> clazz) {
		return clazz == Short.TYPE || clazz == Short.class;
	}

	public static boolean isFloat(Class<?> clazz) {
		return clazz == Float.TYPE || clazz == Float.class;
	}

	public static boolean isDouble(Class<?> clazz) {
		return clazz == Double.TYPE || clazz == Double.class;
	}

	/**
	 * 获取对象的私有变量-父类
	 */
	public static Object getgetSuperField(Object instance, String name)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Object object = null;
		if (instance != null && !TextUtils.isEmpty(name)) {
			Field field = instance.getClass().getSuperclass().getDeclaredField(name);
			if (field != null) {
				field.setAccessible(true);
				object = field.get(instance);
			}
		}
		return object;
	}
	
	/**
	 * 获取对象的私有变量
	 */
	public static Object getgetField(Object instance, String name)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Object object = null;
		if (instance != null && !TextUtils.isEmpty(name)) {
			Field field = instance.getClass().getDeclaredField(name);
			if (field != null) {
				field.setAccessible(true);
				object = field.get(instance);
			}
		}
		return object;
	}
}