$(function() {
	createDataGrid('lotteryCouponTableId', cfg);
});



var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 80,
					formatter : function(value, row, index) {
						var str = "";
							str += '<button type="button" onclick="javascript:del(\'' + value + '\'); " style="display:none;" showname="OPER_ACTIVITY_MATERIAL_BTN_DEL" class="btn btn-danger active">删除</button>';
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); " style="display:none;" showname="OPER_ACTIVITY_MATERIAL_BTN_ENABLE" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); " style="display:none;" showname="OPER_ACTIVITY_MATERIAL_BTN_DISABLE" class="btn btn-primary active">禁用</button>';
							}
							str += '<button type="button" onclick="javascript:editLotteryMaterial(\'' + value + '\',\'' + row.name + '\'); " style="display:none;" showname="OPER_ACTIVITY_MATERIAL_BTN_EDIT" class="btn btn-primary active">编辑</button>';
						return str;
					}
				}, 
				{
					title : '实物名称',
					field : 'name',
					width : 20,
					sortable : true
				},
				{
					title : '添加时间',
					field : 'insertTime',
					width : 20,
					sortable : true
				},
				{
					title : '启用/禁用',
					field : 'isVisable',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "是";
						} else if(value == 2){
							return "否";
						}
		
					}
		
				}];
var cfg = {
	url : './searchlotteryMaterialList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 删除实物奖品
 * @param pk
 */
function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateLotteryMaterial.do',
				data : {
					 pk : pk,
					 isDelete  : 2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
                        bootstrapTableRefresh("lotteryCouponTableId");
						//$('#lotteryCouponTableId').bootstrapTable('refresh');
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
 * 启用、禁用
 * @param pk
 * @param isVisable
 */
function isVisable(pk,isVisable) {
			$.ajax({
				type : 'POST',
				url : './updateLotteryMaterial.do',
				data : {
					 pk : pk,
					 isVisable : isVisable
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#lotteryCouponTableId').bootstrapTable('refresh');
					}

				}
			});
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchlotteryMaterialList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('lotteryCouponTableId', cfg);
}

/**
 * 新增实物奖品
 */
function insertLotteryMaterial(){
	$('#editLotteryMaterialId').modal();
	$("#name").val("");	
	$("#pk").val("");	
}

/**
 * 编辑实物奖品
 * @param pk
 * @param name
 */
function editLotteryMaterial(pk,name){
	
	$('#editLotteryMaterialId').modal();
	$('#pk').val(pk);
	if(name != undefined ){
		$('#name').val(name);
	}
}

/**
 * 保存实物奖品
 */
function submit(){

	var name = $("#name").val();
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : './updateLotteryMaterial.do',
			data : {
				pk : $("#pk").val(),
				name :name, 
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#lotteryCouponTableId').bootstrapTable('refresh');
				}
			}
		});
		}
}




