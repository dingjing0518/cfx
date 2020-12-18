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
<link href="././style/css/approval.css" rel="stylesheet" type="text/css" />
<script src="./pageJs/economics/economCustAuditDirector.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">审批管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<div class="panel-body">
						<section class="panel">
							<div class="companyinfo">
								<div class="title-top">
									<h3>公司信息</h3>
								</div>
								<input type="hidden" id="econCustmerPk" value="${economicsCustDto.pk }"/>
								<input type="hidden" id="taskId" value="${taskId}"/>
								<input type="hidden" id="processInstanceId" value="${economicsCustDto.processInstanceId}"/>
								<input type="hidden"  id="companyPk" value="${economicsCustDto.companyPk}" />
								<div class="companytable">
									<p>公司名称：${economicsCustDto.companyName }</p>
									<p>联系人：${economicsCustDto.contacts }</p>
									<p>手机号码:${economicsCustDto.contactsTel }</p>
								</div>
							</div>
							<div class="shenpimessage">
								<div class="title-top">
									<h3>审批信息及批注</h3>
								</div>
								<ul>
									<li style="width:auto;"><strong>申请产品：</strong>${economicsCustDto.goodsName }</li>
									<li><span>申请时间：</span><fmt:formatDate value="${economicsCustDto.insertTime }" pattern="yyyy-MM-dd HH:mm:ss"/></li>
									<div class="clearfix"></div>
								</ul>
								<c:forEach items="${economicsCustDto.goodsList}" var="dimen" > 
								<ul>
									<c:if test="${dimen.goodsType == 2||dimen.goodsType == 4}"><!-- 化纤贷含七天利率 -->
										<input type="hidden" name="econGoodsPk_d" value="${dimen.pk}" />
										<input type="hidden" name="productName_d" value="${dimen.economicsGoodsName}" />
										<li style="height:100px"><strong>审批产品：</strong>${dimen.economicsGoodsName}</li>
										<li class="width18"><span>建议额度：</span><input type="text"  onkeyup="format_input_num(this)"   maxlength="15" name="limit_d" required="true"  value= "<fmt:formatNumber value="${dimen.suggestAmount}" pattern="##.##"/>" />&nbsp;&nbsp;W</li>
										<li class="width25">
											<span>有效时间：</span>
											<input class="form-controlgoods form-dateday responsive" type="text" name="effectStartTime_d"  value="<fmt:formatDate value='${dimen.effectStartTime}' pattern='yyyy-MM-dd'/>"  />
											<span style="width:auto;">至</span>
											<input class="form-controlgoods form-dateday responsive" type="text"  name="effectEndTime_d"  value="<fmt:formatDate value='${dimen.effectEndTime}' pattern='yyyy-MM-dd'/>"  />
										</li>
										<li class="width25"><span>总利率：</span><input type="text"   maxlength="10" name="totalRate_d"   value= "<fmt:formatNumber value="${dimen.totalRate}" pattern="##.###"/>"  required="true"  />&nbsp;&nbsp;</li>
										<br/>
										<li class="width18"><span>银行利率：</span><input type="text"   maxlength="10" name="bankRate_d" value= "<fmt:formatNumber value="${dimen.bankRate}" pattern="##.###"/>"  required="true"  />&nbsp;&nbsp;</li>
										<li class="width25"><span>期限：</span><input type="text" onkeyup="format_input_num(this)"    maxlength="10" name="term_d" value= "<fmt:formatNumber value="${dimen.term}" pattern="##"/>" required="true"  />&nbsp;&nbsp;</li>
		     							<li><span>七天利率：</span><input type="text"   maxlength="10" name="sevenRate"  value= "<fmt:formatNumber value="${dimen.sevenRate}" pattern="##.###"/>" required="true"  />&nbsp;&nbsp;</li>
									</c:if>
									<c:if test="${dimen.goodsType == 1||dimen.goodsType == 3}">
										<input type="hidden" name="econGoodsPk_bt" value="${dimen.pk}" />
										<input type="hidden" name="productName_bt" value="${dimen.economicsGoodsName}" />
										<li style="height:100px;"><strong>审批产品：</strong>${dimen.economicsGoodsName}</li>
										<li class="width18"><span>建议额度：</span><input type="text"  onkeyup="format_input_num(this)"   maxlength="15" name="limit_bt" required="true"  value= "<fmt:formatNumber value="${dimen.suggestAmount}" pattern="##.##"/>" />&nbsp;&nbsp;W</li>
										<li class="width25">
											<span>有效时间：</span>
											<input class="form-controlgoods form-dateday responsive" type="text" name="effectStartTime_bt"  value="<fmt:formatDate value='${dimen.effectStartTime}' pattern='yyyy-MM-dd'/>"  />
											<span style="width:auto;">至</span>
											<input class="form-controlgoods form-dateday responsive" type="text"  name="effectEndTime_bt"  value="<fmt:formatDate value='${dimen.effectEndTime}' pattern='yyyy-MM-dd'/>"  />
										</li>
										<li class="width25"><span>总利率：</span><input type="text"   maxlength="10" name="totalRate_bt"   value= "<fmt:formatNumber value="${dimen.totalRate}" pattern="##.###"/>"  required="true"  />&nbsp;&nbsp;</li>
										<br/>
										<li class="width18"><span>银行利率：</span><input type="text"   maxlength="10" name="bankRate_bt" value= "<fmt:formatNumber value="${dimen.bankRate}" pattern="##.###"/>"  required="true"  />&nbsp;&nbsp;</li>
										<li class="width25"><span>期限：</span><input type="text" onkeyup="format_input_num(this)"    maxlength="10" name="term_bt" value= "<fmt:formatNumber value="${dimen.term}" pattern="##"/>" required="true"  />&nbsp;&nbsp;</li>
									</c:if>
									
									<li class="suyh">
										<span style="width:auto;">所属银行：${dimen.bankName}</span>
									</li>
									<div class="clearfix"></div>
									</ul>
								 </c:forEach> 
								 
								<ul>
									<li style="width:auto;">
										<span>审批意见：</span>
										<textarea style="resize: none;height:100px;" class="form-control"   maxlength="40"  id="remarks" name="remarks" ></textarea>
									</li>
									<div class="clearfix"></div>
								</ul>
								<div class="button">
									<button class="save-button" onclick="submitAudit(1);" >通过</button>
									<button class="cancel-button" onclick="submitAudit(2);">驳回</button>
								</div>
							</div>
							<div class="approval-history">
								<div class="title-top">
								 	<h3>审批历史</h3>
								</div>
								 <div class="approvaltable">
										<div class="tab-content">
											<table id="approveId" style="width: 100%"></table>
										</div>
									</table>
								</div> 
								
							</div>
							<div class="dataview">
								<div class="title-top">
									<h3>资料录入及查看历史</h3>
								</div>
								<div class="datainput">
									<div class="datainput-content">
										<ul>
											<li id = "li_0" class="active">
												<div class="step">
						                            <span>√</span>
						                            <div class="line"></div>
						                        </div>
												<p>金融专员完善资料</p>
												<span class="time" id = "time_0"></span>
											</li>
											<li id = "li_1">
												<div class="step">
						                            <span>√</span>
						                            <div class="line"></div>
						                        </div>
												<p>风控专员审批</p>
												<span class="time" id = "time_1"></span>
											</li>
											<li id = "li_2">
												<div class="step">
						                            <span>√</span>
						                            <div class="line"></div>
						                        </div>
												<p>金融总监审批</p>
												<span class="time" id = "time_2"></span>
											</li>
											<li id = "li_3">
												<div class="step">
						                            <span>√</span>
						                        </div>
						                        <p>总经理</p>
						                        <span class="time" id = "time_3"></span>
					                        </li>
										</ul>
									</div>
									<button class="closeshow">关闭显示</button>
								</div>
							</div>
							
									<div class="datalist datalistnew">
								<ul>
									<li><i>*</i>客户类型：</li>
									<li class="bordershow">
									 <c:if test="${improvingdata.customerType ==1}">
										       	  一般客户
								     </c:if>
								     <c:if test="${improvingdata.customerType ==2}">
								       	  重点客户
								     </c:if>
									</li>
									<li class="showright" >
										<span id ="customerType"></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>企业成立年限：</li>
									<li class="bordershow">
									${improvingdata.establishingTime}个月
										<span class="spanright">${improvingdata.establishingTimeScore}分</span>
									</li>
									<li class="showright">
										<span id ="establishingTime" ></span>
										<span class="spanright"id ="establishingTimeScore"></span>
									</li>
								</ul>
								<ul>
									<li>上一年度纳税销售金额：</li>
									<li class="bordershow">
										<fmt:formatNumber value="${improvingdata.lastTaxSales}" pattern="##.##"/>万元
										<span class="spanright">${improvingdata.taxSalesScore}分</span>
									</li>
									<li class="showright">
										<span id = "lastTaxSales"></span>
										<span class="spanright" id ="taxSalesScore"></span>
									</li>
								</ul>
								<ul>
									<li>关联企业上一自然年度纳税销售金额：</li>
									<li class="bordershow">
										<fmt:formatNumber value="${improvingdata.releLastTaxSales}" pattern="##.##"/>万元
									<!-- 	<span class="spanright">amountGuara分</span> -->
									</li>
									<li class="showright">
										<span id ="releLastTaxSales"></span>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								<ul>
									<li>当期企业对外担保余额：</li>
									<li class="bordershow">
										<fmt:formatNumber value="${improvingdata.amountGuara}" pattern="##.##"/>万元
										<!-- <span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<span id ="amountGuara"></span>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								<ul>
									<li>当期关联企业对外担保余额：</li>
									<li class="bordershow">
									<fmt:formatNumber value="${improvingdata.releAmountGuara}" pattern="##.##"/>万元
										<!-- <span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<span id ="releAmountGuara"></span>
										<!-- <span class="spanright">**分</span> -->
									</li class="bordershow">
								</ul>
								<ul>
									<li><i>*</i>企业担保余额与纳税销售比值：</li>
									<li class="bordershow">
										${improvingdata.guaraTaxSalesPer}%
										<span class="spanright">${improvingdata.guaraTaxSalesPerScore}分</span>
									</li>
									<li class="showright">
										<span id = "guaraTaxSalesPer"></span>
										<span class="spanright" id = "guaraTaxSalesPerScore"></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>关联企业对外担保与纳税销售比：</li>
									<li class="bordershow">
										${improvingdata.releGuaraTaxSalesPer}%
										<span class="spanright">${improvingdata.releGuaraTaxSalesPerScore}分</span>
									</li>
									<li class="showright">
										<span id ="releGuaraTaxSalesPer"></span>
										<span class="spanright" id ="releGuaraTaxSalesPerScore"></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>企业当期融资金额：</li>
									<li class="bordershow">
									<fmt:formatNumber value="${improvingdata.finanAmount}" pattern="##.##"/>万元
									</li>
									<li class="showright">
										<span id ="finanAmount"></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>关联企业当期融资金额：</li>
									<li class="bordershow">
										<fmt:formatNumber value="${improvingdata.releFinanAmount}" pattern="##.##"/>万元
										<!-- <span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<span id ="releFinanAmount"></span>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								
								
								<ul>
									<li><i>*</i>企业融资纳税销售比：</li>
									<li class="bordershow">
										${improvingdata.finanAmountTaxSales}%
										<span class="spanright">${improvingdata.finanAmountTaxSalesScore}分</span>
									</li>
									<li class="showright">
										<span id ="finanAmountTaxSales"></span>
										<span class="spanright" id = "finanAmountTaxSalesScore"></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>关联企业名下负债与纳税销售比：</li>
									<li class="bordershow">
										${improvingdata.releFinanAmountTaxSales}%
										<span class="spanright">${improvingdata.releFinanAmountTaxSalesScore}分</span>
									</li>
									<li class="showright">
										<span id ="releFinanAmountTaxSales"></span>
										<span class="spanright" id= "releFinanAmountTaxSalesScore"></span>
										
									</li>
								</ul>
								<ul>
									<li><i>*</i>合作金融机构数量：</li>
									<li class="bordershow">
										${improvingdata.finanInstitution}家
										<span class="spanright">${improvingdata.finanInstitutionScore}分</span>
									</li>
									<li class="showright">
										<span id ="finanInstitution"></span>
										<span class="spanright" id = "finanInstitutionScore"></span>
									</li>
								</ul>
								<ul>
									<li>
										<div class="step">参与评分的设备名称：</div>
										<div class="step">其他设备：</div>
									</li>
									<li class="bordershow">
										<div class="step">
								 			
											<span  id="deviceStatusHide" style = "display:none">${improvingdata.deviceStatus}</span>
											<span id = "deviceStatusHideSpan"></span>
										</div> 
										<div class="step">
										<span id="otherDeviceStatusHide" style = "display:none">${improvingdata.otherDeviceStatus}</span>
										<span id = "otherDeviceStatusHideSpan"></span>
										<%-- <c:forEach items="${improvingdata.otherDeviceStatus}" var="other" > 
												设备类型：${other.deviceType}；设备品牌：${other.deviceBrand}；设备数量${other.deviceNum}台
											</c:forEach> --%>
										</div>
										<span class="spanright">${improvingdata.deviceStatusScore}分</span>
									</li>
									<li class="showright">
										<div class="step">
										<span id = "deviceStatus">
										
										</span>
										</div>
										<div class="step">
										<span id = "otherDeviceStatus"></span>
										</div>
										<span class="spanright" id ="deviceStatusScore"></span>
									</li>
								</ul>
								<ul>
									<li>企业在平台上一年度累计交易金额：</li>
									<li class="bordershow">
								<fmt:formatNumber value="${improvingdata.transAmountOnline}" pattern="##.##"/>万元
									<span class="spanright">${improvingdata.transAmountOnlineScore}分</span>
									</li>
									<li class="showright"><span id = "transAmountOnline"></span>
									<span class="spanright" id ="transAmountOnlineScore" ></span>
									</li>
								</ul>
								<ul>
									<li>
										<div class="step">原料年采购额：</div>
									</li>
									<li class="bordershow">
										<span style = "display:none"  id = "rawPurchaseAmountHide">${improvingdata.rawPurchaseAmount}</span>
										<span  id = "rawPurchaseAmountHideSpan"></span>
										<!--<div class="step">
											2018年累计**万元
											
										</div>
										<div class="step">
											2018年累计**万元
										</div> -->
										<span class="spanright">${improvingdata.rawPurchaseAmountScore}分</span>
									</li>
									<li class="showright">
										<div class="step"><span id ="rawPurchaseAmount"></span></div>
										
										<span class="spanright" id ="rawPurchaseAmountScore"></span>
									</li>
								</ul>
								<ul>
									<li>企业原料采购额环比稳定性：</li>
									<li class="bordershow">${improvingdata.rawPurchaseIncrePer}%<span class="spanright">${improvingdata.rawPurchaseIncrePerScore}分</span></li>
									<li class="showright"><span  id = "rawPurchaseIncrePer"></span>
									<span class="spanright" id = "rawPurchaseIncrePerScore"></span></li>
								</ul>
								<ul>
									<li>原料线上采购额较总采购额占比：</li>
									<li class="bordershow">${improvingdata.transOnlineRawPurchPer}%<span class="spanright">${improvingdata.transOnlineRawPurchPerScore}分</span></li>
									<li class="showright" ><sapn id = "transOnlineRawPurchPer"></sapn>%
									<span class="spanright"  id = "transOnlineRawPurchPerScore"></span></li>
								</ul>
								<ul>
									<li>企业在平台上一年度交易频次：</li>
									<li class="bordershow">${improvingdata.useMonthsNum}个月<span class="spanright">${improvingdata.useMonthsNumScore}分</span></li>
									<li class="showright" ><span id = "useMonthsNum"></span ><span class="spanright" id = "useMonthsNumScore"></span></li>
								</ul>
								<ul>
									<li>企业在平台上一年度金融产品使用频次：</li>
									<li class="bordershow">${improvingdata.econUseMonthsNum}个月<span class="spanright">${improvingdata.econUseMonthsNumScore}分</span></li>
									<li class="showright" ><span id ="econUseMonthsNum"></span ><span class="spanright" id ="econUseMonthsNumScore"></span></li>
								</ul>
								<ul>
									<li>近12个月累计金融产品提用金额：</li>
									<li class="bordershow"><fmt:formatNumber value="${improvingdata.econUseMonthsAmount}" pattern="##.##"/>万元
									<span class="spanright">${improvingdata.econUseMonthsAmountScore}分</span></li>
									<li class="showright" ><span id ="econUseMonthsAmount"></span><span class="spanright" id ="econUseMonthsAmountScore"></span></li>
								</ul>
								<ul>
									<li>企业在平台上一年度金融产品还款逾期次数：</li>
									<li class="bordershow">${improvingdata.econOverdueNum}次<span class="spanright">${improvingdata.econOverdueNumScore}分</span></li>
									<li class="showright" ><span id ="econOverdueNum"></span ><span class="spanright" id ="econOverdueNumScore"></span></li>
								</ul>
								<ul>
									<li>银行近期年化结算流水：</li>
									<li class="bordershow">
										<fmt:formatNumber value="${improvingdata.bankAnnualizAmount}" pattern="##.##"/>万元
										<%-- <span class="spanright">${improvingdata.econOverdueNum}分</span> --%>
									</li>
									<li class="showright" ><span  id = "bankAnnualizAmount" ></span>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								<ul>
									<li><i>*</i>企业银行流水占销售比：</li>
									<li class="bordershow">
										${improvingdata.annualizTaxSalesPer}%
										<span class="spanright">${improvingdata.annualizTaxSalesPerScore}分</span>
									</li>
									<li class="showright" ><span id ="annualizTaxSalesPer"></span><span class="spanright" id ="annualizTaxSalesPerScore"></span></li>
								</ul>
								<ul>
									<li><i>*</i>企业近期纳税销售年化值：</li>
									<li class="bordershow">
									<fmt:formatNumber value="${improvingdata.annualizTaxAmount}" pattern="##.##"/>万元
										<span class="spanright">${improvingdata.annualizTaxAmountScore}分</span>
									</li>
									<li class="showright" ><span id ="annualizTaxAmount"></span><span class="spanright" id ="annualizTaxAmountScore"></span></li>
								</ul>
								<ul>
									<li><i>*</i>原料供应商名称：</li>
									<li class="bordershow">
										${improvingdata.rawBrand}<span class="spanright">${improvingdata.rawBrandScore}分</span>
									</li>
									<li class="showright" ><span id ="rawBrand"></span><span class="spanright" id ="rawBrandScore"></span></li>
								</ul>
								<ul>
									<li><i>*</i>原料近期采购年化值：</li>
									<li class="bordershow">
											<fmt:formatNumber value="${improvingdata.rawAnnualizAmount}" pattern="##.##"/>万元
										<span class="spanright">${improvingdata.rawAnnualizAmountScore}分</span>
									</li>
									<li class="showright" >
									<span id ="rawAnnualizAmount"></span>
									<span class="spanright" id ="rawAnnualizAmountScore"></span></li>
								</ul>
								<ul>
									<li><i>*</i>生产经营厂房权属性质及面积：</li>
									<li class="bordershow">
										<div class="step">
											<p>类型：自有</p>
											<span>面积数：</span>
											${improvingdata.workshopOwnSquare}&nbsp;&nbsp;平方米
											
										</div>
										<div class="step">
											<p>类型：租赁</p>
											<span>面积数：</span>
											${improvingdata.workshopRentSquare}&nbsp;&nbsp;平方米
										</div>
										<div class="step">
											<p>汇总面积：</p><span>${improvingdata.workshopAllSquare}m2
											</span>
										</div>
										<span class="spanright">${improvingdata.workshopSquareScore}分</span>
									</li>
									<li class="showright">
										<div class="step">
											<p>类型：自有</p>
											<span>面积数：</span>
											<span id ="workshopOwnSquare"></span>
										</div>
										<div class="step">
											<p>类型：租赁</p>
											<span>面积数：</span>
											<span id = "workshopRentSquare"></span>
										</div>
										<div class="step">
											<p>汇总面积:</p><span id ="workshopAllSquare" ></span>
										</div>
										<span class="spanright" id= "workshopSquareScore" ></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>企业生产流程：</li>
									<li class="bordershow">
										     <c:if test="${improvingdata.flowOfProduction ==1}">
										       	  一步
										     </c:if>
										     <c:if test="${improvingdata.flowOfProduction ==2}">
										       	  两步
										     </c:if>
										    <c:if test="${improvingdata.flowOfProduction ==3}">
										       	 三步及以上
										     </c:if>
										
									<span class="spanright">${improvingdata.flowOfProductionScore}分</span>
									</li>
									<li class="showright">
										<span id ="flowOfProduction" ></span>
										<span class="spanright" id = "flowOfProductionScore" ></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>企业商业经营模式：</li>
									<li class="bordershow">
									
										     <c:if test="${improvingdata.managementModel ==1}">
										       	代加工
										     </c:if>
										     <c:if test="${improvingdata.managementModel ==2}">
										       	自产自销
										     </c:if>
										    <c:if test="${improvingdata.managementModel ==3}">
										       	自产自销+外发加工
										     </c:if>
										      <c:if test="${improvingdata.managementModel ==4}">
										       	自产自销+代加工
										     </c:if>
										
										<span class="spanright">${improvingdata.managementModelScore}分</span>
									</li>
									<li class="showright" >
										<sapn id ="managementModel"></sapn>
										<span class="spanright" id ="managementModelScore"></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>企业在平台上一年度合作供应商数量：</li>
									<li class="bordershow">
										${improvingdata.businessShopNum}家<span class="spanright">${improvingdata.businessShopNumScore}分</span>
									</li>
									<li class="showright"><span id = "businessShopNum"></span>
									<span class="spanright" id = "businessShopNumScore"></span></li>
								</ul>
								<ul>
									<li><i>*</i>企业股权结构及高管稳定性：</li>
									<li class="bordershow">
										${improvingdata.shareChangeNum}次<span class="spanright">${improvingdata.shareChangeNumScore}分</span>
									</li>
									<li class="showright">
										<span id ="shareChangeNum"></span><span class="spanright" id ="shareChangeNumScore"></span>
									</li>
								</ul>
								<ul>
									<li><i>*</i>近三年行政处罚及被执行次数：</li>
									<li class="bordershow">
										${improvingdata.enforcedNum}次<span class="spanright">${improvingdata.enforcedNumScore}分</span>
									</li>
									<li class="showright">
										<span id ="enforcedNum"></span>
										<span class="spanright" id ="enforcedNumScore"></span>
									</li>
								</ul>
								<ul>
									<li>企业还款付息逾期次数：</li>
									<li class="bordershow">
										${improvingdata.zxEnforcedNum}次<span class="spanright">${improvingdata.zxEnforcedNumScore}分</span>
									</li>
									<li class="showright">
										<span id ="zxEnforcedNum"></span>
										<span class="spanright" id ="zxEnforcedNumScore"></span>
									</li>
								</ul>
								<ul>
									<li>企业与金融机构合作时间：</li>
									<li class="bordershow">
										${improvingdata.ecomCooperateTime}年<span class="spanright">${improvingdata.ecomCooperateTimeScore}分</span>
									</li>
									<li class="showright">
										<span id ="ecomCooperateTime"></span><span class="spanright" id= "ecomCooperateTimeScore"></span>
									</li> 
								</ul>
								<ul>
									<li>企业与供应商合作年限：</li>
									<li class="bordershow">
										${improvingdata.supplierCooperateTime}年
										<span class="spanright">${improvingdata.supplierCooperateTimeScore}分</span>
									</li>
									<li class="showright">
										<span id ="supplierCooperateTime"></span>
										<span class="spanright" id = "supplierCooperateTimeScore">分</span>
									</li>
								</ul>
								<ul>
									<li>主要采购原料纤度：</li>
									<li class="bordershow">
										 	<%-- <c:if test="${improvingdata.purcherVariety ==1}">
										       DPF≥0.5
										     </c:if>
									      	<c:if test="${improvingdata.purcherVariety ==2}">
									       		DPF＜0.5
										     </c:if> --%>
										     DPF = ${improvingdata.purcherVariety}
										<span class="spanright">${improvingdata.purcherVarietyScore}分</span>
									</li>
									<li class="showright">
										<span>DPF = <span id = "purcherVariety"></span></span>
										<span class="spanright" id ="purcherVarietyScore">
										
										</span>
									</li>
								</ul>
								<ul>
									<li>
										企业经营地点稳定性：</li>
									<li class="bordershow">
										${improvingdata.manageStable}年<span class="spanright">${improvingdata.manageStableScore}分</span>
									</li>
									<li class="showright">
										<span id ="manageStable"></span>
										<span class="spanright" id ="manageStableScore"></span>
									</li>
								</ul>
								<ul>
									<li>单月企业能耗：</li>
									<li class="bordershow">
										<span style = "display:none" id = "consumPerMonthHide">${improvingdata.consumPerMonth}</span>
										<span  id = "consumPerMonthHideSpan"></span>
										<!-- <div class="step">
											1月12000元
										</div> -->
										<!-- <span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<span  id = "consumPerMonth"></span>
									</li>
								</ul>
								<ul>
									<li>合计企业能耗：</li>
									<li class="bordershow">
										<fmt:formatNumber value="${improvingdata.consumAddup}" pattern="##.##"/>元
										<%-- <span class="spanright">${improvingdata.consumAddup}分</span> --%>
									</li>
									<li class="showright">
										<span id = "consumAddup"></span>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								<ul>
									<li>企业能耗平均值：</li>
									<li class="bordershow">
										<fmt:formatNumber value="${improvingdata.averageConsum}" pattern="##.##"/>元
									<!-- 	<span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<span id = "averageConsum"></span>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								<ul>
									<li>企业主要生产能耗稳定性情况：</li>
									<li class="bordershow">
										${improvingdata.consumStable}%<span class="spanright">${improvingdata.consumStableScore}分</span>
									</li>
									<li class="showright">
										<span id ="consumStable"></span>
										<span class="spanright" id ="consumStableScore"></span>
									</li>
								</ul>
								<ul>
									<li>行业平均能耗：</li>
									<li class="bordershow">
											<fmt:formatNumber value="${improvingdata.industryAverageConsum}" pattern="##.##"/>元
										<!-- <span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<span id ="industryAverageConsum" ></span>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								<ul>
									<li>企业主要能耗与行业平均能耗对比情况：</li>
									<li class="bordershow">
										${improvingdata.averageIndustryConsumPer}%
										<span class="spanright">${improvingdata.averageIndustryConsumPerScore}分</span>
									</li>
									<li class="showright">
										<span id ="averageIndustryConsumPer"></span>
										<span class="spanright" id ="averageIndustryConsumPerScore"></span>
									</li>
								</ul>
								<ul>
									<li>
										<div class="step">上游重合客户数：</div>
										<div class="step">上游客户重合基数：</div>
										<div class="step">上游客户稳定性：</div>
									</li>
									<li class="bordershow">
										<div class="step">
											${improvingdata.upstreamCustomerNum}家
										</div>
										<div class="step">
											${improvingdata.upstreamCustomerBaseNum}家
										</div>
										<div class="step">
											${improvingdata.upstreamCustomerStable}%
											<span class="spanright">${improvingdata.upstreamCustStableScore }分</span>
										</div>
									<!-- 	<span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<div class="step">
											<span id ="upstreamCustomerNum"></span>
										</div>
										<div class="step">
											<span id ="upstreamCustomerBaseNum"></span>
										</div>
										<div class="step">
											<span id="upstreamCustomerStable"></span>
												<span class="spanright" id ="upstreamCustStableScore" ></span>
										</div>
									<!-- 	 -->
									</li>
								</ul>
								<ul>
									<li>
										<div class="step">下游重合客户数：</div>
										<div class="step">下游客户重合基数：</div>
										<div class="step">下游客户稳定性：</div>
									</li>
									<li class="bordershow">
										<div class="step">
											${improvingdata.downstreamCustomerNum}家
										</div>
										<div class="step">
											${improvingdata.downstreamCustomerBaseNum}家
										</div>
										<div class="step">
											${improvingdata.downstreamCustomerStable}%
											<span class="spanright">${improvingdata.downstreamCustStableScore}分</span>
										</div>
									
									</li>
									<li class="showright">
										<div class="step">
											<span id ="downstreamCustomerNum"></span>
										</div>
										<div class="step">
											<span id ="downstreamCustomerBaseNum"></span>
										</div>
										<div class="step">
											<span id = "downstreamCustomerStable"></span>
												<span class="spanright" id ="downstreamCustStableScore" ></span>
										</div>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								<ul>
									<li>
										<div class="step">重点客户采购量合计：</div>
										<div class="step">企业去年年度进项税金额：</div>
										<div class="step">上游客户交易量稳定性：</div>
									</li>
									<li class="bordershow">
										<div class="step">
											<fmt:formatNumber value="${improvingdata.vipCustPurchaserAddUp}" pattern="##.##"/>万元
										</div>
										<div class="step">
										<fmt:formatNumber value="${improvingdata.inputTaxAmount}" pattern="##.##"/>万元
										</div>
										<div class="step">
											${improvingdata.upstreamVipCustStable}%
											 <span class="spanright">${improvingdata.upstreamVipCustStableScore}分</span>
										</div>
										<!-- <span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<div class="step">
											<span id ="vipCustPurchaserAddUp"></span>
										</div>
										<div class="step">
											<span id = "inputTaxAmount"></span>
										</div>
										<div class="step">
											<span id ="upstreamVipCustStable"></span>
											<span class="spanright" id ="upstreamVipCustStableScore" >**分</span> 
											
										</div>
										<!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								<ul>
									<li>重点客户销售金额合计：</li>
									<li class="bordershow">
										<fmt:formatNumber value="${improvingdata.vipCustomerSales}" pattern="##.##"/>万元
										<!-- <span class="spanright">**分</span> -->
									</li>
									<li class="showright">
										<span id = "vipCustomerSales"></span><!-- <span class="spanright">**分</span> -->
									</li>
								</ul>
								
								<ul>
									<li>下游客户交易量稳定性：</li>
									<li class="bordershow">
										${improvingdata.downstreamCustSalesStable}%
										<span class="spanright">${improvingdata.downstreamCustSalesStableScore}分</span> 
									</li>
								
									<li class="showright">
										<span id ="downstreamCustSalesStable"></span>
										 <span class="spanright" id="downstreamCustSalesStableScore"></span> 
									</li>
								</ul>
								<ul>
									<li>上一年度国税系统纳税评级：</li>
									<li class="bordershow">
											<c:if test="${improvingdata.taxLevel ==1}">
										      A级
										     </c:if>
										     <c:if test="${improvingdata.taxLevel ==2}">
										       B级
										     </c:if>
										     <c:if test="${improvingdata.taxLevel ==3}">
										      C级
										     </c:if>
									     	<c:if test="${improvingdata.taxLevel ==4}">
										       D级
										     </c:if>
										     <span class="spanright">${improvingdata.taxLevelScore}分</span>
									</li>
									<li class="showright">
										<span id ="taxLevel"></span>
										<span class="spanright" id = "taxLevelScore"></span>
									</li>
								</ul>
								<ul>
									<li>
										<div class="step">平台收入：</div>
										<div class="step">企业授信额度：</div>
										<div class="step">企业近十二个月平台利润贡献度：</div>
									</li>
									<li class="bordershow">
										<div class="step">
											<fmt:formatNumber value="${improvingdata.platformSales}" pattern="##.##"/>万元
										</div>
										<div class="step">
											<fmt:formatNumber value="${improvingdata.lineOfCredit}" pattern="##.##"/>万元
										</div>
										<div class="step">
											${improvingdata.platformProfitPer}%
										</div>
										<!--  <span class="spanright">**分</span> -->
										<strong class="spanright">总得分:${improvingdata.allScore}分</strong> 
									</li>
									<li class="showright">
										<div class="step">
											<span id="platformSales"></span>
										</div>
										<div class="step">
											<span id ="lineOfCredit"></span>
										</div>
										<div class="step">
											<span id ="platformProfitPer"></span>
										</div>
										<strong class="spanright">总得分:<span id = "allScore"></span></strong> 
									</li>
								</ul>
								<ul class="ul-table">
									<li class="bordershow">
										<h4>审批记录</h4>
										<table id= "nowProcessInstanceId">
										
										</table>
									</li>
									<li class="showright">
									 	<h4>历史审批记录</h4>
										<table id= "hisProcessInstanceId">
								
										</table>
									</li>
								</ul>
							</div>
	                    </section>
					</div>
                    </section>
				</div>
			</div>
		</div>

	    <script>
	    $(".closeshow").click(function() {
	        $("li.showright").addClass("none");
	        $("li.bordershow").addClass("width");
	    });
	function entersearch(){  
		    var event = window.event || arguments.callee.caller.arguments[0];  
		    if (event.keyCode == 13)  
		       {  
		    	SearchClean();  
		       }  
	} 
	
	 function format_input_num(obj){
		 if(obj.value.length==1){
	            obj.value=obj.value.replace(/[^0-9]/g,'')
	        }else{
	            obj.value=obj.value.replace(/\D/g,'')
	        }

	    }
</script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>