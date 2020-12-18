$(function() {
	createDataGrid('lotteryManageId', cfg);
});



var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 80,
					formatter : function(value, row, index) {
						var str = "";
							str += '<button type="button" onclick="javascript:del(\'' + value + '\'); " style="display:none;" showname="OPER_ACTIVITY_SETTINGS_BTN_DEL" class="btn btn-danger active">删除</button>';
							if(row.isVisable == 2){
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',1); " style="display:none;" showname="OPER_ACTIVITY_SETTINGS_BTN_ENABLE" class="btn btn-primary active">启用</button>';
							}else{
								str += '<button type="button" onclick="javascript:isVisable(\'' + value + '\',2); " style="display:none;" showname="OPER_ACTIVITY_SETTINGS_BTN_DISABLE" class="btn btn-primary active">禁用</button>';
							}

							str += '<button type="button" onclick="javascript:editLottery(\'' + value + '\'); " style="display:none;" showname="OPER_ACTIVITY_SETTINGS_BTN_EDIT" class="btn btn-primary active">编辑</button>';

							/*if(row.activityType == 1){
								str += '<button type="button" onclick="javascript:getLotterySource(\'' + value + '\'); " style="display:none;" showname="BTN_SHOWLOTTERYACTIVITYSOURCE" class="btn btn-primary active">查看活动来源</button>';
								str += '<button type="button" onclick="javascript:showAwardRule(\'' + value + '\'); " style="display:none;" showname="BTN_SHOWLOTTERYCOUPONUSERULE" class="btn btn-primary active">奖品使用规则</button>';
							}
							if(row.activityType == 2){
								str += '<button type="button" onclick="javascript:getLotteryInviteRule(\'' + value + '\'); " style="display:none;" showname="BTN_SHOWSLOTTERYACTIVITYRULE" class="btn btn-primary active">查看邀请活动规则</button>';
							}*/
						return str;
					}
				}, 
				{
					title : '活动名称',
					field : 'name',
					width : 20,
					sortable : true
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
						}
                        else  if(value==3){
                            return "白条老客户尊享礼";
                        }
		
					}
				},
				{
					title : '最高获奖次数',
					field : 'maximumNumber',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == null) {
							return "无限制";
						} else{
							return value;
						}
		
					}
				},
				{
					title : '开始时间',
					field : 'startTime',
					width : 20,
					sortable : true
				},
				{
					title : '结束时间',
					field : 'endTime',
					width : 20,
					sortable : true
				},
				{
					title : '是否过期',
					field : 'isEnd',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "否";
						} else  if(value == 2){
							return "是";
						}
		
					}
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
	url : './lotteryManageList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'asc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 删除活动
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
				url : './editLotteryActivity.do',
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
                        bootstrapTableRefresh("lotteryManageId");
						//$('#lotteryManageId').bootstrapTable('refresh');
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
 * 启用、禁用
 * @param pk
 * @param isVisable
 */
function isVisable(pk,isVisable) {

			$.ajax({
				type : 'POST',
				url : './editLotteryActivity.do',
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
						$('#lotteryManageId').bootstrapTable('refresh');
					}

				}
			});
}
function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './lotteryManageList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'asc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('lotteryManageId', cfg);
}

/**
 * 新增活动
 */
function insertLottery(){
	$('#editLotteryId').modal();
	$("#modalLabel").html("新增活动");
	 $("#name").val('');
	 $("#startTime").val('');
    $("#activityType").val('');
	 $("#endTime").val('');
    $("#pk").val('');
    $('#maximumNumber').val('');
    $('#maximumNumber').removeAttr("readonly");
    $("#bindRole").removeAttr("disabled");
    $("#bindRole").empty().append("<option value=\"\">--请选择--</option><option value=\"1\">公司</option><option value=\"2\">个人</option>");
}

/**
 * 编辑活动
 * @param pk
 */
