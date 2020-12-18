$(function() {
	createDataGrid('tokenId', cfg);
    $('#addRaw').click(function () {
        $('#rawAddDiv')
            .append(
                '<div class="step">'
                + '<input type="text" style="border-radius: 4px;margin-left: 12px!important;height: 34px;width: 110px;" name="param"/>'
                + '<input type="text" style="border-radius: 4px;margin-left: 12px!important;height: 34px;width: 255px;" name="param"/>'
                + '<a class="inster" style="color: #0000FF;border: none;background: transparent;padding-left: 30px;margin-bottom: 15px;" onclick="dropRow($(this).parent())">删除</a>'
                + '</div>'
            );
    });
});
var toolbar = [];
var columns = [
	{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
			str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" style="display:none;" showname="MG_SYSPROPERTY_BTN_EDIT" onclick="javascript:editToken(\''
					+ value
					+ '\',\''+row.type+'\',\''+row.productPk+'\'); ">编辑</button>';
        str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" style="display:none;" showname="MG_SYSPROPERTY_BTN_DEL" onclick="javascript:del(\''
            + value
            + '\'); ">删除</button>';

		return str;
	}
	},
		{
			title : '产品名称',
			field : 'productName',
			width : 150,
			sortable : true
		},
		{
			title : '英文名称',
			field : 'productShotName',
			width : 150,
			sortable : true
		},
		{
			title : '内容',
			field : 'content',
			width : 150,
			sortable : true,
            formatter: function (value, row, index) {
                return '<span class="ellipsis"  title=' + value + '>' + value + '</span>'
            }
		},
		{
			title : '类型',
			field : 'type',
			width : 80,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "系统";
				} else if(value == 2){
					return "银行";
				}else if(value == 3){
                    return "线上支付";
                }else if(value == 4){
                    return "风控";
                }else if(value == 5){
                    return "票据";
                }else if(value == 6){
                    return "erp";
                }
			}

		}
		 ];
