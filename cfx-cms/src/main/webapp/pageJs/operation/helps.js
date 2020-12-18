$(function() {
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
				var estimatedTime =row.estimatedTime;
				if(row.isVisable==2){
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;"  showname="OPER_HELP_LIST_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editHelpsStatus(\''
						+ value + '\',1);">启用</button></a>'
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;"   showname="OPER_HELP_LIST_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editHelpsStatus(\''
						+ value + '\',2);">禁用</button></a>'
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;"  showname="OPER_HELP_LIST_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:deleteHelps(\''
						+ value + '\',2);">删除</button></a>'
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button"  style="display:none;"  showname="OPER_HELP_LIST_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editNews(\''
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
			title : '标题',
			field : 'title',
			width : 20,
			sortable : true
		},{
			title : '所属分类',
			field : 'name',
			width : 20,
			sortable : true
		},{
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		},
		{
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},
		{
			title : '状态',
			field : 'status',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "未发布";
				} else  if (value == 2) {
					return "已发布";
				} 

			}
		
		}];

var cfg = {
		url : './helps_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 启用、禁用
 * @param pk
 * @param isv
 */
function editHelpsStatus(pk,isv){
	var parm = {
			pk: pk,
			isVisable:isv
		};
	$.ajax({
		type : 'POST',
		url : './updateSysHelps.do',
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
 */
function deleteHelps(pk,isd){
	var parm = {
			pk: pk,
			isDelete:isd
		};
	$.ajax({
		type : 'POST',
		url : './delSysHelps.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			$('#NewsId').bootstrapTable('refresh');

		}
	});
}

/**
 * 跳转到编辑帮助页面
 * @param pk
 */
function editNews(pk){
	 window.location=getRootPath()+"/addHelps.do?pk="+pk;
}

/**
 * 切换帮助发布状态Table页
 * @param s
 */
function findNews(s){
	if(s==0){
		$("#status").val("");
	}else{
		$("#status").val(s);
	}
	var cfg = {
			url : './helps_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('NewsId', cfg);
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
			url : './helps_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('NewsId', cfg);
}