$(function() {
	createDataGrid('c2bMarrieddealId', cfg);
	$(".form_datetime").datetimepicker({
	    format: "yyyy-mm-dd hh:ii",
	    autoclose: true,
	    todayBtn: true,
	    language:'zh-CN',
	    pickerPosition:"bottom-right"
	  });
});
var toolbar = [];
var columns = [
{
	
	title : '操作',
	field : 'pk',
	width : 30,
	formatter : function(value, row, index) {
		var str = "";
			if(row.companyPk == null){
					str += ' <a class="save" href="javascript:;" style="text-decoration:none;">'+ 
						'<button type="button" style="display:none;" showname="BTN_TRACKRECORDSRECOMMEND" class="btn btn-warning" onclick="javascript:trackRecords(\''
					+ value
					+ '\');">跟踪记录</button></a>';
					
			}
			/*if(row.bidStatus == 0 || row.bidStatus == null){
				str += '<button id="editable-sample_new" style="display:none;" showname="BTN_QUOTE" class="btn btn-primary" data-toggle="modal" onclick="javascript:quotedPrice(\'' + row.supplierPk + '\',\'' + row.recordPk + '\',\''+value+'\'); ">报价</button>&nbsp;&nbsp;'
			}*/
		return str;
	}
}
		,{
			title : '需求单号',
			field : 'pk',
			width : 15,
			sortable : true
		},

		{
			title : '商品信息',
			field : 'productInfo',
			width : 20,
			sortable : true
		},
		{
			title : '备注',
			field : 'remarks',
			width : 20,
			sortable : true
		},
		{
			title : '求购数量(吨)',
			field : 'buyCounts',
			width : 10,
			sortable : true
		},
		{
			title : '发布时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},
		{
			title : '采购商名称',
			field : 'purchaserName',
			width : 20,
			sortable : true
		}, {
			title : '联系人(采购商)',
			field : 'pcontacts',
			width : 10,
			sortable : true
		}, {
			title : '联系电话(采购商)',
			field : 'pcontactsTel',
			width : 10,
			sortable : true
		},
		{
			title : '供应商名称',
			field : 'supplierName',
			width : 20,
			sortable : true
		}, {
			title : '联系人(供应商)',
			field : 'scontacts',
			width : 10,
			sortable : true
		}, {
			title : '联系电话(供应商)',
			field : 'scontactsTel',
			width : 10,
			sortable : true
		}, 
		{
			title : '匹配方式',
			field : 'type',
			width : 10,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 0) {
					return "未匹配";
				}else if (value == 1) {
					return "系统匹配";
				} else if (value == 2) {
					return "人工匹配";
				}else{
					return "未匹配";
				}
			}
		}, 
		{
			title : '状态',
   			field : 'bidStatus',
   			width : 20,
   			sortable : true,
   			formatter : function(value, row, index) {
   				if (value == 0) {
   					return "未报价";
   				}else if (value == 1) {
   					return "已报价";
   				} else if (value == 2) {
   					return "未中标";
   				}else if (value == 3) {
   					return "已中标";
   				}else if (value == -1) {
   					return "已过期";
   				}else if(value == null){
   					return "未报价";
   				}else{
   					return "未知";
   				}

   			}
   		},
		{
			title : '推送时间',
			field : 'pushTime',
			width : 20,
			sortable : true
		}
		
		];
var cfg = {
	url : './c2bMarrieddealBidSupplierDataList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pushTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function editMarrieddeal(pk,status) {
	var parm = {
			'c2bMarrieddealId' : pk,
			'status' : status
		};

		$.ajax({
			type : 'POST',
			url : './editMarrieddeal.do',
			data : parm,
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$('#c2bMarrieddealId').bootstrapTable('refresh');
				}

			}
		});
}

