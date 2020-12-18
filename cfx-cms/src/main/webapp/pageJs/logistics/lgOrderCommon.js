/**
 * 物流订单详情展示
 * @param pk
 */
function showDetail(pk){
	//清空
	  $("#trackDetail").html("");
	$.ajax({
		type : "POST",
		url : './lgOrderDetail.do',
		data : {
			pk:pk
		},
		dataType : "json",
		success : function(data) {
			$("#orderInfo").html("订单编号："+checkNull(data.mainData.deliveryNumber)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 订单金额："+checkNum(data.mainData.originalTotalFreight)
					+"元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 交易状态: "+checkNull(data.mainData.orderStatusName)+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 支付方式："+checkNull(data.mainData.paymentName));
			if (checkNull(data.mainData.supplierName)== "") {
				$("#fromCompanyName").html("提货公司：" + checkNull(data.mainData.fromCompanyName));
			} else {
				$("#fromCompanyName").html("提货公司：" + checkNull(data.mainData.supplierName));
			}
			$("#fromAddress").html("提货地址："+checkNull(data.mainData.fromAddress));
			$("#fromContacts").html("联系人："+checkNull(data.mainData.fromContacts));
			$("#fromContactsTel").html("手机号码："+checkNull(data.mainData.fromContactsTel));
			$("#toCompanyName").html("收货公司："+checkNull(data.mainData.toCompanyName));
			$("#toAddress").html("收货地址："+checkNull(data.mainData.toAddress));
			$("#toContacts").html("联系人："+checkNull(data.mainData.toContacts));
			$("#toContactsTel").html("手机号码："+checkNull(data.mainData.toContactsTel));
			$("#invoiceTitle").html("公司抬头："+checkNull(data.mainData.invoiceTitle));
			$("#taxidNumber").html("纳税人识别号："+checkNull(data.mainData.taxidNumber));
			$("#bankName").html("开户行："+checkNull(data.mainData.bankName));
			$("#bankAccount").html("银行账户："+checkNull(data.mainData.bankAccount));
			$("#remark").html(checkNull(data.mainData.remark));
			if(data.trackDetail.length>0){
				var content="";
				for(var i = 0 ; i< data.trackDetail.length ; i++){
					content  = content+"<span>"+data.trackDetail[i].orderStatusName+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.trackDetail[i].insertTime+"&nbsp;&nbsp;&nbsp;&nbsp;"+data.trackDetail[i].stepDetail+"<span>"
				}
			}
			  $("#trackDetail").append(content);
			  $("#goodsName").html(checkNull(data.mainData.productName)+
					  checkNull(data.mainData.varietiesName)+checkNull(data.mainData.seedvarietyName)+
					  checkNull(data.mainData.specName)+checkNull(data.mainData.seriesName)+ 
					  checkNull(data.mainData.gradeName)+checkNull(data.mainData.batchNumber));
			  $("#boxes").html(checkNum(data.mainData.boxes)+"（"+checkNum(data.mainData.unit)+"）");
			  $("#weight").html(checkNum(data.mainData.weight));
			  $("#presentFreight").html(checkNum(data.mainData.originalFreight));
			  $("#presentFreightCopy").html(checkNum(data.mainData.originalFreight) + "元/吨");
			  $("#presentTotalFreight").html(checkNum(data.mainData.originalTotalFreight)); 
			  $("#boxesCopy").html(checkNum(data.mainData.boxes)+checkNum(data.mainData.unit));
			  $("#weightCopy").html(checkNum(data.mainData.weight)+"吨");
			 
			  $("#presentTotalFreightCopy").html("￥"+ checkNum(data.mainData.originalTotalFreight)); 
			  $("#presentTotalFreightFact").html("￥"+ checkNum(data.mainData.originalTotalFreight)); 
			  
		}
	});
}


