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

/**
 * 根据传入Pk查询 省市区镇
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


$(function() {
	createDataGrid('supplierId', cfg);
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
				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="COMMON_CM_SUPPLIER_BTN_CREDIT" class="btn btn-warning" onclick="javascript:auditCompany(\'' + value + '\',\'' + row.parentPk + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
   				row.contactsTel + '\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.refuseReason+'\',\''+row.auditSpStatus+'\',1,\''+row.block+'\'); ">  审核</button> </a>';
   				if (row.auditSpStatus == 1) {
   				}else if(row.auditSpStatus == 2){
   					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_BTN_SUBCOM"  class="btn btn btn-primary" data-toggle="modal" data-target="#childCompany" onclick="javascript:childCompany(\''+value+'\'); ">  子公司 </button> </a>';
   					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_BTN_SADMIN" class="btn btn btn-primary" data-toggle="modal" data-target="#setAdmin" onclick="javascript:setAdmin(\''+value+'\',1); ">设置超级管理员 </button> </a>';
   				}else if(row.auditSpStatus == 3){
   				}
   				str+=' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editCompayId" onclick="javascript:editCompay(\'' + value + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
   				row.contactsTel + '\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.block+'\',\''+row.parentPk+'\');">编辑</button></a>';
   				
   					if (row.isVisable == 1) {
   						str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_BTN_DISABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',2); ">禁用</button></a>';
   					} else {
   						str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_BTN_ENABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',1); ">启用</button></a>';
   						 
   					}
   					if (row.auditSpStatus == 2) {
   					str +='<a class="save" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_BTN_ACCOUNT" class="btn btn btn-default" onclick="bankAccount(\'' + value + '\',\''+row.name+'\',1); ">账户</button></a>';
   					}
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
		}
		,{
			title : '注册电话',
			field : 'mobile',
			width : 20,
			sortable : true
		},
		{
			title : '联系人',
			field : 'contacts',
			width : 20,
			sortable : true
		},
		{
			title : '电话',
			field : 'contactsTel',
			width : 20,
			sortable : true
		},
		{
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
				if(row.ocUrl!=null&&row.trUrl!=''){
					str+="<a rel='group"+index+"' title='税务登记证' href='"+row.trUrl+"'><img class='bigpicture' src='"+row.trUrl+"' onerror='errorImg(this)'></a>";
				}
				str+=//"<a rel='group"+index+"' title='开户许可证' href='"+row.opUrl+"'><img class='bigpicture' src='"+row.opUrl+"' onerror='errorImg(this)'></a>"+
					 "<a rel='group"+index+"' title='企业认证授权书' href='"+row.ecUrl+"'><img class='bigpicture' src='"+row.ecUrl+"' onerror='errorImg(this)'></a>";
				return str;
			}
		}, {
			title : '启用/禁用',
			field : 'isVisable',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else if (value == 2) {
					return "禁用";
				}
			}

		}, {
			title : '审核状态',
			field : 'auditSpStatus',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "待审核";
				} else if (value == 2) {
					return "审核通过";
				} else if (value == 3) {
					return "审核不通过";
				}

			}

		},
		{
			title : '注册时间',
			field : 'insertTime',
			width : 20,
			sortable : true
		},
		{
			title : '最后修改时间',
			field : 'updateTime',
			width : 20,
			sortable : true
		},{
			title : '审核时间',
			field : 'auditSpTime',
			width : 20,
			sortable : true
		},{
        title : '板块',
        field : 'block',
        width : 20,
        sortable : true,
        formatter : function(value, row, index) {

            if( value != "" && value == "cf"){
                return "化纤";
            }
            if( value != "" && value == "sx"){
                return "纱线";
            }
        	//如果存在逗号，说明有两个板块
        	if( value != null && value.indexOf(",")!= -1){
				return "化纤,纱线";
			}

		}
    }];
var cfg = {
	url : './searchCompanyList.do?companyType=2&parentPk=-1&modelType='+GetRequest("modelType"),
	columns : columns,
	uniqueId : 'pk',
	sortName : 'updateTime',
	sortOrder : 'desc',
	toolbar : toolbar,
	fixedColumns : true,
	fixedNumber : 1,
	onLoadSuccess:loadFancyBox
};

/**
 * 启用禁用
 * @param pk
 * @param sta
 */
function editState(pk, sta) {

	var parm = {
		'pk' : pk,
		'isVisable' : sta
		
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
				$('#supplierId').bootstrapTable('refresh');
			}else{
				$('#childs').bootstrapTable('refresh');
			}

		}
	});
}

