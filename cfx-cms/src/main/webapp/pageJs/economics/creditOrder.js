$(function() {
	createDataGrid('creditOrderId', cfg);
	$(".form_datetime").datetimepicker({
	      minView: "month",
	      format: "yyyy-mm-dd",
	      autoclose: true,
	      todayBtn: true,
	      language:'zh-CN',
	      pickerPosition:"bottom-right"
	    });
	
	
});
var toolbar = [];
var columns = [	{
	title : '操作',
	field : 'orderNumber',
	width : 150,
	formatter : function(value, row, index) {
	   var str="";
	   if(row.loanStatus==1){
		   str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;margin:0px 5px!important;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_ALLOCBANK" class="btn btn-warning" onclick="javascript:allocationOfBank(\'' + value + '\',  \''  + row.purchaserPk	+ '\');">分配银行</button></a>';
	   }
	   if(row.loanStatus==5 || row.loanStatus == 6|| row.loanStatus ==3){

		   str += '<button type="button" data-toggle="modal" data-target="#creditOrderDetail" style="display:none;margin:0px 5px!important;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_DETAIL" class="btn btn-warning" onclick="javascript:creditOrderDetail(\''
				+ row.orderNumber+ '\',  \''  + row.purchaserName+ '\',  \'' + row.supplierName	+ '\',\''  + row.loanNumber+ '\',\''  + row.contractNumber+ '\', \''
				+ row.totalRate+ '\',  \''  + row.bankName+ '\',  \'' 
				+ row.loanAmount+ '\',  \''  + row.principal+ '\',  \'' 
				+ row.loanStartTime+ '\',  \''  + row.repaidSerCharge+ '\',  \''  + row.repaidInterest+ '\',  \''  + row.bankRate+ '\',  \''
				+ row.repaymentTime+ '\' ,  \''  + row.loans+ '\'  ,  \''  + row.sevenRate+ '\',  \''  + row.platformRate+ '\',  \''
				+ row.repayingInterest+ '\',  \''  + row.repayingSerCharge+ '\' ,  \''  + row.amountPayable+ '\' ,  \''  + row.economicsGoodsType+ '\' );">订单详情</button></a>';
	  
		   if(row.isOverdue==1){
				  str += '<button type="button" class="btn btn-warning" style="display:none;margin:0px 5px!important;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_OVERDUE" onclick="javascript:updataOverdue(\''
						+ row.orderNumber+ '\',2);">逾期</button></a>';
		   }else if(row.isOverdue==2){
			   str += '<button type="button" class="btn btn-warning" style="display:none;margin:0px 5px!important;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_REPOVERDUE" onclick="javascript:updataRepOverdue(\''
					+ row.orderNumber+ '\',1);">逾期撤回</button></a>';
		   }
	   
	   }
	   if(row.economicsGoodsType==1 ||row.economicsGoodsType==2){
		   if(row.loanStatus==3 || row.loanStatus == 6){
			   str += '<button type="button" class="btn btn-warning" style="display:none;margin:0px 5px!important;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_SEARCHREPAY" onclick="javascript:searchRepayment(\''
					+ row.orderNumber+ '\');">查询还款</button></a>';
			   }
		   if(row.loanStatus==2){
			   str += '<button type="button" class="btn btn-warning" style="display:none;margin:0px 5px!important;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_SEARCHLOAN" onclick="javascript:searchLoan(\''
					+ row.orderNumber+ '\');">查询放款</button></a>';
			   }
	   }
	   if(row.economicsGoodsType==3 ||row.economicsGoodsType==4){
		   if(row.loanStatus==2){
			   str += '<button type="button" class="btn btn-warning" style="display:none;margin:0px 5px!important;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_SURELOAN" onclick="javascript:sureLoanModal(\''
					+ row.orderNumber+ '\');">确认</button></a>';
		   } 
		   if(row.loanStatus==3||row.loanStatus==6){  
			   str += '<button type="button" class="btn btn-warning" style="display:none;margin:0px 5px!important;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_REPAYLOAN" onclick="javascript:repayLoanModal( \''  + row.orderNumber+ '\',\'' 
				   	+ row.loanNumber+ '\',\''  + row.purchaserName+ '\',\''  + row.bankName+ '\', \''
					+ row.loans+ '\',  \''  + row.loanAmount+ '\',  \'' 
					+ row.loanStartTime+ '\',  \''  + row.principal+ '\',  \'' 
					+ row.repaidInterest+ '\');">还款</button></a>';
		   }
	   }
	   if(row.block == "cf" && row.economicsGoodsType==2 &&(row.loanStatus==3||row.loanStatus==6)){
			str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
				+ '<button type="button"  style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_BILLOFLOADING" class="btn btn-warning" onclick="javascript:openBillOfLoading(\''
				+ row.orderNumber
				+ '\');">订单提货</button></a>';
	   }
	   return str ;
	}
}, {
	title : '借款单号',
	field : 'orderNumber',
	width : 20,
	sortable : true
},
{
	title : '采购商',
	field : 'purchaserName',
	width : 20,
	sortable : true
}, 
{
	title : '借贷银行',
	field : 'bankName',
	width : 20,
	sortable : true
},
{
	title : '借贷状态',
	field : 'loans',
	width : 20,
	sortable : true
},
/*{
	title : '订单金额',
	field : 'orderAmount',
	width : 20,
	sortable : true
},
{
	title : '实际总金额',
	field : 'actualAmount',
	width : 20,
	sortable : true
},*/
{
	title : '放款总金额',
	field : 'loanAmount',
	width : 20,
	sortable : true
},
{
	title : '已还本金',
	field : 'principal',
	width : 20,
	sortable : true
},
{
	title : '放款时间',
	field : 'loanStartTime',
	width : 20,
	sortable : true
},
{
	title : '到期时间',
	field : 'loanEndTime',
	width : 20,
	sortable : true
},
{
	title : '是否逾期',
	field : 'isOverdueName',
	width : 20,
   sortable: true
  
},
{
	title : '还款时间',
	field : 'repaymentTime',
	width : 20,
	sortable : true
},

{
	title : '供应商',
	field : 'supplierName',
	width : 20,
	sortable : true
},
{
	title : '合同编号',
	field : 'contractNumber',
	width : 20,
	sortable : true
},
{
	title : '借据编号',
	field : 'loanNumber',
	width : 20,
	sortable : true
}

];
var cfg = {
	url : './creditOrder_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'loanStartTime',
	sortOrder : 'desc',
	toolbar : toolbar,
    paginationShowPageGo: true,
	onLoadSuccess:btnList
};

/**
 * 借款单管理导出
 */
function excel() {
	var confirm = new $.flavr({
		content : '确定导出订单吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './exportOrder.do'+urlParams(1),
				dataType : "text",
				data : {

				},
				success : function(resp) {
					window.location.href = resp;
				}
			});
		}
	});
}

