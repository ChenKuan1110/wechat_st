package com.dao;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 线程监听类
 */
public enum  TcpListener {
    INSTANCE;
    TcpServer tcpServer;

    public void connected(){
        try {
            ServerSocket serverSocket = new ServerSocket(30000);
            System.out.println("TcpListener.connected():启动监听");

            while (true){
                System.out.println("服务器正在监听--》tcpListener");
                System.out.println(ManageTread.getOnLineUser());
                Socket socket = serverSocket.accept();
                System.out.println("有新的设备连接到服务器--》tcpListener");
                tcpServer = new TcpServer(socket);
                tcpServer.start();

                try {
                    Thread.sleep(2000);
                    if (tcpServer.getUSER_ID() != null){
                        System.out.println("获取到的设备id--》tcpListener：" + tcpServer.getUSER_ID());
                        //设备管理
                        ManageTread.addTread(tcpServer.getUSER_ID(),tcpServer);
                        System.out.println("已添加设备到集合--》tcpListener：" + tcpServer.getUSER_ID());
                        System.out.println(ManageTread.getOnLineUser());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
