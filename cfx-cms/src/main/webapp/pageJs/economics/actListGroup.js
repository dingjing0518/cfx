$(function() {
	createDataGrid('actListGroup', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'id',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		str += '<button id="editable-sample_new" style="display:none;"  showname="EM_EMPACCOUNT_ACCOUNT_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:edit(\''
			+ value
			+ '\',\''
			+ row.groupId
			+ '\',\''
			+ row.firstName
			+ '\',\''
			+ row.groups
            +     '\'); ">编辑</button>';
		str += '<button type="button" onclick="javascript:del(\''
				+ value
				+ '\',\''
				+ row.groupId
				+ '\'); " style="display:none;" showname="EM_EMPACCOUNT_ACCOUNT_BTN_DEL" class="btn btn-danger active">删除</button>';
		
		return str;
	}
}, 
		{
			title : '帐号',
			field : 'id',
			width : 150,
			sortable : true
		},
		{
			title : '姓名',
			field : 'firstName',
			width : 150,
			sortable : true
		},
		{
			title : '邮箱',
			field : 'email',
			width : 150,
			sortable : true
		},
		{
			title : '拥有权限',
			field : 'groups',
			width : 150,
			sortable : true
		},
		 ];
var cfg = {
	url : './listWithGroups.do',
	columns : columns,
	uniqueId : 'id',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 人员管理->帐号管理 保存编辑和新增操作
 * @param accountId
 * @param groupId
 * @param name
 * @param group
 */
function submit() {
	
	
	 var groupId=$("#groupId").val();

	 if(groupId=="null"){
			new $.flavr({
				modal : false,
				content : "角色名称不能为空！"
			});
			return;
		}
	 
	 
	 
	 var accountId=$("#accountId").val();

	 if(accountId=="null"){
			new $.flavr({
				modal : false,
				content : "帐号不能为空！"
			});
			return;
		}
	
	 if(valid("#dataForm")){
		 
		 
			$.ajax({
				type : "POST",
				url : './updateActListGroup.do',
				data : {
					groupId : $("#groupId").val(),
					accountId : $("#accountId").val(),
					pk:$("#pk").val()
				},
				dataType : "json",
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); 
					if (data.success) {
					
						$("#quxiao").click();
						$('#actListGroup').bootstrapTable('refresh');
					} 
					 
				}
			});
	 }

	
}

/**
 * 人员管理->帐号管理 编辑和新增
 * @param accountId
 * @param groupId
 * @param name
 * @param group
 */
function edit(accountId, groupId,name,group) {
	if(accountId==null && groupId==null){
		$('#pk').val('add');
	}
	else{
		$('#pk').val('edit');
	}
	$('#editActListGroup').modal();
	if (null != accountId && '' != accountId) {
		$("#accountId").val(accountId);
		$("#groupId").val(groupId);
		$("button[data-id=accountId] span.filter-option").html(name); 
		 $("button[data-id=accountId]").attr("title",name); 
		 
		 $("button[data-id=groupId] span.filter-option").html(group); 
		 $("button[data-id=groupId]").attr("title",group); 
		
	} else {
		
		
		
		 $("button[data-id=accountId] span.filter-option").html("--请选择--"); 
		 $("button[data-id=accountId]").attr("title","--请选择--"); 
			
		 $("button[data-id=groupId] span.filter-option").html("--请选择--"); 
		 $("button[data-id=groupId]").attr("title","--请选择--"); 
		 $("#accountId").val('');
		 $("#groupId").val('');
		 
		
	}
}

/**
 * 人员管理->帐号管理 删除操作
 * @param accountId
 * @param groupId
 */
function del(accountId,groupId) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './deleteActListGroup.do',
				data : {
					'accountId' : accountId,
					'groupId':groupId
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao").click();
						$('#actListGroup').bootstrapTable('refresh');
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
 * 人员管理->帐号管理 搜索清除
 * @param type
 * @constructor
 */
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './listWithGroups.do'+urlParams(1),
			columns : columns,
			uniqueId : 'id',
			sortName : 'sort',
			sortOrder : 'asc',
			queryParams : function(params) {
				return {
					parentPk:'-1',
					limit : params.limit,
					start : params.offset,
					orderType : params.order,
					orderName : params.sort
				}
			},
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('actListGroup', cfg);
}