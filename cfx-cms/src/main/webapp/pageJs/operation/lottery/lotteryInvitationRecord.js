$(function() {
	createDataGrid('invitationRecordTableId', cfg);
});

var toolbar = [];
var columns = [
				{
					title : '操作',
					field : 'pk',
					width : 80,
					formatter : function(value, row, index) {
						var str = "";
							str += '<button type="button" onclick="javascript:conformInvitationStatus(\'' + value + '\',\'' + row.tcompanyPk + '\',\'' + row.tcompanyName + '\',\'' + row.invitationStatus + '\'); " style="display:none;" showname="BTN_DOINVITATIONSTATUS" class="btn btn-warning">状态确认</button>';
							str += '<button type="button" onclick="javascript:editInvitation(\'' + value + '\',\'' + row.mname + '\',\'' + row.mphone + '\',\'' + row.insertTime + '\',\'' + row.tname + '\',\'' + row.tphone + '\',\'' + row.applyStatus + '\',\'' + row.mcompanyPk + '\',\'' + row.tcompanyPk + '\',\'' + row.tcompanyName + '\'); " style="display:none;" showname="BTN_EDITINVITATIONRECORD" class="btn btn-primary active">编辑</button>';
							if(row.invitationStatus != 3){
								str += '<button type="button" onclick="javascript:putAward(\'' + value + '\',\'' + row.province + '\',\'' + row.provinceName + '\',\'' + row.city + '\',\'' + row.cityName + '\',\'' + row.area + '\',\'' + row.areaName + '\',\''+row.grantType+'\',\''+row.awardStatus+'\',\'' + row.mcompanyPk + '\'); " style="display:none;" showname="BTN_PUTINVITATIONCOUPON" class="btn btn-danger active">发放</button>';
							}
						return str;
					}
				},{
					title : '邀请人',
					field : 'mname',
					width : 30,
					sortable : true
				}, 
				 {
					title : '邀请人电话',
					field : 'mphone',
					width : 30,
					sortable : true
				}, {
					title : '邀请公司',
					field : 'mcompanyName',
					width : 30,
					sortable : true
				},
				{
					title : '邀请码',
					field : 'beInvitedCode',
					width : 20,
					sortable : true
				},
				{
					title : '受邀人姓名',
					field : 'tname',
					width : 20,
					sortable : true
				}, 
				{
					title : '受邀人电话',
					field : 'tphone',
					width : 20,
					sortable : true
				}, 
				{
					title : '受邀公司',
					field : 'tcompanyName',
					width : 20,
					sortable : true
				},
				{
					title : '受邀时间',
					field : 'insertTime',
					width : 20,
					sortable : true
				},
				{
					title : '是否已申请开通白条',
					field : 'applyStatus',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "是";
						} else  if(value==2){
							return "否";
						}
					}
		
				},
				{
					title : '邀请状态',
					field : 'invitationStatus',
					width : 20,
					sortable : true,
					formatter : function(value, row, index) {
						if (value == 1) {
							return "待确认";
						} else  if(value==2){
							return "邀请成功";
						}else  if(value==3){
							return "邀请失败";
						}
					}
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
						}else{
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
	url : './searchLotteryInvReList.do',
	columns : columns,
	uniqueId : 'pk',
	sortName : 'insertTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	onLoadSuccess:btnList
};
function findGoodsbrand(s){
	if(s!=0){
		$("#isInvitationStatus").val(s);
	}else{
		$("#isInvitationStatus").val('');
	}
	var cfg = {
			url : './searchLotteryInvReList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('invitationRecordTableId', cfg);
}

function SearchClean(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './searchLotteryInvReList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};

	createDataGrid('invitationRecordTableId', cfg);
}

function conformInvitationStatus(pk,tcompanyPk,tcompanyName,invitationStatus){
	$("#editIsInvitationId").modal();
	$("#invitationPk").val(pk);
	$("#conformNote").val("");
	$("#conformTcompanyPk").val(tcompanyPk);
	$("#conformTcompanyName").val(tcompanyName);
	$.ajax({
		type : 'GET',
		url : "./getInvitationRecordBypk.do",
		data : {
				pk:pk,
				tcompanyPk:tcompanyPk,
				tcompanyName:tcompanyName
				},
		dataType : 'json',
		async : true,
		success : function(data) {
			if(data){
				$("#tname").val(data.tname);
				$("#tphone").val(data.tphone);
				$("#tcompanyName").val(data.tcompanyName)
				$("#conformNote").val(regExpEnterShow(data.note));
				if(data.applyStatus == 1){
					$("#applyStatus").val("是");
				}
				if(data.applyStatus == 2){
					$("#applyStatus").val("否");
				}
			}
			if(invitationStatus == 2){
				$('#invitationFail').attr('disabled',true);
			}else{
				$('#invitationFail').attr('disabled',false);
			}
		}
	});
}

function submit(status){
	var invitationPk = $("#invitationPk").val();
	var pk = $("#invitationPk").val();
	var tcompanyPk = $("#conformTcompanyPk").val();
	var tcompanyName = $("#conformTcompanyName").val();
	var conformNote = $("#conformNote").val();
	$.ajax({
		type : 'GET',
		url : "./getInvitationRecordBypk.do",
		data : {
				pk:pk,
				tcompanyPk:tcompanyPk,
				tcompanyName:tcompanyName
				},
		dataType : 'json',
		async : true,
		success : function(data) {
			if(data.companyNameIsExist == false && status == 2){//邀请成功时判断公司是否存在
				new $.flavr({
					modal : false,
					content : "受邀人公司不存在，不能进行邀请成功状态确认，请在编辑中修改受邀人公司名称！"
				});
			}else{
				editInvitationRecord(invitationPk,status,conformNote);
			}
		}
	});
		
}

function editInvitationRecord(invitationPk,status,conformNote){

	$.ajax({
		type : "POST",
		url : './updateInvitationRecord.do',
		data : {
			pk : invitationPk,
			invitationStatus :status,
			note:regExpEnter(conformNote)
		},
		async : true,
		dataType : "json",
		success : function(data) {
			if (data.success) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				$('#editIsInvitationId').modal('hide');
				$('#invitationRecordTableId').bootstrapTable('refresh');
			}
		}
	});
}

