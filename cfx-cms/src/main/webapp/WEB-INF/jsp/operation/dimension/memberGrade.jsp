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
<script src="./pageJs/operation/dimension/memberGrade.js"></script>
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
			<li>会员体系</li>
			<li class="active">等级设置</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					  <!--    <label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;是否启用：</span> 
			 				<select  name="isVisable" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">启用</option>
					            	<option  value="2">禁用</option>
			   				</select>
			   			</label> -->
			   			
			   			
			   			
			   			<label class="col-lg-3 col-lg-2"><span>&nbsp;等级名称：</span> 
					     	<input type="text"  name="gradeName" class="form-controlgoods" aria-controls="dynamic-table" >	
					     </label>
			   			
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>更新时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"   value="" readonly /></label>
			           	
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button style="display:none;" showname="OPER_MSS_GRADE_BTN_ADD" class="btn btn-primary" id="btn" onclick="insertDimenReward();">新增</button>
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
	<div class="modal fade" id="editDimensionalityREId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">等级设置</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">等级名称:</label>
							<div class="col-sm-6">
							<input type="text" class="form-control" id="gradeName" name="gradeName" style="width:280px;margin-left:0px" required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">等级:</label>
							<div class="col-sm-6">
							<input type="text" class="form-control" id="gradeNumber" name="gradeNumber" style="width:280px;margin-left:0px" required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">对应分值:</label>
							
		   					<div class="col-sm-2">
		   						<input type="text" class="form-control" id="numberStart" name="numberStart" style="width:100px;margin-left:0px" required="true"/>
		   					</div>
		   					<div class="col-sm-1">
		   						至
		   					</div>
		   					<div class="col-sm-2">
		   						<input type="text" class="form-control" id="numberEnd" name="numberEnd" style="width:100px;margin-left:0px" required="true"/>
		   					</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">等级系数(纤贝):</label>
							<div class="col-sm-6">
							<input type="text" class="form-control" id="fbGradeRatio" name="fbGradeRatio" style="width:280px;margin-left:0px" required="true"/>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">等级系数(经验):</label>
							<div class="col-sm-6">
							<input type="text" class="form-control" id="emGradeRatio" name="emGradeRatio" style="width:280px;margin-left:0px" required="true"/>
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