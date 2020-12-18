<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>内部管理系统</title>
	<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
	</c:import>
	<link href="././style/css/approval.css" rel="stylesheet" type="text/css" />
		<link href="./pageJs/include/third-party/fancybox/fancybox.css" rel="stylesheet" type="text/css" />
	
	<script src="./pageJs/marketing/orderBillOfLoading.js"></script>
</head>
<body class="sticky-header">
<div class="page-heading">
	<ul class="breadcrumb">
		<li class="active">授信管理/</li>
		<li class="active">借款单管理/订单提货</li>
	</ul>
</div>
<div class="wrapper">
	<div class="row">
		<div class="col-sm-12">
			<section class="panel">
				<div class="panel-body">
					<section class="panel">
						<div class="companyinfo product-jkdinfo">
							<div class="title-top">
								<h3>借款单信息</h3>
							</div>
							<input type = "hidden" id="orderNumber" value ="${deliveryBasic.order.orderNumber}"/>
							<input type = "hidden" id="roleName" value ="${roleName}"/>
							<input type = "hidden" id="flag" value ="${flag}"/>
								<div class="table">
								<ul class="clearfix">
			                        <li class="clearfix">
			                            <p>订单编号</p><span class="info"><em>${deliveryBasic.order.orderNumber}</em></span>
			                            <p>订单状态</p><span class="info"><em> 
			                         <c:if test="${deliveryBasic.order.orderStatus==0}">
										  待审核
								     </c:if>
								     <c:if test="${deliveryBasic.order.orderStatus==1}">
								       	  待付款
								     </c:if>
								     <c:if test="${deliveryBasic.order.orderStatus==2}">
								       	  待确认
								     </c:if>
								     <c:if test="${deliveryBasic.order.orderStatus==3}">
								       	  待发货
								     </c:if>
								     <c:if test="${deliveryBasic.order.orderStatus==4}">
								       	  已发货
								     </c:if>
								     <c:if test="${deliveryBasic.order.orderStatus==5}">
								       	  部分发货
								     </c:if>
								     <c:if test="${deliveryBasic.order.orderStatus==6}">
								       	  已完成
								     </c:if>
								     <c:if test="${deliveryBasic.order.orderStatus==-1}">
								       	  订单取消
								     </c:if>
								 </em>
								     </span>
			                            <p>店铺</p><span class="info"><em>${deliveryBasic.order.storeName}</em></span>
			                            <p>下单时间</p><span class="info"><em><fmt:formatDate value="${deliveryBasic.order.insertTime}" pattern="yyyy-MM-dd hh:mm:ss" /></em></span>
			                        </li>
			                        <li class="clearfix">
			                            <p>订单金额</p><span class="info"><em>${deliveryBasic.order.orderAmount}</em></span>
			                            <p>支付方式</p><span class="info"><em>
			                         	<c:if test="${deliveryBasic.order.paymentType==1}">
								       	线下支付
								     	</c:if>
								       <c:if test="${deliveryBasic.order.paymentType==2}">
								       	  余额支付
								    	 </c:if>
								       <c:if test="${deliveryBasic.order.paymentType==3}">
								       	  金融产品
								     </c:if></em>
			                            </span>
			                            <p>借据编号</p><span class="info"><em>${deliveryBasic.loan.loanNumber}</em></span>
			                            <p>借款状态</p><span class="info"><em>
			                              <c:if test="${deliveryBasic.loan.loanStatus==2}">
								       		 申请中
								    	 </c:if>
								    	 <c:if test="${deliveryBasic.loan.loanStatus==3}">
								       		 申请成功
								    	 </c:if>
								    	  <c:if test="${deliveryBasic.loan.loanStatus==4}">
								       		 申请失败
								    	 </c:if>
								    	  <c:if test="${deliveryBasic.loan.loanStatus==5}">
								       		 已还款
								    	 </c:if>
								    	 <c:if test="${deliveryBasic.loan.loanStatus==6}">
								       		 部分还款
								    	 </c:if></em>
								    	 </span>
			                        </li>
			                        <li class="clearfix">
			                            <p>订单来源</p><span class="info"><em>
			                              <c:if test="${deliveryBasic.order.source==1}">
								       	 pc端
								    	 </c:if>
								    	   <c:if test="${deliveryBasic.order.source==2}">
								       	 移动端
								    	 </c:if>
								    	   <c:if test="${deliveryBasic.order.source==3}">
								       	app
								    	 </c:if>
								    	 <c:if test="${deliveryBasic.order.source==4}">
								       	后台录入
								    	 </c:if></em>
			                            </span>
			                            <p>支付时间</p><span class="info"><em><fmt:formatDate value="${deliveryBasic.order.paymentTime}" pattern="yyyy-MM-dd hh:mm:ss" /></em></span>
			                            <p>采购商名称</p><span class="info"><em>${deliveryBasic.order.purchaser.purchaserName}</em></span>
			                            <p>收货地址</p><span class="info"><em>${deliveryBasic.order.address.provinceName}${deliveryBasic.order.address.cityName}${deliveryBasic.order.address.areaName}${deliveryBasic.order.address.townName}${deliveryBasic.order.address.address}</em></span>
			                        </li>
			                        <li class="clearfix">
			                            <p>发票抬头</p><span class="info"><em>${deliveryBasic.order.purchaser.invoiceName}</em></span>
			                            <p>金融产品</p><span class="info"><em>${deliveryBasic.order.economicsGoodsName}</em></span>
			                            <p>剩余提货金额</p><span class="info"><em>${deliveryBasic.surplusAmount}</em></span>
			                            <p>首付款金额</p><span class="info"><em>${deliveryBasic.downPaymentAmount }</em></span>
			                        </li>
			                       
			                    </ul>
		                    </div>
						</div>
					
						<div class="product-thinfo">
							<div class="title-top">
								<h3>提货明细</h3>
							</div>
							<ul class="ul-auctionshow biddingprice-tabletop clearfix">
			                    <li class="select-status current" data-status=""  id = "active_1">
			                        <a href="javascript:;" onclick="byStatus('1,2',1);">待确认</a>
			                    </li>
			                    <li class="select-status" data-status="1" id = "active_2">
			                        <a href="javascript:;" onclick="byStatus(3,2);">已确认</a>
			                    </li>
			                    <li class="select-status" data-status="2"id = "active_3">
			                        <a href="javascript:;"  onclick="byStatus(4,3);">已驳回</a>
			                    </li>
			                    <div style="clear:both"></div>
			                </ul>
		                <div class="table">
		                    <ul class="table-top clearfix">
		                        <li>日期</li>
		                        <li>提货单号</li>
		                        <li>商品信息</li>
		                        <li><span>实际成交单价</span><span>（元/吨）</span></li>
		                        <li>数量/重量</li>
		                        <li>本次需应还款金额</li>
		                        <li>状态</li>
		                        <li>凭证</li>
		                        <li>操作</li>
		                    </ul>
		                    <div id = "statusContentDiv">
		                    
		                     </div>
		                 
                			</div>
						</div>
					</section>
				</div>
			</section>
		</div>
	</div>
