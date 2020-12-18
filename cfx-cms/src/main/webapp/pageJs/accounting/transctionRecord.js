$(function() {
	createDataGrid('transctionRecord', cfg);
});

var toolbar = [];
var columns = [
       		/*{
       			title : '操作',
       			field : 'pk',
       			width : 200,
       			formatter : function(value, row, index) {
       				var str = "";
       				var insertTime="";
    				var auditTime="";
    				var updateTime = "";
    				var creditEndTime = "";
    				 if(row.insertTime!=null){
    					 insertTime= josn_to_String(row.insertTime.time);
    					} 
    				 if(row.auditTime!=null){
    					 auditTime= josn_to_String(row.auditTime.time);
    					}
    				 if(row.updateTime!=null){
    					 updateTime= josn_to_String(row.updateTime.time);
    					}
    				 if(row.creditEndTime!=null){
    					 creditEndTime= josn_to_String(row.creditEndTime.time);
    					}
       				if (row.creditStatus == 1) {
       				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" class="btn btn-warning" onclick="javascript:editState(\'' + value + '\',2,\''+insertTime+'\',\''+updateTime+'\',\''+auditTime+'\',\''+creditEndTime+'\'); ">  审核通过 </button> </a>';
       				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" class="btn btn-warning" onclick="javascript:editState(\'' + value + '\',3,\''+insertTime+'\',\''+updateTime+'\',\''+auditTime+'\',\''+creditEndTime+'\'); ">  审核不通过 </button> </a>';
       				}else if(row.creditStatus == 2){
       					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" class="btn btn-warning" onclick="javascript:editState(\'' + value + '\',3,\''+insertTime+'\',\''+updateTime+'\',\''+auditTime+'\',\''+creditEndTime+'\'); ">  审核不通过 </button> </a>';
       				}else if(row.creditStatus == 3){
       					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" class="btn btn-warning" onclick="javascript:editState(\'' + value + '\',2,\''+insertTime+'\',\''+updateTime+'\',\''+auditTime+'\',\''+creditEndTime+'\'); ">  审核通过 </button> </a>';
       				}
       				return str;
       			}
       		},*/
		{
			title : '公司名称',
			field : 'companyName',
			width : 20,
			sortable : true
		},
		{
			title : '虚拟账户',
			field : 'transactionAccount',
			width : 20,
			sortable : true
		},
		{
			title : '交易状态',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function (value, row, index) {
				if(value==1){
					return	"交易成功";
				}else{
					return "交易失败";
				} 
			}
		},
		{
			title : '交易金额',
			field : 'transactionAmount',
			width : 20,
			sortable : true
		},
		{
			title : '交易时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}];
var cfg = {
	url : './financeRecordList.do?transactionType=1',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar
};

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './financeRecordList.do?transactionType=1'+urlParams(''),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar
		};
	createDataGrid('transctionRecord', cfg);
}



