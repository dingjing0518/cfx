<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="./pageJs/include/third-party/fancybox/fancybox.css" rel="stylesheet"
	type="text/css" />
<title>内部管理系统</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<!-- 页面内容导航开始-->
<script src="./pageJs/logistics/report/customerDataReport.js"></script>
<script src="./pageJs/logistics/common.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">数据报表</a></li>
			<li class="active">客户报表</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<label class="col-lg-2 col-lg-3"><span>客户名称：</span> <input class="form-controlgoods input-append " type="text" name="purchaserName"   value="" onkeydown="entersearch()"/></label>	
						<label class="col-lg-2 col-lg-3"><span>联系电话：</span> <input class="form-controlgoods input-append " type="text" name="purchasersContacts"   value="" onkeydown="entersearch()"/></label>	
						<label class="col-lg-2 col-lg-3"><span>订单编号：</span> <input class="form-controlgoods input-append " type="text" name="orderPk"   value="" onkeydown="entersearch()"/></label>	
						<label class="col-lg-2 col-lg-3"><span>下单时间：</span> <input class="form-controlgoods input-append form-dateday form-dateday_start" type="text" name="insertStartTime"   value="" onkeydown="entersearch()"  readonly /></label>
	                    <label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append form-dateday form-dateday_end" type="text" name="insertEndTime"   value="" readonly /></label>
						<label class="col-lg-2 col-lg-3"><span>订单状态：</span>
						 <select class="selectpicker bla bla bli" name="orderStatus" id='orderStatus' style="width: 200px;" >
							<option value="">--请选择--</option>
							<option value="2">已取消</option>
							<option value="3">已签收</option>
						  	<option value="4">配送中</option>
						  	<option value="5">提货中</option>
							<option value="6">财务已确认</option>
						  	<option value="8">待确认</option>
							<option value="9">待付款</option>
							<option value="10">已关闭</option>
						 </select>
					 	</label>	
					 	
					 	<label class="col-lg-2 col-lg-3"><span>结算状态：</span>
						 <select class="selectpicker bla bla bli" name="isSettleFreight" id='isSettleFreight' style="width: 200px;" >
							<option value="">--请选择--</option>
							<option value="1" >未结算</option>
							<option value="2">已结算</option>
						 </select>
					 	</label>	
					 	<input type = "hidden" id="reportStatus" name="reportStatus"/>
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
		          <button style="display:none;" showname="LG_REPORT_CUT_BTN_EXPORT" class="btn btn-success dropdown-toggle" onclick="exportReport();" data-toggle="dropdown">导出 </button>
             	</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findReportStatus(1);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findReportStatus(2);">进行中</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findReportStatus(3);">已完成</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findReportStatus(4);">异常</a>
                                </li>
                            </ul>
                        </header>
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
                                <table id="customerDataId" style="width: 100%"></table>
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
	
	 </script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>