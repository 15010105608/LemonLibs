package com.lemonjiang.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;

import com.lemonjiang.lemonlib.MainApp;
import com.lemonjiang.lemonlibs.R;


/**
 * 自定义圆角ToastDialog
 */
public class RoundToastDialog extends Dialog {
	private TextView tv_msg;
	private ViewGroup vg_container;
	/** 自动关闭时长 */
	private long autoclosedDuration = 1500;

	public RoundToastDialog(Context context) {
		super(context);
		init(context);
	}

	public RoundToastDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	private void init(Context context) {
		vg_container = (ViewGroup) LayoutInflater.from(getContext()).inflate(
				R.layout.applib_view_round_toastdialog, null);
		tv_msg = (TextView) vg_container.findViewById(R.id.tv_msg);
		setContentView(vg_container);
	}

	/***
	 * 获取显示信息对象
	 * 
	 * @return
	 */
	public TextView getMessageView() {
		return tv_msg;
	}

	/**
	 * 设置显示信息
	 * 
	 * @param msg
	 *            信息
	 */
	public void setMessage(String msg) {
		tv_msg.setText(msg);
	}

	@Override
	public void show() {
		try {
			super.show();
			if (autoclosedDuration > 0) {
				handler.postDelayed(autoClosedRunnable, autoclosedDuration);
			} else {
				handler.removeCallbacks(autoClosedRunnable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 设置自动关闭时长
	 * @param duration
	 */
	public void setAutoClosedDuration(int duration){
		if (autoclosedDuration > 0) {
			if(isShowing()){
				handler.postDelayed(autoClosedRunnable, autoclosedDuration);
			}
		} else {
			handler.removeCallbacks(autoClosedRunnable);
		}
	}
	
	/**
	 * handler
	 */
	private Handler handler = new Handler() {
	};
	/**
	 * 自动关闭线程
	 */
	private Runnable autoClosedRunnable = new Runnable() {

		@Override
		public void run() {
			if(isShowing()){
				cancel();
			}
		}
	};

	/**
	 * 设置宽高
	 * 
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 */
	public void setLayoutParams(int w, int h) {
		LayoutParams params = vg_container.getLayoutParams();
		if (w != 0) {
			params.width = w;
		}
		if (h != 0) {
			params.height = h;
		}
	}

	/**
	 * 获取ProgressDialog
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息提示
	 * @param cancelable
	 *            是否启用返回键
	 * @param cancelListener
	 *            返回时触发的事件
	 * @param iconResId
	 *            图标资源Id
	 * @param themeResId
	 *            主题资源Id
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @return
	 */
	public static RoundToastDialog getInstance(Context context, String msg,
			boolean cancelable, OnCancelListener cancelListener, int iconResId,
			int themeResId, int w, int h) {
		if (themeResId == 0) {
			themeResId = R.style.RoundProgressDialog_Theme;
		}
		RoundToastDialog dialog = new RoundToastDialog(context, themeResId);
		dialog.setTitle("");

		// 在下面这种情况下，后台的activity不会被遮盖，也就是只会遮盖此dialog大小的部分
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0.5f;// 背景遮盖部分的透明度
		dialog.getWindow().setAttributes(lp);
		if (!TextUtils.isEmpty(msg)) {
			dialog.setMessage(msg);
		} else {
			dialog.setMessage("");
		}
		dialog.setCancelable(cancelable);
		if (cancelable) {
			// 单击dialog之外的地方，可以dismiss掉dialog
			dialog.setCanceledOnTouchOutside(true);
		}
		dialog.setOnCancelListener(cancelListener);
		if (w != 0 || h != 0) {
			dialog.setLayoutParams(w, h);
		}
		return dialog;
	}

	/**
	 * 获取ProgressDialog
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息提示
	 * @param cancelable
	 *            是否启用返回键
	 * @param cancelListener
	 *            返回时触发的事件
	 * @return
	 */
	public static RoundToastDialog getInstance(Context context, String msg,
			boolean cancelable, OnCancelListener cancelListener) {
		return getInstance(context, msg, cancelable, cancelListener, 0, 0,
				(MainApp.getScreen().getWidthPixels() / 3) * 2, 0);
	}

}
