package com.dao;

import java.util.HashMap;
import java.util.Iterator;

public class ManageTread {
    public static HashMap hashMap = new HashMap<String,TcpServer>();

    /**
     * 向集合中添加一个设备线程
     */
    public static void addTread(String id,TcpServer tcpServer){
        hashMap.put(id,tcpServer);
    }

    /**
     * 根据设备id获取对应线程
     * @param id 设备编号
     * @return 返回类型TcpServer
     */
    public static TcpServer getTread(String id){
        return (TcpServer) hashMap.get(id);
    }

    /**
     * 返回当前在线的设备
     * @return 返回类型String
     */
    public static String getOnLineUser(){
        //使用迭代器完成
        Iterator iterator = hashMap.keySet().iterator();
        String reset = "";
        while (iterator.hasNext()){
            reset += iterator.next().toString()+" ";
        }
        return reset;
    }

    /**
     * 根据设备id删除一个设备
     * @param id 设备编号
     */
    public static void deleteUser(String id){
        hashMap.remove(id);
    }
}
