package com.wechat.po;

/**
 * 图文实体类
 * @author chenkuan
 *
 */
public class News {
	private String Title;	//标题
	private String Description;	//描述
	private String PicUrl;	//图片Url
	private String Url;		//点击图文消息跳转链接
	
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
}
