<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>

<script type="text/javascript">

	$(function(){
		/* 鼠标聚焦显示搜索框 */
		$("#search").focus(function(){
			$("#searchDiv").show();
		});
		/* 鼠标离焦隐藏搜索框 */
		$("#search").blur(function(){
			$("#searchDiv").hide();
		});
		/* 输入文字进行异步加载数据(搜索相关商品显示在框内) */
		$("#search").keyup(function(){
			var info = $("#search").val();
			$.ajax({
				url:"${pageContext.request.contextPath}/searchProduct",
				type:"post",
				data:{"info":info},
				dataType:"json",
				success:function(data){
					$("#searchDiv").empty();
					for(var i=0;i<data.length;i++){
						$("#searchDiv").append("<div style='font-size:13px;cursor:pointer;padding:8px'>"+ data[i].pname +"</div>");
						$("#searchDiv div").hover(
								function(){$(this).css("background-color","#ccc");},
								function(){$(this).css("background-color","white");})
					}
				}
			});
		});
	});

</script>

<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<li><a href="login.jsp">登录</a></li>
			<li><a href="register.jsp">注册</a></li>
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="order_list.jsp">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="product_list.htm">手机数码<span class="sr-only">(current)</span></a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li>
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search" id="search">
						<div style="position:absolute;z-index:1000;width:173.267px;border:1px solid grey;background:white;display:none;padding:0px" id="searchDiv"></div>
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
	</nav>
</div>