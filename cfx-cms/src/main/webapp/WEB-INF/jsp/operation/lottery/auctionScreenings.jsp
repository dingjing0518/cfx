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
<script src="./pageJs/auctionScreenings.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">活动管理</a></li>
			<li class="active">活动场次管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-sm-3 col-md-2 col-lg-2"><span>&nbsp;是否启用：</span> 
			 				<select  name="isVisable" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">启用</option>
					            	<option  value="2">禁用</option>
			   				</select>
			   			</label>
			   			<label class="col-sm-3 col-md-2 col-lg-2"><span>活动时间：</span> <input class="form-controlgoods input-append date form_datetime" type="text" name="startTime"   value="" readonly /></label>
			           	<label class="col-sm-3 col-md-2 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form_datetime" type="text" name="endTime"   value="" readonly /></label>
			           	
			           	<label class="col-sm-3 col-md-2 col-lg-2"><span>更新时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateTimeBegin"   value="" readonly /></label>
			           	<label class="col-sm-3 col-md-2 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateTimeEnd"   value="" readonly /></label>
			            <input type="hidden" id="isVisableStatus" name="isVisableStatus" />
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button style="display:none;" showname="BTN_ADDAUCTIONSCREENINGS" class="btn btn-primary" id="btn" onclick="insertAuctionScreenings();">新增</button>
		            	
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findGoodsbrand(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findGoodsbrand(1);">启用</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findGoodsbrand(2);">禁用</a>
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
                                <table id="goodsBrandId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editGoodsBrand" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">添加活动场次</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">活动名称:</label>
							<div class="col-sm-10">
							<input type="text" class="form-control" id="auctionName" name="auctionName" style="width:280px;margin-left:24px" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">活动时间:</label>
							<div class="col-sm-3">
							<input type="text" class="form-control form_datetime" id="startTime" name="startTime" style="width:90px;margin-left:24px" required="true" onchange="changeTime();"/>
							</div>
							<div class="col-sm-3">
							<input type="text" class="form-control form_datetime" id="endTime" name="endTime" style="width:90px;margin-left:24px" required="true" onchange="changeTime();"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label>
							<div class="col-sm-5">
							<input type="text" class="form-control" id="sort" name="sort" style="width:80px;margin-left:24px" required="true" />
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
	<!-- 活动规则子页面 -->
	<div class="modal fade" id="subAuctionRuleId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050;width: 1000px;margin: 0 auto;top: 30px;">
		<div>
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">活动规则</h4>
				</div>
				<input  type="hidden" id="auctionScreeningsPk"/>
				<section class="panel">
					<header class="panel-heading" > 
					<button id="editable-sample_new" style="display:none;" showname="BTN_SHOWSCREENINGS" class="btn btn-primary btn-new"
									data-toggle="modal" onclick="javascript:insertActivityRule();">
									新增规则 <i class="fa fa-plus"></i>
								</button>
						<input type="hidden" id="bidStatus" name="bidStatus" value=""/>
					</header>
				<div class="panel-body">
					<section class="panel">
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
                                <table id="autionScreeningsTableId" style="width: 100%;height:60%;"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	<!--编辑活动规则-->
	<div class="modal fade" id="editActivityRuleId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">编辑活动规则</h4>
				</div>
				<div class="modal-body">
				<form  id="reasonForm">
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 140px; display: block;">
							<p style="margin:20px 10px;">
							<span style="width:100%;height:50px;line-height:50px;display:block;">
								<i style="float:left;font-style:normal;width:80px;">活动结束后&nbsp;</i>
								<div style="float:left;margin-left:85px;margin-top:-60px;">
								<select  name="hours" id="hours" class="selectpicker" size="20">
						          	 	<option value="">-请选择-</option>
						            	<option  value="1">1</option>
						            	<option  value="2">2</option>
						            	<option  value="3">3</option>
						            	<option  value="4">4</option>
						            	<option  value="5">5</option>
						            	<option  value="6">6</option>
						            	<option  value="7">7</option>
						            	<option  value="8">8</option>
						            	<option  value="9">9</option>
						            	<option  value="10">10</option>
						            	<option  value="11">11</option>
						            	<option  value="12">12</option>
				   				</select>
				   				</div>
				   	            <div style="float:left;display: inline-block;width: 450px;margin-left: 210px;margin-top: -40px;">
				   				<i style="float: left;font-style: normal;margin-top: -15px;display: inline-block;">&nbsp;小时内，不提交订单</i>
				   				<div style="float: left;margin-top: -20px;margin-left: 10px;">
				   				  <input type="text" style="width:50px;" name="frequency" id="frequency"  value="" />&nbsp;次，取消之后&nbsp;
				   				  <input type="text" name="days" id="days" style="width:50px;"  value="" />
			   				    </div>
			   				    </div>
			   				</span>
			   				<span style="width:100%;margin-left:10px;line-height:22px;display:block;">
			   				&nbsp;天竞拍资格。
			   				</span>
			   				</p>
					</div>
				</form>
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="qx" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="saveActivity();">保存</button>
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