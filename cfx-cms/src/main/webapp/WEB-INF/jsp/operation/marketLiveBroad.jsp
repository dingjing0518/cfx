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
<script src="./pageJs/operation/marketLiveBroad.js"></script>
<style>
#dataForm .bootstrap-select > .dropdown-toggle{
  width:180px;
}
.slect-form .bootstrap-select,.slect-form .bootstrap-select button{
	float:none!important;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">新闻咨询</a></li>
			<li class="active">行情直播</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
				
				<label class="col-lg-3 col-lg-2"><span>类型：</span> 
					<select class="form-controlgoods input-append " name="livebroadcategoryPk">
						<option value="">全部</option>
						<c:forEach items="${broadCategoryList}" var="m">
						<option value="${m.pk}">${m.name}</option>
						</c:forEach>
					</select>
				</label>
				
				<label class="col-lg-3 col-lg-2"><span>添加时间：</span> 
				<input class="form-controlgoods input-append date form-dateday" type="text" name="strStartTime"   value="" readonly /></label>
			     <label class="col-lg-3 col-lg-2"><span>至：</span> 
			     <input class="form-controlgoods input-append date form-dateday" type="text" name="strEndTime"   value="" readonly /></label>
				 <input type="hidden" id="status" name="status"/>
				 <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			     <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
				 <a href="#" style="text-decoration: none;">
					 <button id="editable-sample_new" style="display:none;" showname="OPER_INFO_MARKET_BTN_ADD" class="btn btn-primary" data-toggle="modal" data-target="#myModal1" onclick="javascript:editNews('');">新增</button>
				 </a>
		             </header>
				<div class="panel-body">
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
			                    <table id="NewsId" style="width: 100%"></table>
                                </div>
                            </div>
                        </div>
                  
				</div>
				</section>
			</div>
		</div>
	</div>
	
	
	
	
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加行情直播</h4>
				</div>
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk" name="pk" />
					
					
			   			
			   				<div class="form-group slect-form">
							<label for="inputEmail3" class="col-sm-2 control-label"
								>直播分类</label>
							<div class="col-sm-10">
							<select   id="livebroadcategoryPk" class="selectpicker bla bla bli"   data-live-search="true" required="true" style=float:none;"">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${broadCategoryList}" var="m">
					            	<option  value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
			   				
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">内容编辑</label>
							<div class="col-sm-10">
							<textarea rows="10" cols="50" id="content" name="content" class="form-control" required="true" maxLength="100"></textarea>
						
							</div>
						</div>
						
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">关键字</label>
							<div class="col-sm-10">
								<input class="form-control" name="keyword" id="keyword" required="true" maxlength="100" onchange="inputLength();"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-10">
								<center>提示：关键字和内容编辑均在100字以内。</center>
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
 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>