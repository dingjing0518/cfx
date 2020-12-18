$(function() {
	createDataGrid('ecGoodsId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				
				var str = "";
				str += '<button type="button" onclick="javascript:del(\''
						+ value
						+ '\'); " style="display:none;" showname="EM_PRO_BTN_DEL" class="btn btn-danger active">删除</button>';
				str += '<button id="editable-sample_new" style="display:none;" showname="EM_PRO_BTN_EDIT" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="javascript:edit(\''
						+ value + '\'); ">编辑</button>';
				if (row.status == 1) {
					str += '<button type="button" onclick="javascript:editStatus(\''
							+ value
							+ '\',2); " style="display:none;" showname="EM_PRO_BTN_DISABLE" class="btn btn-warning active">禁用</button>';
				} else {
					str += '<button type="button" onclick="javascript:editStatus(\''
							+ value
							+ '\',1); " style="display:none;" showname="EM_PRO_BTN_ENABLE" class="btn btn-warning active">启用</button>';
				}
		
				return str;
			}
		}, {
			title : '产品名称',
			field : 'name',
			width : 20,
			sortable : true
		}, {
			title : '状态',
			field : 'status',
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
			title : '更新时间',
			field : 'updateTime',
			width : 30,
			sortable : true
		}, {
			title : '供应商店铺名称',
			field : 'storeNames',
			width : 30,
			sortable : true
		}
		];
