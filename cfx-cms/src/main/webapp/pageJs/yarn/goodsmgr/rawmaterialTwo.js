$(function() {
	createDataGrid('rawmaterialId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		str += '<button id="editable-sample_new" style="display:none;" showname="YARN_GOODS_SETTING_RAWMATTWO_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:edit(\''
				+ value
				+ '\',\''
				+ row.name
				+ '\',\''
				+ row.sort
				+ '\',\'' + row.isVisable + '\',\'' + row.parentPk + '\'); ">编辑</button>';
		str += '<button type="button" onclick="javascript:del(\''
				+ value
				+ '\'); " style="display:none;" showname="YARN_GOODS_SETTING_RAWMATTWO_BTN_DEL" class="btn btn-danger active">删除</button>';
		if (row.isVisable == 1) {
			str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',2); " style="display:none;" showname="YARN_GOODS_SETTING_RAWMATTWO_BTN_DISABLE" class="btn btn-danger active">禁用</button>';
		} else {
			str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',1); " style="display:none;" showname="YARN_GOODS_SETTING_RAWMATTWO_BTN_ENABLE" class="btn btn-primary">启用</button>';
		}
		return str;
	}
	},
		{
			title : '原料一级名称',
			field : 'parentName',
			width : 150,
			sortable : true
		}
    ,
    {
        title : '原料二级名称',
        field : 'name',
        width : 150,
        sortable : true
    },
    {
        title : '是否混纺',
        field : 'isBlendName',
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
		}, {
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		} ];
var cfg = {
	url : './searchRawMaterialTwo.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 保存
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
		if(!checkIsEmpty($("#parentPk").val())){
            new $.flavr({
                modal : false,
                content : "请选择原料一级！"
            });
            return;
		}
        if(!checkIsEmpty($("#name").val())){
            new $.flavr({
                modal : false,
                content : "请填写原料二级名称！"
            });
            return;
        }

	$.ajax({
		type : "POST",
		url : './updateRawMaterial.do',
		data : {
			pk : $("#pk").val(),
			name : $("#name").val(),
			sort : $("#sort").val(),
			parentPk : $("#parentPk").val(),
			isVisable : $('input:radio[name="isVisable"]:checked').val()
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			}); /* Closing the dialog */
			if (data.success) {
			
				$("#quxiao").click();
				$('#rawmaterialId').bootstrapTable('refresh');
			} 
			 
		}
	});
	}
}

/**
 * 编辑
 * @param pk
 * @param name
 * @param sort
 * @param isVisable
 */
function edit(pk, name, sort, isVisable,parentPk) {
	$('#editRawMaterailId').modal();
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#name").val(name);
		$("#sort").val(sort == 'undefined'?"":sort);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked",
				true);
        $.ajax({
            type : 'POST',
            url : './getRawMaterialTwo.do',
            data : {
                parentPk : "-1",
                isVisable:1
            },
            dataType : 'json',
            success : function(data) {
                $("#parentPk").empty();
                $("#parentPk").append("<option value=''>--请选择--</option>");
                if (data) {
                    for ( var i = 0; i < data.length; i++) {
                    	if(data[i].pk == parentPk){
                            $("#parentPk").append("<option value='" + data[i].pk + "' selected>"+ data[i].name + "</option>");
						}else{
                            $("#parentPk").append("<option value='" + data[i].pk + "'>"+ data[i].name + "</option>");
						}

                    }
                }
                $("#parentPk").selectpicker('refresh');//
            }
        });
	} else {
		$("#pk").val('');
		$("#name").val('');
		$("#sort").val('');
		$("input[name='isVisable'][value=1]").prop("checked", true);
		$("#parentPk").val('');
		$("#parentPk").selectpicker('refresh');
	}
}

/**
 * 删除
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
				url : './updateRawMaterialEx.do',
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
						$('#rawmaterialId').bootstrapTable('refresh');
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
		url : './updateRawMaterialEx.do',
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
				$('#rawmaterialId').bootstrapTable('refresh');
			}
		}
	});
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchRawMaterialTwo.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('rawmaterialId', cfg);
}