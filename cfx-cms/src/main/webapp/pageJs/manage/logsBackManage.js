$(function() {
	createDataGrid('operationLogsBackId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '账号',
			field : 'accountName',
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
			title : '请求链接',
			field : 'url',
			width : 20,
			sortable : true
		}
		
		];
var cfg = {
	url : './searchOperationLogsBack.do',
	columns : columns,
	uniqueId : 'insertTime',
	sortName : 'sort',
	sortOrder : 'desc',
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
			url : './searchOperationLogsBack.do'+urlParams(1),
			columns : columns,
			uniqueId : 'insertTime',
			sortName : 'sort',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('operationLogsBackId', cfg);
}