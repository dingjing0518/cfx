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
<style>
@media (max-width:1540px){
.input-append-responsive{width:100px!important;}
}

</style>
<!-- 页面内容导航开始-->
<script src="./pageJs/economics/onlinePayRecord.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">线上支付</a></li>
			<li class="active">线上支付记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
								<label class="col-lg-2 col-lg-3"><span>订单号：</span> <input class="form-controlgoods input-append " type="text" name="orderNumber"   value="" onkeydown="entersearch()"/></label>	
			                    <label class="col-lg-2 col-lg-3"><span>采购商：</span> <input class="form-controlgoods input-append " type="text" name="purchaserName"   value="" onkeydown="entersearch()"/></label>	
								<label class="col-lg-2 col-lg-3"><span>供应商：</span> <input class="form-controlgoods input-append " type="text" name="supplierName"   value="" onkeydown="entersearch()"/></label>	
			     				<label class="col-lg-2 col-lg-3"><span>收款账户：</span> <input class="form-controlgoods input-append " type="text" name="receivablesAccount"   value="" onkeydown="entersearch()"/></label>	
			     				<label class="col-lg-2 col-lg-3"><span>产品：</span> 
			     				  <select class="form-controlgoods input-append input-append-responsive " name="onlinePayGoodsPk" style="width: 180px;">
			     					<option value="">全部</option>
							   		<c:forEach items="${goodsList}" var="list">
							   			<option value="${list.pk}">${list.name}</option>
							   		</c:forEach>
							   		</select>
			     				</label>
			      		<input type="hidden" id="status" name="status" />
			      	<button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      	<button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
		            </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="byStatus('');">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byStatus(1);">处理中</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byStatus(2);">成功</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byStatus(3);">失败</a>
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
                                 <table id="recordId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="ecnomicsBank" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑</h4>
				</div>
				<div class="modal-body">
				<form   id="dataForm">
							<input type="hidden" id="pk" name="pk" />
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-4 control-label"
							style="text-align: left;">银行名称</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="bankName" name="bankName" required="true"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-4 control-label"
							style="text-align: left;">网关地址</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="gateway" name="gateway" required="true"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-4 control-label"
							style="text-align: left;">授信总金额</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="creditTotalAmount" name="creditTotalAmount" required="true" oninput = "javascript:CheckInputIntFloat(this);"/>
						</div>
					</div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 图片放上去显示js -->
	<script type="text/javascript">
		function showImg1(){
		document.getElementById("wxImg1").style.display='block';
		}
		function hideImg1(){
		document.getElementById("wxImg1").style.display='none';
		}
	</script>
	<!-- 汉化inputfile-->
	 <script>
		$(function(){
			$('.bigpicture').bigic();
		});
    </script>
    <script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	searchTabs(this);  
	       }  
	} 
</script>
    <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>