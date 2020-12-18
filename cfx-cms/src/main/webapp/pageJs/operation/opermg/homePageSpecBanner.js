$(function() {
	createDataGrid('homePageSpecTableBannerId', cfg);
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
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_HOMEPAGE_SPEC_VISBANNER" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',1);">启用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_HOMEPAGE_SPEC_VISBANNER" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',2);">禁用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="BTN_HOMEPAGE_SPEC_DELETEBANNER" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_HOMEPAGE_SPEC_UPDATEBANNER" class="btn btn btn-primary" data-toggle="modal" data-target="#editHomePageProductNameId" onclick="javascript:editHomePageVarietiesBanner(\''
					+ value
					+ '\',\''
					+ row.productnamePk
					+ '\',\''
					+ row.productName
					+ '\',\''
					+ row.specPk
					+ '\',\''
					+ row.specName
					+ '\',\''
					+ row.isVisable
					+ '\',\''
					+ row.sort
					+ '\');">编辑</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_HOMEPAGE_UPDATEBANNER" class="btn btn btn-primary" data-toggle="modal" data-target="#editSortId" onclick="javascript:sort(\''
					+ value
					+ '\',\''
					+ row.sort
					+ '\');">排序修改</button></a>';
				return str;
			}
		} ,
		{
			title : '品名',
			field : 'productName',
			width : 20,
			sortable : true
		},
		{
			title : '规格',
			field : 'specName',
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
		url : './searchHomePageSpecBannerList.do',
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
	   var url = "";
	   if($('#updateType').val() == 1){
		   url = "./insertHomePageSpecBanner.do";
	   }else{
		   url = "./updateHomePageSpecBanner.do"; 
	   }
	   $.ajax({
		   type : "POST",
		   url : url,
		   data : {
			   pk : $("#pk").val(),
			   productnamePk:$("#homePageProductName").val(),
			   specPk:$("#homePageSpec").val(),
			   specName:$("#homePageSpec option:selected").text(),
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
				   $('#homePageSpecTableBannerId').bootstrapTable('refresh');
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
function editHomePageVarietiesBanner(pk,productPk,name,specPk,specName,isVisable,sort) {
	$(".kv-file-remove").click();
	$('#editHomePageVarietiesId').modal();
	
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$("#homePageProductName").val(productPk);
		$("#homePageProductName option:selected").text(name)
		
		$("#homePageSpec").val(specPk);
		$("#homePageSpec option:selected").text(specName)
		
		
	    if(sort!="undefined"){
	    	$('#sort').val(sort);
	    }else{
	    	$('#sort').val('');
	    }
		$("#sort").attr("disabled",true);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked", true);
		$("input[name='isVisable'][value=" + 1 + "]").attr("disabled",true);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",true);
		$('#updateType').val(2);
	} else {
		$('#pk').val('');
		$('#homePageProductName').val('');
		$("#homePageSpec").val('');
		$('#sort').val('');
	    $("input[name='isVisable'][value=1]").prop("checked", true);
	    $('#sort').attr("disabled",false);
	    $("input[name='isVisable'][value=" + 1 + "]").attr("disabled",false);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",false);
		$('#updateType').val(1);
	}
 
 
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
		url : './updateIsVisAndDelHomePgSpecBanner.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#homePageSpecTableBannerId').bootstrapTable('refresh');
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
		url : './updateHomePageSpecBannerSort.do',
		data : {sort:$("#sortButton").val(),pk:$("#editSortPk").val()},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$(function () { $('#editSortId').modal('hide')});
				$('#homePageSpecTableBannerId').bootstrapTable('refresh');
			}

		}
	});
}
function Search() {
	var cfg = {
		url : './searchHomePageSpecBannerList.do?'+ urlParams(''),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageSpecTableBannerId', cfg);
}

function Clean(){
	cleanpar();
	var cfg = {
			url : './searchHomePageSpecBannerList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageSpecTableBannerId', cfg);
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
				url : './updateIsVisAndDelHomePgSpecBanner.do',
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
						$('#homePageSpecTableBannerId').bootstrapTable('refresh');
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