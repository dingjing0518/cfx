$(function() {
	createDataGrid('priceTrendHistoryTable', cfg);
});


var toolbar = [];
var columns = [ {
					title : '工艺',
					field : 'technologyName',
					width : 20,
					sortable : true
				},{
					title : '原料一级',
					field : 'firstMaterialName',
					width : 20,
					sortable : true
				}
				,{
					title : '原料二级',
					field : 'secondMaterialName',
					width : 20,
					sortable : true
				},{
					title : '规格',
					field : 'specifications',
					width : 20,
					sortable : true
				},{
					title : '品牌',
					field : 'brandName',
					width : 20,
					sortable : true
				},{
					title : '当前价格(元/千克)',
					field : 'price',
					width : 20,
					sortable : true
				},{
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
				},
					{
						title : '日期',
						field : 'date',
						width : 20,
						sortable : true
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
							str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICEHIST_BTN_UNSHOW" onclick="javascript:isShowHistory(\''
									+ value+ '\',\''+ row.date + '\',\''+ row.movementPk + '\',\''+ row.price + '\',2);">不显示</button></a>';
						}else if(row.isShow == 2){
		   					str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICEHIST_BTN_SHOW" onclick="javascript:isShowHistory(\''
		   						+ value + '\',\''+ row.date + '\',\''+ row.movementPk + '\',\''+ row.price + '\',1); ">显示</button> </a>';
		   				}
		   				str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICEHIST_BTN_EDIT" onclick="javascript:edit(\''
                            + value + '\',\''+ row.price + '\',\''+ row.yarnPrice + '\',\''+ row.date + '\',\''+ row.technologyName + '\',\''+ row.firstMaterialName + '\',\''+ row.secondMaterialName + '\',\''+ row.specifications + '\',\''+ row.brandName + '\',\''+ row.goodsPk + '\',\''+ row.movementPk + '\',\''+ row.isShow + '\'); ">编辑 </button> </a>';
		   				str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICEHIST_BTN_ADD" onclick="javascript:insert(\''
                            + value + '\',\''+ row.price + '\',\''+ row.yarnPrice + '\',\''+ row.date + '\',\''+ row.technologyName + '\',\''+ row.firstMaterialName + '\',\''+ row.secondMaterialName + '\',\''+ row.specifications + '\',\''+ row.brandName + '\',\''+ row.goodsPk + '\',\''+ row.movementPk + '\'); ">新增 </button> </a>';
		   				return str;
					}
				}
				];
var cfg = {
	url : './searchSxPriceTrendHistoryList.do',
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
			url : './searchSxPriceTrendHistoryList.do'+urlParams(1),
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
		url : './isShowSxPriceTrendHistory.do',
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
				url : './deleteSxPriceTrendHistory.do',
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
function edit(pk,price ,yarnPrice ,date ,technologyName,firstMaterialName ,secondMaterialName ,specifications,brandName ,goodsPk,movementPk,isShow){
	$("#myModalLabel").html("编辑");
	$('#editPriceTrend').modal();
	$("#goodsInfo").val(technologyName+" "+firstMaterialName+" "+secondMaterialName+" "+specifications);
	if(yarnPrice != 'undefined'){
		$("#yarnPrice").val(yarnPrice);
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
function insert(pk,price ,yarnPrice,date ,technologyName,firstMaterialName,secondMaterialName,specifications ,brandName ,goodsPk ,movementPk){
	$("#myModalLabel").html("新增");
	$('#editPriceTrend').modal();
	$("#goodsInfo").val(technologyName+" "+firstMaterialName+" "+secondMaterialName+" "+specifications);
	if(yarnPrice != 'undefined'){
		$("#yarnPrice").val(yarnPrice);
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


    if (checkNumber(price) == false||price<=0||price>100000) {
        new $.flavr({
            modal : false,
            content : "对应价格必须是小数位不超过两位的数字,取值大于 0 小于等于 100000！"
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
		url = "./insertSxPriceTrendHistory.do";
	}else{
		url = "./updateSxPriceTrendHistory.do";
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
        url : './exportSxPriceTrendHistoryList.do'+urlParams(1)+"&block=sx",
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
