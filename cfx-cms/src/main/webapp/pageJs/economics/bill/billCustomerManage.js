var bankSelect = ""
$(function() {
	createDataGrid('billCustomerManageId', cfg);
	
	
	var sourceHtml = "<option value=''>--请选择--</option>";
	for(var i = 0; i< useResource.length; i++){
		sourceHtml +="<option value="+useResource[i].key+">"+useResource[i].value+"</option>";
	}
	$("#source").empty().append(sourceHtml);
	bankOpion();
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
          			field : 'fullMessage',
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
						if(row.isExitHistory==1){
							str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" style="display:none;" showname="EM_ECONOMICS_BILL_CUSTOMER_BTN_APPLICATIONRECORD" onclick="javascript:approveHistory(\''+  row.companyPk + '\'); ">申请记录</button>';
						}
						if(row.roleName=="jingrongzhuanyuan"){
							if(row.status == 3 || row.status==4){
								 str += '<button id="editable-sample_new" style="display:none;" showname="EM_ECONOMICS_BILL_CUSTOMER_BTN_HISTORYRECORD"  class="btn btn-primary" data-toggle="modal" onclick="javascript:deal(\''+  row.processInstanceId + '\'); ">历史批注</button>';
							}
							if(row.status==4 ){
								str +='<button type="button" onclick="javascript:startApply(\'' + value + '\',\'' + row.companyPk + '\',\'' + row.companyName + '\',\'' + row.contacts + '\',\'' + row.contactsTel + '\',\'1\'); "  style="display:none;" showname="EM_ECONOMICS_BILL_CUSTOMER_BTN_SUBMITAUDIT" class="btn btn-primary active">提交审核</button>';
							}
							if(row.status==1){
									str +='<button type="button" onclick="javascript:startApply(\'' + value + '\',\'' + row.companyPk + '\',\'' + row.companyName + '\',\'' + row.contacts + '\',\'' + row.contactsTel + '\'); "  style="display:none;" showname="EM_ECONOMICS_BILL_CUSTOMER_BTN_SUBMITAUDIT" class="btn btn-primary active">提交审核</button>';
							}
						}
					
						if(row.roleName=="fengkongzhuguan"){
							if(row.status == 3 || row.status==4){
								 str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" style="display:none;" showname="EM_ECONOMICS_BILL_CUSTOMER_BTN_HISTORYRECORD" onclick="javascript:deal(\''+  row.processInstanceId + '\'); ">历史批注</button>';
							}
							str += '<button type="button" onclick="javascript:assigningUse(\'' + value + '\'); "  style="display:none;" showname="EM_CUST_APPLY_BTN_DISCUSTOMER_BTN_DISCUSTOMER" class="btn btn-primary active">分配客户</button>';
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
					title : '提交时间',
					field : 'insertTime',
					width : 20,
					sortable : true
				},{
					title : '处理时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				},{
					title : '审批状态',
					field : 'status',
					width : 20,
					sortable : true,
					formatter : function (value, row, index) {
						if(value==1){
							return "待审批";
						}else if(value==2){
							return "审批中";
						}else if(value==3){
							return "已审批";
						}else if(value==4){
							return "驳回";
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
	url : './searchBillCustomerApplyList.do',
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
						$('#billCustomerManageId').bootstrapTable('refresh');
					}

				}
			});
}

/**
 * 跳转申请记录列表
 * @param companyPk
 */
function  approveHistory(companyPk){
	window.location.href = getRootPath()+"/billApproveHistoryPage.do?companyPk="+companyPk;
}

/**
 * 历史批注子页面列表
 * @param processInstanceId
 */
function deal(processInstanceId) {

	var hiscfg = {
			url : './searchEconCustAuditDetailList.do?processInstanceId='+processInstanceId,
			columns : hiscolumns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			onLoadSuccess : btnList
		};
	
	createDataGrid('histroyId', hiscfg);
	$('#editId').modal();
}

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchBillCustomerApplyList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('billCustomerManageId', cfg);
}

/**
 * 查询银行，选择框展示
 */
function  bankOpion(){
	$.ajax({
		type : "POST",
		url : "./searchBankList.do",
		data : {},
		dataType : "json",
		success : function(data) {
			bankSelect = data;
		}
	});
	
	
	
}

/**
 * 分配客户
 * @param pk
 */
function assigningUse(pk){
	 $("button[data-id=assidirPk] span.filter-option").html("--请选择--"); 
	 $("button[data-id=assidirPk]").attr("title","--请选择--"); 
	$('#customersDimensionalityId').modal();
	$("#pk").val(pk);
	var data1 = $('#billCustomerManageId').bootstrapTable('getRowByUniqueId',pk);
	$('#assidirPk').val(data1.assidirPk);
	$("button[data-id='assidirPk']").children().eq(0).text(data1.assidirName==""||data1.assidirName==undefined?"--请选择--":data1.assidirName);	
	
}

/**
 * 审批状态Table切换
 * @param s
 */
function findStatus(s) {
	if (s != 0) {
		$("#status").val(s);
	} else {
		$("#status").val('');
	}
	var cfg = {
		url : './searchBillCustomerApplyList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('billCustomerManageId', cfg);
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
					$('#billCustomerManageId').bootstrapTable('refresh');
				}
			}
		});
	}
}


/**
 * 保存分配客户修改
 */
function submitAssi(){
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : "./updateBillCustomer.do",
			data : {
				pk : $("#pk").val(),
				assidirPk:$("#assidirPk").val(),
				assidirName :$("button[data-id='assidirPk']").children().eq(0).text(),
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#billCustomerManageId').bootstrapTable('refresh');
				}
			}
		});
	}
}



/**
 * 提交审核
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
		url : "./startBillApply.do",
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
				$('#billCustomerManageId').bootstrapTable('refresh');
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

/*
 * 查看
 */
function  check(companyPk){
	
	$('#checkFile').modal();
	$('#companyPk').val(companyPk);
	$('#shotName').val("");
	$("#shotName").selectpicker('refresh');
	
}

function  checkFile(){
	$('#checkFile').modal();
	if(valid("#dataForm1")){
	$.ajax({
		type : "POST",
		url : "./checkEconCustomerFile.do",
		data : {
			companyPk : $('#companyPk').val(),
			shotName :$('#shotName').find("option:selected").attr("data")
		},
		dataType : "json",
		success : function(data) {
		
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.code==0000) {
					$("#quxiaoFile").click();
				}
		}
	});
	}
}


/*
 * 下载
 */
function  downTxt(url){
		window.location.href = getRootPath()+"/download.do?url="+url;
}



