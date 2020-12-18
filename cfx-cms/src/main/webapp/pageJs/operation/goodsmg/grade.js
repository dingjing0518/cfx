$(function() {
	createDataGrid('gradeId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 200,
			formatter : function(value, row, index) {
				var str = "";
				str += '<button id="editable-sample_new" style="display:none;" showname="OPER_GOODS_SETTINGS_GRADE_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:editGrade(\''
						+ value
						+ '\',\''
						+ row.name
						+ '\',\''
						+ row.chineseName
						+ '\',\''
						+ row.sort
						+ '\',\''
						+ row.isVisable + '\'); ">编辑</button>';
				str += '<button type="button" onclick="javascript:del(\''
						+ value
						+ '\'); " style="display:none;" showname="OPER_GOODS_SETTINGS_GRADE_BTN_DEL" class="btn btn-danger active">删除</button>';
				if (row.isVisable == 1) {
					str += '<button type="button" onclick="javascript:editState(\''
							+ value
							+ '\',\''
							+ row.isDelete
							+ '\',2,\''
							+ row.name
							+ '\'); " style="display:none;" showname="OPER_GOODS_SETTINGS_GRADE_BTN_DISABLE" class="btn btn-danger active">禁用</button>';
				} else {
					str += '<button type="button" onclick="javascript:editState(\''
							+ value
							+ '\',\''
							+ row.isDelete
							+ '\',1,\''
							+ row.name
							+ '\'); " style="display:none;" showname="OPER_GOODS_SETTINGS_GRADE_BTN_ENABLE" class="btn btn-primary">启用</button>';
				}

				return str;
			}
		}, {
			title : '等级名称',
			field : 'name',
			width : 150,
			sortable : true
		},

		{
			title : '中文名称',
			field : 'chineseName',
			width : 150,
			sortable : true
		}, {
			title : '等级排序',
			field : 'sort',
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

		}, {
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		} ];
var cfg = {
	url : './searchGrade.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

/**
 * 保存等级
 */
function submit() {
	var name = $("#name").val();
	var NamePro = $("#namePrompt").html();
	if (NamePro.length > 0) {
		alert("该等级名称存在");
		return;
	}

	if (valid("#dataForm")) {
		if(isNotZeroDNumber($("#sort").val()) == false){
			new $.flavr({
				modal : false,
				content : "输入的排序数字需大于零的整数"
			});
			return;
		}

		$.ajax({
			type : 'POST',
			url : "./updateGrade.do",
			data : $('#dataForm').serialize(),
			dataType : 'json',
			async : true,
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#gradeId').bootstrapTable('refresh');
				}

			}
		});
	}
}

/**
 * 检查是否已存在相同等级
 * @param name
 */
function checkGradeName(name) {
	$("#namePrompt").html("");
	if (name.length > 0) {
		$.ajax({
			type : 'GET',
			url : './getGradeByName.do',
			data : {
				'name' : name
			},
			dataType : 'json',
			success : function(data) {

				if (data.length > 0) {
					$("#namePrompt").html("该等级名称已存在");
				}

			}
		});

	}
}

/**
 * 编辑等级
 * @param pk
 * @param name
 * @param chineseName
 * @param sort
 * @param isVisable
 */
function editGrade(pk, name, chineseName, sort, isVisable) {
	$('#myModal1').modal();
	if (null != pk && '' != pk) {
		if('undefined' != name && '' != name && null != name && undefined != name){
            $("#name").val(name);
		}else{
            $("#name").val("");
		}
		if ('undefined' != chineseName) {
			$("#chineseName").val(chineseName);
		} else {
			$("#chineseName").val('');
		}
		$("#sort").val(sort == 'undefined'?"":sort);
		$("#pk").val(pk);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked",
				true);
	} else {
		$("#name").val('');
		$("#chineseName").val('');
		$("#pk").val('');
		$("#sort").val('');
		$("input[name='isVisable'][value=1]").prop("checked", true);
	}
}

/**
 * 禁用、启用
 * @param pk
 * @param isde
 * @param isvi
 * @param name
 */
function editState(pk, isde, isvi, name) {

	var parm = {
		pk : pk,
		isDelete : isde,
		isVisable : isvi,
		name : name
	};

	$.ajax({
		type : 'POST',
		url : './updateGrade.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#quxiao").click();
				$('#gradeId').bootstrapTable('refresh');
			}

		}
	});

}

/**
 * 删除等级
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
				url : './updateGrade.do',
				data : {
					pk : pk,
					isDelete : 2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
					if (data.success) {
						$("#quxiao").click();
						$('#gradeId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}
function SearchClean(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchGrade.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('gradeId', cfg);
}
