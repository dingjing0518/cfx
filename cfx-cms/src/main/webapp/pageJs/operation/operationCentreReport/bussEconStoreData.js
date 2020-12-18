$(function() {
	var countTimeStart = $("input[name='countTimeStart']").val();
	var countTimeEnd = $("input[name='countTimeEnd']").val();
	var cfg = {
			url : './searchBussEconSupplierData.do?countTimeStart='+countTimeStart+"&countTimeEnd="+countTimeEnd,
			columns : columns,
			uniqueId : 'id',
			sortName : 'countTime',
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
			pageList: [5, 10, 25, 50,100,1000]

		};
	createDataGrid('bussEconStoreData', cfg);
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
					field : 'countTime',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 1,
                   	rowspan: 2,
                   	
				}, 
				{
					title : '店铺名称',
					field : 'storeName',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 1,
                   	footerFormatter:function (data) {
	                    return "";
	                },
                   	rowspan: 2,
                   	footerFormatter:function (data) {
                        return "汇总";
                    }
				},
				{
					title : '化纤白条（支付方式为白条支付）',
					field : 'dimenName',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 3,
                   	footerFormatter:function (data) {
	                    return "";
	                },
                   	rowspan: 1
				}, 
				{
					title : '化纤贷（支付方式为化纤贷）',
					field : 'dimenTypeName',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 3,
                   	footerFormatter:function (data) {
	                    return "";
	                },
                   	rowspan: 1
				}
				],
				[
				 {
					 	field: 'countBlank',
					    title: "订单数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	var count = 0; 
							for (var i in data) {
	                            if(parseFloat(data[i].countBlank).toString() !="NaN"){
	                                count = accAdd(count,(data[i].countBlank)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
					 
				 },
				 {
					 	field: 'weightBlank',
					    title: "成交量/吨",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),4);
					    },
					    footerFormatter:function (data) {
					    	var count = 0.0; 
							for (var i in data) {
	                            if(parseFloat(data[i].weightBlank).toString() !="NaN"){
	                                count = accAdd(count,(data[i].weightBlank)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),4);
		                }
					 
				 },
				 {
					 	field: 'amountBlank',
					    title: "交易额（元）",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),2);
					    },
					    footerFormatter:function (data) {
					    	var count = 0.0; 
							for (var i in data) {
	                            if(parseFloat(data[i].amountBlank).toString() !="NaN"){
	                                count = accAdd(count,(data[i].amountBlank)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),2);
		                }
					 
				 },
				 {
					 	field: 'countLoan',
					    title: "订单数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	var count = 0; 
							for (var i in data) {
	                            if(parseFloat(data[i].countLoan).toString() !="NaN"){
	                                count = accAdd(count,(data[i].countLoan)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
					 
				 },
				 {
					 	field: 'weightLoan',
					    title: "成交量/吨",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),4);
					    },
					    footerFormatter:function (data) {
					    	var count = 0.0; 
							for (var i in data) {
	                            if(parseFloat(data[i].weightLoan).toString() !="NaN"){
	                                count = accAdd(count,(data[i].weightLoan)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),4);
		                }
					 
				 },
				 {
					 	field: 'amountLoan',
					    title: "交易额（元）",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),2);
					    },
					    footerFormatter:function (data) {
					    	var count = 0.0; 
							for (var i in data) {
	                            if(parseFloat(data[i].amountLoan).toString() !="NaN"){
	                                count = accAdd(count,(data[i].amountLoan)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),2);
		                }
					 
				 }
				 ]];


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
        url : './searchBussEconSupplierData.do'+urlParams(1),
        columns : columns,
        uniqueId : 'id',
        sortName : 'countTime',
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
        pageList: [5, 10, 25, 50,100,1000]

		};

	createDataGrid('bussEconStoreData', cfg);
}

/**
 * 导出选中数据，不选中则导出全部
 */
function exportPresent(){
	
	//获取选中项
    var rows = $.map($('#bussEconStoreData').bootstrapTable('getSelections'),function(row){
        return row ;
    });
    
    var ids="";
    if(rows != null && rows != ""){
	    for(var i=0;i<rows.length;i++){
	        if(i==0 || i=="0"){
	            ids+=rows[i].id;
	        }else{
	            ids+=","+rows[i].id;
	        }
	    }
    }
    

	$.ajax({
		type : "POST",
		url:'./exportBussEconStoreData.do'+urlParamsByHeader(1,"ewaijiangli"), //请求的url
		dataType : "json",
		data : {
			ids:ids
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});

}
