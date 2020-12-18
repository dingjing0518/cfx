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
<script src="./pageJs/operation/articlecatogy.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">资讯中心</a></li>
			<li class="active">文章分类</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading"
					style="margin-bottom:10px;"> <a href="#"
					style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTCLASSIFY_BTN_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal1"
						onclick="javascript:editCategory(); ">添加分类</button>
				</a>
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
					<h4 class="modal-title" id="myModalLabel">添加文章分类</h4>
				</div>
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk" name="pk" />
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">分类名称</label>
							<div class="col-sm-10">
								<input class="form-control" name="name" id="name" maxlength="50" required="true"/>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">所属系统</label>
							<div class="col-sm-10">
								<select class="form-control" name="parentId" id="parentId" required="true">
									<option value="1">电商系统</option>
									<option value="2">物流系统</option>
									<option value="3">金融系统</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">展示位置</label>
							<div class="col-sm-10">
								<select class="form-control" name="showType" id="showType" required="true">
									<option value="1">首页上侧</option>
									<option value="2">首页底部</option>
									<option value="3">资讯页</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">排序</label>
							<div class="col-sm-10">
								<input class="form-control" name="sort" id="sort" required="true"/>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-10">
								<center>提示：分类名称需在50字以内。</center>
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