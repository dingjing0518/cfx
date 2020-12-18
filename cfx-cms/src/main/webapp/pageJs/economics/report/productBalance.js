$(function() {
	btnList();
});

/**
 * 查询金融产品使用情况列表
 */
function search(){
		window.location = getRootPath() + "/productBalance.do?startTime=" + $("#startTime").val()+"&endTime="+$("#endTime").val();
}

/**
 * 导出金融产品使用余额数据
 */
function exportData(){

	$.ajax({
		type : "POST",
		url : './exportProductBalance.do',
		dataType : "json",
		data :{
			startTime:$("#startTime").val(),
			bankName:$('#selectBank option:selected').text(),
			endTime:$("#endTime").val()
			
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});

}