function byOrderFlag(s){
	var hflag=$("#flag").val();
	if((hflag>1&&hflag<5)&&hflag!=''){

		columns.splice(8,2);
	}

	if(hflag==5){
		columns.splice(8,3);
	}
	$("#flag").val(s);
	
 var flag=$("#flag").val();
	var columnNew=columns;
	 
	if(flag>1&&flag<6){
		columnNew.splice(8,0,iteam1);
		columnNew.splice(9,0,iteam2);
	}
	if(flag==5 ){
		columnNew.splice(10,0,iteam3);
 
	}
	
	var cfg = {
			url : './creditOrder_data.do'+urlParams(1),
			columns : columnNew,
			uniqueId : 'pk',
			sortName : 'loanStartTime',
			sortOrder : 'desc',
			toolbar : toolbar,
        	paginationShowPageGo: true,
			onLoadSuccess:btnList
		};
	createDataGrid('creditOrderId', cfg);
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './creditOrder_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'loanStartTime',
			sortOrder : 'desc',
			toolbar : toolbar,
        	paginationShowPageGo: true,
			onLoadSuccess:btnList
		};
	createDataGrid('creditOrderId', cfg);
}

/**
 * 借款单列表状态切换table
 * @param orderStatus
 */
function byOrderStatus(orderStatus){
    $("#loanStatus").val(orderStatus);
    var cfg = {
        url : './creditOrder_data.do'+urlParams(1),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'loanStartTime',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess:btnList
    };
    createDataGrid('creditOrderId', cfg);
}

