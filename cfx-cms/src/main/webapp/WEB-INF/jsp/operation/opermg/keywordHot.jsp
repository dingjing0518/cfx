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
<script src="./pageJs/operation/opermg/keywordHot.js"></script>
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
			<li>运营管理</li>
			<li class="active">热门搜索管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading"
					style="margin-bottom:10px;"> 
				  <label class="col-lg-2 col-lg-3"><span>&nbsp;关键词：</span> 
					         <input type="text"  name="name" class="form-controlgoods" aria-controls="dynamic-table">
					     </label>
					<label class="col-lg-2 col-lg-3"><span>&nbsp;是否推荐：</span>
						<select name="status" class="selectpicker bla bla bli">
							<option value="">--请选择--</option>
							<option value="1">是</option>
							<option value="2">否</option>
						</select> 
					</label>     
					<label class="col-lg-2 col-lg-3"><span>添加时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeBegin"   value="" readonly /></label>
			           <label class="col-lg-2 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>	     	
		  <button class="btn btn-primary" id="btn" onclick="Search();"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="Clean();"> 清除</button>
					<a href="#" style="text-decoration: none;">
					<button style="display:none;" showname="OPER_MG_HOMESHOW_SEARCH_BTN_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal3"
						onclick="javascript:addKeyWordHot(); ">新增</button>
				</a>
				<input type="hidden" id="block" name="block" value="${block}"/>
				</header>
				<div class="panel-body">
					<section class="panel">

					<div class="panel-body">
						<div class="tab-content">
							<div class="tab-pane active" id="all">
							</div>
							<div class="tab-pane" id="verify"></div>
							<table id="keywordHotId" style="width: 100%"></table>
						</div>
					</div>
					</section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!-- 新建分类model -->
	<div class="modal fade" id="myModal3" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增</h4>
				</div>
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk" name="pk" />
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">关键词</label>
							<div class="col-sm-10">
								<input class="form-control" name="name" required="true"/>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">跳转地址</label>
							<div class="col-sm-10">
								<input class="form-control" name="linkUrl" id="linkUrl" required="true"/>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">排序</label>
							<div class="col-sm-4">
								<input class="form-control" name="sort" id="sort">
							</div>
						</div>
								<div class="form-group col-ms-12"
								style="height: 30px; display: none;">
								<input type ="hidden" name = "status" id="status"  value="2"/>
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
		 $(".form_datetime").datetimepicker({
		    	format: "yyyy-mm-dd hh:ii",
			    autoclose: true,
			    todayBtn: true,
			    language:'zh-CN',
			    pickerPosition:"bottom-right"
		    });
	</script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>