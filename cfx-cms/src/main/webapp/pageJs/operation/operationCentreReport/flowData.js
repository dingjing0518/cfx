$(function() {
	createDataGrid('flowData', cfg);
});


var toolbar = [];
var columns = [
               [ {
				checkbox : true,
				valign: "middle",
               	halign: "center",
               	align: "center",
               	colspan: 1,
               	rowspan: 2,
               	width:'20px',
                formatter:stateFormatter,
                
				},{
					title : '日期',
					field : 'date',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 1,
                   	rowspan: 2,
                   	footerFormatter:function (data) {
                        return "汇总";
                    }
				}, 
				{
					title : '平台PV',
					field : '',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 2,
                   	rowspan: 1
				}, 
				{
					title : '访客数UV',
					field : '',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 2,
                   	rowspan: 1
				},
				{
					title : 'IP数',
					field : '',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 2,
                   
                   	rowspan: 1
				}
				],
				[
				 {
					 	field: 'pcWithPV',
					    title: "PC",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].pcWithPV).toString() !="NaN"){
	                                count += parseFloat(value[i].pcWithPV);
	                            }
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					 
				 },
				 {
					 	field: 'wapWithPV',
					    title: "WAP",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].wapWithPV).toString() !="NaN"){
	                                count += parseFloat(value[i].wapWithPV);
	                            }
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					 
				 },
				 {
					 	field: 'pcWithUV',
					    title: "PC",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].pcWithUV).toString() !="NaN"){
	                                count += parseFloat(value[i].pcWithUV);
	                            }
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					 
				 },
				 {
					 	field: 'wapWithUV',
					    title: "WAP",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].wapWithUV).toString() !="NaN"){
	                                count += parseFloat(value[i].wapWithUV);
	                            }
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					 
				 },
				 {
					 	field: 'pcWithIP',
					    title: "PC",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].pcWithIP).toString() !="NaN"){
	                                count += parseFloat(value[i].pcWithIP);
	                            }
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					 
				 },
				 {
					 	field: 'wapWithIP',
					    title: "WAP",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].wapWithIP).toString() !="NaN"){
	                                count += parseFloat(value[i].wapWithIP);
	                            }
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
				 }
				 ]];

var cfg = {
	url : './flowDataList.do',
	columns : columns,
	uniqueId : '',
	sortName : 'date',
	sortOrder : 'desc',
	toolbar : toolbar,
	showFooter:true,
	pageSize: 10, 
	onLoadSuccess: function(data){    
		btnList;
		if(data.total==0){
			$("div[class = 'fixed-table-footer'] table tbody tr").each(function(i){   
				 $(this).hide();
		    });
		}
  },
	pageList: [10, 25, 50,100,1000]

};


/**
 * 复选框操作
 * @param value
 * @param row
 * @param index
 * @returns {*}
 */
function stateFormatter(value, row, index) {
    if (row.rowstate == true)
        return {
            disabled : true,//设置是否可用
            checked : true//设置选中
        };
    return value;
}

/**
 * 数据汇总
 * @param arg1
 * @param arg2
 * @returns {number}
 */
function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)) 
	return (arg1*m+arg2*m)/m 
	}

function SearchClean(type){
	if(type == 2){
		cleanpar();
		
	}
	var cfg = {
        url : './flowDataList.do'+urlParams(1),
        columns : columns,
        uniqueId : '',
        sortName : 'date',
        sortOrder : 'desc',
        toolbar : toolbar,
        showFooter:true,
    	pageSize: 10, 
    	onLoadSuccess: function(data){    
    		btnList;
    		if(data.total==0){
    			$("div[class = 'fixed-table-footer'] table tbody tr").each(function(i){   
    				 $(this).hide();
    		    });
    		}
      },
        pageList: [10, 25, 50,100,1000]
	};

	createDataGrid('flowData', cfg);
}

function exportPresent(){
	
	//获取选中项
	 var row = $.map($('#flowData').bootstrapTable('getSelections'),function(row){
	        return row ;
	    });
	    var ids="";
	    if(row != null && row != ""){
		    for(var i=0;i<row.length;i++){
		        if(i==0 || i=="0"){
		            ids+=row[i].id;
		        }else{
		            ids+=","+row[i].id;
		        }
		    }
	    }
	    
	$.ajax({
		type : "POST",
		url : './exportFlowData.do',
		dataType : "json",
		data : {
			ids:ids,
			startTime:$('#startTime').val(),
			endTime:$('#endTime').val()
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});
}

