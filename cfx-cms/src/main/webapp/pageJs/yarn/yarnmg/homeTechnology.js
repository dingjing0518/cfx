$(function() {
	createDataGrid('homePageTechTableBannerId', cfg);
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
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="YARN_OPER_SHOW_TRCH_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',1);">启用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="YARN_OPER_SHOW_TRCH_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',2);">禁用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="YARN_OPER_SHOW_TRCH_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="YARN_OPER_SHOW_TRCH_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" onclick="javascript:editHomeTechnologyBanner(\''
					+ value
					+ '\',\''
					+ row.bannerrawmaterialPk
					+ '\',\''
					+ row.materialName
					+ '\',\''
					+ row.technologyPk
					+ '\',\''
					+ row.technologyName
					+ '\',\''
					+ row.isVisable
					+ '\',\''
					+ row.sort
					+ '\');">编辑</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="YARN_OPER_SHOW_TRCH_BTN_SORT" class="btn btn btn-primary" data-toggle="modal" data-target="#editSortId" onclick="javascript:sort(\''
					+ value
					+ '\',\''
					+ row.sort
					+ '\');">排序修改</button></a>';
				return str;
			}
		} ,
		{
			title : '原料',
			field : 'materialName',
			width : 20,
			sortable : true
		},
		{
			title : '工艺',
			field : 'technologyName',
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
		url : './searchBannerTechnologyList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
};

/**
 * 保存
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
	   var url = "";
	   if($("#updateType").val() == 1){
		   url = "./insertHomeTechBanner.do";
	   }else{
		  url = "./updateVDSTechBanner.do";
	   }
	   $.ajax({
		   type : "POST",
		   url : url,
		   data : {
			   pk : $("#pk").val(),
			   bannerrawmaterialPk:$("#bannerrawmaterialPk").val(),
			   technologyPk:$("#technologyPk").val(),
			   technologyName:$("#technologyPk option:selected").text(),
			   sort : $("#sort").val(),
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
				   $('#homePageTechTableBannerId').bootstrapTable('refresh');
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
 * @param varietiesPk
 * @param varietiesName
 * @param isVisable
 * @param sort
 */
function editHomeTechnologyBanner(pk,bannerrawmaterialPk,materialName,technologyPk,technologyName,isVisable,sort) {
	$(".kv-file-remove").click();
	$('#editHomeTechologyId').modal();
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$("#bannerrawmaterialPk").val(bannerrawmaterialPk);
		$("#bannerrawmaterialPk option:selected").text(materialName)
		
		$("#technologyPk").val(technologyPk);
		$("#technologyPk option:selected").text(technologyName)
		
		
	    if(sort!="undefined"){
	    	$('#sort').val(sort);
	    }else{
	    	$('#sort').val('');
	    }
		$("#sort").attr("disabled",true);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked", true);
		$("input[name='isVisable'][value=" + 1 + "]").attr("disabled",true);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",true);
		$("#updateType").val(2);
	} else {
		$('#pk').val('');
		$('#bannerrawmaterialPk').val('');
		$("#technologyPk").val('');
		$('#sort').val('');
	    $("input[name='isVisable'][value=1]").prop("checked", true);
	    $('#sort').attr("disabled",false);
	    $("input[name='isVisable'][value=" + 1 + "]").attr("disabled",false);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",false);
		$("#updateType").val(1);
	}
 
 
}

/**
 * 启用禁用
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
		url : './updateVDSTechBanner.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#homePageTechTableBannerId').bootstrapTable('refresh');
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
		url : './updateVDSTechBanner.do',
		data : {sort:$("#sortButton").val(),
			pk:$("#editSortPk").val()},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$(function () { $('#editSortId').modal('hide')});
				$('#homePageTechTableBannerId').bootstrapTable('refresh');
			}

		}
	});
}
function Search() {
	var cfg = {
		url : './searchBannerTechnologyList.do?'+ urlParams(''),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageTechTableBannerId', cfg);
}

function Clean(){
	cleanpar();
	var cfg = {
			url : './searchBannerTechnologyList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageTechTableBannerId', cfg);
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
				url : './updateVDSTechBanner.do',
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
						$('#homePageTechTableBannerId').bootstrapTable('refresh');
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