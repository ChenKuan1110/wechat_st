package com.wechat.po.message;

import com.wechat.po.Image;
/**
 * 图片消息类
 * @author chenkuan
 *
 */
public class ImageMessage extends BaseMessage {
	private String MsgType = "image";		
	private Image Image;		//图片消息的内容
	public Image getImage() {
		return Image;
	}
	public void setImage(Image image) {
		Image = image;
	}
	public String getMsgType() {
		return MsgType;
	}
	
}
