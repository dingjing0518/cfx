$(function() {
	createDataGrid('economicsBankId', cfg);
});

var toolbar = [];
var columns = [
       		{
       			title : '操作',
       			field : 'pk',
       			width : 100,
       			formatter : function(value, row, index) {
       				var str = "";
    				 str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="EM_CREDIT_BANK_BTN_EDIT" class="btn btn-warning" onclick="javascript:edit(\'' + value + '\'); ">编辑</button> </a>';
    				 if(row.isVisable==1){
    					 str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="EM_CREDIT_BANK_BTN_DISABLE" class="btn btn-warning" onclick="javascript:editState(\'' + value + '\',2); ">停用 </button> </a>';
    				 }else{
    					 str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="EM_CREDIT_BANK_BTN_ENABLE" class="btn btn-warning" onclick="javascript:editState(\'' + value + '\',1); ">启用 </button> </a>';
    				 }
    				 return str;
       			}
       		},
		{
			title : '银行名称',
			field : 'bankName',
			width : 50,
			sortable : true
		},{
			title : '网关地址',
			field : 'gateway',
			width : 20,
			sortable : true
		},
		{
			title : '授信总金额',
			field : 'creditTotalAmount',
			width : 20,
			sortable : true
		},
		{
			title : '状态',
			field : 'isVisable',
			width : 20,
			sortable : true,
			formatter : function (value, row, index) {
				if(value==1){
					return "启用";
				}else{
					return "禁用";
				} 
			}
		}];
var cfg = {
	url : './economicsBank_data.do'+urlParams(1),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'bankName',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function add(){
	$("#dataForm input").each(function(){
		$(this).val("");
	});
	$("#myModalLabel").html("新增");
	$('#ecnomicsBank').modal();
}

function edit(pk){
	$("#myModalLabel").html("编辑");
	$('#ecnomicsBank').modal();
	var row = $('#economicsBankId').bootstrapTable('getRowByUniqueId',pk);
	var newRow = cloneObj(row);
	
	 for(i in newRow){
		 $("#dataForm").find("input[id='"+i+"']").val(newRow[i]);
	 }
}

/**
 * 停用、启用
 * @param pk
 * @param isVisable
 */
function editState(pk,isVisable) {
	$.ajax({
		type : 'POST',
		url : './saveEconomicsBank.do',
		data : {pk:pk,isVisable:isVisable},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#quxiao").click();
			$('#economicsBankId').bootstrapTable('refresh');
		}
	});
}

/**
 * 保存修改
 */
function submit(){
	if(valid("#dataForm")){
		var parm = {
				pk:$("#pk").val(),
				bankName:$("#bankName").val(),
				gateway:$("#gateway").val(),
				creditTotalAmount:$("#creditTotalAmount").val(),
		};
		$.ajax({
			type : 'POST',
			url : './saveEconomicsBank.do',
			data : parm,
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				$("#quxiao").click();
				$('#economicsBankId').bootstrapTable('refresh');
			}
		});
	}
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './economicsBank_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'bankName',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('economicsBankId', cfg);
}