var iteam1= {
	title : '已还金额',
	field : 'repaymentAmount',
	width : 20,
	sortable : true,
	formatter : function(value, row, index) {
		if(value==null){
			return 0;
		}else{
			return value;
		}
	
	}
};

var iteam2={
	title : '剩余未还金额',
	field : 'balance',
	width : 20,
	sortable : true,
	formatter : function(value, row, index) {
		if(value==null){
			return 0;
		}else{
			return value;
		}
	}
}; 
var iteam3={
	title : '利息金额',
	field : 'interest',
	width : 20,
	sortable : true,
	formatter : function(value, row, index) {
		if(value==null){
			return 0;
		}else{
			return value;
		}
	}
};

/**
 * 分配银行
 * @param pk
 * @param purchaserPk
 */
function allocationOfBank(pk,purchaserPk){
	$("#orderid").val(pk);
	$('#creditOrderwinds').modal();
    var cfg = {
         	url : './searchAuthorizedBanks.do?companyPk='+purchaserPk,
         	columns : columnTwo,
         	uniqueId : 'pk',
         	sortName : 'creditAuditTime',
         	sortOrder : 'desc',
         	toolbar : toolbar
         };
          
	createDataGrid('banks', cfg);
	 
}

var columnTwo = [{
	title : '操作',
	field : 'pk',
	width : 150,
	formatter : function(value, row, index) {
	   
	  var str = ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:submit(\'' + row.pk + '\',  \''  + row.bankName	+ '\');">提交</button></a>';
	   
		return str ;
	}
},
            		{
            			title : '银行名称',
            			field : 'bankName',
            			width : 50,
            			sortable : true
            		},{
            			title : '网关地址',
            			field : 'gateway',
            			width : 20,
            			sortable : true
            		},
            		{
            			title : '授信总金额',
            			field : 'creditTotalAmount',
            			width : 20,
            			sortable : true
            		},
            		{
            			title : '授信开始时间',
            			field : 'creditStartTime',
            			width : 50,
            			sortable : true
            		},{
            			title : '授信结束时间',
            			field : 'creditEndTime',
            			width : 50,
            			sortable : true
            		}];

/**
 * 提交分配银行
 * @param economicsBankPk
 * @param economicsBankName
 */
function submit(economicsBankPk,economicsBankName){
	$.ajax({
		type : 'POST',
		url : "./updateLoanNumber.do",
		data:{
			orderNumber:$("#orderid").val(),
			bankPk : economicsBankPk,
			bankName:economicsBankName
		},
		dataType : 'json',
		async : true,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiao").click();
			$('#creditOrderId').bootstrapTable('refresh');
		}
	});
	
}

/**
 * 合同下载
 * @param orderNumber
 */
function downPDF(orderNumber) {
	var confirm = new $.flavr({
		content : '确定下载合同吗?',
		dialog : 'confirm',
		onConfirm : function() {
			window.location.href = getRootPath()+"/uploadPDF.do?orderNumber="+orderNumber;
			confirm.close();
		}
	});
}

/**
 * 报价数据 导出
 */
function exportCreditOrder(){

    $.ajax({
        type : 'POST',
        url : './exportCreditOrders.do'+urlParams(1)+"&orderName=loanStartTime&orderType=desc",
        data : {},
        dataType : 'json',
        success : function(data) {
            if (data.success){
                new $.flavr({
                    modal : false,
                    content :data.msg
                });
            }
        }
    });
	//window.location.href='./exportCreditOrders.do'+urlParams(1)+"&orderName=loanStartTime&orderType=desc";
}

var columnsNew = [ 
                 {
                 	title : '订单号',
                 	field : 'orderNumber',
                 	width : 20
                 },
                 {
                 	title : '还款本金',
                 	field : 'amount',
                 	width : 20
                 }, 
                  {
                 	title : '还款利息',
                 	field : 'interest',
                 	width : 20
                 },
                 {
                 	title : '还款罚息',
                 	field : 'penalty',
                 	width : 20
                 },
                 {
                 	title : '还款复利',
                 	field : 'compound',
                 	width : 20
                 }
                 ,
                 {
                 	title : '还款日期',
                 	field : 'createTime',
                 	width : 20
                 }
                
                 ];
