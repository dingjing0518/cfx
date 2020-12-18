$(function() {

	var insertStartTime = $("input[name='insertStartTime']").val();
	var insertEndTime = $("input[name='insertEndTime']").val();

	var cfg = {
		url : './searchOrderList.do?insertStartTime=' + insertStartTime
				+ "&insertEndTime=" + insertEndTime,
		columns : columns,
		uniqueId : 'orderNUmber',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
        queryParams : function(params) {
        return {
            block:"sx",
            limit : params.limit,
            start : params.offset,
            orderType : params.order,
            orderName : params.sort
        };
    },
		onLoadSuccess : loadFancyBox
	};

	createDataGrid('orderId', cfg);

	$(".form_datetime").datetimepicker({
		format : "yyyy-mm-dd hh:ii",
		autoclose : true,
		todayBtn : true,
		language : 'zh-CN',
		pickerPosition : "bottom-right"
	});
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 30,
			formatter : function(value, row, index) {
				var str = "";
				if (row.orderStatus > 0) {
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button" style="display:none;" showname="YARN_ORDER_BTN_DOWNLOAND" class="btn btn-warning" onclick="javascript:downPDF(\''
							+ row.orderNumber
							+ '\');">下载合同</button></a>';
				}
                if (row.orderStatus != -1) {
                    str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
                        + '<button type="button" data-toggle="modal"  style="display:none;" showname="YARN_ORDER_BTN_CLOSE" class="btn btn-warning" onclick="javascript:closeOrder(\''
                        + row.orderNumber
                        + '\');">关闭订单</button></a>';
                }
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
						+ '<button type="button" data-toggle="modal" data-target="#orderGoods" style="display:none;" showname="YARN_ORDER_BTN_ORDERGOODS" class="btn btn-warning" onclick="javascript:orderGoods(\''
						+ row.orderNumber
						+ '\',  \''  + row.block+ '\');">订单商品</button></a>';
                if (row.orderStatus == -1) {
                    str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
                        + '<button type="button" data-toggle="modal" data-target="#creditCustomerTrack" style="display:none;" showname="YARN_ORDER_BTN_ORDERTRACK" class="btn btn-warning" onclick="javascript:contractTrack(\''
                        + row.orderNumber
                        + '\');">订单追踪</button></a>';
                }
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
						+ '<button type="button" data-toggle="modal" data-target="#payMentVoucherId" style="display:none;" showname="YARN_ORDER_BTN_VOUCHER" class="btn btn-warning" onclick="javascript:orderPaymentVoucher(\''
						+ row.orderNumber
						+ '\');">上传付款凭证</button></a>';
				return str;
			}
		},
		{
			title : '订单号',
			field : 'orderNumber',
			width : 20,
			sortable : true
		},
		{
			title : '订单状态',
			field : 'orderStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 0) {
					return "待审核";
				} else if (value == 1) {
					return "待付款";
				} else if (value == 2) {
					return "待确认";
				} else if (value == 3) {
					return "待发货";
				} else if (value == 4) {
					return "已发货";
				} else if (value == 5) {
					return "部分发货";
				} else if (value == 6) {
					return "已完成";
				} else if (value == -1) {
					return "订单取消";
				}

			}
		},
		{
			title : '订单来源',
			field : 'source',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "pc端";
				} else if (value == 2) {
					return "移动端";
				} else if (value == 3) {
					return "app";
				} else if (value == 4) {
					return "后台录入";
				}

			}
		},

		{
			title : '采购商名称',
			field : 'purchaserName',
			width : 20,
			sortable : true
		}
			 , { title : '发票抬头', field : 'invoiceName', width : 20, sortable :
			 true }
			 ,
		{
			title : '店铺',
			field : 'storeName',
			width : 20,
			sortable : true
		},
		{
			title : '订单金额',
			field : 'actualAmount',
			width : 20,
			sortable : true
		},
		{
			title : '下单时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},
		{
			title : '支付方式',
			field : 'paymentName',
			width : 20,
			sortable : true
		},
		   {
	        title : '金融产品',
	        field : 'economicsGoodsName',
	        width : 20,
	        sortable : true
	    },
		{
			title : '支付时间',
			field : 'paymentTime',
			width : 20,
			sortable : true
		},
		{
			title : '收货地址',
			field : 'detailAddress',
			width : 20,
			sortable : true
		},
		{
			title : '联系人',
			field : 'contacts',
			width : 20,
			sortable : true
		},
		{
			title : '备注',
			field : 'meno',
			width : 20,
			sortable : true
		},
		{
			title : '付款凭证',
			field : 'voucherList',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var str = '';
				if (value != undefined && value.length > 0) {
					for ( var i = 0; i < value.length; i++) {
						str += "<a rel='group" + index
								+ "' title='付款凭证' href='" + value[i].url
								+ "'><img class='bigpicture' src='"
								+ value[i].url
								+ "' onerror='errorImg(this)'></a>";
					}
				}
				return str;

			}
		} ];

