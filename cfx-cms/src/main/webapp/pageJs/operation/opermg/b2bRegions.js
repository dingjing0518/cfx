$(function() {
	editB2bRole();
});

/**
 * 展示地区树
 */
function editB2bRole() {
	$('#treeId').data('jstree', false).empty();
	$("#treeId").jstree({
		'plugins' : [ "wholerow", "checkbox", "types","state","contextmenu","dnd"],//,"contextmenu","ui","themes","json_data",'unique'
		core : {
			themes : {
				"responsive" : false
			},
			check_callback : true,
			data : {
				async : true,
				url : './b2bRegionsManageMenuTree.do',
				dataType : "json",
				data : function(n) {
					btnList();
					return {
						id : n.attr ? n.attr("id") : -1
					};
				}
			}
		},
		"contextmenu":{
					"items":{
						"create":null,  
			            "rename":null,  
			            "remove":null,  
			            "ccp":null,
			            "新建地区":{
			            	"label":"新建地区",
			            "action":function(data){
			            	var inst = jQuery.jstree.reference(data.reference);

			            	var node = inst.get_node(data.reference);
			            	edit(node.id, node.text);
			            	
			            }
		
			            },
			            "删除地区":{
			            	"label":"删除地区",
			            	"action":function(data){
			            		var inst = jQuery.jstree.reference(data.reference);
			            		var node = inst.get_node(data.reference);
			            		deleteRegion(node.id, node.text);
        	
			            	}

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

/**
 * 编辑
 * @param parentPk
 * @param parentName
 */
function edit(parentPk, parentName) {
	$('#editRegionsId').modal();
	if (null != parentPk && '' != parentPk) {
		$("#parentPk").val(parentPk);
		$("#parentName").val(parentName);

	} else {
		$("#parentPk").val('');
		$("#parentName").val('');

	}
}

/**
 * 删除
 * @param pk
 * @param name
 */
function deleteRegion(pk, name){
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
	$.post("./updateRegionsManageMenuTree.do",{pk:pk,name:name,isDelete:2},function(data){
		if(data){
			new $.flavr({
					modal : false,
					content : data.msg
				});
				$(function () { $('#editRegionsId').modal('hide')});
				window.location.href = getRootPath()+"/regionsManage.do";
			}
		},"json");
	}
});
}

/**
 * 保存编辑地区
 */
function submit() {
	
	
	if(valid("#dataForm")){
		$.post("./updateRegionsManageMenuTree.do",{parentPk:$("#parentPk").val(),name:$("#name").val(),isDelete:1},function(data){
			if(data){
				new $.flavr({
					modal : false,
					content : data.msg
				});
				$(function () { $('#editRegionsId').modal('hide')});
				window.location.href = getRootPath()+"/regionsManage.do";
			}
		},"json");
	}
}

/**
 * 同步地区到OSS json格式
 */
function saveRegions(){
	$.ajax({
		type : "POST",
		url : './saveB2bRegionsToMongo.do',
		data : "",
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiao").click();
			$('#purchaserId').bootstrapTable('refresh');
		}
	});
	
}
