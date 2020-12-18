$(function() {
	createDataGrid('dimensionalityManageIdEx', cfg);
});

var toolbar = [];
var columns = [ {
				checkbox : true,
                formatter:stateFormatter
				},{
					title : '公司名称',
					field : 'companyName',
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
					title : '维度',
					field : 'dimenName',
					width : 20,
					sortable : true
				}, 
				{
					title : '类型',
					field : 'dimenTypeName',
					width : 20,
					sortable : true
				}, 
				{
					title : '订单号',
					field : 'orderNumber',
					width : 20,
					sortable : true
				},
				{
					title : '赠送周期',
					field : 'periodType',
					width : 20,
					sortable : true,
					align: 'center',
					formatter : function(value, row, index) {
						if (value == 1) {
							return "一次赠送";
						} else  if(value==2){
							return "每天";
						}else  if(value==3){
							return "每周";
						}
					},
					footerFormatter:'汇总：'
				}, 
				{
					title : '纤贝数',
					field : 'fibreShellNumber',
					width : 20,
					sortable : true,
					align: 'center',
					footerFormatter: function (value) {
						var count = 0; 
						for (var i in value) {

                            if(parseFloat(value[i].fibreShellNumber).toString() !="NaN"){

                                count += value[i].fibreShellNumber;
                            }

							} 
						return '纤贝数:'+count.toFixed(2)+'  ';
						}
				}, 
				
				{
					title : '经验值',
					field : 'empiricalValue',
					width : 20,
					sortable : true,
					align: 'center',
					footerFormatter: function (value) {
						var count = 0; 
						for (var i in value) {

                            if(parseFloat(value[i].empiricalValue).toString() !="NaN"){
                                count += value[i].empiricalValue;
                            }
							} 
						return '经验值:'+count.toFixed(2)+'  ';
						}
				} ,
				{
					title : '满足条件',
					field : 'conditionTypeName',
					width : 20,
					sortable : true
				} ,
				{
					title : '时间范围',
					field : 'timesDetail',
					width : 20,
					sortable : true
				},
				{
					title : '启用/禁用',
					field : 'isVisable',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "启用";
						} else  if(value==2){
							return "禁用";
						}
		
					}
				},{
					title : '更新时间',
					field : 'updateTime',
					width : 20,
					sortable : true
				}];
var cfg = {
	url : './searchDimensionalityExtPresentList.do?type=2',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	showFooter:true,
	onLoadSuccess:btnList,
	pageList: [5, 10, 25, 50,100,1000]
	
};


function stateFormatter(value, row, index) {
    if (row.rowstate == true)
        return {
            disabled : true,//设置是否可用
            checked : true//设置选中
        };
    return value;
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
		$("#dimenCategory").empty().append("<option value=''>--请选择--</option>");
		$("#dimenCategory").selectpicker('refresh');
	}
	var cfg = {
			url : './searchDimensionalityExtPresentList.do'+urlParamsByHeader(1,"ewaijiangli"),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			showFooter:true,
			onLoadSuccess:btnList,
			pageList: [5, 10, 25, 50,100,1000]
		};

	createDataGrid('dimensionalityManageIdEx', cfg);
}
function changeDimenSelect(){
	if( $("#dimenType").val()==""){
		$("#dimenCategory").empty().append("<option value=''>--请选择--</option>");
		$("#dimenCategory").selectpicker('refresh');//
	}else{
		$.ajax({
			type : "POST",
			url : "./getDimenReListByCategory.do",
			data : {
				type : $("#dimenType").val()
			},
			dataType : "json",
			success : function(data) {
				var html = "<option value=''>--请选择--</option>";
				
				if(data != null && data != ''){
					//var html = "<option value=''>--请选择--</option>";
					for(var m = 0; m < data.length; m++){
						html += "<option value="+data[m].dimenType+">"+data[m].dimenTypeName+"</option>";	
					}
					$("#dimenCategory").empty().append(html);
				}
				$("#dimenCategory").selectpicker('refresh');//
			}
		});
	}
}

function exportPresent(){
	var pks ="";
	//获取选中项
	var selected = $('#dimensionalityManageIdEx').bootstrapTable('getSelections');
	if(selected == null || selected == ""){
		
		
		new $.flavr({
			modal : false,
			content : "请先选择要导出的行数据！"
		});
		return;
	}else{
	for(var i=0 ; i<selected.length;i++){
		pks = pks+selected[i].pk+",";
		}
	pks = pks.substring(0,pks.length-1)
	}
	
	
	$.ajax({
		type : "POST",
		url:'./exportDimensionalityExtPresent.do', //请求的url
		dataType : "json",
		data : {
			ids : pks,
			companyName : $("#companyName").val(),
			dimenTypeName : $('#dimenType option:selected').text() != "--请选择--" ? $(
					'#dimenType option:selected').text()
					: "",
			dimenCategoryName : $('#dimenCategory option:selected')
					.text() != "--请选择--" ? $(
					'#dimenCategory option:selected').text() : "",
			periodTypeName : $('#periodType option:selected').text() != "--请选择--" ? $(
					'#periodType option:selected').text()
					: "",
			isVisableName : $('#isVisable option:selected').text() != "--请选择--" ? $(
					'#isVisable option:selected').text()
					: "",
			updateStartTime : $("#updateStartTime").val(),
			updateEndTime : $("#updateEndTime").val(),
			orderNumber : $("#orderNumber").val(),
			contactsTel : $("#contactsTel").val()
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});



	
}

