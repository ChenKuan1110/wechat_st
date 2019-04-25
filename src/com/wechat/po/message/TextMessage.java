package com.wechat.po.message;

public class TextMessage extends BaseMessage {
	//消息类型
	private String MsgType = "text";
	//消息内容
	private String Content;
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgType() {
		return MsgType;
	}
	
}
