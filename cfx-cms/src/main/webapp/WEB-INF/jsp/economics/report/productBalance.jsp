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
<!-- 页面内容导航开始-->
<script src="./pageJs/economics/report/productBalance.js"></script>
<style>
.left {
    width: 90%;
}
@media (max-width:1370px){
.form-controlgoods{
width:90px;
}
}
@media (max-width:1280px){
.form-controlgoods{
width:80px;
}
.left {
    width: 90%;
}
}
.table>caption+thead>tr:first-child>th, 
.table>colgroup+thead>tr:first-child>th, 
.table>thead:first-child>tr:first-child>th, 
.table>caption+thead>tr:first-child>td, 
.table>colgroup+thead>tr:first-child>td, 
.table>thead:first-child>tr:first-child>td{
	border-top:1px solid #ddd;
    border-right: 1px solid #ddd;
    border-bottom-width: 1px;
}
td {
    border-right: 1px solid #ddd;
}
.table>thead>tr>th {
    border-bottom-width: 1px;
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">数据报表&gt;金融产品使用余额统计表 </li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
					<div class="left">
						<label class="col-lg-2 col-lg-2"><span>时间范围：</span> <input class="form-controlgoods input-append date " type="text" id="startTime"   value="${start}" onkeydown="entersearch()"  readonly /></label>
	                    <label class="col-lg-2 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date " type="text" id="endTime"   value="${end}" readonly /></label>
					 	
		    </div>
		    <div class="right">
		      <button class="btn btn-primary" id="btn" onclick="search()"> 搜索</button>
			  <button  style="display:none;" class="btn btn-primary" showname="EM_REPORT_PRODUCTBALANCE_BTN_EXPORT"   id="btn" onclick="exportData()"><span id="loadingExportData">导出</span></button>    
			   </div>
             	</header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <table  style="text-align:center;width: 100%;border: 1px solid #ddd;" class="table table-hover table-striped">
                                <thead>
                                <tr>
                                	<th style="text-align: center; vertical-align: middle; " rowspan="2" colspan="1">
                                		<div class="th-inner ">日期</div>
                                		<div class="fht-cell"></div>
                                	</th>
                                	 <c:forEach items="${bankList}" var="dimen" > 
	                                	 <th style="text-align: center; vertical-align: middle; " rowspan="1" colspan="2">
		                                	<div class="th-inner ">${dimen.bankName}</div>
		                                	<div class="fht-cell"></div>
	                                	</th>
                                	 </c:forEach>
                                	</tr>
                                	<tr>
                                	 <c:forEach items="${bankList}" var="dimen" > 
	                                	 <th style="text-align: center; vertical-align: middle;     border-right: 1px solid #ddd;" rowspan="1" colspan="1" >
	                                		<div class="th-inner ">化纤白条</div>
	                                		<div class="fht-cell"></div>
	                                	</th>
	                                	<th style="text-align: center; vertical-align: middle;    border-right: 1px solid #ddd; " rowspan="1" colspan="1" >
	                                		<div class="th-inner ">化纤贷</div>
	                                		<div class="fht-cell"></div>
	                                	</th>
                                	 </c:forEach>
                                	
                                	</tr>
                                </thead>
                                	<tbody>
								          <tr>
										${content}
								          </tr>
								        </tbody>
                                </table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>

	<script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	searchTabs();  
	       }  
	} 	
	
	 $("#startTime").datetimepicker({
		 format: 'yyyy-mm-dd',
		 language: 'zh-CN',
		 weekStart: 1,
		 todayBtn: 1,//显示‘今日’按钮
		 autoclose: 1,
		//	todayHighlight: 1,
		startView: 2,
   		minView: 2,  //Number, String. 默认值：0, 'hour'，日期时间选择器所能够提供的最精确的时间选择视图。
		forceParse: 0,
            endDate: new Date(),
    }).on('changeDate',function(ev){
        var starttime=$("#startTime").val();
        $("#endTime").datetimepicker('setStartDate',starttime);
        $("#startTime").datetimepicker('hide');
   });
   
   $("#endTime").datetimepicker({
		format: 'yyyy-mm-dd',
		 language: 'zh-CN',
		 weekStart: 1,
		 todayBtn: 1,//显示‘今日’按钮
			 autoclose: 1,
		//	todayHighlight: 1,
			startView: 2,
   		minView: 2,  //Number, String. 默认值：0, 'hour'，日期时间选择器所能够提供的最精确的时间选择视图。
		forceParse: 0,
            endDate: new Date(),
  }).on('changeDate',function(ev){
      var starttime=$("#startTime").val();
      var endtime=$("#endTime").val();
      $("#startTime").datetimepicker('setEndDate',endtime);
      $("#endTime").datetimepicker('hide'); 
 });
	
	</script>
	
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>