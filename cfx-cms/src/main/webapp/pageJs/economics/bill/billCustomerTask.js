$(function() {
	createDataGrid('customerTaskId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				var str = '<button id="editable-sample_new" style="display:none;" showname="EM_APPROVAL_APPROVING_BTN_APPROVAL"  class="btn btn-primary" data-toggle="modal" onclick="javascript:deal(\''+ value + '\',\'' + row.taskId + '\',\'' + row.processInstanceId + '\'); ">审批</button>';
				return str;
			}
		}, {
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
			field : 'goodsName',
			width : 20,
			sortable : true
		},  {
			title : '金融专员',
			field : 'assidirName',
			width : 20,
			sortable : true
		}, {
			title : '申请时间',
			field : 'insertTime',
			width : 30,
			sortable : true
		}, {
			title : '提交审核时间',
			field : 'approvalTime',
			width : 30,
			sortable : true
		} ];
var cfg = {
	url : './approveBillTaskData.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

/**
 * 跳转审批页面
 * @param economCustPk
 * @param taskId
 * @param processInstanceId
 */
function deal(billCustPk,taskId,processInstanceId){

	window.location.href = getRootPath()+"/redirectBillPage.do?taskId="+taskId+"&billCustPk="+billCustPk+"&processInstanceId="+processInstanceId;
	
}

/**
 * 保存意向客户处理
 */
function submit() {
	if (valid("#dataForm")) {
		$.ajax({
			type : "POST",
			url : './updateEcPurposecust.do',
			data : {
				pk : $("#pk").val(),
				remarks : $("#remarks").val(),
				status : $("input[name='status']:checked").val()
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#customerTaskId').bootstrapTable('refresh');
				}
			}
		});
	}
}