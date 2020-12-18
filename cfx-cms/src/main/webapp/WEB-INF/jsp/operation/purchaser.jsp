<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<link href="./pageJs/include/third-party/fancybox/fancybox.css"
	rel="stylesheet" type="text/css" />
<title>内部管理系统</title>
<!-- 页面内容导航开始-->
<script src="./pageJs/operation/purchaser.js"></script>
<style>
.bootstrap-select>.dropdown-toggle {
	float: none;
}
</style>
<style>
.bootstrap-select>.dropdown-toggle {
	float: right;
}
.ui-autocomplete {
	    max-height: 200px;
	    max-width: 342px;
	    overflow-y: auto;
	    background-color:#FFFFFF;
	    overflow-x: hidden;
  }
</style>
</head>
<%-- <jsp:include page="/WEB-INF/jsp/include/subPage.jsp"></jsp:include> --%>
<body class="sticky-header">
	<style>
.imgC {
	width: 80px;
	height: 80px;
	background: url(./style/images/bgbg.png) no-repeat;
	background-size: 100%;
}
@media (max-width:1500px){
.tongyi{width:60px;line-height:18px;}
}
@media (max-width:1600px){
.addressnew {
    width: 31%!important;
}
.addressnew span {
    margin-right: 62px;
}
.addressnew .address{
width:24%;}
}
@media (max-width:1366px){
.addressnew span {
    margin-right: 25px;
}
}
</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">采购商管理</a></li>
			<li class="active">采购商审核</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading"
					style="margin-bottom:10px;"> <label
					class="col-lg-3 col-lg-2"><span>公司名称：</span> <input
					class="form-controlgoods input-append " type="text"
					autocomplete="off" name="name" value="" onkeydown="entersearch()" /></label>
				<label class="col-lg-3 col-lg-2"><span>注册电话：</span> <input
					class="form-controlgoods input-append " type="text"
					autocomplete="off" name="mobile" value="" /></label> <label
					class="col-lg-3 col-lg-2"><span>是否启用：</span> <select
					class="selectpicker bla bla bli " name="isVisable"><option
							value="">--请选择--</option>
						<option value="1">启用</option>
						<option value="2">禁用</option></select></label> <label class="col-lg-3 col-lg-2"><span>注册时间：</span>
					<input class="form-controlgoods input-append date form-dateday"
					type="text" name="insertStartTime" value="" readonly /></label> <label
					class="col-lg-3 col-lg-2"><span>至</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="insertEndTime" value="" readonly /></label> <label
					class="col-lg-3 col-lg-2"><span class="tongyi">统一社会信用代码：</span> <input
					class="form-controlgoods input-append " type="text"
					autocomplete="off" name="organizationCode" value="" /></label> <label
					class="col-lg-3 col-lg-2"><span>最后修改时间</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="updateStartTime" value="" readonly /></label> <label
					class="col-lg-3 col-lg-2"><span>至</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="updateEndTime" value="" readonly /></label> <label
					class="col-lg-3 col-lg-2"><span>审核时间</span> <input
					style="position: relative;z-index: 9;" class="form-controlgoods input-append date form-dateday"
					type="text" name="auditStartTime" value="" readonly /></label> <label
					class="col-lg-3 col-lg-2"><span>至</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="auditEndTime" value="" readonly /></label> <input
					type="hidden" id="auditStatus" name="auditStatus" value="" />
					<label class="col-lg-3 col-lg-2 addressnew" style="    width: 18%;"><span>所在地区：</span> 
	                     <div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
							<select class="form-control col-sm-3" style="padding:6px;" id ="provinceName" name="provinceName" onchange="changeCitySearch(this);" required="true" style="float:none;">
							<option value="">--省--</option>
						 	<c:forEach var="c" items="${province}">
									<option value="${c.pk }">${c.name}</option>
								</c:forEach>
							</select>	
						</div>
						<div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
							<select class="form-control col-sm-3" id = "cityName" style="padding:6px;"  name="cityName" onchange="changeAreaSearch(this);" required="true" style="float:none;"><option value="">--市--</option></select>
						</div>
						<div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
							<select class="form-control col-sm-3" id ="areaName" style="padding:6px;"  name="areaName"  required="true" style="float:none;"><option value="">--区--</option></select>
						</div>
					</label>
					 <input type="hidden" name="parentPk" value="-1" />

				<button class="btn btn-primary" id="btn" onclick="searchTabs(1)">
					搜索</button>
				<button class="btn btn-primary" id="btn" onclick="searchTabs(2)">
					清除</button>
				<button style="display: none;" showname="COMMON_CM_PURCHASER_BTN_ADD"
					class="btn btn-primary" id="btn" onclick="addParent()">
					新增公司</button>
				<button style="display: none;" showname="COMMON_CM_PURCHASER_BTN_EXPORT"
					class="btn btn-default dropdown-toggle" onclick="excel();"
					data-toggle="dropdown">导出</button>
						<input type = "hidden" id="modelType" name="modelType" value="${modelType}"/>
				</header>
				<div class="panel-body">
					<section class="panel"> <header
						class="panel-heading custom-tab ">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#all" data-toggle="tab"
							onclick="findPurchaser(0);">全部</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findPurchaser(1);">待审核</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findPurchaser(2);">审核通过</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findPurchaser(3);">审核不通过</a></li>
					</ul>
					</header>
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="tab-pane active" id="all"></div>
							<div class="tab-pane" id="verify"></div>
							<div class="tab-pane" id="verifydone"></div>
							<div class="tab-pane" id="unverify"></div>
							<table id="purchaserId" style="width: 100%"></table>
						</div>
					</div>
					</section>
				</div>
				</section>
			</div>
		</div>
	</div>

	<div class="modal fade" id="childCompany" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document"
			style="width: 80%; height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">公司管理</h4>
				</div>
				<input type="hidden" id="parentPk" />
				<div class="modal-body">
					<header class="panel-heading custom-tab ">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#all" data-toggle="tab"
							onclick="findChild(0);">全部</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findChild(1);">待审核</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findChild(2);">审核通过</a></li>
						<li class=""><a href="#off-shelve" data-toggle="tab"
							onclick="findChild(3);">审核不通过</a></li>
					</ul>
					</header>
					<button showname="COMMON_CM_PURCHASER_COM_BTN_SUBADD" class="btn btn-primary" id="addChild" onclick="addChild()"
						style="float: right; margin-bottom: 10px; position: relative; top: -38px;display: none;">添加子公司</button>
					<table id="childs" style="width: 100%"></table>
				</div>

			</div>
		</div>
	</div>
	<div class="modal fade" id="editCompayId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1051">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">采购商信息编辑</h4>
				</div>
				<div class="modal-body">
					<form id="dataForm">
						<!-- 树形 -->
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">公司名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" autocomplete="off"
									id="compayName" name="name" required="true" maxlength="50" /> <input
									type="hidden" id="pk" name="pk" /> <input type="hidden"
									id="editCompanyRemarks" name="editCompanyRemarks" /> <input
									type="hidden" id="auditStatusFlag" name="auditStatusFlag" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">联系人</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" autocomplete="off"
									id="contacts" name="contacts" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">联系电话</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" autocomplete="off"
									id="contactsTel" name="contactsTel" required="true" />
							</div>
						</div>

						<div class="form-group col-ms-12"
							style="height: 35px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">统一社会信用代码</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" autocomplete="off"
									id="organizationCode" name="organizationCode" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">地区</label>
							<div class="col-sm-3">
								<select class="form-control col-sm-3" id="province"
									name="province" onchange="changeCity(this);" required="true"
									style="float: none;">
									<option value="">--省--</option>
									<c:forEach var="c" items="${province}">
										<option value="${c.pk }">${c.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-3">
								<select class="form-control col-sm-3" id="city" name="city"
									onchange="changeArea(this);" required="true"
									style="float: none;">
									<option value="">--市--</option>
								</select>
							</div>
							<div class="col-sm-3">
								<select class="form-control col-sm-3" id="area" name="area"
									onchange="changeTown(this);" required="true"
									style="float: none;">
									<option value="">--区--</option>
								</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 100%; width: 100%; display: block;">
							<img id="blUrl" class="imgC" title="营业执照" onclick="op(this.src)" />

							<img id="ecUrl" class="imgC" title="企业认证授权书"
								onclick="op(this.src)" />
						</div>
						<div class="form-group col-ms-12"
							style="height: 100%; width: 100%; display: block;">
							<label for="inputEmail3" class="col-sm-12 control-label"
								style="text-align: left;">上传营业执照</label>
							<div class="">
								<form enctype="multipart/form-data">
									<input type="file" name="file"
										onchange="fileChange(this,'blUrl');" style="float: none;" />
								</form>
							</div>
						</div>

						<div class="form-group col-ms-12"
							style="height: 100%; width: 100%; display: block;">
							<label for="inputEmail3" class="col-sm-12 control-label"
								style="text-align: left;">上传企业认证授权书</label>
							<div class="">
								<form enctype="multipart/form-data">
									<input type="file" name="file"
										onchange="fileChange(this,'ecUrl');" style="float: none;" />
								</form>
							</div>
						</div>
					</form>
				</div>

				<div class="modal-footer" style="margin-top: -15px;">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!--发票信息 -->
	<div class="modal fade" id="editInvoiceId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1051">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">发票信息编辑</h4>
				</div>
				<div class="modal-body">
					<form id="dataFormInvoice">
						<!-- 树形 -->
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">公司名称</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="invoiceCompayName"
									name="invoiceCompayName" required="true" readonly="readonly" />
								<input type="hidden" id="invoiceCompanyPk"
									name="invoiceCompanyPk" /> <input type="hidden" id="invoicePk"
									name="invoicePk" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">纳税人识别号</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="taxidNumber"
									name="taxidNumber" required="true" />
							</div>
						</div>

						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">注册地区</label>
							<div class="col-sm-3">
								<select class="form-control col-sm-3" id="invoiceProvince"
									name="invoiceProvince" onchange="changeInvoiceCity(this);"
									style="float: none;">

								</select>
							</div>
							<div class="col-sm-3">
								<select class="form-control col-sm-3" id="invoiceCity"
									name="invoiceCity" onchange="changeInvoiceArea(this);"
									style="float: none;">
									<option value="">--市--</option>
								</select>
							</div>
							<div class="col-sm-3">
								<select class="form-control col-sm-3" id="invoiceArea"
									name="invoiceArea" onchange="changeTown(this);"
									style="float: none;">
									<option value="">--区--</option>
								</select>
							</div>
						</div>




						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">注册地址</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="regAddress"
									name="regAddress" required="true" />
							</div>
						</div>

						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">联系电话</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="regPhone"
									name="regPhone" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">开户银行</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="bankName"
									name="bankName" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">银行账号</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="bankAccount"
									name="bankAccount" required="true" />
							</div>
						</div>
					</form>
				</div>

				<div class="modal-footer" style="margin-top: -15px;">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary"
						onclick="submitInvoice();">保存</button>
				</div>
			</div>
		</div>
	</div>



	<!-- 审核信息页面 -->
	<div class="modal fade" id="auditCompayId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1051">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabelid">采购商审核信息</h4>
				</div>
				<div class="modal-body">
					<form id="dataForm">
						<!-- 树形 -->
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">公司名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" readonly="readonly"
									id="auditCompayName" name="name" required="true" /> <input
									type="hidden" id="auditPk" name="auditPk" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">联系人</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" readonly="readonly"
									id="auditContacts" name="contacts" required="true" />
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">联系电话</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" readonly="readonly"
									id="auditContactsTel" name="contactsTel" required="true" />
							</div>
						</div>

						<div class="form-group col-ms-12"
							style="height: 40px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">统一社会信用代码</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" readonly="readonly"
									id="auditOrganizationCode" name="organizationCode"
									required="true" />
							</div>
						</div>

						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">地区</label>
							<div class="col-sm-10" style="margin-left: -15px;">
								<div class="col-sm-3">
									<input type="text" class="form-control" readonly="readonly"
										id="auditProvince" name="auditProvince" required="true" />
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control" readonly="readonly"
										id="auditCity" name="auditCity" required="true" />
								</div>
								<div class="col-sm-3">
									<input type="text" class="form-control" readonly="readonly"
										id="auditArea" name="auditArea" required="true" />
								</div>
							</div>
						</div>

						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">不通原因</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="auditNoPassR"
									id="auditNoPassR" value="" style="height: 60px;"></textarea>
							</div>
						</div>

						<br/>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">备注</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="remarks" id="remarks"
									value="" style="height: 60px;"></textarea>
							</div>
						</div>


						<br /> <br />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">证件信息</label>
						</div>
						<div class="form-group col-ms-12"
							style="height: 100%; width: 100%; display: block;">
							<img id="auditBlUrl" class="imgC" title="营业执照"
								onclick="op(this.src)" /> <img id="auditEcUrl" class="imgC"
								title="企业认证授权书" onclick="op(this.src)" />
						</div>
					</form>
				</div>

				<div class="modal-footer" style="margin-top: -15px;" id="companyAuthID">
					<button type="button" style="display: none;" showname="COMMON_CM_PURCHASER_CREDIT_COM_BTN_PASS" class="btn btn-primary"
						onclick="auditSubmit(2);">审核通过</button>
					<button type="button" style="display: none;" showname="COMMON_CM_PURCHASER_CREDIT_COM_BTN_UNPASS" class="btn btn-primary"
						onclick="auditSubmit(3);">审核不通过</button>
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
				</div>
				<div class="modal-footer" style="margin-top: -15px;" id="subCompanyAuthID">
					<button type="button" style="display: none;" showname="COMMON_CM_PURCHASER_CREDIT_SUBCOM_BTN_PASS" class="btn btn-primary"
							onclick="auditSubmit(2);">审核通过</button>
					<button type="button" style="display: none;" showname="COMMON_CM_PURCHASER_CREDIT_SUBCOM_BTN_UNPASS" class="btn btn-primary"
							onclick="auditSubmit(3);">审核不通过</button>
					<button type="button" class="btn btn-default" id="quxiao"
							data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="noAuditCompany" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">审核不通过</h4>
				</div>
				<div class="modal-body">
					<form id="reasonForm">
						<input type="hidden" class="form-control" id="insertTimeR"
							name="insertTime" /> <input type="hidden" class="form-control"
							id="updateTimeR" name="updateTime" /> <input type="hidden"
							class="form-control" id="auditStatusR" name="auditStatus" /> <input
							type="hidden" id="auditPk" name="auditPk" />
						<!-- 树形 -->
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">拒绝原因</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="refuseReason"
									name="refuseReason" required="true" />
							</div>
						</div>
					</form>
				</div>

				<div class="modal-footer" style="margin-top: -15px;">
					<button type="button" class="btn btn-default" id="qx"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="refuse();">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 添加备注 -->
	<div class="modal fade" id="remarksCompany" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="remarksModalLabel">编辑备注</h4>
				</div>
				<div class="modal-body">
					<form id="remarksForm">
						<input type="hidden" class="form-control" id="remarksPk"
							name="remarksPk" /> <input type="hidden" class="form-control"
							id="remarksCompany" name="remarksCompany" />
						<!-- 树形 -->
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">备注</label>
							<div class="col-sm-10">
								<textarea style="resize: none; height: 100px;"
									class="form-control" id="remarks1"
									contenteditable="plaintext-only"></textarea>
							</div>
						</div>
					</form>
				</div>

				<div class="modal-footer" style="margin-top: 50px;">
					<button type="button" class="btn btn-default" id="qx"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary"
						onclick="submitRemarks();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 绑定超级管理员 -->
	<div class="modal fade" id="setAdmin" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document"
			style="width: 600px; height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">绑定超级管理员</h4>
				</div>
				
				
				
				<input type="hidden" id="companyType" /> <input type="hidden"
					id="getAdminByCompanyTypePk" />
				<div class="modal-body">
				
					<label class="col-lg-3 col-lg-2">
						<span>手机号：</span>
					 </label> 
				 	<input type="text" autocomplete="off" name="adminMobile" id = "adminMobile" value="" />
					<button class="btn btn-primary" id="btn" onclick="searchTabsMonbile(1)"> 搜索</button>
					
					<table id="members" style="width: 100%"></table>
					<input type="hidden" id="oldMemberPk" />
					
				</div>
				<div class="modal-footer" style="margin-top: -15px;">
					<button type="button" class="btn btn-primary"
						onclick="sureAdmin();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 图片放上去显示js -->
	<script type="text/javascript">
		function entersearch() {
			var event = window.event || arguments.callee.caller.arguments[0];
			if (event.keyCode == 13) {
				searchTabs();
			}
		}
		function op(c_url) {
			if (c_url.indexOf(".jpg") != -1 || c_url.indexOf(".png") != -1
					|| c_url.indexOf(".jpeg") != -1) {//判断是否存在图片
				window.open(c_url);
			}
			return false;
		}

		function showImg1() {
			document.getElementById("wxImg1").style.display = 'block';
		}
		function hideImg1() {
			document.getElementById("wxImg1").style.display = 'none';
		}
		function errorImg(img) {
			img.src = "./style/images/bgbg.png";
			img.onerror = null;
			if(img.src = "./style/images/bgbg.png"){
		    	$('#purchaserId a').removeAttr("href");
		    	$('#childs a').removeAttr("href");
		    }
		}
	</script>
	<!-- 汉化inputfile-->
	<script>
		$(function() {
			/* $('.bigpicture').bigic(); */
		});
		$(".file").each(
				function(i, n) {
					$("#file-0" + i).fileinput(
							{
								language : 'zh',
								uploadUrl : getRootPath() + "/uploadPhoto.do", // you must set a valid URL here else you will get an error  
								allowedFileExtensions : [ 'xls', 'jpg', 'png',
										'gif' ],
								maxFileCount : 3, //同时最多上传3个文件  
								showUpload : true,//是否显示上传按钮  
								showRemove : false,//是否显示删除按钮  
								showCaption : false,
								//allowedFileTypes: ['image', 'video', 'flash'],  这是允许的文件类型 跟上面的后缀名还不是一回事  
								//这是文件名替换  
								slugCallback : function(filename) {
									return filename.replace('(', '_').replace(
											']', '_');
								}
							});
					//这是提交完成后的回调函数    
					$("#file-0" + i).on(
							"fileuploaded",
							function(event, data, previewId, index) {
								$("#file-0" + i).parent().parent().prev().attr(
										"src", data.response.data);

							});
					$("#file-0" + i).on('filesuccessremove',
							function(event, id) {
								/* if($("#PURL_flag").val()==0){
									$("#tupian").val('');
								}else{
									$("#tupian").val($('#PURL')[0].src);
								} */

							});
				});
		function fileChange(self, pk) {
			var formData = new FormData();
			formData.append("file", $(self)[0].files[0]);
			$.ajax({
				url : getRootPath() + "/uploadPhoto.do",
				dataType : "json",
				type : 'POST',
				cache : false,
				data : formData,
				processData : false,
				contentType : false
			}).done(
					function(res) {
						if (res.data.indexOf(".jpg") != -1|| res.data.indexOf(".png") != -1	|| res.data.indexOf(".jpeg") != -1) {
							$("#" + pk).attr("src", res.data);
						} else {
							var index = res.data.lastIndexOf("\/");
							res.data = res.data.substring(index + 1,
									res.data.length);
							var json = $.parseJSON(res.data);
							new $.flavr({
								modal : false,
								content : json.msg
							});
						}
					}).fail(function(res) {
			});

		}
	</script>
	<script

		src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>