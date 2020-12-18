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
<script src="./pageJs/operation/lottery/lotteryManage.js"></script>
<style>
.modal-backdrop.in{z-index:100;}
.modal{z-index:1000;}
.bootstrap-select > .dropdown-toggle{float:none;}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">会员活动</a></li>
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
					     <label class="col-lg-2 col-lg-3"><span>&nbsp;是否启用：</span> 
			 				<select  name="isVisable" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">启用</option>
					            	<option  value="2">禁用</option>
			   				</select>
			   			</label>
			   			<label class="col-lg-2 col-lg-3"><span>活动时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="startTimeStr"   value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="endTimeStr"   value="" readonly /></label>
			           	
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button  style="display:none;" showname="OPER_ACTIVITY_SETTINGS_BTN_ADD" class="btn btn-primary" id="btn" onclick="insertLottery();">新增活动</button>
		            	<!--  <button style="display:none;" showname="BTN_ADDLOTTERYACTIVITY" class="btn btn-primary" id="btn" onclick="insertLottery();">新增</button> -->
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
                                <table id="lotteryManageId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editLotteryId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">编辑活动</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />

						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">活动名称:</label>
							<div class="col-sm-6">
							<input type="text" class="form-control" id="name" name="name" style="width:280px;margin-left:0px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">活动类型:</label>
							<div class="col-sm-6">
							<select  id="activityType" name="activityType" class="form-control bla bla bli"  style="width:280px;margin-left:0px" data-live-search="true" required="true" onchange="changeLottryType()">
				          	 	<option value="">--请选择--</option>
				            	<option value="1">抽奖</option>
				            	<option value="2">白条新客户见面礼</option>
								<option value="3">白条老客户尊享礼</option>
		   					</select>
							</div>
						</div>
						<div id ="isShow">
							<div class="form-group col-ms-12"
								style="height: 30px; display: block;">
								<label for="inputEmail3" class="col-sm-2 control-label"
									style="text-align: left;">最高获奖次数:</label>
								<div class="col-sm-6">
								<input type="text" class="form-control" id="maximumNumber" name="maximumNumber" style="width:280px;margin-left:0px"/>
								</div>
								<div class="col-sm-4">
								(不填写，默认不限制)
								</div>
							</div>
							<div class="form-group col-ms-12"
								style="height: 30px; display: block;" id="isShowRole">
								<label for="inputEmail3" class="col-sm-2 control-label"
									style="text-align: left;">绑定角色:</label>
								<div class="col-sm-6">
								<select  id="bindRole" name="bindRole" class="form-control bla bla bli"  style="width:280px;margin-left:0px" data-live-search="true">
									<option value="">--请选择--</option>
									<option value="1">公司</option>
									<option value="2">个人</option>
								</select>
								</div>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="startTime" class="col-sm-2 control-label"
								style="text-align: left;">开始时间:</label>
							<div class="col-sm-9">
							<input type="text" class="form-control input-append date form_datetime" id="startTime" name="startTime" autocomplete="off" style="width: 280px;float: none;margin-left:0px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">结束时间:</label>
							<div class="col-sm-9">
							<input type="text" class="form-control input-append date form_datetime" id="endTime" name="endTime" autocomplete="off" style="width: 280px;float: none;margin-left:0px" required="true" />
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
	btnList();
    $(function(){
        $('.bigpicture').bigic();
    });
    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        language:'zh-CN',
        pickerPosition:"bottom-right"
    });
</script>
	<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>