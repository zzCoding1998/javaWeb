<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>支付成功</title>
<link href="${pageContext.request.contextPath }/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div style="width:100%;height:34px;padding:10px;margin-top:30px;font-size:20px">支付成功！</div>
	<hr/>
	<div style="width:800px;margin:auto;margin-top:80px;background-color:lightblue;padding:24px">
		<div style="width:100px;height:100px;float:left">
			<img src="${pageContext.request.contextPath }/img/right.png" class="img-circle" width=100% height=100%>
		</div>
		<div style="width:650px;height:76px;font-size:24px;float:left;padding:12px;">购买成功，等待商家发货！<br/>
			去看看别的商品吧
		</div>
		<div style="clear:both"></div>
		<div style="margin:auto;">
			<div style="width:500px;float:left;padding:50px">
				<p>订单编号：${trade_no }</p>
				<p>交易编号：${out_trade_no }</p>
				<p>交易金额：${total_amount }</p>
				<p>支付结果：${payResult }</p>
			</div>
			<div style="width:113px;float:left;text-align:center;margin-top:95px">
				<button type="button" class="btn btn-danger" onclick="javascript:location.href='${pageContext.request.contextPath}'">返回商城首页</button>
			</div>
			<div style="clear:both"></div>
		</div>
	</div>
</body>
</html>