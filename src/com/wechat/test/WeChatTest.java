package com.wechat.test;

import java.io.IOException;

import org.apache.http.ParseException;

import com.wechat.po.AccessToken;
import com.wechat.po.SingleAccessToken;
import com.wechat.util.WeChatUtil;

import net.sf.json.JSONObject;

/**
 * 测试类 --测试微信公众号接口
 * @author chenkuan
 *
 */
public class WeChatTest {
	public static void main(String[] args) {
		
		try {
			//获取AccessToken
			AccessToken access_token = WeChatUtil.getAccessToken();
			String token = access_token.getAccess_token();
			int time = access_token.getExpires_in();
			System.out.println("票据："+ token);
			System.out.println("有效时间："+ time);
			
//			deleteMenu(token);
			createMenu(token);
//			queryMenu(token);
			
			
			
		} catch (ParseException e) {
			System.err.println("解析json格式错误！");
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * 创建菜单
	 * @param token
	 */
	public static void createMenu(String token) {
		String menu = JSONObject.fromObject(WeChatUtil.initMenu()).toString();
		
		System.out.println("创建菜单的json字符串："+menu);
		
		int result = 0;
		try {
			result = WeChatUtil.createMenu(token,menu );
		} catch (ParseException | IOException e) {
			
			e.printStackTrace();
		}
		if(result == 0) {
			System.out.println("创建菜单成功！");
		}else {
			System.out.println("创建菜单失败！");
			System.out.println("错误码："+result);
		}
	}
	
	/**
	 * 删除菜单
	 * @param token
	 */
	public static void deleteMenu(String token) {
		int result = 0;
		try {
			result = WeChatUtil.deleteMenu(token);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		if(result == 0) {
			System.out.println("删除菜单成功！");
		}
	}
	
	/**
	 * 查询菜单
	 * @param token
	 */
	public static void queryMenu(String token) {
		JSONObject q_menu = null;
		try {
			q_menu = WeChatUtil.queryMenu(token);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(q_menu != null) {
			System.out.println(q_menu.toString());
		}
		
		System.out.println(SingleAccessToken.getInstance().getAccessToken().getAccess_token());
	}
}
