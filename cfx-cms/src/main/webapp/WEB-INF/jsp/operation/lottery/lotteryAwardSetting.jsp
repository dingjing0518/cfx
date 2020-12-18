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
<script src="./pageJs/operation/lottery/lotteryAwardSetting.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">抽奖管理</a></li>
			<li class="active">奖品设置</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-lg-3 col-lg-2"><span>&nbsp;奖品类型：</span> 
			 				<select  name=awardType class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="2">实物</option>
					            	<option  value="3">再接再厉,明日再来</option>
			   				</select>
			   			</label>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button style="display:none;" showname="OPER_ACTIVITY_LOTTERY_SETTINGS_BTN_ADD" class="btn btn-primary" id="btn" onclick="insertLotteryAward();">新增</button>
		            	
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
                                <table id="lotteryAwardTableId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editLotteryAwardId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">奖项编辑</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					<%--<input type="hidden" id="onlineTime" />--%>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">活动类型:</label>
							<div class="col-sm-9">
							<select  id="activityType" name="activityType" class="form-control"  style="width:300px;margin-left:24px" data-live-search="true" required="true" onchange="changeActivityType()" >
				          	 	<option value="">--请选择--</option>
								<option value="1" selected = "selected" >抽奖</option>
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">活动名称:</label>
							<div class="col-sm-9">
							<select  id="activityPk" name="activityPk" class="form-control"  style="width:300px;margin-left:24px" data-live-search="true"  required="true">
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">奖&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项:</label>
							<div class="col-sm-9">
							<input type="text" class="form-control" id="name" name="name" style="width:300px;margin-left:24px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">奖品类型:</label>
							<div class="col-sm-9">
							<select  id="awardType" name="awardType" class="form-control"  style="width:300px;margin-left:24px" data-live-search="true" required="true" onchange="changeAwardType();">
				          	 	<option value="">--请选择--</option>
								<option  value="2">实物</option>
								<option  value="3">再接再厉,明日再来</option>
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">奖品种类:</label>
							<div class="col-sm-9">
							<select  id="awardVariety" name="awardVariety" class="form-control"  style="width:300px;margin-left:24px" disabled="disabled" data-live-search="true" required="true">
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">奖品名称:</label>
							<div class="col-sm-9">
								<select  id="relevancyPk" name="relevancyPk" class="form-control"  style="width:300px;margin-left:24px" data-live-search="true" required="true">
			   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量:</label>
							<div class="col-sm-9">
							<input type="text" class="form-control" id="amount" name="amount" style="width:300px;margin-left:24px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							 style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								   style="text-align: left;">包装数量:</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="packageNumber" name="packageNumber" style="width:300px;margin-left:24px" required="true" />
							</div>
						</div>
					<div id="isLotteryActivity" class="form-group col-ms-12" >
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">获奖率:</label>
							<div class="col-sm-9">
							<input type="text" class="form-control" id="awardPercent" name="awardPercent" style="width:300px;margin-left:24px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">奖项设置顺序:</label>
							<div class="col-sm-5">
							<input type="text" class="form-control" id="showSort" name="showSort" style="width:80px;margin-left:24px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							 style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								   style="text-align: left;">中奖时间区间:</label>
							<div class="col-sm-9" style="padding-left: 40px;">
								<div class="row">
									<div class="col-sm-4" style="width:170px;"><input type="text" class="form-control form_datetime" id="startTime" name="startTime" style="   " required="true" /></div>
									<div class="col-sm-1" style="    padding-left: 8px;"><span style="margin-top: 5px;float:left"> 至</span></div>
									<div class="col-sm-4" style="width:170px;"><input type="text" class="form-control form_datetime" id="endTime" name="endTime" style="" required="true" /></div>
								</div>


							</div>
						</div>
					</div>
						 <!-- 编辑页面结束 -->
						</form>
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
<script>
	 var now=new Date();
	 var strTime=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();
		$(function(){
			$('.bigpicture').bigic();
		});

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