//package com.dao;
//
//import javax.swing.*;
//
//import com.wechat.servlet.Servlet;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//
///**
// * 服务器监听类
// * 服务器启动之后就会阻塞等待连接
// * 如果放在主线程中，主线程也就会被阻塞
// * 所以将有阻塞的代码提出来放入一个独立的线程中是十分有必要的
// */
//public class ServerListener{
//	
//	
//    private TcpPrc m_TpcPrc;
//    private String path;
//    private int flag = 0;
//    
//    Map<Integer,TcpPrc> map = new HashMap<Integer,TcpPrc>();
//	int i = 1;
//	
//	private static ServerListener s = new ServerListener();
//	
//	public static ServerListener getInstance() {
//		return s;
//	}
//	
//    /**
//     * 继承实现线程
//     * 重写父类的方法，run方法
//     */
//    private ServerListener(){
//        
//    }
//    
//    public void connected() throws IOException  {
//    	
//    		try {
//    			if(!NetUtil.isPortUsing("127.0.0.1", 9999)) {
//        			ServerSocket serverSocket = new ServerSocket(9999);
//        			System.out.println("服务器启动");
//        			Socket socket = serverSocket.accept();
//        			m_TpcPrc = new TcpPrc(socket,path);
//    	              map.put(i,m_TpcPrc );
//    	              m_TpcPrc.start();
//    	              m_TpcPrc.StartNow();
//    			}else {
//    				map.get(i).StartNow();
//    			}
//
//    	    	
//    		}catch(Exception e) {
//    			e.printStackTrace();
//    		}
//    }
////    public void run() {
////        try {
////            /**
////             * 打开监听端口，1-65535，
////             * 最好选择大的，和预留及程序占用的端口区分开
////             */
////            ServerSocket serverSocket = new ServerSocket(9999);
//////            System.out.println("服务器启动");
////            /**
////             *  监听并等待连接
////             *  对于accept()
////             *  每当有新的客户端连接上来，都会返回一个新的socket
////             *  如果有多个客户端，同理就会有多个socket
////             *  所以将其放入while循环，一直处于监听的状态
////             */
////            while (true){
////                Socket socket = serverSocket.accept();
////                System.out.println("com.dao.ServerListener -- 通信端口号：" + socket.getPort());
////                //每当有新的连接就弹出提示框提示
//////                JOptionPane.showMessageDialog(null,"设备连接");
////                /**
////                 * 多台设备，多个socket不可能同时放在此处处理
////                 * 所以需要为每一个socket创建一个线程
////                 * 并将socket传递给新的线程CheckSocket
////                 * 让他们分开来处理各自的事情
////                 */
////                    m_TpcPrc = new TcpPrc(socket,path);
////                    m_TpcPrc.start();
////
////                /**
////                 * 项目调用
////                 */
//////                TcpPrc tmp = (TcpPrc)TcpPrc.m_Map.get(socket.getInetAddress().toString() + socket.getPort());
//////                    tmp.StartNow();
////////                    tmp.getID();
//////                    while(TcpPrc.isGetResult) {
//////                    	m_TpcPrc.stop();
//////                    }
////                    
////            }
////        }catch (Exception e){
////            e.printStackTrace();
////        }
////    }
//}
