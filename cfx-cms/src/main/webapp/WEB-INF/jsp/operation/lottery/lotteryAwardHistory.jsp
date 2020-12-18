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
<script src="./pageJs/operation/lottery/lotteryAwardHistory.js"></script>
<style>
.bootstrap-select > .dropdown-toggle{float:right;}
</style>
<!-- 页面内容导航开始-->
<style>
.bootstrap-select > .dropdown-toggle{float:none;}
.diqu .help-block span{
	margin-top:0px!important;
}

</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">抽奖管理</a></li>
			<li class="active">抽奖记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
				 <label class="col-sm-6 col-md-4 col-lg-3"><span>获奖时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeStart"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
					 <label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;奖品发放状态：</span> 
			 				<select  name="awardStatus" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
						            	<option value="1">未发放</option>
							 			<option value="2">已发放</option>
							 			<option value="3">无</option>
			   				</select>
			   			</label>
			   		<label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;奖品类型：</span> 
			 				<select  name="awardType" class="selectpicker bla bla bli">
					          	 		<option value="">--请选择--</option>
						            	<option value="1">优惠券</option>
							 			<option value="2">实物</option>
							 			<option value="3">再接再厉、明日再来</option>
			   				</select>
			   			</label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>所获奖项：</span> <input class="form-controlgoods input-append " type="text" name="awardName"   value="" /></label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>注册手机号：</span> <input class="form-controlgoods input-append " type="text" name="mobile"   value="" /></label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>姓名：</span> <input class="form-controlgoods input-append " type="text" name="memberName"   value="" /></label>
			            <input type="hidden" id="isStatus" name="isStatus" />
			            <input type="hidden" id="isHistory" name="isHistory" value="1"/>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button style="display:none;" showname="OPER_ACTIVITY_LOTTERY_RECORD_BTN_EXPORT" class="btn btn-default dropdown-toggle" onclick="exportAwardHistory();" data-toggle="dropdown"><span id="loadingExportAwardData">导出</span></button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findGoodsbrand(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findGoodsbrand(2);">已中奖</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findGoodsbrand(1);">未中奖</a>
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
                                <table id="lotteryAwardRosterTableId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editLotteryAwardRosterId" tabindex="-1" role="dialog"
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
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					<input type="hidden" class="form-control" id="companyPk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">&nbsp;收&nbsp;件&nbsp;人:</label>
							<div class="col-sm-8">
							<input type="text" class="form-control" id="contacts" name="contacts" required="true"  maxlength="20"/>
							</div>
						</div>
						<div class="form-group col-ms-12 diqu"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区:</label>
						<div class="col-sm-10" style="margin-left:-15px;">
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="province" name="province" onchange="changeCity(this);" required="true">
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
							<select class="form-control col-sm-3" id="area" name="area"  required="true" onchange="changeTown(this);">
							<option value="">--区--</option>
							</select>
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="town" name="town" >
								<option value="">--镇--</option>
							</select>
						</div>
						</div>
					</div>
					<div class="form-group col-ms-12"
							style="height: 65px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">详细地址:</label>
							<div class="col-sm-10">
							<textarea style="width:280px;height:60px;resize: none;" required="true" id="address" name="address" ></textarea>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">手机号码:</label>
							<div class="col-sm-10">
							<input type="text" class="form-control" id="contactsTel" name="contactsTel" style="width:280px;margin-left:0px;" required="true"  />

							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">奖品类型:</label>
							<div class="col-sm-4">
							<select class="form-control col-sm-2" id="awardType"  name="awardType" disabled="disabled">
							</select>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">发放状态:</label>

							<div class="col-sm-4">
							<select class="form-control col-sm-2" id="awardStatus" style="width:280px;margin-left:0px;float:none;" name="awardStatus" required="true" onchange="changeAwardStatus()">
							</select>
							</div>
						</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">发放方式:</label>
							<div class="col-sm-4">
							<select class="form-control col-sm-2" id="grantType" style="width:280px;margin-left:0px;float:none;" name="grantType" required="true">
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
						<form id="hideForm" style="display: none;"></form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" id ="submit"  class="btn btn-primary" onclick="submit();">保存</button>
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