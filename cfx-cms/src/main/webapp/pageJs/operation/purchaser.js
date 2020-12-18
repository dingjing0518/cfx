function changeCity(self){
	var pk = $(self).val();
	$("#city").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#area").empty().append("<option value=''>区</option>");
	
}

function changeArea(self){
	$("#area").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
	$("#town").empty().append("<option value=''>镇</option>");
}

function changeCitySearch(self){
	var pk = $(self).val();
	$("#cityName").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#areaName").empty().append("<option value=''>区</option>");
}

function changeAreaSearch(self){
	$("#areaName").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
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

$(function() {
	createDataGrid('purchaserId', cfg);
	
	$("input[type='radio']").change(function(event){
		$("#ocUrl").toggle();
		$("#trUrl").toggle();
		$("div[name='ocUrl']").toggle();
		$("div[name='trUrl']").toggle();
		$(event).prop("checked","checked");
	});	
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
       		{
       			title : '操作',
       			field : 'pk',
       			width : 200,
       			formatter : function(value, row, index) {
       				var str = "";
       				var insertTime=row.insertTime;
    				var auditTime=row.auditTime;
    				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="COMMON_CM_PURCHASER_BTN_CREDIT" class="btn btn-warning" onclick="javascript:auditCompany(\'' + value + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
       				row.contactsTel + '\',\''+insertTime+'\',\''+auditTime+'\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.remarks+'\',\''+row.refuseReason+'\',\''+row.auditStatus+'\',1); ">  审核</button> </a>';
    				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_BTN_COM"  class="btn btn btn-primary" data-toggle="modal" data-target="#childCompany" onclick="javascript:companyManage(\''+value+'\',\'' + row.auditStatus + '\'); ">公司管理 </button> </a>';
    				if (row.auditStatus == 1) {
       				}else if(row.auditStatus == 2){
       					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_BTN_SADMIN" class="btn btn btn-primary" data-toggle="modal" data-target="#setAdmin" onclick="javascript:setAdmin(\''+value+'\'); ">设置超级管理员 </button> </a>';
       				}else if(row.auditStatus == 3){
       				}
       				str+=' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editCompayId" onclick="javascript:editCompay(\'' + value + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
       				row.contactsTel + '\',\''+insertTime+'\',\''+auditTime+'\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.remarks+'\',\''+row.auditStatus+'\');">编辑</button></a>';
       					if (row.isVisable == 1) {
       						str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_BTN_DISABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',2,\''+row.remarks+'\'); ">禁用</button></a>';
       					} else {       						
       						str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_BTN_ENABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',1,\''+row.remarks+'\'); ">启用</button></a>';
       					}
       					str +='<a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_BTN_REMARK" class="btn btn btn-default" onclick="javascript:remarks(\'' + value + '\',\''+row.remarks+'\',1); ">备注</button></a>';
       				return str;
       			}
       		},
		{
			title : '公司名称',
			field : 'name',
			width : 20,
			sortable : true
		},{
			title : '所在地区',
			field : 'address',
			width : 20,
			sortable : true
		},
		{
			title : '注册电话',
			field : 'mobile',
			width : 20,
			sortable : true
		},{
			title : '统一社会信用代码',
			field : 'organizationCode',
			width : 20,
			sortable : true
		},
		{
			title : '营业执照五证',
			field : 'organizationCode',
			width : 50,
			sortable : true,
			formatter : function(value, row, index) {
				var str="";
				str+="<a rel='group"+index+"' title='营业执照' href='"+row.blUrl+"'><img class='bigpicture' src='"+row.blUrl+"' onerror='errorImg(this)'></a>";
				if(row.ocUrl!=null&&row.ocUrl!=''){
					str+="<a rel='group"+index+"' title='组织机构代码' href='"+row.ocUrl+"'><img class='bigpicture' src='"+row.ocUrl+"' onerror='errorImg(this)'></a>";
				}
				if(row.trUrl!=null&&row.trUrl!=''){
					str+="<a rel='group"+index+"' title='税务登记证' href='"+row.trUrl+"'><img class='bigpicture' src='"+row.trUrl+"' onerror='errorImg(this)'></a>";
				}
				str+=//"<a rel='group"+index+"' title='开户许可证' href='"+row.opUrl+"'><img class='bigpicture' src='"+row.opUrl+"' onerror='errorImg(this)'></a>"+
					 "<a rel='group"+index+"' title='企业认证授权书' href='"+row.ecUrl+"'><img class='bigpicture' src='"+row.ecUrl+"' onerror='errorImg(this)'></a>";
				return str;
			}
		},
	 	{
			title : '审核状态',
			field : 'auditStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "待审核";
				} else  if(value==2){
					return "审核通过";
				}else if(value==3){
					return "审核不通过";
				}

			}

		},{
			title : '备注',
			field : 'remarks',
			width : 20,
			sortable : true
		},{
			title : '审核时间',
			field : 'auditTime',
			width : 20,
			sortable : true
		},
		{
			title : '注册时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},
		{
			title : '资料维护时间',
			field : 'infoUpdateTime',
			width : 20,
			sortable : true
		},
		{
			title : '最后修改时间',
			field : 'updateTime',
			width : 20,
			sortable : true
		}
		,{
			title : '联系人',
			field : 'contacts',
			width : 20,
			sortable : true
		},
		{
			title : '联系电话',
			field : 'contactsTel',
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
		}
		];
var cfg = {
	url : './searchCompanyList.do?companyType=1&parentPk=-1&modelType='+GetRequest("modelType"),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'infoUpdateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	fixedColumns : true,
	fixedNumber : 1,
	onLoadSuccess:loadFancyBox
};


/**
 * 启用、禁用
 * @param pk
 * @param isvi
 * @param remarks
 */
function editState(pk,isvi,remarks) {
	    var remark = "";
	    console.info(remarks);
	if(remarks != "undefined"){
			remark = remarks;
	    }
	var parm = {
			'pk' : pk,
			'isVisable' : isvi,
			'remarks':remark
		};
	$.ajax({
		type : 'POST',
		url : './updateB2bCompany.do',
		data : parm,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if($("#childCompany").is(":hidden")){
				$('#purchaserId').bootstrapTable('refresh');
			}else{
				$('#childs').bootstrapTable('refresh');
			}
		}
	});

}

