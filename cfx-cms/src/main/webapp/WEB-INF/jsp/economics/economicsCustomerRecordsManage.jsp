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
<script src="./pageJs/economics/economicsCustomerRecordsManage.js"></script>
<style>
.modal-backdrop.in{z-index:100;}
.modal{z-index:1000;}
.bootstrap-select > .dropdown-toggle{float:none;}
.selectleft .btn-group{float:left!important;}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>金融中心/</li>
			<li class="active">申请客户记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<label class="col-lg-2 col-lg-3"><span>公司名称：</span> <input class="form-controlgoods input-append " type="text" name="companyName"   value="" onkeydown="entersearch()"/></label>	
						 <label class="col-lg-2 col-lg-3"><span>申请时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"  id="insertStartTime"  value="" onkeydown="entersearch()"  readonly /></label>
	                    <label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"  id="insertEndTime"  value="" readonly /></label>
		                <label class="col-lg-2 col-lg-3"><span>手机号码：</span> <input class="form-controlgoods input-append " type="text" name=contactsTel   value="" onkeydown="entersearch()"/></label>	
					 	</label>
					 	<input type ='hidden' id = "status" name ="status"/>	
		      <button class="btn btn-primary" id="btn" onclick="SearchClean(1)"> 搜索</button>
			      <button class="btn btn-warning" id="btn" onclick="SearchClean(2)"> 清除</button>
			      	<button style="display:none;" showname="EM_CUST_APPLY_BTN_ADD" 
				class="btn btn-default dropdown-toggle" onclick="addApply();" data-toggle="dropdown">新增 </button>
             	</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findStatus(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findStatus(1);">待受理</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findStatus(2);">已受理</a>
                                </li>
                                 <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findStatus(3);">已作废</a>
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
                                <table id="dimensionalityManageId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="customersDimensionalityId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">分配客户</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
			<input type="hidden" id="pk"/>
					<!-- 编辑页面开始 -->
						<div class="form-group col-sm-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">金融专员:</label>
							<div class="col-sm-8">
							<select   id="assidirPk" name="assidirPk" class="selectpicker bla bla bli"   data-live-search="true" required="true">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${acccountList}" var="m">
					            	<option  value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						<div class="form-group col-sm-12"
							>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">客户来源:</label>
							<div class="col-sm-8" >
							<select   id="source" name="source" class="selectpicker bla bla bli"   data-live-search="true" required="true">
					          	 	<option value="">--请选择--</option>
									<c:forEach items="${sourceList}" var="m">
										<option value="${m.pk}">${m.sourceName }</option>
									</c:forEach>
			   				</select>
							</div>
						</div>
						<div class="clearfix"></div>
						
						<!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitAssi();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
<!--================== 	新增弹框 =========================-->
	<div class="modal fade" id="addEconRecordId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">新增申请</h4>
				</div>

				<div class="modal-body">
			<form id="dataFormApply">
			<input type="hidden" id="pk"/>
					<!-- 编辑页面开始 -->
						<div class="form-group col-sm-12">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">公司名称:</label>
							<div class="col-sm-8 selectleft">
							<select   id="companyPk" name="companyPk" class="selectpicker bla bla bli"   data-live-search="true" required="true">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${purCompanyList}" var="m">
					            	<option  value="${m.pk}">${m.name}</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						<div class="form-group col-sm-12">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">申请产品:</label>
							<div class="col-sm-8">
							<c:forEach items="${economicGoodsList}" var="m">
							<label style="font-weight: normal;"><input name="productPks" type="checkbox" value="${m.pk}" style="margin-left:3px;" />${m.name}</label>
					        </c:forEach>  
							</div>
						</div>
						<div class="clearfix"></div>
						<!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiaoApply"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitApply();">保存</button>
				</div>
			</div>
		</div>
	</div>
	    <script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	SearchClean();  
       }  
} 
</script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>