$(function() {
	createDataGrid('versionId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 120,
			formatter : function(value, row, index) {
				var str = "";
				var estimatedTime = row.estimatedTime;
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_APP_VERSION_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editVersion(\''
						+ value + '\');">编辑</button></a>'
						
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_APP_VERSION_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:editStatus(\''
						+ value
						+ '\',2,\''
						+ row.isvisable
						+ '\');">删除</button></a>'

				if (row.isVisable == 2) {
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_APP_VERSION_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editStatus(\''
							+ value
							+ '\',\''
							+ row.isDelete
							+ '\',1);">启用</button></a>'
				} else {
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_APP_VERSION_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editStatus(\''
							+ value
							+ '\',\''
							+ row.isDelete
							+ '\',2);">禁用</button></a>'
				}
				return str;
			}
		}, {
			title : '终端',
			field : 'terminal',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "安卓";
				} else if (value == 2) {
					return "IOS";
				}

			}

		}, {
			title : '版本号',
			field : 'version',
			width : 20,
			sortable : true
		}, {
			title : '版本名称',
			field : 'versionName',
			width : 20,
			sortable : true
		}, {
			title : '版本地址',
			field : 'downloadUrl',
			width : 20,
			sortable : true
		}, {
			title : '时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}, {
			title : '发布人',
			field : 'publisher',
			width : 20,
			sortable : true
		}, {
			title : '说明',
			field : 'remark',
			width : 20,
			sortable : true,
		} ];

var cfg = {
	url : './searchVersionList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

/**
 * 启用、禁用或删除
 * 
 * @param pk
 * @param isd
 * @param isv
 * @param estimatedTime
 */
function editStatus(pk, isd, isv) {
	var parm = {
		pk : pk,
		isDelete : isd,
		isVisable : isv,
	};
	$.ajax({
		type : 'POST',
		url : './updateVersion.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			$('#versionId').bootstrapTable('refresh');

		}
	});

}

function editVersion(pk) {
	$('#editVersionId').modal();
	$("#pk").val(pk);
	if (pk != '' && pk != null) {
		var data = $('#versionId').bootstrapTable('getRowByUniqueId', pk);
		$("#terminal").val(data.terminal);
		var str=data.version;

		$("#version").val(str);
		$("#downloadUrl").val(data.downloadUrl);
		$("#remark").val(data.remark);
		$("#versionName").val(data.versionName);
	} else {
		$("#terminal").val('');
		$("#version").val('');
		$("#downloadUrl").val('');
		$("#remark").val('');
		$("#versionName").val('');
	}

}
/**
 * 搜索和清除
 * 
 * @param type
 */
function searchTabs(type) {
	// 清除所有内容
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './searchVersionList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('versionId', cfg);
}

function submit() {
	if (valid("#dataForm")) {
		// 版本号校验
		var version   = $('#version').val();
		/*if (isVersionNum(version)) {
			new $.flavr({
				modal : false,
				content : "版本号格式必须是三位，x.x.x的形式！"
			});
			return;
		}*/
		$.ajax({
			type : 'POST',
			url : './updateVersion.do',
			data : {
				pk : $("#pk").val(),
				terminal : $("#terminal").val(),
				version : version,
				downloadUrl : $("#downloadUrl").val(),
				remark : regEnter( $("#remark").val()),
				versionName: $("#versionName").val()
			},
			dataType : 'json',
			success : function(data) {
				if (!data.success) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
				} else {
					$("#quxiao").click();
					$('#versionId').bootstrapTable('refresh');

				}
			}
		});
	}
}
