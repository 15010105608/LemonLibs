package com.lemonjiang.config;

import java.io.File;

/**
 * 文件配置信息
 */
public class FileConfig {
	// ///////////////////////////////////////////目录配置////////////////////////////////////

	/** 根目录的文件夹名称 */
	public static String FILE_ROOT_NAME;

	/**
	 * 初始化
	 */
	public static void init(String dirName) {
		FileConfig.FILE_ROOT_NAME = dirName;

		FILE_ROOT_DIR = FILE_ROOT_NAME + File.separator;
		FILE_CACHE_DIR = FILE_ROOT_DIR + "cache" + File.separator;
		FILE_CACHE_DIR_USER = FILE_CACHE_DIR + "user" + File.separator;
		FILE_CACHE_SYSTEM_DIR = FILE_CACHE_DIR + "system" + File.separator;
		FILE_CACHE_BASEDATA_DIR = FILE_CACHE_DIR + "basedata" + File.separator;
		FILE_CACHE_IMAGE_DIR = FILE_CACHE_DIR + "image" + File.separator;
		FILE_DOWNLOAD_DIR = FILE_ROOT_DIR + "download" + File.separator;
		FILE_DATA_DIR = FILE_ROOT_DIR + "data" + File.separator;
		FILE_LOG_DIR = FILE_ROOT_DIR + "log" + File.separator;
	}

	/** 根目录 */
	public static String FILE_ROOT_DIR;

	/** 缓存资源目录 */
	public static String FILE_CACHE_DIR;

	/** user缓存目录文件 */
	public static String FILE_CACHE_DIR_USER;

	/** 系统缓存数据目录 */
	public static String FILE_CACHE_SYSTEM_DIR;

	/** 基础数据缓存数据目录 */
	public static String FILE_CACHE_BASEDATA_DIR;

	/** 缓存图片目录 */
	public static String FILE_CACHE_IMAGE_DIR;

	/** 下载目录 */
	public static String FILE_DOWNLOAD_DIR;

	/** 下载后数据的存放目录 */
	public static String FILE_DATA_DIR;

	/** 日志目录 */
	public static String FILE_LOG_DIR;

	// ///////////////////////////////////////////后缀名配置//////////////////////////////////

	/** 缓存图片的后缀名 */
	public static String SUFFIX_IMAGE = ".img";
	/** 临时文件的后缀名 */
	public static String SUFFIX_TEMP = ".temp";
	/** 文件的后缀名 */
	public static String SUFFIX_DATA = ".data";
	/** 日志文件的后缀名 */
	public static String SUFFIX_LOG = ".log";

	// ///////////////////////////////////////////其他配置////////////////////////////////////

	/** 系统缓存空间大小-字节 */
	public static long CACHESIZE_SYSTEM = 128 * 1024 * 1024;

	/** 用户缓存空间大小-字节 */
	public static long CACHESIZE_USER = 128 * 1024 * 1024;

	/** 基本数据缓存空间大小-字节 */
	public static long CACHESIZE_BASEDATA = 128 * 1024 * 1024;
}
