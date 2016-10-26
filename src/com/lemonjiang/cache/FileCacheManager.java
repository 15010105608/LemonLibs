package com.lemonjiang.cache;


/**
 * 缓存管理类，默认DISKLRUCACHE缓存模式
 */
public class FileCacheManager {
	public FileCache mCache;
	/**缓存模式*/
	private static int CACHEFILEMODEL;
	
	static{
		CACHEFILEMODEL = FileCacheManager.MODEL_DISKLRUCACHE;
	}
	
	/**普通缓存*/
	public static final int MODEL_NORMAL = 0;
	/**DISKLRUCACHE缓存*/
	public static final int MODEL_DISKLRUCACHE = 1;

	public FileCacheManager(String subDir, long cacheSize) {
		if (CACHEFILEMODEL == MODEL_NORMAL) {
			mCache = new FileCacheByNormal(subDir, cacheSize);
		} else if (CACHEFILEMODEL == MODEL_DISKLRUCACHE) {
			mCache = new FileCacheByDiskLruCache(subDir, cacheSize);
		}else{
			mCache = new FileCacheByDiskLruCache(subDir, cacheSize);
		}
	}
	
	/**
	 * 设置缓存模式
	 * 
	 * FileCacheManager.MODEL_NORMAL 普通模式
	 * FileCacheManager.MODEL_DISKLRUCACHE DISKLRUCACHE模式
	 * 
	 * @param cacheFileModel
	 */
	public static void setCacheFileModel(int cacheFileModel) {
		CACHEFILEMODEL = cacheFileModel;
	}

	/**
	 * 获取缓存模式
	 * 
	 * FileCacheManager.MODEL_NORMAL 普通模式
	 * FileCacheManager.MODEL_DISKLRUCACHE DISKLRUCACHE模式
	 * 
	 * @return
	 */
	public static int getCacheFileModel() {
		return CACHEFILEMODEL;
	}

	/**
	 * 获取缓存对象
	 * 
	 * @return
	 */
	protected FileCache getFileCache() {
		return mCache;
	}
}
