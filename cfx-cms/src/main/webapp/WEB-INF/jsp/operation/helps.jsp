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
<script src="./pageJs/operation/helps.js"></script>
<style>
.bootstrap-select > .dropdown-toggle{float:none;}
</style>
<style>
.bootstrap-select > .dropdown-toggle{float:right;}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">资讯中心</a></li>
			<li class="active">帮助列表</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
				<label class="col-lg-2 col-lg-3"><span>是否启用：</span> <select class="form-controlgoods input-append " name="isVisable" style="width: 110px;"><option value="">全部</option><option value="1">启用</option><option value="2">禁用</option></select></label>
				<label class="col-lg-2 col-lg-3"><span>标题：</span> <input class="form-controlgoods input-append " type="text" name="title"   value="" onkeydown="entersearch()"/></label>	
				<label class="col-lg-2 col-lg-3"><span>添加时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"   value="" readonly /></label>
			           <label class="col-lg-2 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"   value="" readonly /></label>
				 <label class="col-lg-2 col-lg-3"><span>所属分类：</span> <select class="form-controlgoods input-append " name="helpCategoryPk" style="width: 110px;">
					 <option value="">全部</option>
					 <c:forEach items="${helperCategoryList }" var="list">
					 <option value="${list.pk }">${list.name }</option>
					 </c:forEach>
				 </select></label>
				   <label class="col-lg-2 col-lg-3"><span>放置终端：</span>
						 <select class="selectpicker bla bla bli" name="showPlace" style="width: 200px;" >
							<option value="">--请选择--</option>
							<option value="1">PC端</option>
						  	<option value="2">WAP端</option>
						  	<option value="3">APP端</option>
						 </select>
					 	</label>	
				 <input type="hidden" id="status" name="status"/>
				 <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			     <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
				 <a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="OPER_HELP_LIST_BTN_ADD" class="btn btn-primary" data-toggle="modal" data-target="#myModal1" onclick="javascript:editNews('');">新增帮助</button>
				    </a> 
		             </header>
				<div class="panel-body">
					<section class="panel">
					 <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findNews(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findNews(1);">未发布</a>
                                </li>
                            <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findNews(2);">已发布</a>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
			                    <table id="NewsId" style="width: 100%"></table>
                                </div>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	 <script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	searchTabs();  
	       }  
	} 
</script>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>