/**
 * 数据诊断
 */
package com.page.servlet;

import com.dao.ContrastData;
import com.dao.OperateData;
import com.dao.DataInDatabase;
import com.dao.ManagerData;
import com.dao.ParaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
@WebServlet("/DiagnoseServlet")
public class DiagnoseServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=gbk");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		DataInDatabase dataInDatabase = new DataInDatabase();
		ManagerData managerData = new ManagerData();
		ParaData paraData = new ParaData();

		String ManagerPath = this.getServletConfig().getServletContext().getRealPath("/")+"//WEB-INF//upload//";

		File file = new File(ManagerPath);
		String uploadName[] = file.list();

		if (uploadName.length == 0){
			out.println("<script>alert(\"请选择诊断文件\");window.location.href='/wechat_st/index.jsp'</script>;");
		}else {
			int id = ContrastData.getInstance().CompareData(managerData,paraData);
//			if (id == -1){
//				out.println("<script>alert(\"匹配失败\");window.location.href='/wechat_st/index.jsp'</script>;");
//				OperateData.INSTANCE.DeleteFiles(ManagerPath);
//			}else{
			Map<Integer,List> map = OperateData.INSTANCE.LoadData();
			OperateData.INSTANCE.ReadDatabase(dataInDatabase,map,id);
			OperateData.INSTANCE.ReadManager(OperateData.INSTANCE.getManagerPath(0),managerData);

			request.setAttribute("id",id);
			request.setAttribute("Data",dataInDatabase);
			request.setAttribute("M_DATE",managerData);
			request.getRequestDispatcher("/diagnose.jsp").forward(request,response);
//			}
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
}