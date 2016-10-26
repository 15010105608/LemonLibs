package com.lemonjiang.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import java.lang.reflect.Field;
import java.util.Calendar;

import com.lemonjiang.util.DateUtil;

/**
 * UI公共
 */
public class UICommon {

	/**
	 * 设置-日期控件
	 * 
	 * @param context
	 *            上下文
	 * @param v_info
	 *            显示控件
	 * @param cld_def
	 *            默认日期
	 * @param maxDate
	 *            限制最大日期，单位：毫秒数
	 * @param splitString
	 *            分隔符
	 * @param mListenerDateDialog
	 *            监听事件
	 */
	public static void setDateEt(final Context context, final TextView v_info,
			final Calendar cld_def, final long maxDate,
			final String splitString,
			final OnChangeListenerDateDialog mListenerDateDialog) {
		v_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!v_info.getText().toString().trim().equals("")) {
					String[] date = v_info.getText().toString()
							.split(splitString);
					int start_month = Integer.valueOf(date[1]) - 1;
					showDateDialog(context, v_info, Integer.valueOf(date[0]),
							start_month == 0 ? 12 : start_month,
							Integer.valueOf(date[2]), maxDate, splitString,
							mListenerDateDialog);
				} else {
					showDateDialog(context, v_info, cld_def.get(Calendar.YEAR),
							cld_def.get(Calendar.MONTH),
							cld_def.get(Calendar.DATE), maxDate, splitString,
							mListenerDateDialog);
				}
			}
		});
	}

	/**
	 * 设置-时间控件
	 * 
	 * @param context
	 *            上下文
	 * @param v_info
	 *            显示控件
	 * @param splitString
	 *            分隔符
	 * @param mListenerTimeDialog
	 *            监听事件
	 */
	public static void setTimeEt(final Context context, final TextView v_info,
			final String splitString,
			final OnChangeListenerTimeDialog mListenerTimeDialog) {
		v_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] seconds = v_info.getText().toString()
						.split(splitString);
				showTimeDialog(context, v_info, Integer.valueOf(seconds[0]),
						Integer.valueOf(seconds[1]), splitString,
						mListenerTimeDialog);
			}
		});
	}

	public interface OnChangeListenerDateDialog {
		public void change(TextView v, int year, int month, int day,
				String splitString, String content);
	}

	public interface OnChangeListenerTimeDialog {
		public void change(TextView v, int hour, int minute,
				String splitString, String content);
	}

	/**
	 * 日期对话框
	 * 
	 * @param context
	 *            上下文
	 * @param v_info
	 *            显示控件
	 * @param defaultYear
	 *            默认的年
	 * @param defaultMonth
	 *            默认的月
	 * @param defaultDay
	 *            默认的日
	 * @param maxDate
	 *            限制最大日期，单位：毫秒数
	 * @param splitString
	 *            分隔符
	 * @param mListenerDateDialog
	 *            监听事件
	 */
	@SuppressLint("NewApi")
	public static void showDateDialog(Context context, final TextView v_info,
			int defaultYear, int defaultMonth, int defaultDay, long maxDate,
			final String splitString,
			final OnChangeListenerDateDialog mListenerDateDialog) {

		final DatePickerDialog datePickerDialog = new DatePickerDialog(context,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						int month = monthOfYear + 1;
						if (mListenerDateDialog != null) {
							String content = year + splitString
									+ DateUtil.getNumAndZero(month)
									+ splitString
									+ DateUtil.getNumAndZero(dayOfMonth);
							mListenerDateDialog.change(v_info, year, month,
									dayOfMonth, splitString, content);
						}
					}
				}, defaultYear, defaultMonth, defaultDay);
		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			DatePicker dp = datePickerDialog.getDatePicker();
			if (maxDate != 0) {
				dp.setMaxDate(maxDate);
			}
		}
		datePickerDialog.show();
	}

	/**
	 * 时间对话框
	 * 
	 * @param context
	 *            上下文
	 * @param v_info
	 *            显示控件
	 * @param defaultHour
	 *            默认的小时
	 * @param defaultMinute
	 *            默认的分钟
	 * @param splitString
	 *            分隔符
	 * @param mListenerTimeDialog
	 *            监听事件
	 */
	public static void showTimeDialog(Context context, final TextView v_info,
			int defaultHour, int defaultMinute, final String splitString,
			final OnChangeListenerTimeDialog mListenerTimeDialog) {
		new TimePickerDialog(context, new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if (mListenerTimeDialog != null) {
					String content = DateUtil.getNumAndZero(hourOfDay)
							+ splitString + DateUtil.getNumAndZero(minute);
					mListenerTimeDialog.change(v_info, hourOfDay, minute,
							splitString, content);
				}
			}
		}, defaultHour, defaultMinute, true).show();
	}

	/**
	 * 获取无限循环动画
	 * */
	public static RotateAnimation getRotateAnimationForLoop(Context context) {
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(600); 
		rotateAnimation.setInterpolator(AnimationUtils.loadInterpolator(
				context, android.R.anim.linear_interpolator));
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setRepeatMode(RotateAnimation.RESTART);
		rotateAnimation.setRepeatCount(-1);
		return rotateAnimation;
	}

	/**
	 * 执行动画移动
	 * */
	public static void moveImage(View v, int duration, float startX, float toX,
			float startY, float toY) {
		TranslateAnimation mTranslateAnimation = new TranslateAnimation(startX,
				toX, startY, toY);
		mTranslateAnimation.setFillAfter(true);
		mTranslateAnimation.setDuration(duration);
		v.startAnimation(mTranslateAnimation);
	}

	/***
	 * 获取状态栏高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getStatusBarHeight(Activity activity) {
		int sbar = 0;
		if (android.os.Build.VERSION.SDK_INT >= 15) {
			Class<?> c = null;
			Object obj = null;
			Field field = null;
			int x = 0;
			try {
				c = Class.forName("com.android.internal.R$dimen");
				obj = c.newInstance();
				field = c.getField("status_bar_height");
				x = Integer.parseInt(field.get(obj).toString());
				sbar = activity.getResources().getDimensionPixelSize(x);
			} catch (Exception e1) {
			}
		} else {
			Rect frame = new Rect();
			activity.getWindow().getDecorView()
					.getWindowVisibleDisplayFrame(frame);
			sbar = frame.top;
		}
		return sbar;
	}

	/***
	 * 获取标题栏高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getTitleBarHeight(Activity activity) {
		int contentTop = activity.getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		return contentTop - getStatusBarHeight(activity);
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/**
	 * 获取字体高度
	 */
	public static int getFontHeight(float fontSize, Paint paint) {
		if (paint == null) {
			paint = new Paint();
		}
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.ascent);
	}

	/**
	 * 获取字体宽高度
	 */
	public static int[] getFontWH(String mText, float fontSize, Paint paint) {
		if (paint == null) {
			paint = new Paint();
		}
		paint.setTextSize(fontSize);
		Rect bounds = new Rect();
		paint.getTextBounds(mText, 0, mText.length(), bounds);
		return new int[] { bounds.width(), bounds.height() };
	}

	/**
	 * 获取字体宽度
	 */
	public static int getFontWidth(String mText, float fontSize, Paint paint) {
		if (paint == null) {
			paint = new Paint();
		}
		paint.setTextSize(fontSize);
		return (int) paint.measureText(mText);
	}
}
