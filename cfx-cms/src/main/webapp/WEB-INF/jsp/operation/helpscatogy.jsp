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
<script src="./pageJs/operation/helpscatogy.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">资讯中心</a></li>
			<li class="active">帮助分类</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> 
				<header class="panel-heading"
					style="margin-bottom:10px;">
					  <label class="col-lg-2 col-lg-3"><span>放置终端：</span>
						 <select class="selectpicker bla bla bli" name="showPlace" style="width: 200px;" >
							<option value="">--请选择--</option>
							<option value="1">PC端</option>
						  	<option value="2">WAP端</option>
						  	<option value="3">APP端</option>
						 </select>
					 	</label>	
					 	<button class="btn btn-primary" id="btn" onclick="searchTabs()"> 搜索</button>
					<button id="editable-sample_new" style="display:none;" showname="OPER_HELP_CLASSIFY_BTN_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal1"
						onclick="javascript:editCategory(); ">添加分类</button>
			 </header>
				<div class="panel-body">
					<section class="panel">
						<div class="tab-content">
							<div class="tab-pane active" id="all">
								<table id="categoryId" style="width: 100%"></table>
							</div>
						</div>
					</section>
				</div>
				</section>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加帮助分类</h4>
				</div>
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk" name="pk" />
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">分类名称</label>
							<div class="col-sm-9">
								<input class="form-control" name="name" id="name" />
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">是否展示首页</label>
							<div class="col-sm-9">
								<select class="form-control" name="showType" id="showType" required="true">
									<option value="" selected="selected">--请选择--</option>
									<option value="1">是</option>
									<option value="2">否</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">放置终端</label> 
							<div class="col-sm-5">	
								<label><input name="showPlace" type="checkbox" value="1" style="margin-left:3px;" />PC端</label>
								<label><input name="showPlace" type="checkbox" value="2"  style="margin-left:3px;" />WAP端</label>
								<label><input name="showPlace" type="checkbox" value="3" style="margin-left:3px;"/>APP端</label>
						    </div>
						</div>
						
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label" required="true">排序</label>
							<div class="col-sm-9">
								<input class="form-control" name="sort" id="sort" />
							</div>
						</div>
					</form>
					<!-- 结束 -->
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