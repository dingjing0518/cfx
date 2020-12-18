/**************************地区管理选择切换START***************************/
function changeCity(self){
	var pk = $(self).val();
    var s = getIndex(self);
	$('#cityIndex_'+s+'').empty().append("<option value=''>--市--</option>").append(getRegions(pk));
	$('#areaIndex_'+s+'').empty().append("<option value=''>--区--</option>");
    $("#cityName").empty().append("<option value=''>--市--</option>").append(getRegions(pk));
    $("#areaName").empty().append("<option value=''>--区--</option>");
}

function changeArea(self){
    var s = getIndex(self);
    $('#areaIndex_'+s+'').empty().append("<option value=''>--区--</option>").append(getRegions($(self).val()));
    $("#areaName").empty().append("<option value=''>--区--</option>").append(getRegions($(self).val()));
}

function getIndex(self){
    var ind = $(self).attr("id");
    var s = 1;
    if(ind != null && ind != ""){
        s = ind.split("_")[1];
    }
    return s;
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
/**************************地区管理选择切换END***************************/

$(function() {
	createDataGrid('manageRegionId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			formatter : function(value, row, index) {
				var str = "";
				str += '<button id="editable-sample_new" style="display:none;" showname="MARKET_PERSONNEL_MG_REGION_BTN_EDIT" class="btn btn-primary" data-toggle="modal" onclick="javascript:addLogisticsRoute(\''
						+ value + '\',\'' + row.name + '\'); ">编辑</button>';
				str += '<button type="button" style="display:none;" showname="MARKET_PERSONNEL_MG_REGION_BTN_DEL" onclick="javascript:deleteRegion(\''
						+ value
						+ '\'); " class="btn btn-danger active">删除</button>';
				if(row.isVisable ==2){
                    str += '<button type="button" style="display:none;" showname="MARKET_PERSONNEL_MG_REGION_BTN_ENABLE" onclick="javascript:updateStatus(\''
                        + value
                        + '\',1); " class="btn btn-primary">启用</button>';
				}
                if(row.isVisable ==1) {
                    str += '<button type="button" style="display:none;" showname="MARKET_PERSONNEL_MG_REGION_BTN_DISABLE" onclick="javascript:updateStatus(\''
                        + value
                        + '\',2); " class="btn btn-danger active">禁用</button>';
                }
				return str;
			}
		}, 
		{
			title : '区域名称',
			field : 'name',
            sortable : true
		},{
			title : '地区',
			field : 'area',
		    sortable : true
		},{
			title : '启用/禁用',
			field : 'isVisable',
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else if (value == 2) {
					return "禁用";
				}  
			}
		},{
			title : '修改时间',
			field : 'updateTime',
			sortable : true
		} ];

