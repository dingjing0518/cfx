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
<script src="./pageJs/operation/opermg/banner.js"></script>
<style>
.checkbox-inline input[type="checkbox"] {
	margin-top: 5px;
}
.selectclose.open .open>.dropdown-menu {
    display: none;
}
</style>
<!-- 页面内容导航开始-->
</head>
<!-- 添加分类就在多页签里边，不需要单独的分类目录，不然太多东西了，个人建议 -->
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
		<li>运营管理</li>
			<li>广告管理</li>
			<li class="active">广告位管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading"
					style="margin-bottom:10px;"> 
					<label class="col-lg-2 col-lg-3"><span>上线时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="onlineStime"   value="" readonly /></label>
			           <label class="col-lg-2 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="onlineEtime"   value="" readonly /></label>	     	
		             	
				<label class="col-lg-2 col-lg-3"><span>结束时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="endStime"   value="" readonly /></label>
			           <label class="col-lg-2 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="endEtime"   value="" readonly /></label>	 
					
					<label
					class="col-lg-2 col-lg-3 selectclose"><span>&nbsp;是否上线：</span>
					<select name="isVisable" class="selectpicker bla bla bli">
						<option value="">--请选择--</option>
						<option value="1">上线</option>
						<option value="2">下线</option>

				</select> </label>
					
				  <label class="col-lg-2 col-lg-3"><span>&nbsp;名称：</span> 
					         <input type="text"  name="name" class="form-controlgoods" aria-controls="dynamic-table">
					     </label>
					     
					    <label
					class="col-lg-2 col-lg-3 selectclose"><span>&nbsp;对应位置：</span>
					<select name="position" class="selectpicker bla bla bli"  >
					<option value="">--请选择--</option>
						<option value="1">首页轮播图</option>
						<option value="2">首页轮播图下方</option>
						<option value="3">资讯轮播图</option>
						<option value="4">在线竞拍</option>
						<option value="5">顶部活动广告</option>
						<option value="6">中部活动广告</option>
						<option value="7">开机启动图</option>

				</select> </label>
				<label
					class="col-lg-2 col-lg-3 selectclose"><span>&nbsp;放置平台：</span>
					<select name="platform" class="selectpicker bla bla bli"  >
					<option value="">--请选择--</option>
						<option value="1">pc</option>
						<option value="2">wap</option>
						<option value="3">app</option>
				</select> </label>
				<input type="hidden" id="types" name = "type" />
		  <button class="btn btn-primary" id="btn" onclick="Search();"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="Clean();"> 清除</button>
					<a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="OPER_MG_AD_ADSENSE_BTN_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal3"
						onclick="javascript:editBanner('',''); ">添加广告</button>
				</a> </header>
				<div class="panel-body">
					<section class="panel"> <header
						class="panel-heading custom-tab ">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#all" data-toggle="tab"
							onclick="findBanner(1);">首页</a></li>
						<!-- <li class=""><a href="#verify" data-toggle="tab"
							onclick="findBanner(2);">推荐广告位</a></li> -->
							<li class=""><a href="#verify" data-toggle="tab"
							onclick="findBanner(5);">竞拍</a></li>
							<li class=""><a href="#verify" data-toggle="tab"
							onclick="findBanner(3);">资讯</a></li>
							<!-- <li class=""><a href="#verify" data-toggle="tab"
							onclick="findBanner(4);">wap端首页</a></li> -->
							<li class=""><a href="#verify" data-toggle="tab"
							onclick="findBanner(6);">活动</a></li>
					</ul>
					</header>
					<div class="panel-body">
						<div class="tab-content">
							<div class="tab-pane active" id="all">
							</div>
							<div class="tab-pane" id="verify"></div>
							<table id="bannerId" style="width: 100%"></table>
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
					<h4 class="modal-title" id="myModalLabel">添加广告</h4>
				</div>
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk" name="pk" />
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">名称</label>
							<div class="col-sm-10">
								<input class="form-control" name="name" id="name" />
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">放置栏目</label>
							<div class="col-sm-10">
								<select class="form-control" name="type" id="type" required="true">
									
									<option value="">--请选择--</option>
									<option value="1">首页</option>
									<!-- <option value="2">推荐广告位</option> -->
									<option value="5">竞拍</option>
									<option value="3">资讯</option>
									<!-- <option value="4">wap端首页</option> -->
									<option value="6">活动</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">放置平台</label>
							<div class="col-sm-10">
								<select class="form-control" name="platform" id="platform" required="true">
									<option value="">--请选择--</option>
									<option value="1">pc</option>
									<option value="2">wap</option>
									<option value="3">app</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">对应位置</label>
							<div class="col-sm-10">
								<select class="form-control" name="position" id="position" required="true">
									<option value="">--请选择--</option>
									<option value="1">首页轮播图</option>
									<option value="2">首页轮播图下方</option>
									<option value="3">资讯轮播图</option>
									<option value="4">在线竞拍</option>
									<option value="5">顶部活动广告</option>
									<option value="6">中部活动广告</option>
									<option value="7">开机启动图</option>
								</select>
							</div>
						</div>
					
						<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">上线时间</label>
								<div class="col-sm-10">
									<input class="form-control input-append date form-dateday" type="text" id="onlineTime" name="onlineTime" value="" readonly>
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">结束时间</label>
								<div class="col-sm-10">
									<input class="form-control input-append date form-dateday" type="text"   id="endTime" name="endTime"   value="" readonly>
								</div>
							</div>
							
							<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">跳转地址</label>
							<div class="col-sm-10">
								<input class="form-control" name="linkUrl" id="linkUrl" />
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">上传图片</label>
							<div class="col-sm-10">
							<img  id="PURL" style='width:410px;height:80px; ' />
							<input id="PURL_flag"    type="hidden" name = "PURL_flag"/> 
							<input id="url" type="hidden" name = "url" /> 
								<input id="file" class="file" name="file" type="file"
									data-min-file-count="1">
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">排序</label>
							<div class="col-sm-4">
								<input class="form-control" name="sort" id="sort">
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">广告描述</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="details" id="details"
									style="height: 60px;"></textarea>
							</div>
						</div>
						<!-- <div class="form-group col-ms-12"
								style="height: 30px; display: block;">
								<label for="inputEmail3" class="col-sm-2 control-label"
									style="text-align: left;">是否禁用</label>
								<div class="col-sm-5">
									<label>启用<input
										name="isVisable" type="radio" value="1" checked='checked' style="margin-left:3px;"/>
									</label> <label>禁用<input name="isVisable" type="radio" value="2" style="margin-left:3px;"/>
									</label>
								</div>	
							</div> -->
								<div class="form-group col-ms-12"
								style="height: 30px; display: none;">
								<input type ="hidden" name = "isVisable" id="isVisable"  value="1"/>
							</div>
					</form>
					<!-- 结束 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="submitId" onclick="submit();">保存</button>
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
			maxFileCount : 3, //同时最多上传3个文件  
			 showUpload: true ,//是否显示上传按钮  
	         showRemove: false,//是否显示删除按钮  
	         showCaption: false,
			//allowedFileTypes: ['image', 'video', 'flash'],  这是允许的文件类型 跟上面的后缀名还不是一回事  
			//这是文件名替换  
			slugCallback : function(filename) {
				return filename.replace('(', '_').replace(']', '_');
			}
		});
	 	//这是提交完成后的回调函数    
		$("#file").on("fileuploaded", function(event, data, previewId, index) {
			$("#url").val(data.response.data);
			//alert("上传完成");
		 
		});
		$('#file').on('filesuccessremove', function(event, id) {
			if($("#PURL_flag").val()==0){
				$("#url").val('');
			}else{
				$("#url").val($('#PURL')[0].src);
			}
		
		});
		 $(".form_datetime1").datetimepicker({
			  minView: "month",
		      format: "yyyy-mm-dd",
		      autoclose: true,
		      todayBtn: true,
		      language:'zh-CN',
		      pickerPosition:"top-left"
		    });
		 
		 $(".form_datetime").datetimepicker({
		    	format: "yyyy-mm-dd hh:ii",
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