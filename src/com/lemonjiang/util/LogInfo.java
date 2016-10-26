package com.lemonjiang.util;

import java.io.Serializable;
import java.util.Date;

import android.text.TextUtils;

import com.lemonjiang.config.Config;

/**
 * 日志对象
 */
public class LogInfo implements Serializable {
	/**
	 * 日志类型
	 */
	public enum LogType {
		/**
		 * 系统
		 */
		SYSTEM("系统"),
		/**
		 * 错误
		 */
		ERROR("错误"),
		/**
		 * 调试
		 */
		DEBUG("调试");
		private String val;

		@Override
		public String toString() {
			return this.val;
		}

		private LogType(String val) {
			this.val = val;
		}

	}

	/**
	 * 输出Log日志模式
	 */
	public enum LogModel {
		/**
		 * 写入sd卡
		 */
		SD,
		/**
		 * 打印logcat
		 */
		LOGCAT,
		/**
		 * 上传服务器
		 */
		WEB
	}

	private static final long serialVersionUID = 1L;

	private LogType type;// 类型
	private String tag;// tag
	private LogModel model;// 模式
	private String msg;// 信息
	private Throwable exception;// Exception
	private String addDate;// 添加日期
	private String remark;// 备注

	public LogInfo() {
	}

	/**
	 * 日志
	 * 
	 * @param type
	 *            类型
	 * @param model
	 *            模型
	 * @param msg
	 *            信息
	 * @param addDate
	 *            添加日期
	 * @param remark
	 *            备注
	 */
	public LogInfo(LogType type, LogModel model, String msg, String addDate,
			String remark) {
		init(type, model, msg, addDate, remark);
	}

	/**
	 * 日志
	 * 
	 * @param type
	 *            类型
	 * @param msg
	 *            信息
	 * @param addDate
	 *            添加日期
	 * @param remark
	 *            备注
	 */
	public LogInfo(LogType type, String msg, String addDate, String remark) {
		init(type, null, msg, addDate, remark);
	}

	/**
	 * 日志
	 * 
	 * @param type
	 *            类型
	 * @param msg
	 *            信息
	 * @param remark
	 *            备注
	 */
	public LogInfo(LogType type, String msg, String remark) {
		init(type, null, msg, null, remark);
	}

	/**
	 * 日志
	 * 
	 * @param type
	 *            类型
	 * @param msg
	 *            信息
	 */
	public LogInfo(LogType type, String msg) {
		init(type, null, msg, null, null);
	}

	/**
	 * 初始化
	 * 
	 * @param type
	 *            类型
	 * @param model
	 *            模型
	 * @param msg
	 *            信息
	 * @param addDate
	 *            添加日期
	 * @param remark
	 *            备注
	 */
	private void init(LogType type, LogModel model, String msg, String addDate,
			String remark) {
		this.type = type;
		if (model == null) {
			model = Config.LOGMODEL;
		} else {
			this.model = model;
		}
		this.msg = msg;
		this.addDate = addDate;
		this.remark = remark;
		if (TextUtils.isEmpty(addDate)) {
			this.addDate = DateUtil.dateToString(new Date(),
					"yyyy-MM-dd HH:mm:ss");
		}
	}

	/**
	 * 获取类型
	 */
	public LogType getType() {
		return type;
	}

	/**
	 * 设置类型
	 */
	public void setType(LogType type) {
		this.type = type;
	}

	/**
	 * 获取模式
	 * 
	 * @return
	 */
	public LogModel getModel() {
		return model;
	}

	/**
	 * 设置模式
	 * 
	 * @param model
	 */
	public void setModel(LogModel model) {
		this.model = model;
	}

	/**
	 * 获取信息
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置信息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 获取添加日期
	 */
	public String getAddDate() {
		return addDate;
	}

	/**
	 * 设置添加日期
	 */
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	/**
	 * 获取备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取Exception
	 * 
	 * @return
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * 设置Exception
	 * 
	 * @param exception
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	/**
	 * 获取tag
	 * 
	 * @return
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 设置tag
	 * 
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

}
