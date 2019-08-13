<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>激活成功</title>
<link href="${pageContext.request.contextPath }/css/bootstrap.min.css" rel="stylesheet">
<script>
	function forwardShop(){
		location.href="${pageContext.request.contextPath}/index.jsp";
	}
</script>
</head>
<body>
	<div style="width:100%;height:34px;padding:10px;margin-top:30px;font-size:20px">激活成功！</div>
	<hr/>
	<div style="width:800px;height:285px;margin:auto;margin-top:80px;background-color:lightblue;padding:24px">
		<div style="width:100px;height:100px;float:left">
			<img src="${pageContext.request.contextPath }/img/right.png" class="img-circle" width=100% height=100%>
		</div>
		<div style="width:650px;height:76px;font-size:24px;float:left;padding:12px;">恭喜你，激活成功！<br/>
			前往商城进行购物！
		</div>
		<div style="clear:both"></div>
		<div style="margin:auto;margin-top:69px;">
			<div style="width:100%;float:left;text-align:center">
				<button type="button" class="btn btn-success" onclick="forwardShop()">前往商城</button>
			</div>
			<div style="clear:both"></div>
		</div>
	</div>
</body>
</html>