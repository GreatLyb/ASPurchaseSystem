package com.lysoft.baseproject.bean;

/**
 * 类描述：加载页面显示的时候显示的信息，需要配置显示的文字和现实的图片
 * 创建人：xiao
 * 创建时间：2018/2/28
 */
public class LoadViewInfo
{

	private String mMsgInfo;
	private int mDrawableID=0;
	public String getMsgInfo()
	{
		return mMsgInfo;
	}
	public void setMsgInfo(String mMsgInfo)
	{
		this.mMsgInfo = mMsgInfo;
	}
	public int getDrawableID()
	{
		return mDrawableID;
	}
	public void setDrawableID(int mDrawableID)
	{
		this.mDrawableID = mDrawableID;
	}

	public LoadViewInfo() {

	}

	public LoadViewInfo(String msgInfo, int drawableID)
	{
		super();
		this.mMsgInfo = msgInfo;
		this.mDrawableID = drawableID;
	}
	
}
