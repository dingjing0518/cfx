$(function() {
	var flag = $("#flag").val();
	getStatus(flag);
	
});

/**
 * 列表查询切换
 * @param flag
 */
function getStatus(flag) {
	if(flag==1){
		byStatus('1,2', 1);//金融
	}else if(flag==2){//营销
		byStatus('1,2', 1);
	}else if(flag==3){//财务
		byStatus('2', 1);
	};
}

function byStatus(status, flag) {
	$("li").removeClass("current");
	$("#active_" + flag).addClass("current")
	$
			.ajax({
				type : 'POST',
				url : './searchDeliveryOrder.do',
				async:false,
				data : {
				orderNumber : $("#orderNumber").val(),
					status : status
},
				dataType : 'json',
				success : function(data) {
					var content = "";
					if (data != '' && data.length > 0) {
						for (var i = 0; i < data.length; i++) {
							var sevenCharge = 0;  
							var statusName = ''
							if($("#flag").val()==1 || $("#flag").val()==2 )	{
								if (data[i].status == 1||data[i].status == 2) {
									statusName = "待确认"
								} else if ( data[i].status == 3) {
									statusName = "已确认"
								} else if (data[i].status == 4) {
									statusName = "已驳回"
								}
							}else if($("#flag").val()==3){
								 if ( data[i].status == 2) {
									 statusName = "待确认"
								 }else if( data[i].status == 3){
									 statusName = "已确认"
								 }else if( data[i].status == 4){
									 statusName = "已驳回"
								 }
								
							}
							content += '<div class="table-item clearfix"><ul class="leftabsolut clearfix">'
									+ '<div class="pomiddle">' + '<li><p>'
									+ josn_to_String(data[i].date).substring(0,10)+'</p><p>'+ josn_to_String(data[i].date).substring(11,19)+ '</p></li>' + ' <li>'
									+ data[i].deliveryNumber + '</li>'
									+ '</div></ul>'
									+ '<div class="ul-middleitme clearfix">';
							var goods = data[i].deliveryGoods;
							for (var j = 0; j < goods.length; j++) {
								content += '<ul class="ul-middle clearfix">'
										+ '<li>'
										+ '<p>'+checkUndefined(goods[j].cfGoods.productName)+''+checkUndefined(goods[j].cfGoods.varietiesName)+'</p>'
										+'<p>'+checkUndefined(goods[j].cfGoods.specifications)+''+checkUndefined(goods[j].cfGoods.seriesName)+'</p>'
										+'<p>'+checkUndefined(goods[j].cfGoods.batchNumber)+''+checkUndefined(goods[j].cfGoods.distinctNumber)+''+checkUndefined(goods[j].cfGoods.gradeName)+'<p></p>'+checkUndefined(goods[j].cfGoods.packNumber)+'</p>'
										+ '<li>'
										+ goods[j].presentPrice
										+ '</li>'
										+ '<li><div class="distable">'
										+ '<p class="clearfix"><span>采购量：</span><span class="distable-right">'
										+ goods[j].boxes
										+ '箱/'
										+ goods[j].weight
										+ '吨</span></p>'
										+ '<p class="clearfix"><span>已发量：</span><span class="distable-right">'
										+ goods[j].boxesShipped
										+ '箱/'
										+ goods[j].weightShipped
										+ '吨 </span></p>'
										+ '<p class="clearfix"><span>未发量：</span><span class="distable-right">'
										+ goods[j].noBoxesShipped
										+ '箱/'
										+ goods[j].noWeightShipped
										+ '吨</span></p>'
										+ '<p class="clearfix"><span>提货量：</span><span class="distable-right color-red">'
										+ goods[j].deliverBoxes
										+ '箱/'
										+ goods[j].deliverWeight
										+ '吨</span></p>'
										+ '</div></li>'
										+ '<li><div class="distable">'
										+ '<p class="clearfix"><span>本金：</span><span class="distable-right">'
										+ goods[j].principal
										+ '元</span></p>'
										+ '<p class="clearfix"><span>利息：</span><span class="distable-right">'
										+ goods[j].interest
										+ '元</span></p>'
										+ '<p class="clearfix"><span>服务费：</span><span class="distable-right">'
										+ goods[j].sevenCharge
										+ '元</span></p>'
										+ '<p class="clearfix"><span>合计：</span><span class="distable-right color-red">'
										+ (parseFloat(goods[j].principal)+ parseFloat( goods[j].interest)+  parseFloat(goods[j].sevenCharge)).toFixed(2)
										+ '元</span></p>' + '</div></li></ul>';
								sevenCharge =	parseFloat(goods[j].sevenCharge)+parseFloat(sevenCharge);
							}
							content += '</div> <ul class="rightabsoult clearfix"><div class="pomiddle"><li>'
									+ statusName + '</li><li>';
							var payV = data[i].payVoucherList;
							for (var k = 0; k < payV.length; k++) {
								content += '<a rel="group' + i+'" title="付款凭证" href="'+payV[k].url+' "><img style="width:60px;height:60px;margin:0px 1%;" class="bigpicture" src="'
										+ payV[k].url
										+ '" onerror="errorImg(this)"></img></a>'
							}
							content = content +"</li>";
							if($("#flag").val()==2){//营销
								if (data[i].status == 3) {
									content += '<li><button type="button" " style="display:none;" showname="MARKET_ORDER_BTN_TRUSTEXP"  onclick = "exportRequst('+data[i].deliveryNumber+')" class="btn btn-primary">提货申请单</button>'
											+ ' <button type="button" style="display:none;" showname="MARKET_ORDER_BTN_REQEXP" class="btn btn-primary" onclick = "exportTrust('+data[i].deliveryNumber+')">提货委托单</button></li>';
								}
							}
							if( $("#flag").val()==1){//金融
								 if (data[i].status == 1 ){
									if(sevenCharge>0){
										content += '<li><button type="button"  showname="EM_CREDIT_ORDER_LOANBILL_BTN_SURE" onclick = "submitSure('+data[i].deliveryNumber+',2)" class="btn btn-primary">确认</button>';
									}else{
										content += '<li><button type="button" style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_SURE" onclick = "submitAllSure('+data[i].deliveryNumber+',3)" class="btn btn-primary">确认</button>';
									}
									content += ' <button type="button" class="btn btn-primary" style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_REBJECT" onclick = "submitSure('+data[i].deliveryNumber+',4)">驳回</button></li>';
								}else if ( data[i].status == 3){
									content += '<li><button type="button" style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_REQEXP" onclick = "exportRequst('+data[i].deliveryNumber+')" class="btn btn-primary">提货申请单</button>'
									+ ' <button type="button" class="btn btn-primary"  style="display:none;" showname="EM_CREDIT_ORDER_LOANBILL_BTN_TRUSTEXP" onclick = "exportTrust('+data[i].deliveryNumber+')">提货委托单</button></li>';
								}
							}
							if ( $("#flag").val()==3){//财务
								if(data[i].status == 2){
									content += '<li><button type="button" style="display:none;" showname="FC_ODER_MGR_BILLOFLOADING_BTN_SURE"   onclick = "submitAllSure('+data[i].deliveryNumber+',3)" class="btn btn-primary">确认</button>';
									content += '<button type="button" style="display:none;" showname="FC_ODER_MGR_BILLOFLOADING_BTN_REJECT"  onclick = "submitSure('+data[i].deliveryNumber+',4)" class="btn btn-primary">驳回</button></li>';
								}else if(data[i].status == 3){
									content += '<li><button type="button" style="display:none;" showname="FC_ODER_MGR_BILLOFLOADING_BTN_REQEXP"  onclick = "exportRequst('+data[i].deliveryNumber+')" class="btn btn-primary">提货申请单</button>'
									+ ' <button type="button" class="btn btn-primary" style="display:none;" showname="FC_ODER_MGR_BILLOFLOADING_BTN_TRUSTEXP"  onclick = "exportTrust('+data[i].deliveryNumber+')">提货委托单</button></li>';
								}
							}
							content +='</div></ul></div></div>';
						} 
					
				
					}
					$("#statusContentDiv").html(content)
				}
			});
	loadFancyBox();
}

