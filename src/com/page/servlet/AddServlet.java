/**
 * 入库操作
 */
package com.page.servlet;

import com.dao.OperateData;

import com.dao.ManagerData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class AddServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		response.setContentType("text/html; charset=gbk");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String ManagerPath = this.getServletConfig().getServletContext().getRealPath("/")+"//WEB-INF//upload//";
		String AddPath = this.getServletConfig().getServletContext().getRealPath("/")+"//WEB-INF//add//";

		if (request.getParameter("id") == null){
			/**
			 * 添加数据的操作
			 */
			try {
				File file = new File(AddPath);
				String[] fileName = file.list();
				if (fileName.length == 0){
					out.println("<p style=color:red>请不要对同一个文件进行重复添加，请重新选择文件</p>");
				}else {
					ManagerData managerData = new ManagerData();
					String[] data = new String[3];
					data[0] = request.getParameter("recipe");
					data[1] = request.getParameter("health");
					data[2] = "test";

					OperateData.INSTANCE.ReadManager(AddPath+fileName[0],managerData);
					int type = OperateData.INSTANCE.WriteToDatabase(managerData,data);

					if (type != -1){
						out.println("<script>alert(\"添加成功\");window.location.href='/sxcj/index.jsp'</script>;");
					}else{
						out.println("<script>alert(\"添加失败\");window.location.href='/sxcj/index.jsp'</script>;");
					}
					OperateData.INSTANCE.DeleteFiles(AddPath);
				}
			}catch (Exception e){
				out.println("<script>alert(\"添加失败\");window.location.href='/sxcj/index.jsp'</script>;");
				e.printStackTrace();
			}
		}else {
			/**
			 * 诊断后入库的操作
			 */
			int id = Integer.parseInt(request.getParameter("id"));
			try {

				File file = new File(ManagerPath);
				String[] fileName = file.list();
				if (fileName.length == 0){
					out.println("<p style=color:red>请不要对同一个文件进行重复添加，请重新选择文件</p>");
				}else {
					ManagerData managerData = new ManagerData();
					OperateData.INSTANCE.ReadManager(ManagerPath+fileName[0],managerData);
					String[] data = OperateData.INSTANCE.getResult(OperateData.INSTANCE.getDatabasePath(0),id);

					int type = OperateData.INSTANCE.WriteToDatabase(managerData,data);
					if (type != -1){
						out.println("<script>alert(\"添加成功\");window.location.href='/sxcj/index.jsp'</script>;");
					}else{
						out.println("<script>alert(\"添加失败\");window.location.href='/sxcj/index.jsp'</script>;");
					}
					OperateData.INSTANCE.DeleteFiles(ManagerPath);
				}
			}catch (Exception e){
				out.println("<script>alert(\"添加失败\");window.location.href='/wechat_st/index.jsp'</script>;");
				e.printStackTrace();
			}
		}
	}
}