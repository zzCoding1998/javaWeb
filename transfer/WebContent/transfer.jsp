<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>模拟转账操作</title>
</head>
<body>

	<form action="${pageContext.request.contextPath }/transfer" method="post">
		转入账户:<input type="text" name="in"/><br/>
		转出账户:<input type="text" name="out"/><br/>
		转账金额:<input type="text" name="count"/><br/>
		<input type="submit" value="转账"/>
	</form>

</body>
</html>