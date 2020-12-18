$(function() {
	createDataGrid('personnelId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var inserttime=row.insertTime;
		var str = ""; 
			str += '<button id="editable-sample_new" style="display:none;" showname="MARKET_PERSON_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:editAccount(\''
					+ value
					+ '\',\''
					+ row.type
					+ '\',\''
					+ inserttime
					+ '\',\''
					+ row.accountPk
					+ '\',\''
					+ row.accountName
					+ '\',\''
					+ row.regionPk
					+ '\',\''
					+ row.regionName
					+ '\',\''
					+ row.isVisable
					+ '\'); ">编辑</button>';
			str += '<button type="button" onclick="javascript:del(\''
					+ value + '\',\''
					+ row.type
					+ '\' ); " style="display:none;" showname="MARKET_PERSON_BTN_DEL" class="btn btn-danger active">删除</button>';
			if (row.isVisable == 1) {
				str += '<button type="button" onclick="javascript:editState(\''
						+ value
						+ '\',2); " style="display:none;" showname="MARKET_PERSON_BTN_DISABLE" class="btn btn-danger active">禁用</button>';
			} else {
				str += '<button type="button" onclick="javascript:editState(\''
						+ value
						+ '\',1); " style="display:none;" showname="MARKET_PERSON_BTN_ENABLE" class="btn btn-primary">启用</button>';
			}

		return str;
	}
},       
		{
			title : '账号类型',
			field : 'type',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "业务经理";
				} else if(value==2) {
					return "平台交易员";
				}else if(value==3) {
					return "区域经理";
				}

			}
		},
		{
			title : '账号',
			field : 'accountName',
			width : 150,
			sortable : true
		},
		{
			title : '所属大区',
			field : 'regionName',
			width : 150,
			sortable : true
		},

		{
			title : '启用/禁用',
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

		},	{
			title : '修改时间',
			field : 'updateTime',
			width : 20,
			sortable : true
		}
		 ];
var cfg = {
	url : './personnel_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function submit() {
	var type = $("#type").val();
	
	if (valid("#dataForm")) {
		if(type=='3'){
			if($("#regionPk").val()==''){
				new $.flavr({
					modal : false,
					content : "区域经理所属大区不能为空！"
				});
				return;
			}
		}
		$.ajax({
			type : 'POST',
			url : "./insertPersonnel.do",
			data :{
				pk:$("#pk").val(),
				type:type,
				accountPk:$("#accountPk").val(),
				accountName:$("button[data-id=accountPk]").attr("title"),
				regionPk:$("#regionPk").val()
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
				$('#personnelId').bootstrapTable('refresh');
				}
			}
		});
	}

}

/**
 * 编辑人员
 * @param pk
 * @param type
 * @param insertTime
 * @param accountPk
 * @param accountName
 * @param personnelPk
 * @param personnelName
 * @param isVisable
 */
function editAccount(pk, type ,insertTime ,accountPk,accountName ,regionPk,regionName, isVisable) {
		$('#myModal1').modal();
		$("#regionPk").val('');
		$("button[data-id=regionPk]").attr("title",""); 
	    $("button[data-id=regionPk] span.filter-option").html("--请选择--");
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#type").val(type);
		if(type==1){
			$("button[data-id=type] span.filter-option").html("业务经理");
		}else if(type==2){
			$("button[data-id=type] span.filter-option").html("平台业务员");
		}else if(type==3){
			$("button[data-id=type] span.filter-option").html("区域经理");
		}
		
		$("#insertTime").val(insertTime);
		if(regionPk!=''&&regionPk!='undefined'&&regionPk!=undefined){
			$("#regionPk").val(regionPk);
		    $("button[data-id=regionPk] span.filter-option").html(regionName);
		}
        selectAccount(accountPk,accountName);
		
	} else {
		changeAccount();
		$("#pk").val('');
		$("#type").val('');
	    $("#insertTime").val('');
	    $("#accountPk").val('');
	    $("button[data-id=type]").attr("title",""); 
	    $("button[data-id=type] span.filter-option").html("--请选择--"); 
	    $("button[data-id=accountPk]").attr("title",""); 
	    $("button[data-id=accountPk] span.filter-option").html("--请选择--"); 
	   
	}

}

/**
 * 启用、禁用
 * @param pk
 * @param state
 */
function editState(pk, state) {
	$.ajax({
		type : 'POST',
		url : './insertPersonnel.do',
		data : {
			pk:pk,
			isVisable:state
		},
		dataType : 'json',
		success : function(data) {
			$("#quxiao").click();
			$('#personnelId').bootstrapTable('refresh');

		}
	});

}

/**
 * 编辑时显示人员信息
 */
function changeAccount(){
	$.ajax({
		type : 'POST',
		url : './onlineAccount.do',
		data : {},
		dataType : 'json',
		success : function(data) {
			$("#accountPk").empty();
			$("#accountPk").append(" <option value=''>--请选择--</option>");
				if (data.length > 0) {
				
					for ( var i = 0; i < data.length; i++) {
						$("#accountPk").append(
								"<option value='" + data[i].pk + "'>"
										+ data[i].name + "</option>");
					}
				
				}
				$("#accountPk").selectpicker('refresh');//	
		
		}
	});
	
}

/**
 * 新增时显示人员信息
 * @param accountPk
 * @param accountName
 */
function selectAccount(accountPk,accountName){
    $.ajax({
        type : 'POST',
        url : './onlineAccount.do',
        data : {},
        dataType : 'json',
        success : function(data) {
            $("#accountPk").empty();
            $("#accountPk").append(" <option value=''>--请选择--</option>");
            $("#accountPk").append(
                "<option value='" + accountPk + "' selected>"
                + accountName + "</option>");
            if (data.length > 0) {
                for ( var i = 0; i < data.length; i++) {
                        $("#accountPk").append(
                            "<option value='" + data[i].pk + "'>"
                            + data[i].name + "</option>");
                }
            }
            $("#accountPk").selectpicker('refresh');//

        }
    });

}

/**
 * 删除人员
 * @param pk
 */
function del(pk,type) {
	var confirm = new $.flavr({
		content : '确定删除该账户所设置相关数据?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './insertPersonnel.do',
				data : {
					pk:pk,
					isDelete:2,
					type:type
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); 
					if (data.success) {
						$("#quxiao").click();
						$('#personnelId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}


function Search() {
	var cfg = {
		url : './personnel_data.do'+ urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
		
	};
	createDataGrid('personnelId', cfg);
}