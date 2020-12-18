$(function() {
	createDataGrid('purchaserMembersId', cfg);
});

/**
 * 获取跳转链接参数
 * @param key
 * @returns {*}
 * @constructor
 */
function GetRequest(key) {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest[key];
}
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 200,
			formatter : function(value, row, index) {
				var str = "";
				str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display: none;" showname="COMMON_CM_MEMBER_BTN_CREDIT" class="btn btn-warning" onclick="javascript:editAudit(\''
						+ value
						+ '\',\''
						+ row.companyName
						+ '\',\''
						+ row.mobile
						+ '\',\''
						+ row.rolePk
						+ '\',\''
						+ row.companyPk
						+ '\',\''
						+ row.registerSource
						+ '\',\''
						+ row.companyType
						+ '\'); ">  审核 </button> </a>';
				
				if (row.auditStatus == 1) {
				} else if (row.auditStatus == 2) {
					str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display: none;" showname="COMMON_CM_MEMBER_BTN_DEL" class="btn btn-warning" onclick="javascript:delMember(\''
						+ value + '\'); ">  删除 </button> </a>';
					str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display: none;" showname="COMMON_CM_MEMBER_BTN_BINDROLE" class="btn btn-warning" data-toggle="modal" data-target="#setRole" onclick="javascript:saveRole(\''
							+ value + '\'); ">绑定角色 </button> </a>';
				} else if (row.auditStatus == 3) {
				}
				if (row.isVisable == 1) {
					str += '<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display: none;" showname="COMMON_CM_MEMBER_BTN_DISABLE" class="btn btn btn-default" onclick="javascript:editState(\''
							+ value + '\',2); ">禁用</button></a>';
				} else {
					str += '<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display: none;" showname="COMMON_CM_MEMBER_BTN_ENABLE" class="btn btn btn-default" onclick="javascript:editState(\''
							+ value + '\',1); ">启用</button></a>';

				}

				return str;
			}
		}, {
			title : '公司名称',
			field : 'companyName',
			width : 20,
			sortable : true
		},

		{
			title : '注册电话',
			field : 'mobile',
			width : 20,
			sortable : true
		},

		{
			title : '员工姓名',
			field : 'employeeName',
			width : 20,
			sortable : true
		},

		{
			title : '员工工号',
			field : 'employeeNumber',
			width : 20,
			sortable : true
		},

		{
			title : '所属角色',
			field : 'roleName',
			width : 20,
			sortable : true
		}, {
			title : '启用/禁用',
			field : 'isVisable',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else if (value == 2) {
					return "禁用";
				}
			}

		}, {
			title : '审核状态',
			field : 'auditStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "待审核";
				} else if (value == 2) {
					return "审核通过";
				} else if (value == 3) {
					return "审核不通过";
				}

			}
		}, {
			title : '累计纤贝数',
			field : 'shell',
			width : 20,
			sortable : true
		}, {
			title : '累计经验值',
			field : 'experience',
			width : 20,
			sortable : true
		}, {
			title : '等级',
			field : 'level',
			width : 20,
			sortable : true
		}, {
			title : '等级名称',
			field : 'levelName',
			width : 20,
			sortable : true
		}, {
			title : '最后赠送时间',
			field : 'feedTime',
			width : 20,
			sortable : true
		}, {
			title : '注册时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}, {
			title : '最后修改时间',
			field : 'updateTime',
			width : 20,
			sortable : true
		}, {
			title : '审核时间',
			field : 'auditTime',
			width : 20,
			sortable : true
		} ];
var cfg = {
	url : './searchMembersList.do?type=1' + urlParams()+"&modelType="+GetRequest("modelType"),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList,
	fixedColumns : true,
	fixedNumber : 1,
};

/**
 * 审核会员
 * @param pk
 * @param name
 * @param contacts
 * @param rolePk
 * @param companyPk
 * @param registerSource
 * @param companyType
 */
function editAudit(pk, name, contacts, rolePk, companyPk, registerSource,
		companyType) {

	$('#auditMemberId').modal();
	$("#dataForm .bootstrap-select button").css("width", "435px");
	if (null != pk && '' != pk) {
		$('#auditPk').val(pk);
		$('#auditPhone').val(contacts);
		$('#rolePk').val(rolePk);
		if (companyType == 0) {
			$(".roleTwo").show();
			$(".roleOne").hide();
		} else {
			$(".roleTwo").hide();
			$(".roleOne").show();
		}
		if (name == 'undefined') {
			$('#auditCompanyPk').val('');
		} else {
			$('#auditCompanyPk').val(name);
		}
		$("#dataForm .pull-left").text(name);
		$('#auditNoPassR').val("");
	}

}

