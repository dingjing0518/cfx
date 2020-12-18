$(function() {
	createDataGrid('fiberLoanOrderId', cfg);
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
		   str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_ALLOCATIONBANK" class="btn btn-warning" onclick="javascript:allocationOfBank(\'' + value + '\',  \''  + row.purchaserPk	+ '\');">分配银行</button></a>';
	   }
	   str+='<button type="button" style="display:none;" showname="BTN_DOWNLOADCONTACT" class="btn btn-warning" onclick="javascript:downPDF(\''+ row.orderNumber+ '\');">下载合同</button></a>';
	   if(row.loanStatus==5 || row.loanStatus == 6){
	   str += '<button type="button" data-toggle="modal" data-target="#repaymentwinds" style="display:none;" showname="BTN_REPAYMENTRECORD" class="btn btn-warning" onclick="javascript:repaymentRecords(\''
			+ row.orderNumber+ '\');">还款记录</button></a>';
	   }
		return str ;
	}
}, {
	title : '订单号',
	field : 'orderNumber',
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
{
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
},
{
	title : '已还金额',
	field : 'principal',
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
	field : 'iouNumber',
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
	title : '还款时间',
	field : 'repaymentTime',
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
	title : '供应商',
	field : 'supplierName',
	width : 20,
	sortable : true
},
{
	title : '操作人',
	field : 'mobile',
	width : 20,
	sortable : true
}];
var cfg = {
	url : './fiberLoanOrder_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};
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
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('creditOrderId', cfg);
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		$("header").find("input").each(function(){
			$(this).val('');
		})
	}
	var cfg = {
			url : './fiberLoanOrder_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('fiberLoanOrderId', cfg);
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
 * 提交
 * @param economicsBankPk
 * @param economicsBankName
 */
function submit(economicsBankPk,economicsBankName){
	$.ajax({
		type : 'POST',
		url : "./updateOrder.do",
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
			$('#fiberLoanOrderId').bootstrapTable('refresh');
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
 * 化纤贷订单状态table页切换
 * @param orderStatus
 */
function byOrderStatus(orderStatus){
	$("#loanStatus").val(orderStatus);
//	$("#repaymentStatus").val('');
//	if($("#loanStatus").val()==5){
//		$("#loanStatus").val('');
//		$("#repaymentStatus").val(1);
//	}
//	if($("#loanStatus").val()==6){
//		$("#loanStatus").val('');
//		$("#repaymentStatus").val(2);
//	}
	var cfg = {
			url : './fiberLoanOrder_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('fiberLoanOrderId', cfg);
}

/**
 * 报价数据导出
 */
function exportCreditOrder(){
	
	window.location.href='./exportFiberLoanOrders.do'+urlParams(1)+"&orderName=insertTime&orderType=desc";
	
/*	var confirm = new $.flavr({
		content : '确定导出报价数据吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './exportFiberLoanOrders.do'+urlParams(1),
				dataType : "text",
				data : {
				},
				success : function(resp) {
					window.location.href = resp;
				}
			});
		}
	});*/
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
 * 还款记录查询
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