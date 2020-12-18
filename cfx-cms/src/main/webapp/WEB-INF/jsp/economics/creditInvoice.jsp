<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="./pageJs/include/third-party/fancybox/fancybox.css" rel="stylesheet"
	type="text/css" />
	<style type="text/css">
		.select .btn-group.bootstrap-select.bla.bli{
			float: left!important;
		}
	</style>
<title>内部管理系统</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<!-- 页面内容导航开始-->
<script src="./pageJs/economics/creditInvoice.js"></script>
<script src="./pageJs/logistics/common.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">授信客户管理</a></li>
			<li class="active">发票查询</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<div style="height: 35px;">
							<label class="col-lg-2 col-lg-3"><p>公司名称：${creditExtDot.companyName}</p></label>
							<label class="col-lg-2 col-lg-3"><p>联系人：${creditExtDot.creditContacts}</p></label>
							<label class="col-lg-2 col-lg-3"><p>手机号码：${creditExtDot.creditContactsTel}</p></label>
						</div>
						<div style="height: 35px;">
							<label class="col-lg-2 col-lg-3"><p>金融产品：${creditExtDot.productNames}</p></label>
							<label class="col-lg-2 col-lg-3"><p>审批状态：${creditExtDot.creditStatusName}</p></label>
						</div>
						<div>
							<label class="sqsj" style="float:left;margin-right: 40px;"><span>申请时间：</span> <input style="float:left;" class="form-controlgoods input-append form-dateday form-dateday_start" type="text" name="billingDateStart" id="billingDateStart"  value="" onkeydown="entersearch()"  readonly />
							<span style="padding: 0 10px;">至</span> <input style="float:left;" class="form-controlgoods input-append form-dateday form-dateday_end" type="text" name="billingDateEnd" id="billingDateEnd"  value="" readonly /></label>
							<label class="fplx" style="float:left;margin-right: 40px"><span>发票类型：</span>
									 <select class="selectpicker bla bla bli "  id="dataType" name="dataType" style="width: 200px;" >
										<option value="">--请选择--</option>
										<option value="1" >进项</option>
										<option value="2">销项</option>
									 </select>
									</label>
									<button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
						<button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
						<button class="btn btn-success dropdown-toggle" style="display:none;" showname="EM_CREDITCUST_BTN_INVOICESYNC" onclick="syncInvoice();" data-toggle="dropdown">发票同步 </button>
						<button class="btn btn-success dropdown-toggle" style="display:none;" showname="EM_CREDITCUST_BTN_INVOICEEXPORT" onclick="exportInvoice();" data-toggle="dropdown">导出 </button>
						<button class="btn btn-success dropdown-toggle" style="display:none;" showname="EM_CREDITCUST_BTN_BACKCREDIT" onclick="backToCreditCustomer();" data-toggle="dropdown">返回授信客户管理 </button>
						<input type="hidden" id="companyPk" value="${creditExtDot.companyPk}"/>
							<input type="hidden" id="creditPk" value="${creditExtDot.pk}"/>
						</div>
             	</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">

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
                                <table id="creditInvoiceId" style="width: 100%"></table>
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