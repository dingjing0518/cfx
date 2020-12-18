$(function() {
	var cfg = {
			url : './searchMemuBarManage.do'+urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	
	createDataGrid('memuBarManageId', cfg);
});
var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			width : 80,
			formatter : function(value, row, index) {
				var str = "";
				 str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button id="editable-sample_new" style="display:none;" showname="OPER_RF_MEMUMG__BTN_DEL" class="btn btn-default" data-toggle="modal" data-target="#myModal" onclick="javascript:del(\''
								+ value + '\');">删除</button></a>';
				str += ' <a class="delete" href="javascript:;" style="text-decoration:none;"> <button type="button" style="display:none;" showname="OPER_RF_MEMUMG__BTN_EDIT" class="btn btn btn-primary" data-toggle="modal" data-target="#myModal3" onclick="javascript:edit(\''
					+ value
					+ '\',\''
					+ row.name
					+ '\',\''
					+ row.source
					+ '\',\''
					+ row.url
					+ '\',\''
					+ row.sort
					+ '\',\''
					+ row.urlLink
					+ '\' );">编辑</button></a>';
									
				return str;
			}
		},
		{
			title : '名称',
			field : 'name',
			width : 20,
			sortable : true
		},
    	{
			title : '来源',
			field : 'source',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "PC";
				}if (value == 2) {
					return "WAP";
				}
				if (value == 3) {
					return "APP";
				}
			}
    	},
	 	{
			title : '预览图',
			field : 'urlLink',
			width : 20,
			sortable : true,
			formatter : function(value, row, index) {
				 var url="";
				 if(value != "" && value != null){
                     url="<a class='fancybox' href='"+value+"'><img style='width:auto;height:30px;' src='"+value+"'/></a>";
				 }
				 return url;
			}
		}  , {
			title : '排序',
			field : 'sort',
			width : 20,
			sortable : true
		} ,{
			title : '跳转地址',
			field : 'url',
			width : 20,
			sortable : true
		} ,
		{
			title : '创建时间',
			field : 'insertTime',
			width : 50,
			sortable : true
		}];




function del(pk) {
    var confirm = new $.flavr({
        content : '确定删除吗?',
        dialog : 'confirm',
        onConfirm : function() {
            confirm.close();
            $.ajax({
                type : 'POST',
                url : './deleteMemuBarManage.do',
                data : {
                    pk : pk
                },
                dataType : 'json',
                success : function(data) {
                    new $.flavr({
                        modal : false,
                        content : data.msg
                    });

                    if (data.success) {
                        $("#quxiao").click();
                        bootstrapTableRefresh("memuBarManageId");
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
 * 编辑
 * @param pk
 * @param name
 * @param source
 * @param url
 * @param sort
 * @param urlLink
 */
function edit(pk,name,source,url,sort,urlLink) {
	$(".kv-file-remove").click();
	$('#myModal2').modal();
	if(url==null||url==''){
		$("#PURL_flag").val(0);
	}else{
		$("#PURL_flag").val(1);
	}
	var imgId=document.getElementById("PURL");
	if (null != pk && '' != pk) {
		if(url.length==0){
			imgId.style.display="none";
		}else{
			imgId.style.display="";
		}
		$('#pk').val(pk);
		$('#name').val(name);
		if(sort!='undefined'){
			$('#sort').val(sort);
		}else{
			$('#sort').val('');
		}
		if(url!='undefined'){
			$('#url').val(url);
		}else{
			$('#url').val('');
		}

	    $("#PURL").attr("src",urlLink);
	    $('#tupian').val(urlLink);
        var html ="<option value=\"\">--请选择--</option>";
        for(var i = 0;i < memubar_type.length;i++){
            if(memubar_type[i].key == source ){
                html +="<option value="+memubar_type[i].key+" selected>"+memubar_type[i].value+"</option>";
            }else{
                html +="<option value="+memubar_type[i].key+">"+memubar_type[i].value+"</option>";
            }
        }
        $("#source").html(html);
	} else {
		var imgId=document.getElementById("PURL");
		imgId.style.display="none";
		$('#pk').val('');
		$('#name').val('');
		$('#sort').val('');
	    $("#url").val('');
	    $("#tupian").val('');
	    $("#name").attr("disabled",false);
        $('#source').val('');
	}
}
/**
 * 保存
 */
function submit() {
	if(!validNotHidden("#dataForm")){
		return;   
	 }
	   var pk=$("#pk").val();
	  var sotr = $("#sort").val();
	   if(isNotZeroDNumber(sotr) == false || sotr.length > 8){
		   new $.flavr({
				modal : false,
				content : "排序必须大于零的整数并且小于八位数字"
			});
		   return;
	   }
    	var regexUrl = $("#url").val();
	   // if(regexUrl != null && regexUrl != ''){
       //     var httpRegex = regexUrl.substr(0, 4);
       //     var httpsRegex = regexUrl.substr(0, 5);
	   // 		if (httpRegex != "http" && httpsRegex != "https"){
       //          new $.flavr({
       //              modal : false,
       //              content : "跳转地址需以http://或https://开头！"
       //          });
       //          return;
		// 	}
	   // }
		$.ajax({
			type : "POST",
			url : "./editMemuBarManage.do",
			data : {
				pk : $("#pk").val(),
				url : regexUrl,
				name : $("#name").val(),
				sort : sotr,
                source : $("#source").val(),
                urlLink:$('#tupian').val(),
                block:$('#block').val()
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$("#quxiao").click();
					$('#memuBarManageId').bootstrapTable('refresh');
				}
			}
		});
	}

/**
 * 列表table切换
 * @param s
 */
function findSource(s){
	$("#type").val(s);
	var cfg = {
		url : './searchMemuBarManage.do'+urlParams(1),
		columns : columns,
		uniqueId : 'pk',
		sortName : 'sort',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:loadFancyBox
	};
	createDataGrid('memuBarManageId', cfg);
}

function searchTabs(type){
	//清除所有内容
	if(type==2){
		cleanpar();
	}
	var cfg = {
			url : './searchMemuBarManage.do'+urlParams(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'sort',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:loadFancyBox
		};
	createDataGrid('memuBarManageId', cfg);
}

function loadFancyBox(){
	$(".fancybox").fancybox({
	    'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic',
		'titlePosition' : 'inside'
	});
	btnList();
}
 
