$(function() {
	createDataGrid('priceIndexId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				var str = "";
				// str += '<button type="button" onclick="javascript:del(\''
				// 		+ value
				// 		+ '\'); " style="display:none;" showname="EM_PRO_BTN_DEL" class="btn btn-danger active">删除</button>';
				str += '<button id="editable-sample_new" style="display:none;" showname="EM_REPORT_PRICE_INDEX_EDIT" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="javascript:edit(\''
						+ value + '\',\''+row.productPk+'\',\''+row.priceIndex+'\'); ">编辑</button>';
				if (row.isVisable == 1) {
					str += '<button type="button" onclick="javascript:editStatus(\''
							+ value
							+ '\',2); " style="display:none;" showname="EM_REPORT_PRICE_INDEX_DISABLE" class="btn btn-warning active">禁用</button>';
				} else {
					str += '<button type="button" onclick="javascript:editStatus(\''
							+ value
							+ '\',1); " style="display:none;" showname="EM_REPORT_PRICE_INDEX_ENABLE" class="btn btn-warning active">启用</button>';
				}
				return str;
			}
		}, {
			title : '品名',
			field : 'productName',
			width : 20,
			sortable : true
		}, {
			title : '价格指数',
			field : 'priceIndex',
			width : 20,
			sortable : true
		}, {
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
		}, {
			title : '更新时间',
			field : 'updateTime',
			width : 30,
			sortable : true
		}];
var cfg = {
	url : './searchProductPriceList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

function findStatus(s) {
	if (s != 0) {
		$("#isVisable").val(s);
	} else {
		$("#isVisable").val('');
	}
	var cfg = {
		url : './searchProductPriceList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('priceIndexId', cfg);
}

/**
 * 金融中心-产品管理搜索和清除功能
 * @param type
 */
function searchTabs(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchProductPriceList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('priceIndexId', cfg);
}

/**
 * 保存编辑和新增金融产品
 */
function submit() {
	if (valid("#dataForm")) {
		$.ajax({
			type : "POST",
			url : './updateProductPrice.do',
			data : {
				pk : $("#pk").val(),
                productPk : $("#productPk").val(),
                productName:$("#productPk").find("option:selected").text(),
                priceIndex : $("#priceIndex").val()
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#priceIndexId').bootstrapTable('refresh');
				}
			}
		});
	}
}

/**
 * 编辑金融产品
 * @param pk
 */
function edit(pk,productPk,priceIndex) {
	// 清空
	clean();
    $("#pk").val(pk),
    $("#productPk").val(productPk);
    $("#productPk").attr("disabled",true);
    $("#priceIndex").val(priceIndex);
}

/**
 * 新增金融产品页面
 * @param pk
 */
function editGoods(pk) {
	$("#pk").val(pk);
	$("#myModalLabel").html("新增产品");
    $("#productPk").attr("disabled",false);
	clean();
}

/**
 * 新增时清除到所有新增页面输入框里的数据
 */
function clean() {
	$("#dataForm").find("input[type='text'],input[type='hidden']").each(function() {
				$(this).val("");
			});
	$("#productPk").val("");
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
				url : './updateEcGoods.do',
				data : {
					pk : pk,
					isDelete : 2
				},
				dataType : "json",
				success : function(data) {
					$("#quxiao").click();
					$('#priceIndexId').bootstrapTable('refresh');
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
function editStatus(pk,status) {
	$.ajax({
		type : "POST",
		url : './updateProductPrice.do',
		data : {
			pk : pk,
			isVisable : status
		},
		dataType : "json",
		success : function(data) {
			$("#quxiao").click();
			$('#priceIndexId').bootstrapTable('refresh');
		}
	});
}

function backToPriceIndex() {
	window.location.href="./transactionPriceIndex.do";
}