$(function() {
	createDataGrid('econProductBussiness', cfg);
	GetRequest("bankPk");
});

/**
 * 获取跳转链接参数
 * @param key
 * @returns {*}
 * @constructor
 */
function GetRequest(key) {   
	   var url = location.search; //获取url中"?"符后的字串  
	   var theRequest = new Object();   
	   if (url.indexOf("?") != -1) {   
	      var str = url.substr(1);   
	      strs = str.split("&");   
	      for(var i = 0; i < strs.length; i ++) {   
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
	      }   
	   } 
	   return theRequest[key];   
	}

var toolbar = [];
var columns = [
               [{
            	   field: 'storeName',
                   title: '店铺名称',
                   valign: "middle",
                   halign: "center",
                   align: "center",
                   colspan: 1,
                   rowspan: 3,
                   
               },
               {
                   field: 'nat_Org_Code',
                   title: '1月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '2月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '3月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '4月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '5月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '6月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '7月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '8月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '9月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '10月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '11月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '12月',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               },
               {
                   field: 'org_Name',
                   title: '合计',
                   valign: "middle",
                   halign: "center",
                   align: "left",
                   colspan: 4,
                   rowspan: 1,
                   formatter: function (value, row, index) {
                       return '<a href="javascript:open(\'' + row.nat_Org_Code + '\',\'' + row.org_Name + '\')">' + value + '</a>';
                   }
               }
               ],
               [{
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               },
               {
                   field: 'tax',
                   title: "化纤贷",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2
               },
               {
                   field: 'taxzl',
                   title: "化纤白条",
                   halign: "center",
                   valign: "middle",
                   align: "right",
                   colspan: 2

               }
               ],
               [
					{
					    field: 'loanOrderCount1',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount1',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount1',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount1',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount2',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount2',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount2',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount2',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount3',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount3',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount3',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount3',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount4',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount4',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount4',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount4',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount5',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount5',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount5',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount5',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount6',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount6',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount6',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount6',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount7',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount7',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount7',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount7',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount8',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount8',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount8',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount8',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount9',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount9',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount9',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount9',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount10',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount10',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount10',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount10',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount11',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount11',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount11',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount11',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderCount12',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAmount12',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderCount12',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAmount12',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'loanOrderAllCount',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'loanOrderAllAmount',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					},
					{
					    field: 'blankOrderAllCount',
					    title: "订单数",
					    halign: "center",
					    valign: "middle",
					    align: "right"
					
					},
					{
					    field: 'blankOrderAllAmount',
					    title: "订单金额",
					    halign: "center",
					    valign: "middle",
					    align: "right",
                        formatter : function(value) {
                            return formatNumber(parseFloat(value),2);
                        }
					
					}
                
                
                ]];
var cfg = {
	url : './searchEconProdBussList.do?bankPk='+GetRequest("bankPk"),//+urlParams(1)
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	pagination:false,
	onLoadSuccess:btnList
};

/**
 * 查询和清除
 * @param type
 * @constructor
 */
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
    var years = $("#years").val();
    var bankPk = $("#selectBank").val();
	var cfg = {
			url : './searchEconProdBussList.do?years='+years+"&bankPk="+bankPk,
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			pagination:false,
			onLoadSuccess:btnList
		};

	createDataGrid('econProductBussiness', cfg);
}

/**
 * 导出供应商金融产品交易数据
 */
function downloadRF(){
	
	$.ajax({
		type : "POST",
		url : './exportEconProdBussRF.do',
		dataType : "json",
		data :{
			bankPk:$("#selectBank").val(),
			bankName:$('#selectBank option:selected').text(),
			year:$("#years").val()
			
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});

}