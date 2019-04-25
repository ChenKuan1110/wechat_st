package com.page.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		 String savePath = this.getServletContext().getRealPath("/WEB-INF/upload/");
		 File file = new File(savePath);
		 if (!file.exists() && !file.isDirectory()) {
			 file.mkdir();
		 }
		 try{
			 DiskFileItemFactory factory = new DiskFileItemFactory();
			 ServletFileUpload upload = new ServletFileUpload(factory);
			 upload.setHeaderEncoding("UTF-8");
			 if(!ServletFileUpload.isMultipartContent(request)){
				 return;
			 }
			 List<FileItem> list = upload.parseRequest(request);
			 for(FileItem item : list){
				 if(item.isFormField()){

				 }else{
					 String filename = item.getName();
					 if(filename==null || filename.trim().equals("")){
						 continue;
					 }
					 filename = filename.substring(filename.lastIndexOf("/")+1);
					 InputStream in = item.getInputStream();
					 FileOutputStream out = new FileOutputStream(savePath + "/" + filename);

					 byte buffer[] = new byte[1024];
					 int len = 0;
					 while((len=in.read(buffer))>0){
						 out.write(buffer, 0, len);
					 }
					 in.close();
					 out.close();
					 item.delete();
				 }
			 }
		 }catch (Exception e) {
			 e.printStackTrace();
		 }
		 request.getRequestDispatcher("/DiagnoseServlet").forward(request, response);
	 }
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	 	doPost(request,response);
	}
}
