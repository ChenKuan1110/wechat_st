package com.wechat.po;

import java.io.IOException;

import org.apache.http.ParseException;

import com.wechat.util.WeChatUtil;

/**
 *  AccessToken 单列类
 * @author chenkuan
 *
 */
public class SingleAccessToken {
	
	private AccessToken accessToken;
	private static SingleAccessToken singleAccessToken;
	
	private SingleAccessToken(){
		try {
			accessToken = WeChatUtil.getAccessToken();
			
			
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		initThread();
	}
	
	/**
     * 获取SingleAccessToken对象
     * @return
     */
    public static SingleAccessToken getInstance(){
        if(singleAccessToken==null){
            singleAccessToken=new SingleAccessToken();
        }
        return singleAccessToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
    
    
    /**
     * 开启线程，设置SingleAccessToken为空
     */
    private void initThread(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                   //睡眠7000秒
                    Thread.sleep(30*1000);    
                    singleAccessToken=null;

                } catch (InterruptedException e) {
                    
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
