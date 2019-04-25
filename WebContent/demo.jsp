<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name='viewport' content="width=device-width">
	<title>网页内容</title>
</head>
<style type="text/css">
	.wrap{
		width:96%;
		margin:3%;
		text-align: center;
	}
	.wrap img{
		width:100%;
	}	
</style>
<body>
	<div class="wrap">
		<img src="${path}/_asset/images/wangzhi.png">
	</div>
</body>
</html>