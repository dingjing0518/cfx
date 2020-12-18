$(function() {
	createDataGrid('lotteryAwardTableId', cfg);
});



var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 80,
					formatter : function(value, row, index) {
						var str = "";
							str += '<button type="button" onclick="javascript:del(\'' + value + '\'); " style="display:none;" showname="OPER_ACTIVITY_LOTTERY_SETTINGS_BTN_DEL" class="btn btn-danger active">删除</button>';
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); " style="display:none;" showname="OPER_ACTIVITY_LOTTERY_SETTINGS_BTN_ENABLE" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); " style="display:none;" showname="OPER_ACTIVITY_LOTTERY_SETTINGS_BTN_DISABLE" class="btn btn-primary active">禁用</button>';
							}
							str += '<button type="button" onclick="javascript:editLotteryAward(\'' + value + '\'); " style="display:none;" showname="OPER_ACTIVITY_LOTTERY_SETTINGS_BTN_EDIT" class="btn btn-primary active">编辑</button>';
						return str;
					}
				}, 
				{
					title : '活动类型',
					field : 'activityType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "抽奖";
						} else  if(value==2){
							return "白条新客户见面礼";
						} else  if(value==3){
                            return "白条老客户尊享礼";
                        }else{
							return "";
						}
		
					}
				},
				{
					title : '活动名称',
					field : 'activityName',
					width : 20,
					sortable : true
				},
				{
					title : '奖项',
					field : 'name',
					width : 20,
					sortable : true
				},
				{
					title : '奖品类型',
					field : 'awardType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 2) {
							return "实物";
						} else  if(value==3){
							return "再接再厉,明日再来";
						}
		
					}
		
				}, 
				{
					title : '奖品种类',
					field : 'awardVariety',
					width : 30,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 2) {
							return "实物";
						} else  if(value==3){
							return "无";
						}
					}
				}, {
					title : '奖品名称',
					field : 'awardName',
					width : 30,
					sortable : true
				}, {
					title : '奖品数量',
					field : 'amount',
					width : 30,
					sortable : true
				}, {
					title : '获奖率(%)',
					field : 'awardPercent',
					width : 30,
					sortable : true
				}, {
					title : '中奖区间',
					field : 'rangeTime',
					width : 30,
					sortable : true
				},{
					title : '启用/禁用',
					field : 'isVisable',
					width : 30,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "是";
						} else  if(value==2){
							return "否";
						}
					}
				}
				];
var cfg = {
	url : './lotteryAwardSettingList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 删除奖品
 * @param pk
 */
function del(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './editLotteryAward.do',
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
                        bootstrapTableRefresh("lotteryAwardTableId");
						//$('#lotteryAwardTableId').bootstrapTable('refresh');
					}

				}
			});
		},
		onCancel : function() {
			$("#quxiao").click();
		}
	});
}

/**
 * 启用、禁用奖品
 * @param pk
 * @param isVisable
 */
function isVisable(pk,isVisable) {

			$.ajax({
				type : 'POST',
				url : './updateLotteryAwardStatus.do',
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
						$('#lotteryAwardTableId').bootstrapTable('refresh');
					}

				}
			});
}

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './lotteryAwardSettingList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('lotteryAwardTableId', cfg);
}

/**
 * 奖品新增页面
 */
function insertLotteryAward(){
	$('#editLotteryAwardId').modal();
    $('#modalLabel').html("奖项新增");
	$("#activityPk").val("");	
	$("#activityType").val("");	
	$("#name").val("");
	$("#awardType").val("");	
	$("#awardVariety").val("");
	$("#relevancyPk").val("");
	$("#amount").val("");
	$("#awardPercent").val("");
	$("#awardSort").val("");
	$("#showSort").val("");
    $("#endTime").val("");
    $("#startTime").val("");
    $("#packageNumber").val("");
    $("#pk").val("");
    $("#isLotteryActivity").show();
}

/**
 * 奖品编辑
 * @param pk
 */
