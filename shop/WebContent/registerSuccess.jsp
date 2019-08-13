<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册成功</title>
<link href="${pageContext.request.contextPath }/css/bootstrap.min.css" rel="stylesheet">
<script>
	function backToIndex(){
		location.href="${pageContext.request.contextPath}/index.jsp"
	}
	function forwardMail(){
		location.href="https://mail.${email.split('@')[1]}"
	}
</script>
</head>
<body>
	<div style="width:100%;height:34px;padding:10px;margin-top:30px;font-size:20px">注册成功！</div>
	<hr/>
	<div style="width:800px;height:285px;margin:auto;margin-top:80px;background-color:lightblue;padding:24px">
		<div style="width:100px;height:100px;float:left">
			<img src="${pageContext.request.contextPath }/img/right.png" class="img-circle" width=100% height=100%>
		</div>
		<div style="width:650px;height:76px;font-size:24px;float:left;padding:12px;">恭喜你，注册成功！<br/>
			请使用邮箱<font color=red>${email }</font>激活账号！
		</div>
		<div style="clear:both"></div>
		<div style="margin:auto;margin-top:69px;">
			<div style="width:50%;float:left;text-align:center">
				<button type="button" class="btn btn-danger" onclick="forwardMail()">前往邮箱激活</button>
			</div>
			<div style="width:50%;float:left;text-align:center">
				<button type="button" class="btn btn-success" onclick="backToIndex()">返回首页</button>
			</div>
			<div style="clear:both"></div>
		</div>
	</div>
</body>
</html>