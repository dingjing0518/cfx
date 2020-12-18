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
				if (row.isConfirm == 1) {
				str += '<button id="editable-sample_new" style="display:none;" showname="EM_REPORT_TRANS_PRICE_INDEX_BTN_SURE" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="javascript:edit(\''
						+ row.id + '\',\''+row.dueofpayMount+'\'); ">确认</button>';
                }
				return str;
			}
		}, {
			title : '借据编号',
			field : 'loanNumber',
			width : 20,
			sortable : true
		}, {
			title : '商品信息',
			field : 'goodsInfo',
			width : 20,
			sortable : true
		}, {
			title : '到期时间',
			field : 'repaymentTime',
			width : 20,
			sortable : true
		}, {
			title : '采购商名称',
			field : 'purchaserName',
			width : 20,
			sortable : true
		}, {
			title : '联系人',
			field : 'contacts',
			width : 20,
			sortable : true
		}, {
			title : '手机号码',
			field : 'contactsTel',
			width : 20,
			sortable : true
		}, {
			title : '借款状态',
			field : 'loanStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 3) {
					return "申请成功";
				} else if (value == 6) {
					return "部分还款";
				}
			}
		}, {
			title : '成交价格指数',
			field : 'priceIndex',
			width : 20,
			sortable : true
		}, {
			title : '当前价格指数',
			field : 'nowPriceIndex',
			width : 20,
			sortable : true
		}, {
			title : '涨跌幅(%)',
			field : 'riseAndFall',
			width : 20,
			sortable : true
		}, {
			title : '状态',
			field : 'isConfirm',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "未确认";
				} else if (value == 2) {
					return "已确认";
				}
			}
		}, {
			title : '保证金金额',
			field : 'dueofpayMount',
			width : 30,
			sortable : true,
        	formatter : function(value, row, index) {
                return "需补交保证金"+value+"元";
        }
		}];
var cfg = {
	url : './searchTransactionProductPriceList.do',
	columns : columns,
	uniqueId : 'loanNumber',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

function findStatus(s) {
	if (s != 0) {
		$("#isConfirm").val(s);
	} else {
		$("#isConfirm").val('');
	}
	var cfg = {
		url : './searchTransactionProductPriceList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'loanNumber',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('priceIndexId', cfg);
}

/**
 * @param type
 */
function searchTabs(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchTransactionProductPriceList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'loanNumber',
		sortName : 'insertTime',
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

   var dueofpayMountHidden = $("#dueofpayMountHidden").val();
    var dueofpayMount = $("#dueofpayMount").val();
    if (dueofpayMount != dueofpayMountHidden){
        new $.flavr({
            modal : false,
            content : "填写的金额和需补交保证金不一致！"
        });
        return;
	}

	if (valid("#dataForm")) {
		$.ajax({
			type : "POST",
			url : './updateTransactionProductPrice.do',
			data : {
				id : $("#id").val(),
                dueofpayMount : $("#dueofpayMount").val(),
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
function edit(id,dueofpayMount) {
	// 清空
	clean();
    $("#id").val(id),
    $("#dueofpayMountHidden").val(dueofpayMount);
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
     * 导出价格指数
     */
    function exportPriceProduct() {
        $.ajax({
            type : 'POST',
            url : './exportProductPriceIndex.do'+ urlParams(1),
            data : {},
            dataType : 'json',
            success : function(data) {
                if (data.success){
                    new $.flavr({
                        modal : false,
                        content :data.msg
                    });
                }
            }
        });
    }
    function backToBasePrice() {
		window.location.href="./priceIndex.do";
    }