/**
 * 公司审核
 * @param pk
 * @param name
 * @param contacts
 * @param contactsTel
 * @param insertTime
 * @param auditTime
 * @param blUrl
 * @param organizationCode
 * @param ocUrl
 * @param trUrl
 * @param opUrl
 * @param ecUrl
 * @param province
 * @param provinceName
 * @param city
 * @param cityName
 * @param area
 * @param areaName
 * @param remarks
 * @param refuseReason
 * @param auditStatus
 * @param flage
 */
function auditCompany(pk,name,contacts,contactsTel,insertTime,auditTime,blUrl,organizationCode,ocUrl,trUrl,opUrl,ecUrl,province,provinceName,city,cityName,area,areaName,remarks,refuseReason,auditStatus,flage){
	
	$("#myModalLabelid").html("采购商审核信息");
	$('#auditCompayId').modal();
    if(flage == 1){
        $("#subCompanyAuthID").attr("style","display:none;");
        $("#companyAuthID").attr("style","display:block;");
    }else{
        $("#subCompanyAuthID").attr("style","display:block;");
        $("#companyAuthID").attr("style","display:none;");
    }
	if (null != pk && '' != pk) {
		$('#auditPk').val(pk);
		$('#auditCompayName').val(name);
		$('#auditContacts').val(contacts=="undefined"?"":contacts);
		$('#auditContactsTel').val(contactsTel=="undefined"?"":contactsTel);
		$("#auditProvince").val(provinceName=="undefined"?"":provinceName);
		$("#auditCity").val(cityName=="undefined"?"":cityName);
		$("#auditArea").val(areaName=="undefined"?"":areaName);
		
		$("#remarks").val(remarks=="undefined"?"":regExpEnterShow(remarks));
		
		if(organizationCode!= "undefined"){
			$('#auditOrganizationCode').val(organizationCode);
		}else{
			$('#auditOrganizationCode').val("");	
		}
		$("#auditBlUrl").attr("src",(blUrl=="undefined"||blUrl=="")?"./style/images/bgbg.png":blUrl);
		$("#auditEcUrl").attr("src",(ecUrl=="undefined"||ecUrl=="")?"./style/images/bgbg.png":ecUrl);
		//$("#auditBlHref").attr("href",blUrl=="undefined"?"":blUrl);
		if(auditStatus == 3){
			$("#auditNoPassR").val(refuseReason=="undefined"?"":regExpEnterShow(refuseReason));
		}else{
			$("#auditNoPassR").val("");
		}
		
	}
}

