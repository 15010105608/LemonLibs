package com.lemonjiang.cache;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.lemonjiang.cache.core.DiskLruCache;
import com.lemonjiang.cache.core.DiskLruCache.Editor;
import com.lemonjiang.cache.core.DiskLruCache.Snapshot;
import com.lemonjiang.secure.MD5;
import com.lemonjiang.util.DateUtil;
import com.lemonjiang.util.FileUtil;
import com.lemonjiang.util.LogUtil;


/**
 * 文件缓存基类-集成DiskLruCache
 */
public class FileCacheByDiskLruCache implements FileCache {

	private static final String TAG = "FileCacheByDiskLruCache";

	/** 缓存目录名称 */
	private String subDir;
	/** 缓存空间大小 */
	private long cacheSize;
	/** 缓存版本 */
	private static final int VERSION = 2014;
	/** 硬盘缓存对象 */
	private DiskLruCache dc;

	public FileCacheByDiskLruCache(String subDir, long cacheSize) {
		this.subDir = subDir;
		this.cacheSize = cacheSize;
		init();
	}

	@Override
	public void init() {
		String path = FileUtil.getDataRootDir() + File.separator + subDir;
		try {
			dc = DiskLruCache.open(FileUtil.getFile(path), VERSION, 1,
					cacheSize);
		} catch (IOException e) {
			LogUtil.log(TAG + "-init-IOException-e>" + e.getMessage());
		} finally {
		}
	}

	@Override
	public boolean save(String key, Cache cache) {
		if (dc != null) {
			if (!dc.isClosed() && cache != null) {
				try {
					Editor editor = dc.edit(getCacheFileName(key));
					if (editor != null) {
						String content = cache.getContent()
								+ DateUtil.dateToString(cache.getDate(),
										"yyyy-MM-dd HH:mm:ss");
						editor.set(0, content);
						editor.commit();
						return true;
					}
				} catch (IOException e) {
					LogUtil.log(TAG + "-save-IOException-e>" + e.getMessage());
				}
			}
		}
		return false;
	}

	@Override
	public Cache get(String key) {
		Cache cache = null;
		if (dc != null) {
			if (!dc.isClosed()) {
				try {
					Snapshot snapshot = dc.get(getCacheFileName(key));
					if (snapshot != null) {
						cache = new Cache();
						String content = snapshot.getString(0);
						if (!TextUtils.isEmpty(content)) {
							cache.setContent(content.substring(0,
									content.length() - 19));
							cache.setDate(DateUtil.stringToDate(
									content.substring(content.length() - 19),
									"yyyy-MM-dd HH:mm:ss"));
						}
					}
				} catch (IOException e) {
					LogUtil.log(TAG + "-get-IOException-e>" + e.getMessage());
				}
			}
		}
		return cache;
	}

	@Override
	public boolean delete(String key) {
		if (dc != null) {
			try {
				if (!dc.isClosed()) {
					return dc.remove(getCacheFileName(key));
				}
			} catch (IOException e) {
				LogUtil.log(TAG + "-delete-IOException-e>" + e.getMessage());
			}
		}
		return false;
	}

	@Override
	public void clear() {
		if (dc != null && dc.getDirectory().exists()) {
			FileUtil.deleteDir(dc.getDirectory());
		}
	}

	@Override
	public void releaseCache() {
		if (this.dc != null) {
			if (!this.dc.isClosed()) {
				try {
					this.dc.close();
				} catch (IOException e) {
					LogUtil.log(TAG + "-releaseCache-IOException-e>"
							+ e.getMessage());
				}
			}
			this.dc = null;
		}
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
