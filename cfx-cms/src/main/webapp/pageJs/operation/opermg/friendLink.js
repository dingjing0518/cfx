$(function() {
	createDataGrid('friendLinkId', cfg);
});
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				if(row.status==1){
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_LINK_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:edit(\''
						+ value + '\',2);">禁用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_LINK_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:edit(\''
						+ value + '\',1);">启用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_LINK_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				return str;
			}
		},
		{
			title : '网站名称',
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
			title : '启用/禁用',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else if (value == 2) {
					return "禁用";
				}  
			}
		} ,{
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		} ];

var cfg = {
		url : './searchFriendLinkList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
};

/**
 * 修改提交
 */
function submit() {
   if(validNotHidden("#dataForm")){
	   var params = {};
	   
	   $("#myModal3").find("input").each(function(){
		   params[$(this).attr("name")] = $(this).val();
	   });
	   $.ajax({
		   type : "POST",
		   url : "./updateFriendLink.do",
		   data : params,
		   dataType : "json",
		   success : function(data) {
			   new $.flavr({
				   modal : false,
				   content : data.msg
			   }); /* Closing the dialog */
			   if (data.success) {
				   $("#quxiao").click();
				   $('#friendLinkId').bootstrapTable('refresh');
			   }
		   }
	   });
   }
}

/**
 * 新增友情链接
 */
function add(){
	$('#myModal3').modal();
	$("#myModal3").find("input").each(function(){
		$(this).val('');
	});
	$("#status").val(2);
}

/**
 * 搜索
 * @constructor
 */
function Search() {
	var cfg = {
		url : './searchFriendLinkList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('friendLinkId', cfg);
}

/**
 * 清除
 * @constructor
 */
function Clean(){
	cleanpar();
	var cfg = {
			url : './searchFriendLinkList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('friendLinkId', cfg);
}

/**
 * 启用、禁用
 * @param pk
 * @param status
 */
function edit(pk,status){
	$.ajax({
		type : 'POST',
		url : './updateFriendLink.do',
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
				$('#friendLinkId').bootstrapTable('refresh');
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
				url : './updateFriendLink.do',
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
						$('#friendLinkId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}


/**
 * 刷新列表按钮
 */
function loadFancyBox(){
	btnList();
}