var cfg = {
	url : './searchSysProperty.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'asc',
    fitColumns:false,
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function searchSysProperty(type){

    if(type == 2){
        cleanpar();
    }
        var cfg = {
            url : './searchSysProperty.do' + urlParams(1),
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
    var type = $("#type").val();
    var pk = $("#pk").val();
    param['pk'] = pk;
    var productPk = $("#productPk").val();
    var productName = $("#productPk").find("option:selected").text();
    if (checkIsEmpty(productPk)) {
        param['productPk'] = productPk;
        param['productName'] = productName;
    }
    if (!checkIsEmpty(type)) {
        new $.flavr({
            modal : false,
            content : "请先选择类型！"
        });
        return;
    }

    if (checkIsEmpty(type) && type !=1 && type != 6 && !checkIsEmpty(productPk)){
        new $.flavr({
            modal : false,
            content : "请先选择产品！"
        });
        return;
    }
	param['type'] = type;

    var json = "{"
    var array=new Array()
    $('input[name="param"]').each(function(index){
        array[index] = $(this).val();
    });
    var isEmpty = false;
    for(var n = 0; n < (array.length)/2; n++){
        var key = array[(n*2)];
        var value = array[(n*2)+1];
        if (checkIsEmpty(key) && checkIsEmpty(value)){
            json += "\""+key+"\":"+"\""+value+"\",";
            isEmpty = true;
        }
    }

    if (!isEmpty) {
        new $.flavr({
            modal : false,
            content : "配置属性不能为空"
        });
        return;
    }
    json = json.substring(0, json.length - 1);
    json +="}";
    param['content'] = json;

	$.ajax({
		type : 'POST',
		url : './updateSysProperty.do',
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


function del(pk){
    var confirm = new $.flavr({
        content : '确定删除吗?',
        dialog : 'confirm',
        onConfirm : function() {
            confirm.close();
            $.ajax({
                type : 'POST',
                url : './delSysProperty.do',
                data : {pk:pk},
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
        },
        onCancel : function() {
            $("#quxiao").click();
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
function editToken(pk,type,productPk) {
    $('#myModal1').modal();
    $("#rawAddDiv").empty().append('<input type="text" style="border-radius: 4px;margin-left: 12px!important;height: 34px;width: 110px;" name="param" id="key"/>'
        +'<input type="text" style="border-radius: 4px;margin-left: 12px!important;height: 34px;width: 255px;" name="param" id="value"/>'
        +'</div>');
        isVisiable(false);//编辑时类型，产品不可用
    if (null != pk && '' != pk) {
        $("#pk").val(pk);
        isVisiable(true);
        $("#myModalLabel").html("编辑");
        $.ajax({
            type : 'POST',
            url : './getSysPropertyBypk.do',
            data : {pk:pk},
            dataType : 'json',
            success : function(data) {
                if (checkIsEmpty(data) && checkIsEmpty(data.content)){
                    var json = $.parseJSON(data.content);
                    var consNumber = 1;
                    for(var i in json){
                        if (consNumber == 1){
                            $("#key").val(i);
                            $("#value").val(json[i]);
                        } else{
                            $('#rawAddDiv')
                                .append(
                                    '<div class="step">'
                                    + '<input type="text" style="border-radius: 4px;margin-left: 12px!important;height: 34px;width: 110px;" name="param" value="'+i+'"/>'
                                    + '<input type="text" style="border-radius: 4px;margin-left: 12px!important;height: 34px;width: 255px;" name="param" value="'+json[i]+'"/>'
                                    + '<a class="inster" style="color: #0000FF;border: none;background: transparent;padding-left: 30px;margin-bottom: 15px;" onclick="dropRow($(this).parent())">删除</a>'
                                    + '</div>'
                                );
                        }
                        consNumber++
                    }
                }
            }
        });
        changeType(type,productPk,1);
    } else {
        $("#myModalLabel").html("新增");
        $("#pk").val("");
        $("#productPk").empty().append("<option value=\"\">--请选择--</option>");
        $('#productPk').selectpicker('refresh');
        var htmltype = "<option value=\"\">--请选择--</option>";
        for(var i = 0;i < config_Type.length;i++){
            htmltype +="<option value=\""+config_Type[i].key+"\">"+config_Type[i].value+"</option>";
        }
        $("#isShowByType").hide();
        $("#type").empty().append(htmltype);
        $('#type').selectpicker('refresh');
    }
}


function isVisiable(isAble) {
    $("#productPk").attr("disabled",isAble);
    $("#type").attr("disabled",isAble);

}

function changeType(type,productPk,flage) {
	if (flage == 2){
        type = $("#type").val();
	}
    var htmlStore ="<option value=\"\">--请选择--</option>";
    var htmltype ="<option value=\"\">--请选择--</option>";
    if (type == 1 || type == 6) {
        $("#isShowByType").hide();
        $("#productPk").empty().append(htmlStore);
    } else if(checkIsEmpty(type) && type != 1 && type != 6) {
        $("#isShowByType").show();
        $.ajax({
            type : 'POST',
            url : './getSysPropertyProduct.do',
            data : {type:type},
            dataType : 'json',
            success : function(data) {
                for(var i = 0;i < data.length;i++){
                	if (productPk == data[i].productPk) {
                        htmlStore +="<option value=\""+data[i].productPk+"\" selected>"+data[i].productName+"</option>";
					} else{
                        htmlStore +="<option value=\""+data[i].productPk+"\">"+data[i].productName+"</option>";
					}
                }
                $("#productPk").empty().append(htmlStore);
                $('#productPk').selectpicker('refresh');
            }
        });
    }
    for(var i = 0;i < config_Type.length;i++){
        if (type == config_Type[i].key) {
            htmltype +="<option value=\""+config_Type[i].key+"\" selected>"+config_Type[i].value+"</option>";
        } else{
            htmltype +="<option value=\""+config_Type[i].key+"\">"+config_Type[i].value+"</option>";
        }
    }
    $("#type").empty().append(htmltype);
    $('#type').selectpicker('refresh');
}

function dropRow(value){
    value.empty();
}