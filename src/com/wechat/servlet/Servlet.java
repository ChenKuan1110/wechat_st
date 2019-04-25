package com.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.wechat.util.CheckUtil;
import com.wechat.util.MessageUtil;


/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private int equipment_code = 0;		//记录设别码
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
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
			
			//从服务器发送给用户的信息
			String message = null;
			
			System.out.println("接收用户发送来的消息（转换为map后）:\n"+map.toString()+"\n");
			
			if(msgType.equals(MessageUtil.MESSAGE_TEXT)) {	//文本消息
				String content = map.get("Content");	//接收到的文本消息内容
				
				
				
				//根据接收到的文本消息内容进行回复
				switch (content) {
				case "文本":
					message = MessageUtil.initText(toUserName, fromUserName, "收到你的消息：文本");
					break;
				case "图文消息":
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
					break;
				case "视频消息":
					message = MessageUtil.initVideoMessage(toUserName, fromUserName, "", "视频消息测试", "视频消息测试");
					break;
				default:
					try {
						equipment_code = Integer.parseInt(content);		//获取到的设备码
						message = MessageUtil.initText(toUserName, fromUserName, "收到设备码："+equipment_code+"\n马上为您开启设备...");
						System.out.println("*******接收到用户发送的设备码："+equipment_code);
						
					}catch(NumberFormatException e) {
						System.out.println("获取到的不是设备码！");
						e.printStackTrace();
					}
				}
			}else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) { 					//自定义菜单事件
				String eventType = map.get("Event");	//事件类型
				
				System.out.println("******接收到的事件类型："+eventType);
				
				//根据事件类型不同来执行不同的操作
				if(MessageUtil.EVENT_CLICK.equals(eventType)) {		//Click事件
					String eventKey = map.get("EventKey");		
					System.out.println("******事件类型的key值："+eventKey);
					switch (eventKey) {
					case "click":
						message = MessageUtil.initText(toUserName, fromUserName, "成功点击Click菜单");
						break;
					case "32":
						String label = map.get("Label");
						message = MessageUtil.initText(toUserName, fromUserName,label );
						break;
					case "31":
						message = MessageUtil.initText(toUserName, fromUserName, "启用扫描成功！");
						break;
					default:
						break;
					}
					
				}else if (MessageUtil.EVENT_SUBSCRIBE.equals(eventType)) {	//关注事件
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.welcomeText());
				}else if(MessageUtil.EVENT_SCANCODE.equals(eventType)) { 
					message = MessageUtil.initText(toUserName, fromUserName, "调用扫描成功！");
				}else if(MessageUtil.EVENT_SCANCODE_WAITmsg.equals(eventType)) { 		//扫码
					System.out.println("获取到的事件值："+map.get("EventKey"));
					System.out.println("扫描到的信息"+map.get("ScanCodeInfo"));
					message = MessageUtil.initText(toUserName, fromUserName, "扫描成功，开启设备中！！！");
				}else if (MessageUtil.EVENT_LOCATION.equals(eventType)) {
					String label = map.get("Label");
					System.out.println("获取到的地理位置（）："+label);
					message = MessageUtil.initText(toUserName, fromUserName, label);
				}
				
			}
			
			out.print(message);	//响应客户端消息
//			System.out.println("返回给客户端的消息：\n"+message+"\n");
			
			//TODO  如果获取到设备码，就转发到处理的Servlet
			if(equipment_code != 0) {		//
				request.setAttribute("equipment_code", equipment_code);
				request.getRequestDispatcher("DaoServlet").forward(request, response);
				System.out.println("**********设备码已转发到处理Servlet！************");
				equipment_code = 0;	//把设备码置零
			}
			
			
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
