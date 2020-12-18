<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>银行审批额度</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<script src="./pageJs/economics/report/bankApproveAmount.js"></script>
<style>
.bootstrap-table .table:not (.table-condensed ), .bootstrap-table .table:not
	 (.table-condensed ) >tbody>tr>th, .bootstrap-table .table:not (.table-condensed
	 ) >tfoot>tr>th, .bootstrap-table .table:not (.table-condensed ) >thead>tr>td,
	.bootstrap-table .table:not (.table-condensed ) >tbody>tr>td,
	.bootstrap-table .table:not (.table-condensed ) >tfoot>tr>td {
	padding: 0px;
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">数据管理>银行审批额度</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading">
				<input type="hidden" id="bankPk" name="bankPk" value="${bankPk}" />
				<label class="col-lg-3 col-lg-2"><span>年份：</span> <input
					class="form-controlgoods input-append date form-dateNowyear"
					type="text" name="year" id="year" value="${year}"
					onkeydown="entersearch()" readonly /> </label>
					<label class="col-sm-6 col-md-4 col-lg-3"><span>银行名称：</span>
						<select class="form-controlgoods input-append" id="selectBank" style="width: 180px;">
							<c:forEach items="${bankList}" var="banks">
								<option value="${banks.pk}" ${banks.pk == bankPk?'selected':''}>${banks.bankName}</option>
							</c:forEach>
						</select></label>
				<button class="btn btn-primary" onclick="searchTabs()">搜索</button>
	
				<button showname="EM_REPORT_BANKAMOUNT_BTN_EXPORT" style="display:none;" class="btn btn-primary" onclick="exportData();" data-toggle="dropdown"><span id="loadingExportData">导出</span></button>
	
				</header>
				<div class="panel-body">
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
												<td rowspan="5">时间</td>
												<td colspan="24">额度</td>
											</tr>
											<tr>
												<td colspan="12">新增</td>
												<td colspan="12">累计</td>
											</tr>
											<tr>
												<td colspan="6">化纤白条</td>
												<td colspan="6">化纤贷</td>
												<td colspan="6">化纤白条</td>
												<td colspan="6">化纤贷</td>
											</tr>
											<tr>
											<tr>
												<td>盛虹</td>
												<td>新凤鸣</td>
												<td>营销</td>
												<td>平台</td>
												<td>其他</td>
												<td>合计</td>
												<td>盛虹</td>
												<td>新凤鸣</td>
												<td>营销</td>
												<td>平台</td>
												<td>其他</td>
												<td>合计</td>
												<td>盛虹</td>
												<td>新凤鸣</td>
												<td>营销</td>
												<td>平台</td>
												<td>其他</td>
												<td>合计</td>
												<td>盛虹</td>
												<td>新凤鸣</td>
												<td>营销</td>
												<td>平台</td>
												<td>其他</td>
												<td>合计</td>
											</tr>
											</tr>
											<c:forEach items="${bankApproveAmountList}" var="dimen">
												<tr>
													<td>${dimen.title}</td>
													<td>${dimen.sBTAmount}</td>
													<td>${dimen.xBTAmount}</td>
													<td>${dimen.yBTAmount}</td>
													<td>${dimen.pBTAmount}</td>
													<td>${dimen.qBTAmount}</td>
													<td>${dimen.BTAmount}</td>
													<td>${dimen.sDAmount}</td>
													<td>${dimen.xDAmount}</td>
													<td>${dimen.yDAmount}</td>
													<td>${dimen.pDAmount}</td>
													<td>${dimen.qDAmount}</td>
													<td>${dimen.DAmount}</td>
													<td>${dimen.sBTTotalAmount}</td>
													<td>${dimen.xBTTotalAmount}</td>
													<td>${dimen.yBTTotalAmount}</td>
													<td>${dimen.pBTTotalAmount}</td>
													<td>${dimen.qBTTotalAmount}</td>
													<td>${dimen.BTTotalAmount}</td>
													<td>${dimen.sDTotalAmount}</td>
													<td>${dimen.xDTotalAmount}</td>
													<td>${dimen.yDTotalAmount}</td>
													<td>${dimen.pDTotalAmount}</td>
													<td>${dimen.qDTotalAmount}</td>
													<td>${dimen.DTotalAmount}</td>
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
</body>
<script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	searchTabs();  
	       }  
	} 	
	</script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</html>