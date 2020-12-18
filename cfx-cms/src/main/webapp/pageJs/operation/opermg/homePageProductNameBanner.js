$(function() {
	createDataGrid('homePageProductNameTableBannerId', cfg);
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
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_NAME_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',1);">启用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_NAME_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',2);">禁用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_NAME_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_HOMESHOW_NAME_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editHomePageProductNameId" onclick="javascript:editHomePageProductNameBanner(\''
					+ value
					+ '\',\''
					+ row.productName
					+ '\',\''
					+ row.productPk
					+ '\',\''
					+ row.isVisable
					+ '\',\''
					+ row.sort
					+ '\',\''
					+ row.detail
					+ '\',\''
					+ row.url
					+ '\');">编辑</button></a>';
				
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_HOMESHOW_NAME_BTN_SORT" class="btn btn btn-primary" data-toggle="modal" data-target="#editSortId" onclick="javascript:sort(\''
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
		},{
			title : '描述',
			field : 'detail',
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
		},{
			title : '预览图',
			field : 'url',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				 var url="";
				 if(value!="" && value != null){
					 url="<img style='width:auto;height:40px;' src='"+value+"'/>";
				 }
				
				 return url;
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
		url : './searchHomePageProductNameBannerList.do',
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
		   url = "./insertHomePageProductNameBanner.do";
	   }else{
		   url = "./updateHomePageProductNameBanner.do";
	   }
	   
	   $.ajax({
		   type : "POST",
		   url : url,
		   data : {
			   pk : $("#pk").val(),
			   productPk:$("#homePageProductName").val(),
			   productName :$("#homePageProductName option:selected").text(),
			   sort : $("#sort").val(),
			   url:$('#tupian').val(),
			   detail:$('#detail').val(),
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
				   $('#homePageProductNameTableBannerId').bootstrapTable('refresh');
			   }
		   }
	   });
   }
}

/**
 * 编辑和添加
 * @param pk
 * @param name
 * @param productPk
 * @param isVisable
 * @param sort
 * @param detial
 * @param url
 */
$(function () { $('#editHomePageProductNameId').on('hide.bs.modal', function () {
	$(".pm").removeClass("open");
	$(".chooseslect").removeClass("open");
	})
});
function editHomePageProductNameBanner(pk,name,productPk,isVisable,sort,detial,url) {
	$(".kv-file-remove").click();
	$('#editHomePageProductNameId').on('show.bs.modal', function () {
		$(".pm").addClass("open");
		$(".chooseslect").addClass("open");
	});
	
	if(url==null||url==''){
		$("#PURL_flag").val(0);
	}else{
		$("#PURL_flag").val(1);
	}
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$("#homePageProductName").val(productPk);
		$("#homePageProductName option:selected").text(name)
		$('#homePageProductName').attr("disabled",true);
	    if(sort!="undefined"){
	    	$('#sort').val(sort);
	    }else{
	    	$('#sort').val('');
	    }
		$('#sort').attr("disabled",true);
		

		$("#PURL").css('display','block'); 
		
		$("#PURL").attr("src",url);
	    $('#tupian').val(url);
		if(detial!="undefined"){
	    	$('#detail').val(detial);
	    }else{
	    	$('#detail').val('');
	    }
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked", true);
		
		$("input[name='isVisable'][value=" + 1 + "]").attr("disabled",true);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",true);
		$('#updateType').val(2);
	} else {
		$('#pk').val('');
		$('#homePageProductName').val('');
		$('#sort').val('');
		$('#detail').val('');
		$('#tupian').val('');
		$('#url').val('');
		$("#PURL").css('display','none'); 
		$("#PURL").attr("src","");
	    $("input[name='isVisable'][value=1]").prop("checked", true);
	    $('#sort').attr("disabled",false);
	    $("input[name='isVisable'][value=" + 1 + "]").attr("disabled",false);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",false);
		$('#homePageProductName').attr("disabled",false);
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
		url : './updateIsVisAndDelHomePgProNameBanner.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#homePageProductNameTableBannerId').bootstrapTable('refresh');
			}

		}
	});

}
/**
 * 搜索
 * @constructor
 */
function Search() {
	var cfg = {
		url : './searchHomePageProductNameBannerList.do?'+ urlParams(''),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageProductNameTableBannerId', cfg);
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
	$('#editSortId').on('show.bs.modal', function () {
		$(".pm").addClass("open");
		$(".chooseslect").addClass("open");
	});
}
$(function () { $('#editSortId').on('hide.bs.modal', function () {
	$(".pm").removeClass("open");
	$(".chooseslect").removeClass("open");
	})
});
/**
 * 保存 修改排序
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
		url : './updateHomePageProductNameBannerSort.do',
		data : {sort:$("#sortButton").val(),pk:$("#editSortPk").val()},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$(function () { $('#editSortId').modal('hide')});
				$('#homePageProductNameTableBannerId').bootstrapTable('refresh');
			}

		}
	});
}

/**
 * 清除
 * @constructor
 */
function Clean(){
	cleanpar();
	var cfg = {
			url : './searchHomePageProductNameBannerList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageProductNameTableBannerId', cfg);
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
				url : './updateIsVisAndDelHomePgProNameBanner.do',
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
						$('#homePageProductNameTableBannerId').bootstrapTable('refresh');
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
 * 刷新列表
 */
function loadFancyBox(){
	$(".fancybox").fancybox({
	    'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic',
		'titlePosition' : 'inside'
	});
	btnList();
}