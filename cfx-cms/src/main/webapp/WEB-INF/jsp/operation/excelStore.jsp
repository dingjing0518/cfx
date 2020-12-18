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
	<script src="./pageJs/operation/excelStore.js"></script>
	<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
<div class="page-heading">
	<ul class="breadcrumb">
		<li><a href="#">任务管理</a></li>
		<li class="active">下载列表</li>
	</ul>
</div>
<!--主体内容开始-->
<div class="wrapper">
	<div class="row">
		<div class="col-sm-12">
			<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
					<label class="col-lg-3 col-lg-2"><span>导出类型：</span> <select class="form-controlgoods input-append " name="type" style="width: 110px;">
						<option value="">全部</option>
						<option value="1">客户数据</option>
						<option value="2">订单数据</option>
						<option value="3">商品数据</option>
						<option value="4">报表数据</option>
					</select></label>
					<label class="col-lg-3 col-lg-2"><span>操作人：</span> <input class="form-controlgoods input-append " type="text" name="accountName"   value="" /></label>
					<label class="col-lg-3 col-lg-3"><span>申请导出时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" style="width: 110px;" name="insertTimeStart"   value="" readonly /></label>
					<label class="col-lg-3 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
					<input type="hidden" id="isDeal" name="isDeal"/>
					<button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
					<button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
					<a href="#" style="text-decoration: none;">
					</a>
				</header>
				<div class="panel-body">
					<section class="panel">
						<header class="panel-heading custom-tab ">
							<ul class="nav nav-tabs">
								<li class="active">
									<a href="#all" data-toggle="tab" onclick="findStatus(0);">全部</a>
								</li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="findStatus(2);">进行中</a>
								</li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="findStatus(1);">已结束</a>
								</li>
							</ul>
						</header>
						<div class="panel-body">
							<div class="tab-content">
								<div class="tab-pane active" id="all">
									<table id="excelStoreId" style="width: 100%"></table>
								</div>
							</div>
						</div>
					</section>
				</div>
			</section>
		</div>
	</div>
</div>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>