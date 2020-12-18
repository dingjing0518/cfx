$(function() {
	createDataGrid('b2bRoleId', cfg);
});

/**
 * 改变角色时切换展示树
 * @param self
 */
function changeRole(self){
	var type = $(self).val();
	if(type==1){
		$("#treeId").show();
		$("#treeId2").hide();
	}else{
		$("#treeId").hide();
		$("#treeId2").show();
	}
	
}

/**
 * 编辑角色
 * @param pk
 * @param name
 * @param companyType
 */
function editB2bRole(pk, name,companyType) {
	$('#editB2bRoleId').modal();
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$('#name').val(name);
		$('#companyType').val(companyType);
		$("#companyType").attr("disabled",true);
	} else {
		$('#companyType').val(1);
		$('#pk').val('');
		$('#name').val('');
		$("#companyType").attr("disabled",false);
	}
	$('#treeId').data('jstree', false).empty();
	$("#treeId").jstree({
		'plugins' : [ "wholerow", "checkbox", "types","sort","unique" ],
		core : {
			themes : {
				"dots": true,
				"responsive" : true
			},
			check_callback : true,
			data : {
				async : true,
				url : './lgRoleMenuTree.do?companyType=1',
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
	
	$('#treeId2').data('jstree', false).empty();
	$("#treeId2").jstree({
		'plugins' : [ "wholerow", "checkbox", "types","sort","unique" ],
		core : {
			themes : {
				"dots": true,
				"responsive" : true
			},
			check_callback : true,
			data : {
				async : true,
				url : './lgRoleMenuTree.do?companyType=2',
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
	if(companyType == 1){
		$("#treeId").show();
		$("#treeId2").hide();
	}else{
		$("#treeId").hide();
		$("#treeId2").show();
	}
	
}
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				var disable = "false";
				if(row.companyType!=0){
					str += '<button id="editable-sample_new" style="display:none;" showname="LG_ROLEMGR_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:editB2bRole(\''
						+ value + '\',\'' + row.name + '\',\'' + row.companyType + '\'); ">编辑</button>';
					str += '&nbsp;&nbsp;&nbsp;<button type="button" onclick="javascript:deleteB2bRole(\''
						+ value
						+ '\',\''
						+ row.name
						+ '\'); " style="display:none;" showname="LG_ROLEMGR_BTN_DEL" class="btn btn-danger active">删除</button>';
				}
				return str;
			}
		}, 
		{
			title : '角色名称',
			field : 'name',
			width : 150,
			sortable : true

		}, {
			title : '角色类型',
			field : 'companyType',
			width : 150,
			sortable : true,
			formatter:function(value,row,index){
				if(value==0){
					return "超级管理员";
				}else {
					return "普通业务员";
				}
			}
		} ];
var cfg = {
	url : './lgRoleTypeGrid.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 删除角色
 * @param rolepk
 * @param name
 */
function deleteB2bRole(rolepk, name) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './saveLgRole.do',
				data : {
					pk : rolepk,
					isDelete : 2
				},
				dataType : "json",
				success : function(data) {
					$("#quxiao").click();
					$('#b2bRoleId').bootstrapTable('refresh');
				}
			});
		}});	
}

/**
 * 保存角色
 */
function submit() {
	var pk = $('#pk').val();
	var type;
	if (pk.length == 0) {
		type = 0;
	} else {
		type = 1;
	}
	var treeId = "treeId";
	if($("#treeId").is(":hidden")){
		treeId = "treeId2";
	}
	var node = $("#"+treeId).jstree("get_checked");
	var name = $("#name").val();
	if (name.length == 0) {
		new $.flavr({
			modal : false,
			content : "请输入角色名称"
		});
		return;
	}
	if (node.length == 0) {
		new $.flavr({
			modal : false,
			content : "请勾选菜单角色"
		});
		return;
	}
	var NamePro = $("#namePrompt").html();
	if (NamePro.length > 0) {
		new $.flavr({
			modal : false,
			content : "该角色名称已存在"
		});
		return;
	}
	if($("#companyType").val()==""){
		new $.flavr({
			modal : false,
			content : "请选择角色类型"
		});
		return;
	}
	$.ajax({
		type : "POST",
		url : './saveLgRole.do',
		data : {
			name : name,
			node : JSON.stringify(node),
			type : type,
			companyType : 1,
			pk : pk,
			isDelete : 1
		},
		dataType : "json",
		success : function(data) {
			$("#quxiao").click();
			$('#b2bRoleId').bootstrapTable('refresh');
		}
	});

}

/**
 * 判断角色名称是否存在
 * @param name
 */
function checkLgRoleName(name) {
	$("#namePrompt").html("");
	if (name.length > 0) {
		$.ajax({
			type : 'GET',
			url : './checkLgRoleName.do',
			data : {
				'name' : name
			},
			dataType : 'json',
			success : function(data) {
				if (data.length > 0) {
					$("#namePrompt").html("该角色名称已存在");
				}

			}
		});

	}
}