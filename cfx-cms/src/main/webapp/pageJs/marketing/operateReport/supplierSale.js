$(function() {
	createDataGrid('supplierSaleId', cfg);
});

var toolbar = [];
var columns = [ {
	title : '店铺名称',
	field : 'storeName',
	width : 20,
	sortable : true,
	footerFormatter : function(data) {
		return "<font color='red'>截止2019年7月8号前，历史数据按照分公司进行统计。</font>";
	}
}, {
	title : '业务员',
	field : 'accountName',
	width : 20,
	sortable : true
}, {
	title : '订单笔数',
	field : 'counts',
	width : 20,
	sortable : true
},

{
	title : '订单吨数',
	field : 'weight',
	width : 20,
	sortable : true
},

{
	title : '订单金额',
	field : 'amount',
	width : 20,
	sortable : true,
	formatter : function(value) {
		return formatNumber(parseFloat(value), 2);
	}
} ];
var cfg = {
	url : './searchSupplierSaleReport.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'receivablesTime',
	sortOrder : 'desc',
	pageSize : 5,
	showFooter : true,
	pagination : true,
	pageList : [ 5, 10, 25, 50, 100 ],
	toolbar : toolbar,
	onLoadSuccess : btnList
};

function searchTabs() {
	var cfg = {
		url : './searchSupplierSaleReport.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'receivablesTime',
		sortOrder : 'desc',
		pageSize : 5,
		pageList : [ 5, 10, 25, 50, 100 ],
		showFooter : true,
		pagination : true,
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('supplierSaleId', cfg);
}

/**
 * 供应商销售报表数据导出
 */
function exportData() {

	$.ajax({
		type : "POST",
		url : './exportSupplierSaleReport.do',
		dataType : "json",
		data : {
			startTime : $("#startTime").val(),
			endTime : $("#endTime").val()
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});
}