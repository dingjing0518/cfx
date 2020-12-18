<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>内部管理系统</title>
<link href="./pageJs/include/third-party/fancybox/fancybox.css" rel="stylesheet"
	type="text/css" />
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<script >
$(document).ready(function(){
	$("input[name='insertStartTime']").val(new Date().Format("yyyy-MM-dd"));
	$("input[name='insertEndTime']").val(new Date().Format("yyyy-MM-dd"));
});
</script>
<script src="./pageJs/economics/financeOrderM.js"></script>
<style>
label span{
width:100px;
}
.form-controlgoods{
width: 180px;
    float: left;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">订单管理</a></li>
			<li class="active">订单管理</li>
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
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>采购商名称：</span> <input class="form-controlgoods input-append " type="text" name="purchaserName"   value="" onkeydown="entersearch()"/></label>
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>供应商名称：</span> <input class="form-controlgoods input-append " type="text" name="supplierName"   value="" onkeydown="entersearch()"/></label>	     	
				     <!--   <label class="col-sm-6 col-md-4 col-lg-3"><span>采购商账号：</span> <input class="form-controlgoods input-append " type="text" name="mobile"   value="" /></label> -->
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>店铺：</span> <input class="form-controlgoods input-append " type="text" name="storeName"   value="" onkeydown="entersearch()"/></label>	     	
				       <label class="col-sm-6 col-md-4 col-lg-3"><span>订单来源：</span> <select class="form-controlgoods input-append " name="source" style="width: 180px;">
				       		<option value="">全部</option>
				       		<option value="1">pc端</option>
				       		<option value="2">移动端</option>
				       		<option value="3">app</option>
				       	</select></label>
				       	
				       	 <label class="col-sm-6 col-md-4 col-lg-3"><span>订单类型：</span> <select class="form-controlgoods input-append " name="purchaseType" style="width: 180px;">
				       		<option value="">全部</option>
				       		<option value="1">自采</option>
				       		<option value="2">代采</option>
				       		<option value="3">后台代采</option>
				       		<%--<option value="4">自拍</option>--%>
				       		<%--<option value="5">代拍</option>--%>
				     
				       	</select></label>	     	
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>下单时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>支付时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="paymentStartTime"   value="" readonly /></label>
			           <label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="paymentEndTime"   value="" readonly /></label>
					   <label class="col-sm-6 col-md-4 col-lg-3"><span>支付方式：</span> 
					   <select class="form-controlgoods input-append " name="paymentType" style="width: 180px;">
					   		<option value="">全部</option>
					   		<c:forEach items="${paymentList}" var="list">
					   			<option value="${list.type}">${list.name}</option>
					   		</c:forEach>
					   </select></label>	
			             <input type = "hidden" id="orderStatus" name="orderStatus"/>
			             <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      		<button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
			            <button style="display:none;" showname="EM_CREDIT_ORDER_ORDERMG_BTN_EXPORT" class="btn btn-default dropdown-toggle" onclick="excel();" data-toggle="dropdown"><span id="loadingExportData">导出</span></button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="byOrderStatus('');">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(0);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(1);">待付款</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(2);">待确认</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(3);">待发货</a>
                                </li>
                                   <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(4);">已发货</a>
                                </li>
                              
                                    <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(5);">部分发货</a>
                                </li>
                                 <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(6);">已完成</a>
                                </li>
                                 <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(-1);">已取消</a>
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
                                 <table id="orderId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="orderGoods" tabindex="-1" role="dialog"
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
			<!-- 	  <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findChild(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findChild(1);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findChild(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findChild(3);">审核不通过</a>
                                </li>
                            </ul>
                        </header> -->
					 <table id="goods" style="width: 100%"></table>
				</div>

			</div>
		</div>
	</div>
	
	

<div class="modal fade" id="payMentVoucherId" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 25%;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">上传付款凭证</h4>
				</div>
				<input type = "hidden" id="orderNumberId" name="orderNumberId"/>
				<div class="modal-body">
				<form   id="dataForm">
					<div class="form-group col-ms-12" style="height:90px;width:100px; display: block;">
						<img height="80" width="80" id="payUrl" class="imgC" title="付款凭证" onclick="op(this.src)"/>
					</div>
					  <div class="form-group col-ms-12"
						style="height:100%;width:100%; display: block;">
						<label for="inputEmail3" class="col-sm-12 control-label"
							style="text-align: left;">上传付款凭证</label>
						<div class="">
							<form enctype="multipart/form-data">
	                        <input type="file" name="file"  onchange="fileChange(this,'payUrl');"  required="true"/>
			                </form>
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
</body>
</html>
<script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	searchTabs();  
       }  
} 	
</script>
<!-- 图片放上去显示js -->
	<script type="text/javascript">
	
	 function op(c_url){
		 if( c_url.indexOf(".jpg") !=-1 
				 || c_url.indexOf(".png") !=-1
				 || c_url.indexOf(".jpeg") !=-1){//判断是否存在图片
			 window.open(c_url); 
		 }
	 }
	
	//图像默认图片
	function errorImg(img) {
	    img.src = "./style/images/bgbg.png";
	    img.onerror = null;
	};
	$(function(){
		$('.bigpicture').bigic();
	});
		/* function showImg1(){
		document.getElementById("wxImg1").style.display='block';
		}
		function hideImg1(){
		document.getElementById("wxImg1").style.display='none';
		} */
	</script>
	<!-- 汉化inputfile-->
	<script>
	function startUploading(self,key){
		var formData = new FormData();
		formData.append( "file", $(self).prev()[0].files[0]);
		$.ajax({
		    url: getRootPath() + "/uploadPhoto.do",
		    dataType: "json",
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			 $("#"+key).attr("src",res.data);
		}).fail(function(res) {
			
		});
	}
	function fileChange(self,pk){
		var formData = new FormData();
		formData.append( "file", $(self)[0].files[0]);
		$.ajax({
		    url: getRootPath() + "/uploadPhoto.do",
		    dataType: "json",
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			 $("#"+pk).attr("src",res.data);
		}).fail(function(res) {
		});
	}
	 </script>
<script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
<script src="./pageJs/include/form-dateday.js"></script>