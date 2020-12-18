function changeCity(self){
	var pk = $(self).val();
	$("#city").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#area").empty().append("<option value=''>区</option>");
	
}

function changeArea(self){
	$("#area").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
	$("#town").empty().append("<option value=''>镇</option>");
}

function changeCitySearch(self){
	var pk = $(self).val();
	$("#cityName").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#areaName").empty().append("<option value=''>区</option>");
}

function changeAreaSearch(self){
	$("#areaName").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
}

function getRegions(pk){
	if(pk==""){
		pk="-2"
	}
	var html = "";
	$.ajax({
		type : 'GET',
		url : "./searchSysRegionsList.do",
		data : {parentPk:pk},
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data){
				for ( var i = 0; i < data.length; i++) {
					html +="<option value='"+data[i].pk+"'>"+data[i].name+"</option>";
				}
			}
		}
	});
	return html;
}


$(function() {
	createDataGrid('supplierMId', cfg);
});

var toolbar = [];
var columns = [
       		{
       			title : '操作',
       			field : 'pk',
       			width : 100,
       			formatter : function(value, row, index) {
       				var	str = ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname = "MARKET_CUST_SUP_BTN_DISTRIBUTE" class="btn btn btn-primary" data-toggle="modal" data-target="#editCompayId" onclick="javascript:editCompay(\'' + value + '\',\'' + row.accountPk + '\');">分配</button></a>';
       				return str;
       			}
       		},
		{
			title : '店铺名称',
			field : 'name',
			width : 20,
			sortable : true
		},
		{
			title : '联系人',
			field : 'contacts',
			width : 20,
			sortable : true
		},
		{
			title : '电话',
			field : 'contactsTel',
			width : 20,
			sortable : true
		},{
			title : '所在地区',
			field : 'address',
			width : 20,
			sortable : true
		},{
			title : '业务经理',
			field : 'accountName',
			width : 20,
			sortable : true
		},{
			title : '区域经理',
			field : 'regionName',
			width : 20,
			sortable : true
		},{
			title : '分配时间',
			field : 'distributeMemberTime',
			width : 20,
			sortable : true
		}];
var cfg = {
	url : './supplierM_data.do?',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'pk',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
};

/**
 * 保存分配线下业务员
 */
function submit(){
	if(valid("#dataForm")){
		var params={
			companyPk:$("#companyPk").val(),
			accountPk:$("#marketingCompany").val(),
			companyType:2
		};
		$.ajax({
			type : 'POST',
			url : "./updateMarketingCompany.do",
			data : params,
			dataType : 'json',
			async : true,
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
				$("#quxiao").click();
				$('#supplierMId').bootstrapTable('refresh');
				}
			}
		});
	}
}

/**
 * 分配线下业务员
 * @param pk
 * @param accountPk
 */
function editCompay(pk,accountPk) {
	$('#editCompayId').on('show.bs.modal', function () {
		$(".ywgl").addClass("open");});
	if (null != pk && '' != pk) {
		$("#companyPk").val(pk);
		$("#marketingCompany").val(accountPk);
		$("#editCompayId .pull-left").text($("#marketingCompany option[value='"+accountPk+"']").text());
	} else {
		$("#companyPk").val('');
	}
}
$(function () { $('#editCompayId').on('hide.bs.modal', function () {
	$(".ywgl").removeClass("open");})
});
function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './supplierM_data.do?'+urlParams(''),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'pk',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('supplierMId', cfg);
}

/**
 * 刷新列表页面
 */
function loadFancyBox(){
	for(var i=0;i<200;i++){
		if($("a[rel=group"+i+"]").attr("title")!=undefined&&$("a[rel=group"+i+"]").attr("href")!=''){
				//图片弹出框
			$("a[rel=group"+i+"]").fancybox({
					'titlePosition' : 'over',
					'cyclic'        : true,
					'titleFormat'	: function(title, currentArray, currentIndex, currentOpts) {
						return '<span id="fancybox-title-over">' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
					}
				});
			}
	}
	btnList();
}