</div>
<script>

	
	function errorImg(img) {
	    img.src = "./style/images/bgbg.png";
	    img.onerror = null;
	}

	function fileChange(self,pk){
		var formData = new FormData();
		formData.append( "file", $(self)[0].files[0]);
		formData.append( "orderNumber",$("#orderNumber").val());
		$.ajax({
		    url: getRootPath() + "/uploadPhotoPay.do",
		    dataType: "json",
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			if (res.data.indexOf(".jpg") != -1|| res.data.indexOf(".png") != -1	|| res.data.indexOf(".jpeg") != -1) {
               
				/* $("#" + pk).attr("src", res.data);  */ 
				$('#btn_file').val("");
				$("#divImg").html($("#divImg").html()+imgSrc( res.data,res.payId));
			} else {
				var index = res.data.lastIndexOf("\/");
				res.data = res.data.substring(index + 1,
						res.data.length);
				var json = $.parseJSON(res.data);
				new $.flavr({
					modal : false,
					content : json.msg
				});
			}
		});
	}
var  i = 0;	
function imgSrc(data,payId){
	var  content = '<div id="scrollDiv" name = "scrollDiv_'+i+'"><ul><li class="voucher">'+
	'<img id = tyle="width:100px;height:100px;" src ="'+data+ '"  class="bigpicture"  class="imgC" title="付款凭证" onclick="op(this.src)" onerror="errorImg(this)"/>'+
   ' <div class="countcheck"><p class="deletePicture" onclick="deletePicture('+i+','+payId+');">删除</p> </div></li> </ul></div>   ';
   i = i+1;
   return content
} 



</script>
   <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
