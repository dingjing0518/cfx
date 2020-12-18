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
<script src="./pageJs/marketing/purchaserM.js"></script>
<style>
#editCompayId .bootstrap-select > .dropdown-toggle{
  width:180px;
}
#editCompayId span.help-block span {
    position: absolute;
    right: -5px;
    margin-top: 5px!important;
}
.ywgl.open .open>.dropdown-menu {
    display: none;
}
@media (max-width:1600px){
.addressnew {
    width: 31%!important;
}
.addressnew span {
    margin-right: 62px;
}
.addressnew .address{
width:24%;}
}
@media (max-width:1366px){
.addressnew span {
    margin-right: 25px;
}
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">分配客户</a></li>
			<li class="active">分配采购商</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
								<label class="col-lg-2 col-lg-3"><span>公司名称：</span> <input class="form-controlgoods input-append " type="text" name="name"   value="" /></label>
			                    <label class="col-lg-2 col-lg-3"><span>电话：</span> <input class="form-controlgoods input-append " type="text" name="mobile"   value=""/></label>
			                   
			                    <label class="col-lg-2 col-lg-3 ywgl"><span>业务经理：</span>
			                       <select name="accountPk" class="selectpicker bla bla bli"    data-live-search="true">
										   <option value="">--请选择--</option>
											<c:forEach items="${accountList }" var="m">
												<option value="${m.accountPk }">${m.accountName }</option>
											</c:forEach>  
								   </select> 
								</label>
								<label class="col-lg-2 col-lg-3"><span>注册时间：</span>
								 <input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"   value="" readonly /></label>
								 <label class="col-lg-2 col-lg-3"><span>至</span> 
								 <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"   value="" readonly /></label>
							     <label class="col-lg-2 col-lg-3"><span>分配时间</span> <input style="float:left;margin-left:20px;" class="form-controlgoods input-append date form-dateday" type="text" name="distributeMemberStartTime"   value="" readonly /></label>
                                 <label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="distributeMemberEndTime"   value="" readonly /></label>
								<label class="col-lg-2 col-lg-3"><span>是否分配：</span> 
									<select class="selectpicker bla bla bli " name="isDistribute">
										<option value="">全部</option>
										<option value="1">已分配</option>
										<option value="2">未分配</option>
									</select>
								</label> 
								
								<label class="col-lg-3 col-lg-2 addressnew" style="    width: 18%;relative;z-index: 9;"><span>所在地区：</span> 
	                     <div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
							<select class="form-control col-sm-3" style="padding:6px;" id ="provinceName" name="provinceName" onchange="changeCitySearch(this);" required="true" style="float:none;">
							<option value="">--省--</option>
						 	<c:forEach var="c" items="${province}">
									<option value="${c.pk }">${c.name}</option>
								</c:forEach>
							</select>	
						</div>
						<div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
							<select class="form-control col-sm-3" id = "cityName" style="padding:6px;"  name="cityName" onchange="changeAreaSearch(this);" required="true" style="float:none;"><option value="">--市--</option></select>
						</div>
						<div class="col-sm-3 address" style="padding-left: 0px;padding-right: 10px;">
							<select class="form-control col-sm-3" id ="areaName" style="padding:6px;"  name="areaName"  required="true" style="float:none;"><option value="">--区--</option></select>
						</div>
					</label>
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
		             </header>
			      				<input type="hidden" id="searchType" name="searchType" value="0"/>	
				<div class="panel-body">
					<section class="panel">
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
                                <table id="purchaserMId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>


<!--查看禁用产品申请通过列表-->

	<div class="modal fade" id="childsCom" tabindex="-1" role="dialog"
		 aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document"
			 style="width: 80%; height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">授信审核通过</h4>
				</div>
				<input type="hidden" id="parentPk" />
				<div class="modal-body">
					<header class="panel-heading custom-tab ">

					</header>
					<table id="childsEconomics" style="width: 100%"></table>
				</div>

			</div>
		</div>
	</div>
	

	<div class="modal fade" id="editCompayId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">分配业务经理</h4>
				</div>
				<div class="modal-body">
				<form   id="dataForm">
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">业务经理</label>
						<div class="col-sm-6">
							<select id="marketingCompany" name="marketingCompany" required="true" class="selectpicker bla bla bli"   data-live-search="true">
							<option value="">--请选择--</option>
							<c:forEach var="person" items="${accountList}">
							<option value="${person.accountPk }">${person.accountName}</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<input type="hidden" value="" id="companyPk"/>
				</form>
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" value="${isShowF}" id="isShow"/>
	<!-- 图片放上去显示js -->
	<script type="text/javascript">
		function showImg1(){
		document.getElementById("wxImg1").style.display='block';
		}
		function hideImg1(){
		document.getElementById("wxImg1").style.display='none';
		}
	</script>
    <script>
		function entersearch(){  
		    var event = window.event || arguments.callee.caller.arguments[0];  
		    if (event.keyCode == 13)  
		       {  
		    	searchTabs();  
		       }  
		} 
		
		 function errorImg(img) {
		        img.src = "./style/images/bgbg.png";
		        img.onerror = null;
		        if(img.src = "./style/images/bgbg.png"){
			    	$('#purchaserMId a').removeAttr("href");
			    }
		    }
   </script>
    <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>