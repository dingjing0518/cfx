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
<script src="./pageJs/economics/creditOrder.js"></script>
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
			<li><a href="#">授信管理</a></li>
			<li><a href="#">订单管理</a></li>
			<li class="active">借款单管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">
			            <label class="col-sm-6 col-md-4 col-lg-3"><span>借款单号：</span> <input class="form-controlgoods input-append " type="text" name="orderNumber"   value="" onkeydown="entersearch()"/></label>
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>合同编号：</span> <input class="form-controlgoods input-append " type="text" name="contractNumber"   value="" onkeydown="entersearch()"/></label>	     	
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>借据编号：</span> <input class="form-controlgoods input-append " type="text" name="loanNumber"   value="" onkeydown="entersearch()"/></label>
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>采购商：</span> <input class="form-controlgoods input-append " type="text" name="purchaserName"   value="" onkeydown="entersearch()"/></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>放款开始时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="loanStartTimeBegin"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="loanStartTimeEnd"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>放款到期时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="loanEndTimeBegin"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="loanEndTimeEnd"   value="" readonly /></label>

 						<label class="col-sm-6 col-md-4 col-lg-3"><span>金融产品：</span> 	
				            <select name="economicsGoodsName" class="form-controlgoods input-append ">
								   <option value="">--请选择--</option>
									<c:forEach items="${economicsGoodsList }" var="m">
									<option value="${m.name}">${m.name }</option>
									</c:forEach>  
							 </select>
					    </label>
					
						<label class="col-sm-6 col-md-4 col-lg-3"><span>借贷银行：</span>
							<select name="bankPk" class="form-controlgoods input-append ">
								<option value="">--请选择--</option>
								<c:forEach items="${economicsBanksList }" var="m">
									<option value="${m.pk}">${m.bankName }</option>
								</c:forEach>
							</select>
						</label>
							<label class="col-sm-6 col-md-4 col-lg-3" ><span>是否逾期：</span>
							<select  name="isOverdue"  class="form-controlgoods input-append ">
								<option value="">全部</option>
								<option value="1">正常</option>
								<option value="2">逾期</option>
							</select>
						</label>
			             <input type = "hidden" id="loanStatus" name="loanStatus"/>
			             <input type = "hidden" id="repaymentStatus" name="repaymentStatus"/>
			             <button class="btn btn-primary" onclick="searchTabs(1)"> 搜索</button>
			      		<button class="btn btn-primary"  onclick="searchTabs(2)"> 清除</button>
			          <button style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_EXPORT" class="btn btn-default dropdown-toggle" onclick="exportCreditOrder();" data-toggle="dropdown">导出 </button>
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
                                 <table id="creditOrderId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>

	
	<div class="modal fade" id="repaymentwinds" tabindex="-1" role="dialog" aria-labelledby="modelLabel">
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
	
	
 <div class="modal fade" id="creditOrderDetail" tabindex="-1" role="dialog" aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 1140px;max-height: 660px;overflow-y: auto;height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">还款订单详情</h4>
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
							<li><p>借据编号</p><p class="right" id= "loanNumber"></p><p>借款单号</p><p class="right" id = "orderNumberCopy"></p></li>
							<li><p>合同编号</p><p class="right" id = "contractNumber"></p><p>总利率</p><p class="right" id = "totalRate"></p></li>
							<li><p>借贷银行</p><p class="right" id = "bankName"></p><p>服务费利率</p><p class="right" id = "platformRate"></p></li>
							<li><p>放款总金额（元）</p><p class="right" id="loanAmount"></p><p>已还本金（元）</p><p class="right" id = "principal"></p></li>
							<li><p>放款时间</p><p class="right" id = "loanStartTime"></p><p>已还本金服务费</p><p class="right" id = "repaidSerCharge"></p></li>
							<li><p>银行利率（%）</p><p class="right" id="bankRate"></p><p>未还本金平台服务费</p><p class="right" id = "repayingSerCharge"></p></li>
							<li><p>已还银行利息（元）</p><p class="right" id = "repaidInterest"></p><p>还款时间</p><p class="right" id = "repaymentTime"></p></li>
							<li><p>未还银行利息（元）</p><p class="right" id = "repayingInterest"></p><p>客户应还金额</p><p class="right" id = "amountPayable"></p></li>
							<li><p>放款状态</p><p class="right" id = "loans"></p><p id = "sevenRateP">7天利率</p><p class="right" id = "sevenRate"></p></li>
							</ul>
						</div>
					</div>
					<div class="table-title"><p>历史还款记录</p></div>
					 <table id="creditOrderDetailId" style="width: 100%"></table>
				</div>

			</div>
		</div>
	</div> 
	
	 <div class="modal fade" id="sureLoan" tabindex="-1" role="dialog" aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 400px;max-height: 660px;overflow-y: auto;height: auto;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" >贷款申请</h4>
				</div>
				<input type="hidden" id="orderNumberSureLoan"/>	
				<div class="modal-body">
					<p>确认该笔订单当前贷款已发放</p>
				</div>
				<div class="modal-footer">
					<button type="button" style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_SURELOAN_BTN_LOANSUCCESS"  class="btn btn-primary" onclick="apiSureLoan(1);">放款成功</button>
					<button type="button" style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_SURELOAN_BTN_LOANFAIL" class="btn btn-primary" onclick="apiSureLoan(2);">放款失败</button>
					<button type="button" class="btn btn-default" id="quxiaoSure" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div> 


	<div class="modal fade" id="repayLoan" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:930px;top:20%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">
					还款申请
				</h4>
			</div>
			
			<input type="hidden" id="orderNumberRepay"/>	
			<div class="modal-body">
				<ul class="hksq-table">
					<li><p>借款单号：</p><p style="text-align: left;" id = "loanNumberRepay"></p></li>
					<li><p>采购商：</p><p style="text-align: left;width:180px;" id = "purchaserNameRepay"></p></li>
					<li><p>借贷银行：</p><p style="text-align: left;" id = "bankNameRepay"></p></li>
				</ul>
				<ul class="hksq-table">
					<li><p>借贷状态：</p><p style="text-align: left;" id = "loansRepay"></p></li>
					<li><p>放款金额：</p><p style="text-align: left;" id = "loanAmountRepay"></p></li>
					<li><p>放款时间：</p><p style="text-align: left;width:150px;" id = "loanStartTimeRepay"></p></li>
				</ul>
				<ul class="hksq-table">
					<li><p>已还本金：</p><p style="text-align: left;" id = "principalRepay"></p></li>
					<li><p>已还利息：</p><p style="text-align: left;" id = "interestRepay"></p></li>
				</ul>
				<ul class="hksq-table">
					<li><p>本次还款本金：</p><input id = "nowAmountRepay"name="" style="width:120px;" oninput= "changeInterest(this.value)" ></li>
					<li><p>本次还款利息：</p><input id = "nowInterestRepay" type="" name="" style="width:120px;"> </li>
				</ul>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick = "apiRepayLoan()">确认还款</button>
				<button type="button" class="btn btn-default" data-dismiss="modal" id = "quxiaoRepay">取消</button>
				
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
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
	
	
	  function changeInterest(amount){
		  getRepayLoan($("#loanNumberRepay").html(),$("#nowAmountRepay").val());  	
	    }
	    
</script>
<script src="./pageJs/include/form-dateday.js"></script>