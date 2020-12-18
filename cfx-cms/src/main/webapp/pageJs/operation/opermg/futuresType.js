$(function() {

	var cfg = {
			url : './futuresTypeList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('futuresTypeId', cfg);
	
	if($("#block").val()=="cf"){
		$("#futuresTypeId").bootstrapTable('showColumn', 'currencyUnit');//显示某列
		$("#futuresTypeId").bootstrapTable('showColumn','unit');
	}else if ($("#block").val()=="sx"){
		$("#futuresTypeId").bootstrapTable('hideColumn', 'currencyUnit');//隐藏某列
		$('#futuresTypeId').bootstrapTable('hideColumn', 'unit');
	}
	
});

var toolbar = [];
var columns = [
{
	title : '操作',
	field : 'pk',
	width : 200,
	formatter : function(value, row, index) {
		var str = "";
			str += '<button id="editable-sample_new" class="btn btn-primary" style="display:none;" showname="OPER_MG_HOMESHOW_FUTURES_VARI_BTN_ENDIT"  data-toggle="modal" onclick="javascript:addFuturesType(\''
					+ value
					+ '\',\''
					+ row.name
					+ '\',\''
					+ row.isHome
					+ '\',\''
					+ row.sort
					+ '\',\''
					+ row.currencyUnit
					+ '\',\''
					+ row.unit
					+ '\'); ">编辑</button>';
			if(row.isVisable == 1){
				str += '<button  style="display:none;" showname="OPER_MG_HOMESHOW_FUTURES_VARI_BTN_DISABLE" type="button" onclick="javascript:editIsVisable(\''
					+ value
					+ '\',2); "  class="btn btn-danger active">禁用</button>';
			}else{
				str += '<button style="display:none;" showname="OPER_MG_HOMESHOW_FUTURES_VARI_BTN_ENABLE" type="button" onclick="javascript:editIsVisable(\''
					+ value
					+ '\',1); "  class="btn btn-danger active">启用</button>';
			}
			

		return str;
	}
}, 
		{
			title : '期货品种',
			field : 'name',
			width : 150,
			sortable : true
		},
		{
			title : '启用/禁用',
			field : 'isVisable',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				}else if (value == 2) {
					return "禁用";
				}
			}
		},
		{
			title : '首页显示',
			field : 'isHome',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "显示";
				}else if (value == 2) {
					return "不显示";
				}
			}
		},
		{
			title : '货币单位',
			field : 'currencyUnit',
			width : 150,
			sortable : true
		},{
			title : '单位',
			field : 'unit',
			width : 150,
			sortable : true
		},
		{
			title : '排序',
			field : 'sort',
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
			url : './futuresTypeList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('futuresTypeId', cfg);
}

/**
 * 新增和编辑
 * @param pk
 * @param name
 * @param isHome
 * @param sort
 */
function addFuturesType(pk, name, isHome, sort,currencyUnit,unit) {

	if (null != pk && '' != pk) {
		$("#modalLabel").html("编辑");
		$('#name').attr("disabled","disabled");
		$('#pk').val(pk),
		$('#name').val(name),
		$('#sort').val(sort),
		$("#currencyUnit").val(currencyUnit);
		$("#unit").val(unit);
		 $("input[name='isHome'][value=" +isHome + "]").prop("checked", true);
	} else {
		$("#modalLabel").html("新增");
		$('#name').attr("disabled",false);
		$("#name").val('');
		$("#sort").val('');
		$("#currencyUnit").val('');
		$("#unit").val('');
		 $("input[name='isHome'][value=1]").prop("checked", true);
		$("#pk").val('');
	}
	if($("#block").val()=="cf"){
		$("#currencyUnitDiv").show();
		$("#unitDiv").show();
	}else{
		$("#currencyUnitDiv").hide();
		$("#unitDiv").hide();
	}
	$('#editModel').modal();
}


/**
 * 启用、禁用
 * @param pk
 * @param isVisable
 */
function editIsVisable(pk,isVisable) {
	$.ajax({
		type : "POST",
		url : './updateFuturesType.do',
		data : {
			pk : pk,
			isVisable :isVisable
		},
		dataType : "json",
		success : function(data) {
			$("#quxiao").click();
			$('#futuresTypeId').bootstrapTable('refresh');
		}
	});
}

/**
 * 保存
 */
function submit() {
	if($('#sort').val()!=''&& $('#sort').val()!=null){
		if(!isDNumber($('#sort').val())||$('#sort').val()>=100){
			new $.flavr({
				modal : false,
				content : "排序应最多两位整数"
			});
			return;
		}
	}
	if (valid("#dateForm")) {
		$.ajax({
			type : 'post',
			url : './updateFuturesType.do',
			data : {
				pk : $('#pk').val(),
				name : $('#name').val(),
				sort : $('#sort').val(),
				currencyUnit : $('#currencyUnit').val(),
				unit : $('#unit').val(),
				isHome :$('input:radio[name="isHome"]:checked').val(),
				block:$('#block').val()
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
						$('#futuresTypeId').bootstrapTable('refresh');
					}
				}
			}
		});
	}
}

/**
 * 跳转到期货管理
 */
function  openFuturesPage(){
	var flag = $("#block").val()=="cf"?1:2;
	window.location = getRootPath() + "/futuresPage.do?modelType="+flag;
}


