$(function() {
	var cfg = {
			url : './searchBannerList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('bannerId', cfg);
});
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				var startt=row.startTime;
				var endt=row.endTime;
				var onlinet=row.onlineTime;
				if(row.isVisable==2){
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_AD_ADSENSE_BTN_ONLINE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',\''+ row.type+ '\',1);">上线</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_AD_ADSENSE_BTN_OFFLINE" class="btn btn-warning"   onclick="javascript:editBannerStatus(\''
						+ value + '\',\''+ row.type+ '\',2);">下线</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_AD_ADSENSE_BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\',\''+ row.type+ '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_AD_ADSENSE_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:editBanner(\''
					+value+'\');">编辑</button></a>';
									
				return str;
			}
		}, {
			title : '是否上线',
			field : 'isVisable',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "上线";
				} else if (value == 2) {
					return "下线";
				}  
			}
		} ,
		{
			title : '名称',
			field : 'name',
			width : 20,
			sortable : true
		},

		{
			title : '预览图',
			field : 'url',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				 var url="";
				 if(value!=""){
					 url="<a class='fancybox' href='"+value+"'><img style='width:auto;height:30px;' src='"+value+"'/></a>";
				 }
				 return url;
			}
		}, {
			title : '放置栏目',
			field : 'type',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "首页";
				} else if (value == 3) {
					return "资讯";
				}  else if (value == 5) {
					return "竞拍";
				}  else if (value == 6) {
					return "活动";
				}  
			}
		}, {
			title : '放置平台',
			field : 'platform',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "pc";
				} else if (value == 2) {
					return "wap";
				}   else if (value == 3) {
                    return "app";
                }
            }
		}, {
			title : '对应位置',
			field : 'position',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "首页轮播";
				} else if (value == 2) {
					return "首页轮播下方";
				}  else if (value == 3) {
					return "资讯轮播图";
				}else if (value == 4) {
					return "在线竞拍";
				}else if (value == 5) {
					return "顶部活动广告";
				}else if (value == 6) {
					return "中部活动广告";
				}else if (value == 7) {
                    return "开机启动图";
                }
			}
		} , {
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		}, {
			title : '广告描述',
			field : 'details',
			width : 20,
			sortable : true
		},{
			title : '上线时间',
			field : 'onlineTime',
			width : 20,
			sortable : true
		}
		
//		,{
//			title : '开始时间',
//			field : 'startTime',
//			width : 20,
//			sortable : true
//		}
		,{
			title : '结束时间',
			field : 'endTime',
			width : 50,
			sortable : true
		}
		,{
			title : '跳转地址',
			field : 'linkUrl',
			width : 20,
			sortable : true
		}  ];



/**
 * 提交保存
 */
function submit() {
   var pk =$('#pk').val();
   var Turl = "";
   if(pk.length==0){
	   Turl='./insertBanner.do';
   }else{
	   Turl='./updateBanner.do';
   }
   if(validNotHidden("#dataForm")){
	   var params = {};
	   $("#myModal3").find("input,select,textarea").each(function(){
		   params[$(this).attr("name")] = $(this).val();
	   });
	   	 var  endTime =  new Date(Date.parse($("#endTime").val()));
		 var   onlineTime = new Date(Date.parse($("#onlineTime").val()));
		 if($("#endTime").val() == null || $("#endTime").val() == ""
			 || $("#onlineTime").val() == null || $("#onlineTime").val() == ""){
             new $.flavr({
                 modal : false,
                 content : "上线时间和结束时间不能为空"
             });
             return;
		 }

		   if(endTime < onlineTime){
				new $.flavr({
					modal : false,
					content : "上线时间不能大于结束时间"
				});
				return;
			}
			if(isNotZeroDNumber($("#sort").val()) == false || $("#sort").val().length > 9){
				new $.flavr({
					modal : false,
					content : "输入的排序数字需大于零的整数并且小于十位数字"
				});
                $("#submitId").attr("disabled",false);
				return;
			}
			params['onlineTime'] = onlineTime;
			params['endTime'] = endTime;
	   $.ajax({
		   type : "POST",
		   url : Turl,
		   data : params,
		   dataType : "json",
		   success : function(data) {
			   new $.flavr({
				   modal : false,
				   content : data.msg
			   }); /* Closing the dialog */
			   if (data.success) {
				   $("#quxiao").click();
				   $('#bannerId').bootstrapTable('refresh');
			   }
		   }
	   });
      // $("#submitId").attr("disabled",false);
   }
}

/**
 * 编辑
 * @param pk
 */
$(function () { $('#myModal3').on('hide.bs.modal', function () {
	$(".selectclose").removeClass("open");
	})
});
function editBanner(pk) {
	var data = $('#bannerId').bootstrapTable('getRowByUniqueId',pk);

	var url = '';
	if(data != null){
		url = data.url;	
	}

	$(".kv-file-remove").click();
	$('#myModal3').on('show.bs.modal', function () {
		$(".selectclose").addClass("open");
	});
	if(url==null||url==''){
		$("#PURL_flag").val(0);
	}else{
		$("#PURL_flag").val(1);
	}
	var imgId=document.getElementById("PURL");
	if (null != pk && '' != pk) {

        $("#details").val(data.details);
		if(url.length==0){
				imgId.style.display="none";
		}else{
			imgId.style.display="";
		}
		$("#PURL").attr("src",url);

		for(var d in data){
			$("#myModal3").find("input[name='"+d+"']").val(data[d]);
			$("#myModal3").find("select[name='"+d+"']").val(data[d]);
		}
	} else {
		var imgId=document.getElementById("PURL");
		imgId.style.display="none";
		$('#pk').val('');
		$('#name').val('');
		$('#type').val('');
		$('#url').val('');
		$('#linkUrl').val('');
		$('#sort').val('');
		$('#details').val('');
		imgId.src="";
	    $('#url').val('');
	    $("#isVisable").val(1);
	    $("#endTime").val("");
		$('#onlineTime').val("");
		$('#position').val("");
		$('#platform').val("");
		
		
	}
 
 
}

/**
 * 上下线
 * @param pk
 * @param type
 * @param isv
 */
function editBannerStatus(pk,type,isv) {
	var parm = {
		pk: pk,
	    isVisable:isv,
	    type:type
	};
	$.ajax({
		type : 'POST',
		url : './updateBanner.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#bannerId').bootstrapTable('refresh');
			}

		}
	});

}

/**
 * 切换不同Table
 * @param s
 */
function findBanner(s){
    $('#types').val(s);
	var cfg = {
			url : './searchBannerList.do'+ urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('bannerId', cfg);
}

/**
 * 搜索
 * @constructor
 */
function Search() {
    var type = $('#types').val();
	var cfg = {
		url : './searchBannerList.do'+ urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('bannerId', cfg);
}

/**
 * 清除
 * @constructor
 */
function Clean(){
    var type = $('#types').val();
	cleanpar();
	var cfg = {
			url : './searchBannerList.do'+urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('bannerId', cfg);
}


/**
 * 删除
 * @param pk
 * @param type
 */
function del(pk,type) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './updateBanner.do',
				data : {
					 pk : pk,
					 isDelete  : 2,
					 type:type
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#bannerId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}

function changeType(){
	if($("#type").val() == 6){
		$('#isNotTimeId').hide();
		$('#isTimeId').show();
	}else{
		$('#isTimeId').hide();
		$('#isNotTimeId').show();
	}
	
}

function loadFancyBox(){
	$(".fancybox").fancybox({
	    'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic',
		'titlePosition' : 'inside'
	});
	btnList();
}