$(function() {
	createDataGrid('lotteryCouponMemberTableId', cfg);
});

var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 80,
					formatter : function(value, row, index) {
						var str = "";
							str += '<button type="button" onclick="javascript:showCouponMemberDetail(\'' + value + '\',\'' + row.backAmount + '\',1); " style="display:none;" showname="BTN_SHOWCOUPONMEMBER" class="btn btn-warning">查看详情</button>';
							//使用状态是赠送中并且来源是抽奖来源
							if(row.status == 3 && row.source == 1){
								str += '<button type="button" onclick="javascript:giveCoupon(\'' + value + '\'); " style="display:none;" showname="BTN_GIVECOUPON" class="btn btn-primary active">审核</button>';
							}
							//返款，当借贷状态是"已还款"，优惠券使用状态是"已使用"并且已申请返款"已申请"的，还未进行返款操作的可以进行返款
							if(row.loanStatus == 5 && row.status == 2 && row.applyStatus == 2 && row.backStatus != 2){
								str += '<button type="button" onclick="javascript:showCouponMemberDetail(\'' + value + '\',\'' + row.backAmount + '\',2); " style="display:none;" showname="BTN_BACKAMOUNT" class="btn btn-primary active">返款</button>';
							}
						return str;
					}
				},{
					title : '优惠券种类',
					field : 'couponType',
					width : 30,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "免息券";
						} else{
							return "";
						}
					}
				}, 
				 {
					title : '发放时间',
					field : 'insertTime',
					width : 30,
					sortable : true
				}, 
				{
					title : '来源',
					field : 'source',
					width : 30,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "抽奖";
						} else if (value == 2){
							return "赠送";
						}else {
							return "";
						}
					}
				},
				{
					title : '优惠券金额（元）',
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
					title : '券编码',
					field : 'couponNumber',
					width : 20,
					sortable : true
				}, 
				{
					title : '获券人',
					field : 'memberName',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (row.status == 4) {
							return row.omemberName;
						} else{
							return value;
						}
					}
				},
				{
					title : '注册电话',
					field : 'contactTel',
					width : 20,
					sortable : true
				},
				{
					title : '公司名称',
					field : 'companyName',
					width : 20,
					sortable : true
				},
				{
					title : '使用状态',
					field : 'status',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 0) {
							return "新增";
						} else if (value == 1) {
							return "未使用";
						} else  if(value==2){
							return "已使用";
						}else  if(value==3){
							return "赠送中";
						}else  if(value==4){
							return "已赠送";
						}else  if(value==5){
							return "已过期";
						}else{
							return "无";
						}
					}
				},
				{
					title : '使用券的订单号',
					field : 'orderNumber',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == null || value == "" ) {
							return "无";
						} else {
							return value;
						}
		
					}
				},
				{
					title : '申请返款',
					field : 'applyStatus',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "未申请";
						} else  if(value==2){
							return "已申请";
						}else  if(value==3){
							return "已确认";
						}else{
							return "无";
						}
					}
		
				},
				{
					title : '借贷状态',
					field : 'loanStatus',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "待申请";
						} else  if(value==2){
							return "申请中";
						}else  if(value==3){
							return "申请成功";
						}else  if(value==4){
							return "申请失败";
						}else  if(value==5){
							return "已还款";
						}else  if(value==6){
							return "部分还款";
						}else{
							return "无";
						}
		
					}
		
				},
				{
					title : '还款利息',
					field : 'interest',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == null) {
							return "无";
						} else {
							return value;
						}
		
					}
				},
				{
					title : '返款金额',
					field : 'backAmount',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == null) {
							return "无";
						} else {
							return value;
						}
		
					}
				},
				{
					title : '返款状态',
					field : 'backStatus',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "未返款";
						} else  if(value==2){
							return "已返款";
						}else{
							return "无";
						}
					}
		
				}];
