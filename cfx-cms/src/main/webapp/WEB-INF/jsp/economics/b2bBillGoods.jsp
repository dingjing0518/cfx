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
<style>
	.seclectwidth .bootstrap-select > .dropdown-toggle{
		float: left;
	    width: 461px;
	}
	.imgC{
		width: 80px;
		height: 80px;
		background:url(./style/images/bgbg.png) no-repeat;
        background-size: 100%;
        display:block;
	}
</style>
<!-- 页面内容导航开始-->
<script src="./pageJs/economics/b2bBillGoods.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">票据产品管理</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<label class="col-lg-2 col-lg-3"><span>产品名称：</span> <input class="form-controlgoods input-append " type="text" name="name"   value="" /></label>
						<label class="col-lg-2 col-lg-3"><span>英文简称：</span> <input class="form-controlgoods input-append " type="text" name="shotName"   value="" /></label>
						<label class="col-lg-2 col-lg-3"><span>收款银行：</span> <input class="form-controlgoods input-append " type="text" name="bankName"   value="" /></label>
		                <label class="col-lg-2 col-lg-3"><span>类型：</span>
						 <select class="selectpicker bla bla bli" name="type" style="width: 200px;" >
							<option value="">--请选择--</option>
							 <option value="0">无限制</option>
							<option value="1">商承票</option>
						  	<option value="2">银承票</option>
						 </select>
					 	</label>	
					 	<button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
			      <a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="EM_BILLGOODS_MG_BTN_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal"
						onclick="javascript:editGoods(); ">新增产品</button>
				</a> 
             	</header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="billGoodsId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
<!-- ===================================== -->
<!-- 新建分类model -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑产品</h4>
				</div>
				
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk"/>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">产品名称</label>
							<div class="col-sm-10">
								<input class="form-control" type="text" name="name" id="name" required="true"/>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">类型</label>
							<div class="col-sm-10 seclectwidth">
								<select name="type" id="type" class="form-control">
									<option value="">--请选择--</option>
									<option value="0">无限制</option>
									<option value="1">商承票</option>
									<option value="2">银承票</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">收款银行</label>
							<div class="col-sm-10">
								<select name="bankPk" id="bankPk" class="form-control">
									<option value="">--请选择--</option>
									<c:forEach items="${economicsBankList }" var="m">
										<option value="${m.pk}">${m.bankName }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">网关地址</label>
							<div class="col-sm-10">
								<input class="form-control" type="text" name="gateway" id="gateway" required="true"/>
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
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
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
	 
	 </script>
</body>
</html>