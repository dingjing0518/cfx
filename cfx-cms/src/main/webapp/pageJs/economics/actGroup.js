$(function() {
	createDataGrid('actGroupId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'id',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
//		if (row.account != 'admin') {
			str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" style="display:none;" showname="EM_EMPACCOUNT_ROLE_BTN_EDIT" onclick="javascript:editAccount(\''
					+ value
					+ '\',\''
					+ row.name
					+ '\',\''
					+ row.type
					+ '\'); ">编辑</button>';
			str += '<button type="button"  style="display:none;" showname="EM_EMPACCOUNT_ROLE_BTN_DEL" onclick="javascript:editState(\''
					+ value
					+ '\'); " class="btn btn-danger active">删除</button>';
		
//		}

		return str;
	}
}, 
		{
			title : '角色名',
			field : 'id',
			width : 150,
			sortable : true
		},
		{
			title : '角色名称',
			field : 'name',
			width : 150,
			sortable : true
		}
		
		];
var cfg = {
	url : './searchActGroupList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 用户管理->人员角色 保存编辑和新增操作
 */
function submit() {
	
	var id = $("#id").val();
	var name = $("#name").val();
	var type=$("#type").val();
	 if(validNotHidden("#dataForm")){
		 $.ajax({
				type : 'POST',
				url : "./updateActGroup.do",
				data : {
					 id : id,
					 name  : name,
					 type:type
				},
				dataType : 'json',
				async : true,
				success : function(data) {
					
					new $.flavr({
						modal : false,
						content : data.msg
					});
					if (data.success) {
						$("#quxiao").click();
						$('#actGroupId').bootstrapTable('refresh');
					}
					
				}
			});
	 }
	

}

/**
 * 用户管理->人员角色 编辑和新增
 * @param pk
 * @param name
 * @param type
 */
function editAccount(pk, name,type) {

	$('#myModal1').modal();
	if (null != pk && '' != pk) {
	
		$("#id").attr("disabled",true);
		$("#name").val(name);
		$("#id").val(pk);
		$("#type").val(type);
		
	} else {
		$("#id").attr("disabled",false);
		$("#name").val('');
		$("#id").val('');
		$("#type").val('');
	}

}

/**
 * 用户管理->人员角色 删除
 * @param pk
 */
function editState(pk) {
	
	var parm = null;

		parm = {
			'id' : pk
		};
	
	$.ajax({
		type : 'POST',
		url : 'deleteActGroup.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			
			if(!data.success){
				new $.flavr({
					modal : false,
					content : data.msg
				});
			}
			$("#quxiao").click();
			$('#actGroupId').bootstrapTable('refresh');

		}
	});

}	

	