function searchReqx(s) {
	if(s==-1){
		$("#type").val("-1");//查询全部
	}else{
		$("#type").val(s);
	}
	var cfg = {
		url : "./c2bMarrieddealBidSupplierDataList.do"+urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('c2bMarrieddealId', cfg);
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './c2bMarrieddealBidSupplierDataList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('c2bMarrieddealId', cfg);
}

function loadFancyBox(){
	for(var i=0;i<200;i++){
		if($("#creditId a[rel=group"+i+"]").attr("title")!=undefined){
			//图片弹出框
			$("#creditId a[rel=group"+i+"]").fancybox({
				'titlePosition' : 'over',
				'cyclic'        : true,
				'titleFormat'	: function(title, currentArray, currentIndex, currentOpts) {
					return '<span id="fancybox-title-over">' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
				}
			});
		}
	}
}

/////////////////////////////////跟踪记录
var itemTrack0 = {
		
		title : '操作',
		field : 'pk',
		width : 30,
		formatter : function(value, row, index) {
			var str = "";
			//str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" onclick="javascript:quotedPrice(\'2\',\'' + row.pk + '\'); ">修改</button>&nbsp;&nbsp;'
			if(row.isDelete == 1){
			str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:delTrack(\''
						+ row.pk
						+ '\');">删除</button></a>';
			}
			return str;
		}
	};
var itemTrack1={
		title : '需求单号',
		field : 'marrieddealId',
		width : 20,
		sortable : true
	};
var itemTrack2={
		title : '拜访内容',
		field : 'remark',
		width : 20,
		sortable : true
	};

var itemTrack3 = {
	title : '拜访时间',
	field : 'insertTime',
	width : 10,
	sortable : true
};

var itemTrack4 = {
			title : '采购商确认',
   			field : 'isConfirmed',
   			width : 20,
   			sortable : true,
   			formatter : function(value, row, index) {
   				if (value == 1) {
   					return "已确认";
   				} else if(value == 0){ 
   					return "未确认";
   				} else{
   					return "其他";
   				}
   			}
   		};

var columnTrack = [].concat(columns);
columnTrack.splice(0,13);
columnTrack[0] = itemTrack0;
columnTrack[1] = itemTrack1;
columnTrack[2] = itemTrack2;
columnTrack[3] = itemTrack3;
columnTrack[4] = itemTrack4;

function  trackRecords(pk){
		$("#supplierTrackRecordId").modal();
		
		var cfg = {
				url : './c2bMarrieddealSupplierTrackRecordByPk.do?pk='+pk,
				columns : columnTrack,
				uniqueId : 'pk',
				sortName : 'insertTime',
				sortOrder : 'desc',
				toolbar : toolbar,
				onLoadSuccess:btnList
			};
		createDataGrid('c2bMarrieddealTrackId', cfg);
		$("#trackPk").val(pk);//设置跟踪记录要导出的数据
		getSupplierInfo(pk);
		$("#track_remark").val("");
		$(":radio[value=1]").prop("checked",true);
}

//根据pk获取对应报价
function getSupplierInfo(bidPk){
	$.ajax({
		type : 'POST',
		url : './c2bMarrieddealBidSupplierInfoByPk.do',
		data : {
			pk:	bidPk
		},
		dataType : 'json',
		success : function(data) {
			if(data){
				$("#track_marrieddealId").val(data.pk);
				$("#track_productInfo").val(data.productInfo);
				$("#track_use").val(data.useName);
				$("#track_count").val(data.buyCounts);
				$("#track_insertTime").val(data.insertTime);
				$("#track_remarks").val(data.remarks);
				$("#track_purchaserName").val(data.purchaserName);
				$("#track_purchaserContacts").val(data.contacts);
				$("#track_purchaserTel").val(data.contactsTel);
				$("#track_bidPrice").val(data.bidPrice);
				$("#track_bidTime").val(data.bidTime);

			}
		}
	});
}

function submitSupplierTrack(){
	var marrieddealId = $("#track_marrieddealId").val();
	//var productInfo = $("#track_productInfo").val();
	var purchaserName = $("#track_purchaserName").val();
	var purchaserContacts = $("#track_purchaserContacts").val();
	var purchaserTel = $("#track_purchaserTel").val();
	var remark = $("#track_remark").val();
	var isConfirmed= $("input[name='track_isConfirmed']").val();
	var val = $('input:radio[name="track_isConfirmed"]:checked').val();
	var supplierName = $("#track_supplierName").val();
	var scontacts = $("#track_supplierContacts").val();
	var scontactsTel = $("#track_supplierTel").val();
	var bidPrice = $("#track_bidPrice").val();
	var bidTime = $("#track_bidTime").val();
	$.ajax({
		type : "POST",
		url : './editC2bMarrieddealTrack.do',
		data : {
			marrieddealId:marrieddealId,
			purchaserName:purchaserName,
			purchaserContacts:purchaserContacts,
			purchaserTel:purchaserTel,
			remark:remark,
			isConfirmed:val,
			supplierName:supplierName,
			supplierContacts:scontacts,
			supplierTel:scontactsTel,
			bidPrice:bidPrice
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
				$("#quxiao").click();
				$("#c2bMarrieddealTrackId").bootstrapTable('refresh');
		}
	});
	
}
function delTrack(pk){
	$.ajax({
		type : 'POST',
		url : './delC2bMarrieddealTrack.do',
		data : {
			pk:pk
		},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
				$('#c2bMarrieddealTrackId').bootstrapTable('refresh');
		}
	});
}
function exportsBidSupplier(){
	var params = '';
	$("#headone").find("input").each(function(i,n){
		if(i==0){
			params+="?"+$(n).attr("name")+"="+$(n).val();
		}else{
			params+="&"+$(n).attr("name")+"="+$(n).val();
		}
	})
 	var confirm = new $.flavr({
		content : '确定导出报价数据吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './exportBidSupplierByParams.do'+params,
				dataType : "text",
				data : {
				},
				success : function(resp) {
					window.location.href = resp;
				}
			});
		}
	});
}