/**
 * 还款记录
 * @param orderNumber
 */
function repaymentRecords(orderNumber){
		var cfg = {
				url : './getB2bRepaymentRecordData.do?orderNumber='+orderNumber,
				columns : columnsNew,
				uniqueId : 'pk',
				sortName : 'pk',
				sortOrder : 'desc',
				toolbar : toolbar
			};
		createDataGrid('repaymentRecord', cfg);
}



var columnsOrder = [ {
						title : '操作',
						field : 'id',
						width : 15,
						formatter : function(value, row, index) {
							if(row.agentPayStatus == 3){
								var str = ' <a class="save" href="javascript:;" style="text-decoration:none;"><button id = "reduct'+row.id+'" type="button" class="btn btn-warning"  style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_DETAIL_BTN_DEDU" " onclick="javascript:deductRepayRecord(\'' + row.id + '\');">扣款</button></a>';
							}
							return str ;
						}
					},
                   {
                  	title : '还款日期',
                  	field : 'createTime',
                  	width : 20
                  },
                  {
                  	title : '还款本金',
                  	field : 'amount',
                  	width : 20
                  }, 
                  {
                	title : '应收服务费',
                  	field : 'serviceCharge',
                  	width : 20,
                  	formatter : function(value, row, index) {
                		if(value==null){
                			return '0.0';
                		}else{
                			return value;
                		}
                	
                	}
                  },{
                  	title : '化纤贷服务费',
                  	field : 'sevenCharge',
                  	width : 20,
                  	formatter : function(value, row, index) {
                		if(value==null){
                			return '0.0';
                		}else{
                			return value;
                		}
                	
                	}
                  }, 
                  {
                  	title : '应收利息',
                	field : 'interestReceivable',
                	width : 20,
                	formatter : function(value, row, index) {
                		if(value==null){
                			return '0.0';
                		}else{
                			return value;
                		}
                	
                	}
                  }, 
                   {
                  	title : '还款利息',
                  	field : 'interest',
                  	width : 20,
                  	formatter : function(value, row, index) {
                		if(value==null){
                			return '0.0';
                		}else{
                			return value;
                		}
                	
                	}
                  },
                  {
                  	title : '还款罚息',
                  	field : 'penalty',
                  	width : 20,
                  	formatter : function(value, row, index) {
                		if(value==null){
                			return '0.0';
                		}else{
                			return value;
                		}
                	
                	}
                  },
                  {
                  	title : '还款复利',
                  	field : 'compound',
                  	width : 20,
                  	formatter : function(value, row, index) {
                		if(value==null){
                			return '0.0';
                		}else{
                			return value;
                		}
                	
                	}
                  } 
                 
                  ];

/**
 * 订单详情显示
 * @param orderNumber
 * @param purchaserName
 * @param supplierName
 * @param loanNumber
 * @param contractNumber
 * @param totalRate
 * @param bankName
 * @param loanAmount
 * @param principal
 * @param loanStartTime
 * @param repaidSerCharge
 * @param repaidInterest
 * @param bankRate
 * @param repaymentTime
 * @param loans
 * @param sevenRate
 * @param platformRate
 * @param repayingInterest
 * @param repayingSerCharge
 * @param amountPayable
 * @param economicsGoodsType
 */