/**
 * 导出
 */
function getOrderGoods() {
    $.ajax({
        type : 'POST',
        url : './exportOrderGoods.do'+ urlParams(1),
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

/**
 * 订单导出
 */
function getOrder() {
    $.ajax({
        type : 'POST',
        url : './exportOrder.do'+ urlParams(1),
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

/**
 * 下载合同
 * @param orderNumber
 */
function downPDF(orderNumber) {
	var confirm = new $.flavr({
		content : '确定下载合同吗?',
		dialog : 'confirm',
		onConfirm : function() {
            window.location.href = getRootPath() + "/uploadPDF.do?orderNumber="
                + orderNumber+"&block=sx";
			confirm.close();
		}
	});
}

/**
 * 订单状态去换table页
 * @param s
 */
function byOrderStatus(s) {
	$("#orderStatus").val(s);
	var cfg = {
		url : './searchOrderList.do' + urlParams(1),
		columns : columns,
        uniqueId : 'orderNUmber',
        sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : loadFancyBox
	};
	createDataGrid('orderId', cfg);
}

/**
 * 搜索和清除
 * @param type
 */
function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchOrderList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'orderNumber',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : loadFancyBox
	};
	createDataGrid('orderId', cfg);
}

var columnsNew = [
		{
			title : '商品信息',
			field : 'sxGoods',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var brandName = typeof (row.brandName) == "undefined" ? " "
						: row.brandName;
				var materialName = typeof (value.materialName) == "undefined" ? " "
						: value.materialName;
				var rawMaterialName = typeof (value.rawMaterialName) == "undefined" ? " "
						: value.rawMaterialName;
				var technologyName = typeof (value.technologyName) == "undefined" ? " "
						: value.technologyName;
				var rawMaterialParentName = typeof (value.rawMaterialParentName) == "undefined" ? " "
						: value.rawMaterialParentName;
				return brandName + " " + technologyName + " " + rawMaterialName +" " + materialName +" " + rawMaterialParentName +"<br>"
			}
		}, {
			title : '箱数',
			field : 'afterBoxes',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (null == value || "" == value || "0" == value) {
					return row.boxes;
				} else {
					return value;
				}
			}
		}, {
			title : '总重量',
			field : 'afterWeight',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (null == value || "" == value || "0.0" == value) {
					return row.weight;
				} else {
					return value;
				}
			}
		}, {
			title : '商品单价',
			field : 'originalPrice',
			width : 20,
			sortable : true
		}, {
			title : '商品成交单价',
			field : 'presentPrice',
			width : 20,
			sortable : true
		}, {
			title : '商品总价',
			field : 'originalTotalPrice',
			width : 20,
			sortable : true
		}, {
			title : '商品成交总价',
			field : 'presentTotalPrice',
			width : 20,
			sortable : true
		}, {
			title : '运费单价',
			field : 'originalFreight',
			width : 20,
			sortable : true
		}, {
			title : '运费成交单价',
			field : 'presentFreight',
			width : 20,
			sortable : true
		}, {
			title : '运费总价',
			field : 'originalTotalFreight',
			width : 20,
			sortable : true
		}, {
			title : '运费成交总价',
			field : 'presentTotalFreight',
			width : 20,
			sortable : true
		}
		, {
			title : '订单状态',
			field : 'orderStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				
				if (value == 0) {
					return "待审核";
				} else if (value == 1) {
					return "待付款";
				} else if (value == 2) {
					return "待确认";
				} else if (value == 3) {
					return "待发货";
				} else if (value == 4) {
					return "已发货";
				} else if (value == 5) {
					return "部分发货";
				} else if (value == 6) {
					return "已完成";
				} else if (value == -1) {
					return "订单取消";
				}
			}
		} ];

