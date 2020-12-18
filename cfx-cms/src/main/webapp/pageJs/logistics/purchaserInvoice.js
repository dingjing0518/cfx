$(function() {
	var cfg = {
		url : './purchaserInvoice_data.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('purchaserInvoiceId', cfg);

});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			formatter : function(value, row, index) {
				var str = "";
				if (row.memberInvoiceStatus == 1 && row.orderStatus==3) {
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button" style="display:none;" showname="LG_FC_PURINVOICE_BTN_INVOICE" class="btn btn-warning" onclick="javascript:makeInvoice(\''
							+ row.pk
							+ '\');">开票</button></a>';
				}
				if (row.memberInvoiceStatus == 2 && row.orderStatus==3 ) {
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button" style="display:none;" showname="LG_FC_PURINVOICE_BTN_SENDINVOICE" class="btn btn-warning" onclick="javascript:sendInvoice(\''
							+ row.pk
							+ '\',\''
							+ row.invoiceName
							+ '\');">寄送发票</button></a>';
				}
				return str;
			}
		}, {
			title : '公司抬头',
			field : 'invoiceName',
		}, {
			title : '税号',
			field : 'invoiceTaxidNumber',
		}, {
			title : '注册地址',
			field : 'invoiceRegAddress',
		}, {
			title : '注册电话',
			field : 'invoiceRegPhone',

		}, {
			title : '开户行名称',
			field : 'invoiceBankName',
		}, {
			title : '银行账号',
			field : 'invoiceBankAccount',
		}, {
			title : '申请时间',
			field : 'applyTime',
			sortable : true
		}, {
			title : '开票时间',
			field : 'invoiceTime',
			sortable : true
		}, {
			title : '开票状态',
			field : 'memberInvoiceName',
		}, {
			title : '开票金额（元）',
			field : 'presentTotalFreight',
		}, {
			title : '收件人',
			field : 'contactName',
		}, {
			title : '收件人联系电话',
			field : 'contactTel',
		}, {
			title : '收件地址',
			field : 'contactAddress',
		}];

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
		cleanDate();
	}
	var sta = $("#memberInvoiceStatus").val();
	//设置对应的选项卡
	$('.nav-tabs li').removeClass('active');
	$('.nav-tabs li').eq(sta).addClass('active');
	
	var cfg = {
		url : './purchaserInvoice_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('purchaserInvoiceId', cfg);
}

/**
 * 发票开票状态Table页面切换
 * @param s
 */
function findInvoiceStatus(s) {
	$("#memberInvoiceStatus").val(s);
	$("button[data-id=memberInvoiceStatus] span.filter-option").html($("#memberInvoiceStatus  option:selected").text()); 
	var cfg = {
		url : './purchaserInvoice_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('purchaserInvoiceId', cfg);
}

/**
 * 开票
 * @param pk
 */
function makeInvoice(pk) {
	var confirm = new $.flavr({
		content : '确定开具该发票吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateInvoice.do',
				data : {
					'pk' : pk,
					'memberInvoiceStatus' : 2,
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						// $("#quxiao").click();
						$('#purchaserInvoiceId').bootstrapTable('refresh');
					}
				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});

}

/**
 * 寄送发票
 * @param pk
 * @param invoiceName
 */
function sendInvoice(pk, invoiceName) {
	var confirm = new $.flavr({
		content : '确定寄送' + invoiceName + '发票吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateInvoice.do',
				data : {
					'pk' : pk,
					'memberInvoiceStatus' : 3,
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						// $("#quxiao").click();
						$('#purchaserInvoiceId').bootstrapTable('refresh');
					}
				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});

}

/**
 * 导出发票
 */
function exportInvoice() {

	$.ajax({
		type : "POST",
		url : './exportPurchaserInvoiceForm.do' +urlParams(1),
		dataType : "json",
		data :{},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});


}
