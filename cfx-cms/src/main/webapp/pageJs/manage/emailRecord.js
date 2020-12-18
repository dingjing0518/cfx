$(function() {
	createDataGrid('emailRecordId', cfg);
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
			title : '发送时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}  ];
var cfg = {
	url : './emailRecordList.do',
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
			url : './emailRecordList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('emailRecordId', cfg);
}
 