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
<script src="./pageJs/marketing/c2b/c2bMarrieddeal.js"></script>
<!-- 页面内容导航开始-->
<style >
	.bootstrap-select > .help-block {
		margin-top: 40px;
	}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">采购需求</a></li>
			<li class="active">求购管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" > 
						<label class="col-sm-6 col-md-4 col-lg-3"><span>需求单号：</span> <input class="form-controlgoods input-append " type="text" name=pk   value="" onkeydown="entersearch()"/></label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>采购商名称：</span> <input class="form-controlgoods input-append " type="text" name=purchaserName   value="" onkeydown="entersearch()"/></label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>联系电话：</span> <input class="form-controlgoods input-append " type="text" name=contactsTel   value="" onkeydown="entersearch()"/></label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>品名：</span>
						<select name="productPk" class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${productList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
						</label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>品种：</span>
							 <select name="varietiesPk"  class="selectpicker bla bla bli" data-live-search="true">
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>
						</label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>等级：</span>
							<select name="gradePk" class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${gradeList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
			   			</label>
			   			<!-- <label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;审核状态：</span>
							<select name="status" class="selectpicker bla bla bli">
								<option value="">--请选择--</option>
								<option value="0">待审核</option>
								<option value="1">审核通过</option>
								<option value="3">审核不通过</option>
							</select> 
						</label> -->
						<label class="col-sm-6 col-md-4 col-lg-3"><span>审核时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="auditTimesStart"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="auditTimesEnd"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>发布时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="issueTimesStart"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="issueTimesEnd"   value="" readonly /></label>
						
						
					<button class="btn btn-primary" id="btn" onclick="searchTabs(1);">
						搜索</button>
					<button class="btn btn-primary" id="btn" onclick="searchTabs(2);">
						清除
						</button>
						<button id="editable-sample_new" style="display:none;" showname="BTN_SENDMARRIED"  class="btn btn-primary"
									data-toggle="modal" onclick="javascript:edit('');">
									发布需求
								</button>
								
						<button id="editable-sample_new" style="display:none;" showname="BTN_EXPORTMARRIED"  class="btn btn-primary"
									data-toggle="modal" onclick="javascript:exports();">
									导出
								</button>		
						<input type="hidden" id="auditStatus" name="auditStatus" value=""/>
						
					</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findMarri(-2);">全部</a>
                                </li>
                                  <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(0);">待审核</a>
                                </li>
                               <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(1);">已审核</a>
                                </li>
                               
                                 <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(-1);">审核未通过</a>
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
                                <table id="c2bMarrieddealId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>

	<!-- 编辑和发布需求 -->
	<div class="modal fade" id="editPublishingRequirements" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">编辑需求</h4>
				</div>

				<div class="modal-body">
			<form id="dataFormGoods">
					<!-- 编辑页面开始 -->
					<input type="hidden" id="pk" name="pk" value=""/>
					<input type="hidden" class="form-control" id="pk" />
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">公司名称</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							  <select id="purchaserName" name="purchaserName" class="selectpicker bla bla bli"   data-live-search="true" required="true"  onchange="getCompanyByPk()">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${pcompanyList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">联系人</label>
							<div class="col-sm-5" style="width:210px;">
							<input type="text" class="form-control" id="contacts" name="contacts"  required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">联系电话</label>
							<div class="col-sm-5" style="width:210px;">
							<input type="text" class="form-control" id="contacts_tel"  name="contacts_tel" required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">品名</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							  <select id="productPk" name="productPk" class="selectpicker bla bla bli"  data-live-search="true" required="true"  onchange="hiddenSpec()">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${productList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						<div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">品种</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							   <select id="addVarietiesPk"  name="addVarietiesPk"  class="selectpicker bla bla bli" required="true" data-live-search="true" ><!-- onchange="addChangeVari()" -->
 
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>  	
							</div>
						</div>
						<div id="productSpec" class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">规格大类</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							  <select id="addSpecPk" name="addSpecPk" class="selectpicker bla bla bli"  required="true" data-live-search="true"  onchange="AddChangeSpec()">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${specList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
							<div class="col-sm-5" style="margin-left:-22px;">
							<select class="form-control col-sm-3" id="addSeriesPk" name="addSeriesPk" data-live-search="true"  style="display:none;">
									<option value="">--请选择规格系列--</option>
							</select>
							</div>
						</div>
						 <div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">等级</label>
							<div class="col-sm-5" style="margin-left:-22px;">
							  <select id="gradePk" name="gradePk" class="selectpicker bla bla bli" data-live-search="true" required="true" data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${gradeList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">求购数量</label>
							<div class="col-sm-4" style="width:160px;">
								<input type="text" class="form-control" id="buyCounts" name="buyCounts" required="true"/>
							</div>
							<div class="col-sm-1" style="width:21px;">
								<span>(吨)</span>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">备注</label>
							<div class="col-sm-5">
								<textarea class="form-control" id="remarks" name="remarks" style="width:260px;"></textarea>
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
	
	<!-- 审核发布需求 -->
	<div class="modal fade" id="noAuditQuoted" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">审核不通过</h4>
				</div>
				<div class="modal-body">
				<form   id="reasonForm">
					<input type="hidden" class="form-control" id="auditStatusR" name="auditStatus"/>
					<input type="hidden" class="form-control" id="auditPk" name="auditPk"/>
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">拒绝原因</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="refuseReason" name="refuseReason" required="true"/>
						</div>
					</div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="qx" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="refuse();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 推荐供应商 -->
	<div class="modal fade" id="pushSupplierId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">推荐供应商</h4>
				</div>
				<div class="modal-body">
				<form  id="reasonForm">
					<input type="hidden" class="form-control" id="auditStatusR" name="auditStatus"/>
					<input type="hidden" class="form-control" id=marrieddealBidPk name="marrieddealBidPk"/>
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">推荐供应商:</label>
						<div class="col-sm-4">
							<select id="selectPushSupplierId"  name="selectPushSupplierId"  class="selectpicker bla bla bli" required="true" data-live-search="true">
							   <option value="">--请选择--</option>
							 </select>
						</div>
					</div>
				</form>
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="qx" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="push();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 跟踪记录信息trackRecordId -->
	<div class="modal fade" id="trackRecordId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="col-sm-12">
				<div class="modal-header" style="position:relative;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">跟踪记录</h4>
				</div>
			    <button id="editable-sample_new" class="btn btn-primary"  style="position:absolute;top:370px;right:175px;"
									data-toggle="modal" onclick="javascript:exportsTrack();">
									导出</button>			
			<form id="dataForm">
				<section class="panel">
					<header class="panel-heading" > 
						<div class="modal-body">
					<!-- 编辑页面开始 -->
					<input type="hidden" id="pk" name="pk" value=""/>
					<input type="hidden" class="form-control" id="trackPk" name="trackPk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-xs-6 col-sm-2 col-md-2 control-label"
								style="text-align: right;">需求单号:</label>
							<div class="col-xs-6 col-sm-4 col-md-4">
								<input type="text" readonly="readonly"  style="border-style:none;font-size:14px;padding:0px 3px !important;" class="form-control " id="track_marrieddealId" required="true"/>
							</div>
							<label for="inputEmail3" class="col-xs-6 col-sm-2 col-md-2 control-label"
								style="text-align: right;">商品信息:</label>
							<div class="col-xs-6 col-sm-4 col-md-4">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_productInfo"   required="true"/>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-xs-6 col-sm-2 col-md-2 control-label"
								style="text-align: right;">需求重量:</label>
							<div class="col-xs-6 col-sm-4 col-md-4">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_count"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-xs-6 col-sm-2 col-md-2 control-label"
								style="text-align: right;">发布时间:</label>
							<div class="col-xs-6 col-sm-4 col-md-4">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_insertTime"   required="true"/>
							</div>							
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-xs-6 col-sm-2 col-md-2 control-label"
								style="text-align: right;">备注:</label>
							<div class="col-xs-6 col-sm-4 col-md-4">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_remarks"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-xs-6 col-sm-2 col-md-2 control-label"
								style="text-align: right;">采购商名称:</label>
							<div class="col-xs-6 col-sm-4 col-md-4">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_purchaserName"   required="true"/>
							</div>							
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-xs-6 col-sm-2 col-md-2 control-label"
								style="text-align: right;">联系人:</label>
							<div class="col-xs-6 col-sm-4 col-md-4">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_purchaserContacts"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-xs-6 col-sm-2 col-md-2 control-label"
								style="text-align: right;">手机号码:</label>
							<div class="col-xs-6 col-sm-4 col-md-4">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_purchaserTel"   required="true"/>
							</div>							
						</div>

				</div>
				<div class="modal-footer">
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">拜访内容:</label>
							<div class="col-sm-6">
								<textarea class="form-control" id="track_remark" style="width:500px;"></textarea>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">采购商确认:</label>
							
							<div class="col-sm-2">
								<label class="radio-inline">
							        <input type="radio"  name="track_isConfirmed" value="1" checked> 确认
							    </label>
							    <label class="radio-inline">
							        <input type="radio" name="track_isConfirmed"  value="0"> 未确认
							    </label>
							</div>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitTrack();">保存</button>
					
				</div>
				
					</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="searchQuotedStatus(-1);">全部</a>
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
                                <table id="c2bMarrieddealTrackId" style="width: 100%;height:60%;"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
	</div>
<!-- 报价管理子页面 -->
	<div class="modal fade" id="quotedPriceManageId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="col-sm-12">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">报价管理</h4>
				</div>
				<section class="panel">
					<header class="panel-heading" > 
						<!-- <label class="col-sm-6 col-md-4 col-lg-3"><span>需求单号：</span> <input class="form-controlgoods input-append " type="text" name=bpk   value="" /></label> -->
						<label class="col-sm-6 col-md-4 col-lg-3"><span>供应商名称：</span> <input class="form-controlgoods input-append " type="text" name="supplierName"   value="" /></label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>联系电话：</span> <input class="form-controlgoods input-append " type="text" name="bcontactsTel"   value="" /></label>
						
						<label class="col-sm-6 col-md-4 col-lg-3"><span>报价时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimesStart"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimesEnd"   value="" readonly /></label>

					<button class="btn btn-primary" id="btn" onclick="searchquotedPriceTabs(1);">
						搜索</button>
					<button class="btn btn-primary" id="btn" onclick="searchquotedPriceTabs(2);">
						清除
						</button>
						<!-- <button id="editable-sample_new" class="btn btn-primary"
									data-toggle="modal" onclick="javascript:exportsBidSub();">
									导出
								</button>	 -->	
						<input type="hidden" id="bidStatus" name="bidStatus" value=""/>
						<input type="hidden" id="marrieddealPk" name="marrieddealPk" value=""/>
					</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="searchQuotedStatus(-2);">全部</a>
                                </li>
                                  <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="searchQuotedStatus(100);">未报价</a>
                                </li>
                               <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="searchQuotedStatus(1);">已报价</a>
                                </li>
                                 <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="searchQuotedStatus(2);">未中标</a>
                                </li> 
                                <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="searchQuotedStatus(3);">已中标</a>
                                </li>
                                <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="searchQuotedStatus(-1);">已过期</a>
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
                                <table id="c2bMarrieddealBidId" style="width: 100%;height:60%;"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
<!-- 报价信息quotedPriceId -->
	<div class="modal fade" id="quotedPriceId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="col-sm-12">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">报价</h4>
				</div>
				<section class="panel">
			<header class="panel-heading" >

			<div class="form-group col-ms-12">
					<!-- 编辑页面开始 -->
					<input type="hidden" id="ppType" name="ppType" value=""/>
					<input type="hidden" id="pushMemnerPk" name="pushMemnerPk" value=""/>
					<input type="hidden" id="pushMemnerName" name="pushMemnerName" value=""/> 
					<input type="hidden" id="pushSupplierPk" name="pushSupplierPk" value=""/> 
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;text-align: left;">
							<h4 class="modal-title" id="noAuditModalLabelQuoted">需求信息:</h4>
					</div>
						
						<div class="form-group col-ms-12"
							style="height: 70px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">需求单号:</label>
							<div class="col-sm-2"  style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control " id="bidRecordPk" name="bidRecordPk"  required="true" />
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">商品信息:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="bidRecordProductInfo" name="bidRecordProductInfo"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">求购重量:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="bidRecordBuyCounts"  name="bidRecordBuyCounts"  required="true"/>
							</div>
							<!-- <label for="inputEmail3" class="col-sm-1 control-label"
								style="text-align: right;">用途:</label>
							<div class="col-sm-1">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="quotedPrice_use"   required="true"/>
							</div> -->
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;margin-top:10px;">备注:</label>
							<div class="col-sm-2" style="margin-top:5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="bidRecordRemarks" name="bidRecordRemarks"   required="true"/>
							</div>
						</div>
					</div>
					<div class="form-group col-ms-12"
							style="height: 30px; display: block; text-align: left;">
							<h4 class="modal-title" id="noAuditModalLabelQuoted">供应商信息:</h4>
					</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">供应商名称:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="bidRecordSupplierName" name="bidRecordSupplierName"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">联系人:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text"  readonly="readonly" style="border-style:none;" class="form-control" id="bidRecordContacts"  name="bidRecordContacts"  required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">手机号码:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="bidRecordContactsTel"  name="bidRecordContactsTel"  required="true"/>
							</div>
						</div>
					
					<div class="modal-footer">
						<form id="dataForm">
						<div class="form-group col-ms-12" id="select_bid_condition"
							style="height: 30px; display: block;">
							<input type="hidden" id="companyPk" name="companyPk" />
						<label class="col-sm-6 col-md-4 col-lg-3"><span>品名：</span>
						<select name="productPk"  class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${productList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
						</label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>品种：</span>
							 <select name="varietiesPk"    class="selectpicker bla bla bli" data-live-search="true">
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>
						</label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>规格：</span>
							<select name="specPk" id="specPk" class="selectpicker bla bla bli"   data-live-search="true" onchange="AddQuotedPriceChangeSpec()">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${specList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
			   			</label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>规格系列：</span>
							<select id="seriesPk" name="seriesPk"  class="selectpicker bla bla bli"  data-live-search="true" >
					          	 	<option value="">--请选择--</option>
			   				</select>
			   			</label>
			   			<label class="col-sm-6 col-md-4 col-lg-3"><span>批号：</span> <input class="form-controlgoods input-append " id="batchNumber" type="text" name=batchNumber  value="" /></label>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
						<button type="button" class="btn btn-primary" onclick="searchGoodsTabs(1);">搜索</button>
						<button type="button" class="btn btn-default" onclick="searchGoodsTabs(2);">清除</button>
				</div>
					</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="searchQuotedStatus(-2);">全部</a>
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
                                <table id="c2bGoodsBidId" style="width: 100%;height:60%;"></table>
                            </div>
                        </div>
                    </section>
                    <input type="hidden" id="bpk"/>
                                                            您的报价:<input type="text" id="bidPrice" maxlength="8"/>元/吨
                     &nbsp;&nbsp;&nbsp;&nbsp;<input type="hidden" id="boxesId"/>
                                                            箱数:<input type="text" id="boxesNum" maxlength="5" value="0"/>箱                                       
                    <button type="button" class="btn btn-primary" onclick="addMarriedBid();">报价</button>
				</div>
				</section>
			</div>
	</div>	
		
<!-- 跟踪记录信息supplierTrackRecordId -->
	<div class="modal fade" id="bidTrackRecordId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="col-sm-12">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">跟踪记录</h4>
				</div>
			<form id="dataForm">
				<section class="panel">
					<header class="panel-heading" > 
						<div class="modal-body">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="trackPk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">需求单号:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control " id="track_marrieddealBidId" required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">商品信息:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_productInfoBid"   required="true"/>
							</div>
							<!-- <label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">用途:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_use"   required="true"/>
							</div> -->
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">需求重量:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_countBid"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">发布时间:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_insertTimeBid"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">备注:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_remarksBid"   required="true"/>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">采购商名称:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_purchaserNameBid"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">联系人:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_purchaserContactsBid"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">手机号码:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_purchaserTelBid"   required="true"/>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">供应商名称:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_supplierNameBid"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">联系人:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_supplierContactsBid"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">手机号码:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_supplierTelBid"   required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">报价(元/吨):</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_bidPriceBid"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">报价时间:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_bidTimeBid"   required="true"/>
							</div>
						</div>
						
				</div>
				<div class="modal-footer">
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">拜访内容:</label>
							<div class="col-sm-6">
								<textarea class="form-control" id="track_remarkBid" style="width:560px;"></textarea>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">采购商确认:</label>
							
							<div class="col-sm-2">
								<label class="radio-inline">
							        <input type="radio"  name="track_isConfirmed" value="1"> 确认
							    </label>
							    <label class="radio-inline">
							        <input type="radio" name="track_isConfirmed"  value="0"> 未确认
							    </label>
							</div>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitBidTrack();">保存</button>
				</div>
					</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="searchQuotedStatus(-1);">全部</a>
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
                                <table id="c2bMarrieddealBidTrackId" style="width: 100%;height:60%;"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
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
   <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>