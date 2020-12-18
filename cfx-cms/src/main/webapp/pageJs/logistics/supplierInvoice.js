$(function() {
	var cfg = {
		url : './supplierInvoice_data.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('supplierInvoiceId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			formatter : function(value, row, index) {
				var str = "";
				if (row.orderStatus == 3 && row.supplierInvoiceStatus == 2) {

					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button" style="display:none;" showname="LG_FC_SUPPINVOICE_BTN_INVOICE" class="btn btn-warning" onclick="javascript:sureInvoices(\''
							+ row.pk
							+ '\');">确认开票</button></a>';
				}
				return str;
			}
		}, {
			title : '公司抬头',
			field : 'logisticsCompanyName',
			sortable : true
		}, {
			title : '联系人',
			field : 'contacts',
			sortable : true
		}, {
			title : '手机号',
			field : 'contactsTel',
		}, {
			title : '订单号',
			field : 'orderPk',

		}, {
			title : '订单结算金额（元）',
			field : 'presentTotalFreight',
			
		}, {
			title : '配送吨数',
			field : 'weight',
		}, {
			title : '开票状态',
			field : 'supplierInvoiceName',
		}, {
			title : '提交时间',
			field : 'applyTime',
		}];


function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
		cleanDate();
	}
	var sta = $("#supplierInvoiceStatus").val();
	//设置对应的选项卡
	$('.nav-tabs li').removeClass('active');
	$('.nav-tabs li').eq(sta).addClass('active');
	var cfg = {
		url : './supplierInvoice_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('supplierInvoiceId', cfg);
}

/**
 * 开票状态Table页面切换
 * @param s
 */
function findSupplierInvoiceStatus(s) {
	$("#supplierInvoiceStatus").val(s);
	$("button[data-id=supplierInvoiceStatus] span.filter-option").html($("#supplierInvoiceStatus  option:selected").text()); 
	var cfg = {
		url : './supplierInvoice_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('supplierInvoiceId', cfg);
}

/**
 * 确认开票
 * @param pk
 */
function sureInvoices(pk) {
	var confirm = new $.flavr({
		content : '确定已收到该发票吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateSupplierInvoice.do',
				data : {
					'pk' : pk,
					'supplierInvoiceStatus' : 3,
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						// $("#quxiao").click();
						$('#supplierInvoiceId').bootstrapTable('refresh');
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
 * 导出供应商开票数据
 */
function exportInvoice() {


	$.ajax({
		type : "POST",
		url : './exportSupplierInvoiceForm.do'+urlParams(1),
		dataType : "json",
		data : {},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});


}

