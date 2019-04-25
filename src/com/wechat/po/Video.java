package com.wechat.po;

/**
 * 视频类
 * @author chenkuan
 *
 */
public class Video {
	private String MediaId;		//经上传接口上传的视频MediaId
	private String Title;		//视频标题
	private String Description; 	//视频描述
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
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
	
	
	
}
