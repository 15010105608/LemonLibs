package com.lemonjiang.cache;

import java.io.Serializable;
import java.util.Date;

/**
 * 缓存对象
 */
public class Cache implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// 缓存内容
	private String content;
	// 缓存日期
	private Date date;

	public Cache() {
	}

	/**
	 * @param content
	 *            缓存内容
	 * 
	 * @param date
	 *            缓存时间
	 * */
	public Cache(String content, Date date) {
		this.content = content;
		this.date = date;
	}

	/**
	 * 获取缓存内容
	 * */
	public String getContent() {
		return content;
	}

	/**
	 * 设置缓存内容
	 * */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取缓存日期
	 * */
	public Date getDate() {
		return date;
	}

	/**
	 * 设置缓存日期
	 * */
	public void setDate(Date date) {
		this.date = date;
	}

}
