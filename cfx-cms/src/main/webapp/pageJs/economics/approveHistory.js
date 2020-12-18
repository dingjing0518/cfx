var companyPk = $("#companyPk").val(); 
$(function() {
	var cfg = {
			url : './searchApproveHistoryList.do?companyPk='+ $("#companyPk").val(),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			onLoadSuccess : change
			
		};
	
	createDataGrid('approveId', cfg);
	
	$('#approveId').on('click-row.bs.table', function (e, row, element)   
	    {  	
			$('.success').removeClass('success');//去除之前选中的行的，选中样式
			$(element).addClass('success');//添加当前选中的 success样式用于区别
	        //历史记录资料详情
	        onclickInfo(row);
	    });  
})

var columns = [
            		{
            			title : '申请产品',
            			field : 'productName',
            			width : 20,
            			sortable : true,
            			
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
    				}, {
            			title : '申请时间',
            			field : 'insertTime',
            			width : 20,
            			sortable : true
            		}, {
            			title : '审批时间',
            			field : 'approveTime',
            			width : 20,
            			sortable : true
            		} ];

/**
 * 金融中心-申请记录 历史记录资料详情
 */
function onclickInfo(row) {
	
	$("#approveForm").find("input,select").each(function(){
		if(!$(this).is(":hidden")){
			$(this).val("");
		}
	});
	
	$("#approveForm").find("label").each(function(){
		 if($(this).hasClass('score')){
			$(this).text("");
		}
	});
	 
	$("#companyName").text(row.companyName);
	$("#contacts").text(row.contacts);
	$("#contactsTel").text(row.contactsTel);
	$("#productName").text(row.productName);
	$("#insertTime").text(row.insertTime);
	$("#econCustmerPk").val(row.econDatumPk);
	$("#addnew").html("");
	$("#addnew2").html("");
	
	$("#qiyejingyingziliao").text("");
	$("#xianxiajiaoyi").text("");
	$("#shouxinguanli").text("");
	$("#danbaoguanli").text("");
	$("#buchongziliao").text("");
	$("#down1").hide();
	$("#down2").hide();
	$("#down3").hide();
	$("#down4").hide();
	$("#down5").hide();
	
	//遍历审批产品
	var test = "";
	if(row.content!=undefined&&row.content.length>0){
		var content = JSON.parse(row.content);
		for(var  i=0 ; content.length>i ; i++ ){
			var  suggestAmount = typeof(content[i].suggestAmount)=="undefined"?0:content[i].suggestAmount;
			var effectStartTime = typeof(content[i].effectStartTime)=="undefined"?"":content[i].effectStartTime.split(" ")[0];
			var effectEndTime = typeof(content[i].effectEndTime)=="undefined"?"":content[i].effectEndTime.split(" ")[0];
			var economicsGoodsName = typeof(content[i].economicsGoodsName)=="undefined"?"":content[i].economicsGoodsName;
			var bankName = typeof(content[i].bankName)=="undefined"?"":content[i].bankName;

			test  =  test +' <div class="col-ms-12" style="height: 33px; display: block;">'+
			'<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;">审批产品：'+
			'<label class="control-label" >'+economicsGoodsName+'</label></label>'+
			'<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;">产品金额：'+
			'<label class="control-label" >'+suggestAmount+ ' </label>W</label>'+
			'<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;width:auto;">所属银行：'+
			'<label class="control-label" >'+bankName+ ' </label></label>'+
			'<label for="inputEmail3" class="col-sm-3 control-label" style="text-align: right;">有效时间：'+
			'<label class="control-label" >'+effectStartTime+'</label></label>'+
			'<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">至'+
			'<label class="control-label" >'+effectEndTime+'</label></label></div >';
		}
	}
	
	$("#editHtml").html(test);
	if(row.datumContent!=""&& row.datumContent!=undefined){
	var  datumContent = JSON.parse(row.datumContent);
	if( datumContent.length>0){
		for(var  i=0 ; datumContent.length>i ; i++ ){
			//企业经营资料
			if(datumContent[i].datumType==1){
				showdatumType1(datumContent[i]);
			}
			//线下交易管理
			if(datumContent[i].datumType==2){
				showdatumType2(datumContent[i]);
			}
			//授信管理
			if(datumContent[i].datumType==3){
				showdatumType3(datumContent[i]);
			}
			//担保管理
			if(datumContent[i].datumType==4){
				showdatumType4(datumContent[i]);
			}
			//补充资料
			if(datumContent[i].datumType==5){
				showdatumType5(datumContent[i]);
			}
		}
	}
	}
}


/**
 * 历史记录资料详情-企业经营资料 table页面切换显示
 * @param ruleJson
 */
