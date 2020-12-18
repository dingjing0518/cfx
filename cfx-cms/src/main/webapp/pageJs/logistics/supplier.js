
$(function() {
    createDataGrid('supplierDataId', cfg);
});


/**
 * 新增物流公司
 */
function addLogictics(){
    $("#pk").val("");
    $("#myModalLabel").html("新增公司");
    $('#editLogisticsCompanyId').modal();
    $("#dataForm").find("input[type='text'],input[type='hidden']").each(function(){
        $(this).val("");
    });
    $("#dataForm img").each(function(){
        $(this).attr("src","./style/images/bgbg.png");
    });
    var file = $("#inputLicense") ;
	file.after(file.clone().val(""));      
	file.remove();  
}

/**
 * 编辑物流公司
 * @param pk
 * @param name
 * @param contacts
 * @param mobile
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
 function editLogisticsCompany(pk, name,contacts,mobile,blUrl,organizationCode,ocUrl,trUrl,opUrl,ecUrl,province,provinceName,city,cityName,area,areaName) {
    $("#myModalLabel").html("编辑公司信息");
    $('#editLogisticsCompanyId').modal();
    
 	var file = $("#inputLicense") ;
	file.after(file.clone().val(""));      
	file.remove();  
	
    if (null != pk && '' != pk) {
        $('#pk').val(pk);
        $('#companyName').val(name);
        $('#contacts').val(contacts);
        $('#mobile').val(mobile);
        if(blUrl==""||blUrl==undefined||blUrl==null){
        	$("#blUrl").attr("src","./style/images/bgbg.png");
        }else{
        	$("#blUrl").attr("src",blUrl);
        }
    } else {
        $('#pk').val('');
        $('#companyName').val('');
        $('#contacts').val('');
        $('#mobile').val('');
        $("#blUrl").attr("src","./style/images/bgbg.png");
    }
}

/**
 * 保存物流公司
 */
function submit(){

    var params = {};
    $("#dataForm").find("input,select").each(function(){
        if($(this).attr("name")!=undefined){
            params[$(this).attr("name")] = $(this).val();
        }
    })

    $("#dataForm img").each(function(){
        if($(this).attr("id")!=undefined){
            params[$(this).attr("id")] = $(this).attr("src");
        }
        if($(this).is(":hidden")){
            params[$(this).attr("id")] = "";
        }
    });


    var name = $("#companyName").val();
    var mobile = $("#mobile").val();
    var contacts = $("#contacts").val();
    if(mobile == null || mobile == "" ||name == null || name == ""||contacts == null || contacts == ""){
        new $.flavr({
            modal : false,
            content : "所有字段都不能为空！"
        });
        return;
    }
    if(!isMobile(mobile)){
        new $.flavr({
            modal : false,
            content : "请输入正确的手机号码！"
        });
        return;
    }
    if(valid("#dataForm")){
        $.ajax({
            type : 'POST',
            url : "./updateLogisticsCompany.do",
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
                    $('#supplierDataId').bootstrapTable('refresh');
                }
            }
        });
    }
}



var toolbar = [];
var columns = [
    {
        title : '操作',
        field : 'pk',
        formatter : function(value, row, index) {
            var str = "";

            str+=' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="LG_SUPPLIER_BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#editCompayId" onclick="javascript:editLogisticsCompany(\'' + value + '\',\'' + row.name + '\',\'' + row.contacts + '\',\'' +
                row.mobile + '\',\''+row.blUrl+'\',\''+row.organizationCode+'\',\''+row.ocUrl+'\',\''+row.trUrl+'\',\''+row.opUrl+'\',\''+row.ecUrl+'\',\''+row.province+'\',\''+row.provinceName+'\',\''+row.city+'\',\''+row.cityName+'\',\''+row.area+'\',\''+row.areaName+'\');">编辑</button></a>';

            if (row.isVisable == 1) {
                str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="LG_SUPPLIER_BTN_DISABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',2); ">禁用</button></a>';
            } else {
                str +='<a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="LG_SUPPLIER_BTN_ENABLE" class="btn btn btn-default" onclick="javascript:editState(\'' + value + '\',1); ">启用</button></a>';
            }

            return str;
        }
    },
    {
        title : '公司名称',
        field : 'name',
        sortable : true
    }
    ,{
        title : '手机号码',
        field : 'mobile',
        sortable : true
    },
    {
        title : '联系人',
        field : 'contacts',
        sortable : true
    },
    {
        title : '营业执照五证',
        field : 'organizationCode',
        sortable : true,
        formatter : function(value, row, index) {
            var str="";
           if(row.blUrl!=undefined){
        	   if(row.blUrl ==null || row.blUrl==''){
                   str+="<a rel='group"+index+"' title='营业执照' href='./style/images/bgbg.png'><img class='bigpicture' src='"+row.blUrl+"' onerror='errorImg(this)'></a>";
               }else{
                   str+="<a rel='group"+index+"' title='营业执照' href='"+row.blUrl+"'><img class='bigpicture' src='"+row.blUrl+"' onerror='errorImg(this)'></a>";
               }
           }
            return str;
        }
    },
    {
        title : '更新时间',
        field : 'updateTime',
        sortable : true
    },
    {
        title : '状态',
        field : 'isVisable',
        sortable : true,
        formatter : function(value, row, index) {
            if (value == 1) {
                return "正常";
            } else if (value == 2) {
                return "禁用";
            }
        }

    }];

var cfg = {
    url : './logisticsCompanyList.do',
    columns : columns,
    uniqueId : 'pk',
    sortName : 'updateTime',
    sortOrder : 'desc',
    toolbar : toolbar,
    onLoadSuccess:loadFancyBox
};

/**
 * 刷新数据列表
 */
function loadFancyBox(){
	for(var i=0;i<200;i++){
		if($("a[rel=group"+i+"]").attr("title")!=undefined&&$("a[rel=group"+i+"]").attr("href")!=''){
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


function searchTabs(type){
    //清除所有内容
    if(type==2){
        cleanpar();
        cleanDate();
    }
    var cfg = {
        url : './logisticsCompanyList.do?'+urlParams(''),
        columns : columns,
        uniqueId : 'pk',
        sortName : 'updateTime',
        sortOrder : 'desc',
        toolbar : toolbar,
        onLoadSuccess:loadFancyBox
    };
    createDataGrid('supplierDataId', cfg);
}

/**
 * 状态切换Table
 * @param s
 */
function findSupplier(s){
	$("#isVisable").val(s);
	var cfg = {
			url : './logisticsCompanyList.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('supplierDataId', cfg);
}

/**
 * 启用、禁用
 * @param value
 * @param s
 */
function  editState(value , s){
    $.ajax({
        type : 'POST',
        url : "./editLogisticsCompanyState.do",
        data : {
			pk : value,
			isVisable : s,
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
                $('#supplierDataId').bootstrapTable('refresh');
            }
        }
    });
}
