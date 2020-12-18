<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>内部管理系统</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">

</c:import>
<script src="./pageJs/fiberLoanOrder.js"></script>
<style>
label span{
width:100px;
}
.form-controlgoods{
width:200px;
float:left;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">授信管理</a></li>
			<li class="active">化纤贷订单</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">
			            <label class="col-sm-6 col-md-4 col-lg-3"><span>订单编号：</span> <input class="form-controlgoods input-append " type="text" name="orderNumber"   value="" onkeydown="entersearch()"/></label>	
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>合同编号：</span> <input class="form-controlgoods input-append " type="text" name="contractNumber"   value="" onkeydown="entersearch()"/></label>	     	
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>借据编号：</span> <input class="form-controlgoods input-append " type="text" name="iouNumber"   value="" onkeydown="entersearch()"/></label>
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>操作人：</span> <input class="form-controlgoods input-append " type="text" name="mobile"   value="" onkeydown="entersearch()"/></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>放款开始时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="loanStartTimeBegin"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="loanStartTimeEnd"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>放款到期时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="loanEndTimeBegin"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="loanEndTimeEnd"   value="" readonly /></label>
			             <input type = "hidden" id="loanStatus" name="loanStatus"/>
			             <input type = "hidden" id="repaymentStatus" name="repaymentStatus"/>
			             <button class="btn btn-primary" onclick="searchTabs(1)"> 搜索</button>
			      		<button class="btn btn-primary"  onclick="searchTabs(2)"> 清除</button>
			          <button style="display:none;" showname="BTN_EXPORTCREDITORDER" class="btn btn-default dropdown-toggle" onclick="exportCreditOrder();" data-toggle="dropdown">导出 </button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="byOrderStatus('');">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(1);">待申请</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(2);">申请中</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(3);">申请成功</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(4);">申请失败</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(5);">已还款</a>
                                </li>
                                 <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(6);">部分还款</a>
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
                                 <table id="fiberLoanOrderId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	
	 
	<div class="modal fade" id="creditOrderwinds" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 1300px;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" id="quxiao"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">分配银行</h4>
				</div>
				<input type="hidden" id="parentPk"/>			
				
				<div class="modal-body">
				  <header class="panel-heading custom-tab ">
                       
                        </header>
			    <input type="hidden" id="orderid"/>
					 <table id="banks" style="width: 100%"></table>
				</div>

			</div>
		</div>
	</div>
	
	
	<div class="modal fade" id="repaymentwinds" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 80%;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">商品列表</h4>
				</div>
				<input type="hidden" id="parentPk"/>			
				<div class="modal-body">
					 <table id="repaymentRecord" style="width: 100%"></table>
				</div>

			</div>
		</div>
	</div>
</body>
</html>
    <script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	searchTabs(this);  
	       }  
	} 
</script>
<script src="./pageJs/include/form-dateday.js"></script>