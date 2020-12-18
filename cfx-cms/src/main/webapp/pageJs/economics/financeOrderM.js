$(function() {
	
	var insertStartTime = $("input[name='insertStartTime']").val();
	var insertEndTime = $("input[name='insertEndTime']").val();
	
	var cfg = {
			url : './order_data.do?insertStartTime='+insertStartTime+"&insertEndTime="+insertEndTime,
			columns : columns,
			uniqueId : 'orderNUmber',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	
	
	createDataGrid('orderId', cfg);
	
	$(".form_datetime").datetimepicker({
	    format: "yyyy-mm-dd hh:ii",
	    autoclose: true,
	    todayBtn: true,
	    language:'zh-CN',
	    pickerPosition:"bottom-right"
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
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
					+'<button type="button" style="display:none;" showname="EM_CREDIT_ORDER_ORDERMG_BTN_DOWNLOAD" class="btn btn-warning" onclick="javascript:downPDF(\''
						+ row.orderNumber+ '\');">下载合同</button></a>';
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
					+'<button type="button" data-toggle="modal" data-target="#orderGoods" style="display:none;" showname="EM_CREDIT_ORDER_ORDERMG_BTN_ORDERGOODS" class="btn btn-warning" onclick="javascript:orderGoods(\''
					+ row.orderNumber+ '\');">订单商品</button></a>';
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
					+'<button type="button" data-toggle="modal" data-target="#payMentVoucherId" style="display:none;" showname="EM_CREDIT_ORDER_ORDERMG_BTN_UPLOAD" class="btn btn-warning" onclick="javascript:orderPaymentVoucher(\''
					+ row.orderNumber+ '\');">上传付款凭证</button></a>';
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
		}else if (value == 1) {
			return "待付款";
		} else if (value == 2) {
			return "待确认";
		} else if (value == 3) {
			return "待发货";
		} else if (value == 4) {
			return "已发货";
		} else if (value == 5){
			return "部分发货";
		}else if (value == 6){
			return "已完成";
		}else if (value == -1){
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
			return "后台代采";
		}

	}
}, 
{
	title : '采购商名称',
	field : 'purchaserName',
	width : 20,
	sortable : true
}, 
{
	title : '供应商名称',
	field : 'supplierName',
	width : 20,
	sortable : true
}, {
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
//{
//	title : '订单来源',
//	field : 'source',
//	width : 20,
//	sortable : true,
//	formatter : function(value, row, index) {
//		if(value==1){
//			return "pc端";
//		}else if(value ==2){
//			return "移动端";
//		}else if(value==3){
//			return "app";
//		}else{
//			return "";
//		}
//	}
//},
{
	title : '下单时间',
	field : 'insertTime',
	width : 20,
	sortable : true
},{
	title : '支付方式',
	field : 'paymentName',
	width : 20,
	sortable : true
},{
	title : '支付时间',
	field : 'paymentTime',
	width : 20,
	sortable : true
}, /*{
	title : '供应商',
	field : 'supplierName',
	width : 20,
	sortable : true
}, {
	title : '实际总金额',
	field : 'actualAmount',
	width : 20,
	sortable : true
}, */{
	title : '收货地址',
	field : 'detailAddress',
	width : 20,
	sortable : true
}, {
	title : '联系人',
	field : 'contacts',
	width : 20,
	sortable : true
},/* {
	title : '联系电话',
	field : 'contactsTel',
	width : 20,
	sortable : true
},*/ {
	title : '备注',
	field : 'meno',
	width : 20,
	sortable : true
}, {
	title : '付款凭证',
	field : 'voucherList',
	width : 20,
	sortable : true,
	formatter : function(value, row, index) {
		var str = '';
		if(value!=undefined&&value.length>0){
			for (var i = 0; i < value.length; i++) {
				str+="<a rel='group"+index+"' title='付款凭证' href='"+value[i].url+"'><img class='bigpicture' src='"+value[i].url+"' onerror='errorImg(this)'></a>";
			}
		}
		return str;

	}
}
];

/**
 * 授信管理-订单管理 导出订单
 */
function excel() {
	
	window.location.href='./exportFinanceOrder.do'+urlParams(1)+"&orderName=insertTime&orderType=desc";
	

}

/**
 * 订单导出
 */
function excelOrder() {
	var confirm = new $.flavr({
		content : '确定导出订单吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './exportOrderForm.do'+urlParams(1),
				dataType : "text",
				data : {

				},
				success : function(resp) {
					window.location.href = resp;
				}
			});
		}
	});
}


