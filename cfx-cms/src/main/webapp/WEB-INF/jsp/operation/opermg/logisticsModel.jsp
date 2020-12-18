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
<script src="./pageJs/operation/opermg/logisticsModel.js"></script>
</head>
<body class="sticky-header">
	<!-- 页面内容导航开始-->
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营管理</a></li>
			<li class="active">物流方式</li>
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
							<div class="btn-group">
								
								<!-- 开始 -->
	 
								<!-- 模态框结束 -->
							</div>
						</div>
						<table id="logisticsModelId" style="width: 100%"></table>


					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!--主题内容结束-->
</body>
</html>