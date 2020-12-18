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
<script src="./pageJs/manage/smsRecord.js"></script>
<!-- 页面内容导航开始	-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">管理中心</a></li>
			<li class="active">短信记录</li>
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
					   <%-- <label class="col-lg-3 col-lg-2"><span>&nbsp;发送状态：</span>
					          <select name="status" id="status" class="selectpicker bla bla bli"    >
					          <option value="">--请选择--</option>
					         <option value="成功">成功</option>
					          <option value="失败">失败</option>
					            
					          </select>
					     </label>--%>
					     <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</butto>
			     </header>
			     	<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findSmsStatus(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findSmsStatus('成功');">已成功</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findSmsStatus('失败');">失败</a>
                                </li>
                          
                            </ul>
                        </header>
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
                                	<table id="smsRecordId" style="width: 100%"></table>
                         
                            </div>
                        </div>
                    </section>
				</div>
				
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