/**
 * 提交会员审核
 * @param auditStatus
 */
function auditSubmit(auditStatus) {
	var pk = $('#auditPk').val();
	var parm = {
		'pk' : pk,
		'auditStatus' : auditStatus
	};

	if (auditStatus == 2) {
		parm['isAuditStatusTwo'] = '2';
		sendPost(parm);
	} else {
		$("#auditPk").val(pk);
		var context = $("#auditNoPassR").val();

		if (context.replace(/(^\s*)|(\s*$)/g, "") == "" || context == null) {
			new $.flavr({
				modal : false,
				content : "请填写不通过原因！"
			});
		} else {
			refuse(pk, auditStatus, context);
		}

	}

}

/**
 * 删除会员
 * @param pk
 */
function delMember(pk) {
	var params = {
		pk : pk
	};
	var confirm = new $.flavr({
		content : '确定刪除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			$.ajax({
				type : 'POST',
				url : './delMember.do',
				data : params,
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
					$("#qx").click();
					$('#purchaserMembersId').bootstrapTable('refresh');
				}
			});
		}
	});
}

/**
 * 审核不通过时 触发该方法
 * @param pk
 * @param auditStatus
 * @param context
 */
function refuse(pk, auditStatus, context) {
	var params = {
		pk : pk,
		auditStatus : auditStatus,
		refuseReason : context
	};
	$.ajax({
		type : 'POST',
		url : './updateMember.do',
		data : params,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#qx").click();
			$(function() {
				$('#auditMemberId').modal('hide')
			});
			$('#purchaserMembersId').bootstrapTable('refresh');
		}
	});
}

/**
 * 启用禁用
 * @param pk
 * @param isvi
 */
function editState(pk, isvi) {
	var parm = {
		'pk' : pk,
		'isVisable' : isvi
	};
	sendPost(parm);

}
function editMember(pk, name, contacts, rolePk, companyPk, registerSource,
		companyType, auditStatus) {
	$('#editMemberId').modal();
	if (auditStatus == 2) {
		$("#companyPk").attr("disabled", true);
	} else {
		$("#companyPk").attr("disabled", false);
	}
	$("#dataForm .bootstrap-select button").css("width", "435px");
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$('#name').val(name);
		$('#phone').val(contacts);
		$('#rolePk').val(rolePk);
		if (companyType == 0) {
			$(".roleTwo").show();
			$(".roleOne").hide();
		} else {
			$(".roleTwo").hide();
			$(".roleOne").show();
		}
		// $('#insertTime').val(insertTime);
		// $('#auditTime').val(auditTime);
		$('#companyPk').val(companyPk);
		$('#registerSource').val(registerSource);
		$("#dataForm .pull-left").text(name);
	} else {
		$('#pk').val('');
		$('#companyName').val('');
		$('#phone').val('');
		$('#rolePk').val('');
		// $('#insertTime').val('');
		// $('#auditTime').val('');
		$('#registerSource').val('');
	}
}

/**
 * 会员新增
 */
function addMember() {
	$('#editMemberId').modal();
	$("#dataForm .bootstrap-select button").css("width", "435px");

	$('#phone').val("");
	$('#employeeNumber').val("");
	$('#employeeName').val("");
	$("#dataForm").find("select").each(function() {
		if (!$(this).attr("disabled")) {
			$(this).val("");
		}
	});
	$("#dataForm .pull-left").text("--请选择--");
	$(".roleTwo").hide();
	$(".roleOne").show();
}

/**
 * 采购商会员信息编辑 提交前判断
 */
function submit() {

	if ($("#companyPk").val() == "") {
		new $.flavr({
			modal : false,
			content : "请选择公司"
		});
		return;
	}
	var mobile = $('#phone').val();
	if (mobile.length == 0) {
		new $.flavr({
			modal : false,
			content : "请输入电话"
		});
		return;
	}
	if (regMobile(mobile) == false) {
		new $.flavr({
			modal : false,
			content : "手机号码格式不正确！"
		});
		return;
	}
	var employeeName = $('#employeeName').val();
	if (null != employeeName && "" != employeeName
			&& employeeName.length>20) {
		new $.flavr({
			modal : false,
			content : '员工姓名不能超过10个汉字或20个英文字符！'
		});
		return;
	}
	var employeeNumber = $('#employeeNumber').val();
	if (null != employeeNumber && "" != employeeNumber
			&& employeeNumber.length>20) {
		new $.flavr({
			modal : false,
			content : '员工工号不能超过10个汉字或20个英文字符！'
		});
		return;
	}

	var params = {};
	$("#dataForm").find("input,select").each(function() {
		if ($(this).attr("name") != undefined) {
			params[$(this).attr("name")] = $(this).val();
		}
	});

	params['companyName'] = $(
			"#companyPk option[value='" + params['companyPk'] + "']").text();

	sendPost(params);
	$("#quxiao").click();
}

