$(function() {
	var cfg = {
		url : './grossProfitReport_data.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('grossProfitId', cfg);
});

 

var toolbar = [];
var columns = [
        {
			title : '采购商名称',
			field : 'invoiceName',
		}, {
			title : '订单编号',
			field : 'orderPk',
		},  {
			title : '提货单号',
			field : 'deliveryNumber',
		}, {
			title : '物流供应商',
			field : 'logisticsCompanyName',
		}, {
			title : '运输重量（吨）',
			field : 'weight',
		}, {
			title : '对外单价（元/吨）',
			field : 'profitOriginalFreight',
		}, {
			title : '订单总价（元）',
			field : 'goodsOriginalFreight',
		},{
			title : '内部结算单价（元/吨）',
			field : 'profitPresentFreight',
		},  {
			title : '内部运费总价（元）',
			field : 'goodsPresentFreight',
		}, {
			title : '利润（元）',
			field : 'profit',
		} ];

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
		cleanDate();
	}
	var cfg = {
		url : './grossProfitReport_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('grossProfitId', cfg);
}

/**
 * 毛利报表 订单状态Table切换
 * @param s
 */
function findReportStatus(s) {
	$("#reportStatus").val(s);
	var cfg = {
		url : './grossProfitReport_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('grossProfitId', cfg);
}

/**
 * 导出毛利报表数据
 */
function exportReport() {

	$.ajax({
		type : "POST",
		url : './exportGrossProfitReport.do'+urlParams(1),
		dataType : "json",
		data :{
		} ,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});
}

/**
 * 清除搜索条件
 */
function cleanDate(){
    $('.form-dateday_start').datetimepicker('setEndDate','');
    $('.form-dateday_end').datetimepicker('setStartDate','');
    $('.form-dateday_startcopy').datetimepicker('setEndDate','');
    $('.form-dateday_endcopy').datetimepicker('setStartDate','');
}