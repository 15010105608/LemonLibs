package com.lemonjiang.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lemonjiang.lemonlib.MainApp;
import com.lemonjiang.lemonlibs.R;
import com.lemonjiang.util.ReflectUtils;


/**
 * 自定义圆角Toast
 */
public class RoundToast extends Toast {
	private TextView tv_msg;
	private ImageView iv_icon;
	private ViewGroup ll_container;

	public RoundToast(Context context) {
		super(context);
		init(context);
	}

	@SuppressLint("InflateParams")
	private void init(Context context) {
		final ViewGroup vg_container = (ViewGroup) LayoutInflater.from(context)
				.inflate(R.layout.applib_view_round_toast, null);
		tv_msg = (TextView) vg_container.findViewById(R.id.tv_msg);
		iv_icon = (ImageView) vg_container.findViewById(R.id.iv_icon);
		ll_container = (ViewGroup) vg_container.findViewById(R.id.ll_container);
		setView(vg_container);
	}

	/**
	 * 统一 Toast 动画
	 * 
	 * @param mToast
	 */
	private static void updateToastAnim(Toast mToast) {
		if (mToast != null) {
			try {
				Object mTN = ReflectUtils.getgetSuperField(mToast, "mTN");
				if (mTN != null) {
					Object mParams = ReflectUtils.getgetField(mTN, "mParams");
					if (mParams != null
							&& mParams instanceof WindowManager.LayoutParams) {
						WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
						params.windowAnimations = R.style.RoundToast_Animation;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
		if (tv_msg.getVisibility() != ViewGroup.VISIBLE) {
			tv_msg.setVisibility(View.VISIBLE);
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
		LayoutParams params = (LayoutParams) ll_container.getLayoutParams();
		if (params != null) {
			if (w != 0) {
				params.width = w;
			}
			if (h != 0) {
				params.height = h;
			}
		} else {
			params = new LayoutParams(w, h);
			ll_container.setLayoutParams(params);
		}
	}

	/**
	 * 获取Toast
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息
	 * @param iconResId
	 *            图标资源Id
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @return 显示对象
	 */
	public static RoundToast getInstance(Context context, String msg,
			int iconResId, int w, int h) {
		RoundToast toast = new RoundToast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		updateToastAnim(toast);
		if (!TextUtils.isEmpty(msg)) {
			toast.setMessage(msg);
		} else {
			toast.getMessageView().setVisibility(View.GONE);
		}
		if (iconResId != 0) {
			toast.setIcon(iconResId);
		} else {
			toast.getIconView().setVisibility(View.GONE);
		}
		if (w != 0 || h != 0) {
			toast.setLayoutParams(w, h);
		}
		return toast;
	}

	/**
	 * 获取Toast
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @return 显示对象
	 */
	public static RoundToast getInstance(Context context, String msg, int w,
			int h) {
		return getInstance(context, msg, 0, w, h);
	}

	/**
	 * 获取Toast
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息
	 * @return 显示对象
	 */
	public static RoundToast getInstance(Context context, String msg) {
		return getInstance(context, msg, 0);
	}

	/**
	 * 获取Toast
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息
	 * @return 显示对象
	 */
	public static RoundToast getInstanceByCenter(Context context, String msg) {
		return getInstance(context, msg, 0, 0, 0);
	}

	/**
	 * 获取Toast
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息
	 * @param iconResId
	 *            图标资源Id
	 * @return 显示对象
	 */
	public static RoundToast getInstance(Context context, String msg,
			int iconResId) {
		return getInstance(context, msg, iconResId, MainApp.getScreen()
				.getWidthPixels(), MainApp.getScreen().getHeightPixels());
	}
}
