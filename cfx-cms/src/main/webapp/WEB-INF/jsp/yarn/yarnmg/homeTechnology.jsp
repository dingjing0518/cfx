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
<script src="./pageJs/yarn/yarnmg/homeTechnology.js"></script>
<style>
.checkbox-inline input[type="checkbox"] {
	margin-top: 5px;
}

</style>
<!-- 页面内容导航开始-->
</head>
<!-- 添加分类就在多页签里边，不需要单独的分类目录，不然太多东西了，个人建议 -->
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">首页显示管理</a></li>
			<li class="active">工艺类型</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading"
					style="margin-bottom:10px;"> 
					<label class="col-lg-3 col-lg-2"><span>添加时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeStart"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
					<label class="col-lg-3 col-lg-2"><span>&nbsp;原料：</span>
						<select name="bannerrawmaterialPk" class="selectpicker bla bla bli">
							
							<option value="">--请选择--</option>
							<c:forEach  var ="c" items="${noIsVisSxMaterialList}" >
							<option value="${c.pk }">${c.rawMaterialName }</option>
							</c:forEach>
						</select> 
					</label>
					
					<label class="col-lg-3 col-lg-2"><span>&nbsp;工艺：</span>
						<select name="technologyPk" class="selectpicker bla bla bli">
							
							<option value="">--请选择--</option>
							<c:forEach  var ="c" items="${noIsVisSxTechnologyList }" >
							<option value="${c.pk }">${c.name }</option>
							</c:forEach>
						</select> 
					</label>
					
					
					<label class="col-lg-3 col-lg-2"><span>&nbsp;是否启用：</span>
						<select name="isVisable" class="selectpicker bla bla bli">
							<option value="">--请选择--</option>
							<option value="1">启用</option>
							<option value="2">禁用</option>
						</select> 
					</label>
		  <button class="btn btn-primary" id="btn" onclick="Search();"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="Clean();"> 清除</button>
					<a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="YARN_OPER_SHOW_TRCH_BTN_ADD" class="btn btn-primary"
						data-toggle="modal" 
						onclick="javascript:editHomeTechnologyBanner('','','',''); ">添加工艺</button>
				</a> </header>
				<div class="panel-body">
					<section class="panel">
					<div class="panel-body">
						<div class="tab-content">
							<div class="tab-pane active" id="all">
							</div>
							<div class="tab-pane" id="verify"></div>
							<table id="homePageTechTableBannerId" style="width: 100%"></table>
						</div>
					</div>
					</section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!-- 排序-->
	<div class="modal fade" id="editSortId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">排序编辑</h4>
				</div>
				<div class="modal-body">
				<form   id="reasonForm">
					<input type="hidden" class="form-control" id="editSortPk" name="editSortPk"/>
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">排序</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="sortButton" name="sortButton" required="true"/>
						</div>
					</div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="qx" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="editSort();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 新建分类model -->
	<div class="modal fade" id="editHomeTechologyId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑首页工艺</h4>
				</div>
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk" name="pk" />
						<input type="hidden" id="updateType" name="updateType" />
					<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">原料</label>
							<div class="col-sm-10">
								<select class="form-control" name="bannerrawmaterialPk" id="bannerrawmaterialPk" required="true">
									<option value="">--请选择--</option>
									<c:forEach  var ="c" items="${noIsVisSxMaterialList }" >
										<option value="${c.pk }">${c.rawMaterialName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">工艺</label>
							<div class="col-sm-10">
								<select class="form-control" name="technologyPk" id="technologyPk" required="true">
									<option value="">--请选择--</option>
									<c:forEach  var ="c" items="${noIsVisSxTechnologyList }" >
										<option value="${c.pk }">${c.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">排序</label>
							<div class="col-sm-4">
								<input class="form-control" name="sort" id="sort" required="true">
							</div>
						</div>
						<div class="form-group col-ms-12"
								style="height: 30px; display: block;">
								<label for="inputEmail3" class="col-sm-2 control-label"
									style="text-align: right;padding-top:2px;">是否禁用</label>
								<div class="col-sm-5">
									<label>启用<input
										name="isVisable" type="radio" value="1" checked='checked' style="margin-left:3px;"/>
									</label> <label>禁用<input name="isVisable" type="radio" value="2" style="margin-left:3px;"/>
									</label>
								</div>	
						</div>
					</form>
					<!-- 结束 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 新建分类model 结束-->
	<script>
		 $(".form_datetime1").datetimepicker({
			  minView: "month",
		      format: "yyyy-mm-dd",
		      autoclose: true,
		      todayBtn: true,
		      language:'zh-CN',
		      pickerPosition:"top-left"
		    });
	</script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>