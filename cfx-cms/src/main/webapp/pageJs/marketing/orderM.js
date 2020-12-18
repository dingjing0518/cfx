$(function() {

	var insertStartTime = $("input[name='insertStartTime']").val();
	var insertEndTime = $("input[name='insertEndTime']").val();
	var cfg = {
		url : './orderM_data.do?insertStartTime=' + insertStartTime
				+ "&insertEndTime=" + insertEndTime,
		columns : columns,
		uniqueId : 'orderNumber',
		sortName : 'orderNumber',
		sortOrder : 'desc',
		toolbar : toolbar,
        queryParams : function(params) {
            return {
                block:"cf",
                limit : params.limit,
                start : params.offset,
                orderType : params.order,
                orderName : params.sort
            };
        },
		onLoadSuccess : loadFancyBox
	};
	createDataGrid('orderMId', cfg);

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
							+ '<button type="button" style="display:none;" showname="MARKET_ORDER_BTN_ORDERCONTACT_EXPORT" class="btn btn-warning" onclick="javascript:downPDF(\''
							+ row.orderNumber
							+ '\');">下载合同</button></a>';
				}
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
						+ '<button type="button" data-toggle="modal" data-target="#orderGoods" style="display:none;" showname="MARKET_ORDER_BTN_GOODS" class="btn btn-warning" onclick="javascript:orderGoods(\''
						+ row.orderNumber
						+ '\',  \''  + row.block+ '\');">订单商品</button></a>';
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
						+ '<button type="button" data-toggle="modal" data-target="#payMentVoucherId" style="display:none;" showname="MARKET_ORDER_BTN_PAY_IMPORT" class="btn btn-warning" onclick="javascript:orderPaymentVoucher(\''
						+ row.orderNumber
						+ '\');">上传付款凭证</button></a>';
				
				if(row.block=="cf"&&row.economicsGoodsType==2&&(row.orderStatus==3||row.orderStatus==5)){
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
						+ '<button type="button"  style="display:none;" showname="MARKET_ORDER_BTN_BILLOFLOADING" class="btn btn-warning" onclick="javascript:openBillOfLoading(\''
						+ row.orderNumber
						+ '\');">订单提货</button></a>';
				}
				
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
			field : 'orderStatusName',
			width : 20,
			sortable : true
			
		},
		{
			title : '采购商名称',
			field : 'purchaserName',
			width : 20,
			sortable : true
		},
		{
			title : '发票抬头',
			field : 'invoiceName',
			width : 20,
			sortable : true
		},
		{
			title : '店铺',
			field : 'storeName',
			width : 20,
			sortable : true
		},
		{
			title : '店铺业务员',
			field : 'supPerson',
			width : 20,
			sortable : true
		},
		{
			title : '采购商业务员',
			field : 'purPerson',
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
			title : '订单类型',
			field : 'purchaseType',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "自采";
				} else if (value == 2) {
					return "代采";
				} else if (value == 3) {
					return "后台代采";
				}
			}
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
			title : '支付时间',
			field : 'paymentTime',
			width : 20,
			sortable : true
		},
		{
			title : '收款时间',
			field : 'receivablesTime',
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

var columnsNew = [
		{
			title : '商品名称',
			field : 'cfGoods',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var seriesName = typeof (value.seriesName) == "undefined" ? " "
						: value.seriesName;
				var productName = typeof (value.productName) == "undefined" ? " "
						: value.productName;
				var specName = typeof (value.specName) == "undefined" ? " "
						: value.specName;
				var varietiesName = typeof (value.varietiesName) == "undefined" ? " "
						: value.varietiesName;
				var seedvarietyName = typeof (value.seedvarietyName) == "undefined" ? " "
						: value.seedvarietyName;
				var gradeName = typeof (value.gradeName) == "undefined" ? " "
						: value.gradeName;
				return productName + " " + specName + " " + seriesName + " "
						+ varietiesName + " " + seedvarietyName + " "
						+ gradeName;
			}
		}, {
			title : '批号',
			field : 'cfGoods.batchNumber',
			width : 20,
			sortable : true
		},  {
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
			title : '是否含印花税',
			field : 'stampDutyName',
			width : 20,
			sortable : true
		},{
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
		}, {
			title : '所属店铺',
			field : 'storeName',
			width : 20,
			sortable : true
		}, {
			title : '发货状态',
			field : 'orderStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == -1) {
					return "已取消";
				} else if (value > 3) {
					return "已发货";
				} else {
					return "未发货";
				}
			}
		} ];

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './orderM_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'orderNumber',
		sortName : 'orderNumber',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : loadFancyBox
	};
	createDataGrid('orderMId', cfg);
}

/**
 * 订单状态Table页面切换
 * @param s
 */
function byOrderStatus(s) {
	$("#orderStatus").val(s);
	var cfg = {
		url : './orderM_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'orderNumber',
		sortName : 'orderNumber',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : loadFancyBox
	};
	createDataGrid('orderMId', cfg);
}

/**
 * 订单导出
 */
function excel() {

    $.ajax({
        type : 'POST',
        url : './exportOrderM.do'+ urlParams(1),
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
		// var confirm = new $.flavr(
		// 		{
		// 			content : '确定导出订单吗?',
		// 			dialog : 'confirm',
		// 			onConfirm : function() {
		// 				confirm.close();
		// 				var exportData = $("#loadingExportData").html(
		// 						" 正在导出...");
		// 				exportData
		// 						.prepend("<img src='style/images/exportorder.gif' height='20' width='20' />");
		// 				$.ajax({
		// 					type : "POST",
		// 					url : './getExportOrderM.do' + urlParams(1),
		// 					dataType : "text",
		// 					data : {},
		// 					timeout : 1000 * 60 * 5,
		// 					success : function(resp) {
		// 						if (resp == "success") {
		// 							window.location.href = './exportOrderM.do'
		// 									+ urlParams(1);
		// 							$("#loadingExportData").html("导出");
		// 						} else {
		// 							$("#loadingExportData").html("导出");
		// 							new $.flavr({
		// 								modal : false,
		// 								content : "导出订单不能超过5000条!"
		// 							});
		// 						}
		// 					},
		// 					error : function(msg) {
		// 						$("#loadingExportData").html("导出");
		// 						new $.flavr({
		// 							modal : false,
		// 							content : "网络请求超时，请重试！"
		// 						});
		// 					}
		// 				});
		// 			}
		// 		});

	}

/**
 * 刷新列表
 */
function loadFancyBox() {
	for ( var i = 0; i < 10; i++) {
		if ($("#orderMId a[rel=group" + i + "]").attr("title") != undefined) {
			// 图片弹出框
			$("#orderMId a[rel=group" + i + "]").fancybox(
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
 * 下载订单合同
 * @param orderNumber
 */
function downPDF(orderNumber) {
	var confirm = new $.flavr({
		content : '确定下载合同吗?',
		dialog : 'confirm',
		onConfirm : function() {
			window.location.href = getRootPath() + "/uploadPDF.do?orderNumber="
					+ orderNumber+"&block=cf";
			confirm.close();
		}
	});
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
 * 保存付款凭证
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
					$('#orderMId').bootstrapTable('refresh');
				}
			}
		});

	}
}

/**
 * 查询订单商子列表
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
		onLoadSuccess : loadFancyBox,
		
	};
	createDataGrid('goods', cfg);
}


function openBillOfLoading(orderNumber){
	window.location.href = getRootPath() + "/openBillOfLoading.do?orderNumber="
	+ orderNumber +"&flag=2";
}