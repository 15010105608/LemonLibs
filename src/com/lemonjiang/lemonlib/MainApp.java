package com.lemonjiang.lemonlib;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lemonjiang.cache.BaseDataFileCache;
import com.lemonjiang.cache.SystemFileCache;
import com.lemonjiang.cache.UserFileCache;
import com.lemonjiang.config.Constants;
import com.lemonjiang.config.FileConfig;
import com.lemonjiang.entity.Screen;
import com.lemonjiang.util.PreferencesUtil;
import com.lemonjiang.util.QuickTimer;
import com.lemonjiang.util.QuickTimer.OnTimeListener;
import com.lemonjiang.util.ScreenUtil;

public class MainApp {
	private static String pagename;// 包名
	private static Context mContext;// 上下文
	private static List<Activity> mActivities;// activity集合
	private static SharedPreferences mPreferences;// 属性文件对象
	private static Screen screen;// 屏幕

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		mContext = context;
		mPreferences = PreferencesUtil.getInstance(mContext,
				Constants.KEY_SYS_SETTING_NAME);
		screen = ScreenUtil.getScreen(mContext);
		pagename = context.getPackageName();
		setCacheDirName(pagename);
	}

	/**
	 * 设置缓存目录名称
	 * 
	 * @param dirName
	 */
	public static void setCacheDirName(String dirName) {
		FileConfig.init(pagename);
	}

	/**
	 * 获取屏幕对象
	 * */
	public static Screen getScreen() {
		return screen;
	}

	/**
	 * 获取Application的上下文
	 * */
	public static Context getContext() {
		return mContext;
	}

	/**
	 * 获取包名
	 * */
	public static String getPagename() {
		return pagename;
	}

	/**
	 * 根据key获取属性值
	 * */
	public static String getPref(String key, String defValue) {
		return mPreferences.getString(key, defValue);
	}

	/**
	 * 保存属性
	 * */
	public static void savePref(String key, String value) {
		Editor editor = mPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 获取activity集合数量
	 * 
	 * @return
	 */
	public static int getActivityCount() {
		if (mActivities != null) {
			return mActivities.size();
		}
		return 0;
	}

	/**
	 * 保存activity
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		if (mActivities == null) {
			mActivities = new ArrayList<Activity>();
		}
		mActivities.add(activity);
	}

	/**
	 * 根据className移除activity
	 * 
	 * @param className
	 */
	public static void removeActivity(String className) {
		if (mActivities != null) {
			int len = mActivities.size();
			for (int i = len - 1; i >= 0; i--) {
				if (mActivities.get(i).getClass().getName().equalsIgnoreCase(className)) {
					mActivities.get(i).finish();
					break;
				}
			}
		}
	}

	/**
	 * 获取activitys
	 * 
	 * @return
	 */
	public static List<Activity> getActivitys() {
		return mActivities;
	}

	/**
	 * 移除activity
	 * 
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {
		if (mActivities != null) {
			mActivities.remove(activity);
		}
	}

	/**
	 * 关闭activity
	 * 
	 */
	public static void finishActivity() {
		if (mActivities != null) {
			int len = mActivities.size() - 1;
			for (int i = len; i >= 0; i--) {
				mActivities.get(i).finish();
			}
			mActivities = null;
		}
	}

	/**
	 * 释放资源
	 * */
	public static void destroy() {
		finishActivity();

		releaseBaseDataFileCache();
		releaseSystemFileCache();
		releaseUserFileCache();
	}

	/**
	 * 释放缓存-基本
	 */
	public static void releaseBaseDataFileCache() {
		if (BaseDataFileCache.getFileCacheManager() != null) {
			BaseDataFileCache.getInstance().releaseCache();
			BaseDataFileCache.clearFileCacheManager();
		}
	}

	/**
	 * 释放缓存-系统
	 */
	public static void releaseSystemFileCache() {
		if (SystemFileCache.getFileCacheManager() != null) {
			SystemFileCache.getInstance().releaseCache();
			SystemFileCache.clearFileCacheManager();
		}
	}

	/**
	 * 释放缓存-用户
	 */
	public static void releaseUserFileCache() {
		if (UserFileCache.getFileCacheManager() != null) {
			UserFileCache.getInstance().releaseCache();
			UserFileCache.clearFileCacheManager();
		}
	}

	/**
	 * 退出系统
	 */
	public static void exit() {
		new QuickTimer().start(new OnTimeListener() {

			@Override
			public void onTimer() {
				// 杀死进程
				System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}, 200);
	}
}
