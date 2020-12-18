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
<script src="./pageJs/operation/article.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">资讯中心</a></li>
			<li class="active">文章列表</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
				<label class="col-lg-2 col-lg-3"><span>是否启用</span> <select class="form-controlgoods input-append " name="isVisable"><option value="">全部</option><option value="1">启用</option><option value="2">禁用</option></select></label>
				<label class="col-lg-2 col-lg-3"><span>是否置顶</span> <select class="form-controlgoods input-append " name="top"><option value="">全部</option><option value="1">是</option><option value="2">否</option></select></label>
				<!-- <label class="col-lg-2 col-lg-3"><span>是否发布</span> <select class="form-controlgoods input-append " name="status"><option value="">全部</option><option value="2">已发布</option><option value="1">未发布</option></select></label> -->
				<label class="col-lg-2 col-lg-3"><span>所属系统</span> 
					<select class="form-controlgoods input-append " name="parentId">
						<option value="">全部</option>
						<option value="1">电商系统</option>
						<option value="2">物流系统</option>
						<option value="3">金融系统</option>
					</select>
				</label>
				<label class="col-lg-2 col-lg-3"><span>分类名称</span> 
					<select class="form-controlgoods input-append " name="categoryPk" id = "categoryPk">
						<option value="">全部</option>
						<c:forEach items="${categorysList}" var="m">
						<option value="${m.pk}">${m.name}</option>
						</c:forEach>
					</select>
				</label>
				 <label class="col-lg-2 col-lg-3"><span>关键词</span> <input class="form-controlgoods input-append " type="text" name="keyword"   value="" onkeydown="entersearch()"/></label>
				 <label class="col-lg-2 col-lg-3"><span>标题</span> <input class="form-controlgoods input-append " type="text" name="title"   value=""/></label>
				 <label class="col-lg-2 col-lg-3"><span>是否推送</span>
						<select class="form-controlgoods input-append " name="isPush">
							<option value="">全部</option>
							<option value="1">未推送</option>
							<option value="2">已推送</option>
						</select>
					</label>
				 <input type="hidden" id="status" name="status"/>
				 <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			     <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
					<a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_ADD" class="btn btn-primary" data-toggle="modal" data-target="#myModal1" onclick="javascript:editNews('');">新增文章</button>
				    </a>
				    <a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_EXPORT" class="btn btn-primary" data-toggle="modal" data-target="#myModal1" onclick="javascript:exportNews();">导出</button>
				    </a>
		             </header>
				<div class="panel-body">
					<section class="panel">
					 <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findNews(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findNews(1);">未发布</a>
                                </li>
                            <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findNews(2);">已发布</a>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
			                    <table id="NewsId" style="width: 100%"></table>
                                </div>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
    <script>
	    $(".form_datetime").datetimepicker({
	      format: "yyyy-mm-dd hh:ii",
	      autoclose: true,
	      todayBtn: true,
	      language:'zh-CN',
	      pickerPosition:"bottom-right"
	    });
	 </script>
<script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	searchTabs();  
       }  
} 
</script>
</body>
</html>