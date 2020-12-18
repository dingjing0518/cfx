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
	field : 'memberPk',
	width : 30,
	formatter : function(value, row, index) {
		var str = "";
				if(row.auditStatus== 0){
					//str += '<button type="button" onclick="javascript:del(\'' + row.pk + '\'); " style="display:none;" showname="BTN_DELETEPRICEMARRIED" class="btn btn-danger active">删除</button>&nbsp;&nbsp;'
					//str += '<button id="editable-sample_new" style="display:none;" showname="BTN_AUDITMARRIED" class="btn btn-primary" data-toggle="modal" onclick="javascript:verifyOrder(\'1\',\'' + row.pk + '\'); ">审核通过</button>&nbsp;&nbsp;'
					//str += '<button id="editable-sample_new" style="display:none;" showname="BTN_AUDITMARRIED" class="btn btn-primary" data-toggle="modal" onclick="javascript:verifyOrder(\'-1\',\'' + row.pk + '\'); ">审核不通过</button>&nbsp;&nbsp;'
				}
				/*if(row.auditStatus != 1){
				str += '<button id="editable-sample_new" style="display:none;" showname="BTN_UPDATEPRICEMARRIED" class="btn btn-primary" data-toggle="modal" onclick="javascript:edit(\'' + row.pk + '\'); ">编辑</button>&nbsp;&nbsp;'
				}*/
//				if(row.auditStatus ==-1){
//				str += '<button id="editable-sample_new" style="display:none;" showname="BTN_AUDITMARRIED" class="btn btn-primary" data-toggle="modal" onclick="javascript:verifyOrder(\'1\',\'' + row.pk + '\'); ">审核通过</button>&nbsp;&nbsp;'
//				}
				if(row.auditStatus==1){
				//str += '<button type="button" onclick="javascript:del(\'' + row.pk + '\'); " style="display:none;" showname="BTN_DELETEPRICEMARRIED" class="btn btn-danger active">删除</button>&nbsp;&nbsp;'
				//str += '<button id="editable-sample_new" style="display:none;" showname="BTN_AUDITMARRIED" class="btn btn-primary" data-toggle="modal" onclick="javascript:verifyOrder(\'-1\',\'' + row.pk + '\'); ">审核不通过</button>'
				
				//str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_MARRIEDRECOMMENDSP" class="btn btn-warning" onclick="javascript:pushSupplier(\'' + row.pk + '\',\'' + row.purchaserPk + '\');">推荐供应商</button></a>&nbsp;&nbsp;&nbsp;';
				/*str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_PRICEQUTOEDPRICE" class="btn btn-warning" onclick="javascript:qutoedPriceManage(\''
					+ row.pk
					+ '\');">报价管理</button></a>&nbsp;&nbsp;&nbsp;';*/
				}
				if(row.auditStatus==1 || row.auditStatus==-1 ){
				str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_PRICETRACKRECORDS" class="btn btn-warning" onclick="javascript:trackRecords(\''
					+ row.pk
					+ '\');">跟踪记录</button></a>';
				}
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
		}
		,{
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
			title : '审核不通过原因',
			field : 'refuseReason',
			width : 20,
			sortable : true
		},
		
		{
			title : '采购商名称',
			field : 'purchaserName',
			width : 20,
			sortable : true
		}, {
			title : '联系人',
			field : 'contacts',
			width : 10,
			sortable : true
		}, {
			title : '联系电话',
			field : 'contactsTel',
			width : 10,
			sortable : true
		}, {
			title : '审核时间',
			field : 'auditTime',
			width : 20,
			sortable : true
		}, 
		{
			title : '审核人',
			field : 'account',
			width : 20,
			sortable : true
		}, 
		{
			title : '报价状态',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 0) {
					return "已过期";
				} else if (value == 1) {
					return "已中标";
				} else if (value == 2) {
					return "已经报价";
				}else if (value == 9) {
					return "等待报价";
				}

			}
		}
		];
