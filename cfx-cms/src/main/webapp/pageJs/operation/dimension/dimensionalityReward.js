$(function() {
	createDataGrid('dimensionalityManageId', cfg);
});



var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
						str += '<button type="button" onclick="javascript:del(\'' + value + '\',2); " style="display:none;" showname="OPER_MSS_AWARD_BTN_DEL" class="btn btn-danger active">删除</button>';
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1,\'' + row.dimenCategory + '\',\'' + row.dimenType + '\'); " style="display:none;" showname="OPER_MSS_AWARD_BTN_ENABLE" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2,\'' + row.dimenCategory + '\',\'' + row.dimenType + '\'); " style="display:none;" showname="OPER_MSS_AWARD_BTN_DISABLE" class="btn btn-primary active">禁用</button>';
							}
							str += '<button type="button" onclick="javascript:edit(\'' + value + '\',\'' + row.dimenCategory + '\',\'' + row.dimenName + '\',\'' 
							+ row.dimenType + '\',\'' + row.dimenTypeName + '\',\'' + row.periodType + '\',\'' + row.fibreShellNumber + '\',\'' + row.empiricalValue 
							+ '\',\'' + row.fibreShellRatio + '\',\'' + row.empiricalRatio + '\'); " style="display:none;" showname="OPER_MSS_AWARD_BTN_EDIT" class="btn btn-primary active">编辑</button>';
							//str += '<button type="button" onclick="javascript:presentList(\'' + value + '\',\'' + row.dimenCategory+ '\',\'' + row.dimenType + '\'); " style="display:none;" showname="BTN_DIMENSIONALITYREWARDLIST" class="btn btn-primary active " data-toggle="modal" data-target="#childCompany">赠送列表</button>';
						return str;
					}
				}, 
				{
					title : '维度',
					field : 'dimenName',
					width : 20,
					sortable : true
				}, 
				{
					title : '类型',
					field : 'dimenTypeName',
					width : 20,
					sortable : true
				},
				{
					title : '赠送周期',
					field : 'periodType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "一次赠送";
						} else  if(value==2){
							return "每天";
						}else  if(value==3){
							return "每周";
						}
					}
				}, 
				{
					title : '纤贝数',
					field : 'fibreShellNumber',
					width : 20,
					sortable : true
				}, 
				{
					title : '经验值',
					field : 'empiricalValue',
					width : 20,
					sortable : true
				}, 
				{
					title : '奖励纤贝系数',
					field : 'fibreShellRatio',
					width : 20,
					sortable : true
				}, 
				{
					title : '奖励经验系数',
					field : 'empiricalRatio',
					width : 20,
					sortable : true
				},
				{
					title : '启用/禁用',
					field : 'isVisable',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "启用";
						} else  if(value==2){
							return "禁用";
						}
		
					}
				},{
					title : '更新时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				}];
var cfg = {
	url : './searchDimensionalityRewardList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function isVisable(pk,isVisable,dimenCategory,dimenType) {
			$.ajax({
				type : 'POST',
				url : './updateDimensionalityReward.do',
				data : {
					 pk : pk,
					 isVisable : isVisable,
					 dimenCategory:dimenCategory,
					 dimenType:dimenType
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#dimensionalityManageId').bootstrapTable('refresh');
					}

				}
			});
}

function del(pk,isDelete){
	
	var confirm = new $.flavr({
		content : '确定要删除?',
		dialog : 'confirm',
		onConfirm : function() {
			$.ajax({
				type : 'POST',
				url : './delDimensionalityReward.do',
				data : {
					 pk : pk,
					 isDelete : isDelete
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#dimensionalityManageId').bootstrapTable('refresh');
					}
				}
			});
		}
	});
}

