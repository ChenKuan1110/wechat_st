package com.dao;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wechat.util.MessageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 此类主要判断微信发送的设备id，是否在线
 * 
 * @author chenkuan
 *
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		/**
		 * 微信端发来客户端设备编号
		 */

		String toUserName = (String) request.getAttribute("toUserName"); // 公众号id
		String fromUserName = (String) request.getAttribute("fromUserName"); // 用户id
		String backMessage = null; // 微信返回用户的提示消息
		PrintWriter out = response.getWriter();

		int clientId = (int) request.getAttribute("equipment_code");
		String id = String.valueOf(clientId); // 微信服务器转发过来的设备码

		System.out.println("com.dao.TestServlet --- 接收到微信服务器转发的设备码：" + clientId);

		String userIds = ManageTread.getOnLineUser(); // 获取所有在线设备

//		if (NetUtil.isLocalePortUsing(9999)) { // 端口未被占用
//			TcpListener.INSTANCE.connected();
//			backMessage = MessageUtil.initText(toUserName, fromUserName, "网络繁忙，稍后再试！");
//		} else {
//			// 有设备在线, 查找请求设备id是否在列表中
//			String[] allUser = userIds.split(" "); // 解析出所有在线的设备
//			if (!Arrays.asList(allUser).contains(id)) { // 发送过来的id在维护列表中
//				// 请求设备不在线，返回提示信息
//				backMessage = MessageUtil.initText(toUserName, fromUserName, "请求的设备不在线");
//			} else {
//
//				TcpServer tcpServer = ManageTread.getTread(id); // 拿到线程
//				tcpServer.startNow(); // 让对应id的设备开始采集
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					backMessage = MessageUtil.initText(toUserName, fromUserName, TcpServer.getResult());
//					e.printStackTrace();
//				}
//				backMessage = MessageUtil.initText(toUserName, fromUserName, TcpServer.getResult());
//			}
//		}

        if("".equals(userIds)) {
        	TcpListener.INSTANCE.connected();
        	backMessage = MessageUtil.initText(toUserName, fromUserName, "网络繁忙，稍后再试！");
        }else {
        	 //有设备在线, 查找请求设备id是否在列表中
			String[] allUser = userIds.split(" "); // 解析出所有在线的设备
			if (!Arrays.asList(allUser).contains(id)) { // 发送过来的id不在维护列表中
				// 请求设备不在线，返回提示信息
				backMessage = MessageUtil.initText(toUserName, fromUserName, "请求的设备不在线");
			} else {

				TcpServer tcpServer = ManageTread.getTread(id); // 拿到线程
				tcpServer.startNow(); // 让对应id的设备开始采集
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					backMessage = MessageUtil.initText(toUserName, fromUserName, TcpServer.getResult());
					e.printStackTrace();
				}
				
				backMessage = MessageUtil.initText(toUserName, fromUserName, "正在为您开启设备...");
				//调用方法将之前获取过的结果置为空
				TcpServer.setResultNull();
			}
        	
        }

		// 返回微信提示信息
		out.print(backMessage);
	}
}
