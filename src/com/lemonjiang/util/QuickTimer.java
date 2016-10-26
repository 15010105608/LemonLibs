package com.lemonjiang.util;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * 定时器
 */
public class QuickTimer {
	/** 定时器类型-普通类型 */
	private static final int TYPE_NORMAL = 0;
	/** 定时器类型-循环类型 */
	private static final int TYPE_CIRCLE = 1;

	private Timer mTimer;
	private TimerTask mTimerTask;
	private OnTimeListener mListener;

	/** 是否在运行 */
	private boolean isRunning = false;
	private int type;

	@SuppressLint("HandlerLeak")
	public QuickTimer() {
		type = TYPE_NORMAL;

		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);
			}
		};
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				if (mListener != null) {
					mListener.onTimer();
					if (type == TYPE_NORMAL) {
						isRunning = false;
					}
				}
			}
		}
	};

	/**
	 * 启动定时器
	 * 
	 * @param listener
	 *            事件
	 * @param delay
	 *            延迟毫秒数
	 */
	public void start(OnTimeListener listener, long delay) {
		this.mListener = listener;
		isRunning = true;
		type = TYPE_NORMAL;
		mTimer.schedule(mTimerTask, delay);
	}

	/**
	 * 启动定时器，自动循环执行
	 * 
	 * @param listener
	 *            事件
	 * @param delay
	 *            延迟毫秒数
	 * @param period
	 *            首次之后自动循环执行毫秒数
	 */
	public void start(OnTimeListener listener, long delay, long period) {
		this.mListener = listener;
		isRunning = true;
		type = TYPE_CIRCLE;
		mTimer.schedule(mTimerTask, delay, period);
	}

	/**
	 * 停止定时器
	 * */
	public void stop() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
			mTimer = null;
		}
		mTimerTask = null;
		mListener = null;
		isRunning = false;
	}

	/**
	 * 是否在运行
	 * */
	public boolean isRun() {
		return isRunning;
	}

	/**
	 * 监听事件
	 */
	public interface OnTimeListener {
		public void onTimer();
	}
}