function SearchClean(type){
	if(type == 2){
		cleanpar();
		$("#selectDimenCategory").empty().append("<option value=''>--请选择--</option>");
		$("#selectDimenCategory").selectpicker('refresh');
	}
	
	var cfg = {
			url : './searchDimensionalityRewardList.do'+urlParamsByHeader(1,"jiangli"),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('dimensionalityManageId', cfg);
}
function insertDimenReward(){
	 $('#editDimensionalityREId').modal();
	 $("#pk").val('');
	 $("#dimenType").val('');
	 $("#dimenCategory").val('');
	 $("#periodType").val('');
	 $("#fibreShellNumber").val('');
	 $("#fibreShellRatio").val('');
	 $("#empiricalValue").val('');
	 $("#empiricalRatio").val('');
	/* var html = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< member_dimansionality.length; i++){
			 html +="<option value="+member_dimansionality[i].key+">"+member_dimansionality[i].value+"</option>";
		}
	$("#dimenCategory").empty().append(html); 
	*/
	 
	 $("#dimenType").empty().append("<option value=''>--请选择--</option>"); 
	 $("#dimenType").selectpicker('refresh');//
	 
	 
	var htmlPeriodType = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< presentPeriodType.length; i++){
		 htmlPeriodType +="<option value="+presentPeriodType[i].key+">"+presentPeriodType[i].value+"</option>";
		}
	$("#periodType1").empty().append(htmlPeriodType); 
}

function changeDimen(){
	
	$.ajax({
		type : "POST",
		url : "./getDimenReListByCategory.do",
		data : {
			type : $("#dimenCategory").val()
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data != null && data != ''){
				var html = "<option value=''>--请选择--</option>";
				for(var m = 0; m < data.length; m++){
					html += "<option value="+data[m].dimenType+">"+data[m].dimenTypeName+"</option>";	
				}
				
				$("#dimenType").empty().append(html);
			}
			$("#dimenType").selectpicker('refresh');//
		}
	});
}

function edit(pk,dimenCategory,dimenName,dimenType,dimenTypeName,periodType,fibreShellNumber,empiricalValue,fibreShellRatio,empiricalRatio){
	$('#editDimensionalityREId').modal();
	$("#pk").val(pk);
	$("#fibreShellNumber").val(fibreShellNumber);
	$("#fibreShellRatio").val(fibreShellRatio);
	$("#empiricalValue").val(empiricalValue);
	$("#empiricalRatio").val(empiricalRatio);
	
	$("#dimenCategory").val(dimenCategory=="undefined"?"":dimenCategory);
	
	$.ajax({
		type : "POST",
		url : "./getDimenReListByCategory.do",
		data : {
			type : dimenCategory
		},
		dataType : "json",
		success : function(data) {
			if(data != null && data != ''){
				var html = "<option value=''>--请选择--</option>";
				for(var m = 0; m < data.length; m++){
					
					  html += "<option value="+data[m].dimenType+">"+data[m].dimenTypeName+"</option>";
					
						
				}
				
				$("#dimenType").empty().append(html);
				$("#dimenType").val(dimenType);
		
				
			}
			$("#dimenType").selectpicker('refresh');//
		}
	});

/*	
	if(dimenCategory !="undefined"){
		$("#dimenCategory").empty().append("<option value='"+dimenCategory+"'>"+dimenName+"</option>");
	}
	if(dimenType != "undefined"){
		$("#dimenType").empty().append("<option value='"+dimenType+"'>"+dimenTypeName+"</option>");
	}*/
	
	
	
	var htmlPeriodType = "<option value=''>--请选择--</option>";
	 for(var a = 0; a < presentPeriodType.length; a++){
		 if(presentPeriodType[a].key == periodType){
			 htmlPeriodType +="<option value="+presentPeriodType[a].key+" selected>"+presentPeriodType[a].value+"</option>"; 
		 }else{
			 htmlPeriodType +="<option value="+presentPeriodType[a].key+">"+presentPeriodType[a].value+"</option>"; 
		 }
		}
	$("#periodType1").empty().append(htmlPeriodType); 
}



