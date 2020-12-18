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
<script src="./pageJs/manage/smsTemplate.js"></script>
<!-- 页面内容导航开始	-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">管理中心</a></li>
			<li class="active">短信记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
				<input type="hidden" name="type" id="type" value="1"></input>
					      <label class="col-lg-2 col-lg-3"><span>&nbsp;模板名称：</span> 
					         <input type="text"  name="freeSignName" class="form-controlgoods" aria-controls="dynamic-table"  >
					     </label>
					    
					     <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			     </header>
			     	<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findSmsType(1);">采购商</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findSmsType(2);">供应商</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findSmsType(3);">个人</a>
                                </li>
                          
                            </ul>
                        </header>
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
                                	<table id="smsTemplateId" style="width: 100%"></table>
                         
                            </div>
                        </div>
                    </section>
				</div>
				
				</section>
			</div>
		</div>
	</div>

	<!-- 绑定角色 -->
	<div class="modal fade" id="setRole" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 500px;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">绑定角色</h4>
				</div>
				<!-- <div class="panel-heading" style="float:right;margin-right:24px;" onclick="addChild()">
			      <button class="btn btn-primary" id="addChild" >添加子公司</button>
				</div> -->
				<input id="smsName" type="hidden"/>
				<input  id="smsType" type="hidden"/>
				<div class="modal-body">
					 <table id="roles" style="width: 100%"></table>
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-primary" onclick="saveRoles();">保存</button>
				</div>
			</div>
		</div>
	</div>
<!-- 绑定后台角色 -->
	<div class="modal fade" id="setBackRole" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 500px;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">绑定后台角色</h4>
				</div>
				<!-- <div class="panel-heading" style="float:right;margin-right:24px;" onclick="addChild()">
			      <button class="btn btn-primary" id="addChild" >添加子公司</button>
				</div> -->
				<input id="smsBackName" type="hidden"/>
				<div class="modal-body">
					 <table id="backRoles" style="width: 100%"></table>
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-primary" onclick="saveBackRoles();">保存</button>
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