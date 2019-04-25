/**
 * 数据展示
 */
package com.page.servlet;

import com.dao.OperateData;
import java.io.IOException;
import java.io.PrintWriter;

import com.dao.ManagerData;

import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ShowServlet")
public class ShowServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=gbk");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		ManagerData managerData = new ManagerData();
		String readpaths=this.getServletConfig().getServletContext().getRealPath("/")+"//WEB-INF//add//";

		try {
			File file1 = new File(readpaths);
			String readName[] = file1.list();
			if (readName.length==0)
			{
				out.println("<script>alert(\"请选择添加文件\");window.location.href='index.jsp'</script>;");
			} else {
				String readpath=readpaths+readName[0];
				OperateData.INSTANCE.ReadManager(readpath,managerData);
				request.setAttribute("M_DATE",managerData);
				request.getRequestDispatcher("/showdata.jsp").forward(request,response);
				return;
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
					doPost(request,response);
	}
}