var cfg = {
	url : './searchLotteryCouponMemberList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};
function findCouponMember(s){
	if(s!=0){
		$("#isStatus").val(s);
	}else{
		$("#isStatus").val('');
	}
	var cfg = {
			url : './searchLotteryCouponMemberList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('lotteryCouponMemberTableId', cfg);
}

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchLotteryCouponMemberList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('lotteryCouponMemberTableId', cfg);
}

//查看详情
function auditCouponMember(pk){
	$("#editLotteryInvitationRecordId").modal();
	$("#pk").val(pk)
	$("#editStatus").val("")
}

function submitPutCoupon(){
	var pk = $("#pk").val();
	var editStatus = $("#editStatus").val();
	
	if(valid("#dataPutForm")){
		$.ajax({
			type : "POST",
			url : './updateLotteryCouponMember.do',
			data : {
				pk : pk,
				status :editStatus
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#lotteryCouponMemberTableId').bootstrapTable('refresh');
				}
			}
		});
		}
}

//返款
function creditAmount(pk,province,provinceName,city,cityName,area,areaName) {
	
	$('#editLotteryInvitationRecordId').modal();	
	$("#pk").val(pk);
	var html = "";
	//获取省市区默认选中
	$.ajax({
		type : 'GET',
		url : "./searchSysRegionsList.do",
		data : {parentPk:-1},
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data){
				for ( var i = 0; i < data.length; i++) {
					if(data[i].pk == province){
						html +="<option value='"+data[i].pk+"' selected>"+data[i].name+"</option>";
					}else{
						html +="<option value='"+data[i].pk+"'>"+data[i].name+"</option>";
					}
				}
			}
		}
	});
	$("#province").empty().append(html);
	if(cityName !="undefined"){
		$("#city").empty().append("<option value='"+city+"'>"+cityName+"</option>");
	}
	if(areaName !="undefined"){
		$("#area").empty().append("<option value='"+area+"'>"+areaName+"</option>");
	}
	$("#awardType").val("");
	$("#awardStatus").val("");
	$("#mname").val("");
	$("#address").val("");
	$("#mphone").val("");
	$("#note").val("");
	
	var parm =  {
			'pk' : pk
		};
	$.ajax({
		type : 'POST',
		url : './getInvitationRecordBypk.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			if(data != null){
				$("#mname").val(data.mname);
				$("#address").val(data.address);
				$("#mphone").val(data.mphone);
				$("#note").val(data.note);
			}
		}
	});
}

//审核
function giveCoupon(pk){
	$("#editGiveCouponInfoId").modal();
	
	$("#giveorderNumber").val("");
	$("#givecouponNumber").val("");
	$("#giveorderStatus").val("");
	$("#giveloanStatus").val("");
	
	$("#givemmobile").val("");
	$("#givememberName").val("");
	
	$.ajax({
		type : 'POST',
		url : './getLotteryCouponMemberByPk.do',
		data : {pk:pk},
		dataType : 'json',
		success : function(data) {
			if(data != null){
				var html = "<option value=\"\">--请选择--</option>";
				var noStatus = "<option value='' selected >无</option>";
				$("#givememberName").val(data.acceptMemberName);
				$("#givemmobile").val(data.acceptMobile);
				$("#givememberPk").val(data.acceptMemberPk);
				$("#parentPk").val(data.pk);//原持券人Pk
				
				//优惠券使用状态
				for ( var i = 0; i < lottery_coupon_status.length; i++) {
					//if(data.orderStatus != undefined){
						if(lottery_coupon_status[i].key == data.status){
							html +="<option value='"+lottery_coupon_status[i].key+"' selected >"+lottery_coupon_status[i].value+"</option>";
						}else{
							html +="<option value='"+lottery_coupon_status[i].key+"'>"+lottery_coupon_status[i].value+"</option>";
						}
//					}else{
//						html = noStatus;
//					}
				}
				
				$("#giveStatus").html(html);
				$("#giveStatus").selectpicker('refresh');
				
				//订单状态
				html = "<option value=\"\">--请选择--</option>";
				
				for ( var i = 0; i < order_status.length; i++) {
					if(data.orderStatus != undefined){
						if(order_status[i].key == data.orderStatus){
							html +="<option value='"+order_status[i].key+"' selected >"+order_status[i].value+"</option>";
						}else{
							html +="<option value='"+order_status[i].key+"'>"+order_status[i].value+"</option>";
						}
					}else{
						html = noStatus;
					}
					
				}
				$("#giveorderStatus").html(html);
				$("#giveorderStatus").selectpicker('refresh');
				
				//借贷状态
				html = "<option value=\"\">--请选择--</option>";
				for ( var i = 0; i < order_loan_status.length; i++) {
					if(data.loanStatus != undefined){
						if(order_loan_status[i].key == data.loanStatus){
							html +="<option value='"+order_loan_status[i].key+"' selected >"+order_loan_status[i].value+"</option>";
						}else{
							html +="<option value='"+order_loan_status[i].key+"'>"+order_loan_status[i].value+"</option>";
						}
					}else{
						html = noStatus;
					}
				}
				$("#giveloanStatus").html(html);
				$("#giveloanStatus").selectpicker('refresh');
				//获取公司列表
				
				setTimeout(function(){//延迟一秒  
					$.ajax({
						type : 'GET',
						url : "./getCouponB2bCompanyDto.do",
						data : {},
						dataType : 'json',
						async : true,
						success : function(dataCompany) {
						html = "<option value=\"\">--请选择--</option>";
						html += "<option value=\"\" selected>"+data.acceptCompanyName == 'undefind'?"":data.acceptCompanyName+"</option>";
						if(dataCompany.length>0){
							 for(var i =0;i<dataCompany.length;i++){
								 if(dataCompany[i].name == data.acceptCompanyName){
									 html = "<option value=\"\">--请选择--</option>";
									 html +="<option value="+dataCompany[i].pk+" selected>"+dataCompany[i].name+"</option>";
								 }else{
									 html +="<option value="+dataCompany[i].pk+">"+dataCompany[i].name+"</option>"; 
								 }
							}
							 $("#givecompanyName").html(html);
							 $("#givecompanyName").selectpicker('refresh');
							}
						}
					});
                 },500);
			}
		}
	});
}

