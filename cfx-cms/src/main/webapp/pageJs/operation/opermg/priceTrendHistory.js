$(function() {
	createDataGrid('priceTrendHistoryTable', cfg);
});


var toolbar = [];
var columns = [ {
					title : '品名',
					field : 'productName',
					width : 20,
					sortable : true
				}, 
				{
					title : '品种',
					field : 'varietiesName',
					width : 20,
					sortable : true
				},
				{
					title : '规格大类',
					field : 'specName',
					width : 20,
					sortable : true
				}, 
				{
					title : '规格系列',
					field : 'seriesName',
					width : 20,
					sortable : true
				}, 
				{
					title : '规格',
					field : 'specifications',
					width : 20,
					sortable : true
				},
				{
					title : '批号',
					field : 'batchNumber',
					width : 20,
					sortable : true
				},
				{
					title : '品牌',
					field : 'brandName',
					width : 20,
					sortable : true
				}, 
				{
					title : '价格',
					field : 'price',
					width : 20,
					sortable : true
				}, 
				{
					title : '日期',
					field : 'date',
					width : 20,
					sortable : true
				},
				{
					title : '是否显示',
					field : 'isShow',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if(value==1){
							return "显示";
						}else if(value==2){
							return "不显示";
						}
					}
				},{
					title : '修改时间',
					field : 'insertTime',
					width : 20,
					sortable : true
				},{
					title : '操作',
					field : 'id',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
						if (row.isShow == 1) {
							str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" style="display:none;" showname="OPER_MG_HOMESHOW_PRICEHIST_BTN_UNSHOW" onclick="javascript:isShowHistory(\''
									+ value+ '\',\''+ row.date + '\',\''+ row.movementPk + '\',\''+ row.price + '\',2);">不显示</button></a>';
						}else if(row.isShow == 2){
		   					str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="OPER_MG_HOMESHOW_PRICEHIST_BTN_SHOW" onclick="javascript:isShowHistory(\''
		   						+ value + '\',\''+ row.date + '\',\''+ row.movementPk + '\',\''+ row.price + '\',1); ">显示</button> </a>';
		   				}
//						str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="BTN_PRICETRENDHISTORY_DELETE" onclick="javascript:isDelete(\''
//		   					+ value + '\',\''+ row.goodsPk + '\',\''+ row.movementPk + '\'); ">删除 </button> </a>';
		   				str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="OPER_MG_HOMESHOW_PRICEHIST_BTN_EDIT" onclick="javascript:edit(\''
		   					+ value + '\',\''+ row.price + '\',\''+ row.tonPrice + '\',\''+ row.date + '\',\''+ row.productName + '\',\''+ row.varietiesName + '\',\''+ row.specName + '\',\''+ row.seriesName + '\',\''+ row.specifications + '\',\''+ row.batchNumber + '\',\''+ row.brandName + '\',\''+ row.gradeName + '\',\''+ row.goodsPk + '\',\''+ row.movementPk + '\',\''+ row.isShow + '\'); ">编辑 </button> </a>';
		   				str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="OPER_MG_HOMESHOW_PRICEHIST_BTN_ADD" onclick="javascript:insert(\''
		   					+ value + '\',\''+ row.price + '\',\''+ row.tonPrice + '\',\''+ row.date + '\',\''+ row.productName + '\',\''+ row.varietiesName + '\',\''+ row.specName + '\',\''+ row.seriesName + '\',\''+ row.specifications + '\',\''+ row.batchNumber + '\',\''+ row.brandName + '\',\''+ row.gradeName + '\',\''+ row.goodsPk + '\',\''+ row.movementPk + '\'); ">新增 </button> </a>';
		   				return str;
					}
				}
				];
var cfg = {
	url : './searchPriceTrendHistoryList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'date',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchPriceTrendHistoryList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'date',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('priceTrendHistoryTable', cfg);
}

/**
 * 显示、不显示
 * @param pk
 * @param isShow
 */
function isShowHistory(pk,date,movementPk,price,isShow){
	
	$.ajax({
		type : 'POST',
		url : './isShowPriceTrendHistory.do',
		data : {
			id:pk,
			isShow:isShow,
            date:date,
            movementPk:movementPk,
            price:price
			},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiao").click();
			$('#priceTrendHistoryTable').bootstrapTable('refresh');
		}
	});
}

