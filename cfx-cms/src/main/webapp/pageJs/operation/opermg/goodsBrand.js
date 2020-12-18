$(function() {
	var cfg = {
			url : './searchGoodsBrandList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	
	createDataGrid('goodsBrandId', cfg);
	
});



var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 80,
	formatter : function(value, row, index) {
		var str = "";
			str += '<button type="button" onclick="javascript:del(\'' + value + '\'); " style="display:none;" showname="OPER_MG_BRAND_BTN_DEL" class="btn btn-danger active">删除</button>';
			if (row.auditStatus == 1) {
				str += '<button type="button" onclick="javascript:editState(\''
						+ value + '\',\'' + row.isDelete + '\',2); " style="display:none;" showname="OPER_MG_BRAND_BTN_PASS" class="btn btn-warning active">审核通过</button>';
				str += '<button type="button" onclick="javascript:editState(\''
					+ value
					+ '\',\'' + row.isDelete + '\',3); " style="display:none;" showname="OPER_MG_BRAND_BTN_UNPASS" class="btn btn-warning">审核不通过</button>';
			} 

		return str;
		}
	}, 
		{
			title : '公司名称',
			field : 'storeName',
			width : 20,
			sortable : true
			
		},
		{
			title : '厂家品牌',
			field : 'brandName',
			width : 20,
			sortable : true
		},
		{
			title : '品牌简称',
			field : 'brandShortName',
			width : 20,
			sortable : true
		},
		{
			title : '申请状态',
			field : 'auditStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "待审核";
				} else  if(value==2){
					return "审核通过";
				}else if(value==3){
					return "审核不通过";
				}

			}

		}, {
			title : '添加时间',
			field : 'insertTime',
			width : 30,
			sortable : true
		}, {
			title : '审核时间',
			field : 'auditTime',
			width : 30,
			sortable : true
		}];

function findGoodsbrand(s){
	if(s!=0){
		$("#auditStatus").val(s);
	}else{
		$("#auditStatus").val('');
	}
	var cfg = {
			url : './searchGoodsBrandList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('goodsBrandId', cfg);
}

/**
 * 审核通过/不通过
 * @param pk
 * @param isde
 * @param isvi
 */
function editState(pk, isde, isvi) {
	var parm =  {
			'pk' : pk,
			'isDelete' : isde,
			'auditStatus' : isvi
		};
 
	$.ajax({
		type : 'POST',
		url : './updateGoodsBrand.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			$('#goodsBrandId').bootstrapTable('refresh');

		}
	});

}

/**
 * 删除
 * @param pk
 */
function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateGoodsBrand.do',
				data : {
					 pk : pk,
					 isDelete  : 2,
					 block:$("#block").val()
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#goodsBrandId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchGoodsBrandList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('goodsBrandId', cfg);
}

/**
 * 新增
 */
function editGoodsBrand(){
	$('#editGoodsBrand').modal();
	 $("button[data-id=storePk] span.filter-option").html("--请选择--"); 
	 $("button[data-id=storePk]").attr("title","--请选择--"); 
	 $("#companyPk").val('');	
	 $("button[data-id=brandPk] span.filter-option").html("--请选择--"); 
	 $("button[data-id=brandPk]").attr("title","--请选择--"); 
	 $("#brandName").val('');
	 $("#brandShortName").val('');
	 $("#storePk").val("null");
}

/**
 * 保存
 */
function submit(){
	 var storePk=$("#storePk").val();
	 var storeName = $("button[data-id=storePk]").attr("title");
	 var brandName=$("#brandName").val();
	 var block = $("#block").val();
	 if(storePk=="null"){
			new $.flavr({
				modal : false,
				content : "请选择店铺！"
			});
			return;
		}
	 if(brandName.length>50){
			new $.flavr({
				modal : false,
				content : "品牌名称长度不能大于50！"
			});
			return;
		}
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : './insertGoodsBrand.do',
			data : {
				pk : $("#pk").val(),
				storePk :storePk, 
				storeName :storeName, 
				brandName: trim(brandName),
				brandShortName :$("#brandShortName").val(),
				block:block
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#goodsBrandId').bootstrapTable('refresh');
				}
			}
		});
		}
}

/**
 * 去空格
 * @param s
 * @returns {*}
 */
function trim(s){
    return s.replace(/(^\s*)|(\s*$)/g, "");
}
