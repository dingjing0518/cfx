
/**************************地区管理选择切换START***************************/
function changeCity(self) {
	var pk = $(self).val();
	$("#city").empty().append("<option value=''>市</option>").append(
			getRegions(pk));
	$("#area").empty().append("<option value=''>区</option>");
}

function changeArea(self) {
	$("#area").empty().append("<option value=''>区</option>").append(
			getRegions($(self).val()));
	$("#town").empty().append("<option value=''>镇</option>");
}

function changeTown(self) {
	$("#town").empty().append("<option value=''>镇</option>").append(
			getRegions($(self).val()));
}

function changeToCity(self) {
	var pk = $(self).val();
	$("#toCity").empty().append("<option value=''>市</option>").append(
			getRegions(pk));
	$("#toArea").empty().append("<option value=''>区</option>");
}

function changeToArea(self) {
	$("#toArea").empty().append("<option value=''>区</option>").append(
			getRegions($(self).val()));
	$("#toTown").empty().append("<option value=''>镇</option>");
}

function changeToTown(self) {
	$("#toTown").empty().append("<option value=''>镇</option>").append(
			getRegions($(self).val()));
}

function getRegions(pk) {

	var html = "";
	$.ajax({
		type : 'GET',
		url : "./getRegions.do",
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
/**************************地区管理选择切换END***************************/


$(function() {
	var cfg = {
		url : './lgOrder_data.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
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
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
						+ '<button type="button" data-toggle="modal" data-target="#detailOrderId" style="display:none;" showname="LG_ORDER_NORMAL_BTN_DETAIL" class="btn btn btn-default" onclick="javascript:showDetail(\''
						+ row.pk + '\');">订单详情</button></a>';
				if (row.isMatched == 1) {// 已匹配
					if (row.orderStatus == 9) {// 待付款
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button" data-toggle="modal" data-target="#pushOrderId" style="display:none;" showname="LG_ORDER_NORMAL_BTN_PUSH" class="btn btn-warning" onclick="javascript:pushOrder(\''
							+ row.pk
							+ '\',\''
							+ row.weight
							+ '\');">推送订单</button></a>';
						/*str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
								+ '<button type="button" style="display:none;" showname="BTN_NORMAL_DELIVERYORDERCONFIRM" class="btn btn-warning" onclick="javascript:financialConfirm(\''
								+ row.pk
								+ '\');">确认收款</button></a>&nbsp;&nbsp;&nbsp;';*/
					}
					
					if (row.orderStatus == 8) {// 待财务确认
						/*str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button" data-toggle="modal" data-target="#pushOrderId" style="display:none;" showname="LG_ORDER_NORMAL_BTN_PUSH" class="btn btn-warning" onclick="javascript:pushOrder(\''
							+ row.pk
							+ '\',\''
							+ row.weight
							+ '\');">推送订单</button></a>&nbsp;&nbsp;&nbsp;';*/
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
								+ '<button type="button" style="display:none;" showname="LG_ORDER_NORMAL_BTN_CONFIRM" class="btn btn-warning" onclick="javascript:financialConfirm(\''
								+ row.pk
								+ '\');">确认收款</button></a>';
					}
					if (row.orderStatus == 6) {// 财务已确认，带指定车辆
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
								+ '<button type="button" style="display:none;" showname="LG_ORDER_NORMAL_BTN_PRINT" class="btn btn-warning" onclick="javascript:printDelivery(\''
								+ row.pk
								+ '\');">打印提货单</button></a>';
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
								+ '<button type="button" data-toggle="modal" data-target="#pushOrderId" style="display:none;" showname="LG_ORDER_NORMAL_BTN_PUSH" class="btn btn-warning" onclick="javascript:pushOrder(\''
								+ row.pk
								+ '\',\''
								+ row.weight
								+ '\');">推送订单</button></a>';
					}
					if (row.orderStatus == 5) {// 提货中
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
								+ '<button type="button" style="display:none;" showname="LG_ORDER_NORMAL_BTN_PRINT" class="btn btn-warning" onclick="javascript:printDelivery(\''
								+ row.pk
								+ '\');">打印提货单</button></a>';
					}
					if (row.orderStatus == 4) {// 配送中
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
								+ '<button type="button" data-toggle="modal" data-target="#feedbackOrderId" style="display:none;" showname="LG_ORDER_NORMAL_BTN_FEEDBACK" class="btn btn-warning" onclick="javascript:feedbackOrder(\''
								+ row.pk
								+ '\');">异常反馈</button></a>';
						str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button"  style="display:none;" showname="LG_ORDER_NORMAL_BTN_GOODSLOCATION" class="btn btn-warning" onclick="javascript:goodsLocation(\''
							+ row.pk
							+ '\');">货物所在地</button></a>';
					}
				} else {// 未匹配线路的匹配线路
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button" data-toggle="modal" data-target="#pushOrderId" style="display:none;" showname="LG_ORDER_NORMAL_BTN_PUSH" class="btn btn-warning" onclick="javascript:pushOrder(\''
							+ row.pk
							+ '\',\''
							+ row.weight
							+ '\');">推送订单</button></a>';
					/*str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'
							+ '<button type="button" data-toggle="modal" data-target="#editOrderId" style="display:none;" showname="BTN_DELIVERYORDEREDIT" class="btn btn btn-primary" onclick="javascript:editOrder(\''
							+ row.pk
							+ '\');">编辑</button></a>&nbsp;&nbsp;&nbsp;';*/
				}
				return str;
			}
		}, {
			title : '订单号',
			field : 'orderPk',
			sortable : true
		}, {
			title : '订单时间',
			field : 'orderTime',
			sortable : true
		}, {
			title : '物流供应商',
			field : 'logisticsCompanyName',
		}, {
			title : '要求配送时间',
			field : 'arrivedTime',

		}, {
			title : '商品信息',
			field : 'goodsName',
		}, {
			title : '采购量（箱）',
			field : 'boxes',
		}, {
			title : '重量（吨）',
			field : 'weight',
		},{
			title : '运费单价（元）',
			field : 'originalFreight',
			
		},{
			title : '运费总价（元）',
			field : 'originalTotalFreight',
		},{
			title : '订单状态',
			field : 'orderStatus',
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 2) {
					return "已取消";
				} else if (value == 3) {
					return "已签收";
				} else if (value == 4) {
					return "配送中";
				} else if (value == 5) {
					return "提货中";
				} else if (value == 6) {
					return "已确认，待指派车辆";
				} else if (value == 8) {
					return "待财务确认";
				} else if (value == 9) {
					return "待付款";
				}  else if (value == 10) {
					return "已关闭";
				} else {
					return "";
				}
			}
		}, {
			title : '提货公司抬头',
			field : 'supplierName',
			formatter : function(value, row, index) {
				
					return row.supplierName;
			}
		}, {
			title : '提货地址',
			field : 'fromAddress',
		}, {
			title : '提货联系人',
			field : 'fromContacts',
		}, {
			title : '提货手机号',
			field : 'fromContactsTel',
		}, {
			title : '收货公司抬头',
			field : 'toCompanyName',
		}, {
			title : '配送地址',
			field : 'toAddress',
		}, {
			title : '配送联系人',
			field : 'toContacts',
		}, {
			title : '配送手机号',
			field : 'toContactsTel',
		} ];

/**
 * 物流正常订单状态Table切换
 * @param s
 */
function byOrderStatus(s) {
	
	if (s == 0) {// 未匹配订单
	
		$("#orderStatus").val('');
		$("#isMatched").val(0);
	}
	if (s== 1) {// 全部
	
		$("#orderStatus").val('');
		$("#isMatched").val('');
	}
	if (s != 1 && s != 0) {// 其他

		$("#isMatched").val(1);
		$("#orderStatus").val(s);
	}
	var cfg = {
		url : './lgOrder_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('orderId', cfg);

}

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
		cleanDate();
	}
	var cfg = {
		url : './lgOrder_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('orderId', cfg);
}

/**
 * 订单导出
 */
function excelOrder() {

	$.ajax({
		type : "POST",
		url : './exportLgOrderForm.do'+urlParams(1),
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});

}


/**
 * 待财务确认
 * @param pk
 */
function financialConfirm(pk) {
	var confirm = new $.flavr({
		content : '确定该订单已收款吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './sureMoney.do',
				data : {
					'pk' : pk,
					'orderStatus' : 6,
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						// $("#quxiao").click();
						$('#orderId').bootstrapTable('refresh');
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
 * 模态框：异常反馈
 * 
 * @param pk
 */
function feedbackOrder(pk) {
	// 清空数据
	var file = $("#file") ;
	file.after(file.clone().val(""));      
	file.remove();  
	 $("#trackDetailfb").html("");
	$('#abnormalRemark').val('');
	$('#imgs_div').html('');
	$
			.ajax({
				type : "POST",
				url : './lgOrderDetail.do',
				data : {
					pk : pk
				},
				dataType : "json",
				success : function(data) {
					$("#orderNumberId").val(pk);
					$("#orderInfofb")
							.html(
									"订单编号："
											+ checkNull(data.mainData.deliveryNumber)
											+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 订单金额："
											+ checkNum(data.mainData.originalTotalFreight)
											+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 交易状态: "
											+ checkNull(data.mainData.orderStatusName)
											+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 支付方式："
											+ checkNull(data.mainData.paymentName));
					if (checkNull(data.mainData.supplierName)== "") {
						$("#fromCompanyNamefb").html("提货公司：" + checkNull(data.mainData.fromCompanyName));
					} else {
						$("#fromCompanyNamefb").html("提货公司：" + checkNull(data.mainData.supplierName));
					}
					$("#fromAddressfb").html(
							"提货地址：" + checkNull(data.mainData.fromAddress));
					$("#fromContactsfb").html(
							"联系人：" + checkNull(data.mainData.fromContacts));
					$("#fromContactsTelfb").html(
							"手机号码：" + checkNull(data.mainData.fromContactsTel));
					$("#toCompanyNamefb").html(
							"收货公司：" + checkNull(data.mainData.toCompanyName));
					$("#toAddressfb").html("收货地址：" + checkNull(data.mainData.toAddress));
					$("#toContactsfb").html("联系人：" + checkNull(data.mainData.toContacts));
					$("#toContactsTelfb").html(
							"手机号码：" + checkNull(data.mainData.toContactsTel));
					$("#invoiceTitlefb").html(
							"公司抬头：" + checkNull(data.mainData.invoiceTitle));
					$("#taxidNumberfb").html(
							"纳税人识别号：" + checkNull(data.mainData.taxidNumber));
					$("#bankNamefb").html("开户行：" +checkNull( data.mainData.bankName));
					$("#bankAccountfb").html(
							"银行账户：" + checkNull(data.mainData.bankAccount));
					$("#remarkfb").html(checkNull(data.mainData.remark));
					
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
					
					$("#goodsNamefb").html(
							checkNull(data.mainData.productName)+
							checkNull(data.mainData.varietiesName)+ checkNull(data.mainData.seedvarietyName)+
							checkNull(data.mainData.specName)+checkNull(data.mainData.seriesName)+ 
							checkNull( data.mainData.gradeName)+checkNull(data.mainData.batchNumber));
					$("#boxesfb").html(checkNum(data.mainData.boxes)+"（"+checkNum(data.mainData.unit)+"）");
					$("#weightfb").html(checkNum(data.mainData.weight));
					$("#presentFreightfb").html(checkNum(data.mainData.originalFreight));
					$("#presentFreightCopyfb").html(checkNum(data.mainData.originalFreight)+ "元/吨");
					$("#presentTotalFreightfb").html(
							checkNum(data.mainData.originalTotalFreight));
					$("#boxesCopyfb").html(checkNum(data.mainData.boxes) +checkNum(data.mainData.unit));
					$("#weightCopyfb").html(checkNum(data.mainData.weight) + "吨");
					
					$("#presentTotalFreightCopyfb").html(
							"￥" + checkNum(data.mainData.presentTotalFreight));
					$("#presentTotalFreightFactfb").html(
							"￥" + checkNum(data.mainData.presentTotalFreight));
				}
			});
}

/**
 * 保存订单反馈
 */
function submitFeedback() {
	if ($("#abnormalRemark").val() == "") {
		new $.flavr({
			modal : false,
			content : "请填写异常描述！"
		});
		return;
	}
	var img = "";
	$("#imgs_div .imgC").each(function() {
		var src = $(this).attr("src");
		if (img == "") {
			img = src;
		} else {
			img = img + "," + src;
		}
	});

	if ( img == "") {
		new $.flavr({
			modal : false,
			content : "请上传异常图片！"
		});
		return;
	}
	
	$.ajax({
		type : "POST",
		url : './submitFeedBack.do',
		data : {
			pk : $("#orderNumberId").val(),
			imgs : img,
			abnormalRemark : $("#abnormalRemark").val()
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			}); /* Closing the dialog */
			if (data.success) {
				$("#qx").click();
				$('#feedbackOrderId').modal('hide')
				$('#orderId').bootstrapTable('refresh');
			}
		}
	});
}



/**
 * 货物所在地页面跳转
 * @param pk
 */
function goodsLocation(pk) {
		window.location.href = getRootPath()+"/lgGoodsLocationPage.do?orderPk="+pk;
}


/**
 * 编辑物流订单
 * @param pk
 */
function editOrder(pk) {
	$('#editPk').val(pk);
	$.ajax({
		type : "POST",
		url : './logOrderAddrDetail.do',
		data : {
			pk : pk
		},
		dataType : "json",
		success : function(data) {
			$("#editFromContacts").val(data.fromContacts);
			$("#editFromContactsTel").val(data.fromContactsTel);
			$("#editToContacts").val(data.toContacts);
			$("#editToContactsTel").val(data.toContactsTel);
			$("#editToAddress").val(data.toAddress);
			$("#editFromAddress").val(data.fromAddress);
			$("#province").val(data.fromProvincePk);
			$("#city").empty().append("<option value=''>市</option>").append(getRegions(data.fromProvicePk));
			$("#city").val(data.fromCityPk);
			$("#area").empty().append("<option value=''>区</option>").append(getRegions(data.fromCityPk));
			$("#area").val(data.fromAreaPk);
			$("#town").empty().append("<option value=''>镇</option>").append(getRegions(data.fromAreaPk));	
		 	$("#toProvince").val(data.toProvincePk);
		 	$("#toCity").empty().append("<option value=''>市</option>").append(getRegions(data.toProvicePk));
			$("#toCity").val(data.toCityPk);
			$("#toArea").empty().append("<option value=''>区</option>").append(getRegions(data.toCityPk));
			$("#toArea").val(data.toAreaPk);
			$("#toTown").empty().append("<option value=''>镇</option>").append(getRegions(data.toAreaPk));	
			$("#toTown").val(data.toTownPk);
		
		}
	});
}

/**
 * 保存物流订单
 */
function saveEditOrder() {
	if (!isMobile($("#editFromContactsTel").val())) {
		new $.flavr({
			modal : false,
			content : "请输入正确的提货人手机号！"
		});
		return;
	}
	
	if( $("#province").val() == ""||$("#City").val() == ""||$("#area").val() == ""){
		new $.flavr({
			modal : false,
			content : "提货地址省市区不能为空！"
		});
		return;
	}
	if(!isMobile($("#editToContactsTel").val())){
		new $.flavr({
			modal : false,
			content : "请输入正确的收货人手机号！"
		});
		return;
	}
	if($("#toProvince").val() == ""||$("#toCity").val() == ""){
		new $.flavr({
			modal : false,
			content : "配送地址省市不能为空！"
		});
		return;
	}
	if(valid("#editForm")){
		$.ajax({
			type : "POST",
			url : './saveOrder.do',
			data : {
				pk : $('#editPk').val(),
				fromContacts : $("#editFromContacts").val(),
				fromContactsTel : $("#editFromContactsTel").val(),
				toContacts : $("#editToContacts").val(),
				toContactsTel : $("#editToContactsTel").val(),
				toProvinceName : $("#toProvince").val() == "" ? "" : $("#toProvince option:selected").text(),
				toCityName : $("#toCity").val() == "" ? "" : $("#toCity option:selected").text(),
				toAreaName : $("#toArea").val() == "" ? "" : $("#toArea option:selected").text(),
				toTownName : $("#toTown").val() == "" ? "" : $("#toTown option:selected").text(),
				toProvincePk : $("#toProvince ").val(),
				toCityPk : $("#toCity").val(),
				toAreaPk : $("#toArea").val(),
				toTownPk : $("#toTown").val(),
				toAddress : $("#editToAddress").val(),
				fromProvinceName : $("#province").val() == "" ? "" : $("#province option:selected").text(),
				fromCityName : $("#city").val() == "" ? "" : $("#city option:selected").text(),
				fromAreaName : $("#area").val() == "" ? "" : $("#area option:selected").text(),
				fromTownName : $("#town").val() == "" ? "" : $("#town option:selected").text(),
				fromProvincePk : $("#province").val(),
				fromCityPk : $("#city").val(),
				fromAreaPk : $("#area").val(),
				fromTownPk : $("#town").val(),
				fromAddress : $("#editFromAddress").val(),
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$('#editOrderId').modal('hide')
					$('#orderId').bootstrapTable('refresh');
				}
			}
	
		});
	};
}

/**
 * 打印配送单
 * @param pk
 */
function printDelivery(pk) {
	
	//window.location.href='./exportDeliveryForm.do'+urlParams(1)+"&pk="+pk;
var confirm = new $.flavr({
		content : '确定导出要打印提货单吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './exportDeliveryForm.do',
				dataType : "text",
				async:true,
				data : {
					'pk' : pk
				},
				success : function(data) {
					window.location.href='./downLoandsPDF.do?path='+data;
					//window.location.href = resp;
				}
			});
		}
	});
}

