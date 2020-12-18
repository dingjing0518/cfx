$(function() {
	//search();
	btnList();
});

/**
 * 查询金融产品使用情况列表
 */
function searchTabs(){

    window.location = getRootPath() + "/econProductUse.do?bankPk=" + $("#selectBank").val()+"&year="+$("#year").val();
}

/**
 * 导出金融产品使用情况数据
 */
function exportData(){
	$.ajax({
		type : "POST",
		url : './exportEconProductUseList.do',
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