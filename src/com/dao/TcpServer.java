package com.dao;

import com.dao.ContrastData;
import com.dao.OperateData;
import com.dao.ManagerData;
import com.dao.ParaData;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class TcpServer extends Thread{
    private Socket socket;
    private boolean isConected;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private BufferManager readBuffer;
    private BufferManager writeBuffer;

    private DataPrc dataPrc;
    private TcpWriteData tcpWriteData;
    private TcpReadData tcpReadData;
    public String USER_ID;
    
    private static String[] result = null;
    public static boolean isGetResult = false;	//是否获取过结果的标志	


    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    /**
     * 命令状态
     */
    private enum RecStatus{
        FIND_HEAD,
        WAIT_CHECK,
        WAIT_PARAM,
        WAIT_CRC
    }
    private enum CMD_ID{
        NULL,
        HEAD_BEAT,
        REGISTERD,
        CONFIG_INFO,
        RETURN,
        RUNNING
    }

    /**
     * 构造方法
     * @param socket 保持与客户端通讯的socket
     */
    public TcpServer(Socket socket){
        this.socket = socket;
        isConected = true;
        readBuffer = new BufferManager(1024 * 1024);
        writeBuffer = new BufferManager(1024 * 1024);
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主线程的run方法
     */
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new HeartBeat(),1,5000);

        tcpWriteData = new TcpWriteData();
        tcpReadData = new TcpReadData();
        dataPrc = new DataPrc();

        tcpWriteData.start();
        tcpReadData.start();
        dataPrc.start();

    }

    public static byte[] linkBytes(byte command){
        byte[] bytes = new byte[20];

        bytes[0] = (byte) 0xa5;
        bytes[1] = (byte) 0xa5;

        bytes[2] = (byte) 0x02;
        bytes[3] = command;//0x05设备启动

        bytes[4] = (byte) 0x00;
        bytes[5] = (byte) 0x00;
        bytes[6] = (byte) 0x00;
        bytes[7] = (byte) 0x00;

        byte sum = (byte)0;
        for (int i = 0 ; i < 8 ; i++){
            sum += bytes[i];
        }
        bytes[8] = sum;

        bytes[9] = (byte) 0xff;
        bytes[10] = (byte) 0xff;

        return bytes;
    }
    /**
     * 设备启动,微信端判断合法调用
     */
    public void startNow(){
        writeBuffer.PushBuffer(linkBytes((byte) 0x05), 11);
    }
    /**
     * 心跳类
     */
    class HeartBeat extends TimerTask{
        public void run() {
            try {
                writeBuffer.PushBuffer(linkBytes((byte) 0x01), 11);
            }catch (Exception e){
                isConected = false;
                System.out.println("设备掉线,删除设备--》tcpServer.HeartBeat");

                e.printStackTrace();
            }
        }
    }
    /**
     * 业务处理线程
     */
    private class DataPrc extends Thread {
        public void run() {
            TcpServer.RecStatus recStatus = TcpServer.RecStatus.FIND_HEAD;
            TcpServer.CMD_ID cmd_id = TcpServer.CMD_ID.HEAD_BEAT;
            int paramLen = 0;
            byte[] buf;
            //如果心跳包的标志位为true，也就是设备还处于连接中，那么才执行下面的程序
            while(isConected) {
                switch (recStatus) {
                    case FIND_HEAD:
                        //从接收buffer里查找0XA5 0XA5
                        if (readBuffer.FindHead()) {
                            recStatus = TcpServer.RecStatus.WAIT_CHECK;
                        }
                        break;

                    case WAIT_CHECK:
                        //等待收满9个字节，并检验累加和
                        buf = readBuffer.GetBuffer(9);
                        if (buf != null) {
                            byte sum = (byte) 0;
                            for (int i = 0; i < 8; i++) {
                                sum += buf[i];
                            }
                            if (sum == buf[8]) {
                                cmd_id = TcpServer.CMD_ID.values()[buf[3]];

                                paramLen = ((int) buf[4] & 0xff) << 24;
                                paramLen += ((int) buf[5] & 0xff) << 16;
                                paramLen += ((int) buf[6] & 0xff) << 8;
                                paramLen += ((int) buf[7] & 0xff) << 0;

                                recStatus = TcpServer.RecStatus.WAIT_PARAM;
                            }
                        }
                        break;

                    case WAIT_PARAM:
                        //等待收满
                        buf = readBuffer.GetBuffer(paramLen + 2);
                        if (buf != null) {
                            switch (cmd_id) {
                                case HEAD_BEAT:
                                    System.out.println("接收到心跳包--》tcpServer.DataPrc");
//                                    m_RecTimeout = 0;
                                    break;

                                case REGISTERD://注册
                                    /**
                                     * 解析id
                                     */
                                    long userId;
                                    userId = ((long) buf[0] & 0xff) << 24;
                                    userId += ((long) buf[1] & 0xff) << 16;
                                    userId += ((long) buf[2] & 0xff) << 8;
                                    userId += ((long) buf[3] & 0xff) << 0;

                                    System.out.println("设备编号id--》tcpServer.DataPrc："+ Long.toHexString(userId));
                                    //获取集合中已经存在的设备连接
                                    String idInMap = ManageTread.getOnLineUser();
                                    System.out.println("在线设备字符串--》tcpServer.DataPrc："+ idInMap);

                                    if ("".equals(idInMap)){
                                        System.out.println("第一台设备开始对设备id初始化");
                                        setUSER_ID(Long.toHexString(userId));
                                    }else {
                                        System.out.println("开始对设备id初始化");
                                        //返回所有连接的id拼接字符串idInMap 以空格分割
                                        String[] ids = idInMap.split(" ");
                                        System.out.println("在线设备数量--》tcpServer.DataPrc："+ Arrays.toString(ids));
                                        //对新注册的id进行判断是否已经存在连接
                                        if (!Arrays.asList(ids).contains(Long.toHexString(userId))){
                                            //集合中没有这个id
                                            setUSER_ID(Long.toHexString(userId));
                                        }else {
                                            System.out.println("注册设备已经存在--》tcpServer.DataPrc");
                                            isConected = false;
                                            return;
                                        }
                                    }

                                    break;

                                case RETURN://文件
                                    File text = new File(System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/wechat/");
                                    if (!text.exists() && !text.isDirectory()) {
                                        text.mkdir();
                                    }
                                   
                                    
                                    try {
                                        System.out.println("文件写出到服务器--》tcpServer.DataPrc");
//                                        UUID uuid = UUID.randomUUID();

                                        File file1 = new File(text.getAbsolutePath() + text.separatorChar +"test__.csv");
                                        FileOutputStream fileOutputStream = new FileOutputStream(file1);
                                        fileOutputStream.write(buf, 0, paramLen);
                                        fileOutputStream.close();
                                        
                                        if(OperateData.INSTANCE.operateCsv(file1, new File(System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/upload/test.xls"))) {
                                        	System.out.println("TcpSever-->csv文件成功转换xls");
                                        	OperateData.INSTANCE.DeleteFiles(System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/wechat/");
                                        	int minId = ContrastData.getInstance().CompareData(new ManagerData(),new ParaData());
                                            System.out.println("com.dao.TcpServlet -- minId:" + minId);
                                            result = OperateData.INSTANCE.getResult(OperateData.INSTANCE.getDatabasePath(0), minId-1);
                                            System.out.println("com.dao.TcpServlet --诊断 result:" + Arrays.toString(result));
                                            OperateData.INSTANCE.DeleteFiles(System.getProperty("catalina.home")+"/webapps/wechat_st/WEB-INF/upload/");
                                            System.out.println("TcpSever-->转换xls文件诊断成功");
                                        }else {
                                        	//诊断失败
                                        	result = new String[] {"诊断失败"};
                                        }
                                        
                                    } catch(Exception e) {
                                        e.printStackTrace();
                                    }

                                    break;

                                default:
                                    break;
                            }
                            recStatus = TcpServer.RecStatus.FIND_HEAD;
                        }
                        break;

                    case WAIT_CRC:
                        break;

                    default:
                        break;
                }
                try {
                    Thread.sleep(10);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 将接收到的数据向事先开辟好的buffer（1M）中写入
     */
    private class TcpWriteData extends Thread {
        public void run() {
            byte[] buf;
            while(isConected) {
                try {
                    buf = writeBuffer.GetBuffer();
                    if(buf != null) {
                        dataOutputStream.write(buf, 0, buf.length);
                    }
                    Thread.sleep(10);
                } catch(Exception e) {
                    isConected = false;

                    //删除设备
                    ManageTread.deleteUser(getUSER_ID());
//                    e.printStackTrace();
                    try {
                        if (dataOutputStream!=null){
                            dataOutputStream.close();
                        }
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
    /**
     * 在事先开辟好的buffer（1M）中读取数据
     * 读取速度1024 * 4
     */
    private class TcpReadData extends Thread {
        public void run() {
            byte[] buf = new byte[1024 * 4];
            while(isConected) {
                try {
                    int readSize, bufSize;
                    bufSize = readBuffer.GetSize();
                    if(bufSize > 0){
                        //确保输入流中有数据
                        if (dataInputStream.available()!=0){
                            if(bufSize < 4096) {
                                readSize = dataInputStream.read(buf, 0, bufSize);
                            }
                            else {
                                readSize = dataInputStream.read(buf, 0, 4096);
                            }
                            if(readSize > 0) {
                                readBuffer.PushBuffer(buf, readSize);
                            }
                        }
                    }
                    Thread.sleep(10);
                } catch(Exception e) {
                    isConected = false;
                    e.printStackTrace();
                    try {
                        if (dataInputStream!=null){
                            dataInputStream.close();
                        }
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
    /**
     * 微信获取结果（文本）
     * @return
     */
    public static String getResult() {
    	
    	
    	if(result!=null) {
    		String receipt = result[0];	//处方
        	String direct = result[1];	//健康指导
        	
        	String myResult = receipt +"\n"+ direct;
        	System.out.println("com.dao.TcpServer --getResult(): 返回数据："+myResult);
        	
        	return myResult;
    	}
    	return null;
    	
    }
    /**
     * 微信网页获取结果
     */
    public static String[] getResult_html() throws Exception  {
    	if(result != null){
    		return result;
    	}else {
    		throw new Exception("没有获取到结果");
    	}
    }
    /**
     * 将存储微信端返回结果置为空
     * 		此方法在微信发送验证码之后调用
     */
	 public static void setResultNull() {
	    	
	    	result = null;
	    }
}