function loadFancyBox() {
	for ( var i = 0; i < 200; i++) {
		if ($("#statusContentDiv a[rel=group" + i + "]").attr("title") != undefined) {
			// 图片弹出框
			$("#statusContentDiv a[rel=group" + i + "]").fancybox(
					{
						'titlePosition' : 'over',
						'cyclic' : true,
						'titleFormat' : function(title, currentArray,
								currentIndex, currentOpts) {
							return '<span id="fancybox-title-over">'
									+ (currentIndex + 1) + ' / '
									+ currentArray.length
									+ (title.length ? ' &nbsp; ' + title : '')
									+ '</span>';
						}
					});
		}
	}
	btnList();
}

function checkUndefined(res){
	if(res==undefined||res == 'undefined'){
		return '';
	}else{
		return res;
	}
}
function submitSure(deliveryNumber,status){
	$.ajax({
		type : 'POST',
		url : './sureDeliveryOrderStatus.do',
		data : {deliveryNumber:deliveryNumber,
			status:status
			},
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				getStatus($("#flag").val());
			}
				new $.flavr({
					modal : false,
					content : data.msg
				});
			
		}
	});
}

function submitAllSure(deliveryNumber,status){

	$.ajax({
		type : 'POST',
		url : './sureDeliveryOrder.do' + urlParams(1),
		data : {deliveryNumber:deliveryNumber},
		dataType : 'json',
		success : function(data) {
			if (data.code == "0000") {
				getStatus($("#flag").val());
			}
				new $.flavr({
					modal : false,
					content : data.msg
				});
			
		}
	});
}

/**
 * 订单导出
 */
function getOrder() {
	$.ajax({
		type : 'POST',
		url : './exportOrder.do' + urlParams(1),
		data : {},
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
			}
		}
	});
}

/**
 * 下载合同
 * 
 * @param orderNumber
 */
