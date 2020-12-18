 $(function() {
	var cfg = {
			url : './supplierSettleAccount_data.do',
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	
	createDataGrid('settleAcountId', cfg);
});

var toolbar = [];
var columns = [ 
{
	title : '操作',
	field : 'pk',
	formatter : function(value, row, index) {
		var str = "";
			if(row.isAbnormal==1 && row.orderStatus ==3 && row.isSettleFreight== 1){
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
					+'<button type="button" style="display:none;" showname="LG_FC_SETTLEMENT_BTN_SETTLEMENT" class="btn btn-warning" onclick="javascript:sureSettleFreight(\''
					+ row.pk+ '\');">结算</button></a>';
			}
			
			if(row.isAbnormal==2 && row.orderStatus ==3 && row.isSettleFreight== 1){
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
					+'<button type="button" style="display:none;" showname="LG_FC_SETTLEMENT_BTN_SETTLEMENT" class="btn btn-warning" onclick="javascript:sureSettleFreight(\''
					+ row.pk+ '\');">结算</button></a>';
			}
		return str;
	}
},{
	title : '供应商名称',
	field : 'logisticsCompanyName',
},{
	title : '联系人',
	field : 'logisticsCompanyContacts',
},{
	title : '联系电话',
	field : 'logisticsCompanyContactsTel',
},{
	title : '订单编号',
	field : 'orderPk',
},{
	title : '订单时间',
	field : 'orderTime',
	sortable : true
},{
	title : '订单状态',
	field : 'orderStatusName',
},{
	title : '重量（吨）',
	field : 'weight',
},{
	title : '结算单价（元）',
	field : 'presentFreight',
	formatter : function(value, row, index) {
		if(value==null||value==""){
			return 0;
		}else{
			return value;
		}
	}
},{
	title : '结算状态',
	field : 'isSettleFreight',
	formatter : function(value, row, index) {
		if(value==1){
			return "未结算";
		}else if(value==2){
			return "已结算";
		}else{
			return "";
		}
	}
},
{
	title : '异常状态',
	field : 'isAbnormalName',
},{
	title : '结算金额',
	field : 'presentTotalFreight',
}];



function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
		cleanDate();
	}
	var sta = $("#isSettleFreight").val();
	//设置对应的选项卡
	$('.nav-tabs li').removeClass('active');
	$('.nav-tabs li').eq(sta).addClass('active');
	
	var cfg = {
			url : './supplierSettleAccount_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('settleAcountId', cfg);
}

 /**
  * 结算状态Table切换
  * @param s
  */
 function  findSettleAccount(s){
	if(s==""){
		$("#isSettleFreight").val(s);
		$("#isAbnormal").val(s);
	}
	if(s==1||s==2){
		$("#isSettleFreight").val(s);
		$("#isAbnormal").val(1);
	}
	if(s==3){
		$("#isSettleFreight").val('');
		$("#isAbnormal").val(2);
	}
	$("button[data-id=isSettleFreight] span.filter-option").html($("#isSettleFreight  option:selected").text()); 
	var cfg = {
			url : './supplierSettleAccount_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('settleAcountId', cfg);
}


 /**
  * 结算
  * @param pk
  */
 function sureSettleFreight(pk){
		var confirm = new $.flavr({
			content : '确定该订单已结算吗?',
			dialog : 'confirm',
			onConfirm : function() {
				confirm.close();
				$.ajax({
					type : 'POST',
					url : './updateSettleFreight.do',
					data : {
						'pk' : pk,
						'isSettleFreight': 2,	
					},
					dataType : 'json',
					success : function(data) {
						new $.flavr({
							modal : false,
							content : data.msg
						}); /* Closing the dialog */
						if (data.success) {
							//$("#quxiao").click();
							$('#settleAcountId').bootstrapTable('refresh');
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
  * 导出数据
  */
 function exportSettle() {
		$.ajax({
			type : "POST",
			url : './exportSettleForm.do'+urlParams(1),
			dataType : "json",
			data : {},
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
			}
		});
}
