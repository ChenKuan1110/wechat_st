package com.wechat.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;



import com.dao.TcpServer;
import com.wechat.util.CheckUtil;
import com.wechat.util.CommonUtil;
import com.wechat.util.MessageUtil;

/**
 * 专门测试舌苔公众号发送设备码
 * @author chenkuan
 *
 */
@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int equipment_code = 0;		//记录设别码
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DemoServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		PrintWriter out = response.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置请求和相应的字符串编码为UTF-8
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//响应
		PrintWriter out = response.getWriter();
		
		Map<String, String> map;
		try {
 			 map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");	//发送方
			String toUserName = map.get("ToUserName");	//该公众号
			String msgType = map.get("MsgType");	//消息类型
			
			String message = null;		//从服务器发送给用户的信息
			
			System.out.println("com.wechat.sevlet --- 接收用户发送来的消息（转换为map后）:\n"+map.toString());
			
			if(msgType.equals(MessageUtil.MESSAGE_TEXT)) {	//文本消息
				String content = map.get("Content");	//接收到的文本消息内容
				
				//处理设备码
				try {
					equipment_code = Integer.parseInt(content);
					System.out.println("com.wechat.servlet.DemoServlet --- 从消息中解析出的设备码："+ equipment_code); 
					
					
					// 如果获取到设备码，就转发到处理的Servlet
					if(equipment_code != 0) {		//
						request.setAttribute("equipment_code", equipment_code);
						request.setAttribute("fromUserName",fromUserName);
						request.setAttribute("toUserName",toUserName);
						request.getRequestDispatcher("TestServlet").forward(request, response);
						equipment_code = 0;	//把设备码置零
					}
					
				}catch(NumberFormatException e){
					/**
					 * 执行其他业务操作（客户发送文本）
					 */
					System.out.println("com.wechat.servlet -- 用户发送的文本内容："+ content);
					message = MessageUtil.initText(toUserName, fromUserName, "您发送的文本内容是："+content);
					switch (content) {
					case "图文测试":
						message = MessageUtil.initNewsMessage(toUserName, fromUserName);
						break;

					default:
						break;
					}
				}
			}else if(MessageUtil.MESSAGE_IMAGE.equals(msgType)){	//图片消息处理
				String mediaId = map.get("MediaId");
				
				String picUrl0 = map.get("PicUrl");			//图片地址
				String PicUrl = picUrl0.substring(0, picUrl0.length() - 1);		//图片地址（原图）
				System.out.println("DemoServlet.doPost()--->PicUrl:"+PicUrl);
				
				try {
					CommonUtil.download(PicUrl, "wechat_test.jpg", "/Users/chenkuan/Desktop/");
					message = MessageUtil.initText(toUserName, fromUserName,
							"图片上传成功！稍后为你解析...");
				} catch (Exception e) {
					System.out.println("保存失败！！！");
				}
				
				
//				message = MessageUtil.initImageMessage(toUserName, fromUserName, mediaId);
				
			}else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) { 					//自定义菜单事件
				String eventType = map.get("Event");	//事件类型
				
				System.out.println("com.wechat.servlet  --  接收到的事件类型：" + eventType);
				
				//根据事件类型不同来执行不同的操作
				if(MessageUtil.EVENT_CLICK.equals(eventType)) {		//Click事件
					String eventKey = map.get("EventKey");		
					System.out.println("com.wechat.servlet -- 事件类型的key值：" + eventKey);
					switch (eventKey) {
					case "click":
						String backInfo = TcpServer.getResult();
						if(backInfo == null) {	//没获取到诊断结果
							
							//返回提示信息
							message = MessageUtil.initText(toUserName, fromUserName, "还未出测试结果，请您稍后再试");
						}else {
//							//返回带结果图文消息链接
//							message = MessageUtil.initNewsMessage(toUserName, fromUserName);
							//返回字符串
							message = MessageUtil.initText(toUserName, fromUserName, backInfo);
							
							
							
						}
						break;
					case "operate":
						//返回操作信息
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.operateText());
						break;
					default:
						break;
					}	
				}else if (MessageUtil.EVENT_SUBSCRIBE.equals(eventType)) {	//关注事件
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.welcomeText());
				}else if(MessageUtil.EVENT_SCANCODE.equals(eventType)) { 	//扫码
					message = MessageUtil.initText(toUserName, fromUserName, "调用扫描成功！");
				}else if(MessageUtil.EVENT_SCANCODE_WAITmsg.equals(eventType)) { 		//扫码发送消息
					System.out.println("com.wechat.servlet --- 扫描到的信息："+map.get("ScanCodeInfo"));
					message = MessageUtil.initText(toUserName, fromUserName, "扫描成功，开启设备中！！！");
				}else if (MessageUtil.EVENT_LOCATION.equals(eventType)) {	//上报位置事件
					String label = map.get("Label");
					System.out.println("com.wechat.servlet --- 获取到的地理位置（）："+label);
					message = MessageUtil.initText(toUserName, fromUserName, label);
				}
			}
			
			out.print(message);	//响应客户端消息
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
