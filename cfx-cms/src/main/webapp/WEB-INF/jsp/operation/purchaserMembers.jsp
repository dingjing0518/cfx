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
<script src="./pageJs/operation/purchaserMembers.js"></script>
<style>
.btn-group.open .dropdown-toggle{line-height:20px;}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">客户管理</a></li>
			<li class="active">会员管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
			           <label class="col-lg-3 col-lg-2"><span>公司名称</span> <input class="form-controlgoods input-append " type="text" name="companyName"   value="" onkeydown="entersearch()"/></label>	
			           <label class="col-lg-3 col-lg-2"><span>注册电话</span> <input class="form-controlgoods input-append " type="text" name="mobile"   value="" onkeydown="entersearch()"/></label>
			           <label class="col-lg-3 col-lg-2"><span>注册时间</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"   value="" readonly /></label>
			          
			            <label class="col-lg-3 col-lg-2"><span>等级</span> <input class="form-controlgoods input-append " type="text" name="level"   value="" onkeydown="entersearch()"/></label>	
					    <label class="col-lg-3 col-lg-2"><span>等级名称</span> <input class="form-controlgoods input-append " type="text" name="levelName"   value="" onkeydown="entersearch()"/></label>	
			           <label class="col-lg-3 col-lg-2"><span>最后赠送时间</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="feedStartTime"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="feedEndTime"   value="" readonly /></label> 

			           <label class="col-lg-3 col-lg-2"><span>审核时间</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="auditStartTime"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="auditEndTime"   value="" readonly /></label>
					 
					   <label class="col-lg-3 col-lg-2"><span>注册平台</span>
					   <select class="selectpicker bla bla bli" name="registerSource" style="width: 200px;">
					   				<option value="">--请选择--</option>
									<option value="1">pc端</option>
									<option value="3">app端</option>
									<option value="2">wap端</option></select></label>	
			            <input type="hidden" id="auditStatus" name="auditStatus" value="0"/>
			            <button class="btn btn-primary" onclick="searchTabs(1)"> 搜索</button>
			      		<button class="btn btn-primary" onclick="searchTabs(2)"> 清除</button>
			      		<button class="btn btn-primary" style="display: none;" showname="COMMON_CM_MEMBER_BTN_ADD" onclick="addMember()"> 新增</button>
			      		<button class="btn btn-primary" style="display: none;" showname="COMMON_CM_MEMBER_BTN_EXPORT" onclick="excel()">导出</button>
						<input type = "hidden" id="modelType" name="modelType" value="${modelType}"/>
					</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findPurchaserMem(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaserMem(1);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaserMem(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaserMem(3);">审核不通过</a>
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
                                 <table id="purchaserMembersId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editMemberId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">采购商会员信息编辑</h4>
				</div>
				<div class="modal-body">
				<form id="dataForm">
							<input type="hidden" id="pk" name="pk" value=""/>
							<!-- <input type="hidden" id="insertTime" name="insertTime" value=""/>
							<input type="hidden" id="auditTime" name="auditTime" value=""/> -->
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">公司名称</label>
						<div class="col-sm-10">
							<select   id="companyPk" name="companyPk" class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${companyList}" var="m">
					            	<option value="${m.pk}">${m.name}</option>
					           	    </c:forEach>  
			   				</select>
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">联系电话</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" id="phone" name="mobile"/>
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">员工工号</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" id="employeeNumber" name="employeeNumber"/>
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">员工姓名</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" id="employeeName" name="employeeName"/>
						</div>
					</div>
					</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 审核信息页面 -->
	<div class="modal fade" id="auditMemberId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">会员审核信息</h4>
				</div>
				<div class="modal-body">
				<form id="dataForm">
							<input type="hidden" id="auditPk" name="auditPk" value=""/>
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">公司名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" readonly="readonly" id="auditCompanyPk" name="auditCompanyPk"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">联系电话</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control" readonly="readonly" id="auditPhone" name="auditPhone"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">不通过原因</label>
						<div class="col-sm-10">
						<textarea class="form-control" name="auditNoPassR" id="auditNoPassR" value=""
									style="height: 40px;"></textarea>
						</div>
					</div>
					</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" style="display: none;" showname="COMMON_CM_MEMBER_CREDIT_BTN_PASS" class="btn btn-primary" onclick="auditSubmit(2);">审核通过</button>
					<button type="button" style="display: none;" showname="COMMON_CM_MEMBER_CREDIT_BTN_UNPASS" class="btn btn-primary" onclick="auditSubmit(3);">审核不通过</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<!-- 绑定角色 -->
	<div class="modal fade" id="setRole" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">绑定角色</h4>
				</div>
				<input id="memberPk" type="hidden"/>
				
				<div class="modal-body">
				
					<div class="form-group col-ms-12" style="height: 34px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label" style="text-align: left;    line-height: 34px;">采购商角色:</label>
							<div class="col-sm-6">
							<select id="purchaserRole"  multiple="multiple" style="width:280px;margin-left:0px">

					            	<c:forEach items="${purchaserRoleList}" var="m">
					            	<option value="${m.pk}" purType = "${m.companyType}" >${m.name}</option>
					           	    </c:forEach>  
		   					</select>
							</div>
					</div>
					
					<div class="form-group col-ms-12" style="height: 34px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label" style="text-align: left;    line-height: 34px;">供应商角色:</label>
							<div class="col-sm-6">
							<select id="supplierRole"  multiple="multiple" style="width:280px;margin-left:0px">

					            	<c:forEach items="${supplierRoleList}" var="m">
					            	<option value="${m.pk}" supType = "${m.companyType}" >${m.name}</option>
					           	    </c:forEach>  
		   					</select>
							</div>
					</div>
				
					</div>
					<!--  <table id="roles" style="width: 100%"></table> -->
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-primary" onclick="saveRoles();">保存</button>
				</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    </script>
	<!-- 图片放上去显示js -->
	<script type="text/javascript">
		function entersearch(){  
		    var event = window.event || arguments.callee.caller.arguments[0];  
		    if (event.keyCode == 13)  
		       {  
		    	searchTabs();  
		       }  
		} 
	
		function showImg1(){
		document.getElementById("wxImg1").style.display='block';
		}
		function hideImg1(){
		document.getElementById("wxImg1").style.display='none';
		}
	</script>
	<!-- 汉化inputfile-->
	<script>
	/*  $('#file-0c').fileinput({
         language: 'zh', //设置语言
     }); */
	 </script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>