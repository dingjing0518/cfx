$(function() {
	createDataGrid('onlinePayGoodsTable', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		str += '<button id="editable-sample_new" class="btn btn-primary" style="display:none;" showname="EM_ECONOMICS_ONLINEPAYGOODS_BTN_EDIT" data-toggle="modal" onclick="javascript:edit(\''
				+ value
				+ '\',\''
				+ row.name
				+ '\',\''+ row.isVisable+ '\',\''+ row.bankPk+ '\',\''+ row.bankName+ '\'); ">编辑</button>';
		str += '<button type="button" onclick="javascript:del(\''
				+ value
				+ '\'); " style="display:none;" showname="EM_ECONOMICS_ONLINEPAYGOODS_BTN_DEL" class="btn btn-danger active">删除</button>';
		if (row.isVisable == 1) {
			str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',2); " style="display:none;" showname="EM_ECONOMICS_ONLINEPAYGOODS_BTN_DISABLE" class="btn btn-danger active">禁用</button>';
		} else {
			str += '<button type="button" onclick="javascript:editVisable(\''
					+ value
					+ '\',1); " style="display:none;" showname="EM_ECONOMICS_ONLINEPAYGOODS_BTN_ENABLE" class="btn btn-primary">启用</button>';
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
			title : '银行名称',
			field : 'bankName',
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
	url : './searchOnlinePayGoodsList.do',
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
    var bankName = "";
    var bankPk = $("#bankName").val();
    if(checkIsEmpty(bankPk)){
        bankName = $("#bankName option:selected").text();
	}
    $.ajax({
        type : "POST",
        url : './updateOnlinePayGoods.do',
        data : {
            pk : $("#pk").val(),
            name : $("#name").val().trim(),
            bankPk : bankPk,
            bankName : bankName
        },
        dataType : "json",
        success : function(data) {

            if (data.success) {
                new $.flavr({
                    content : data.msg
                }); /* Closing the dialog */
                $("#quxiao").click();
                bootstrapTableRefresh("onlinePayGoodsTable");
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
function edit(pk, name, isVisable,bankPk,bankName) {
	$('#editOnlinePayGoods').modal();
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#name").val(name);
        $("#bankPk").val(bankPk);
        $("button[data-id=bankName] span.filter-option").html(bankName);
        $("button[data-id=bankName]").attr("title",bankName);
	} else {
		$("#pk").val('');
		$("#name").val('');
        $("#bankName").val('');
        $("button[data-id=bankName] span.filter-option").html("--请选择--");
        $("button[data-id=bankName]").attr("title","--请选择--");
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
				url : './updateStatusOnlinePayGoods.do',
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
                        bootstrapTableRefresh("onlinePayGoodsTable");
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
		url : './updateStatusOnlinePayGoods.do',
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
                bootstrapTableRefresh("onlinePayGoodsTable");
			}
		}
	});
}
function SearchClean(type) {
	if(type == 2){
		cleanpar();
	}
	var cfg = {
		url : './searchOnlinePayGoodsList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'name',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('onlinePayGoodsTable', cfg);
}