function submitgiveCoupon(){
	var parentPk = $("#parentPk").val();
	
	var acceptCompanyPk = $("#givecompanyName").val();
	var acceptCompanyName = $("#givecompanyName option:selected").text();
	
	var acceptMemberPk = $("#givememberPk").val();
	var acceptMemberName = $("#givememberName").val();
	
	var acceptMobile = $("#givemmobile").val();

	var params = {
			parentPk:parentPk,
			acceptCompanyPk:acceptCompanyPk,
			acceptCompanyName:acceptCompanyName,
			acceptMemberPk:acceptMemberPk,
			acceptMemberName:acceptMemberName,
			acceptMobile:acceptMobile	
	};
	$.ajax({
		type : 'GET',
		url : "./updateGiveLotteryCouponMember.do",
		data : params,
		dataType : 'json',
		async : true,
		success : function(data) {
			
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiaogiveCoupon").click();
			$('#lotteryCouponMemberTableId').bootstrapTable('refresh');
		}
	});
}

function showCouponMemberDetail(pk,backAmount,status){
	$("#showLotteryCouponMemberDetailId").modal();
	isEmpty(pk,status);
	$.ajax({
		type : 'POST',
		url : './getLotteryCouponMemberByPk.do',
		data : {pk:pk},
		dataType : 'json',
		success : function(data) {
			if(data != null){
				var html = "<option value=\"\">--请选择--</option>";
				var noStatus = "<option value='' selected >无</option>";
				getCouponMember(data,backAmount,status);
				isSelect(data,html,noStatus);
			}
		}
	});
}