function  showdatumType1(ruleJson){
	$("#addnew").html("");
	if(ruleJson.rule!=null &&ruleJson.rule  != undefined){
		var	rules = JSON.parse( ruleJson.rule);
		$("input[name='productionType']:checked").val(rules.productionType);
		$("#capacityId").val(rules.capacity);
		
		//循环查询设备概况
		if(rules.rule!=null &&rules.rule  != undefined){

		var  ruleJson1 = rules.rule;
		$("#operationNumber").val(ruleJson1.length);
		operationNumber = ruleJson1.length;//如果新增输入框从最后一个添加\
		var test = "";
		for(var i = 0 ;i < ruleJson1.length; i++){
			
			var number = i+1;
			if(i+1 != 1){//等于1时页面上已存在，不需要生成
				test   = test + '<div class="col-ms-12" style="line-height: 40px; display: block;height:52px;">'
				+ '<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">&nbsp;</label>'
				+ '<label class="control-label col-sm-2" style="width:120px;">'
				+ '<select class="form-control col-sm-3" name="deviceTypeId" id="deviceTypeId_'
				+ number
				+ '" required="true" disabled="disabled">'
				+ '<option value="">--请选择--</option>'
				+ '</select>'
				+ '</label>'
				+ '<label class="control-label col-sm-2" style="width:120px;">'
				+ '<select class="form-control col-sm-3" name="useTypeId" id="useTypeId_'
				+ number
				+ '" required="true" disabled="disabled">'
				+ '<option value="">--请选择--</option>'
				+ '</select>'
				+ '</label>'
				+ '<label class="control-label col-sm-2" style="width:120px;">'
				+ '<input type="text" class="form-control" name="machineNumber" id="machineNumber_'
				+ number
				+ '" required="true" readonly="readonly">'
				+ '</label>'
				+ '<label class="control-label" style="float:left;">台，已用'
				+ '</label>'
				+ '<label class="control-label col-sm-2" style="width:120px;">'
				+ '<input type="text" class="form-control" name="machineYear" id="machineYear_'
				+ number
				+ '" required="true" readonly="readonly">'
				+ '</label>'
				+ '<label class="control-label" style="float:left;">年'
				+ '</label></div>';
			}
		}
		$("#addnew").html(test);
		for(var i = 0 ;i < ruleJson1.length; i++){
			selectPutValue(i+1,ruleJson1[i]);
		}
		}
	}
	
	$("#remarks6").val(ruleJson.remarks);
	$("#qiyejingyingziliao").val(ruleJson.url);
	$("#qiyejingyingziliao").text(subUrl(ruleJson.url,1));
	var palceHtml = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< companyPlace.length; i++){
		 if(companyPlace[i].key == ruleJson.rule.companyPlace){
			 palceHtml +="<option value="+companyPlace[i].key+" selected>"+companyPlace[i].value+"</option>";
		 }else{
			 palceHtml +="<option value="+companyPlace[i].key+">"+companyPlace[i].value+"</option>";
		 }
		}
	$("#companyPlaceId").empty().append(palceHtml);
	

}

/**
 * 历史记录资料详情-线下交易管理 table页面切换显示
 * @param data
 */
function  showdatumType2(data){
	//已存在查询出来之后显示到页面
	$("#remarks1").val(data.remarks);
	$("#xianxiajiaoyi").val(data.url);
	$("#xianxiajiaoyi").text(subUrl(data.url,2));
	//循环查询交易管理数据
	
	if(data.rule!=undefined){
		var rules = JSON.parse(data.rule).rule;
		if(rules!=""&&rules.length>0){
		$("#amountNumber").val(rules.length);
		if($("#tradeYear_"+rules.length+"").val() == undefined){//判断是否已经生成输入框
			var  test = "";
			for(var i = 0 ;i < rules.length; i++){
				if(i+1 != 1){//等于1时页面上已存在，不需要生成
					var tradeNumber = i+1;
					test = test +'<div class="col-ms-12" style="height: 40px;display: block;">'
						+ '<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">年份:</label>'
						+ '<label class="control-label col-sm-1">'
						+ '<input class="form-control col-sm-3 form-dateyear" id="tradeYear_'
						+ tradeNumber
						+ '" name="tradeYear" required="true" readonly></input>'
						+ '</label>'
						+ '<label class="control-label" style="float:left;">销售规模:'
						+ '</label>'
						+ '<label class="control-label col-sm-2"  style="width:80px;">'
						+ '<input class="form-control col-sm-3" id="salesVolume_'
						+ tradeNumber
						+ '" name="salesVolume" required="true" readonly></input>'
						+ '</label>'
						+ '<label class="control-label" style="float:left;">开票总额:'
						+ '</label>'
						+ '<label class="control-label col-sm-2"  style="width:80px;">'
						+ '<input class="form-control col-sm-3" id="invoiceAmount_'
						+ tradeNumber
						+ '" name="invoiceAmount" required="true" readonly></input><div style=" float: right;margin-top: -35px;margin-right: -10px;">w</div>'
						+ '</label><label class="control-label" style="float:left;">销售总额:'
						+ '</label>'
						+ '<label class="control-label col-sm-2"  style="width:80px;">'
						+ '<input class="form-control col-sm-3" id="salesAmount_'
						+ tradeNumber
						+ '" name="salesAmount" required="true" readonly></input><div style=" float: right;margin-top: -35px;margin-right: -10px;">w</div>'
						+ '</label></div>';
				}
			}
			$("#addnew2").html(test);
			for(var i = 0 ;i < rules.length; i++){
				selectedPutTradeValue(i+1,rules[i]);
			}
		}else{
			selectedPutTradeValue(1, rules[0]);
		}
		}
		
	}
	
}

