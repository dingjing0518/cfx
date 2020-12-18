$(function(){
	
})

/**
 * 保存新增或编辑帮助
 * @param flag
 */
function submit(flag){
	var content = ue.getContent();
	if(valid("#dataForm")){
		
		var sort = $("#sort").val();

        if(isNotZeroDNumber(sort) == false || sort.length > 9){
            new $.flavr({
                modal : false,
                content : "输入的排序数字需大于零的整数并且小于十位数字"
            });
            return;
        }
		if(content==""){
			new $.flavr({
				modal : false,
				content : "请输入正文内容"
			});
			return;
		}
		
		var checkBoxValue ="";
		$("input[name='showPlace']:checked").each(function(){
			checkBoxValue += $(this).val()+",";
		})
		checkBoxValue = checkBoxValue.substring(0,checkBoxValue.length-1);
		if (checkBoxValue == "" || checkBoxValue == null) {
			new $.flavr({
				modal : false,
				content : "请选择放置终端"
			});
			return;
		}
		
		$.ajax({
			type : 'POST',
			url : "./insertSysHelps.do",
			data : {
				pk:$("#pk").val(),
				title:$("#title").val(),
				helpCategoryPk : $("#helpCategoryPk").val(),
				status:flag,
				showType:$("#showType").val(),
				sort:sort,
				content:content,
				showPlace:checkBoxValue
			},
			dataType : 'json',
			async : true,
			success : function(data) {
				if(data.success==false){
					new $.flavr({
						modal : false,
						content : data.msg
					});
					return;
				}else{
					window.location=getRootPath()+"/helps.do";
				}
				
				
			}
		});
	}
}

/**
 * 跳转到添加帮助页面
 * @constructor
 */
function OpenNews(){
	  window.location=getRootPath()+"/helps.do";
}
