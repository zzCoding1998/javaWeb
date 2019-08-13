<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/bootstrap.min.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/style.css"
	type="text/css" />

<script type="text/javascript">
	/* 切换验证码图片 */
	$(function(){
		$("#checkImg").click(function(){
			$(this).prop("src","${pageContext.request.contextPath }/checkImg?time="+new Date().getMilliseconds());
		});
	});
	
	//页面加载重新获取验证码
	$(function(){
		$("#checkImg").prop("src","${pageContext.request.contextPath }/checkImg?time="+new Date().getMilliseconds());
	});
	
	/* 性别回显 */
	/* 改这里，在外面定义变量，把验证码搞挂了，原因不明 */
	$(function(){
		if(${user.sex=="male"}){
			$("#sex1").prop("checked","true");
		}else if(${user.sex=="male"}){
			$("#sex2").prop("checked","true");
		}
	});
</script>
<script type="text/javascript">
	
	//自定义用户名存在验证
	$.validator.addMethod("isUsable",function(value,element,param){
		var isExist = false;
		$.ajax({
			async:false,
			url:"${pageContext.request.contextPath}/user?method=isAccountExist",
			data:{"username":value},
			method:"post",
			dataType:"json",
			success:function(data){
				isExist = data;
			}
		});
		return !isExist;
	});


	/* 表单校验 */
	$(function(){
		$("#registerForm").validate({
			rules:{
				username:{
					required:true,
					minlength:2,
					maxlength:16,
					isUsable:true
				},
				password:{
					required:true,
					minlength:6,
					maxlength:16
				},
				repassword:{
					equalTo:"#password"
				},
				email:{
					required:true,
					email:true
				},
				name:{
					required:true,
					minlength:2,
					maxlength:16
				},
				sex:{
					required:true
				},
				birthday:{
					required:true
				},
				checkCode:{
					required:true
				}
			},
			messages:{
				username:{
					required:"用户名不能为空",
					minlength:"用户名不能少于2位",
					maxlength:"用户名不能多于16位",
					isUsable:"该用户名已存在"
				},
				password:{
					required:"密码不能为空",
					minlength:"密码不能少于6位",
					maxlength:"密码不能多于16位"
				},
				repassword:{
					equalTo:"两次密码不一致"
				},
				email:{
					required:"邮箱不能为空",
					email:"请输入正确的邮箱"
				},
				name:{
					required:"姓名不能为空",
					minlength:"姓名不能少于2位",
					maxlength:"姓名不能多于16位"
				},
				sex:{
					required:"请选择性别"
				},
				birthday:{
					required:"请选择生日"
				},
				checkCode:{
					required:"验证码不能为空"
				}
			}
		})
	});
</script>
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
</style>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form class="form-horizontal" style="margin-top: 5px;"
					id="registerForm"
					action="${pageContext.request.contextPath }/user?method=register" method="post">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username"
								name="username" value="${user.username }" placeholder="请输入用户名">
						</div>
						<label for="username" class="error" generated="true"
							style="display: none; color: red"></label> <label class="error"
							style="color: red">${accountExist }</label>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="password"
								name="password" placeholder="请输入密码">
						</div>
						<label for="password" class="error" generated="true"
							style="display: none; color: red"></label>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="repassword"
								name="repassword" placeholder="请输入确认密码">
						</div>
						<label for="repassword" class="error" generated="true"
							style="display: none; color: red"></label>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="email" name="email"
								value="${user.email }" placeholder="Email">
						</div>
						<label for="email" class="error" generated="true"
							style="display: none; color: red"></label>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="name" name="name"
								value="${user.name }" placeholder="请输入姓名">
						</div>
						<label for="name" class="error" generated="true"
							style="display: none; color: red"></label>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								name="sex" id="sex1" value="male"> 男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="sex2" value="fmale"> 女
							</label>
						</div>
						<label for="sex" class="error" generated="true"
							style="display: none; color: red"></label>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday"
								id="birthday" value="${birthday }">
						</div>
						<label for="birthday" class="error" generated="true"
							style="display: none; color: red"></label>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="checkCode"
								id="checkCode">

						</div>
						<div class="col-sm-2">
							<img id="checkImg" />
						</div>
						<span
							style="font-size: 13px; padding-top: 5px; display: block-inline">看不清？点击图片切换</span>&nbsp;&nbsp;&nbsp;&nbsp;
						<label for="checkCode" class="error" generated="true"
							style="display: none; color: red"></label>
						<label class="error" style="color: red">${registerInfo }</label>
					</div>


					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




