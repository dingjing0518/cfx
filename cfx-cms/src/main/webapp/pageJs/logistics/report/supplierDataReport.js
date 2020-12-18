$(function() {
	var cfg = {
		url : './supplierDataReport_data.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('supplierDataId', cfg);
});

var toolbar = [];
var columns = [
        {
			title : '物流承运商',
			field : 'logisticsCompanyName',
		}, {
			title : '提货工厂',
			field : 'fromCompanyName',
		}, {
			title : '订单编号',
			field : 'orderPk',

		},{
			title : '订单时间',
			field : 'insertTime',
			sortable : true
		},{
			title : '收货单位',
			field : 'toCompanyName',
		},{
			title : '收货地点',
			field : 'toAddress',
		},{
			title : '签收时间',
			field : 'signTime',
			sortable : true
		}, {
			title : '品名',
			field : 'productName',
		}, {
			title : '订单状态',
			field : 'orderStatusName',
		},{
			title : '结算单价（元）',
			field : 'presentFreight',
		}, {
			title : '重量（吨）',
			field : 'weight',
		}, {
			title : '结算状态',
			field : 'isSettleFreightName',
		}, {
			title : '异常状态',
			field : 'isAbnormal',
		},
		{
			title : '结算金额',
			field : 'goodsPresentFreight',
		}];

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
		cleanDate();
	}
	var sta = $("#isSettleFreight").val();
	//设置对应的选项卡
	$('.nav-tabs li').removeClass('active');
	$('.nav-tabs li').eq(sta).addClass('active');
	
	var cfg = {
		url : './supplierDataReport_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('supplierDataId', cfg);
}

/**
 * 物流供应商报表 订单状态Table切换
 * @param s
 */
function findSupplierDataStatus(s) {
	if(s==""){
		$("#isSettleFreight").val(s);
		$("#isAbnormal").val(s);
	}
	if(s==1||s==2){
		$("#isSettleFreight").val(s);
		$("#isAbnormal").val(1);
	}
	if(s==3){
		$("#isSettleFreight").val('');
		$("#isAbnormal").val(2);
	}
	$("button[data-id=isSettleFreight] span.filter-option").html($("#isSettleFreight  option:selected").text()); 
	
	var cfg = {
		url : './supplierDataReport_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('supplierDataId', cfg);
}

/**
 * 导出物流供应商报表数据
 */
function exportReport() {
		$.ajax({
			type : "POST",
			url : './exportSupplierDataReport.do' +urlParams(1),
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


/**
 * 清除搜索条件
 */
function cleanDate(){
    $('.form-dateday_start').datetimepicker('setEndDate','');
    $('.form-dateday_end').datetimepicker('setStartDate','');
    $('.form-dateday_startcopy').datetimepicker('setEndDate','');
    $('.form-dateday_endcopy').datetimepicker('setStartDate','');
}
