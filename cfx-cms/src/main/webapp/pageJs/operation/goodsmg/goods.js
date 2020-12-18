$(function() {
	createDataGrid('goodsId', cfg);
});

var toolbar = [];
var columns = [
//		{
//			title : '价格趋势',
//			field : 'pk',
//			width : 20,
//			formatter : function(value, row, index) {
//				var str = "";
//				if(row.isHome == undefined || row.isHome==2){
//					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:block;" showname="BTN_SHOW_HOMEPAGE" class="btn btn-warning"   onclick="javascript:showHomePage(\''
//						+ value + '\',1);">显示</button></a>';
//				}else{
//					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:block;" showname="BTN_SHOW_HOMEPAGE" class="btn btn-warning"   onclick="javascript:showHomePage(\''
//						+ value + '\',2);">不显示</button></a>';
//				}
//				return str;
//			}
//		},
// 		{
// 			title : '商品名称',
// 			field : 'name',
// 			width : 20,
// 			sortable : true
// 		},
	
		{
			title : '店铺',
			field : 'storeName',
			width : 20,
			sortable : true
		},

		{
			title : '挂牌价(元/吨)',
			field : 'tonPrice',
			width : 20,
			sortable : true
		}, {
			title : '公司名称',
			field : 'companyName',
			width : 20,
			sortable : true
		}, {
			title : '厂家品牌',
			field : 'brandName',
			width : 20,
			sortable : true
		}, {
			title : '品名名称',
			field : 'productName',
			width : 20,
			sortable : true
		}, {
			title : '规格大类名称',
			field : 'specName',
			width : 20,
			sortable : true
		}, {
			title : '规格系列名称',
			field : 'seriesName',
			width : 20,
			sortable : true
		}, {
			title : '等级名称',
			field : 'gradeName',
			width : 20,
			sortable : true
		}, {
			title : '品种名称',
			field : 'varietiesName',
			width : 20,
			sortable : true
		}, {
			title : '子品种名称',
			field : 'seedvarietyName',
			width : 20,
			sortable : true
		}, {
			title : '创建时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}, {
			title : '更新时间',
			field : 'updateTime',
			width : 20,
			sortable : true
		}, {
			title : '上架时间',
			field : 'upTime',
			width : 20,
			sortable : true
		}, {
			title : '批号',
			field : 'batchNumber',
			width : 20,
			sortable : true
		}, {
			title : '箱重',
			field : 'tareWeight',
			width : 20,
			sortable : true
		}, {
			title : '总箱数',
			field : 'totalBoxes',
			width : 20,
			sortable : true
		}, {
			title : '总库存',
			field : 'totalWeight',
			width : 20,
			sortable : true
		},{
			title : '厂区名称',
			field : 'plantName',
			width : 20,
			sortable : true
		}, {
			title : '仓库名称',
			field : 'wareName',
			width : 20,
			sortable : true
		}, {
			title : '用途描述',
			field : 'purposeDescribe',
			width : 20,
			sortable : true
		}];
