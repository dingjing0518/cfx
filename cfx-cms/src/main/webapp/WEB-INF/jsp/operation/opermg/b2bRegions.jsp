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
<script src="./pageJs/operation/opermg/b2bRegions.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">管理中心</a></li>
			<li class="active">地区管理</li>
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
							<div class="btn-group" style="margin-bottom: 20px; width: 100%">
								<!-- <h4 class="modal-title" id="myModalLabel">地区管理树图</h4> -->
								&nbsp;&nbsp;&nbsp;&nbsp;<a class="delete" href="javascript:;" style="text-decoration:none;"> 
								<button type="button" style="display:none;" showname="OPER_MG_REGION_BTN_SYNC" class="btn btn btn-primary" data-toggle="modal"
								data-target="#myModal3" onclick="javascript:saveRegions();">
								同步地区
								</button>
								</a>
								(鼠标右键点击操作--新建地区和删除地区)
								<table id="b2bRoleId" style="width: 100%">
								<div class="portlet light ">
									<div class="portlet-body">
										<div id="treeId" class="tree-demo"></div>
									</div>
								</div>
								</table>
							</div>
						</div>
					</div>
					
	<div class="modal fade" id="editRegionsId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑地区</h4>
				</div>
				<div class="modal-body">
				<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk"/>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">父节点名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="parentName" readonly="readonly" />
								<input type="hidden" class="form-control" id="parentPk" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">编辑地区</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="name"/>
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
					
					
					
				</div>
				</section>
			</div>
		</div>
	</div>
</body>
</html>