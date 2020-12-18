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
<script src="./pageJs/marketing/personnel.js"></script>
<style>
#dataForm .bootstrap-select > .dropdown-toggle{
   width:180px;
}
#dataForm button.btn.dropdown-toggle {
    float: none!important;
}
@media (max-width:1366px){
	.responsiveinput .bootstrap-select > .dropdown-toggle{width:100px;}
	.responsiveinput{width:18%;}
}
@media (max-width:1217px){
	.responsiveinput .bootstrap-select > .dropdown-toggle{width:90px;}
}
</style>
</head>
<body class="sticky-header">
	<!-- 页面内容导航开始-->
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">人员管理</a></li>
			<li class="active">人员管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading"
					style="margin-bottom:10px;"> 
					<label class="col-lg-2 col-lg-3 responsiveinput"><span>&nbsp;账号类型：</span>
						<select class="selectpicker bla bla bli" name="type" id=""   >
							<option value="">--请选择--</option>
							<option value="1">业务员经理</option>
							<option value="2">平台交易员</option>
							<option value="3">区域经理</option>
						</select> 
					</label>
					<label class="col-lg-2 col-lg-3 responsiveinput"><span>&nbsp;是否显示：</span>
					<select name="isVisable" class="selectpicker bla bla bli">
						<option value="">--请选择--</option>
						<option value="1">启用</option>
						<option value="2">禁用</option>

				</select> 
				</label>
				<label class="col-lg-2 col-lg-3 responsiveinput" >
				<span>&nbsp;账号管理：</span>
				<select  name="accountPk" class="selectpicker bla bla bli"  data-live-search="true">
						   <option value="">--请选择--</option>
							<c:forEach items="${accountList}" var="m">
							<option value="${m.pk}">${m.name}</option>
							</c:forEach>  
					</select>  
				</label>
				<label  class="col-lg-2 col-lg-3 responsiveinput">
				<span>&nbsp;所属大区：</span>
				    <select  name="regionPk"  class="selectpicker bla bla bli"  data-live-search="true">
						   <option value="">--请选择--</option>
							<c:forEach items="${regionNoIsVisiableList}" var="m">
							<option value="${m.pk}">${m.name }</option>
							</c:forEach>  
					</select>  
				</label>
				   
		  <button class="btn btn-primary" id="btn" onclick="Search();"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="cleanpar();Search();"> 清除</button>
				<a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="MARKET_PERSON_BTN_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal3"
						onclick="javascript:editAccount('',''); ">账号分配 </button>
				</a> </header>
				
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
												<h4 class="modal-title" id="myModalLabel">分配账号</h4>
											</div>
											<div class="modal-body">
												<!-- 开始 -->
												<form class="form-horizontal" id="dataForm">
													<input type="hidden" id="pk" name="pk" />
													<input type="hidden" id="insertTime"  /> 
													<div class="form-group">
														<label for="inputPassword3" class="col-sm-2 control-label">账号类型：</label>
														<div class="col-sm-5" style="width:210px;">
															<select class="selectpicker bla bla bli"  data-live-search="true"   name="type" id="type"  onchange="checktype(this.value);" required="true">
															<option value="">--请选择--</option>
															<option value="1">业务经理</option>
															<option value="2">平台交易员</option>
															<option value="3">区域经理</option>
															</select>
														</div>
													</div>
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">账号：</label>
														<div class="col-sm-5" style="margin-left:-36px;">
															<select  id="accountPk" name="accountPk" class="selectpicker bla bla bli"  data-live-search="true" required="true">
															</select>  
														</div>
													</div>
													<div class="form-group" >
														<label for="inputEmail3" class="col-sm-2 control-label">所属大区：</label>
														<div class="col-sm-5" style="margin-left:-36px;">
															<select name="regionPk" id="regionPk" class="selectpicker bla bla bli"  data-live-search="true">
																   <option value="">--请选择--</option>
										                            <c:forEach items="${regionNoIsVisiableList}" var="m">
										                            <option value="${m.pk}">${m.name }</option>
										                            </c:forEach>  
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
						<table id="personnelId" style="width: 100%"></table>

						 
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!--主题内容结束-->
</body>
</html>