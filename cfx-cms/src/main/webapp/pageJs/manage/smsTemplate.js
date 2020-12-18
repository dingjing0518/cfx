$(function() {
	createDataGrid('smsTemplateId', cfg);
	$("#smsType").val(1);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'templateCode',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		if (row.isSms == 1) {
			str += '<button type="button" style="display:none;" showname="MG_SMS_BTN_MAS_ISVISABLE" onclick="javascript:editSms(\''
					+ value
					+ '\',1,2); " class="btn btn-danger active">禁用短信</button>';
		} else {
			str += '<button type="button" style="display:none;" showname="MG_SMS_BTN_MAS_ENABLE" onclick="javascript:editSms(\''
					+ value
					+ '\',1,1); " class="btn btn-primary">启用短信</button>';
		}
		if (row.isMail == 1) {
			str += '<button type="button" style="display:none;" showname="MG_SMS_BTN_MAIL_ISVISABLE" onclick="javascript:editSms(\''
					+ value
					+ '\',2,2); " class="btn btn-danger active">禁用站内信</button>';
		} else {
			str += '<button type="button" style="display:none;" showname="MG_SMS_BTN_MAIL_ENABLE" onclick="javascript:editSms(\''
					+ value
					+ '\',2,1); " class="btn btn-primary">启用站内信</button>';
		}
		if (row.type !=3) {
			str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display: none;" showname="MG_SMS_BTN_BIND_ROLE" class="btn btn-warning" data-toggle="modal" data-target="#setRole" onclick="javascript:saveRole(\'' + row.name + '\',\'' + row.type + '\'); ">绑定角色 </button> </a>';
			//str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display: none;" showname="BTN_ROLEMEMBER" class="btn btn-warning" data-toggle="modal" data-target="#setBackRole" onclick="javascript:saveBackRole(\'' + row.name + '\',\'' + row.type + '\'); ">绑定后台角色 </button> </a>';
			/*str += '&nbsp;&nbsp;&nbsp;<button type="button" style="display:none;" showname="BTN_VISPRODUCT" onclick="javascript:assignRoles(\''
				+ value
				+ '\',2,1); " class="btn btn-primary">配置角色</button>';*/
		}
		
	 
		return str;
	}
},       
		{
			title : '模板名称',
			field : 'freeSignName',
			width : 150,
			sortable : true
		},
		{
			title : '短信内容',
			field : 'content',
			width : 150,
			sortable : true
		},
		{
			title : '短信状态',
			field : 'isSms',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else  {
					return "禁用";
				}  

			}
		}, 
		{
			title : '站内信状态',
			field : 'isMail',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else  {
					return "禁用";
				}  

			}
		}
		 
	  ];
var cfg = {
	url : './smsTemplateList.do?type=1',
	columns : columns,
	uniqueId : 'templateCode',
	sortName : 'templateCode',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList,
	fixedColumns : true,
	fixedNumber : 1
};


function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './smsTemplateList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'templateCode',
			sortName : 'templateCode',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('smsTemplateId', cfg);
}

/**
 * table列表页面切换
 * @param s
 */
function findSmsType(s){
	$("#smsType").val(s);
	if(s==0){
		$("#type").val("");
	}else{
		$("#type").val(s);
	}
	var cfg = {
			url : './smsTemplateList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'templateCode',
			sortName : 'templateCode',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('smsTemplateId', cfg);
}

/**
 * 启用、禁用短信站内信
 * @param templateCode
 * @param type
 * @param isValue
 */
function editSms(templateCode, type,isValue) {
	var parm = null;
	if (type == 1) {
		parm = {
			'templateCode' : templateCode,
			'isSms' : isValue
		};
	} else {
		parm = {
				'templateCode' : templateCode,
				'isMail' : isValue
		};
	}
	$.ajax({
		type : 'POST',
		url : './updateSmsTemplate.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			}); /* Closing the dialog */
			if (data.success) {
				$('#smsTemplateId').bootstrapTable('refresh');
			}
		}
	});
}

/**
 * 绑定角色
 * @param name
 * @param type
 */
function saveRole(name,type){
	$("#smsName").val(name);
	var columnMember = [
		          		{
		          			title : '操作',
		          			field : 'pk',
		          			width : 200,
		          			formatter : function(value, row, index) {
		          				var str ="";
		          				if(row.isRole !="-1"){
		          					$("#oldMemberPk").val(row.pk);
		          					str =  "<input type='checkbox' value='"+row.pk+"' checked='checked' onchange='changeMember(this)'>";
		          				}else{
		          					str =  "<input type='checkbox' value='"+row.pk+"' onchange='changeMember(this)'>";
		          				}
		          				return str;
		          			}},
				   		{
				   			title : '角色',
				   			field : 'name',
				   			width : 20,
				   			sortable : true
				   		},
				   		{
				   			title : '类型',
				   			field : 'companyType',
				   			width : 20,
				   			sortable : true,
				   			formatter : function(value, row, index) {
		          				if(value == 1){
		          					return "采购商";
		          				}else if(value == 2){
		          					return "供应商";
		          				}else{
		          					return "超级管理员";
		          				}
		          			}
				   		}];
	var url = './getRoleListBySms.do?smsName='+name+'&companyType='+$("#smsType").val();
	var cfg = {
			url : url,
			columns : columnMember,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('roles', cfg);
}

/**
 * 绑定后台角色
 * @param name
 * @param type
 */
function saveBackRole(name,type){
	$("#smsBackName").val(name);
	var columnMember = [
		          		{
		          			title : '操作',
		          			field : 'pk',
		          			width : 200,
		          			formatter : function(value, row, index) {
		          				var str ="";
		          				if(row.isRole !="-1"){
		          					$("#oldMemberPk").val(row.pk);
		          					str =  "<input type='checkbox' value='"+row.pk+"' checked='checked' onchange='changeMember(this)'>";
		          				}else{
		          					str =  "<input type='checkbox' value='"+row.pk+"' onchange='changeMember(this)'>";
		          				}
		          				return str;
		          			}},
				   		{
				   			title : '后台角色',
				   			field : 'name',
				   			width : 20,
				   			sortable : true
				   		}];
	var url = './getBackRoleListBySms.do?smsName='+name;
	var cfg = {
			url : url,
			columns : columnMember,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('backRoles', cfg);
}

/**
 * 保存 绑定角色
 */
function saveRoles(){
	var rolePks = "";
	$("#roles input[type=checkbox]:checked").each(function(){
		rolePks += $(this).attr("value")+",";
	});
	$.ajax({
		type : 'POST',
		url : './insertSmsRole.do',
		data : {smsName:$("#smsName").val(),rolePks:rolePks,type:$("#smsType").val()},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$(function () { $('#setRole').modal('hide')});
			$('#smsTemplateId').bootstrapTable('refresh');
		}
	});
	
}

/**
 * 保存 后台绑定角色
 */
function saveBackRoles(){
	var rolePks = "";
	$("#backRoles input[type=checkbox]:checked").each(function(){
		rolePks += $(this).attr("value")+",";
	});
	$.ajax({
		type : 'POST',
		url : './setSmsBackRole.do',
		data : {smsName:$("#smsBackName").val(),rolePks:rolePks,type:3},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$(function () { $('#setBackRole').modal('hide')});
			$('#smsTemplateId').bootstrapTable('refresh');
		}
	});
	
}