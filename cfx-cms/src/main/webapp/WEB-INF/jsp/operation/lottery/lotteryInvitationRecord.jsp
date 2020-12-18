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
<script src="./pageJs/operation/lottery/lotteryInvitationRecord.js"></script>
<style>
.bootstrap-select > .dropdown-toggle{float:none;}
#dataEditForm .bootstrap-select > .dropdown-toggle{
  width:180px;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">抽奖活动</a></li>
			<li class="active">邀请管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					    
			   			<label class="col-lg-2 col-lg-3"><span>受邀人电话：</span> <input class="form-controlgoods input-append " type="text" name="tphone"   value="" /></label>
			   			<label class="col-lg-2 col-lg-3"><span>邀请公司：</span> <input class="form-controlgoods input-append " type="text" name="mcompanyName"   value="" /></label>
			   			<label class="col-lg-2 col-lg-3"><span>邀请人姓名：</span> <input class="form-controlgoods input-append " type="text" name="mname"   value="" /></label>
			            <label class="col-lg-2 col-lg-3"><span>&nbsp;邀请状态：</span> 
			 				<select  name="invitationStatus" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">待确认</option>
					            	<option  value="2">邀请成功</option>
					            	<option  value="3">邀请失败</option>
			   				</select>
			   			</label>
			            <input type="hidden" id="isInvitationStatus" name="isInvitationStatus" />
			             <div>
				             <input type="file" class="col-sm-3 col-md-2 col-lg-2" id="updateFile" value="" style="width:220px"/>
				             <button class="btn btn-default dropdown-toggle" onclick="importInvitationRecord();">导入</button>
				             <button style="display:none;" showname="BTN_EXPORTINVITATIONRECORD_FORM" class="btn btn-default dropdown-toggle" onclick="exportInvitationRecord();" data-toggle="dropdown">
				             <span id="loadingExportInvitationRecordData">导出</span></button>
				             <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
				             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
		            	 </div>
				            
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findGoodsbrand(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findGoodsbrand(1);">待确认</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findGoodsbrand(2);">邀请成功</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findGoodsbrand(3);">邀请失败</a>
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
                                <table id="invitationRecordTableId" style="width: 100%"></table>
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
					<h4 class="modal-title" id="modalLabel">奖品发放</h4>
				</div>

				<div class="modal-body">
			<form id="dataPutForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					<input type="hidden" class="form-control" id="putMcompanyPk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">&nbsp;收&nbsp;件&nbsp;人:</label>
							<div class="col-sm-6">
							<input type="text" class="form-control" id="contacts" name="contacts" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区:</label>
						<div class="col-sm-10" style="margin-left:-15px;">
						<div class="col-sm-3">
							<select class="form-control col-sm-2" id="province" name="province" onchange="changeCity(this);" required="true">
							<option value="">--省--</option>
						 	<c:forEach var="c" items="${province}">
									<option value="${c.pk }">${c.name}</option>
								</c:forEach>
							</select>	
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="city" name="city"  onchange="changeArea(this);" required="true">
							<option value="">--市--</option>
							</select>
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="area" name="area" required="true">
							<option value="">--区--</option>
							</select>
						</div>
						</div>
					</div>
					<div class="form-group col-ms-12"
							style="height: 65px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">详细地址:</label>
							<div class="col-sm-5">
							<textarea style="width:280px;height:60px;resize: none;" required="true" id="address" name="address" ></textarea>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">手机号码:</label>
							<div class="col-sm-6">
							<input type="text" class="form-control" id="contactsTel" name="contactsTel" required="true" />
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">发放状态:</label>
							<div class="col-sm-4">
							<select class="form-control" id="awardStatus" name="awardStatus" required="true" onchange="changeAwardStatus()">
							</select>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">发放方式:</label>
							<div class="col-sm-4">
							<select class="form-control" id="grantType" name="grantType" required="true">
							</select>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 90px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
							<div class="col-sm-5">
							<textarea style="width:280px;height:90px;resize: none;" id="note" name="note"></textarea>
							</div>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
						<form id="hidePutForm" style="display: none;"></form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitPutAward();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="editIsInvitationId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">确认信息</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" id="invitationPk" />
					<input type="hidden" id="conformTcompanyPk" />
						<input type="hidden" id="conformTcompanyName" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-4 control-label"
								style="text-align: right;">受邀人姓名:</label>
							<div class="col-sm-8">
							<input type="text" class="form-control" id="tname" name="tname" style="width:280px;margin-left:24px"  readonly="readonly"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-4 control-label"
								style="text-align: right;">受邀人电话:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="tphone" name="tphone" style="width:280px;margin-left:24px" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-4 control-label"
								style="text-align: right;">受邀人公司名称:</label>
							<div class="col-sm-8">
							<input type="text" class="form-control" id="tcompanyName" name="tcompanyName" style="width:280px;margin-left:24px" readonly="readonly"/>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-4 control-label"
								style="text-align: right;">是否已申请开通白条:</label>
							<div class="col-sm-8">
							<input type="text" class="form-control" id="applyStatus" name="applyStatus" style="width:280px;margin-left:24px" disabled="disabled"/>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 90px; display: block;">
							<label for="inputEmail3" class="col-sm-4 control-label"
								style="text-align: right;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
							<div class="col-sm-8">
							<textarea style="width:280px;height:90px;margin-left:24px;resize: none;" id="conformNote" name="conformNote"></textarea>
							</div>
						</div>
						
						 <!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<!-- <button type="button" class="btn btn-default" id="quxiaoInvitation"
						data-dismiss="modal">取消</button> -->
				<center>
					<button type="button" class="btn btn-primary" id="invitationSuccess" onclick="submit(2);">邀请成功</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn btn-primary" id="invitationFail" onclick="submit(3);">邀请失败</button>
				</center>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editIsInvitationInfoId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">编辑信息</h4>
				</div>
				<div class="modal-body">
					<form id="dataEditForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="invitationRecordPk" />
					<input type="hidden" class="form-control" id="mcompanyPk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">邀请人:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="editmname" name="editmname"  required="true" readonly="readonly" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">邀请人电话:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="editmphone" name="editmphone"  required="true" readonly="readonly" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">邀请时间:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="editinsertTime"  name="editinsertTime" required="true" readonly="readonly" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">好友姓名:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="edittname" name="edittname"   required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">好友电话:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="edittphone" name="edittphone"   required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">好友公司名称:</label>
							<div class="col-sm-5" style="margin-left:-22px" >
							<select  id="edittcompanyPk" name="edittcompanyPk" class="selectpicker bla bla bli control-label" data-live-search="true" onchange="isOneCompany();">
					         <option value="null">--请选择--</option>
			   				</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">是否已申请开通白条:</label>
							<div class="col-sm-5" style="width:210px;">
							<select class="form-control col-sm-3" id="editapplyStatus" name="editapplyStatus" >
									<option value="">--请选择--</option>
							</select>
							</div>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
						<form id="hideEditForm" style="display: none;"></form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiaoEdit"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="submitEditId" onclick="submitEdit();">保存</button>
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