<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <title>添加数据</title>
    <link rel="stylesheet" href="${path}/_asset/css/style_showdata.css">
</head>

<body>
    <form action="${path}/AddServlet" method="post">
        <h1>舌像数据核对及添加</h1>
        <div class="row">
            <span class="key">姓名:</span><input type="text" value="${M_DATE.managerName}">
        </div>
        
        <div class="row">
            <span class="key">RGB_R:</span><input type="text" value="${M_DATE.RGB_R}">
            <span class="key">RGB_G:</span><input type="text" value="${M_DATE.RGB_G}">
            <span class="key">RGB_B:</span><input type="text" value="${M_DATE.RGB_B}">
        </div>
        <div class="row">
            <span class="key">HSB_H:</span><input type="text" value="${M_DATE.HSV_H}">
            <span class="key">HSB_S:</span><input type="text" value="${M_DATE.HSV_S}">
            <span class="key">HSB_V:</span><input type="text" value="${M_DATE.HSV_V}">
        </div>
        <div class="row">
            <span class="key">LAB_L:</span><input type="text" value="${M_DATE.LAB_L}">
            <span class="key">LAB_A:</span><input type="text" value="${M_DATE.LAB_A}">
            <span class="key">LAB_B:</span><input type="text" value="${M_DATE.LAB_B}">
        </div>
        <div class="row">
            <span class="key">舌体颜色:</span><input type="text" value="${M_DATE.tongue}">
            <span class="key">苔色:</span><input type="text" value="${M_DATE.coat}">
        </div>
        <div class="row">
            <span class="key">厚薄:</span><input type="text" value="${M_DATE.large}">
            <span class="key">润燥:</span><input type="text" value="${M_DATE.moist}">
            <span class="key">肥胖:</span><input type="text" value="${M_DATE.fat}">
        </div>
        <div class="row">
            <span class="key">齿痕:</span><input type="text" value="${M_DATE.tooth}">
            <span class="key">裂纹:</span><input type="text" value="${M_DATE.creak}">
            <span class="key">瘀斑:</span><input type="text" value="${M_DATE.bruise}">
        </div>

        <div class="recept">
            <span>养生执导:</span><br><textarea name="recipe" cols="30" rows="10"></textarea>
        </div>
        <div class="reduct">
            <span>处方:</span><br><textarea name="health" cols="30" rows="10"></textarea>
        </div>
        <input class="btn" type="submit" value="上传数据">
        <a class="btn" href="${path}/FinalServlet?cmd=add">返回主页</a>
    </form>
</body>
</html>