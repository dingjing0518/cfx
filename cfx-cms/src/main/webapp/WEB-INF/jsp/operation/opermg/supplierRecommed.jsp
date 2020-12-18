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
<script src="./pageJs/operation/opermg/supplierRecommed.js"></script>
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
@media (max-width:1600px){
.bootstrap-select {
    margin-right: 30px;
}
#myModal2 .bootstrap-select{
	margin-right: 74px;
}
}
@media (max-width:1366px){
.bootstrap-select {
    margin-right: 0px;
}
#myModal2 .bootstrap-select{
	margin-right: 74px;
}
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">广告管理</a></li>
			<li class="active">供应商推荐</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<label class="col-lg-2 col-lg-2"><span>放置终端：</span>
							<select class="selectpicker bla bla bli" name="platform" style="width: 110px;">
								<option value="">请选择</option>
								<option value="1">PC端</option>
								<option value="2">WAP端</option>
								<option value="3">APP端</option>
							</select>
						</label>
						<label class="col-lg-4 col-lg-2"><span>品牌名称：</span>
							<select class="selectpicker bla bla bli" data-live-search="true" name="brandPk" style="width: 110px;">
								<option value="">请选择</option>
								<c:forEach items="${brandList }" var="m">
									<option value="${m.brandPk}">${m.brandName }</option>
								</c:forEach>
							</select>
						</label>
					 <label class="col-lg-4 col-lg-2"><span>添加时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeBegin"   value="" readonly /></label>
			           <label class="col-lg-4 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
                     <label class="col-lg-4 col-lg-2"><span>放置位置：</span>
						 <select class="selectpicker bla bla bli" name="position" style="width: 110px;">
							 <option value="">请选择</option>
							 <option value="1">次导航</option>
							 <option value="2">品牌专区</option>
							 <option value="3">优质供应商</option>
						 </select>
					 </label>

						<label class="col-lg-4 col-lg-2"><span>所属板块：</span>
							<select class="selectpicker bla bla bli" name="block" style="width: 110px;">
								<option value="">请选择</option>
								<option value="cf">化纤</option>
								<option value="sx">纱线</option>
							</select>
						</label>
						<label class="col-lg-4 col-lg-2"><span>关联店铺：</span>
							<select class="selectpicker bla bla bli" data-live-search="true" name="storePk" style="width: 110px;">
								<option value="">请选择</option>
								<c:forEach items="${disGoodsBrandList }" var="m">
									<option value="${m.storePk}">${m.storeName }</option>
								</c:forEach>
							</select>
						</label>
						<label class="col-lg-4 col-lg-2"><span>所属区域：</span>
							<select class="selectpicker bla bla bli" name="region" style="width: 110px;">
								<option value="">--请选择--</option>
								<option value="1">太仓</option>
								<option value="2">常熟</option>
								<option value="3">张家港</option>
								<option value="4">萧山</option>
								<option value="5">绍兴</option>
								<option value="6">桐乡</option>
								<option value="7">吴江</option>
								<option value="8">其他</option>
							</select>
						</label>
                    <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      	<button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
                    <a href="#" style="text-decoration:none;" >
                        <button id="editable-sample_new" style="display:none;" showname="OPER_MG_AD_RECOMMEND_BTN_ADD" class="btn btn-primary" data-toggle="modal" data-target="#myModal2" onclick="javascript:editSupplierRE('',''); ">添加</button>
                   </a>
						<input type="hidden" name="isOnline" id="isOnline"/>

                </header>

                	<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findSuppRE('');">全部</a>
                                </li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="findSuppRE('1');">上线</a>
								</li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="findSuppRE('2');">下线</a>
								</li>

                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
                                </div>
                                  <table id="supplierRecommedId" style="width: 100%"></table>
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
						<h4 class="modal-title" id="myModalLabel">推荐供应商</h4>
					</div>
					<div class="modal-body">
						<!-- 开始 -->
						<form class="form-horizontal" id="dataForm">
							<input type="hidden" id="pk" name="pk" />
							<div class="form-group tbsel">
								<label for="inputEmail3" class="col-sm-2 control-label">放置终端</label>
								<div class="col-sm-8" >
									<label style="margin-right: 15px;"><input name="platform" type="checkbox" value="1" style="margin-left:3px;float: left;margin-right: 3px;" />PC端</label>
									<label><input name="platform"  type="checkbox" value="2"  style="margin-left:3px;float: left;margin-right: 3px;" />WAP端</label>
									<label><input name="platform"  type="checkbox" value="3"  style="margin-left:3px;float: left;margin-right: 3px;" />APP端</label>
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">品牌名称</label>
								<div class="col-sm-6" style="margin-right: 74px;margin-left:-10px;">
									<select id="brandName" name="brandName" class="selectpicker bla bla bli" data-live-search="true" onchange='changeGoodsBrand(this.value)'>
										<option value="">--请选择--</option>
										<c:forEach items="${brandList }" var="m">
											<option value="${m.brandPk}">${m.brandName }</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="form-group tbsel">
								<label for="inputEmail3" class="col-sm-2 control-label">放置位置</label>
								<div class="col-sm-8" >
									<label style="margin-right: 15px;"><input name="position" type="checkbox" value="1" style="margin-left:3px;float: left;margin-right: 3px;" />次导航</label>
									<label><input name="position" type="checkbox" value="2"  style="margin-left:3px;float: left;margin-right: 3px;" />品牌专区</label>
									<label><input name="position" type="checkbox" value="3"  style="margin-left:3px;float: left;margin-right: 3px;" />优质供应商</label>
								</div>
							</div>

							<div class="form-group tbsel">
								<label for="inputEmail3" class="col-sm-2 control-label">所属板块</label>
								<div class="col-sm-8" >
									<label style="margin-right: 15px;"><input name="block" type="checkbox" value="cf" style="margin-left:3px;float: left;margin-right: 3px;" />化纤</label>
									<label><input name="block" type="checkbox" value="sx"  style="margin-left:3px;float: left;margin-right: 3px;" />纱线</label>
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">关联店铺</label>
								<div class="col-sm-6" style="margin-right: 74px;margin-left:-10px;">
									<select id="storeName" name="storeName" class="selectpicker bla bla bli"   data-live-search="true">
										<option value="">--请选择--</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">跳转地址</label>
								<div class="col-sm-8">
									<input class="form-control" name="linkUrl" id="linkUrl" value="">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">所在区域</label>
								<div class="col-sm-6" style="margin-right: 74px;margin-left:-10px;">
									<select id="region" name="region" class="selectpicker bla bla bli">
										<option value="">--请选择--</option>
										<option value="1">太仓</option>
										<option value="2">常熟</option>
										<option value="3">张家港</option>
										<option value="4">萧山</option>
										<option value="5">绍兴</option>
										<option value="6">桐乡</option>
										<option value="7">吴江</option>
										<option value="8">其他</option>
									</select>
								</div>
							</div>
							<div class="form-group" id="imgDivId">
								<label for="inputEmail3" class="col-sm-2 control-label">上传图片</label>
								<div class="col-sm-10">
									<!-- <img src=""  id="PURL" style='width:auto;height:80px; 'alt="" /> -->
							        <input id="tupian" type="hidden" /> 
                                  	<img  id="PURL" style='width:auto;height:80px; ' />
							<input id="PURL_flag"    type="hidden"/>
								<input id="file" class="file" name="file" type="file"
									data-min-file-count="1">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">是否推荐</label>
								<div class="col-sm-8">
									<label>是<input name="isRecommed" type="radio" value="1" style="margin-left:3px;" /></label>
									<label>否<input name="isRecommed" type="radio" value="2" checked='checked' style="margin-left:3px;"/></label>
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">排序</label>
								<div class="col-sm-8">
									<input class="form-control" name="sort" id="sort" value="">
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
         //maxImageWidth:200,
         //maxImageHeight:61,
         //maxFileSize:500,
		//allowedFileTypes: ['image', 'video', 'flash'],  这是允许的文件类型 跟上面的后缀名还不是一回事
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
	   /*  $('#file-04').fileinput({
	        language: 'zh', //设置语言
	    }); */
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