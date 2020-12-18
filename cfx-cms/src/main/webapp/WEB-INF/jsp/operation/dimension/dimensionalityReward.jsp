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
<script src="./pageJs/operation/dimension/dimensionalityReward.js"></script>
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
			<li><a href="#">会员体系</a></li>
			<li class="active">奖励管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;" id="jiangli">
				 
					   <label class="col-lg-2 col-lg-3"><span>&nbsp;维度：</span> 
			 				<select  name="selectDimen" id="selectDimen" class="selectpicker bla bla bli" onchange="changeDimenSelect()">
					          	 	<option value="">--请选择--</option>
					          	 	<c:forEach items="${allDimenDtoList }" var="dimen">
					          	 	<option  value="${dimen.type }">${dimen.name }</option>
					          	 	</c:forEach>
			   				</select>
			   			</label>
			   			<label class="col-lg-2 col-lg-3"><span>&nbsp;类型：</span> 
			 				<select  name="selectDimenCategory" id="selectDimenCategory" class="selectpicker bla bla bli" >
					          	 	<option value="">--请选择--</option>
			   				</select>
			   			</label>
			   			
			   				
							<label class="col-lg-2 col-lg-3"><span>&nbsp;赠送周期：</span> 
							<select  id="periodType" name="periodType" class="selectpicker bla bla bli" >
		   					<option value=''>--请选择--</option>
		   					<option  value="1">一次赠送</option>
					            	<option  value="2">每天</option>
					            	<option  value="3">每周</option>
		   					</select>
							</label>
						
			   				<label class="col-lg-2 col-lg-3"><span>更新时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"   value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"   value="" readonly /></label>
			           	
			      
			   			<label class="col-lg-2 col-lg-3"><span>&nbsp;是否启用：</span> 
			 				<select  name="isVisable" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">启用</option>
					            	<option  value="2">禁用</option>
			   				</select>
			   			</label>
			   		
			           	
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button  style="display:none;"  showname="OPER_MSS_AWARD_BTN_ADD" class="btn btn-primary" id="btn" onclick="insertDimenReward();">新增</button>
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
                                <table id="dimensionalityManageId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	
	
	
	
	
	
<div class="modal fade" id="childCompany" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 92%;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">赠送列表</h4>
				</div>
						
				<div class="modal-body">
			
					 	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;" id="t">
				
							<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>&nbsp;赠送周期</span> 
							
							 <input type="hidden" name="dimenCategory1" id="dimenCategory1" />
				             <input type="hidden" name="dimenType1" id="dimenType1" />
				             <input type="hidden"  name="type1" id="type1"></input>
				             
							
							
							<select  id="periodType2" name="periodType2" class="selectpicker bla bla bli" >
		   					<option value=''>--请选择--</option>
		   					<option  value="1">一次赠送</option>
					            	<option  value="2">每天</option>
					            	<option  value="3">每周</option>
		   					</select>
							</label>
						
			   				<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>更新时间</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"  id="updateStartTime" value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"  id="updateEndTime" value="" readonly /></label>
			          
			      
			   	
			   			
			   			<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>公司名称</span> <input class="form-controlgoods input-append " type="text" name="companyName" id="companyName"  value="" onkeydown="entersearch()"/></label>	
			   			
			   			<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>电话</span> <input class="form-controlgoods input-append " type="text" name="contactsTel" id="contactsTel"  value="" onkeydown="entersearch()"/></label>	
			   			
			   			
			            <button class="btn btn-primary" id="btn" onclick="SearchCleanP(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchCleanP(2);"> 清除</button>
		              <button  showname="BTN_EXPORTORDERM" class="btn btn-default dropdown-toggle" onclick="exportPresent();" data-toggle="dropdown"><span id="loadingExportData">导出</span></button>
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
                                <table id="childs" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
				</div>

			</div>
		</div>
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	<div class="modal fade" id="editDimensionalityREId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width:600px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">奖励管理</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">维度:</label>
							<div class="col-sm-5">
							<select  id="dimenCategory" name="dimenCategory" class="form-control bla bla bli"  style="width:180px;margin-left:0px" required="true" onchange="changeDimen()">
		   						   <option value="">--请选择--</option>
					          	 	<c:forEach items="${allDimenDtoList }" var="dimen">
					          	 	<option  value="${dimen.type }">${dimen.name }</option>
					          	 	</c:forEach>
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">类型:</label>
							<div class="col-sm-5">
							<select  id="dimenType" name="dimenType" class="form-control bla bla bli"  style="width:180px;margin-left:0px" required="true">
		   					<option value=''>--请选择--</option>
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">赠送周期:</label>
							<div class="col-sm-5">
							<select  id="periodType1" name="periodType1" class="form-control bla bla bli"  style="width:180px;margin-left:0px" required="true">
		   					<option value=''>--请选择--</option>
		   					</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">纤贝数:</label>
							<div class="col-sm-5">
							<input type="text" class="form-control" id="fibreShellNumber" name="fibreShellNumber" style="width:280px;margin-left:0px" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">奖励纤贝系数:</label>
							<div class="col-sm-5">
							<input type="text" class="form-control" id="fibreShellRatio" name="fibreShellRatio" style="width:280px;margin-left:0px" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">经验值:</label>
							<div class="col-sm-5">
							<input type="text" class="form-control" id="empiricalValue" name="empiricalValue" style="width:280px;margin-left:0px" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">奖励经验系数:</label>
							<div class="col-sm-5">
							<input type="text" class="form-control" id="empiricalRatio" name="empiricalRatio" style="width:280px;margin-left:0px" />
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
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>