/**
 * 订单商品子页面列表
 * @param orderNumber
 */
function orderGoods(orderNumber,block) {
	var cfg = {
		url : './searchOrderGoodsList.do',
		columns : columnsNew,
		uniqueId : 'pk',
		sortName : 'pk',
		sortOrder : 'desc',
		queryParams : function(params) {
			return {
				orderNumber : orderNumber,
				block : block
			}
		},
		toolbar : toolbar,
		onLoadSuccess : loadFancyBox
	};
	createDataGrid('goods', cfg);
}
/**
 * 上传付款凭证
 * @param orderNumber
 */
function orderPaymentVoucher(orderNumber) {
	$('#payMentVoucherId').modal();


	$("#payMentVoucherId img").each(function() {
		$(this)[0].src = "./style/images/bgbg.png";
	});

	$("input[name='file']").val("");
	$('#payUrl').val(null);
	$('#orderNumberId').val(orderNumber);
}

/**
 * 关闭订单
 * @param orderNumber
 */
function closeOrder(orderNumber) {
    $("#orderNumber").val(orderNumber);
    $("#orderCloseReason").modal();
    $("#closeReason").val("");
}

function submitClose() {

    var closeReason = $("#closeReason").val().trim();
    if (!checkIsEmpty(closeReason)){
        new $.flavr({
            modal : false,
            content : "请输入关闭原因！"
        });
        return;
    }
    $.ajax({
			type : 'POST',
			url : './closeOrder.do',
			data : {
				closeReason : closeReason,
				orderNumber:$("#orderNumber").val()
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao1").click();
						$('#orderId').bootstrapTable('refresh');
					}
				}
			});
}

/**
 * 提交付款凭证
 */
function submit() {

	var params = {};
	$("#dataForm img").each(function() {
		if ($(this).attr("id") != undefined) {
			params[$(this).attr("id")] = $(this).attr("src");
		}
		if ($(this).is(":hidden")) {
			params[$(this).attr("id")] = "";
		}
	});
	params['orderNumber'] = $('#orderNumberId').val();

	if (valid("#dataForm")) {
		$.ajax({
			type : "POST",
			url : './uploadOrderPaymentVoucher.do',
			dataType : "json",
			data : params,
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$("#quxiao").click();
					$('#orderId').bootstrapTable('refresh');
				}
			}
		});

	}
}

/**
 * 刷新列表
 */
function loadFancyBox() {
	for ( var i = 0; i < 10; i++) {
		if ($("#orderId a[rel=group" + i + "]").attr("title") != undefined) {
			// 图片弹出框
			$("#orderId a[rel=group" + i + "]").fancybox(
					{
						'titlePosition' : 'over',
						'cyclic' : true,
						'titleFormat' : function(title, currentArray,
								currentIndex, currentOpts) {
							return '<span id="fancybox-title-over">'
									+ (currentIndex + 1) + ' / '
									+ currentArray.length
									+ (title.length ? ' &nbsp; ' + title : '')
									+ '</span>';
						}
					});
		}
	}
	btnList();
}


/**
 * 订单追踪
 * @param pk
 */
function contractTrack(pk){
    $("#creditCustomerTrack").modal();
    $("#contractTrack").html("");
    $.ajax({
        type : 'POST',
        url : './getOrderRecordTrack.do?contractNo='+pk,
        data : {},
        dataType : 'json',
        success : function(data) {
            if (checkIsEmpty(data)){
                var html = "";
                for(var i = 0; i< data.length; i++){
                    html += data[i].insertTime+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ data[i].content+"；<br/>"
                }
                $("#contractTrack").html(html);
            }
        }
    });
}