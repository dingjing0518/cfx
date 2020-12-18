$(function() {

	var operationNumber = 1;//记录添加输入框下标
	var tradeNumber = 1;
	selectedAmount(tradeNumber);
	selectedOperation(operationNumber);//首次进入页面初始加载为默认第一个table
	selectOrionValue(operationNumber)
	var palceHtml = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< companyPlace.length; i++){
		 palceHtml +="<option value="+companyPlace[i].key+">"+companyPlace[i].value+"</option>";
		}
	 $("#companyPlaceId").empty().append(palceHtml);
	//点击新增时按钮时添加
	$ ('#add').click (function (){
		
		var inputOpNumber = $("#operationNumber").val();
		operationNumber = inputOpNumber ;
		operationNumber++;
		addInput(operationNumber);
	   	selectOrionValue(operationNumber);//输入框原始内容
	   	$("#operationNumber").val(operationNumber);
	});
	
	//点击新增时按钮时添加
	$ ('#add2').click(function (){
		var inputAmountNumber = $("#amountNumber").val();
		tradeNumber = inputAmountNumber;
		tradeNumber++;
		addAmountInput(tradeNumber);
		$("#amountNumber").val(tradeNumber);
	   });
	
});

/**
 * 动态添加企业资料 设备状况 条数
 * @param operationNumber
 */
function addInput(operationNumber){
	$('#addnew').append('<div class="col-ms-12" style="line-height:40px; display: block;">' +
			'<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">&nbsp;</label>' +
			'<label class="control-label col-sm-2">'+
			  '<select class="form-control col-sm-3" name="deviceTypeId" id="deviceTypeId_'+operationNumber+'" required="true">'
					+'<option value="">--请选择--</option>'
				+'</select>'+
			'</label>'+
			'<label class="control-label col-sm-2">'+
			'<select class="form-control col-sm-3" name="useTypeId" id="useTypeId_'+operationNumber+'" required="true">'
				+'<option value="">--请选择--</option>'
			+'</select>'+
			'</label>'+
			'<label class="control-label col-sm-2">'+
				'<input type="text" class="form-control" name="machineNumber" id="machineNumber_'+operationNumber+'" required="true">'+
			'</label>'+
			'<label class="control-label" style="float:left;">台，已用'+	
			'</label>'+
			'<label class="control-label col-sm-2">'+
				'<input type="text" class="form-control" name="machineYear" id="machineYear_'+operationNumber+'" required="true">'+
			'</label>'+
			'<label class="control-label" style="float:left;">年' +
		'</label>'+
		'</div>');
}

/**
 * 动态增加输入框
 * @param tradeNumber
 */
function addAmountInput(tradeNumber){
	$('#addnew2').append('<div class="col-ms-12" style="height: 40px;display: block;">'+
			'<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">年份:</label>'+
			'<label class="control-label col-sm-1">'+
			  '<input class="form-control form-dateday col-sm-3" id="tradeYear_'+tradeNumber+'" name="tradeYear" required="true"></input>'+
			'</label>'+
			'<label class="control-label" style="float:left;">销售规模:'+	
			'</label>'+
			'<label class="control-label col-sm-2">'+
			  '<input class="form-control col-sm-3" id="salesVolume_'+tradeNumber+'" name="salesVolume" required="true"></input>'+
			'</label>'+
			'<label class="control-label" style="float:left;">开票总额:'+
			'</label>'+
			'<label class="control-label col-sm-2">'+
			  '<input class="form-control col-sm-3" id="invoiceAmount_'+tradeNumber+'" name="invoiceAmount" required="true"></input>'+
			'</label>'+
			'<label class="control-label" style="float:left;">销售总额:'	+
			'</label>'+
			'<label class="control-label col-sm-2">'+
			  '<input class="form-control col-sm-3" id="salesAmount_'+tradeNumber+'" name="salesAmount" required="true"></input>'+
			'</label>'+
		'</div>');
}

/**
 * 企业经营资料table
 * @param operationNumber
 */
