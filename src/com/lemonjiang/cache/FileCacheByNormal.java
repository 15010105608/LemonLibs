package com.lemonjiang.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.lemonjiang.secure.MD5;
import com.lemonjiang.util.DateUtil;
import com.lemonjiang.util.FileUtil;
import com.lemonjiang.util.LogUtil;

/**
 * 文件缓存基类-普通
 */
public class FileCacheByNormal implements FileCache {

	private static final String TAG = "FileCacheByNormal";
	
	/**后缀名*/
	private static final String SUFFIX=".0";
	
	/** 缓存目录名称 */
	private String subDir;
	/** 缓存空间大小 */
	private AtomicLong cacheSize;
	/** 缓存的总数量大小 */
	private AtomicInteger cacheCount;
	/** 缓存限制空间大小 */
	private final long sizeLimit;
	/** 缓存限制数量 */
	private final int countLimit;
	/** 文件最后使用日期 */
	private final ConcurrentHashMap<String, Long> lastUsageDates;
	/** 缓存目录文件 */
	private File cacheDir;

	/**
	 * @param subDir
	 *            缓存目录
	 * @param cacheSize
	 *            缓存空间大小
	 * */
	public FileCacheByNormal(String subDir, long sizeLimit) {
		this.subDir = subDir;
		this.countLimit = Integer.MAX_VALUE;
		this.sizeLimit = sizeLimit;
		this.cacheSize = new AtomicLong();
		cacheCount = new AtomicInteger();
		lastUsageDates = new ConcurrentHashMap<String, Long>();
		// lastUsageDates=Collections.synchronizedCollection(new HashMap<String,
		// Long>());
		init();
	}

	@Override
	public void init() {
		String path = FileUtil.getDataRootDir() + File.separator + subDir;
		FileUtil.createDir(path);
		cacheDir = new File(path);
		calculateCacheSizeAndCacheCount();
	}