/**
 * 推送订单
 * @type {number}
 */
var tempWeight = 0;
function pushOrder(pk, weight) {
	// 清空
	$("input[name='pk']").val(pk);
	$("#eTotalFreight").val('');
	$("#epresentTotalFreight").val('');
	document.getElementById("logisticsCompanyName").options.selectedIndex = 0; //回到初始状态
	$("#logisticsCompanyName").selectpicker('refresh');
	tempWeight = weight;
}


/**
 * 查询物流公司价格
 * @param logisticPk
 */
function changeSelect(logisticPk) {
	$("#eTotalFreight").val('');
	$("#epresentTotalFreight").val('');
	if(logisticPk!=''){
		$.ajax({
			type : "POST",
			url : './matchLgCompanyRoute.do',
			data : {
				pk : $("input[name='pk']").val(),
				logisticPk : logisticPk,
			},
			dataType : "json",
			success : function(data) {
				if (data.length !=0) {
					if (data[0].list[0].linePk == undefined||data[0].list[0].linePk==null||data[0].list[0].linePk=="") {//符合线路的最低价
						tempWeight = data[0].list[0].toWeight;
						// 对外运费总价=freight， 对内运费总价=basicPrice
						$("#eTotalFreight").val( data[0].freight);//对外价
						$("#epresentTotalFreight").val( data[0].basicPrice);//对内价
						$("#settleWeight").val(data[0].weight),//承运商结算重量
						$("#linePricePk").val(data[0].list[0].pk == undefined ?null:data[0].list[0].pk),
						$("#basisLinePrice").val(0)//对内单价
						$("#originalFreight").val(0)//对外单价
					}else{//符合线路的阶梯价
						//对外运费总价=实际重量*salePrice，对内运费总价=实际重量*basisPrice
						$("#eTotalFreight").val(data[0].list[0].salePrice * data[0].weight);//对外价
						$("#epresentTotalFreight").val((data[0].list[0].basisPrice * data[0].weight).toFixed(2));//对内价
						$("#settleWeight").val(data[0].weight),
						$("#linePricePk").val(data[0].list[0].pk == undefined ?null:data[0].list[0].pk),
						$("#basisLinePrice").val( data[0].list[0].basisPrice)//对内单价
						$("#originalFreight").val( data[0].list[0].salePrice)//对外单价
					}
				}else{
					new $.flavr({
						modal : false,
						content : "无匹配线路，请重新选择！"
					});
				}
			}
		});
	}else{
		new $.flavr({
			modal : false,
			content : "无匹配线路，请重新选择！"
		});
	}
}


