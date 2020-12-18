$(function() {
	var cfg = {
			url : './lgGoodsLocation.do' + urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('logisticsTrackingId', cfg);
});

function changeCity(self){
	var pk = $(self).val();
	$("#cityPk").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#areaPk").empty().append("<option value=''>区</option>");	
}

function changeArea(self){
	$("#areaPk").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
}

/**
 * 根据pK查询地区集合
 * @param pk
 * @returns {string}
 */
function getRegions(pk) {
	var html = "";
	$.ajax({
		type : 'GET',
		url : "./searchSysRegionsList.do",
		data : {
			parentPk : pk
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data) {
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].pk + "'>"
							+ data[i].name + "</option>";
				}
			}
		}
	});
	return html;
}





var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			formatter : function(value, row, index) {
				var str = "";
				str += '<button id="editable-sample_new" style="display:none;" showname="LG_ORDER_NORMAL_BTN_GOODSLOC_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:edit(\''
						+ value + '\',\'' + row.provincePk + '\',\'' + row.provinceName + '\',\'' + row.cityPk + '\',\'' + row.cityName + '\',\'' + row.areaPk + '\',\'' + row.areaName + '\',\'' + row.arrivalTime + '\',\'' +row.meno+ '\',\'' + row.orderPk + '\'); ">编辑</button>';
				str += '<button type="button" style="display:none;" showname="LG_ORDER_NORMAL_BTN_GOODSLOC_DEL" onclick="javascript:delPk(\''
						+ value
						+ '\'); " class="btn btn-danger active">删除</button>';
				return str;
			}
		}, 
		{
			title : '时间',
			field : 'updateTime',
		},{
			title : '货物所在地',
			field : 'provinceName',
			formatter : function(value, row, index) {
				return row.provinceName+row.cityName+row.areaName;
			}
		} , {
			
			
			title : '预计送达时间',
			field : 'arrivalTime',
		}, {
			title : '备注',
			field : 'meno',
		} ];


/**
 * 删除
 * @param pk
 */
function delPk(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './delLgLogisticsTrack.do',
				data : {
					'pk' : pk
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); 
					if (data.success) {
						$('#logisticsTrackingId').bootstrapTable('refresh');
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
 * 新增
 */
function add() {
	clean();
	$("#myModalLabel").html("新增货物所在地");
	$('#editGoodsLocationId').modal();
}

/**
 * 编辑
 * @param pk
 * @param provincePk
 * @param provinceName
 * @param cityPk
 * @param cityName
 * @param areaPk
 * @param areaName
 * @param arrivalTime
 * @param meno
 * @param orderPk
 */
function edit(pk,provincePk,provinceName,cityPk,cityName,areaPk,areaName,arrivalTime,meno,orderPk){
	clean();
	$("#myModalLabel").html("编辑货物所在地");
	$("#pk").val(pk);
	$("#provincePk").val(provincePk);
	$("#cityPk").empty().append("<option value=''>市</option>").append(getRegions(provincePk));
	$("#cityPk").val(cityPk);
	$("#areaPk").empty().append("<option value=''>区</option>").append(getRegions(cityPk));
	$("#areaPk").val(areaPk);
	$("#arrivalTime").val(arrivalTime);
	$("#meno").val(ClearBr(meno));
	$('#editGoodsLocationId').modal();
}

/**
 * 清除条件查询
 */
function  clean(){
	$("#dataForm").find("input,select,textarea").each(function(){
		$(this).val("");
	});
}

/**
 * 保存货物所在地
 */
function submit() {
	 if($("#provincePk").val()==""||$("#cityPk").val()==""||$("#areaPk").val()==""){
			new $.flavr({
				modal : false,
				content : "货物所在地省市区不能为空！"
			});
			return;
	}
	 if($("#arrivalTime").val()==""){
			new $.flavr({
				modal : false,
				content : "预计达到时间不能为空！"
			});
			return;
	}
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : './saveLgLogisticsTrack.do',
			data : {
				pk : $("#pk").val(),
				orderPk : $("#orderPk").val(),
				provincePk :$("#provincePk").val(),
				provinceName: $("#provincePk").val()==""?"":$("#provincePk option:selected").text(),
				cityPk :$("#cityPk").val(),
				cityName: $("#cityPk").val()==""?"":$("#cityPk option:selected").text(),
				areaPk :$("#areaPk").val(),
				areaName: $("#areaPk").val()==""?"": $("#areaPk option:selected").text(),
				arrivalTime:$("#arrivalTime").val(),
				meno:ClearBr($("#meno").val())
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); 
				if (data.success) {
					$("#quxiao").click();
					$('#logisticsTrackingId').bootstrapTable('refresh');
				} 
			}
		});	
	};	
}

/**
 * 跳转到物流正常订单
 */
function  openLgNormalOrder(){
	window.location = getRootPath() + "/lgNormalOrder.do";
}

/**
 * 输入框清空
 * @param key
 * @returns {*}
 * @constructor
 */
function ClearBr(key) { 
	if(key != null && key !="" && key !='undefined' && key !=undefined){
		key = key.replace(/<\/?.+?>/g,""); 
		key = key.replace(/[\r\n]/g, ""); 
		return key; 
	}else{
		return "";
	}
	
	}