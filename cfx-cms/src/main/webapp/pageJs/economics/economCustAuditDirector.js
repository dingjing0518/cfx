
$(function() {
	
	var cfgHis = {
			url : './searchApproveHistoryList.do?companyPk='+ $("#companyPk").val(),
			columns : hisColumns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			
		};
	
	createDataGrid('approveId', cfgHis);
	createDataGrid('nowProcessInstanceId', nowCfg);
	getProcess(GetRequest("processInstanceId"))

	//默认处于隐藏状态
	$("li.showright").addClass("none");
    $("li.bordershow").addClass("width");
    //设备概况
    $("#deviceStatusHideSpan").html(deviceStatus($("#deviceStatusHide").html()))
    $("#otherDeviceStatusHideSpan").html(otherdeviceStatus($("#otherDeviceStatusHide").html()))
    //原料累计采购金额
    $("#rawPurchaseAmountHideSpan").html(rawPurchaseAmount($("#rawPurchaseAmountHide").html()))
    //单月企业能耗
    $("#consumPerMonthHideSpan").html(consumPerMonth($("#consumPerMonthHide").html()))
});

function deviceStatus (deviceStatusStr){
	var html = "";
	if(deviceStatusStr!=''&& deviceStatusStr!=null){
		var json =	JSON.parse(deviceStatusStr);
		html = " <div class='step'>设备类型："+json.deviceType+"；设备品牌："+json.deviceBrand+"；设备数量"+json.deviceNum+"台</div>";
	}
	return html;
}
function otherdeviceStatus (deviceStatusStr){
	var html = "";
	if(deviceStatusStr!=''&& deviceStatusStr!=null){
		var json =	JSON.parse(deviceStatusStr);
		for(var i = 0 ; i < json.length;i++){
			html = html+ " <div class='step'>设备类型："+json[i].deviceType+"；设备品牌："+json[i].deviceBrand+"；设备数量"+json[i].deviceNum+"台</div>";
		}
	}
	return html;
}

function rawPurchaseAmount (rawPurchaseAmountStr){

	var html = "";
	if(rawPurchaseAmountStr!=''&& rawPurchaseAmountStr!=null){
		var json =	JSON.parse(rawPurchaseAmountStr);
		for(var i = 0 ; i < json.length;i++){
			html = html+ " <div class='step'>"+json[i].rawPurchaseYear+"年累计"+json[i].rawPurchaseAmount+"万元</div>";
		}
	}
	return html;
}

function consumPerMonth (consumPerMonthStr){

	var html = "";
	if(consumPerMonthStr!=''&& consumPerMonthStr!=null){
		var json =	JSON.parse(consumPerMonthStr);
		for(var i = 0 ; i < json.length;i++){
			html = html+ " <div class='step'>"+json[i].consumPerMonth+"月"+json[i].consumPerMonthAmount+"元</div>";
		}
	}
	return html;
}
function GetRequest(key) {
	var url = location.search; // 获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest[key];
}


var hisColumns = [
       		 {
       			title : '申请时间',
       			field : 'insertTime',
       			width : 20,
       			sortable : true
       		},{
       			title : '申请产品',
       			field : 'productName',
       			width : 20,
       			sortable : true,
       			
       		}, {
       			title : '审批时间',
       			field : 'approveTime',
       			width : 20,
       			sortable : true
       		}, {
					title : '审批状态',
					field : 'auditStatus',
					width : 20,
					sortable : true,
					formatter : function (value, row, index) {
						if(value==1){
							return "待审批";
						}else if(value==2){
							return "审批中";
						}else if(value==3){
							return "已审批";
						}else if(value==4){
							return "驳回";
						}  
					}
				},{
					title : '操作',
					field : 'pk',
					width : 80,
					formatter : function(value, row, index) {
						var str = '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" onclick="javascript:check(\''+ row.processInstanceId + '\'); ">查看</button>';
						return str;
					}
				} ];




var toolbar = [];
var columns = [ {
	title : '审批人',
	field : 'userId',
	width : 20,
	sortable : true
}, {
	title : '审批时间',
	field : 'time',
	width : 20,
	sortable : true
}, {
	title : '审批内容',
	field : 'fullMessage',
	width : 20,
	sortable : true
},  {
	title : '审批状态',
	field : 'state',
	width : 20,
	sortable : true
}];


