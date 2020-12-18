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
<script src="./pageJs/economics/bill/billPay.js"></script>
<style>
.form-controlgoods{
float:left;
width:200px;
}
label span{
width:100px;
}
.repaymentwinds-top p {
    display: inline-block;
    margin: 0px 20px;
}
.repaymentwinds-table {
    border: 1px solid #ddd;
    margin-top: 20px;
    margin-bottom: 30px;
    border-radius:4px;
    border-left: none;
    border-bottom: none;
}
.repaymentwinds-table ul {
    list-style: none;
    padding-left: 0px;
    margin-bottom:0px;
    border-radius:4px;
    
}
.repaymentwinds-table ul li:after{
display:table;
clear:both;
content:"";
}
.repaymentwinds-table ul li{
border-bottom:1px solid #ddd;
border-left: none;
border-bottom: none;
*zoom:1;
}
.repaymentwinds-table ul li:last-child{
border-bottom:none;
}
.repaymentwinds-table ul li p{
    margin-bottom: 0px;
    padding:0px 20px;
    border-bottom: 1px solid #ddd;
    border-left: 1px solid #ddd;
    width: 200px;
    height: 40px;
    line-height: 40px;
    background-color: #f9f9f9;
    word-break: break-all;
    overflow: hidden;
    display: table-cell;
    vertical-align: middle;
}
.repaymentwinds-table ul li p.right {
    background: none;
    width: 350px;
    line-height: 18px;
}
.repaymentwinds-table ul li p:last-child{
border-right:none;
}
.repaymentwinds-content{
margin-bottom:30px;
}
.repaymentwinds-table ul li:first-child p:first-child{
border-top-left-radius: 4px;
}
.repaymentwinds-table ul li:last-child p:last-child{
border-bottom-left-radius: 4px;
}
.table-title p {
    background: #f9f9f9;
    width: 100%;
    margin-bottom: 0px;
    text-align: center;
    height: 40px;
    line-height: 40px;
    border: 1px solid #ddd;
    border-bottom: none;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
}
.fixed-table-container {
    border-top-left-radius: 0px;
    -webkit-border-top-left-radius: 0px;
    -moz-border-top-left-radius: 0px;
    border-top-right-radius: 0px;
    -webkit-border-top-right-radius: 0px;
    -moz-border-top-right-radius: 0px;
}
@media (max-width:1400px){
.form-controlgoods{
	width:120px;
}
}

	ul.hksq-table {
		    list-style: none;
		    padding-left: 0px;
		    color:#666;
		    *zoom:1;
		}
		ul.hksq-table:after{
			display:table;
			content: "";
			clear:both;
		}
		.hksq-table li {
		    float: left;
		    width: 33%;
		}
		.hksq-table p {
	        float: left;
		    width: 98px;
		    text-align: right;
		    color: #333;
		}

</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">票据管理</a></li>
			<li class="active">票据支付记录</li>
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
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>支付流水号：</span> <input class="form-controlgoods input-append " type="text" name="serialNumber"   value="" onkeydown="entersearch()"/></label>	     	
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>采购商信息：</span> <input class="form-controlgoods input-append " type="text" name="purchaserName"   value="" onkeydown="entersearch()"/></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>票据交易时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeBegin"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
 						<label class="col-sm-6 col-md-4 col-lg-3"><span>票据产品：</span> <input class="form-controlgoods input-append " type="text" name="goodsName"   value="" onkeydown="entersearch()"/></label>
 						
			             <input type = "hidden" id="status" name="status"/>
			             <button class="btn btn-primary" onclick="searchTabs(1)"> 搜索</button>
			      		<button class="btn btn-primary"  onclick="searchTabs(2)"> 清除</button>
			          <button style="display:none;" showname="EM_ECONOMICS_BILL_PAYRECORD_BTN_EXPORT" class="btn btn-default dropdown-toggle" onclick="exportBillOrder();" data-toggle="dropdown">导出 </button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="byOrderStatus('');">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(1);">处理中</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(2);">成功</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(3);">失败</a>
                                </li>
                                  <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(4);">锁定</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(5);">完成处理中</a>
                                </li>
                             <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(-1);">取消处理中</a>
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
                                 <table id="billOrderId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>

	
	
	
	
 <div class="modal fade" id="billOrderDetail" tabindex="-1" role="dialog" aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 1140px;max-height: 660px;overflow-y: auto;height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">支付记录</h4>
				</div>
				<input type="hidden" id="parentPk"/>			
				<div class="modal-body">
					<div class="repaymentwinds-content">
						<div class="repaymentwinds-top">
							<p>采购商名称：<p id ="purchaserName"></p></p>
							<p>供应商名称：<p id ="supplierName"></p></p>
						</div>
						<div class="repaymentwinds-table" id = "clearForm">
							<ul>
							<li><p>订单编号</p><p class="right" id= "orderNumber"></p><p>支付流水号</p><p class="right" id = "serialNumber"></p></li>
							<li><p>支付状态</p><p class="right" id = "statusName"></p><p>订单交易金额（元）</p><p class="right" id = "amount"></p></li>
							<li><p>店铺名称</p><p class="right" id = "storeName"></p><p>票据产品</p><p class="right" id = "goodsName"></p></li>
							<li><p>票据产品英文简称</p><p class="right" id="goodsShotName"></p><p>票据交易时间</p><p class="right" id = "insertTime"></p></li>
							</ul>
						</div>
					</div>
					<div class="table-title"><p>支付记录</p></div>
					 <table id="billInventoryDetailId" style="width: 100%"></table>
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