package com.lemonjiang.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.lemonjiang.lemonlib.MainApp;
import com.lemonjiang.view.RoundToast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;

/**
 * 公共类
 */
public class Common {

	/**
	 * 获取DisplayImageOptions
	 * 
	 * @return
	 */
	public static DisplayImageOptions getDisplayImageOptions() {
		Builder builder = new DisplayImageOptions.Builder();
		builder.cacheInMemory(true);
		builder.cacheOnDisc(true);
		return builder.build();
	}

	/**
	 * 查找某个路径是否在规定内的后缀名
	 * 
	 * @param path
	 *            路径
	 * @param ss
	 *            后缀名数组
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String findSuffix(String path, String[] ss) {
		path = path.toLowerCase();
		for (int i = 0, len = ss.length; i < len; i++) {
			if (path.endsWith(ss[i].toLowerCase())) {
				return ss[i];
			}
		}
		return null;
	}

	/**
	 * 获取资源文件的id
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            资源id
	 * @param type
	 *            资源类型，如：layout,id,string
	 * @return
	 */
	public static int getRes(Context context, String name, String type) {
		return context.getResources().getIdentifier(name, type,
				context.getPackageName());
	}

	/**
	 * 获取资源文件的id
	 * 
	 * @param name
	 *            资源id
	 * @param type
	 *            资源类型，如：layout,id,string
	 * @return
	 */
	public static int getRes(String name, String type) {
		return getRes(MainApp.getContext(), name, type);
	}