/**
 * 删除
 * @param pk
 * @param goodsPk
 * @param movementPk
 */
function isDelete(pk,goodsPk,movementPk){
	
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './deletePriceTrendHistory.do',
				data : {
					id : pk,
					goodsPk:goodsPk,
					movementPk:movementPk
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
					if (data.success) {
						$("#quxiao").click();
						$('#priceTrendHistoryTable').bootstrapTable('refresh');
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
 * @param price
 * @param tonPrice
 * @param date
 * @param productName
 * @param varietiesName
 * @param specName
 * @param seriesName
 * @param specifications
 * @param batchNumber
 * @param brandName
 * @param gradeName
 * @param goodsPk
 * @param movementPk
 * @param isShow
 */
function edit(pk,price,tonPrice,date,productName,varietiesName,specName,seriesName,specifications,batchNumber,brandName,gradeName,goodsPk,movementPk,isShow){
	$("#myModalLabel").html("编辑");
	$('#editPriceTrend').modal();
	$("#goodsInfo").val(productName+" "+varietiesName+" "+brandName+" "+specName+" "+specifications+" "+batchNumber+" "+gradeName);
	if(tonPrice != 'undefined'){
		$("#tonPrice").val(tonPrice);	
	}
	if(price != 'undefined'){
		$("#price").val(price);
	}
	$("#date").val(date.substring(0,10));
	$("#goodsPk").val(goodsPk);
	$("#movementPk").val(movementPk);
	$("#date").attr("disabled",true);
	$("#isInsert").val(false);
	$("#isShow").val(isShow);
}

/**
 * 新增
 * @param pk
 * @param price
 * @param tonPrice
 * @param date
 * @param productName
 * @param varietiesName
 * @param specName
 * @param seriesName
 * @param specifications
 * @param batchNumber
 * @param brandName
 * @param gradeName
 * @param goodsPk
 * @param movementPk
 */
function insert(pk,price,tonPrice,date,productName,varietiesName,specName,seriesName,specifications,batchNumber,brandName,gradeName,goodsPk,movementPk){
	$("#myModalLabel").html("新增");
	$('#editPriceTrend').modal();
	$("#goodsInfo").val(productName+" "+varietiesName+" "+brandName+" "+specName+" "+specifications+" "+batchNumber+" "+gradeName);
	if(tonPrice != 'undefined'){
		$("#tonPrice").val(tonPrice);	
	}
	$("#price").val("")
	$("#date").val("");
	$("#goodsPk").val(goodsPk);
	$("#movementPk").val(movementPk);
	$("#date").attr("disabled",false);
	$("#isInsert").val(true);
	$("#isShow").val(1);
}

/**
 * 保存
 */
function submit(){
	var date = $("#date").val();
	var goodsPk = $("#goodsPk").val();
	var movementPk = $("#movementPk").val();
	var price = $("#price").val();
	var isInsert = $("#isInsert").val();
	var isShow =	$("#isShow").val();
	if (checkNumber(price) == false||price<1000||price>100000) {
		new $.flavr({
			modal : false,
			content : "对应价格必须是小数位不超过两位的数字,取值区间【1000,100000】！"
		});
		return;
	}
	if (date== null||date=="") {
		new $.flavr({
			modal : false,
			content : "日期不能为空！"
		});
		return;
	}
	var url = "";
	if(isInsert){
		url = "./insertPriceTrendHistory.do";
	}else{
		url = "./updatePriceTrendHistory.do";	
	}
	
	var pk = $("#pk").val();
	$.ajax({
		type : 'POST',
		url : url,
		data : {
			price:price,
			date:date+"",
			goodsPk:goodsPk,
			movementPk:movementPk,
			isShow:isShow
			},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiao").click();
			$('#priceTrendHistoryTable').bootstrapTable('refresh');
		}
	});
}

/**
 * 验证是否是小数位不小于两位的数字
 * @param n
 * @returns {*|boolean}
 */
function checkNumber(n) {
    var filter  = /^([1-9]\d*|0)(\.\d{1,2})?$/;
    return filter.test(n)
}

/**
 * 导出历史数据
 */
function exportHistory(){

    $.ajax({
        type : 'POST',
        url : './exportPriceTrendHistoryList.do'+urlParams(1)+"&block=cf",
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