var cfg = {
		url : './searchManageRegionList.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'insertTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
 

function SearchTabs(type){
	if(type == 2){
		cleanpar();
	}
    var provinceName = "";
    var cityName = "";
    var areaName = "";
	if ($('#provinceName').val() != ""){
        provinceName = $('#provinceName option:selected').text();
	}
    if ($('#cityName').val() != ""){
        cityName = $('#cityName option:selected').text();
    }
    if ($('#areaName').val() != ""){
        areaName = $('#areaName option:selected').text();
    }
	var cfg = {
			url : './searchManageRegionList.do?name='+$("#names").val()+"&provinceName="+provinceName+"&cityName="+cityName+"&areaName="+areaName,
			columns : columns,
			uniqueId : 'pk',
			sortName : 'insertTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('manageRegionId', cfg);
}

/**
 * 删除
 * @param pk
 */
function deleteRegion(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './delManageRegion.do',
				data : {
					'pk' : pk
				},
				dataType : 'json',
				success : function(data) {
					new $.flavr({
						modal : false,
						content : data.msg
					}); /* Closing the dialog */
					if (data.success) {
						$('#manageRegionId').bootstrapTable('refresh');
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
 * 更新启用禁用状态
 * @param pk
 */
function updateStatus(pk,status) {
            $.ajax({
                type : 'POST',
                url : './updateManageRegion.do',
                data : {
                    'pk' : pk,
					'isVisable':status
                },
                dataType : 'json',
                success : function(data) {
                    new $.flavr({
                        modal : false,
                        content : data.msg
                    });
                    if (data.success) {
                        $('#manageRegionId').bootstrapTable('refresh');
                    }
                }
            });
}



//判断添加区域个数
var index = 1;

/**
 * 新增区域
 */
function  addArea(){
    index ++;
    areaDiv(index);
}
/**
 * 编辑区域
 * @param number
 */
function  editArea(number) {
    areaDiv(number);
}

/**
 * 拼接到Div区域HTML
 * @param n 动态区域数量
 */
function areaDiv(index){
    var  content = "<div class='form-group col-ms-12' style='height: 30px; display: block;'>"+
        "<label for='inputEmail3' class='col-sm-2 control-label' style='text-align: left;'></label>"+
        "<div class='col-sm-10' style='margin-left:-15px;'>"+
        "<div class='col-sm-3'>"+
        "<select class='form-control col-sm-3' id='provinceIndex_"+index+"' onchange='changeCity(this);'>"+
        "<option value=''>--省--</option>"+
        getRegions(-1)+
        "</select>"+
        "</div><div class='col-sm-3'>"+
        "<select class='form-control col-sm-3 ' id='cityIndex_"+index+"' onchange='changeArea(this);'>"+
        "<option value=''>--市--</option>"+
        "</select>"+
        "</div><div class='col-sm-3'>"+
        "<select class='form-control col-sm-3' id='areaIndex_"+index+"'>"+
        "<option value=''>--区--</option>"+
        "</select>"+
        "</div>"+
        "<a style = 'margin-left: 23px;color: red;' onclick='del(this)'>删除</a>"+
        "</div>"+
        "</div>";
    $("#indexNumber").val(index);
    $("#addAreaDiv").append(content);
}

/**
 * 删除平台公布价
 * @param ctrl
 */
function del(ctrl) {
    var indexs = $("#indexNumber").val();
    if (indexs != null && parseInt(indexs) > 1) {
        $("#indexNumber").val(indexs -1);
	}
	$(ctrl).parent().parent().remove();
}

/**
 * 展示编辑或新增页面页面
 * @param pk
 */
function addLogisticsRoute(pk) {
	$('#editLogisticsRouteId').modal();
	if (null != pk && '' != pk) {
		detail(pk);
	} else {
        index=1;
        $("#addAreaDiv").html("");
        $("#name").val("");
        $("#pk").val("");
        $("#provinceIndex_1").val("");
        $("#cityIndex_1").empty().append("<option value=''>--市--</option>");
        $("#areaIndex_1").empty().append("<option value=''>--区--</option>");
		$("#myModalLabel").html("新增大区");
		
	}
}

/**
 * 编辑线路
 * @param keyValue
 */
function detail(keyValue) {
	$("#myModalLabel").html("编辑大区");
	$("#addAreaDiv").html("");
	$.ajax({
		type : "post",
		url : './getSearchManageRegionByPk.do',
		data : {
			pk : keyValue
		},
		dataType : 'json',
		success : function(data) {
            if(data.area != "") {
                var jsonarray = $.parseJSON(data.area);
                $.each(jsonarray, function (i, value) {
                    if (i == 0) {
                        $("#provinceIndex_1").val(value.province);
                        $("#provinceIndex_1 option:selected").text(value.provinceName);
                        $("#cityIndex_1").empty().append("<option value=''>--市--</option>").append(getRegions(value.province));
                        $("#cityIndex_1").val(value.city);
                        $("#areaIndex_1").empty().append("<option value=''>--区--</option>").append(getRegions(value.city));
                        $("#areaIndex_1").val(value.area);
                    } else {
                        //保证添加这是下标是最后一条数据的下标
                        index++;
                        editArea((Number(i) + Number(1)));
                        $('#provinceIndex_' + (Number(i) + Number(1)) + '').val(value.province);
                        $('#provinceIndex_' + (Number(i) + Number(1)) + ' option:selected').text(value.provinceName);
                        $('#cityIndex_' + (Number(i) + Number(1)) + '').empty().append("<option value=''>--市--</option>").append(getRegions(value.province));
                        $('#cityIndex_' + (Number(i) + Number(1)) + '').val(value.city);
                        $('#areaIndex_' + (Number(i) + Number(1)) + '').empty().append("<option value=''>--区--</option>").append(getRegions(value.city));
                        $('#areaIndex_' + (Number(i) + Number(1)) + '').val(value.area);
                    }
                });
            }
			$("#pk").val(data.pk);
			$("#name").val(data.name);

		}
	});
}

/**
 * 删除数组指定元素
 * @param val
 */
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

/**
 * 保存区域
 */
function submit() {
var json = "[";
	var province = "";
    var provinceName = "";
    var city = "";
    var cityName = "";
    var area = "";
    var areaName = "";
    var indx = $("#indexNumber").val();
    var indexNumber = 1;
    if(indx != "" && indx != null){
        indexNumber = parseInt(indx);
	}
	var selectedIndex = 1;
	//区域json数据拼接
    for (var n = 1; n < indexNumber+1; n++){
			province = $('#provinceIndex_'+n+'').val();
			if(province !=undefined && province != null && province != ""){
				provinceName = $('#provinceIndex_'+n+' option:selected').text();
				json += "{\"province\":\""+province+"\",\"provinceName\":\""+provinceName+"\"";
			}
			city = $('#cityIndex_'+n+'').val();
			if(city !=undefined && city != null  && city != ""){
				cityName = $('#cityIndex_'+n+' option:selected').text()
				json += ",\"city\":\""+city+"\",\"cityName\":\""+cityName+"\"";
			}
			area = $('#areaIndex_'+n+'').val();
			if (area != null && area != "" && area != undefined){
				areaName = $('#areaIndex_'+n+' option:selected').text();
				json +=  ",\"area\":\""+area+"\",\"areaName\":\""+areaName+"\"";
			}
			if(province !=undefined && province != null && province != "") {
				json += "},";
				//添加标识判断是否必填项已填写
                selectedIndex ++;
			}
        }
    		json = json.substring(json.length - 1,0);
			if(json != ""){
				json +="]";
			}
			//如果省选择了，市为必填
    for (var i = 1; i < selectedIndex+1; i++){
        if($('#provinceIndex_'+i+'').val() != "" && $('#cityIndex_'+i+'').val() == ""){
            new $.flavr({
                modal : false,
                content : "请填写完整省市必填项！"
            });
            return ;
		}
    }
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : './updateManageRegion.do',
			data : {
				pk : $("#pk").val(),
				name : $("#name").val(),
                area:json
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				});
				if (data.success) {
					$("#quxiao1").click();
					$('#manageRegionId').bootstrapTable('refresh');
				} 
			}
		});
	};
}

/**
 * 如果关闭把下标初始化为1
 */
function closeBtn(){
	index = 1;
}



