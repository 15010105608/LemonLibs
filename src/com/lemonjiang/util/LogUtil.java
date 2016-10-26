package com.lemonjiang.util;

import java.io.File;
import java.util.Date;

import android.text.TextUtils;
import android.util.Log;

import com.lemonjiang.config.Config;
import com.lemonjiang.config.FileConfig;
import com.lemonjiang.util.LogInfo.LogType;

/**
 * 日志操作类
 */
public class LogUtil {

	/**
	 * 写入日志
	 * 
	 * @param log
	 *            日志类
	 * @return 是否成功
	 */
	public synchronized static boolean write(LogInfo log) {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n\r\n--").append(log.getType().toString()).append("--");
		sb.append("\t--").append(log.getAddDate()).append("--\r\n");
		sb.append("----").append(log.getMsg()).append("\r\n");
		if (!TextUtils.isEmpty(log.getRemark())) {
			sb.append("----").append(log.getRemark());
		}
		
		// 每月生成一份日志文件
		String fileName = DateUtil.dateToString(new Date(), "yyyy-MM")
				+ FileConfig.SUFFIX_LOG;
		String dir = FileUtil.getDataRootDir() + File.separator
				+ FileConfig.FILE_LOG_DIR;
		FileUtil.createDir(dir);
		return FileUtil.saveFile(dir + fileName, sb.toString(), true);
	}
	
	/**
	 * 记录Exception信息
	 * @param e
	 * @param remark
	 * @return
	 */
	public synchronized static boolean writeByException(Throwable e,
			String remark) {
		String msg = "Exception in thread \""
				+ Thread.currentThread().getName() + "\" "
				+ e.getClass().getName() + ": " + e.getMessage() + "\r\n";
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		if (stackTraceElements != null) {
			int len = stackTraceElements.length;
			for (int i = 0; i < len; i++) {
				StackTraceElement element = stackTraceElements[i];
				msg += "at：" + element.getClassName() + "."
						+ element.getMethodName() + "(" + element.getFileName()
						+ ":" + element.getLineNumber() + ")\r\n";
			}
		}
		return write(new LogInfo(LogType.ERROR, msg, remark));
	}

	/**
	 * 打印日志
	 * 
	 * @param tag
	 *            标签
	 * @param msg
	 *            信息
	 */
	private static void log(String tag, String msg) {
		Log.i(tag, msg);
	}

	/**
	 * 打印日志
	 * 
	 * @param msg
	 *            信息
	 */
	public static void log(String msg) {
		log(Config.TAG, msg);
	}
}
