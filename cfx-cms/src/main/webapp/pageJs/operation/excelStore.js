$(function() {
	createDataGrid('excelStoreId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width :120,
			formatter : function(value, row, index) {
				var str = "";
					if (row.isDeal == 1){
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> ' +
						'<button button id="editable-sample_new" style="display:none;" showname="MANAGE_EXCELSTORE_BTN_DOWNLOAD" class="btn btn-warning" onclick="javascript:downLoadFile(\'' + row.url + '\');">下载</button></a>'
                    }
						return str;
			}
		},
		{
			title : '状态',
			field : 'isDeal',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {;
				if (value == 2) {
					return "进行中";
				} else  if (value == 1) {
					return "已结束";
				}else  if (value == 3) {
                    return "任务异常";
                }

			}
		},
		{
			title : '导出类型',
			field : 'type',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "客户数据";
				} else  if (value == 2) {
					return "订单数据";
				}else  if (value == 3) {
                    return "商品数据";
                }else  if (value == 4) {
                    return "报表数据";
                }

			}
		},
		{
			title : '导出位置',
			field : 'name',
			width : 20,
			sortable : true
		},{
			title : '导出条件',
			field : 'paramsName',
			width : 20,
			sortable : true
		},
		{
			title : '申请导出时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},
		{
			title : '预计结束时间',
			field : 'preTimes',
			width : 20,
			sortable : true
		},
		{
			title : '操作人',
			field : 'accountName',
			width : 20,
			sortable : true
		},
		{
			title : '操作人账号',
			field : 'account',
			width : 20,
			sortable : true
		},
		{
			title : '所属权限',
			field : 'roleName',
			width : 20,
			sortable : true
		}];

var cfg = {
		url : './searchExcelStore.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 下载存储在OSS上的文件
 * @param pk
 */
function downLoadFile(url){
    if (!checkIsEmpty(url)){
            new $.flavr({
                modal : false,
                content : "要下载的文件为空！"
            });
            return;
        }
        
     var  fileName = url.substring(url.lastIndexOf(".")+1);
	if(fileName=="txt"){
		  window.location.href = encodeURI (getRootPath() + "/uploadTxT.do?fileName=" +url.substring(url.lastIndexOf('/')+1,url.lastIndexOf(".")));
	}else{
		 window.location.href = url;
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
			url : './searchExcelStore.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('excelStoreId', cfg);
}

function findStatus(s){
    if(s==0){
        $("#isDeal").val("");
    }else{
        $("#isDeal").val(s);
    }
    var cfg = {
        url : './searchExcelStore.do'+urlParams(1),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'insertTime',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess:btnList
    };
    createDataGrid('excelStoreId', cfg);
}