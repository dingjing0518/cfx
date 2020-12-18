$(function() {

	var cfg = {
			url : './marketLiveBroad_data.do'+urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList,
		fixedColumns : true,
		fixedNumber : 1
	};
	
	createDataGrid('NewsId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width :120,
			formatter : function(value, row, index) {
				var str = "";
				var estimatedTime ='';
                    str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new"  style="display:none;" showname="OPER_INFO_MARKET_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:editNewsStatus(\''
                        + value + '\',2,\'' + row.isVisable + '\',\'' + estimatedTime + '\');">删除</button></a>'

                    str += '<button id="editable-sample_new" style="display:none;" showname="OPER_INFO_MARKET_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:editNews(\''
                        + value
                        + '\',\''
                        + row.keyword
                        + '\',\''
                        + row.livebroadcategoryName
                        + '\',\''
                        + row.livebroadcategoryPk
                        + '\'); ">编辑</button>';
				return str;
		
			}
		}, 
		{
			title : '直播分类',
			field : 'livebroadcategoryName',
			width : 20,
			sortable : true
		} ,
		{
			title : '内容',
			field : 'content',
			width : 20,
			sortable : true
		} ,
		

		{
			title : '关键词',
			field : 'keyword',
			width : 20,
			sortable : true
		},{
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}
		
		];


/**
 * 删除
 * @param pk
 * @param isd
 * @param isv
 * @param estimatedTime
 */
function editNewsStatus(pk,isd,isv,estimatedTime){
	var parm = {
			pk: pk,
			isDelete: isd,
			isVisable:isv
			//estimatedTime: new Date(Date.parse(estimatedTime)) 
		};
	$.ajax({
		type : 'POST',
		url : './updateMarketLiveBroadStatus.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
            bootstrapTableRefresh("NewsId");
			//$('#NewsId').bootstrapTable('refresh');

		}
	});
	
}
/**
 * 编辑
 * @param pk
 * @param content
 * @param keyword
 * @param livebroadcategoryName
 * @param livebroadcategoryPk
 */
function editNews(pk,keyword,livebroadcategoryName,livebroadcategoryPk){
	$('#myModal1').modal();
	if (null != pk && '' != pk) {
        $.ajax({
            type : 'POST',
            url : './getMarketLiveBroadByPk.do',
            data : {pk:pk},
            dataType : 'json',
            success : function(data) {
            	//处理文本换行问题
                $("#content").val(data.content.replace('<br/>','\n'));
            }
        });
		$("#pk").val(pk);
		$("#keyword").val(keyword);
		$("#livebroadcategoryPk").val(livebroadcategoryPk);

        $.ajax({
            type : 'POST',
            url : './getLivebroadcategoryByPk.do',
            data : {pk:livebroadcategoryPk},
            dataType : 'json',
            success : function(data) {
				if (data == null || data == "" || data.isVisable == 2 ) {
                    $("button[data-id=livebroadcategoryPk] span.filter-option").html("--请选择--");
                    $("button[data-id=livebroadcategoryPk]").attr("title","--请选择--");
				}else{
                    $("button[data-id=livebroadcategoryPk] span.filter-option").html(data.name);
                    $("button[data-id=livebroadcategoryPk]").attr("title",data.name);
                }
            }
        });
	}else{
		$("#pk").val('');
		$("#content").val('');
		$("#keyword").val('');
		 $("button[data-id=livebroadcategoryPk] span.filter-option").html("--请选择--"); 
		 $("button[data-id=livebroadcategoryPk]").attr("title","--请选择--"); 
		 $("#livebroadcategoryPk").val("null");
	
		
	}
}
/**
 * 保存行情直播
 */
function submit(){
	 var livebroadcategoryPk=$("#livebroadcategoryPk").val();
	 var livebroadcategoryName = $("button[data-id=livebroadcategoryPk]").attr("title");

	 var keyword=$("#keyword").val();
    var content=$("#content").val();
	 if(livebroadcategoryPk=="null"){
			new $.flavr({
				modal : false,
				content : "请选择行情分类！"
			});
			return;
		}
	
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : './updateMarketLiveBroadStatus.do',
			data : {
				pk : $("#pk").val(),
				livebroadcategoryPk :livebroadcategoryPk, 
				livebroadcategoryName :livebroadcategoryName, 
				content: content,
				keyword:keyword
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#NewsId').bootstrapTable('refresh');
				}
			}
		});
		}
}

/**
 * 搜索和清除
 * @param type
 */
function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './marketLiveBroad_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('NewsId', cfg);
}

function inputLength(){

}