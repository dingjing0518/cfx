$(function() {
	createDataGrid('billSuppSigningId', cfg);

});
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 150,
			formatter : function(value, row, index) {
				var str = "";
				str += '<button type="button" data-toggle="modal" style="display:none;margin:0px 5px!important;" showname="EM_BILL_SUPPSIGNING_BTN_DELETE" class="btn btn-warning" onclick="javascript:apiBillSinging(\''
					+ row.companyPk+ '\');">签约</button></a>';
				if(row.isExtAccount==0){
					str += '<button type="button" data-toggle="modal" style="display:none;margin:0px 5px!important;" showname="EM_BILL_SUPPSIGNING_BTN_SIGNING" class="btn btn-warning" onclick="javascript:del(\''
						+ row.pk+ '\',2);">删除</button></a>';
				}
				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="EM_BILL_SUPPSIGNING_BTN_ACCOUNT" class="btn btn-warning" onclick="javascript:supAccount(\'' +row.companyPk + '\',\''+row.companyName+'\'); ">账户</button> </a>';

				return str;
			}
		}, {
			title : '公司名称',
			field : 'companyName',
			width : 20,
			sortable : true
		}, {
			title : '企业信用代码',
			field : 'organizationCode',
			width : 20,
			sortable : true
		}
];
var cfg = {
	url : './searchBillSuppSigningList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchBillSuppSigningList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('billSuppSigningId', cfg);
}

function addCompany (){
	$('#companyModel').modal();
	$('#companyPk').val('');
	$("#companyPk").selectpicker('refresh');
}


function del(pk , isDelete){
	var parm= {
			isDelete:isDelete,
			pk : pk
		};
	$.ajax({
		type : "POST",
		url : "./updateBillSuppSigning.do",
		data : parm,
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#quxiao").click();
				$('#billSuppSigningId').bootstrapTable('refresh');
			}
		}
	});
}

function submit(){
	if(validNotHidden("#dataForm")){
		var parm= {
			companyPk:$('#companyPk').val(),
			pk:$('#pk').val()
		};
		$.ajax({
			type : "POST",
			url : "./updateBillSuppSigning.do",
			data : parm,
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$("#quxiao").click();
					$('#billSuppSigningId').bootstrapTable('refresh');
				}
			}
		});
	}
	

}


function apiBillSinging (companyPk){
	var parm= {
			companyPk:companyPk
		};
	$.ajax({
		type : "POST",
		url : "./apiBillSinging.do",
		data : parm,
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
				$('#billSuppSigningId').bootstrapTable('refresh');
		}
	});
}

/**
 * 供应商签约账户
 * @param companyPk
 */
function supAccount(companyPk,companyName){
	
    window.location = getRootPath() + "/jumpBillSupSigningAccount.do?companyPk="+companyPk+"&companyName="+companyName;
		
}
