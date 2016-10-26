package com.lemonjiang.cache;


import java.io.File;

import com.lemonjiang.config.Constants;
import com.lemonjiang.config.FileConfig;
import com.lemonjiang.lemonlib.MainApp;

/**
 * 用户缓存
 */
public class UserFileCache {
	private static FileCacheManager mCacheManager;

	private UserFileCache(){
	}
	
	/**
	 * 获取缓存对象
	 * 
	 * @return
	 */
	public synchronized static FileCache getInstance() {
		if (mCacheManager == null) {
			String username = MainApp.getPref(Constants.KEY_USER_CACHE_FLAG,
					"user") + File.separator;
			mCacheManager = new FileCacheManager(FileConfig.FILE_CACHE_DIR_USER
					+ username, FileConfig.CACHESIZE_USER);
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