function selectedOperation(operationNumber){
	$.ajax({
		type : "POST",
		url : "./getByEconCustPk.do",
		data : {
			pk:$("#econCustmerPk").val(),
			datumType:$("#datumType").val()
		},
		dataType : "json",
		success : function(data) {
			if (data != "0" && data.rule !="" && data.rule != null) {
				var ruleJson = JSON.parse(data.rule);
				//已存在查询出来之后显示到页面
				$("input[name='productionType']:checked").val(ruleJson.productionType);
				$("#capacityId").val(ruleJson.capacity);
				$("#remarks").val(data.remarks);
				$("#pk").val(data.pk);
				var palceHtml = "<option value=''>--请选择--</option>";
				 for(var i = 0; i< companyPlace.length; i++){
					 if(companyPlace[i].key == ruleJson.rule.companyPlace){
						 palceHtml +="<option value="+companyPlace[i].key+" selected>"+companyPlace[i].value+"</option>";
					 }else{
						 palceHtml +="<option value="+companyPlace[i].key+">"+companyPlace[i].value+"</option>";
					 }
					}
				$("#companyPlaceId").empty().append(palceHtml);
				//循环查询设备概况
				var rules = ruleJson.rule;
				$("#operationNumber").val(rules.length);
				operationNumber = rules.length;//如果新增输入框从最后一个添加
				for(var i = 0 ;i < rules.length; i++){
					if(i+1 != 1){//等于1时页面上已存在，不需要生成
						addInput(i+1);
					}
					selectPutValue(i+1,rules[i]);
				}
			} 
			if(data == "0"){
				selectPutValue(operationNumber,null);//没有数据
			}
		}
	});
}

/**
 * 动态展示多条企业资料 设备状况
 * @param number
 * @param rule
 */
function selectPutValue(number,rule){
	
	var deviceHtml = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< deviceType.length; i++){
		 if(rule != null && deviceType[i].key == rule.deviceType){
			 deviceHtml +="<option value="+deviceType[i].key+" selected>"+deviceType[i].value+"</option>"; 
		 }else{
			 deviceHtml +="<option value="+deviceType[i].key+">"+deviceType[i].value+"</option>";
		 }
		}
	$("#deviceTypeId_"+number+"").empty().append(deviceHtml);
	
	 var usehtml = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< useType.length; i++){
		 if(rule != null && useType[i].key == rule.useType){
			 usehtml +="<option value="+useType[i].key+" selected>"+useType[i].value+"</option>"; 
		 }else{
			 usehtml +="<option value="+useType[i].key+">"+useType[i].value+"</option>";
		 }
		}
	$("#useTypeId_"+number+"").empty().append(usehtml);
	$("#machineNumber_"+number+"").val(rule == null?"":rule.machineNumber);
	$("#machineYear_"+number+"").val(rule == null?"":rule.machineYear);
}

/**
 * 添加原始输入框
 * @param operationNumber
 */
function selectOrionValue(operationNumber){
	
	var deviceHtml = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< deviceType.length; i++){
			 deviceHtml +="<option value="+deviceType[i].key+">"+deviceType[i].value+"</option>";
		}
	$("#deviceTypeId_"+operationNumber+"").empty().append(deviceHtml);
	
	 var usehtml = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< useType.length; i++){
		 usehtml +="<option value="+useType[i].key+">"+useType[i].value+"</option>";
		}
	$("#useTypeId_"+operationNumber+"").empty().append(usehtml);
}


/**
 * 线下交易管理 动态展示条件输入框的值
 * @param number
 * @param rule
 */
function selectedPutTradeValue(number,rule){
	$("#tradeYear_"+number+"").val(rule == null?"":rule.tradeYear);
	$("#salesVolume_"+number+"").val(rule == null?"":rule.salesVolume);
	$("#invoiceAmount_"+number+"").val(rule == null?"":rule.invoiceAmount);
	$("#salesAmount_"+number+"").val(rule == null?"":rule.salesAmount);
}

/**
 * 通过或驳回
 */
