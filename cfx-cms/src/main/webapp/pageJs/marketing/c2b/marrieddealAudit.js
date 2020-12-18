$(function() {
	createDataGrid('marrieddealAuditId', cfg);
});
var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'marrieddealId',
	width : 50,
	formatter : function(value, row, index) {
		var str = "";
		if(row.flag==2&&row.auditStatus==1){
			str+=' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn btn-primary" data-toggle="modal" data-target="#" onclick="javascript:edit(\'' + value + '\');">编辑</button></a>';
				
		}
		if(row.auditStatus==1){
			str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:editMarrieddealAudit(\''
				+ value
				+ '\',2);">通过</button></a>';
			str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:editrefuseReason(\''
				+ value
				+ '\');">不通过</button></a>';
		}else if(row.auditStatus==2){
			str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:editrefuseReason(\''
				+ value
				+ '\');">不通过</button></a>';
		}else{
			str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" class="btn btn-warning" onclick="javascript:editMarrieddealAudit(\''
				+ value
				+ '\',2);">通过</button></a>';
		}
		
		return str;
	}
} ,
{
	title : '审核状态',
	field : 'auditStatus',
	width : 20,
	sortable : true,
	formatter : function(value, row, index) {
		if (value == 1) {
			return "待审核";
		} else if (value == 2) {
			return "审核通过";
		} else if (value == 3) {
			return "审核不通过";
		} 

	}

},
{
	title : '备注',
	field : 'refuseReason',
	width : 20,
	sortable : true
},

		{
			title : '采购单号',
			field : 'marrieddealId',
			width : 20,
			sortable : true
		},
		{
			title : '求购来源',
			field : 'flag',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				  if (value == 2) {
					return "后台录入";
				} else{
					return "前台录入";
				} 

			}

		},

		
		{
			title : '采购标题',
			field : 'name',
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
			title : '联系人',
			field : 'contacts',
			width : 20,
			sortable : true
		},
		{
			title : '手机',
			field : 'contactsTel',
			width : 20,
			sortable : true
		},
		{
			title : '需求有效期',
			field : 'endTime',
			width : 20,
			sortable : true
		},
		{
			title : '发布时间',
			field : 'startTime',
			width : 20,
			sortable : true
		},
		{
			title : '品名',
			field : 'productName',
			width : 20,
			sortable : true
		},
		{
			title : '品种',
			field : 'varietiesName',
			width : 20,
			sortable : true
		},
		{
			title : '规格(吨)',
			field : 'specName',
			width : 20,
			sortable : true
		},
		{
			title : '等级',
			field : 'gradeName',
			width : 20,
			sortable : true
		},{
			title : '求夠数量',
			field : 'buyCounts',
			width : 20,
			sortable : true
		}];
var cfg = {
	url : './marrieddealAudit_data.do',
	columns : columns,
	uniqueId : 'marrieddealId',
	sortName : 'startTime',
	sortOrder : 'desc',
	toolbar : toolbar
};

function editMarrieddealAudit(pk, auditstatus) {
	var parm = {
			marrieddealId: pk,
			auditStatus:auditstatus,
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
					$('#marrieddealAuditId').bootstrapTable('refresh');
				}

			}
		});
}
function editrefuseReason(pk){
	$('#refuseReason').modal();
	 $("#refuseReasonid").val(pk);
}
function saveRefuseReason(){
	var pk=$("#refuseReasonid").val();
	var row = $('#marrieddealAuditId').bootstrapTable('getRowByUniqueId', pk);
	var parm = {
			marrieddealId: row.marrieddealId,
			auditStatus:3,
			refuseReason:$("#refuseReasons").val()
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
					$("#refuseReasons_quxiao").click();
					$('#marrieddealAuditId').bootstrapTable('refresh');
				}

			}
		});
	
}

