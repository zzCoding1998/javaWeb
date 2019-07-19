<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
			function addProduct(){
				window.location.href = "${pageContext.request.contextPath}/getCategory";
			}
			function delProduct(count){
				if(confirm("确定要删除该信息？")){
					var pid = document.getElementById("pid"+count).value;
					window.location.href = "${pageContext.request.contextPath}/delProduct?pid="+pid;
				}
			}
			$(function(){
				$("#pname").val("${condition.pname}");
				$("#isHot option[value='${condition.is_hot}']").prop("selected","true");
				$("#cid option[value='${condition.cid}']").prop("selected","true");
			});
	</script>
</HEAD>
<body>
	<br>
	<form action="${pageContext.request.contextPath }/adminSearchProduct" method="post"><b>
		&nbsp;&nbsp;商品名称：<input type="text" id="pname" name="pname"  />&nbsp;&nbsp;
		是否热门：<select name="is_hot" id="isHot">
					<option value="">不限</option>
					<option value="1">是</option>
					<option value="0">否</option>
				</select>&nbsp;&nbsp;
		商品类别：<select name="cid" id="cid">
					<option value="">不限</option>
					<c:forEach items="${categoryList }" var="category">
						<option value="${category.cid }">${category.cname }</option>
					</c:forEach>
				</select>&nbsp;&nbsp;
		<input type="submit" value="搜索" /></b>
	</form>
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/user/list.jsp"
		method="post">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
						<button type="button" id="add" name="add" value="添加"
							class="button_add" onclick="addProduct()">
							&#28155;&#21152;</button>

					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

								<td align="center" width="18%">序号</td>
								<td align="center" width="17%">商品图片</td>
								<td align="center" width="17%">商品名称</td>
								<td align="center" width="17%">商品价格</td>
								<td align="center" width="17%">是否热门</td>
								<td width="7%" align="center">编辑</td>
								<td width="7%" align="center">删除</td>
							</tr>
							<c:forEach items="${pageInfo.currentList }" var="product" varStatus="vs" >
								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${vs.count }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%"><img width="40" height="45" src="${pageContext.request.contextPath }/${product.pimage }"></td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${product.pname }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${product.shop_price }</td>
									<c:if test="${product.is_hot==1 }">
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">是</td>
									</c:if>	
									<c:if test="${product.is_hot==0 }">
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">否</td>
									</c:if>		
									<td align="center" style="HEIGHT: 22px"><a
										href="${ pageContext.request.contextPath }/editProductUI?pid=${product.pid}">
											<img
											src="${pageContext.request.contextPath}/images/i_edit.gif"
											border="0" style="CURSOR: hand">
									</a></td>
	
									<td align="center" style="HEIGHT: 22px"><a href="#"> <img
											src="${pageContext.request.contextPath}/images/i_del.gif"
											width="16" height="16" border="0" style="CURSOR: hand" onclick="delProduct(${vs.count })">
									</a></td>
								</tr>
								<input type="hidden" name="pid" id="pid${vs.count }" value="${product.pid }" />
							</c:forEach>
						</table>
					</td>
				</tr>

			</TBODY>
		</table>
	</form>
	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			<!-- 上一页 -->
			<c:if test="${pageInfo.currentPage==1 }">
				<li class="disabled">
					<a href="javascript:void(0)" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
			</c:if>
			<c:if test="${pageInfo.currentPage!=1 }">
				<li>
					<a href="${pageContext.request.contextPath }/adminSearchProduct?currentPage=${pageInfo.currentPage-1}&pname=${condition.pname}&is_hot=${condition.is_hot}&cid=${condition.cid}" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
			</c:if>
			<!-- 数字方格 -->
			<c:forEach begin="1" end="${pageInfo.totalPage }" var="pageNum">
				<c:if test="${pageInfo.currentPage==pageNum }">
					<li class="active"><a href="javascript:void(0)">${pageNum }</a></li>
				</c:if>
				<c:if test="${pageInfo.currentPage!=pageNum }">
					<li><a href="${pageContext.request.contextPath }/adminSearchProduct?currentPage=${pageNum}&pname=${condition.pname}&is_hot=${condition.is_hot}&cid=${condition.cid}">${pageNum }</a></li>
				</c:if>
			</c:forEach>
			<!-- 下一页 -->
			<c:if test="${pageInfo.currentPage==pageInfo.totalPage }">
				<li class="disabled">
					<a href="javascript:void(0)" aria-label="Next"> 
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:if>
			<c:if test="${pageInfo.currentPage!=pageInfo.totalPage }">
				<li>
					<a href="${pageContext.request.contextPath }/adminSearchProduct?currentPage=${pageInfo.currentPage+1 }&pname=${condition.pname}&is_hot=${condition.is_hot}&cid=${condition.cid}" aria-label="Next"> 
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:if>
		</ul>
	</div>
	<!-- 分页结束 -->
</body>
</HTML>

