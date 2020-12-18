$(function() {
	createDataGrid('contractId', cfg);
	$(".form_datetime").datetimepicker({
	      minView: "month",
	      format: "yyyy-mm-dd",
	      autoclose: true,
	      todayBtn: true,
	      language:'zh-CN',
	      pickerPosition:"bottom-right"
	    });
});
/**
 * 获取跳转链接参数
 * @param key
 * @returns {*}
 * @constructor
 */
function GetRequest(key) {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest[key];
}
var toolbar = [];
var columns = [	{
	title : '操作',
	field : 'orderNumber',
	width : 150,
	formatter : function(value, row, index) {
	   var str="";
	   str+='<button type="button" style="display:none;" showname="CONTRACT_COMMON_BTN_DOWNLOAND" class="btn btn-warning" onclick="javascript:downContractPDF(\''+ row.contractNo+ '\',\'' + row.block + '\');">下载合同</button></a>';
	   str+='<button type="button" style="display:none;" showname="CONTRACT_COMMON_BTN_CONTRACTGOODS" class="btn btn-warning" onclick="javascript:contractGoods(\''+ row.contractNo+ '\',\'' + row.block + '\');">合同商品</button></a>';
        if(row.contractStatus != -1) {
        	str += '<button type="button" style="display:none;" showname="CONTRACT_COMMON_BTN_CLOSE" class="btn btn-warning" onclick="javascript:contractCLOSE(\'' + row.contractNo + '\');">合同关闭</button></a>';
        } else{
            str+='<button type="button" style="display:none;" showname="CONTRACT_COMMON_BTN_CONTRACTTRACK" class="btn btn-warning" onclick="javascript:contractTrack(\''+ row.contractNo+ '\');">合同追踪</button></a>';
		}

	   return str ;
	}
}, {
	title : '合同编号',
	field : 'contractNo',
	width : 20,
	sortable : true
},
{
	title : '合同状态',
	field : 'contractStatus',
	width : 20,
	sortable : true,
    formatter : function(value, row, index) {
        if (value == 1) {
            return "待付款";
        } else if (value == 2) {
            return "待审批";
        } else if (value == 3) {
            return "待发货";
        } else if (value == 4) {
            return "部分发货";
        } else if (value == 5) {
            return "待收货";
        } else if (value == 6) {
            return "已完成";
        } else if (value == -1) {
            return "已关闭";
        }else{
            return "--";
        }
    }
}, 
{
	title : '采购商',
	field : 'purchaserName',
	width : 20
},
{
	title : '采购商电话',
	field : 'telephone',
	width : 20
},
{
	title : '店铺',
	field : 'storeName',
	width : 20,
	sortable : true
},
{
	title : '合同金额',
	field : 'totalAmount',
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
	title : '签收单位',
	field : 'signingCompany',
	width : 20
},
{
	title : '收货人',
	field : 'signingAcc',
	width : 20
},
	{
        title : '联系方式',
        field : 'signingTel',
        width : 20
    },
    {
        title : '送货地址',
        field : 'toAddress',
        width : 20
    },
    {
        title: '付款凭证',
        field: 'payVoucherUrl',
        width: 20,
		formatter : function(value, row, index) {
		var str = '';
		if (value != undefined && value.length > 0) {
			for ( var i = 0; i < value.length; i++) {
                var path = value[i];
                if(path == null || path == undefined){
                    path = './style/images/bgbg.png';
                }
				
				str += "<a class='fancybox' title='付款凭证' href='" + path
				+ "'><img class='bigpicture' src='"+path+"'></a>";
			}
		}
		return str;

}
    }

];
var cfg = {
	url : './searchContractList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
    queryParams : function(params) {
    return {
    	block:$("#block").val(),
        limit : params.limit,
        start : params.offset,
        orderType : params.order,
        orderName : params.sort
    };
},
    paginationShowPageGo: true,
	onLoadSuccess:loadFancyBox
};

/**
 * 合同订单状态table切换
 * @param contractStatus
 */
function byOrderStatus(contractStatus){
    $("#contractStatus").val(contractStatus);
    var cfg = {
        url : './searchContractList.do'+urlParams(1),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'insertTime',
        paginationShowPageGo: true,
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess:loadFancyBox
    };
    createDataGrid('contractId', cfg);
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './searchContractList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
        	paginationShowPageGo: true,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('contractId', cfg);
}

/**
 * 合同关闭
 * @param pk
 */

function contractCLOSE(pk) {
	$("#pk").val(pk);
	$("#creditCustomerReason").modal();
    $("#closeReason").val("");
}

function submitClose(){
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
                url : './updateContract.do?contractNo='+$("#pk").val(),
                data : {
                		contractStatus:-1,
                    	closeReason:closeReason
				},
                dataType : 'json',
                success : function(data) {
                    new $.flavr({
                        modal : false,
                        content : data.msg
                    });
                    if(data.success == true){
                        $("#quxiao").click();
                        $('#contractId').bootstrapTable('refresh');
                    }
                }
            });
}

