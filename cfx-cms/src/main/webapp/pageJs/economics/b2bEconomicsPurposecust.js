$(function() {
	createDataGrid('ecPurposecustId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				var str="";
				if (row.status == 1) {
					 str = '<button id="editable-sample_new" style="display:none;" showname="EM_CUST_PURPOSE_BTN_DEAL"  class="btn btn-primary" data-toggle="modal" onclick="javascript:deal(\''+ value + '\',\'' + row.name + '\'); ">处理</button>';
				}
				
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
		}, {
			title : '提交时间',
			field : 'insertTime',
			width : 30,
			sortable : true
		}, {
			title : '处理状态',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "未处理";
				} else if (value == 2) {
					return "已处理";
				}
			}
		}, {
			title : '类型',
			field : 'type',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "授信";
				} else if (value == 2) {
					return "票据";
				}
			}
		}, {
			title : '处理时间',
			field : 'updateTime',
			width : 30,
			sortable : true
		}, {
			title : '备注',
			field : 'remarks',
			width : 30,
			sortable : true
		} ];
var cfg = {
	url : './ecPurposecust_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

/**
 * 切换意向客户处理状态table
 * @param s
 */
function findStatus(s) {
	if (s != 0) {
		$("#status").val(s);
	} else {
		$("#status").val('');
	}
	var cfg = {
		url : './ecPurposecust_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('ecPurposecustId', cfg);
}

/**
 * 意向客户管理搜索和清除
 * @param type
 */
function searchTabs(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './ecPurposecust_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('ecPurposecustId', cfg);
}

/**
 * 意向客户处理操作
 * @param pk
 */
function deal(pk) {
	$('#editId').modal();
	$('input:radio[name="status"][value="1"]').prop('checked', true);
	$("#remarks").val('');
	$("#pk").val(pk);
}

/**
 * 保存处理结果
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
					$('#ecPurposecustId').bootstrapTable('refresh');
				}
			}
		});
	}
}