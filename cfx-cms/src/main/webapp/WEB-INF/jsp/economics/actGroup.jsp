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
<script src="./pageJs/economics/actGroup.js"></script>
</head>
<body class="sticky-header">
	<!-- 页面内容导航开始-->
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">用户管理->人员角色</li>
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
								<button id="editable-sample_new" class="btn btn-primary"
									data-toggle="modal" data-target="#myModal1"
									style="display:none; margin-bottom: 10px;"  showname="EM_EMPACCOUNT_ROLE_BTN_ADD" onclick="editAccount()">
									新增 <i class="fa fa-plus"></i>
								</button>
								<div class="modal fade" id="myModal1" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="myModalLabel">编辑角色</h4>
											</div>
											<div class="modal-body">
												<!-- 开始 -->
												<form class="form-horizontal" id="dataForm">
												
                                                    <input type="hidden" id="type" name="type"/>
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">角色名</label>
														<div class="col-sm-10">
															<input class="form-control" name="id" id="id" required="true" />
														</div>
													</div>
													<div class="form-group">
														<label for="inputEmail3" class="col-sm-2 control-label">角色名称</label>
														<div class="col-sm-10">
															<input class="form-control" name="name" id="name" required="true"/>
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
						<table id="actGroupId" style="width: 100%"></table>

					
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!--主题内容结束-->
</body>
</html>