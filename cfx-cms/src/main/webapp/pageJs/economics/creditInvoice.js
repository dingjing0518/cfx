$(function() {
	var cfg = {
		url : './creditInvoiceSearch.do?companyPk='+$("#companyPk").val(),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'billingDate',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};

	createDataGrid('creditInvoiceId', cfg);

});

var toolbar = [];
var columns = [
		{
			title : '发票代码',
			field : 'invoiceCode',
		}, {
			title : '发票号码',
			field : 'invoiceNumber',
		}, {
			title : '开票日期',
			field : 'billingDate',
		}, {
			title : '合计金额',
			field : 'totalAmount',

		}, {
			title : '税率',
			field : 'taxRate',
		}, {
			title : '合计税额',
			field : 'totalTax',
		}, {
			title : '发票类型',
			field : 'dataType',
			sortable : true,
			formatter: function (value, row, index) {
					if (row.dataType == "1") {
						return "进项";
					}else if (row.dataType == "2"){
						return "销项";
					}
			}
		}, {
			title : '发票状态',
			field : 'state',
			sortable : true,
			formatter: function (value, row, index) {
					if (row.state == 0) {
						return "正常";
					}else if (row.state == 1){
						return "作废";
					}else if (row.state == 2){
						return "红冲";
					}else if (row.state == 3){
                        return "失控";
                    }else if (row.state == 4){
                        return "异常";
                    }
				}
		}, {
			title : '购方税号',
			field : 'purchaserTaxNo',
		}, {
			title : '购方名称',
			field : 'purchaserName',
		}, {
			title : '销方税号',
			field : 'salesTaxNo',
		}, {
			title : '销方名称',
			field : 'salesTaxName',
		}, {
			title : '货物或应税劳务名称',
			field : 'commodityName',
		}, {
			title : '数量',
			field : 'quantity',
		}, {
			title : '单价',
			field : 'unitPrice',
		}];

function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
		cleanDate();
	}
    var cfg = {
        url : './creditInvoiceSearch.do?companyPk='+$("#companyPk").val()+
			"&billingDateStart="+$("#billingDateStart").val()+"&billingDateEnd="+$("#billingDateEnd").val()+"&dataType="+$("#dataType").val(),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'billingDate',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess : btnList
    };

    createDataGrid('creditInvoiceId', cfg);
}



/**
 * 导出发票
 */
function exportInvoice() {

	$.ajax({
		type : "POST",
		url : './exportCreditInvoiceList.do' +urlParams(1),
		dataType : "json",
		data :{},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});
}



/**
 * 同步发票
 */
function syncInvoice() {
    var billingDateStart = $("#billingDateStart").val();
    var billingDateEnd = $("#billingDateEnd").val();
    var dataType = $("#dataType").val();
	if(!checkIsEmpty(billingDateStart) && !checkIsEmpty(billingDateEnd) && !checkIsEmpty(dataType)){
        new $.flavr({
            modal : false,
            content : "同步时请先选择申请时间范围和发票类型"
        });
        return;
	}
    // if (DateDiff(billingDateStart,billingDateEnd) > 180){
    //     new $.flavr({
    //         modal : false,
    //         content : "申请时间范围请选择半年以内"
    //     });
    //     return;
	// }
	
    $.ajax({
        type : "POST",
        url : './syncCreditInvoice.do' +urlParams(1),
        dataType : "json",
        data :{
        	companyPk:$("#companyPk").val(),
            creditPk:$("#creditPk").val()
		},
        success : function(data) {
            new $.flavr({
                modal : false,
                content : data.msg
            });
            if (data.success){
                bootstrapTableRefresh("creditInvoiceId");
			}
        }
    });
}

function backToCreditCustomer(){
    window.location.href = getRootPath() + "/creditCustomer.do";

}
