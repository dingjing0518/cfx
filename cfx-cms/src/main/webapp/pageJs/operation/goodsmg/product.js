$(function() {
	createDataGrid('productId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 200,
			formatter : function(value, row, index) {
				var str = "";
				str += '<button id="editable-sample_new" style="display:none;" showname="OPER_GOODS_SETTINGS_NAME_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:edit(\''
						+ value
						+ '\',\''
						+ row.name
						+ '\',\''
						+ row.sort
						+ '\',\''
						+ row.isVisable
						+ '\'); ">编辑</button>';
				str += '<button type="button" style="display:none;" showname="OPER_GOODS_SETTINGS_NAME_BTN_DEL" onclick="javascript:del(\''
						+ value
						+ '\'); " class="btn btn-danger active">删除</button>';
				if (row.isVisable == 1) {
					str += '<button type="button" style="display:none;" showname="OPER_GOODS_SETTINGS_NAME_BTN_DISABLE" onclick="javascript:editVisable(\''
							+ value
							+ '\',2); " class="btn btn-danger active">禁用</button>';
				} else {
					str += '<button type="button" style="display:none;" showname="OPER_GOODS_SETTINGS_NAME_BTN_ENABLE" onclick="javascript:editVisable(\''
							+ value
							+ '\',1); " class="btn btn-primary">启用</button>';
				}
				return str;
			}
		}, {
			title : '品名名称',
			field : 'name',
			width : 150,
			sortable : true
		}, {
			title : '排序',
			field : 'sort',
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

		}, {
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		} ];
var cfg = {
	url : './searchProduct.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

/**
 * 编辑
 * @param pk
 * @param name
 * @param sort
 * @param isVisable
 */
function edit(pk, name, sort, isVisable) {
	$('#editProductId').modal();
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#name").val(name);
		$("#sort").val(sort == 'undefined'?"":sort);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked",
				true);
	} else {
		$("#pk").val('');
		$("#name").val('');
		$("#sort").val('');
		$("input[name='isVisable'][value=1]").prop("checked", true);
	}
}

/**
 * 保存
 */
function submit() {
	if (valid("#dataForm")) {
		if(isNotZeroDNumber($("#sort").val()) == false){
			new $.flavr({
				modal : false,
				content : "输入的排序数字需大于零的整数"
			});
			return;
		}
		$.ajax({
			type : "POST",
			url : './updateProduct.do',
			data : {
				pk : $("#pk").val(),
				name : $("#name").val(),
				sort : $("#sort").val(),
				productType : $("#productType").val(),
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
					$('#productId').bootstrapTable('refresh');
				}
			}
		});
	}
	;

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
				url : './updateProduct.do',
				data : {
					'pk' : pk,
					'isDelete' : 2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao").click();
						$('#productId').bootstrapTable('refresh');
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
		url : './updateProduct.do',
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
				$('#productId').bootstrapTable('refresh');
			}
		}
	});
}
function SearchClean(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchProduct.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('productId', cfg);
}
