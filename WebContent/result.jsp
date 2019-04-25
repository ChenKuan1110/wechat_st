<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dao.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">
	<link rel="stylesheet" href="${path}/_asset/css/style_result.css">
	<title>诊断结果</title>
</head>
<%
	String direction;
	String receipt;
	String imgUrl;
	try{
			String[] myResult = TcpServer.getResult_html();
			direction = myResult[0];
			receipt = myResult[1];
			imgUrl = myResult[2];
			out.print(direction+receipt+imgUrl);
			
	}catch(Exception e){
		direction = "这里是健康指导";
		receipt = "这里是处方";
		imgUrl = "wangzhi";
	}
	
	
%>
<body>
	<div class="wrap">
		<h2>诊断结果</h2>
		<!-- 养生指导 -->
		<div class="box">
			<span class="title">养生指导：</span>
			<textarea name="direct_content" id="direct" class="textarea"><%=direction %></textarea>
		</div>
		<!-- 处方 -->
		<div class="box">
			<span class="title">处方建议：</span>
			<textarea name="receipt_content" id="receipt" class="textarea"><%=receipt %></textarea>
		</div>
		<!-- 图片建议 -->
		<img src="${path}/_asset/images/data/<%=imgUrl %>.jpg" id="img"  alt="指导图片" >
	</div>
	<img src=""  id="bigImg">
	
</body>
<script>
	var wrap = document.getElementsByClassName('wrap')[0],
		img = document.getElementById('img'),
		img_big = document.getElementById('bigImg');

	img.onclick = function(){
		img_big.src = img.src;
		wrap.style.display = 'none';
		document.body.style.backgroundColor = '#000';
	}
	img_big.onclick = function(){
		img_big.src = '';
		wrap.style.display = 'none';
		document.body.style.backgroundColor = '#fff';
	}
</script>
</html>