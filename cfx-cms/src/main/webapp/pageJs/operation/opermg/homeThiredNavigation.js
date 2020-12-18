$(function() {
	createDataGrid('homeId', cfg);
});
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 60,
			formatter : function(value, row, index) {
				var str = "";
				if(row.isVisable==2){
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_THIRDNAVIGATION_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',1);">启用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_THIRDNAVIGATION_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',2);">禁用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_THIRDNAVIGATION_BTN_DELETE" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_THIRDNAVIGATION_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editHomePageProductNameId" onclick="javascript:editHome(\''
					+ value
					+ '\',\''
					+ row.secondNavigationPk
					+ '\',\''
					+ row.navigation
					+ '\',\''
					+ row.navigationPk
					+ '\',\''
					+ row.isVisable
					+ '\',\''
					+ row.sort
					+ '\',\''
					+ row.secondNavigation
					+ '\');">编辑</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_THIRDNAVIGATION_BTN_SORT" class="btn btn btn-primary" data-toggle="modal" data-target="#editSortId" onclick="javascript:sort(\''
					+ value
					+ '\',\''
					+ row.sort
					+ '\');">排序修改</button></a>';
				return str;
			}
		} ,
		{
			title : '名称',
			field : 'navigation',
			width : 20,
			sortable : true
		},
		{
			title : '所属二级',
			field : 'secondNavigation',
			width : 20,
			sortable : true
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
		},
		{
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		},{
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}];

var cfg = {
		url : './homeThirdNavigationList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
};

/**
 * 提交
 */
function submit() {
   var pk =$('#pk').val();
   if(valid("#dataForm")){
	   
	   if(!/^\+?[1-9]\d*$/.test($("#sort").val())) {
			new $.flavr({
				modal : false,
				content : "排序数字需大于0的整数！"
			});
			return;
			}
	   if(!/^\d{1,6}$/.test($("#sort").val())) {
			new $.flavr({
				modal : false,
				content : "排序数字需在6位以内的整数！"
			});
			return;
			}
	  
		   url = "./updateThirdNavigation.do"; 
	   $.ajax({
		   type : "POST",
		   url : url,
		   data : {
			   pk : $("#pk").val(),
			   secondNavigationPk:$("#secondNavigationPk").val(),
			   navigationPk:$("#navigationPk").val(),
			   navigation:$("#navigationPk option:selected").text(),
			   sort : $("#sort").val(),
			   isVisable : $('input:radio[name="isVisable"]:checked').val(),
			   updateStatus:2
		   },
		   dataType : "json",
		   success : function(data) {
			   new $.flavr({
				   modal : false,
				   content : data.msg
			   }); /* Closing the dialog */
			   if (data.success) {
				   $("#quxiao").click();
				   $('#homeId').bootstrapTable('refresh');
			   }
		   }
	   });
   }
}

/**
 * 编辑和新增
 * @param pk
 * @param productPk
 * @param name
 * @param specPk
 * @param specName
 * @param isVisable
 * @param sort
 */
function editHome(pk,secondNavigationPk,navigation,navigationPk,isVisable,sort,secondNavigation) {
	$('#editHomeId').modal();
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$("#secondNavigationPk").val(secondNavigationPk);
		changeThirdNavigation(navigationPk);
		
	/*	$("#navigationPk").val(navigationPk);
		$("#navigationPk option:selected").text(navigation)*/
		$("#secondNavigationPk option:selected").text(secondNavigation)
		$('#sort').val(sort);
	    if(sort!="undefined"){
	    	$('#sort').val(sort);
	    }else{
	    	$('#sort').val('');
	    }
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked", true);
	} else {
		$('#pk').val('');
		$('#navigationPk').val('');
		$("#secondNavigationPk").val('');
		$('#sort').val('');
	    $("input[name='isVisable'][value=1]").prop("checked", true);
	}
 
}

function  changeThirdNavigation(navigationPk){
	var secondNavigationPk = $("#secondNavigationPk").val();
	$.ajax({
		type : 'POST',
		url : './changeThirdNavigation.do',
		data : {
			pk :secondNavigationPk
		},
		dataType : 'json',
		success : function(data) {
			$("#navigationPk").empty();
			$("#navigationPk").append("<option value=''>--请选择--</option>");
			if (data.length>0) {
				for ( var i = 0; i < data.length; i++) {
					if(data[i].pk == navigationPk){
						$("#navigationPk").append(
								"<option value='" + data[i].pk + "'  selected>"
										+ data[i].name + "</option>");
					}else{
						$("#navigationPk").append(
								"<option value='" + data[i].pk + "'>"
										+ data[i].name + "</option>");
					}
					
					
				}
			}
	
		}
	});
	
}
/**
 * 启用、禁用
 * @param pk
 * @param isv
 */
function editBannerStatus(pk,isv) {
	var parm = {
		pk: pk,
	    isVisable:isv
	};
	$.ajax({
		type : 'POST',
		url : './updateThirdNavigationSort.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#homeId').bootstrapTable('refresh');
			}

		}
	});

}

/**
 * 修改排序
 * @param pk
 * @param sort
 */
function sort(pk,sort){

	if(sort !=""){
		$("#sortButton").val(sort);
		$("#editSortPk").val(pk);
	}else{
		$("#sortButton").val("");
		$("#editSortPk").val("");
	}
	$("#editSortId").modal();
}

/**
 * 保存修改排序
 */
function editSort(){
	if(!/^\+?[1-9]\d*$/.test($("#sortButton").val())) {
		new $.flavr({
			modal : false,
			content : "排序数字需大于0的整数！"
		});
		return;
		}
	
	if(!/^\d{1,6}$/.test($("#sortButton").val())) {
		new $.flavr({
			modal : false,
			content : "排序数字需在6位以内的整数！"
		});
		return;
		}
	$.ajax({
		type : 'POST',
		url : './updateThirdNavigationSort.do',
		data : {sort:$("#sortButton").val(),pk:$("#editSortPk").val()},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$(function () { $('#editSortId').modal('hide')});
				$('#homeId').bootstrapTable('refresh');
			}

		}
	});
}
function Search() {
	var cfg = {
		url : './homeThirdNavigationList.do?'+ urlParams(''),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homeId', cfg);
}

function Clean(){
	cleanpar();
	var cfg = {
			url : './homeThirdNavigationList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homeId', cfg);
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
				url : './updateThirdNavigationSort.do',
				data : {
					 pk : pk,
					 isDelete  : 2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#homeId').bootstrapTable('refresh');
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
	$(".fancybox").fancybox({
	    'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic',
		'titlePosition' : 'inside'
	});
	btnList();
}