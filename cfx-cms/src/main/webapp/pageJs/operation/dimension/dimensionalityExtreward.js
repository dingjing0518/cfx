$(function() {
	createDataGrid('dimensionalityManageIdEx', cfg);
});

var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
						str += '<button type="button" onclick="javascript:del(\'' + value + '\',2); " style="display:none;" showname="OPER_MSS_EXAWARD_BTN_DEL" class="btn btn-danger active">删除</button>';
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); " style="display:none;" showname="OPER_MSS_EXAWARD_BTN_ENABLE" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); " style="display:none;" showname="OPER_MSS_EXAWARD_BTN_DISABLE" class="btn btn-primary active">禁用</button>';
							}
							str += '<button type="button" onclick="javascript:edit(\'' + value + '\',\'' + row.dimenCategory + '\',\'' + row.dimenName + '\',\'' 
							+ row.dimenType + '\',\'' + row.dimenTypeName + '\',\'' + row.periodType + '\',\'' + row.fibreShellNumber + '\',\'' + row.empiricalValue 
							+ '\',\'' + row.numberTimes + '\',\'' + row.conditionType + '\',\'' + row.util + '\',\'' + row.periodTime + '\'); " style="display:none;" showname="OPER_MSS_EXAWARD_BTN_EDIT" class="btn btn-primary active">编辑</button>';
/*							str += '<button type="button" onclick="javascript:presentList(\'' + value + '\',\'' + row.dimenCategory+ '\',\'' + row.dimenType + '\'); " style="display:none;" showname="BTN_DIMENSIONALITYREWARDLIST" class="btn btn-primary active " data-toggle="modal" data-target="#childCompany">赠送列表</button>';
*/						return str;
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
					title : '满足条件',
					field : 'detail',
					width : 20,
					sortable : true
				}, 
				{
					title : '时间范围',
					field : 'timesDetail',
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
	url : './searchDimenExtrewardList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function isVisable(pk,isVisable) {
			$.ajax({
				type : 'POST',
				url : './updateDimenExtreward.do',
				data : {
					 pk : pk,
					 isVisable : isVisable
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#dimensionalityManageIdEx').bootstrapTable('refresh');
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
				url : './delDimenExtreward.do',
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
						$('#dimensionalityManageIdEx').bootstrapTable('refresh');
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
			url : './searchDimenExtrewardList.do'+urlParamsByHeader(1,"ewaijiangli"),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('dimensionalityManageIdEx', cfg);
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
				var html = "<option value=''>--请选择--</option>";
				
				if(data != null && data != ''){
					//var html = "<option value=''>--请选择--</option>";
					for(var m = 0; m < data.length; m++){
						html += "<option value="+data[m].dimenType+">"+data[m].dimenTypeName+"</option>";	
					}
					$("#selectDimenCategory").empty().append(html);
				}
				$("#selectDimenCategory").selectpicker('refresh');//
			}
		});
	}
	

}
function insertDimenReward(){
	 $('#editDimensionalityREIdEx').modal();
	 $("#pk").val('');
	 $("#dimenType").val('');
	 $("#dimenCategory").val('');
	 $("#periodType").val('');
	 $("#fibreShellNumber").val('');
	 $("#conditionType").val('');
	 $("#empiricalValue").val('');
	 $("#util").val('');
	 $("#numberTimes").val('');
	 $("#periodTime").val('');
	/* var html = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< member_dimansionality.length; i++){
			 html +="<option value="+member_dimansionality[i].key+">"+member_dimansionality[i].value+"</option>";
		}
	$("#dimenCategory").empty().append(html); */
	 
	 $("#dimenType").empty().append("<option value=''>--请选择--</option>"); 
	 $("#dimenType").selectpicker('refresh');//
	
	var htmlPeriodType = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< presentPeriodType.length; i++){
		 htmlPeriodType +="<option value="+presentPeriodType[i].key+">"+presentPeriodType[i].value+"</option>";
		}
	$("#periodType1").empty().append(htmlPeriodType); 
	
	var htmlPeriodTime = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< periodTimes.length; i++){
		 htmlPeriodTime +="<option value="+periodTimes[i].key+">"+periodTimes[i].value+"</option>";
		}
	$("#periodTime").empty().append(htmlPeriodTime); 
	
	var htmlCondition = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< conditionTypes.length; i++){
		 htmlCondition +="<option value="+conditionTypes[i].key+">"+conditionTypes[i].value+"</option>";
		}
	$("#conditionType").empty().append(htmlCondition); 
	
	var htmlUtil = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< utilTypes.length; i++){
		 htmlUtil +="<option value="+utilTypes[i].key+">"+utilTypes[i].value+"</option>";
		}
	$("#util").empty().append(htmlUtil); 
}