/**
 * 审核供应商
 * @param pk
 * @param parentPk
 * @param name
 * @param contacts
 * @param contactsTel
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
 * @param refuseReason
 * @param auditSpStatus
 * @param companyFlage
 */
function auditCompany(pk,parentPk, name,contacts,contactsTel,blUrl,
						organizationCode,ocUrl,trUrl,opUrl,ecUrl,province,provinceName,
						city,cityName,area,areaName,refuseReason,auditSpStatus,companyFlage,block){
	$("#myModalLabelid").html("供应商审核信息");
	if(companyFlage == 1){
        $("#subCompanyAuthID").attr("style","display:none;");
        $("#companyAuthID").attr("style","display:block;");
	}else{
        $("#subCompanyAuthID").attr("style","display:block;");
        $("#companyAuthID").attr("style","display:none;");
	}
	$('#auditCompayId').modal();
	if (null != pk && '' != pk) {
		$('#auditPk').val(pk);
		$('#auditCompayName').val(name);
		$('#auditContacts').val(contacts=="undefined"?"":contacts);
		$('#auditContactsTel').val(contactsTel=="undefined"?"":contactsTel);
		$("#auditProvince").val(provinceName=="undefined"?"":provinceName);
		$("#auditCity").val(cityName=="undefined"?"":cityName);
		$("#auditArea").val(areaName=="undefined"?"":areaName);
		$("#parentPk").val(parentPk=="undefined"?"":parentPk);
		$("#companyFlage").val(companyFlage=="undefined"?"":companyFlage);
		if(organizationCode!= "undefined"){
			$('#auditOrganizationCode').val(organizationCode);
		}else{
			$('#auditOrganizationCode').val("");	
		}
		$("#auditBlUrl").attr("src",(blUrl=="undefined"||blUrl=="")?"./style/images/bgbg.png":blUrl);
		$("#auditEcUrl").attr("src",(ecUrl=="undefined"||ecUrl=="")?"./style/images/bgbg.png":ecUrl);
		if(auditSpStatus == 3){
			
			$("#auditNoPassR").val(refuseReason=="undefined"?"":regExpEnterShow(refuseReason));
		}else{
			$("#auditNoPassR").val('');
		}

        //板块赋值
        if(block != null && block !=undefined && block !=""){
            //如果存在两个板块权限
            if(block.indexOf(",") != -1){
                $("input[name='blockCredit']").prop("checked",true);
            }
            if(block == "cf"){
                $("#cfCreditId").prop("checked",true);
                $("#sxCreditId").prop("checked",false);
            }
            if(block == "sx"){
                $("#sxCreditId").prop("checked",true);
                $("#cfCreditId").prop("checked",false);
            }
            $("#sxCreditId").prop("disabled",true);
            $("#cfCreditId").prop("disabled",true);

        }
	}
}

/**
 * 提交前判断是审核通过或不通过
 * @param auditStatus
 */
function auditSubmit(auditStatus){
	var pk = $('#auditPk').val();
	var organizationCode = $('#auditOrganizationCode').val();
	var companyFlage = $("#companyFlage").val();

    var block = "";
    $("input[name='blockCredit']:checkbox:checked").each(function(){
        if($(this).val() != null && $(this).val() != ''){
            block +=$(this).val()+",";
        }
    })
    if(block == ""){
        block = "cf,";
    }
    block = block.substring(0,block.length - 1);

	var params = {
				'pk' : pk,
				'parentPk':$('#parentPk').val(),
				'auditSpStatus' : auditStatus,
				'name' :$('#auditCompayName').val(),
				"organizationCode":organizationCode,
				"isAudit":"1",
        		"block":block
			};
		if(auditStatus==2){
			sendPost(params,companyFlage);
		}else{
			$("#auditPk").val(pk);
			var context = $("#auditNoPassR").val();
			
			if(context.replace(/(^\s*)|(\s*$)/g, "") == "" || context == null){
				new $.flavr({
					modal : false,
					content : "请填写不通过原因！"
				});
			}else{
				refuse(pk,auditStatus,regExpEnter(context),organizationCode,companyFlage);
			}
			//$("#noAuditCompany").modal();
		}
	
}

/**
 * 审核不通过时触发
 * @param pk
 * @param auditStatus
 * @param context
 * @param organizationCode
 * @param companyFlage
 */
function refuse(pk,auditStatus,context,organizationCode,companyFlage){
		var params  = {
				pk:pk,
				auditSpStatus:auditStatus,
				refuseReason:context,
				organizationCode:organizationCode,
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
				$(function () { $('#auditCompayId').modal('hide')});
				if(companyFlage == 1){
					$('#supplierId').bootstrapTable('refresh');
				}else{
					$('#childs').bootstrapTable('refresh');
				}
				
			}
		});
}

