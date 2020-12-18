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
<!-- 页面内容导航开始-->
<script src="./pageJs/marketing/manageRegion.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">人员管理</a></li>
			<li class="active">大区管理</li>
		</ul>
	</div>
	<!-- 页面内容导航
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<label class="col-lg-2 col-lg-3"><span>区域名称：</span> <input class="form-controlgoods input-append " type="text" name="name" id="names" value=""  aria-controls="dynamic-table" /></label>
						<label class="col-lg-3 col-lg-3 addressnew"><span>地区：</span>
							<div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
								<select class="form-control col-sm-2" style="padding:6px;" id ="provinceName" name="provinceName" onchange="changeCity(this);" style="float:none;">
									<option value="">--省--</option>
									<c:forEach var="c" items="${province}">
										<option value="${c.pk }">${c.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
								<select class="form-control col-sm-2" id = "cityName" style="padding:6px;"  name="cityName" onchange="changeArea(this);" style="float:none;"><option value="">--市--</option></select>
							</div>
							<div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
								<select class="form-control col-sm-2" id ="areaName" style="padding:6px;"  name="areaName" style="float:none;"><option value="">--区--</option></select>
							</div>
						</label>
      				 	 <button class="btn btn-primary" onclick="SearchTabs(1)"> 搜索</button>
			             <button class="btn btn-warning" onclick="SearchTabs(2)"> 清除</button>
						<button style="display:none;" showname="MARKET_PERSONNEL_MG_REGION_BTN_ADD" class="btn btn-primary" onclick="javascript:addLogisticsRoute();"> 新增</button>

		             </header>
		             
					<div class="panel-body">
						<div class="adv-table editable-table ">
							<div class="clearfix">
								<div class="btn-group" style="margin-bottom: 20px; width: 100%">

									<table id="manageRegionId" style="width: 100%"></table>
								</div>
							</div>
						</div>
					</div>
					
			</section>
			</div>
		</div>
	</div>
<!--------------- 编辑模态框 -------------------------->
<div class="modal fade" id="editLogisticsRouteId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" >
		<div class="modal-dialog" role="document" style="width: 790px;height:750px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true" onclick="closeBtn();">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑大区</h4>
				</div>
				<div class="modal-body">
				<form  id="dataForm">
				<input type="hidden" class="form-control" id="pk" />
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">区域名称</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="name" name="name" required="true"  maxlength="90" />
						</div>
					</div>
                  	<div class="form-group col-ms-12"style="height: 30px; display: block;">
						<input type="hidden" id="indexNumber" value=""/>
	                  	<label for="inputEmail3" class="col-sm-2 control-label"style="text-align: left;">地区</label>
						<div class="col-sm-10" style="margin-left:-15px;">
							<div class="col-sm-3">
								<select class="form-control col-sm-3" id="provinceIndex_1" name="provinceIndex" onchange="changeCity(this);">
									<option value="">--省--</option>
									<c:forEach var="c" items="${province}">
										<option value="${c.pk }">${c.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select class="form-control col-sm-3 " id="cityIndex_1" name="cityIndex" onchange="changeArea(this);">
									<option value="">--市--</option></select>
							</div>
							<div class="col-sm-3">
								<select class="form-control col-sm-3"  id="areaIndex_1" name="areaIndex">
									<option value="">--区--</option></select>
							</div>
							<button class="btn btn-primary"  type="button" onclick="addArea()" style = "margin-left: 7px;">新增</button>
						</div>
	                  </div>
	                  <!-- 新增区域 -->
	                  <div id="addAreaDiv"></div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao1" data-dismiss="modal" onclick="closeBtn();">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!---------------------- END -->
	<script type="text/javascript">

	 </script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>