<%@page contentType="text/html;charset=UTF-8"%>
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
<script src="./pageJs/operation/opermg/specHot.js"></script>
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
.selectclose.open .open>.dropdown-menu {
    display: none;
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">首页显示管理</a></li>
			<li class="active">热门规格</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
					 <label class="col-lg-3"><span>品名：</span>
					 <div style="margin-left:-10px;" class="selectclose">
					 	<select  name="firstLevelPk" class="selectpicker bla bla bli"   data-live-search="true">
								   <option value="">--请选择--</option>
									<c:forEach items="${productList }" var="m">
									<option value="${m.pk}">${m.name }</option>
									</c:forEach>  
							 </select> 
					 </div>
					 </label>
					 <label class="col-lg-3"><span>品种：</span>
					 <div style="margin-left:-10px;" class="selectclose">
					  	 <select  name="secondLevelPk"  class="selectpicker bla bla bli">
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							</select>  
					 </div>
					  </label>
					 <label class="col-lg-3 col-lg-2"><span>开始时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeBegin"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>	
					<label class="col-lg-3 col-lg-2"><span>是否上线：</span> <select class="form-controlgoods input-append " name="status" style="width: 110px;"><option value="">全部</option><option value="1">上线</option><option value="2">下线</option></select></label>
                     <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      	<button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
                    <a href="#" style="text-decoration:none;" >
                        <button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SPEC_BTN_ADD" class="btn btn-primary" data-toggle="modal" data-target="#specHotModel" onclick="javascript:add(); ">添加</button>
                   </a>
						<input type="hidden" id="block" name="block" value="${block}"/>
                </header>
                	<div class="panel-body">
					<section class="panel">
                      
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
                                </div>
                                  <table id="specHotId" style="width: 100%"></table>
                            </div>
                        </div>
				</section>
			</div>
			</section>
		</div>
	</div>
	 <!-- 推荐广告model -->
       <div class="modal fade" id="specHotModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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

							<div class="form-group tbsel">
								<label for="inputEmail3" class="col-sm-2 control-label">品名:</label>
								<div class="col-sm-6" style="margin-right: 74px;margin-left:-10px;">
									<select id=firstLevelPk name="firstLevelPk"  class="selectpicker bla bla bli" data-live-search="true" required="true">
										<option value="">--请选择--</option>
										<c:forEach items="${productList }" var="m">
											<option value="${m.pk}">${m.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="form-group tbsel">
								<label for="inputEmail3" class="col-sm-2 control-label">品种:</label>
								<div class="col-sm-6" style="margin-right: 74px;margin-left:-10px;">
							<select id="secondLevelPk" name="secondLevelPk"  class="selectpicker bla bla bli" data-live-search="true" required="true">
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							</select>  
								</div>
							</div>
							<div class="form-group tbchkbox" >
								<label for="inputEmail3" class="col-sm-2 control-label">规格:</label>
								<div class="col-sm-8">
									<input class="form-control" type="text" name="fourthLevelName" value="" required="true"/>
								</div>
							</div>
								<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">跳转地址:</label>
								<div class="col-sm-8">
									<input class="form-control" name="linkUrl" value="" required="true">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">排序:</label>
								<div class="col-sm-8">
									<input class="form-control" name="sort" value="" required="true">
								</div>
							</div>
							<input type="hidden" name="status" id="status" value="1"/>
							<input type="hidden" name="pk" id="pk"/>
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
		<!-- 新建分类model 结束-->
    <script>
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