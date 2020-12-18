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
<script src="./pageJs/economics/actListGroup.js"></script>
<style>
#dataForm .panel-heading .form-controlgoods{width:180px;}
.bootstrap-select > .dropdown-toggle{width:180px;}
.has-error .help-block{margin-left:180px;}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">人员管理->帐号管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-lg-2 col-lg-3"><span>&nbsp;帐号：</span> 
					         <input type="text"  name="id" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()" >
					     </label>
					     
					     <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</butto>
			     </header>
				<div class="panel-body">
					<div class="adv-table editable-table ">
						<div class="clearfix">
							<div class="btn-group" style="margin-bottom: 20px; width: 100%">
								<!-- 模态框 -->
								<button id="editable-sample_new" style="display:none;" showname="EM_EMPACCOUNT_ACCOUNT_BTN_ADD" class="btn btn-primary btn-new"
									data-toggle="modal" onclick="javascript:edit();">
									新增 <i class="fa fa-plus"></i>
								</button>
								<table id="actListGroup" style="width: 100%"></table>
							</div>
						</div>
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editActListGroup" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">编辑</h4>
				</div>

				<div class="modal-body">
				<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
				
					<input type="hidden" class="form-control" id="pk" />
					
					    <div class="form-group col-ms-12"
							style="height: 30px; display: inline-block;width:100%">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">帐号</label>
							<div class="col-sm-5">
							<select id="accountId" name="accountId" class="selectpicker bla bla bli"   data-live-search="true" required="true">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${accountList}" var="m">
					            	<option  value="${m.account}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
					
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: inline-block;width:100%">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">角色名称</label>
							<div class="col-sm-5">
							<select id="groupId" name="groupId" class="selectpicker bla bla bli"   data-live-search="true" required="true">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${roleList}" var="m">
					            	<option  value="${m.id}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						
						
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

</body>
</html>
<script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	SearchClean();  
       }  
} 
</script>