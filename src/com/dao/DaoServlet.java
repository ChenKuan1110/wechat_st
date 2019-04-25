package com.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.wechat.util.MessageUtil;

/**
 * Servlet implementation class DaoServlet
 */
@WebServlet("/DaoServlet")
public class DaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//记录客户端上传后的数据
	private static String info;
	
    PrintStream printStream;
    public static String getInfo() {
		return info;
	}



	public static void setInfo(String info) {
		DaoServlet.info = info;
	}



	/**
     * @see HttpServlet#HttpServlet()
     */
    public DaoServlet() {
        super();
        
    }
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		System.out.println("执行到此");
		
		//设备码
		int code = (int) request.getAttribute("equipment_code");
		//公众号id
		String toUserName = (String) request.getAttribute("toUserName");
		//用户id
		String fromUserName = (String) request.getAttribute("fromUserName");
		
		System.out.println("标示符："+request.getServletPath()+"接收到的设备码为："+code);
		System.out.println("开始控制设备");
		System.out.println("发送方："+fromUserName);
		System.out.println("接收方："+toUserName);
		
		
		PrintWriter out = response.getWriter();
		String message = null;	//返回消息
		
		if (code != 0) {
			
			message = MessageUtil.initText(toUserName, fromUserName, "正在为您开启设备");
			//返回公众号等待
			
			System.out.println("控制设备等操作");
			out.print(message);
			
			//TODO	从数据库取数据
			try {
	            	
	                //此操作是让其在29997号端口进行监听
					
					ServerSocket serverSocket = new ServerSocket(29997);
	                out.println("正在监听...");
	                System.out.println("正在监听...");
	                

	                //等待某个客户端来连接,accept会返回一个Socket连接
	                Socket socket = serverSocket.accept();
	                out.println("连接成功！");
	                //用IO流来接收客户端发送来的信息
	                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
					printStream = new PrintStream(socket.getOutputStream(),true);
					
					printStream.println(code);
					while (true){
						info = bufferedReader.readLine();
						if (info!=null){
							System.out.println(info);
						}
						break;
					}
					socket.close();
					serverSocket.close();
	        }catch (Exception e){
	            e.printStackTrace();
	        }			
		}	
	}
	
	
	
	public boolean isPortUsing(String host,int port)
	{
		boolean flag = false;
		Socket socket = null;
		try {
			if (flag){
				InetAddress inetAddress = InetAddress.getByName(host);
				socket = new Socket(inetAddress,port);
				flag = true;
				socket.close();
			}
			else {
				flag=true;
				//socket.close();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}

}
