<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>采购商交易统计</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<script src="./pageJs/marketing/operateReport/purchaserSale.js"></script>

<style>
.form-controlgoods{
float:left;
width:200px;
margin-left:10px;
}
.table-striped thead tr:first-child th:first-child,.table-striped thead tr:first-child th:first-child+th{
height:80px!important;}
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
			<li class="active">数据管理>采购商交易统计</li>
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
			          <input class="form-controlgoods input-append date form-dateNowyear" type="text" name="year" id = "year"  value="${year}" onkeydown="entersearch()"  readonly />
			            </label>
			             <button class="btn btn-primary" onclick="SearchClean()"> 搜索</button>
			      		<!-- <button class="btn btn-primary"  onclick="SearchClean(2)"> 清除</button> -->
			          <button style="display: none;" showname="MARKET_REPORT_PURCHASERSALE_BTN_EXPORT" class="btn btn-primary" onclick="downloadRF();" data-toggle="dropdown"><span id="loadingExportData">导出</span></button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                 <table id="purchaserSale" style="width: 100%"></table>
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
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>