/**
 * 审核通过触发
 * @param params
 * @param companyFlage
 */
function sendPost(params,companyFlage){
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
			$(function () { $('#auditCompayId').modal('hide')});
			if(companyFlage == 1){
				$('#supplierId').bootstrapTable('refresh');
			}else{
				$('#childs').bootstrapTable('refresh');
			}
		}
	});
}


/**
 * 审核状态切换Table
 * @param s
 */
function findPurchaser(s) {
	if(s==0){
		$("#auditSpStatus").val('');
	}else{
		$("#auditSpStatus").val(s);
	}
	var cfg = {
			url : './searchCompanyList.do?companyType=2'+urlParams('')+"&isDelete=1&modelType="+$("#modelType").val(),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('supplierId', cfg);
}

/**
 * 查询条件改变触发
 */
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	searchTabs();  
       }  
}  
function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './searchCompanyList.do?companyType=2'+urlParams('')+"&isDelete=1&modelType="+$("#modelType").val(),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('supplierId', cfg);
}

/**
 * 跳转到账户管理页面
 * @param companyPk
 * @param companyName
 * @param isCompanyType 公司类别，总公司、子公司
 */
function bankAccount(companyPk,companyName,isCompanyType){
	
    window.location = getRootPath() + "/jumpSupplierBank.do?companyPk="+companyPk+"&companyName="+companyName+"&isCompanyType="+isCompanyType+"&modelType="+$("#modelType").val();
		
}

/**
 * 同步银行
 */
function syncBank(){
	$("#syncBankNameId").attr('disabled',true);
	$.ajax({
		type: 'post',
		url:'./getSysBankName.do',
		data:{},
		dataType:'json',
		success : function(data) {
			if(data != null){
				new $.flavr({
					modal : false,
					content : data.msg
				});
			}
			$("#syncBankNameId").attr('disabled',false);
		}
	});
	
}

/**
 * 编辑供应商
 * @param pk
 * @param name
 * @param contacts
 * @param contactsTel
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
 */
function editCompay(pk, name,contacts,contactsTel,blUrl,organizationCode,
					ocUrl,trUrl,opUrl,ecUrl,province,provinceName,
					city,cityName,area,areaName,block,parentPk) {
    $("#myModalLabel").html("编辑公司信息");
    $('#editCompayId').modal();
    $("input[name='file']").val('');
    $("input[name='block']").prop("checked",false);
    if (null != pk && '' != pk) {
        $('#pk').val(pk);
        $('#companyName').val(name);
        $('#contacts').val(contacts);
        $('#contactsTel').val(contactsTel);
        $("#blUrl").attr("src",blUrl);
        $("#province").val(province);
        $("#city").empty().append("<option value='"+city+"'>"+cityName+"</option>");
        $("#area").empty().append("<option value='"+area+"'>"+areaName+"</option>");
        if(organizationCode!= "undefined"){
            $('#organizationCode').val(organizationCode);
        }else{
            $('#organizationCode').val("");
        }

        if(ocUrl!='undefined' && ocUrl!=""){
            $("#ocUrl").show();
            $("#trUrl").show();
            $("div[name='ocUrl']").show();
            $("div[name='trUrl']").show();
            $("input[type='radio'][value='1']").prop("checked","checked");
        }else{
            $("#ocUrl").hide();
            $("#trUrl").hide();
            $("div[name='ocUrl']").hide();
            $("div[name='trUrl']").hide();
            $("input[type='radio'][value='2']").prop("checked","checked");
        }
//		$("#ecUrl").attr("src",ecUrl);
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
        //板块赋值
        if(block != null && block !=undefined && block !=""){
            checkRadio(block);
        }
        //当编辑为子公司时，需要把总公司的模块赋值到子公司
        if(parentPk != "-1"){
            checkChildRadio(parentPk);
        }

    } else {
        $('#pk').val('');
        $('#companyName').val('');
        $('#contacts').val('');
        $('#contactsTel').val('');
        $("#blUrl").attr("src",'');
        $("#ocUrl").attr("src",'');
        $("#trUrl").attr("src",'');
        $("#opUrl").attr("src",'');
        $("#ecUrl").attr("src",'');
        $("#editCompayId img").each(function(){
            $(this)[0].src = "./style/images/bgbg.png";
        });
    }
    $("input[type=radio],input[name=file]").each(function(i,n){
        //$(n).attr("disabled",true);
    });
}

