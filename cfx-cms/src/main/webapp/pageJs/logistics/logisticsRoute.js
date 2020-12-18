/**************************地区管理选择切换START***************************/
function changeCity(self){
	var pk = $(self).val();
	$("#city").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#area").empty().append("<option value=''>区</option>");	
}

function changeArea(self){
	$("#area").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
	$("#town").empty().append("<option value=''>镇</option>");
}

function changeTown(self){
	$("#town").empty().append("<option value=''>镇</option>").append(getRegions($(self).val()));	
}

function changeToCity(self){
	var pk = $(self).val();
	$("#toCity").empty().append("<option value=''>市</option>").append(getRegions(pk));
	$("#toArea").empty().append("<option value=''>区</option>");	
}

function changeToArea(self){
	$("#toArea").empty().append("<option value=''>区</option>").append(getRegions($(self).val()));
	$("#toTown").empty().append("<option value=''>镇</option>");
}

function changeToTown(self){
	$("#toTown").empty().append("<option value=''>镇</option>").append(getRegions($(self).val()));	
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
	createDataGrid('logisticsRouteId', cfg);
});

var toolbar = [];
var columns = [
		{
			title : '操作',
			field : 'pk',
			formatter : function(value, row, index) {
				var str = "";
				str += '<button id="editable-sample_new" style="display:none;" showname="LG_ROUTE_BTN_EDT" class="btn btn-primary" data-toggle="modal" onclick="javascript:addLogisticsRoute(\''
						+ value + '\',\'' + row.name + '\'); ">编辑</button>';
				str += '<button type="button" style="display:none;" showname="LG_ROUTE_BTN_DEL" onclick="javascript:delPk(\''
						+ value
						+ '\',\''
						+ row.name
						+ '\'); " class="btn btn-danger active">删除</button>';
				return str;
			}
		}, 
		{
			title : '物流供应商名称',
			field : 'companyName',
		},{
			title : '模板名称',
			field : 'name',
		} , {
			title : '始发地',
			field : 'fromAddress',
		}, {
			title : '目的地',
			field : 'toAddress',
		},{
			title : '启用/禁用',
			field : 'status',
			formatter : function(value, row, index) {
				if (value == 1) {
					return "启用";
				} else if (value == 0) {
					return "禁用";
				}  
			}
		},{
			title : '最后更新时间',
			field : 'updateTime',
			sortable : true
		} ];

var cfg = {
		url : './logisticsRouteGrid.do',
		columns : columns,
		uniqueId : 'pk',
		sortName : 'updateTime',
		sortOrder : 'desc',
		toolbar : toolbar,
		onLoadSuccess:btnList
	};
 

function SearchTabs(type){
	if(type == 2){
		cleanpar();
	}
	var cfg = {
			url : './logisticsRouteGrid.do'+urlParamsNew(1),
			columns : columns,
			uniqueId : 'pk',
			sortName : 'updateTime',
			sortOrder : 'desc',
			toolbar : toolbar,
			onLoadSuccess:btnList
		};
	createDataGrid('logisticsRouteId', cfg);
}

/**
 *  导出线路管理数据
 */
function exportRoute(){
	$.ajax({
		type : "POST",
		url : './exportLogisticsRoute.do',
		dataType : "json",
		data : {
			companyPk : $('#companyPk').val(),
			logisticsCompanyName : $('#companyPk option:selected').text()!="--请选择--"?$('#companyPk option:selected').text():"",
			fromAddress : $('#fromAddress').val(),
			toAddress : $('#toAddress').val(),
			fromUpdateTime : $('#fromUpdateTime').val(),
			toUpdateTime :  $('#toUpdateTime').val(),
			status : $('#status').val()
		},
		success : function(data) {
			new $.flavr({
				modal : false,
				content : data.msg
			});
		}
	});

}


/**
 * 获取<header>标签内的参数值
 * @param type
 * @returns {string}
 */
function urlParamsNew(type){
	var params = "";
	$("header").find("select,input").each(function(i,n){
		if(type==1&&i==1){
			sign="?";
		}else{
			sign="&";
		}
		if($(n).attr("name")==undefined){
			return true;
		}
		params+=sign+$(n).attr("name")+"="+$(n).val();
	});
	return encodeURI(params);
}

/**
 * 删除线路管理
 * @param pk
 */
