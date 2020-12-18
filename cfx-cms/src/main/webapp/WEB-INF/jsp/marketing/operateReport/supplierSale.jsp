<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商销售报表</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<script src="./pageJs/marketing/operateReport/supplierSale.js"></script>
<script src="./pageJs/include/datagrid.js"></script>
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
			<li class="active">数据管理>供应商销售报表</li>
		</ul> 
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">
			          <label class="col-lg-3 col-lg-2">
			          		<span>日期：</span> <input name="startTime" id="startTime" class="form-controlgoods input-append date form-dateday" type="text" value="${startTime }" readonly>
	          		</label> 
		          	<label class="col-lg-3 col-lg-2">
		          		<span>至</span> <input name="endTime" id="endTime" class="form-controlgoods input-append date form-dateday" type="text" value="${endTime }" readonly>
	          		</label>	
			             <button class="btn btn-primary" onclick="searchTabs()"> 搜索</button>
			          <button style="display: none;" showname="MARKET_REPORT_SUPPLIERSALE_BTN_EXPORT" class="btn btn-primary" onclick="exportData();" data-toggle="dropdown"><span id="loadingExportData">导出</span></button>
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
                                 <table id="supplierSaleId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<script>
	
	</script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>