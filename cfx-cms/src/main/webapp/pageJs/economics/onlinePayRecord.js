$(function() {
	createDataGrid('recordId', cfg);
});

var toolbar = [];
var columns = [
       		{
       			title : '操作',
       			field : 'orderNumber',
       			width : 100,
       			formatter : function(value, row, index) {
       				var str = "";
    				if(row.status==1){
       				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="EM_ECONOMICS_ONLINEPAYRECORD_BTN_SEARCH" class="btn btn-warning" onclick="javascript:check(\'' + value + '\'); ">查询</button> </a>';
    				}
    				 return str;
       			}
       		},
		{
			title : '订单号',
			field : 'orderNumber',
			width : 50,
			sortable : true
		},{
			title : '状态',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function (value, row, index) {
				if(value==1){
					return "处理中";
				} else  if (value==2){
					return "成功";
				} else{
					return "失败";
				}
			}
		},{
			title : '交易流水号',
			field : 'serialNumber',
			width : 20,
			sortable : true
		},
		{
			title : '采购商',
			field : 'purchaserName',
			width : 20,
			sortable : true
		},{
			title : '供应商',
			field : 'supplierName',
			width : 20,
			sortable : true
		},{
			title : '订单金额',
			field : 'orderAmount',
			width : 20,
			sortable : true
		},{
			title : '创建时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},{
			title : '收款账户',
			field : 'receivablesAccount',
			width : 20,
			sortable : true
		},{
			title : '收款户名',
			field : 'receivablesAccountName',
			width : 20,
			sortable : true
		},{
			title : '线上支付产品',
			field : 'onlinePayGoodsName',
			width : 20,
			sortable : true
		},{
			title : '简称',
			field : 'shotName',
			width : 20,
			sortable : true
		}
		];
var cfg = {
	url : './searchOnlinePayRecord.do'+urlParams(1),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};



function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './searchOnlinePayRecord.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('recordId', cfg);
}




/**
 * @param s
 */
function byStatus(s){
	$("#status").val(s);
	var cfg = {
			url : './searchOnlinePayRecord.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('recordId', cfg);
}

/**
 * 查询
 * @param orderNumber
 */
function check(orderNumber){
	$.ajax({
		type : 'post',
		url : './checkOnlineRecord.do',
		data : {
			orderNumber : orderNumber
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			createDataGrid('recordId', cfg);
		}
	});
}
