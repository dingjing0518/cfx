$(function() {
	createDataGrid('accountId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		if (row.account != 'admin') {
			str += '<button id="editable-sample_new" style="display:none;" showname="MG_ACCOUNT_BTN_EDIT"  class="btn btn-primary" data-toggle="modal" onclick="javascript:editAccount(\''
					+ value
					+ '\',\''
					+ row.rolePk
					+ '\',\''
					+ row.account
					+ '\',\''
					+ row.name
					+ '\',\''
					+ row.mobile
					+ '\',\''
					+ row.email
					+ '\'); ">编辑</button>';
			str += '<button type="button" style="display:none;" showname="MG_ACCOUNT_BTN_DEL"  onclick="javascript:editState(\''
					+ value
					+ '\',1,2); " class="btn btn-danger active">删除</button>';
			if (row.isVisable == 1) {
				str += '<button type="button" style="display:none;" showname="MG_ACCOUNT_BTN_DISABLE"  onclick="javascript:editState(\''
						+ value
						+ '\',2,2); " class="btn btn-danger active">禁用</button>';
			} else {
				str += '<button type="button" style="display:none;" showname="MG_ACCOUNT_BTN_ENABLE"  onclick="javascript:editState(\''
						+ value
						+ '\',2,1); " class="btn btn-primary">启用</button>';
			}
			str += '<button type="button" style="display:none;" showname="MG_ACCOUNT_BTN_PASSWORD_RESET"  onclick="javascript:rePassword(\''
				+ value
				+ '\'); " class="btn btn-primary">密码重置</button>';
		}

		return str;
	}
}, 
		{
			title : '登录账号',
			field : 'account',
			width : 150,
			sortable : true
		},
		{
			title : '姓名',
			field : 'name',
			width : 150,
			sortable : true
		},
		{
			title : '联系电话',
			field : 'mobile',
			width : 150,
			sortable : true
		},
		{
			title : '权限组',
			field : 'roleName',
			width : 150,
			sortable : true
		},
		{
			title : '电子邮箱',
			field : 'email',
			width : 150,
			sortable : true
		},
		{
			title : '是否禁用',
			field : 'isVisable',
			width : 80,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else {
					return "禁用";
				}

			}

		}
		];
var cfg = {
	url : './searchManageAccountList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess : btnList

};

/**
 * 保存
 */
function submit() {
	var account = $("#account").val();
	var name = $("#name").val();
	var mobile = $("#mobile").val();
	if (account.length == 0) {
		alert("请输入登录账号");
		return;
	}
	if (name.length == 0) {
		alert("请输入姓名");
		return;
	}

	if (mobile.length == 0) {
		alert("请输入联系电话");
		return;
	}

	$.ajax({
		type : 'POST',
		url : "./updateManageAccount.do",
		data : $('#dataForm').serialize(),
		dataType : 'json',
		async : true,
		success : function(data) {
			$("#quxiao").click();
			$('#accountId').bootstrapTable('refresh');
		}
	});

}

/**
 * 编辑
 * @param pk
 * @param rolePk
 * @param account
 * @param name
 * @param mobile
 * @param email
 */
function editAccount(pk, rolePk, account, name, mobile, email) {
	$('#myModal1').modal();
	if (null != pk && '' != pk) {
		$("#rolePk").val(rolePk);
		$("#account").val(account);
		$("#name").val(name);
		$("#mobile").val(mobile);
		$("#email").val(email);
		$("#pk").val(pk);
	} else {
		$("#account").val('');
		$("#name").val('');
		$("#mobile").val('');
		$("#email").val('');
		$("#pk").val('');
	}

}

/**
 * 删除、启用和禁用
 * @param pk
 * @param type
 * @param state
 */
function editState(pk, type, state) {
	var parm = null;
	if (type == 1) {
		parm = {
			'pk' : pk,
			'isDelete' : state,
			'isVisable' : state
		};
	} else {
		parm = {
			'pk' : pk,
			'isDelete' : 1,
			'isVisable' : state
		};
	}
	$.ajax({
		type : 'POST',
		url : 'updateIsVisableOrDelete.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			$("#quxiao").click();
			$('#accountId').bootstrapTable('refresh');

		}
	});

}

/**
 * 密码重置
 * @param pk
 */
function rePassword(pk){
	
	
	var confirm = new $.flavr({
		content : '确定要重置此账号的密码吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './updateRePassword.do',
				dataType : "json",
				data : {
					pk:pk
				},
				success : function(data) {
						new $.flavr({
							modal : false,
							content : data.msg
						});
						$("#quxiao").click();
						$('#accountId').bootstrapTable('refresh');
				}
			});
		}
	});
}
	
