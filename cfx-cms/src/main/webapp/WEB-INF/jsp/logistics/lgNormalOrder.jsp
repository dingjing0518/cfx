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
<script src="./pageJs/logistics/lgNormalOrder.js"></script>
<script src="./pageJs/logistics/lgOrderCommon.js"></script>
<script src="./pageJs/logistics/common.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
<style>
    .imgC {
        width: 80px;
        height: 80px;
        background: url(./style/images/bgbg.png) no-repeat;
        background-size: 100%;
    }
    .bootstrap-select > .dropdown-toggle{width:203px;}
</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">订单管理</a></li>
			<li class="active">正常订单</li>
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
				       	<label class="col-lg-2 col-lg-3"><span>商品搜索</span> <input class="form-controlgoods input-append " type="text" name="goodsInfo"   value=""  onkeydown="entersearch()"/></label>	     	
				      	<label class="col-lg-2 col-lg-3"><span>提货公司</span> <input class="form-controlgoods input-append " type="text" name="fromCompanyName"   value="" onkeydown="entersearch()"/></label>
				      	<label class="col-lg-2 col-lg-3"><span>提货人号码</span> <input class="form-controlgoods input-append " type="text" name="fromContactsTel"   value="" onkeydown="entersearch()"/></label>	     	
		       			<label class="col-lg-2 col-lg-3"><span>送货公司</span> <input class="form-controlgoods input-append " type="text" name="toCompanyName"  value="" onkeydown="entersearch()"/></label>	     	
				        <label class="col-lg-2 col-lg-3"><span>收货人号码</span> <input class="form-controlgoods input-append " type="text" name="toContactsTel"   value="" onkeydown="entersearch()"/></label>	     	
			            <input type = "hidden" id="orderStatus" name="orderStatus"/>
			            <input type = "hidden" id ="isMatched" name = "isMatched"/>
			            <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      		<button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
		            	<button style="display:none;" showname="LG_ORDER_NORMAL_BTN_EXPORT" class="btn btn-success dropdown-toggle" onclick="excelOrder();" data-toggle="dropdown">导出 </button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="byOrderStatus(1);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(0);">未匹配</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(9);">待付款</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(8);">待财务确认</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(6);">待指派车辆</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(5);">提货中</a>
                                </li>
                                <li class="">
                                  	<a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(4);">配送中</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(3);">已签收</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(2);">已取消</a>
                                </li>
                                 <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="byOrderStatus(10);">已关闭</a>
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

	<div class="modal fade" id="detailOrderId" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 1000px;height:1500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">订单详情</h4>
				</div>
				<input type = "hidden" id="orderNumberId" name="orderNumberId"/>
				<div class="modal-body"  id = "detail-modal-body">
						   <div class="cart_order">订单信息  </div>					
						   <div class="cancel_receiver_info reciptinfo">
						      <span id="orderInfo"></span> 
						   </div>
						   
						   <!--提货信息 -->
						   <div class="delivery_addree_info">
						     <div class="delivery_addree">
							     <label>提货信息</label>
								 <p>
								   <span id="fromCompanyName"></span>                      
								   <span id="fromAddress"></span>                                  
								   <span id="fromContacts"></span>                     
								   <span id="fromContactsTel"></span>    
								 </p>
							 </div>
						   </div>
						   <div class="delivery_addree_info">
						     <div class="delivery_addree">
							     <label>收货信息</label>
								 <p>
								   <span id ="toCompanyName"></span>                      
								   <span id="toAddress"></span>                                  
								   <span id="toContacts"></span>                     
								   <span id="toContactsTel"></span>    
								 </p>
							 </div>
						   </div>
						   <div class="delivery_addree_info" style="margin-right:0px;">
						     <div class="delivery_addree">
							     <label>发票信息</label>
								 <p>
								   <span id="invoiceTitle" ></span>
								   <span id="taxidNumber"></span>
								   <span id="regAddress"></span>  
								   <span id="bankName"></span> 
								   <span id="bankAccount"></span>                      
								 </p>
							 </div>
						   </div>
						   <div class="cart_order">物流信息跟踪</div>
						   		<div class="cancel_receiver_info reciptinfo" id = "trackDetail">
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
						       <li class="goodsdetail" style=" width: 270px;"id="goodsName"></li>
						       <li class="goodspirce"style=" width: 270px;"id ="boxes"></li>
						       <li class="goodspirce"style=" width: 270px;" id="weight"></li>
						       <li class="goodspirce"style=" width: 270px;"id="presentFreight"></li>
							   <li class="goodspirce"style=" width: 270px;" id="presentTotalFreight"></li>
						     </ul>
						  </div>
						 <div class="cart_order">备注</div>
						  <div class="cancel_receiver_info reciptinfo">
						      <span id="remark"></span> 
						   </div>
						   
						  <div class="cartcount paddingt20px fr">
						    <i><p>运输箱数</p><span id ="boxesCopy" style= "color:red;"></span></i>
						    <i><p>运输重量</p><span id="weightCopy" style= "color:red;"></span></i>
						    <i><p>运输单价</p><span id="presentFreightCopy" style= "color:red;"></span></i>
							<i><p>运输总价</p><span id="presentTotalFreightCopy" style= "color:red;"></span></i>
						    <i class="linetop" style="float: left;"><p>实付金额</p><span class="color_red size18px" style="float: right; color:red;"id="presentTotalFreightFact"> </span></i>
						  </div>
						
						  <div class="backchart">
						 <div class="car_bt_tip paddingt20px " style = "float:right;">
							<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
 						</div>
				 	 </div>
				</div>
				<!--  <div class="modal-footer" >
					<button type="button" class="" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>  -->
			</div>
		</div>
	</div>	
		<!---------------------- 推荐供应商 ----------------------->
	<div class="modal fade" id="pushOrderId" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 600px;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">推荐供应商</h4>
				</div>
				<input type = "hidden"  name="pk"/>
				<input type = "hidden" id= 'settleWeight' name="settleWeight"/>
				<input type = "hidden" id= 'linePricePk' name="linePricePk"/>
				<input type = "hidden" id= 'basisLinePrice' name="basisLinePrice"/>
				<input type = "hidden" id= 'originalFreight' name="originalFreight"/>
				
				<div class="modal-body">
					<div class="form-group col-ms-12" style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;width: auto;">物流供应商名称：</label>
						<div class="col-sm-5">
						<select  id="logisticsCompanyName" class="selectpicker bla bla bli"  data-live-search="true"  onchange="changeSelect(this.value)">
				          	 	<option value="">--请选择--</option>
				            	<c:forEach items="${lgCompanyList}" var="m">
				            	<option value="${m.pk}">${m.name }</option>
				           	    </c:forEach>  
		   				</select>
						</div>
					</div>	
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">对外价：</label>
						<div class="col-sm-5">
						    <input type="text" class="form-control" id="eTotalFreight" name="eTotalFreight"  readonly="readonly"/> 
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">结算价：</label>
						<div class="col-sm-5">
						    <input type="text" class="form-control" id="epresentTotalFreight" name="epresentTotalFreight"  readonly="readonly"/> 
						</div>
					</div>
			  	<div class="modal-footer" >
					<button type="button" class="btn btn-default" id="quxiao1" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitLogCompanyName();">保存</button>
				</div> 
			</div>
		</div>
	</div>	
	</div>
	<!-------------- END ------------------->
	
	<!-------------- 异常反馈 ---------------->
	<div class="modal fade" id="feedbackOrderId" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 1000px;height: 1500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">异常反馈</h4>
				</div>
				<input type = "hidden" id="orderNumberId" name="orderNumberId"/>
				<div class="modal-body" id="feedback-modal-body">
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
						   <div class="delivery_addree_info" style="margin-right:0px;">
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
						   </div>
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
						      <span id="remarkfb"></span> 
						   </div>

						 <div class="form-group col-ms-12" style="height: 90px; display: block; margin-top: 30px;">
								<label for="inputEmail3" class="col-sm-2 control-label"
									style="text-align: left;width:auto;">异常描述：</label>
								<div class="col-sm-5" style="width:450px;">
									<textarea class="form-control" id="abnormalRemark" style="height:90px;"  onKeyDown="LimitTextArea(this) "></textarea>
								</div>
							</div> 
						<!-- <a rel='group"+index+"' title='组织机构代码' href='"+row.ocUrl+"'>
						<img class='bigpicture' src='"+row.ocUrl+"' onerror='errorImg(this)'>
						</a> -->
						
					 	 <div class="form-group col-ms-12" style="height: 30px; display: block; margin-top: 30px;">
							<label for="inputEmail3" class="col-sm-12 control-label"
								style="text-align: left;width:auto;">上传图片:</label>
							<div class="col-sm-5">
								<form enctype="multipart/form-data">
		                        <input  type="file" name="file" id = "file" onchange="fileChange(this,'blUrl');"  />
		                    
				                </form>
							</div>
						</div>
						<div class="form-group col-ms-12" style="height: 30px; display: block; margin-top: 30px;">
							<div class="col-sm-5" style = "width: -webkit-fill-available;margin-top: 20px;" id="imgs_div">
							</div>
						</div> 
						  <div class="backchart">
						 <div class="car_bt_tip paddingt20px " style = "float:right;">
							<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
							<button type="button" class="btn btn-primary" onclick="submitFeedback();">保存</button>
 						</div>
				 	 </div>
				</div>
			</div>
		</div>
	</div>	
	
	<!-------------- END -------------------->
	
	<!----------- 编辑订单 ------------------->
	<div class="modal fade" id="editOrderId" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 750px;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">修改订单</h4>
				</div>
				<input type = "hidden"  id="editPk"/>
				<div class="modal-body">
					<form  id="editForm">
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">提货联系人：</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="editFromContacts" name="editFromContacts" required="true"  maxlength="20"/>
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">提货人手机号：</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="editFromContactsTel" name="editFromContactsTel"  />
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">提货地址：</label>
						<div class="col-sm-9" style="margin-left:-15px;">
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="province" name="province" onchange="changeCity(this);">
							<option value="">--省--</option>
						 	<c:forEach var="c" items="${province}">
									<option value="${c.pk }">${c.name}</option>
								</c:forEach>
							</select>	
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="city" name="city" onchange="changeArea(this);"><option value="">--市--</option></select>
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="area" name="area" onchange="changeTown(this);"><option value="">--区--</option></select>
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="town" name="town"  ><option value="">--镇--</option></select>
						</div>
						</div>
					</div> 
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">提货详细地址：</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="editFromAddress" name="editFromAddress"/>
						</div>  
					</div>	
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">收货联系人：</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="editToContacts" name="editToContacts" required="true"  maxlength="20"/>
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">收货人手机号：</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="editToContactsTel" name="editToContactsTel"  maxlength="11"/>
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">配送地址：</label>
						<div class="col-sm-9" style="margin-left:-15px;">
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="toProvince" name="toProvince" onchange="changeToCity(this);">
							<option value="">--省--</option>
						 	<c:forEach var="c" items="${province}">
									<option value="${c.pk }">${c.name}</option>
								</c:forEach>
							</select>	
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="toCity" name="toCity" onchange="changeToArea(this);"><option value="">--市--</option></select>
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="toArea" name="toArea" onchange="changeToTown(this);"><option value="">--区--</option></select>
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="toTown" name="toTown" ><option value="">--镇--</option></select>
						</div>
						</div>
					</div>         
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">配送详细地址：</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="editToAddress" name="editToAddress"/>
						</div>
					</div>     
					</form>  
				</div>
				
			  	<div class="modal-footer" >
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="saveEditOrder();">保存</button>
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
        var file = $(self).prev()[0].files[0];
		formData.append( "file", file);
        if(!isImg(file.name)){
            new $.flavr({
                modal : false,
                content : "上传的图片格式不正确（图片格式为.jpg、.png、.jpeg）！"
            });
            return;
        }
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
        var file = $(self)[0].files[0];
		formData.append( "file", file);

        if(!isImg(file.name)){
            new $.flavr({
                modal : false,
                content : "上传的图片格式不正确（图片格式为.jpg、.png、.jpeg）！"
            });
            return;
		}
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

	function isImg(url){

        if(url.indexOf(".jpg") !=-1
            || url.indexOf(".png") !=-1
            || url.indexOf(".jpeg") !=-1){
            return true;
		}else{
            return false;
		}

	}


	//可编辑图片
	function setImg(url) {
		var html = "<div style='position: relative;display: inline-block;margin-bottom:10px;'>"+
   		"<img class='imgC'  src='"+url+"' onclick='op(this.src)'> "+
		"<label class='delLabel'><a class='bulecolor' onclick='del(this)' style='position: absolute;right: 6px; top: 0px;'>删除</a></label></div>";
		
		$("#imgs_div").append(html);
	}
	
	// 删除图片
	function del(ctrl) {
		$(ctrl).parent().parent().remove();
	}
	
	
	
	 </script>
<script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
<script src="./pageJs/include/form-dateday.js"></script>