$(function() {
	createDataGrid('creditReportGoodsTable', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		str += '<button id="editable-sample_new" style="display:none;" showname="EM_ECONOMICS_CRECHEMG_GOODSMG_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:edit(\''
				+ value
				+ '\',\''
				+ row.name
				+ '\',\''
				+ row.isVisable
				+ '\'); ">编辑</button>';
		str += '<button type="button" onclick="javascript:del(\''
				+ value
				+ '\'); " style="display:none;" showname="EM_ECONOMICS_CRECHEMG_GOODSMG_BTN_DEL" class="btn btn-danger active">删除</button>';
		if (row.isVisable == 1) {
			str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',2); " style="display:none;" showname="EM_ECONOMICS_CRECHEMG_GOODSMG_BTN_DISABLE" class="btn btn-danger active">禁用</button>';
		} else {
			str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',1); " style="display:none;" showname="EM_ECONOMICS_CRECHEMG_GOODSMG_BTN_ENABLE" class="btn btn-primary">启用</button>';
		}
		return str;
	}
},
		{
			title : '产品名称',
			field : 'name',
			width : 150,
			sortable : true
		},
		{
			title : '简称',
			field : 'shotName',
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
		}];
var cfg = {
	url : './searchCreditReportGoodsList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'name',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 保存子品种
 */
function submit() {

if (valid("#dataForm")){

    $.ajax({
        type : "POST",
        url : './updateCreditReportGoods.do',
        data : {
            pk : $("#pk").val(),
            name : $("#name").val().trim()
        },
        dataType : "json",
        success : function(data) {

            if (data.success) {
                new $.flavr({
                    content : data.msg
                }); /* Closing the dialog */
                $("#quxiao").click();
                bootstrapTableRefresh("creditReportGoodsTable");
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
 * 编辑
 * @param pk
 * @param name
 * @param sort
 * @param parentPk
 * @param isVisable
 * @param parentName
 */
function edit(pk, name, isVisable) {
	$('#editCreditReportGoods').modal();
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#name").val(name);
	} else {
		$("#pk").val('');
		$("#name").val('');
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
				url : './updateStatusCreditReportGoods.do',
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
                        bootstrapTableRefresh("creditReportGoodsTable");
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
		url : './updateStatusCreditReportGoods.do',
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
                bootstrapTableRefresh("creditReportGoodsTable");
			}
		}
	});
}
function SearchClean(type) {
	if(type == 2){
		cleanpar();
	}
	var cfg = {
		url : './searchCreditReportGoodsList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'name',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('creditReportGoodsTable', cfg);
}
