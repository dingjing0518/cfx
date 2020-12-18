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
<script src="./pageJs/logistics/purchaserInvoice.js"></script>
<script src="./pageJs/logistics/common.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">财务管理</a></li>
			<li class="active">发票管理（采购商）</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
								<label class="col-lg-2 col-lg-3"><span>公司抬头：</span> <input class="form-controlgoods input-append " type="text" name="invoiceName"   value="" onkeydown="entersearch()"/></label>	
								<label class="col-lg-2 col-lg-3"><span>申请时间：</span> <input class="form-controlgoods input-append form-dateday form-dateday_start" type="text" name="applicateStartTime"   value="" onkeydown="entersearch()"  readonly /></label>
			                    <label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append form-dateday form-dateday_end" type="text" name="applicateEndTime"   value="" readonly /></label>
			                    <label class="col-lg-2 col-lg-3"><span>开票时间：</span> <input class="form-controlgoods input-append form-dateday form-dateday_startcopy" type="text" name="invoiceStartTime"   value="" readonly /></label>
			                    <label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append form-dateday form-dateday_endcopy" type="text" name="invoiceEndTime"   value="" readonly /></label>
			                    <label class="col-lg-2 col-lg-3"><span>开票状态：</span>
								 <select class="selectpicker bla bla bli"  id="memberInvoiceStatus" name="memberInvoiceStatus" style="width: 200px;" >
									<option value="">--请选择--</option>
									<option value="1" >未开票</option>
									<option value="2">已开票</option>
								  	<option value="3">已寄送</option>
								 </select>
							 	</label>
 								<label class="col-lg-2 col-lg-3"><span>联系电话：</span> <input class="form-controlgoods input-append " type="text" name="invoiceRegPhone"   value="" onkeydown="entersearch()"/></label>	
 								<button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
		          <button style="display:none;" showname="LG_FC_PURINVOICE_BTN_EXPORT" class="btn btn-success dropdown-toggle" onclick="exportInvoice();" data-toggle="dropdown">导出 </button>
             	</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findInvoiceStatus();">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findInvoiceStatus(1);">未开票</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findInvoiceStatus(2);">已开票</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findInvoiceStatus(3);">已寄送</a>
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
                                <table id="purchaserInvoiceId" style="width: 100%"></table>
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