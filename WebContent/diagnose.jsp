<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0">
	<title>诊断结果</title>
	<link rel="stylesheet" href="${path}/_asset/css/style_diagnose.css">
</head>
<body>
	<div class="Box">
		
		<h1>舌像诊断结果</h1>

		<div class="form_box">
			<table class="table" id="database">

				<th colspan="6">查询信息</th>

				<tr>
					<td class="title" colspan="3">姓名</td>
					<td colspan="3">${Data.name}</td>
				</tr>
				<tr>
					<td class="title">RGB_R</td>
					<td>${Data.RGB_R}</td>
					<td class="title">RGB_G</td>
					<td>${Data.RGB_G}</td>
					<td class="title">RGB_B</td>
					<td>${Data.RGB_B}</td>
				</tr>
				<tr>
					<td class="title">HSV_H</td>
					<td>${Data.HSV_H}</td>
					<td class="title">HSV_S</td>
					<td>${Data.HSV_S}</td>
					<td class="title">HSV_V</td>
					<td>${Data.HSV_V}</td>
				</tr>
				<tr>
					<td class="title">LAB_L</td>
					<td>${Data.LAB_L}</td>
					<td class="title">LAB_A</td>
					<td>${Data.LAB_A}</td>
					<td class="title">LAB_B</td>
					<td>${Data.LAB_B}</td>
				</tr>
				<tr>
					<td class="title">舌体颜色</td>
					<td colspan="2">${Data.tongueColor}</td>
					<td class="title">苔色</td>
					<td colspan="2">${Data.coatColor}</td>
				</tr>
				<tr>
					<td class="title">厚薄</td>
					<td >${Data.large}</td>
					<td class="title">润燥</td>
					<td>${Data.moist}</td>
					<td class="title">胖瘦</td>
					<td>${Data.fat}</td>
				</tr>
				<tr>
					<td class="title">齿痕</td>
					<td>${Data.tooth}</td>
					<td class="title">裂纹</td>
					<td>${Data.creak}</td>
					<td class="title">瘀斑</td>
					<td>${Data.bruise}</td>
				</tr>
			</table>

			<table class="table" id="upload">

				<th colspan="6">用户上传信息</th>

				<tr>
					<td class="title" colspan="3">姓名</td>
					<td colspan="3" class="data">${M_DATE.managerName}</td>
				</tr>
				<tr>
					<td class="title">RGB_R</td>
					<td class="data">${M_DATE.RGB_R}</td>
					<td class="title">RGB_G</td>
					<td class="data">${M_DATE.RGB_G}</td>
					<td class="title">RGB_B</td>
					<td class="data">${M_DATE.RGB_B}</td>
				</tr>
				<tr>
					<td class="title">HSV_H</td>
					<td class="data">${M_DATE.HSV_H}</td>
					<td class="title">HSV_S</td>
					<td class="data">${M_DATE.HSV_S}</td>
					<td class="title">HSV_V</td>
					<td class="data">${M_DATE.HSV_V}</td>
				</tr>
				<tr>
					<td class="title">LAB_L</td>
					<td class="data">${M_DATE.LAB_L}</td>
					<td class="title">LAB_A</td>
					<td class="data">${M_DATE.LAB_A}</td>
					<td class="title">LAB_B</td>
					<td class="data">${M_DATE.LAB_B}</td>
				</tr>
				<tr>
					<td class="title">舌体颜色</td>
					<td colspan="2" class="data">${M_DATE.tongue}</td>
					<td class="title">苔色</td>
					<td colspan="2" class="data">${M_DATE.coat}</td>
				</tr>
				<tr>
					<td class="title">厚薄</td>
					<td class="data">${M_DATE.large}</td>
					<td class="title">润燥</td>
					<td class="data">${M_DATE.moist}</td>
					<td class="title">胖瘦</td>
					<td class="data">${M_DATE.fat}</td>
				</tr>
				<tr>
					<td class="title">齿痕</td>
					<td class="data">${M_DATE.tooth}</td>
					<td class="title">裂纹</td>
					<td class="data">${M_DATE.creak}</td>
					<td class="title">瘀斑</td>
					<td class="data">${M_DATE.bruise}</td>
				</tr>
			</table>
		</div>

		<form action="${path}/AddServlet" class="info_form" method="post">
			<div class="back_info">
				<span>养生指导：</span>
				<textarea name="tex1" id="tex1" >${Data.health}</textarea>
				<span>处方：</span>
				<textarea name="tex2" id="tex2" >${Data.recipe}</textarea>
				<img class="back_img" src="${path}/_asset/images/data/${Data.cartoon}.jpg" alt="测试图片">
			</div>
			<input type="hidden" name="id" id="id" value="<%=request.getAttribute("id")%>">
			<input type="submit" value="入库">
		</form>

		<a  class="btn" href="${path}/FinalServlet?cmd=diag">返回主页</a>
	</div>

	<img src="" alt="" class="bigImg" id="bigImg">
</body>
<script>
    var box = document.getElementsByClassName('Box')[0];
    var img = document.getElementsByTagName('img')[0];
    var bigImg = document.getElementById("bigImg");

    img.onclick = function(){
        bigImg.src = img.src;
        bigImg.style.display = "block";
        box.style.display = 'none';
    }
    bigImg.onclick = function(){
        bigImg.src = "";
        bigImg.style.display = 'none';
        box.style.display = 'block';
    }
</script>
</html>