//奖品发放
function putAward(pk,province,provinceName,city,cityName,area,areaName,grantType,awardStatus,mcompanyPk) {
	
	$('#editLotteryInvitationRecordId').modal();
	$("#pk").val(pk);
	$("#putMcompanyPk").val(mcompanyPk);
	
	var html = "<option value=\"\">--省--</option>";
	
	if(awardStatus == 1){
		$("#grantType").attr("disabled",true);
	}else{
		$("#grantType").attr("disabled",false);
	}
	//获取省市区默认选中
	$.ajax({
		type : 'GET',
		url : "./searchSysRegionsList.do",
		data : {parentPk:-1},
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data){
				for ( var i = 0; i < data.length; i++) {
					if(data[i].pk == province){
						html +="<option value='"+data[i].pk+"' selected>"+data[i].name+"</option>";
					}else{
						html +="<option value='"+data[i].pk+"'>"+data[i].name+"</option>";
					}
				}
			}
		}
	});
	$("#province").empty().append(html);
	$("#province").selectpicker('refresh');
	if(cityName !="undefined" && cityName !=""){
		$("#city").empty().append("<option value='"+city+"'>"+cityName+"</option>");
	}else{
		$("#city").empty().append("<option value=\"\">--市--</option>");
	}
	$("#city").selectpicker('refresh');
	if(areaName !="undefined" && areaName !=""){
		$("#area").empty().append("<option value='"+area+"'>"+areaName+"</option>");
	}else{
		$("#area").empty().append("<option value=\"\">--区--</option>");
	}
	$("#area").selectpicker('refresh');
	
	
	
	var htmlAwardStatus = "<option value=\"\">--请选择--</option>";
	for ( var i = 0; i < award_status.length; i++) {
			if(award_status[i].key == awardStatus){
				htmlAwardStatus +="<option value='"+award_status[i].key+"' selected >"+award_status[i].value+"</option>";
			}else{
				htmlAwardStatus +="<option value='"+award_status[i].key+"'>"+award_status[i].value+"</option>";
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
	
	
	
	//$("#grantType").val("");
	//$("#awardStatus").val("");
	$("#contacts").val("");
	$("#address").val("");
	$("#contactsTel").val("");
	$("#note").val("");
	
	var parm =  {
			'pk' : pk
		};
	$.ajax({
		type : 'POST',
		url : './getInvitationRecordBypk.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			if(data != null){
				$("#contacts").val(data.contacts);
				$("#address").val(regExpEnterShow(data.address));
				$("#contactsTel").val(data.contactsTel);
				$("#note").val(regExpEnterShow(data.note));
				//发放状态
				var htmlAwardStatus ="<option value=\"\">--请选择--</option>";
				for(var i = 0;i < award_status.length;i++){
					if(award_status[i].key == data.awardStatus){
						htmlAwardStatus +="<option value="+award_status[i].key+" selected>"+award_status[i].value+"</option>";
					}else{
						htmlAwardStatus +="<option value="+award_status[i].key+">"+award_status[i].value+"</option>";
					}
				}
				$("#awardStatus").html(htmlAwardStatus);
				
				//发放方式
				var htmlGrantType ="<option value=\"\">--请选择--</option>";
				for(var i = 0;i < grant_type.length;i++){
					if(award_status[i].key == data.grantType){
						htmlGrantType +="<option value="+grant_type[i].key+" selected>"+grant_type[i].value+"</option>";
					}else{
						htmlGrantType +="<option value="+grant_type[i].key+">"+grant_type[i].value+"</option>";
					}
				}
				$("#grantType").html(htmlGrantType);
				
			}
		}
	});
}

