$(function() {

	var cfg = {
		url : './supplierSaleDataReportList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'date',
		sortOrder : 'desc',
		toolbar : toolbar,
		pagination : true,
		showFooter : true,
		fixedColumns : true,
		fixedNumber : 2,
		pageSize : 10,
		pageList : [ 5, 10, 25, 50, 100 ],
		onLoadSuccess : btnList
	};

	createDataGrid('supplierSaleDateId', cfg);
});

var toolbar = [];
var columns = [ [ {
	field : 'name',
	title : '店铺名称',
	valign : "middle",
	halign : "center",
	colspan : 1,
	rowspan : 2,
	footerFormatter : function(data) {
		return "<font color='red'>截止2019年7月8号前，历史数据按照分公司进行统计。</font>";
	}

}, {
	field : 'accountName',
	title : '业务员',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 2,

}, {
	field : '',
	title : 'POY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 4,
	rowspan : 1,
}, {
	field : '',
	title : 'FDY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 4,
	rowspan : 1,
}, {
	field : '',
	title : 'DTY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 4,
	rowspan : 1,
}, {
	field : '',
	title : '切片',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 4,
	rowspan : 1,
}, {
	field : '',
	title : '其他',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 4,
	rowspan : 1,
} ], [ {
	field : 'POYnum',
	title : '订单数',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'POYpriceb',
	title : '订单金额',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'POYweightb',
	title : '销售量',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'POYRatio',
	title : '占比（销售量）',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'FDYnum',
	title : '订单数',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'FDYpriceb',
	title : '订单金额',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'FDYweightb',
	title : '销售量',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'FDYRatio',
	title : '占比（销售量）',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'DTYnum',
	title : '订单数',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'DTYpriceb',
	title : '订单金额',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'DTYweightb',
	title : '销售量',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'DTYRatio',
	title : '占比（销售量）',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'CUTnum',
	title : '订单数',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'CUTpriceb',
	title : '订单金额',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'CUTweightb',
	title : '销售量',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'CUTRatio',
	title : '占比（销售量）',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'othernum',
	title : '订单数',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'otherpriceb',
	title : '订单金额',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'otherweightb',
	title : '销售量',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'otherRatio',
	title : '占比（销售量）',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
} ] ];

function searchClean(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './supplierSaleDataReportList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		pagination : true,
		fixedColumns : true,
		fixedNumber : 2,
		showFooter : true,
		pageSize : 10,
		pageList : [ 5, 10, 25, 50, 100 ],
		onLoadSuccess : btnList
	};

	createDataGrid('supplierSaleDateId', cfg);
}

/**
 * 供应商销售数据导出
 */
function exportData() {
	$.ajax({
		type : "POST",
		url : './exportSupplierSaleDataReport.do',
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