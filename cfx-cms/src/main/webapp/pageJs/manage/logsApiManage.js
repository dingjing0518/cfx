$(function() {
	createDataGrid('operationLogsApiId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '用户手机号',
			field : 'mobile',
			width : 150,
			sortable : true
		},
		{
			title : '时间',
			field : 'insertTime',
			width : 150,
			sortable : true
		},
		{
			title : '请求IP',
			field : 'ip',
			width : 80,
			sortable : true,
		},
		{
			title : '浏览器信息',
			field : 'information',
			width : 80,
			sortable : true,
		},
		{
			title : '请求链接',
			field : 'url',
			width : 20,
			sortable : true
		}
		
		];
var cfg = {
	url : './searchOperationLogsApi.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
	//onLoadSuccess:loadFancyBox
};


function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var insertStartTime = $("input[name='insertStartTime']").val();
	var insertEndTime = $("input[name='insertEndTime']").val();
	if(insertStartTime != null && insertStartTime != "" && (insertEndTime == null || insertEndTime == "")
			|| insertEndTime != null && insertEndTime != "" && (insertStartTime == null || insertStartTime == "")){
		new $.flavr({
			modal : false,
			content : "请选择时间范围！"
		});
		return;
	}
	
	var cfg = {
			url : './searchOperationLogsApi.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('operationLogsApiId', cfg);
}