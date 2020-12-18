$(function() {
	createDataGrid('processDefinition', cfg);
});

var toolbar = [];
var columns = [

		{
			title : '编号',
			field : 'id',
			width : 150,
			sortable : true
		},
		{
			title : '流程名称',
			field : 'name',
			width : 150,
			sortable : true
		},
		{
			title : '流程定义的key',
			field : 'key',
			width : 150,
			sortable : true
		},
		{
			title : '版本',
			field : 'version',
			width : 150,
			sortable : true
		},
		{
			title : '流程部署Id',
			field : 'deploymentId',
			width : 150,
			sortable : true
		}
		
		];
var cfg = {
	url : './processDefinitionPage.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'asc',
	toolbar : toolbar
};
function search(){
	var cfg = {
			url : './processDefinitionPage.do' + urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'asc',
			toolbar : toolbar
		};
		createDataGrid('processDefinition', cfg);
}


	
