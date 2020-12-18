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
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_SECONDNAVIGATION_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editStatus(\''
						+ value + '\',1);">启用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_SECONDNAVIGATION_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editStatus(\''
						+ value + '\',2);">禁用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_SECONDNAVIGATION_BTN_DELETE" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_SECONDNAVIGATION_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editHome" onclick="javascript:editHome(\''
					+ value
					+ '\',\''
					+ row.navigation
					+ '\',\''
					+ row.navigationPk
					+ '\',\''
					+ row.parentNavigation
					+ '\',\''
					+ row.isVisable
					+ '\',\''
					+ row.sort
					+ '\',\''
					+ row.url
					+ '\');">编辑</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_SECONDNAVIGATION_BTN_UPSORT" class="btn btn btn-primary" data-toggle="modal" data-target="#editSortId" onclick="javascript:sort(\''
					+ value
					+ '\',\''
					+ row.sort
					+ '\');">排序修改</button></a>';
				return str;
			}
		} ,
		{
			title : '导航名称',
			field : 'navigation',
			width : 20,
			sortable : true,
			
			
		},
		{
			title : '所属一级导航',
			field : 'parentNavigation',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "化纤";
				} else if (value == 2) {
					return "纱线";
				}  else if (value == 3) {
					return "热销品牌";
				} 
			}
		},{
			title : '链接',
			field : 'url',
			width : 20,
			sortable : true,
			
			
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
		url : './homeSecondNavigationList.do',
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
	   var  parentNavigation  = $("#parentNavigation").val();
	   var navigation = "";
	   var navigationPk = "";
	   if(parentNavigation==3){//热销品牌
		   navigation = $("#navigationBrand").val();
	   }else{//化纤，纱线
		   navigationPk = $("#navigationPk").val();
		   navigation = $("#navigationPk option:selected").text();
	   }
	   
	   if(navigation==""||navigation=="--请选择--"){
		   new $.flavr({
				modal : false,
				content : "名称不能为空！！"
			});
			return;
	   }
	   
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
	   
	  
	   $.ajax({
		   type : "POST",
		   url : "./updateSecondNavigation.do",
		   data : {
			   pk : $("#pk").val(),
			   parentNavigation:parentNavigation,
			   navigationPk:navigationPk,
			   navigation:navigation,
			   sort : $("#sort").val(),
			   url : $("#url").val(),
			   isVisable : $('input:radio[name="isVisable"]:checked').val()
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
 */
function editHome(pk,navigation,navigationPk,parentNavigation,isVisable,sort,url) {
	$('#editHomeId').modal();
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$("#parentNavigation").val(parentNavigation);
		changeSecondNavigation(navigationPk);
		
		if(parentNavigation==3){//热销品牌
			$('#navigationBrand').val(navigation)
		}else{//纱线，化纤
			$("#navigationPk").val(navigationPk);
			$("#navigationPk option:selected").text(navigation)
		}
		
		//所属一级
		if(parentNavigation==1){
			$("#parentNavigation option:selected").text("化纤")
		}else if(parentNavigation==2){
			$("#parentNavigation option:selected").text("纱线")
		}else if(parentNavigation==3){
			$("#parentNavigation option:selected").text("热销品牌")
		}
		
		$('#sort').val(sort);
	    if(sort!="undefined"){
	    	$('#sort').val(sort);
	    }else{
	    	$('#sort').val('');
	    }
		$('#url').val(url);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked", true);
	} else {
		$("#urlDiv").hide();
		$("#navigationDiv").show();
		$("#navigationBrandDiv").hide();
		
		$('#pk').val('');
		$('#navigationPk').val('');
		$("#parentNavigation").val('');
		$('#sort').val('');
		$('#url').val('');
		$('#navigationBrand').val('')
	    $("input[name='isVisable'][value=1]").prop("checked", true);
	}
 
}
/**
 * 一级导航，联动二级
 * @param type
 */
function  changeSecondNavigation(navigationPk){
	var parentNavigation = $("#parentNavigation").val();
	if(parentNavigation == 1 || parentNavigation==2 ){
		$.ajax({
			type : 'POST',
			url : './changeSecondNavigation.do',
			data : {
				type :parentNavigation
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
		
		$("#navigationDiv").show();
		$("#navigationBrandDiv").hide();
		$("#urlDiv").hide();
	}else{
		
		$("#navigationDiv").hide();
		$("#navigationBrandDiv").show();
		$("#urlDiv").show();
	}

}
/**
 * 启用、禁用
 * @param pk
 * @param isv
 */
function editStatus(pk,isv) {
	var parm = {
		pk: pk,
	    isVisable:isv
	};
	$.ajax({
		type : 'POST',
		url : './updateSecondNavigationSort.do',
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
		url : './updateSecondNavigationSort.do',
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
		url : './homeSecondNavigationList.do?'+ urlParams(''),
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
			url : './homeSecondNavigationList.do',
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
				url : './updateSecondNavigationSort.do',
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