/*********************************************报价*********************/

		var itemBid0 = {
				title:"选择",
				field:'pk',
				width :5,
				formatter:function(value, row, index){
					return "<input type='checkbox' value='"+row.pk+"' onchange='changeGoods(this)'>";
				}
				};
		var itemBid1={
				title : '品名',
				field : 'productName',
				width : 20,
				sortable : true
		};
		var itemBid2={
				title : '品种',
				field : 'varietiesName',
				width : 20,
				sortable : true
		};
		
		var itemBid3 = {
				title : '子品种',
				field : 'seedvarietyName',
				width : 10,
				sortable : true
		};
		
		var itemBid4 = {
			title : '规格',
				field : 'specName',
				width : 20,
				sortable : true
			};
		var itemBid5 = {
			
			title : '规格系列',
			field : 'seriesName',
			width : 30,
			sortable:true
		};
		
		var itemBid6 = {
			title : '批号',
			field : 'batchNumber',
			width : 10,
			sortable : true
		};
var columnBids = [].concat(columns);
columnBids.splice(0,12);
columnBids[0] = itemBid0;
columnBids[1] = itemBid1;
columnBids[2] = itemBid2;
columnBids[3] = itemBid3;
columnBids[4] = itemBid4;
columnBids[5] = itemBid5;
columnBids[6] = itemBid6;

function quotedPrice(supplierPk,bidPk,marrieddealPk){

$("#quotedPriceId").modal();
//查询商品列表
var cfg = {
	url : './goods_data.do?auditStatus=2&isUpdown=2&companyPk='+supplierPk, 
	columns : columnBids,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar
};
	createDataGrid('c2bGoodsBidId', cfg);
	getBidInfo(bidPk);
	$("#bpk").val(bidPk);
	$("#companyPk").val(supplierPk);
	$("#boxesNum").val("0");
	$("#bidPrice").val("0");
	}

function getBidInfo(bidPk){
			$.ajax({
				type : 'POST',
				url : './getC2bMarrieddealBidRecord.do?pk='+bidPk,
				data : "",
				dataType : 'json',
				success : function(data) {
					if(data){
						$("#bidRecordPk").val(data.marrieddealPk);
						$("#bidRecordProductInfo").val(data.productInfo);
						$("#bidRecordBuyCounts").val(data.buyCounts);
						$("#bidRecordRemarks").val(data.remarks);
						$("#bidRecordSupplierName").val(data.supplierName);
						$("#pushSupplierPk").val(data.supplierPk);
						$("#bidRecordContacts").val(data.contacts);
						$("#bidRecordContactsTel").val(data.bcontactsTel);
						$("#ppType").val(data.type);
						$("#pushMemnerPk").val(data.memberPk);
						$("#pushMemnerName").val(data.memberName);
						$("#bidRecordBuyCounts").val(data.buyCounts);
					}
				}
			});
	}