	/**
	 * 计算 cacheSize和cacheCount
	 */
	private void calculateCacheSizeAndCacheCount() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int size = 0;
				int count = 0;
				File[] cachedFiles = cacheDir.listFiles();
				if (cachedFiles != null) {
					for (File cachedFile : cachedFiles) {
						size += calculateSize(cachedFile);
						count += 1;
						lastUsageDates.put(cachedFile.getAbsolutePath(),
								cachedFile.lastModified());
					}
					cacheSize.set(size);
					cacheCount.set(count);
				}
			}
		}).start();
	}

	/**
	 * 计算文件大小
	 * 
	 * @param file
	 * @return
	 */
	private long calculateSize(File file) {
		return file.length();
	}
	
	/**
	 * 获取缓存文件名
	 * @param key
	 * @return
	 */
	private String getFilePath(String key){
		return cacheDir.getAbsolutePath() + File.separator
				+ getCacheFileName(key) + SUFFIX;
	}

	@Override
	public boolean save(String key, Cache cache) {
		// 文件路径
		String filePath = getFilePath(key);
		// 内容
		String content = cache.getContent()
				+ DateUtil.dateToString(cache.getDate(), "yyyy-MM-dd HH:mm:ss");
		File file = new File(filePath);
		// 保存文件
		boolean flag_body = FileUtil.saveFile(file, content, false);
		if (flag_body) {
			int curCacheCount = cacheCount.get();
			// 判断文件数量是否超标
			while (curCacheCount + 1 > countLimit) {
				// 移除旧缓存文件
				long freedSize = removeNext();
				if (freedSize != -1) {
					// 计算总缓存大小-移除缓存大小
					cacheSize.addAndGet(-freedSize);
					// 缓存文件的数量-1
					curCacheCount = cacheCount.addAndGet(-1);
				}
			}
			// 缓存文件数量+1
			cacheCount.addAndGet(1);

			// 计算此文件大小
			long valueSize = calculateSize(file);
			// 获取当前缓存总大小
			long curCacheSize = cacheSize.get();
			// 判断当前缓存是否超标
			while (curCacheSize + valueSize > sizeLimit) {
				// 移除旧缓存文件
				long freedSize = removeNext();
				if (freedSize != -1) {
					// 计算总缓存大小-移除缓存大小
					curCacheSize = cacheSize.addAndGet(-freedSize);
					// 缓存文件的数量-1
					curCacheCount = cacheCount.addAndGet(-1);
				}
			}
			// 添加到总缓存大小
			cacheSize.addAndGet(valueSize);

			// 更新此文件更新日期
			Long currentTime = System.currentTimeMillis();
			file.setLastModified(currentTime);
			lastUsageDates.put(filePath, currentTime);
		}
		return flag_body;
	}

	@Override
	public Cache get(String key) {
		Cache cache = null;
		// 文件路径
		String filePath = getFilePath(key);
		String content = "";
		try {
			File file = new File(filePath);
			// 获取缓存内容
			content = FileUtil.inputStreamTocontent(new FileInputStream(file));
			if (!TextUtils.isEmpty(content)) {
				cache = new Cache();
				// 内容
				cache.setContent(content.substring(0, content.length() - 19));
				// 日期
				cache.setDate(DateUtil.stringToDate(
						content.substring(content.length() - 19),
						"yyyy-MM-dd HH:mm:ss"));
			}
			// 更新此文件更新日期
			Long currentTime = System.currentTimeMillis();
			file.setLastModified(currentTime);
			lastUsageDates.put(filePath, currentTime);
		} catch (IOException e) {
			LogUtil.log(TAG + "-get-IOException-e>" + e.getMessage());
		} finally {
		}
		return cache;
	}

	/**
	 * 移除旧的文件
	 * 
	 * @return 返回移除的缓存大小
	 */
	private long removeNext() {
		if (lastUsageDates.isEmpty()) {
			return 0;
		}
		Long oldestUsage = null;
		String mostLongUsedFilePath = null;
		// 遍历缓存文件列表
		Set<Entry<String, Long>> entries = lastUsageDates.entrySet();
		for (Entry<String, Long> entry : entries) {
			if (TextUtils.isEmpty(mostLongUsedFilePath)) {
				mostLongUsedFilePath = entry.getKey();
				oldestUsage = entry.getValue();
			} else {
				Long lastValueUsage = entry.getValue();
				// 查找旧文件路径
				if (lastValueUsage < oldestUsage) {
					oldestUsage = lastValueUsage;
					mostLongUsedFilePath = entry.getKey();
				}
			}
		}
		long fileSize = 0;
		boolean rs = false;
		if (!TextUtils.isEmpty(mostLongUsedFilePath)) {
			// 移除旧文件
			File file = new File(mostLongUsedFilePath);
			fileSize = calculateSize(file);
			rs = file.delete();
			if (rs) {
				lastUsageDates.remove(mostLongUsedFilePath);
			}
		}
		return rs ? fileSize : -1;
	}

	@Override
	public void clear() {
		releaseCache();

		if (cacheDir.exists()) {
			FileUtil.deleteDir(cacheDir);
		}
	}

	@Override
	public void releaseCache() {
		lastUsageDates.clear();
		cacheSize.set(0);
		cacheCount.set(0);
	}

	@Override
	public boolean delete(String key) {
		boolean rs = false;
		String filePath=getFilePath(key);
		File file = new File(filePath);
		long fileSize = calculateSize(file);
		rs = file.delete();
		if (rs) {
			lastUsageDates.remove(filePath);
			// 计算总缓存大小-移除缓存大小
			cacheSize.addAndGet(-fileSize);
			// 缓存文件的数量-1
			cacheCount.addAndGet(-1);
		}
		return rs;
	}

	/**
	 * 转换链接字符串
	 * */
	@SuppressLint("DefaultLocale")
	@Override
	public String getCacheFileName(String key) {
		return MD5.encode(key).toLowerCase();
	}
}
