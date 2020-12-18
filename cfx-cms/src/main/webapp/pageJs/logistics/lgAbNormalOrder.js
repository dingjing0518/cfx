
function changeCity(self){
	var pk = $(self).val();
	$("#city").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#area").empty().append("<option value=''>区</option>");	
}

function changeArea(self){
	$("#area").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
	$("#town").empty().append("<option value=''>镇</option>");
}

function changeTown(self){
	$("#town").empty().append("<option value=''>镇</option>").append(getRegions($(self).val()));	
}

function changeToCity(self){
	var pk = $(self).val();
	$("#toCity").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#toArea").empty().append("<option value=''>区</option>");	
}

function changeToArea(self){
	$("#toArea").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
	$("#toTown").empty().append("<option value=''>镇</option>");
}

function changeToTown(self){
	$("#toTown").empty().append("<option value=''>镇</option>").append(getRegions($(self).val()));	
}

function getRegions(pk){
	var html = "";
	$.ajax({
		type : 'GET',
		url : "./getRegions.do",
		data : {parentPk:pk},
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data){
				for ( var i = 0; i < data.length; i++) {
					html +="<option value='"+data[i].pk+"'>"+data[i].name+"</option>";
				}
			}
		}
	});
	return html;
}

$(function() {
	var cfg = {
			url : './lgAbOrder_data.do',
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	
	createDataGrid('orderId', cfg);

	});

	var toolbar = [];
	var columns = [ 
	{
		title : '操作',
		field : 'pk',
		formatter : function(value, row, index) {
			var str = "";
				if(row.isConfirmed==2){
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
						+'<button type="button" data-toggle="modal" data-target="#feedbackOrderId"  style="display:none;" showname="LG_ORDER_ORDERERROR_BTN_CONFIRM" class="btn btn-warning" onclick="javascript:feedbackOrder(\''
						+ row.pk+ '\',1);">确认异常</button></a>';
				}else{
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
						+'<button type="button" data-toggle="modal" data-target="#feedbackOrderId" style="display:none;" showname="LG_ORDER_ORDERERROR_BTN_DETAIL" class="btn btn btn-default" onclick="javascript:feedbackOrder(\''
							+ row.pk+ '\',2);">订单详情</button></a>';
				}
			return str;
		}
	},               
	 {
		title : '订单号',
		field : 'orderPk',
		sortable : true
	},
	{
		title : '订单时间',
		field : 'orderTime',
		sortable : true
	}, {
		title : '物流供应商',
		field : 'logisticsCompanyName',
	},{
		title : '要求配送时间',
		field : 'arrivedTime',
	}, {
		title : '商品信息',
		field : 'goodsName',
	},
	{
		title : '采购量（箱）',
		field : 'boxes',
	},{
		title : '重量（吨）',
		field : 'weight',
	},{
		title : '运费单价（元）',
		field : 'originalFreight',
		
	},{
		title : '运费总价（元）',
		field : 'originalTotalFreight',
	},
	{
		title : '订单状态',
		field : 'isConfirmed',
		width : 20,
		sortable : true,
		formatter : function(value, row, index) {
			if(value==1){
				return "异常订单-已确认";
			}else if(value ==2){
				return "异常订单-未确认";
			}else{
				return "";
			}
		}
	},
	{
		title : '提货公司抬头',
		field : 'supplierName',
		width : 20,
		formatter : function(value, row, index) {
			if (row.supplierName == "" ||row.supplierName==null) {
				return row.fromCompanyName ;
			} else {
				return row.supplierName;
			}
		}
	}, {
		title : '提货地址',
		field : 'fromAddress',
		width : 20
	},{
		title : '提货联系人',
		field : 'fromContacts',
		width : 20
	},{
		title : '提货手机号',
		field : 'fromContactsTel',
		width : 20
	},{
		title : '收货公司抬头',
		field : 'toCompanyName',
		width : 20
	},{
		title : '配送地址',
		field : 'toAddress',
		width : 20
	},{
		title : '配送联系人',
		field : 'toContacts',
		width : 20
	},{
		title : '配送手机号',
		field : 'toContactsTel',
		width : 20
}];


function byIsConfirmed(s){
	$("#isConfirmed").val(s);
	var cfg = {
			url : './lgAbOrder_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('orderId', cfg);
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
		cleanDate();
	}
	var cfg = {
			url : './lgAbOrder_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('orderId', cfg);
}


function excelOrder() {
	$.ajax({
		type : "POST",
		url : './exportLgAbOrderForm.do'+urlParams(1),
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});

}


function feedbackOrder(pk,sign){
	$("#trackDetailfb").html("");
	//是否确认异常
	if(sign==1){
		$("#queren").show();	
	}else{
		$("#queren").hide();	
	}
	//清空图片
	$("#imgs_div").html("");
	$.ajax({
		type : "POST",
		url : './lgOrderFeedBackDetail.do',
		data : {
			pk:pk
		},
		dataType : "json",
		success : function(data) {
			$("#deliveryId").val(pk);
			$("#orderInfofb").html("订单编号："+checkNull(data.mainData.deliveryNumber)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 订单金额："+checkNum(data.mainData.originalTotalFreight)
					+"元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 交易状态: "+checkNull(data.mainData.orderStatusName)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 支付方式："+checkNull(data.mainData.paymentName));
			if (checkNull(data.mainData.supplierName)== "") {
				$("#fromCompanyNamefb").html("提货公司：" + checkNull(data.mainData.fromCompanyName));
			} else {
				$("#fromCompanyNamefb").html("提货公司：" + checkNull(data.mainData.supplierName));
			}
			
			$("#fromAddressfb").html("提货地址："+checkNull(data.mainData.fromAddress));
			$("#fromContactsfb").html("联系人："+checkNull(data.mainData.fromContacts));
			$("#fromContactsTelfb").html("手机号码："+checkNull(data.mainData.fromContactsTel));
			$("#toCompanyNamefb").html("收货公司："+checkNull(data.mainData.toCompanyName));
			$("#toAddressfb").html("收货地址："+checkNull(data.mainData.toAddress));
			$("#toContactsfb").html("联系人："+checkNull(data.mainData.toContacts));
			$("#toContactsTelfb").html("手机号码："+checkNull(data.mainData.toContactsTel));
			$("#goodsNamefb").html(checkNull(data.mainData.productName)+
					checkNull(data.mainData.varietiesName)+ checkNull(data.mainData.seedvarietyName)+
					checkNull(data.mainData.specName)+checkNull(data.mainData.seriesName)+ 
					checkNull( data.mainData.gradeName)+checkNull(data.mainData.batchNumber));
			 $("#boxesfb").html(checkNum(data.mainData.boxes)+"（"+checkNum(data.mainData.unit)+"）");
			 $("#weightfb").html(checkNum(data.mainData.weight));
			 $("#presentFreightfb").html(checkNum(data.mainData.originalFreight));
			 $("#presentTotalFreightfb").html(checkNum(data.mainData.originalTotalFreight)); 
			 $("#remark").html(checkNull(data.mainData.remark)); 
			if (data.trackDetail.length > 0) {
					var content = "";
					for (var i = 0; i < data.trackDetail.length; i++) {
						content = content + "<span>"
								+ checkNull(data.trackDetail[i].orderStatusName)
								+ "&nbsp;&nbsp;&nbsp;&nbsp;"
								+ checkNull(data.trackDetail[i].insertTime)
								+ "&nbsp;&nbsp;&nbsp;&nbsp;"
								+checkNull( data.trackDetail[i].stepDetail) + "<span>"
					}
				}
				
				$("#trackDetailfb").append(content);
			 
			 $("#abnormalRemark").html(checkNull(data.mainData.abnormalRemark));
			 	var content="";
				if(data.feedBackPic.length>0){
				for(var i = 0 ; i< data.feedBackPic.length ; i++){
					content  = content+"<div style='position: relative;display: inline-block;margin-bottom:10px;'><a class='fancybox' href='"+data.feedBackPic[i].exceptionPicUrl+"'>"+
			   		"<img class='imgC' src='"+data.feedBackPic[i].exceptionPicUrl+"'>"+
					"</a></div>";
				}
			}
			  $("#imgs_div").append(content);
			  $(".fancybox").fancybox();
		}
	});
}




function sureFeedback(){
	$.ajax({
		type : "POST",
		url : './sureFeedback.do',
		data : {
			pk:$("#deliveryId").val()
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({ 
				modal : false,
				content : data.msg
			}); 
			if (data.success) {
				$('#feedbackOrderId').modal('hide');
				$('#orderId').bootstrapTable('refresh');
				}
			}
		});
}




