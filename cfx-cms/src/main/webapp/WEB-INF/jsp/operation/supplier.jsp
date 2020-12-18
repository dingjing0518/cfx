<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="./pageJs/include/third-party/fancybox/fancybox.css" rel="stylesheet"
	type="text/css" />
<title>内部管理系统</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<!-- 页面内容导航开始-->
<script src="./pageJs/operation/supplier.js"></script>
</head>
<body class="sticky-header">
<style>
	.imgC{
		width: 80px;
		height: 80px;
		background:url(./style/images/bgbg.png) no-repeat;
           background-size: 100%;
	}
	.ui-autocomplete {
	    max-height: 200px;
	    max-width: 342px;
	    overflow-y: auto;
	    background-color:#FFFFFF;
	    /* 防止水平滚动条 */
	    overflow-x: hidden;
  }
  @media (max-width:1500px){
  .tongyi{width:60px;line-height:18px;}
  }
  @media (max-width:1600px){
.addressnew {
    width: 31%!important;
}
.addressnew span {
    margin-right:30px;
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
			<li><a href="#">供应商管理</a></li>
			<li class="active">供应商审核</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;height: 150px">
								<label class="col-lg-3 col-lg-2"><span>公司名称：</span> <input class="form-controlgoods input-append "  autocomplete="off" type="text" name="name"   value="" onkeydown="entersearch()"/></label>	
								<label class="col-lg-3 col-lg-2"><span>电话：</span> <input class="form-controlgoods input-append "  autocomplete="off" type="text" name="contactsTel"   value="" onkeydown="entersearch()"/></label>	
								<label class="col-lg-3 col-lg-2"><span class="tongyi">统一社会信用代码：</span> <input class="form-controlgoods input-append "  autocomplete="off" type="text" name="organizationCode"   value=""/></label>
			                    <label class="col-lg-3 col-lg-2"><span>注册时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"   value="" onkeydown="entersearch()"  readonly /></label>
			                     <label class="col-lg-3 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"   value="" readonly /></label>
			                     <label class="col-lg-3 col-lg-2"><span>是否启用：</span> <select class="selectpicker bla bla bli" name="isVisable" style="width: 200px;" ><option value="">--请选择--</option><option value="1" >启用</option><option value="2">禁用</option></select></label>	
			                    <label class="col-lg-3 col-lg-2"><span>注册电话：</span> <input class="form-controlgoods input-append " type="text" name="mobile"   value=""/></label>
			                     <label class="col-lg-3 col-lg-2"><span>最后修改时间</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"   value="" readonly /></label>
			                     <label class="col-lg-3 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"   value="" readonly /></label>
						        <label class="col-lg-3 col-lg-2"><span>审核时间</span> <input style="position: relative;z-index: 9;" class="form-controlgoods input-append date form-dateday" type="text" name="auditSpStartTime" value="" readonly /></label>
						        <label class="col-lg-3 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="auditSpEndTime" value="" readonly /></label>
						        <label class="col-lg-3 col-lg-2 addressnew" style="width: 18%;"><span>所在地区：</span>
				                     <div class="col-sm-3 address" style="padding-left:0px;padding-right: 10px;">
										<select class="form-control col-sm-3" style="padding: 6px;" id ="provinceName" name="provinceName" onchange="changeCitySearch(this);" required="true" style="float:none;">
										<option value="">--省--</option>
									 	<c:forEach var="c" items="${province}">
												<option value="${c.pk }">${c.name}</option>
											</c:forEach>
										</select>	
									</div>
									<div class="col-sm-3 address" style="padding-left:0px;padding-right: 10px;">
										<select class="form-control col-sm-3" style="padding: 6px;" id = "cityName"  name="cityName" onchange="changeAreaSearch(this);" required="true" style="float:none;"><option value="">--市--</option></select>
									</div>
									<div class="col-sm-3 address" style="padding-left:0px;padding-right: 10px;">
										<select class="form-control col-sm-3" style="padding: 6px;" id ="areaName"  name="areaName"  required="true" style="float:none;"><option value="">--区--</option></select>
									</div>
								</label>
			      				 <input type="hidden" id="auditSpStatus" name="auditSpStatus" value=""/>
						         <input type = "hidden" id="modelType" name="modelType" value="${modelType}"/>
			      				 <input type="hidden" name="parentPk" value="-1"/>	
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
			      <button class="btn btn-primary" id="btn" style="display: none;" showname="COMMON_CM_SUPPLIER_BTN_ADD" onclick="addParent()"> 新增公司</button>
		           <button style="display:none;" showname="COMMON_CM_SUPPLIER_BTN_EXPORT" class="btn btn-default dropdown-toggle" onclick="excel();" data-toggle="dropdown">导出 </button>
		           <!-- <button class="btn btn-default dropdown-toggle" id="btn" style="display: none;" showname="BTN_SUPPLIER_SYNCBANK" id="syncBankNameId" onclick="syncBank()">同步银行</button> -->
		           </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findPurchaser(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(1);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(3);">审核不通过</a>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="supplierId" style="width: 100%"></table>
                               <!--  <div class="tab-pane" id="off-shelve">
                                    
                                </div> -->
                            </div>
                        </div>
                    </section>
				</div>
				</section>
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
					<h4 class="modal-title" id="myModalLabel">供应商会员信息编辑</h4>
				</div>
				<div class="modal-body">
				<form  id="dataForm">
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">公司名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  autocomplete="off" id="companyName" name="name" required="true"/>
							<input type="hidden" id="pk" name="pk" />
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">联系人</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control"  autocomplete="off" id="contacts" name="contacts" /> 
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">联系电话</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control"  autocomplete="off" id="contactsTel" name="contactsTel"/> 
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 35px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">统一社会信用代码</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control"  autocomplete="off" id="organizationCode" name="organizationCode" required="true"/> 
						</div>
					</div>
					<div class="form-group" style="height: 35px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label">板块：</label>
						<div class="col-sm-5">
							<label style="margin-right: 15px;"><input name="block" id="cfId" type="checkbox" value="cf" style="margin-left:3px;float: left;margin-right: 3px;" />化纤</label>
							<label><input name="block" id="sxId" type="checkbox" value="sx"  style="margin-left:3px;float: left;margin-right: 3px;" />纱线</label>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">地区</label>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="province" name="province" onchange="changeCity(this);" required="true" style="float:none;">
							<option value="">--省--</option>
						 	<c:forEach var="c" items="${province}">
									<option value="${c.pk }">${c.name}</option>
								</c:forEach>
							</select>	
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="city" name="city" onchange="changeArea(this);" required="true" style="float:none;"><option value="">--市--</option></select>
						</div>
						<div class="col-sm-3">
							<select class="form-control col-sm-3" id="area" name="area"  required="true" style="float:none;"><option value="">--区--</option></select>
						</div>
					</div>
					<div class="form-group col-ms-12" style="height:100%;width:100%; display: block;">
						<img id="blUrl" class="imgC" title="1" onclick="op(this.src)" />

							<img id="ecUrl" class="imgC" title="1"
								onclick="op(this.src)" />
					</div>
					  <div class="form-group col-ms-12"
						style="height:100%;width:100%; display: block;">
						<label for="inputEmail3" class="col-sm-12 control-label"
							style="text-align: left;">上传营业执照</label>
						<div class="">
							<form enctype="multipart/form-data">
	                        <input type="file" name="file"  onchange="fileChange(this,'blUrl');" />
			                </form>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height:100%;width:100%; display: block;">
						<label for="inputEmail3" class="col-sm-12 control-label"
							style="text-align: left;">上传企业认证授权书</label>
						<div class="">
							<div class="">
							<form enctype="multipart/form-data">
	                        <input type="file" name="file"  onchange="fileChange(this,'ecUrl');" />
			                </form>
						</div>
						</div>
					</div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="childCompany" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 80%;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">子公司</h4>
				</div>
				<input type="hidden" id="parentPk"/>
				<input type="hidden" id="parentPkBlock"/><!--用于处理子公司传入总公司模块值-->
				<div class="modal-body">
				  <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findChild(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findChild(1);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findChild(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findChild(3);">审核不通过</a>
                                </li>
                            </ul>
                        </header>
			      <button showname="COMMON_CM_SUPPLIER_SUBCOM_BTN_SUBADD" class="btn btn-primary" id="addChild" onclick="addChild()" style="float:right;margin-bottom:10px;position: relative;top:-38px;display: none;">添加子公司</button>
					 <table id="childs" style="width: 100%"></table>
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
				<form   id="reasonForm">
					<input type="hidden" class="form-control" id="insertTimeR" name="insertTime"/>
					<input type="hidden" class="form-control" id="updateTimeR" name="updateTime"/>
					<input type="hidden" class="form-control" id="auditSpStatusR" name="auditSpStatus"/>
					<input type="hidden" id="auditPk" name="auditPk" />		
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">拒绝原因</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="refuseReason" name="refuseReason" required="true"/>
						</div>
					</div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="qx" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="refuse();">保存</button>
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
					<h4 class="modal-title" id="myModalLabelid">供应商审核信息</h4>
				</div>
				<div class="modal-body">
				<form   id="dataForm">
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">公司名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" readonly="readonly" id="auditCompayName" name="name" required="true"/>
							<input type="hidden" id="companyFlage" name="companyFlage" />
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">联系人</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" readonly="readonly" id="auditContacts" name="contacts" required="true"/> 
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">联系电话</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" readonly="readonly" id="auditContactsTel" name="contactsTel" required="true"/> 
						</div>
					</div>
					<div class="form-group" style="height: 35px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label">板块：</label>
						<div class="col-sm-5">
							<label style="margin-right: 15px;"><input name="blockCredit" id="cfCreditId" type="checkbox" value="cf" style="margin-left:3px;float: left;margin-right: 3px;" />化纤</label>
							<label><input name="blockCredit" id="sxCreditId" type="checkbox" value="sx"  style="margin-left:3px;float: left;margin-right: 3px;" />纱线</label>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 35px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">统一社会信用代码</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" readonly="readonly" id="auditOrganizationCode" name="organizationCode" required="true"/> 
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">地区</label>
						<div class="col-sm-10" style="margin-left:-15px;">
						<div class="col-sm-3">
							<input type="text" class="form-control" readonly="readonly" id="auditProvince" name="auditProvince" required="true"/> 	
						</div>
						<div class="col-sm-3">
						<input type="text" class="form-control" readonly="readonly" id="auditCity" name="auditCity" required="true"/>
						</div>
						<div class="col-sm-3">
						<input type="text" class="form-control" readonly="readonly" id="auditArea" name="auditArea" required="true"/>
						</div>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">不通原因</label>
						<div class="col-sm-10">
						<textarea class="form-control" name="auditNoPassR" id="auditNoPassR" value=""
									style="height: 60px;"></textarea>
						</div>
					</div>
					<br/>
					<br/>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">证件信息</label>
					</div>
					<div class="form-group col-ms-12" style="height:100%;width:100%; display: block;">
						<img id="auditBlUrl" class="imgC" title="营业执照" onclick="op(this.src)"/>
						<img id="auditEcUrl" class="imgC" title="企业认证授权书" onclick="op(this.src)"/>
					</div>
				</form>
				</div>
				<div class="modal-footer" style="margin-top:-15px;" id="companyAuthID">
					<button type="button" style="display: none;" showname="COMMON_CM_SUPPLIER_CREDIT_COM_BTN_PASS" class="btn btn-primary" onclick="auditSubmit(2);">审核通过</button>
					<button type="button" style="display: none;" showname="COMMON_CM_SUPPLIER_CREDIT_COM_BTN_UNPASS" class="btn btn-primary" onclick="auditSubmit(3);">审核不通过</button>
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
				</div>
				<div class="modal-footer" style="margin-top:-15px;" id="subCompanyAuthID">
					<button type="button" style="display: none;" showname="COMMON_CM_SUPPLIER_CREDIT_SUBCOM_BTN_PASS" class="btn btn-primary" onclick="auditSubmit(2);">审核通过</button>
					<button type="button" style="display: none;" showname="COMMON_CM_SUPPLIER_CREDIT_SUBCOM_BTN_UNPASS" class="btn btn-primary" onclick="auditSubmit(3);">审核不通过</button>

					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>

		<!-- 绑定超级管理员 -->
	<div class="modal fade" id="setAdmin" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" style="width: 600px;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">绑定超级管理员</h4>
				</div>
				<input type="hidden" id="companyType"/>			
				<div class="modal-body">
				<label class="col-lg-3 col-lg-2">
						<span>手机号：</span>
					 </label> 
				 	<input type="text" autocomplete="off" name="adminMobile" id = "adminMobile" value="" />
					<button class="btn btn-primary" id="btn" onclick="searchTabsMonbile(1)"> 搜索</button>
					 <table id="members" style="width: 100%"></table>
					 <input type="hidden" id="oldMemberPk"/>
					 <input type="hidden" id="oldChildMemberPk"/>
					 <input type="hidden" id="getAdminByCompanyPk"/>
					 <input type="hidden" id="flag"/>
					 
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-primary" onclick="sureAdmin();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 图片放上去显示js -->
	<script type="text/javascript">
	 function op(c_url){
		 if( c_url.indexOf(".jpg") !=-1 
				 || c_url.indexOf(".png") !=-1
				 || c_url.indexOf(".jpeg") !=-1){//判断是否存在图片
			 window.open(c_url); 
		 }
	 }
	
	 function showImg1(){
			document.getElementById("wxImg1").style.display='block';
		}
		function hideImg1(){
			document.getElementById("wxImg1").style.display='none';
		}
	//图像默认图片
	function errorImg(img) {
	    img.src = "./style/images/bgbg.png";
	    img.onerror = null;
	    if(img.src = "./style/images/bgbg.png"){
	    	$('#supplierId a').removeAttr("href");
	    	$('#childs a').removeAttr("href");
	    }
	};
	
	/* $(function(){
		$('.bigpicture').bigic();
	}); */
	
	</script>
	<!-- 汉化inputfile-->
	<script>
	function startUploading(self,key){
		var formData = new FormData();
		formData.append( "file", $(self).prev()[0].files[0]);
		$.ajax({
		    url: getRootPath() + "/uploadPhoto.do",
		    dataType: "json",
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			 $("#"+key).attr("src",res.data);
		}).fail(function(res) {
			
		});
	}
	function fileChange(self,pk){
		var formData = new FormData();
		formData.append( "file", $(self)[0].files[0]);
		$.ajax({
		    url: getRootPath() + "/uploadPhoto.do",
		    dataType: "json",
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
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

	
	    
function getKey(label, arr) {  
 var key = false;  
 for (var i = 0; i < arr.length; i++) {  
     var o = arr[i];  
     if (label == o.label) {  
         return o.value;  
     }  
 }  
}	
	
	
	
	
	 </script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>