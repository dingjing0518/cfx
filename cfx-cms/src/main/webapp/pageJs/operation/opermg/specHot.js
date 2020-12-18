$(function() {
	createDataGrid('specHotId', cfg);
});
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				var str = "";
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_MG_HOMESHOW_SPEC_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#specHotModel" onclick="javascript:edit(\''
					+ value
					+ '\',\''+row.sort+'\');">编辑</button></a>';
				if(row.status==2){
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SPEC_BTN_ONLINE" class="btn btn-warning"   onclick="javascript:editStatus(\''
						+ value + '\',1);">上线</button></a>';
				}else{
					str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SPEC_BTN_OFFLINE" class="btn btn-warning"   onclick="javascript:editStatus(\''
						+ value + '\',2);">下线</button></a>';
				}
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_SPEC_BTN_DEL" class="btn btn-default" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
									
				return str;
			}
		},
		{
			title : '品名',
			field : 'firstLevelName',
			width : 20,
			sortable : true
		},
		{
			title : '品种',
			field : 'secondLevelName',
			width : 20,
			sortable : true
		},
		{
			title : '规格',
			field : 'fourthLevelName',
			width : 20,
			sortable : true
		}  , {
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		} ,
		{
			title : '跳转地址',
			field : 'linkUrl',
			width : 20,
			sortable : true
		},{
			title : '是否上线',
			field : 'status',
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
			title : '添加时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		}
		];

var cfg = {
		url : './searchSpecHotList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
	sortOrder : 'desc',
    queryParams : function(params) {
        return {
            block:$("#block").val(),
            limit : params.limit,
            start : params.offset,
            orderType : params.order,
            orderName : params.sort
        };
    },
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
};

/**
 * 保存
 */
function submit() {
	if(!validNotHidden("#dataForm")){
		$("span[for='firstLevelPk']").children().eq(0).css("margin","3px -192px");
		$("span[for='secondLevelPk']").children().eq(0).css("margin","3px -192px");
		return;   
	 }
    var st = $("input[name='sort']").val();
	if(st != null && st != ""){
        if(isNotZeroDNumber(st) == false || st.length > 9){
            new $.flavr({
                modal : false,
                content : "输入的排序数字需大于零的整数并且小于十位数字"
            });
            return;
        }
	}
	var params = {};
	$('#specHotModel').find("input,select").each(function(){
		params[$(this).attr("name")] = $(this).val();
	});
	params['firstLevelName'] = $("button[data-id='firstLevelPk']").children().eq(0).text();
	params['secondLevelName'] =  $("button[data-id='secondLevelPk']").children().eq(0).text();
    params['block'] = $("#block").val();
		$.ajax({
			type : "POST",
			url : "./updateSpecHot.do",
			data : params,
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#specHotId').bootstrapTable('refresh');
				}
			}
		});
    $('#specHotId').bootstrapTable('refresh');
	}

/**
 * 新增
  */
function add(){
	$('#specHotModel').on('show.bs.modal', function () {
		$(".selectclose").addClass("open");
	});
	$("#specHotModel").find("input,select").each(function(){
		$(this).val("");
	});
	$("#dataForm .pull-left").text("--请选择--");
	$("#status").val(1);
}
$(function () { $('#specHotModel').on('hide.bs.modal', function () {
	$(".selectclose").removeClass("open");
	})
});
/**
 * 编辑
 * @param pk
 * @param sort
 */
function edit(pk,sort) {
	$('#specHotModel').modal();
	var data = $('#specHotId').bootstrapTable('getRowByUniqueId',pk);
	for(var d in data){
		$("#specHotModel").find("input[name='"+d+"']").val(data[d]);
		$("#specHotModel").find("select[name='"+d+"']").val(data[d]);
	}
    if(sort == null || sort == "" || sort == undefined || sort == "undefined"){
        $("input[name=\"sort\"]").val("");
    }else{
        $("input[name=\"sort\"]").val(sort);
    }
    $("button[data-id='secondLevelPk']").children().eq(0).text(data.secondLevelName);
    $("button[data-id='firstLevelPk']").children().eq(0).text(data.firstLevelName);
}

/**
 * 上线下线
 * @param pk
 * @param status
 */
function editStatus(pk,status){
	$.ajax({
		type : "POST",
		url : './updateSpecHotStatus.do',
		dataType : "json",
		data : {pk:pk,status:status},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#specHotId').bootstrapTable('refresh');
			}
		}
	});
}

/**
 * 删除
 * @param pk
 */
function del(pk){
	var confirm = new $.flavr({
		content : '确定刪除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : "POST",
				url : './delSpecHot.do',
				dataType : "json",
				data : {pk:pk},
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
					if (data.success) {
						$('#specHotId').bootstrapTable('refresh');
					}
				}
			});
		}
	});
}

/**
 * 搜索和清除
 * @param type
 */
function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './searchSpecHotList.do?'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('specHotId', cfg);
}

function loadFancyBox(){
	btnList();
}
 