function downPDF(orderNumber) {
	var confirm = new $.flavr({
		content : '确定下载合同吗?',
		dialog : 'confirm',
		onConfirm : function() {
			window.location.href = getRootPath()
					+ "/uploadPDF_F.do?orderNumber=" + orderNumber;
			confirm.close();
		}
	});
}

/**
 * 上传付款凭证
 * 
 * @param orderNumber
 */
function orderPaymentVoucher(orderNumber) {
	$('#payMentVoucherId').modal();

	$("#payMentVoucherId img").each(function() {
		$(this)[0].src = "./style/images/bgbg.png";
	});

	$("input[name='file']").val("");
	$('#payUrl').val(null);
	$('#orderNumberId').val(orderNumber);
}

/**
 * 提交付款凭证
 */
function submit() {

	var params = {};
	$("#dataForm img").each(function() {
		if ($(this).attr("id") != undefined) {
			params[$(this).attr("id")] = $(this).attr("src");
		}
		if ($(this).is(":hidden")) {
			params[$(this).attr("id")] = "";
		}
	});
	params['orderNumber'] = $('#orderNumberId').val();

	if (valid("#dataForm")) {
		$.ajax({
			type : "POST",
			url : './uploadOrderPaymentVoucher.do',
			dataType : "json",
			data : params,
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$("#quxiao").click();
					$('#orderId').bootstrapTable('refresh');
				}
			}
		});

	}
}

function deletePicture(name, id) {
	if (id != "") {
		$.ajax({
			type : "POST",
			url : './delPayvoucher.do',
			dataType : "json",
			data : {
				id : id
			},
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$('div[name="scrollDiv_' + name + '"]').each(function() {
						this.remove();
					});
				}
			}
		});
	}

}



function cleanDelivery() {
	$('#boxTotal').html('0 &nbsp;&nbsp;箱');
	$('#weigthTotal').html( '0&nbsp;&nbsp;吨');
	$('#priceTotal').html('￥0');
	$("#inputNum").find("li[class= 'price']").each(function() {
			$(this).html('0 ');
	});
	$("#cleanInput").find("input").each(function() {
		if (!$(this).is(":hidden")) {
			$(this).val("");
		}
	});
	
}

function submintDelivery() {
	var datas = [];
	var boxs = [];
	var weights =[];
	var childOrderNumbers =[];
	$("#inputNum").find("input[name='box']").each(function() {
		boxs .push($(this).val());
	});
	
	for(var i= 0;i<boxs.length+1;i++){
		if(boxs[i]!=""&&parseFloat(boxs[i])> 100000){
			new $.flavr({
				modal : false,
				content : "提货数量不能大于100000！"
			});
			return;
		}
	}
	$("#inputNum").find("input[name='weight']").each(function() {
		weights .push($(this).val());
	});
	
	for(var i= 0;i<weights.length+1;i++){
		if(weights[i]!=""&&parseFloat(weights[i])> 99999){
			new $.flavr({
				modal : false,
				content : "提货重量不可大于99999吨！"
			});
			return;
		}
	}
	$("#inputNum").find("input[name='childOrderNumber']").each(function() {
		childOrderNumbers .push($(this).val());
	});
	for(var i =0 ; i<childOrderNumbers.length;i++){
		var data = {// json对象 {key:value}
				"childOrderNumber" : childOrderNumbers[i],
				"deliverBoxes" : boxs[i],
				"deliverWeight" : weights[i]
			};
			datas.push(data);// 数组的push方法
	}
				

	var params = {};
	var orderNumber = $('#orderNumber').val();
	params['orderNumber'] = $('#orderNumber').val();
	params['json'] = JSON.stringify(datas);

	$.ajax({
		type : "POST",
		url : './updateCreditDeliveryOrder.do',
		dataType : "json",
		data : params,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.code == "0000") {
				window.location.href = getRootPath()
						+ "/openBillOfLoading.do?orderNumber=" + orderNumber+"&flag="+$("#flag").val();
			}
		}
	});

}

function opendialog() {

	    var sum = 0;
	    $("#divImg img").each(function(){
	    	sum++;
	    });
	    if(sum==3){
	    	new $.flavr({
				modal : false,
				content : "提货凭证最多上传3张！"
			});
			return;
	    }else{
	    	document.getElementById("btn_file").click();
	    }
}

/**
 * 打印申请单
 */
function exportRequst(deliveryNumber){
	
	var confirm = new $.flavr({
		content : '确定下载提货申请单吗?',
		dialog : 'confirm',
		onConfirm : function() {
			window.location.href = getRootPath()
					+ "/uploadPDF_deliveryReq.do?deliveryNumber="+ deliveryNumber;
			confirm.close();
		}
	});
	
}


/**
 * 打印委托书
 */
function exportTrust(deliveryNumber){
	var confirm = new $.flavr({
		content : '确定下载提货委托书吗?',
		dialog : 'confirm',
		onConfirm : function() {
			window.location.href = getRootPath()
					+ "/uploadPDF_trust.do?deliveryNumber="+ deliveryNumber;
			confirm.close();
		}
	});
}