function changeDimen(){

	
	$.ajax({
		type : "POST",
		url : "./getDimenReListByCategory.do",
		data : {
			type : $("#dimenCategory").val()
		},
		dataType : "json",
		success : function(data) {
			var html = "<option value=''>--请选择--</option>";
			if(data != null && data != ''){
				
				for(var m = 0; m < data.length; m++){
					html += "<option value="+data[m].dimenType+">"+data[m].dimenTypeName+"</option>";	
				}
				
			}
			$("#dimenType").empty().append(html);
			$("#dimenType").selectpicker('refresh');//
		}
	});
}

function edit(pk,dimenCategory,dimenName,dimenType,dimenTypeName,periodType,
		fibreShellNumber,empiricalValue,numberTimes,conditionType,util,periodTime){
	$('#editDimensionalityREIdEx').modal();
	$("#pk").val(pk);
	$("#fibreShellNumber").val(fibreShellNumber);
	$("#empiricalValue").val(empiricalValue);
	$("#numberTimes").val(numberTimes);
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

	
	var htmlPeriodType = "<option value=''>--请选择--</option>";
	 for(var a = 0; a < presentPeriodType.length; a++){
		 if(presentPeriodType[a].key == periodType){
			 htmlPeriodType +="<option value="+presentPeriodType[a].key+" selected>"+presentPeriodType[a].value+"</option>"; 
		 }else{
			 htmlPeriodType +="<option value="+presentPeriodType[a].key+">"+presentPeriodType[a].value+"</option>"; 
		 }
		}
	$("#periodType1").empty().append(htmlPeriodType); 
	
	var htmlPeriodTime = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< periodTimes.length; i++){
		 if(periodTimes[i].key == periodTime){
			 htmlPeriodTime +="<option value="+periodTimes[i].key+" selected>"+periodTimes[i].value+"</option>";
		 }else{
			 htmlPeriodTime +="<option value="+periodTimes[i].key+">"+periodTimes[i].value+"</option>";
		 }
		}
	$("#periodTime").empty().append(htmlPeriodTime); 
	
	var htmlCondition = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< conditionTypes.length; i++){
		 if(conditionTypes[i].key == conditionType){
			 htmlCondition +="<option value="+conditionTypes[i].key+" selected>"+conditionTypes[i].value+"</option>"; 
		 }else{
			 htmlCondition +="<option value="+conditionTypes[i].key+">"+conditionTypes[i].value+"</option>"; 
		 }
		}
	$("#conditionType").empty().append(htmlCondition); 
	
	var htmlUtil = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< utilTypes.length; i++){
		 if(utilTypes[i].key == util){
			 htmlUtil +="<option value="+utilTypes[i].key+" selected>"+utilTypes[i].value+"</option>";
		 }else{
			 htmlUtil +="<option value="+utilTypes[i].key+">"+utilTypes[i].value+"</option>"; 
		 }
		}
	$("#util").empty().append(htmlUtil); 
}

