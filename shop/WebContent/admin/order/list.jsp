<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/Style1.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script language="javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<script>
	function showModal(oid){
		//模态框异步加载订单数据
		$.ajax({
			async:true,
			url:"${pageContext.request.contextPath}/admin?method=getOrderInfoByOid",
			type:"post",
			data:{"oid":oid},
			dataType:"json",
			success:function(data){
				/* 清空table */
				var tableHead = "";
				tableHead += "<tr style='FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3'>";
				tableHead += "<td align='center' width='17%'>图片</td>";
				tableHead += "<td align='center' width='17%'>商品</td>";
				tableHead += "<td align='center' width='17%'>价格</td>";
				tableHead += "<td align='center' width='17%'>数量</td>";
				tableHead += "<td align='center' width='17%'>小计</td>";
				tableHead += "</tr>";
				$("#orderDiv~table").html(tableHead);
				/* 写入数据 */
				$("#orderDiv").html("<p class='text-primary'>订单编号:"+oid+"</p>");
				for(var i=0;i<data.length;i++){
					var content = "<tr>";
					content += "<td style='CURSOR: hand; HEIGHT: 22px' align='center'><img wigth='80px' height='50px' src='${pageContext.request.contextPath}/"+data[i].product.pimage+"'/></td>";
					content += "<td style='CURSOR: hand; HEIGHT: 22px' align='center'>"+data[i].product.pname+"</td>";
					content += "<td style='CURSOR: hand; HEIGHT: 22px' align='center'>"+data[i].product.shop_price+"</td>";
					content += "<td style='CURSOR: hand; HEIGHT: 22px' align='center'>"+data[i].count+"</td>";
					content += "<td style='CURSOR: hand; HEIGHT: 22px' align='center'>"+data[i].subtotal+"</td>";
					content += "</tr>"
					$("#orderDiv~table").append(content);
				}
			},
			error:function(){
				alert("error");
			}
		});
		$("#myModal").modal('show');
	}
	
	function hideModal(){
		$("#myModal").modal("hide");
	}
</script>


</HEAD>
<body>

	<!-- 模态框 -->
	<div id="myModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" style="position:absolute;margin-top:90px">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content" style="padding:17px">
	    	<div id="orderDiv"></div>
	      	<table cellSpacing="1" cellPadding="0" width="100%" align="center"
		bgColor="#f5fafe" border="0">
			</table>
			<div style="padding-left:400px;margin:12px"><button class="btn btn-primary" onclick="hideModal();">关闭</button></div>
	    </div>
	  </div>
	</div>

	<br>
	<table cellSpacing="1" cellPadding="0" width="100%" align="center"
		bgColor="#f5fafe" border="0">
		<TBODY>
			<tr>
				<td class="ta_01" align="center" bgColor="#afd1f3"><strong>订单列表</strong>
				</TD>
			</tr>
			<tr>
				<td class="ta_01" align="right">
					<button type="button" id="add" name="add" value="添加"
						class="button_add" onclick="addCategory()">
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
							<td align="center" width="17%">订单编号</td>
							<td align="center" width="17%">订单金额</td>
							<td align="center" width="17%">收货人</td>
							<td align="center" width="17%">订单状态</td>
							<td width="7%" align="center">订单详情</td>
						</tr>
							<c:forEach items="${orderList }" var="order" varStatus="vs">
								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${vs.count }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">${order.oid }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${order.total }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${order.name }</td>
									<c:if test="${order.state==0 }">
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">未付款</td>
									</c:if>
									<c:if test="${order.state==1 }">
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="17%">已付款</td>
									</c:if>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="7%">
										<button type="button" id="myButton" class="btn btn-primary" style="margin:5px" onclick="showModal('${order.oid}')">订单详情</button>
									</td>
								</tr>
							</c:forEach>
					</table>
				</td>
			</tr>
		</TBODY>
	</table>
</body>
</HTML>

