<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>平台销售数据</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<style>
.form-controlgoods{
float:left;
width:200px;
margin-left:10px;
}
@media  (max-width:1730px){
.form-controlgoods{
width:105px;
}
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">营销中心</a></li>
			<li class="active">数据管理>平台销售数据</li>
		</ul> 
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">
			         <label class="col-lg-3 col-lg-2"><span>日期：</span>
			          <input class="form-controlgoods input-append date form-dateNowyear" type="text" name="year" id = "year"  value="${year}" onkeydown="entersearch()"  readonly />
			            </label>
			             <button class="btn btn-primary" onclick="searchTabs()"> 搜索</button>
			      		<!-- <button class="btn btn-primary"  onclick="searchTabs(2)"> 清除</button> -->
			          <button style="display:none;" showname="MARKET_REPORT_SHOPSALEDATA_BTN_EXPORT" class="btn btn-primary" onclick="exportData();" data-toggle="dropdown"><span id="loadingExportData">导出</span></button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                               <div class="bootstrap-table" style="margin-top:0px;">
                               		<div class="fixed-table-container" style="padding-bottom: 0px;">
                               			<div class="fixed-table-body">
                               				<table id="fiberLoanOrderId" style="text-align:center;width: 100%" class="table table-hover table-striped">
								          <tr>
								          <td colspan="19">${year }年每月平台销售数据</td>
								          </tr>
								          <tr>
								            <td rowspan="2" style="text-align: center; vertical-align: middle; ">月份</td>
								            <td colspan="3">累积</td>
								            <td colspan="5">盛虹</td>
								            <td colspan="5">新凤鸣</td>
								            <td colspan="5">其他供应商</td>
								          </tr>
								          <tr>
								           <td>总量（T）</td>
								           <td>交易金额</td>
								           <td>总订单数（条）</td>
								            <td>数量（T）</td>
								           <td>交易金额</td>
								           <td>订单数（条）</td>
								            <td>占比（%）</td>
								           <td>增长率（%）</td>
								           <td>数量（T）</td>
								           <td>交易金额</td>
								           <td>订单数（条）</td>
								            <td>占比（%）</td>
								           <td>增长率（%）</td>
								           <td>数量（T）</td>
								           <td>交易金额</td>
								           <td>订单数（条）</td>
								            <td>占比（%）</td>
								           <td>增长率（%）</td>
								          </tr>
								          <tr>
								          <c:if test="${list != null }">
								          <c:forEach items="${ list}" var="saleData">
								          	<td>${saleData.monthName }</td>
											  <td><fmt:formatNumber type="number" maxFractionDigits="4" value="${saleData.weightAddUp }" /></td>
											  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${saleData.amountAddUp}" /></td>
								          	<td>${saleData.countsAddUp }</td>
								          	<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${saleData.weightSH }" /></td>
											  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${saleData.amountSH }" /></td>
								          	<td>${saleData.countsSH }</td>
								          	<td>${saleData.precentSH }</td>
								          	<td>${saleData.addUpPrecentSH }</td>
								          	<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${saleData.weightXFM }" /></td>
											  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${saleData.amountXFM }" /></td>
								          	<td>${saleData.countsXFM }</td>
								          	<td>${saleData.precentXFM }</td>
								          	<td>${saleData.addUpPrecentXFM }</td>
								          	<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${saleData.weightOther }" /></td>
											  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${saleData.amountOther }" /></td>
								          	<td>${saleData.countsOther }</td>
								          	<td>${saleData.precentOther }</td>
								          	<td>${saleData.addUpPrecentOther }</td>
								          </tr>
								          </c:forEach>
								          </c:if>
								          <tr>
								          	<td>合计</td>
								          	<c:if test="${totalData != null }">
								          	<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${totalData.weightAddup}" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalData.amountAddup}" /></td>
								          	<td>${totalData.countsAddup}</td>
								          	<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${totalData.weightSH}" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalData.amountSH}" /></td>
								          	<td>${totalData.countsSH}</td>
								          	<td></td>
								          	<td></td>
								          	<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${totalData.weightXFM}" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalData.amountXFM}" /></td>
								          	<td>${totalData.countsXFM}</td>
								          	<td></td>
								          	<td></td>
								          	<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${totalData.weightOther}" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalData.amountOther}" /></td>
								          	<td>${totalData.countsOther}</td>
								          	<td></td>
								          	<td></td>
								          	</c:if>
								          </tr>
								        </table>
                               			</div>
                               		</div>
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
	
	$(function() {

		btnList();
	});
	
	
	function searchTabs(){
		var year = $("#year").val();
		window.location=getRootPath()+"/shopSaleDataReport.do?years="+year;
	}
	function exportData(){
		var year = $("#year").val();
		$.ajax({
			type : "POST",
			url : './exportShopSaleDataReport.do',
			dataType : "json",
			data : {
				year:year
			},
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
			}
		});
	}
	</script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>