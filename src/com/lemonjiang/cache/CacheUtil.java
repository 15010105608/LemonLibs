package com.lemonjiang.cache;


import java.io.File;
import java.util.Date;

import com.lemonjiang.config.FileConfig;
import com.lemonjiang.lemonlib.MainApp;
import com.lemonjiang.util.FileUtil;

/**
 * 
 * 缓存工具类
 */
public class CacheUtil {

	/**
	 * 清除WebView缓存
	 */
	public static void clearWebViewCache() {
		clearWebViewCacheDir(MainApp.getContext().getCacheDir());
		MainApp.getContext().deleteDatabase("webview.db");
		MainApp.getContext().deleteDatabase("webviewCache.db");
	}

	/**
	 * 清除缓存文件夹中WebView的缓存
	 * 
	 * @param file
	 *            文件对象
	 * @return 删除文件的数量
	 */
	private static int clearWebViewCacheDir(File file) {
		int deletedFiles = 0;
		if ((file != null) && (file.isDirectory()) && (file.exists())) {
			File[] list = file.listFiles();
			for (int i = 0, len = list.length; i < len; i++) {
				if (file.getName().equalsIgnoreCase("webviewcache")) {
					if (list[i].isDirectory()) {
						deletedFiles += clearWebViewCacheDir(list[i]);
					} else {
						if (list[i].delete()) {
							deletedFiles++;
						}
					}
				}
			}
		}
		return deletedFiles;
	}

	/**
	 * 自动清理缓存
	 * 
	 * @param day
	 *            过期天数
	 * */
	public static int autoClearCache(int day) {
		return excuteClear(new File(FileUtil.getDataRootDir()
				+ FileConfig.FILE_CACHE_DIR), day);
	}

	/**
	 * 遍历查找过期缓存文件并删除
	 * 
	 * @param file
	 *            文件对象
	 * @param day
	 *            过期天数
	 * @return 删除文件的数量
	 */
	private static int excuteClear(File file, int day) {
		int deletedFiles = 0;
		if ((file.exists()) && (file.isDirectory())) {
			long date = new Date().getTime();
			File[] list = file.listFiles();
			if (list != null) {
				for (int i = 0, len = list.length; i < len; i++) {
					if (list[i].isDirectory()) {
						deletedFiles += excuteClear(list[i], day);
					} else {
						if (clearFile(list[i], date, day)) {
							deletedFiles++;
						}
					}
				}
			}
		}
		return deletedFiles;
	}

	/**
	 * 超过缓存期限就删除该文件
	 * 
	 * @param file
	 *            文件对象
	 * @param date
	 *            日期毫秒数
	 * @param day
	 *            过期天数
	 * @return 是否成功
	 */
	private static boolean clearFile(File file, long date, int day) {
		if ((file != null) && (file.exists()) && (file.isFile())
				&& ((date - file.lastModified()) / 86400000L > day)) {
			return file.delete();
		}
		return false;
	}
}
