$(function() {
	createDataGrid('roleId', cfg);
});



/**
 * 编辑权限
 * @param pk
 * @param name
 */
function editRole(pk, name) {
	$('#editRoleId').modal();
    $("#namePrompt").html("");
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$('#name').val(name);
	} else {
		$('#pk').val('');
		$('#name').val('');
	}
	$('#treeId').data('jstree', false).empty();
	$("#treeId").jstree({
		'plugins' : [ "wholerow", "checkbox", "types" ],
		core : {
			themes : {
				"responsive" : false
			},
			check_callback : true,
			data : {
				async : true,
				url : './authorityTree.do',
				dataType : "json",
				data : function(n) {
					return {
						id : n.attr ? n.attr("id") : -1,
						pk : pk

					};
				}
			}
		},
		"types" : {
			"default" : {
				"icon" : "fa fa-folder icon-state-warning icon-lg"
			},
			"file" : {
				"icon" : "fa fa-file icon-state-warning icon-lg"
			}
		}
	});
}


var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				str += '<button id="editable-sample_new" style="display:none;" showname="MG_ROLE_BTN_EDIT"  class="btn btn-primary" data-toggle="modal" onclick="javascript:editRole(\''
						+ value + '\',\'' + row.name + '\'); ">编辑</button>';
				str += '<button style="display:none;" showname="MG_ROLE_BTN_DEL"  type="button" onclick="javascript:deleteRole(\''
						+ value
						+ '\',\''
						+ row.name
						+ '\'); " class="btn btn-danger active">删除</button>';
				return str;
			}
		}, {
			title : '权限组',
			field : 'name',
			width : 150,
			sortable : true

		} ];
var cfg = {
	url : './roleGrid.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 删除权限组
 * @param rolepk
 * @param name
 */
function deleteRole(rolepk, name) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateManaegRole.do',
				data : {
					type : 1,
					pk : rolepk,
					name : name,
					isDelete : 2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao").click();
						$('#roleId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
			
		}
	});
}

/**
 * 保存权限修改
 */
function submit() {
	var pk = $('#pk').val();
	var type;
	if (pk.length == 0) {
		type = 0;
	} else {
		type = 1;
	}
	var node = $("#treeId").jstree("get_checked");
	var name = $("#name").val();
	if (name.length == 0) {
		alert("请输入权限组");
		return;
	}
	if (node.length == 0) {
		alert("请勾选菜单权限");
		return;
	}
//	var NamePro = $("#namePrompt").html();
//	if (NamePro.length > 0) {
//		alert("该权限组已存在");
//		return;
//	}
	$.ajax({
		type : "POST",
		url : './updateManaegRole.do',
		data : {
			name : name,
			node : JSON.stringify(node),
			type : type,
			pk : pk,
			isDelete : 1
		},
		dataType : "json",
		success : function(data) {
			$("#quxiao").click();
			$('#roleId').bootstrapTable('refresh');
		}
	});

}

/**
 * 编辑时查询是否已存在相同的权限组
 * @param name
 */
function checkRoleName(name) {
	$("#namePrompt").html("");
	if (name.length > 0) {
		$.ajax({
			type : 'GET',
			url : './CheckRoleName.do',
			data : {
				'name' : name
			},
			dataType : 'json',
			success : function(data) {

				if (data.length > 0) {
					$("#namePrompt").html("该权限组已存在");
				}

			}
		});

	}
}