package com.wechat.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.wechat.po.AccessToken;
import com.wechat.po.menu.Button;
import com.wechat.po.menu.*;

import net.sf.json.JSONObject;

/**
 * 微信工具类——解析、组装Json、调用微信各种接口
 * @author chenkuan
 *
 */
public class WeChatUtil {
	//公众号基本信息——appid、appsecret
//	private static final String APPID = "wxc331578ee40046ba";
//	private static final String APPSECRET = "4f7234f604b6e71207b475f9318e5d7d";
	private static final String APPID = "wxe612c154760180a2";
	private static final String APPSECRET = "74d40fc384f246d4220d4eda53b9c952";
	
	//获取AccessToken的接口
	private static final String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&"
			+ "appid=APPID&"
			+ "secret=APPSECRET";
	//文件上传接口--临时素材
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?"
			+ "access_token=ACCESS_TOKEN"
			+ "&type=TYPE";
	
	//创建菜单接口
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?"
			+ "access_token=ACCESS_TOKEN";
	//查询菜单接口
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?"
			+ "access_token=ACCESS_TOKEN";
	//删除菜单接口
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?"
			+ "access_token=ACCESS_TOKEN";
	
	
	/**
	 * get请求
	 * @param url  接口地址
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	
	/**
	 * POST请求
	 * @param url 
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	
	/**
	 * 文件上传
	 * @param filePath 文件路径
	 * @param accessToken	全局票据	
	 * @param type			文件类型	
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	/**
	 * 获取AccessToken
	 * @return	AccessToken对象
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException {
		AccessToken accessToken = new AccessToken();
		String url = ACCESSTOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null) {
			accessToken.setAccess_token(jsonObject.getString("access_token"));
			accessToken.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return accessToken;
	}
	
	
	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();
		
		ClickButton button11 = new ClickButton();
		button11.setName("刷新/查看结果");
		button11.setType("click");
		button11.setKey("click");
		
		ClickButton button12 = new ClickButton();
		button12.setName("操作指引");
		button12.setType("click");
		button12.setKey("operate");
		
		Button button1 = new Button();
		button1.setName("舌象检查");
		button1.setSub_button(new Button[] {button11,button12});
		
		ViewButton button2 = new ViewButton();
		button2.setName("view菜单");
		button2.setType("view");
		button2.setUrl("http://www.yunbaozhai.net/wechat_st/demo.jsp");
		
		ClickButton button31 = new ClickButton();
		button31.setName("点击扫码");
		button31.setKey("31");
		button31.setType("scancode_push");
		
		
		
		ClickButton button32 = new ClickButton();
		button32.setName("扫码_等待");
		button32.setType("scancode_waitmsg");
		button32.setKey("33");
		
		
		Button button3 = new Button();
		button3.setName("菜单");
		button3.setSub_button(new Button[] {button31,button32});
		
		menu.setButton(new Button[] {button1,button2,button3});
		
		
		
		
		
		return menu;
	}
	
	/**
	 * 创建菜单
	 * @param token 票据
	 * @param menu 菜单
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int createMenu(String token,String menu) throws ParseException, IOException {
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		
		return result;
		
	}
	/**
	 * 查询菜单
	 * @param token	票据
	 * @return 菜单对象的json字符串 
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	/**
	 * 删除菜单
	 * @param token 票据
	 * @return	
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int deleteMenu(String token) throws ParseException, IOException{
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		int result = 0;
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	
	
	
	
	
	
	
}
