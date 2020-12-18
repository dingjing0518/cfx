$(function() {
	var cfg = {
			url : './futuresList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('futuresId', cfg);
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
			str += '<button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_FUTURES_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:addFutures(\''
					+ row.id
					+ '\',\''
					+ row.futuresPk
					+ '\',\''
					+ row.futuresName
					+ '\',\''
					+ row.price
					+ '\',\''
					+ row.date
					+ '\'); ">编辑</button>';
			str += '<button type="button" style="display:none;" showname="OPER_MG_HOMESHOW_FUTURES_BTN_DEL"  onclick="javascript:del(\''
				+ row.id
				+ '\'); "  class="btn btn-danger active">删除</button>';

		return str;
	}
}, 
		{
			title : '期货品种',
			field : 'futuresName',
			width : 150,
			sortable : true
		},
		{
			title : '单价',
			field : 'price',
			width : 150,
			sortable : true
		},
		{
			title : '时间（收盘）',
			field : 'date',
			width : 150,
			sortable : true
		},
		{
			title : '修改时间',
			field : 'updateTime',
			width : 150,
			sortable : true
		}
		];

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './futuresList.do'+urlParamsNew(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('futuresId', cfg);
}

/**
 * 获取要提交页面参数
 * @param type
 * @returns {string}
 */
function urlParamsNew(type){
	var params = "";
	$("header").find("select,input").each(function(i,n){
		if(type==1&&i==1){
			sign="?";
		}else{
			sign="&";
		}
		if($(n).attr("name")==undefined){
			return true;
		}
		params+=sign+$(n).attr("name")+"="+$(n).val();
	});
	return encodeURI(params);
}

/**
 * 编辑和新增
 * @param pk
 * @param futuresPk
 * @param futuresName
 * @param price
 * @param date
 */
function addFutures(pk, futuresPk, futuresName, price, date) {
	
	if (null != pk && '' != pk) {
		$("#modalLabel").html("编辑");
		$('#pk').val(pk),
		$('#price').val(price),
		$('#date').val(date),
		$("button[data-id=futuresType] span.filter-option").html(futuresName);
		$("button[data-id=futuresType]").attr("title", futuresName);
		$("#futuresType").val(futuresPk);
        $('#futuresType').attr("disabled",true);
        $('.dropdown-menu.open').addClass('none');
    } else {
		$("#modalLabel").html("新增");
		$("#date").val(new Date().Format("yyyy-MM-dd"));
		$("#price").val('');
		$("button[data-id=futuresType] span.filter-option").html("--请选择--");
		$("button[data-id=futuresType]").attr("title", "--请选择--");
		$("#futuresType").val("");
		$("#pk").val('');
		$("#futuresType").attr("disabled",false);
        $('.dropdown-menu.open').removeClass('none');

	}
	$('#editModel').modal();
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
				type : "POST",
				url : './delFutures.do',
				data : {
					pk : pk,
				},
				dataType : "json",
				success : function(data) {
					$("#quxiao").click();
					$('#futuresId').bootstrapTable('refresh');
				}
			});
		}
	});
}

/**
 * 提交
 */
function submit() {
	
	if($('#price').val()!=''&& $('#price').val()!=null){
		if(!isNumTwo($('#price').val())||$('#price').val()>100000||0>=$('#price').val()){
			new $.flavr({
				modal : false,
				content : "单价应为（0,100000】的数，小数位不超过两位！"
			});
			return;
		}
	}
	
	if (valid("#dateForm")) {
		$.ajax({
			type : 'post',
			url : './updateFutures.do',
			data : {
				id : $('#pk').val(),
				futuresPk : $('#futuresType').val(),
				price : $('#price').val(),
				date : $('#date').val(),
				futuresName :$("#futuresType").val()==""?"": $("button[data-id=futuresType]").attr("title"),
				block:$("#block").val()
			},
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success != null) {
					new $.flavr({
						modal : false,
						content : data.msg
					});
					if(data.success == true){
						$("#quxiao").click();
						$('#futuresId').bootstrapTable('refresh');
					}
				
				}
			}
		});
	}
}


/**
 * 跳转到品种管理列表页面
 */
function returnFuturesType(){
    window.location = getRootPath() + "/futuresTypePage.do?modelType="+$("#block").val();

}

	