/**
 * 审核提交前判断
 * @param auditStatus
 */
function auditSubmit(auditStatus){
	var pk = $('#auditPk').val();
	var auditOrganizationCode = $('#auditOrganizationCode').val();
	var remarks=$('#remarks').val();
	var params = {
				'pk' : pk,
				'auditStatus' : auditStatus,
				'name' :$('#auditCompayName').val(),
				"organizationCode":auditOrganizationCode,
				"remarks":regExpEnter(remarks),
				"isAudit":"1"
			};
		if(auditStatus==2){ //审核通过
			sendPost(params);
		}else{//审核不通过
			$("#auditPk").val(pk);
			var context = $("#auditNoPassR").val();
			if(context.replace(/(^\s*)|(\s*$)/g, "") == "" || context == null){
				new $.flavr({
					modal : false,
					content : "请填写不通过原因！"
				});
			}else{
				refuse(pk,auditStatus,regExpEnter(context),auditOrganizationCode,remarks);
			}
			//$("#noAuditCompany").modal();
		}
	
}

/**
 * 判断通过后提交审核不通过状态
 * @param pk
 * @param auditStatus
 * @param context
 * @param auditOrganizationCode
 * @param remarks
 */
function refuse(pk,auditStatus,context,auditOrganizationCode,remarks){
		var params  = {
				pk:pk,
				auditStatus:auditStatus,
				refuseReason:context,
				organizationCode:auditOrganizationCode,
				remarks:remarks,
				isAudit:"1"
		};
		$.ajax({
			type : 'POST',
			url : './updateB2bCompany.do',
			data : params,
			dataType : 'json',
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				$("#qx").click();
				$(function () { $('#auditCompayId').modal('hide');});
				if($("#childCompany").is(":hidden")){
					$('#purchaserId').bootstrapTable('refresh');
				}else{
					$('#childs').bootstrapTable('refresh');
				}
				
			}
		});
}

/**
 * 判断通过后提交审核通过状态
 * @param params
 */
function sendPost(params){
	$.ajax({
		type : 'POST',
		url : './updateB2bCompany.do',
		data : params,
		dataType : 'json',
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$(function () { $('#auditCompayId').modal('hide');});
			if($("#childCompany").is(":hidden")){
				$('#purchaserId').bootstrapTable('refresh');
			}else{
				$('#childs').bootstrapTable('refresh');
			}
		}
	});
}

/**
 * 采购商信息编辑 提交
 */
function submit(){
	
	var params = {};
	params['flag'] = 1;//编辑标识
	$("#dataForm").find("input,select").each(function(){
		if($(this).attr("name")!=undefined){
			params[$(this).attr("name")] = $(this).val();
		}
	});
	params['isAudit'] = 2;
	params['provinceName'] = $("#province option:selected").text();
	params['cityName'] = $("#city option:selected").text();
	params['areaName'] = $("#area option:selected").text();
	$("#dataForm img").each(function(){
		if($(this).attr("id")!=undefined){
			params[$(this).attr("id")] = $(this).attr("src");
		}
		if($(this).is(":hidden")){
			params[$(this).attr("id")] = "";
		}
	});
	if(params['pk'] ==''){
		params['companyType'] = 1;
	}
	//新增子公司
	if($("#parentPk").val()!="" && $("#parentPk").val() != params['pk']){
		params['parentPk'] = $("#parentPk").val();
	}
	
	if($("#editCompanyRemarks").val()!=""){
		params['remarks'] = $("#editCompanyRemarks").val();
	}
	params['auditStatus'] = $("#auditStatusFlag").val();
	
	isBlUrl = $("#blUrl")[0].src;
	isEcUrl = $("#ecUrl")[0].src;
	if(valid("#dataForm")){
		var mobile = $("#contactsTel").val();
		if(regMobile(mobile) == false){
			new $.flavr({
				modal : false,
				content : "手机号码格式不正确！"
			});
			return ;
		}
	
		if(stringLength(params['contacts'],20) == false){
			new $.flavr({
				modal : false,
				content : contact_message
			});
			return ;
		}

        var  orgCode = params['organizationCode'];
        if(orgCode != ""){
            params['organizationCode'] = orgCode.toUpperCase();
        }


		var url = "";
		if(params['pk'] ==''){
			url = "./insertB2bCompany.do";
		}else{
			url = "./updateB2bCompany.do";
		}
		$.ajax({
			type : 'POST',
			url : url,
			data : params,
			dataType : 'json',
			async : true,
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if(data.success==true){
					$("#quxiao").click();
					if($("#childCompany").is(":hidden")){
						$('#purchaserId').bootstrapTable('refresh');
					}else{
						$('#childs').bootstrapTable('refresh');
					}
				}
			}
		});
	}
}

