$(function() {
	createDataGrid('categoryId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 50,
			formatter : function(value, row, index) {
				var str = "";
				if(row.isVisable==2){
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_HELP_CLASSIFY_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editCategoryStatus(\''
						+ value + '\',1);">启用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_HELP_CLASSIFY_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editCategoryStatus(\''
						+ value + '\',2);">禁用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_HELP_CLASSIFY_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:deleteCategory(\''
						+ value + '\',2);">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_HELP_CLASSIFY_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editCategory(\''
					+ value
					+ '\',\''
					+ row.name
					+ '\',\''
					+ row.sort
					+ '\',\''
					+ row.showType
					+ '\',\''
					+ row.showPlace
					+ '\');">编辑</button></a>';
									
				return str;
			}
		}, {
			title : '启用/禁用',
			field : 'isVisable',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else if (value == 2) {
					return "禁用";
				}  

			}

		} ,
		{
			title : '分类名称',
			field : 'name',
			width : 20,
			sortable : true
		},
		{
			title : '放置终端',
			field : 'showPlace',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var name = "";
				if(value!=undefined&&value!=""){
					var arry =	value.split(",")
					for(var i=0;i<arry.length;i++){
						if(arry[i]==1){
							name += "PC端"+",";
						}else if(arry[i]==2){
							name += "WAP端"+",";
						}else if(arry[i]==3){
							name += "APP端"+",";
						}
					}
					name = name.substring(0,name.length-1);
				}
				return name;
			}
		},
		{
			title : '是否展示首页',
			field : 'showType',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "是";
				} else if (value == 2) {
					return "否";
				} 

			}
		}
		 , {
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		}   ];

var cfg = {
		url : './helpscategory_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 编辑帮助分类
 * @param pk
 * @param name
 * @param sort
 * @param showType
 * @param showPlace
 */
function editCategory(pk,name,sort,showType,showPlace){
	$('#myModal1').modal();
	if (null != pk && '' != pk) {
		$("input[type='checkbox']:checked").prop("checked",false);
		$("#pk").val(pk);
		$("#name").val(name);
		$("#sort").val(sort);
		if(sort == "undefined"){
			$("#sort").val("");
		}else{
			$("#sort").val(sort);
		}
		if(showPlace!=""){
			var arry =	showPlace.split(",")
			for(var i=0;i<arry.length;i++){
				$("input[name='showPlace']").each(function(){
					if($(this).val()==arry[i]){
						$(this).prop("checked","checked");
					}
				})
			}
		}
		$("#showType").val(showType);
	}else{
		$("#pk").val("");
		$("#name").val("");
		$("#sort").val("");
		$("#showType").val("");
		$("input[type='checkbox']:checked").prop("checked",false);
	}
}

/**
 * 保存帮助分类
 */
function submit(){
	var name = $("#name").val();
	if (name.length == 0) {
		new $.flavr({
			modal : false,
			content : "请输入分类名称"
		});
		return;
	}
	var sort = $("#sort").val();
	if(!/^\d+$/.test(sort)) {
		new $.flavr({
			modal : false,
			content : "输入的排序数字需大于0并且为整数！"
		});
		return;
		}
	var showType = $("#showType").val();
	if (showType == "" || showType == null) {
		new $.flavr({
			modal : false,
			content : "请选择是否展示首页"
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
		url : "./updateSysHelpsCategory.do",
		data : {
				pk:$("#pk").val(),
				name:name,
				showType:showType,
				showPlace:checkBoxValue,
				sort:sort
				},
		dataType : 'json',
		async : true,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#quxiao").click();
				$('#categoryId').bootstrapTable('refresh');
			}
		}
	});

}

/**
 * 启用、禁用
 * @param pk
 * @param isv
 */
function editCategoryStatus(pk,isv){
	var parm = {
			pk: pk,
			isVisable:isv
		};

		$.ajax({
			type : 'POST',
			url : './updateSysHelpsCategoryStatus.do',
			data : parm,
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$('#categoryId').bootstrapTable('refresh');
				}

			}
		});
}

/**
 * 删除
 * @param pk
 * @param isd
 */
function deleteCategory(pk,isd){
	var parm = {
			pk: pk,
			isDelete:isd
		};

		$.ajax({
			type : 'POST',
			url : './delSysHelpsCategory.do',
			data : parm,
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$('#categoryId').bootstrapTable('refresh');
				}

			}
		});
	
}

/**
 * 搜索和清除
 */
function searchTabs() {
	var cfg = {
			url : './helpscategory_data.do'+ urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	createDataGrid('categoryId', cfg);
}
 