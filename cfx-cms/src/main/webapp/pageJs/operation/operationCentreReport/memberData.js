$(function() {
	createDataGrid('memberData', cfg);
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
					title : '采购商',
					field : '',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 7,
                   	rowspan: 1
				},
				{
					title : '供应商',
					field : '',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 7,
                   	rowspan: 1
				}, 
				{
					title : '化纤白条',
					field : '',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 5,
                   	rowspan: 1
				},
				{
					title : '化纤贷',
					field : '',
					valign: "middle",
                   	halign: "center",
                   	align: "center",
                   	width:'20px',
                   	colspan: 5,
                   	rowspan: 1
				}
				],
				[
				 {
					 	field: 'newPurNum',
					    title: "新注册采购商数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                                count += value[i].newPurNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
				 },
				 {
					 	field: 'pcLoginPurNum',
					    title: "PC端登录采购商数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
                                count +=value[i].pcLoginPurNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
				 },
				 {
					 	field: 'wapLoginPurNum',
					    title: "WAP端登录采购商数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                                count += value[i].wapLoginPurNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					   
					 
				 },
				 {
					 	field: 'passPurNum',
					    title: "审核通过采购商数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
                                count += value[i].passPurNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					 
				 },
				 {
					 	field: 'auditPurNum',
					    title: "累计待审核采购商数",
					    halign: "center",
					    width:'20px',
					    valign: "middle",
					    align: "center",
//					    footerFormatter:function (value) {
//					    	var count = 0; 
//							for (var i in value) {
//	                                count += value[i].auditPurNum;
//							} 
//							return  formatNumber(count.toFixed(0), 0) ;
//		                }
					   
					 
				 },
				 {
					 	field: 'accumPurNum',
					    title: "累计注册采购商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center"
				 },
				 {
					 	field: 'accumPassPurNum',
					    title: "累计审核通过采购商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
				 },
				 {
					 	field: 'newSupNum',
					    title: "新注册供应商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                                count += value[i].newSupNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
			
				 },
				 {
					 	field: 'passSupNum',
					    title: "审核通过供应商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                                count += value[i].passSupNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
				 },
				 {
					 	field: 'auditSupNum',
					    title: "累计待审核供应商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
//					    footerFormatter:function (value) {
//					    	var count = 0; 
//							for (var i in value) {
//	                                count += value[i].auditSupNum;
//							} 
//							return  formatNumber(count.toFixed(0), 0) ;
//		                }
				 },
				 {
					 	field: 'loginSupNum',
					    title: "PC端登录供应商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                                count += value[i].loginSupNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
				 },
				 {
					 	field: 'wapLoginSupNum',
					    title: "WAP端登录供应商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
                                count += value[i].wapLoginSupNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					    
					 
				 },
				 {
					 	field: 'accumSupNum',
					    title: "累计注册供应商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    
				 },
				 {
					 	field: 'accumPassSupNum',
					    title: "累计审核通过供应商数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
				 },
				 {
					 	field: 'btApplyNum',
					    title: "化纤白条申请数（客户数）",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
                                count +=value[i].btApplyNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					    
					 
				 },
				 {
					 	field: 'btCreditNum',
					    title: "已授信白条数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
                                count += value[i].btCreditNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					    
					 
				 },
				 {
					 	field: 'btAuditNum',
					    title: "累计待审核白条数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
//					    footerFormatter:function (value) {
//					    	var count = 0; 
//							for (var i in value) {
//	                                count += value[i].btAuditNum;
//							} 
//							return  formatNumber(count.toFixed(0), 0) ;
//		                }
				 },
				 {
					 	field: 'btAccumApplyNum',
					    title: "累计申请白条数（客户数）",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
				 },
				 {
					 	field: 'btAccumCreditNum',
					    title: "累计授信白条数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
				 },
				 {
					 	field: 'dApplyNum',
					    title: "化纤贷申请数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
                                count += value[i].dApplyNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
					 
				 },
				 {
					 	field: 'dCreditNum',
					    title: "已授信化纤贷数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    footerFormatter:function (value) {
					    	var count = 0; 
							for (var i in value) {
	                                count += value[i].dCreditNum;
							} 
							return  formatNumber(count.toFixed(0), 0) ;
		                }
				 },
				 {
					 	field: 'dAuditNum',
					    title: "累计待审核化纤贷数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
//					    footerFormatter:function (value) {
//					    	var count = 0; 
//							for (var i in value) {
//	                                count += value[i].dAuditNum;
//							} 
//							return  formatNumber(count.toFixed(0), 0) ;
//		                }
				 },
				 {
					 	field: 'dAccumApplyNum',
					    title: "累计申请化纤贷数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
					    
					    
				 },
				 {
					 	field: 'dAccumCreditNum',
					    title: "累计授信化纤贷数",
					    halign: "center",
					    valign: "middle",
					    width:'20px',
					    align: "center",
				 }
				 ]
               ];

var cfg = {
	url : './memberDataList.do',
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
        url : './memberDataList.do'+urlParams(1),
        columns : columns,
        uniqueId : 'pk',
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

	createDataGrid('memberData', cfg);
	
}

/**
 * 导出选中数据，如不选中则导出全部
 */
function exportPresent(){
	console.log(11);
	//获取选中项
	 var row = $.map($('#memberData').bootstrapTable('getSelections'),function(row){
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
		url : './exportMemberData.do',
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