function submit(){
	 var dimenTypeName="";
	 var dimenType=$("#dimenType").val();
	 if(dimenType.length>0){
		 dimenTypeName = $("#dimenType option:selected").text();
	 }
	 var dimenName = "";
	 var dimenCategory = $("#dimenCategory").val();
	 if(dimenCategory.length>0){
		 dimenName = $("#dimenCategory option:selected").text();
	 }
	 var periodType=$("#periodType").val();
	 var periodType1=$("#periodType1").val();
	 var fibreShellNumber = $("#fibreShellNumber").val();
	 var fibreShellRatio = $("#fibreShellRatio").val();
	 var empiricalValue = $("#empiricalValue").val();
	 var empiricalRatio = $("#empiricalRatio").val();
	 
	 
	 
	 if(!isDNumber(fibreShellNumber)||fibreShellNumber>1000000){
		 new $.flavr({
				modal : false,
				content : "纤贝数要求范围【0,1000000】的整数！"
			});
			return;
	 }
	 
	
	 if(fibreShellRatio<=0  || fibreShellRatio>10 ||  !isFloatTwo(fibreShellRatio)){
		 new $.flavr({
				modal : false,
				content : "奖励纤贝系数要求范围(0,10]，小数位只能有2位！"
			});
			return;
	 }
	 
	 if(!isDNumber(empiricalValue)||empiricalValue>1000000){
		 new $.flavr({
				modal : false,
				content : "经验值要求范围【0,1000000】的整数！"
			});
			return;
	 }
	 
	 if(empiricalRatio<=0 || empiricalRatio>10 || !isFloatTwo(empiricalRatio)){
		 new $.flavr({
				modal : false,
				content : "奖励经验系数要求范围(0,10]，小数位只能有2位！"
			});
			return;
	 }
	 
	 
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : "./updateDimensionalityReward.do",
			data : {
				pk : $("#pk").val(),
				dimenCategory :dimenCategory,
				dimenName:dimenName,
				dimenType:dimenType,
				dimenTypeName:dimenTypeName,
				periodType:periodType,
				fibreShellNumber:fibreShellNumber,
				fibreShellRatio:fibreShellRatio,
				empiricalValue:empiricalValue,
				empiricalRatio:empiricalRatio,
				periodType1:periodType1
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#dimensionalityManageId').bootstrapTable('refresh');
				}
			}
		});
	}
}

function changeDimenSelect(){
	if( $("#selectDimen").val()==""){
		$("#selectDimenCategory").empty().append("<option value=''>--请选择--</option>");
		$("#selectDimenCategory").selectpicker('refresh');//
	}else{
	$.ajax({
		type : "POST",
		url : "./getDimenReListByCategory.do",
		data : {
			type : $("#selectDimen").val()
		},
		dataType : "json",
		success : function(data) {
			if(data != null && data != ''){
				var html = "<option value=''>--请选择--</option>";
				for(var m = 0; m < data.length; m++){
					html += "<option value="+data[m].dimenType+">"+data[m].dimenTypeName+"</option>";	
				}
				
				$("#selectDimenCategory").empty().append(html);
			}
			$("#selectDimenCategory").selectpicker('refresh');
		}
	});
	}
}












