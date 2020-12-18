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
<script src="./pageJs/manage/sysProperty.js"></script>
<style>
#dataForm .bootstrap-select > .dropdown-toggle{
  width:180px;
}
.datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber{
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
}
span.ellipsis{
	overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 900px;
    display: block;
}
</style>
</head>
<body class="sticky-header">
	<!-- 页面内容导航开始-->
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">管理中心</a></li>
			<li class="active">系统配置</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
					<label class="col-lg-2"><span>&nbsp;产品名称：</span>
						<input type="text"  name="productName" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
					</label>
					<label class="col-lg-2"><span>&nbsp;类型：</span>
						<select name="type" class="form-controlgoods input-append ">
							<option value="">--请选择--</option>
							<option value="1">系统</option>
							<option value="2">银行</option>
							<option value="3">线上支付</option>
							<option value="4">风控</option>
							<option value="5">票据</option>
							<option value="6">erp</option>
						</select>
					</label>
					     <button class="btn btn-primary" onclick="searchSysProperty(1)"
									style="margin-bottom: 10px;">
									查询
								</button>
					<button class="btn btn-primary" onclick="searchSysProperty(2)"
							style="margin-bottom: 10px;">
						清除
					</button>
					     <button class="btn btn-primary" style="display:none; margin-bottom: 10px;" showname="MG_SYSPROPERTY_BTN_ADD" onclick="editToken('')"
									data-toggle="modal" data-target="#myModal1"
									style="margin-bottom: 10px;">
									新增 <i class="fa fa-plus"></i>
						</button>
				</header>
				<div class="panel-body">
					<div class="adv-table editable-table ">
						<div class="clearfix">
							<div class="btn-group">
								<div class="modal fade" id="myModal1" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="myModalLabel">系统配置</h4>
											</div>
											<div class="modal-body">
												<!-- 开始 -->
												<form class="form-horizontal" id="dataForm">
													<input type="hidden" id="pk" name="pk" />
													<div class="form-group">
														<label for="inputPassword3" class="col-sm-2 control-label">类型</label>
														<div class="col-sm-6" style="float:left;width:200px;margin-left:10px" >
															<select  name="type" id="type" class="selectpicker bla bla bli" data-live-search="true" onchange="changeType('','',2)">
																<option value="">--请选择--</option>
																<option value="1">系统</option>
																<option value="2">银行</option>
																<option value="3">线上支付</option>
																<option value="4">风控</option>
																<option value="5">票据</option>
																<option value="6">erp</option>
															</select>
														</div>
													</div>

													<div class="form-group" id="isShowByType">
														<label for="inputPassword3" class="col-sm-2 control-label">产品</label>
														<div class="col-sm-6" style="float:left;width:200px;margin-left:10px" >
															<select id="productPk" name="productPk" class="selectpicker bla bla bli"   data-live-search="true" >
													          	 	<option value="">--请选择--</option>
			   												</select>
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">配置属性</label>
														<div class="col-sm-10" id="cleanEdit">
															<div class="step" id="rawAddDiv">
																<input type="text" style="border-radius: 4px;margin-left: 12px!important;height: 34px;width: 110px;" name="param" id="key"/>
																<input type="text" style="border-radius: 4px;margin-left: 12px!important;height: 34px;width: 255px;" name="param" id="value"/>
															</div>
														</div>
													</div>
												</form>
												<!-- 结束 -->
											</div>
											<div class="modal-footer">
												<a class="inster" id="addRaw" style="color: #0000FF;border: none;background: transparent;padding-left: 30px;margin-bottom: 15px;"><b>新增配置属性</b></a>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<button type="button" class="btn btn-default" id="quxiao"
													data-dismiss="modal">取消</button>
												<button type="button" class="btn btn-primary"
													onclick="submit();">保存</button>
											</div>
										</div>
									</div>
								</div>
								<!-- 模态框结束 -->
							</div>
						</div>
						<div class="space15"></div>
						<table id="tokenId" style="width: 100%"></table>
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!--主题内容结束-->
	<input type="hidden" id="accountName" value="${adto.name}"/>
</body>
</html>
<script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	searchToken();  
       }  
} 
</script>