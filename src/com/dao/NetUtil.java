package com.dao;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetUtil {
    public static void main(String[] args) {
        try {
            boolean flag = isPortUsing("127.0.0.1",9999);
            System.out.println(flag);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    /**
     * 本地判断端口是否被占用
     * @param port 端口号
     * @return
     */
    public static boolean isLocalePortUsing(int port){
        boolean flag = true;
        try {
            flag = isPortUsing("127.0.0.1",port);
        } catch (UnknownHostException e) {
//            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 判断端口是否被占用
     * @param host ip地址
     * @param port 端口号
     * @return
     * @throws UnknownHostException
     */
    public static boolean isPortUsing(String host,int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress inetAddress = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(inetAddress,port);
            socket.close();
            flag = true;
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return flag;
    }
}
