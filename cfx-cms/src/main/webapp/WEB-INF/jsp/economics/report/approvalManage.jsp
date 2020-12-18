<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>审批表</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<script src="./pageJs/economics/report/approvalManage.js"></script>
<style>
@media screen and (max-width: 1920px) and (min-width: 1600px){
.page-container-bg-solid .frame {
    width: 100%;
    height: 900px!important;
    background: #fff;
}
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">数据管理>审批表</li>
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
			          <input class="form-controlgoods input-append date form-dateNowyear" type="text" name="years" id = "years"  value="${year}" onkeydown="entersearch()"  readonly />
			            </label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>银行名称：</span>
							<select class="form-controlgoods input-append" id="selectBank" style="width: 180px;">
								<c:forEach items="${bankList}" var="banks">
									<option value="${banks.pk}" ${banks.pk == bankPk?'selected':''}>${banks.bankName}</option>
								</c:forEach>
							</select></label>
			             <button class="btn btn-primary" onclick="searchTabs()"> 搜索</button>
			          <button style="display:none;" showname="EM_REPORT_APPROVE_BTN_EXPORT"  class="btn btn-primary" onclick="exportData();" data-toggle="dropdown"><span id="loadingExportData">导出</span></button>
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
								            <td rowspan="3">时间</td>
								            <td colspan="12">营销</td>
								            <td colspan="12">平台</td>
								            <td colspan="12">盛虹</td>
								            <td colspan="12">新凤鸣</td>
								            <td colspan="12">其他</td>
								          </tr>
								          <tr>
								            <td colspan="3">化纤白条</td><td colspan="3">化纤贷</td><td colspan="3">化纤白条+化纤贷</td><td colspan="3">合计</td>
								            <td colspan="3">化纤白条</td><td colspan="3">化纤贷</td><td colspan="3">化纤白条+化纤贷</td><td colspan="3">合计</td>
								            <td colspan="3">化纤白条</td><td colspan="3">化纤贷</td><td colspan="3">化纤白条+化纤贷</td><td colspan="3">合计</td>
								            <td colspan="3">化纤白条</td><td colspan="3">化纤贷</td><td colspan="3">化纤白条+化纤贷</td><td colspan="3">合计</td>
								            <td colspan="3">化纤白条</td><td colspan="3">化纤贷</td><td colspan="3">化纤白条+化纤贷</td><td colspan="3">合计</td>
								          </tr>
								          <tr>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								            <td>审核中</td><td>通过</td><td>否定</td>
								          </tr>
								         	 <c:forEach items="${approvalManageList}" var="dimen" > 
									               <tr>
								                 	<td>${dimen.title}</td>
								                 	<td>${dimen.yBTApproving}</td><td>${dimen.yBTPass}</td><td>${dimen.yBTUnpass}</td>
								         		 	<td>${dimen.yDApproving}</td><td>${dimen.yDPass}</td><td>${dimen.yDUnpass}</td>
										            <td>${dimen.yBTDApproving}</td><td>${dimen.yBTDPass}</td><td>${dimen.yBTDUnpass}</td>
										            <td>${dimen.yApproving}</td><td>${dimen.yPass}</td><td>${dimen.yUnpass}</td>
										            
										            <td>${dimen.pBTApproving}</td><td>${dimen.pBTPass}</td><td>${dimen.pBTUnpass}</td>
										            <td>${dimen.pDApproving}</td><td>${dimen.pDPass}</td><td>${dimen.pDUnpass}</td>
										            <td>${dimen.pBTDApproving}</td><td>${dimen.pBTDPass}</td><td>${dimen.pBTDUnpass}</td>
										            <td>${dimen.pApproving}</td><td>${dimen.pPass}</td><td>${dimen.pUnpass}</td>
										            
										           	<td>${dimen.sBTApproving}</td><td>${dimen.sBTPass}</td><td>${dimen.sBTUnpass}</td>
								         		 	<td>${dimen.sDApproving}</td><td>${dimen.sDPass}</td><td>${dimen.sDUnpass}</td>
										            <td>${dimen.sBTDApproving}</td><td>${dimen.sBTDPass}</td><td>${dimen.sBTDUnpass}</td>
										            <td>${dimen.sApproving}</td><td>${dimen.sPass}</td><td>${dimen.sUnpass}</td>
										            
										        	<td>${dimen.xBTApproving}</td><td>${dimen.xBTPass}</td><td>${dimen.xBTUnpass}</td>
								         		 	<td>${dimen.xDApproving}</td><td>${dimen.xDPass}</td><td>${dimen.xDUnpass}</td>
										            <td>${dimen.xBTDApproving}</td><td>${dimen.xBTDPass}</td><td>${dimen.xBTDUnpass}</td>
										            <td>${dimen.xApproving}</td><td>${dimen.xPass}</td><td>${dimen.xUnpass}</td>
										            
										            <td>${dimen.qBTApproving}</td><td>${dimen.qBTPass}</td><td>${dimen.qBTUnpass}</td>
								         		 	<td>${dimen.qDApproving}</td><td>${dimen.qDPass}</td><td>${dimen.qDUnpass}</td>
										            <td>${dimen.qBTDApproving}</td><td>${dimen.qBTDPass}</td><td>${dimen.qBTDUnpass}</td>
										            <td>${dimen.qApproving}</td><td>${dimen.qPass}</td><td>${dimen.qUnpass}</td>
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
</body>
</html>