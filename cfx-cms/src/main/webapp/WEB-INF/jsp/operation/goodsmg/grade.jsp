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
<script src="./pageJs/operation/goodsmg/grade.js"></script>
</head>
<body class="sticky-header">
	<!-- 页面内容导航开始-->
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营中心</a></li>
			<li class="active">商品等级</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading"
					style="margin-bottom:10px;"> <label
					class="col-lg-3 col-lg-2"><span>&nbsp;是否显示：</span>
					<select name="isVisable" class="selectpicker bla bla bli">
						<option value="">--请选择--</option>
						<option value="1">启用</option>
						<option value="2">禁用</option>

				</select> </label>
				<button class="btn btn-primary" id="btn" onclick="SearchClean(1);">
					搜索</button>
				<button class="btn btn-primary" id="btn" onclick="SearchClean(2);">
					清除
					</butto>
				</header>
				<div class="panel-body">
					<div class="adv-table editable-table ">
						<div class="clearfix">
							<div class="btn-group">
								<button onclick="editGrade('','','')" style="display:none;" showname="OPER_GOODS_SETTINGS_GRADE_BTN_ADD" class="btn btn-primary btn-new"
									data-toggle="modal" data-target="#myModal1"
									style="margin-bottom: 10px;">
									新增 <i class="fa fa-plus"></i>
								</button>
								<!-- 开始 -->
								<div class="modal fade" id="myModal1" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="myModalLabel">编辑等级</h4>
											</div>
											<div class="modal-body">
												<!-- 开始 -->
												<form class="form-horizontal" id="dataForm">
													<input type="hidden" id="pk" name="pk" />

													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">等级名称</label>
														<div class="col-sm-10">
															<input class="form-control" name="name" id="name" maxlength="20" required="true"
																><span
																id="namePrompt"></span>
														</div>
													</div>
													
													
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">中文名称</label>
														<div class="col-sm-10">
															<input class="form-control" name="chineseName" maxlength="30" id="chineseName" 
																><span
																id="chineseNamePrompt"></span>
														</div>
													</div>
													
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">排序</label>
														<div class="col-sm-10">
															<input class="form-control" name="sort" value="0" maxlength="8"
																id="sort">
														</div>
													</div>
												<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">是否禁用</label>
														<div class="col-sm-5">
															 <label>启用<input
																name="isVisable" type="radio" value="1"  style="margin-left:3px;" />
															</label> <label>禁用<input name="isVisable" type="radio"
																value="2" style="margin-left:3px;"/>
															</label>
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
						<table id="gradeId" style="width: 100%"></table>


					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!--主题内容结束-->
</body>
</html>