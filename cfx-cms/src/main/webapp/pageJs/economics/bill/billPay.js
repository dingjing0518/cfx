$(function() {
	createDataGrid('billOrderId', cfg);
	$(".form_datetime").datetimepicker({
		minView : "month",
		format : "yyyy-mm-dd",
		autoclose : true,
		todayBtn : true,
		language : 'zh-CN',
		pickerPosition : "bottom-right"
	});

});
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'orderNumber',
			width : 150,
			formatter : function(value, row, index) {
				var str = "";


					str += '<button type="button" data-toggle="modal" data-target="#billOrderDetail" style="display:none;margin:0px 5px!important;" showname="EM_ECONOMICS_BILL_PAYRECORD_BTN_RECORD" class="btn btn-warning" onclick="javascript:billOrderDetail(\''
							+ row.orderNumber
							+ '\',  \''
							+ row.serialNumber
							+ '\',  \''
							+ row.statusName
							+ '\', \''
							+ row.amount
							+ '\',\''
							+ row.purchaserName
							+ '\',\''
							+ row.supplierName
							+ '\', \''
							+ row.storeName
							+ '\',  \''
							+ row.goodsName
							+ '\',  \''
							+ row.goodsShotName
							+ '\' ,  \''
							+ row.insertTime
							+ '\' );">支付记录</button></a>';
			
				return str;
			}
		}, {
			title : '订单编号',
			field : 'orderNumber',
			width : 20,
			sortable : true
		}, {
			title : '支付流水号',
			field : 'serialNumber',
			width : 20,
			sortable : true
		}, {
			title : '支付状态',
			field : 'statusName',
			width : 20,
			sortable : true
		}, {
			title : '订单交易金额',
			field : 'amount',
			width : 20,
			sortable : true
		}, {
			title : '票据金额',
			field : 'billAmount',
			width : 20,
			sortable : true
		}, {
			title : '采购商名称',
			field : 'purchaserName',
			width : 20,
			sortable : true
		}, {
			title : '供应商名称',
			field : 'supplierName',
			width : 20,
			sortable : true
		}, {
			title : '店铺信息',
			field : 'storeName',
			width : 20,
			sortable : true
		}, {
			title : '票据产品',
			field : 'goodsName',
			width : 20,
			sortable : true
		}, {
			title : '票据产品英文简称',
			field : 'goodsShotName',
			width : 20,
			sortable : true
		}, {
			title : '票据交易时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}

];
var cfg = {
	url : './billOrder_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	paginationShowPageGo : true,
	onLoadSuccess : btnList
};

/**
 * 导出
 */
function exportBillOrder() {


			$.ajax({
				type : "POST",
				url : './exportBillOrder.do'+urlParams(1),
				dataType : "json",
				success : function(data) {
					  if (data.success){
			                new $.flavr({
			                    modal : false,
			                    content :data.msg
			                });
			            }
				}
			});
}

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './billOrder_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		paginationShowPageGo : true,
		onLoadSuccess : btnList
	};
	createDataGrid('billOrderId', cfg);
}

/**
 * 借款单列表状态切换table
 * 
 * @param orderStatus
 */
function byOrderStatus(orderStatus) {
	$("#status").val(orderStatus);
	var cfg = {
		url : './billOrder_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('billOrderId', cfg);
}


var columnsInventory = [
		{
			title : '银行票据号',
			field : 'billNumber',
			width : 20
		}, {
			title : '票据状态',
			field : 'statusName',
			width : 20
		}, {
			title : '支付状态',
			field : 'payStatusName',
			width : 20
		}, {
			title : '转让标记',
			field : 'transferName',
			width : 20
		}, {
			title : '票据种类',
			field : 'billCode',
			width : 20
		}, {
			title : '票据金额',
			field : 'amount',
			width : 20
		}, {
			title : '出票日',
			field : 'startDate',
			width : 20
		}, {
			title : '到期日',
			field : 'endDate',
			width : 20
		}, {
			title : '出票人名称',
			field : 'drawer',
			width : 20
		}, {
			title : '出票人组织机构代码',
			field : 'drawerCode',
			width : 20
		}, {
			title : '收款人',
			field : 'payee',
			width : 20
		}, {
			title : '收款人组织机构代码',
			field : 'payeeCode',
			width : 20
		}, {
			title : '承兑人',
			field : 'acceptor',
			width : 20
		}, {
			title : '承兑人组织机构代码',
			field : 'acceptorCode',
			width : 20
		} ,{
			title : '承兑人账号',
			field : 'acceptorAccount',
			width : 20
		},{
			title : '承兑人开户行行号',
			field : 'acceptorBankNo',
			width : 20
		},{
			title : '贴现利率',
			field : 'discountRate',
			width : 20
		},{
			title : '贴现利息',
			field : 'discountInterest',
			width : 20
		},{
			title : '贴现实付金额',
			field : 'discountAmount',
			width : 20
		}

];

function billOrderDetail(orderNumber, serialNumber, statusName, amount,
		purchaserName, supplierName, storeName, goodsName, goodsShotName, insertTime) {
	var cfg = {
		url : './billInventory_data.do?orderNumber=' + orderNumber,
		columns : columnsInventory,
		uniqueId : 'pk',
		sortName : 'pk',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('billInventoryDetailId', cfg);

	$("#clearForm").find("p[class = 'right'] ").each(function() {
		$(this).html("");
	});
	$("#orderNumber")
			.html(
					typeof (orderNumber) != "undefined"
							&& orderNumber != "undefined" ? orderNumber : "");
	$("#supplierName")
			.html(
					typeof (supplierName) != "undefined"
							&& supplierName != "undefined" ? supplierName : "");
	$("#purchaserName").html(
			typeof (purchaserName) != "undefined"
					&& purchaserName != "undefined" ? purchaserName : "");
	$("#serialNumber")
			.html(
					typeof (serialNumber) != "undefined"
							&& serialNumber != "undefined" ? serialNumber : "");
	$("#statusName")
			.html(
					typeof (statusName) != "undefined"
							&& statusName != "undefined" ? statusName : "");
	$("#amount").html(
			typeof (amount) != "undefined" && amount != "undefined" ? amount
					: 0);
	$("#storeName")
			.html(
					typeof (storeName) != "undefined"
							&& storeName != "undefined" ? storeName : "");
	$("#goodsName")
			.html(
					typeof (goodsName) != "undefined"
							&& goodsName != "undefined" ? goodsName : "");
	$("#goodsShotName").html(
			typeof (goodsShotName) != "undefined"
					&& goodsShotName != "undefined" ? goodsShotName : "");
	$("#insertTime")
			.html(
					typeof (insertTime) != "undefined"
							&& insertTime != "undefined" ? insertTime : "");
}




