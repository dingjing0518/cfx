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
<script src="./pageJs/marketing/c2b/c2bMarrieddealBid.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">采购需求</a></li>
			<li class="active">报价管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" id="headone"> 
						<label class="col-sm-6 col-md-4 col-lg-3"><span>需求单号：</span> <input class="form-controlgoods input-append " type="text" name="bpk"   value="" onkeydown="entersearch()"/></label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>供应商名称：</span> <input class="form-controlgoods input-append " type="text" name="supplierName"   value="" onkeydown="entersearch()"/></label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>联系电话：</span> <input class="form-controlgoods input-append " type="text" name="bcontactsTel"   value="" onkeydown="entersearch()"/></label>
						
						<label class="col-sm-6 col-md-4 col-lg-3"><span>报价时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimesStart"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimesEnd"   value="" readonly /></label>
						
						<!-- <label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;状态：</span>
							<select name="isVisable" class="selectpicker bla bla bli">
								<option value="">--请选择--</option>
								<option value="1">启用</option>
								<option value="2">禁用</option>
							</select> 
						</label> -->
					<button class="btn btn-primary" id="btn1" onclick="searchTabs(1,this);">
						搜索</button>
					<button class="btn btn-primary" id="btn" onclick="searchTabs(2,this);">
						清除
						</button> 
					<button id="editable-sample_new" style="display:none;" showname="BTN_EXPORTQUOTE" class="btn btn-primary"
									data-toggle="modal" onclick="javascript:exportsBid();">
									导出
								</button>		
						<input type="hidden" id="bidStatus" name="bidStatus" value=""/>
					</header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="searchQuotedStatus(-2);">全部</a>
                                </li>
                                  <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="searchQuotedStatus(0);">未报价</a>
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
                                <table id="c2bMarrieddealId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
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
					<!-- <input type="hidden" id="pk" name="pk" value=""/> -->
					<div class="form-group col-ms-12"
							style="height: 30px; display: block;text-align: left;">
							<h4 class="modal-title" id="noAuditModalLabelQuoted">需求信息:</h4>
					</div>
						
						<div class="form-group col-ms-12"
							style="height: 70px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">需求单号:</label>
							<div class="col-sm-2"  style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control " id="pk" name="pk"  required="true" />
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">商品信息:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="productInfo" name="productInfo"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">求购重量:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="buyCounts"  name="buyCounts"  required="true"/>
							</div>
							<!-- <label for="inputEmail3" class="col-sm-1 control-label"
								style="text-align: right;">用途:</label>
							<div class="col-sm-1">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="quotedPrice_use"   required="true"/>
							</div> -->
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;margin-top:10px;">备注:</label>
							<div class="col-sm-2" style="margin-top:5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="remarks" name="remarks"   required="true"/>
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
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="supplierName" name="supplierName"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">联系人:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text"  readonly="readonly" style="border-style:none;" class="form-control" id="contacts"  name="contacts"  required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">手机号码:</label>
							<div class="col-sm-2" style="margin-top:-5px;">
								<input type="text" readonly="readonly" style="border-style:none;" class="form-control" id="contactsTel"  name="contactsTel"  required="true"/>
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
							<select name="specPk"  class="selectpicker bla bla bli"   data-live-search="true" onchange="AddChangeSpec()">
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
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control " id="track_marrieddealId" required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">商品信息:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_productInfo"   required="true"/>
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
								style="text-align: right;">需求重量(吨):</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_count"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">发布时间:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_insertTime"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">备注:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_remarks"   required="true"/>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">采购商名称:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_purchaserName"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">联系人:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_purchaserContacts"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">手机号码:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_purchaserTel"   required="true"/>
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">供应商名称:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_supplierName"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">联系人:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_supplierContacts"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">手机号码:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_supplierTel"   required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">报价(元/吨):</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly" style="border-style:none" class="form-control" id="track_bidPrice"   required="true"/>
							</div>
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">报价时间:</label>
							<div class="col-sm-2">
								<input type="text" readonly="readonly"  style="border-style:none" class="form-control" id="track_bidTime"   required="true"/>
							</div>
						</div>
						
				</div>
				<div class="modal-footer">
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: right;">拜访内容:</label>
							<div class="col-sm-6">
								<textarea class="form-control" id="track_remark" style="width:560px;"></textarea>
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
                                <table id="c2bMarrieddealTrackId" style="width: 100%;height:60%;"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
</body>
</html>
<script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       { 
	    	var dom = document.getElementById("btn1");
	    	searchTabs(1,dom);  
	       }  
	} 
</script>
<script src="./pageJs/include/form-dateday.js"></script>