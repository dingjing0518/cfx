$(function() {
	createDataGrid('addGoodsInfo', cfgGoods);
	createDataGrid('priceTrendGoodsInfo', cfgPrice);
});


var toolbarGoods = [];
var columnsGoods = [ {
					title : '所有商品列表',
					field : 'b2bGoodsInfo',
					width : 20,
					sortable : true
				},{
					title : '操作',
					field : 'pk',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
							str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_HOMESHOW_PRICE_EDITGOODS_BTN_ADD" class="btn btn-warning" onclick="javascript:isAdd(\''
									+ value+ '\');">添加</button></a>';
						return str;
					}
				}
				];
var cfgGoods = {
	url : './searchGoodsToPriceTrendList.do',
	columns : columnsGoods,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbarGoods,
    queryParams : function(params) {
    return {
        block:"cf",
        limit : params.limit,
        start : params.offset,
        orderType : params.order,
        orderName : params.sort
    };
},
	onLoadSuccess:btnList
};

var toolbarPrice = [];
var columnsPrice = [ {
					title : '价格趋势商品列表',
					field : 'goodsInfo',
					width : 20,
					sortable : true
				},{
					title : '操作',
					field : 'pk',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
							str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_HOMESHOW_PRICE_EDITGOODS_BTN_REMOVE" class="btn btn-warning" onclick="javascript:isDelete(\''
									+ value+ '\');">移出</button></a>';
						return str;
					}
				}
				];
var cfgPrice = {
	url : './searchEditPriceTrendList.do',
	columns : columnsPrice,
	uniqueId : 'pk',
	sortName : 'date',
	sortOrder : 'desc',
	toolbar : toolbarPrice,
    queryParams : function(params) {
        return {
            block:"cf",
            limit : params.limit,
            start : params.offset,
            orderType : params.order,
            orderName : params.sort
        };
    },
	onLoadSuccess:btnList
};

function SearchCleanGoods(type){
	if(type == 2){
		cleanpar();
	}
	var cfgFind = {
			url : './searchGoodsToPriceTrendList.do'+urlParams(1),
			columns : columnsGoods,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbarGoods,
        	queryParams : function(params) {
            return {
                block:"cf",
                limit : params.limit,
                start : params.offset,
                orderType : params.order,
                orderName : params.sort
            };
        },
			onLoadSuccess:btnList
		};

	createDataGrid('addGoodsInfo', cfgFind);
}

function SearchCleanPrice(type){
	if(type == 2){
		cleanpar();
	}
	var cfgFind = {
			url : './searchEditPriceTrendList.do'+urlParams(1),
			columns : columnsPrice,
			uniqueId : 'pk',
			sortName : 'date',
			sortOrder : 'desc',
			toolbar : toolbarPrice,
        	queryParams : function(params) {
            return {
                block:"cf",
                limit : params.limit,
                start : params.offset,
                orderType : params.order,
                orderName : params.sort
            };
        },
			onLoadSuccess:btnList
		};

	createDataGrid('priceTrendGoodsInfo', cfgFind);
}

/**
 * 从商品表向价格趋势表添加数据
 * @param pk
 */
function isAdd(pk){
	
	$.ajax({
		type : 'POST',
		url : './insertGoodsPriceTrend.do',
		data : {
			 pk : pk
		},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});  
			if (data.success) {
				$("#quxiao").click();
				$('#priceTrendGoodsInfo').bootstrapTable('refresh');
			}

		}
	});
}

/**
 * 从价格趋势表移除商品
 * @param pk
 */
function isDelete(pk){
	
	var confirm = new $.flavr({
		content : '确定要移除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updatePriceTrend.do',
				data : {
					pk : pk,
					isDelete : 2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
					if (data.success) {
						$("#quxiao").click();
						$('#priceTrendGoodsInfo').bootstrapTable('refresh');
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
 * 跳转到价格趋势列表
 */
function  openFuturesPage(){
	window.location = getRootPath() + "/priceTrend.do?modelType=1";
}