function creditOrderDetail(orderNumber,purchaserName,supplierName,loanNumber,contractNumber,totalRate
		,bankName,loanAmount,principal,loanStartTime,repaidSerCharge,repaidInterest,bankRate,
		repaymentTime,loans,sevenRate,platformRate,repayingInterest,repayingSerCharge,amountPayable,economicsGoodsType){
	
	var cfg = {
			url : './getB2bRepaymentRecordData.do?orderNumber='+orderNumber,
			columns : columnsOrder,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'desc',
			toolbar : toolbar,
			 onLoadSuccess:btnList
		};
	createDataGrid('creditOrderDetailId', cfg);
	
	$("#clearForm").find("p[class = 'right'] ").each(function(){
		$(this).html("");
	});
	$("#purchaserName").html(typeof(purchaserName) != "undefined" && purchaserName!="undefined"?purchaserName:"");
	$("#supplierName").html(typeof(supplierName) != "undefined" && supplierName!="undefined"?supplierName:"");
	$("#loanNumber").html(typeof(loanNumber) != "undefined" && loanNumber!="undefined"?loanNumber:"");
	$("#orderNumberCopy").html(orderNumber);
	$("#contractNumber").html(typeof(contractNumber) != "undefined" && contractNumber!="undefined"?contractNumber:"");
	$("#totalRate").html(typeof(totalRate) != "undefined" && totalRate!="undefined"?totalRate:0);
	$("#bankName").html(typeof(bankName) != "undefined" && bankName!="undefined"?bankName:"");
	$("#platformRate").html(typeof(platformRate) != "undefined" && platformRate!="undefined"?platformRate:0);
	$("#loanAmount").html(typeof(loanAmount) != "undefined" && loanAmount!="undefined"?loanAmount:0);
	$("#principal").html(typeof(principal) != "undefined" && principal!="undefined"?principal:0);
	$("#loanStartTime").html(typeof(loanStartTime) != "undefined" && loanStartTime!="undefined"?loanStartTime:"");
	$("#repaidSerCharge").html(typeof(repaidSerCharge) != "undefined" && repaidSerCharge!="undefined"?repaidSerCharge:0);
	$("#bankRate").html(typeof(bankRate) != "undefined" && bankRate!="undefined"?bankRate:0);
	$("#repayingSerCharge").html(typeof(repayingSerCharge) != "undefined" && repayingSerCharge!="undefined"?repayingSerCharge:0);//未还本金服务费
	$("#repaidInterest").html(typeof(repaidInterest) != "undefined" && repaidInterest!="undefined"?repaidInterest:0);
	$("#repaymentTime").html(typeof(repaymentTime) != "undefined" && repaymentTime!="undefined"?repaymentTime:"");
	$("#repayingInterest").html(typeof(repayingInterest) != "undefined" && repayingInterest!="undefined"?repayingInterest:0);//未还银行利息
	$("#amountPayable").html(typeof(amountPayable) != "undefined" && amountPayable!="undefined"?amountPayable:0);//客户应还金额
	$("#loans").html(loans);//客户应还金额
	if(economicsGoodsType==2){
		$("#sevenRateP").html("7天利率");
		$("#sevenRate").html(typeof(sevenRate) != "undefined" && sevenRate!="undefined"?sevenRate:0);//7天利率
	}else{
		$("#sevenRateP").html("");
	}
	
}


/**
 * 查询放款
 * @param orderNumber
 */
function searchLoan(orderNumber){
	$.ajax({
        type: 'POST',
        url: './searhLoanByOrderNumber.do',
        data: {orderNumber:orderNumber},
        dataType: 'json',
        success: function (data) {
            new $.flavr({
                modal: false,
                content: data.msg
            });
            if (data.success == true) {
                $("#quxiao").click();
                $('#creditOrderId').bootstrapTable('refresh');
            }
        }
    });
}

/**
 * 查询还款
 * @param orderNumber
 */
function searchRepayment(orderNumber){
	$.ajax({
        type: 'POST',
        url: './searhRepaymentByOrderNumber.do',
        data: {orderNumber:orderNumber},
        dataType: 'json',
        success: function (data) {
            new $.flavr({
                modal: false,
                content: data.msg
            });
            if (data.success == true) {
                $("#quxiao").click();
                $('#creditOrderId').bootstrapTable('refresh');
            }
        }
    });
}


/**
 * 还款记录扣款
 * @param pk
 */
function deductRepayRecord(id){
	$("#reduct"+id).attr("disabled","false");
	$.ajax({
        type: 'POST',
        url: './deductRepayRecord.do',
        data: {id:id},
        dataType: 'json',
        success: function (data) {
        	$("#reduct"+id).removeAttr("disabled");
            new $.flavr({
                modal: false,
                content: data.msg
            });
            $("#creditOrderDetail").modal('hide'); 
            $('#creditOrderId').bootstrapTable('refresh');
        }
    });
}

/**
 * 还款查询
 * @param loanNumber
 * @param purchaserName
 * @param bankName
 * @param loans
 * @param loanAmount
 * @param loanStartTime
 * @param principal
 * @param interest
 */