var nowCfg = {
		url : './searchEconCustAuditDetailList.do?processInstanceId='+ GetRequest("processInstanceId"),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
/**
 * 审核通过或驳回
 * @param status
 */
function submitAudit(status) {
	var pk = "";
	var effectStartTimes = "";
	var effectEndTimes = "";
	var limits ="";
	var totalRate = "";
	var bankRate = "";
	var term = "";
	var sevenRate = "";
	var effectStartTimesArr = [];
	var effectEndTimesArr = [];
	var limitsArr =[];
	var productNamesArr = []
	var totalRateArr = [];
	var bankRateArr = [];
	var termArr =[];
	var sevenRateArr = []
	 var str = "";
	var type = "d";
	   var params = {};
	if (status != 2) {
			 $("input[name='effectStartTime_"+type+"']").each(function(){
				 effectStartTimes =effectStartTimes  + $(this).val()+ ",";
				 effectStartTimesArr.push($(this).val());
		     })
		     
		      $("input[name='effectEndTime_"+type+"']").each(function(){
				 effectEndTimes =effectEndTimes  + $(this).val()+ ",";
				 effectEndTimesArr.push( $(this).val());
		     })
		     
		      $("input[name='limit_"+type+"']").each(function(){
				 limits =limits  + $(this).val()+ ",";
				 limitsArr.push( $(this).val());
		      })
		     
		      $("input[name='totalRate_"+type+"']").each(function(){
		        	totalRate =totalRate  + $(this).val()+ ",";
		        	totalRateArr.push( $(this).val());
		      })
		        $("input[name='bankRate_"+type+"']").each(function(){
		        	bankRate =bankRate  + $(this).val()+ ",";
		        	bankRateArr.push( $(this).val());
		      })
		        $("input[name='term_"+type+"']").each(function(){
		        	term =term  + $(this).val()+ ",";
		        	termArr.push( $(this).val());
		      })
		        $("input[name='sevenRate']").each(function(){
		        	sevenRate =sevenRate  + $(this).val()+ ",";
		        	sevenRateArr.push( $(this).val());
		      })
		      
		      $("input[name='productName_"+type+"']").each(function(){ 
		    	  productNamesArr.push( $(this).val());
		     })
		     
		      for(var i = 0 ; i < productNamesArr.length;i++){
		    	  if (limitsArr.length==0||limitsArr[i]==undefined ||limitsArr[i] == "") {
						new $.flavr({
							modal : false,
							content : "建议额度不能为空！"
						});
						return;
					}
					if (effectStartTimesArr.length == 0||effectEndTimesArr.length == 0
							||effectStartTimesArr[i]==undefined ||effectEndTimesArr[i]==undefined 
							|| effectStartTimesArr[i] == ""||effectEndTimesArr[i] == "") {
						new $.flavr({
							modal : false,
							content : "有效时间不能为空！"
						});
						return;
					}
					
					if (totalRateArr.length == 0||totalRateArr.length == 0
							||totalRateArr[i]==undefined ||totalRateArr[i] == ""||!isNumRate(totalRateArr[i])) {
						new $.flavr({
							modal : false,
			                content:"总利率是取值范围【0,100】，小数位不超过3位的数！"
						});
						return;
					}
					
					if (bankRateArr.length == 0||bankRateArr.length == 0
							||bankRateArr[i]==undefined || bankRateArr[i] == ""||!isNumRate(bankRateArr[i])) {
						new $.flavr({
							modal : false,
			                content:"银行利率是取值范围【0,100】，小数位不超过3位的数！"
						});
						return;
					}
					
					if(parseInt(totalRateArr[i])<parseInt( bankRateArr[i])){
						new $.flavr({
			                modal: false,
			                content:"总利率不能小于银行利率，重新填写！"
			            });
			            return;
					}
					
					if (termArr.length == 0||termArr.length == 0
							||termArr[i]==undefined || termArr[i] == ""||!isDNumber(termArr[i])||termArr[i] >180) {
						new $.flavr({
							modal : false,
							content:"期限为【0,180】的整数！"
						});
						return;
					}
					if(type=="d"){
						if (sevenRateArr.length == 0||!isNumRate(sevenRateArr[i])
								||sevenRateArr[i]==undefined ||sevenRateArr[i] == "") {
							new $.flavr({
								modal : false,
				                content:"平台七天利率是取值范围【0,100】，小数位不超过3位的数！"
							});
							return;
						}
					}
				
		    	  str = str + "审批产品：" + productNamesArr[i] + "，建议额度："
					+ limitsArr[i] + "w，有效时间："
					+ effectStartTimesArr[i] + "到"
					+ effectEndTimesArr[i] + "；"+"总利率："+totalRateArr[i]+"；"+"银行利率："+bankRateArr[i]+"；"+"期限："+termArr[i]+"；"
					if(type=="d"){
						str  = str +"七天利率："+sevenRateArr[i]+"；"
					}
		      }
			 
			  $("input[name='econGoodsPk_d']").each(function(){
					 pk =pk  + $(this).val()+ ",";
			     })
			     
				params['effectStartTimesD'] = effectStartTimes;
				params['effectEndTimesD'] = effectEndTimes;
				params['limitsD'] = limits;
				params['totalRateD'] = totalRate;
				params['bankRateD'] = bankRate;
				params['termD'] = term;
				params['sevenRateD'] = sevenRate;
				params['econGoodsPkD']= pk;
				
				pk = "";
				effectStartTimes = "";
				effectEndTimes = "";
				limits ="";
				totalRate = "";
				bankRate = "";
				term = "";
				sevenRate = "";
				effectStartTimesArr = [];
				effectEndTimesArr = [];
				limitsArr =[];
				productNamesArr = []
				totalRateArr = [];
				bankRateArr = [];
				termArr =[];
				sevenRateArr = [];
				
				type = "bt";
				 $("input[name='effectStartTime_"+type+"']").each(function(){
					 effectStartTimes =effectStartTimes  + $(this).val()+ ",";
					 effectStartTimesArr.push($(this).val());
			     })
			     
			      $("input[name='effectEndTime_"+type+"']").each(function(){
					 effectEndTimes =effectEndTimes  + $(this).val()+ ",";
					 effectEndTimesArr.push( $(this).val());
			     })
			     
			      $("input[name='limit_"+type+"']").each(function(){
					 limits =limits  + $(this).val()+ ",";
					 limitsArr.push( $(this).val());
			      })
			     
			      $("input[name='totalRate_"+type+"']").each(function(){
			        	totalRate =totalRate  + $(this).val()+ ",";
			        	totalRateArr.push( $(this).val());
			      })
			        $("input[name='bankRate_"+type+"']").each(function(){
			        	bankRate =bankRate  + $(this).val()+ ",";
			        	bankRateArr.push( $(this).val());
			      })
			        $("input[name='term_"+type+"']").each(function(){
			        	term =term  + $(this).val()+ ",";
			        	termArr.push( $(this).val());
			      })
			      
			      $("input[name='productName_"+type+"']").each(function(){ 
			    	  productNamesArr.push( $(this).val());
			     })
			     
			      for(var i = 0 ; i < productNamesArr.length;i++){
			    	  if (limitsArr.length==0||limitsArr[i]==undefined ||limitsArr[i] == "") {
							new $.flavr({
								modal : false,
								content : "建议额度不能为空！"
							});
							return;
						}
						if (effectStartTimesArr.length == 0||effectEndTimesArr.length == 0
								||effectStartTimesArr[i]==undefined ||effectEndTimesArr[i]==undefined 
								|| effectStartTimesArr[i] == ""||effectEndTimesArr[i] == "") {
							new $.flavr({
								modal : false,
								content : "有效时间不能为空！"
							});
							return;
						}
						
						if (totalRateArr.length == 0||totalRateArr.length == 0
								||totalRateArr[i]==undefined ||totalRateArr[i] == ""||!isNumRate(totalRateArr[i])) {
							new $.flavr({
								modal : false,
				                content:"总利率是取值范围【0,100】，小数位不超过3位的数！"
							});
							return;
						}
						
						if (bankRateArr.length == 0||bankRateArr.length == 0
								||bankRateArr[i]==undefined || bankRateArr[i] == ""||!isNumRate(bankRateArr[i])) {
							new $.flavr({
								modal : false,
				                content:"银行利率是取值范围【0,100】，小数位不超过3位的数！"
							});
							return;
						}
						
						if(parseInt(totalRateArr[i])<parseInt( bankRateArr[i])){
							new $.flavr({
				                modal: false,
				                content:"总利率不能小于银行利率，重新填写！"
				            });
				            return;
						}
						
						if (termArr.length == 0||termArr.length == 0
								||termArr[i]==undefined || termArr[i] == ""||!isDNumber(termArr[i])||termArr[i] >180) {
							new $.flavr({
								modal : false,
								content:"期限为【0,180】的整数！"
							});
							return;
						}
			
					
			    	  str = str + "审批产品：" + productNamesArr[i] + "，建议额度："
						+ limitsArr[i] + "w，有效时间："
						+ effectStartTimesArr[i] + "到"
						+ effectEndTimesArr[i] + "；"+"总利率："+totalRateArr[i]+"；"+"银行利率："+bankRateArr[i]+"；"+"期限："+termArr[i]+"；"
			      }
				 
				  $("input[name='econGoodsPk_bt']").each(function(){
						 pk =pk  + $(this).val()+ ",";
				     })
				     
				    params['effectStartTimesBt'] = effectStartTimes;
					params['effectEndTimesBt'] = effectEndTimes;
					params['limitsBt'] = limits;
					params['totalRateBt'] = totalRate;
					params['bankRateBt'] = bankRate;
					params['termBt'] = term;
					params['econGoodsPkBt']= pk;
					if(str!=""){
						 str  = "（" + str + "）"
					 }
				}

				params['remarks']=  $("#remarks").val() + str ;
				params['taskId']= $("#taskId").val();
				params['state']= status;
				params['pk']= $("#econCustmerPk").val();
				$.ajax({
					type : 'POST',
					url : './updateAuditEconomCust.do',
					data : params,
					dataType : 'json',
					success : function(data) {
						new $.flavr({
							modal : false,
							content : data.msg
						});
						setTimeout(function() {
							if (data.success) {
								window.location.href = getRootPath() + "/approveTask.do";
							}
						}, 1000);
			
					}
				});
}

/**
 * 资料录入进度
 * @param processInstanceId
 */
function  getProcess (processInstanceId){
	$.ajax({
		type : 'POST',
		url : './searchProcess.do',
		data : {
			processInstanceId : processInstanceId
		},
		dataType : 'json',
		success : function(data) {
			for(var i=0 ; i<data.length;i++){
				$("#li_"+data[i].key).addClass("active");
				$("#time_"+data[i].key).html(data[i].time);
				
			}
		}
	});
	
}

/**
 * 查看
 * @param econCustmerPk
 */
function check(processInstanceId){
	  	$("li.showright").removeClass("none");
	    $("li.bordershow").removeClass("width");
	    
	    var cfg = {
	    		url : './searchEconCustAuditDetailList.do?processInstanceId='+ processInstanceId,
	    		columns : columns,
	    		uniqueId : 'pk',
	    		sortName : 'insertTime',
	    		sortOrder : 'desc',
	    		toolbar : toolbar,
	    		onLoadSuccess : btnList
	    	};
		createDataGrid('hisProcessInstanceId', cfg)
		
		$.ajax({
			type : 'POST',
			url : './searchProcessDetail.do',
			data : {
                processInstanceId : processInstanceId
			},
			dataType : 'json',
			success : function(data) {
				$("#customerType").html(getCustomerType (data.customerType));
				$("#establishingTime").html(data.establishingTime+"个月");
				$("#establishingTimeScore").html(data.establishingTimeScore+"分");
				$("#lastTaxSales").html(data.lastTaxSales+"万元");
				$("#taxSalesScore").html(data.taxSalesScore+"分");
				$("#releLastTaxSales").html(data.releLastTaxSales+"万元");
				$("#amountGuara").html(data.amountGuara+"万元");
				$("#releAmountGuara").html(data.releAmountGuara+"万元");
				$("#guaraTaxSalesPer").html(data.guaraTaxSalesPer+"%");
				$("#guaraTaxSalesPerScore").html(data.guaraTaxSalesPerScore+"分");
				$("#releGuaraTaxSalesPer").html(data.releGuaraTaxSalesPer+"%");
				$("#releGuaraTaxSalesPerScore").html(data.releGuaraTaxSalesPerScore+"分");
				$("#finanAmount").html(data.finanAmount+"万元");
				$("#releFinanAmount").html(data.releFinanAmount+"万元");
				$("#releFinanAmountTaxSales").html(data.releFinanAmountTaxSales+"%");
				$("#releFinanAmountTaxSalesScore").html(data.releFinanAmountTaxSalesScore+"分");

				$("#finanAmountTaxSales").html(data.finanAmountTaxSales+"%");
				$("#finanAmountTaxSalesScore").html(data.finanAmountTaxSalesScore+"分");
				$("#finanInstitution").html(data.finanInstitution!=undefined?data.finanInstitution+"家":"0家");
				$("#finanInstitutionScore").html(data.finanInstitutionScore+"分");
				$("#deviceStatusScore").html(data.deviceStatusScore+"分");
				$("#transAmountOnline").html(data.transAmountOnline+"万元");
				
				$("#transAmountOnlineScore").html(data.transAmountOnlineScore+"分");
				$("#rawPurchaseAmountScore").html(data.rawPurchaseAmountScore+"分");
				$("#rawPurchaseIncrePer").html(data.rawPurchaseIncrePer+"%");
				$("#rawPurchaseIncrePerScore").html(data.rawPurchaseIncrePerScore+"分");
				$("#transOnlineRawPurchPer").html(data.transOnlineRawPurchPer);
				$("#transOnlineRawPurchPerScore").html(data.transOnlineRawPurchPerScore+"分");
				$("#useMonthsNum").html(data.useMonthsNum+"个月");
				$("#useMonthsNumScore").html(data.useMonthsNumScore+"分");
				$("#transOnlineRawPurchPerScore").html(data.transOnlineRawPurchPerScore+"分");

				$("#econUseMonthsNum").html(data.econUseMonthsNum+"个月");
				$("#econUseMonthsNumScore").html(data.econUseMonthsNumScore+"分");
				
				$("#econUseMonthsAmount").html(data.econUseMonthsAmount+"万元");
				$("#econUseMonthsAmountScore").html(data.econUseMonthsAmountScore+"分");
				$("#econOverdueNum").html(data.econOverdueNum+"次");
				$("#econOverdueNumScore").html(data.econOverdueNumScore+"分");
				$("#bankAnnualizAmount").html(data.bankAnnualizAmount+"万元");
				$("#annualizTaxSalesPer").html(data.annualizTaxSalesPer+"%");
				
				$("#annualizTaxSalesPerScore").html(data.annualizTaxSalesPerScore+"分");
				$("#annualizTaxAmount").html(data.annualizTaxAmount+"万元");
				$("#annualizTaxAmountScore").html(data.annualizTaxAmountScore+"分");
				$("#rawBrand").html(data.rawBrand);
				$("#rawBrandScore").html(data.rawBrandScore+"分");
				$("#rawAnnualizAmount").html(data.rawAnnualizAmount+"万元");
				$("#rawAnnualizAmountScore").html(data.rawAnnualizAmountScore+"分");
				
				$("#workshopOwnSquare").html(data.workshopOwnSquare+"平方米");
				$("#workshopRentSquare").html(data.workshopRentSquare+"平方米");
				$("#workshopAllSquare").html(data.workshopAllSquare+"m2");
				$("#workshopSquareScore").html(data.workshopSquareScore+"分");
				$("#rawBrandScore").html(data.rawBrandScore+"分");
			
				//企业生产流程
				var flowOfProduction = "";
				if(data.flowOfProduction==1){
					flowOfProduction = "一步"
				}else if(data.flowOfProduction==2){
					flowOfProduction = "两步"
				}else if(data.flowOfProduction==3){
					flowOfProduction = "三步及以上"
				}
				$("#flowOfProduction").html(flowOfProduction);
				$("#flowOfProductionScore").html(data.flowOfProductionScore+"分");
				
				//企业商业经营模式
				var  managementModel = "";
				if(data.managementModel==1){
					managementModel = "代加工";
				}else if(data.managementModel==2){
					managementModel = "自产自销";
				}else if(data.managementModel==3){
					managementModel = "自产自销+外发加工";
				}else if(data.managementModel==4){
					managementModel = "自产自销+代加工";
				}
				$("#managementModel").html(managementModel);
				$("#managementModelScore").html(data.managementModelScore+"分");
				$("#businessShopNum").html(data.businessShopNum+"家");
				$("#businessShopNumScore").html(data.businessShopNumScore+"分");
				$("#shareChangeNum").html(data.shareChangeNum+"次");
				$("#shareChangeNumScore").html(data.shareChangeNumScore+"分");
				$("#enforcedNum").html(data.enforcedNum+"次");
				$("#enforcedNumScore").html(data.enforcedNumScore+"分");
				$("#zxEnforcedNum").html(data.zxEnforcedNum+"次");
				$("#zxEnforcedNumScore").html(data.zxEnforcedNumScore+"分");
				$("#ecomCooperateTime").html(data.ecomCooperateTime+"年");
				$("#ecomCooperateTimeScore").html(data.ecomCooperateTimeScore+"分");
				$("#supplierCooperateTime").html(data.supplierCooperateTime!=undefined?data.supplierCooperateTime +"年":"年");
				$("#supplierCooperateTimeScore").html(data.supplierCooperateTimeScore+"分");
				
				$("#purcherVariety").html(data.purcherVariety);
				$("#purcherVarietyScore").html(data.purcherVarietyScore+"分");
				
				$("#manageStable").html(data.manageStable+"年");
				$("#manageStableScore").html(data.manageStableScore+"分");
				$("#consumAddup").html(data.consumAddup+"元");
				$("#averageConsum").html(data.averageConsum+"元");
				$("#consumStable").html(data.consumStable+"%");
				$("#consumStableScore").html(data.consumStableScore+"分");
				$("#industryAverageConsum").html(data.industryAverageConsum+"元");
				$("#averageIndustryConsumPer").html(data.averageIndustryConsumPer+"%");
				$("#averageIndustryConsumPerScore").html(data.averageIndustryConsumPerScore+"分");
				$("#upstreamCustomerNum").html(data.upstreamCustomerNum+"家");
				$("#upstreamCustomerBaseNum").html(data.upstreamCustomerBaseNum+"家");
				$("#upstreamCustomerStable").html(data.upstreamCustomerStable+"%");
				$("#upstreamCustStableScore").html(data.upstreamCustStableScore+"分");
				$("#downstreamCustomerNum").html(data.downstreamCustomerNum+"家");
				$("#downstreamCustomerBaseNum").html(data.downstreamCustomerBaseNum+"家");
				$("#downstreamCustomerStable").html(data.downstreamCustomerStable+"%");
				$("#vipCustPurchaserAddUp").html(data.vipCustPurchaserAddUp+"万元");
				$("#inputTaxAmount").html(data.inputTaxAmount+"万元");
				$("#upstreamVipCustStable").html(data.upstreamVipCustStable+"%");
				$("#upstreamVipCustStableScore").html(data.upstreamVipCustStableScore+"分");
				$("#vipCustomerSales").html(data.vipCustomerSales+"万元");
				$("#downstreamCustStableScore").html(data.downstreamCustStableScore+"分");
				$("#downstreamCustSalesStable").html(data.downstreamCustSalesStable+"%");
				$("#downstreamCustSalesStableScore").html(data.downstreamCustSalesStableScore+"分");
				
				
				var  taxLevel = "";
				if(data.taxLevel == 1){
					taxLevel = "A级";
				}else if (data.taxLevel == 2){
					taxLevel = "B级";
				}else if (data.taxLevel == 3){
					taxLevel = "C级"
				}else if (data.taxLevel == 4){
					taxLevel = "D级"
				}
				$("#taxLevel").html(taxLevel);
				
				$("#taxLevelScore").html(data.taxLevelScore+"分");
				$("#platformSales").html(data.platformSales+"万元");
				$("#lineOfCredit").html(data.lineOfCredit+"万元");
				$("#platformProfitPer").html(data.platformProfitPer+"%");
				$("#allScore").html(data.allScore+"分");
				
				 //设备概况
			    $("#deviceStatus").html(deviceStatus(data.deviceStatus))
			    $("#otherDeviceStatus").html(otherdeviceStatus(data.otherDeviceStatus))
			    //原料累计采购金额
			    $("#rawPurchaseAmount").html(rawPurchaseAmount(data.rawPurchaseAmount))
			    //单月企业能耗
			    $("#consumPerMonth").html(consumPerMonth(data.consumPerMonth))
			}
		});
	
	
}


function getCustomerType(type){
	var str = "";
	if( type!="undefined"&&type!=""){
		if(type==1){
			str = "一般客户";
		}else if(type==2){
			str = "重点客户";
		}
	}
		return str ;
}