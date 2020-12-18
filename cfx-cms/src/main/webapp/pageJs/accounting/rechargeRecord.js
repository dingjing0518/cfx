$(function() {
	createDataGrid('rechargeRecord', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '公司名称',
			field : 'companyName',
			width : 150,
			sortable : true
		},
		{
			title : '虚拟账户号',
			field : 'transactionAccount',
			width : 150,
			sortable : true
		},
		{
			title : '充值状态',
			field : 'status',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "成功";
				} else {
					return "失败";
				}

			}
		},
		{
			title : '充值金额',
			field : 'transactionAmount',
			width : 80,
			sortable : true 

		}, {
			title : '充值时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}/*,
		{
			title : '余额',
			field : 'balance',
			width : 80,
			sortable : true 

		} */];
var cfg = {
	url : './financeRecordList.do?transactionType=2',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar
};

 
function search(type){
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './financeRecordList.do?transactionType=2'+urlParams(''),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'asc',
			toolbar : toolbar
		};
	createDataGrid('rechargeRecord', cfg);
}

 