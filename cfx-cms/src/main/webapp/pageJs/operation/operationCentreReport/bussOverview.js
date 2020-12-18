$(function() {
	createDataGrid('bussOverview', cfg);
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
                   	width:'30px',
                   	colspan: 1,
                   	rowspan: 2,
                   	footerFormatter:function (data) {
                        return "汇总";
                    }
                   	
				}, 
				{
					title : '交易店铺数',
					field : '',
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
					title : '交易采购商数',
					field : '',
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
					title : '订单数',
					field : '',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 5,
                   	footerFormatter:function (data) {
	                    return "";
	                },
                   	rowspan: 1
				},
				{
					title : '成交量（吨）',
					field : '',
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
					title : '交易额（元）',
					field : '',
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
					title : '历史累计订单数',
					field : '',
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
					title : '历史累计成交量',
					field : '',
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
					title : '历史累计交易额',
					field : '',
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
					 	field: 'storeAllCount',
					    title: "总店铺数",
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
	                            if(parseFloat(data[i].storeAllCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].storeAllCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'storeBlankCount',
					    title: "白条店铺数",
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
	                            if(parseFloat(data[i].storeBlankCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].storeBlankCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'storeLoanCount',
					    title: "化纤贷店铺数",
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
	                            if(parseFloat(data[i].storeLoanCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].storeLoanCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'purchaserCount',
					    title: "总采购商数",
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
	                            if(parseFloat(data[i].purchaserCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].purchaserCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'purchBlankCount',
					    title: "白条采购商数",
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
	                            if(parseFloat(data[i].purchBlankCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].purchBlankCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'purchLoanCount',
					    title: "化纤贷采购商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	var count = 0; 
							for (var i in data) {
	                            if(parseFloat(data[i].purchLoanCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].purchLoanCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'orderCount',
					    title: "总订单数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	var count = 0; 
							for (var i in data) {
	                            if(parseFloat(data[i].orderCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].orderCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'orderBlankCount',
					    title: "白条订单数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	var count = 0; 
							for (var i in data) {
	                            if(parseFloat(data[i].orderBlankCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].orderBlankCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'orderLoanCount',
					    title: "化纤贷订单数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	var count = 0; 
							for (var i in data) {
	                            if(parseFloat(data[i].orderLoanCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].orderLoanCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'orderSelfCount',
					    title: "自采订单数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	var count = 0; 
							for (var i in data) {
	                            if(parseFloat(data[i].orderSelfCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].orderSelfCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'orderWapCount',
					    title: "wap订单数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	var count = 0; 
							for (var i in data) {
	                            if(parseFloat(data[i].orderWapCount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].orderWapCount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),0);
		                }
				 },
				 {
					 	field: 'transWeight',
					    title: "总成交量",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),4);
					    },
					    footerFormatter:function (data) {
					    	var count = 0.0; 
							for (var i in data) {
	                            if(parseFloat(data[i].transWeight).toString() !="NaN"){
	                                count = accAdd(count,(data[i].transWeight)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),4);
		                }
				 },
				 {
					 	field: 'transBlankWeight',
					    title: "白条成交量",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),4);
					    },
					    footerFormatter:function (data) {
					    	var count = 0.0; 
							for (var i in data) {
	                            if(parseFloat(data[i].transBlankWeight).toString() !="NaN"){
	                                count = accAdd(count,(data[i].transBlankWeight)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),4);
		                }
				 },
				 {
					 	field: 'transLoanWeight',
					    title: "化纤贷成交量",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),4);
					    },
					    footerFormatter:function (data) {
					    	var count = 0.0; 
							for (var i in data) {
	                            if(parseFloat(data[i].transLoanWeight).toString() !="NaN"){
	                                count = accAdd(count,(data[i].transLoanWeight)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),4);
		                }
					 
				 },
				 {
					 	field: 'transAmount',
					    title: "总交易额",
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
	                            if(parseFloat(data[i].transAmount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].transAmount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),2);
		                }
				 },
				 {
					 	field: 'transBlankAmount',
					    title: "白条交易额",
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
	                            if(parseFloat(data[i].transBlankAmount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].transBlankAmount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),2);
		                }
					 
				 },
				 {
					 	field: 'transLoanAmount',
					    title: "化纤贷交易额",
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
	                            if(parseFloat(data[i].transLoanAmount).toString() !="NaN"){
	                                count = accAdd(count,(data[i].transLoanAmount)*1);
	                            }

								} 
					    	return formatNumber(parseFloat(count),2);
		                }
					 
				 },
				 {
					 	field: 'addUpOrderCount',
					    title: "累计订单数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					    
					 
				 },
				 {
					 	field: 'addUpOrderBlankCount',
					    title: "累计白条订单数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					 
				 },
				 {
					 	field: 'addUpOrderLoanCount',
					    title: "累计化纤贷订单数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),0);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					 
				 },
				 {
					 	field: 'addUpTransWeight',
					    title: "累计成交量",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),4);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					 
				 },
				 {
					 	field: 'addUpTransBlankWeight',
					    title: "累计白条成交量",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),4);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					 
				 },
				 {
					 	field: 'addUpTransLoanWeight',
					    title: "累计化纤贷成交量",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),4);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					 
				 },
				 {
					 	field: 'addUpTransAmount',
					    title: "累计交易额",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),2);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					 
				 },
				 {
					 	field: 'addUpTransBlankAmount',
					    title: "累计白条交易额",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),2);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					 
				 },
				 {
					 	field: 'addUpTransLoanAmount',
					    title: "累计化纤贷交易额",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    formatter : function(value) {
					    	return formatNumber(parseFloat(value),2);
					    },
					    footerFormatter:function (data) {
					    	return "";
		                }
					 
				 }
				 ]];

var cfg = {
	url : './searchBussOverview.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'countTime',
	sortOrder : 'desc',
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
        url : './searchBussOverview.do'+urlParamsByHeader(1,"ewaijiangli"),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'countTime',
        sortOrder : 'desc',
        toolbar : toolbar,
        pageSize: 10,
        showFooter:true,
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

	createDataGrid('bussOverview', cfg);
}

/**
 * 数据导出，选中时导出选中数据，不选中则导出全部数据
 */
function exportPresent(){
	console.log(11);
	//获取选中项
    var row = $.map($('#bussOverview').bootstrapTable('getSelections'),function(row){
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
		url:'./exportBussOverview.do'+urlParamsByHeader(1,"ewaijiangli"), //请求的url
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