/**
 * 历史记录资料详情-授信管理 table页面切换显示
 * @param data
 */
function  showdatumType3(data){
	if(data.rule!=null&&data.rule!=undefined){
	var ruleJson =JSON.parse( data.rule);
	$("#creditYear").val(ruleJson.creditYear);
	$("#creditAmount").val(ruleJson.creditAmount);
	$("#creditNumber").val(ruleJson.creditNumber);
	$("#bankNumber").val(ruleJson.businessBankNumber);
	$("#buildingAmount").val(ruleJson.buildingAmount);
	$("#businessBankNumber").val(ruleJson.bankNumber);
	$("#buildingCreditAmount").val(ruleJson.buildingCreditAmount);
	}
	//已存在查询出来之后显示到页面
	$("#remarks2").val(data.remarks);
	$("#shouxinguanli").text(subUrl(data.url,3));
	$("#shouxinguanli").val(data.url);
}



/**
 * 历史记录资料详情-担保管理 table页面切换显示
 * @param data
 */
function  showdatumType4(data){
	if(data.rule!=null&& data.rule!=undefined){
		var ruleJson = JSON.parse(data.rule);
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
	
	//已存在查询出来之后显示到页面
	$("#remarks3").val(data.remarks);
	
	$("#danbaoguanli").text(subUrl(data.url,4));
	$("#danbaoguanli").val(data.url);
}

/**
 * 历史记录资料详情-补充资料 table页面切换显示
 * @param data
 */
function  showdatumType5(data){
		$("#remarks4").val(data.remarks);
		$("#buchongziliao").text(subUrl(data.url,5));
		$("#buchongziliao").val(data.url);
	}


function addProductInput(number,content){
	$("#productName_"+number).text(content.productName);
	$("#limit_"+number).text(content.productName);
	$("#effectStartTime_"+number).text(content.effectStartTime);
	$("#effectEndTime_"+number).text(content.effectEndTime);
}

//TABLE1111111111
/**
 * 展示不同资料table页面下来选择框内容
 * @param number
 * @param rule
 */
function selectPutValue(number,rule){
	var deviceHtml = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< deviceType.length; i++){
		 if(rule != null && deviceType[i].key == rule.deviceType){
			 deviceHtml +="<option value="+deviceType[i].key+" selected>"+deviceType[i].value+"</option>"; 
		 }
		}
	$("#deviceTypeId_"+number+"").empty().append(deviceHtml);
	 var usehtml = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< useType.length; i++){
		 if(rule != null && useType[i].key == rule.useType){
			 usehtml +="<option value="+useType[i].key+" selected>"+useType[i].value+"</option>"; 
		 }
		}
	$("#useTypeId_"+number+"").empty().append(usehtml);
	$("#machineNumber_"+number+"").val(rule == null?"":rule.machineNumber);
	$("#machineYear_"+number+"").val(rule == null?"":rule.machineYear);
}

//TABLE1111111111
function addInput(operationNumber){
	$('#addnew').after();
}


/**
 * 增加输入框
 * @param tradeNumber
 */
function addAmountInput(tradeNumber){
	$('#addnew2').after();
}


//TABLE22222222222
/**
 * 不同资料table页面下来选择框内容赋值
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
 * 获得改变选择框并获取数据
 */
function change(){
	$("#approveId tbody").find("tr").eq(0).addClass('success');
	 var index = $('#approveId').find('tr.success').data('index');//获得选中的行
	onclickInfo($('#approveId').bootstrapTable('getData')[index]);//返回选中行所有数据

}

/**
 * 查看图片
 * @param urlPk
 */
function op(urlPk) {
	var c_url = $("#"+urlPk).val();
	if (c_url.indexOf(".jpg") != -1 || c_url.indexOf(".png") != -1
			|| c_url.indexOf(".pdf") != -1) {// 判断是否存在图片
			window.open(c_url);
	}
}

/**
 * 返回图片url复制到页面并显示
 * @param url
 * @param flag
 * @returns {*}
 */
function subUrl (url,flag){
	if(url!=null||url!=""){
		var index = url .lastIndexOf("\/");  
		url  = url .substring(index + 1, url .length);
		$("#down"+flag).show();
	}else{
		$("#down"+flag).hide();
	}
	return url
	
}
