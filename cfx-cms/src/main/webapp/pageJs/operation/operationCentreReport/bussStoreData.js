$(function() {
	var cfg = {
			url : './bussStoreDataList.do'+urlParams(1),
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
			exportDataType: "basic", //basic', 'all', 'selected'.
			showFooter:true,
			striped: true,
			pageList: [5, 10, 25, 50,100,1000]

		};
	createDataGrid('bussStoreData', cfg);
});


var toolbar = [];
var columns =  [
				{
					checkbox : true,
					valign: "middle",
				   	halign: "center",
				   	align: "center",
				   	width:'20px',
				    formatter:stateFormatter,
				   
					},
				{
					title : '日期',
					field : 'date',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	
					
				}, 
				{
					title : '店铺名称',
					field : 'storeName',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                    footerFormatter:function (data) {
				        return "汇总";
				    }
				},
				{
					title : '订单数',
					field : 'number',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
					formatter : function(value, row, index) {
						return   formatNumber(parseFloat(value), 0)
		
					},
					footerFormatter:function (value) {
						var count = 0; 
						for (var i in value) {
                            if(parseFloat(value[i].number).toString() !="NaN"){
                                count += parseFloat(value[i].number);
                            }
						} 
						return   formatNumber(count.toFixed(0), 0) ;
	                }
				},
				{
					title : '成交量/吨',
					field : 'weight',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
					formatter : function(value, row, index) {
						return   formatNumber(parseFloat(value), 4)
		
					},
					footerFormatter:function (value) {
						var count = 0; 
						for (var i in value) {
                            if(parseFloat(value[i].weight).toString() !="NaN"){
                                count +=parseFloat( value[i].weight);
                            }
						} 
						return  formatNumber(count.toFixed(4), 4) ;
	                }
				},
				{
					title : '交易额（元）',
					field : 'amount',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
					formatter : function(value, row, index) {
						return   formatNumber(parseFloat(value), 2)
		
					},
					footerFormatter:function (value) {
						var count = 0; 
						for (var i in value) {
                            if(parseFloat(value[i].amount).toString() !="NaN"){
                                count +=parseFloat( value[i].amount);
                            }
						} 
						return  formatNumber(count, 2) ;
	                }
				}, 
				{
					title : '累计订单数',
					field : 'accumNumber',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
					formatter : function(value, row, index) {
						return   formatNumber(parseFloat(value), 0)
					},
				}, 
				{
					title : '累计成交量/吨',
					field : 'accumWeight',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
					formatter : function(value, row, index) {
						return   formatNumber(parseFloat(value), 4)
		
					},
				},
				{
					title : '累计交易额（元）',
					field : 'accumAmount',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
					formatter : function(value, row, index) {
						return   formatNumber(parseFloat(value), 2)
		
					},
				}];



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
			url : './bussStoreDataList.do'+urlParams(1),
			columns : columns,
			uniqueId : '',
			sortName : 'date',
			sortOrder : 'desc',
			toolbar : toolbar,
			showFooter:true,
			pageSize: 10, 
			exportDataType: "basic", //basic', 'all', 'selected'.
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
	createDataGrid('bussStoreData', cfg);
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
 * 导出选中数据，不选中则导出全部
 */
function exportReport(){
	
	//获取选中项
	 var row = $.map($('#bussStoreData').bootstrapTable('getSelections'),function(row){
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
		url : './exportBussStoreData.do',
		dataType : "json",
		data : {
			ids:ids,
			startTime:$('#startTime').val(),
			endTime:$('#endTime').val(),
			storeName :$('#storeName').val()
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});

}


