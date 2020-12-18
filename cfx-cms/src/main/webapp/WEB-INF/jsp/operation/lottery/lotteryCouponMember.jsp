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
<script src="./pageJs/operation/lottery/lotteryCouponMember.js"></script>
<style>
.bootstrap-select > .dropdown-toggle{float:none;}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">优惠券管理</a></li>
			<li class="active">优惠券列表</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					    <label class="col-sm-6 col-md-4 col-lg-3"><span>发放时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeStart"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;优惠券种类：</span> 
			 				<select  name="couponType" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">免息券</option>
					            	
			   				</select>
			   			</label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>券编码：</span> <input class="form-controlgoods input-append " type="text" name="couponNumber"   value="" /></label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;借贷状态：</span> 
			 				<select  name="loanStatus" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">待申请</option>
					            	<option  value="2">申请中</option>
					            	<option  value="3">申请成功</option>
					            	<option  value="4">申请失败</option>
					            	<option  value="5">已还款</option>
					            	<option  value="6">部分还款</option>
			   				</select>
			   			</label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>获券人姓名：</span> <input class="form-controlgoods input-append " type="text" name="memberName"   value="" /></label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;返款状态：</span> 
			 				<select  name="backStatus" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">未返款</option>
					            	<option  value="2">已返款</option>
			   				</select>
			   			</label>
			            <input type="hidden" id="isStatus" name="isStatus" />
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button style="display:none;" showname="BTN_EXPORTCOUPONMEMBER_FORM" class="btn btn-default dropdown-toggle" onclick="exportCouponMember();" data-toggle="dropdown"><span id="loadingExportCouponMemberData">导出</span></button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findCouponMember(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findCouponMember(1);">未使用</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findCouponMember(2);">已使用</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findCouponMember(3);">赠送中</a>
                                </li>
                               <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findCouponMember(4);">已赠送</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findCouponMember(5);">已过期</a>
                                </li>
                            </ul>
                        </header>
                        <div class="bootstrap-table" style="margin-top:10px;">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
    
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="lotteryCouponMemberTableId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editLotteryInvitationRecordId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">优惠券使用</h4>
				</div>

				<div class="modal-body">
			<form id="dataPutForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">&nbsp;使用状态:</label>
							<div class="col-sm-10">
							<select class="form-control col-sm-2" id="editStatus" name="editStatus" style="width:280px;margin-left:24px" required="true">
							<option value="">--请选择--</option>
						 	<option value="1">未使用</option>
						 	<option value="2">已使用</option>
						 	<option value="3">赠送中</option>
						 	<option value="4">已赠送</option>
						 	<option value="5">已过期</option>
							</select>	
							</div>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitPutCoupon();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="showLotteryCouponMemberDetailId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="couponMemberDetailInfo">详情信息</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="detailCpoponMemberPk" />
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">使用状态:</label>
							<div class="col-sm-5" style="width:210px;">
							<select class="form-control col-sm-3" id="detailStatus" name="detailStatus" disabled="disabled">
									
							</select>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">订单号:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailOrderNumber"  name="detailOrderNumber" required="true"  readonly="readonly"/>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">订单状态:</label>
							<div class="col-sm-5" style="width:210px;">
							<select class="form-control col-sm-3" id="detailorderStatus" name="detailorderStatus" disabled="disabled">
									
							</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">券编码:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailcouponNumber"  name="detailcouponNumber" required="true"  readonly="readonly"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">借贷状态:</label>
							<div class="col-sm-5" style="width:210px;">
							<select class="form-control col-sm-3" id="detailloanStatus" name="detailloanStatus" disabled="disabled">
									
							</select>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">申请返款:</label>
							<div class="col-sm-5" style="width:210px;">
							<select class="form-control col-sm-3" id="detailapplyStatus" name="detailapplyStatus" disabled="disabled">
									
							</select>
							</div>
						</div>
						
					<div class="form-group col-ms-12" id="couponGiveMemberId">
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">赠送人姓名:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailoMemberName"  name="detailoMemberName"  readonly="readonly"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">被赠送人姓名:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailmemberName"  name="detailmemberName"   readonly="readonly"/>
							</div>
						</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">返款方式:</label>
							<div class="col-sm-5" style="width:210px;">
							<select class="form-control col-sm-3" id="detailbackType" name="detailbackType" onchange="changeBackType();">
								<option value="">--请选择--</option>
							 	<option value="1">支付宝</option>
							 	<option value="2">银行卡</option>
							</select>
							</div>
						</div>
					<!-- 银行显示内容Start -->
				<div class="form-group col-ms-12" id ="bankInfo">	
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">开户姓名:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailbankAccountName"  name="detailbankAccountName" required="true" />
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">开户银行:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailbankName"  name="detailbankName" required="true"/>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">开户支行:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailbranchName"  name="detailbranchName" required="true"/>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">银行卡号:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailbankCard"  name="detailbankCard" required="true" />
							</div>
						</div>
					</div>	
					<!-- 银行显示内容End -->	
					
					<!-- 支付宝显示内容显Start -->
				<div class="form-group col-ms-12" id ="aliPayInfo">	
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">账号姓名:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailalipayName"  name="detailalipayName" required="true"/>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">支付宝账号:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailalipayAccount"  name="detailalipayAccount" required="true" />
							</div>
						</div>
					<!-- 支付宝显示内容显End -->	
				</div>	
				<div id="isBackAmountId">
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">返款金额:</label>
							<div class="col-sm-9">
								<input type="text" id="detailBackAmount"  name="detailBackAmount" class="form-control" readonly="readonly"/>
							</div>
						</div>
					<!-- <div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">返款时间:</label>
						<div class="col-sm-9">
						    <input type="text" class="form-control input-append date form-dateday" style="width: 200px;float: left;margin-left:0px" id="detailrefundTime" name="detailrefundTime" required="true"/> 
						</div>
					</div> -->
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">返款时间:</label>
							<div class="col-sm-5" style="width:210px;">
							<input type="text" class="form-control input-append date form-dateday" id="detailrefundTime" name="detailrefundTime" style="width: 200px;float: none;margin-left:0px" required="true" />
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;"> 
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">返款状态:</label>
							<div class="col-sm-5" style="width:210px;">
							<select class="form-control col-sm-2" id="detailbackStatus" name="detailbackStatus" required="true">
									
							</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;"><span id="detailthridNumberTitle">交易号:</span></label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="detailthridNumber"  name="detailthridNumber" required="true" />
							</div>
						</div>
					</div>	
						 <!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiaoCouponMember"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitDetail();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 赠送页面 -->
	<div class="modal fade" id="editGiveCouponInfoId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">赠送信息</h4>
				</div>
				<div class="modal-body">
					<form id="dataEditForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" id="parentPk" /><!-- 原会员优惠券Pk --> 
					<!--<input type="hidden" id="oMemberPk" />原获券人pk 
					<input type="hidden" id="omemberName" />原获券人名称 
					<input type="hidden" id="givecouponAmount" />
					<input type="hidden" id="givename" />
					<input type="hidden" id="givecouponPk" /> -->
					
					<input type="hidden" id="givememberPk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">使用状态:</label>
							<div class="col-sm-5" style="width:210px;">
							<select class="form-control col-sm-3" id="giveStatus" name="giveStatus" disabled="disabled">
									
							</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">订单号:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="giveorderNumber"  name="giveorderNumber" required="true"  readonly="readonly"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">订单状态:</label>
							<div class="col-sm-5">
								<select class="form-control col-sm-3" id="giveorderStatus" name="giveorderStatus" disabled="disabled">		
								</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">券编码:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="givecouponNumber" name="givecouponNumber"   required="true" readonly="readonly"/>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">借贷状态:</label>
							<div class="col-sm-5">
								<select class="form-control col-sm-3" id="giveloanStatus" name="giveloanStatus" disabled="disabled">		
								</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">公司名称:</label>
							<div class="col-sm-6">
								<select class="form-control selectpicker col-sm-3" id="givecompanyName" name="givecompanyName" data-live-search="true" >		
								<option value="">--请选择--</option>
								</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">受赠人姓名:</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="givememberName"  name="givememberName" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">受赠人电话:</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="givemmobile"  name="givemmobile" required="true" />
							</div>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiaogiveCoupon"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitgiveCoupon();">审核</button>
				</div>
			</div>
		</div>
	</div>
	    <script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	SearchClean();  
       }  
} 
</script>
<script>
	 var now=new Date();
	 var strTime=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();
		$(function(){
			$('.bigpicture').bigic();
		});
	    $(".form_datetime").datetimepicker({
	      format: "hh:ii",
	      startView:1,
	      autoclose: true,
	      todayBtn: true,
	      language:'zh-CN',
	      startDate:strTime,
	      pickerPosition:"bottom-right"
	    });
    </script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>