var ptoolbar = [];
var pcolumns = [
				{
					title : '公司名称',
					field : 'companyName',
					width : 20,
					sortable : true
				}, 
				{
					title : '电话',
					field : 'contactsTel',
					width : 20,
					sortable : true
				}, 
				{
					title : '维度',
					field : 'dimenName',
					width : 20,
					sortable : true
				}, 
				{
					title : '类型',
					field : 'dimenTypeName',
					width : 20,
					sortable : true
				},
				{
					title : '订单号',
					field : 'orderNumber',
					width : 20,
					sortable : true
				},
				{
					title : '赠送周期',
					field : 'periodType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "一次赠送";
						} else  if(value==2){
							return "每天";
						}else  if(value==3){
							return "每周";
						}
					}
				}, 
				{
					title : '纤贝数',
					field : 'fibreShellNumber',
					width : 20,
					sortable : true
				}, 
				{
					title : '经验值',
					field : 'empiricalValue',
					width : 20,
					sortable : true
				}, 
				{
					title : '纤贝数(加权后)',
					field : 'fbShellNumberWeighting',
					width : 20,
					sortable : true
				}, 
				{
					title : '经验值(加权后)',
					field : 'empiricalValueWeighting',
					width : 20,
					sortable : true
				},
				{
					title : '更新时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				}];



var pcolumns1 = [
				{
					title : '公司名称',
					field : 'companyName',
					width : 20,
					sortable : true
				}, 
				{
					title : '电话',
					field : 'contactsTel',
					width : 20,
					sortable : true
				}, 
				{
					title : '维度',
					field : 'dimenName',
					width : 20,
					sortable : true
				}, 
				{
					title : '类型',
					field : 'dimenTypeName',
					width : 20,
					sortable : true
				},
				{
					title : '赠送周期',
					field : 'periodType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "一次赠送";
						} else  if(value==2){
							return "每天";
						}else  if(value==3){
							return "每周";
						}
					}
				}, 
				{
					title : '纤贝数',
					field : 'fibreShellNumber',
					width : 20,
					sortable : true
				}, 
				{
					title : '经验值',
					field : 'empiricalValue',
					width : 20,
					sortable : true
				}, 
				{
					title : '纤贝数(加权后)',
					field : 'fbShellNumberWeighting',
					width : 20,
					sortable : true
				}, 
				{
					title : '经验值(加权后)',
					field : 'empiricalValueWeighting',
					width : 20,
					sortable : true
				},
				{
					title : '更新时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				}];


function presentList(pk,dimenCategory,dimenType){
	$("#dimenCategory1").val(dimenCategory);
	$("#dimenType1").val(dimenType);
	$("#type").val(1);
	var columnNew;
	if(dimenCategory!=2){
		
		 columnNew = [].concat(pcolumns1);
	}else{
		 columnNew = [].concat(pcolumns);
	}
	
	var cfg = {
			url : './searchDimensionalityPresentList.do?'+"dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&type=1",
			columns : columnNew,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : ptoolbar
		};

	createDataGrid('childs', cfg);
	
	
	
	

/*	window.open(getRootPath()+"/dimensionalityPresent.do?dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&type=1",'_self','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+

			',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');*/
	
	
	//window.showModelessDialog(getRootPath()+"/dimensionalityPresent.do?dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&type=1",window,"dialogWidth:500px;dialogHeight:550px");

	
	
/*	$("#myModal").on("hidden.bs.modal", function() {
	
	    $(this).removeData("bs.modal");
	    $(".modal-body").children().remove();

	});
		$("#myModal").modal({
		
	    remote:getRootPath()+"/dimensionalityPresent.do?dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&type=1"
		
	});*/
	/*window.location.href=getRootPath()+"/dimensionalityPresent.do?dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&type=1";*/
	
}



function SearchCleanP(type){
	if(type == 2){
		$("#t").find("input,select").each(function(){
			if(!$(this).is(":hidden")){
				$(this).val("");
			}
		});
	   $(".filter-option").each(function(){
		   $(this).text("--请选择--");
	   });
	}
	
	var dimenCategory=	$("#dimenCategory1").val();

    var dimenType=$("#dimenType1").val();
    
    var periodType=$("#periodType2").val();
    
   
 
    var updateStartTime=$("#updateStartTime").val();
    
    var updateEndTime=$("#updateEndTime").val();
    
    var contactsTel=$("#contactsTel").val();
    
    var companyName=$("#companyName").val();
	
	
	var columnNew;
	if(dimenCategory!=2){
		
		 columnNew = [].concat(pcolumns1);
	}else{
		 columnNew = [].concat(pcolumns);
	}
	var turl="?dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&periodType="+periodType+"&updateStartTime="+updateStartTime+"&updateEndTime="+updateEndTime+"&contactsTel="+contactsTel+"&companyName="+companyName+"&type=1";
	var cfg = {
			url : './searchDimensionalityPresentList.do'+turl,
			columns : columnNew,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('childs', cfg);
	
	

}


function exportPresent(){
	
	var dimenCategory=	$("#dimenCategory1").val();

    var dimenType=$("#dimenType1").val();
    
    var periodType=$("#periodType2").val();
    
    var type=$("#type").val();
   
 
    var updateStartTime=$("#updateStartTime").val();
    
    var updateEndTime=$("#updateEndTime").val();
    
    var contactsTel=$("#contactsTel").val();
    
    var companyName=$("#companyName").val();
	
    var turl="?dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&periodType="+periodType+"&updateStartTime="+updateStartTime+"&updateEndTime="+updateEndTime+"&contactsTel="+contactsTel+"&companyName="+companyName+"&type="+1;
	var confirm = new $.flavr(
			{
				content : '确定导出吗?',
				dialog : 'confirm',
				onConfirm : function() {
					confirm.close();
					var exportData = $("#loadingExportData").html(" 正在导出...");
					exportData.prepend("<img src='style/images/exportorder.gif' height='20' width='20' />");
					window.location.href = './exportDimensionalityPresentList.do' + turl;
					$("#loadingExportData").html("导出");
				}
			});
}
