package com.lemonjiang.cache;

import com.lemonjiang.config.FileConfig;


/**
 * 基本数据缓存
 */
public class BaseDataFileCache{
	private static FileCacheManager mCacheManager;

	private BaseDataFileCache() {
	}

	/**
	 * 获取缓存对象
	 * 
	 * @return
	 */
	public synchronized static FileCache getInstance() {
		if (mCacheManager == null) {
			mCacheManager = new FileCacheManager(
					FileConfig.FILE_CACHE_BASEDATA_DIR,
					FileConfig.CACHESIZE_BASEDATA);
		}
		return mCacheManager.getFileCache();
	}
	
	/**
	 * 获取缓存对象
	 * 
	 * @return
	 */
	public static FileCacheManager getFileCacheManager() {
		return mCacheManager;
	}
	
	/**
	 * 清空缓存对象
	 */
	public static void clearFileCacheManager() {
		mCacheManager = null;
	}
}
