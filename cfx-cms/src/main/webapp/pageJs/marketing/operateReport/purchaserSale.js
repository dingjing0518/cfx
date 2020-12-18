$(function() {
	createDataGrid('purchaserSale', cfg);
	GetRequest("bankPk");
});

/**
 * 获取页面跳转时传入的参数
 * 
 * @param key
 * @returns {*}
 * @constructor
 */
function GetRequest(key) {
	var url = location.search; // 获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest[key];
}

var toolbar = [];
var columns = [ [ {
	field : 'purchaserName',
	title : '采购商',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 2,
}, {
	field : 'accountName',
	title : '业务员',
	valign : "middle",
	halign : "center",
	align : "center",
	colspan : 1,
	rowspan : 2,

}, {
	field : 'nat_Org_Code',
	title : '1月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,
}, {
	field : 'org_Name',
	title : '2月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '3月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '4月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '5月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '6月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '7月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '8月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '9月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '10月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '11月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '12月',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

}, {
	field : 'org_Name',
	title : '累积',
	valign : "middle",
	halign : "center",
	align : "left",
	colspan : 3,
	rowspan : 1,

} ], [ {
	field : 'weight1',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount1',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts1',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight2',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount2',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts2',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight3',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount3',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts3',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight4',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount4',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts4',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight5',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount5',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts5',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight6',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount6',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts6',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight7',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount7',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts7',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight8',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount8',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts8',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight9',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount9',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts9',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight10',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount10',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts10',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight11',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount11',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts11',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weight12',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amount12',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'counts12',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'weightAll',
	title : "数量（T）",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'amountAll',
	title : "金额",
	halign : "center",
	valign : "middle",
	align : "right"

}, {
	field : 'countsAll',
	title : "条数",
	halign : "center",
	valign : "middle",
	align : "right"

}

] ];
var cfg = {
	url : './searchPurchaserSaleList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	pagination : true,
	fixedColumns : true,
	fixedNumber : 2,
	onLoadSuccess : btnList
};

function SearchClean() {
	var cfg = {
		url : './searchPurchaserSaleList.do' + urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		pagination : true,
		fixedColumns : true,
		fixedNumber : 2,
		onLoadSuccess : btnList
	};

	createDataGrid('purchaserSale', cfg);
}

/**
 * 采购商交易统计导出
 */
function downloadRF() {
	$.ajax({
		type : "POST",
		url : './exportPurchaserSaleList.do',
		dataType : "json",
		data : {
			year : $("#year").val(),
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});
}