/**
 * 合同下载
 * @param orderNumber
 */
function downPDF(orderNumber) {
	var confirm = new $.flavr({
		content : '确定下载合同吗?',
		dialog : 'confirm',
		onConfirm : function() {
			window.location.href = getRootPath()+"/uploadPDF.do?orderNumber="+orderNumber;
			confirm.close();
		}
	});
}

/**
 * 订单管理状态Table切换
 * @param s
 */
function byOrderStatus(s){
	$("#orderStatus").val(s);
	var cfg = {
			url : './order_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'orderNumber',
			sortName : 'orderNumber',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('orderId', cfg);
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './order_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'orderNumber',
			sortName : 'orderNumber',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('orderId', cfg);
}


var columnsNew = [ 
 {
	title : '商品名称',
	field : 'orderNumber',
	width : 20,
	sortable : true,
	formatter : function(value, row, index) {
		var seriesName = typeof(row.seriesName) =="undefined"?" ":row.seriesName;
		var productName = typeof(row.productName) =="undefined"?" ":row.productName;
		var specName = typeof(row.specName) =="undefined"?" ":row.specName;
		var varietiesName = typeof(row.varietiesName) =="undefined"?" ":row.varietiesName;
		var seedvarietyName = typeof(row.seedvarietyName) =="undefined"?" ":row.seedvarietyName;
		var gradeName = typeof(row.gradeName) =="undefined"?" ":row.gradeName;
		return productName+" "+specName+" "+seriesName+" "+varietiesName+" "+seedvarietyName+" "+gradeName;
	}
},
{
	title : '批号',
	field : 'batchNumber',
	width : 20,
	sortable : true
},
{
	title : '箱数',
	field : 'boxes',
	width : 20,
	sortable : true
}, 
 {
	title : '总重量',
	field : 'weight',
	width : 20,
	sortable : true
},
{
	title : '商品单价',
	field : 'originalPrice',
	width : 20,
	sortable : true
},
{
	title : '商品成交单价',
	field : 'presentPrice',
	width : 20,
	sortable : true
},{
	title : '商品总价',
	field : 'originalTotalPrice',
	width : 20,
	sortable : true
},{
	title : '商品成交总价',
	field : 'presentTotalPrice',
	width : 20,
	sortable : true
},{
	title : '运费单价',
	field : 'originalFreight',
	width : 20,
	sortable : true
},{
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
		if(value==-1){
			return "已取消";
		}else if(value>3){
			return "已发货";
		}else{
			return "未发货";
		}
	}
}
];

/**
 * 订单商品
 * @param orderNumber
 */
function orderGoods(orderNumber){
	var cfg = {
			url : './orderGoods_data.do?orderNumber='+orderNumber,
			columns : columnsNew,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('goods', cfg);
}

/**
 * 上传付款凭证
 * @param orderNumber
 */
function orderPaymentVoucher(orderNumber){
	$('#payMentVoucherId').show();
	
	$("#dataForm img").each(function(){
        $(this)[0].src = "./style/images/bgbg.png";
		//$(this).attr("src","");
	});
	$("input[name='file']").val("");
	$('#payUrl').val(null);
	$('#orderNumberId').val(orderNumber);
}

/**
 * 上传付款凭证保存
 */
function submit(){
	
	var params = {};
	$("#dataForm img").each(function(){
		if($(this).attr("id")!=undefined){
			params[$(this).attr("id")] = $(this).attr("src");
		}
		if($(this).is(":hidden")){
			params[$(this).attr("id")] = "";
		}
	});
	params['orderNumber'] = $('#orderNumberId').val();
	
	if(valid("#dataForm")){
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
 * 列表刷新
 */
function loadFancyBox(){
	for(var i=0;i<200;i++){
		if($("#orderId a[rel=group"+i+"]").attr("title")!=undefined&&$("a[rel=group"+i+"]").attr("href")!=''){
			//图片弹出框
			$("#orderId a[rel=group"+i+"]").fancybox({
				'titlePosition' : 'over',
				'cyclic'        : true,
				'titleFormat'	: function(title, currentArray, currentIndex, currentOpts) {
					return '<span id="fancybox-title-over">' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
				}
			});
		}
	}
	btnList();
}