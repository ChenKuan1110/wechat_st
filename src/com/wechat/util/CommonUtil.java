package com.wechat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 工具类
 * @author chenkuan
 *
 */
public class CommonUtil {
	
	public static void main(String[] args) {
		try {
			download("http://mmbiz.qpic.cn/mmbiz_jpg/CSghUDmV4Oz1oquEpGmywOBoicuHU1C004Q7rfkgM8I3t96jSuoia6eFhqqlMxiaEPH7d9OedDu5KibSDicgs9Y7AUg/",
					"test1.jpg",
					"/Users/chenkuan/Desktop/");
			System.out.println("文件保存成功！");
		} catch (Exception e) {
			System.out.println("文件保存失败！");
		}
	}
	

	/**
	 *  下载指定url的内容，保存到指定位置
	 * @param urlString	下载文件的链接地址
	 * @param filename	保存的文件名
	 * @param savePath	保存路径
	 * @throws Exception
	 */
	public static void download(String urlString, String filename,String savePath) throws Exception {
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    //设置请求超时为5s
	    con.setConnectTimeout(5*1000);
	    // 输入流
	    InputStream is = con.getInputStream();
	    
	
	    // 1K的数据缓冲
	    byte[] bs = new byte[1024];
	    // 读取到的数据长度
	    int len;
	    // 输出的文件流
	   File sf=new File(savePath);
	   if(!sf.exists()){
		   sf.mkdirs();
	   }
	   OutputStream os = new FileOutputStream(sf.getPath()+File.separator+filename);
	    // 开始读取
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // 完毕，关闭所有链接
	    os.close();
	    is.close();
	} 

}