/**
 * 采购商信息编辑
 * @param pk
 * @param name
 * @param contacts
 * @param contactsTel
 * @param insertTime
 * @param auditTime
 * @param blUrl
 * @param organizationCode
 * @param ocUrl
 * @param trUrl
 * @param opUrl
 * @param ecUrl
 * @param province
 * @param provinceName
 * @param city
 * @param cityName
 * @param area
 * @param areaName
 * @param remarks
 * @param auditStatus
 */
function editCompay(pk, name,contacts,contactsTel,insertTime,auditTime,blUrl,organizationCode,ocUrl,trUrl,opUrl,ecUrl,province,provinceName,city,cityName,area,areaName,remarks,auditStatus) {
	
	$("#myModalLabel").html("编辑公司信息");
	$('#editCompayId').modal();
	if (null != pk && '' != pk) {
		$('#pk').val(pk);
		$('#compayName').val(name);
		$('#contacts').val(contacts=="undefined"?"":contacts);
		
		$('#contactsTel').val(contactsTel=="undefined"?"":contactsTel);
		$('#editCompanyRemarks').val(remarks=="undefined"?"":remarks);
		$('#insertTime').val(insertTime);
		$("#auditStatusFlag").val(auditStatus=="undefined"?"":auditStatus);
		$("#province").val(province=="undefined"?"":province);
		if(city !="undefined"){
			$("#city").empty().append("<option value='"+city+"'>"+cityName+"</option>");
		}
		if(area != "undefined"){
			$("#area").empty().append("<option value='"+area+"'>"+areaName+"</option>");
		}
		if(organizationCode!= "undefined"){
			$('#organizationCode').val(organizationCode);
		}else{
			$('#organizationCode').val("");	
		}
		
		$('#auditTime').val(auditTime);
		if(blUrl=="undefined" ||blUrl==""){
			$("#blUrl")[0].src="./style/images/bgbg.png";
		}else{
			$("#blUrl").attr("src",blUrl);
		}
		if(ecUrl=="undefined" || ecUrl==""){
			$("#ecUrl")[0].src = "./style/images/bgbg.png";
		}else{
			$("#ecUrl").attr("src",ecUrl);
		}
		$('#auditEcUrl').val('');
	} else {
		$('#pk').val('');
		$('#compayName').val('');
		$('#contacts').val('');
		$('#contactsTel').val('');
		$('#insertTime').val('');
		$('#auditTime').val('');
		$("#ocUrl").attr("src",'');
		$("#trUrl").attr("src",'');
		$("#opUrl").attr("src",'');
		$("#editCompayId img").each(function(){
			$(this)[0].src = "./style/images/bgbg.png";
		});
	}
	$("input[name='file']").val('');
	$("input[type=radio],input[name=file]").each(function(i,n){
	});
}

/**
 * 采购商审核状态Table页切换
 * @param s
 */
function findPurchaser(s){
	if(s==0){
		$("#auditStatus").val("");
	}else{
		$("#auditStatus").val(s);
	}
	var cfg = {
			url : './searchCompanyList.do'+urlParams(1)+"&isDelete=1&companyType=1&modelType="+$("#modelType").val(),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'infoUpdateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('purchaserId', cfg);
}

/**
 * 搜索和清除
 * @param type
 */
function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './searchCompanyList.do'+urlParams(1)+"&isDelete=1&companyType=1&modelType="+$("#modelType").val(),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'infoUpdateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('purchaserId', cfg);
}

/**
 * 刷新列表页面
 */
