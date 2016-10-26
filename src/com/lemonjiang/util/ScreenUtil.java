package com.lemonjiang.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lemonjiang.entity.Screen;
import com.lemonjiang.lemonlib.MainApp;


/**
 * 屏幕工具类
 */
public class ScreenUtil {

	/**
	 * 获取屏幕的分辨率0：宽度 1：高度 2：密度 【密度：120->0.75 160->1.0 240->1.5 320->2.0】
	 * 
	 * @param context
	 * @return
	 */
	public static float[] getScreenSpec(Context context) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(outMetrics);
		return new float[] { outMetrics.widthPixels, outMetrics.heightPixels,
				outMetrics.density };
	}

	/**
	 * 获取密度值
	 * 
	 * @param context
	 * @return
	 */
	public static int getDensityDpi(Context context) {
		return context.getResources().getDisplayMetrics().densityDpi;
	}

	/**
	 * 获取屏幕对象
	 * 
	 * @param context
	 * @return
	 */
	public static Screen getScreen(Context context) {
		Screen screen = new Screen();
		float[] specs = getScreenSpec(context);
		screen.setWidthPixels((int) specs[0]);
		screen.setHeightPixels((int) specs[1]);
		screen.setDensity(specs[2]);
		return screen;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue) {
		return (int) (dpValue * MainApp.getScreen().getDensity() + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(float pxValue) {
		return (int) (pxValue / MainApp.getScreen().getDensity() + 0.5f);
		//return (int) ((pxValue- 0.5f) / MainApp.getScreen().getDensity());
	}
}