var cfg = {
	url : './c2bMarrieddealDataList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};


var item0 = {
		
		title : '操作',
		field : 'bpk',
		width : 30,
		formatter : function(value, row, index) {
			var str = "";
			if(row.bidStatus == 0){
			//str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" onclick="javascript:quotedPrice(\'2\',\'' + row.pk + '\'); ">报价</button>&nbsp;&nbsp;'
			}		
			str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:bidTrackRecords(\''
						+ value
						+ '\',\''+row.marrieddealPk+'\');">跟踪记录</button></a>';
			return str;
		}
	};

var item1 = {
			title : '需求单号',
   			field : 'marrieddealPk',
   			width : 15,
   			sortable : true
   		};
var item2 = {
		title : '商品信息',
		field : 'productInfo',
		width : 20,
		sortable : true
	};
var item3 = {
		title : '备注',
		field : 'remarks',
		width : 20,
		sortable : true
	};
var item4 = {
			title : '报价(元/吨)',
   			field : 'bidPrice',
   			width : 10,
   			sortable : true
   		};
var item5 = {
	title : '联系人',
	field : 'contacts',
	width : 10,
	sortable : true
};

var item6 = {
			title : '报价时间',
   			field : 'insertTime',
   			width : 20,
   			sortable : true
   		};
var item7 = {
			title : '采购商名称',
   			field : 'bpurchaserName',
   			width : 20,
   			sortable : true
   		};


var item8 = {
	title : '求购数量(吨)',
	field : 'buyCounts',
	width : 10,
	sortable : true
};
var item9={
		title : '供应商名称',
		field : 'supplierName',
		width : 20,
		sortable : true
	};



var item10 = {
	title : '联系电话',
	field : 'bcontactsTel',
	width : 10,
	sortable : true
};
var item11 = {
			title : '状态',
   			field : 'bidStatus',
   			width : 20,
   			sortable : true,
   			formatter : function(value, row, index) {
   				if (value == 3) {
   					return "已中标";
   				} else if(value == 0){ 
   					return "未报价";
   				} else if (value == 1) {
   					return "已报价";
   				} else if (value == 2) {
   					return "未中标";
   				}

   			}
   		};
var item12 = {
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
   					return "未知";
   				}

   			}
   		};

var columnNew = [].concat(columns);
columnNew.splice(0,12);
columnNew[0] = item0;
columnNew[1] = item1;
columnNew[2] = item2;
columnNew[3] = item3;
columnNew[4] = item4;
columnNew[5] = item5;
columnNew[6] = item6;
columnNew[7] = item7;
columnNew[8] = item8;
columnNew[9] = item9;
columnNew[10] = item10;
columnNew[11] = item11;
columnNew[12] = item12;
/***************************报价管理***************************************************/
function qutoedPriceManage(pk){
	$("#quotedPriceManageId").modal();
	$("#marrieddealPk").val(pk);
	var cfg = {
			url : './c2bMarrieddealBidDataList.do?bpk='+pk,
			columns : columnNew,
			uniqueId : 'bpk',
			sortName : 'endTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('c2bMarrieddealBidId', cfg);
		
}

function searchquotedPriceTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './c2bMarrieddealBidDataList.do'+urlParams(1),
			columns : columnNew,
			uniqueId : 'bpk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('c2bMarrieddealBidId', cfg);
}