	/**
	 * UUID+设备号序列号 唯一识别码（不可变）
	 * 
	 * @return
	 */
	public static String getMyUUID() {
		String uuid;
		final TelephonyManager tm = (TelephonyManager) MainApp.getContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(MainApp
						.getContext().getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		uuid = deviceUuid.toString();
		if (!TextUtils.isEmpty(uuid)) {
			uuid = uuid.replace("-", "");
		}
		return uuid;
	}

	/**
	 * 转换html
	 * 
	 * @param content
	 * @return
	 */
	public static String getConvertHtml(String content) {
		String rs = content;
		if (!TextUtils.isEmpty(content)) {
			rs = "";
			String[] arr_contents = content.split("\r\n");
			for (int i = 0, len = arr_contents.length; i < len; i++) {
				rs += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ arr_contents[i] + "<br/>";
			}
		}
		return rs;
	}

	/**
	 * 解析img标签
	 * 
	 * @param content
	 *            html内容
	 * @param img_id_prefix
	 *            img的id前缀
	 * @param def_img_url
	 *            默认图片（相对assets文件夹路径）
	 * @return 0：html内容，1：图片集合
	 */
	public static Object[] getHtmlByImg(String content, String img_id_prefix,
			String def_img_url) {
		// 图片的最大宽度
		int image_widht = 220;

		Object[] rs = new Object[2];
		List<String[]> imgurls = new ArrayList<String[]>();

		// 启用不区分大小写的匹配
		// 匹配img标签
		Pattern pattern = Pattern.compile("<img[^>]+>",
				Pattern.CASE_INSENSITIVE);

		// 匹配src
		Pattern src_pattern = Pattern.compile("src=\"\\S+\"",
				Pattern.CASE_INSENSITIVE);
		// 匹配alt
		Pattern alt_pattern = Pattern.compile("alt=\"\\S+\"",
				Pattern.CASE_INSENSITIVE);
		// 匹配width
		Pattern width_pattern = Pattern.compile("width=\"\\d+(px)?\"",
				Pattern.CASE_INSENSITIVE);
		// 匹配height
		Pattern height_pattern = Pattern.compile("height=\"\\d+(px)?\"",
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(content);
		int len = 0;
		int index = 0;
		while (matcher.find()) {
			String _content = matcher.group();
			int width = 0;// 宽度
			int height = 0;// 高度

			String src = "";
			String alt = "";

			Matcher src_matcher = src_pattern.matcher(_content);
			if (src_matcher.find()) {
				src = src_matcher.group().replace("\"", "").replace("src=", "");
				imgurls.add(new String[] { src, img_id_prefix + index });
			}

			Matcher alt_matcher = alt_pattern.matcher(_content);
			if (alt_matcher.find()) {
				alt = alt_matcher.group().replace("\"", "").replace("alt=", "");
			}

			Matcher width_matcher = width_pattern.matcher(_content);
			if (width_matcher.find()) {
				width = Integer.valueOf(width_matcher.group().replace("\"", "")
						.replace("width=", "").replace("px", ""));
			}
			Matcher height_matcher = height_pattern.matcher(_content);
			if (height_matcher.find()) {
				height = Integer.valueOf(height_matcher.group()
						.replace("\"", "").replace("height=", "")
						.replace("px", ""));
			}

			// 超过屏幕宽度，进行缩小
			if (width > image_widht) {
				// 等比例缩放
				if (height > 0) {
					height = (int) (height * image_widht / (float) width);
				}
				width = image_widht;
			}
			String str = "";
			if (!src.equals("")) {
				str = "<img "
						+ (alt.equals("") ? "" : (" alt=\"" + alt + "\""))
						+ " id=\"" + img_id_prefix + +index
						+ "\" src=\"file:///android_asset/" + def_img_url
						+ "\" "
						+ (width > 0 ? ("width=\"" + width + "px\"") : "")
						+ " "
						+ (height > 0 ? ("height=\"" + height + "px\"") : "")
						+ "/>";
			} else {
				str = "<img src=\"\"/>";
			}
			content = content.substring(0, matcher.start() + len) + str
					+ content.substring(matcher.end() + len, content.length());
			len += str.length() - _content.length();
			index++;
		}
		rs[0] = content;
		rs[1] = imgurls;
		return rs;
	}

	/**
	 * 设置HttpURLConnection
	 * 
	 * @param connection
	 *            URLConnection链接
	 * @param timeout
	 *            超时毫秒数
	 * @return HttpURLConnection
	 * */
	public static HttpURLConnection setHttpURLConnection(
			HttpURLConnection connection, int timeout) throws IOException {
		connection.setConnectTimeout(timeout);
		connection.setReadTimeout(timeout);
		connection.setRequestMethod("GET");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection
				.setRequestProperty(
						"Accept",
						"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		connection.setRequestProperty("Accept-Language", "zh-CN");
		connection.setRequestProperty("Referer", "");
		connection.setRequestProperty("Charset", "UTF-8");
		connection
				.setRequestProperty(
						"User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		connection.setRequestProperty("Connection", "Keep-Alive");

		return connection;
	}

	/**
	 * 获取总共分几页
	 * 
	 * @param readcount
	 *            总记录数
	 * @param pagesize
	 *            每页大小
	 * @return 页数
	 */
	public static int getPageCount(int readcount, int pagesize) {
		if (readcount != 0 && pagesize != 0) {
			return (readcount + pagesize - 1) / pagesize;
		}
		return 0;
	}

	/**
	 * 解析URL的Get参数
	 * 
	 * @param str
	 *            url参数字符串
	 * @return 键值对
	 */
	@SuppressWarnings("deprecation")
	public static Map<String, String> decodeUrl(String str) {
		Map<String, String> params = new HashMap<String, String>();
		if (str != null) {
			String array[] = str.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				params.put(URLDecoder.decode(v[0]),
						v.length > 1 ? URLDecoder.decode(v[1]) : "");
			}
		}
		return params;
	}

	/**
	 * 计算百分比
	 * 
	 * @param num
	 *            数量
	 * @param total
	 *            总数量
	 * @return 百分比
	 */
	public static int getPercent(int num, float total) {
		float rs = (num / total) * 100;
		return (int) rs;
	}

	/**
	 * 获取当前程序的版本号
	 * 
	 * @return
	 */
	public static int getAppVersionCode() {
		PackageInfo packInfo = getPackageInfo();
		if (packInfo != null) {
			return packInfo.versionCode;
		}
		return 0;
	}

	/**
	 * 获取当前程序的版本名称
	 * 
	 * @return
	 */
	public static String getAppVersionName() {
		PackageInfo packInfo = getPackageInfo();
		if (packInfo != null) {
			return packInfo.versionName;
		}
		return "";
	}

	/**
	 * 获取当前程序的信息
	 * 
	 * @return
	 */
	public static PackageInfo getPackageInfo() {
		PackageManager packageManager = MainApp.getContext()
				.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(MainApp.getContext()
					.getPackageName(), 0);
		} catch (NameNotFoundException e) {
		}
		return packInfo;
	}

	/**
	 * 显示信息-自带
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息
	 */
	public static void showMessage(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示信息-中间
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            信息
	 */
	public static void ShowMessageByRoundToast(Context context, String msg) {
		RoundToast.getInstance(context, msg).show();
	}

	/**
	 * 显示信息-自带
	 * 
	 * @param context
	 *            上下文
	 * @param toast
	 *            显示对象
	 * @param msg
	 *            信息
	 * @return 显示对象
	 */
	public static Toast showMessage(Context context, Toast toast, String msg) {
		if (msg != null) {
			if (toast == null) {
				toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			} else {
				toast.setText(msg);
			}
			toast.show();
		}
		return toast;
	}

	/**
	 * 显示信息-中间
	 * 
	 * @param context
	 *            上下文
	 * @param toast
	 *            显示对象
	 * @param msg
	 *            信息
	 * @return 显示对象
	 */
	public static RoundToast ShowMessageByRoundToast(Context context,
			RoundToast toast, String msg) {
		if (msg != null) {
			if (toast == null) {
				toast = RoundToast.getInstance(context, msg);
			} else {
				toast.setMessage(msg);
			}
			toast.show();
		}
		return toast;
	}

}
