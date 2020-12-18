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
<script src="./pageJs/manage/emailRecord.js"></script>
<!-- 页面内容导航开始	-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">管理中心</a></li>
			<li class="active">站内信记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-lg-3 col-lg-2"><span>&nbsp;手机号码：</span> 
					         <input type="text"  name="mobile" class="form-controlgoods" aria-controls="dynamic-table"  >
					     </label>
					     <label class="col-lg-3 col-lg-2"><span>&nbsp;公司名称：</span> 
					        <input type="text"  name="companyName" class="form-controlgoods" aria-controls="dynamic-table"  >
					     </label>
					     <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</butto>
			     </header>
			        	<table id="emailRecordId" style="width: 100%"></table>
				
				</section>
			</div>
		</div>
	</div>

	

</body>
</html>
<script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	SearchClean();  
       }  
} 
</script>