function submit() {
 
	$.ajax({
		type : "POST",
		url : './editMarrieddeal.do',
		data : {
			pk : $("#pk").val(),
			auditStatus : $('input:radio[name="auditstatus"]:checked').val(),
			status:5
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});  
			if (data.success) {
				$("#quxiao").click();
				$('#marrieddealAuditId').bootstrapTable('refresh');
			}
		}
	});
}
function findMarri(s){
	if(s==0){
		$("#auditStatus").val("");
	}else{
		$("#auditStatus").val(s);
	}
	var cfg = {
			url : './marrieddealAudit_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'marrieddealId',
			sortName : 'marrieddealId',
			sortOrder : 'asc',
			toolbar : toolbar
		};
	createDataGrid('marrieddealAuditId', cfg);
}
function edit(pk){
	$("#marrieddealId").val(pk);
	$('#editMarrId').modal();

	$('#editGoods').modal();
	if (null != pk && '' != pk) {
		  $.ajax({
		        type: 'GET',
		        url:  './GetMarrieddealAudit.do',
		        data: {
		            'pk': pk
		        },
		        dataType: 'json',
		        success: function(data) {
		        	$("#marrieddealId").val(data.marrieddealId);
		        	$("#name").val(data.name);
		        	var comParentPk=data.comParentPk;//子公司父节点是否为null 或者空
		        	var pidPk="";
		        	var pidName="";
		        	if(comParentPk){//有子公司
		        		pidPk=data.compPk;
		        		pidName=data.compName;
		        		$.post(getRootPath()+"/searchChildrenCompanys.do",{parentPk:comParentPk},function(data2){
		        			var da=data2;
							if(data2.length>0){
								 
								 var html = "<option value=''>--请选择子公司--</option>";
								 for(var i =0;i<data2.length;i++){
									 if(data2[i].pk==data.compPk){
											html +="<option value="+data2[i].pk+" selected>"+data2[i].name+"</option>";
									 }else{
										 html +="<option value="+data2[i].pk+">"+data2[i].name+"</option>";
									 }
									
								}
								 
								$("#companyPk").empty().append(html);
							}
						},"json"); 
		        	}else{
		        		pidPk=data.comParPk;
		        		pidName=data.comParName;
		        	}
		        		 $("button[data-id=companyPID] span.filter-option").html(data.comParName); 
		        		 $("button[data-id=companyPID]").attr("title",data.comParName); 
						 $("#companyPID").val(data.comParPk);	
				//		 $("#marrieddealId").val(data.marrieddealId);	
		        	
					
//						 $.post(getRootPath()+"/searchBrandByCompany.do",{companyPk:data.companyPk},function(data1){
//							 var html = "<option value=''>--请选择品牌--</option>";
//							if(data1.length>0){
//								 for(var i =0;i<data1.length;i++){
//								 if(data1[i].pk==data.brandPk){
//										html +="<option value="+data1[i].pk+" selected>"+data1[i].name+"</option>";
//								 }else{
//										html +="<option value="+data1[i].pk+">"+data1[i].name+"</option>";
//								 }
//								
//								}
//							 
//								
//							}
//							$("#brandPk").empty().append(html);
//						},"json"); 
//						 $.post(getRootPath()+"/searchProductByCompany.do",{companyPk:data.companyPk},function(data11){
//							 var html = "<option value=''>--请选择品名--</option>";
//							if(data11.length>0){
//								 for(var i =0;i<data11.length;i++){
//									 if(data11[i].pk==data.productPk){
//											html +="<option value="+data11[i].pk+" selected>"+data11[i].name+"</option>";
//									 }else{
//											html +="<option value="+data11[i].pk+">"+data11[i].name+"</option>";
//									 }
//									 
//								}
//							 
//								
//							}
//							$("#productPk").empty().append(html);
//						},"json"); 
					 $("button[data-id=addSpecPk] span.filter-option").html(data.specName); 
	        		 $("button[data-id=addSpecPk]").attr("title",data.specName); 
					 $("#addSpecPk").val(data.specPk);	
					 
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
									$("#addSeedvarietyPk").empty();
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
									$("#addSeedvarietyPk").selectpicker('refresh');//
								}
							});
						 var st=data.startTime;
						 var et=data.endTime;
						 $("#buyCounts").val(data.buyCounts);
						 $("#starttime").val(st);
						 $("#endtime").val(et);
						 $("#price").val(data.price);
					
		        }
		    });
	  
		 
 
	} else {
		$("#pk").val('');
    	$("#name").val('');
    		 $("button[data-id=companyPID] span.filter-option").html("--请选择--"); 
    		 $("button[data-id=companyPID]").attr("title","--请选择--"); 
			 $("#companyPID").val('');	
		
     
		
	
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
			 
			 $("button[data-id=addSeedvarietyPk] span.filter-option").html("--请选择子品种--"); 
    		 $("button[data-id=addSeedvarietyPk]").attr("title","--请选择子品种--"); 
			 $("#addSeedvarietyPk").val('');
			 
			 $("#buyCounts").val('');
			 $("#starttime").val('');
			 $("#endtime").val('');
			 
		
    }


	 

}
function changeCompay(){
	 $("#companyPk").empty();
//	 $("#brandPk").empty();
//	 $("#productPk").empty();
	 
	 searchChildrenCompanys();
	 
//	 var companyPid=$("#companyPID").val();
//	 searchBrandByCompany(companyPid);//母公司名下品牌
//	 searchProductByCompany(companyPid);
	  
}
function searchChildrenCompanys(){
	 $.post(getRootPath()+"/searchChildrenCompanys.do",{parentPk:$("#companyPID").val()},function(data){
		 var html = "<option value=''>--请选择子公司--</option>";	
		 if(data.length>0){
				 for(var i =0;i<data.length;i++){
					html +="<option data-contacts="+data[i].contacts+" data-contactsTel="+data[i].contactsTel+" value="+data[i].pk+">"+data[i].name+"</option>";
				}
			}
		 $("#companyPk").empty().append(html);
		},"json"); 
}
//function changeChildCompany(){
//    var companypk=$("#companyPk").val();
//    $("#brandPk").empty();
//    $("#productPk").empty();
//    searchBrandByCompany(companypk);//子公司名下品牌
//    searchProductByCompany(companypk);
//}
function searchBrandByCompany(companyPid){//公司名下品牌
	$.post(getRootPath()+"/searchBrandByCompany.do",{companyPk:companyPid},function(data1){
		 var html = "<option value=''>--请选择品牌--</option>";
		if(data1.length>0){
			 for(var i =0;i<data1.length;i++){
				html +="<option value="+data1[i].pk+">"+data1[i].name+"</option>";
			}
		 
			
		}
		$("#brandPk").empty().append(html);
	},"json"); 
}
function searchProductByCompany(companyPid){//公司名下品名
	$.post(getRootPath()+"/searchProductByCompany.do",{companyPk:companyPid},function(data1){
		 var html = "<option value=''>--请选择品名--</option>";
		if(data1.length>0){
			 for(var i =0;i<data1.length;i++){
				html +="<option value="+data1[i].pk+">"+data1[i].name+"</option>";
			}
		 
			
		}
		$("#productPk").empty().append(html);
	},"json"); 
}
 function AddChangeSpec(){
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
function saveMarrieddeal(){
	var sTime=$("#starttime").val();
	var eTime=$("#endtime").val();
	if(sTime==null||sTime==''){
		new $.flavr({
			modal : false,
			content : "请输入开始时间"
		}); /* Closing the dialog */
		return ;
	}
	if(eTime==null||eTime==''){
		new $.flavr({
			modal : false,
			content : "请输入结束时间时间"
		}); /* Closing the dialog */
		return ;
	}
	
	 var companypid=$("#companyPID").val();//父
	 var companyPk=$("#companyPk").val();//子
	 var companyName=$("button[data-id=companyPID]").attr("title");
	 var contacts="";
	 var contactsTel="";
	 if(companyPk!=null&&companyPk.length>0){
		    var obj=document.getElementById("companyPID");  
			var index=obj.selectedIndex;  
			contacts=obj.options[index].getAttribute("data-contacts");  
			contactsTel=obj.options[index].getAttribute("data-contactsTel");  
		 companypid = companyPk;
	     companyName=$("#companyPk option:selected").text();
	 }else{
		   /* var obj=document.getElementById("companyPk");  
			var index=obj.selectedIndex;  
			contacts="";  
			contactsTel="";  
		 companyName=$("#companyPk option:selected").text();*/
	 }
	 var brandpk=$("#brandPk").val();
	 var brandName= "";
	 if(brandpk!=null&&brandpk.length>0){
		 brandName=$("#brandPk option:selected").text();
		// brandName=$("button[data-id=brandPk]").attr("title");
	 }
	 var productPk=$("#productPk").val();
	 var productName="";
	 if(productPk!=null&&productPk.length>0){
		 productName= $("#productPk option:selected").text();
	//	 productName=$("button[data-id=productPk]").attr("title");
	 } 
	 var specPk=$("#addSpecPk").val();
	 var specName="";
	 if(specPk!=null&&specPk.length>0){
		 specName=$("button[data-id=addSpecPk]").attr("title");
	 }
	 
	 var seriesName="";
	 var seriesPk=$("#addSeriesPk").val();
	 if(seriesPk!=null&&seriesPk.length>0){
		 seriesName=$("#addSeriesPk option:selected").text();
	 }
	 
	 var gradePk=$("#gradePk").val();
	 var gradeName="";
	 if(gradePk!=null&&gradePk.length>0){
		 gradeName=$("button[data-id=gradePk]").attr("title");
	 }
	 
	 var varietiesPk=$("#addVarietiesPk").val();
	 var varietiesName="";
	 if(varietiesPk!=null&&varietiesPk.length>0){
		 varietiesName=$("button[data-id=addVarietiesPk]").attr("title");
	 }
	 var seedvarietyPk=$("#addSeedvarietyPk").val();
	 var seedvarietyName="";
	 if(seedvarietyPk!=null&&seedvarietyPk.length>0){
		 seedvarietyName=$("button[data-id=addSeedvarietyPk]").attr("title");
	 }
	 var price = $("#price").val();
	   if(!regex(price)){
			new $.flavr({
				modal : false,
				content : "价格需输入正确的数字"
			}); /* Closing the dialog */
			return ;
		}
		if(valid("#dataForm")){
			$.ajax({
				type : "POST",
				url : './saveMarrieddeal.do',
				data : {
					pk : $("#pk").val(),
					name : $("#name").val(),
					companyPk :companypid,
					companyName:companyName,
					contactsTel:contactsTel,
					contacts:contacts,
				//	marrieddealId:$("#marrieddealId").val(),
					brandPk:brandpk,
					brandName: brandName,
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
					startTime : new Date(Date.parse($("#starttime").val())),
					endTime :  new Date(Date.parse($("#endtime").val())),
					buyCounts:$("#buyCounts").val(),
					price:price
				},
				dataType : "json",
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiao").click();
						$('#marrieddealAuditId').bootstrapTable('refresh');
					}
				}
			});
			}
	 
}