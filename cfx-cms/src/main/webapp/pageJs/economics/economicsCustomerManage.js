var bankSelect = ""
$(function() {
	createDataGrid('dimensionalityManageId', cfg);
	var sourceHtml = "<option value=''>--请选择--</option>";
	for (var i = 0; i < useResource.length; i++) {
		sourceHtml += "<option value=" + useResource[i].key + ">"
				+ useResource[i].value + "</option>";
	}
	$("#source").empty().append(sourceHtml);
	bankOpion();
});

var toolbar = [];
var hiscolumns = [ {
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
//				if (row.isExitHistory == 1) {
//					str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" style="display:none;" showname="EM_CUST_APPLY_BTN_APPLICATIONRECORD" onclick="javascript:approveHistory(\''
//							+ row.companyPk + '\'); ">申请记录</button>';
//				}
				str += '<button style="display:none;" showname="EM_CUST_APPLY_BTN_SEARCH"  id="editable-sample_new" class="btn btn-primary" data-toggle="modal" onclick="javascript:check(\''
						+ row.companyPk + '\'); ">查询</button>';
				if (row.creditUrl != undefined && row.creditUrl != "") {
					str += '<button id="editable-sample_new" style="display:none;" showname="EM_CUST_APPLY_BTN_DOWN"  class="btn btn-primary" data-toggle="modal" onclick="javascript:downTxt(\''
							+ row.creditUrl + '\'); ">下载</button>';
				}
				if (row.roleName == "jingrongzhuanyuan") {
					if (row.auditStatus == 3 || row.auditStatus == 4) {
						str += '<button id="editable-sample_new" style="display:none;" showname="EM_CUST_APPLY_BTN_HISTORYRECORD"  class="btn btn-primary" data-toggle="modal" onclick="javascript:deal(\''
								+ row.processInstanceId
								+ '\',\''
								+ row.processInstanceId
								+ '\'); ">历史批注</button>';
					}
					if (row.auditStatus == 4) {
						if (row.isHaveBank == 0) {
							str += '<button type="button" onclick="javascript:startApply(\''
									+ value
									+ '\',\''
									+ row.companyPk
									+ '\',\''
									+ row.companyName
									+ '\',\''
									+ row.contacts
									+ '\',\''
									+ row.contactsTel
									+ '\',\'1\'); "  style="display:none;" showname="EM_CUST_APPLY_BTN_SUBMITAUDIT" class="btn btn-primary active" disabled="disabled">提交审核</button>';

						} else {
							str += '<button type="button" onclick="javascript:startApply(\''
									+ value
									+ '\',\''
									+ row.companyPk
									+ '\',\''
									+ row.companyName
									+ '\',\''
									+ row.contacts
									+ '\',\''
									+ row.contactsTel
									+ '\',\'1\'); "  style="display:none;" showname="EM_CUST_APPLY_BTN_SUBMITAUDIT" class="btn btn-primary active">提交审核</button>';
						}
					}

                    str += '<button type="button" onclick="javascript:perfectDatum(\''
                        + value
                        + '\',\''
                        + row.companyPk
                        + '\',\''
                        + row.companyName
                        + '\',\''
                        + row.contacts
                        + '\',\''
                        + row.contactsTel
                        + '\',\''+ row.processInstanceId+ '\',\''+ row.auditStatus+ '\',\''+ row.goodsName+ '\'); "  style="display:none;" showname="EM_CUST_APPLY_BTN_PERFECTADTUM" class="btn btn-primary active">完善资料</button>';

					if (row.auditStatus == 1) {
						if (row.isHaveBank == 0) {
							str += '<button type="button" onclick="javascript:startApply(\''
									+ value
									+ '\',\''
									+ row.companyPk
									+ '\',\''
									+ row.companyName
									+ '\',\''
									+ row.contacts
									+ '\',\''
									+ row.contactsTel
									+ '\'); "  style="display:none;" showname="EM_CUST_APPLY_BTN_SUBMITAUDIT" class="btn btn-primary active" disabled="disabled">提交审核</button>';
						} else {
							str += '<button type="button" onclick="javascript:startApply(\''
									+ value
									+ '\',\''
									+ row.companyPk
									+ '\',\''
									+ row.companyName
									+ '\',\''
									+ row.contacts
									+ '\',\''
									+ row.contactsTel
									+ '\'); "  style="display:none;" showname="EM_CUST_APPLY_BTN_SUBMITAUDIT" class="btn btn-primary active">提交审核</button>';
						}
					}
				}

				if (row.roleName == "fengkongzhuguan") {

					if (row.auditStatus == 3 || row.auditStatus == 4) {
						str += '<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" style="display:none;" showname="EM_CUST_APPLY_BTN_HISTORYRECORD" onclick="javascript:deal(\''
								+ row.processInstanceId
								+ '\'); ">历史批注</button>';
					}

					str += '<button type="button" onclick="javascript:assigningUse(\''
							+ value
							+ '\'); "  style="display:none;" showname="EM_CUST_APPLY_BTN_DISCUSTOMER" class="btn btn-primary active">分配客户</button>';
				}

				str += '<button type="button" onclick="javascript:exportImprovingdata(\''
						+ value
						+ '\',\''
						+ row.companyName
						+ '\'); " style="display:none;"  showname="EM_CUST_APPLY_MG_BTN_EXPORT" class="btn btn-primary active">导出</button>';

				str += '<button id="editable-sample_new" style="display:none;" showname="EM_CUST_APPLY_BTN_IMPORT"  class="btn btn-primary" data-toggle="modal" onclick="javascript:importDatumFile(\''
						+ row.companyPk
						+ '\',\''
						+ row.companyName
						+ '\',\''+ row.processInstanceId+ '\'); ">导入</button>';

				return str;
			}
		}, {
			title : '公司名称',
			field : 'companyName',
			width : 20,
			sortable : true
		}, {
			title : '联系人',
			field : 'contacts',
			width : 20,
			sortable : true

		}, {
			title : '联系电话',
			field : 'contactsTel',
			width : 20,
			sortable : true
		}, {
			title : '申请产品',
			field : 'goodsName',
			width : 20,
			sortable : true
		}, {
			title : '提交时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}, {
			title : '客户来源',
			field : 'source',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "盛虹推荐";
				} else if (value == 2) {
					return "自主申请";
				} else if (value == 3) {
					return "新凤鸣推荐";
				} else if (value == 4) {
					return "营销推荐";
				} else if (value == 5) {
					return "其他供应商推荐";
				}else if(value == 6){
					return  "CMS后台申请";
				}
			}
		},{
			title : '处理时间',
			field : 'updateTime',
			width : 20,
			sortable : true
		}, {
			title : '审批状态',
			field : 'auditStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "待审批";
				} else if (value == 2) {
					return "审批中";
				} else if (value == 3) {
					return "已审批";
				} else if (value == 4) {
					return "驳回";
				}
			}
		}, {
			title : '金融专员',
			field : 'assidirName',
			width : 20,
			sortable : true
		}, {
			title : '统计时间',
			field : 'staticTime',
			width : 20,
			sortable : true
		} ];
