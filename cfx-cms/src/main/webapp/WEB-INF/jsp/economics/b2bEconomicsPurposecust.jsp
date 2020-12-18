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
<script src="./pageJs/economics/b2bEconomicsPurposecust.js"></script>
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
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>金融中心/</li>
			<li class="active">意向客户管理</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;height: 100px">
					<div class="left">
					 <label class="col-lg-2 col-lg-2"><span>公司名称：</span> <input class="form-controlgoods input-append " type="text" name="companyName"   value="" onkeydown="entersearch()"/></label>	
						<label class="col-lg-2 col-lg-2"><span>处理时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"   value="" onkeydown="entersearch()"  readonly /></label>
	                    <label class="col-lg-2 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"   value="" readonly /></label>
						 <label class="col-lg-2 col-lg-2"><span>提交时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"   value="" onkeydown="entersearch()"  readonly /></label>
	                    <label class="col-lg-2 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"   value="" readonly /></label> 
						<label class="col-lg-2 col-lg-2"><span>手机号码：</span> <input class="form-controlgoods input-append " type="text" name=contactsTel   value="" onkeydown="entersearch()"/></label>	
						<label class="col-lg-2 col-lg-3"><span>类型：</span>
						 <select class="selectpicker bla bla bli" name="type" id="type" style="width: 200px;" >
							<option value="">--请选择--</option>
							<option value="1">授信</option>
						  	<option value="2">票据</option>
						 </select>
					 	</label>
					 	<input type="hidden" id="status" name="status"/>
		    </div>
		    <div class="right">
		      <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
			   </div>
             	</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findStatus(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findStatus(1);">未处理</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findStatus(2);">已处理</a>
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
                                <table id="ecPurposecustId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
<!-- ===================================== -->
<div class="modal fade" id="editId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">意向客户处理</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">处理状态</label> 
								<div class="col-sm-5">
									<label>未处理
									<input name="status" type="radio" value="1" checked='checked' style="margin-left:3px;"/>
									</label> 
									<label>已处理
									<input name="status" type="radio" value="2" style="margin-left:3px;"/>
									</label>
								</div>
						</div> 
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">备注</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="remarks" id="remarks" value="" style="height: 60px;"></textarea>
							</div>
						</div> 
						<!-- 编辑页面结束 -->
						</form>
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
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	searchTabs();  
	       }  
	} 	
	</script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"/>
	 <script src="./pageJs/include/form-dateday.js"/>
</body>
</html>