function isSelect(data,html,noStatus){
	//优惠券使用状态
	for ( var i = 0; i < lottery_coupon_status.length; i++) {
		//if(data.orderStatus != undefined){
			if(lottery_coupon_status[i].key == data.status){
				html +="<option value='"+lottery_coupon_status[i].key+"' selected >"+lottery_coupon_status[i].value+"</option>";
			}else{
				html +="<option value='"+lottery_coupon_status[i].key+"'>"+lottery_coupon_status[i].value+"</option>";
			}
//		}else{
//			html = noStatus;
//		}
	}
	$("#detailStatus").html(html);
	$("#detailStatus").selectpicker('refresh');
	
	//订单状态
	html = "<option value=\"\">--请选择--</option>";
	for ( var i = 0; i < order_status.length; i++) {
		if(data.orderStatus != undefined){
			if(order_status[i].key == data.orderStatus){
				html +="<option value='"+order_status[i].key+"' selected >"+order_status[i].value+"</option>";
			}else{
				html +="<option value='"+order_status[i].key+"'>"+order_status[i].value+"</option>";
			}
		}else{
			html = noStatus;
		}
	}
	$("#detailorderStatus").html(html);
	$("#detailorderStatus").selectpicker('refresh');
	
	//返款状态
	html = "<option value=\"\">--请选择--</option>";
	for ( var i = 0; i < lottery_back_status.length; i++) {
		if(data.backStatus != undefined){
			if(lottery_back_status[i].key == data.backStatus){
				html +="<option value='"+lottery_back_status[i].key+"' selected >"+lottery_back_status[i].value+"</option>";
			}else{
				html +="<option value='"+lottery_back_status[i].key+"'>"+lottery_back_status[i].value+"</option>";
			}
		}else{
			html = noStatus;
		}
	}
	$("#detailbackStatus").html(html);
	$("#detailbackStatus").selectpicker('refresh');
	
	
	//申请返款
	html = "<option value=\"\">--请选择--</option>";
	for ( var i = 0; i < lottery_apply_Status.length; i++) {
		if(data.applyStatus != undefined){
			if(lottery_apply_Status[i].key == data.applyStatus){
				html +="<option value='"+lottery_apply_Status[i].key+"' selected >"+lottery_apply_Status[i].value+"</option>";
			}else{
				html +="<option value='"+lottery_apply_Status[i].key+"'>"+lottery_apply_Status[i].value+"</option>";
			}
		}else{
			html = noStatus;
		}
	}
	$("#detailapplyStatus").html(html);
	$("#detailapplyStatus").selectpicker('refresh');
	
	//返款方式
	html = "<option value=\"\">--请选择--</option>";
	
	for ( var i = 0; i < lottery_back_type.length; i++) {
		
			if(lottery_back_type[i].key == data.backType){
				html +="<option value='"+lottery_back_type[i].key+"' selected >"+lottery_back_type[i].value+"</option>";
			}else{
				html +="<option value='"+lottery_back_type[i].key+"'>"+lottery_back_type[i].value+"</option>";
			}
	}
	if(data.backType == undefined){
		html += noStatus;
	}
	$("#detailbackType").html(html);
	$("#detailbackType").selectpicker('refresh');

	//借贷状态
	html = "<option value=\"\">--请选择--</option>";
	for ( var i = 0; i < order_loan_status.length; i++) {
		if(data.loanStatus != undefined){
			if(order_loan_status[i].key == data.loanStatus){
				html +="<option value='"+order_loan_status[i].key+"' selected >"+order_loan_status[i].value+"</option>";
			}else{
				html +="<option value='"+order_loan_status[i].key+"'>"+order_loan_status[i].value+"</option>";
			}
		}else{
			html = noStatus;
		}
	}
	$("#detailloanStatus").html(html);
	$("#detailloanStatus").selectpicker('refresh');
	
}

