$(function() {
	createDataGrid('goodsUpperId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				var insertTime=row.insertTime;
				var auditTime=row.auditTime;
				var updateTime=row.updateTime;
				if (row.isUpdown == 1) {
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="YARN_GOODS_MG_BTN_UP" class="btn btn-warning" onclick="javascript:editState(\''
							+ value
							+ '\',2,\'' + insertTime + '\',\'' + auditTime + '\',\'' + updateTime + '\');">上架</button></a>';
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="YARN_GOODS_MG_BTN_DOWN" class="btn btn-warning" onclick="javascript:editState(\''
							+ value
							+ '\',3,\'' + insertTime + '\',\'' + auditTime + '\',\'' + updateTime + '\');">下架</button></a>';
				} else if (row.isUpdown == 2) {
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="YARN_GOODS_MG_BTN_DOWN" class="btn btn-warning" onclick="javascript:editState(\''
							+ value
							+ '\',3,\'' + insertTime + '\',\'' + auditTime + '\',\'' + updateTime + '\');">下架</button></a>';
				} else if (row.isUpdown == 3) {
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="YARN_GOODS_MG_BTN_UP" class="btn btn-warning" onclick="javascript:editState(\''
							+ value
							+ '\',2,\'' + insertTime + '\',\'' + auditTime + '\',\'' + updateTime + '\');">上架</button></a>';
				}

				return str;
			}
		},{
			title : '是否上架',
			field : 'isUpdownName',
			width : 20,
			sortable : true
		},{
			title : '商品类型',
			field : 'typeName',
			width : 20,
			sortable : true
		},{
			title : '是否显示',
			field : 'isNewProductName',
			width : 20,
			sortable : true
		},{
			title : '是否混纺',
			field : 'sxGoodsExt.isBlendName',
			width : 20,
			sortable : true
		},
		{
			title : '店铺',
			field : 'storeName',
			width : 20,
			sortable : true
		},{
			title : '挂牌价（元/千克）',
			field : 'tonPrice',
			width : 20,
			sortable : true
		},  {
			title : '公司名称',
			field : 'companyName',
			width : 20,
			sortable : true
		}, {
			title : '厂家品牌',
			field : 'brandName',
			width : 20,
			sortable : true
		},{
			title : '原料一级',
			field : 'sxGoodsExt.rawMaterialParentName',
			width : 20
		},{
			title : '原料二级',
			field : 'sxGoodsExt.rawMaterialName',
			width : 20
		},{
			title : '工艺',
			field : 'sxGoodsExt.technologyName',
			width : 20
		}, {
			title : '规格',
			field : 'sxGoodsExt.specName',
			width : 20
		},{
			title : '物料名称',
			field : 'sxGoodsExt.materialName',
			width : 20
		},{
			title : '证书名称',
			field : 'sxGoodsExt.certificateName',
			width : 20
		}, {
			title : '创建时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}, {
			title : '更新时间',
			field : 'updateTime',
			width : 20,
			sortable : true
		}, {
			title : '上架时间',
			field : 'upTime',
			width : 20,
			sortable : true
		},{
			title : '箱/包重',
			field : 'tareWeight',
			width : 20,
			sortable : true
		},{
			title : '可用箱数/包数',
			field : 'totalBoxes',
			width : 20,
			sortable : true
		},{
			title : '可用库存',
			field : 'totalWeight',
			width : 20,
			sortable : true
		},{
			title : '厂区名称',
			field : 'sxGoodsExt.plantName',
			width : 20
		}, {
			title : '仓库名称',
			field : 'sxGoodsExt.wareName',
			width : 20
		}, {
			title : '用途描述',
			field : 'sxGoodsExt.meno',
			width : 20
		} ];

var cfg = {
	url : './searchGoodsManageList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	queryParams : function(params) {
		return {
			auditStatus : 2,
			limit : params.limit,
			start : params.offset,
			orderType : params.order,
			orderName : params.sort
		}
	},
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 商品上架、下架
 * @param pk
 * @param sta
 */
function editState(pk, sta) {
	var parm = {
		pk: pk,
		isUpdown : sta
	};
	$.ajax({
		type : 'POST',
		url : './updateGoods.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#goodsUpperId').bootstrapTable('refresh');
			}

		}
	});

}

/**
 * 商品上下架状态切换Table
 * @param s
 */
function findMarri(s) {
	if(s==0){
		$("#isUpdown").val("");
	}else{
		$("#isUpdown").val(s);
	}
		var url="./searchGoodsManageList.do"+urlParams(1)+"&auditStatus=2";
	var cfg = {
		url : url,
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('goodsUpperId', cfg);
}
function SearchClean(type){
	
	if(type == 2){
		cleanpar();
	}
	var url="./searchGoodsManageList.do"+urlParams(1)+"&auditStatus=2";
	var cfg = {
			url :url,
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('goodsUpperId', cfg);
}


/**
 * 原料一级变动，级联原料二级
 */
function  changeSecondMaterial(){
	$.ajax({
		type : 'POST',
		url : './getchangeSecondMaterial.do',
		data : {
			rawMaterialPk : $("#rawMaterialParentPk").val()
		},
		dataType : 'json',
		success : function(data) {
			$("#rawSecondMaterialPk").empty();
			$("#rawSecondMaterialPk").append("<option value=''>--请选择--</option>");
			if (data) {
				for ( var i = 0; i < data.length; i++) {
					$("#rawSecondMaterialPk").append(
							"<option value='" + data[i].pk + "'>"
									+ data[i].name + "</option>");
				}
			}
			$("#rawSecondMaterialPk").selectpicker('refresh');//
		}
	});
}

/**
 * 导出商品信息
 */
function excel() {
    $.ajax({
        type : 'POST',
        url : './exportSxGoodsList.do' + urlParams(1),
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