var cfg = {
	url : './ecGoods_data.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};

function findStatus(s) {
	if (s != 0) {
		$("#status").val(s);
	} else {
		$("#status").val('');
	}
	var cfg = {
		url : './ecGoods_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('ecGoodsId', cfg);
}

/**
 * 金融中心-产品管理搜索和清除功能
 * @param type
 */
function searchTabs(type) {
	if (type == 2) {
		cleanpar();
	}
	var cfg = {
		url : './ecGoods_data.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess : btnList
	};
	createDataGrid('ecGoodsId', cfg);
}

/**
 * 保存编辑和新增金融产品
 */
function submit() {
	//绑定客户
	var datas = [];
	if(	$("#storeInfoPks").val() != null &&$("#storeInfoPks").val()!=undefined ){
		var arr = $("#storeInfoPks").val().split(',');
		var arrName = $("#storeInfoName").val().split(',');
		for(var i =0 ; i<arr.length;i++){
			var data = {// json对象 {key:value}
					"pk" : arr[i],
					"name" : arrName[i]
				};
			datas.push(data);// 数组的push方法
		}
	}
	
	if (valid("#dataForm")) {
		$.ajax({
			type : "POST",
			url : './updateEcGoods.do',
			data : {
				pk : $("#pk").val(),
				name : $("#name").val(),
				goodsType : $("#goodsType").val(),
				proportion:$('input:radio[name="proportion"]:checked').val(),
				storeInfo: JSON.stringify(datas)
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#ecGoodsId').bootstrapTable('refresh');
				}
			}
		});
	}
}

/**
 * 编辑金融产品
 * @param pk
 */
function edit(pk) {
	// 清空
	clean();
	$.ajax({
		type : "POST",
		url : './ecGoodsDetail.do',
		data : {
			pk : pk,
		},
		dataType : "json",
		success : function(data) {
			$("#pk").val(data.pk),
			$("#name").val(data.name),
			$("#goodsType").val(data.goodsType);
			$("input[name='proportion'][value='" + data.proportion + "']").prop("checked", true);
			//绑定店铺
			$("#storeInfoPks").val("");
			$("#storeInfoName").val("");
			if(data.storeInfo!=undefined&&data.storeInfo!=''){
				var content = JSON.parse(data.storeInfo);
				var arr = new Array(content.length)
				var arrName = new Array(content.length)
				for(var i= 0 ;i< content.length;i++){
					arr[i] = content[i].pk;
					arrName[i] =content[i].name;
					divContent( content[i].pk , content[i].name)
					
				}
				$("#storeInfoPks").val(arr.join(','));
				$("#storeInfoName").val(arrName.join(','));
			}
			searchTabsName();	
		}
		
	});
}

/**
 * 新增金融产品页面
 * @param pk
 */
function editGoods(pk) {
	$("#pk").val(pk);
	$("#myModalLabel").html("新增产品");
	clean();
	searchTabsName();	
}

/**
 * 新增时清除到所有新增页面输入框里的数据
 */
function clean() {
	$("#dataForm").find("input[type='text'],input[type='hidden']").each(function() {
				$(this).val("");
			});
	$("input[name='proportion'][value='1.0']").prop("checked", true);
	$("#goodsType").val("");
	$("#storeDiv").html("");
	$("#storeName").val("");
}

/**
 * 删除金融产品
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
				url : './updateEcGoods.do',
				data : {
					pk : pk,
					isDelete : 2
				},
				dataType : "json",
				success : function(data) {
					$("#quxiao").click();
					$('#ecGoodsId').bootstrapTable('refresh');
				}
			});
		}
	});
}

/**
 * 启用、禁用
 * @param pk
 * @param status
 */
function editStatus(pk,status) {
	$.ajax({
		type : "POST",
		url : './updateEcGoods.do',
		data : {
			pk : pk,
			status : status
		},
		dataType : "json",
		success : function(data) {
			$("#quxiao").click();
			$('#ecGoodsId').bootstrapTable('refresh');
		}
	});
}

/**
 * 搜索店铺页面名称
 */
function searchTabsName(){
	var url = './storeBind.do?name='+$("#storeName").val();
	var cfg = {
			url : url,
			columns : columnStore,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
		};
	createDataGrid('stores', cfg);
}


var columnStore = [
	          		{
	          			title : '选择',
	          			field : 'pk',
	          			width : 20,
	          			formatter : function(value, row, index) {
	          				var str ="";
	          				if(	$("#storeInfoPks").val() != null &&$("#storeInfoPks").val()!=undefined ){
	          					if($("#storeInfoPks").val().split(',').indexOf(row.pk)!=-1){
		          					str =  "<input type='checkbox' value='"+row.pk+"' checked='checked' onchange='changeStore(this.value)'>";
	          					}else{
		          					str =  "<input type='checkbox' value='"+row.pk+"' onchange='changeStore(this.value,this.text)'>";
	          					}
	          				}
	          				return str;
	          			}},
			   		{
			   			title : '客户名称',
			   			field : 'name',
			   			width : 40,
			   			sortable : true
			   		}];

/**
 * 修改复选框 获取改变要绑定店铺的值
 * @param self
 */
function changeStore(self){
	var rows = $('#stores').bootstrapTable('getRowByUniqueId',self);//行的数据
	if(	$("#storeInfoPks").val() != null &&$("#storeInfoPks").val()!=undefined ){
		var arr = $("#storeInfoPks").val().split(',');
		var arrName = $("#storeInfoName").val().split(',');
		var index = arr.indexOf(self);
		if(index!=-1){
			arr.splice(index, 1); 
			arrName.splice(index, 1); 
			$("#stores input:checkbox[value="+self+"]").prop("checked",false);
			$("#"+self).remove();
		}else{
			arr.push(self) ;
			arrName.push(rows.name);
			divContent( self , rows.name)
		}
		$("#storeInfoPks").val(arr.join(',').replace(/^(\s|,)+|(\s|,)+$/g, ''));
		$("#storeInfoName").val(arrName.join(',').replace(/^(\s|,)+|(\s|,)+$/g, ''));
	}else{
		$("#storeInfoPks").val(self);
		$("#storeInfoName").val(rows.name);
		divContent( self , rows.name)
	}
}
function  divContent( pk , name){
	if(pk!=''&&pk!=undefined &&name!=''&name!=undefined){
		$("#storeDiv").append(	'<label  class="col-lg-2 col-lg-3" style="width: auto;" id ="'+pk+'"><span>'+name+'</span><div class=" col-sm-2 storecheck">'+
				'<p class="deleteStore"  onclick="javascript:deleteStore('+"'"+pk+"'"+');">X</p></div></label>');
	}
}

function deleteStore (self){
	var arr = $("#storeInfoPks").val().split(',');
	var arrName = $("#storeInfoName").val().split(',');
	var index = arr.indexOf(self);
	if(index!=-1){
		arr.splice(index, 1); 
		arrName.splice(index, 1); 
	}
	$("#stores input:checkbox[value="+self+"]").prop("checked",false);
	$("#"+self).remove();
	$("#storeInfoPks").val(arr.join(',').replace(/^(\s|,)+|(\s|,)+$/g, ''));
	$("#storeInfoName").val(arrName.join(',').replace(/^(\s|,)+|(\s|,)+$/g, ''));
}
