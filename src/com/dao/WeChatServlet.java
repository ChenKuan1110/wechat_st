//package com.dao;
//
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@WebServlet("/WeChatServlet")
//public class WeChatServlet extends HttpServlet{
//    public void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException,IOException{
//
//        response.setContentType("text/html; charset=gbk");
//        response.setCharacterEncoding("utf-8");
//        PrintWriter out = response.getWriter();
//        String id = (String) request.getAttribute("id");
//        System.out.println(id);
//
//        String path = request.getServletContext().getRealPath("/WEB-INF/upload/");
//        System.out.println("com.dao.WechatServlet: path:"+ path);
//        
//        ServerListener.getInstance().connected();
//
//        response.sendRedirect("/WeChatDiagnoseServlet");
//
//
//
////        try {
////            /**
////             * 启动连接客户端的线程
////             */
////
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//    }
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doPost(request,response);
//    }
//}
