<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>

<script type="text/javascript">
	function isFormCurrent(){
		var flag=3;
		//收货地址
		if($("#address").val()==""){
			$("#addressLabel").text("请填写收货地址！");
			flag -= 1;
		}else{
			$("#addressLabel").text("");
		}
		//收件人
		if($("#name").val()==""){
			$("#nameLabel").text("请填写收件人姓名！");
			flag -= 1;
		}else{
			$("#nameLabel").text("");
		}
		//联系方式
		if($("#telephone").val()==""){
			$("#telephoneLabel").text("请填写联系方式！");
			flag -= 1;
		}else{
			$("#telephoneLabel").text("");
		}
		if(flag==3){
			$("#orderForm").submit();
		}
	}
</script>

</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>
	
	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong>订单详情</strong>
				<table class="table table-bordered">
					<tbody>
						<tr class="warning">
							<th colspan="5">订单编号:${orders.oid }</th>
						</tr>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<c:forEach items="${orders.orderItemList }" var="orderItem">
							<tr class="active">
							<td width="60" width="40%">
							<input type="hidden" name="id" value=${orderItem.itemid }> 
							<img src="${pageContext.request.contextPath }/${orderItem.product.pimage }" width="70" height="60"></td>
							<td width="30%"><a target="_blank">${orderItem.product.pname }</a></td>
							<td width="20%">￥${orderItem.product.shop_price }</td>
							<td width="10%">${orderItem.count }</td>
							<td width="15%"><span class="subtotal">￥${orderItem.subtotal }</span></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div style="text-align: right; margin-right: 120px;">
				商品金额: <strong style="color: #ff6600;">￥${orders.total }元</strong>
			</div>

		</div>

		<div>
			<hr />
			<form class="form-horizontal" id="orderForm" method="post" action="${pageContext.request.contextPath }/order?method=orderConfirm">
				<div class="form-group">
					<label for="username" class="col-sm-1 control-label">地址</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="address" name="address"
							placeholder="请输入收货地址">
					</div>
					<label class="error" id="addressLabel" style="color: red">${addressInfo }</label>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-1 control-label">收货人</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="name" name="name"
							placeholder="请输收货人">
					</div>
					<label class="error" id="nameLabel" style="color: red">${nameInfo }</label>
				</div>
				<div class="form-group">
					<label for="confirmpwd" class="col-sm-1 control-label">电话</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="telephone" name="telephone"
							placeholder="请输入联系方式">
					</div>
					<label class="error" id="telephoneLabel" style="color: red">${telephoneInfo }</label>
				</div>
			</form>

			<hr />

			<div style="margin-top: 5px; margin-left: 150px;">
				<strong>选择支付方式：</strong>
				<p>
					<input type="radio" name="payment" value="alipay" checked="checked"/>支付宝线上支付
					<img src="${pageContext.request.contextPath }/img/alipay.jpg" width=229px height=68px align="middle" />
				</p>
				<hr />
				<p style="text-align: right; margin-right: 100px;">
					<a onclick="isFormCurrent()">
						<img src="./images/finalbutton.gif" width="204" height="51"
						border="0" />
					</a>
				</p>
				<hr />

			</div>
		</div>

	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>