function submitRemarks(){
//if(valid("#operationDatum")){
	
	var number = parseInt($("#operationNumber").val())+1;//记录增加的输入框数量
	var ruleJson = "[";
	for(var i = 1; i < number; i++){
		
		var deviceType = $("#deviceTypeId_"+i+"").val();
		
		var useType = $("#useTypeId_"+i+"").val();
		
		var machineNumber = $("#machineNumber_"+i+"").val();
		
		var machineYear = $("#machineYear_"+i+"").val();
		
		ruleJson += "{\"deviceType\":\""+deviceType+"\",\"useType\":\""+useType+"\",\"machineNumber\":\""+machineNumber+"\",\"machineYear\":\""+machineYear+"\"},";
	}
	ruleJson = ruleJson.substring(0,ruleJson.length-1);
	ruleJson +="]";
	//经营场所权属情况
	var companyPlace = $("#companyPlaceId").val();
	//说明
	var remarks = $("#remarks").val();
	//产能
	var capacity = $("#capacityId").val();
	
	var companyPk = $("#companyPk").val();
	var companyName = $("#companyName").text();
	var contacts = $("#contacts").text();
	var contactsTel = $("#contactsTel").text();
	
	//企业生产类型
	var productionType = $("input[name='productionType']:checked").val();
	
	var ruleList = "{\"companyPlace\":\""+companyPlace+"\",\"capacity\":\""+capacity+"\",\"productionType\":\""+productionType+"\",\"rule\":"+ruleJson+"}";
	
	var params = {
			pk:$("#pk").val(),
			econCustmerPk:$("#econCustmerPk").val(),
			companyPk : companyPk,
			companyName:companyName,
			contacts:contacts,
			contactsTel:contactsTel,
			rule :ruleList,
			datumType:$("#datumType").val(),
			remarks:remarks,
		};
	
	submit(params);
//}
}
//TABLE2222222
function selectedAmount(tradeNumber){
	
}
//TABLE2222222
function submitAmount(){
	var number = parseInt($("#amountNumber").val())+1;//记录增加的输入框数量
	var ruleJson = "[";
	for(var i = 1; i < number; i++){
		
		var tradeYear = $("#tradeYear_"+i+"").val();//年份
		
		var salesVolume = $("#salesVolume_"+i+"").val();//销售规模
		
		var invoiceAmount = $("#invoiceAmount_"+i+"").val();//开票总额
		
		var salesAmount = $("#salesAmount_"+i+"").val();//销售总额
		ruleJson += "{\"tradeYear\":\""+tradeYear+"\",\"salesVolume\":\""+salesVolume+"\",\"invoiceAmount\":\""+invoiceAmount+"\",\"salesAmount\":\""+salesAmount+"\"},";
	}
	ruleJson = ruleJson.substring(0,ruleJson.length-1);
	ruleJson +="]";
	var remarks = $("#remarks1").val();
	var ruleList = "{\"rule\":"+ruleJson+"}";
	var companyPk = $("#companyPk").val();
	var companyName = $("#companyName").text();
	var contacts = $("#contacts").text();
	var contactsTel = $("#contactsTel").text();
	var params = {
			pk:$("#pk").val(),
			econCustmerPk:$("#econCustmerPk").val(),
			companyPk : companyPk,
			companyName:companyName,
			contacts:contacts,
			contactsTel:contactsTel,
			rule :ruleList,
			datumType:$("#datumType").val(),
			remarks:remarks,
		};
	submit(params);
}

function submit(params){
	$.ajax({
		type : "POST",
		url : "./updateEconomicsCustomer.do",
		data : params,
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
			}
		}
	});
}

/**
 * 线下交易数据Table
 * @param type
 */
function changeTitles2(type){
	$("#datumType").val(type);
	$.ajax({
		type : "POST",
		url : "./getByEconCustPk.do",
		data : {
			pk:$("#econCustmerPk").val(),
			datumType:$("#datumType").val()
		},
		dataType : "json",
		success : function(data) {
			if (data != "0" && data.rule !="" && data.rule != null) {
				var ruleJson = JSON.parse(data.rule);
				//已存在查询出来之后显示到页面
				$("#remarks1").val(data.remarks);
				$("#pk").val(data.pk);
				//循环查询交易管理数据
				var rules = ruleJson.rule;
				$("#amountNumber").val(rules.length);
				if($("#tradeYear_"+rules.length+"").val() == undefined){//判断是否已经生成输入框
					for(var i = 0 ;i < rules.length; i++){
						if(i+1 != 1){//等于1时页面上已存在，不需要生成
							addAmountInput(i+1);
						}
						selectedPutTradeValue(i+1,rules[i]);
					}
				}
			} 
			if(data == "0"){
				selectedPutTradeValue(1,null);
				$("#pk").val("");
			}
		}
	});
}

/**
 * 企业经营管理Table
 * @param type
 */
function changeTitles1(type){
	$("#datumType").val(type);
	$.ajax({
		type : "POST",
		url : "./getByEconCustPk.do",
		data : {
			pk:$("#econCustmerPk").val(),
			datumType:$("#datumType").val()
		},
		dataType : "json",
		success : function(data) {
			if (data != "0" && data.rule !="" && data.rule != null) {
				var ruleJson = JSON.parse(data.rule);
				//已存在查询出来之后显示到页面
				$("input[name='productionType']:checked").val(ruleJson.productionType);
				$("#capacityId").val(ruleJson.capacity);
				$("#remarks").val(data.remarks);
				$("#pk").val(data.pk);
				var palceHtml = "<option value=''>--请选择--</option>";
				 for(var i = 0; i< companyPlace.length; i++){
					 if(companyPlace[i].key == ruleJson.companyPlace){
						 palceHtml +="<option value="+companyPlace[i].key+" selected>"+companyPlace[i].value+"</option>";
					 }else{
						 palceHtml +="<option value="+companyPlace[i].key+">"+companyPlace[i].value+"</option>";
					 }
					}
				$("#companyPlaceId").empty().append(palceHtml);
				
				//循环查询设备概况
				var rules = ruleJson.rule;
				$("#operationNumber").val(rules.length);
				if($("#deviceTypeId_"+rules.length+"").val() == undefined){//判断是否已经生成输入框
					for(var i = 0 ;i < rules.length; i++){
						if(i+1 != 1){//等于1时页面上已存在，不需要生成
							addInput(i+1);
						}
						selectPutValue(i+1,rules[i]);
					}
				}
			} 
			if(data == "0"){
				selectPutValue(1,null);//没有数据
				$("#pk").val("");
			}
			
		}
	});	
}