function loadFancyBox(){
	for(var i=0;i<200;i++){
		if($("a[rel=group"+i+"]").attr("href")!=undefined&&$("a[rel=group"+i+"]").attr("href")!=''){
				//图片弹出框
			$("a[rel=group"+i+"]").fancybox({
					'titlePosition' : 'over',
					'cyclic'        : true,
					'titleFormat'	: function(title, currentArray, currentIndex, currentOpts) {
						return '<span id="fancybox-title-over">' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
					}
				});
			}
	}
	btnList();
}
/**
 * 刷新列表页面-子列表
 */
function loadFancyBoxTwo(){
	for(var i=0;i<100;i++){
		if($("#childs a[rel=group1"+i+"]").attr("title")!=undefined&&$("#childs a[rel=group1"+i+"]").attr("href")!=""){
			//图片弹出框
			$("#childs a[rel=group1"+i+"]").fancybox({
				'titlePosition' : 'over',
				'cyclic'        : true,
				'titleFormat'	: function(title, currentArray, currentIndex, currentOpts) {
					return '<span id="fancybox-title-over">' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
				}
			});
		}
	}
    btnList();
}

/**
 * 新增公司
 */
function addParent(){
	$("#parentPk").val("");
	$("#myModalLabel").html("新增公司");
	$('#editCompayId').modal();
	$("#dataForm").find("input[type='text'],input[type='hidden']").each(function(){
		$(this).val("");
	});
	$("#editCompayId img").each(function(){
		//$(this).attr("src","");
		$(this)[0].src = "./style/images/bgbg.png";
	});
	$("input[name='file']").val("");
	$("#province").val("");
	$("#city").empty().append("<option value=''>-市-</option>");
	$("#area").empty().append("<option value=''>-区-</option>");
}

var columnMember = [
	          		{
	          			title : '操作',
	          			field : 'pk',
	          			width : 200,
	          			formatter : function(value, row, index) {
	          				var str ="";
	          				if(row.companyTypeArr != null && row.companyTypeArr.indexOf("0")!= -1){
	          					$("#oldMemberPk").val(row.pk);
	          					str =  "<input type='checkbox' value='"+row.pk+"' checked='checked' onchange='changeMember(this)'>";
	          				}else{
	          					str =  "<input type='checkbox' value='"+row.pk+"' onchange='changeMember(this)'>";
	          				}
	          				return str;
	          			}},
			   		{
			   			title : '会员手机号',
			   			field : 'mobile',
			   			width : 20,
			   			sortable : true
			   		}];

/**
 * 设置超级管理员
 * @param companyPk
 */
function setAdmin(companyPk){
	$("#adminMobile").val("");
	var url = './getMemberListByAdmin.do?pk='+companyPk+"&mobile="+$("#adminMobile").val()+"&companyType=1&modelType="+$("#modelType").val();
	var cfg = {
			url : url,
			columns : columnMember,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar
		};
	
	$("#getAdminByCompanyTypePk").val(companyPk);
	createDataGrid('members', cfg);
}

/**
 * 绑定超级管理员 手机号搜索
 */
function searchTabsMonbile(){
	var url = './getMemberListByAdmin.do?pk='+$("#getAdminByCompanyTypePk").val()+"&mobile="+$("#adminMobile").val();
	var cfg = {
			url : url,
			columns : columnMember,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar
		};
	createDataGrid('members', cfg);
}

/**
 * 备注
 * @param pk
 * @param remarks
 * @param type
 */
function  remarks(pk,remarks,type){
	$('#remarksCompany').modal();
	$("#remarksPk").val(pk);
	$("#remarksCompany").val(type);
	if(remarks != null && remarks != "undefined"){
		$("#remarks1").val(remarks);	
	}else{
		$("#remarks1").val("");	
		
	}
	
}

/**
 * 保存备注
 */
function submitRemarks(){
	
	var pk = $("#remarksPk").val();
	var remarks = $("#remarks1").val();
	var type = $("#remarksCompany").val();
	$.ajax({
		type : 'POST',
		url : "./updateB2bCompany.do",
		data :{
			pk:pk,
			remarks:remarks,
			isUpdateRemarks:"1"
		},
		dataType : 'json',
		async : true,
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
			if(data.success==true){
				$("#quxiao").click();
				
				$(function () { $('#remarksCompany').modal('hide');});
				if(type == 1){
					$('#purchaserId').bootstrapTable('refresh');
				}else{
					$('#childs').bootstrapTable('refresh');
				}
				
			}
		}
	});
}

