

	
var type1=GetRequest('type');

var dimenCategory=GetRequest('dimenCategory');


$(function() {
	
	    btnList();

		createDataGrid('dimensionalityManageIdPre', cfg);
		

		
		if(type1==2){
			$('#dimensionalityManageIdPre').bootstrapTable('hideColumn', 'fbShellNumberWeighting');
			$('#dimensionalityManageIdPre').bootstrapTable('hideColumn', 'empiricalValueWeighting');
		}
		if(dimenCategory==1||dimenCategory==6){
			$('#dimensionalityManageIdPre').bootstrapTable('hideColumn', 'orderNumber');
		}

	
	//$('#dimensionalityManageIdPre').bootstrapTable('showColumn', 'ShopName');
	
		
   // $('#dimensionalityManageIdPre').bootstrapTable('hideColumn', 'companyName');
});
function GetRequest(key) {   
	   var url = location.search; //获取url中"?"符后的字串   
	   var theRequest = new Object();   
	   if (url.indexOf("?") != -1) {   
	      var str = url.substr(1);   
	      strs = str.split("&");   
	      for(var i = 0; i < strs.length; i ++) {   
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
	      }   
	   } 
	   return theRequest[key];   
	}  
var toolbar = [];
var columns = [
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
				/*{
					title : '是否启用',
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
				},*/
				{
					title : '更新时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				}];
var cfg = {
	url : './searchDimensionalityPresentList.do?'+"dimenCategory="+GetRequest('dimenCategory')+"&dimenType="+GetRequest('dimenType')+"&type="+GetRequest('type'),
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
				url : './updateDimensionalityReward.do',
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
						$('#dimensionalityManageIdPre').bootstrapTable('refresh');
					}

				}
			});
}


function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchDimensionalityPresentList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('dimensionalityManageIdPre', cfg);
	
	var type1=$('#type').val();

	var dimenCategory=$('#dimenCategory').val();
	
	
	if(type1==2){
		$('#dimensionalityManageIdPre').bootstrapTable('hideColumn', 'fbShellNumberWeighting');
		$('#dimensionalityManageIdPre').bootstrapTable('hideColumn', 'empiricalValueWeighting');
	}
	if(dimenCategory==1){
		$('#dimensionalityManageIdPre').bootstrapTable('hideColumn', 'orderNumber');
	}

}
function changeDimenSelect(){
	$.ajax({
		type : "POST",
		url : "./getDimenReListByCategory.do",
		data : {
			type : $("#dimenCategory").val()
		},
		dataType : "json",
		success : function(data) {
			if(data != null && data != ''){
				var html = "<option value=''>--请选择--</option>";
				for(var m = 0; m < data.length; m++){
					html += "<option value="+data[m].dimenType+">"+data[m].dimenTypeName+"</option>";	
				}
				
				$("#dimenType").empty().append(html);
			}else{
				$("#dimenType").empty();
			}
			$("#dimenType").selectpicker('refresh');//
		}
	});
}
function exportPresent(){
	
	var confirm = new $.flavr(
			{
				content : '确定导出吗?',
				dialog : 'confirm',
				onConfirm : function() {
					confirm.close();
					var exportData = $("#loadingExportData").html(" 正在导出...");
					exportData
							.prepend("<img src='style/images/exportorder.gif' height='20' width='20' />");
					window.location.href = './exportDimensionalityPresentList.do' + urlParams(1);
					$("#loadingExportData").html("导出");
				}
			});
}

