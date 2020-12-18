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
<script src="./pageJs/logistics/lgRole.js"></script>
<script src="./pageJs/logistics/common.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">管理中心</a></li>
			<li class="active">角色管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<div class="panel-body">
					<div class="adv-table editable-table ">
						<div class="clearfix">
							<div class="btn-group" style="margin-bottom: 20px; margin-top:10px;width: 100%">
								<!-- 模态框 -->
								<button id="editable-sample_new" style="display:none;" showname="LG_ROLEMGR_BTN_ADD" class="btn btn-primary btn-new"
									data-toggle="modal" onclick="javascript:editB2bRole('','',1);">
									新增 <i class="fa fa-plus"></i>
								</button>
								<table id="b2bRoleId" style="width: 100%"></table>
							</div>
						</div>
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
 
	<div class="modal fade" id="editB2bRoleId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑角色</h4>
				</div>
				<div class="modal-body">
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">角色名称</label>
						<div class="col-sm-10">
							<input type="email" class="form-control" id="name" onmouseout="checkLgRoleName(this.value);"><span id="namePrompt"></span>
							<input
								type="hidden" id="pk" value="">
						</div>
					</div>
					<div class="portlet light ">
						<div class="portlet-body">
							<div id="treeId" class="tree-demo"></div>
							<div id="treeId2" class="tree-demo" style="display: none;"></div>
						</div>
					</div>
					<!-- 树形结束 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>