function repayLoanModal(orderNumber,loanNumber,purchaserName,bankName,loans,loanAmount,loanStartTime,principal,interest){
	$("#orderNumberRepay").val(orderNumber);
	$("#loanNumberRepay").html(orderNumber);
	$("#purchaserNameRepay").html(purchaserName);
	$("#bankNameRepay").html(bankName);
	$("#loansRepay").html(loans);
	$("#loanAmountRepay").html(loanAmount);
	$("#loanStartTimeRepay").html(loanStartTime);
	$("#principalRepay").html(principal);
	$("#interestRepay").html(interest);
	$("#nowAmountRepay").val(ForDight(loanAmount,principal));
	//本次还款利息= (当前系统时间-放款时间-1)*当前客户产品对应利率/360*本次还款本金
	
	getRepayLoan(orderNumber,$("#nowAmountRepay").val());
	
	$("#nowInterestRepay").val(interest);
	$('#repayLoan').modal();
	 
}


function ForDight(loanAmount,principal){
	var Dight = Number(loanAmount)- Number(principal);
	Dight = Math.round (Dight*Math.pow(10,2))/Math.pow(10,2); 
	return Dight; 
} 

/**
 * 还款
 * @param loanNumber
 * @param nowAmountRepay
 */
function getRepayLoan(orderNumber,nowAmountRepay){
	
	$.ajax({
	    type: 'POST',
	    url: './getRepayLoan.do',
	    data: {
	    	orderNumber:orderNumber,
	    	amount :nowAmountRepay,
	    },
	    dataType: 'json',
	    success: function (data) {
	    	$("#nowInterestRepay").val(data.interest);
	        
	    }
	});

}


/**
 * 确认
 */
function sureLoanModal(orderNumber){
	
	$('#orderNumberSureLoan').val(orderNumber);
	$('#sureLoan').modal();
}

/**
 * 确认贷款
 */
function apiSureLoan(status){

	$.ajax({
	    type: 'POST',
	    url: './apiInsertLoan.do',
	    data: {
	    	orderNumber:$('#orderNumberSureLoan').val(),
	    	status  :status,
	    },
	    dataType: 'json',
	    success: function (data) {
	    	new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiaoSure").click();
			$('#creditOrderId').bootstrapTable('refresh');
	        
	    }
	});

}

/**
 * 确认还款
 */
function apiRepayLoan() {
	$.ajax({
	    type: 'POST',
	    url: './apiInsertRepayment.do',
	    data: {
	    	orderNumber:$("#orderNumberRepay").val(),
	    	principal  :$("#nowAmountRepay").val(),
	    	interest :$("#nowInterestRepay").val(),
	    },
	    dataType: 'json',
	    success: function (data) {
	    	new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiaoRepay").click();
			$('#creditOrderId').bootstrapTable('refresh');
	        
	    }
	});
}


function updataOverdue(orderNumber,isOverdue){

	var confirm = new $.flavr({
		content : '确定该笔借款单是否逾期?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
			    url: './updataOverdue.do',
			    data: {
			    	orderNumber:orderNumber,
			    	isOverdue  :isOverdue,
			    },
			    dataType: 'json',
				success : function(resp) {
					new $.flavr({
						modal : false,
						content : resp.msg
					});
					$('#creditOrderId').bootstrapTable('refresh');
				}
			});
		}
	});

}



function updataRepOverdue(orderNumber,isOverdue){

	var confirm = new $.flavr({
		content : '确定该笔借款单是否撤回逾期?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
			    url: './updataOverdue.do',
			    data: {
			    	orderNumber:orderNumber,
			    	isOverdue  :isOverdue,
			    },
			    dataType: 'json',
				success : function(resp) {
					new $.flavr({
						modal : false,
						content : resp.msg
					});
					$('#creditOrderId').bootstrapTable('refresh');
				}
			});
		}
	});
}

/**
 * 提货申请
 * @param orderNumber
 */
function  openBillOfLoading(orderNumber){
	window.location.href = getRootPath() + "/openBillOfLoading.do?orderNumber="
	+ orderNumber+"&flag=1" ;
}
