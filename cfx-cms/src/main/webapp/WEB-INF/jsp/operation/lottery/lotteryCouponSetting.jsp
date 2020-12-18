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
<script src="./pageJs/operation/lottery/lotteryCouponSetting.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">优惠券管理</a></li>
			<li class="active">基本设置</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-lg-2 col-lg-3"><span>&nbsp;优惠券种类：</span> 
			 				<select  name="isVisable" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">免息券</option>
			   				</select>
			   			</label>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button style="display:none;" showname="BTN_ADDCOUPONSETTING" class="btn btn-primary" id="btn" onclick="insertLotteryCoupon();">新增</button>
		            	
		             </header>
				<div class="panel-body">
					<section class="panel">
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
                                <table id="lotteryCouponTableId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editLotteryCouponId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">优惠券编辑</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">优惠券种类:</label>
							<div class="col-sm-9">
							<select  id="couponType" name="couponType" class="form-control"  style="width:280px;margin-left:14px" data-live-search="true" required="true">
				          	 	<option value="">--请选择--</option>
				            	<option value="1">免息券</option>
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">金额(元):</label>
							<div class="col-sm-9">
							<input type="text" class="form-control" id="couponAmount" name="couponAmount" style="width:280px;margin-left:14px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">优惠券名称:</label>
							<div class="col-sm-9">
							<input type="text" class="form-control" id="name" name="name" style="width:280px;margin-left:14px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">使用方式:</label>
							<div class="col-sm-9">
							<select  id="useType" name="useType" class=" form-control"  style="width:280px;margin-left:14px" data-live-search="true" required="true">
				          	 	<option value="">--请选择--</option>
				            	<option value="1">金融白条支付</option>
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">有效期:</label>
							<div class="col-sm-2">
								<input type="text" class="form-control input-append date form-dateday" id="startTime" name="startTime" style="width:100px;margin-left:18px" />
							</div>
							<div class="col-sm-5">
								<div class="col-sm-1" style="width:20px;margin-left:24px;margin-right:14px;line-height:34px">至</div>
								<input type="text" class="form-control input-append date form-dateday" id="endTime" name="endTime" style="width:100px;margin-left:14px" />
							</div>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
						<form id="hideForm" style="display: none;"></form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
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
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>