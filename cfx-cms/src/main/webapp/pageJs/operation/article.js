$(function() {
	createDataGrid('NewsId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'newsPk',
			width :120,
			formatter : function(value, row, index) {
				var str = "";
				var estimatedTime =row.estimatedTime;
                    if (row.isVisable == 2) {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editNewsStatus(\''
                            + value + '\',\'' + row.isDelete + '\',1,\'' + estimatedTime + '\');">启用</button></a>'
                    } else {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editNewsStatus(\''
                            + value + '\',\'' + row.isDelete + '\',2,\'' + estimatedTime + '\');">禁用</button></a>'
                    }
                    if (row.status == 2) {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_UNPUBLISH" class="btn btn-warning"   onclick="javascript:updatePublishStatus(\''
                            + value + '\',1);">未发布</button></a>'
                    } else {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_PUBLISH" class="btn btn-warning"   onclick="javascript:updatePublishStatus(\''
                            + value + '\',2);">已发布</button></a>'
                    }
                    if (row.isPush == 2) {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" class="btn btn-warning"  style="display:none;" showname="OPER_INFO_ARTLIST_BTN_PUSH" onclick="javascript:updatePushStatus(\''
                            + value + '\',2);">已推送</button></a>'
                    } else {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" class="btn btn-warning" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_UNPUSH"  onclick="javascript:updatePushStatus(\''
                            + value + '\',2);">未推送</button></a>'
                    }

                    if (row.top == 1) {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_DISTOP" class="btn btn-warning"   onclick="javascript:editNewsTop(\''
                            + value + '\' ,2,\'' + estimatedTime + '\');">取消置顶</button></a>'
                    } else {
                        str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_TOP" class="btn btn-warning"   onclick="javascript:editNewsTop(\''
                            + value + '\' ,1,\'' + estimatedTime + '\');">置顶</button></a>'
                    }

                    str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:delNewsStatus(\''
                        + value + '\',2,\'' + row.isVisable + '\');">删除</button></a>'

                    str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_INFO_ARTLIST_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editNews(\''
                        + value
                        + '\');">编辑</button></a>'

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
			title : '标题',
			field : 'title',
			width : 20,
			sortable : true
		} ,
		{
			title : '阅读量',
			field : 'pvCount',
			width : 20,
			sortable : true
		},
		{
			title : '分类名称',
			field : 'categoryNames',
			width : 20,
			sortable : true
		} ,
		{
			title : '所属系统',
			field : 'belongSysNames',
			width : 20,
			sortable : true
		}
		 , {
			title : '是否置顶',
			field : 'top',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "是";
				} else if (value == 2)  {
					return "否";
				} 

			}
		},
		{
			title : '关键词',
			field : 'keyword',
			width : 20,
			sortable : true
		},
		{
			title : '缩略图',
			field : 'url',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var str="";
				if(row.url!=null&&row.url!=''){
					str+="<a rel='group"+index+"' title='缩略图' href='"+row.url+"'><img class='bigpicture' src='"+row.url+"' onerror='errorImg(this)'></a>";
				}
			
				return str;

			}
		},{
			title : '推荐位置',
			field : 'recommendPosition',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "首页资讯模块";
				} else if (value == 2)  {
					return "行情中心模块";
				}else if (value == 3)  {
					return "首页资讯模块,行情中心模块";
				} else if (value == -1)  {
					return "暂无";
				} else{
					return "暂无";
				} 

			}

		}  
		,{
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},
		{
			title : '是否发布',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 2) {
					return "已发布";
				} else if (value == 1){
					return "未发布";
				} 

			}
		}, {
        title : '是否推送',
        field : 'isPush',
        width : 20,
        sortable : true,
        formatter : function(value, row, index) {
            if (value == 1) {
                return "未推送";
            } else if (value == 2)  {
                return "已推送";
            }

        }
    }];

var cfg = {
		url : './searchSysNewsStorageEntity.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList,
	fixedColumns : true,
	fixedNumber : 1
};

/**
 * 启用、禁用或删除
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
		url : './updateSysNewsStatus.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			$('#NewsId').bootstrapTable('refresh');

		}
	});
	
}


/**
 * 删除
 * @param pk
 * @param isd
 * @param isv
 * @param estimatedTime
 */
function delNewsStatus(pk,isd,isv){
    var parm = {
        pk: pk,
        isDelete: isd,
        isVisable:isv
    };
    var confirm = new $.flavr({
        content : '确定删除吗?',
        dialog : 'confirm',
        onConfirm : function() {
            confirm.close();
            $.ajax({
                type : 'POST',
                url : './updateSysNewsStatus.do',
                data :parm,
                dataType : 'json',
                success : function(data) {
                    new $.flavr({
                        modal : false,
                        content : data.msg
                    });
                    if (data.success) {
                        $("#quxiao").click();
                        $('#NewsId').bootstrapTable('refresh');
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
 * 修改发布状态
 * @param pk
 * @param status
 */
function updatePublishStatus(pk,status){
	var parm = {
			pk: pk,
			status: status
		};
	$.ajax({
		type : 'POST',
		url : './updateSysNewsStatus.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			$('#NewsId').bootstrapTable('refresh');

		}
	});
}

/**
 * 推送资讯到app
 * @param pk
 * @param status
 */
function updatePushStatus(pk,isPush){
    var parm = {
        pk: pk,
        isPush: isPush
    };
    $.ajax({
        type : 'POST',
        url : './updatePushStatus.do',
        data : parm,
        dataType : 'json',
        success : function(data) {
            $('#NewsId').bootstrapTable('refresh');

        }
    });
}

/**
 * 修改置顶/不置顶状态
 * @param pk
 * @param top
 * @param estimatedTime
 */
function editNewsTop(pk,top,estimatedTime){
	var parm = {
			pk: pk,
			top: top
			//estimatedTime: new Date(Date.parse(estimatedTime))
		};
	$.ajax({
		type : 'POST',
		url : './updateSysNewsStatus.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			
			$('#NewsId').bootstrapTable('refresh');

		}
	});
}

/**
 * 跳转到编辑新闻页面
 * @param newsPk
 */
function editNews(newsPk){
	 window.location=getRootPath()+"/addnews.do?pk="+newsPk;
}

/**
 * 切换新闻发布状态table页
 * @param s
 */
function findNews(s){
	if(s==0){
		$("#status").val("");
	}else{
		$("#status").val(s);
	}
	var cfg = {
			url : './searchSysNewsStorageEntity.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('NewsId', cfg);
}


function inputStorage(){
	$("#editable-sample_new_storage").attr('disabled',true);
	$.ajax({
		type : 'POST',
		url : './importStorage.do',
		data : {
		},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$("#editable-sample_new_storage").attr('disabled',false);
				$("#quxiao").click();
				$('#NewsId').bootstrapTable('refresh');
		}
	});

}

/**
 * 导出文章
 */
function exportNews(){
	$.ajax({
		type : "POST",
		url:'./exportSysNewsStorageEntity.do'	+urlParams(1), //请求的url
		dataType : "json",
		data : {
			categoryName:$('#categoryPk option:selected').text()
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});
	
//	var confirm = new $.flavr({
//		content : '确定导出文章列表吗?',
//		dialog : 'confirm',
//		onConfirm : function() {
//			window.location.href = getRootPath() + "/exportSysNewsStorageEntity.do"
//					+urlParams(1);
//			confirm.close();
//		}
//	});
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
			url : './searchSysNewsStorageEntity.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('NewsId', cfg);
}