/**
 * 线上交易数据Table
 * @param type
 */
function changeTitles3(type){
	$("#datumType").val(type);
	$.ajax({
		type : "POST",
		url : "./getByEconCustPk.do",
		data : {
			pk:$("#econCustmerPk").val(),
			datumType:$("#datumType").val()
		},
		dataType : "json",
		success : function(data) {
			if (data != "0" && data.rule !="" && data.rule != null) {
				var ruleJson = JSON.parse(data.rule);
				//已存在查询出来之后显示到页面
				$("#remarks2").val(data.remarks);
				$("#pk").val(data.pk);
				$("#creditAmount").val(ruleJson.creditAmount);
				$("#creditNumber").val(ruleJson.creditNumber);
				$("#bankNumber").val(ruleJson.bankNumber);
				$("#buildingAmount").val(ruleJson.buildingAmount);
				$("#businessBankNumber").val(ruleJson.businessBankNumber);
				
			} 
			if(data == "0"){
				$("#pk").val("");
			}
		}
	});	
}

/**
 * 担保管理Tbale
 * @param type
 */
function changeTitles5(type){
	$("#datumType").val(type);
	$.ajax({
		type : "POST",
		url : "./getByEconCustPk.do",
		data : {
			pk:$("#econCustmerPk").val(),
			datumType:$("#datumType").val()
		},
		dataType : "json",
		success : function(data) {
			if (data != "0" && data.rule !="" && data.rule != null) {
				//var ruleJson = JSON.parse(data.rule);
				//已存在查询出来之后显示到页面
				$("#remarks4").val(data.remarks);
				$("#pk").val(data.pk);
			} 
			if(data == "0"){
				$("#pk").val("");
			}
		}
	});	
}

function submitCredit(){
	
	var creditAmount = $("#creditAmount").val();
	var creditNumber = $("#creditNumber").val();
	var bankNumber = $("#bankNumber").val();
	var buildingAmount = $("#buildingAmount").val();
	var buildingCreditAmount = $("#buildingCreditAmount").val();
	var businessBankNumber = $("#businessBankNumber").val();
	var creditYear = $("#creditYear").val();
	var ruleJson = "{\"creditAmount\":\""+creditAmount+"\",\"creditNumber\":\""+creditNumber+"\",\"bankNumber\":\""+bankNumber+"\",\"creditYear\":\""+creditYear+"\"," +
			"\"buildingAmount\":\""+buildingAmount+"\",\"buildingCreditAmount\":\""+buildingCreditAmount+"\",\"businessBankNumber\":\""+businessBankNumber+"\"}";
	var remarks = $("#remarks2").val();
	var companyPk = $("#companyPk").val();
	var companyName = $("#companyName").text();
	var contacts = $("#contacts").text();
	var contactsTel = $("#contactsTel").text();
	var params = {
			pk:$("#pk").val(),
			econCustmerPk:$("#econCustmerPk").val(),
			companyPk : companyPk,
			companyName:companyName,
			contacts:contacts,
			contactsTel:contactsTel,
			rule :ruleJson,
			datumType:$("#datumType").val(),
			remarks:remarks
		};
	submit(params);
}


function submitIllustrate(){
	
	var companyPk = $("#companyPk").val();
	var companyName = $("#companyName").text();
	var contacts = $("#contacts").text();
	var contactsTel = $("#contactsTel").text();
	var remarks = $("#remarks4").val();
	var params = {
			pk:$("#pk").val(),
			econCustmerPk:$("#econCustmerPk").val(),
			companyPk:companyPk,
			companyName:companyName,
			contacts:contacts,
			contactsTel:contactsTel,
			datumType:$("#datumType").val(),
			remarks:remarks
		};
	submit(params);
}

/**
 * 授信管理Table
 * @param type
 */