var columnCF = [
            		{

            			title : '商品名称',
            			field : 'cfGoods',
            			width : 20,
            			formatter : function(value, row, index) {

                                var productName = typeof (value.productName) == "undefined" ? " "
                                    : value.productName;
                                var varietiesName = typeof (value.varietiesName) == "undefined" ? " "
                                    : value.varietiesName;
                                var specName = typeof (value.specName) == "undefined" ? " "
                                    : value.specName;
                                var seriesName = typeof (value.seriesName) == "undefined" ? " "
                                    : value.seriesName;
                                var batchNumber = typeof (value.batchNumber) == "undefined" ? " "
                                    : value.batchNumber;
                                var distinctNumber = typeof (value.distinctNumber) == "undefined" ? " "
                                    : value.distinctNumber;
                                var level = typeof (value.level) == "undefined" ? " "
                                    : value.level;
                                var packageNumber = typeof (value.packageNumber) == "undefined" ? " "
                                    : value.packageNumber;
                                return productName + " " + varietiesName + " " + specName + " "
                                    + seriesName + " " + batchNumber + " "
                                    + distinctNumber + " " + level + " " + packageNumber;
            			}
            		
            		},{
            			title : '批号',
            			field : 'cfGoods.batchNumber',
            			width : 20
            		},
            		{
            			title : '箱数',
            			field : 'boxes',
            			width : 20,
            			sortable : true
            		},
            		{
            			title : '总重量',
            			field : 'totalWeight',
            			width : 20,
            			sortable : true
            		},{
            			title : '单价(元/吨)',
            			field : 'basicPrice',
            			width : 20,
            			sortable : true
            		}
					,{
						title : '优惠(元/吨)',
						field : 'discount',
						width : 20,
						sortable : true
					},{
						title : '合同单价(元/吨)',
						field : 'contractPrice',
						width : 20,
						sortable : true
					},{
						title : '包装费(元/吨)',
						field : 'cfGoods.packageFee',
						width : 20
					},{
						title : '装车费(元/吨)',
						field : 'cfGoods.loadFee',
						width : 20
					},{
						title : '自提管理费(元/吨)',
						field : 'cfGoods.adminFee',
						width : 20
					},{
						title : '合同价(元/吨)',
						field : 'contractNo',
						width : 20,
						formatter : function(value, row, index) {
					    	var basicPrice  = parseFloat(row.basicPrice!=undefined?row.basicPrice:0);
					    	var discount = parseFloat(row.discount!=undefined?row.discount:0);
					    	var loadFee = parseFloat(row.cfGoods.loadFee!=undefined?row.cfGoods.loadFee:0);
					    	var adminFee = parseFloat(row.cfGoods.adminFee!=undefined?row.cfGoods.adminFee:0);
					    	var packageFee =  parseFloat(row.cfGoods.packageFee!=undefined?row.cfGoods.packageFee:0);
					    return	(basicPrice-discount+loadFee +adminFee + packageFee).toFixed(2);
					    }
					}];

var columnSX = [
        		{

        		    title : '商品名称',
        		    field : 'sxGoods',
        		    width : 20,
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
        		},
        		{
        			title : '箱数',
        			field : 'boxes',
        			width : 20,
        			sortable : true
        		},
        		{
        			title : '总重量',
        			field : 'totalWeight',
        			width : 20,
        			sortable : true
        		},{
        			title : '单价(元/千克)',
        			field : 'basicPrice',
        			width : 20,
        			sortable : true
        		}
				,{
					title : '优惠(元)',
					field : 'discount',
					width : 20,
					sortable : true
				},{
					title : '合同单价(元/千克)',
					field : 'contractPrice',
					width : 20,
					sortable : true
				},{
					title : '包装费(元)',
					field : 'sxGoods.packageFee',
					width : 20
				},{
					title : '装车费(元)',
					field : 'sxGoods.loadFee',
					width : 20
				},{
					title : '自提管理费(元)',
					field : 'sxGoods.adminFee',
					width : 20
				},{
					title : '合同价(元)',
					field : 'contractNo',
					width : 20,
					formatter : function(value, row, index) {
				    	var basicPrice  = parseFloat(row.basicPrice!=undefined?row.basicPrice:0);
				    	var discount = parseFloat(row.discount!=undefined?row.discount:0);
				    	var loadFee = parseFloat(row.sxGoods.loadFee!=undefined?row.sxGoods.loadFee:0);
				    	var adminFee = parseFloat(row.sxGoods.adminFee!=undefined?row.sxGoods.adminFee:0);
				    	var packageFee =  parseFloat(row.sxGoods.packageFee!=undefined?row.sxGoods.packageFee:0);
				    return	(basicPrice-discount+loadFee +adminFee + packageFee).toFixed(2);
				    }
				}];

/**
 * 查询合同商品子页面列表
 * @param pk
 */
function contractGoods(pk,block){
    $("#contractHiddenId").val(pk);
    $('#contractGoodsWinds').modal();
    if (block == "cf") {
        var cfg = {
            url : './searchContractGoods.do',
            columns : columnCF,
            uniqueId : 'pk',
            sortName : 'insertTime',
            sortOrder : 'desc',
            queryParams : function(params) {
                return {
                    contractNo : pk,
                    block : block,
                    limit : params.limit,
                    start : params.offset,
                    orderType : params.order,
                    orderName : params.sort
                }
            },
            toolbar : toolbar
        };
    }else{
        var cfg = {
            url : './searchContractGoods.do',
            columns : columnSX,
            uniqueId : 'pk',
            sortName : 'insertTime',
            sortOrder : 'desc',
            queryParams : function(params) {
                return {
                    contractNo : pk,
                    block : block,
                    limit : params.limit,
                    start : params.offset,
                    orderType : params.order,
                    orderName : params.sort
                }
            },
            toolbar : toolbar
        };
    }
    createDataGrid('contractGoodsId', cfg);

}

/**
 * 合同追踪
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

/**
 * 合同下载
 * @param contractNo
 */
function downContractPDF(contractNo,block) {
	var confirm = new $.flavr({
		content : '确定下载合同吗?',
		dialog : 'confirm',
		onConfirm : function() {
			window.location.href = getRootPath()+"/contractPDFDownLoad.do?contractNo="+contractNo+"&block="+block;
			confirm.close();
		}
	});
}

/**
 * 刷新页面按钮
 */
function loadFancyBox(){
	$(".fancybox").fancybox({
	    'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic',
		'titlePosition' : 'inside'
	});
	btnList();
}