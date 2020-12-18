$(function() {
	createDataGrid('customerTaskId', cfg);
});

var toolbar = [];
var columns = [
	/*	{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				var str = '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" onclick="javascript:deal(\''+  row.processInstanceId + '\',\'' + row.processInstanceId + '\'); ">历史批注</button>';
				return str;
			}
		}, */
		{
			title : '公司名称',
			field : 'companyName',
			width : 20,
			sortable : true
		}, {
			title : '联系人',
			field : 'contacts',
			width : 20,
			sortable : true
		}, {
			title : '手机号码',
			field : 'contactsTel',
			width : 20,
			sortable : true
		}, {
			title : '申请产品',
			field : 'productName',
			width : 20,
			sortable : true
		}, {
			title : '金融专员',
			field : 'assidirName',
			width : 20,
			sortable : true
		},
		{
			title : '审核状态',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
   				if(value==3){
   					return '审核通过';
   				}
   				if(value==4){
   					return '审核不通过';
   				}
   				if(value==1){
   					return '等待审核';
   				}
   			}
		},{
			title : '申请时间',
			field : 'insertTime',
			width : 20,
			sortable : true
			 
   			}
		,{
			title : '最后审批时间',
			field : 'approveTime',
			width : 20,
			sortable : true
   			}
		];
var cfg = {
	url : './finishedBillList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'approveTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

/**
 * 历史批注列表
 * @param processInstanceId
 */
function deal(processInstanceId) {

	var hiscfg = {
			url : './searchEconCustAuditDetailList.do?processInstanceId='+processInstanceId,
			columns : hiscolumns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : histoolbar,
			onLoadSuccess : btnList
		};
	
	createDataGrid('histroyId', hiscfg);
	$('#editId').modal();
}

var histoolbar = [];
var hiscolumns = [
		{
			title : '审批时间',
			field : 'time',
			width : 20,
			sortable : true
		}, {
			title : '审核人',
			field : 'userId',
			width : 20,
			sortable : true
		}, {
			title : '审核批注',
			field : 'message',
			width : 20,
			sortable : true
		} ];
