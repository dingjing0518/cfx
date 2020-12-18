$(function() {
	createDataGrid('marrieddealId', cfg);
});
var toolbar = [];
var columns = [
		{
			
			title : '操作',
			field : 'marrieddealId',
			width : 50,
			formatter : function(value, row, index) {
				var str = "";
				/*str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn btn-primary" data-toggle="modal" data-target="#editMarrId" onclick="javascript:editMarrieddeal(\''
						+ value + '\');">审核</button></a>&nbsp;&nbsp;&nbsp;'*/
					if(row.status==1){
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:editMarrieddeal(\''
							+ value
							+ '\',0);">过期</button></a>';
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:editMarrieddeal(\''
							+ value
							+ '\',2);">成交</button></a>';
					}
				
				return str;
			}
		},
		{
			title : '跟踪状态',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 0) {
					return "过期";
				} else if (value == 1) {
					return "洽谈中";
				} else if (value == 2) {
					return "成交";
				}

			}

		},
		{
			title : '采购单号',
			field : 'marrieddealId',
			width : 20,
			sortable : true
		},

		{
			title : '采购标题',
			field : 'name',
			width : 20,
			sortable : true
		},

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
			title : '手机',
			field : 'contactsTel',
			width : 20,
			sortable : true
		}, {
			title : '需求有效期',
			field : 'endTime',
			width : 20,
			sortable : true
		}, {
			title : '发布时间',
			field : 'startTime',
			width : 20,
			sortable : true
		}, {
			title : '品名',
			field : 'productName',
			width : 20,
			sortable : true
		}, {
			title : '品种',
			field : 'varietiesName',
			width : 20,
			sortable : true
		}, {
			title : '规格(吨)',
			field : 'specName',
			width : 20,
			sortable : true
		}, {
			title : '等级',
			field : 'gradeName',
			width : 20,
			sortable : true
		} ];
var cfg = {
	url : './marrieddealAudit_data.do?auditStatus=2',
	columns : columns,
	uniqueId : 'marrieddealId',
	sortName : 'startTime',
	sortOrder : 'asc',
	toolbar : toolbar
};

function editMarrieddeal(pk,status) {
	var parm = {
			'marrieddealId' : pk,
			'status' : status
		};

		$.ajax({
			type : 'POST',
			url : './editMarrieddeal.do',
			data : parm,
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$('#marrieddealId').bootstrapTable('refresh');
				}

			}
		});
}

function submit() {

	$.ajax({
		type : "POST",
		url : './editMarrieddeal.do',
		data : {
			marrieddealId : $("#marrieddealId").val(),
			status : $('input:radio[name="status"]:checked').val()
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#quxiao").click();
				$('#marrieddealId').bootstrapTable('refresh');
			}
		}
	});
}
function findMarri(s) {
	if(s==3){
		$("#status").val("");
	}else{
		$("#status").val(s);
	}
	var cfg = {
		url : "./marrieddealAudit_data.do?auditStatus=2"+urlParams(''),
		columns : columns,
		uniqueId : 'marrieddealId',
		sortName : 'marrieddealId',
		sortOrder : 'asc',
		toolbar : toolbar
	};
	createDataGrid('marrieddealId', cfg);
}