function delPk(pk) {
	var confirm = new $.flavr({
		content : '确定删除吗?',
		dialog : 'confirm',
		onConfirm : function() {
			confirm.close();
			$.ajax({
				type : 'POST',
				url : './delLogisticsRoute.do',
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
						//$("#quxiao").click();
						$('#logisticsRouteId').bootstrapTable('refresh');
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
 * 新增平台公布价
 */
function  addPrice(){
	var  content = "<div class='form-group col-ms-12' style='height: 30px; display: block;'>"+
	"<label for='inputEmail3' class='col-sm-2 control-label'style='text-align: left;'></label>"+
	"<div class='col-sm-10'>"+
	"<input class='x-input x_ipt st1' type='text' name='showstartweight[]' value='' placeholder='' style='width:92px;'>"+
	" -<input class='x-input x_ipt st2' type='text' name='showendweight[]' value='' placeholder='' style='width:92px;margin-right:5px'>吨，  "+
	" <input class='x-input x_ipt st3' type='text' name='showlogisticsprice[]' value='' placeholder='' style='width:70px;'>元/吨&nbsp&nbsp&nbsp内部结算价："+
	" <input class='x-input x_ipt st4' type='text' name='showinlogisticsprice[]' value='' placeholder='' style='width:70px;'>元/吨<a style = 'margin-left: 23px;color: red;' onclick='del(this)' >删除</a></div></div> "
	$("#addPriceDiv").append(content);
}

/**
 * 删除平台公布价
 * @param ctrl
 */
function del(ctrl) {
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
		$("#myModalLabel").html("新增线路");
		clearRounte ();
		
	}
}

/**
 * 清空线路
 */
function clearRounte (){
	$("#pk").val('');
	$("#name").val('');
	document.getElementById("companyName").options.selectedIndex = 0; //回到初始状态
	$("#companyName").selectpicker('refresh');
	document.getElementById("productName").options.selectedIndex = 0; //回到初始状态
	$("#productName").selectpicker('refresh');
	document.getElementById("grade").options.selectedIndex = 0; //回到初始状态
	$("#grade").selectpicker('refresh');
	$("input[name='isstatus'][value=1]").prop("checked", true);
	$("input[name='minweight']").val("");
	$("input[name='logisticsprice']").val("");
	$("input[name='inlogisticsprice']").val("");
	$(" input[ name='showstartweight[]' ] ").val("");
	$("input[name='showendweight[]']").val("");
	$("input[name='showlogisticsprice[]']").val("");
	$("input[name='showinlogisticsprice[]']").val("");
	$("#addPriceDiv").html("");
	$("#province").val("");
	$("#city").empty().append("<option value=''>--市--</option>");
	$("#area").empty().append("<option value=''>--区--</option>");
	$("#town").empty().append("<option value=''>--镇--</option>");
	$("#toProvince").val("");
	$("#toCity").empty().append("<option value=''>--市--</option>");
	$("#toArea").empty().append("<option value=''>--区--</option>");
	$("#toTown").empty().append("<option value=''>--镇--</option>");
}

/**
 * 编辑线路
 * @param keyValue
 */
function detail(keyValue) {
	$("#myModalLabel").html("编辑线路");
	$("#addPriceDiv").html("");
	$.ajax({
		type : "post",
		url : './LogisticsRouteDetail.do',
		data : {
			pk : keyValue
		},
		dataType : 'json',
		success : function(data) {		
			clearRounte ();
			$("#pk").val(data.pk);
			$("#name").val(data.name);
			$("button[data-id=companyName] span.filter-option").html( data.companyName); 
			$("button[data-id=companyName]").attr("title",data.companyName); 
			$("#companyName").val(data.companyPk);
			//$('#companyName').selectpicker('val', data.companyPk);
			$("#productName").selectpicker('val',data.productPk);
			$("#grade").selectpicker('val',data.gradePk);	
			$("input[type='radio'][value='"+data.status+"']").prop("checked","checked");			
			$("input[name='minweight']").val(validationNumber(data.leastWeight));
			$("input[name='logisticsprice']").val(data.freight);
			$("input[name='inlogisticsprice']").val(data.basicPrice);			
			if(data.list.length>0){
				for(var i = 0 ; i< data.list.length ; i++){
					if(i==0){
						$("input[name='pricePk[]']").val(checkNum(data.list[0].pk));
						$("input[name='showstartweight[]']").val(validationNumber(data.list[0].fromWeight));
						$("input[name='showendweight[]']").val(validationNumber(data.list[0].toWeight));
						$("input[name='showlogisticsprice[]']").val(checkNum(data.list[0].salePrice));
						$("input[name='showinlogisticsprice[]']").val(checkNum(data.list[0].basisPrice));
					}else{
						var  content = "<input name = 'pricePk[]' value='"+data.list[i].pk +"' style = 'display:none' type='text' ></input>" +
								"<div class='form-group col-ms-12' style='height: 30px; display: block;'>"+
						"<label for='inputEmail3' class='col-sm-2 control-label'style='text-align: left;'></label>"+
						"<div class='col-sm-10'>"+
						"<input class='x-input x_ipt st1' type='text' name='showstartweight[]' value='"+checkNum(data.list[i].fromWeight) +"' placeholder='' style='width:92px;'>"+
						" -<input class='x-input x_ipt st2' type='text' name='showendweight[]' value='"+checkNum(data.list[i].toWeight) +"' placeholder='' style='width:92px;margin-right:5px'>吨，  "+
						" <input class='x-input x_ipt st3' type='text' name='showlogisticsprice[]' value='"+checkNum(data.list[i].salePrice) +"' placeholder='' style='width:70px;' onkeyup='javascript:CheckInputIntFloat(this);'>元/吨&nbsp&nbsp&nbsp内部结算价："+
						" <input class='x-input x_ipt st4' type='text' name='showinlogisticsprice[]' value='"+checkNum(data.list[i].basisPrice) +"' placeholder='' style='width:70px;' onkeyup='javascript:CheckInputIntFloat(this);'>元/吨<a style = 'margin-left: 23px;color: red;' onclick='del(this)' >删除</a></div></div> "
						$("#addPriceDiv").append(content);
					}
				}
			}
			$("#province").val(data.fromProvicePk);
			$("#city").empty().append("<option value=''>市</option>").append(getRegions(data.fromProvicePk));
			$("#city").val(data.fromCityPk);
			$("#area").empty().append("<option value=''>区</option>").append(getRegions(data.fromCityPk));
			$("#area").val(data.fromAreaPk);
			$("#town").empty().append("<option value=''>镇</option>").append(getRegions(data.fromAreaPk));	
			$("#town").val(data.fromTownPk);
		 	$("#toProvince").val(data.toProvicePk);
		 	$("#toCity").empty().append("<option value=''>市</option>").append(getRegions(data.toProvicePk));
			$("#toCity").val(data.toCityPk);
			$("#toArea").empty().append("<option value=''>区</option>").append(getRegions(data.toCityPk));
			$("#toArea").val(data.toAreaPk);
			$("#toTown").empty().append("<option value=''>镇</option>").append(getRegions(data.toAreaPk));	
			$("#toTown").val(data.toTownPk);
		}
	});
}

/**
 * 数字校验，保留4位有效数字
 * @param n
 * @returns {*}
 */
function validationNumber(n) {
	if(n==undefined){
		return 0;	
	}else{
		return	formatNumber(n,4);
	}
	
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
 * 保存线路
 */
function submit() {
	 if($("#companyName").val()==""){
			new $.flavr({
				modal : false,
				content : "物流供应商不能为空！"
			});
			return;
	}
	 
	 if($("input[name='logisticsprice']").val()==""){
		 new $.flavr({
				modal : false,
				content : "最低起运价，对外结算价不能为空！"
			});
			return;
	 }
	 
	 if($("input[name='inlogisticsprice']").val()==""){
		 new $.flavr({
				modal : false,
				content : "最低起运价，内部结算价不能为空！"
			});
			return;
	 }
	 
		var showstartweights = "";
		var showendweights ="";
		var showlogisticsprices = "";
		var showinlogisticsprices="";
		var pricePks= "";
		var showstartweight =document.getElementsByName("showstartweight[]");
		var showendweight = document.getElementsByName("showendweight[]");
		var showlogisticsprice = document.getElementsByName("showlogisticsprice[]");
		var showinlogisticsprice = document.getElementsByName("showinlogisticsprice[]");
		var pricePk = document.getElementsByName("pricePk[]");
	 	
		//验证阶梯价的合理性
		for (var i = 0; i < showstartweight.length; i++) {
			if (parseFloat(showstartweight[i].value)> parseFloat(showendweight[i].value)
					||parseFloat(showstartweight[i].value)<parseFloat($("input[name='minweight']").val())) {
				new $.flavr({
					modal : false,
					content : "您填写的阶梯重量不合理，请重新填写！"
				});
				return;
			}
		}
		for (var i = 0, j = showstartweight.length; i < j; i++){
			showstartweights = showstartweights +showstartweight[i].value+ ",";
		}
		for (var i = 0, j = showendweight.length; i < j; i++){
			showendweights = showendweights+showendweight[i].value+ ",";
		}
		
		for (var i = 0, j = showlogisticsprice.length; i < j; i++){
			showlogisticsprices = showlogisticsprices+showlogisticsprice[i].value+ ",";
		}
		for (var i = 0, j = showinlogisticsprice.length; i < j; i++){
			showinlogisticsprices = showinlogisticsprices +showinlogisticsprice[i].value + ",";
		}
		for (var i = 0, j = pricePk.length; i < j; i++){
			pricePks = pricePks +pricePk[i].value + ",";
		}
	 
	 if($("#province").val()==""||$("#toProvince").val()==""||$("#city").val()==""||$("#toCity").val()==""){
		 new $.flavr({
				modal : false,
				content : "路线省市不能为空！"
			});
			return;
	 }
	
	if(valid("#dataForm")){
		$.ajax({
			type : "POST",
			url : './saveLogisticsRoute.do',
			data : {
				pk : $("#pk").val(),
				name : $("#name").val(),
				companyPk:$("#companyName").val(),
				status:$('input:radio[name="isstatus"]:checked').val(),
				productPk :$("#productName").val(),
				productName: $("#productName").val()==""?"": $("button[data-id=productName]").attr("title"),
				gradePk: $("#grade").val(),
				gradeName: $("#grade").val()==""?"":$("button[data-id=grade]").attr("title"),
						
				fromProvicePk:$("#province").val(),
				fromCityPk:$("#city").val(),
				fromAreaPk:$("#area").val(),
				fromTownPk:$("#town").val(),
				
				fromProviceName :$("#province").val()==""? "": $("#province option:selected").text(),
				fromCityName:$("#city").val()==""?"":$("#city option:selected").text(),
				fromAreaName:$("#area").val()==""?"":$("#area option:selected").text(),
				fromTownName:$("#town").val()==""?"":$("#town option:selected").text(),
				
				toProvicePk:$("#toProvince ").val(),
				toCityPk:$("#toCity").val(),
				toAreaPk:$("#toArea").val(),
				toTownPk:$("#toTown").val(),
				
				toProviceName :$("#toProvince").val()==""?"":$("#toProvince option:selected").text(),
				toCityName:$("#toCity").val()==""?"":$("#toCity option:selected").text(),
				toAreaName:$("#toArea").val()==""?"":$("#toArea option:selected").text(),
				toTownName:$("#toTown").val()==""?"":$("#toTown option:selected").text(),
			
				leastWeight:$("input[name='minweight']").val()==""?"":$("input[name='minweight']").val(),
				freight:$("input[name='logisticsprice']").val(),
				basicPrice:$("input[name='inlogisticsprice']").val(),
				showstartweights :showstartweights,
				showendweights:showendweights,
				showlogisticsprices:showlogisticsprices,
				showinlogisticsprices:showinlogisticsprices,
				pricePks:pricePks
			},
			dataType : "json",
			success : function(data) {
				new $.flavr({
					modal : false,
					content : data.msg
				}); 
				if (data.success) {
					$("#quxiao").click();
					$('#editLogisticsRouteId').modal('hide')
					$('#logisticsRouteId').bootstrapTable('refresh');
				} 
			}
		});
	};
	
}

/**
 * 上传线路文件
 */
function importRoute() {
	//清空
	var file = $("#routeFile") ;
	file.after(file.clone().val(""));      
	file.remove(); 
	$('#importRouteFile').modal();
}

/**
 * 导入线路
 */
function submitFile(){
	  var file = document.getElementById("routeFile").files;
	  var form = new FormData();// 可以增加表单数据
	  if(file.length>0){
		  var str = file[0].name;
		  if(file[0] == undefined|| str.indexOf("xls")==-1 && str.indexOf("xlsx")==-1){
		   	 new $.flavr({
						modal : false,
						content : "请选择要导入的excel文件！"
					});
		   	 return;
		   }
	  }else{
		  new $.flavr({
				modal : false,
				content : "请选择要导入的excel文件！"
			});
		  return;
	  }
	
	   form.append("filename", file[0]);// 文件对象
        $.ajax({
            type : 'POST',
            url : "./importRoute.do",
            data: form,
            //关闭序列化
            processData: false,
            contentType: false,
        
        async : true,
        success : function(data) {
            new $.flavr({
                modal : false,
                content : data
            });
            $('#importRouteFile').modal('hide')
			$('#logisticsRouteId').bootstrapTable('refresh');
        }
        
        });
    }


