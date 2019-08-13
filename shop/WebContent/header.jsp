<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- 引入jquery（静态包含导致多次引入jquery，引起calidate插件失效，注掉！） -->
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script> --%>
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
			<c:if test="${!empty sessionScope.user }">
				<li>${user.username }先生/女士，欢迎您！</li>
				<li><a href="${pageContext.request.contextPath }/user?method=logout">退出</a></li>
			</c:if>
			<c:if test="${empty sessionScope.user }">
				<li><a href="login.jsp">登录</a></li>
				<li><a href="register.jsp">注册</a></li>
			</c:if>
			<li><a href="${pageContext.request.contextPath }/cart.jsp">购物车</a></li>
			<li><a href="${pageContext.request.contextPath }/order?method=getMyOrder">我的订单</a></li>
		</ol>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		/* 搜索框点击显示搜索结果 */
		$("#searchInput").focus(function(){
			$("#searchShow").css("visibility","visible");
		});
		
		/* 搜索框离焦，撤去搜索结果 */
		$("#searchInput").blur(function(){
			$("#searchShow").css("visibility","none");
		});
		
		/* keydown事件触发异步加载，从后台获取数据，展示在div中 */
		$("#searchInput").keyup(function(){
			var keywords = $("#searchInput").val();
			/* 当搜索框内没有字符，关闭展示框 */
			if(keywords==""){
				$("#searchShow").html("");
				return;
			}
			$.ajax({
				async:true,
				url:"${pageContext.request.contextPath}/product",
				data:{"method":"searchProductByKeywords","keywords":keywords},
				type:"post",
				dataType:"json",
				success:function(data){
					var content = "";
					for(var i=0;i<data.length;i++){
						var pid = data[i].pid;
						var pname = data[i].pname;
						content += "<div style='margin:7px' onmouseover='changeGrey(this)' onmouseout='changeWhite(this)'><a style='text-decoration:none;' href='${pageContext.request.contextPath}/product?method=productInfoByPid&pid="+pid+"'>"+pname+"</a></div>";
					}
					$("#searchShow").html(content);
				}
			});
		});
	});
	/* 展示项颜色控制 */
	/* 当鼠标移到该项，该项变成灰色 */
	function changeGrey(obj){
		$(obj).css("background-color","#E0E0E0");
	}
	/* 当鼠标从该项移出，该项变成白色 */
	function changeWhite(obj){
		$(obj).css("background-color","white");
	}
	
</script>

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
				<a class="navbar-brand" href="${pageContext.request.contextPath }">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryUl">
					<!-- <li class="active"><a href="product_list.htm">手机数码<span class="sr-only">(current)</span></a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li> -->
				</ul>
				<form class="navbar-form navbar-right" role="search" method="post" action="${pageContext.request.contextPath }/product?method=searchAllProductByKeywords">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search" id="searchInput" name="keywords">
						<div style="width:174px;position:absolute;background-color:white;z-index:100;visibility:visible" id="searchShow"></div>
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
	</nav>
</div>

<!-- 查询所有分类 -->
<script type="text/javascript">
	$(function(){
		$.ajax({
			async:false,
			url:"${pageContext.request.contextPath}/product?method=findAllCategory",
			type:"post",
			data:{},
			dataType:"json",
			success:function(data){
				var content = "";
				for(var i=0;i<data.length;i++){
					var id = data[i].cid;
					content += "<li id="+id+"><a href='${pageContext.request.contextPath}/product?method=productListByCid&cid="+ data[i].cid+ "'>"+ data[i].cname +"</a></li>";
				}
				$("#categoryUl").html(content);
			}
		});
	});
</script>