function submitPutAward(){
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
	var awardStatus = $("#awardStatus").val();
	var grantType = $("#grantType").val();
	if(address == null || address == "" || address == 'undefined' || address == undefined){
		new $.flavr({
			modal : false,
			content : "详细地址信息不能为空！"
		}); /* Closing the dialog */
		return;
	}
	var putMcompanyPk = $("#putMcompanyPk").val();
	if(valid("#dataPutForm")){
		$.ajax({
			type : "POST",
			url : './updateInvitationRecord.do',
			data : {
				pk : pk,
				contacts :contacts, 
				address :regExpEnter(address), 
				contactsTel:contactsTel,
				note :regExpEnter(note), 
				province :province, 
				provinceName:provinceName,
				city :city, 
				cityName :cityName, 
				area:area,
				areaName :areaName, 
				awardStatus:awardStatus,
				grantType: grantType,
				isPut:1,
				mcompanyPk:putMcompanyPk
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); /* Closing the dialog */
				if (data.success) {
					$("#quxiao").click();
					$('#invitationRecordTableId').bootstrapTable('refresh');
				}
			}
		});
		}
}

//编辑
function editInvitation(pk,mname,mphone,insertTime,tname,tphone,applyStatus,mcompanyPk,tcompanyPk,tcompanyName){
	$('#editIsInvitationInfoId').modal();
	$("#invitationRecordPk").val(pk);
	$("#mcompanyPk").val(mcompanyPk);
		if( mname != "undefined"){
			$("#editmname").val(mname);
		}
		if( mphone != "undefined"){
			$("#editmphone").val(mphone);
		}
		if( insertTime != "undefined"){
			$("#editinsertTime").val(insertTime);
		}
		if( tname != "undefined"){
			$("#edittname").val(tname);
		}
		if( tphone != "undefined"){
			$("#edittphone").val(tphone);
		}

		var htmlStatus = "<option value=''>--请选择--</option>";
		for(var i = 0;i < is_apply_status.length ;i++){
			if(is_apply_status[i].key == applyStatus){
				htmlStatus +="<option value='"+is_apply_status[i].key+"' selected>"+is_apply_status[i].value+"</option>";
			}else{
				htmlStatus +="<option value='"+is_apply_status[i].key+"'>"+is_apply_status[i].value+"</option>";
			}
			$("#editapplyStatus").html(htmlStatus);
		}
		var htmlCompany = "<option value=\"null\">--请选择--</option>";
		if(tcompanyPk == 'undefined'){
			htmlCompany +="<option value=\"\" selected>"+tcompanyName+"</option>";	
		}
		setTimeout(function(){//两秒后跳转  
			$.ajax({
				type : 'GET',
				url : "./getB2bCompayDto.do",
				data : {},
				dataType : 'json',
				async : false,
				success : function(data) {
				if(data.length>0){
					 for(var i =0;i<data.length;i++){
						 var companyName = "";
						 if(data[i].name == undefined || data[i].name == ""){
							 companyName = "";
						 }else{
							 companyName = data[i].name;
						 }
						 if(data[i].pk==tcompanyPk){
							 htmlCompany +="<option value="+data[i].pk+" selected>"+companyName+"</option>";
						 }else{
							 htmlCompany +="<option value="+data[i].pk+">"+companyName+"</option>";
						 }
					} 
					 $("#edittcompanyPk").empty().append(htmlCompany);
					 $("#edittcompanyPk").selectpicker('refresh');
					}
				}
			});
         },1000);
		
	}

