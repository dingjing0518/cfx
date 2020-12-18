<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	<script src="./pageJs/economics/perfectCustomerDatum.js"></script>
	<link href="././style/css/approval.css" rel="stylesheet" type="text/css" />
	<!-- 页面内容导航开始-->
	<input type="hidden" id="firstCompanyPk" value="${economicsCustomer.companyPk}"/>
	<input type="hidden" id="econCustmerPk" value="${economicsCustomer.pk}"/>
	<input type="hidden" id="processInstanceId" value="${economicsCustomer.processInstanceId}"/>

	<input type="hidden" id="consumPerMonthJson" value='${datum.consumPerMonth}'/>
	<input type="hidden" id="rawPurchaseAmountJson" value='${datum.rawPurchaseAmount}'/>
	<input type="hidden" id="deviceStatusJson" value='${datum.deviceStatus}'/>
	<input type="hidden" id="otherDeviceStatusJson" value='${datum.otherDeviceStatus}'/>
	<input type="hidden" id="auditStatus" value='${economicsCustomer.auditStatus}'/>

</head>
<body class="sticky-header">
<div class="page-heading">
	<ul class="breadcrumb">
		<li> 金融中心 /</li>
		<li class="active">金融管理/</li>
		<li class="active">申请客户管理/完善资料</li>
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
							<div class="companytable">
								<p>公司名称：${economicsCustomer.companyName}</p>
								<p>联系人：${economicsCustomer.contacts}</p>
								<p>手机号码:${economicsCustomer.contactsTel}</p>
							</div>
							<div class="companytable">
								<p>申请产品:${economicsCustomer.goodsName}</p>
							</div>
						</div>
						<div class="approval-history">
							<div class="title-top">
								<h3>审批历史</h3>
							</div>
							<div class="approvaltable">
								<table id="perfectCustomerDatumId">
								</table>
							</div>
						</div>
						<div class="dataview">
							<div class="title-top">
								<h3>资料录入及查看历史</h3>
							</div>
							<div class="datainput">
								<h4>资料录入 <span id="lastSaveTimeShow"> 上次保存时间:${updateTime}</span></h4>
								<div class="datainput-content">
									<ul>
										<li id = "il_0" class="active">
											<div class="step">
												<span>√</span>
												<div class="line"></div>
											</div>
											<p>金融专员完善资料</p>
											<span class="time" id = "time_0"></span>
										</li>
										<li id = "il_1">
											<div class="step">
												<span>√</span>
												<div class="line"></div>
											</div>
											<p>风控专员审批</p>
											<span class="time" id = "time_1"></span>
										</li>
										<li id = "il_2">
											<div class="step">
												<span>√</span>
												<div class="line"></div>
											</div>
											<p>金融总监审批</p>
											<span class="time" id = "time_2"></span>
										</li>
										<li id = "il_3">
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
						<div class="datalist">
							<ul>
								<li><i></i>客户类型：</li>
								<li class="bordershow">
									<input type="radio" value="1" name="customerType" <c:if test="${datum.customerType == 1 || datum.customerType == null}">checked</c:if> />一般客户
									<input type="radio" value="2" name="customerType" <c:if test="${datum.customerType == 2}">checked</c:if>/>重点客户
								</li>
								<li class="showright">
									<span id="customerTypeShow"></span>
								</li>
							</ul>
							<ul>
								<li><i></i>企业成立年限：</li>
								<li class="bordershow">
									<input class="form-controlgoods input-append date form-datemonth" type="text"  id="establishingTime" value="${datum.establishingYear}" readonly  valids='notNull' /> &nbsp;&nbsp;${datum.establishingTime == null?0:datum.establishingTime} 个月
								</li>
								<li class="showright">
									<span id="establishingTimeShow"></span>
								</li>
							</ul>
							<ul>
								<li>上一年度纳税销售金额：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="lastTaxSales" value="<fmt:formatNumber value="${datum.lastTaxSales}" pattern="0.00" />" valids='notNull'/>&nbsp;&nbsp;万元
								</li>
								<li class="showright">
									<span id="lastTaxSalesShow"></span>
								</li>
							</ul>
							<ul>
								<li>关联企业上一自然年度纳税销售金额：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="releLastTaxSales" value="<fmt:formatNumber value="${datum.releLastTaxSales}" pattern="0.00"/>" valids='notNull'/>&nbsp;&nbsp;万元

								</li>
								<li class="showright">
									<span id="releLastTaxSalesShow"></span>
								</li>
							</ul>
							<ul>
								<li>当期企业对外担保余额：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="amountGuara" value="<fmt:formatNumber value="${datum.amountGuara}" pattern="0.00"/>" valids='notNull'/>&nbsp;&nbsp;万元

								</li>
								<li class="showright">
									<span id="amountGuaraShow"></span>
								</li>
							</ul>
							<ul>
								<li>当期关联企业对外担保余额：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="releAmountGuara" value="<fmt:formatNumber value="${datum.releAmountGuara}" pattern="0.00"/>" valids='notNull'/>&nbsp;&nbsp;万元

								</li>
								<li class="showright">
									<span id="releAmountGuaraShow"></span>
								</li>
							</ul>
							<ul>
								<li><i></i>企业担保余额与纳税销售比值：</li>
								<li class="bordershow" style="vertical-align: middle;">
									<input type="hidden" id="guaraTaxSalesPer" value="0.0"/>
									${datum.guaraTaxSalesPer == null?0.0:datum.guaraTaxSalesPer}%
								</li>
								<li class="showright" style="vertical-align: middle;">
									<span id="guaraTaxSalesPerShow"></span>
								</li>
							</ul>
							<ul>
								<li><i></i>关联企业对外担保与纳税销售比：</li>
								<li class="bordershow" style="vertical-align: middle;">
									<input type="hidden" id="releGuaraTaxSalesPer" value="0.0"/>
									${datum.releGuaraTaxSalesPer == null?0.0:datum.releGuaraTaxSalesPer}%
								</li>
								<li class="showright" style="vertical-align: middle;">
									<span id="releGuaraTaxSalesPerShow"></span>

								</li>
							</ul>
							<ul>
								<li><i></i>企业当期融资金额：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="finanAmount" value="<fmt:formatNumber value="${datum.finanAmount}" pattern="0.00"/>" valids='notNull' />&nbsp;&nbsp;万元
								</li>
								<li class="showright">
									<span id="finanAmountShow"></span>
								</li>
							</ul>
							<ul>
								<li><i></i>关联企业当期融资金额：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="releFinanAmount" value="<fmt:formatNumber value="${datum.releFinanAmount}" pattern="0.00"/>" valids='notNull'/>&nbsp;&nbsp;万元
								</li>
								<li class="showright">
									<span id="releFinanAmountShow"></span>
								</li>
							</ul>
							<ul>
								<li><i></i>企业融资纳税销售比：</li>
								<li class="bordershow" style="    vertical-align: middle;">
									<input type="hidden" id="finanAmountTaxSales" value="0.0"/>
									${datum.finanAmountTaxSales == null?0.0:datum.finanAmountTaxSales}%
								</li>
								<li class="showright" style="    vertical-align: middle;">
									<span id="finanAmountTaxSalesShow"></span>
								</li>
							</ul>
							<ul>
								<li><i></i>关联企业名下负债与纳税销售比：</li>
								<li class="bordershow" style="    vertical-align: middle;">
									<input type="hidden" id="releFinanAmountTaxSales" value="0.0"/>
									${datum.releFinanAmountTaxSales == null?0.0:datum.releFinanAmountTaxSales}%
								</li>
								<li class="showright" style="    vertical-align: middle;">
									<span id="releFinanAmountTaxSalesShow"></span>
								</li>
							</ul>
							<ul>
								<li><i></i>合作金融机构数量：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="finanInstitution" value="${datum.finanInstitution}" valids='notNull' />&nbsp;&nbsp;家
								</li>
								<li class="showright">
									<span id="finanInstitutionShow"></span>
								</li>
							</ul>
							<ul>
								<li>
									<div class="step">参与评分的设备名称：</div>
									<div class="step">其他设备：</div>
								</li>
								<li class="bordershow" style="vertical-align: top;">
									<div class="step">
										<span>设备类型：</span>
										<select onchange="changeDeviceType(this.value)" id="deviceType" valids='notNull'>
											<option value="">--请选择--</option>
											<option value="1" <c:if test="${deviceType == '圆机'}">selected</c:if>>圆机</option>
											<option value="2" <c:if test="${deviceType == '经编机'}">selected</c:if>>经编机</option>
											<option value="3" <c:if test="${deviceType == '加弹机'}">selected</c:if>>加弹机</option>
											<option value="4" <c:if test="${deviceType == '梭织机'}">selected</c:if>>梭织机</option>
										</select>
										<span>设备品牌：</span>
										<select id="deviceBrand">
											<option value="">--请选择--</option>
										</select>
										<span>设备数量：</span>
										<input type="text" onkeyup="format_input_nPlus(this,10)" maxlength="10" id="deviceNum" value="${deviceNum}"/>&nbsp;&nbsp;台
									</div>
									<div class="step" id="addDeviceDiv">
										<span>设备类型：</span>
										<select id="deviceType1" name="deviceType" onchange="changeAddDevice(this.value,1)" valids='notNull'>
											<option value="">--请选择--</option>
											<option value="1" >圆机</option>
											<option value="2">经编机</option>
											<option value="3">加弹机</option>
											<option value="4">梭织机</option>
										</select>
										<span>设备品牌：</span>
										<select id="deviceBrand1" name="deviceBrand">
											<option value="">--请选择--</option>
										</select>
										<span>设备数量：</span>
										<input type="text" onkeyup="format_input_nPlus(this,10)" maxlength="10" id="deviceNum1" name="deviceNum" />&nbsp;&nbsp;台
										<button class="inster" id="addNewDevice">新增</button>
									</div>
								</li>
								<li class="showright">
									<div class="step">
										<span id="deviceStatusShow"></span>
									</div>
									<div class="step">
										<span id="otherDeviceStatusShow"></span>
									</div>
								</li>
							</ul>
							<ul>
								<li>企业在平台上一年度累计交易金额：</li>
								<li class="bordershow"><fmt:formatNumber value="${purchaserSalesAmount==null?0:purchaserSalesAmount}" pattern="0.00"/> 万元</li>
								<input type="hidden" id="transAmountOnline" value="<fmt:formatNumber value="${purchaserSalesAmount==null?0:purchaserSalesAmount}" pattern="0.00"/>">
								<li class="showright"><span id="transAmountOnlineShow"></span></li>
							</ul>
							<ul>
								<li>
									<div class=""step>原料年采购额：</div>
								</li>
								<li class="bordershow">
									<div class="step" id="rawAddDiv">
										<input class="form-controlgoods input-append date form-dateyear" type="text"  readonly id="rawPurchaseYear1"/>
										<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="rawPurchaseAmount1" valids='notNull'/>&nbsp;&nbsp;万元
										<button class="inster" id="addRaw">新增</button>
									</div>
								</li>
								<li class="showright">
									<span id="rawPurchaseAmountShow"></span>
								</li>
							</ul>
							<ul>
								<li>企业原料采购额环比稳定性：</li>
								<li class="bordershow">${datum.rawPurchaseIncrePer == null?0.0:datum.rawPurchaseIncrePer}%</li>
								<li class="showright"><span id="rawPurchaseIncrePerShow"></span></li>
							</ul>
							<input type="hidden" id="rawPurchaseIncrePer" value="0.0">
							<ul>
								<li>原料线上采购额较总采购额占比：</li>
								<li class="bordershow" style="vertical-align: middle;">${datum.transOnlineRawPurchPer==null?0.0:datum.transOnlineRawPurchPer} %</li>
								<li class="showright" style="vertical-align: middle;"><span id="transOnlineRawPurchPerShow"></span></li>
							</ul>
							<input type="hidden" id="transOnlineRawPurchPer" value="0.0">
							<ul>
								<li>企业在平台上一年度交易频次：</li>
								<li class="bordershow">${onlineAddupNumber}个月</li>
								<li class="showright"><span id="useMonthsNumShow"></span> </li>
							</ul>
							<input type="hidden" id="useMonthsNum" value="${onlineAddupNumber}">
							<ul>
								<li>企业在平台上一年度金融产品使用频次：</li>
								<li class="bordershow">${econAddUpNumbers}个月</li>
								<li class="showright"><span id="econUseMonthsNumShow"></span></li>
							</ul>
							<input type="hidden" id="econUseMonthsNum" value="${econAddUpNumbers}">
							<ul>
								<li>近12个月累计金融产品提用金额：</li>
								<li class="bordershow"><fmt:formatNumber value="${countEconSalesAmount}" pattern="0.00"/>万元</li>
								<li class="showright"><span id="econUseMonthsAmountShow"></span></li>
							</ul>
							<input type="hidden" id="econUseMonthsAmount" value="${countEconSalesAmount}">
							<ul>
								<li>企业在平台上一年度金融产品还款逾期次数：</li>
								<li class="bordershow">${countEconGoodsIsOver}次</li>
								<li class="showright"><span id="econOverdueNumShow"></span></li>
							</ul>
							<input type="hidden" id="econOverdueNum" value="${countEconGoodsIsOver}">
							<ul>
								<li>银行近期年化结算流水：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="bankAnnualizAmount" value="<fmt:formatNumber value="${datum.bankAnnualizAmount}" pattern="0.00"/>" valids='notNull'/>&nbsp;&nbsp;万元
								</li>
								<li class="showright"><span id="bankAnnualizAmountShow"></span></li>
							</ul>
							<ul>
								<li><i></i>企业银行流水占销售比：</li>
								<li class="bordershow" style="vertical-align: middle;">
									${datum.bankAnnualizAmount == null?0.0:datum.annualizTaxSalesPer}%
								</li>
								<li class="showright" style="vertical-align: middle;"><span id="annualizTaxSalesPerShow"></span></li>
							</ul>
							<input type="hidden" id="annualizTaxSalesPer" value="0.0">
							<ul>
								<li><i></i>企业近期纳税销售年化值：</li>
								<li class="bordershow">
									<input class="form-controlgoods input-append date form-datemonth" type="text"  id="annualizTaxAmountYear" value="${datum.annualizTaxSalesTime}" readonly valids='notNull' />
									<span>累计销售</span>
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="inputAnnualizTax" value="<fmt:formatNumber value="${datum.annualizTaxSalesAmount}" pattern="0.00"/>" valids='notNull' />&nbsp;&nbsp;万元

									&nbsp;&nbsp;&nbsp;&nbsp;年化值：${datum.annualizTaxAmount}万元
								</li>
								<li class="showright"><span id="annualizTaxAmountShow"></span></li>
							</ul>
							<ul>
								<li><i></i>原料供应商名称：</li>
								<li class="bordershow">
									<select class="selectpicker bla bla bli " data-live-search="true" id="rawBrandPk"  valids='notNull' >
										<option value="">--请选择--</option>
										<c:forEach items="${brandList}" var="m">
											<c:choose>
												<c:when test="${datum.rawBrandPk != null && datum.rawBrandPk == m.brandPk}">
													<option value="${m.brandPk}" selected>${m.brandName }</option>
												</c:when>
												<c:otherwise>
													<option value="${m.brandPk}">${m.brandName }</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</li>
								<li class="showright"><span id="rawBrandPkShow"></span></li>
							</ul>
							<ul>
								<li><i></i>原料近期采购年化值：</li>
								<li class="bordershow">
									<input class="form-controlgoods input-append date form-datemonth" type="text"  id="rawAnnualizAmountYear" readonly value="${datum.rawAnnualizSalesTime}" valids='notNull'/>
									<span>累计采购</span>

									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="inputRawAnnualiz" value="${datum.rawAnnualizSalesAmount}" valids='notNull'/>&nbsp;&nbsp;万元
									&nbsp;&nbsp;&nbsp;&nbsp;年化值：<fmt:formatNumber value="${datum.rawAnnualizAmount}" pattern="0.00"/>万元
								</li>
								<li class="showright"><span id="rawAnnualizAmountShow"></span></li>
							</ul>
							<ul>
								<li><i></i>生产经营厂房权属性质及面积：</li>
								<li class="bordershow">
									<div class="step">
										<p>类型：自有</p>
										<span>面积数：</span>
										<input type="text"  onkeyup="format_input_num(this,8)" maxlength="8" id="workshopOwnSquare" value="${datum.workshopOwnSquare}" valids='notNull'/>&nbsp;&nbsp;平方米
									</div>
									<div class="step">
										<p>类型：租赁</p>
										<span>面积数：</span>
										<input type="text" onkeyup="format_input_num(this,8)" maxlength="8" id="workshopRentSquare" value="${datum.workshopRentSquare}" valids='notNull'/>&nbsp;&nbsp;平方米
									</div>
									<div class="step">
										<input type="hidden" id="workshopAllSquare" value="">
										<p>汇总面积：${datum.workshopAllSquare} m2</p>
									</div>
								</li>
								<li class="showright">
									<div class="step">
										<span id="workshopOwnSquareShow"></span>
									</div>
									<div class="step">
										<span id="workshopRentSquareShow"></span>
									</div>
									<div class="step">
										<span id="workshopAllSquareShow"></span>
									</div>
								</li>
							</ul>
							<ul>
								<li><i></i>企业生产流程：</li>
								<li class="bordershow">
									<select class="selectpicker bla bla bli " id="flowOfProduction" valids='notNull'>
										<option value="">--请选择--</option>
										<option value="1" <c:if test="${datum.flowOfProduction == 1}">selected</c:if>>一步</option>
										<option value="2" <c:if test="${datum.flowOfProduction == 2}">selected</c:if>>两步</option>
										<option value="3" <c:if test="${datum.flowOfProduction == 3}">selected</c:if>>三步及以上</option>
									</select>
								</li>
								<li class="showright"><span id="flowOfProductionShow"></span></li>
							</ul>
							<ul>
								<li><i></i>企业商业经营模式：</li>
								<li class="bordershow">
									<select class="selectpicker bla bla bli " id="managementModel" valids='notNull'>
										<option value="">--请选择--</option>
										<option value="1" <c:if test="${datum.managementModel == 1}">selected</c:if>>代加工</option>
										<option value="2" <c:if test="${datum.managementModel == 2}">selected</c:if>>自产自销</option>
										<option value="3" <c:if test="${datum.managementModel == 3}">selected</c:if>>自产自销+外发加工</option>
										<option value="4" <c:if test="${datum.managementModel == 4}">selected</c:if>>自产自销+代加工</option>
									</select>
								</li>
								<li class="showright"><span id="managementModelShow"></span></li>
							</ul>
							<ul>
								<li><i></i>企业在平台上一年度合作供应商数量：</li>
								<li class="bordershow">
									${countStoreSalesNum == null ? 0:countStoreSalesNum} 家
								</li>
								<li class="showright"><span id="businessShopNumShow"></span></li>
							</ul>
							<input type="hidden" id="businessShopNum" value="${countStoreSalesNum}">
							<ul>
								<li><i></i>企业股权结构及高管稳定性：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="shareChangeNum" value="${datum.shareChangeNum}" valids='notNull'/>&nbsp;&nbsp;次
								</li>
								<li class="showright"><span id="shareChangeNumShow"></span></li>
							</ul>
							<ul>
								<li><i></i>近三年行政处罚及被执行次数：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="enforcedNum" value="${datum.enforcedNum}" valids='notNull'/>&nbsp;&nbsp;次
								</li>
								<li class="showright"><span id="enforcedNumShow"></span></li>
							</ul>
							<ul>
								<li>企业还款付息逾期次数：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="zxEnforcedNum" value="${datum.zxEnforcedNum}" valids='notNull'/>&nbsp;&nbsp;次
								</li>
								<li class="showright"><span id="zxEnforcedNumShow"></span></li>
							</ul>

							<ul>
								<li>企业与金融机构合作时间：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="ecomCooperateTime" value="${datum.ecomCooperateTime}" valids='notNull'/>&nbsp;&nbsp;年
								</li>
								<li class="showright"><span id="ecomCooperateTimeShow"></span></li>
							</ul>
							<ul>
								<li>企业与供应商合作年限：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="supplierCooperateTime" value="${datum.supplierCooperateTime}" valids='notNull'/>&nbsp;&nbsp;年
								</li>
								<li class="showright"><span id="supplierCooperateTimeShow"></span></li>
							</ul>
							<ul>
								<li>主要采购原料纤度：</li>
								<li class="bordershow">
									<span>DPF= </span>&nbsp;&nbsp;<input type="text" onkeyup="format_input_num(this,8)" maxlength="8" id="purcherVariety" value="${datum.purcherVariety}" valids='notNull'/>
								</li>
								<li class="showright"><span id="purcherVarietyShow"></span></li>
							</ul>
							<ul>
								<li>
									<strong style="float: left;font-size: 20px;margin-bottom: 40px;">加分项</strong>
									<span style="padding-top:36px;float:right">企业经营地点稳定性：</span></li>
								<li class="bordershow" style="vertical-align: bottom;">
									<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="manageStable" value="${datum.manageStable}" valids='addIsNull'/>&nbsp;&nbsp;年
								</li>
								<li class="showright" style="vertical-align: bottom;"><span id="manageStableShow"></span></li>
							</ul>
							<ul>
								<li>单月企业能耗：</li>
								<li class="bordershow">
									<div class="step" id="addConsumPerMonth">
										<input class="form-controlgoods input-append date form-datemonth" type="text"  readonly id="consumPerMonth1" valids='addIsNull'/>
										<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="consumPerMonthAmount1" valids='addIsNull' />&nbsp;&nbsp;元
										<button class="inster" id="addConsum">新增</button>
									</div>
								</li>
								<li class="showright">
									<span id="consumPerMonthShow"></span>
								</li>
							</ul>
							<ul>
								<li>合计企业能耗：</li>
								<li class="bordershow">
									<fmt:formatNumber value="${datum.consumAddup == null ? 0.0:datum.consumAddup}" pattern="0.00"/>元
								</li>
								<li class="showright">
									<span id="consumAddupShow"></span>
								</li>
							</ul>
							<input type="hidden" id="consumAddup" value="0.0">
							<ul>
								<li>企业能耗平均值：</li>
								<li class="bordershow">
									<fmt:formatNumber value="${datum.averageConsum == null ? 0.0:datum.averageConsum}" pattern="0.00"/>元
								</li>
								<li class="showright">
									<span id="averageConsumShow"></span>
								</li>
							</ul>
							<input type="hidden" id="averageConsum" value="0.0">
							<ul>
								<li>企业主要生产能耗稳定性情况：</li>
								<li class="bordershow">
									${datum.consumStable == null ? 0.0:datum.consumStable}%
								</li>
								<li class="showright">
									<span id="consumStableShow"></span>
								</li>
							</ul>
							<input type="hidden" id="consumStable" value="0.0">
							<ul>
								<li>行业平均能耗：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="industryAverageConsum" value="<fmt:formatNumber value="${datum.industryAverageConsum}" pattern="0.00"/>" valids='addIsNull'/>&nbsp;&nbsp;元
								</li>
								<li class="showright">
									<span id="industryAverageConsumShow"></span>
								</li>
							</ul>
							<ul>
								<li>企业主要能耗与行业平均能耗对比情况：</li>
								<li class="bordershow">
									${datum.averageIndustryConsumPer == null ? 0.0:datum.averageIndustryConsumPer}%
								</li>
								<li class="showright">
									<span id="averageIndustryConsumPerShow"></span>
								</li>
							</ul>
							<input type="hidden" id="averageIndustryConsumPer" value="0.0">
							<ul>
								<li>
									<div class="step">上游重合客户数：</div>
									<div class="step">上游客户重合基数：</div>
									<div class="step">上游客户稳定性：</div>
								</li>
								<li class="bordershow">
									<div class="step">
										<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="upstreamCustomerNum" value="${datum.upstreamCustomerNum}" valids='addIsNull'/>&nbsp;&nbsp;家
									</div>
									<div class="step">
										<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="upstreamCustomerBaseNum" value="${datum.upstreamCustomerBaseNum}" valids='addIsNull'/>&nbsp;&nbsp;家
									</div>
									<div class="step">
										<input type="hidden" id="upstreamCustomerStable" value="0.0">
										${datum.upstreamCustomerStable == null ?0.0:datum.upstreamCustomerStable}%
									</div>
								</li>
								<li class="showright">
									<div class="step">
										<span id="upstreamCustomerNumShow"></span>
									</div>
									<div class="step">
										<span id="upstreamCustomerBaseNumShow"></span>
									</div>
									<div class="step">
										<span id="upstreamCustomerStableShow"></span>
									</div>
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
										<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="downstreamCustomerNum" value="${datum.downstreamCustomerNum}" valids='addIsNull'/>&nbsp;&nbsp;家
									</div>
									<div class="step">
										<input type="text" onkeyup="format_input_nPlus(this,4)" maxlength="4" id="downstreamCustomerBaseNum" value="${datum.downstreamCustomerBaseNum}" valids='addIsNull'/>&nbsp;&nbsp;家
									</div>
									<div class="step">
										<input type="hidden" id="downstreamCustomerStable" value="0.0">
										${datum.downstreamCustomerStable==null?0.0:datum.downstreamCustomerStable}%
									</div>
								</li>
								<li class="showright">
									<div class="step">
										<span id="downstreamCustomerNumShow"></span>
									</div>
									<div class="step">
										<span id="downstreamCustomerBaseNumShow"></span>
									</div>
									<div class="step">
										<span id="downstreamCustomerStableShow"></span>
									</div>
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
										<input type="text" onkeyup="format_input_nPlus(this,8)" maxlength="8" id="vipCustPurchaserAddUp" value="<fmt:formatNumber value="${datum.vipCustPurchaserAddUp}" pattern="0"/>" valids='addIsNull'/>&nbsp;&nbsp;万元
									</div>
									<div class="step">
										<input type="text" onkeyup="format_input_nPlus(this,8)" maxlength="8" id="inputTaxAmount" value="<fmt:formatNumber value="${datum.inputTaxAmount}" pattern="0"/>" valids='addIsNull'/>&nbsp;&nbsp;万元
									</div>
									<div class="step">
										<input type="hidden" id="upstreamVipCustStable" value="0.0">
										${datum.upstreamVipCustStable == null? 0.0:datum.upstreamVipCustStable}%
									</div>
								</li>
								<li class="showright">
									<div class="step">
										<span id="vipCustPurchaserAddUpShow"></span>
									</div>
									<div class="step">
										<span id="inputTaxAmountShow"></span>
									</div>
									<div class="step">
										<span id="upstreamVipCustStableShow"></span>
									</div>
								</li>
							</ul>
							<ul>
								<li>重点客户销售金额合计：</li>
								<li class="bordershow">
									<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="vipCustomerSales" value="<fmt:formatNumber value="${datum.vipCustomerSales}" pattern="0.00"/>" valids='addIsNull'/>&nbsp;&nbsp;万元

								</li>
								<li class="showright"><span id="vipCustomerSalesShow"></span></li>
							</ul>
							<ul>
								<li>下游客户交易量稳定性：</li>
								<li class="bordershow">
									<input type="hidden" id="downstreamCustSalesStable" value="0.0" >
									${datum.downstreamCustSalesStable == null?0.0:datum.downstreamCustSalesStable}%
								</li>
								<li class="showright"><span id="downstreamCustSalesStableShow"></span></li>
							</ul>
							<ul>
								<li>上一年度国税系统纳税评级：</li>
								<li class="bordershow">
									<select class="selectpicker bla bla bli " id="taxLevel" valids='addIsNull'>
										<option value="">--请选择--</option>
										<option value="1" <c:if test="${datum.taxLevel == 1}">selected</c:if>>A级</option>
										<option value="2" <c:if test="${datum.taxLevel == 2}">selected</c:if>>B级</option>
										<option value="3" <c:if test="${datum.taxLevel == 3}">selected</c:if>>C级</option>
										<option value="4" <c:if test="${datum.taxLevel == 4}">selected</c:if>>D级</option>
									</select>
								</li>
								<li class="showright"><span id="taxLevelShow"></span></li>
							</ul>
							<ul>
								<li>
									<div class="step">平台收入：</div>
									<div class="step">企业授信额度：</div>
									<div class="step">企业近十二个月平台利润贡献度：</div>
								</li>
								<li class="bordershow">
									<div class="step">
										<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="platformSales" value="<fmt:formatNumber value="${datum.platformSales}" pattern="0.00"/>" valids='addIsNull'/>&nbsp;&nbsp;万元
										<span style="float: none;margin-left: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${countPlatformAmount == null?0:countPlatformAmount}" pattern="0.00"/> 万元(平台计算结果，供参考，如有疑问可修改)</span>
									</div>
									<div class="step">
										<input type="text" onkeyup="format_input_num(this,16)" maxlength="16" id="lineOfCredit" value="<fmt:formatNumber value="${datum.lineOfCredit}" pattern="0.00"/>" valids='addIsNull'/>&nbsp;&nbsp;万元
										<span style="float: none;margin-left: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatNumber value="${countCreditAmount == null?0:countCreditAmount}" pattern="0.00"/> 万元(平台计算结果，供参考，如有疑问可修改)</span>
									</div>
									<div class="step">
										<input type="hidden" id="platformProfitPer" value="0.0">
										${datum.platformProfitPer == null?0.0:datum.platformProfitPer}%
									</div>
								</li>
								<li class="showright">
									<div class="step">
										<span id="platformSalesShow"></span>
									</div>
									<div class="step">
										<span id="lineOfCreditShow"></span>
									</div>
									<div class="step">
										<span id="platformProfitPerShow"></span>
									</div>
								</li>
							</ul>
							<ul class="ul-table">
								<li class="bordershow">
									<h4>审批记录</h4>
									<table id="approveHistoryTableId">

									</table>
								</li>
								<li class="showright">
									<h4>历史审批记录</h4>
									<table id="historyAuditId">
									</table>
								</li>
							</ul>
							<div class="button">
								<c:if test="${economicsCustomer.auditStatus == 1 || economicsCustomer.auditStatus == 4}">
									<button class="save-button" onclick="submitDatum()">保存</button>
								</c:if>
								<button class="cancel-button" onclick="backToCustomer()">返回</button>
							</div>
						</div>
					</section>
				</div>
			</section>
		</div>
	</div>
