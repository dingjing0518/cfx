$(function() {
	var cfg = {
			url : './bussEconPurchaserDataList.do'+urlParams(1),
			columns : columns,
			uniqueId : '',
			sortName : 'date',
			sortOrder : 'desc',
			toolbar : toolbar,
			showFooter:true,
			onLoadSuccess: function(data){    
	    		btnList;
	    		if(data.total==0){
	    			$("div[class = 'fixed-table-footer'] table tbody tr").each(function(i){   
	    				 $(this).hide();
	    		    });
	    		}
	      },
			pageSize: 10, 
			pageList: [5, 10, 25, 50,100]

		};
	createDataGrid('bussEconPurchaserData', cfg);
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
                   	
				}, 
				{
					title : '采购商名称',
					field : 'purchaserName',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 1,
                   	footerFormatter:function (data) {
                        return "汇总";
                    },
                   	rowspan: 2
				},
				{
					title : '化纤白条（支付方式为白条支付）',
					field : 'dimenName',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 3,
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
                   	rowspan: 1
				}
				],
				[
				 {
					 	field: 'btNumber',
					    title: "订单数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].btNumber).toString() !="NaN"){
	                                count += parseFloat(value[i].btNumber);
	                            }
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
				 },
				 {
					 	field: 'btWeight',
					    title: "成交量/吨",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].btWeight).toString() !="NaN"){
	                                count += parseFloat(value[i].btWeight);
	                            }
							} 
							return  formatNumber(count.toFixed(4), 4) ;
		                }
				 },
				 {
					 	field: 'btAmount',
					    title: "交易额（元）",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].btAmount).toString() !="NaN"){
	                                count += parseFloat(value[i].btAmount);
	                            }
							} 
							return  formatNumber(count, 2) ;
       }
					 
				 },
				 {
					 	field: 'dNumber',
					    title: "订单数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].dNumber).toString() !="NaN"){
	                                count += parseFloat(value[i].dNumber);
	                            }
							} 
							return  formatNumber(count.toFixed(0),0) ;
		                }
					 
				 },
				 {
					 	field: 'dWeight',
					    title: "成交量/吨",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].dWeight).toString() !="NaN"){
	                                count += parseFloat(value[i].dWeight);
	                            }
							} 
							return  formatNumber(count.toFixed(4), 4) ;
		                }
					 
				 },
				 {
					 	field: 'dAmount',
					    title: "交易额（元）",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                            if(parseFloat(value[i].dAmount).toString() !="NaN"){
	                                count += parseFloat(value[i].dAmount);
	                            }
							} 
							return  formatNumber(count, 2) ;
		                }
				 }
				 ]];


/**
 * 设置是否选中复选框
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
			url : './bussEconPurchaserDataList.do'+urlParams(1),
			columns : columns,
			uniqueId : '',
			sortName : 'date',
			sortOrder : 'desc',
			toolbar : toolbar,
			showFooter:true,
			onLoadSuccess: function(data){    
	    		btnList;
	    		if(data.total==0){
	    			$("div[class = 'fixed-table-footer'] table tbody tr").each(function(i){   
	    				 $(this).hide();
	    		    });
	    		}
	      },
			pageSize: 10, 
			
			pageList: [5, 10, 25, 50,100]
		};
	createDataGrid('bussEconPurchaserData', cfg);
}

/**
 * 格式化时间
 * @param fmt
 * @returns {*}
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 复选框选中导出，不选中导出全部
 */
function exportReport(){
	
	//获取选中项
	 var row = $.map($('#bussEconPurchaserData').bootstrapTable('getSelections'),function(row){
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
		url : './exportBussEconPurchaserData.do',
		dataType : "json",
		data : {
			ids:ids,
			startTime:$('#startTime').val(),
			endTime:$('#endTime').val(),
			purchaserName :$('#purchaserName').val()
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});


}



