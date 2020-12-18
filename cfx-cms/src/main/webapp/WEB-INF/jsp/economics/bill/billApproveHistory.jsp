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

<script src="./pageJs/economics/bill/billApproveHistory.js"></script>
<style>
.modal-backdrop.in {
	z-index: 100;
}

.modal {
	z-index: 1000;
}

.bootstrap-select>.dropdown-toggle {
	float: none;
}
#addnew .col-ms-12 {
    margin-left: 5px;
}
#editHtml{
*zoom:1;
}
#editHtml:after{
content:"";
clear:both;
display:table;
}
#editHtml .col-ms-12{
*zoom:1;
height:auto!important;
}
#editHtml .col-ms-12:after{
content:"";
clear:both;
display:table;
}
#editHtml label.col-sm-2:last-child {
    width: 180px;
    text-align:left!important;
}
#verify06 label.control-label.col-sm-1 {
    width: 100px;
}
@media (max-width:1600px){
	#editHtml label.col-sm-3{
		padding-left:10px;
		padding-right:10px;
		text-align:left!important;
	}
}
@media (max-width:1520px){
	#editHtml label.col-sm-3{
    width: 234px;
	}
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>票据管理/客户管理</li>
			<li class="active">申请记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper" style="background:#fff;">
		<div class="row">
			<div class="col-sm-12">
				
				<section class="panel tableleft" style="float:left;width:38%;">
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="tab-pane active" id="all"></div>
							<div class="tab-pane" id="verify"></div>
							<div class="tab-pane" id="verifydone"></div>
							<div class="tab-pane" id="unverify"></div>
							<table id="approveId" style="width: 100%"></table>
						</div>
					</div>
				</section>
				     	<input type="hidden" id="companyPk" value="${companyPk}"/>
                <section class="panelright" style="width: 61%;display: inline-block;margin-left:1%;">
           
						<!-- 固定三行 -->
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">公司名称：</label>
								<label class="control-label" id="companyName"></label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">联&nbsp;系&nbsp;人&nbsp;&nbsp;：</label>
								<label class=" control-label" id="contacts"> </label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">手机号码：</label>
								<label class=" control-label" id="contactsTel"></label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
						 	<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">申请产品：</label>
								<label class=" control-label" id="productName"></label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">申请时间：</label>
								<label class=" control-label" id="insertTime"></label>
							</div>
						</div>
						
							<div id ="editHtml"></div>
					
                </section>
			</div>
		</div>
	</div>
	<script>
		function entersearch() {
			var event = window.event || arguments.callee.caller.arguments[0];
			if (event.keyCode == 13) {
				SearchClean();
			}
		}
	</script>
	<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>