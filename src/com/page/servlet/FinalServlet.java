/**
 * 扫尾处理
 */
package com.page.servlet;

import com.dao.OperateData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/FinalServlet")
public class FinalServlet extends HttpServlet{
    
	private static final long serialVersionUID = 1L;

	@Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=gbk");
        response.setCharacterEncoding("utf-8");

        String str = request.getParameter("cmd");
        String ManagerPath="";
        if (str.equals("diag")){
            ManagerPath = this.getServletConfig().getServletContext().getRealPath("/")+"//WEB-INF//upload//";
        }else if (str.equals("add")){
            ManagerPath = this.getServletConfig().getServletContext().getRealPath("/")+"//WEB-INF//add//";
        }
        OperateData.INSTANCE.DeleteFiles(ManagerPath);
        response.sendRedirect("/wechat_st/index.jsp");
    }
}
