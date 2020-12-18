$(function() {
	var cfg = {
		url : './getSysCompanyBankCardByPk.do' + urlParams(1)+"&isCompanyType="+$("#isCompanyType").val()+"&modelType="+$("#modelType").val(),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pageSize : 1000, // 每页的记录行数（*）
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('supplierBankId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				var str = "";
				str += '<button type="button" onclick="javascript:del(\''
						+ value
						+ '\'); " style="display:none;" showname="COMMON_CM_SUPPLIER_ACCOUNT_SUBSUP_BTN_DEL" class="btn btn-danger active">删除</button>';
				str += '<button style="display:none;" id="editable-sample_new" showname="COMMON_CM_SUPPLIER_ACCOUNT_SUBSUP_BTN_EDIT" class="btn btn-primary" data-toggle="modal"  onclick="javascript:edit(\''
						+ value
						+ '\',\''
						+ row.ecbankPk
						+ '\',\''
						+ row.ecbankName
						+ '\',\''
						+ row.bankName
						+ '\',\''
						+ row.bankNo
						+ '\',\''
						+ row.bankAccount
						+ '\'); ">修改</button>';
				return str;
			}
		}, {
			title : '公司名称',
			field : 'companyName',
			width : 20,
			sortable : true
		}, {
			title : '银行名称',
			field : 'ecbankName',
			width : 20,
			sortable : true
		}, {
			title : '开户行名称',
			field : 'bankName',
			width : 20,
			sortable : true
		}, {
			title : '开户行号',
			field : 'bankNo',
			width : 20,
			sortable : true
		}, {
			title : '银行账号',
			field : 'bankAccount',
			width : 30,
			sortable : true
		} ];

/**
 * 实体账户新增
 */
function addBank() {
	$("button[data-id=ecbankPk] span.filter-option").html("--请选择--");
	$("button[data-id=ecbankPk]").attr("title", "--请选择--");
	$('#companyBankCardPk').val("");
	$("#ecbankPk").val(""), 
	$('#creditBankName').val("");
	$('#creditBankNo').val("");
	$('#creditBankAccount').val("");
	$("#myModalLabel").html("新增银行信息");
	$('#supplierBankModel').modal();
}

/**
 * 实体账户编辑
 * @param pk
 * @param ecbankPk
 * @param ecbankName
 * @param bankName
 * @param bankNo
 * @param bankAccount
 */
function edit(pk, ecbankPk, ecbankName, bankName, bankNo, bankAccount) {
	

	if(ecbankPk=='undefined' ||ecbankPk=='' ){
		$("button[data-id=ecbankPk] span.filter-option").html("--请选择--");
		$("button[data-id=ecbankPk]").attr("title", "--请选择--");
		$("#ecbankPk").val("");
	}else{
		$("button[data-id=ecbankPk] span.filter-option").html(ecbankName);
		$("button[data-id=ecbankPk]").attr("title", ecbankName);
		$("#ecbankPk").val(ecbankPk);
	}
	
	$('#creditBankName').val(bankName);
	$('#creditBankNo').val(bankNo);
	$('#creditBankAccount').val(bankAccount);
	$('#companyBankCardPk').val(pk);
	$("#myModalLabel").html("修改银行信息");
	$('#supplierBankModel').modal();
}

/**
 * 实体账户编辑 保存
 */
function creditSubmit() {
	var creditBankName = $('#creditBankName').val();
	var creditBankNo = $('#creditBankNo').val();
	var creditBankAccount = $('#creditBankAccount').val();
	var companyPk = $('#companyPk').val();
	var pk = $('#companyBankCardPk').val();
	
	if(creditBankName==""){
		new $.flavr({
			modal : false,
			content : "开户行名称不能为空！"
		});
		return;
	}
	
	if (!/^\d{1,50}$/.test(creditBankAccount)) {
		new $.flavr({
			modal : false,
			content : "银行卡号50位以内的数字！"
		});
		return;
	}
	
	
	if(creditBankNo==""){
		new $.flavr({
			modal : false,
			content : "开户行不存在！"
		});
		return;
	}

	if (valid("#creditDataForm")) {
		$.ajax({
			type : 'post',
			url : './updateCompanyBankCard.do',
			data : {
				pk : pk,
				companyPk : companyPk,
				bankName : creditBankName,
				bankNo : creditBankNo,
				bankAccount : creditBankAccount,
				ecbankPk : $("#ecbankPk").val(),
				ecbankName :$("#ecbankPk").val()==""?"": $("button[data-id=ecbankPk]").attr("title")
			},
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success != null) {
					if(data.code!='0001'){
						new $.flavr({
							modal : false,
							content : data.msg
						});
						if (data.pk != null && data.pk != "") {
							$('#companyBankCardPk').val(data.pk)
						}
						$("#quxiao").click();
						$('#supplierBankId').bootstrapTable('refresh');
					}else{
						new $.flavr({
							modal : false,
							content : data.msg
						});
					}
					
					
				}
			}
		});
	}
}

/**
 * 实体账户删除
 * @param pk
 */
function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './delSupplierBank.do',
				data : {
					pk : pk,
				},
				dataType : "json",
				success : function(data) {
					$("#quxiao").click();
					$('#supplierBankId').bootstrapTable('refresh');
				}
			});
		}
	});
}

/**
 * 根据输入内容动态查询开户行名称
 */
function isCreditBankName() {
	

	
	var creditBankName = $('#creditBankName').val();
	
	if(creditBankName==""){
		new $.flavr({
			modal : false,
			content : "开户行名称不能为空！"
		});
		return;
	}
	
	$.ajax({
		type : 'post',
		url : './getSysBankNameCodeByBankName.do',
		data : {
			bankname : $('#creditBankName').val()
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data != null && data != "") {
				if (data.success == false) {
					new $.flavr({
						modal : false,
						content : "开户行名称不存在！"
					});
					$('#buttonIsVisableId').attr('disabled', "true");
				} else {
					$('#creditBankNo').val(data.bankno);
					$("#buttonIsVisableId").removeAttr("disabled");
				}
			}
		}
	});
}