var cfg = {
	url : './searchEconCustList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

function isVisable(pk, isVisable) {
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

/**
 * 跳转申请记录列表
 * 
 * @param companyPk
 */
function approveHistory(companyPk) {
	window.location.href = getRootPath() + "/approveHistory.do?companyPk="
			+ companyPk;
}

/**
 * 历史批注子页面列表
 * 
 * @param processInstanceId
 */
function deal(processInstanceId) {

	var hiscfg = {
		url : './searchEconCustAuditDetailList.do?processInstanceId='+ processInstanceId,
		columns : hiscolumns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		onLoadSuccess : btnList
	};

	createDataGrid('histroyId', hiscfg);
	$('#editId').modal();
}
function SearchClean(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchEconCustList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('dimensionalityManageId', cfg);
}

/**
 * 查询银行，选择框展示
 */
function bankOpion() {
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
 * 
 * @param pk
 */
function assigningUse(pk) {
	$("#customerGoodsBankDiv").html("");
	var contant = ""
	$
			.ajax({
				type : "POST",
				url : "./searchCustomerGoods.do",
				data : {
					economicsCustomerPk : pk
				},
				dataType : "json",
				success : function(data) {
					if (data.length > 0) {
						for (var j = 0; j < data.length; j++) {
							// 银行选择框
							var select = '<select name="economicsBankPks" class="selectpicker bla bla bli"><option value="">--请选择--</option>';
							for (var i = 0; i < bankSelect.length; i++) {
								if (data[j].bankPk == "") {
									select = select + '<option value="'
											+ bankSelect[i].pk + '">'
											+ bankSelect[i].bankName
											+ '</option>';
								} else {
									if (data[j].bankPk == bankSelect[i].pk) {
										select = select + '<option value="'
												+ bankSelect[i].pk
												+ '"  selected = "selected" >'
												+ bankSelect[i].bankName
												+ '</option>';
									} else {
										select = select + '<option value="'
												+ bankSelect[i].pk + '">'
												+ bankSelect[i].bankName
												+ '</option>';
									}
								}
							}
							select = select + '</select>';
							contant = contant
									+ '<input type="hidden" name="customerGoodsPk" value = "'
									+ data[j].pk
									+ '" /><div class="form-group col-ms-12" style="height: 30px; display: block;">'
									+ '<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">'
									+ data[j].economicsGoodsName + '</label>'
									+ '<div class="col-sm-8">' + select
									+ '</div></div>'
						}
						$("#customerGoodsBankDiv").html(contant);
					}
				}
			});

	$("button[data-id=source] span.filter-option").html("--请选择--");
	$("button[data-id=source]").attr("title", "--请选择--");
	$("button[data-id=assidirPk] span.filter-option").html("--请选择--");
	$("button[data-id=assidirPk]").attr("title", "--请选择--");

	$('#customersDimensionalityId').modal();
	$("#pk").val(pk);
	var data1 = $('#dimensionalityManageId').bootstrapTable('getRowByUniqueId',
			pk);
	var sourceName = "";
	if (data1.source == 1) {
		sourceName = "盛虹推荐";
	} else if (data1.source == 2) {
		sourceName = "自主申请";
	} else if (data1.source == 3) {
		sourceName = "新凤鸣推荐";
	} else if (data1.source == 4) {
		sourceName = "营销推荐";
	} else if (data1.source == 5) {
		sourceName = "其他供应商推荐";
	} else if (data1.source == 6) {
		sourceName = "CMS后台申请";
	} else if (data1.source == "" || data1.source == undefined) {
		sourceName = "--请选择--";
	}

	$('#source').val(data1.source);
	$("button[data-id='source']").children().eq(0).text(sourceName);
	$('#assidirPk').val(data1.assidirPk);
	$("button[data-id='assidirPk']")
			.children()
			.eq(0)
			.text(
					data1.assidirName == "" || data1.assidirName == undefined ? "--请选择--"
							: data1.assidirName);

}

/**
 * 审批状态Table切换
 * 
 * @param s
 */
function findStatus(s) {
	if (s != 0) {
		$("#auditStatus").val(s);
	} else {
		$("#auditStatus").val('');
	}
	var cfg = {
		url : './searchEconCustList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('dimensionalityManageId', cfg);
}
function submit() {
	var name = "";
	var type = $("#name").val();
	if (type.length > 0) {
		name = $("#name option:selected").text();
	}
	if (valid("#dataForm")) {
		$.ajax({
			type : "POST",
			url : "./updateDimensionality.do",
			data : {
				pk : $("#pk").val(),
				name : name,
				type : type
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
function submitAssi() {

	var customerGoodsPks = "";
	var economicsBankPks = "";
	var economicsBankNames = "";
	$("input[name='customerGoodsPk']").each(function() {
		customerGoodsPks = customerGoodsPks + $(this).val() + ",";
	})

	$("select[name='economicsBankPks']").each(
			function() {
				economicsBankPks = economicsBankPks + $(this).val() + ",";
				economicsBankNames = economicsBankNames
						+ $(this).find("option:selected").text() + ",";
			})

	if (economicsBankPks.length > 0) {
		for (var i = 0; i < economicsBankPks.length; i++) {
			if (economicsBankPks[i] == "") {
				new $.flavr({
					modal : false,
					content : "产品所属银行不能为空！"
				});
			}
		}
	}

	if (valid("#dataForm")) {
		$.ajax({
			type : "POST",
			url : "./updateEconomicsAssidirName.do",
			data : {
				pk : $("#pk").val(),
				assidirPk : $("#assidirPk").val(),
				assidirName : $("button[data-id='assidirPk']").children().eq(0)
						.text(),
				bankPk : $("#bankPk").val(),
				bankName : $("button[data-id='bankPk']").children().eq(0)
						.text(),
				source : $("#source").val(),
				economicsBankPksStr : economicsBankPks,
				customerGoodsPksStr : customerGoodsPks,
				economicsBankNamesStr : economicsBankNames,
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
 * 跳转完善资料页面
 * 
 * @param pk
 * @param companyPk
 * @param companyName
 * @param contacts
 * @param contactsTel
 */
function perfectDatum(pk, companyPk, companyName, contacts, contactsTel,processInstanceId,auditStatus,goodsName) {
	window.location.href = getRootPath() + "/perfectCustomerDatum.do?pk=" + pk
			+ "&companyPk=" + companyPk + "&companyName=" + companyName
			+ "&contacts=" + contacts + "&contactsTel=" + contactsTel+"&processInstanceId="
		    +processInstanceId+"&auditStatus="+auditStatus+"&goodsName="+encodeURI(goodsName);
}

/**
 * 提交审核
 * 
 * @param pk
 * @param companyPk
 * @param companyName
 * @param contacts
 * @param contactsTel
 * @param isReApply
 */
function startApply(pk, companyPk, companyName, contacts, contactsTel,
		isReApply) {

	$.ajax({
		type : "POST",
		url : "./startApply.do",
		data : {
			pk : pk,
			isReApply : isReApply
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
 * 
 * @param pk
 * @param companyPk
 * @param companyName
 * @param contacts
 * @param contactsTel
 */
function submitAudit(pk, companyPk, companyName, contacts, contactsTel) {
	window.location.href = getRootPath() + "/economicsCustomerAudit.do?pk="
			+ pk + "&companyPk=" + companyPk + "&companyName=" + companyName
			+ "&contacts=" + contacts + "&contactsTel=" + contactsTel;
}

/*
 * 查看
 */
function check(companyPk) {

	$('#checkFile').modal();
	$('#companyPk').val(companyPk);
	$('#shotName').val("");
	$("#shotName").selectpicker('refresh');

}

function checkFile() {
	$('#checkFile').modal();
	if (valid("#dataForm1")) {
		$.ajax({
			type : "POST",
			url : "./checkEconCustomerFile.do",
			data : {
				companyPk : $('#companyPk').val(),
				shotName : $('#shotName').find("option:selected").attr("data")
			},
			dataType : "json",
			success : function(data) {

				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.code == 0000) {
					$("#quxiaoFile").click();
				}
			}
		});
	}
}






/*
 * 下载
 */
function downTxt(url) {
	window.location.href = getRootPath() + "/download.do?url=" + url;
}

/**
 * 导出
 * @param econCustmerPk
 * @param companyName
 */
function exportImprovingdata(econCustmerPk,companyName) {
	$('#exportFileId').modal();
	$('#econCustmerPk').val(econCustmerPk);
	$('#companyName').val(companyName);
	$('input:radio[name="fileType"][value="XLS"]').prop('checked', true);
}

function  submitExport () {
	$.ajax({
		type : "POST",
		url : "./exportImprovingdata.do",
		data : {
			companyName : $('#companyName').val(),
			economCustPk : 	$('#econCustmerPk').val(),
			fileType  :	$('input:radio[name="fileType"]:checked').val()
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
				$("#quxiaoExport").click();
		}
	});
}

function importDatumFile(companyPk, companyName,processInstanceId) {

	$('#importDatumId').modal();
	$('#importCompanyPk').val(companyPk);
    $('#processInstanceId').val(processInstanceId);
	$("#updateFile").val("");
}

function submitImport() {

	var companyPk = $('#importCompanyPk').val();
    var processInstanceId = $('#processInstanceId').val();
	var file = document.getElementById("updateFile").files;
	if (file.length == 0) {
		new $.flavr({
			modal : false,
			content : "请选择要导入的excel文件！"
		});
		return;
	}
	// FormData 对象
	var form = new FormData();// 可以增加表单数据
	var str = file[0].name;
	if (file[0] == undefined || str.indexOf("xls") == -1
			&& str.indexOf("xlsx") == -1) {
		new $.flavr({
			modal : false,
			content : "请选择要导入的excel文件！"
		});
		return;
	}
	form.append("filename", file[0]);// 文件对象
	$.ajax({
		type : "POST",
		url : './importPerfectCustDatumScore.do?companyPk=' + companyPk+"&processInstanceId="+processInstanceId,
		data : form,
		// 关闭序列化
		processData : false,
		dataType : "json",
		contentType : false,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success){
                $("#quxiaoModal").click();
                $('#importDatumId').bootstrapTable('refresh');
			}
		}
	});
	$("#updateFile").val("");
}


function exportAllDatumFile() {
	$('#exportAllFileId').modal();
	$('input:radio[name="allfileType"][value="XLS"]').prop('checked', true);
}




function  submitAllExport () {
	$.ajax({
		type : "POST",
		url : './exportImprovingdata.do'+urlParams(1)+'&fileType='+	$('input:radio[name="allfileType"]:checked').val(),
		data : {},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
				$("#quxiaoAllExport").click();
		}
	});
}
