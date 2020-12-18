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
<script src="./pageJs/economics/bill/billCustomerManage.js"></script>
<style>
.modal-backdrop.in {
	z-index: 100;
}
.left {
    width: 90%;
}
.modal {
	z-index: 1000;
}

.bootstrap-select>.dropdown-toggle {
	float: none;
}
#dataForm label.col-sm-2.control-label {
    width: 40%;
    line-height:34px;
}
#dataForm .form-group.col-ms-12 {
    height: 34px!important;
}
#dataForm .col-sm-8 {
    width: 60%;
    padding-right: 20%;
}
#dataForm .col-sm-8 select {
    border: 1px solid #ccc;
    width: 100px;
    float: right;
    border-radius: 4px;
    height: 34px;
}
@media screen and (max-width:1800px){
#dataForm .col-sm-8 select{
		width:110px;
	}
}
@media (max-width:1388px){
.panel-heading  .form-controlgoods{
width:90px;
}
}
@media (max-width:1024px){
	#dataForm .col-sm-8 select{
		width:180px;
	}
}
@media (max-width:1280px){
.form-controlgoods{
width:90px;
}
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>票据管理/</li>
			<li class="active">客户管理</li>
		</ul> 
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading"
					style="margin-bottom:10px;"> <div class="left"><label
					class="col-lg-2 col-lg-2"><span>公司名称：</span> <input
					class="form-controlgoods input-append " type="text"
					name="companyName" value="" onkeydown="entersearch()" /></label> <label
					class="col-lg-2 col-lg-2"><span>处理时间：</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="updateStartTime" id="updateStartTime" value=""
					onkeydown="entersearch()" readonly /></label> <label
					class="col-lg-2 col-lg-2"><span>至</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="updateEndTime" id="updateEndTime" value=""
					readonly /></label> <label class="col-lg-2 col-lg-2"><span>提交时间：</span>
					<input class="form-controlgoods input-append date form-dateday"
					type="text" name="insertStartTime" id="insertStartTime" value=""
					onkeydown="entersearch()" readonly /></label> <label
					class="col-lg-2 col-lg-2"><span>至</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="insertEndTime" id="insertEndTime" value=""
					readonly /></label> <label class="col-lg-2 col-lg-2"><span>手机号码：</span>
					<input class="form-controlgoods input-append " type="text"
					name=contactsTel value="" onkeydown="entersearch()" /></label> 
					
				 <input type="hidden" id="status" name="status" />
</div>
<div class="right">
				<button class="btn btn-primary" id="btn" onclick="SearchClean(1)">
					搜索</button>
				<button class="btn btn-warning" id="btn" onclick="SearchClean(2)">
					清除</button>
					</div>
				</header>
				<div class="panel-body">
					<section class="panel"> <header
						class="panel-heading custom-tab ">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#all" data-toggle="tab"
							onclick="findStatus(0);">全部</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findStatus(1);">待审批</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findStatus(2);">审批中</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findStatus(3);">审批通过</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findStatus(4);">审批失败</a></li>
					</ul>
					</header>
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="tab-pane active" id="all"></div>
							<div class="tab-pane" id="verify"></div>
							<div class="tab-pane" id="verifydone"></div>
							<div class="tab-pane" id="unverify"></div>
							<table id="billCustomerManageId" style="width: 100%"></table>
						</div>
					</div>
					</section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="customersDimensionalityId" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width: 800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">分配客户</h4>
				</div>

				<div class="modal-body">
					<form id="dataForm">
						<input type="hidden" id="pk" />
						<!-- 编辑页面开始 -->
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">金融专员:</label>
							<div class="col-sm-8">
								<select id="assidirPk" name="assidirPk"class="selectpicker bla bla bli" data-live-search="true"
									required="true">
									<option value="">--请选择--</option>
									<c:forEach items="${acccountList}" var="m">
										<option value="${m.pk}">${m.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<!-- 编辑页面结束 -->
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary"
						onclick="submitAssi();">保存</button>
				</div>
			</div>
		</div>
	</div>


	<!-- ===================================== -->
	<div class="modal fade" id="editId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">批注列表</h4>
				</div>

				<div class="modal-body">
					<section class="panel">
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="tab-pane active" id="all"></div>
							<div class="tab-pane" id="verify"></div>
							<div class="tab-pane" id="verifydone"></div>
							<div class="tab-pane" id="unverify"></div>
							<table id="histroyId" style="width: 100%"></table>
						</div>
					</div>
					</section>
				</div>

			</div>
		</div>
	</div>
	<!-- ========================================================================================= -->
	<div class="modal fade" id="approveModelId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">申请记录</h4>
				</div>

				<div class="modal-body">
					<section class="panel">
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="tab-pane active" id="all"></div>
							<div class="tab-pane" id="verify"></div>
							<div class="tab-pane" id="verifydone"></div>
							<div class="tab-pane" id="unverify"></div>
							<table id="approveId" style="width: 100%"></table>
						</div>
					</div>
					</section>
				</div>

			</div>
		</div>
	</div>
	
		<div class="modal fade" id="checkFile" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">查询</h4>
				</div>

				<div class="modal-body">
					<form id="dataForm1">
						<input type="hidden" id="companyPk" />
						<!-- 编辑页面开始 -->
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-4 control-label">征信产品:</label>
							<div class="col-sm-8"  style="right: 100px;">
								<select id="shotName" name="shotName"class="selectpicker bla bla bli" data-live-search="true"
									required="true">
									<option value="">--请选择--</option>
									<c:forEach items="${creditReportGoodsList}" var="m">
										<option value="${m.pk}" data ="${m.shotName}">${m.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<!-- 编辑页面结束 -->
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiaoFile"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="checkFile();">查询</button>
				</div>
			</div>
		</div>
	</div>
	

	<script>
	
	
		function entersearch() {
			var event = window.event || arguments.callee.caller.arguments[0];
			if (event.keyCode == 13) {
				SearchClean();
			}
		}
	</script>
	<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>