package com.lemonjiang.entity;

import java.io.Serializable;

/**
 * 屏幕信息
 * */
public class Screen implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 分辨率宽度 */
	private int widthPixels;
	/** 分辨率高度 */
	private int heightPixels;
	/** 密度实际值，如:0,0.75,1,1.5,2 */
	private float density;

	public Screen() {
	}

	/**
	 * 获取宽度dp
	 * 
	 * @return
	 */
	public int getWidthPixels() {
		return widthPixels;
	}

	/**
	 * 设置宽度dp
	 * 
	 * @return
	 */
	public void setWidthPixels(int widthPixels) {
		this.widthPixels = widthPixels;
	}

	/**
	 * 获取高度dp
	 * 
	 * @return
	 */
	public int getHeightPixels() {
		return heightPixels;
	}

	/**
	 * 设置高度dp
	 * 
	 * @return
	 */
	public void setHeightPixels(int heightPixels) {
		this.heightPixels = heightPixels;
	}

	/**
	 * 获取密度实际值，如:0,0.75,1,1.5,2
	 * 
	 * @return
	 */
	public float getDensity() {
		return density;
	}

	/**
	 * 设置密度实际值，如:0,0.75,1,1.5,2
	 * 
	 * @param density
	 */
	public void setDensity(float density) {
		this.density = density;
	}
}
