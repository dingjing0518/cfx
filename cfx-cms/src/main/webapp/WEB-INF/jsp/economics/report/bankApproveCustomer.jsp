<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>银行审批客户数</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<style type="text/css">
.bootstrap-table .table:not(.table-condensed), .bootstrap-table .table:not(.table-condensed) > tbody > tr > th, .bootstrap-table .table:not(.table-condensed) > tfoot > tr > th, .bootstrap-table .table:not(.table-condensed) > thead > tr > td, .bootstrap-table .table:not(.table-condensed) > tbody > tr > td, .bootstrap-table .table:not(.table-condensed) > tfoot > tr > td{
padding:0px;
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
			<li class="active">数据管理>银行审批客户数</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">
			           <label class="col-lg-3 col-lg-2"><span>年份：</span> 
						<input name="years" id="years" class="form-controlgoods input-append date form-dateyear" type="text" value="${years}" readonly> </label>
                        <label class="col-sm-6 col-md-4 col-lg-3"><span>银行名称：</span>
                            <select class="form-controlgoods input-append" id="selectBank" style="width: 180px;">
                                <c:forEach items="${bankList}" var="banks">
                                    <option value="${banks.pk}" ${banks.pk == bankPk?'selected':''}>${banks.bankName}</option>
                                </c:forEach>
                            </select></label>
                        <button class="btn btn-primary" onclick="searchTabs()">搜索</button>
					<button style="display:none;" class="btn btn-primary" showname="EM_REPORT_BANKCUST_BTN_EXPORT" 
					onclick="downloadRF();" data-toggle="dropdown">导出</button>
					
					


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
            <td rowspan="5">时间</td>
            <td colspan="36">客户数</td>
          </tr>
          <tr>
            <td colspan="18">新增</td>
            <td colspan="18">累计</td></tr>
          <tr>
            <td colspan="6">化纤白条</td><td colspan="6">化纤贷</td><td colspan="6">化纤白条+化纤贷</td>
            <td colspan="6">化纤白条</td><td colspan="6">化纤贷</td><td colspan="6">化纤白条+化纤贷</td>
          </tr>
          <tr>
            <tr>
            <td>盛虹</td><td>新凤鸣</td><td>营销</td><td>平台</td><td>其他</td><td>合计</td>
            <td>盛虹</td><td>新凤鸣</td><td>营销</td><td>平台</td><td>其他</td><td>合计</td>
            <td>盛虹</td><td>新凤鸣</td><td>营销</td><td>平台</td><td>其他</td><td>合计</td>
            <td>盛虹</td><td>新凤鸣</td><td>营销</td><td>平台</td><td>其他</td><td>合计</td>
            <td>盛虹</td><td>新凤鸣</td><td>营销</td><td>平台</td><td>其他</td><td>合计</td>
            <td>盛虹</td><td>新凤鸣</td><td>营销</td><td>平台</td><td>其他</td><td>合计</td>
          </tr>
          </tr>
          <c:forEach items="${custRFList }" var="custList">
          <tr>
            <td>${custList.monthName }</td>
            <td>${custList.blankSH }</td><td>${custList.blankXFM }</td><td>${custList.blankYX }</td>
            <td>${custList.blankPT }</td><td>${custList.blankOther }</td><td>${custList.blankGoodsTypeCount }</td>
            
            <td>${custList.loanSH }</td><td>${custList.loanXFM }</td><td>${custList.loanYX }</td>
            <td>${custList.loanPT }</td><td>${custList.loanOther }</td><td>${custList.loanGoodsTypeCount }</td>
            
            <td>${custList.blaLoSH }</td><td>${custList.blaLoXFM }</td><td>${custList.blaLoYX }</td>
            <td>${custList.blaLoPT }</td><td>${custList.blaLoOther }</td><td>${custList.blaLoGoodsTypeCount }</td>
            
            <td>${custList.blankAddUpSH }</td><td>${custList.blankAddUpXFM }</td><td>${custList.blankAddUpYX }</td>
            <td>${custList.blankAddUpPT }</td><td>${custList.blankAddUpOther }</td><td>${custList.blankAddUpCount }</td>
            
            <td>${custList.loanAddUpSH }</td><td>${custList.loanAddUpXFM }</td><td>${custList.loanAddUpYX }</td>
            <td>${custList.loanAddUpPT }</td><td>${custList.loanAddUpOther }</td><td>${custList.loanAddUpCount }</td>
            
            <td>${custList.blaLoAddUpSH }</td><td>${custList.blaLoAddUpXFM }</td><td>${custList.blaLoAddUpYX }</td>
            <td>${custList.blaLoAddUpPT }</td><td>${custList.blaLoAddUpOther }</td><td>${custList.blaLoAddUpCount }</td>
          </tr>
          </c:forEach>
          <tr>
            <td>合计</td>
            <td>${monthSum.blankSHMonthCount }</td><td>${monthSum.blankXFMMonthCount }</td><td>${monthSum.blankYXMonthCount }</td>
            <td>${monthSum.blankPTMonthCount }</td><td>${monthSum.blankOtherMonthCount }</td><td>${monthSum.blankMonthAllCount }</td><td>${monthSum.loanSHMonthCount }</td>
            <td>${monthSum.loanXFMMonthCount }</td><td>${monthSum.loanYXMonthCount }</td><td>${monthSum.loanPTMonthCount }</td><td>${monthSum.loanOtherMonthCount }</td><td>${monthSum.loanMonthAllCount }</td>
            <td>${monthSum.blaLoSHMonthCount }</td><td>${monthSum.blaLoXFMMonthCount }</td>
            <td>${monthSum.blaLoYXMonthCount }</td><td>${monthSum.blaLoPTMonthCount }</td><td>${monthSum.blaLoOtherMonthCount }</td><td>${monthSum.blaLoMonthAllCount }</td>
            
            <td>${monthSum.blankAddUpMonthSH }</td><td>${monthSum.blankAddUpMonthXFM }</td><td>${monthSum.blankAddUpMonthYX }</td><td>${monthSum.blankAddUpMonthPT }</td>
            <td>${monthSum.blankAddUpMonthOther }</td><td>${monthSum.blankAddUpMonthCount }</td>
            <td>${monthSum.loanAddUpMonthSH}</td><td>${monthSum.loanAddUpMonthXFM}</td><td>${monthSum.loanAddUpMonthYX}</td><td>${monthSum.loanAddUpMonthPT}</td>
            <td>${monthSum.loanAddUpMonthOther}</td><td>${monthSum.loanAddUpMonthCount}</td>
            <td>${monthSum.blaLoAddUpMonthSH}</td><td>${monthSum.blaLoAddUpMonthXFM}</td><td>${monthSum.blaLoAddUpMonthYX}</td><td>${monthSum.blaLoAddUpMonthPT}</td>
            <td>${monthSum.blaLoAddUMonthpOther}</td><td>${monthSum.blaLoAddUpMonthCount}</td>
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
			var bankPk = $("#selectBank").val();
			window.location=getRootPath()+"/bankApproveCustomer.do?years="+years+"&bankPk="+bankPk;
		}
		function downloadRF(){
			var years = $("#years").val();
			var bankPk = $("#selectBank").val();

            $.ajax({
                type: 'POST',
                url: getRootPath() + "/exportBankApproveCustomer.do?years="+years+"&bankPk="+bankPk,
                data: {},
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        new $.flavr({
                            modal: false,
                            content: data.msg
                        });
                    }
                }
            });

		}
	</script>
	
	<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>