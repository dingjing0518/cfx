$(function() {
	
	var cfg = {
			url : './livebroadcastcategory_data.do'+urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
	
	createDataGrid('livebroadcastcategoryId', cfg);
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
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_LIVECLASSIFY_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editCategoryStatus(\''
                            + value + '\',\'' + row.isDelete + '\',1);">启用</button></a>';
                    } else {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_LIVECLASSIFY_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editCategoryStatus(\''
                            + value + '\',\'' + row.isDelete + '\',2);">禁用</button></a>';
                    }
                    str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_LIVECLASSIFY_BTN_DEL" class="btn btn-default"  onclick="javascript:editCategoryStatus(\''
                        + value + '\',2,\'' + row.isVisable + '\');">删除</button></a>';

                    str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_INFO_LIVECLASSIFY_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editCategory(\''
                        + value
                        + '\',\''
                        + row.name
                        + '\',\''
                        + row.parentId
                        + '\',\''
                        + row.sort
                        + '\',\''
                        + row.showType
                        + '\');">编辑</button></a>';
				return str;
			}
		}, 
		{
			title : '直播分类',
			field : 'name',
			width : 20,
			sortable : true
		},
	
		 {
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		} ,
		{
			title : '状态',
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
			title : '最后更新时间',
			field : 'updateTime',
			width : 20,
			sortable : true
		} ];

var cfg = {
		url : './livebroadcastcategory_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 编辑直播分类
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
		$("#sort").val(sort);
		
	}else{
		$("#dataForm").find("input,select").val("");
	}
}

/**
 * 保存直播分类
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
    	var params = {};
        $('#dataForm').find("input,select").each(function(){
    		params[$(this).attr("name")] = $(this).val();
    	});
    	params['block'] = $("#block").val();

	$.ajax({
		type : 'POST',
		url : "./updateLiveBroadCastCategory.do",
		data :params,
		dataType : 'json',
		async : true,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#quxiao").click();
				$('#livebroadcastcategoryId').bootstrapTable('refresh');
			}
		}
	});
	}
}

/**
 * 禁用、启用和删除
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
			url : './updateLiveBroadCastCategory.do',
			data : parm,
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
                    bootstrapTableRefresh("livebroadcastcategoryId");
					//$('#livebroadcastcategoryId').bootstrapTable('refresh');
				}

			}
		});
}

 