function editLottery(pk){
    $('#editLotteryId').modal();
    $('#maximumNumber').removeAttr("readonly");
    $("#bindRole").removeAttr("disabled");
    $("#modalLabel").html("编辑活动");
	$.ajax({
		type : "POST",
		url : './getLotteryActivityByPk.do',
		data : {
			pk : pk
		},
		dataType : "json",
		success : function(data) {
			 $("#pk").val(pk);
			 $("#name").val(data.name);
			 
			 var html = "<option value=''>--请选择--</option>";
			 
			 if(data.activityType == 1){
				 html +="<option value='1' selected>抽奖</option>";
			 }else{
				 html +="<option value='1'>抽奖</option>"; 
			 }
			 if(data.activityType == 2){
                 $('#maximumNumber').attr("readonly","readonly");
                 $("#bindRole").attr("disabled","disabled");
                 $("#bindRole").empty();
				 html +="<option value='2' selected>白条新客户见面礼</option>";
			 }else{
				 html +="<option value='2'>白条新客户见面礼</option>";
			 }
            if(data.activityType == 3){
                $('#maximumNumber').attr("readonly","readonly");
                $("#maximumNumber").val("");
                $("#bindRole").empty();
                $("#bindRole").attr("disabled","disabled");
                html +="<option value='3' selected>白条老客户尊享礼</option>";
            }else{
                html +="<option value='3'>白条老客户尊享礼</option>";
            }
			 $("#activityType").html(html);
			 $("#maximumNumber").val(data.maximumNumber);
			 
			 var htmlRole = "<option value=''>--请选择--</option>";
			 if(data.bindRole == 1){
				 htmlRole +="<option value='1' selected>公司</option>";
			 }else{
				 htmlRole +="<option value='1'>公司</option>"; 
			 }
			 if(data.bindRole == 2){
				 htmlRole +="<option value='2' selected>个人</option>";
			 }else{
				 htmlRole +="<option value='2'>个人</option>";
			 }
			 $("#bindRole").html(htmlRole);
			 
			 $("#onlineTime").val(timeStamp2String(data.onlineTime));
			 $("#startTime").val(timeStamp2String(data.startTime));	
			 $("#endTime").val(timeStamp2String(data.endTime));
			 
		}
	});
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

/**
 * 活动类型改变时级联到获奖次数，和绑定角色
 */
function changeLottryType(){
    var activityType = $("#activityType").val();
    if(activityType != 1){
        $('#maximumNumber').attr("readonly","readonly");
        $("#bindRole").attr("disabled","disabled");
        $("#maximumNumber").val("");
        //$("#bindRole").val("");
        $("#bindRole").empty();
	}else{
        $('#maximumNumber').removeAttr("readonly");
        $("#bindRole").empty().append("<option value=\"\">--请选择--</option><option value=\"1\">公司</option><option value=\"2\">个人</option>");

        $("#bindRole").removeAttr("disabled");
	}
}

/**
 * 保存活动
 */
function submit(){
	var name = $("#name").val();	
	var activityType = $("#activityType").val();
	var maximumNumber = $("#maximumNumber").val();
	var bindRole = $("#bindRole").val();
	//var onlineTime = $("#onlineTime").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(valid("#dataForm")){
		if(startTime > endTime || startTime == endTime){
			new $.flavr({
				modal : false,
				content : "开始时间不能大于或等于结束时间！"
			});
			return;
		}
		
	if(maximumNumber != undefined && maximumNumber != ""){
		if(!isNotZeroDNumber(maximumNumber) && !/^\d{1,10}$/.test(maximumNumber)) {
			new $.flavr({
				modal : false,
				content : "输入的数字必须大于零的整数，并且长度不能超过10位！"
			});
			return;
			}
	}

    if(activityType == 1 && bindRole == "") {
                new $.flavr({
                    modal : false,
                    content : "请选择绑定角色"
                });
                return;
            }

		$.ajax({
			type : "POST",
			url : "./editLotteryActivity.do",
			data : {
				pk : $("#pk").val(),
				name :name,
				activityType:activityType,
				isDelete:1,
				maximumNumber:maximumNumber,
				bindRole:bindRole,
				startTimeStr:startTime,
				endTimeStr:endTime,
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#lotteryManageId').bootstrapTable('refresh');
				}
			}
		});
	}
}

