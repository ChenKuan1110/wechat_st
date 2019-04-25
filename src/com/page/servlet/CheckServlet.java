/**
 * 下载验证
 */
package com.page.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String pass = request.getParameter("text");
        System.out.println("com.dao.CheckServlet.service: 接收到从前端发送的密码为："+ pass);
        if (pass.equals("123456")){
            String fileName = "database.xls";
            fileName = new String(fileName.getBytes("ISO8859-1"),"UTF-8");
            String path = this.getServletConfig().getServletContext().getRealPath("/WEB-INF/database/"+fileName);
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            Files.copy(Paths.get(path),response.getOutputStream());
        }else {
            PrintWriter out = response.getWriter();
            out.println("<script>alert(\"密码错误，请核对密码\");window.location.href='/wechat_st/index.jsp'</script>;");
        }
    }
}
