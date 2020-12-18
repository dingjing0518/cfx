<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员数据</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<script src="./pageJs/operation/operationCentreReport/memberData.js"></script>
<style>
td.bs-checkbox {text-align: center!important;vertical-align: middle!important;width: 36px;}
.panel-body {overflow: auto;padding-bottom:70px;}
.panel{    overflow-x: auto;}
.bootstrap-table,table#memberData {width:3800px;}
.fixed-table-body{overflow-x: hidden;overflow-y: hidden;}
.fixed-table-pagination{    display: block;position: absolute;bottom: 30px;width: 97%;}
.bootstrap-table .fixed-table-footer tbody > tr > td{border-right:1px solid rgba(249,249,249);}
.fixed-table-footer table:first-child{border-left:1px solid rgba(249,249,249);border-right:1px solid rgba(249,249,249);}
.fixed-table-footer table  tr td:first-child{border-right:1px solid rgba(249,249,249);}
.fixed-table-footer table  tr td:first-child+td{text-align:left!important;}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营中心</a></li>
			<li class="active">数据管理>会员数据</li>
		</ul> 
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 	<header class="panel-heading" style="margin-bottom:10px;">
					  	<input type="hidden" id="type" name="type"  value ="1"/>	
						<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>日期：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="startTime" id="startTime"  value="" readonly /></label>
				        <label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="endTime"  id="endTime"  value="" readonly /></label>
				        <button style="margin-left:18px;position: relative;z-index: 10000;" class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
	        			<button class="btn btn-primary"  onclick="SearchClean(2)"> 清除</button>  			           
			            <button  style="position: relative;z-index: 10000;display: none;" showname="OPER_REPORTFORMS_MEMBER_BTN_EXPORT" class="btn btn-primary" id="btn" onclick = "exportPresent();">导出</button>
	             	</header>
				<div class="panel-body">
					<section class="panel">
                        <div class="bootstrap-table" style="margin-top:10px;">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
    
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>   
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="memberData" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
<script>
    btnList();
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