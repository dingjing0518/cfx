$(function() {
	createDataGrid('b2bRoleId', cfg);
});

/**
 * 根据公司不同，现在和隐藏不同的树
 * @param self
 */
function changeRole(self){
	var type = $(self).val();
    checkCompanyType(type);
	
}


function checkCompanyType(companyType){

    if(companyType == 1){
        $("#treeId").show();
        $("#treeId2").hide();

    }else if (companyType == 2) {
        $("#treeId").hide();
        $("#treeId2").show();
    }
}

/**
 * 展示前端权限数据树
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
				url : './b2bRoleMenuTree.do?companyType=1',
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
				url : './b2bRoleMenuTree.do?companyType=2',
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
    checkCompanyType(companyType);
	
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
					str += '<button id="editable-sample_new" style="display:none;" showname="OPER_MG_ROLE_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:editB2bRole(\''
						+ value + '\',\'' + row.name + '\',\'' + row.companyType + '\'); ">编辑</button>';
					str += '<button type="button" onclick="javascript:deleteB2bRole(\''
						+ value
						+ '\',\''
						+ row.name
						+ '\'); " style="display:none;" showname="OPER_MG_ROLE_BTN_DEL" class="btn btn-danger active">删除</button>';
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
				if(value==1){
					return "化纤采购商";
				}else if(value==2){
					return "化纤供应商";
				}else{
					return "超级管理员";
				}
			}
		} ];
var cfg = {
	url : './b2bRoleGrid.do',
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
				url : './saveB2bRole.do',
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
 * 提交修改
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
		url : './saveB2bRole.do',
		data : {
			name : name,
			node : JSON.stringify(node),
			type : type,
			companyType : $("#companyType").val(),
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
 * 编辑改变角色
 * @param name
 */
function checkB2bRoleName(name) {
	$("#namePrompt").html("");
	if (name.length > 0) {
		$.ajax({
			type : 'GET',
			url : './checkB2bRoleName.do',
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