function checkRadio(value){

    //如果存在两个板块权限
    if(value.indexOf(",") != -1){
        $("input[name='block']").prop("checked",true);
    }
    if(value == "cf"){
        $("#cfId").prop("checked",true);
    }
    if(value == "sx"){
        $("#sxId").prop("checked",true);
    }
}

function  checkChildRadio(value) {
    $.ajax({
        type : 'POST',
        url : "./getParentCompanyByParentPk.do",
        data : {parentPk:value},
        dataType : 'json',
        async : true,
        success : function(data) {
        	if (data != null) {
        		//保存总公司的模块数据，用于子公司选择模块时判断
				$("#parentPkBlock").val(data.block);
               // checkRadio(data.block);
			}
        }
    });
}

/**
 * 供应商会员信息编辑
 */
function submit(){
	 
	var params = {};
	$("#dataForm").find("input,select").each(function(){
		if($(this).attr("name")!=undefined){
			params[$(this).attr("name")] = $(this).val();
		}
	});
	
	if(stringLength(params['contacts'],20) == false){
		new $.flavr({
			modal : false,
			content : contact_message
		});
		return ;
	}

    var block = "";
    $("input[name='block']:checkbox:checked").each(function(){
        if($(this).val() != null && $(this).val() != ''){
            block +=$(this).val()+",";
        }
    })
        block = block.substring(0,block.length - 1);
    if(block == ""){
        new $.flavr({
            modal : false,
            content : "请选择板块！"
        });
        return ;
    }
    params['block'] = block;
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
		params['companyType'] = 2;
		params['auditStatus'] = 2;
		params['auditSpStatus'] = 1;
	}
	//新增子公司
	if($("#parentPk").val()!="" && $("#parentPk").val() != params['pk']){
		params['parentPk'] = $("#parentPk").val();
		//判断模块范围是否超过总公司
        var parentPkBlock = $("#parentPkBlock").val();
		if (checkIsEmpty(block) && checkIsEmpty(parentPkBlock)){
			if (block.length > parentPkBlock.length){
                new $.flavr({
                    modal : false,
                    content : "子公司所选板块不能多于总公司板块！"
                });
                return;
			}
		}
	}
	var url ="";
	if(params['pk'] ==''){
		url = "./insertB2bCompany.do";
	}else{
		url = "./updateB2bCompany.do";
	}
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
						$('#supplierId').bootstrapTable('refresh');
					}else{
						$('#childs').bootstrapTable('refresh');
					}
				}
			}
		});
	}
}

