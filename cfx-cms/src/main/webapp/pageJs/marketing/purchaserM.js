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
	createDataGrid('purchaserMId', cfg);
});

var toolbar = [];
var columns = [
       		{
       			title : '操作',
       			field : 'pk',
       			width : 100,
       			formatter : function(value, row, index) {
           			var	str =	'<a class="save" href="javascript:;" style="text-decoration:none;"><button style="display:none;" showname="MARKET_CUST_PURCHASER_COL_CHECK" type="button"  class="btn btn btn-primary" data-toggle="modal" data-target="#childsCom" onclick="javascript:economicsList(\''+value+'\'); ">查看 </button> </a>';
			
						str+=' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button style="display:none;" showname="MARKET_CUST_PUR_BTN_DISTRIBUTE" type="button" class="btn btn btn-primary" data-toggle="modal" data-target="#editCompayId" onclick="javascript:editCompay(\'' + value + '\',\'' + row.accountPk + '\');">分配</button></a>';
				
				
                    	return str;
       			}
       		},
		{
			title : '公司名称',
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
		},
		{
			title : '所在地区',
			field : 'address',
			width : 20,
			sortable : true
		},
		{
			title : '营业执照五证合一',
			field : 'businessLicense',
			width : 50,
			sortable : true,
			formatter : function(value, row, index) {
				var str="";
				if(row.blUrl!=undefined){
					str+="<a rel='group"+index+"' title='营业执照' href='"+row.blUrl+"'><img class='bigpicture' src='"+row.blUrl+"' onerror='errorImg(this)'></a>";
				}
				if(row.ecUrl!=undefined){
					str+="<a rel='group"+index+"' title='企业认证授权书' href='"+row.ecUrl+"'><img class='bigpicture' src='"+row.ecUrl+"' onerror='errorImg(this)'></a>";
				}
				return str;
			}
		},
		{
			title : '业务经理',
			field : 'accountName',
			width : 20,
			sortable : true
		},
		{
			title : '区域经理',
			field : 'regionName',
			width : 20,
			sortable : true
		},{
			title : '注册时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},{
			title : '分配时间',
			field : 'distributeMemberTime',
			width : 20,
			sortable : true
		}];
var cfg = {
	url : './searchPurchaserMList.do?',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
};

/**
 * 保存线下业务员
 */
function submit(){
	if(valid("#dataForm")){
		var params={
			companyPk:$("#companyPk").val(),
			accountPk:$("#marketingCompany").val(),
			companyType:1
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
				$('#purchaserMId').bootstrapTable('refresh');
				}
			}
		});
	}
}

/**
 * 采购商分配线下业务员
 * @param pk
 * @param accountPk
 */
function editCompay(pk,accountPk) {
	$('#editCompayId').modal();
	$(".ywgl").addClass("open");
	if (null != pk && '' != pk) {
		$("#companyPk").val(pk);
		$("#marketingCompany").val(accountPk);
		$("#dataForm .pull-left").text($("#marketingCompany option[value='"+accountPk+"']").text());
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
			url : './searchPurchaserMList.do?'+urlParams(''),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('purchaserMId', cfg);
}

/**
 * 页面列表刷新
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

//金融产品通过查看列表
var columnsTwo = [

    {
        title : '金融产品',
        field : 'economicsGoodsName',
        width : 20,
        sortable : true
    },
    {
        title : '建议额度(W)',
        field : 'suggestAmount',
        width : 20,
        sortable : true
    },
    {
        title : '授信开始时间',
        field : 'creditStartTime',
        width : 20,
        sortable : true
    },{
        title : '授信结束时间',
        field : 'creditEndTime',
        width : 20,
        sortable : true
    },
    {
        title: '审核状态',
        field: 'status',
        width: 20,
        sortable: true,
        formatter: function (value, row, index) {
            if (row.status == 1) {
                return "待审核";
            } else if (row.status == 2) {
                return "审核通过";
            } else {
                return "审核不通过";
            }
        }
    },
    {
        title: '所属银行',
        field: 'bank',
        width: 20,
        sortable: true
    }];

/**
 * 金融产品申请通过列表
 * @param pk
 */
function economicsList(pk){
    var cfgs = {
        url : './searchCreditGoods.do.do?status=2&companyPk='+pk,
        columns : columnsTwo,
        uniqueId : 'pk',
        toolbar : toolbar,
        onLoadSuccess:loadFancyBox
    };
    createDataGrid('childsEconomics', cfgs);
}
