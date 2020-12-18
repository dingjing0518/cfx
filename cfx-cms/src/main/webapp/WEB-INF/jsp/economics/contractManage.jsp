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
<script src="./pageJs/economics/contractManage.js"></script>
<style>
.form-controlgoods{
float:left;
width:200px;
}
label span{
width:100px;
}
@media (max-width:1400px){
.form-controlgoods{
	width:120px;
}
}
.text-danger{  color: #FF0000 ;  }
.text-track-display{
	padding: 0 10px;
	display: block;
	line-height: 25px;
}
span#contractTrack img {
	width: 120px;
	height: auto;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">授信管理</a></li>
			<li><a href="#">订单管理</a></li>
			<li class="active">合同管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>合同编号：</span> <input class="form-controlgoods input-append " type="text" name="contractNo"   value=""/></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>下单时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeStart"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>店铺：</span> <input class="form-controlgoods input-append " type="text" name="storeName"   value="" /></label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>支付时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="payTimeStart"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="payTimeEnd"   value="" readonly /></label>
 						<label class="col-sm-6 col-md-4 col-lg-3"><span>支付方式：</span>
				            <select name="paymentType" class="form-controlgoods input-append ">
								   <option value="">--请选择--</option>
									<c:forEach items="${paymentList }" var="m">
									<option value="${m.type}">${m.name }</option>
									</c:forEach>  
							 </select>
					    </label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>金融产品：</span>
							<select name="economicsGoodsName" class="form-controlgoods input-append ">
								<option value="">--请选择--</option>
								<c:forEach items="${economicsGoodsList }" var="m">
									<option value="${m.name}">${m.name }</option>
								</c:forEach>
							</select>
						</label>
			             <input type = "hidden" id="contractStatus" name="contractStatus"/>
			             <input type = "hidden" id="contractHiddenId" name="contractHiddenId"/>
						 <input type = "hidden" id="block" name="block" value="${block}"/>
			             <button class="btn btn-primary" onclick="searchTabs(1)"> 搜索</button>
			      		<button class="btn btn-primary"  onclick="searchTabs(2)"> 清除</button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="byOrderStatus('');">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(1);">待付款</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(2);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(3);">待发货</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(4);">部分发货</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(5);">待收货</a>
                                </li>
                                 <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(6);">已完成</a>
                                </li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(-1);">已关闭</a>
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
                                 <table id="contractId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="contractGoodsWinds" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 80%;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">合同商品</h4>
				</div>
				<input type="hidden" id="parentPk"/>			
				<div class="modal-body">
					 <table id="contractGoodsId" style="width: 100%"></table>
				</div>

			</div>
		</div>
	</div>
	<div class="modal fade" id="creditCustomerReason" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">关闭合同</h4>
				</div>
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk" name="pk" />

						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label"><i class="text-danger">*</i>&nbsp;关闭原因</label>
							<div class="col-sm-8">
								<input class="form-control" type="text" maxlength="50" id="closeReason" name="closeReason"  placeholder="请输入关闭原因" value="" />
							</div>
						</div>
					</form>
					<!-- 结束 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
							data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="submitId"
							onclick="submitClose();">确定</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="creditCustomerTrack" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="customerTrack">合同追踪</h4>
				</div>
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataFormTrack">
						<div class="form-group text-track-display">
							<span id="contractTrack"></span>

						</div>
					</form>
					<!-- 结束 -->
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
<script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>