$(function() {
	//search();
	btnList();
});

/**
 * 报表列表数据查询
 */
function searchTabs(){
    window.location = getRootPath() + "/bankApproveAmount.do?bankPk=" + $("#selectBank").val()+"&year="+$("#year").val();
}

/**
 * 导出银行审批额度报表
 */
function exportData(){

	$.ajax({
		type : "POST",
		url : './exportBankApproveAmountList.do',
		dataType : "json",
		data :{
			bankPk:$("#selectBank").val(),
			bankName:$('#selectBank option:selected').text(),
			year:$("#year").val()
			
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});

}