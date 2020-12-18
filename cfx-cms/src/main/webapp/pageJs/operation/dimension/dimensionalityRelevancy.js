$(function() {
	createDataGrid('dimensionalityManageId', cfg);
	GetRequest('type');
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
					title : '操作',
					field : 'pk',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); " style="display:none;" showname="OPER_MSS_DIMENSION_TYPE_BTN_ENABLE" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); " style="display:none;" showname="OPER_MSS_DIMENSION_TYPE_BTN_DISABLE" class="btn btn-primary active">禁用</button>';
							}
							str += '<button type="button" onclick="javascript:edit(\'' + value + '\',\'' + row.dimenCategory + '\',\'' + row.dimenName + '\',\'' + row.dimenType + '\',\'' + row.dimenTypeName + '\'); " style="display:none;" showname="OPER_MSS_DIMENSION_TYPE_BTN_EDIT" class="btn btn-primary active">编辑</button>';
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
	url : './searchDimensionalityRelevancyList.do?dimenCategory='+GetRequest('type'),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};
//console.log('1111111111',columns);
////console.log($("#dimensionalityManageId").find('tbody'));
//$("#dimensionalityManageId").mouseover(function(){
//	$("#dimensionalityManageId").find('tbody tr td:nth-child(5)').wrapInner("<a href='#' data-toggle='tooltip' title='columns.linkUrl' style='text-decoration:none;color:#666;'></a>");
//});
//$("#dimensionalityManageId").one("mouseenter",function(){
//	var value= $("#dimensionalityManageId").find('tbody tr td:nth-child(5)').text();
//	$("#dimensionalityManageId").find('tbody tr td:nth-child(5)').wrapInner("<a href='#' data-toggle='tooltip' title='"+value+"' style='text-decoration:none;color:#666;'></a>");
//	return
//})
function isVisable(pk,isVisable) {
			$.ajax({
				type : 'POST',
				url : './updateDimensionalityRelevancy.do',
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
						$('#dimensionalityManageId').bootstrapTable('refresh');
					}

				}
			});
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchDimensionalityRelevancyList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('dimensionalityManageId', cfg);
}
function insertDimensionalityRe(){
	 $('#editDimensionalityREId').modal();
	 $("#pk").val('');
	 var html = "<option value=''>--请选择--</option>";
	 for(var i = 0; i< dimansionality_account.length; i++){
		 if(dimansionality_account[i].type == $("#dimenCategory").val()){
			 var dimenType = dimansionality_account[i].list;
			 for(var n = 0; n< dimenType.length; n++){
				 html +="<option value="+dimenType[n].key+">"+dimenType[n].value+"</option>";
			 }
		 }
		}
	$("#dimenType").empty().append(html); 
}
function edit(pk,dimenCategory,dimenName,dimenType,dimenTypeName){
	$('#editDimensionalityREId').modal();
	$("#pk").val(pk);
	$("#dimenName").val(dimenName);
	$("#dimenCategory").val(dimenCategory);
	var html = "<option value=''>--请选择--</option>";
		for(var i = 0; i< dimansionality_account.length; i++){
			if(dimansionality_account[i].type == dimenCategory){
				var dimenTypes = dimansionality_account[i].list;
				for(var n = 0; n< dimenTypes.length; n++){
					if(dimenTypes[n].key == dimenType){
						html +="<option value="+dimenTypes[n].key+" selected>"+dimenTypes[n].value+"</option>";
					}else{
						html +="<option value="+dimenTypes[n].key+">"+dimenTypes[n].value+"</option>";
					}
				}
			}
	}
	$("#dimenType").empty().append(html);
}

function submit(){
	var dimenTypeName="";
	 var dimenType=$("#dimenType").val();
	 if(dimenType.length>0){
		 dimenTypeName = $("#dimenType option:selected").text();
	 }
	var dimenCategory = $("#dimenCategory").val();
	var dimenName = $("#dimenName").val();
	var linkUrl = $("#linkUrl").val();
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : "./updateDimensionalityRelevancy.do",
			data : {
				pk : $("#pk").val(),
				dimenCategory :dimenCategory,
				dimenName:dimenName,
				dimenType:dimenType,
				dimenTypeName:dimenTypeName,
				linkUrl:linkUrl
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
