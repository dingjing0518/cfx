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
<link href="./style/css/logistics/orderDetail.css" rel="stylesheet" type="text/css" /> 
<script src="./pageJs/logistics/lgAbNormalOrder.js"></script>
<script src="./pageJs/logistics/lgOrderCommon.js"></script>
<script src="./pageJs/logistics/common.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<style>
	    .imgC {
	    	margin-right: 10px;
	        width: 80px;
	        height: 80px;
	        background: url(./style/images/bgbg.png) no-repeat;
	        background-size: 100%;
	    }
	</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">订单管理</a></li>
			<li class="active">异常订单</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">
		            <label class="col-lg-2 col-lg-3"><span>订单编号</span> <input class="form-controlgoods input-append " type="text" name="orderNumber"   value="" onkeydown="entersearch()"/></label>	
		              	<label class="col-lg-2 col-lg-3"><span>下单时间</span> <input class="form-controlgoods input-append date form-dateday form-dateday_start" type="text" name="orderTimeStart"   value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday form-dateday_end" type="text" name="orderTimeEnd"   value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span>配送时间</span> <input class="form-controlgoods input-append date form-dateday form-dateday_startcopy" type="text" name="arrivedTimeStart"   value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday form-dateday_endcopy" type="text" name="arrivedTimeEnd"   value="" readonly /></label>
				       	<label class="col-lg-2 col-lg-3"><span>商品搜索</span> <input class="form-controlgoods input-append " type="text" name="goodsInfo"   value=""   onkeydown="entersearch()"/></label>	     	
				      	<label class="col-lg-2 col-lg-3"><span>提货公司：</span> <input class="form-controlgoods input-append " type="text" name="fromCompanyName"   value="" onkeydown="entersearch()"/></label>
				      	<label class="col-lg-2 col-lg-3"><span>提货人号码</span> <input class="form-controlgoods input-append " type="text" name="fromContactsTel"   value="" onkeydown="entersearch()"/></label>	     	
		       			<label class="col-lg-2 col-lg-3"><span>送货公司</span> <input class="form-controlgoods input-append " type="text" name="toCompanyName"  value="" onkeydown="entersearch()"/></label>	     	
				        <label class="col-lg-2 col-lg-3"><span>收货人号码</span> <input class="form-controlgoods input-append " type="text" name="toContactsTel"   value="" onkeydown="entersearch()"/></label>	     	
			            <input type = "hidden" id="isConfirmed" name="isConfirmed"/>
			            <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      		<button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
		            	<button style="display:none;" showname="LG_ORDER_ORDERERROR_BTN_EXPORT" class="btn btn-success dropdown-toggle" onclick="excelOrder();" data-toggle="dropdown">导出 </button>
		             </header>
					<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="byIsConfirmed('');">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byIsConfirmed(1);">已确认</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byIsConfirmed(2);">未确认</a>
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
	
	<!-------------- 异常反馈 ---------------->
	<div class="modal fade" id="feedbackOrderId" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 750px;height:1500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">异常反馈</h4>
				</div>
				<input type = "hidden" id="deliveryId" name="deliveryId"/>
				<div class="modal-body">
						   <div class="cart_order">订单信息  </div>
						   <div class="cancel_receiver_info reciptinfo">
						      <span id="orderInfofb"></span> 
						   </div>
						   
						   <!--提货信息 -->
						   <div class="delivery_addree_info">
						     <div class="delivery_addree">
							     <label>提货信息</label>
								 <p>
								   <span id="fromCompanyNamefb"></span>                      
								   <span id="fromAddressfb"></span>                                  
								   <span id="fromContactsfb"></span>                     
								   <span id="fromContactsTelfb"></span>    
								 </p>
							 </div>
						   </div>
						   <div class="delivery_addree_info">
						     <div class="delivery_addree">
							     <label>收货信息</label>
								 <p>
								   <span id ="toCompanyNamefb"></span>                      
								   <span id="toAddressfb"></span>                                  
								   <span id="toContactsfb"></span>                     
								   <span id="toContactsTelfb"></span>    
								 </p>
							 </div>
						   </div>
						  <!--  <div class="delivery_addree_info" style="margin-right:0px;">
						     <div class="delivery_addree">
							     <label>发票信息</label>
								 <p>
								   <span id="invoiceTitlefb" ></span>
								   <span id="taxidNumberfb"></span>
								   <span id="regAddressfb"></span>  
								   <span id="bankNamefb"></span> 
								   <span id="bankAccountfb"></span>                      
								 </p>
							 </div>
						   </div> -->
						   <div class="cart_order">物流信息跟踪</div>
						   <div class="cancel_receiver_info reciptinfo" id = "trackDetailfb">
						   </div>
						   <div class="cart_order">订单信息</div>
						   <div class="paymethodn">
						     <ul style="line-height:30px !important;height:30px !important;">
						       <li style="line-height:30px; width: 270px;">商品信息</li>
						       <li style="line-height:30px; width: 270px;">数量(单位)</li>
						       <li style="line-height:30px; width: 270px;">重量(吨)</li>
						       <li style="line-height:30px; width: 270px;">单价(元/吨)</li>
						       <li style="line-height:30px; width: 270px;">运费总价(元/吨)</li>
						     </ul>
						     <ul style="line-height:30px !important;">
						       <li class="goodsdetail" style=" width: 270px;"id="goodsNamefb"></li>
						       <li class="goodspirce"style=" width: 270px;"id ="boxesfb"></li>
						       <li class="goodspirce"style=" width: 270px;" id="weightfb"></li>
						       <li class="goodspirce"style=" width: 270px;"id="presentFreightfb"></li>
							   <li class="goodspirce"style=" width: 270px;" id="presentTotalFreightfb"></li>
						     </ul>
						     <!-- <ul>
						       <div class="cart_order">备注</div>
						     </ul> -->
						  </div>
						  <div class="cart_order">备注</div>
						  <div class="cancel_receiver_info reciptinfo">
						      <span id="remark"></span> 
						   </div>
						 
							<div class="form-group col-ms-12" style="height: 90px; display: block; margin-top: 30px;">
								<label for="inputEmail3" class="col-sm-2 control-label"
									style="text-align: left;width:auto;">异常描述：</label>
								<div class="col-sm-5" style="width:450px;">
									<textarea class="form-control" id="abnormalRemark" style="height:90px;" maxlength="10"  readonly></textarea>
								</div>
							</div> 
							
						<div class="form-group col-ms-12" style="height: 100px; display: block; margin-top: 30px;">
						<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;;width:auto;">异常图片：</label>
							<div class="col-sm-10" id="imgs_div">
							
							</div>
						</div> 
						  <div class="backchart">
						 <div class="car_bt_tip paddingt20px " style = "float:right;">
						 
							<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
							<button type="button" class="btn btn-primary" id="queren"onclick="sureFeedback()">确定</button>
 						</div>
				 	 </div>
				</div>
			</div>
		</div>
	</div>	
	
<!-------------- END -------------------->

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
			 //$("#"+pk).attr("src",res.data);
			 setImg(res.data);
		}).fail(function(res) {
		});
	}







	 </script>
<script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
<script src="./pageJs/include/form-dateday.js"></script>