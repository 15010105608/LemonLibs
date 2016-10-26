package com.lemonjiang.config;

import com.lemonjiang.lemonlib.MainApp;


public class Constants {

	/** 系统设置的文件名称 */
	public static final String KEY_SYS_SETTING_NAME = "sys_setting";
	/** 用户缓存的标识 */
	public static final String KEY_USER_CACHE_FLAG = "user_cache_flag";

	/** 登录超时action */
	public static final String ACTION_BROADCAST_COMMON_MSG_LOGIN_TIME = "action_broadcast_common_msg_login_time";
	/** 登录超时action-msg */
	public static final String ACTION_KEY_LOGIN_TIME_MSG = "msg";
	/** 登录超时action-content */
	public static final String ACTION_KEY_LOGIN_TIME_CONTENT = "content";

	/** 照片/视频 */
	public static final int MEDIA_MODEL_TAKE = 101;
	/** 本地相册 */
	public static final int PHOTO_MODEL_ALBUM = 102;
	/** 图片裁剪 */
	public static final int PHOTO_MODEL_CROP = 103;
	/** 图片-后缀名 */
	public static final String PHOTO_TEMP_SUFFIX = ".jpg";
	/** 视频-后缀名 */
	public static final String VIDEO_TEMP_SUFFIX = ".mp4";
	/** 临时媒体名称 */
	public static final String MEDIA_TEMP_NAME = MainApp.getPagename().replace(
			".", "")
			+ "_temp";
}
