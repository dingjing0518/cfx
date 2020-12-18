$(function() {
	createDataGrid('homePageSeriesTableBannerId', cfg);
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
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SERIES_BTN_ENABLE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',1);">启用</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SERIES_BTN_DISABLE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',2);">禁用</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SERIES_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_HOMESHOW_SERIES_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editHomePageProductNameId" onclick="javascript:editHomePageVarietiesBanner(\''
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
					+ row.seriesPk
					+ '\',\''
					+ row.seriesName
					+ '\',\''
					+ row.varietiesPk
					+ '\',\''
					+ row.varietiesName
					+ '\',\''
					+ row.isVisable
					+ '\',\''
					+ row.sort
					+ '\');">编辑</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_HOMESHOW_SERIES_BTN_SORT" class="btn btn btn-primary" data-toggle="modal" data-target="#editSortId" onclick="javascript:sort(\''
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
			title : '品种',
			field : 'varietiesName',
			width : 20,
			sortable : true
		},
		{
			title : '规格',
			field : 'specName',
			width : 20,
			sortable : true
		},
		{
			title : '规格系列',
			field : 'seriesName',
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
		url : './searchHomePageSeriesBannerList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
};
 
function submit() {
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
	   if($("#pk").val()==''){
		   url = "./insertHomePageSeriesBanner.do";
	   }else{
		   url = "./updateHomePageSeriesBanner.do"; 
	   }
	   
	   $.ajax({
		   type : "POST",
		   url : url,
		   data : {
			   pk : $("#pk").val(),
			   productnamePk:$("#homePageProductName").val(),
			   varietiesPk:$("#homePageVarietiesName").val(),
			   varietiesName:$("#homePageVarietiesName option:selected").text(),
			   specPk:$("#homePageSpec").val(),
			   specName:$("button[data-id='homePageSpec']").children().eq(0).text(),
			   seriesPk:$("#homePageSeries").val(),
			   seriesName:$("button[data-id='homePageSeries']").children().eq(0).text(),
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
				   $('#homePageSeriesTableBannerId').bootstrapTable('refresh');
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
 * @param seriesPk
 * @param seriesName
 * @param varietiesPk
 * @param varietiesName
 * @param isVisable
 * @param sort
 */
function editHomePageVarietiesBanner(pk,productPk,name,specPk,specName,seriesPk,seriesName,varietiesPk,varietiesName,isVisable,sort) {
	$(".kv-file-remove").click();
	$('#editHomePageVarietiesId').modal();
	$("button[data-id='homePageSpec']").css("width","460px");
	$("button[data-id='homePageSeries']").css("width","460px");

	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$("#homePageProductName").val(productPk);
	   $("#homePageVarietiesName").empty().append("<option value='"+varietiesPk+"'>"+varietiesName+"</option>");

        $.ajax({
            type : 'POST',
            url : './getchangeVirietiesName.do',
            data : {
                productnamePk : productPk
            },
            dataType : 'json',
            success : function(data) {
                if (data) {
                        $("#homePageVarietiesName").empty().append('<option value="">--请选择--</option>');
                        for ( var i = 0; i < data.length; i++) {

                        	if(data[i].varietiesPk == varietiesPk){
                                $("#homePageVarietiesName").append(
                                    "<option value='" + data[i].varietiesPk + "' selected>"
                                    + data[i].varietiesName + "</option>");
							}
                            else{
                                $("#homePageVarietiesName").append(
                                    "<option value='" + data[i].varietiesPk + "'>"
                                    + data[i].varietiesName + "</option>");
							}
                        }
                       // $("#homePageVarietiesName").selectpicker('refresh');//
                }
            }
        });
        $.ajax({
            type : 'POST',
            url : './getspecByPid.do',
            data : {
                parentPk : specPk
            },
            dataType : 'json',
            success : function(data) {

                    $("#homePageSeries").empty().append("<option value=''>--请选择--</option>");
                    if (data.length > 0) {
                        for ( var i = 0; i < data.length; i++) {
                        	if(seriesPk == data[i].pk){
                                $("#homePageSeries").append(
                                    "<option value='" + data[i].pk + "' selected>"
                                    + data[i].name + "</option>");
							}else{
                                $("#homePageSeries").append(
                                    "<option value='" + data[i].pk + "'>"
                                    + data[i].name + "</option>");
							}
                        }
                    }
                    $("#homePageSeries").selectpicker('refresh');//
            }
        });


		if(sort!="undefined"){
	    	$('#sort').val(sort);
	    }else{
	    	$('#sort').val('');
	    }
	    $('#sort').attr("disabled",true);
		$("input[name='isVisable'][value=" + isVisable + "]").prop("checked", true);
		
		$("input[name='isVisable'][value=" + 1 + "]").attr("disabled",true);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",true);



		$("#homePageSpec").val(specPk);
		$("button[data-id='homePageSpec']").children().eq(0).text(specName);
		/*$("#homePageSeries").val(seriesPk);
		$("button[data-id='homePageSeries']").children().eq(0).text(seriesName);*/
	} else {
		$('#pk').val('');
		$('#homePageProductName').val('');
		 $("#homePageVarietiesName").empty().append("<option value=''>--请选择--</option>");
		$("#homePageSpec").val('');
		$('#sort').val('');
		$("#homePageSeries").val('');
	    $("input[name='isVisable'][value=1]").prop("checked", true);
	    $('#sort').attr("disabled",false);
	    $("input[name='isVisable'][value=" + 1 + "]").attr("disabled",false);
		$("input[name='isVisable'][value=" + 2 + "]").attr("disabled",false);
		$("#homePageSpec").val('');
		$("#homePageSeries").val('');
		$("button[data-id='homePageSpec']").children().eq(0).text('--请选择--');
		$("button[data-id='homePageSeries']").children().eq(0).text('--请选择--');
	}
}

/**
 * 品名改变触发品种选项改变
 * @param self
 * @param type
 */
function changeVirietiesName(self,type) {
    $.ajax({
        type : 'POST',
        url : './getchangeVirietiesName.do',
        data : {
            productnamePk : $(self).val()
        },
        dataType : 'json',
        success : function(data) {
            if (data) {
                if(type==1){
                    $("#varietiesPk").empty().append('<option value="">--请选择--</option>');
                    for ( var i = 0; i < data.length; i++) {
                        $("#varietiesPk").append(
                            "<option value='" + data[i].varietiesPk + "'>"
                            + data[i].varietiesName + "</option>");
                    }
                    $("#varietiesPk").selectpicker('refresh');//
                }else{
                    $("#homePageVarietiesName").empty().append('<option value="">--请选择--</option>');
                    for ( var i = 0; i < data.length; i++) {
                        $("#homePageVarietiesName").append(
                            "<option value='" + data[i].varietiesPk + "'>"
                            + data[i].varietiesName + "</option>");
                    }
                }
            }
        }
    });
}

/**
 * 规格改变触发规格系列选项改变
 * @param self
 * @param type
 */
function changeSpec(self,type){
//	 document.getElementById("addSeriesPk").style.display="none";
    $.ajax({
        type : 'POST',
        url : './getspecByPid.do',
        data : {
            parentPk : $(self).val()
        },
        dataType : 'json',
        success : function(data) {
            if(type==1){
                $("#seriesPk").empty().append("<option value=''>--请选择--</option>");
                if (data.length > 0) {
                    for ( var i = 0; i < data.length; i++) {
                        $("#seriesPk").append(
                            "<option value='" + data[i].pk + "'>"
                            + data[i].name + "</option>");
                    }
                }
                $("#seriesPk").selectpicker('refresh');//
            }else{
                $("#homePageSeries").empty().append("<option value=''>--请选择--</option>");
                if (data.length > 0) {
                    for ( var i = 0; i < data.length; i++) {
                        $("#homePageSeries").append(
                            "<option value='" + data[i].pk + "'>"
                            + data[i].name + "</option>");
                    }
                }
                $("#homePageSeries").selectpicker('refresh');//
            }
        }
    });
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
		url : './updateIsVisAndDelHomePgSeriesBanner.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#homePageSeriesTableBannerId').bootstrapTable('refresh');
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
 * 保存排序
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
		url : './updateHomePageSeriesBannerSort.do',
		data : {sort:$("#sortButton").val(),pk:$("#editSortPk").val()},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$(function () { $('#editSortId').modal('hide')});
				$('#homePageSeriesTableBannerId').bootstrapTable('refresh');
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
		url : './searchHomePageSeriesBannerList.do'+ urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageSeriesTableBannerId', cfg);
}

/**
 * 清除
 * @constructor
 */
function Clean(){
	cleanpar();
	var cfg = {
			url : './searchHomePageSeriesBannerList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('homePageSeriesTableBannerId', cfg);
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
				url : './updateIsVisAndDelHomePgSeriesBanner.do',
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
						$('#homePageSeriesTableBannerId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}

function queryChangeProductName(){
	$.ajax({
		type : 'POST',
		url : './getHomePageSpec.do',
		data : {
			pk : $("#productnamePk").val()//cf_sys_home_banner_productname 表的pk
		},
		dataType : 'json',
		success : function(data) {
			$("#specPk").empty().append('<option value="">--请选择--</option>');
			if (data) {
				for ( var i = 0; i < data.length; i++) {
					$("#specPk").append(
							"<option value='" + data[i].pk + "'>"
									+ data[i].specName + "</option>");
				}
			}
			$("#specPk").selectpicker('refresh');//
		}
	});
}


function AddChangeSpec(){
	 document.getElementById("addSeriesPk").style.display="none";
	$.ajax({
		type : 'POST',
		url : './getspecByPid.do',
		data : {
			parentPk : $("#addSpecPk").val()
		},
		dataType : 'json',
		success : function(data) {
			
			$("#addSeriesPk").empty();
			$("#addSeriesPk").append("<option value=''>---请选择规格系列---</option>");
			if (data.length > 0) {
				 
				for ( var i = 0; i < data.length; i++) {
					$("#addSeriesPk").append(
							"<option value='" + data[i].pk + "'>"
									+ data[i].name + "</option>");
				}
				
			}
			$("#addSeriesPk").selectpicker('refresh');//
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