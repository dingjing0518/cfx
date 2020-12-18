$(function() {
	createDataGrid('sxSpecTableId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		if (row.isVisable == 2){
            str += '<button type="button" onclick="javascript:updateDisable(\''
                + value
                + '\',1); " style="display:none;" showname="YARN_GOODS_SPECMG_BTN_ENABLE" class="btn btn-danger active">启用</button>';
		} else{
            str += '<button type="button" onclick="javascript:updateDisable(\''
                + value
                + '\',2); " style="display:none;" showname="YARN_GOODS_SPECMG_BTN_DISABLE" class="btn btn-danger active">禁用</button>';
		}
        str += '<button type="button" onclick="javascript:edit(\''
            + value
            + '\',\''+row.sort+'\'); " style="display:none;" showname="YARN_GOODS_SPECMG_BTN_EDIT" class="btn btn-danger active">编辑</button>';
		return str;
	}
},
		{
			title : '名称',
			field : 'name',
			width : 150,
			sortable : true
		},
		{
			title : '添加时间',
			field : 'insertTime',
			width : 150,
			sortable : true
		},
		{
			title : '启用/禁用',
			field : 'isVisable',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else {
					return "禁用";
				}
			}
		} ,
		{
			title : '排序',
			field : 'sort',
			width : 150,
			sortable : true
		} ];
var cfg = {
	url : './searchSxSpec.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 保存子品种
 */
function submit() {

		if(isNotZeroDNumber($("#sort").val()) == false){
			new $.flavr({
				modal : false,
				content : "输入的排序数字需大于零的整数"
			});
			return;
		}
if (validNotHidden("#dataForm")){
    $.ajax({
        type : "POST",
        url : './updateSxSpec.do',
        data : {
            pk : $("#pk").val(),
            name : $("#name").val(),
            sort : $("#sort").val()
        },
        dataType : "json",
        success : function(data) {

            if (data.success) {
                new $.flavr({

                    content : data.msg
                }); /* Closing the dialog */
                $("#quxiao").click();
                $('#sxSpecTableId').bootstrapTable('refresh');
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
function edit(pk, sort) {
	$('#editSpecId').modal();
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#sort").val(sort == 'undefined'?"":sort);
        $("#nameHiddenId").hide();
        $("#name").val('');
	} else {
        $("#nameHiddenId").show();
		$("#pk").val('');
		$("#name").val('');
		$("#sort").val('');
	}
}

/**
 * 删除子品种
 * @param pk
 */
function updateDisable(pk,isVisable) {
			$.ajax({
				type : 'POST',
				url : './updateSxSpecStatus.do',
				data : {
					'pk' : pk,
                    isVisable:isVisable
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao").click();
						$('#sxSpecTableId').bootstrapTable('refresh');
					}
				}
			});

}

function SearchClean(type) {
	if(type == 2){
		cleanpar();
	}
	var cfg = {
		url : './searchSxSpec.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('sxSpecTableId', cfg);
}