/**
 * 采购商会员信息编辑
 * 审核通过
 * 启用禁用
 * 提交
 * @param params
 */
function sendPost(params) {
	$.ajax({
		type : 'POST',
		url : './updateMember.do',
		data : params,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$(function() {
				$('#auditMemberId').modal('hide')
			});
			$('#purchaserMembersId').bootstrapTable('refresh');
		}
	});
}

/**
 * 会员审核状态table切换
 * @param s
 */
function findPurchaserMem(s) {
	$("#auditStatus").val(s);
	var cfg = {
		url : './searchMembersList.do?type=1' + urlParams('')+"&modelType="+$("#modelType").val(),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('purchaserMembersId', cfg);
}

/**
 * 搜索和清除
 * @param type
 */
function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchMembersList.do?type=1' + urlParams('')+"&modelType="+$("#modelType").val(),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('purchaserMembersId', cfg);
}

/**
 * 绑定角色
 * @param pk
 */
function saveRole(pk) {
	$("#memberPk").val(pk);
	$('select[multiple="multiple"]').multiselect('clearSelection');
	$('select[multiple="multiple"]').multiselect('refresh');
	 
	$.ajax({
		type : 'POST',
		url : './getRoleByMember.do',
		data : {
			memberPk :pk
		},
		dataType : 'json',
		success : function(data) {
			if(data.length!=0){
				for(var i = 0 ; data.length>i ;i++){
					//采购商
					if(data[i].companyType==1 || data[i].companyType==3){

					    $("#purchaserRole").find("option[value='"+data[i].pk+"']").prop("selected",true);
					    $("#purchaserRole").multiselect('refresh');
					}
					//供应商
					if(data[i].companyType==2 ||data[i].companyType==4){
						 $("#supplierRole").find("option[value='"+data[i].pk+"']").prop("selected",true);
						 $("#supplierRole").multiselect('refresh');
					}
				}
				
				
				
			}
		}
	});

}

/**
 * 导出会员
 */
function excel() {

	// window.location.href = './exportMembersList.do' + urlParams(1)
	// 		+ "&isDelete=1"+"&modelType="+$("#modelType").val();
    $.ajax({
        type : 'POST',
        url : './exportMembersList.do' + urlParams(1)
            + "&isDelete=1",
        data : {},
        dataType : 'json',
        success : function(data) {
            if (data.success){
                new $.flavr({
                    modal : false,
                    content :data.msg
                });
            }
        }
    });
}

/**
 * 会员绑定角色保存
 */
function saveRoles() {
	var rolePks = "";
	var supType = [];
	var purType = [];
	
	$("#purchaserRole option:selected").each(function(){
		rolePks += $(this).val()+",";
		purType.push($(this).attr("purType")); 
    });
	
	if (checkArry(purType)) {
		new $.flavr({
			modal : false,
			content : "单个商城模块不能设置多个采购商角色！！"
		});
		return;
	}
	
	$("#supplierRole option:selected").each(function(){
		rolePks += $(this).val()+",";
		supType.push($(this).attr("supType")); 
    });
	
	if (checkArry(supType)) {
		new $.flavr({
			modal : false,
			content : "单个商城模块不能设置多个供应商角色！"
		});
		return;
	}

	$.ajax({
		type : 'POST',
		url : './insertMemberRole.do',
		data : {
			memberPk : $("#memberPk").val(),
			rolePks : rolePks
		},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$(function() {
				$('#setRole').modal('hide')
			});
			$('#purchaserMembersId').bootstrapTable('refresh');
		}
	});

}




function  checkArry (ary) {
	var flag = false;
	if(ary.length>1){
		var nary=ary.sort();
		for(var i=0;i<ary.length;i++){
			if (nary[i]==nary[i+1]){
				flag = true; 
				break ; 
			}
		}
	}
	return flag
	
}