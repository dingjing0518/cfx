$(function() {
	var cfg = {
		url : './goodsUpdateReportList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		pagination : true,
		onLoadSuccess : btnList
	};

	createDataGrid('goodsUpdate', cfg);
});

var toolbar = [];
var columns = [ [ {
	field : 'name',
	title : '供应商店铺名称',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'openTime',
	title : '开店时间',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

}, {
	field : 'accountName',
	title : '业务员',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,
}, {
	field : 'updateTime',
	title : '商品更新时间',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 1,

} ] ];

function SearchClean(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './goodsUpdateReportList.do' + urlParams(1),
		columns : columns,
		uniqueId : '',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		showFooter : true,
		onLoadSuccess : btnList,
		pageSize : 10,

		pageList : [ 5, 10, 25, 50, 100 ]
	};
	createDataGrid('goodsUpdate', cfg);
}

/**
 * 产品更新数据报表
 */
function exportData() {

	$.ajax({
		type : "POST",
		url : './exportGoodsUpdateReport.do',
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