var cfg = {
	url : './searchGoodsList.do?isUpdown=2',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

var status = 0;

/**
 *
 * @param s
 */
function findGoods(s) {
	status = s;
	var cfg = {
		url : './searchGoodsList.do?auditStatus=' + s + urlParams(''),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('goodsId', cfg);
}

function SearchClean(type) {
	if(type == 2){
		cleanpar();
	}
	var cfg = {
		url : './searchGoodsList.do?isUpdown=2&'+ urlParams(''),
		columns : columns,
		uniqueId : 'pk',
        sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('goodsId', cfg);
}

function changeVari() {
	$.ajax({
		type : 'POST',
		url : './getvarietiesByPid.do',
		data : {
			parentPk : $("#varietiesPk").val()
		},
		dataType : 'json',
		success : function(data) {
			$("#seedvarietyPk").empty();
			$("#seedvarietyPk").append("<option value=''>---全部---</option>");
			if (data) {
				for ( var i = 0; i < data.length; i++) {
					$("#seedvarietyPk").append(
							"<option value='" + data[i].pk + "'>"
									+ data[i].name + "</option>");
				}
			}
			$("#seedvarietyPk").selectpicker('refresh');//
		}
	});
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
function changeSpec() {
	$.ajax({
		type : 'POST',
		url : './getspecByPid.do',
		data : {
			parentPk : $("#specPk").val()
		},
		dataType : 'json',
		success : function(data) {
			$("#seriesPk").empty();
			$("#seriesPk").append("<option value=''>---全部---</option>");
			if (data) {
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


function hiddenSpec(){
	 var productPk=$("#productPk").val();
	if(productPk.length>0){
		if(parseInt(productPk)==13 ){
			$("#productSpec").hide();//隐藏div  
		}else{
			$("#productSpec").show();//显示div
		}
	}
	
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
			$("#addSeriesPk").append("<option value=''>---请选择规格系列---</option>");
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
 function edit(pk) {
	$('#editGoods').modal();
	if (null != pk && '' != pk) {
		  $.ajax({
		        type: 'GET',
		        url:  './GetGoods.do',
		        data: {
		            'pk': pk
		        },
		        dataType: 'json',
		        success: function(data) {
		        	$("#pk").val(data.pk);
		        	$("#name").val(data.name);
		        	var comParentPk=data.comParentPk;//子公司父节点是否为null 或者空
		        	var pidPk="";
		        	var pidName="";
		        	if(comParentPk.length>0){//有子公司
		        		pidPk=data.compPk;
		        		pidName=data.compName;
		        		$.post(getRootPath()+"/searchChildrenCompanys.do",{companyType:2,parentPk:comParentPk,start:null,limit:null},function(data2){
							if(data2.rows.length>0){
								 var html = "<option value=''>--请选择子公司--</option>";
								 for(var i =0;i<data2.rows.length;i++){
									 if(data2.rows[i].pk==data.compPk){
											html +="<option value="+data2.rows[i].pk+" selected>"+data2.rows[i].name+"</option>";
									 }else{
										 html +="<option value="+data2.rows[i].pk+">"+data2.rows[i].name+"</option>";
									 }
									
								}
								 if(html.length>0){
									 document.getElementById("companyPk").style.display="";
								 }
							
								$("#companyPk").append(html);
							}
						},"json"); 
		        	}else{
		        		pidPk=data.comParPk;
		        		pidName=data.comParName;
		        	}
		        	 $("button[data-id=companyPID] span.filter-option").html(data.comParName); 
	        		 $("button[data-id=companyPID]").attr("title",data.comParName); 
					 $("#companyPID").val(data.comParPk);	
							
							$.post(getRootPath()+"/changeStore.do",{companyPk:pidPk},function(data3){
						 		 var html = "<option value=''>--请选择店铺--</option>";
								if(data3.length>0){
									 for(var i =0;i<data3.length;i++){
										 if(data3[i].pk==data.storePk){
											 html +="<option value="+data3[i].pk+" selected>"+data3[i].name+"</option>";
										 }else{
											 html +="<option value="+data3[i].pk+">"+data3[i].name+"</option>";
										 }
									}
								}
								$("#storePk").empty().append(html);
							},"json"); 
							
							$.post(getRootPath()+"/changePlant.do",{companyPk:pidPk},function(data4){
						 		 var html = "<option value=''>--请选择厂区--</option>";
								if(data4.length>0){
									 for(var i =0;i<data4.length;i++){
										 if(data4[i].pk==data.plantPk){
											 html +="<option value="+data4[i].pk+" selected>"+data4[i].name+"</option>";
										 }else{
											 html +="<option value="+data4[i].pk+"  >"+data4[i].name+"</option>";
										 }
										
									}
								
									
								}
								$("#plantPk").empty().append(html);
							},"json"); 
		        	$.post(getRootPath()+"/changeWare.do",{PlantPk:data.plantPk},function(data5){
						if(data5.length>0){
							 var html = "<option value=''>--请选择仓库--</option>";
							 for(var i =0;i<data5.length;i++){
								 if(data5[i].pk==data.warePk){
									 html +="<option value="+data5[i].pk+" selected>"+data5[i].name+"</option>";
								 }else{
									 html +="<option value="+data5[i].pk+">"+data5[i].name+"</option>";
								 }
								
							}
						
							$("#warePk").empty().append(html);
						}
					},"json"); 
					
					$("#price").val(data.price);
					 $("button[data-id=brandPk] span.filter-option").html(data.brandName); 
	        		 $("button[data-id=brandPk]").attr("title",data.brandName); 
					 $("#brandPk").val(data.brandPk);	
					 $("button[data-id=productPk] span.filter-option").html(data.productName); 
	        		 $("button[data-id=productPk]").attr("title",data.productName); 
					 $("#productPk").val(data.productPk);	
					 $("button[data-id=addSpecPk] span.filter-option").html(data.specName); 
	        		 $("button[data-id=addSpecPk]").attr("title",data.specName); 
					 $("#addSpecPk").val(data.specPk);	
					 
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
								$("#addSeriesPk").append("<option value=''>---请选择---</option>");
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
									$("#addSeedvarietyPk").empty();
									$("#addSeedvarietyPk").append("<option value=''>---请选择---</option>");
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
									$("#addSeedvarietyPk").selectpicker('refresh');//
								}
							});
						 
						 $("#batchNumber").val(data.batchNumber);
						 $("#tareWeight").val(data.tareWeight);
						 $("#totalBoxes").val(data.totalBoxes);
						 $("#totalWeight").val(data.totalWeight);
						 $("#availableBoxes").val(data.availableBoxes);
						 $("#availableWeight").val(data.availableWeight);
						 $("#frozenBoxes").val(data.frozenBoxes);
						 $("#frozenWeight").val(data.frozenWeight);
						 $("input[name='isVisable'][value=" + data.isVisable + "]").prop("checked", true);
						 $("#details").val(data.details);
						 $("#auditTime").val(null);
						 $("#upTime").val(null);
					
		        }
		    });
	  
		 
 
	} else {
		$("#pk").val('');
    	$("#name").val('');
    	 document.getElementById("companyPk").style.display="none";
    		 $("button[data-id=companyPID] span.filter-option").html("--请选择--"); 
    		 $("button[data-id=companyPID]").attr("title","--请选择--"); 
			 $("#companyPID").val('');	
			 $("#storePk").empty().append("<option value=''>--请选择店铺--</option>");
			 $("#plantPk").empty().append("<option value=''>--请选择厂区--</option>");
			 $("#warePk").empty().append("<option value=''>--请选择仓库--</option>");
     
		
		$("#price").val('');
		 $("button[data-id=brandPk] span.filter-option").html("--请选择--"); 
		 $("button[data-id=brandPk]").attr("title","--请选择--"); 
		 $("#brandPk").val('');	
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
			 
			 $("button[data-id=addSeedvarietyPk] span.filter-option").html("--请选择--"); 
    		 $("button[data-id=addSeedvarietyPk]").attr("title","--请选择--"); 
			 $("#addSeedvarietyPk").val('');
			 
			 
			 
			 $("#batchNumber").val('');
			 $("#tareWeight").val('');
			 $("#totalBoxes").val('');
			 $("#totalWeight").val('');
			 $("#availableBoxes").val('');
			 $("#availableWeight").val('');
			 $("#frozenBoxes").val('');
			 $("#frozenWeight").val('');
			 $("#purposeDescribe").val('');
			 $("input[name='isVisable'][value=1]").prop("checked", true);
			 $("#details").val('');
			 $("#specifications").val('');
			 
    }
}
 function changeCompay(){
	 document.getElementById("companyPk").style.display="none";
	 $("#companyPk").empty();
	 $("#storePk").empty();
	 $("#plantPk").empty();
	 	$.post(getRootPath()+"/getChildCompanyList.do",{parentPk:$("#companyPID").val()},function(data){
	 		
			if(data.length>0){
				
				 var html = "<option value=''>--请选择子公司--</option>";
				 for(var i =0;i<data.length;i++){
					html +="<option value="+data[i].pk+">"+data[i].name+"</option>";
				}
				 if(html.length > 0){
					 document.getElementById("companyPk").style.display="";
				 }
			
				$("#companyPk").append(html);
			}
		},"json"); 

        var companyPid=$("#companyPID").val();
	 	$.post(getRootPath()+"/getStoreByCompanyPk.do",{companyPk:companyPid},function(data){
	 		 var html = "<option value=''>--请选择店铺--</option>";
	 		 if(data != null){
				 html +="<option value="+data[0].pk+">"+data[0].name+"</option>";
			}
			$("#storePk").append(html);
		},"json"); 
 }
 
 function changePlant(){
	 var storePk=$("#storePk").val();
	 $.post(getRootPath()+"/getPlant.do",{storePk:storePk},function(data){
 		 var html = "<option value=''>--请选择厂区--</option>";
		if(data.length>0){
			 for(var i =0;i<data.length;i++){
				html +="<option value="+data[i].pk+">"+data[i].name+"</option>";
			}
		}
		$("#plantPk").append(html);
	},"json");
	 
	 $.post(getRootPath()+"/getCompanyBrand.do",{storePk:storePk},function(data){
 		 var htmlBrand = "<option value=''>--请选择厂家品牌--</option>";
		if(data.length>0){
			 for(var i =0;i<data.length;i++){
				 htmlBrand +="<option value="+data[i].pk+">"+data[i].brandName+"</option>";
			}
		}
		$("#brandPk").html(htmlBrand);
	},"json");
	 
 }
 
 
 
 function changeChildCompany(){
     var companypk=$("#companyPk").val();
    
	 	$.post(getRootPath()+"/changeStore.do",{companyPk:companypk},function(data){
	 		 var html = "<option value=''>--请选择店铺--</option>";
			if(data != null){
			
				html +="<option value="+data.pk+">"+data.name+"</option>";			
				
			}
			$("#storePk").empty().append(html);
		},"json"); 
     var html = "<option value=''>--请选择厂区--</option>";
	 	$.post(getRootPath()+"/changePlant.do",{companyPk:companypk},function(data){
			if(data.length>0){
				 for(var i =0;i<data.length;i++){
					html +="<option value="+data[i].pk+">"+data[i].name+"</option>";
				}
			}
			$("#plantPk").empty().append(html);
		},"json"); 
 }
 function changeWare(){
		$.post(getRootPath()+"/getWare.do",{storePk:$("#storePk").val()},function(data){
			if(data.length>0){
				 var html = "<option value=''>--请选择仓库--</option>";
				 for(var i =0;i<data.length;i++){
					html +="<option value="+data[i].pk+">"+data[i].name+"</option>";
				}
			
				$("#warePk").empty().append(html);
			}
		},"json"); 
 }
 
 function submit(){
	 
	 var companypid=$("#companyPID").val();
	 var companyPk = $("#companyPk").val();
	 var companyName="";
	 //选择子公司，保存子公司pk
	 if(companyPk !=null && companyPk.length>0){
		 companyName=$("#companyPk option:selected").text();
	 }else{
		 companyPk=companypid;
		 companyName=$("button[data-id=companyPID]").attr("title");
	 }
	 var brandpk=$("#brandPk").val();
	 var brandName="";
	 if(brandpk!=null && brandpk.length>0){
		 brandName=$("#brandPk option:selected").text();
		 //brandName=$("button[data-id=brandPk]").attr("title");
	 }
	 var storeName="";
	 var storePk=$("#storePk").val();
	 if(storePk.length>0){
		 storeName=$("#storePk option:selected").text();
	 }
	 var plantName="";
	 var plantPK=$("#plantPk").val();
	 if(plantPK.length>0){
		 plantName=$("#plantPk option:selected").text();
	 }
	 
	 var wareName="";
	 var warePk=$("#warePk").val();
	 if(warePk.length>0){
		 wareName=$("#warePk option:selected").text();
	 }
	 
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
	 var seedvarietyPk=$("#addSeedvarietyPk").val();
	 var seedvarietyName="";
	 if(seedvarietyPk.length>0){
		 seedvarietyName=$("button[data-id=addSeedvarietyPk]").attr("title");
	 }
	 var specifications = $("#specifications").val();
	 var purposeDescribe = $("#purposeDescribe").val();
	if(validNotHidden("#goodsDataFormId")){
			$.ajax({
				type : "POST",
				url : './insertGoods.do',
				data : {
					pk : $("#pk").val(),
					type:$("#editType").val(),
					name : $("#name").val(),
					companyPk :companyPk,
					companyName:companyName,
					storePk:storePk,
					storeName:storeName,
					plantPk:plantPK,
					plantName:plantName,
					warePk:warePk,
					wareName:wareName,
					price:$("#price").val(),
					brandPk:brandpk,
					brandName:brandName,
					productPk:productPk,
					productName:productName,
					specPk:specPk,
					specName:specName,
					seriesPk:seriesPk,
					seriesName:seriesName,
					gradePk:gradePk,
					gradeName:gradeName,
					varietiesPk:varietiesPk,
					varietiesName:varietiesName,
					seedvarietyPk:seedvarietyPk,
					seedvarietyName:seedvarietyName,
					batchNumber:$("#batchNumber").val(),
					tareWeight:$("#tareWeight").val(),
					totalBoxes:$("#totalBoxes").val(),
					totalWeight:$("#totalWeight").val(),
					isVisable : $('input:radio[name="isVisable"]:checked').val(),
					purposeDescribe:purposeDescribe,
					specifications:specifications
				},
				dataType : "json",
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao").click();
						$('#goodsId').bootstrapTable('refresh');
					}
				}
			});
			}
	 
 }
 
 function addIndex(){
		var parm = {};
		
		$("#editable-sample_index").attr('disabled',true);
			$.ajax({
				type : 'POST',
				url : './insertGoodsIndex.do',
				timeout : 1000*60*300,
				data : parm,
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
					$("#editable-sample_index").attr('disabled',false);
					if (data.success) {
						$("#refuseReasons_quxiao").click();
						$('#goodsId').bootstrapTable('refresh');
					}
				},
				error:function(jqXHR, textStatus, errorThrown){//如果时间过长，说明正在添加索引，可能导致请求超时
		            if(textStatus=="timeout"){
		            	new $.flavr({
							modal : false,
							content : "请求超时，请重试！"
						});
		            	$("#editable-sample_index").attr('disabled',false);
						$("#refuseReasons_quxiao").click();
		            	$('#goodsId').bootstrapTable('refresh');
		            }
				}
			});
} 
 
 function showHomePage(pk,type){
	 $.ajax({
			type : 'POST',
			url : './updateGoods.do',
			data : {pk:pk,isHome:type},
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				$('#goodsId').bootstrapTable('refresh');
			}
	 });
 }