function isOneCompany(){
	var mcompanyPk = $("#mcompanyPk").val();
	var edittcompanyPk = $("#edittcompanyPk").val();
	if(mcompanyPk == edittcompanyPk){
		new $.flavr({
			modal : false,
			content :"受邀公司不能和邀请公司相同！"
		}); 
		$("#submitEditId").attr('disabled',true); 
	}else{
		$("#submitEditId").attr('disabled',false);
	}
	
}

function submitEdit(){
		var pk = $("#invitationRecordPk").val();
		var tname = $("#edittname").val();
		var tphone = $("#edittphone").val();
		
		var tcompanyPk = $("#edittcompanyPk").val();
		
		if(tcompanyPk == "null"){
			new $.flavr({
				modal : false,
				content :"请选择好友公司！"
			});
			return;
		}
		var tcompanyName = $("#edittcompanyPk option:selected").text();
		var editapplyStatus = $("#editapplyStatus").val();
		if(valid("#dataEditForm")){
			$.ajax({
				type : "POST",
				url : './updateInvitationRecord.do',
				data : {
					pk : pk,
					tname :tname, 
					tphone :tphone, 
					tcompanyPk:tcompanyPk,
					tcompanyName :tcompanyName, 
					applyStatus :editapplyStatus 
				},
				dataType : "json",
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$("#quxiaoEdit").click();
						$('#invitationRecordTableId').bootstrapTable('refresh');
					}
				}
			});
			}
	
}


function changeCity(self){
	var pk = $(self).val();
	$("#city").empty().append("<option value=''>--市--</option>").append(getRegions(pk));
	$("#area").empty().append("<option value=''>--区--</option>");
	$("#city").selectpicker('refresh');
	$("#area").selectpicker('refresh');
}

function changeArea(self){
	$("#area").empty().append("<option value=''>--区--</option>").append(getRegions($(self).val()));
	$("#town").empty().append("<option value=''>--镇--</option>");
	$("#area").selectpicker('refresh');
	$("#town").selectpicker('refresh');
}


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

function exportInvitationRecord() {
	
	var confirm = new $.flavr({
		content : '确定导出邀请管理列表吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			var exportData = $("#loadingExportInvitationRecordData").html(" 正在导出...");
			exportData.prepend("<img src='style/images/exportorder.gif' height='20' width='20' />");
			
			window.location.href = './exportInvitationRecord.do'+urlParams(1);
			$("#loadingExportInvitationRecordData").html("导出");
		}
	});
}


function importInvitationRecord(){

	 var file = document.getElementById("updateFile").files;
	// FormData 对象
     var form = new FormData();// 可以增加表单数据
     var str = file[0].name;
     if(file[0] == undefined|| str.indexOf("xls")==-1 && str.indexOf("xlsx")==-1){
    	 new $.flavr({
				modal : false,
				content : "请选择要导入的excel文件！"
			});
    	 return;
     }
     form.append("filename", file[0]);// 文件对象
	$.ajax({
		type : "POST",
		url : './importInvitationRecord.do'+urlParams(1),
		data: form,
        //关闭序列化
        processData: false,
        contentType: false,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data
			});
		   $("#updateFile").val("");
		   $('#invitationRecordTableId').bootstrapTable('refresh');
		}
	});
}