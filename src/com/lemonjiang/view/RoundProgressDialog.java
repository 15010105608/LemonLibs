package com.lemonjiang.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lemonjiang.common.UICommon;
import com.lemonjiang.lemonlibs.R;


/**
 * 自定义圆角ProgressDialog
 */
public class RoundProgressDialog extends Dialog {
	private TextView tv_msg;
	private ImageView iv_icon;
	private ViewGroup vg_container;
	private RotateAnimation anim;
	private TextView tv_jiazai;
	public RoundProgressDialog(Context context) {
		super(context);
		init(context);
	}

	public RoundProgressDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}
     
	private void init(Context context) {
		anim=UICommon.getRotateAnimationForLoop(context);
		vg_container = (ViewGroup) LayoutInflater.from(getContext()).inflate(
				R.layout.applib_view_round_progress, null);
		tv_msg = (TextView) vg_container.findViewById(R.id.tv_msg);
		iv_icon = (ImageView) vg_container.findViewById(R.id.iv_icon);
		tv_jiazai = (TextView)vg_container.findViewById(R.id.tv_jiazai);
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
		if (TextUtils.isEmpty(msg)) {
			tv_msg.setText("");
			tv_msg.setVisibility(View.GONE);
			tv_jiazai.setVisibility(View.VISIBLE);
		} else {
			tv_msg.setText(msg);
				tv_msg.setVisibility(View.VISIBLE);
				tv_jiazai.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取显示图标对象
	 * 
	 * @return
	 */
	public ImageView getIconView() {
		return iv_icon;
	}

	/**
	 * 设置显示图标
	 * 
	 * @param iconResId
	 *            图标资源Id
	 */
	public void setIcon(int iconResId) {
		iv_icon.setImageResource(iconResId);
		if (iv_icon.getVisibility() != ViewGroup.VISIBLE) {
			iv_icon.setVisibility(View.VISIBLE);
		}
	}

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

	@Override
	public void dismiss() {
		super.dismiss();
		//((AnimationDrawable) iv_icon.getDrawable()).stop();
		iv_icon.clearAnimation();
	}

	/**
	 * 在2.3.3系统中，当onCreate后立即显示对话框时，需要提交实例化RoundProgressDialog对象-getInstance(...)
	 */
	@Override
	public void show() {
		super.show();
		//((AnimationDrawable) iv_icon.getDrawable()).start();
		iv_icon.startAnimation(anim);
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
	public static RoundProgressDialog getInstance(Context context, String msg,
			boolean cancelable, OnCancelListener cancelListener, int iconResId,
			int themeResId, int w, int h) {
		if (themeResId == 0) {
			themeResId = R.style.RoundProgressDialog_Theme;
		}
		RoundProgressDialog dialog = new RoundProgressDialog(context,
				themeResId);
		dialog.setTitle("");

		// 在下面这种情况下，后台的activity不会被遮盖，也就是只会遮盖此dialog大小的部分
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0.2f;// 背景遮盖部分的透明度
		dialog.getWindow().setAttributes(lp);
		if (!TextUtils.isEmpty(msg)) {
			dialog.setMessage(msg);
		} else {
			dialog.getMessageView().setVisibility(View.GONE);
		}
		if (iconResId != 0) {
			dialog.setIcon(iconResId);
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
	public static RoundProgressDialog getInstance(Context context, String msg,
			boolean cancelable, OnCancelListener cancelListener) {
		return getInstance(context, msg, cancelable, cancelListener, 0, 0, 0, 0);
	}

}
