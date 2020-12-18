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
                    if (row.isVisable == 2) {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTCLASSIFY_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editCategoryStatus(\''
                            + value + '\',\'' + row.isDelete + '\',1);">启用</button></a>';
                    } else {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTCLASSIFY_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editCategoryStatus(\''
                            + value + '\',\'' + row.isDelete + '\',2);">禁用</button></a>';
                    }
                    str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTCLASSIFY_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:editCategoryStatus(\''
                        + value + '\',2,\'' + row.isVisable + '\');">删除</button></a>';
                    str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_INFO_ARTCLASSIFY_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editCategory(\''
                        + value + '\',\'' + row.name + '\',\'' + row.parentId + '\',\'' + row.sort + '\',\'' + row.showType + '\');">编辑</button></a>';
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
			title : '展示位置',
			field : 'showType',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "首页上侧";
				} else if (value == 2) {
					return "首页底部";
				} else if(value==3){
					return "资讯页";
				}else if(value==4){
					return "首页中部";
				}
				

			}
		},

		{
			title : '所属系统',
			field : 'parentId',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "电商系统";
				} else if (value == 2) {
					return "物流系统";
				} else if(value==3){
					return "金融系统";
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
		url : './category_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 编辑分类
 * @param pk
 * @param name
 * @param pid
 * @param sort
 * @param showType
 */
function editCategory(pk,name,pid,sort,showType){
	$('#myModal1').modal();
	if (null != pk && '' != pk) {
		$("#pk").val(pk);
		$("#name").val(name);
		$("#parentId").val(pid);
		$("#sort").val(sort);
		$("#showType").val(showType);
	}else{
		$("#dataForm").find("input,select").val("");
	}
}

/**
 * 保存分类
 */
function submit(){
	if(validNotHidden("#dataForm")){
		var sort = $("#sort").val();

        if(isNotZeroDNumber(sort) == false || sort.length > 9){
            new $.flavr({
                modal : false,
                content : "输入的排序数字需大于零的整数并且小于十位数字"
            });
            return;
        }
	$.ajax({
		type : 'POST',
		url : "./updatesysCategory.do",
		data : {
            pk:$("#pk").val(),
            name:$("#name").val(),
            parentId:$("#parentId").val(),
            sort:$("#sort").val(),
            showType:$("#showType").val()
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
}

/**
 * 启用、禁用和删除
 * @param pk
 * @param isd
 * @param isv
 */
function editCategoryStatus(pk,isd,isv){
	var parm = {
			pk: pk,
			isDelete: isd,
			isVisable:isv
		};

		$.ajax({
			type : 'POST',
			url : './updatesysCategory.do',
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

 