/**
 * 导出
 */
function excel() {
	
	//window.location.href='./exportB2bCompany.do'+urlParams(1)+"&companyType=1&modelType="+$("#modelType").val();
    $.ajax({
        type : 'POST',
        url : './exportB2bCompany.do'+urlParams(1)+"&companyType=1",
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

/**
 * 绑定超级管理员 保存
 */
function sureAdmin(){
	var memberPk =  $("#members").find("input[type='checkbox']:checked").val();
	if(memberPk == 'undefined' || memberPk == undefined || memberPk == ""){
		new $.flavr({
			modal : false,
			content : "请选择要设置为超级管理员的手机号！"
		});
		return ;
	}
	$.post("./updateSetAdmin.do",{memberPk:memberPk,oldMemberPk:$("#oldMemberPk").val(),companyPk:$("#getAdminByCompanyTypePk").val()},function(data){
		if(data){
			new $.flavr({
				modal : false,
				content : data.msg
			});
			$(function () { $('#setAdmin').modal('hide');});
			$('#purchaserId').bootstrapTable('refresh');
		}
	},"json");
}

/**
 * 修改复选框 获取改变要绑定超级管理员值
 * @param self
 */
function changeMember(self){
	$("#members input[type='checkbox']:checked").prop("checked",false);
	$(self).prop("checked","checked");
}


/*****************************公司管理START***********************************/
/**
 * 公司管理
 * @param pk
 */
function companyManage(pk){
	var cfg = {
			url : './searchCompanyManageList.do?parentPk='+pk+"&isDelete=1&modelType="+$("#modelType").val(),
			columns : columnNew,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBoxTwo
		};
	createDataGrid('childs', cfg);
	$("#parentPk").val(pk);
}

var item = {
		title : '操作',
		field : 'pk',
		width : 50,
		formatter : function(value, row, index) {
			var str = "";
			var insertTime=row.insertTime;
			var auditTime=row.auditTime;
				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="COMMON_CM_PURCHASER_COM_BTN_CREDIT" class="btn btn-warning" onclick="javascript:auditCompany(\'' + value + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
				row.contactsTel + '\',\''+insertTime+'\',\''+auditTime+'\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.remarks+'\',\''+row.refuseReason+'\',\''+row.auditStatus+'\',2); ">  审核</button> </a>';
            if (row.auditStatus != 1 && row.auditStatus != 3) {
                str += '<a class="save" href="javascript:;" style="text-decoration:none;">  <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_COM_BTN_INVICE" class="btn btn btn-primary" data-toggle="modal" data-target="#editInvoiceId" onclick="javascript:invocieInfo(\'' + value + '\',\'' + row.name + '\'); ">发票信息 </button> </a>';
            }
				if (row.auditStatus == 1) {
				}else if(row.auditStatus == 2){
					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_COM_BTN_SADMIN" class="btn btn btn-primary" data-toggle="modal" data-target="#setAdmin" onclick="javascript:setAdmin(\''+value+'\'); ">设置超级管理员 </button> </a>';
				}else if(row.auditStatus == 3){
//					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" class="btn btn-warning" onclick="javascript:auditCompany(\'' + value + '\',2); ">  审核通过 </button> </a>';
				}
            str+=' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_COM_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editCompayId" onclick="javascript:editCompay(\'' + value + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
                row.contactsTel + '\',\''+insertTime+'\',\''+auditTime+'\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.remarks+'\',\''+row.auditStatus+'\');">编辑</button></a>';

				if (row.isVisable == 1) {
						str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_COM_BTN_DISABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',2,\''+row.remarks+'\'); ">禁用</button></a>';
					} else {
						str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_COM_BTN_ENABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',1,\''+row.remarks+'\'); ">启用</button></a>';
						 
					}
				str +='<a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_PURCHASER_COM_BTN_REMARK" class="btn btn btn-default" onclick="javascript:remarks(\'' + value + '\',\''+row.remarks+'\',2); ">备注</button></a>';
				return str;
		}
};
var item4 = {
		title : '营业执照五证',
		field : 'businessLicense',
		width : 50,
		sortable : true,
		formatter : function(value, row, index) {
			var str="";
			str+="<a rel='group1"+index+"' title='营业执照' href='"+row.blUrl+"'><img class='bigpicture' src='"+row.blUrl+"' onerror='errorImg(this)'></a>";
			if(row.ocUrl!=null&&row.ocUrl!=''){
				str+="<a rel='group1"+index+"' title='组织机构代码' href='"+row.ocUrl+"'><img class='bigpicture' src='"+row.ocUrl+"' onerror='errorImg(this)'></a>";
			}
			if(row.trUrl!=null&&row.trUrl!=''){
				str+="<a rel='group1"+index+"' title='税务登记证' href='"+row.trUrl+"'><img class='bigpicture' src='"+row.trUrl+"' onerror='errorImg(this)'></a>";
			}
			str+=//"<a rel='group1"+index+"' title='开户许可证' href='"+row.opUrl+"'><img class='bigpicture' src='"+row.opUrl+"' onerror='errorImg(this)'></a>"+
				 "<a rel='group1"+index+"' title='企业认证授权书' href='"+row.ecUrl+"'><img class='bigpicture' src='"+row.ecUrl+"' onerror='errorImg(this)'></a>";
			return str;
	
		
			
			
		}
	};
//子公司新的列表
var columnNew = [].concat(columns);
 columnNew[0] = item;
 columnNew[5] = item4;

/**
 * 子公司列表
 * @param value
 */
function findChild(value){
	var url = './searchCompanyManageList.do?parentPk='+$("#parentPk").val()+"&isDelete=1&modelType="+$("#modelType").val();
	if(value!=0){
		url +="&auditStatus="+value;
	}
	var cfg = {
			url : url,
			columns : columnNew,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBoxTwo
		};
	createDataGrid('childs', cfg);
}

/**
 * 新增子公司
 */
function addChild(){
	$("#myModalLabel").html("新增子公司");
	$('#editCompayId').modal();
	$("#dataForm").find("input[type='text'],input[type='hidden']").each(function(){
		$(this).val("");
	});
	
	
	$("input[name='file']").val("");
	$("#editCompayId img").each(function(){
		$(this)[0].src = "./style/images/bgbg.png";
	});
	$("#province").val("");
	$("#city").empty().append("<option value=''>-市-</option>");
	$("#area").empty().append("<option value=''>-区-</option>");
}

/**
 * 发票信息
 * @param pk
 * @param name
 */
function invocieInfo(pk,name) {
	
	$('#editInvoiceId').modal();
	$("#editInvoiceId").find("input[type='text'],input[type='hidden']").each(function(){
		$(this).val("");
	});
	$("#invoiceCompayName").val(name);
	$("#invoiceCompanyPk").val(pk);
	$("#taxidNumber").val("");
	$("#regPhone").val("");
	$("#regAddress").val("");
	$("#bankName").val("");
	$("#bankAccount").val("");
    $("#invoiceProvince").val("");

	$.ajax({
		type : "POST",
		url : './getInvoiceByCompanyPk.do',
		dataType : "json",
		data : {
			companyPk:pk
		},
		success : function(data) {
			if(data.success != false){
				$("#taxidNumber").val(data.taxidNumber);
				$("#regPhone").val(data.regPhone);
				$("#regAddress").val(data.regAddress);
				$("#bankName").val(data.bankName);
				$("#bankAccount").val(data.bankAccount);

                var html = "<option value=''>--省--</option>";
                    if (data.provinceList.length > 0) {
                        for ( var i = 0; i < data.provinceList.length; i++) {

                            if(data.provinceList[i].pk == data.province){
                                html +="<option value="+data.provinceList[i].pk+" selected>"+data.provinceList[i].name+"</option>";
                            }else{
                                html +="<option value="+data.provinceList[i].pk+">"+data.provinceList[i].name+"</option>";
                            }
                        }
                    }
                    $("#invoiceProvince").empty().append(html);
                    $("#invoiceProvince").selectpicker('refresh');
                    //$("#invoiceProvince").empty().append("<option value='"+data.province+"'>"+data.provinceName+"</option>");
				if(data.cityName !="undefined" && data.cityName !=""){
					$("#invoiceCity").empty().append("<option value='"+data.city+"'>"+data.cityName+"</option>");
				}else{
					$("#invoiceCity").empty().append("<option value=\"\">--市--</option>");
				}
				if(data.areaName !="undefined" && data.areaName !=""){
					$("#invoiceArea").empty().append("<option value='"+data.area+"'>"+data.areaName+"</option>");
				}else{
					$("#invoiceArea").empty().append("<option value=\"\">--区--</option>");
				}
			}else{
				$("#invoiceCity").empty().append("<option value=\"\">--市--</option>");
				$("#invoiceArea").empty().append("<option value=\"\">--区--</option>");
			}
			$("#invoiceCity").selectpicker('refresh');
			$("#invoiceArea").selectpicker('refresh');
			//getProvince(data);
		}
	});
}

/**
 * 保存发票信息
 */
function submitInvoice(){
	var compayName = $("#invoiceCompayName").val();
	var companyPk = $("#invoiceCompanyPk").val();
	var taxidNumber = $("#taxidNumber").val();
	var regPhone = $("#regPhone").val();
	var regAddress = $("#regAddress").val();
	var bankName = $("#bankName").val();
	var bankAccount = $("#bankAccount").val();
	// var pk = $("#invoicePk").val();
	
	var province = $("#invoiceProvince").val();
	var city = $("#invoiceCity").val();
	var area = $("#invoiceArea").val();
	 var provinceName = "";
	 if(province.length>0){
		 provinceName=$("#invoiceProvince option:selected").text();
	 }
	 var cityName = "";
	 if(city.length>0){
		 cityName=$("#invoiceCity option:selected").text();
	 }
	 var areaName = "";
	 if(area.length>0){
		 areaName = $("#invoiceArea option:selected").text();
	 }

    if(taxidNumber != ""){
        taxidNumber = taxidNumber.toUpperCase();
    }
	var params = {
			// pk:pk,
			compayName:compayName,
			companyPk:companyPk,
			taxidNumber:taxidNumber,
			regPhone:regPhone,
			regAddress:regAddress,
			bankName:bankName,
			bankAccount:bankAccount,
			province:province,
			provinceName:provinceName,
			city:city,
			area:area,
			cityName:cityName,
			areaName:areaName
	};
	if(validNotHidden("#dataFormInvoice")){
		
		if(isNumberOrChristcross(taxidNumber) == false){
			new $.flavr({
				modal : false,
				content : "纳税人识别号填写不正确！"
			});
			return ;
		}
		if(province == "" || city == "" || area == ""){
			new $.flavr({
				modal : false,
				content : "注册地区不能为空！"
			});
			return ;
		}
		
		if(regMobile(regPhone) == false){
			new $.flavr({
				modal : false,
				content : "注册电话格式不正确！"
			});
			return ;
		}
		
		
		
	$.ajax({
		type : 'POST',
		url : "./editInvoiceDto.do",
		data : params,
		dataType : 'json',
		async : false,
		success : function(data) {
			
			if(data.success == true){
				$(function () { $('#editInvoiceId').modal('hide');});
				$('#childs').bootstrapTable('refresh');
			}
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});
	}
}


/**
 * 获取默认 省
 * @param dto
 */
function getProvince(dto){
	var html = "<option value=\"\">--省--</option>";
	//获取省市区默认选中
	$.ajax({
		type : 'GET',
		url : "./getRegions.do",
		data : {parentPk:-1},
		dataType : 'json',
		async : false,
		success : function(data) {
			if(data){
				for ( var i = 0; i < data.length; i++) {
					if(data[i].pk == dto.province){
						html +="<option value='"+data[i].pk+"' selected>"+data[i].name+"</option>";
					}else{
						html +="<option value='"+data[i].pk+"'>"+data[i].name+"</option>";
					}
					
				}
				$("#invoiceProvince").empty().append(html);
				$("#invoiceProvince").selectpicker('refresh');
			}
		}
	});
}

/**
 * 默认展示 市 区
 * @param self
 */
function changeInvoiceCity(self){
	var pk = $(self).val();
	$("#invoiceCity").empty().append("<option value=''>--市--</option>").append(getRegions(pk));
	$("#invoiceArea").empty().append("<option value=''>--区--</option>");
	$("#invoiceCity").selectpicker('refresh');
	$("#invoiceArea").selectpicker('refresh');
}
/**
 * 默认展示 区
 * @param self
 */
function changeInvoiceArea(self){
	$("#invoiceArea").empty().append("<option value=''>--区--</option>").append(getRegions($(self).val()));
	$("#invoiceArea").selectpicker('refresh');
}
/*****************************公司管理END***********************************/