function searchQuotedStatus(s) {
	if(s==-1){
		$("#bidStatus").val("-2");//查询全部
	}else{
		$("#bidStatus").val(s);
	}
	var cfg = {
		url : "./c2bMarrieddealBidDataList.do"+urlParams(1),
		columns : columnNew,
		uniqueId : 'bpk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('c2bMarrieddealBidId', cfg);
}
/********************************************end************************************************/
//function editMarrieddeal(pk,status) {
//	var parm = {
//			'c2bMarrieddealId' : pk,
//			'status' : status
//		};
//
//		$.ajax({
//			type : 'POST',
//			url : './editMarrieddeal.do',
//			data : parm,
//			dataType : 'json',
//			success : function(data) {
//				new $.flavr({
//					modal : false,
//					content : data.msg
//				});
//				if (data.success) {
//					$('#c2bMarrieddealId').bootstrapTable('refresh');
//				}
//
//			}
//		});
//}

function hiddenSpec(){
	 var productPk=$("#productPk").val();
	if(productPk.length>0){
		if(parseInt(productPk)==13 ){
			$('select[id="addSeriesPk"]').removeAttr("required");
			$('select[id="addSpecPk"]').removeAttr("required");
			$("#productSpec").hide();//隐藏div 
			
		}else{
			$("#productSpec").show();//显示div
			$('select[id="addSeriesPk"]').attr("required", "true");
			$('select[id="addSpecPk"]').attr("required", "true");
		}
	}
	
}


function del(pk,time) {
	var confirm = new $.flavr({
		content : '确认删除该需求吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './deleteMarrieddeal.do',
				data : {
					 pk : pk
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
						$("#quxiao").click();
						$('#c2bMarrieddealId').bootstrapTable('refresh');

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}

function submit() {
	var pk=$("#pk").val();
	/*var brandpk=$("#brandPk").val();
	 var brandName="";
	 if(brandpk!=null&&brandpk.length>0){
		 brandName=$("button[data-id=brandPk]").attr("title");
	 }*/
	var productPk=$("#productPk").val();
	 var productName="";
	 if(productPk.length>0){
		 productName=$("button[data-id=productPk]").attr("title");
	 }
	 var specPk=$("#addSpecPk").val();
	 var specName="";
	 if(specPk.length>0){
		 specName=$("button[data-id=addSpecPk]").attr("title");
	 }
	 var seriesName="";
	 var seriesPk=$("#addSeriesPk").val();
	 if(seriesPk.length>0){
		 seriesName=$("#addSeriesPk option:selected").text();
	 }
	 var gradePk=$("#gradePk").val();
	 var gradeName="";
	 if(gradePk.length>0){
		 gradeName=$("button[data-id=gradePk]").attr("title");
	 }
	 var varietiesPk=$("#addVarietiesPk").val();
	 var varietiesName="";
	 if(varietiesPk.length>0){
		 varietiesName=$("button[data-id=addVarietiesPk]").attr("title");
	 }
//	 var seedvarietyPk=$("#addSeedvarietyPk").val();
//	 var seedvarietyName="";
//	 if(seedvarietyPk.length>0){
//		 seedvarietyName=$("button[data-id=addSeedvarietyPk]").attr("title");
//	 }
	 
//	 var usePk=$("#usePk").val();
//	 var useName="";
//	 if(usePk.length>0){
//		 useName=$("button[data-id=usePk]").attr("title");
//	 }
	
	var purchaserPk = $("#purchaserName").val();
	var purchaserName="";
	 if(purchaserPk != null){
		 purchaserName=$("button[data-id=purchaserName]").attr("title");
	 }
	var contacts = $("#contacts").val();
	var contacts_tel = $("#contacts_tel").val();
	var buyCounts = $("#buyCounts").val();
	var remarks = $("#remarks").val();
	

if(valid("#dataFormGoods")){
	$.ajax({
		type : "POST",
		url : './editC2bMarrieddeal.do',
		data : {
			pk:pk,
			purchaserName:purchaserName,
			purchaserPk:purchaserPk,
			contacts:contacts,
			contactsTel:contacts_tel,
			buyCounts:buyCounts,
			productPk :productPk,
			remarks:remarks,
			productName : productName,
			//brandPk:brandpk,
			//brandName:brandName,
			specPk:specPk,
			specName:specName,
			seriesPk:seriesPk,
			seriesName:seriesName,
			gradePk:gradePk,
			gradeName:gradeName,
			//usePk:usePk,
			//useName:useName,
			varietiesPk:varietiesPk,
			varietiesName:varietiesName
			//seedvarietyPk:seedvarietyPk,
			//seedvarietyName:seedvarietyName
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
				$("#quxiao").click();
				$("#c2bMarrieddealId").bootstrapTable('refresh');
		}
	});
	}
}

function pushSupplier(pk,purchaserPk){
	$('#pushSupplierId').modal();
	document.getElementById("selectPushSupplierId").style.display="none";
	$.ajax({
		type : 'POST',
		url : './getSupplierNotCloldMine.do',
		data : {
			purchaserPk : purchaserPk
		},
		dataType : 'json',
		success : function(data) {
			
			$("#selectPushSupplierId").empty();
			$("#selectPushSupplierId").append("<option value=''>--请选择供应商--</option>");
			if (data.length > 0) {
				 
				for ( var i = 0; i < data.length; i++) {
					$("#selectPushSupplierId").append(
							"<option value='" + data[i].pk + "'>"
									+ data[i].name + "</option>");
				}
				
			}
			$("#selectPushSupplierId").selectpicker('refresh');//
		}
	});

	$("#marrieddealBidPk").val(pk);
	
}
function push(){
	
	 var pushSupplierId=$("#selectPushSupplierId").val();
	 var pushSupplierName="";
	 if(pushSupplierId.length>0){
		 pushSupplierName=$("button[data-id=selectPushSupplierId]").attr("title");
	 }
	 if(pushSupplierId==""){
         new $.flavr({
             modal : false,
             content : "请先选择供应商"
         });
         return;
	 }
	$.ajax({
		type:"POST",
		url:"./pushSupplierToBid.do",
		data:{
			marrieddealPk:$("#marrieddealBidPk").val(),
			supplierName:pushSupplierName,
			supplierPk:pushSupplierId
		},
		dataType:"json",
		success:function(data){
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiao").click();
			$(function () { $('#pushSupplierId').modal('hide')});
			$("#c2bMarrieddealId").bootstrapTable('refresh');
		}
	});
}

function AddChangeSpec(){
	 document.getElementById("addSeriesPk").style.display="none";
	$.ajax({
		type : 'POST',
		url : './getspecByPid.do',
		data : {
			parentPk : $("#addSpecPk").val()
		},
		dataType : 'json',
		success : function(data) {
			
			$("#addSeriesPk").empty();
			$("#addSeriesPk").append("<option value=''>--请选择规格系列--</option>");
			if (data.length > 0) {
				 
				for ( var i = 0; i < data.length; i++) {
					$("#addSeriesPk").append(
							"<option value='" + data[i].pk + "'>"
									+ data[i].name + "</option>");
				}
				
			}
			$("#addSeriesPk").selectpicker('refresh');//
		}
	});
}

function findMarri(s) {
	if(s==-2){
		$("#status").val("-2");//查询全部
	}else{
		$("#status").val(s);
	}
	var cfg = {
		url : "./c2bMarrieddealDataList.do"+urlParams(1),
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
			url : './c2bMarrieddealDataList.do'+urlParams(1),
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

function addChangeVari() {
	$.ajax({
		type : 'POST',
		url : './getvarietiesByPid.do',
		data : {
			parentPk : $("#addVarietiesPk").val()
		},
		dataType : 'json',
		success : function(data) {
			$("#addSeedvarietyPk").empty();
			$("#addSeedvarietyPk").append("<option value=''>---请选择子品种---</option>");
			if (data) {
				for ( var i = 0; i < data.length; i++) {
					$("#addSeedvarietyPk").append(
							"<option value='" + data[i].pk + "'>"
									+ data[i].name + "</option>");
				}
			}
			$("#addSeedvarietyPk").selectpicker('refresh');//
		}
	});
}

function getCompanyByPk(){
	var companyPk = $("#purchaserName").val();
	$("#contacts_tel").val('');
	$("#contacts").val('');
	if(companyPk==''){
		return;
	}
	$.ajax({
		type:"POST",
		data:{
			companyPk:companyPk
		},
		url:"./getCompanyTelByPk.do",
		dataType:"json",
		success:function(data){
				$("#contacts_tel").val(data.contactsTel);
				$("#contacts").val(data.contacts);
			
		}
		
	});
	
}

function edit(pk) {
	$('#editPublishingRequirements').modal();
	$("#pk").val(pk);
	if (null != pk && '' != pk) {
//		  $.ajax({
//		        type: 'GET',
//		        url:  './getC2bMarrieddealByPk.do',
//		        data: {
//		            'pk': pk
//		        },
//		        dataType: 'json',
//		        success: function(data) {
					var data = $('#c2bMarrieddealId').bootstrapTable('getRowByUniqueId',pk);
		        	$("#pk").val(data.pk);
		        	
		        	var pidPk="";
		        	var pidName="";
		        	 $("#dataFormGoods .filter-option").text(data.purchaserName);
		        	 $("button[data-id=purchaserName]").attr("title",data.purchaserName);
					 $("#purchaserName").val(data.purchaserPk);
					 $("#contacts").val(data.contacts);
					 $("#contacts_tel").val(data.contactsTel);
					 $("#buyCounts").val(data.buyCounts);
					 $("#remarks").val(data.remarks);
					 /*$("button[data-id=brandPk] span.filter-option").html(data.brandName); 
	        		 $("button[data-id=brandPk]").attr("title",data.brandName); */
					 $("#brandPk").val(data.brandPk);	
					 $("button[data-id=productPk] span.filter-option").html(data.productName); 
	        		 $("button[data-id=productPk]").attr("title",data.productName); 
					 $("#productPk").val(data.productPk);
					 
					 
					 if(parseInt(data.productPk)==13 ){
							$('select[id="addSeriesPk"]').removeAttr("required");
							$('select[id="addSpecPk"]').removeAttr("required");
							$("#productSpec").hide();//隐藏div 
							
						}else{
							$("#productSpec").show();//显示div
							$('select[id="addSeriesPk"]').attr("required", "true");
							$('select[id="addSpecPk"]').attr("required", "true");
							$("button[data-id=addSpecPk] span.filter-option").html(data.specName); 
			        		 $("button[data-id=addSpecPk]").attr("title",data.specName); 
							 $("#addSpecPk").val(data.specPk);
						}
					 document.getElementById("addSeriesPk").style.display="none";
						$.ajax({
							type : 'POST',
							url : './getspecByPid.do',
							data : {
								parentPk : $("#addSpecPk").val()
							},
							dataType : 'json',
							success : function(data6) {
								$("#addSeriesPk").empty();
								$("#addSeriesPk").append("<option value=''>---请选择规格系列---</option>");
								if (data6.length > 0) {
									 
									for ( var i = 0; i < data6.length; i++) {
										if(data6[i].pk==data.seriesPk){
											$("#addSeriesPk").append(
													"<option value='" + data6[i].pk + "' selected>"
															+ data6[i].name + "</option>");
										}else{
											$("#addSeriesPk").append(
													"<option value='" + data6[i].pk + "'>"
															+ data6[i].name + "</option>");
										}
									
									}
									
								}
								$("#addSeriesPk").selectpicker('refresh');//
							}
						});
						 $("button[data-id=gradePk] span.filter-option").html(data.gradeName); 
		        		 $("button[data-id=gradePk]").attr("title",data.gradeName); 
						 $("#gradePk").val(data.gradePk);	
						 $("button[data-id=addVarietiesPk] span.filter-option").html(data.varietiesName); 
		        		 $("button[data-id=addVarietiesPk]").attr("title",data.varietiesName); 
						 $("#addVarietiesPk").val(data.varietiesPk);
						 
						 $.ajax({
								type : 'POST',
								url : './getvarietiesByPid.do',
								data : {
									parentPk : $("#addVarietiesPk").val()
								},
								dataType : 'json',
								success : function(data7) {
									/*$("#addSeedvarietyPk").empty();
									$("#addSeedvarietyPk").append("<option value=''>---请选择子品种---</option>");
									if (data7) {
										for ( var i = 0; i < data7.length; i++) {
											if(data7[i].pk==data.seedvarietyPk){
												$("#addSeedvarietyPk").append(
														"<option selected value='" + data7[i].pk + "'>"
																+ data7[i].name + "</option>");
											}else{
												$("#addSeedvarietyPk").append(
														"<option value='" + data7[i].pk + "'>"
																+ data7[i].name + "</option>");
											}
										}
									}
									$("#addSeedvarietyPk").selectpicker('refresh');//*/
								}
							});
						 $("#batchNumber").val(data.batchNumber);
						
						 $("input[name='isVisable'][value=" + data.isVisable + "]").prop("checked", true);
						 $("#details").val(data.details);
//		        }
//		    });

	} else {
		$("#dataFormGoods .filter-option").text("--请选择--");
		 $("#purchaserName").val("");
		 $("#contacts").val("");
		 $("#contacts_tel").val("");
		/* $("button[data-id=brandPk] span.filter-option").html("--请选择--"); 
		 $("button[data-id=brandPk]").attr("title","--请选择--"); 
		 $("#brandPk").val('');	*/
		 $("button[data-id=productPk] span.filter-option").html("--请选择--"); 
		 $("button[data-id=productPk]").attr("title","--请选择--"); 
		 $("#productPk").val('');	
		 $("button[data-id=addSpecPk] span.filter-option").html("--请选择--"); 
		 $("button[data-id=addSpecPk]").attr("title","--请选择--"); 
		 $("#addSpecPk").val('');	
		 $("button[data-id=addSeriesPk] span.filter-option").html("--请选择--"); 
		 $("button[data-id=addSeriesPk]").attr("title","--请选择--"); 
		 $("#addSeriesPk").val('');	
			 $("button[data-id=gradePk] span.filter-option").html("--请选择--"); 
    		 $("button[data-id=gradePk]").attr("title","--请选择--"); 
			 $("#gradePk").val('');	
			 
			 $("button[data-id=addVarietiesPk] span.filter-option").html("--请选择--"); 
    		 $("button[data-id=addVarietiesPk]").attr("title","--请选择--"); 
			 $("#addVarietiesPk").val('');
			 $("#buyCounts").val("");
			 $("#remarks").val("");
			/* $("button[data-id=addSeedvarietyPk] span.filter-option").html("--请选择--"); 
    		 $("button[data-id=addSeedvarietyPk]").attr("title","--请选择--"); 
			 $("#addSeedvarietyPk").val('');*/
    }
	
}	
//审核需求
function verifyOrder(auditStatus,pk){
		var params = {
				'pk' : pk,
				'auditStatus' : auditStatus
			};
		if(auditStatus==1){
			sendPost(params);
		}else{
			$("#auditPk").val(pk);
			$("#auditStatusR").val(auditStatus);
			$("#refuseReason").val("");
			$("#noAuditQuoted").modal();
		}
	}

/////////////////////////审核需求中的跟踪记录

var itemTrack0 = {
		
		title : '操作',
		field : 'marrieddealId',
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
   					return "未确认";
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
//跟踪记录
function  trackRecords(pk){
		$("#trackRecordId").modal();
		var cfg = {
				url : './c2bMarrieddealTrackData.do'+urlParams(1)+"&marrieddealId="+pk,
				columns : columnTrack,
				uniqueId : 'pk',
				sortName : 'endTime',
				sortOrder : 'desc',
				toolbar : toolbar,
				onLoadSuccess:btnList
			};
		createDataGrid('c2bMarrieddealTrackId', cfg);
		getC2bMarrieddealByPk(pk);
		$("#trackPk").val(pk);//设置跟踪记录要导出的数据
		$("#track_remark").val("");
		$(":radio[value=1]").prop("checked",true);
	}



function getC2bMarrieddealByPk(pk){
	
	$.ajax({
		type : 'POST',
		url : './getC2bMarrieddealPk.do?pk='+pk,
		data : pk,
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
//				$("#track_remark").val(data.remark);
//				$("input[name='track_isConfirmed']").v(data.isConfirmed);
				
			}
			
		}
	});
}


function submitTrack(){
	
	var marrieddealId = $("#track_marrieddealId").val();
	//var productInfo = $("#track_productInfo").val();
	var purchaserName = $("#track_purchaserName").val();
	var purchaserContacts = $("#track_purchaserContacts").val();
	var purchaserTel = $("#track_purchaserTel").val();
	var remark = $("#track_remark").val();
	var isConfirmed= $("input[name='track_isConfirmed']").val();

	var val = $('input:radio[name="track_isConfirmed"]:checked').val();
	
	
	$.ajax({
		type : "POST",
		url : './editC2bMarrieddealTrack.do',
		data : {
			marrieddealId:marrieddealId,
			purchaserName:purchaserName,
			purchaserContacts:purchaserContacts,
			purchaserTel:purchaserTel,
			remark :remark,
			isConfirmed:val
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

function delBidTrack(pk){
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
				$('#c2bMarrieddealBidTrackId').bootstrapTable('refresh');
		}
	});
}

function submitBidTrack(){
	var marrieddealId = $("#track_marrieddealBidId").val();
	//var productInfo = $("#track_productInfo").val();
	var purchaserName = $("#track_purchaserNameBid").val();
	var purchaserContacts = $("#track_purchaserContactsBid").val();
	var purchaserTel = $("#track_purchaserTelBid").val();
	var remark = $("#track_remarkBid").val();
	var isConfirmed= $("input[name='track_isConfirmed']").val();
	var val = $('input:radio[name="track_isConfirmed"]:checked').val();
	var supplierName = $("#track_supplierNameBid").val();
	var scontacts = $("#track_supplierContactsBid").val();
	var scontactsTel = $("#track_supplierTelBid").val();
	var bidPrice = $("#track_bidPriceBid").val();
	var bidTime = $("#track_bidTimeBid").val();
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
				$("#c2bMarrieddealBidTrackId").bootstrapTable('refresh');
		}
	});
	
}









function exportsTrack(){

 	var confirm = new $.flavr({
		content : '确定导出报表数据吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './exportTrackByMarrieddealId.do'+urlParams(1),
				dataType : "text",
				data : {
					marrieddealId:$("#trackPk").val()
				},
				success : function(resp) {
					window.location.href = resp;
				}
			});
		}
	});
}

