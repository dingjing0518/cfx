$(function() {
	var cfg = {
			url : './searchBrand.do',
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('brandId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 20,
			formatter : function(value, row, index) {
				var str = "";
				
				/*str += '&nbsp;&nbsp;&nbsp;<button id="editable-sample_new" style="display:none;" showname="BTN_EDITRAND" class="btn btn-primary" data-toggle="modal" onclick="javascript:edit(\''
				+ value
				+ '\',\''
				+ row.name
				+ '\',\''
				+ row.sort
				+ '\',\'' + row.isVisable + '\'); ">编辑</button>';*/
				str += '<button type="button" onclick="javascript:del(\'' + value + '\'); " style="display:none;" showname="OPER_GOODS_SETTINGS_BRAND_BTN_DEL" class="btn btn-danger active">删除</button>';
				if (row.isVisable == 1) {
					str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',2); " style="display:none;" showname="OPER_GOODS_SETTINGS_BRAND_BTN_DISABLE" class="btn btn-danger active" class="btn btn-danger active">禁用</button>';
				} else {
					str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',1); " style="display:none;" showname="OPER_GOODS_SETTINGS_BRAND_BTN_ENABLE" class="btn btn-primary">启用</button>';
				}
				
		return str;
	}
} ,
		{
			title : '名称',
			field : 'name',
			width : 150,
			sortable : true
		},
		{
			title : '代号',
			field : 'shortName',
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
		}
		
		];


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
	$.ajax({
		type : "POST",
		url : './updateBrand.do',
		data : {
			pk : $("#pk").val(),
			sort : $("#sort").val(),
			name:$("#name").val(),
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
				$('#brandId').bootstrapTable('refresh');
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
function edit(pk,name, sort, isVisable) {
	$('#editBrandId').modal();
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#sort").val(sort == 'undefined'?"":sort);
		$("#name").val(name);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked",
				true);
	} else {
		$("#pk").val('');
		$("#sort").val('');
		$("#name").val('');
		$("input[name='isVisable'][value=1]").prop("checked", true);
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
				url : './updateBrand.do',
				data : {
					'pk' : pk,
					isDelete : 2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao").click();
						$('#brandId').bootstrapTable('refresh');
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
		url : './updateBrand.do',
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
				$('#brandId').bootstrapTable('refresh');
			}
		}
	});
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchBrand.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('brandId', cfg);
}