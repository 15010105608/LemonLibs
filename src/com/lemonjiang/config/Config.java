package com.lemonjiang.config;

import com.lemonjiang.util.LogInfo.LogModel;


public class Config {

	/** 是否是开发状态，值true为开启(开发者使用)，值false为关闭(正式环境使用) */
	public static boolean ISDEVELOP = false;
	
	/**日志模式*/
	public static LogModel LOGMODEL = LogModel.LOGCAT;
	
	/**日志Tag*/
	public static String TAG = "XFMain";
	
}
