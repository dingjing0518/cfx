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
<script src="./pageJs/marketing/c2b/marrieddealAudit.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">求购管理</a></li>
			<li class="active">求购审核</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
			             	<button id="editable-sample_new" class="btn btn-primary"
									data-toggle="modal" onclick="javascript:edit('');">
									新增 <i class="fa fa-plus"></i>
								</button>
								<input type="hidden" value="" id="auditStatus" name="auditStatus"/>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findMarri(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findMarri(1);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findMarri(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(3);">审核不通过</a>
                                </li>
                               <!--   <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(4);">过期</a>
                                </li>
                                 <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(5);">洽谈中</a>
                                </li>
                                 <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(6);">成交</a>
                                </li> -->
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
                                <table id="marrieddealAuditId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="editMarrId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">添加求购</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="marrieddealId" />
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">采购标题</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="name"   required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">公司名称</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							<select   id="companyPID" class="selectpicker bla bla bli" onchange="changeCompay()"   data-live-search="true">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${companyList}" var="m">
					            	<option data-contacts="${m.contacts}" data-contactsTel="${m.contactsTel}" value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
							<div class="col-sm-5" style="margin-left:-22px;">
							<select class="form-control col-sm-3" id="companyPk" >
							<option value="">--请选择子公司--</option>
							</select>
							</div>
						</div>
							<!-- <div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">采购单号</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="marrieddealId"   required="true"/>
							</div>
						</div> -->
							
							 
					 	<div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">规格大类</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							  <select id="addSpecPk" class="selectpicker bla bla bli"   data-live-search="true" onchange="AddChangeSpec()">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${specList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
							<div class="col-sm-5" style="margin-left:-22px;">
							<select class="form-control col-sm-3" id="addSeriesPk"   >
									<option value="">--请选择规格系列--</option>
							</select>
							</div>
						</div>
						 <div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">等级</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							  <select id="gradePk" class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${gradeList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						     <div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">品种</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							   <select id="addVarietiesPk"    class="selectpicker bla bla bli" data-live-search="true" onchange="addChangeVari()">
 
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>  	
							</div>
							<div class="col-sm-5" style="margin-left:-44px;">
							  <select id="addSeedvarietyPk"   class="selectpicker bla bla bli" data-live-search="true"  >
 
							   <option value="">--请选择子品种--</option>
							 </select>  
							</div>
						</div>	 
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">品牌</label>
							<div class="col-sm-5" style="width:210px;">
								<select class="form-control col-sm-3" id="brandPk">
								<option value="">--请选择品牌--</option>
								<c:forEach items="${brandList}" var="m">
									<option value="${m.pk}">${m.name }</option>
								</c:forEach>
							</select>
							</div>
						</div>
							<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">品名</label>
							<div class="col-sm-5" style="width:210px;">
									<select class="form-control col-sm-3" id="productPk">
									<option value="">--请选择子品名--</option>
									<c:forEach  items="${productList}" var="m">
										<option value="${m.pk}">${m.name }</option>
									</c:forEach>
							</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">求购数量</label>
							<div class="col-sm-5" style="width:210px;">
								<input type="text" class="form-control" id="buyCounts"   required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">求购价格</label>
							<div class="col-sm-5" style="width:210px;">
								<input type="text" class="form-control" id="price"   required="true"/>
							</div>
						</div>
								<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
								<label for="inputEmail3" class="col-sm-2 control-label">开始时间</label>
								<div class="col-sm-8" style="width:210px;">
									<input class="form-control input-append date form_datetime" type="text" id="starttime"   value="" readonly > 
								</div>
							</div>
							<div class="form-group col-ms-12" style="height: 30px; display: block;">
								<label for="inputEmail3" class="col-sm-2 control-label">截止时间</label>
								<div class="col-sm-8" style="width:210px;">
									<input class="form-control input-append date form_datetime" type="text"   id="endtime" readonly  >
								</div>
							</div> 
						
						
						
						 <!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="saveMarrieddeal();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="refuseReason" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">审核不通过</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="refuseReasonid" />
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">理由</label>
							<div class="col-sm-10">
								<textarea rows="" cols="" id="refuseReasons"></textarea>
							</div>
						</div>
					 
						    
						
						
						 <!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="refuseReasons_quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="saveRefuseReason();">保存</button>
				</div>
			</div>
		</div>
	</div>
	 <script>
 		$(".form_datetime").datetimepicker({
 		  minView: "month",
	      format: "yyyy-mm-dd",
	      autoclose: true,
	      todayBtn: true,
	      language:'zh-CN',
	      pickerPosition:"bottom-right"
	    });
</script>
</body>
</html>