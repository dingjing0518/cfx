$(function() {
	createDataGrid('goodsBrandId', cfg);
});



var toolbar = [];
var columns = [
               
				{
					title : '操作',
					field : 'pk',
					width : 80,
					formatter : function(value, row, index) {
						var str = "";
							str += '<button type="button" onclick="javascript:del(\'' + value + '\'); " style="display:none;" showname="BTN_DELETEAUCTIONSCREENING" class="btn btn-danger active">删除</button>';
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); " style="display:none;" showname="BTN_ISVISABLESCREENINGS" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); " style="display:none;" showname="BTN_ISVISABLESCREENINGS" class="btn btn-primary active">禁用</button>';
							}
							str += '<button type="button" onclick="javascript:editAuctionScreenings(\'' + value + '\'); " style="display:none;" showname="BTN_ADDAUCTIONSCREENINGS" class="btn btn-primary active">编辑</button>';
							str += '<button type="button" onclick="javascript:showAuctionRule(\'' + value + '\'); " style="display:none;" showname="BTN_SHOWSCREENINGS" class="btn btn-primary active">查看活动规则</button>';
						return str;
					}
				}, 
				{
					title : '活动名称',
					field : 'auctionName',
					width : 20,
					sortable : true
				},
				{
					title : '排序',
					field : 'sort',
					width : 20,
					sortable : true
				},
				{
					title : '活动时间',
					field : 'auctionTime',
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
		
				}, 
				{
					title : '添加时间',
					field : 'insertTime',
					width : 30,
					sortable : true
				}, {
					title : '更新时间',
					field : 'updateTime',
					width : 30,
					sortable : true
				}];
var cfg = {
	url : './auctionScreeningsList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'sort',
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
			url : './auctionScreeningsList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('goodsBrandId', cfg);
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
			$('#goodsBrandId').bootstrapTable('refresh');

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
				url : './updateAuctionScreenings.do',
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
						$('#goodsBrandId').bootstrapTable('refresh');
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
				url : './updateAuctionScreenings.do',
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
						$('#goodsBrandId').bootstrapTable('refresh');
					}

				}
			});
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './auctionScreeningsList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('goodsBrandId', cfg);
}
function insertAuctionScreenings(){
	$('#editGoodsBrand').modal();	
	 $("#auctionName").val('');	
	 $("#startTime").val('');
	 $("#endTime").val('');	
	 $("#sort").val('');
}

function editAuctionScreenings(pk){
	$('#editGoodsBrand').modal();
	$.ajax({
		type : "POST",
		url : './getAuctionScreeningsByPk.do',
		data : {
			pk : pk
		},
		dataType : "json",
		success : function(data) {
			 $("#pk").val(pk);
			 $("#auctionName").val(data.auctionName);	
			 $("#startTime").val(data.startTime);
			 $("#endTime").val(data.endTime);	
			 $("#sort").val(data.sort);
		}
	});
}

function submit(){
	
	var auctionName = $("#auctionName").val();	
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();	
	var sort = $("#sort").val();
	
	if(!/^\+?[1-9]\d*$/.test(sort)) {
		new $.flavr({
			modal : false,
			content : "排序数字需大于0的整数！"
		});
		return;
		}
	
	if(valid("#dataForm")){
		if(startTime > endTime || startTime == endTime){
			new $.flavr({
				modal : false,
				content : "开始时间不能大于或等于结束时间！"
			});
			return;
		}
		$.ajax({
			type : "POST",
			url : './editAuctionScreenings.do',
			data : {
				pk : $("#pk").val(),
				auctionName :auctionName, 
				startTime :startTime, 
				endTime:endTime,
				sort: sort 
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#goodsBrandId').bootstrapTable('refresh');
				}
			}
		});
		}
}

/*******************************活动场次规则start*********************************************/

var item0 = {
		
		title : '操作',
		field : 'pk',
		width : 30,
		formatter : function(value, row, index) {
			var str = "";
				str += '<button id="editable-sample_new" style="display:none;" showname="BTN_SHOWSCREENINGS" class="btn btn-danger" data-toggle="modal" onclick="javascript:deleteActivity(\''+value+'\'); ">删除</button>&nbsp;&nbsp;'
			return str;
		}
	};
		var item1 = {
				title : '活动名称',
					field : 'auctionName',
					width : 15,
					sortable : true
				};
		var item2 = {
					title : '活动规则',
		   			field : 'detail',
		   			width : 15,
		   			sortable : true
		   		};
		
		var item3 = {
				title : '添加时间',
	   			field : 'insertTime',
	   			width : 15,
	   			sortable : true
	   		};

	var columnNew = [].concat(columns);
	columnNew.splice(0,6);
	columnNew[0] = item0;
	columnNew[1] = item1;
	columnNew[2] = item2;
	function showAuctionRule(pk){
		$("#subAuctionRuleId").modal();
		$("#auctionScreeningsPk").val(pk);
		var cfg = {
				url : './auctionActivityRuleList.do?auctionScreeningsPk='+pk,
				columns : columnNew,
				uniqueId : 'pk',
				sortName : 'insertTime',
				sortOrder : 'desc',
				toolbar : toolbar,
				onLoadSuccess:btnList
			};
	createDataGrid('autionScreeningsTableId', cfg);	
}
	
function insertActivityRule(){
	$("#editActivityRuleId").modal();
	 $("button[data-id=hours] span.filter-option").html("--请选择--"); 
	$("#hours").val('');
	$("#frequency").val('');
	$("#days").val('');
	
}

function saveActivity(){
	
	var hours = $("#hours").val();
	var frequency = $("#frequency").val();
	var days = $("#days").val();
	var concent = "活动结束后"+hours+"小时内，不提交订单"+frequency+"次，取消之后"+days+"天竞拍资格";
	if(hours == ""){
		new $.flavr({
			modal : false,
			content : "请选择时间！"
		});
		return;
	}
	
	if(!/^\+?[1-9]\d*$/.test(frequency) || frequency.length > 4) {
		new $.flavr({
			modal : false,
			content : "不提交订单次数需大于0的整数并小于五位数字！"
		});
		return;
	}
	if(!/^\+?[1-9]\d*$/.test(days) || days.length > 4) {
		new $.flavr({
			modal : false,
			content : "取消竞拍天数需大于0的整数并小于五位数字！"
		});
		return;
	}
	$.ajax({
		type : "POST",
		url : './insertAuctionActivityRule.do',
		data : {
			auctionScreeningsPk:$("#auctionScreeningsPk").val(),
			hours :hours, 
			frequency :frequency, 
			days:days,
			detail: concent 
		},
		dataType : "json",
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if (data.success) {
				$("#editActivityRuleId").modal('hide');
				$('#autionScreeningsTableId').bootstrapTable('refresh');
			}
		}
	});
}

function changeTime(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(startTime != '' && endTime != ''&& startTime != 'undefind' && endTime != 'undefind'){
		
		if(startTime > endTime || startTime == endTime){
			new $.flavr({
				modal : false,
				content : "开始时间不能大于或等于结束时间！"
			});
			return;
		}
	}
	
}


function deleteActivity(pk){
	$.ajax({
		type : "POST",
		url : './delAuctionActivityRule.do',
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
				$('#autionScreeningsTableId').bootstrapTable('refresh');
			}
		}
	});
}

/*******************************活动场次规则end*********************************************/