function loadFancyBox(){
	for(var i=0;i<200;i++){
		if($("a[rel=group"+i+"]").attr("title")!=undefined&&$("a[rel=group"+i+"]").attr("href")!=''){
			console.log($("a[rel=group"+i+"]").attr("href"));
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
function loadFancyBoxTwo(){
	for(var i=0;i<200;i++){
		if($("#childs a[rel=group1"+i+"]").attr("title")!=undefined&&$("#childs a[rel=group1"+i+"]").attr("href")!=''){
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
var item = {
		title : '操作',
		field : 'pk',
		width : 50,
		formatter : function(value, row, index) {
			var str ='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="COMMON_CM_SUPPLIER_SUBCOM_BTN_CREDIT" class="btn btn-warning" onclick="javascript:auditCompany(\'' + value + '\',\'' + row.parentPk + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
			row.contactsTel + '\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.refuseReason+'\',\''+row.auditSpStatus+'\',2,\''+row.block+'\'); ">  审核</button> </a>';
			if (row.auditSpStatus == 1) {
			}else if(row.auditSpStatus == 2){
				str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_SUBCOM_BTN_SADMIN" class="btn btn btn-primary" data-toggle="modal" data-target="#setAdmin" onclick="javascript:setAdmin(\''+value+'\',2); ">设置超级管理员 </button> </a>';
			}else if(row.auditSpStatus == 3){
			}
			str+=' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_SUBCOM_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editCompayId" onclick="javascript:editCompay(\'' + value + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
			row.contactsTel + '\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\',\''+row.block+'\',\''+row.parentPk+'\');">编辑</button></a>';
				if (row.isVisable == 1) {
					str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_SUBCOM_BTN_DISABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',2); ">禁用</button></a>';
				} else {
					str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="COMMON_CM_SUPPLIER_SUBCOM_BTN_ENABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',1); ">启用</button></a>';
				}
				if (row.auditSpStatus == 2) {
					str+='<a class="save" href="javascript:;" style="text-decoration:none;">  <button id="editable-sample_new" style="display:none;" showname="COMMON_CM_SUPPLIER_SUBCOM_BTN_ACCOUNT" class="btn btn-warning" onclick="javascript:bankAccount(\'' +value + '\',\''+row.name+'\',2); ">账户</button> </a>';
				}
				return str;
		}
};
var item5 = {
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
columnNew[7] = item5;
function childCompany(pk){
	var cfg = {
			url : './searchCompanySubList.do?companyType=2&parentPk='+pk+"&isDelete=1&modelType="+$("#modelType").val(),
			columns : columnNew,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBoxTwo
		};

	createDataGrid('childs', cfg);
	$("#parentPk").val(pk);
}

/**
 * 添加子公司
 */
function addChild(){
	$("#myModalLabel").html("新增子公司");
	$('#editCompayId').modal();
    $("input[name='block']").prop("checked",false);
	$("#dataForm").find("input[type='text'],input[type='hidden']").each(function(){
		$(this).val("");
	});
	$("#dataForm img").each(function(){
		$(this).attr("src","");
	});
	$("#editCompayId img").each(function(){
		$(this)[0].src = "./style/images/bgbg.png";
	});
	$("#province").val("");
    $("input[name='file']").val("");
	$("#city").empty().append("<option value=''>-市-</option>");
	$("#area").empty().append("<option value=''>-区-</option>");
}

/**
 * 添加公司
 */
function addParent(){
	$("input[name='file']").val('');
	$("#parentPk").val("");
	$("#myModalLabel").html("新增公司");
	$('#editCompayId').modal();
    $("input[name='block']").prop("checked",false);
	$("#dataForm").find("input[type='text'],input[type='hidden']").each(function(){
		$(this).val("");
	});
	$("#editCompayId img").each(function(){
		$(this)[0].src = "./style/images/bgbg.png";
	});
	$("input[name='file']").val("");
	$("#province").val("");
	$("#city").empty().append("<option value=''>-市-</option>");
	$("#area").empty().append("<option value=''>-区-</option>");
}

/**
 * 子公司根据审核状态切换Table
 * @param value
 */
function findChild(value){
	var url = './searchCompanySubList.do?companyType=2&parentPk='+$("#parentPk").val()+"&isDelete=1&modelType="+$("#modelType").val();
	if(value!=0){
		url += "&auditSpStatus="+value;
	}
	var cfg = {
			url : url,
			columns : columnNew,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBoxTwo
		};
	createDataGrid('childs', cfg);
}

var columnMember = [
	          		{
	          			title : '操作',
	          			field : 'pk',
	          			width : 200,
	          			formatter : function(value, row, index) {
	          				var str ="";
	          				if(row.companyTypeArr != null && row.companyTypeArr.indexOf("0")!= -1 ){
	          					if($("#flag").val() == 1){
	          						$("#oldMemberPk").val(row.pk);	
	          					}else{
	          						$("#oldChildMemberPk").val(row.pk);		
	          					}
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
 * 绑定超级管理员页面
 * @param companyPk
 * @param flag
 */
function setAdmin(companyPk,flag){
	$("#adminMobile").val("");
var url = './getMemberListByAdmin.do?pk='+companyPk+"&mobile="+$("#adminMobile").val()+"&companyType=2&modelType="+$("#modelType").val();
var cfg = {
		url : url,
		columns : columnMember,
		uniqueId : 'pk',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar
	};
	$("#flag").val(flag);
	$("#getAdminByCompanyPk").val(companyPk);
createDataGrid('members', cfg);
}

/**
 * 搜索超级管理员页面电话
 */
function searchTabsMonbile(){
	var url = './getMemberListByAdmin.do?pk='+$("#getAdminByCompanyPk").val()+"&mobile="+$("#adminMobile").val()+"&modelType="+$("#modelType").val();
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
 * 导出采购商
 */
function excel() {
	
	//window.location.href='./exportB2bCompany.do'+urlParams(1)+"&companyType=2&modelType="+$("#modelType").val();

    $.ajax({
        type : 'POST',
        url : './exportB2bCompany.do'+urlParams(1)+"&companyType=2",
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
 * 保存 绑定超级管理员
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
		var oldMemberPk = null;
       if($("#flag").val() == 1){
    	   oldMemberPk =  $("#oldMemberPk").val();
       }else{
    	   oldMemberPk =  $("#oldChildMemberPk").val();
       };
$.post("./updateSetAdmin.do",{memberPk:memberPk,oldMemberPk:oldMemberPk,companyPk:$("#getAdminByCompanyPk").val()},function(data){
	if(data){
		new $.flavr({
			modal : false,
			content : data.msg
		});
		$(function () { $('#setAdmin').modal('hide')});
		
		if($("#flag").val() == 1){
			$('#supplierId').bootstrapTable('refresh');
		}else{
			$('#childs').bootstrapTable('refresh');	
		}
		
		
		
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