function changeTitles4(type){
	$("#datumType").val(type);
	$.ajax({
		type : "POST",
		url : "./getByEconCustPk.do",
		data : {
			pk:$("#econCustmerPk").val(),
			datumType:$("#datumType").val()
		},
		dataType : "json",
		success : function(data) {
			if (data != "0" && data.rule !="" && data.rule != null) {
				var ruleJson = JSON.parse(data.rule);
				//已存在查询出来之后显示到页面
				$("#remarks3").val(data.remarks);
				$("#pk").val(data.pk);
				var partnerHtml = "<option value=''>--请选择--</option>";
				 for(var i = 0; i< cooperativePartner.length; i++){
					 if(ruleJson.cooperativePartner = cooperativePartner[i].key){
						 partnerHtml +="<option value="+cooperativePartner[i].key+" selected>"+cooperativePartner[i].value+"</option>";
					 }else{
						 partnerHtml +="<option value="+cooperativePartner[i].key+">"+cooperativePartner[i].value+"</option>"; 
					 }
					}
				$("#cooperativePartner").empty().append(partnerHtml);

				$("input[name='warrantType']:checked").val(ruleJson.warrantType);
				$("#warrantYear").val(ruleJson.warrantYear);
				$("#warrantNumber").val(ruleJson.warrantNumber);
				$("#warrantAllAmount").val(ruleJson.warrantAllAmount);
				$("#warrantInvAmount").val(ruleJson.warrantInvAmount);
				$("#warrantInvoiceRatio").val(ruleJson.warrantInvoiceRatio);
			} 
			if(data == "0"){
				var partnerHtml = "<option value=''>--请选择--</option>";
				 for(var i = 0; i< cooperativePartner.length; i++){
					 partnerHtml +="<option value="+cooperativePartner[i].key+">"+cooperativePartner[i].value+"</option>"; 
					}
				$("#cooperativePartner").empty().append(partnerHtml);
				$("#pk").val("");
			}
		}
	});	
}


function submitWarrant(){

		var warrantYear = $("#warrantYear").val();
		
		var warrantNumber = $("#warrantNumber").val();
		
		var warrantAllAmount = $("#warrantAllAmount").val();
		
		var warrantInvAmount = $("#warrantInvAmount").val();
		
		var warrantInvoiceRatio = $("#warrantInvoiceRatio").val();
		
		var warrantType = $("input[name='warrantType']:checked").val();
		
		var warrantTypeName = "";
		if(warrantType == 1){
			warrantTypeName = "关注类";
		}

		var cooperativePartner = $("#cooperativePartner").val();
		var ruleJson = "{\"warrantYear\":\""+warrantYear+"\",\"warrantNumber\":\""+warrantNumber+"\",\"warrantAllAmount\":\""+warrantAllAmount+"\",\"warrantInvoiceRatio\":\""+warrantInvoiceRatio+"\"," +
				"\"warrantInvAmount\":\""+warrantInvAmount+"\",\"warrantTypeName\":\""+warrantTypeName+"\"," +
				"\"cooperativePartner\":\""+cooperativePartner+"\"}";
		var remarks = $("#remarks3").val();
	
	var companyPk = $("#companyPk").val();
	var companyName = $("#companyName").text();
	var contacts = $("#contacts").text();
	var contactsTel = $("#contactsTel").text();
	var params = {
			pk:$("#pk").val(),
			econCustmerPk:$("#econCustmerPk").val(),
			companyPk : companyPk,
			companyName:companyName,
			contacts:contacts,
			contactsTel:contactsTel,
			rule :ruleJson,
			datumType:$("#datumType").val(),
			remarks:remarks,
		};
	submit(params);
}

/**
 * 房地产抵押金额/授信总金额百分比
 */
function creditAmountChange(){
	var creditAmount = $("#creditAmount").val();
	var buildingAmount = $("#buildingAmount").val();
	if(buildingAmount != 'undefined' && creditAmount != 'undefined' && creditAmount != 0){	
	var amount = (buildingAmount/creditAmount)*100;
	$("#buildingCreditAmount").val(amount.toFixed(2));
	}
}

/**
 * 担保总金额/纳税销售收入百分比
 */
function changeWarrantChange(){
	var warrantAllAmount = $("#warrantAllAmount").val();
	var warrantInvAmount = $("#warrantInvAmount").val();
	if(warrantInvAmount != 'undefined' && warrantAllAmount != 'undefined' && warrantAllAmount != 0){	
	var amount = (warrantInvAmount/warrantAllAmount)*100;
	$("#warrantInvoiceRatio").val(amount.toFixed(2));
	}
}