/**
 * save订单推送
 */
function submitLogCompanyName() {
	 if($("#eTotalFreight").val()==""||$("#epresentTotalFreight").val()==""){
			new $.flavr({
				modal : false,
				content : "未匹配物流供应商！"
			});
			return;
	}
	$.ajax({
		type : "POST",
		url : './savePushLgCompanyNameExt.do',
		data : {
			pk : $("input[name='pk']").val(),
			logisticsCompanyPk : $("#logisticsCompanyName").val(),
			logisticsCompanyName : $("button[data-id=logisticsCompanyName]").attr("title"),
			originalTotalFreight:$("#eTotalFreight").val(),
			presentTotalFreight:$("#epresentTotalFreight").val(),
			settleWeight:$("#settleWeight").val(),
			linePricePk:$("#linePricePk").val(),
			basisLinePrice:$("#basisLinePrice").val(),
			originalFreight:$("#originalFreight").val(),
			isMatched:1,
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			}); /* Closing the dialog */
			if (data.success) {
				$('#pushOrderId').modal('hide');
				$('#orderId').bootstrapTable('refresh');
			}
		}
	});
	
}

/**
 * 文本域字符限制
 * @param field
 * @constructor
 */
function LimitTextArea(field) {
    maxlimit = 500;
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
      new $.flavr({
			modal : false,
			content : "字数不得多于500！"
		});
   }
}
