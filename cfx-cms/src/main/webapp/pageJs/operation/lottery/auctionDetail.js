$(function(){
 /*	var pk=$("#pk").val();
	if(pk.length>0){
		findCategory(pk);
	}*/
	 
})


function submit(flag){
	var content = ue.getContent();
		if(content==""){
			new $.flavr({
				modal : false,
				content : "请输入正文内容"
			});
			return;
		}
		var ruleType = $("#ruleTypeId").val();
		$.ajax({
			type : 'POST',
			url : "./editAuctionProtocolRule.do",
			data : {
				pk:$("#pk").val(),
				detail:content,
				ruleType:ruleType
			},
			dataType : 'json',
			async : true,
			success : function(data) {
				/*new $.flavr({
					modal : false,
					content : data.msg
				});*/
				if(ruleType == 1){
					window.location=getRootPath()+"/auctionProtocol.do";	
				}
				if(ruleType == 2){
					window.location=getRootPath()+"/auctionRuleDetail.do";
				}
			}
		});
}
function OpenNews(){
	  window.location=getRootPath()+"/article.do";
}