function addMarriedBid(){
		var marrieddealPk = $("#bidRecordPk").val();
		var bidPrice = $("#bidPrice").val();
		var boxesNum = $("#boxesNum").val();
		var buyCounts = $("#buyCounts").val();//求购重量单位吨
		
		var supplierName = $("#bidRecordSupplierName").val();
		var contacts = $("#bidRecordContacts").val();
		var contactsTel = $("#bidRecordContactsTel").val();
		var supplierPk = $("#pushSupplierPk").val();
		var type = $("#ppType").val();
		var memberPk = $("#pushMemnerPk").val();
		var memberName = $("#pushMemnerName").val();	
		var goodsPk = $("#c2bGoodsBidId").find("input[type='checkbox']:checked").val();
if(goodsPk==undefined){
	new $.flavr({
		modal : false,
		content : "请先选择商品"
	});
	return;
	goodsPk = '';
}
if(bidPrice<=0){
	new $.flavr({
		modal : false,
		content : "报价需大于0"
	});
	return;
}

if(!/^\+?[1-9]\d*$/.test(boxesNum)) {
	new $.flavr({
		modal : false,
		content : "箱数输入需大于0的整数！"
	});
	return;
	}
var confirm = new $.flavr({
	content : '价格:'+bidPrice+',确定无误执行报价?',
	dialog : 'confirm',
	onConfirm : function() {
		confirm.close();
		$.ajax({
			type : "POST",
			url : './insertMarrieddealRecordBid.do',
			dataType : "json",
			data : {
				marrieddealPk:marrieddealPk,
				supplierName:supplierName,
				supplierPk:supplierPk,
				bcontactsTel:contactsTel,
				contacts:contacts,
				type:type,
				memberPk:memberPk,
				memberName:memberName,
				bidPrice:bidPrice,
				goodsPk:goodsPk,
				boxesNum:boxesNum,
				buyCounts:buyCounts
			},
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				$("#quotedPriceId").modal('hide');
				$("#c2bMarrieddealId").bootstrapTable('refresh');
			}
		});
	}
});
}	

	function changeGoods(self){
	if(!$(self).prop("checked")){
	$("#c2bGoodsBidId input[type='checkbox']:checked").prop("checked",false);
	}else{
	$("#c2bGoodsBidId input[type='checkbox']:checked").prop("checked",false);
	$(self).prop("checked","checked");
	var buyCounts = $("#buyCounts").val();//求购重量单位吨
	
	$.ajax({
		type : "POST",
		url : './showMarrieddealBidBoxes.do',
		dataType : "json",
		data : {
			goodsPk:$("#c2bGoodsBidId").find("input[type='checkbox']:checked").val(),
			buyCounts:buyCounts
		},
		success : function(data) {
			if(data.success){
				$("#boxesNum").val(data.boxes);	
			}
		}
	});
	}
	}
	
function searchGoodsTabs(type){
		//清除所有内容
		if(type==2){
			$("#batchNumber").val('');
			$("#select_bid_condition").find("select").each(function(i,n){
				$(n).val('');
			});
			$("#select_bid_condition .pull-left").each(function(i,n){
				$(n).text('--请选择--');
			})
		}
		var params = '';
		$("#select_bid_condition").find("input,select").each(function(i,n){
			if($(n).attr("name")!=undefined){
				params+="&"+$(n).attr("name")+"="+$(n).val();
			}
		})
		var cfg = {
				url : './goods_data.do?auditStatus=2&isUpdown=2'+ params,
				columns : columnBids,
				uniqueId : 'pk',
				sortName : 'pk',
				sortOrder : 'asc',
				toolbar : toolbar
			};
		
		createDataGrid('c2bGoodsBidId', cfg);
	}


function AddChangeSpec(){
	$.ajax({
		type : 'POST',
		url : './getspecByPid.do',
		data : {
			parentPk : $("#specPk").val()
		},
		dataType : 'json',
		success : function(data) {
			
			$("#seriesPk").empty();
			$("#seriesPk").append("<option value=''>--请选择规格系列--</option>");
			if (data.length > 0) {
				 
				for ( var i = 0; i < data.length; i++) {
					$("#seriesPk").append(
							"<option value='" + data[i].pk + "'>"
									+ data[i].name + "</option>");
				}
				
			}
			$("#seriesPk").selectpicker('refresh');//
		}
	});
}
/*******************************************************************end**************************************/



