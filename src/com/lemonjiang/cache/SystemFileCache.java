package com.lemonjiang.cache;

import com.lemonjiang.config.FileConfig;


/**
 * 系统缓存
 */
public class SystemFileCache {
	private static FileCacheManager mCacheManager;

	private SystemFileCache() {
	}

	/**
	 * 获取缓存对象
	 * 
	 * @return
	 */
	public synchronized static FileCache getInstance() {
		if (mCacheManager == null) {
			mCacheManager = new FileCacheManager(
					FileConfig.FILE_CACHE_SYSTEM_DIR,
					FileConfig.CACHESIZE_SYSTEM);
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
