package com.dao;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 主线程
 * 监听等放到其他的独立线程处理
 * ServerListener
 */
public class MyServerSocket extends ServerSocket{

	public MyServerSocket() throws IOException {
		super();
	}
   
}
