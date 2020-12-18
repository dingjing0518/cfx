$(function() {
	createDataGrid('homePageMessageTableBannerId', cfg);
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
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_HOMEPAGE_MESSAGE_VISBANNER" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',1);">启用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="BTN_HOMEPAGE_MESSAGE_VISBANNER" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',2);">禁用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="BTN_HOMEPAGE_MESSAGE_DELETEBANNER" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="BTN_HOMEPAGE_MESSAGE_UPDATEBANNER" class="btn btn btn-primary" data-toggle="modal" data-target="#editHomePageProductNameId" onclick="javascript:editHomePageProductNameBanner(\''
					+ value
					+ '\',\''
					+ row.productnamePk
					+ '\',\''
					+ row.productName
					+ '\',\''
					+ row.isVisable
					+ '\',\''
					+ row.type
					+ '\',\''
					+ row.sort
					+ '\',\''
					+ row.url
					+ '\',\''
					+ row.linkUrl
					+ '\',\''
					+ row.urlObj
					+ '\',1);">编辑</button></a>';
				
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
		}, {
			title : '图片类型',
			field : 'type',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 2) {
					return "大图";
				} else if (value == 1) {
					return "小图";
				}  
			}
		},{
			title : '预览图',
			field : 'url',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
					var str = '';
					if(value!=undefined&&value.length>0){
						
							if(value != null && value != ""){
							str+="<img style='width:auto;height:40px;' class='bigpicture' src='"+value+"' >";
							}
					}
					return str;
			}
		},
		{
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		},
		{
			title : '跳转地址',
			field : 'linkUrl',
			width : 20,
			sortable : true
		},{
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}];

var cfg = {
		url : './searchHomePageMessageBannerList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'asc',
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
	   if($("#updateType").val() == 1){
		   url = "./insertHomePageMessageBanner.do";
	   }else{
		  url = "./updateHomePageMessageBanner.do";
	   }
	   
	   $.ajax({
		   type : "POST",
		   url : url,
		   data : {
			   pk : $("#pk").val(),
			   productnamePk:$("#homePageProductName").val(),
			   sort : $("#sort").val(),
			   type : $("#type").val(),
			   url:$('#tupian').val(),
			   linkUrl:$('#linkUrl').val(),
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
				   $('#homePageMessageTableBannerId').bootstrapTable('refresh');
			   }
		   }
	   });
	   
   }
}

/**
 * 编辑和添加
 * @param pk
 * @param productPk
 * @param name
 * @param isVisable
 * @param type
 * @param sort
 * @param url
 * @param linkUrl
 * @param urlObj
 */
function editHomePageProductNameBanner(pk,productPk,name,isVisable,type,sort,url,linkUrl,urlObj) {
	$(".kv-file-remove").click();
	$('#editHomePageProductNameId').modal();

	if(url==null||url==''){
		$("#PURL_flag").val(0);
	}else{
		$("#PURL_flag").val(1);
	}
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$("#homePageProductName").val(productPk);
		$("#homePageProductName option:selected").text(name)
		
		$("#type").val(type);
		if(type == 1){
			$("#type option:selected").text("小图");	
		}
		if(type == 2){
			$("#type option:selected").text("大图");	
		}
	    if(sort!="undefined"){
	    	$('#sort').val(sort);
	    }else{
	    	$('#sort').val('');
	    }
	    if(linkUrl!="undefined"){
	    	$('#linkUrl').val(linkUrl);
	    }else{
	    	$('#linkUrl').val('');
	    }
	   $('#sort').attr("disabled",true);
	    
	    
	    $("#PURL").attr("src",url);
	    $('#tupian').val(url);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked", true);
		$("input[name='isVisable'][value=" + 1 + "]").attr("disabled",true);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",true);
		$("#updateType").val(2);
	} else {
		$('#pk').val('');
		$('#homePageProductName').val('');
		$('#sort').val('');
		$('#type').val('');
		$('#tupian').val('');
		$('#url').val('');
		$('#linkUrl').val('');
		$("#PURL").attr("src","");
	    $("input[name='isVisable'][value=1]").prop("checked", true);
	    $("#updateType").val(1);
	    $('#sort').attr("disabled",false);
	    $("input[name='isVisable'][value=" + 1 + "]").attr("disabled",false);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",false);
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
		url : './updateIsVisAndDelMessageBanner.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#homePageMessageTableBannerId').bootstrapTable('refresh');
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
		url : './updateHomePageMessageBannerSort.do',
		data : {sort:$("#sortButton").val(),pk:$("#editSortPk").val()},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$(function () { $('#editSortId').modal('hide')});
				$('#homePageMessageTableBannerId').bootstrapTable('refresh');
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
		url : './searchHomePageMessageBannerList.do?'+ urlParams(''),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'asc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageMessageTableBannerId', cfg);
}

/**
 * 清除
 * @constructor
 */
function Clean(){
	cleanpar();
	var cfg = {
			url : './searchHomePageMessageBannerList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'asc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageMessageTableBannerId', cfg);
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
				url : './updateIsVisAndDelMessageBanner.do',
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
						$('#homePageMessageTableBannerId').bootstrapTable('refresh');
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