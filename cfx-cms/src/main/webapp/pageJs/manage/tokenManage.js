$(function() {
	createDataGrid('tokenId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
//		if ($("#accountName").val() == 'admin') {
			str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" style="display:none;" showname="MG_TOKEN_BTN_EDIT" onclick="javascript:editToken(\''
					+ value
					+ '\',\'' + row.appId + '\',\'' + row.appSecret + '\',\'' + row.storePk + '\',\'' + row.storeName + '\',\'' + row.tokenType + '\',\'' + row.accType + '\'); ">编辑</button>';
			str += '<button type="button" style="display:none;" showname="MG_TOKEN_BTN_DEL" onclick="javascript:editState(\''
					+ value
					+ '\',1,2); " class="btn btn-danger active">删除</button>';
			if (row.isVisable == 1) {
				str += '<button type="button" style="display:none;" showname="MG_TOKEN_BTN_DISABLE" onclick="javascript:editState(\''
						+ value
						+ '\',2,2); " class="btn btn-danger active">禁用</button>';
			} else {
				str += '<button type="button" style="display:none;" showname="MG_TOKEN_BTN_ENABLE" onclick="javascript:editState(\''
						+ value
						+ '\',2,1); " class="btn btn-primary">启用</button>';
			}
//		}

		return str;
	}
},
		{
			title : '店铺名称',
			field : 'storeName',
			width : 150,
			sortable : true
		},
//		{
//			title : '接口地址',
//			field : 'url',
//			width : 150,
//			sortable : true
//		},
		{
			title : '用途',
			field : 'tokenType',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				if(value=="/erp/"){
					return "erp";
				}
				if(value=="/b2b/pc"){
					return "pc端";
				}
				if(value=="/b2b/wap"){
					return "wap端";
				}
				if(value=="/b2b/app"){
					return "app端";
				}
			}
		},
		{
			title : '访问方式',
			field : 'accType',
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
	url : './tokenData.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function searchToken(){
	var cfg = {
			url : './tokenData.do' + urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
		createDataGrid('tokenId', cfg);
}

function submit() {
	var param = {};
	$("#dataForm").find("input,select").each(function(){
		if($(this).attr("name") !=undefined && $(this).attr("name") !=""){
			param[$(this).attr("name")] = $(this).val();
		}
	})
	if (param['storePk'] != null && param['storePk'] != ""){
        param['storeName'] = $("button[data-id='storePk']").attr("title");
	}
	if(param['appId'] == '' || param['appSecret'] == ''){
		new $.flavr({
			modal : false,
			content : "appId和appSecret不可为空"
		});
		return;
	}

	var accType = $("#accType").val();
	if (accType =='' || !isNotZeroDNumber(accType) || accType.length > 9){
        new $.flavr({
            modal : false,
            content : "请输入大于零并且小于10位的整数！"
        });
        return;
	}
	$.ajax({
		type : 'POST',
		url : './updateToken.do',
		data : param,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#quxiao").click();
				$('#tokenId').bootstrapTable('refresh');
			}

		}
	});
}

/**
 * 编辑
 * @param pk
 * @param appId
 * @param appSecret
 * @param storePk
 * @param storeName
 * @param tokenType
 * @param accType
 */
function editToken(pk,appId,appSecret,storePk,storeName,tokenType,accType) {
	$('#myModal1').modal();


    var htmlStore ="<option value=\"\">--请选择--</option>";
    $.ajax({
        type : 'POST',
        url : './tokenManageGetB2bStore.do',
        data : {},
        dataType : 'json',
        success : function(data) {
            for(var i = 0;i < data.length;i++){
                if(storePk == data[i].pk){
                    htmlStore +="<option value=\""+data[i].pk+"\" selected>"+data[i].name+"</option>";
                }else{
                    htmlStore +="<option value=\""+data[i].pk+"\">"+data[i].name+"</option>";
                }
            }
            $("#storePk").empty().append(htmlStore);
            $('#storePk').selectpicker('refresh');
        }
    });
    var htmlType ="<option value=\"\">--请选择--</option>";
    for(var n = 0;n < token_type.length; n++){
        if(token_type[n].key == tokenType){
            htmlType +="<option value=\""+token_type[n].key+"\" selected>"+token_type[n].value+"</option>";
        }else{
            htmlType +="<option value=\""+token_type[n].key+"\">"+token_type[n].value+"</option>";
        }
    }
    $("#tokenType").empty().append(htmlType);
	if (null != pk && '' != pk) {
        $("#accType").val(accType);
        $("#appSecret").val(appSecret);
        $("#appId").val(appId);
        $("#pk").val(pk);
        $("#myModalLabel").html("编辑账号");
        $("#storePk").attr("disabled","disabled");
	} else {
        $("#accType").val("");
        $("#appSecret").val("");
        $("#appId").val("");
        $("#pk").val("");
        $("#myModalLabel").html("新增账号");
		$("#storePk").attr("disabled",false);
	}
	if(pk =="" || pk == null || pk== undefined || pk== 'undefined'){
		$("#isEdit").val(2);
	}else{
		$("#isEdit").val(1);
	}

}

/**
 * 启用、禁用和删除
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
		};
	} else {
		parm = {
			'pk' : pk,
			'isVisable' : state
		};
	}
	$.ajax({
		type : 'POST',
		url : './updateTokenStatus.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
                bootstrapTableRefresh("tokenId");
			}

		}
	});

}

/**
 * 自动生成uuid
 */
function produceApp(){
	$("#appId").val(guid());
	$("#appSecret").val(guid());
}

