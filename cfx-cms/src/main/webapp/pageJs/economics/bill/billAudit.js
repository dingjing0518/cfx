
$(function() {
	$.ajaxSetup({
		async : false
	})

	createDataGrid('economicsCustId', cfg);
	

	//GetRequest("processInstanceId");

});



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
var columns = [ {
	title : '审批人',
	field : 'userId',
	width : 20,
	sortable : true
}, {
	title : '审批时间',
	field : 'time',
	width : 20,
	sortable : true
}, {
	title : '审批内容',
	field : 'fullMessage',
	width : 20,
	sortable : true
} ];
var cfg = {
	url : './searchEconCustAuditDetailList.do?processInstanceId='
			+ GetRequest("processInstanceId"),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess : btnList
};



/**
 * 审核通过或驳回
 * @param status
 */
function submitAudit(status) {
	var limits ="";
	var pk ="";
	
	var limitsArr =[];
	var productNamesArr = []
	 var str = "（" + "审批产品：" + $("#goodsName").html()  + "。";
	
	if (status != 2) {
	      $("input[name='limit']").each(function(){ 
			 limits =limits  + $(this).val()+ ",";
			 limitsArr.push( $(this).val());
	     })
	     
	     $("input[name='productName']").each(function(){ 
			 productNamesArr.push( $(this).val());
	     })
	     
	      for(var i = 0 ; i < productNamesArr.length;i++){
	    	  if (limitsArr.length==0||limitsArr[i]==undefined ||limitsArr[i] == "") {
					new $.flavr({
						modal : false,
						content : "建议额度不能为空！"
					});
					return;
				}
	    	  
	    	  str = str + "审批产品：" + productNamesArr[i] + "，建议额度："
				+ limitsArr[i] + "w；"
	      }
	     
	     
	}
      $("input[name='billGoodsPk']").each(function(){
		 pk =pk  + $(this).val()+ ",";
     })
     
	$.ajax({
		type : 'POST',
		url : './updateAuditBillCust.do',
		data : {
			pks:pk,
			limits:limits ,
			pk : $("#econCustmerPk").val(),
			remarks : $("#remarks").val()+str+"）"  ,
			taskId : $("#taskId").val(),
			state : status
		},
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			setTimeout(function() {
				if (data.success) {
					window.location.href = getRootPath() + "/approveBillTask.do";
				}
			}, 1000);

		}
	});
}


