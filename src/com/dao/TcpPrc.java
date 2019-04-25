//package com.dao;
//
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.net.Socket;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//
///**
// * 设备业务处理类
// * 在此类中为每个业务创建了一个线程来管理
// */
//public class TcpPrc extends Thread{
//	
//	
//	private static String[] result = null;		//存储返回微信端的结果
//	
//	public static boolean isGetResult = false;	//是否获取过结果的标志	
//	public static int getTimes = 0;
//	
//    //管理连接的容器
//    static public HashMap m_Map = new HashMap();
//
//    //传入的socket
//    private Socket m_s;
//    private DataInputStream m_DataInputStream;
//    private DataOutputStream m_DataOutputStream;
//    private BufferManager m_ReadBuff;
//    private BufferManager m_WriteBuff;
//    private TimerThread m_Timer;
//
//    private TcpReadData m_ThreadReadData;
//    private TcpWriteData m_ThreadWriteData;
//    private DataPrc m_ThreadDataPrc;
//
//    private int m_RecTimeout;
//    private boolean m_IsConected;
//
//    private int ID;
//    private String path;
//
//    private enum RecStatus{
//        FIND_HEAD,
//        WAIT_CHECK,
//        WAIT_PARAM,
//        WAIT_CRC
//    }
//    private enum CMD_ID{
//        NULL,
//        HEAD_BEAT,
//        REGISTERD,
//        CONFIG_INFO,
//        RETURN,
//        RUNNING
//    }
//
//    /**
//     * 构造方法
//     * 初始化一些参数
//     * @param s 传入的socket
//     */
//    public TcpPrc(Socket s,String string) {
//        //存储连接的HashMap
//        m_Map.put(s.getInetAddress().toString() + s.getPort(), this);
//        //判断心跳包的标志
//        m_RecTimeout = 0;
//        m_IsConected = true;
//        /**
//         * 开辟读数据存储空间
//         * 开辟写数据存储空间
//         */
//        m_ReadBuff = new BufferManager(1024 * 1024);
//        m_WriteBuff = new BufferManager(1024 * 1024);
//        m_s = s;
//        this.path = string;
//        try {
//            m_DataInputStream = new DataInputStream(m_s.getInputStream());
//            m_DataOutputStream = new DataOutputStream(m_s.getOutputStream());
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * 在此线程中启动各个线程
//     */
//    public void run() {
//        m_ThreadReadData = new TcpReadData();
//        m_ThreadWriteData = new TcpWriteData();
//        m_ThreadDataPrc = new DataPrc();
//        m_Timer = new TimerThread();
//    
//
//        m_ThreadReadData.start();
//        m_ThreadWriteData.start();
//        m_ThreadDataPrc.start();
//        m_Timer.start();
//
//        while(m_IsConected) {
//            try {
//                Thread.sleep(500);
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//        m_Map.remove(m_s.getInetAddress().toString() + m_s.getPort());
//       
//    }
//    /**
//     * 发送心跳包线程
//     * 由标志位判断发送，5秒一个心跳包，而如果超过10秒没有发送心跳包
//     * 就将心跳包标志位赋值false，这样就可以判断设备已经断开连接
//     */
//    public class TimerThread extends Thread{
//        public void run() {
//            int sendCount = 0;
//            byte[] bytes = new byte[20];
//
//            while(m_IsConected) {
//                if(sendCount < 5) {
//                    sendCount++;
//                } else {
//                    sendCount = 0;
//                    bytes[0] = (byte) 0xa5;
//                    bytes[1] = (byte) 0xa5;
//
//                    bytes[2] = (byte) 0x02;
//
//                    bytes[3] = (byte) 0x01;
//
//                    bytes[4] = (byte) 0x00;
//                    bytes[5] = (byte) 0x00;
//                    bytes[6] = (byte) 0x00;
//                    bytes[7] = (byte) 0x00;
//
//                    byte sum = (byte)0;
//                    for (int i = 0 ; i < 8 ; i++){
//                        sum += bytes[i];
//                    }
//                    bytes[8] = sum;
//
//                    bytes[9] = (byte) 0xff;
//                    bytes[10] = (byte) 0xff;
//
//                    m_WriteBuff.PushBuffer(bytes, 11);
//                }
//
//                try {
//                    Thread.sleep(1000);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//
//                m_RecTimeout++;
//                if(m_RecTimeout > 10) {
//                    m_IsConected = false;
//                }
//            }
//        }
//    }
//    /**
//     * 设备启动线程
//     */
//    public void StartNow() {
//        byte[] bytes = new byte[20];
//
//        bytes[0] = (byte) 0xa5;
//        bytes[1] = (byte) 0xa5;
//
//        bytes[2] = (byte) 0x02;
//        bytes[3] = (byte) 0x05;
//
//        bytes[4] = (byte) 0x00;
//        bytes[5] = (byte) 0x00;
//        bytes[6] = (byte) 0x00;
//        bytes[7] = (byte) 0x00;
//
//        byte sum = (byte)0;
//        for (int i = 0 ; i < 8 ; i++){
//            sum += bytes[i];
//        }
//        bytes[8] = sum;
//
//        bytes[9] = (byte) 0xff;
//        bytes[10] = (byte) 0xff;
//
//        m_WriteBuff.PushBuffer(bytes, 11);
//    }
//    /**
//     * 将接收到的数据向事先开辟好的buffer（1M）中写入
//     */
//    private class TcpWriteData extends Thread {
//        public void run() {
//            byte[] buf;
////            System.out.println(m_s.getInetAddress().toString() + " " + m_s.getPort() + " " + "写线程启动" );
//            //如果心跳包的标志位为true，也就是设备还处于连接中，那么才执行下面的程序
//            while(m_IsConected) {
//                try {
//                    buf = m_WriteBuff.GetBuffer();
//                    if(buf != null) {
//                        m_DataOutputStream.write(buf, 0, buf.length);
////                        System.out.println(m_s.getInetAddress().toString() + " " + m_s.getPort() + " " + "Tcp send size = " + buf.length);
//                    }
//                    Thread.sleep(10);
//                } catch(Exception e) {
//                    m_IsConected = false;
////                    e.printStackTrace();
//                    try {
//                        if (m_DataOutputStream!=null){
//                            m_DataOutputStream.close();
//                        }
//                    }catch (Exception e1){
//                        e1.printStackTrace();
//                    }
//                    break;
//                }
//            }
////            System.out.println(m_s.getInetAddress().toString() + " " + m_s.getPort() + " " + "写线程结束" );
//        }
//    }
//    /**
//     * 在事先开辟好的buffer（1M）中读取数据
//     * 读取速度1024 * 4
//     */
//    private class TcpReadData extends Thread {
//        public void run() {
////            System.out.println(m_s.getInetAddress().toString() + " " + m_s.getPort() + " " + "读线程启动" );
//            byte[] buf = new byte[1024 * 4];
//            //如果心跳包的标志位为true，也就是设备还处于连接中，那么才执行下面的程序
//            while(m_IsConected) {
//                try {
//                    int readSize, bufSize;
//                    bufSize = m_ReadBuff.GetSize();
//                    if(bufSize > 0){
//                        //确保输入流中有数据
//                        if (m_DataInputStream.available()!=0){
//                            if(bufSize < 4096) {
//                                readSize = m_DataInputStream.read(buf, 0, bufSize);
//                            }
//                            else {
//                                readSize = m_DataInputStream.read(buf, 0, 4096);
//                            }
//                            if(readSize > 0) {
//                                m_ReadBuff.PushBuffer(buf, readSize);
////                        System.out.println(m_s.getInetAddress().toString() + " " + m_s.getPort() + " " + "Tcp read size = " + readSize);
//                            }
//                        }
//                    }
//                    Thread.sleep(10);
//                } catch(Exception e) {
//                    m_IsConected = false;
//                    e.printStackTrace();
//                    try {
//                        if (m_DataInputStream!=null){
//                            m_DataInputStream.close();
//                        }
//                    }catch (Exception e1){
//                        e1.printStackTrace();
//                    }
//                }
//            }
////            System.out.println(m_s.getInetAddress().toString() + " " + m_s.getPort() + " " + "读线程结束" );
//        }
//    }
//    /**
//     * 业务处理线程
//     */
//    private class DataPrc extends Thread {
//        int minId=0;
//        public void run() {
////            System.out.println(m_s.getInetAddress().toString() + " " + m_s.getPort() + " " + "处理线程启动" );
//
//            TcpPrc.RecStatus recStatus = TcpPrc.RecStatus.FIND_HEAD;
//            TcpPrc.CMD_ID cmd_id = TcpPrc.CMD_ID.HEAD_BEAT;
//            int paramLen = 0;
//            byte[] buf;
//            //如果心跳包的标志位为true，也就是设备还处于连接中，那么才执行下面的程序
//            while(m_IsConected) {
//                switch (recStatus) {
//                    case FIND_HEAD:
//                        //从接收buffer里查找0XA5 0XA5
//                        if (m_ReadBuff.FindHead()) {
//                            recStatus = TcpPrc.RecStatus.WAIT_CHECK;
//                        }
//                        break;
//
//                    case WAIT_CHECK:
//                        //等待收满9个字节，并检验累加和
//                        buf = m_ReadBuff.GetBuffer(9);
//                        if (buf != null) {
//                            byte sum = (byte) 0;
//                            for (int i = 0; i < 8; i++) {
//                                sum += buf[i];
//                            }
//                            if (sum == buf[8]) {
//                                cmd_id = TcpPrc.CMD_ID.values()[buf[3]];
//
//                                paramLen = ((int) buf[4] & 0xff) << 24;
//                                paramLen += ((int) buf[5] & 0xff) << 16;
//                                paramLen += ((int) buf[6] & 0xff) << 8;
//                                paramLen += ((int) buf[7] & 0xff) << 0;
//
//                                recStatus = TcpPrc.RecStatus.WAIT_PARAM;
//                            }
//                        }
//                        break;
//
//                    case WAIT_PARAM:
//                        //等待收满
//                        buf = m_ReadBuff.GetBuffer(paramLen + 2);
//                        if (buf != null) {
//                            switch (cmd_id) {
//                                case HEAD_BEAT:
//                                    System.out.println("接收到心跳包");
//                                    m_RecTimeout = 0;
//                                    break;
//
//                                case REGISTERD://注册
//                                    long id;
//                                    id = ((long) buf[0] & 0xff) << 24;
//                                    id += ((long) buf[1] & 0xff) << 16;
//                                    id += ((long) buf[2] & 0xff) << 8;
//                                    id += ((long) buf[3] & 0xff) << 0;
//
//
//                                    /**
//                                     * 解析id
//                                     */
//                                    System.out.println(java.lang.Long.toHexString(id));
//                                    break;
//
//                                case RETURN://文件
//                                    File text = new File(System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/upload/");
//                                    
//                                    if (!text.exists() && !text.isDirectory()) {
//                                        text.mkdir();
//                                    }
//                                    try {
//                                       
//                                        UUID uuid = UUID.randomUUID();
////                                        Date date = new Date();
////                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                                        System.out.println(simpleDateFormat.format(date));
//
//                                        File file1 = new File(text.getAbsolutePath() + text.separatorChar + "test__.xls");
//                                        FileOutputStream fileOutputStream = new FileOutputStream(file1);
//                                        fileOutputStream.write(buf, 0, paramLen);
//                                        fileOutputStream.close();
//                                        System.out.println("com.dao.TcpPrc -- DataPrc.run() --- 写出成功");
//                                        
//                                        int minId = ContrastData.getInstance().CompareData(new ManagerData(),new ParaData());
//                                        System.out.println("com.dao.TcpPrc -- minId:" + minId);
//                                        result = OperateData.INSTANCE.getResult(OperateData.INSTANCE.getDatabasePath(0), minId);
//                                        System.out.println("com.dao.TcpPrc -- result:" + Arrays.toString(result));
//                                        OperateData.INSTANCE.DeleteFiles(System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/upload/");
////
////                                        minId = ContrastData.getInstance().CompareData(new ManagerData(),new ParaData());
////                                        Map<Integer,List> map = OperateData.INSTANCE.LoadData();
////                                        OperateData.INSTANCE.ReadDatabase(new DataInDatabase(),map,minId);
////                                        new Diagnose();
//
//
//                                    } catch(Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                
//                                    break;
//
//                                default:
//                                    break;
//                            }
//                            recStatus = TcpPrc.RecStatus.FIND_HEAD;
//                        }
//                        break;
//
//                    case WAIT_CRC:
//                        break;
//
//                    default:
//                        break;
//                }
//                try {
//                    Thread.sleep(10);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            }
////            System.out.println(m_s.getInetAddress().toString() + " " + m_s.getPort() + " " + "处理线程结束" );
//        }
//
//
//    }
////    public static int getId(){
////        return minId;
////    }
//    /**
//     * 获取设备ID
//     * @return
//     */
//    public int getID(){
//        return  ID;
//    }
//    
//    /**
//     * 微信获取结果（文本）
//     * @return
//     */
//    public static String getResult() {
//    	
//    	isGetResult = !isGetResult;
//    	if(!isGetResult) {
//    		String receipt = result[0];	//处方
//        	String direct = result[1];	//健康指导
//        	
//        	String myResult = receipt +"\n"+ direct;
//        	System.out.println("com.dao.TcpPrc --getResult(): 返回数据："+myResult);
//        	
//        	return myResult;
//    	}else {
//    		return null;
//    	}
//    	
//    	
//    }
//    /**
//     * 微信网页获取结果
//     */
//    public static String[] getResult_html() throws Exception  {
//    	if(result != null){
//    		return result;
//    	}else {
//    		return null;
//    	}
//    }
//}