function isEmpty(pk,status){
	if(status == 1){
		$("#couponGiveMemberId").show();
		$("#couponMemberDetailInfo").html("详情信息");
	}else{
		$("#couponGiveMemberId").hide();
		$("#couponMemberDetailInfo").html("返款信息");
	}
	$("#detailCpoponMemberPk").val(pk);
	$("#detailOrderNumber").val("");
	$("#detailcouponNumber").val("");
	$("#detailbankAccountName").val("");
	$("#detailbankName").val("");
	$("#detailbranchName").val("");
	$("#detailbankCard").val("");
	$("#detailalipayName").val("");
	$("#detailalipayAccount").val("");
	$("#detailrefundTime").val("");
	$("#detailthridNumber").val("");
	
	$("#detailapplyStatus").val("");
	$("#detailloanStatus").val("");

	$("#detailorderStatus").val("");
	$("#detailStatus").val("");
	$("#detailbackType").val("");
	$("#detailbackStatus").val("");
	$("#detailBackAmount").val("");
	$("#detailrefundTime").val("");
	
}
function getCouponMember(data,backAmount,status){
	
	$("#detailOrderNumber").val(data.orderNumber);
	$("#detailcouponNumber").val(data.couponNumber);
	$("#parentPk").val(data.pk);
	$("#detailbankAccountName").val(data.bankAccountName);
	$("#detailbankName").val(data.bankName);
	$("#detailbranchName").val(data.branchName);
	$("#detailbankCard").val(data.bankCard);
	$("#detailalipayName").val(data.alipayName);
	$("#detailalipayAccount").val(data.alipayAccount);
	$("#detailrefundTime").val(data.refundTime);
	$("#detailthridNumber").val(data.thridNumber);
	if(backAmount != 'undefined'){
		$("#detailBackAmount").val(backAmount);
	}
	//显示赠与人与被赠与人
	if(data.status == 4 || data.source == 2){
		$("#detailoMemberName").val(data.omemberName);
		$("#detailmemberName").val(data.acceptMemberName);
	}else{
		$("#detailoMemberName").val("");
		$("#detailmemberName").val("");
	}
	if(data.backType == 1){
		$("#bankInfo").hide();
		$("#aliPayInfo").show();
		$("#detailthridNumberTitle").html("支付宝订单号");
	}else if(data.backType == 2){
		$("#aliPayInfo").hide();
		$("#bankInfo").show();
		
		$("#detailthridNumberTitle").html("交易号");
	}else{
		$("#bankInfo").hide();
		$("#aliPayInfo").hide();
	}
	//返款时返款时间和返款状态允许修改
	if(data.loanStatus == 5 && data.status == 2 && data.applyStatus == 2 && data.backStatus != 2 && status != 1){
		$("#isBackAmountId").show();
		
	}else{
		$("#isBackAmountId").hide();
		
	}
}
function submitDetail(){
	
	var backType = $("#detailbackType").val();
	var bankAccountName = $("#detailbankAccountName").val();
	var bankName = $("#detailbankName").val();
	var branchName = $("#detailbranchName").val();
	var bankCard = $("#detailbankCard").val();
	var alipayName = $("#detailalipayName").val();
	var alipayAccount = $("#detailalipayAccount").val();
	var refundTime = $("#detailrefundTime").val();

	var backStatus = $("#detailbackStatus").val();
	var thridNumber = $("#detailthridNumber").val();
	var detailBackAmount = $("#detailBackAmount").val();
	var pk = $("#detailCpoponMemberPk").val();
	
	var params = {
			pk:pk,
			backType:backType,
			backAmount:detailBackAmount,
			bankAccountName:bankAccountName,
			bankName:bankName,
			branchName:branchName,
			bankCard:bankCard,
			alipayName:alipayName,
			alipayAccount:alipayAccount,
			refundTimeStr:refundTime,
			backStatus:backStatus,
			thridNumber:thridNumber
	};
	if(validNotHidden("#dataForm")){
		if (isDNumber(bankCard) == false && backType == 2) {
			new $.flavr({
				modal : false,
				content : "银行卡号不正确，可能存在非数字内容！"
			});
			return;
		}
		
		$.ajax({
			type : 'GET',
			url : "./updateLotteryCouponMember.do",
			data : params,
			dataType : 'json',
			async : true,
			success : function(data) {
				
				new $.flavr({
					modal : false,
					content : data.msg
				});
				$("#quxiaoCouponMember").click();
				$('#lotteryCouponMemberTableId').bootstrapTable('refresh');
			}
		});
	}
}

function changeBackType(){
	var type = $("#detailbackType").val();
	if(type == 1){
		
		$("#bankInfo").hide();
		$("#aliPayInfo").show();
		$("#detailthridNumberTitle").html("支付宝订单号");
	}else if(type == 2){
		$("#aliPayInfo").hide();
		$("#bankInfo").show();
		$("#detailthridNumberTitle").html("交易号");
	}else{
		$("#aliPayInfo").hide();
		$("#bankInfo").hide();
	}
}



function exportCouponMember() {
	
	var confirm = new $.flavr({
		content : '确定导出优惠券会员列表吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			var exportData = $("#loadingExportCouponMemberData").html(" 正在导出...");
			exportData.prepend("<img src='style/images/exportorder.gif' height='20' width='20' />");
			window.location.href = './exportCouponMember.do'+urlParams(1);
			$("#loadingExportCouponMemberData").html("导出");
		}
	});
}
