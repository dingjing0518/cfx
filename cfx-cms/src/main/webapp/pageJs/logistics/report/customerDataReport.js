$(function() {
	var cfg = {
		url : './customerDataReport_data.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('customerDataId', cfg);

});

var toolbar = [];
var columns = [
        {
			title : '客户名称',
			field : 'purchaserName',
			sortable : true
		},{
			title : '联系电话',
			field : 'purchaserContactsTel',
		}, {
			title : '订单编号',
			field : 'orderPk',
		}, {
			title : '公司名称',
			field : 'toCompanyName',
			width : 20
		}, {
			title : '收货地址',
			field : 'toAddress',
			width : 20
		},{
			title : '订单时间',
			field : 'insertTime',
			sortable : true
		}, {
			title : '签收时间',
			field : 'signTime',
			sortable : true
		}, {
			title : '运输耗时',
			field : 'transConsumption',
			sortable : true
		},{
			title : '订单状态',
			field : 'orderStatusName',
		}, {
			title : '结算状态',
			field : 'isSettleFreightName',
		}, {
			title : '结算单价（元）',
			field : 'originalFreight',
		}, {
			title : '重量（吨）',
			field : 'weight',
		},{
			title : '支付金额',
			field : 'goodsOriginalFreight',
		}];

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
		cleanDate();
	}
	var cfg = {
		url : './customerDataReport_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('customerDataId', cfg);
}

/**
 * 结算状态Table页面切换
 * @param s
 */
function findReportStatus(s) {
	$("#reportStatus").val(s);
	var cfg = {
		url : './customerDataReport_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('customerDataId', cfg);
}

/**
 * 导出客户报表数据
 */
function exportReport() {


	$.ajax({
		type : "POST",
		url : './exportCustomerReport.do'+ urlParams(1),
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
