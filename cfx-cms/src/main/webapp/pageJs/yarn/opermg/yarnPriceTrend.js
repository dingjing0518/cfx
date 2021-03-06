$(function() {
	createDataGrid('priceTrendTable', cfg);
});


var toolbar = [];
var columns = [{
					title : '工艺',
					field : 'technologyName',
					width : 20,
					sortable : true
				},{
					title : '原料一级',
					field : 'firstMaterialName',
					width : 20,
					sortable : true
				},{
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
					title : '挂牌价(元/千克)',
					field : 'tonPrice',
					width : 20,
					sortable : true
				},{
					title : '平台更新时间',
					field : 'platformUpdateTime',
					width : 20,
					sortable : true
				},{
					title : '当前价格(元/千克)',
					field : 'price',
					width : 20,
					sortable : true
				},
				{
					title : '日期',
					field : 'date',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						return	value.substr(0,10);
					}
				},{
					title : '排序',
					field : 'sort',
					width : 20,
					sortable : true
				},{
					title : '修改时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				},
				{
					title : '首页显示',
					field : 'isHome',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "显示";
						} else  if(value==2){
							return "不显示";
						}
		
					}
				},{
					title : '操作',
					field : 'pk',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
						if (row.isHome == 1) {
							str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_BTN_UNSHOW" onclick="javascript:isShow(\''
									+ value+ '\',2);">不显示</button></a>';
						}else if(row.isHome == 2){
		   					str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_BTN_SHOW" onclick="javascript:isShow(\''
		   						+ value + '\',1); ">显示</button> </a>';
		   				}
						str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_BTN_DEL" onclick="javascript:isDelete(\''
		   					+ value + '\',\''+ row.goodsPk + '\'); ">删除 </button> </a>';

						str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_BTN_EDIT" onclick="javascript:edit(\''
		   					+ value + '\',\''+ row.price + '\',\''+ row.tonPrice + '\',\''+ row.date + '\',\''+ row.technologyName + '\',\''+ row.firstMaterialName + '\',\''+ row.secondMaterialName + '\',\''+ row.specifications + '\',\''+ row.sort + '\',\''+ row.goodsPk + '\'); ">编辑 </button> </a>';

		   				str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button data-toggle="modal" data-target="#priceTrendHistory" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_BTN_HISTRECORD" onclick="javascript:historyData(\''
		   					+ value + '\'); ">历史记录</button> </a>';
						return str;
					}
				}
				];
var cfg = {
	url : './searchPriceTrendList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'date',
	sortOrder : 'desc',
	toolbar : toolbar,
	pageList:[5,10,25,50,100],
    queryParams : function(params) {
        return {
            block:"sx",
            limit : params.limit,
            start : params.offset,
            orderType : params.order,
            orderName : params.sort
        };
    },
	onLoadSuccess:btnList
};

/**
 * 价格趋势历史子页面列表
 * @param type
 * @constructor
 */
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchPriceTrendList.do?'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'date',
			sortOrder : 'desc',
			toolbar : toolbar,
			pageList:[5,10,25,50,100],
			onLoadSuccess:btnList
		};

	createDataGrid('priceTrendTable', cfg);
}


var toolbarNew = [];
var columnsNew = [ {
						title : '工艺',
						field : 'technologyName',
						width : 20,
						sortable : true
					},{
						title : '原料一级',
						field : 'firstMaterialName',
						width : 20,
						sortable : true
					},{
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
							str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_HISTRECORD_BTN_UNSHOW" onclick="javascript:isShowHistory(\''
									+ value+ '\',\''+ row.date + '\',\''+ row.movementPk + '\',\''+ row.price + '\',2);">不显示</button></a>';
						}else if(row.isShow == 2){
		   					str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_HISTRECORD_BTN_SHOW" onclick="javascript:isShowHistory(\''
		   						+ value + '\',\''+ row.date + '\',\''+ row.movementPk + '\',\''+ row.price + '\',1); ">显示</button> </a>';
		   				}
		   				str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_HISTRECORD_BTN_EDIT" onclick="javascript:editHistory(\''
		   					+ value + '\',\''+ row.price + '\',\''+ row.yarnPrice + '\',\''+ row.date + '\',\''+ row.technologyName + '\',\''+ row.firstMaterialName + '\',\''+ row.secondMaterialName + '\',\''+ row.specifications + '\',\''+ row.goodsPk + '\',\''+ row.movementPk + '\',\''+ row.isShow + '\'); ">编辑 </button> </a>';

						str+='<a class="save" href="javascript:;" style="text-decoration:none;" >  <button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_HISTRECORD_BTN_ADD" onclick="javascript:insertHistory(\''
		   					+ value + '\',\''+ row.price + '\',\''+ row.yarnPrice + '\',\''+ row.date + '\',\''+ row.technologyName + '\',\''+ row.firstMaterialName + '\',\''+ row.secondMaterialName + '\',\''+ row.specifications + '\',\''+ row.goodsPk + '\',\''+ row.movementPk + '\'); ">新增 </button> </a>';
		   				return str;
					}
				}
				];

function historyData(pk){
	
	var cfg = {
			url : './searchSxPriceTrendHistoryList.do?movementPk='+pk,
			columns : columnsNew,
			uniqueId : '_id',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbarNew,
			pageList:[5,10,25,50,100],
			onLoadSuccess:btnList
		};
	$("#movementPk").val(pk);
	createDataGrid('childs', cfg);
}

/**
 * 搜索价格趋势历史列表
 * @param type
 * @constructor
 */
function SearchCleanHistory(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchSxPriceTrendHistoryList.do?'+urlParams(1),
			columns : columnsNew,
			uniqueId : '_id',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbarNew,
			pageList:[5,10,25,50,100],
			onLoadSuccess:btnList
		};
	createDataGrid('childs', cfg);
}

/**
 * 价格趋势列表table页面切换
 * @param s
 */
function findTablet(s){
	if(s!=""){
		$("input[name='isShowHome']").val(s);
	}else{
		$("input[name='isShowHome']").val("");
	}
	var cfg = {
			url : './searchPriceTrendList.do?'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('priceTrendTable', cfg);
}

/**
 * 显示、不显示
 * @param pk
 * @param isHome
 */
function isShow(pk,isHome){
	
	$.ajax({
		type : 'POST',
		url : './updatePriceTrend.do',
		data : {pk:pk,isHome:isHome,isEdit:2},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiao").click();
			$('#priceTrendTable').bootstrapTable('refresh');
		}
	});
}

/**
 * 历史记录 显示不、显示
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
			$('#childs').bootstrapTable('refresh');
		}
	});
}

/**
 * 删除
 * @param pk
 * @param goodsPk
 */
function isDelete(pk,goodsPk){
	
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updatePriceTrend.do',
				data : {
					pk : pk,
					isDelete : 2,
					goodsPk:goodsPk
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
					if (data.success) {
						$("#quxiao").click();
						$('#priceTrendTable').bootstrapTable('refresh');
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
 * 编辑价格趋势
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
 * @param sort
 * @param goodsPk
 */
function edit(pk,price ,tonPrice,date ,technologyName, firstMaterialName ,secondMaterialName ,specifications ,sort ,goodsPk){
	$('#editPriceTrend').modal();
	$("#goodsInfo").val(technologyName+" "+firstMaterialName+" "+secondMaterialName+" "+specifications);
	if(tonPrice != 'undefined'){
		$("#tonPrice").val(tonPrice);
	}
	if(price != 'undefined'){
	$("#price").val(price);
	}else{
	$("#price").val("");
	}
	$("#date").val(date.substring(0,10));
	$("#sort").val(sort);
	$("#pk").val(pk);
	$("#goodsPk").val(goodsPk);
	$("#date").attr("disabled",true);
}

/**
 * 保存价格趋势
 */
function submit(){
	
	var price = $("#price").val();
	var date = $("#date").val();
	var sort = $("#sort").val();
	var goodsPk = $("#goodsPk").val();
	
	if($('#sort').val()!=''&& $('#sort').val()!=null){
		if(!isDNumber($('#sort').val())||$('#sort').val()>=100){
			new $.flavr({
				modal : false,
				content : "排序应不大于两位的整数！"
			});
			return;
		}
	}
	var pk = $("#pk").val();
	$.ajax({
		type : 'POST',
		url : './updatePriceTrend.do',
		data : {
			pk:pk,
			price:price,
			dateStr:date+"",
			sort:sort,
			goodsPk:goodsPk
			
			},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiao").click();
			$('#priceTrendTable').bootstrapTable('refresh');
		}
	});
}

function checkNumber(n) {
    var filter  = /^([1-9]\d*|0)(\.\d{1,2})?$/;
    return filter.test(n)
}

/**
 * 导出价格趋势
 */
function exportPrice(){

    $.ajax({
        type : 'POST',
        url : getRootPath() + '/exportPriceTrend.do?'+urlParams(1),
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
 * 删除价格趋势历史
 * @param pk
 * @param goodsPk
 * @param movementPk
 */
function isDeleteHistory(pk,goodsPk,movementPk){
	
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
						$('#childs').bootstrapTable('refresh');
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
 * 编辑价格趋势历史
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
function editHistory(pk,price ,yarnPrice ,date ,technologyName ,firstMaterialName,secondMaterialName ,specifications ,goodsPk,movementPk,isShow){
	$("#myPriceTrendHistoryModalLabel").html("编辑");
	$('#editPriceTrendHistory').modal();
	$("#goodsInfoHistory").val(technologyName+" "+firstMaterialName+" "+secondMaterialName+" "+specifications);
	if(yarnPrice != 'undefined'){
		$("#tonPriceHistory").val(yarnPrice);
	}
	if(price != 'undefined'){
		$("#priceHistory").val(price);
	}
	$("#dateHistory").val(date.substring(0,10));
	$("#goodsPkHistory").val(goodsPk);
	$("#movementPkHistory").val(movementPk);
	$("#dateHistory").attr("disabled",true);
	$("#isInsertHistory").val(2);//编辑标识
	$("#isShow").val(isShow);
	
}

/**
 * 价格趋势历史新增
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
function insertHistory(pk,price ,yarnPrice,date ,technologyName,firstMaterialName,secondMaterialName,specifications ,goodsPk ,movementPk){
	$("#myPriceTrendHistoryModalLabel").html("新增");
	$('#editPriceTrendHistory').modal();
	$("#goodsInfoHistory").val(technologyName+" "+firstMaterialName+" "+secondMaterialName+" "+specifications);
	if(yarnPrice != 'undefined'){
		$("#tonPriceHistory").val(yarnPrice);
	}
	$("#priceHistory").val("")
	$("#dateHistory").val(new Date().Format("yyyy-MM-dd"));
	$("#goodsPkHistory").val(goodsPk);
	$("#movementPkHistory").val(movementPk);
	$("#dateHistory").attr("disabled",false);
	$("#isInsertHistory").val(1);//新增标识
	$("#isShow").val(1);
}

/**
 * 保存价格趋势历史
 */
function submitHistory(){
	var date = $("#dateHistory").val();
	var goodsPk = $("#goodsPkHistory").val();
    var movementPk =  $("#movementPk").val();
	//var movementPk = $("#movementPkHistory").val();
	var price = $("#priceHistory").val();
	var isInsert = $("#isInsertHistory").val();
	var isShow = $("#isShow").val();
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
	if(isInsert==1){
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
			isShow:isShow,
			movementPk:movementPk
			},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			
			$('#childs').bootstrapTable('refresh');
			$("#quxiaoHistory").click();
		}
	});
}

function changeFirstMaterial(value){
	

    $.ajax({
        type : 'POST',
        url :"./getSecondMaterialList.do",
        data : {
            parentPk:value
        },
        dataType : 'json',
        success : function(data) {
        	$("#secondMaterialName").empty();
        	$("#secondMaterialName").append("<option value=''>--请选择--</option>");
        	
            if(data){
               
                for(var m = 0; m < data.length; m++){
                 $("#secondMaterialName").append("<option value="+data[m].pk+">"+data[m].name+"</option>");
                }
            }
            $("#secondMaterialName").selectpicker('refresh');//
        }
    });
}

/**
 * 跳转到编辑商品列表页面
 */
function editGoods() {
    window.location.href = getRootPath() + '/showEditYarnPriceTrend.do';
}