$(function() {
	createDataGrid('lotteryActivitySourceId', cfg);
	GetRequest('activityPk');
});

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
				{
					title : '操作',
					field : 'pk',
					width : 40,
					formatter : function(value, row, index) {
						var str = "";
							str += '<button type="button" onclick="javascript:del(\'' + value + '\'); " class="btn btn-danger active">删除</button>';
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); "  class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); "  class="btn btn-primary active">禁用</button>';
							}
							//str += '<button type="button" onclick="javascript:editLottery(\'' + value + '\'); " style="display:none;" showname="BTN_ADDAUCTIONSCREENINGS" class="btn btn-primary active">编辑</button>';
							
							str += '<button type="button" onclick="javascript:showAuctionRule(\'' + value + '\',\'' + row.sourcePk + '\'); " class="btn btn-primary active">查看活动规则</button>';
						return str;
					}
				}, 
				{
					title : '活动来源',
					field : 'sourceName',
					width : 20,
					sortable : true
				},
				{
					title : '添加时间',
					field : 'insertTime',
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
		
				}];
var cfg = {
	url : './lotteryActivitySourceRuleList.do?activityPk='+GetRequest('activityPk'),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};
function findGoodsbrand(s){
	if(s!=0){
		$("#isVisableStatus").val(s);
	}else{
		$("#isVisableStatus").val('');
	}
	var cfg = {
			url : './lotteryActivitySourceRuleList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('lotteryActivitySourceId', cfg);
}

function editState(pk, isde, isvi) {
	var parm =  {
			'pk' : pk,
			'isDelete' : isde,
			'auditStatus' : isvi
		};
 
	$.ajax({
		type : 'POST',
		url : './updateGoodsBrand.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			$('#lotteryActivitySourceId').bootstrapTable('refresh');

		}
	});

}
function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './editLotteryActivitySource.do',
				data : {
					 pk : pk,
					 isDelete  : 2
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#lotteryActivitySourceId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}

function isVisable(pk,isVisable) {

			$.ajax({
				type : 'POST',
				url : './editLotteryActivitySource.do',
				data : {
					 pk : pk,
					 isVisable : isVisable
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					});  
					if (data.success) {
						$("#quxiao").click();
						$('#lotteryActivitySourceId').bootstrapTable('refresh');
					}

				}
			});
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './lotteryActivitySourceRuleList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('lotteryActivitySourceId', cfg);
}


function insertLottery(){
	$('#editActivitySourceRuleId').modal();	
	 $("#sourcePk").val('');
}

function submit(){
	var sourcePk = $("#sourcePk").val();
	
	if(sourcePk == "" || sourcePk == undefined){
		new $.flavr({
			modal : false,
			content : "请选择活动来源！"
		});
		return;
	}
	var sourceName = $("#sourcePk option:selected").text();
	var	activityPk = $("input[name='activityPk']").val();
	$.ajax({
		type : "POST",
		url : "./editLotteryActivitySource.do",
		data : {
			sourceName:sourceName,
			sourcePk:sourcePk,
			activityPk:activityPk
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			}); /* Closing the dialog */
			if (data.success) {
				$("#qx").click();
				$('#lotteryActivitySourceId').bootstrapTable('refresh');
			}
		}
	});
}

/*******************************活动来源规则start*********************************************/

var item0 = {
		
		title : '操作',
		field : 'pk',
		width : 30,
		formatter : function(value, row, index) {
			var str = "";
				str += '<button id="editable-sample_new" class="btn btn-danger" data-toggle="modal" onclick="javascript:deleteActivity(\''+value+'\'); ">删除</button>&nbsp;&nbsp;';
			return str;
		}
	};
		var item1 = {
				title : '活动规则',
					field : 'ruleDescribe',
					width : 15,
					sortable : true
				};
		var item2 = {
					title : '添加时间',
		   			field : 'insertTime',
		   			width : 15,
		   			sortable : true
		   		};
		
		var item3 = {
				title : '启用/禁用',
	   			field : 'isVisable',
	   			width : 15,
	   			sortable : true,
	   			formatter : function(value, row, index) {
					if (value == 1) {
						return "启用";
					} else  if(value==2){
						return "禁用";
					}
	
				}
	   			
	   		};

	var columnNew = [].concat(columns);
	columnNew.splice(0,6);
	columnNew[0] = item0;
	columnNew[1] = item1;
	columnNew[2] = item2;
	columnNew[3] = item3;

function showAuctionRule(pk,sourcePk){
		
		$("#activitySourcePk").val(pk);
	
		$("#sourcePkId").val(sourcePk);
		$("#b2bLotteryRuleId").modal();
		var cfg = {
				url : './getLotteryRuleBySourcePk.do?activitySourcePk='+pk,
				columns : columnNew,
				uniqueId : 'pk',
				sortName : 'insertTime',
				sortOrder : 'desc',
				toolbar : toolbar,
				onLoadSuccess:btnList
			};
	createDataGrid('lotteryRuleTableId', cfg);	
	
}
	
function insertLotteryRule(){
	$("#editLotteryRuleId").modal();
	$.ajax({
		type : "POST",
		url : "./getLotterySourcePk.do",
		data : {
			sourcePk : $("#sourcePkId").val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success == true){
				var html = "";
				var oneArr = new Array(); //定义一数组 
				oneArr = data.templateName.split("{number}"); //字符分割 
				for (i=0;i < oneArr.length ;i++ ){
					var param = "param"+(i+1);
					html += oneArr[i];
					if((oneArr.length-1) == i){	
					}else{
						html += "&nbsp;<input type=\"text\" style=\"width:50px;\" name=\""+param+"\" id=\""+param+"\"  value=\"\" />&nbsp;";
					}
				} 
				$("#lotterySourceTemplate").html(html);
				$("#templatePk").val(data.templatePk);
			}else{
				new $.flavr({
					modal : false,
					content : data.msg
				});
			}
		}
	});
}

function saveLotteryRule(){
	
	var templatePk = $("#templatePk").val();
	var sourcePk = $("#sourcePkId").val();
	var activitySourcePk = $("#activitySourcePk").val();
		var params = ""; //定义一数组 
		var r = /^\+?[1-9][0-9]*$/;
	var isNumber = 0;
	 $("#lotterySourceTemplate input[type='text']").each(function(i){
		 
		 if(!r.test($(this).val())){
			 isNumber ++;
		 }
		 params += $(this).val()+";";
		  });
	 
	 if(isNumber > 0){
		 new $.flavr({
				modal : false,
				content : "输入存在不是数字的内容或非正整数！"
			});
			return;
	 }
	if(valid("#ruleForm")){
		$.ajax({
			type : "POST",
			url : "./editLotteryRule.do",
			data : {
				numberParams :params,
				templatePk:templatePk,
				activitySourcePk:activitySourcePk,
				sourcePk:sourcePk,
				isSingle:1
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#qxRule").click();
					$('#lotteryRuleTableId').bootstrapTable('refresh');
				}
			}
		});
	}
}


function deleteActivity(pk){
	$.ajax({
		type : "POST",
		url : './delLotteryRule.do',
		data : {
			pk:pk
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$('#lotteryRuleTableId').bootstrapTable('refresh');
			}
		}
	});
}

/*******************************活动来源规则end*********************************************/
