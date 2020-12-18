$(function() {
	createDataGrid('dimensionalityManageId', cfg);
	
	var sourceHtml = "<option value=''>--请选择--</option>";
	for(var i = 0; i< useResource.length; i++){
		sourceHtml +="<option value="+useResource[i].key+">"+useResource[i].value+"</option>";
	}
	$("#source").empty().append(sourceHtml);
});

var toolbar = [];
var hiscolumns = [
          		{
          			title : '审批时间',
          			field : 'time',
          			width : 20,
          			sortable : true
          		}, {
          			title : '审核人',
          			field : 'userId',
          			width : 20,
          			sortable : true
          		}, {
          			title : '审核批注',
          			field : 'message',
          			width : 20,
          			sortable : true
          		} ];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 50,
					formatter : function(value, row, index) {
						var str = "";
							//待受理
							if(row.status == 1 && row.roleName =="fengkongzhuguan"){
								//已分配客户才能进行受理
								if(row.assiFlag == 2){
									str += '<button type="button"  style="display:none;" showname="EM_CUST_APPLY_RECORDS_BTN_DEAL"  onclick="javascript:acceptance(\'' + value + '\'); "   class="btn btn-primary active">受理</button>';
								}else{
									str += '<button type="button" style="display:none;" showname="EM_CUST_APPLY_RECORDS_BTN_DISCUSTOMER"  onclick="javascript:assigningUse(\'' + value + '\'); "   class="btn btn-primary active" >分配客户</button>';
								}
								str += '<button type="button" style="display:none;" showname="EM_CUST_APPLY_RECORDS_BTN_CANCEL"  onclick="javascript:cancel(\'' + value + '\'); "   class="btn btn-primary active">作废</button>';
							}
							
						return str;
					}
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
		
				},{
					title : '联系电话',
					field : 'contactsTel',
					width : 20,
					sortable : true
				},{
					title : '申请产品',
					field : 'goodsName',
					width : 20,
					sortable : true
				},{
					title : '申请时间',
					field : 'insertTime',
					width : 20,
					sortable : true
				},{
					title : '客户来源',
					field : 'source',
					width : 20,
					sortable : true,
					formatter : function (value, row, index) {
						if(value==1){
							return "盛虹推荐";
						}else if(value==2){
							return "自主申请";
						}else if(value==3){
							return "新凤鸣推荐";
						}else if(value==4){
							return "营销推荐";
						}else if(value==5){
							return "其他供应商推荐";
						}else if(value==6){
							return "CMS后台申请";
						}
					}
				},{
					title : '处理时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				},
				{
					title : '受理状态',
					field : 'status',
					width : 20,
					sortable : true,
					formatter : function (value, row, index) {
						if(value==1){
							return "待受理";
						}else if(value==2){
							return "已受理";
						}else if(value==3){
							return "已作废";
						}  
					}
				},{
					title : '金融专员',
					field : 'assidirName',
					width : 20,
					sortable : true
				},{
					title : '统计时间',
					field : 'staticTime',
					width : 20,
					sortable : true
				}];
var cfg = {
	url : './searchRecordsCustList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

function isVisable(pk,isVisable) {
			$.ajax({
				type : 'POST',
				url : './updateDimensionality.do',
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
			url : './searchRecordsCustList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('dimensionalityManageId', cfg);
}

/**
 * 分配客户
 * @param pk
 */
function assigningUse(pk){
	
	$('#customersDimensionalityId').modal();
	$("#pk").val(pk);
	$('#source').val('');
	/*$("button[data-id='source']").children().eq(0).text("--请选择--");*/
	$("#source").selectpicker('refresh');
	$('#bankPk').val('');
	$("button[data-id='assidirPk']").children().eq(0).text("--请选择--");
	$('#assidirPk').val('');
}

/**
 * 申请记录受理状态table切换
 * @param s
 */
function findStatus(s) {
	if (s != 0) {
		$("#status").val(s);
	} else {
		$("#status").val('');
	}
	var cfg = {
		url : './searchRecordsCustList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('dimensionalityManageId', cfg);
}

function submit(){
	var name="";
	 var type=$("#name").val();
	 if(type.length>0){
		 name = $("#name option:selected").text();
	 }
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : "./updateDimensionality.do",
			data : {
				pk : $("#pk").val(),
				name :name,
				type:type
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

/**
 * 保存分配客户修改
 */
function submitAssi(){
	if(validNotHidden("#dataForm")){
		var parm= {
				pk : $("#pk").val(),
				assidirPk:$("#assidirPk").val(),
				assidirName :$("button[data-id='assidirPk']").children().eq(0).text(),
				source:$("#source").val(),
				assiFlag:2	
		};
		$.ajax({
			type : "POST",
			url : "./updateCreditCustomerRecords.do",
			data : parm,
			dataType : "json",
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
	
}

/**
 * 受理
 * @param pk
 */
function acceptance(pk){
	$.ajax({
		type : "POST",
		url : "./accepttanceCustomer.do",
		data : {
			pk : pk
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			}); /* Closing the dialog */
			if (data.success) {
				$('#dimensionalityManageId').bootstrapTable('refresh');
			}
		}
	});
}

/**
 * 作废
 * @param pk
 */
function cancel(pk){
	$.ajax({
		type : "POST",
		url : "./updateCreditCustomerRecords.do",
		data : {
			pk : pk,
			status:3
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			}); /* Closing the dialog */
			if (data.success) {
				$('#dimensionalityManageId').bootstrapTable('refresh');
			}
		}
	});
}

/**
 * 提交金融产品流程申請
 * @param pk
 * @param companyPk
 * @param companyName
 * @param contacts
 * @param contactsTel
 * @param isReApply
 */
function startApply(pk,companyPk,companyName,contacts,contactsTel,isReApply){
	
	$.ajax({
		type : "POST",
		url : "./startApply.do",
		data : {
			pk : pk,
			isReApply:isReApply
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

/**
 * 跳转审批管理页面
 * @param pk
 * @param companyPk
 * @param companyName
 * @param contacts
 * @param contactsTel
 */
function submitAudit(pk,companyPk,companyName,contacts,contactsTel){
	window.location.href = getRootPath()+"/economicsCustomerAudit.do?pk="+pk+"&companyPk="+companyPk+"&companyName="+companyName+"&contacts="+contacts+"&contactsTel="+contactsTel;
}



	
function addApply(pk){
	
	$('#addEconRecordId').modal();
	$("input[name='productPks']:checked").prop("checked",false);
	$('#companyPk').val('');
	$("#companyPk").selectpicker('refresh');
	
}

function submitApply(){

	var  productPks = "";
	var goodsName = ""
	if(valid("#dataFormApply")){
		$("input[name='productPks']:checkbox:checked").each(function(){ 
			if($(this).val() != null && $(this).val() != ''){
				productPks = productPks +  $(this).val()+",";
				goodsName = goodsName +  $(this).parent().text()+",";

			}
		}) 
		if(	$('#companyPk').val()=="null"){
			new $.flavr({
				modal : false,
				content : "请选择公司！"
			});
			return ;
		}
		if(productPks==""){
			new $.flavr({
				modal : false,
				content : "请选择金融产品！"
			});
			return ;
		}
	
	
		var parm= {
				productPks:productPks.substring(0,productPks.length-1),
				goodsName:goodsName.substring(0,goodsName.length-1),
				companyPk:$('#companyPk').val(),
				
		};
		$.ajax({
			type : "POST",
			url : "./insertCreditcustomer.do",
			data : parm,
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$("#quxiaoApply").click();
					$('#dimensionalityManageId').bootstrapTable('refresh');
				}
			}
		});
	}
	

}
