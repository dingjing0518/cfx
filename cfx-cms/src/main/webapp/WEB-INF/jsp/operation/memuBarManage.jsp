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
<script src="./pageJs/operation/memuBarManage.js"></script>
<!-- 页面内容导航开始-->
<style>
.bootstrap-select{
margin-right: 74px;
}
#dataForm .bootstrap-select > .dropdown-toggle{
  width:180px;
}
.nav-tabs>li {
    height: 60px;
    line-height: 60px;
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营管理</a></li>
			<li class="active">菜单栏管理</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
					 <label class="col-lg-4 col-lg-2"><span>开始时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeStart"   value="" readonly /></label>
			           <label class="col-lg-4 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
					<label class="col-lg-4 col-lg-2"><span>名称：</span><input class="form-controlgoods" name="name"/></label>
                     <label class="col-lg-4 col-lg-2"><span>来源：</span> <select class="form-controlgoods input-append " name="source" style="width: 110px;">
						 <option value="">请选择</option>
						 <option value="1">PC</option>
						 <option value="2">WAP</option>
						 <option value="3">APP</option>
					 </select></label>
                     <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      	<button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
                    <a href="#" style="text-decoration:none;" >
                        <button id="editable-sample_new" style="display:none;" showname="OPER_RF_MEMUMG_BTN_ADD" class="btn btn-primary" data-toggle="modal" data-target="#myModal2" onclick="javascript:edit('',''); ">添加</button>
                   </a>
                </header>
                    <input type="hidden" id="type" name="type" value="1"/>
                	<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findSource('');">全部</a>
                                </li>
								<%--<li class="">--%>
									<%--<a href="#off-shelve" data-toggle="tab" onclick="findSource(1);">PC</a>--%>
								<%--</li>--%>
								<%--<li class="">--%>
									<%--<a href="#off-shelve" data-toggle="tab" onclick="findSource(2);">WEB</a>--%>
								<%--</li>--%>
								<%--<li class="">--%>
									<%--<a href="#off-shelve" data-toggle="tab" onclick="findSource(3);">APP</a>--%>
								<%--</li>--%>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
                                </div>
                                  <table id="memuBarManageId" style="width: 100%"></table>
                            </div>
                        </div>
				</section>
			</div>
		</div>
	</div>
	 <!-- 推荐广告model -->
       <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">编辑菜单栏</h4>
					</div>
					<div class="modal-body">
						<!-- 开始 -->
						<form class="form-horizontal" id="dataForm">
							<input type="hidden" id="pk" name="pk" />
							<div class="form-group tbsel">
								<label for="inputEmail3" class="col-sm-2 control-label">来源</label>
								<div class="col-sm-8">
									<select class="form-control" name="source" id="source" required="true">
									    <option value="">--请选择--</option>
									    <option value="1">PC</option>
									    <option value="2">WAP</option>
										<option value="3">APP</option>
									</select>
								</div>
							</div>


							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">名称</label>
								<div class="col-sm-8">
									<input class="form-control" type="text" id="name" id="name"   value="" required="true" >
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">跳转地址</label>
								<div class="col-sm-8">
									<input class="form-control" name="url" id="url" value="">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">上传图片</label>
								<div class="col-sm-10">
							        <input id="tupian" type="hidden" /> 
                                  	<img  id="PURL" style='width:auto;height:80px;' />
							        <input id="PURL_flag"    type="hidden"/>
								<input id="file" class="file" name="file" type="file"
									data-min-file-count="1">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">排序</label>
								<div class="col-sm-8">
									<input class="form-control" name="sort" id="sort" value="" required="true">
								</div>
							</div>
						</form>
						<!-- 结束 -->
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" id="quxiao"
							data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" id="submitId"
							onclick="submit();">保存</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 新建分类model 结束-->
    <script>
    $("#file").fileinput({
		language : 'zh',
		uploadUrl : getRootPath() + "/uploadPhoto.do", // you must set a valid URL here else you will get an error  
		allowedFileExtensions : [ 'xls', 'jpg', 'png', 'gif' ],
		 maxFileCount : 1, //同时最多上传1个文件  
		 showUpload: true ,//是否显示上传按钮  
         showRemove: false,//是否显示删除按钮  
         showCaption: false,
		//这是文件名替换  
		slugCallback : function(filename) {
			return filename.replace('(', '_').replace(']', '_');
		}
	});
 	//这是提交完成后的回调函数    
	$("#file").on("fileuploaded", function(event, data, previewId, index) {
			$("#tupian").val(data.response.data);
			//alert("上传完成");
		 
		});
		$('#file').on('filesuccessremove', function(event, id) {
			if($("#PURL_flag").val()==0){
				$("#tupian").val('');
			}else{
				$("#tupian").val($('#PURL')[0].src);
			}
		
		});
	    $(".form_datetime").datetimepicker({
		      minView: "month",
		      format: "yyyy-mm-dd",
		      autoclose: true,
		      todayBtn: true,
		      language:'zh-CN',
		      pickerPosition:"bottom-right"
		    });
	 </script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>