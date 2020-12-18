$(function() {
	var cfg = {
		url : './salemanSaleDetailReportList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'date',
		sortOrder : 'desc',
		toolbar : toolbar,
		pagination : true,
		showFooter : true,
		onLoadSuccess : btnList
	};

	createDataGrid('salemanSaleDetail', cfg);
});

var toolbar = [];
var columns = [ [ {
	field : 'accountName',
	title : '业务员姓名',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 2,
	footerFormatter : function(data) {
		return "<font color='red'>截止2019年7月8号前，历史数据按照分公司进行统计。</font>";
	}

}, {
	field : '开店时间',
	title : '采购商（业务员）',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 6,
	rowspan : 1,

}, {
	field : 'nat_Org_Code',
	title : '供应商（店铺业务员）',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 6,
	rowspan : 1,
} ], [

{
	field : 'purcompanyNum',
	title : '客户数',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'pPOYWeightb',
	title : 'POY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'pDTYWeightb',
	title : 'DTY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'pFDYWeightb',
	title : 'FDY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'pCUTWeightb',
	title : '切片',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'pOtherWeightb',
	title : '其他',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'supcompanyNum',
	title : '客户数',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'sPOYWeightb',
	title : 'POY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'sDTYWeightb',
	title : 'DTY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'sFDYWeightb',
	title : 'FDY',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'sCUTWeightb',
	title : '切片',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'sOtherWeightb',
	title : '其他',
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
		url : './salemanSaleDetailReportList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		pagination : true,
		showFooter : true,
		onLoadSuccess : btnList
	};

	createDataGrid('salemanSaleDetail', cfg);
}

/**
 * 导出业务员交易明细
 */
function exportData() {

	$.ajax({
		type : "POST",
		url : './exportsalemanSaleDetailReport.do',
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