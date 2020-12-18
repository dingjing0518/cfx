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
<script src="./pageJs/rechargeRecord.js"></script>
<!-- 页面内容导航开始	-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">财务中心</a></li>
			<li class="active">充值记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->

	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;公司名称：</span> 
					         <input type="text"  name="companyName" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
					     </label>
					     <label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;虚拟账户号：</span> 
					         <input type="text"  name="transactionAccount" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
					     </label>
					    
					      <label class="col-sm-6 col-md-4 col-lg-3"><span>充值时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeBegin"   value="" readonly /></label>
			              <label class="col-sm-6 col-md-4 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
			               <label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;充值状态：</span> 
					         <select name="status" class="selectpicker bla bla bli">
					          <option value="">--请选择--</option>
					          <option value="1">成功</option>
					           <option value="2">失败</option>
					         </select>
					     </label>
					     <button class="btn btn-primary" id="btn" onclick="search(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="search(2);"> 清除</butto>
			     </header>
				<div class="panel-body">
					<div class="adv-table editable-table ">
						<div class="clearfix">
							<div class="btn-group" style="margin-bottom: 20px; width: 100%">
								<!-- 模态框 -->
								 
								<table id="rechargeRecord" style="width: 100%"></table>
							</div>
						</div>
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>

</body>
<script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	search(this);  
	       }  
	} 
</script>
<script src="./pageJs/include/form-dateday.js"></script>
</html>