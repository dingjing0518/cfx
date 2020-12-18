<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>行业数据</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>

</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营中心</a></li>
			<li class="active">数据管理>行业数据</li>
		</ul> 
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 	<header class="panel-heading" style="margin-bottom:10px;" id="ewaijiangli">
						<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>日期（月份）：</span> <input class="form-datemonth form-controlgoods input-append date " type="text" name="months" id="months"  value="${reMonth }" readonly /></label>
				        <button style="margin-left:18px;position: relative;z-index: 10000;" class="btn btn-primary" id="btn" onclick="SearchClean();"> 搜索</button>			            
	             	</header>
		             <!--  -->
					<div class="panel-body" style="width:50%;float:left;padding-left:0px;">
						<h2 style="text-align: center;color: #535351;font-size: 18px;margin-bottom: 18px;">销量TOP10产品规格（按成交量排序）</h2>
						<section class="panel">
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="bootstrap-table" style="margin-top: 0px;">
								<div class="fixed-table-container" style="padding-bottom: 0px;">
									<div class="fixed-table-body">
										<table id="fiberLoanOrderId"
											style="text-align: center; width: 100%"
											class="table table-hover table-striped">
											<tr>
												<td>序号</td>
												<td>品名</td>
												<td>规格系列</td>
												<td>成交量/吨</td>
												<td>交易额（元）</td>
											</tr>
											<c:forEach items="${productSpecList }" var='proSpec'>
											<tr>
												<td>${proSpec.numbers }</td>
												<td>${proSpec.productName }</td>
												<td>${proSpec.specName }</td>
												<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${proSpec.weight }" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${proSpec.allAmount }" /></td>
											</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					</section>
				</div>
				<div class="panel-body" style="width:50%;float:left;padding-right:0px;">
	<h2 style="text-align: center;color: #535351;font-size: 18px;margin-bottom: 18px;">销量TOP10店铺名称（按成交量排序）</h2>
					<section class="panel">
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="bootstrap-table" style="margin-top: 0px;">
								<div class="fixed-table-container" style="padding-bottom: 0px;">
									<div class="fixed-table-body">
										<table id="fiberLoanOrderId"
											style="text-align: center; width: 100%"
											class="table table-hover table-striped">
											<tr>
												<td>序号</td>
												<td>店铺名称</td>
												<td>订单数</td>
												<td>成交量/吨</td>
												<td>交易额（元）</td>
											</tr>
											<c:forEach items="${storeList }" var='store'>
											<tr>
												<td>${store.numbers }</td>
												<td>${store.storeName }</td>
												<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${store.counts }" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${store.weight }" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${store.allAmount }" /></td>
											</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					</section>
				</div>
				<div class="panel-body" style="width:50%;float:left;padding-left:0px;">
	<h2 style="text-align: center;color: #535351;font-size: 18px;margin-bottom: 18px;">销量TOP10采购商（按成交量排序）</h2>
					<section class="panel">
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="bootstrap-table" style="margin-top: 0px;">
								<div class="fixed-table-container" style="padding-bottom: 0px;">
									<div class="fixed-table-body">
										<table id="fiberLoanOrderId"
											style="text-align: center; width: 100%"
											class="table table-hover table-striped">
											<tr>
												<td>序号</td>
												<td>采购商名称</td>
												<td>订单数</td>
												<td>成交量/吨</td>
												<td>交易额（元）</td>
											</tr>
											<c:forEach items="${purchaserList }" var='purchaser'>
											<tr>
												<td>${purchaser.numbers }</td>
												<td>${purchaser.purchaserName }</td>
												<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${purchaser.counts }" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="4" value="${purchaser.weight }" /></td>
												<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${purchaser.allAmount }" /></td>
											</tr>
											</c:forEach>
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
	<script type="text/javascript">
		function SearchClean(){
			var months = $("#months").val();
			window.location=getRootPath()+"/industryData.do?searchMonth="+months;
		}

	</script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>