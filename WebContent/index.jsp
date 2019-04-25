<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>舌像数据采集</title>

	<link rel="stylesheet" href="${path}/_asset/css/style_index.css">
	<style>


	</style>
</head>
<body>
<div class="box">
	<h1>舌像数据采集</h1>
	<form class="form" action="${path}/FileUploadServlet" method="post" enctype="multipart/form-data">
		<input type="file" name="file1" id="file1">
		<input type="submit" value="诊断">
	</form>

	<form class="form" action="${path}/AddFileServlet" method="post" enctype="multipart/form-data">
		<input type="file" name="file2" id="file2">
		<input type="submit" value="添加">
	</form>

	<a class='btn' href="javascript:void(0);" onclick="show_form()">导出</a>
</div>
<form action="${path}/CheckServlet" method="post" class="viri_form" onsubmit="close_form()">
	<input id="password" type="password" placeholder="请输入密码" name="text" required >
	<input type="submit" value="提交" >
	<span class="iconfont close_btn" onclick="close_form()">&#xe889;</span>
</form>


</body>
<script>
    var viri_form = document.getElementsByTagName('form')[2],
    	input_pass = document.getElementById("password");
    function show_form(){
        viri_form.style.display = "block";
    }
    var close = document.getElementsByTagName("span")[0];
    function close_form(){
        viri_form.style.display = 'none';
        input_pass.value = null;
    }
    
    /* TODO */
    function download_check(){
    	
    }
    
</script>
</html>