</div>
<script>
    $("li.showright").addClass("none");
    $("li.bordershow").addClass("width");
    $(".closeshow").click(function() {
        $("li.showright").addClass("none");
        $("li.bordershow").addClass("width");
    });
    
    $(".form-dateyear").datetimepicker({
        startView: 'decade',
        minView: 'decade',
        format: 'yyyy',
        maxViewMode: 2,
        minViewMode:2,
        language : "zh-CN",
        autoclose : true,
        clearBtn: true,
        pickerPosition:"bottom-right"
    });

    $(".form-datemonth").datetimepicker({

        format: 'yyyy-mm',
        weekStart: 1,
        autoclose: true,
        startView: 3,
        minView: 3,
        forceParse: false,
        language: 'zh-CN',
        autoclose : true,
        clearBtn: true,
        pickerPosition: "bottom-right"
    });

    function entersearch(){
        var event = window.event || arguments.callee.caller.arguments[0];
        if (event.keyCode == 13)
        {
            SearchClean();
        }
    }
    // 格式化限制数字文本框输入，只能数字或者两位小数
    function format_input_num(obj,num){

            //obj.value = obj.value.replace(/^[0]+[1-9]*$/gi, '')
			// 清除"数字"和"."以外的字符
			obj.value = obj.value.replace(/[^\d.]/g,"");
			// 验证第一个字符是数字
			obj.value = obj.value.replace(/^\./g,"");
			// 只保留第一个, 清除多余的
			obj.value = obj.value.replace(/\.{2,}/g,".");
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			// 只能输入两个小数
			obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
    }
    //输入数字为正整数,和零
    function format_input_nPlus(obj,num) {

        	//obj.value = obj.value.replace(/^[0]+[1-9]*$/gi, '')

            obj.value=obj.value.replace(/\D/g,'')

    }

// $(".glyphicon-remove").click(function (){
//     $($($(this).parent()).prev()).val("");
// });
</script>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>