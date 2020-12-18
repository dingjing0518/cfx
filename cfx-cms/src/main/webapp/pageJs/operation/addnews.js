$(function(){


	//判断是点击select事件，判断下级select框是否有数据是否有数据，保证样式一致
$($("#isShowSelectId").find("span").eq(1).find("button").selector).click( function() {

	if ($("#categoryPk option").length == 0)
	{
		$(this).parent().addClass("check");

	}else{
		$(this).parent().removeClass("check");
	}
});

})







/**
 * 改变所属分类
 */
function changeCategory(){

	var pid=$("#categoryPId").val();
	$.post(getRootPath()+"/getCategoryByPid.do",{parentId:JSON.stringify(pid)},function(data){
		if(data){
            if(!$("#categoryPk").attr('multiple')){
                $("#categoryPk").attr('multiple','multiple');//添加属性
                $('#categoryPk').multiselect();
            }
		/*	 var html = "<option value=''>---全部---</option>";*/
			 var html = "";
			 for(var i =0;i<data.length;i++){
				html +="<option value="+data[i].pk+">"+data[i].name+"</option>";
			}
			 $("#categoryPk").empty().append(html);
				//	    $("#categoryPk").html(html);  
	            $('#categoryPk').multiselect('rebuild');

		}

	},"json");
}


/**
 * 保存文章
 * @param flag
 */
function submit(flag){
	var content = ue.getContent();
	//if(valid("#dataForm")){
		if(content==""){
			new $.flavr({
				modal : false,
				content : "请输入正文内容"
			});
			return;
		}
		var newsTitle = $("#title").val();
		if(newsTitle == "" || newsTitle == undefined || newsTitle == 'undefined' ){
			new $.flavr({
				modal : false,
				content : "请填写新闻标题！"
			});
			return;
		}
		var position = 0;
		$("input[name='recommendPosition']:checkbox:checked").each(function(){ 
			if($(this).val() != null && $(this).val() != ''){
				position +=parseInt($(this).val())
			}
			}) 
		if(position == 0){
			position = -1;
		}
		var pid = $("#categoryPId").val();
		var catePk = $("#categoryPk").val();
		if(pid == null || pid == "" || pid == undefined || pid == 'undefined'){
			new $.flavr({
				modal : false,
				content : "请选择所属分类"
			});
			return;
		}
		if(catePk == null || catePk == "" || catePk == undefined || catePk == 'undefined'){
			new $.flavr({
				modal : false,
				content : "请选择所属分类"
			});
			return;
		}
		$.ajax({
			type : 'POST',
			url : "./updateSysNews.do",
			data : {
				pk:$("#pk").val(),
				title:newsTitle,
				categoryPk:JSON.stringify($("#categoryPk").val()),
				recommend:$('input:checkbox[name="recommend"]:checked').val(),
				top:$('input:checkbox[name="top"]:checked').val(),
				recommendPosition:position,
				keyword:$("#keyword").val(),
				newSource:$("#newSource").val(),
				newAbstrsct:$("#newAbstrsct").val(),
				content:content,
				status:flag,
				url:$('#url').attr("src")
			},
			dataType : 'json',
			async : true,
			success : function(data) {
				window.location=getRootPath()+"/article.do";
				
			}
		});
	//}
}

/**
 * 跳转到文章列表
 * @constructor
 */
function OpenNews(){
	  window.location=getRootPath()+"/article.do";
}
