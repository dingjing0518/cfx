$(function() {
	createDataGrid('lotteryAwardRosterTableId', cfg);
});

var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 80,
					formatter : function(value, row, index) {
						var str = "";
						if(row.status == 2 && row.awardType == 2 && row.awardStatus == 1){
							
							str += '<button type="button" onclick="javascript:putAward(\'' + value + '\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.town+'\',\''+row.townName+'\',\''+row.awardType+'\',\''+row.grantType+'\',\''+row.awardStatus+'\',\''+row.companyPk+'\',\'1\'); " style="display:none;" showname="OPER_ACTIVITY_LOTTERY_RECORD_BTN_PUT" class="btn btn-danger active">发放</button>';
						}
						
						if(row.status == 2 && row.awardStatus == 2 && row.awardType == 2){
							str += '<button type="button" onclick="javascript:putAward(\'' + value + '\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.town+'\',\''+row.townName+'\',\''+row.awardType+'\',\''+row.grantType+'\',\''+row.awardStatus+'\',\''+row.companyPk+'\',\'2\'); " style="display:none;" showname="OPER_ACTIVITY_LOTTERY_RECORD_BTN_CHECK" class="btn btn-danger active">查看</button>';
						}
						return str;
					}
				},{
					title : '获奖人',
					field : 'memberName',
					width : 30,
					sortable : true
				}, 
				 {
					title : '注册手机号',
					field : 'mobile',
					width : 30,
					sortable : true
				}, {
					title : '公司名称',
					field : 'companyName',
					width : 30,
					sortable : true
				},
				{
					title : '获奖时间',
					field : 'insertTime',
					width : 20,
					sortable : true
				},
				{
					title : '获奖状态',
					field : 'status',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "未中奖";
						} else  if(value==2){
							return "已中奖";
						}
					}
				}, 
				{
					title : '所获奖项',
					field : 'name',
					width : 20,
					sortable : true
				}, 
				{
					title : '所获奖品',
					field : 'awardName',
					width : 20,
					sortable : true
				},
				{
					title : '奖品类型',
					field : 'awardType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "优惠券";
						} else  if(value==2){
							return "实物";
						}else  if(value==3){
							return "再接再厉、明日再来";
						}
					}
				},
				{
					title : '奖品种类',
					field : 'awardVarietyName',
					width : 20,
					sortable : true
				},
				{
					title : '奖品发放状态',
					field : 'awardStatus',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "未发放";
						} else  if(value==2){
							return "已发放";
						}else  if(value==3){
							return "无";
						}
		
					}
		
				},
				{
					title : '奖品发放方式',
					field : 'grantType',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "业务员代送";
						} else  if(value==2){
							return "邮寄";
						}else{
							return "无";
						}
		
					}
				}];
var cfg = {
	url : './lotteryAwardRosterList.do?isHistory=1&colAuthAwardType=2',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};

/**
 * 中奖状态切换Table
 * @param s
 */
function findGoodsbrand(s){
	if(s!=0){
		$("#isStatus").val(s);
	}else{
		$("#isStatus").val('');
	}
	var cfg = {
			url : './lotteryAwardRosterList.do'+urlParams(1)+"&colAuthAwardType=2",
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('lotteryAwardRosterTableId', cfg);
}

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './lotteryAwardRosterList.do'+urlParams(1)+"&colAuthAwardType=2",
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('lotteryAwardRosterTableId', cfg);
}

