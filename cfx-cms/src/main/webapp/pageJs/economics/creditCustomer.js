$(function () {
    createDataGrid('creditCustomerId', cfg);
});


var toolbar = [];
var columns = [
    {
        title: '操作',
        field: 'pk',
        width: 100,
        formatter: function (value, row, index) {
            	var str = "";
          
                  str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_CREDIT" class="btn btn-warning" onclick="javascript:editState(\'' + value + '\',\'' + row.companyPk + '\',\'' + row.goodsType + '\'); ">授信 </button> </a>';
            
            	  if(row.creditStatus == 1){
                      str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_PASS" class="btn btn-warning" onclick="javascript:updateCreditSatuts(\'' + row.companyPk + '\',2); ">审核通过</button> </a>';

            	  }
                if(row.creditStatus == 3){
                    str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_PASS" class="btn btn-warning" onclick="javascript:updateCreditSatuts(\'' + row.companyPk + '\',2); ">审核通过</button> </a>';
                    str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_SEARCHQUATO" class="btn btn-warning" onclick="javascript:searchBankAmount(\'' + row.companyPk + '\'); ">查询银行额度</button> </a>';  
              	  	str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_MODIFYCUSTNO" class="btn btn-warning" onclick="javascript:updateBankAccount(\'' + value + '\'); ">修改银行客户号</button> </a>';  
                }
                
                if(row.creditStatus == 2){
                    str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_REFUSE" class="btn btn-warning" onclick="javascript:updateCreditSatuts(\'' + row.companyPk + '\',3); ">审核不通过</button> </a>';
                    str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_SEARCHQUATO" class="btn btn-warning" onclick="javascript:searchBankAmount(\'' + row.companyPk + '\'); ">查询银行额度</button> </a>';  
              	  	str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_MODIFYCUSTNO" class="btn btn-warning" onclick="javascript:updateBankAccount(\'' + value + '\'); ">修改银行客户号</button> </a>';  
                }
                
/*                str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_UPDATE" class="btn btn-warning" onclick="javascript:updateCredit(\'' + row.companyPk + '\'); ">更新</button> </a>';
*/
                str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_INVOICE" class="btn btn-warning" onclick="javascript:toCreditInvoice(\'' + value + '\',\'' + row.companyPk + '\',\'' + row.companyName + '\',\'' + row.creditContacts + '\',\'' + row.creditContactsTel + '\',\'' + row.productNames + '\',\'' + row.creditStatus + '\'); ">发票查询</button> </a>';

                str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="EM_CREDITCUST_BTN_EDITTAX" class="btn btn-warning" onclick="javascript:billApply(\'' + value + '\',\'' + row.taxNature + '\',\'' + row.taxAuthorityCode + '\',\'' + row.taxAuthorityName + '\',\'' + row.delegateCertUrl + '\'); ">证书申请</button> </a>';
               
                str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"    style="display:none;"  showname="EM_CREDITCUST_BTN_APPLY" class="btn btn-warning" onclick="javascript:customerEconApply(\'' + value + '\',\'' + row.companyPk + '\'); ">客户申请</button> </a>';

            return str;
        }
    
    
    },
    {
        title: '公司名称',
        field: 'companyName',
        width: 50,
        sortable: true
    },
   /* {
        title: '银行客户号',
        field: 'bankAccountNumber',
        width: 20,
        sortable: true
    },*/
    {
        title: '申请人',
        field: 'creditContacts',
        width: 20,
        sortable: true
    },
    {
        title: '手机号码',
        field: 'creditContactsTel',
        width: 20,
        sortable: true
    },
    {
        title: '金融产品',
        field: 'productNames',
        width: 20,
        sortable: true
    },
    {
        title: '审核状态',
        field: 'creditStatus',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (value == 1) {
                return "待审核";
            } else if (value == 2) {
                return "审核通过";
            } else {
                return "审核不通过";
            }
        }
    }
];
var cfg = {
    url: './creditCustomerList.do',
    columns: columns,
    uniqueId: 'pk',
    sortName: 'creditAuditTime',
    sortOrder: 'desc',
    toolbar: toolbar,
    onLoadSuccess: btnList
};

var columnTwo = [
    {
        title: '操作',
        field: 'pk',
        width: 100,
        formatter: function (value, row, index) {
        	
            var str = "";
            if(row.goodsType==1||row.goodsType==2){
            	if(row.bankContractAmount!=undefined && row.bankContractAmount!="" && row.bankContractAmount>0){
                    str += '<a class="save" href="javascript:;" style="text-decoration:none;">  '+
                    ' <button id="editable-sample_new"  showname="EM_CREDITCUST_CREDIT_BTN_EDIT" style="display:none;" class="btn btn btn-primary" onclick="editCreditGoods(\'' + value + '\',\'' + row.suggestAmount + '\',\'' 
                    + row.bankContractAmount + '\',\'' + row.platformAmount + '\',\'' + row.economicsGoodsName + '\',\'' 
                    + row.creditStartTime + '\',\'' + row.creditEndTime + '\',\'' + row.bankCreditStartTime 
                    + '\',\'' + row.bankCreditEndTime + '\',\'' + row.bankAvailableAmount + '\',\'' 
                    + row.goodsType + '\', \'' + row.totalRate + '\',\'' + row.bankRate + '\',\'' 
                    + row.term + '\',\'' + row.sevenRate + '\',\'' + row.economicsBankCompanyPk + '\', event); ">修改 </button></a>';
                }
            }else{
                str += '<a class="save" href="javascript:;" style="text-decoration:none;">  '+
                ' <button id="editable-sample_new"  showname="EM_CREDITCUST_CREDIT_BTN_EDIT" style="display:none;" class="btn btn btn-primary" onclick="editCreditGoods(\'' + value + '\',\'' + row.suggestAmount + '\',\'' 
                + row.bankContractAmount + '\',\'' + row.platformAmount + '\',\'' + row.economicsGoodsName + '\',\'' 
                + row.creditStartTime + '\',\'' + row.creditEndTime + '\',\'' + row.bankCreditStartTime 
                + '\',\'' + row.bankCreditEndTime + '\',\'' + row.bankAvailableAmount + '\',\'' 
                + row.goodsType + '\', \'' + row.totalRate + '\',\'' + row.bankRate + '\',\'' 
                + row.term + '\',\'' + row.sevenRate + '\',\'' + row.economicsBankCompanyPk + '\', event); ">修改 </button></a>';
            }
            
            if(row.isVisable == 2){
            	  str += '<a class="save" href="javascript:;" style="text-decoration:none;">  '+
                  ' <button id="editable-sample_new"  showname="EM_CREDITCUST_CREDIT_BTN_ENABLE" style="display:none;" class="btn btn btn-primary" onclick="updateIsVisable(\'' + value + '\',1); ">启用 </button></a>';
            }else{
            	  str += '<a class="save" href="javascript:;" style="text-decoration:none;">  '+
                  ' <button id="editable-sample_new"  showname="EM_CREDITCUST_CREDIT_BTN_DISABLE" style="display:none;" class="btn btn btn-primary" onclick="updateIsVisable(\'' + value + '\',2); ">禁用</button></a>';
            }
              
            return str;
        }
    },
    {
        title: '金融产品',
        field: 'economicsGoodsName',
        width: 50,
        sortable: true
    },
    {
        title: '建议额度(W)',
        field: 'suggestAmount',
        width: 20,
        sortable: true
    },
    {
        title: '银行授信额度',
        field: 'bankContractAmount',
        width: 20,
        sortable: true
    },
    {
        title: '平台授信开始时间',
        field: 'creditStartTime',
        width: 20,
        sortable: true
    },
    {
        title: '平台授信结束时间',
        field: 'creditEndTime',
        width: 20,
        sortable: true
    },
    {
        title: '银行授信开始时间',
        field: 'bankCreditStartTime',
        width: 20,
        sortable: true
    },
    {
        title: '银行授信结束时间',
        field: 'bankCreditEndTime',
        width: 20,
        sortable: true
    },{
        title: '是否禁用',
        field: 'isVisable',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (row.isVisable == 1) {
                return "启用";
            }  else {
                return "禁用";
            }
        }
    },
    {
        title: '审核状态',
        field: 'status',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (row.status == 1) {
                return "待审核";
            } else if (row.status == 2) {
                return "审核通过";
            } else {
                return "审核不通过";
            }
        }
    },
    {
        title: '所属银行',
        field: 'bank',
        width: 20,
        sortable: true
    }
];

/**
 * 授信额度管理状态table页面切换
 * @param s
 */
function findPurchaser(s) {
    if (s == 0) {
        $("#creditStatus").val("");
    } else {
        $("#creditStatus").val(s);
    }
    var cfg = {
        url: './creditCustomerList.do' + urlParams(1),
        columns: columns,
        uniqueId: 'pk',
        sortName: 'creditAuditTime',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: btnList
    };
    createDataGrid('creditCustomerId', cfg);
}

/**
 * 授信额度管理 搜索和清除
 * @param type
 */
function searchTabs(type) {
    //清除所有内容
    if (type == 2) {
        cleanpar();
    }
    var cfg = {
        url: './creditCustomerList.do' + urlParams(1),
        columns: columns,
        uniqueId: 'pk',
        sortName: 'creditAuditTime',
        sortOrder: 'desc',
        toolbar: toolbar,
        onLoadSuccess: btnList
    };
    createDataGrid('creditCustomerId', cfg);
}

/**
 * 授信操作-展示授信列表
 * @param pk
 * @param companyPk
 * @param goodsType
 */
function editState(pk, companyPk,goodsType) {
	   $("#dataForm").hide();
	    $("#creditStartTime").val("");
	    $("#economicsGoodsName").val("");
	    $("#creditGoodsPk").val("");
	    $("#creditEndTime").val("");
	    $("#pledgeAmount").val("");
	    $("#platformAmount").val("");
	    $("#suggestAmount").val("");
	    $("#bankCreditStartTime").val("");
	    $("#bankCreditEndTime").val("");
	    $("#bankAvailableAmount").val("");
	    $("#totalRate").val("");
	    $("#term").val("");  
	    $("#bankRate").val("");
	    $("#sevenRate").val("");
	    $("#goodsType").val("");
	    $("#isExistBankCreditTime").val("");
	    $("#economicsBankCompanyPk").val("");
	    $('#creditCustomer').modal({
	        show: true,
	        backdrop: true
	    });
 
    var cfg = {
        url: './searchCreditGoods.do?pk=' + pk,
        columns: columnTwo,
        uniqueId: 'pk',
        toolbar: toolbar,
        onLoadSuccess: function (data) {
            if (data[0] != null) {
                $("#creditGoodsPk").val(data[0].pk);
            } else {
                $("#creditGoodsk").val("");
            }
           btnList();

        }
    };
    createDataGrid('creditGoodsId', cfg);
}

/**
 * 授信子页面修改功能
 * @param pk
 * @param suggestAmount
 * @param bankContractAmount
 * @param platformAmount
 * @param economicsGoodsName
 * @param creditStartTime
 * @param creditEndTime
 * @param bankCreditStartTime
 * @param bankCreditEndTime
 * @param bankAvailableAmount
 * @param goodsType
 * @param totalRate
 * @param bankRate
 * @param term
 * @param sevenRate
 * @param economicsBankCompanyPk
 * @param evt
 */
function editCreditGoods(pk, suggestAmount, bankContractAmount, platformAmount, economicsGoodsName, creditStartTime, creditEndTime,
		bankCreditStartTime,bankCreditEndTime,bankAvailableAmount,goodsType,totalRate,bankRate,term,sevenRate,economicsBankCompanyPk, evt) {
	$("#bankAvailableAmount").val("");
	$("#creditStartTime").val("");
    $("#economicsGoodsName").val("");
    $("#creditGoodsPk").val("");
    $("#creditEndTime").val("");
    $("#bankContractAmount").val("");
    $("#platformAmount").val("");
    $("#suggestAmount").val("");
    $("#bankCreditStartTime").val("");
    $("#bankCreditEndTime").val("");
    $("#bankAvailableAmount").val("");
    $("#creditGoodsPk").val(pk);
    $("#economicsGoodsName").val(economicsGoodsName);
    $("#totalRate").val("");
    $("#term").val("");
    $("#bankRate").val("");
    $("#sevenRate").val("");
    $("#goodsType").val("");
    $("#isExistBankCreditTime").val("");
    $("#economicsBankCompanyPk").val("");
    if (typeof(bankContractAmount) != "undefined"&&(bankContractAmount) != "undefined") $("#bankContractAmount").val(bankContractAmount);
    if (typeof(bankAvailableAmount) != "undefined"&&(bankAvailableAmount) != "undefined") $("#bankAvailableAmount").val(bankAvailableAmount);
 
    if (typeof(platformAmount) != "undefined"&&(platformAmount) != "undefined") $("#platformAmount").val(platformAmount);
    if (typeof(suggestAmount) != "undefined"&&(suggestAmount) != "undefined") $("#suggestAmount").val(suggestAmount);
    $("#economicsBankCompanyPk").val(economicsBankCompanyPk);
    if(goodsType==1 ||goodsType==2){
		$("#bankCreditStartTime").val(typeof(bankCreditStartTime) != "undefined"&&(bankCreditStartTime) != "undefined"?bankCreditStartTime:"");
		$("#bankCreditEndTime").val(typeof(bankCreditEndTime) != "undefined"&&(bankCreditEndTime) != "undefined"?bankCreditEndTime:"");
		$("#creditStartTime").val(typeof(creditStartTime) != "undefined"&&(creditStartTime) != "undefined"?creditStartTime:bankCreditStartTime);
		$("#creditEndTime").val(typeof(creditEndTime) != "undefined"&&(creditEndTime) != "undefined"?creditEndTime:bankCreditEndTime);
    }else{
    	$("#bankCreditStartTime").val(typeof(bankCreditStartTime) != "undefined"&&(bankCreditStartTime) != "undefined"?bankCreditStartTime:"");
		$("#bankCreditEndTime").val(typeof(bankCreditEndTime) != "undefined"&&(bankCreditEndTime) != "undefined"?bankCreditEndTime:"");
		$("#creditStartTime").val(typeof(creditStartTime) != "undefined"&&(creditStartTime) != "undefined"?creditStartTime:bankCreditStartTime);
		$("#creditEndTime").val(typeof(creditEndTime) != "undefined"&&(creditEndTime) != "undefined"?creditEndTime:bankCreditEndTime);
  /*  	 if (typeof(bankCreditStartTime) != "undefined"&&(bankCreditStartTime) != "undefined"&&(bankCreditStartTime) !='') {
 	    	$("#bankCreditStartTime").val(bankCreditStartTime);
 	    	//是否存在银行授信时间
 	    	$("#isExistBankCreditTime").val(1);//1存在银行授信时间；2不存在银行授信时间
 	    }else{
 	    	$("#bankCreditStartTime").val(typeof(creditStartTime) != "undefined"&&(creditStartTime) != "undefined"?creditStartTime:"");
 	    	$("#isExistBankCreditTime").val(2);
 	    }
 	    if (typeof(bankCreditEndTime) != "undefined"&&(bankCreditEndTime) != "undefined"&&(bankCreditEndTime) !=''){
 	    	$("#bankCreditEndTime").val(bankCreditEndTime);
 	    } else{
 	    	$("#bankCreditEndTime").val(typeof(creditEndTime) != "undefined"&&(creditEndTime) != "undefined"?creditEndTime:"");
 	    }*/
    }
	    
    if (typeof(totalRate) != "undefined"&&(totalRate) != "undefined") $("#totalRate").val(totalRate);
    if (typeof(bankRate) != "undefined"&&(bankRate) != "undefined") $("#bankRate").val(bankRate);
    if (typeof(term) != "undefined"&&(term) != "undefined")  $("#term").val(term);	

    $("#goodsType").val(goodsType);
    if (typeof(sevenRate) != "undefined"&&(sevenRate) != "undefined") $("#sevenRate").val(sevenRate);
    
    if(goodsType==1||goodsType==3){//化纤白条，不显示平台7天利率
    	$('#sevenRateDiv').hide();
    	
    }else if(goodsType==2||goodsType==4) { //化纤贷，显示平台7天利率
    	$('#sevenRateDiv').show();
    }
   
    $("#dataForm").show();
}

/**
 * 保存授信修改
 */
function submit() {
    if (valid("#dataForm")) {
    	  var creditEndTime =  new Date((new Date($("#creditEndTime").val())).getTime()).format("yyyy-MM-dd") ;
          var creditStartTime = new Date((new Date($("#creditStartTime").val())).getTime()).format("yyyy-MM-dd") ;
          var  platformAmount = $("#platformAmount").val();
          var bankCreditStartTime = $("#bankCreditStartTime").val();
          var bankCreditEndTime = $("#bankCreditEndTime").val();
    	if(platformAmount!="" && creditEndTime!="" && creditStartTime!=""){
		  	if(isNaN(platformAmount)) {
	            new $.flavr({
	                modal: false,
	                content: "平台授信额度须为数字"
	            });
	            return;
	        }
		  	
		
		  	 
		  if (creditEndTime < creditStartTime) {
	            new $.flavr({
	                modal: false,
	                content: "授信开始时间不能大于授信结束时间"
	            });
	            return;
	        }
		  	if( $("#goodsType").val()==2||$("#goodsType").val()==1){
		  	  	 if (parseInt(platformAmount) > parseInt($("#bankContractAmount").val())) {
			            new $.flavr({
			                modal: false,
			                content: "平台授信额度不能大于银行授信额度"
			            });
			            return;
		        }
		  	  	 
//		        if (bankCreditStartTime > creditStartTime ||bankCreditEndTime< creditEndTime ) {
//		            new $.flavr({
//		                modal: false,
//		                content: "平台授信时间不能超过银行授信时间"
//		            });
//		            return;
//		        }
		  	}
	       
    	}else{
    		if(platformAmount=="" && creditEndTime =="" && creditStartTime ==""){
    		}else{
    			new $.flavr({
	                modal: false,
	                content: "授信额度信息不完整"
	            });
	            return;
    		}
    	}
    	 var totalRate = $("#totalRate").val();
    	 var bankRate = $("#bankRate").val();
    	 var sevenRate = $("#sevenRate").val();
    	 if(totalRate==""){
    		 new $.flavr({
                 modal: false,
                 content:"总利率不能为空！"
             });
             return;
    	 }
    	 if(bankRate==""){
    		 new $.flavr({
                 modal: false,
                 content:"银行利率不能为空！"
             });
             return;
    	 }
    	 
    	if( $("#goodsType").val()==2){
    		 if(sevenRate==""){
        		 new $.flavr({
                     modal: false,
                     content:"平台7日利率不能为空！"
                 });
                 return;
        	 }
    	}
    	
    	 if($("#term").val()==""||!isDNumber($("#term").val())||$("#term").val()>180){
    		 new $.flavr({
                 modal: false,
                 content:"期限为【0,180】的整数！"
             });
             return;
    	 }
    	 
		if(!isNumRate(totalRate)){
    		new $.flavr({
                modal: false,
                content:"总利率是取值范围【0,100】，小数位不超过3位的数！"
            });
            return;
    	}
    	
		if(!isNumRate(bankRate)){
    		new $.flavr({
                modal: false,
                content:"银行利率是取值范围【0,100】，小数位不超过3位的数！"
            });
            return;
    	}
    	
		if(!isNumRate(sevenRate)){
    		new $.flavr({
                modal: false,
                content:"平台7日利率是取值范围【0,100】，小数位不超过3位的数！"
            });
            return;
    	}
    	
		if(parseInt(totalRate)<parseInt( bankRate)){
			new $.flavr({
                modal: false,
                content:"总利率不能小于银行利率，重新填写！"
            });
            return;
		}
        var params = {};
        params['pk'] = $("#creditGoodsPk").val();
        params['platformAmount'] = $("#platformAmount").val();
        if(creditEndTime!=""){
        	   params['endTime'] = $("#creditEndTime").val();
        }
        if(creditStartTime!=""){
        	params['startTime'] = $("#creditStartTime").val();
        }
      
        params['totalRate'] = totalRate;
        params['term'] = $("#term").val();
        params['bankRate'] = bankRate;
        params['sevenRate'] = sevenRate;
        
        if($("#isExistBankCreditTime").val()==2||$("#isExistBankAmount").val()==2) {
        	params['economicsBankCompanyPk'] = $("#economicsBankCompanyPk").val();
     	   	params['bankCreditEndTime'] = creditEndTime;
            params['bankCreditStartTime'] = creditStartTime;
   		 	params['bankContractAmount'] = $("#bankContractAmount").val();
        }
        $.ajax({
            type: 'POST',
            url: './updateCreditCustomer.do',
            data: params,
            dataType: 'json',
            success: function (data) {
                new $.flavr({
                    modal: false,
                    content: data.msg
                });
                if (data.success == true) {
                    $("#quxiao").click();
                    $('#creditCustomerId').bootstrapTable('refresh');
                }
            }
        });
    }
}
/**
 * 查询银行额度
 * @param companyPk
 */
function searchBankAmount(companyPk){

		$.ajax({
			type: 'POST',
			url: './searchCustomerAmount.do',
			data: {companyPk:companyPk},
			dataType: 'json',
			success: function (data) {
				new $.flavr({
					modal: false,
					content: data.msg
				});
				if (data.success == true) {
					$("#quxiao").click();
					$('#creditCustomerId').bootstrapTable('refresh');
				}
			}
		});
}

/**
 * 修改银行客户号
 * @param creditPk
 */
function updateBankAccount(creditPk){
	$("#customerNumberDiv").html("");
	$("#creditPk").val(creditPk);
	$.ajax({
		type: 'POST',
		url: './searchCreditGoodsByCreditPk.do',
		data: {creditPk:creditPk},
		dataType: 'json',
		success: function (data) {
			var content = '';
			if(data.length>0){
				for(var i =0 ; i<data.length;i++){
					var  bankPk = typeof(data[i].bankPk)=="undefined"?"":data[i].bankPk;
					var bank = typeof(data[i].bank)=="undefined"?"":data[i].bank;
					var bankAccountNumber = typeof(data[i].bankAccountNumber)=="undefined"?"":data[i].bankAccountNumber;
					content = content+ '<input type="hidden" name="bankPk" value="'+bankPk+'" /><div class="edit-content">';
					if(data[i].economicsGoodsName!=null&& data[i].economicsGoodsName!=''){
						var strs= new Array(); //定义一数组 
						strs=data[i].economicsGoodsName.split(" ,"); //字符分割 
						for(var j = 0 ; j<strs.length;j++){
							var name = typeof(strs[j])=="undefined"?"":strs[j];
							content = content +'<ul class="left"><li>'+name+'</li></ul>';
						}
					}
					content = content +'<ul class="right"><li>'+bank+'</li>'+
					'<li><input class="form-control" name="bankAccountNumber" required="true" value = "'+bankAccountNumber+'"/></li></ul></div>';
				}
				$("#customerNumberDiv").html(content);
			}
			
		}
	});
	
	$('#editCustomerNumber').modal();
}

/**
 * 保存银行客户号
 */
function submitCustomer(){
	var bankPks = "";
	var bankAccountNumbers = "";
	$("input[name='bankPk']").each(function(){
		   bankPks =bankPks  + $(this).val()+ ",";
	     })
     $("input[name='bankAccountNumber']").each(function(){
    	 if($(this).val()==""){
    		 bankAccountNumbers =bankAccountNumbers + null + ",";
    	 }else{
    		 bankAccountNumbers =bankAccountNumbers + $(this).val()+ ",";
    	 }
     })
	  
	var params = {
			creditPk:$("#creditPk").val(),
			bankAccountNumbers:bankAccountNumbers,
			bankPks:bankPks
	};
	$.ajax({
		type: 'POST',
		url: './updateBankAccountNumber.do',
		data:params,
		dataType: 'json',
		success: function (data) {
			new $.flavr({
				modal: false,
				content: data.msg
			});
			if (data.success == true) {
				$("#quxiaoone").click();
				$('#creditCustomerId').bootstrapTable('refresh');
			}
		}
	});
}

Date.prototype.format = function (format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
}

/**
 * 审核通过/不通过
 * @param companyPk
 * @param creditStatus
 */
function updateCreditSatuts(companyPk ,creditStatus) {
	if(creditStatus == 2 && companyPk!=''){
	    $('input:radio[name="isMessage"][value="2"]').prop('checked', true);
		$("#isMessageForm").modal();
		$("#companyPk").val(companyPk);
	}else{
		var params = {
				isMessage:$('input:radio[name="isMessage"]:checked').val(),
				companyPk:companyPk==""?$("#companyPk").val():companyPk,
				creditStatus:creditStatus	
			};
			$.ajax({
				type: 'POST',
				url: './updateCredit.do',
				data:params,
				dataType: 'json',
				success: function (data) {
					new $.flavr({
						modal: false,
						content: data.msg
					});
					if (data.success == true) {
						$("#quxiaoIsMess").click();
						$('#creditCustomerId').bootstrapTable('refresh');
					}
				}
			});
	}
	
}

/**
 * 授信额度更新功能
 * @param companyPk
 */
function updateCredit(companyPk) {
	
	var params = {
			companyPk:companyPk,
		};
		$.ajax({
			type: 'POST',
			url: './updateEconCustomerToCredit.do',
			data:params,
			dataType: 'json',
			success: function (data) {
				new $.flavr({
					modal: false,
					content: data.msg
				});
				if (data.success == true) {
					$('#creditCustomerId').bootstrapTable('refresh');
				}
			}
		});
}

function toCreditInvoice(creditPk,companyPk,companyName,creditContacts,creditContactsTel,productNames,creditStatus ){

    var creditStatusName = "";
    if (creditStatus == 1) {
        creditStatusName = "待审核";
    } else if (creditStatus == 2) {
        creditStatusName = "审核通过";
    } else {
        creditStatusName = "审核不通过";
    }

    window.location.href = getRootPath() + "/creditInvoice.do?companyPk="+companyPk+"&companyName="+
        companyName+"&creditContacts="+creditContacts+"&creditContactsTel="+creditContactsTel+"&productNames="+
        productNames+"&creditStatusName="+creditStatusName+"&pk="+creditPk;
}



/**
 * 授信启用/禁用
 * @param pk
 * @param isVisable
 */
function updateIsVisable(pk , isVisable){
	var params = {
			pk:pk,
			isVisable:isVisable,
		};
		$.ajax({
			type: 'POST',
			url: './updateCreditIsVisable.do',
			data:params,
			dataType: 'json',
			success: function (data) {
				new $.flavr({
					modal: false,
					content: data.msg
				});
				if (data.success == true) {
					$('#creditGoodsId').bootstrapTable('refresh');
				}
			}
		});
}


function billApply(pk,taxNature,taxAuthorityCode,taxAuthorityName,delegateCertUrl) {

    $("#creditBillApply").modal();
    if (checkIsEmpty(taxNature)){
        $("#taxNature").find("option[value='"+taxNature+"']").attr("selected",true);
    } else{
        $("#taxNature").find("option[value='']").attr("selected",true);
    }
    $("#pk").val(pk);

    if (checkIsEmpty(taxAuthorityCode)){
        $("#taxAuthorityCode").val(taxAuthorityCode);
    }else{
        $("#taxAuthorityCode").val("");
    }
    if (checkIsEmpty(taxAuthorityName)){
        $("#taxAuthorityName").val(taxAuthorityName);
    } else{
        $("#taxAuthorityName").val(taxAuthorityName);
    }
    $("#delegateCertURL").val("");
    if (checkIsEmpty(delegateCertUrl)){
        $("#isUpdate").html("是")
    } else{
        $("#isUpdate").html("否")
    }
}

function creditBillSubmit(){

    var file = document.getElementById("delegateCertURL").files;
    if (file.length == 0) {
        new $.flavr({
            modal : false,
            content : "请选择要导入的申请证书委托PDF文件！"
        });
        return;
    }
    // FormData 对象
    var form = new FormData();// 可以增加表单数据
    var str = file[0].name;
    if (file[0] == undefined || str.indexOf("pdf") == -1) {
        new $.flavr({
            modal : false,
            content : "请选择要导入的申请证书委托PDF文件！"
        });
        return;
    }
    form.append("filename", file[0]);// 文件对象

    var taxNature = $("#taxNature").val();
    var taxAuthorityName = $("#taxAuthorityName").val();
    var taxAuthorityCode = $("#taxAuthorityCode").val();
    var pk = $("#pk").val();
    $.ajax({
        type : "POST",
        url : './importTaxAuthority.do?taxNature='+taxNature+"&taxAuthorityName="+taxAuthorityName+
            "&taxAuthorityCode="+taxAuthorityCode+"&pk="+pk,
        data : form,
        // 关闭序列化
        processData : false,
        dataType : "json",
        contentType : false,
        success : function(data) {
           new $.flavr({
               modal : false,
               content : data.msg
           });
           if (data.success){
               $("#quxiao1").click();
               $('#creditCustomerId').bootstrapTable('refresh');
           }
        }
    });
    
}
    



function customerEconApply(creditPk,companyPk){
	
	$("#dataFormApply").find("input,select").each(function(i,n){
		 $(n).val("");
	});
	$("#creditEditPk").val(creditPk)
	$("#companyEditPk").val(companyPk);
    		$.ajax({
    			type: 'POST',
    			url: './getCreditByPk.do',
    			data:{
    				pk:creditPk
    			},
    			dataType: 'json',
    			success: function (data) {
	    			var info = data.creditInfo ;
	    			if(info!="" && info!= undefined){
	    				info = JSON.parse(info);
	    				   $("#hpcnt").val(info.hpcnt);
	    				   $("#prchAmount").val(info.prchAmount);
	    				   $("#prchCount").val(info.prchCount);
	    				   $("#rcName").val(info.rcName);
	    				   $("#rcDocType").val(info.rcDocType);
	    				   $("#rcDocNumber").val(info.rcDocNumber);
	    				   $("#rcDocMobile").val(info.rcDocMobile);
	    				   $("#taxAmount").val(info.taxAmount);
	    				   $("#loanTrem").val(info.loanTrem);
	    				   $("#coopStartDate").val(info.coopStartDate);
	    				   $("#creditAmount").val(toNonExponential(info.creditAmount));
	    				   $("#customerNumberEdit").val(info.customerNumber);
	    				   $("#creditNumberEdit").val(info.creditNumber);
	    				   $("#userIdEdit").val(info.userId);
	    				   $("#totalAmountEdit").val(toNonExponential(info.totalAmount));
	    				   
	    			}
	    				$("#customertApplyId").modal();
	    			
    			}
    		});
    	
    }


function toNonExponential(num) {
	var num = new Number(num);
	num =num.toLocaleString();
	return num.toString().replace(/\$|\,/g, '')
}


function  submitApply(){
	if (valid("#dataFormApply")) {
		  var params = {};
			$("#dataFormApply").find("input,select").each(function(i,n){
				   params[''+$(n).attr("name")+''] = $(n).val();
			});
		$.ajax({
			type: 'POST',
			url: './updateCreditApply.do',
			data:params,
			dataType: 'json',
			success: function (data) {
				new $.flavr({
	               modal : false,
	               content : data.msg
	           });
	           if (data.success||data.code=="0000"){
	               $("#quxiaoApply").click();
	               $('#creditCustomerId').bootstrapTable('refresh');
	           }}
		});
	}
}

