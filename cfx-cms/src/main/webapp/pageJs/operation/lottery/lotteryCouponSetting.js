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
						//删除按钮
						if(row.isShowDelete == 1 && row.isShowEdit == 1){
							str += '<button type="button" onclick="javascript:del(\'' + value + '\'); " style="display:none;" showname="BTN_DELETECOUPONSETTING" class="btn btn-danger active">删除</button>';
						}
						//启用（禁用）按钮
						if(row.isVisable == 2){
							str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); " style="display:none;" showname="BTN_ISVISABLECOUPONSETTING" class="btn btn-primary active">启用</button>';
						}else{
							str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); " style="display:none;" showname="BTN_ISVISABLECOUPONSETTING" class="btn btn-primary active">禁用</button>';
						}
						//编辑按钮
						if(row.isShowEdit == 1){
								str += '<button type="button" onclick="javascript:editLotteryCoupon(\'' + value + '\'); " style="display:none;" showname="BTN_EDITCOUPONSETTING" class="btn btn-primary active">编辑</button>';
						}
						return str;
					}
				}, 
				{
					title : '优惠券种类',
					field : 'couponType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "免息券";
						}else{
							return "";
						}
					}
				},
				{
					title : '金额(元)',
					field : 'couponAmount',
					width : 20,
					sortable : true
				},
				{
					title : '优惠券名称',
					field : 'name',
					width : 20,
					sortable : true
				},
				{
					title : '使用方式',
					field : 'useType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "金融白条支付";
						} else{
							return "";
						}
		
					}
		
				}, {
					title : '有效期',
					field : 'intervalTime',
					width : 30,
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
	url : './searchLotteryCouponList.do',
	columns : columns,
	uniqueId : 'pk',
	orderName : 'insertTime',
	orderType : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateLotteryCoupon.do',
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
						$('#lotteryCouponTableId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}

function isVisable(pk,isVisable) {

			$.ajax({
				type : 'POST',
				url : './updateLotteryCoupon.do',
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
			url : './searchLotteryCouponList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			orderName : 'insertTime',
			orderType : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('lotteryCouponTableId', cfg);
}
function insertLotteryCoupon(){
	$('#editLotteryCouponId').modal();
	$("#couponType").val("");	
	$("#couponAmount").val("");	
	$("#name").val("");
	$("#useType").val("");	
	$("#startTime").val("");
	$("#endTime").val("");
}

function editLotteryCoupon(pk){
	$('#editLotteryCouponId').modal();
	$.ajax({
		type : "POST",
		url : './getLotteryCouponByPk.do',
		data : {
			pk : pk
		},
		dataType : "json",
		success : function(data) {
			if(data != null){
				$("#pk").val(pk);
				$("#name").val(data.name);
				$("#couponAmount").val(data.couponAmount);
				$("#startTime").val(data.startTime);
				$("#endTime").val(data.endTime);
			
				var htmlCouponType ="<option value=\"\">--请选择--</option>";
				for(var i = 0;i < coupon_type.length;i++){
					if(coupon_type[i].key == data.couponType){
						htmlCouponType +="<option value="+coupon_type[i].key+" selected>"+coupon_type[i].value+"</option>";
					}else{
						htmlCouponType +="<option value="+coupon_type[i].key+">"+coupon_type[i].value+"</option>";
					}
				}
				$("#couponType").html(htmlCouponType);
				
				var htmlUseType ="<option value=\"\">--请选择--</option>";
				for(var i = 0;i < use_type.length;i++){
					if(use_type[i].key == data.couponType){
						htmlUseType +="<option value="+use_type[i].key+" selected>"+use_type[i].value+"</option>";
					}else{
						htmlUseType +="<option value="+use_type[i].key+">"+use_type[i].value+"</option>";
					}
				}
				$("#useType").html(htmlUseType);
			}
		}
	});
}

function submit(){
	var couponType = $("#couponType").val();	
	var couponAmount = $("#couponAmount").val();	
	var name = $("#name").val();
	var useType = $("#useType").val();	
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(valid("#dataForm")){
		
		var rex = /^(\+)?\d{1,10}(\.\d{1,2})?$/;
		if(!rex.test(couponAmount) || couponAmount == 0){
			new $.flavr({
				modal : false,
				content : "输入的金额必须大于零！"
			});
			return;
		}
		
		if(startTime == undefined || startTime == ""){
			new $.flavr({
				modal : false,
				content : "开始时间不能为空！"
			});
			return;
		}
		
		if(endTime == undefined || endTime == ""){
			new $.flavr({
				modal : false,
				content : "结束时间不能为空！"
			});
			return;
		}
		if(endTime < startTime){
			new $.flavr({
				modal : false,
				content : "优惠券有效期开始时间不能大于结束时间"
			});
			return;
		}
		
		$.ajax({
			type : "POST",
			url : './updateLotteryCoupon.do',
			data : {
				pk : $("#pk").val(),
				couponType :couponType, 
				couponAmount :couponAmount, 
				useType :useType, 
				name :name, 
				startTimeStr :startTime, 
				endTimeStr :endTime
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




