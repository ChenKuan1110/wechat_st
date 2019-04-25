package com.wechat.po.message;

import com.wechat.po.Video;
/**
 * 视频消息类
 * @author chenkuan
 *
 */
public class VideoMessage extends BaseMessage {
	private String Msgtype = "video";		
	private Video Video;		//视频类对象
	public Video getVideo() {
		return Video;
	}
	public void setVideo(Video video) {
		Video = video;
	}
	public String getMsgtype() {
		return Msgtype;
	}
	
	
}
