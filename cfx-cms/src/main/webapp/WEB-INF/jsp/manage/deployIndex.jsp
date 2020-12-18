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
<script src="./pageJs/manage/deployIndex.js"></script>
</head>
<body class="sticky-header">
	<!-- 页面内容导航开始-->
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">管理中心</a></li>
			<li class="active">流程部署管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
				<label class="col-lg-3 col-lg-2"><span>&nbsp;流程名称：</span> 
					         <input type="text"  name="name" class="form-controlgoods" aria-controls="dynamic-table" >
					     </label>
					     <button id="editable-sample_new" class="btn btn-primary" onclick="search()"
									style="margin-bottom: 10px;">
									查询
								</button>
					     <button id="editable-sample_new"  style="margin-bottom:10px;" style="display:none;" showname="MG_FLOW_DEPLOY_BTN_ADD" class="btn btn-primary" 
									data-toggle="modal" data-target="#myModal1" >
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
												<h4 class="modal-title" id="myModalLabel">添加流程部署</h4>
											</div>
											<div class="modal-body">
												<!-- 开始 -->
												<form class="form-horizontal" id="dataForm">
					
                                               
                                               <input id="file" class="file" name="deployFile" type="file" 
									data-min-file-count="1" >
													
												</form>
												<!-- 结束 -->
											</div>
											
										</div>
									</div>
								</div>
								<!-- 模态框结束 -->
							</div>
						</div>
						<div class="space15"></div>
						<table id="deploy" style="width: 100%"></table>

					
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!--主题内容结束-->
	
		<script>
	$("#file").fileinput({
			language : 'zh',
			uploadUrl : getRootPath() + "/addDeploy.do", // you must set a valid URL here else you will get an error  
			allowedFileExtensions : [ 'zip'],
			maxFileCount : 1, //同时最多上传6个文件  
			 showUpload: true ,//是否显示上传按钮  
	         showRemove: true,//是否显示删除按钮  
	         showCaption: false,
	         fileActionSettings:{
	        	 showUpload: false,
	        	 showRemove: true
	         },
			//这是文件名替换  
			slugCallback : function(filename) {
				return filename.replace('(', '_').replace(']', '_');
			}
		});
	 	//这是提交完成后的回调函数    
		$("#file").on("fileuploaded", function(event, data, previewId, index) {
			$("#quxiao").click();
			$('#deploy').bootstrapTable('refresh');
		});
		
		 $('#file').on('filesuccessremove', function(event, id) {
			
		
		});

	</script>
</body>
</html>