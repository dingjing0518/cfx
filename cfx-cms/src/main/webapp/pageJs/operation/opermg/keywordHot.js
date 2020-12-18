$(function() {
	createDataGrid('keywordHotId', cfg); 
});
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
                    if (row.status == 1) {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SEARCH_BTN_UNRECOMMEND" class="btn btn-warning"   onclick="javascript:edit(\''
                            + value + '\',2);">取消推荐</button></a>';
                    } else {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SEARCH_BTN_RECOMMEND" class="btn btn-warning"   onclick="javascript:edit(\''
                            + value + '\',1);">推荐</button></a>';
                    }
                    str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SEARCH_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''

                        + value + '\');">删除</button></a>';

				return str;
			}
		},
		{
			title : '关键词',
			field : 'name',
			width : 20,
			sortable : true
		},
		{
			title : '链接',
			field : 'linkUrl',
			width : 20,
			sortable : true
		} , {
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		},  {
			title : '是否推荐',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "推荐";
				} else if (value == 2) {
					return "不推荐";
				}  
			}
		} ,{
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		} ];

var cfg = {
		url : './searchKeywordHotList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
    queryParams:function(params){
        return {
            block:$("#block").val(),
            limit : params.limit,
            start : params.offset,
            orderType : params.order,
            orderName : params.sort
        };
	},
	onLoadSuccess:loadFancyBox
};

/**
 * 保存
 */
function submit() {
   if(validNotHidden("#dataForm")){
	   var params = {};
	   
	   $("#myModal3").find("input").each(function(){
		   params[$(this).attr("name")] = $(this).val();
	   });
       var sort = $("#sort").val();
       if (sort =='' || !isNotZeroDNumber(sort) || sort.length > 9){
           new $.flavr({
               modal : false,
               content : "请输入大于零并且小于10位的整数！"
           });
           return;
       }

	   params['block'] = $("#block").val();
	   $.ajax({
		   type : "POST",
		   url : "./updateKeywordHot.do",
		   data : params,
		   dataType : "json",
		   success : function(data) {
			   new $.flavr({
				   modal : false,
				   content : data.msg
			   }); /* Closing the dialog */
			   if (data.success) {
				   $("#quxiao").click();
				   $('#keywordHotId').bootstrapTable('refresh');
			   }
		   }
	   });
   }
}

/**
 * 新增
 */
function addKeyWordHot(){
	$('#myModal3').modal();
	$("#myModal3").find("input").each(function(){
		$(this).val('');
	});
	$("#status").val(1);
}
 
function editKeywordHot(pk) {
	var data = $('#keywordHotId').bootstrapTable('getRowByUniqueId',pk);
	$(".kv-file-remove").click();
	$('#myModal3').modal();
	for(var i in data){
		$("#myModal3").find("input[name='"+i+"']").val(data[i]);
	}
}

function Search() {
	var cfg = {
		url : './searchKeywordHotList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('keywordHotId', cfg);
}

function Clean(){
	cleanpar();
	var cfg = {
			url : './searchKeywordHotList.do'+ urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('keywordHotId', cfg);
}

/**
 * 推荐，取消推荐
 * @param pk
 * @param status
 */
function edit(pk,status){
	$.ajax({
		type : 'POST',
		url : './updateKeywordHot.do',
		data : {
			 pk : pk,
			 status  : status,
		},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});  
			if (data.success) {
				$("#quxiao").click();
				$('#keywordHotId').bootstrapTable('refresh');
			}

		}
	});
}

/**
 * 删除
 * @param pk
 */
function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateKeywordHot.do',
				data : {
					 pk : pk,
					 isDelete  : 2,
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#keywordHotId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}



function loadFancyBox(){
	btnList();
}