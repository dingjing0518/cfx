$(function() {
	createDataGrid('seedvarietyId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		str += '<button id="editable-sample_new" style="display:none;" showname="OPER_GOODS_SETTINGS_SEEDVARI_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:edit(\''
				+ value
				+ '\',\''
				+ row.name
				+ '\',\''
				+ row.sort
				+ '\',\''
				+ row.parentPk
				+ '\',\''
				+ row.isVisable
				+ '\',\''
				+ row.parentName
				+ '\'); ">编辑</button>';
		str += '<button type="button" onclick="javascript:del(\''
				+ value
				+ '\'); " style="display:none;" showname="OPER_GOODS_SETTINGS_SEEDVARI_BTN_DEL" class="btn btn-danger active">删除</button>';
		if (row.isVisable == 1) {
			str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',2); " style="display:none;" showname="OPER_GOODS_SETTINGS_SEEDVARI_BTN_DISABLE" class="btn btn-danger active">禁用</button>';
		} else {
			str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',1); " style="display:none;" showname="OPER_GOODS_SETTINGS_SEEDVARI_BTN_ENABLE" class="btn btn-primary">启用</button>';
		}
		return str;
	}
},
		{
			title : '品种',
			field : 'parentName',
			width : 150,
			sortable : true
		},
		{
			title : '子品种',
			field : 'name',
			width : 150,
			sortable : true
		},
		{
			title : '排序',
			field : 'sort',
			width : 150,
			sortable : true
		},
		{
			title : '是否显示',
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
		},
		{
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		} ];
var cfg = {
	url : './searchVarieties.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	queryParams : function(params) {
		return {
			noparentPk : '-1',
			limit : params.limit,
			start : params.offset,
			orderType : params.order,
			orderName : params.sort
		}
	},
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 保存子品种
 */
function submit() {
	if(valid("#dataForm")){
		if(isNotZeroDNumber($("#sort").val()) == false){
			new $.flavr({
				modal : false,
				content : "输入的排序数字需大于零的整数"
			});
			return;
		}
	$.ajax({
		type : "POST",
		url : './updateVarieties.do',
		data : {
			pk : $("#pk").val(),
			name : $("#name").val(),
			sort : $("#sort").val(),
			parentPk : $("#parentPk").val(),
			isVisable : $('input:radio[name="isVisable"]:checked').val()
		},
		dataType : "json",
		success : function(data) {
			
			if (data.success) {
				new $.flavr({
					 
					content : data.msg
				}); /* Closing the dialog */
				$("#quxiao").click();
				$('#seedvarietyId').bootstrapTable('refresh');
			}else{
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
			}
		}
	});
	}
}

/**
 * 编辑子品种
 * @param pk
 * @param name
 * @param sort
 * @param parentPk
 * @param isVisable
 * @param parentName
 */
function edit(pk, name, sort, parentPk, isVisable,parentName) {
	$('#editSeedvarietyId').modal();
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#name").val(name);
		$("#sort").val(sort == 'undefined'?"":sort);
        $.ajax({
            type : "POST",
            url : './searchVarietiesList.do',
            data : {},
            dataType : "json",
            success : function(datas) {
                if (datas != null) {
                    $("#parentPk").empty();
                    for ( var i = 0; i < datas.length; i++) {
                    	if (datas[i].pk == parentPk){
                            $("#parentPk").append("<option value=\"" + datas[i].pk + "\" selected>" + datas[i].name
                                + "</option>");
						} else{
                            $("#parentPk").append("<option value=\"" + datas[i].pk + "\">" + datas[i].name
                                + "</option>");
						}
                    }
                    $('#parentPk').selectpicker('refresh');
                   	$('#parentPk').selectpicker('render');
                }
            }
        });

        $("input[name='isVisable'][value=" + isVisable + "]").prop("checked",
				true);
		 // $("button[data-id=parentPk] span.filter-option").html(parentName);
		 // $("button[data-id=parentPk]").attr("title",parentName);
	} else {
		$("#pk").val('');
		$("#name").val('');
		$("#sort").val('');
		$("#parentPk").val('');
		$("button[data-id=parentPk] span.filter-option").html("--请选择--"); 
		 $("button[data-id=parentPk]").attr("title","--请选择--");  
		$("input[name='isVisable'][value=1]").prop("checked", true);
	}
}

/**
 * 删除子品种
 * @param pk
 */
function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateVarieties.do',
				data : {
					'pk' : pk,
					'isDelete':2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao").click();
						$('#seedvarietyId').bootstrapTable('refresh');
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
 * 启用、禁用
 * @param pk
 * @param isVisable
 */
function editVisable(pk, isVisable) {
	$.ajax({
		type : 'POST',
		url : './updateVarieties.do',
		data : {
			'pk' : pk,
			'isVisable' : isVisable
		},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			}); /* Closing the dialog */
			if (data.success) {
				$("#quxiao").click();
				$('#seedvarietyId').bootstrapTable('refresh');
			}
		}
	});
}
function SearchClean(type) {
	if(type == 2){
		cleanpar();
	}
	var cfg = {
		url : './searchVarieties.do?noparentPk=-1' + urlParams(''),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		/*queryParams : function(params) {
			return {
				noparentPk : '-1',
				limit : params.limit,
				start : params.offset,
				orderType : params.order,
				orderName : params.sort
			}
		},*/
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('seedvarietyId', cfg);
}
