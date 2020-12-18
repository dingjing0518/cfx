$(function() {
	createDataGrid('paymentId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
			 
		 
			if (row.isVisable == 1) {
				str += '<button type="button" onclick="javascript:editState(\''
						+ value + '\',\''
						+ row.name + '\',\''
						+ row.type + '\',\''
						+ row.iouNumber + '\' ,2); " style="display:none;" showname="OPER_MG_PAYMENT_BTN_DISABLE" class="btn btn-danger active">禁用</button>';
			} else {
				str += '<button type="button" onclick="javascript:editState(\''
						+ value
						+ '\',\''
						+ row.name + '\',\''
						+ row.type + '\',\''
						+ row.iouNumber + '\' ,1); " style="display:none;" showname="OPER_MG_PAYMENT_BTN_ENABLE" class="btn btn-primary">启用</button>';
			}

		return str;
	}
},
		{
			title : '支付方式',
			field : 'name',
			width : 150,
			sortable : true
		},
		 
		 
		{
			title : '启用/禁用',
			field : 'isVisable',
			width : 80,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else {
					return "禁用";
				}

			}

		}
		 ];
var cfg = {
	url : './searchPaymentList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 启用、禁用
 * @param pk
 * @param name
 * @param type
 * @param iouNumber
 * @param isvi
 */
function editState(pk ,name,type,iouNumber, isvi) {

	var parm =  {
			 pk : pk,
			 isVisable  : isvi,
			 name:name,
			 type:type,
			 iouNumber:iouNumber
		};
 
	$.ajax({
		type : 'POST',
		url : './updatePayment.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#quxiao").click();
				$('#paymentId').bootstrapTable('refresh');
			}

		}
	});

}
 
 
