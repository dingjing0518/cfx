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

<script src="./pageJs/economics/approveHistory.js"></script>
<style>
.modal-backdrop.in {
	z-index: 100;
}

.modal {
	z-index: 1000;
}

.bootstrap-select>.dropdown-toggle {
	float: none;
}
#addnew .col-ms-12 {
    margin-left: 5px;
}
#editHtml{
*zoom:1;
}
#editHtml:after{
content:"";
clear:both;
display:table;
}
#editHtml .col-ms-12{
*zoom:1;
height:auto!important;
}
#editHtml .col-ms-12:after{
content:"";
clear:both;
display:table;
}
#editHtml label.col-sm-2:last-child {
    width: 180px;
    text-align:left!important;
}
#verify06 label.control-label.col-sm-1 {
    width: 100px;
}
@media (max-width:1600px){
	#editHtml label.col-sm-3{
		padding-left:10px;
		padding-right:10px;
		text-align:left!important;
	}
}
@media (max-width:1520px){
	#editHtml label.col-sm-3{
    width: 234px;
	}
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>金融中心/</li>
			<li class="active">申请记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper" style="background:#fff;">
		<div class="row">
			<div class="col-sm-12">
				
				<section class="panel tableleft" style="float:left;width:38%;">
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="tab-pane active" id="all"></div>
							<div class="tab-pane" id="verify"></div>
							<div class="tab-pane" id="verifydone"></div>
							<div class="tab-pane" id="unverify"></div>
							<table id="approveId" style="width: 100%"></table>
						</div>
					</div>
				</section>
                <section class="panelright" style="width: 61%;display: inline-block;margin-left:1%;">
                
                
                 <input type="hidden" id="econCustmerPk" value=""/>
                  <input type="hidden" id="datumType" value="1"/><!-- 完善资料类型 -->
						<!-- 固定三行 -->
						<input type="hidden" id="companyPk" value="${companyPk }"/>
						<input type="hidden" id="econCustmerPk" value="${economicsCustomer.pk }"/>
						 <input type="hidden" id="datumType" value="1"/><!-- 完善资料类型 -->
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">公司名称：</label>
								<label class="control-label" id="companyName"></label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">联&nbsp;系&nbsp;人&nbsp;&nbsp;：</label>
								<label class=" control-label" id="contacts"> </label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">手机号码：</label>
								<label class=" control-label" id="contactsTel"></label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
						 	<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">申请产品：</label>
								<label class=" control-label" id="productName"></label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;padding-right:0px;">申请时间：</label>
								<label class=" control-label" id="insertTime"></label>
							</div>
						</div>
						<!-- 
						 <div class="col-sm-12" style="height: 33px;margin-left: 43px;  display: block;" id = "productNew">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">审批产品：	
							<label class="control-label" id="productName_1">1111</label></label>
						
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">产品金额：
							<label class="control-label" id="limit_1">222</label></label>
							
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">有效时间：
							<label class="control-label" id="effectStartTime_1">22222</label></label>
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">至
							<label class="control-label" id="effectEndTime_1">wwwwwww</label></label>
						 -->
							<div id ="editHtml"></div>
					
                 	 <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all06" data-toggle="tab" ">企业经营资料</a>
                                </li>
                                <li class="">
                                    <a href="#verify06" data-toggle="tab" >线下交易管理</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone06" data-toggle="tab" >授信管理</a>
                                </li>
                                <li class="">
                                    <a href="#unverify06" data-toggle="tab">担保管理</a>
                                </li>
                                <li class="">
                                    <a href="#information06" data-toggle="tab">补充资料</a>
                                </li>
                            </ul>
                        </header>             
                         <form id="approveForm" style="margin-top:15px;">
                          <div class="panel-bodycommon">                       
                            <div class="tab-content">
                            <!-- 11 -->
                                <div class="tab-pane active" id="all06">
                                <form style="margin-top:15px;line-height:34px;" id="operationDatum">
                                 <input type="hidden" id="datumType" value="1"/><!-- 完善资料类型 -->
                                   <div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">企业类型</label> 
										<label class="control-label col-sm-10">	
											<label class="control-label">生产型<input name="productionType" type="radio" value="1" checked="checked" style="margin:0 0 0 3px;" disabled="disabled"></label>
											<label class="control-label">贸易型<input name="productionType" type="radio" value="2" style="margin:0 0 0 3px;" disabled="disabled"></label>
									    </label>
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">产能</label>
										<label class="control-label col-sm-10" style="width:300px;">
										  <input class="form-control col-sm-3" id="capacityId" name="capacityId" required="true"  readonly></input>
										</label>
									</div>
									<div class="col-ms-12" style="line-height: 40px; display: block;height:60px;position:relative">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;margin-right:5px;">设备概况</label>
										<label class="control-label col-sm-2" style="width:120px;">
										  <select class="form-control col-sm-3" name="deviceTypeId" id="deviceTypeId_1" required="true" disabled="disabled">
										  <option value="">--请选择--</option>
										  </select>
										</label>
										<label class="control-label col-sm-2" style="width:120px;">
										  <select class="form-control col-sm-3" name="useTypeId" id="useTypeId_1" required="true" disabled="disabled" >
										  <option value="">--请选择--</option>
										  </select>
										</label>
										<label class="control-label col-sm-2" style="width:120px;">
											<input type="text" class="form-control" name="machineNumber" id="machineNumber_1" required="true" readonly>
										</label>
										<label class="control-label" style="float:left;">
											台，已用
										</label>
										<label class="control-label col-sm-2" style="width:120px;">
											<input type="text" class="form-control" name="machineYear"  id="machineYear_1" required="true" readonly>
										</label>
										<label class="control-label" style="float:left;">
											年
										</label>
 									</div>
									<div  id="addnew" >
									
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">

											<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">经营场所权属:</label>
											<label class="control-label col-sm-5">
											  <select class="form-control col-sm-3" id="companyPlaceId" name="companyPlaceId" required="true" style="width:300px;" disabled="disabled">
											  <option value="">--请选择--</option>
											  </select>
											</label>
											
									</div>
									<div class="col-ms-12" style="height:60px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料文档</label>
											<div class="form-group col-ms-12"
												style="height: 80px; width: 100px; display: block; width: 100%">
											<label for="inputEmail3" class="col-sm-6 control-label" style="text-align: right; width: auto;    margin-top: 2px;" id="qiyejingyingziliao" ></label>
								           	<label style="margin-top:2px;"><a for="inputEmail3" class="control-label" id = "down1" onclick = "op('qiyejingyingziliao')">下载</a></label>
											</div>
									</div>
					                <div class="col-ms-12" style="display: block;height:120px;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料说明</label>
										<label class="col-sm-10 control-label">
											<textarea style="resize: none;height:100px;width:600px;"width:600px;" class="form-control" id="remarks6" name="remarks6" contenteditable="plaintext-only" readonly></textarea>
										</label>
									</div>
									
								</form>
                                </div>
                                
                                <!-- 22 -->
                                <div class="tab-pane" id="verify06">
                                <form style="margin-top:15px;line-height:34px;position:relative">
									<div class="col-ms-12" style="display: block;height:40px;position:relative;" >
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">年份:</label>
										<label class="control-label col-sm-1">
										  <input class="form-control col-sm-3 form-dateyear" id="tradeYear_1" name="tradeYear" required="true" readonly="readonly"></input>
										</label>
										<label class="control-label" style="float:left;">
											销售规模:
										</label>
										<label class="control-label col-sm-2" style="width:80px;">
										<input class="form-control col-sm-3" id="salesVolume_1" name="salesVolume" required="true" readonly="readonly"></input>
										</label>
										<label class="control-label" style="float:left;">
											开票总额:
										</label>
										<label class="control-label col-sm-2" style="width:80px;">
										<input class="form-control col-sm-3" id="invoiceAmount_1" name="invoiceAmount" required="true" readonly="readonly"></input>
										<div style=" float: right;margin-top: -35px;margin-right: -10px;">w</div>
										</label>
										</label>
										<label class="control-label" style="float:left;">
											销售总额:
										</label>
										<label class="control-label col-sm-2" style="width:80px;">
										<input class="form-control col-sm-3" id="salesAmount_1" name="salesAmount" required="true" readonly="readonly">
										<div style=" float: right;margin-top: -35px;margin-right: -10px;">w</div>
										</label>
									</div>
									<div id="addnew2">  
									</div>
								<div class="col-ms-12" style="height:60px; display: block;">
									<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料文档：</label>
									<div class="form-group col-ms-12" style="height:8/0px;width:100px; display: block;width:100%">
									<label for="inputEmail3" class="col-sm-1 control-label" style="text-align: right; width: auto;" id="xianxiajiaoyi" ></label>
								    <a for="inputEmail3" class="control-label" id = "down2" onclick = "op('xianxiajiaoyi')" style="display:inline-block;line-height:34px;">下载</a>
									</div>
									
					            </div>
									<div class="col-ms-12" style="display: block;height:120px;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料说明：</label>
										<label class="col-sm-10 control-label">
											<textarea style="resize: none;height:100px;width:600px;"" class="form-control" id="remarks1" contenteditable="plaintext-only" readonly></textarea>
										</label>
									</div>
								</form>
                                </div>
                          <!--      33 -->
                                <div class="tab-pane" id="verifydone06">
                                  <form style="margin-top:15px;line-height:34px;">
                                  <div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">年份:</label>
										<label class="control-label col-sm-10">
										  <input class="form-control form-dateyear col-sm-3" id="creditYear" name="creditYear" required="true" style="width:150px;" disabled="disabled"></input>
										</label>
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">授信总金额:</label>
										<label class="control-label col-sm-10">
										  <input style="width:150px;" class="form-control col-sm-3" id="creditAmount" name="creditAmount" required="true" onblur="creditAmountChange()" readonly="readonly"></input>
										<label for="inputEmail3" class="col-sm-2 control-label" style="width:auto;">w</label>
										
										</label>
										
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">授信机构数:</label>
										<label class="control-label col-sm-10">
										<input style="width:150px;" class="form-control col-sm-3" id="creditNumber" name="creditNumber" required="true" readonly="readonly"></input>
										 
										</label>
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;line-height:14px;">国有商业银行授信金额:</label>
										<label class="control-label col-sm-10">
										  <input style="width:150px;" class="form-control col-sm-3" id="bankNumber" name="bankNumber" required="true" readonly="readonly"></input>
										 <label for="inputEmail3" class="col-sm-2 control-label" style="width:auto;">w</label>
										 
										</label>
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;line-height:14px;">国有商业银行授信数:</label>
										<label class="control-label col-sm-10">
										  <input style="width:150px;" class="form-control col-sm-3" id="businessBankNumber" name="businessBankNumber" required="true" readonly="readonly"></input>
										
										</label>
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">房地产抵押金额:</label>
										<label class="control-label col-sm-10">
										  <input style="width:150px;" class="form-control col-sm-3" id="buildingAmount" name="buildingAmount" required="true" onblur="creditAmountChange()" readonly="readonly"></input>
										<label for="inputEmail3" class="col-sm-2 control-label" style="width:auto;">w</label>
									
										</label>
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;line-height:14px;">房地产抵押金额/授信总金额(%):</label>
										<label class="control-label col-sm-10">
										  <input style="width:150px;" class="form-control col-sm-3" id="buildingCreditAmount" name="buildingCreditAmount" required="true" readonly="readonly"></input>
										
										</label>
									</div>
					                
									<div class="col-ms-12" style="height:65px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料文档:</label>
										   <div class="form-group col-ms-12" style="height:8/0px;width:100px; display: block;width:100%">
											<label for="inputEmail3" class="col-sm-1 control-label" style="text-align: right; width: auto;" id="shouxinguanli" ></label>
							           		<a for="inputEmail3" class="control-label" id = "down3" onclick = "op('shouxinguanli')" style="display:inline-block;line-height:34px;">下载</a>
										</div>
									</div>
									
									
									<div class="col-ms-12" style="display: block;height:120px;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料说明：</label>
										<label class="col-sm-10 control-label">
											<textarea style="resize: none;height:100px;width:600px;"" class="form-control" id="remarks2" contenteditable="plaintext-only" readonly="readonly"></textarea>
										</label>
									</div>
									<div class="col-ms-12" style="display: block;text-align:center;margin-bottom:30px;height:40px;line-height:40px;">
									</div>
								  </form>
                                </div>
                                
                                <!-- 44 -->
                                <div class="tab-pane" id="unverify06">
                                <form style="margin-top:15px;line-height:34px;">
								<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">年份:</label>
										<label class="control-label col-sm-10">
										  <input style="width:150px;" class="form-control form-dateyear col-sm-3" id="warrantYear" name="warrantYear"required="true"  disabled="disabled"></input>
										</label>
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">合作对象:</label>
										<label style="width:180px;" class="control-label col-sm-10">
										  <select class="form-control col-sm-3" id="cooperativePartner" name="cooperativePartner" required="true" disabled="disabled">
										  <option value="">--请选择--</option>
										  </select>
										 
										</label>
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">担保户数:</label>
										<label style="width:180px;" class="control-label col-sm-10">
										<input class="form-control col-sm-3" id="warrantNumber" name="warrantNumber"required="true" readonly></input>
										</label>
										
									</div>
									<div class="col-ms-12" style="height: 60px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">担保总金额:</label>
										<label class="control-label col-sm-10" style="width:180px;">
											<input style="width:150px;" type="text" class="form-control" id="warrantAllAmount" name="warrantAllAmount" required="true" onblur="changeWarrantChange()" readonly>
										</label>w
									</div>
									<div class="col-ms-12" style="height: 60px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;line-height:14px;">纳税销售收入:</label>
										<label class="control-label col-sm-10" style="width:180px;">
											<input style="width:150px;" type="text" class="form-control" id="warrantInvAmount" name="warrantInvAmount" required="true" onblur="changeWarrantChange()" readonly>
										</label>w
									</div>
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;line-height: 14px;">担保总金额/纳税销售收入比(%):</label>
										<label class="control-label col-sm-10">
										<input style="width:150px;" class="form-control col-sm-3" id="warrantInvoiceRatio" name="warrantInvoiceRatio"required="true" readonly="readonly"></input>
										</label>
									</div>
									
									<div class="col-ms-12" style="height: 40px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">担保分类:</label> 
										<label class="control-label col-sm-10" style="width:165px;">	
											<label class="control-label">
											<input name="warrantType" type="radio" value="1" checked="checked" style="margin:0 0 0 3px;" readonly>关注类
											</label>	
										</label>
									</div>
					                
					               
									
									<div class="col-ms-12" style="height:60px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;" >资料文档:</label>
										
										<div class="form-group col-ms-12" style="height:8/0px;width:100px; display: block;width:100%">
										<label for="inputEmail3" class="col-sm-1 control-label" style="text-align: right; width: auto;" id="danbaoguanli" ></label>
								           	<a for="inputEmail3" class="col-sm-6 control-label" id = "down4" onclick = "op('danbaoguanli')" style="display:inline-block;width:29px;padding:0px;line-height:34px;">下载</a>
										</div>
									</div>
									<div class="col-ms-12" style="display: block;height:120px;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料说明:</label>
										<label class="col-sm-10 control-label">
											<textarea style="resize: none;height:100px;width:600px;"" class="form-control" id="remarks3" contenteditable="plaintext-only" readonly></textarea>
										</label>
									</div>
									<div class="col-ms-12" style="display: block;text-align:center;margin-bottom:30px;height:40px;line-height:40px;">
									</div>

								  </form>
                                </div>                                
                                <!-- 55 -->
                                 <div class="tab-pane" id="information06">
                                <form style="margin-top:15px;line-height:34px;">
									<div class="col-ms-12" style="height:60px; display: block;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料文档:</label>
										  
										  <div class="form-group col-ms-12" style="height:8/0px;width:100px; display: block;width:100%">
										<label for="inputEmail3" class="col-sm-1 control-label" style="text-align: right; width: auto;" id="buchongziliao" ></label>
								           	<a for="inputEmail3" class="control-label" id = "down5" onclick = "op('buchongziliao')" style="display:inline-block;line-height:34px;">下载</a>
										</div>
										
									</div>
									
									<div class="col-ms-12" style="display: block;height:120px;">
										<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料说明:</label>
										<label class="col-sm-10 control-label">
											<textarea style="resize: none;height:100px;width:600px;" class="form-control" id="remarks4" contenteditable="plaintext-only" readonly></textarea>
										</label>
									</div>
									<div class="col-ms-12" style="display: block;text-align:center;margin-bottom:30px;height:40px;line-height:40px;">
									</div>
								  </form>
                                </div>
                                
                                
                                
                            </div>
                        </div>
                        </form>
                </section>
			</div>
		</div>
	</div>
	<script>
		function entersearch() {
			var event = window.event || arguments.callee.caller.arguments[0];
			if (event.keyCode == 13) {
				SearchClean();
			}
		}
	</script>
	<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>