function editLotteryAward(pk){
	
	$('#editLotteryAwardId').modal();
    $('#modalLabel').html("奖项编辑");
	$.ajax({
		type : "POST",
		url : './getLotteryAwardByPk.do',
		data : {
			pk : pk
		},
		dataType : "json",
		success : function(data) {
			if(data != null){
				$("#pk").val(pk);
				$("#name").val(data.name);
				$("#amount").val(data.amount);
				$("#awardPercent").val(data.awardPercent);
                $("#packageNumber").val(data.packageNumber);
				//$("#awardSort").val(data.showSort);
				$("#showSort").val(data.showSort);
                if(data.startTime != null){
                    $("#startTime").val(timeStamp2String(data.startTime));
				}
                if(data.endTime != null){
                    $("#endTime").val(timeStamp2String(data.endTime));
				}
				//活动类型
				var htmlActivityType ="<option value=\"\">--请选择--</option>";
				for(var i = 0;i < activity_type.length;i++){
					if(activity_type[i].key == data.activityType && activity_type[i].key==1){
						htmlActivityType +="<option value="+activity_type[i].key+" selected>"+activity_type[i].value+"</option>";
					}else{
						if(activity_type[i].key==1){
						htmlActivityType +="<option value="+activity_type[i].key+">"+activity_type[i].value+"</option>";
						}
					}
			}
				$("#activityType").html(htmlActivityType);

                if(data.activityType == 1){
                    $("#isLotteryActivity").show();
                }
                if(data.activityType == 2||data.activityType == 3){

                    $("#isLotteryActivity").hide();
                }

                //活动名称
				var html ="<option value=\"\">--请选择--</option>";
				$.ajax({
					type : "POST",
					url : './getAllLotteryList.do',
					data : {
						activityType:data.activityType
					},
					dataType : "json",
					success : function(data1) {
						for(var i = 0;i < data1.length;i++){
							 if(data1[i].pk == data.activityPk){
									html +="<option value="+data1[i].pk+" selected>"+data1[i].name+"</option>";
							 }else{
								 html +="<option value="+data1[i].pk+">"+data1[i].name+"</option>";
							 }
						}
						$("#activityPk").html(html);
					}
				});
				
				//奖品类型
				var htmlAwardType ="<option value=\"\">--请选择--</option>";
				
				for(var n = 0; n < award_type.length; n++){
					if(award_type[n].key == data.awardVariety){
						htmlAwardType +="<option value=\""+award_type[n].key+"\" selected>"+award_type[n].value+"</option>";	
					}else{
						htmlAwardType +="<option value="+award_type[n].key+">"+award_type[n].value+"</option>";	
					}
				}
				$("#awardType").html(htmlAwardType);
				//奖品种类
				var htmlVariety ="<option value=\"\">--请选择--</option>";
				var variety_type = [{"key":"2","value":"实物"},{"key":"3","value":"无"}];
				for(var n = 0; n < variety_type.length; n++){
					if(variety_type[n].key == data.awardVariety){
						htmlVariety +="<option value=\""+variety_type[n].key+"\" selected>"+variety_type[n].value+"</option>";	
					}else{
						htmlVariety +="<option value="+variety_type[n].key+">"+variety_type[n].value+"</option>";	
					}
				}
				$("#awardVariety").html(htmlVariety);

				//奖品名称
				var awardVariety = $("#awardVariety").val();
				var htmlRelevancy ="<option value=\"\">--请选择--</option>";
					$.ajax({
						type : "POST",
						url :"./getAllLotteryMaterailList.do",
						data : {
						},
						dataType : "json",
						success : function(data2) {
							if (data2 != null && data.awardVariety == 2){
                                for(var i = 0;i < data2.length;i++){
                                    if(data2[i].pk == data.relevancyPk){
                                        htmlRelevancy +="<option value="+data2[i].pk+" selected>"+data2[i].name+"</option>";
                                    }else{
                                        htmlRelevancy +="<option value="+data2[i].pk+">"+data2[i].name+"</option>";
                                    }
                                }
                                $("#relevancyPk").html(htmlRelevancy);
							}else{
                                htmlRelevancy += "<option value=\"null\" selected>无</option>";
                                $("#relevancyPk").html(htmlRelevancy);
							}
						}
					});
			}
			
		}
	});
}

/**
 * 奖品保存
 */
