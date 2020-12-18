$(function() {
	createDataGrid('smsRecordId', cfg);
});

var toolbar = [];
var columns = [
    
		{
			title : '模板名称',
			field : 'freeSignName',
			width : 150,
			sortable : true
		},
		{
			title : '短信内容',
			field : 'content',
			width : 150,
			sortable : true
		},
		{
			title : '手机号码',
			field : 'mobile',
			width : 150,
			sortable : true,
		},
		{
			title : '公司名称',
			field : 'companyName',
			width : 150,
			sortable : true,
		},
		{
			title : '发送状态',
			field : 'status',
			width : 80,
			sortable : true,

		},{
			title : '发送时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}  ];
var cfg = {
	url : './smsRecordList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};


function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './smsRecordList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('smsRecordId', cfg);
}

/**
 * table列表页面切换
 * @param s
 */
function findSmsStatus(s){
	if(s==0){
		$("#status").val("");
	}else{
		$("#status").val(s);
	}
	var cfg = {
			url : './smsRecordList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('smsRecordId', cfg);
}
 