function exportsBidSub(){

 	var confirm = new $.flavr({
		content : '确定导出报价数据吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './exportBidByParams.do'+urlParams(1),
				dataType : "text",
				data : {
					bpk:$("#marrieddealPk").val()
				},
				success : function(resp) {
					window.location.href = resp;
				}
			});
		}
	});
}



function sendPost(params){
	$.ajax({
		type : 'POST',
		url : './auditC2bMarrieddeal.do',
		data : params,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
				$('#c2bMarrieddealId').bootstrapTable('refresh');
		}
	});
}
function refuse(){
	if(valid("#reasonForm")){
		var params  = {
				pk:$("#auditPk").val(),
				auditStatus:$("#auditStatusR").val(),
				refuseReason:$("#refuseReason").val()
		};
		$.ajax({
			type : 'POST',
			url : './auditC2bMarrieddeal.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				$("#qx").click();
				$('#c2bMarrieddealId').bootstrapTable('refresh');
			}
		});
	}
}
//导出
function exports() {
	var confirm = new $.flavr({
		content : '确定导出报表数据吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './exportReportForms.do'+urlParams(1),
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

/////////////////////////////////报价管理中的跟踪记录
var itemTrackBid0 = {
		
		title : '操作',
		field : 'pk',
		width : 30,
		formatter : function(value, row, index) {
			var str = "";
			//str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" onclick="javascript:quotedPrice(\'2\',\'' + row.pk + '\'); ">修改</button>&nbsp;&nbsp;'
			if(row.isDelete == 1){
			str += ' <a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:delBidTrack(\''
						+ row.pk
						+ '\');">删除</button></a>';
			}
			return str;
		}
	};
var itemTrackBid1={
		title : '需求单号',
		field : 'marrieddealId',
		width : 20,
		sortable : true
	};
var itemTrackBid2={
		title : '拜访内容',
		field : 'remark',
		width : 20,
		sortable : true
	};

var itemTrackBid3 = {
	title : '拜访时间',
	field : 'insertTime',
	width : 10,
	sortable : true
};

var itemTrackBid4 = {
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

var columnTrackBid = [].concat(columns);
columnTrackBid.splice(0,12);
columnTrackBid[0] = itemTrackBid0;
columnTrackBid[1] = itemTrackBid1;
columnTrackBid[2] = itemTrackBid2;
columnTrackBid[3] = itemTrackBid3;
columnTrackBid[4] = itemTrackBid4;

function  bidTrackRecords(bidPk,marrieddealPk){
		$("#bidTrackRecordId").modal();
		var cfg = {
				//c2bMarrieddealBidTrackRecordByPk
				url : './c2bMarrieddealBidTrackRecordByPk.do?pk='+marrieddealPk,
				columns : columnTrackBid,
				uniqueId : 'pk',
				sortName : 'insertTime',
				sortOrder : 'desc',
				toolbar : toolbar,
				onLoadSuccess:btnList
			};
		createDataGrid('c2bMarrieddealBidTrackId', cfg);
		$("#trackPk").val(marrieddealPk);//设置跟踪记录要导出的数据
		getSupplierInfo(bidPk);
		$("#track_remarkBid").val("");
		$(":radio[value=1]").prop("checked",true);
}

//根据pk获取对应报价
function getSupplierInfo(bidPk){
	$.ajax({
		type : 'POST',
		url : './c2bMarrieddealBidInfoByPk.do',
		data : {
			pk:	bidPk
		},
		dataType : 'json',
		success : function(data) {
			if(data){
				$("#track_marrieddealBidId").val(data.pk);
				$("#track_productInfoBid").val(data.productInfo);
				$("#track_useBid").val(data.useName);
				$("#track_countBid").val(data.buyCounts);
				$("#track_insertTimeBid").val(data.insertTime);
				$("#track_remarksBid").val(data.remarks);
				$("#track_purchaserNameBid").val(data.purchaserName);
				$("#track_purchaserContactsBid").val(data.pcontacts);
				$("#track_purchaserTelBid").val(data.pcontactsTel);
				$("#track_supplierNameBid").val(data.supplierName);
				$("#track_supplierContactsBid").val(data.scontacts);
				$("#track_supplierTelBid").val(data.scontactsTel);
				$("#track_bidPriceBid").val(data.bidPrice);
				$("#track_bidTimeBid").val(data.bidTime);

			}
		}
	});
}
