<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>客户申请表</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<style>
.bootstrap-table .table:not (.table-condensed ), .bootstrap-table .table:not
	(.table-condensed ) >tbody>tr>th, .bootstrap-table .table:not (.table-condensed
	) >tfoot>tr>th, .bootstrap-table .table:not (.table-condensed ) >thead>tr>td,
	.bootstrap-table .table:not (.table-condensed ) >tbody>tr>td,
	.bootstrap-table .table:not (.table-condensed ) >tfoot>tr>td {
	padding: 0px;
}
</style>
<script type="text/javascript">
$(function() {

	btnList();
});
</script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">数据管理>客户申请表</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading">
				 <label class="col-lg-3 col-lg-2"><span>年份：</span> 
				<input name="years" id="years" class="form-controlgoods input-append date form-dateyear" type="text"    value="${years }" readonly> </label>
				<button class="btn btn-primary" onclick="searchTabs()">搜索</button>
				<button style="display:none;" showname="EM_REPORT_CUSTAPPLY_BTN_EXPORT" 
					class="btn btn-primary"
					onclick="downloadRF();" data-toggle="dropdown">导出</button>
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
												<td rowspan="2"></td>
												<td colspan="6">化纤白条</td>
												<td colspan="6">化纤贷</td>
												<td colspan="6">化纤白条+化纤贷</td>
												<td colspan="6">累计</td>
											</tr>
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
											<c:forEach items="${custRFList }" var="custList">
												<tr>
													<td>${custList.monthName }</td>
													
													<td>${custList.blankSH }</td>
													<td>${custList.blankXFM }</td>
													<td>${custList.blankYX }</td>
													<td>${custList.blankPT }</td>
													<td>${custList.blankOther }</td>
													<td>${custList.blankGoodsTypeCount }</td>
													<td>${custList.loanSH }</td>
													<td>${custList.loanXFM }</td>
													<td>${custList.loanYX }</td>
													<td>${custList.loanPT }</td>
													<td>${custList.loanOther }</td>
													<td>${custList.loanGoodsTypeCount }</td>
													<td>${custList.blaLoSH }</td>
													<td>${custList.blaLoXFM }</td>
													<td>${custList.blaLoYX }</td>
													<td>${custList.blaLoPT }</td>
													<td>${custList.blaLoOther }</td>
													<td>${custList.blaLoGoodsTypeCount }</td>
													<td>${custList.addUpSH }</td>
													<td>${custList.addUpXFM }</td>
													<td>${custList.addUpYX }</td>
													<td>${custList.addUpPT }</td>
													<td>${custList.addUpOther }</td>
													<td>${custList.addUpCount }</td>
												</tr>
											</c:forEach>
											<tr>
													<td>合计</td>
													
													<td>${monthSum.blankSHMonthCount }</td>
													<td>${monthSum.blankXFMMonthCount }</td>
													<td>${monthSum.blankYXMonthCount }</td>
													<td>${monthSum.blankPTMonthCount }</td>
													<td>${monthSum.blankOtherMonthCount }</td>
													<td>${monthSum.blankMonthAll }</td>
													
													<td>${monthSum.loanSHMonthCount }</td>
													<td>${monthSum.loanXFMMonthCount }</td>
													<td>${monthSum.loanYXMonthCount }</td>
													<td>${monthSum.loanPTMonthCount }</td>
													<td>${monthSum.loanOtherMonthCount }</td>
													<td>${monthSum.loanMonthAll }</td>
													
													<td>${monthSum.blaLoSHMonthCount }</td>
													<td>${monthSum.blaLoXFMMonthCount }</td>
													<td>${monthSum.blaLoYXMonthCount }</td>
													<td>${monthSum.blaLoPTMonthCount }</td>
													<td>${monthSum.blaLoOtherMonthCount }</td>
													<td>${monthSum.blaLoMonthAll }</td>
													
													<td>${monthSum.addUpSHMonthCount }</td>
													<td>${monthSum.addUpXFMMonthCount }</td>
													<td>${monthSum.addUpYXMonthCount }</td>
													<td>${monthSum.addUpPTMonthCount }</td>
													<td>${monthSum.addUpOtherMonthCount }</td>
													<td>${monthSum.addUpMonthAll }</td>
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
	<script type="text/javascript">
		function searchTabs(){
			var years = $("#years").val();
			window.location=getRootPath()+"/customApprove.do?years="+years;
		}

		function downloadRF(){
            var years = $("#years").val();
            $.ajax({
                type : 'POST',
                url : getRootPath() + "/exportCustomerRF.do?years="
                    + years,
                data : {},
                dataType : 'json',
                success : function(data) {
                    if (data.success){
                        new $.flavr({
                            modal : false,
                            content :data.msg
                        });
                    }
                }
            });
		}
	</script>

	<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>