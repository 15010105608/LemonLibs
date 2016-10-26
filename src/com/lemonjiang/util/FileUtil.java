package com.lemonjiang.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.lemonjiang.lemonlib.MainApp;

/**
 * 文件工具类
 * */
public class FileUtil {
	private static final String TAG = "FileUtil";

	/**
	 * 获取Assets文件内容
	 * 
	 * @param path
	 * @return 内容
	 */
	@SuppressWarnings("finally")
	public static String getAssetsFileContent(String path) {
		String rs = null;
		InputStream in = null;
		try {
			in = MainApp.getContext().getAssets().open(path);
			rs = FileUtil.inputStreamTocontent(in);
		} catch (IOException e) {
			LogUtil.log(TAG + "-getAssetsFile-IOException-e>" + e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtil.log(TAG + "-getAssetsFile-IOException-e>"
							+ e.getMessage());
				}
			}
			return rs;
		}
	}

	/**
	 * 获取数据存储根目录 ：SD卡缓存目录优先，其次手机内部缓存目录
	 * 
	 * @return 目录路径
	 */
	public static String getDataRootDir() {
		String root = "";
		if (isSDCardMounted()) {
			root = getSDCardDir();
		} else {
			root = MainApp.getContext().getCacheDir().getAbsolutePath();
		}
		return root;
	}

	/**
	 * 获取文件对象
	 * 
	 * @param path
	 *            文件路径
	 * @param isDelete
	 *            是否先删除再创建
	 * @return File对象
	 */
	public static File getFile(String path, boolean... isDelete) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		} else {
			if (isDelete != null && Boolean.valueOf(isDelete.toString())) {
				file.delete();
				file.mkdirs();
			}
		}
		return file;
	}

	/**
	 * 保存文件
	 * 
	 * @param file
	 *            文件
	 * @param content
	 *            文件内容
	 * @param append
	 *            是否是追加
	 * @return 是否成功
	 */
	@SuppressWarnings("finally")
	public static boolean saveFile(File file, String content, boolean append) {
		boolean rs = true;
		BufferedWriter write = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			write = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, append), "UTF-8"));
			write.write(content);
			write.flush();
		} catch (FileNotFoundException e) {
			LogUtil.log(TAG + "-saveFile-FileNotFoundException-e>"
					+ e.getMessage());
			rs = false;
		} catch (IOException e) {
			LogUtil.log(TAG + "-saveFile-IOException-e>" + e.getMessage());
			rs = false;
		} finally {
			if (write != null) {
				try {
					write.close();
				} catch (IOException e) {
					LogUtil.log(TAG + "-saveFile-IOException-e>"
							+ e.getMessage());
				}
			}
			return rs;
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param path
	 *            文件路径
	 * @param content
	 *            文件内容
	 * @param append
	 *            是否是追加
	 * @return 是否成功
	 */
	public static boolean saveFile(String path, String content, boolean append) {
		return saveFile(new File(path), content, append);
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return 是否存在
	 */
	public static boolean isFileExit(String path) {
		return new File(path).exists();
	}

	/**
	 * 转换文件大小为字符格式
	 * 
	 * @param size
	 *            文件大小(单位：字节)
	 * @return 字符串
	 */
	public static String formetFileSize(long size) {
		String rs = "";
		DecimalFormat format = new DecimalFormat("#.00");
		if (size >= 1024) {
			if (size >= 1024 * 1024) {
				if (size >= 1024 * 1024 * 1024)
					rs = format.format(size / (1024 * 1024 * 1024)) + "G";
				else
					rs = format.format(size / (1024 * 1024)) + "M";
			} else
				rs = format.format(size / 1024) + "K";
		} else {
			rs = "0K";
		}
		return rs;
	}

	/**
	 * 获取输入流内容
	 * 
	 * @param in
	 *            输入流
	 * @return 内容
	 */
	public static String inputStreamTocontent(InputStream in) {
		String content = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = "";
			while ((line = br.readLine()) != null) {
				content += line + "\n";
			}
			if(!TextUtils.isEmpty(content)){
				content=content.substring(0, content.length()-1);
			}
		} catch (IOException e) {
			LogUtil.log(TAG + "-inputStreamTocontent-IOException-e>"
					+ e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LogUtil.log(TAG + "-getInputStreamContent-IOException-e>"
							+ e.getMessage());
				}
			}
		}
		return content;
	}

	/**
	 * 获取手机外部可用空间大小
	 * 
	 * @return 大小（单位：字节）
	 */
	@SuppressWarnings("deprecation")
	public static long getAvailableExternalMemorySize() {
		StatFs sf = new StatFs(getSDCardDir());
		return (long) sf.getAvailableBlocks() * (long) sf.getBlockSize();
	}

	/**
	 * 获取手机外部总空间大小
	 * 
	 * @return 大小（单位：字节）
	 */
	@SuppressWarnings("deprecation")
	public static long getTotalExternalMemorySize() {
		StatFs sf = new StatFs(getSDCardDir());
		return (long) sf.getBlockCount() * (long) sf.getBlockSize();
	}

	/**
	 * 获取手机内部可用空间大小
	 * 
	 * @return 大小（单位：字节）
	 */
	@SuppressWarnings("deprecation")
	public static long getAvailableInternalMemorySize() {
		StatFs sf = new StatFs(Environment.getDataDirectory().getPath());
		return (long) sf.getAvailableBlocks() * (long) sf.getBlockSize();
	}

	/**
	 * 获取手机内部总空间大小
	 * 
	 * @return 大小（单位：字节）
	 */
	@SuppressWarnings("deprecation")
	public static long getTotalInternalMemorySize() {
		StatFs sf = new StatFs(Environment.getDataDirectory().getPath());
		return (long) sf.getBlockCount() * (long) sf.getBlockSize();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            文件路径
	 * @return 是否成功
	 */
	public static boolean deleteFile(String path) {
		File file = new File(path);
		if (file.exists())
			return file.delete();
		return false;
	}

	/**
	 * 删除目录（递归）
	 * 
	 * @param file
	 *            文件对象
	 * @return 是否成功
	 */
	public static boolean deleteDir(File file) {
		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0, len = children.length; i < len; i++) {
				if (!deleteDir(new File(file, children[i]))) {
					return false;
				}
			}
		}
		return file.delete();
	}

	/**
	 * 获取SD卡根目录
	 * 
	 * @return 目录路径
	 */
	public static String getSDCardDir() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件路径
	 * @return 是否成功
	 */
	public static boolean createFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch (IOException e) {
				LogUtil.log(TAG + "-createFile-IOException-e>" + e.getMessage());
			}
		}
		return true;
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 *            文件对象
	 * @return 是否成功
	 */
	public static boolean createFile(File file) {
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch (IOException e) {
				LogUtil.log(TAG + "-createFile-IOException-e>" + e.getMessage());
			}
		}
		return true;
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 *            目录路径
	 * @return 是否成功
	 */
	public static boolean createDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return true;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param file
	 *            文件对象
	 * @return 是否成功
	 */
	public static boolean createDir(File file) {
		if (!file.exists()) {
			return file.mkdirs();
		}
		return true;
	}

	/**
	 * 是否装载了SD卡
	 * 
	 * @return 是否装载
	 */
	public static boolean isSDCardMounted() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * 是否外部存储空间可用
	 * 
	 * @param filesize
	 *            文件大小（单位：字节）
	 * @return 是否可用
	 */
	public static boolean isNeededSpace(long filesize) {
		return getAvailableExternalMemorySize() > filesize;
	}

	/**
	 * 修改文件名
	 * 
	 * @param newPath
	 *            新文件路径
	 * @param oldPath
	 *            原文件路径
	 * @return 是否成功
	 */
	public static boolean renameFileName(String newPath, String oldPath) {
		File file = new File(newPath);
		if (file.exists()) {
			return file.renameTo(new File(oldPath));
		}
		return false;
	}

	/**
	 * 获取路径下的文件总空间大小
	 * 
	 * @param file
	 *            文件对象
	 * @return 文件大小（单位：字节）
	 */
	public static long getFileSize(File file) {
		long size = 0;
		if (file.exists()) {
			File[] list = file.listFiles();
			for (int i = 0, len = list.length; i < len; i++)
				if (!list[i].isDirectory())
					size += list[i].length();
				else
					size += getFileSize(list[i]);
		}
		return size;
	}

}
