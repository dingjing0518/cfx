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
<script src="./pageJs/manage/tokenManage.js"></script>
<style>
#dataForm .bootstrap-select > .dropdown-toggle{
  width:180px;
}
</style>
</head>
<body class="sticky-header">
	<!-- 页面内容导航开始-->
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">管理中心</a></li>
			<li class="active">token管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
				<label class="col-lg-3 col-lg-2"><span>&nbsp;店铺名称：</span> 
					         <input type="text"  name="storeName" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
					     </label>
					     <button id="editable-sample_new" class="btn btn-primary" onclick="searchToken()"
									style="margin-bottom: 10px;">
									查询
								</button>
					     <button id="editable-sample_new" class="btn btn-primary" style="display:none; margin-bottom: 10px;" showname="MG_TOKEN_BTN_ADD" onclick="editToken('')"
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
												<h4 class="modal-title" id="myModalLabel">编辑账号</h4>
											</div>
											<div class="modal-body">
												<!-- 开始 -->
												<form class="form-horizontal" id="dataForm">
													<input type="hidden" id="pk" name="pk" />
													<input type="hidden" id="isEdit" name ="isEdit" value=""/>
													<div class="form-group">
														<label for="inputPassword3" class="col-sm-2 control-label">店铺名称</label>
														<div class="col-sm-6" style="float:left;width:200px;margin-left:10px">
															<select id="storePk" name="storePk" class="selectpicker bla bla bli"   data-live-search="true" >
													          	 	<option value="">--请选择--</option>
													            	<c:forEach items="${companyList }" var="m">
													            	<option value="${m.pk}">${m.name }</option>
													           	    </c:forEach>
			   												</select>
														</div>
														<div class="col-sm-4">
															<button type="button" class="btn btn-primary"
																onclick="produceApp();">自动生成</button>
														</div>
													</div>
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">appId</label>
														<div class="col-sm-10">
															<input class="form-control" name="appId" id="appId" disabled="disabled">
														</div>
													</div>
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">appSecret</label>
														<div class="col-sm-10">
															<input class="form-control" name="appSecret" id="appSecret" disabled="disabled">
														</div>
													</div>
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">访问方式</label>
														<div class="col-sm-10">
																<input class="form-control" name="accType" id="accType">
														</div>
													</div>
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">类型</label>
														<div class="col-sm-10">
															<select class="form-control" name="tokenType" id="tokenType" >
																<option value="/erp/">erp</option>
																<option value="/b2b/pc">pc</option>
																<option value="/b2b/wap">wap</option>
																<option value="/b2b/app">app</option>
															</select>
														</div>
													</div>
												</form>
												<!-- 结束 -->
											</div>
											<div class="modal-footer">
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