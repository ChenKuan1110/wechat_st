package com.dao;

import com.dao.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/WeChatDiagnoseServlet")
public class WeChatDiagnoseServlet extends HttpServlet {
    private ManagerData managerData;
    private ParaData paraData;
    private static String result;

    @Override
    public void init() throws ServletException {
        managerData = new ManagerData();
        paraData = new ParaData();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=gbk");
        response.setCharacterEncoding("utf-8");
        String path = request.getServletContext().getRealPath("//WEB-INF//wechat//");
        String path1 = request.getServletContext().getRealPath("//WEB-INF//database//");

        File file = new File(path);
        String[] fileName = file.list();
        int minId = 0;

        while (true){
            if (fileName.length == 0){
            	System.out.println("到此一游");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                minId = ContrastData.getInstance().CompareData(managerData,paraData);
                result = OperateData.INSTANCE.getResult(path1, minId).toString();
                System.out.println("com.dao.WeChatDiagnose --- result:"+ result);
                break;
            }
        }
//        request.setAttribute("minId",minId);
//        request.getRequestDispatcher("").forward(request,response);

    }
    
}
