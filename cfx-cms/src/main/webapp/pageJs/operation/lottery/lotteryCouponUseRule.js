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

		$.ajax({
			type : 'POST',
			url : "./updateLotteryCouponUseRule.do",
			data : {
				id:$("#pk").val(),
				ruleDetail:content,
				ruleType:1
			},
			dataType : 'json',
			async : true,
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				setTimeout(function(){//两秒后跳转  
					window.location = getRootPath()+"/lotteryCouponUseRule.do";	
				},3000);
				
				
			}
		});
}
