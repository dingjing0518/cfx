$(function() {
	createDataGrid('storeId', cfg);
});

var toolbar = [];
var columns = [
       		{
       			title : '操作',
       			field : 'pk',
       			width : 100,
       			formatter : function(value, row, index) {
       				var str = "";
       					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="MARKET_STORE_BTN_EDIT" class="btn btn-warning" onclick="javascript:editStore(\'' + value + '\'); ">编辑 </button> </a>';
       				
       					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new"  style="display:none;" showname="MARKET_STORE_BTN_ALUM" class="btn btn-warning" onclick="javascript:companyAlbum(\'' + value + '\'); ">公司相册 </button> </a>';
       				
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
			title : '店铺营业时间',
			field : 'sometimes',
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
			title : '手机号码',
			field : 'contactsTel',
			width : 20,
			sortable : true
		},
		{
			title : '库存设置',
			field : 'upperWeight',
			width : 20,
			sortable : true,
			formatter : function (value, row, index) {
				if(value != null && value != ''){
					return	"大于"+value+"吨显示有货";
				}else{
					return	value;
				}
					
			}
		},{
			title : '开店之前显示价格',
			field : 'showPricebfOpen',
			width : 20,
			sortable : true,
			formatter : function (value, row, index) {
				if(value==1){
					return	"显示";
				}else{
					return  "不显示";
				} 
			}
		},{
			title : '店铺开启状态',
			field : 'isOpen',
			width : 20,
			sortable : true,
			formatter : function (value, row, index) {
				if(value==1){
					return	"开启";
				}else{
					return  "关闭";
				} 
			}
		},
		{
			title : '店铺QQ',
			field : 'qq',
			width : 20,
			sortable : true
		},
		{
			title : '店铺LOGO',
			field : 'qq',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				var str="";
				if(row.logoSettings!=undefined){
					if(row.logoSettings != null && row.logoSettings != ''){
						str+="<a rel='group"+index+"' title='店铺LOGO' href='"+row.logoSettings+"'><img class='bigpicture' src='"+row.logoSettings+"'></a>";
					}else{
						str+="<img class='bigpicture' src='./style/images/bgbg.png'>";
					}
				}
				return str;
			}
		}
		];
var cfg = {
	url : './storeM_data.do'+urlParams(1),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'endTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:loadFancyBox
	
};

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

/**
 * 跳转到公司相册页面
 * @param pk
 */
function companyAlbum(pk) {
 window.location=getRootPath()+"/enditStoreAlbum.do?storePk="+pk;
}

/**
 * 跳转到店铺编辑页面
 * @param pk
 */
function editStore(pk) {
	window.location=getRootPath()+"/enditStore.do?storePk="+pk;
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './storeM_data.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'endTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('storeId', cfg);
}

