$(function() {
	createDataGrid('deploy', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'id',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
		if (row.account != 'admin') {
			str += '<button id="editable-sample_new" style="display:none;" showname="MG_FLOW_DEPLOY_BTN_EDIT"  class="btn btn-primary" data-toggle="modal" onclick="javascript:editAccount(\''
					+ value
					+ '\',\''
					+ row.name
					+ '\',\''
					+ row.type
					+ '\'); ">编辑</button>';
			str += '<button type="button" style="display:none;" showname="MG_FLOW_DEPLOY_BTN_DEL"  onclick="javascript:del(\''
					+ value
					+ '\'); " class="btn btn-danger active">删除</button>';
		
		}

		return str;
	}
}, 
		{
			title : '编号',
			field : 'id',
			width : 150,
			sortable : true
		},
		{
			title : '流程名称',
			field : 'name',
			width : 150,
			sortable : true
		},
		{
			title : '部署时间',
			field : 'deploymentTime',
			width : 150,
			sortable : true,
			formatter: function (value, row, index) {
			        return changeDateFormat(value)
			    }
			
		}
		
		];
var cfg = {
	url : './deployPage.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};
function search(){
	var cfg = {
			url : './deployPage.do' + urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'asc',
			toolbar : toolbar
		};
		createDataGrid('deploy', cfg);
}

/**
 * 保存
 */
function submit() {
	
	var id = $("#id").val();
	var name = $("#name").val();
	var type=$("#type").val();
	 if(validNotHidden("#dataForm")){
		 $.ajax({
				type : 'POST',
				url : "./updateActGroup.do",
				data : {
					 id : id,
					 name  : name,
					 type:type
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
						$('#deploy').bootstrapTable('refresh');
					}
					
				}
			});
	 }
	

}

/**
 * 编辑
 * @param pk
 * @param name
 * @param type
 */
function editAccount(pk, name,type) {

	$('#myModal1').modal();
	if (null != pk && '' != pk) {
	
		$("#id").attr("disabled",true);
		$("#name").val(name);
		$("#id").val(pk);
		$("#type").val(type);
		
	} else {
		$("#id").attr("disabled",false);
		$("#name").val('');
		$("#id").val('');
		$("#type").val('');
	}

}

/**
 * 删除
 * @param pk
 */
function del(pk) {
	
	var parm = null;

		parm = {
			'id' : pk
		};
	
	$.ajax({
		type : 'POST',
		url : 'delDeploy.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#quxiao").click();
				$('#deploy').bootstrapTable('refresh');
			}
			
		

		}
	});

}	

	
