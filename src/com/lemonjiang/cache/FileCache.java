package com.lemonjiang.cache;

public interface FileCache {
	
	/**
	 * 初始化
	 * */
	public void init();

	/**
	 * 保存缓存
	 * 
	 * @param key
	 *            缓存Key
	 * @param cache
	 *            缓存内容
	 * */
	public boolean save(String key, Cache cache);

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            缓存key
	 * */
	public Cache get(String key);

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            缓存key
	 * */
	public boolean delete(String key);

	/**
	 * 释放缓存资源
	 * */
	public void releaseCache();
	
	/**
	 * 清空缓存
	 */
	public void clear();

	/**
	 * 转换链接字符串
	 * */
	public String getCacheFileName(String key);
}
