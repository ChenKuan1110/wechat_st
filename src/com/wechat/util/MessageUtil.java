package com.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import com.thoughtworks.xstream.XStream;
import com.wechat.po.message.*;
import com.wechat.po.*;

/**
 * 微信消息工具类
 * @author chenkuan
 * 
 * 		包含
 * 		1.将微信后台接收到的xml数据包解析为Map集合
 * 		2.文本、图片、图文消息的组装及打包为xml数据
 *
 */
public class MessageUtil {

	/**
	 * 微信后台接收/发送 消息类型 和 事件类型
	 */
	public static final String MESSAGE_TEXT = "text";	//文本消息
	public static final String MESSAGE_NEWS = "news";	//图文消息
	public static final String MESSAGE_IMAGE = "image";	//图片消息
	public static final String MESSAGE_VOICE = "voice";	//语音消息
	public static final String MESSAGE_MUSIC = "music";	//音乐消息
	public static final String MESSAGE_VIDEO = "video";	//视频消息
	public static final String MESSAGE_LINK = "link";	//链接
	public static final String EVENT_LOCATION = "location";	//位置事件
	public static final String MESSAGE_EVNET = "event";		//事件消息
	public static final String EVENT_SUBSCRIBE = "subscribe";	//订阅事件
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";	//取消订阅事件
	public static final String EVENT_CLICK = "CLICK";	//点击拉取消息事件
	public static final String EVENT_VIEW = "VIEW";		//点击调转事件
	public static final String EVENT_SCANCODE= "scancode_push";	//扫码事件
	public static final String EVENT_SCANCODE_WAITmsg= "scancode_waitmsg";	//扫码推送事件
	
	
	//*****************************************************************************
	
	/**
	 * xml转为map集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	//*****************************************************************************
	
	/**
	 * 将文本消息转为xml
	 * @param textMessage
	 * @return	xml字符串
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	
	/**
	 * 组装文本消息
	 * @param toUserName	接收方
	 * @param fromUserName	发送方
	 * @param content		发送内容
	 * @return		转换为xml格式的内容
	 */
	public static String initText(String toUserName,String fromUserName,String content) {
		TextMessage text = new TextMessage();
		text.setContent(content);			
		text.setFromUserName(toUserName);	
		text.setToUserName(fromUserName);	
		text.setCreateTime(new Date().getTime());	//设置发送时间
		return textMessageToXml(text);		//返回xml数据
	}
	
	//************************************文本菜单*****************************************
	
	/**
	 * 欢迎文本————关注后回复的内容
	 * @return
	 */
	public static String welcomeText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注！");
		sb.append("\n");
		sb.append("我是舌苔鉴定小专家，欢迎使用我！");
		sb.append("\n");
		sb.append("输入设备编号，即可开启鉴定之旅哦！");
	
		return sb.toString();
	}
	
	
	public static String operateText() {
		StringBuffer sb = new StringBuffer();
		sb.append("设备操作步骤说明：\n\n");
		sb.append("1.扫描设别二维码或手动输入设备号码\n\n");
		sb.append("2.等待设备启动，采集舌象图片\n\n");
		sb.append("3.点击 “刷新”按钮 获取诊断结果\n");
		sb.append("\t\t\t\t\t--望知先生");
		return sb.toString();
	}
	
	
	
	

	
	
	
	//*****************************************************************************
	
	/**
	 * 图片消息转为xml
	 * @param imageMessage 图片消息对象
	 * @return	转换为xml格式的字符串
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 初始化图片消息
	 * @param toUserName	接收方
	 * @param fromUserName	发送方
	 * @param mediaId	图片对象的mediaId
	 * @return 		转换为xml格式的图片消息字符串
	 */
	public static String initImageMessage(String toUserName,String fromUserName,String mediaId) {
		String message = null;
		Image image = new Image();
		image.setMediaId(mediaId);
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setToUserName(fromUserName);
		imageMessage.setFromUserName(toUserName);
		imageMessage.setImage(image);
		imageMessage.setCreateTime(new Date().getTime());
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	
	
	
	
	//*****************************************************************************
	
	/**
	 * 图文消息转为xml
	 * @param newsMessage  图文消息对象
	 * @return	xml字符串
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	
	/**
	 * 组装图文消息
	 * @param toUserName 接收方
	 * @param fromUserName	发送方
	 * @return xml格式的图文消息
	 */
	public static String initNewsMessage(String toUserName,String fromUserName) {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("您的诊断结果");	//设置图文标题
		news.setDescription("诊断结果出炉，点击查看吧！");	//设置图文描述
		news.setPicUrl("http://www.yunbaozhai.net/sxcj/_asset/images/data/9%E7%89%9B%E8%82%89.jpg");	//缩略图url		
		news.setUrl("http://www.yunbaozhai.net/wechat_st/result.jsp");		//设置点击跳转的URL
		newsList.add(news);
		
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);
		newsMessage.setCreateTime(new Date().getTime());
		
		message = newsMessageToXml(newsMessage);
		
		return message;
	}
	
	
	//*****************************************************************************
	
	/**
	 * 视频消息转Xml
	 * @param videoMessage
	 * @return xml格式的视频消息
	 */
	public static String VideoMessageToXml(VideoMessage videoMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", videoMessage.getClass());
		xstream.alias("Video", new News().getClass());
		return xstream.toXML(videoMessage);
	}
	
	
	/**
	 * 初始化视频消息
	 * @param toUserName 发送方
	 * @param fromUserName	接收方
	 * @param mediaId	上传视频消息的MediaId
	 * @param title		视频标题
	 * @param desription 视频描述
	 * @return
	 */
	public static String initVideoMessage(String toUserName,String fromUserName,String mediaId,String title,String description) {
		String message = null;
		Video video = new Video();
		VideoMessage videoMessage = new VideoMessage();
		
		video.setMediaId(mediaId);
		video.setDescription(description);
		video.setTitle(title);
		
		videoMessage.setToUserName(fromUserName);
		videoMessage.setFromUserName(toUserName);
		videoMessage.setCreateTime(new Date().getTime());
		videoMessage.setVideo(video);
		
		message = VideoMessageToXml(videoMessage);
		
		return message;
	}
	
	
	
	
}