//奖品发放
function putAward(pk,province,provinceName,city,cityName,area,areaName,town,townName,awardType,grantType,awardStatus,companyPk,type) {
    if (type ==1 ){
        $("#contacts").attr("disabled",false);
        $("#province").attr("disabled",false);
        $("#city").attr("disabled",false);
        $("#town").attr("disabled",false);
        $("#area").attr("disabled",false);
        $("#address").attr("disabled",false);
        $("#contactsTel").attr("disabled",false);
        $("#awardStatus").attr("disabled",false);
        $("#grantType").attr("disabled",false);
        $("#note").attr("disabled",false);
        $("#submit").show();
        $("#quxiao").show()
        if(awardStatus == 1){
            $("#grantType").attr("disabled",true);
        }else{
            $("#grantType").attr("disabled",false);
        }
    } else if (type ==2 ){
        $("#contacts").attr("disabled",true);
        $("#province").attr("disabled",true);
        $("#city").attr("disabled",true);
        $("#town").attr("disabled",true);
        $("#area").attr("disabled",true);
        $("#address").attr("disabled",true);
        $("#contactsTel").attr("disabled",true);
        $("#awardStatus").attr("disabled",true);
        $("#grantType").attr("disabled",true);
        $("#note").attr("disabled",true);
        $("#submit").hide();
        $("#quxiao").hide()
    }

    $('#editLotteryAwardRosterId').modal();
	$("#pk").val(pk);
	$("#companyPk").val(companyPk);

	$("#province").empty().append("<option value=''>--省--</option>").append(getRegions(-1));
	$("#province").val(province);
	$("#city").empty().append("<option value=''>--市--</option>").append(getRegions(province));
	$("#city").val(city);
	
	$("#area").empty().append("<option value=''>--区--</option>").append(getRegions(city));
	$("#area").val(area);
	
	var html = getRegions(area);
	$("#town").empty().append("<option value=''>--镇--</option>").append(html);	
	$("#town").val(town);
	if(html==""){
		$("#town").hide();
	}else{
		$("#town").show();
	}
	
	var htmlAwardType = "";
	for(var i = 0;i < award_type.length;i++){
		if(award_type[i].key == awardType){
			htmlAwardType +="<option value="+award_type[i].key+" selected>"+award_type[i].value+"</option>";
		}
	}
	$("#awardType").empty().append(htmlAwardType);
	$("#awardType").selectpicker('refresh');
	
	var htmlAwardStatus = "<option value=\"\">--请选择--</option>";
	for ( var i = 0; i < award_status.length; i++) {
			if(award_status[i].key == awardStatus){
				htmlAwardStatus +="<option value='"+award_status[i].key+"' selected >"+award_status[i].value+"</option>";
			}else{
				if (award_status[i].key!=3) {
                    htmlAwardStatus +="<option value='"+award_status[i].key+"'>"+award_status[i].value+"</option>";
                }

			}
	}
	
	$("#awardStatus").empty().append(htmlAwardStatus);
	$("#awardStatus").selectpicker('refresh');

    var htmlgrantType = "<option value=\"\">--请选择--</option>";
    for ( var i = 0; i < grant_type.length; i++) {
        if(grant_type[i].key == grantType){
            htmlgrantType +="<option value='"+grant_type[i].key+"' selected >"+grant_type[i].value+"</option>";
        }else{
            htmlgrantType +="<option value='"+grant_type[i].key+"'>"+grant_type[i].value+"</option>";
        }
    }
	$("#grantType").empty().append(htmlgrantType);
	$("#grantType").selectpicker('refresh');
	$("#contacts").val("");
	$("#address").val("");
	$("#contactsTel").val("");
	$("#note").val("");
	
	var parm =  {
			'pk' : pk
		};
	$.ajax({
		type : 'POST',
		url : './getlotteryRecordBypk.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			if(data != null){
				$("#contacts").val(data.contacts);
				$("#address").val(regEnter(data.address));
				$("#contactsTel").val(data.contactsTel);
				$("#note").val(regEnter(data.note));
			}
		}
	});
}

/**
 * 保存奖品发放
 */