function submit(){
	
	var activityPk = $("#activityPk").val();	
	var name = $("#name").val();
	var awardType = $("#awardType").val();	
	var awardVariety = $("#awardVariety").val();
	var relevancyPk = $("#relevancyPk").val();
	var awardName = $("#relevancyPk option:selected").text();
	var activityType = $("#activityType").val();
	var amount = $("#amount").val();
	var awardPercent = $("#awardPercent").val();
	var awardSort = $("#showSort").val();
	var showSort = $("#showSort").val();
    var endTime = $("#endTime").val();
    var startTime = $("#startTime").val();
    var packageNumber = $("#packageNumber").val();

	var rex = /^[1-9]\d*$/;
	if(validNotHidden("#dataForm")){
		if(isNotZeroDNumber(packageNumber) == false || packageNumber.length > 9){
            new $.flavr({
                modal : false,
                content : "包装数量需大于零的整数并且小于十位数字"
            });
            return;
        }

	//抽奖活动才判断
	if(activityType == 1){
		if(!/^(\+)?\d{1,2}(\.\d{1,2})?$/.test(awardPercent) || awardPercent == 0){
			new $.flavr({
				modal : false,
				content : "中奖率需大于0小于100，小数请保留两位！"
			});
			return;
			}
		if(!isNotZeroDNumber(showSort)  || showSort.length > 2) {
			new $.flavr({
				modal : false,
				content : "显示排序数字需大于0且三位以内的整数！"
			});
			return;
			}
        if(startTime > endTime || startTime == endTime){
            new $.flavr({
                modal : false,
                content : "开始时间不能大于或等于结束时间！"
            });
            return;
        }
	}
        if(!isDNumber(amount) || amount.length > 7) {
            new $.flavr({
                modal : false,
                content : "数量必须是大于等于0且八位以内的整数！"
            });
            return;
        }
		$.ajax({
			type : "POST",
			url : './editLotteryAward.do',
			data : {
				pk : $("#pk").val(),
				activityPk :activityPk, 
				name :name, 
				awardType:awardType,
				awardVariety: awardVariety,
				relevancyPk: relevancyPk,
				awardName: awardName,
				amount: amount,
				awardPercent: awardPercent,
				awardSort: awardSort,
				showSort: showSort,
                endTimeStr:endTime,
                startTimeStr:startTime,
                packageNumber:packageNumber
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#lotteryAwardTableId').bootstrapTable('refresh');
				}
			}
		});
		}
}

/**
 * 活动类型改变时，级联修改活动名称
 */
function changeActivityType(){
	var activityType = $("#activityType").val();
	var html ="<option value=\"\">--请选择--</option>";
	$.ajax({
		type : "POST",
		url : './getAllLotteryList.do',
		data : {
			activityType:activityType
		},
		dataType : "json",
		success : function(data) {
			
			for(var i = 0;i < data.length;i++){
				 if(data[i].activityType == activityType){
						html +="<option value="+data[i].pk+" selected>"+data[i].name+"</option>";
						//获取选中的活动名称的上线时间
                     // $("#onlineTime").val(data[i].onlineTime);
				 }else{
					 html +="<option value="+data[i].pk+">"+data[i].name+"</option>";
				 }
			}
			$("#activityPk").html(html);
		}
	});
	if(activityType == 1){
		$("#isLotteryActivity").find("input").attr('required',true)
		$("#isLotteryActivity").show();
	}
	if(activityType == 2||activityType == 3){
		$("#isLotteryActivity").find("input").attr('required',false)
		$("#awardPercent").val("");
        $("#showSort").val("");
        $("#startTime").val("");
        $("#endTime").val("");
		$("#isLotteryActivity").hide();

	}
}

/**
 * 奖品类型改变时，级联奖品种类
 */
function changeAwardType(){
	
	/*********************奖品类型*************************/
	var awardType = $("#awardType").val();
	var html ="<option value=\"\">--请选择--</option>";
		if(awardType ==3){
            $("#awardVariety").empty().append("<option value=\""+award_variety[1].key+"\" selected>"+award_variety[1].value+"</option>");
		}else if(awardType == 2){
            $("#awardVariety").empty().append("<option value=\""+award_variety[0].key+"\" selected>"+award_variety[0].value+"</option>");
		}
	
	/***********************奖品种类******************************/

	var awardVariety = $("#awardVariety").val();
	var htmlVariety ="<option value=\"\">--请选择--</option>";
	var url = "";
	if(awardVariety == 2){
		url = "./getAllLotteryMaterailList.do";
	}
	if(url != ""){
		$.ajax({
			type : "POST",
			url :url,
			data : {
			},
			dataType : "json",
			success : function(data) {
				for(var i = 0;i < data.length;i++){
					htmlVariety +="<option value="+data[i].pk+">"+data[i].name+"</option>";
				}
				$("#relevancyPk").html(htmlVariety);
			}
		});
	}else{
		htmlVariety += "<option value=\"null\">无</option>";
		$("#relevancyPk").html(htmlVariety);	
	}
}

/**
 * 时间格式化
 * @param time
 * @returns {string}
 */
function timeStamp2String(time){
    var datetime = new Date(time);
    //datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    return year + "-" + month + "-" + date+" "+hour+":"+minute;
}