function submit(){
	
	 var dimenName = "";
	 var dimenCategory = $("#dimenCategory").val();
	 if(dimenCategory.length>0){
		 dimenName = $("#dimenCategory option:selected").text();
	 }
	 var dimenTypeName="";
	 var dimenType=$("#dimenType").val();
	 if(dimenType.length>0){
		 dimenTypeName = $("#dimenType option:selected").text();
	 }
	 var periodTimeName="";
	 var periodTime = $("#periodTime").val();
	 if(periodTime.length>0){
		 periodTimeName = $("#periodTime option:selected").text();
	 }
	 var conditionTypeName="";
	 var conditionType = $("#conditionType").val();
	 if(conditionType.length>0){
		 conditionTypeName = $("#conditionType option:selected").text();
	 }
	 var utilName="";
	 var util = $("#util").val();
	 if(util.length>0){
		 utilName = $("#util option:selected").text();
	 }
	 
	 var numberTimes=$("#numberTimes").val();
	 if(!isNotZeroDNumber(numberTimes)||numberTimes>10000){
		 new $.flavr({
				modal : false,
				content : "满足条件数要求范围【1,10000】的整数！"
			});
			return;
	 }
	 
	 //时间范围描述
	 var timesDetail = periodTimeName;
	 //满足条件描述
	 var detail = conditionTypeName+""+numberTimes+""+utilName;
	 

	 var periodType=$("#periodType").val();
	 var periodType1=$("#periodType1").val();

	 var fibreShellNumber = $("#fibreShellNumber").val();
	 if(!isDNumber(fibreShellNumber)||fibreShellNumber>1000000){
		 new $.flavr({
				modal : false,
				content : "纤贝数要求范围【0,1000000】的整数！"
			});
			return;
	 }
	 
	 var empiricalValue = $("#empiricalValue").val();
	 if(!isDNumber(empiricalValue)||empiricalValue>1000000){
		 new $.flavr({
				modal : false,
				content : "经验值要求范围【0,1000000】的整数！"
			});
			return;
	 }
	 
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : "./updateDimenExtreward.do",
			data : {
				pk : $("#pk").val(),
				dimenCategory :dimenCategory,
				dimenName:dimenName,
				dimenType:dimenType,
				dimenTypeName:dimenTypeName,
				periodType:periodType,
				periodTime:periodTime,
				util:util,
				numberTimes:numberTimes,
				timesDetail:timesDetail,
				detail:detail,
				conditionType:conditionType,
				fibreShellNumber:fibreShellNumber,
				empiricalValue:empiricalValue,
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
					$('#dimensionalityManageIdEx').bootstrapTable('refresh');
				}
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
					title : '更新时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				}];




function presentList(pk,dimenCategory,dimenType){
	
	
	$("#dimenCategory1").val(dimenCategory);
	$("#dimenType1").val(dimenType);

	$("#type").val(2);
	var columnNew;
	if(dimenCategory!=2){
		
		 columnNew = [].concat(pcolumns1);
	}else{
		 columnNew = [].concat(pcolumns);
	}
	
	var cfg = {
			url : './searchDimensionalityPresentList.do?'+"dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&type=2",
			columns : columnNew,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : ptoolbar
		};

	createDataGrid('childs', cfg);
	
	
	
	
	
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
	var turl="?dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&periodType="+periodType+"&updateStartTime="+updateStartTime+"&updateEndTime="+updateEndTime+"&contactsTel="+contactsTel+"&companyName="+companyName+"&type=2";
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
	
	var dimenCategory=	$("#dimenCategory").val();

    var dimenType=$("#dimenType").val();
    
    var periodType=$("#periodType2").val();
    
    var type=$("#type").val();
 
    var updateStartTime=$("#updateStartTime").val();
    
    var updateEndTime=$("#updateEndTime").val();
    
    var contactsTel=$("#contactsTel").val();
    
    var companyName=$("#companyName").val();
	
    var turl="?dimenCategory="+dimenCategory+"&dimenType="+dimenType+"&periodType="+periodType+"&updateStartTime="+updateStartTime+"&updateEndTime="+updateEndTime+"&contactsTel="+contactsTel+"&companyName="+companyName+"&type="+2;
	var confirm = new $.flavr(
			{
				content : '确定导出吗?',
				dialog : 'confirm',
				onConfirm : function() {
					confirm.close();
					var exportData = $("#loadingExportData").html(" 正在导出...");
					exportData
							.prepend("<img src='style/images/exportorder.gif' height='20' width='20' />");
					window.location.href = './exportDimensionalityPresentList.do' + turl;
					$("#loadingExportData").html("导出");
				}
			});
}