function submit(){
	var pk = $("#pk").val();
	var contacts = $("#contacts").val();
	var address = $("#address").val();
	var contactsTel = $("#contactsTel").val();
	var note = $("#note").val();
	var province = $("#province").val();
	var provinceName = "";
	if(province != 'undefined' && province != ""){
		provinceName = $("#province option:selected").text();
	}
	var city = $("#city").val();
	var cityName = "";
	if(city != 'undefined' && city != ""){
		cityName = $("#city option:selected").text();
	}
	var area = $("#area").val();
	var areaName = "";
	if(area != 'undefined' && area != ""){
		areaName = $("#area option:selected").text();
	}
	
	var town = $("#town").val();
	var townName = "";
	if(town != 'undefined' && town != ""){
		townName = $("#town option:selected").text();
	}
	var awardStatus = $("#awardStatus").val();
	var grantType = $("#grantType").val();
	
	if(address == null || address == "" || address == 'undefined' || address == undefined){
		new $.flavr({
			modal : false,
			content : "详细地址信息不能为空！"
		}); /* Closing the dialog */
		return;
	}

	if (contactsTel!="") {
        if(!isPoneAvailable(contactsTel)){
            new $.flavr({
                modal : false,
                content : "手机格式不正确！"
            }); /* Closing the dialog */
            return;
        }
	}

	var companyPk = $("#companyPk").val();
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : './editLotteryRecord.do',
			data : {
				pk : pk,
				contacts :contacts, 
				address :regEnter(address), 
				contactsTel:contactsTel,
				note :regEnter(note), 
				province :province, 
				provinceName:provinceName,
				city :city, 
				cityName :cityName, 
				area:area,
				areaName :areaName, 
				town:town,
				townName :townName, 
				awardStatus:awardStatus,
				grantType: grantType,
				companyPk:companyPk
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#lotteryAwardRosterTableId').bootstrapTable('refresh');
				}
			}
		});
		}
}
/**************************地区改变时的级联操作START**********************************/

function changeCity(self){
	var pk = $(self).val();
	$("#city").empty().append("<option value=''>--市--</option>").append(getRegions(pk));
	$("#area").empty().append("<option value=''>--区--</option>");
	$("#town").empty().append("<option value=''>--镇--</option>")

}

function changeArea(self){
	$("#area").empty().append("<option value=''>--区--</option>").append(getRegions($(self).val()));
	$("#town").empty().append("<option value=''>--镇--</option>")
}

function changeTown(self){
	var html = getRegions($(self).val());
	if(html==""){
		$("#town").empty().append("<option value=''>--镇--</option>")
		$("#town").hide();
	}else{
		$("#town").show();
		$("#town").empty().append("<option value=''>--镇--</option>").append(html);
	}
}
/**************************地区改变时的级联操作END**********************************/

/**
 * 显示奖品发放方式
 */
function changeAwardStatus(){
	var html = "<option value=\"\">--请选择--</option>";
	if($("#awardStatus").val() == 1){
		html +="<option value='3' selected >无</option>";
		$("#grantType").attr("disabled",true);
	}else{
		$("#grantType").attr("disabled",false);
	}
	for ( var i = 0; i < grant_type.length; i++) {
            html +="<option value='"+grant_type[i].key+"'>"+grant_type[i].value+"</option>";
	}
	$("#grantType").empty().append(html);
	$("#grantType").selectpicker('refresh');
	
}

/**
 * 获取省份信息，显示到下拉列表
 * @param pk
 * @returns {string}
 */
function getRegions(pk){
	if(pk==""){
		pk="-2"
	}
	var html = "";
	$.ajax({
		type : 'GET',
		url : "./searchSysRegionsList.do",
		data : {parentPk:pk},
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data){
				for ( var i = 0; i < data.length; i++) {
					html +="<option value='"+data[i].pk+"'>"+data[i].name+"</option>";
				}
			}
		}
	});
	return html;
}

/**
 * 导出奖品数据
 */
function exportAwardHistory() {
	
	// var confirm = new $.flavr({
	// 	content : '确定导出抽奖记录吗?',
	// 	dialog : 'confirm',
	// 	onConfirm : function() {
	// 		confirm.close();
	// 		var exportData = $("#loadingExportAwardData").html(" 正在导出...");
	// 		exportData.prepend("<img src='style/images/exportorder.gif' height='20' width='20' />");
	// 		window.location.href = './exportAwardRoster.do'+urlParams(1)+"&colAuthAwardType=2";
	// 		$("#loadingExportAwardData").html("导出");
	// 	}
	// });

    $.ajax({
        type : 'POST',
        url : './exportAwardRoster.do'+urlParams(1)+"&colAuthAwardType=2",
        data : {},
        dataType : 'json',
        success : function(data) {
            if (data.success){
                new $.flavr({
                    modal : false,
                    content :data.msg
                });
            }
        }
    });
}
