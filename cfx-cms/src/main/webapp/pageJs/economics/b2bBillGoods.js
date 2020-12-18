$(function() {
	createDataGrid('billGoodsId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				var str = "";
				str += '<button type="button" onclick="javascript:del(\''
						+ value
						+ '\'); " style="display:none;" showname="EM_BILLGOODS_MG_BTN_DEL" class="btn btn-danger active">删除</button>';
				str += '<button id="editable-sample_new" style="display:none;" showname="EM_BILLGOODS_MG_BTN_EDIT" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="javascript:edit(\''
						+ value + '\',\''+row.name+'\',\''+row.bankPk+'\',\''+row.bankName+'\',\''+row.type+'\',\''+row.gateway+'\',\''+row.imgUrl+'\'); ">编辑</button>';
				if (row.isVisable == 1) {
					str += '<button type="button" onclick="javascript:editStatus(\''
							+ value
							+ '\',2); " style="display:none;" showname="EM_BILLGOODS_MG_DISABLE" class="btn btn-warning active">禁用</button>';
				} else {
					str += '<button type="button" onclick="javascript:editStatus(\''
							+ value
							+ '\',1); " style="display:none;" showname="EM_BILLGOODS_MG_ENABLE" class="btn btn-warning active">启用</button>';
				}
				return str;
			}
		}, {
			title : '产品名称',
			field : 'name',
			width : 20,
			sortable : true
		}
		// , {
		// 	title : '英文简称',
		// 	field : 'shotName',
		// 	width : 20,
		// 	sortable : true
		// }
		, {
			title : '银行名称',
			field : 'bankName',
			width : 20,
			sortable : true
		}, {
			title : '平台总额度',
			field : 'platformAmount',
			width : 20,
			sortable : true
		}
		// , {
		// 	title : '平台已使用额度',
		// 	field : 'platformUseAmount',
		// 	width : 20,
		// 	sortable : true
		// }
		, {
			title : '类型',
			field : 'type',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
                if (value == 0) {
                    return "无限制";
                } else if (value == 1) {
					return "商承票";
				} else if (value == 2) {
					return "银承票";
				}
			}
		}, {
			title : '网关地址',
			field : 'gateway',
			width : 20,
			sortable : true
		},{
			title : '预览图',
			field : 'imgUrl',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				 var url="";
				 if(value != "" && value != null){
                     url="<a class='fancybox' href='"+value+"'><img style='width:auto;height:30px;' src='"+value+"'/></a>";
				 }
				 return url;
			}
		}  ,
		{
			title : '启用/禁用',
			field : 'isVisable',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else if (value == 2) {
					return "禁用";
				}
			}
		}];
var cfg = {
	url : './searchBillGoodsList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'shotName',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
};

/**
 * 金融中心-产品管理搜索和清除功能
 * @param type
 */
function searchTabs(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchBillGoodsList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'shotName',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('billGoodsId', cfg);
}

function loadFancyBox(){
	$(".fancybox").fancybox({
	    'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic',
		'titlePosition' : 'inside'
	});
	btnList();
}
/**
 * 保存编辑和新增金融产品
 */
function submit() {
	if (valid("#dataForm")) {
		var bankName = "";
        var bankPk = $("#bankPk").val();
        if(checkIsEmpty(bankPk)){
            bankName = $("#bankPk option:selected").text();
        }
        var pk = $("#pk").val();
        var url = "";
        if (checkIsEmpty(pk)){//不为空
            url = './updateBillGoods.do';
		}else{
            url = './insertBillGoods.do';
		}
		$.ajax({
			type : "POST",
			url : url,
			data : {
                pk : pk,
				name : $("#name").val(),
                bankPk : bankPk,
                bankName : bankName,
                gateway : $("#gateway").val(),
				type:$("#type").val(),
				imgUrl:$('#tupian').val(),
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#billGoodsId').bootstrapTable('refresh');
				}
			}
		});
	}
}

/**
 * 新增金融产品页面
 * @param pk
 */
function editGoods() {
    clean();
	$("#myModalLabel").html("新增产品");
    $("#type").empty().append("<option value=''>--请选择--</option>");
    for (var m = 0; m < bill_Type.length; m++) {
        $("#type").append("<option value=" + bill_Type[m].key + ">" + bill_Type[m].value + "</option>");
    }
    $("#type").selectpicker('refresh');//
    
    var imgId=document.getElementById("PURL");
    imgId.style.display="none";
    $("#tupian").val('');
	
}

function edit(pk,name,bankPk,bankName,type,gateway,imgUrl){
	$(".kv-file-remove").click();
    $("#myModalLabel").html("编辑产品");
    $("#name").val(name);
    $("#pk").val(pk);
    $("#bankPk").val(bankPk);
    $("#gateway").val(gateway);
    $("#bankPk option:selected").text(bankName)
    $("#type").empty().append("<option value=''>--请选择--</option>");
    for (var m = 0; m < bill_Type.length; m++) {
        if (bill_Type[m].key == type) {
            $("#type").append("<option value=" + bill_Type[m].key + " selected>" + bill_Type[m].value + "</option>");
        } else {
            $("#type").append("<option value=" + bill_Type[m].key + ">" + bill_Type[m].value + "</option>");
        }
    }
    $("#type").selectpicker('refresh');//
    
    var imgId=document.getElementById("PURL");
	if(imgUrl.length==0){
		imgId.style.display="none";
	}else{
		imgId.style.display="";
	}
    $("#PURL").attr("src",imgUrl);
    $('#tupian').val(imgUrl);
}


/**
 * 新增时清除到所有新增页面输入框里的数据
 */
function clean() {
	$("#dataForm").find("input[type='text'],input[type='hidden'],select").each(function() {
				$(this).val("");
			});
	$(".kv-file-remove").click();
}

/**
 * 删除金融产品
 * @param pk
 */
function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './updateBillGoods.do',
				data : {
					pk : pk,
					isDelete : 2,
                    isUpdateStatus:1,
				},
				dataType : "json",
				success : function(data) {
					$("#quxiao").click();
					$('#billGoodsId').bootstrapTable('refresh');
				}
			});
		}
	});
}

/**
 * 启用、禁用
 * @param pk
 * @param status
 */
function editStatus(pk,isVisable) {
	$.ajax({
		type : "POST",
		url : './updateBillGoods.do',
		data : {
			pk : pk,
            isVisable : isVisable,
            isUpdateStatus:1
		},
		dataType : "json",
		success : function(data) {
			$("#quxiao").click();
			$('#billGoodsId').bootstrapTable('refresh');
		}
	});
}