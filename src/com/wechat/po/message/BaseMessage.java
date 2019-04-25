package com.wechat.po.message;

/**
 * 消息父类
 * @author chenkuan
 *
 */
public class BaseMessage {
	//接收方微信号
	private String ToUserName;
	//发送方微信号
	private String FromUserName;
	//创建时间
	private long CreateTime;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	
	
}
