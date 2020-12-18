function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp  
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp 
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}
function josn_to_String_D(time) {
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1;// js从0开始取
	var date = datetime.getDate();

	if (month < 10) {
		month = "0" + month;
	}
	if (date < 10) {
		date = "0" + date;
	}

	var time = year + "-" + month + "-" + date;
	return time;
}


function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
 
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
 
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}

function josn_to_String(time) {
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1;// js从0开始取
	var date = datetime.getDate();
	var hour = datetime.getHours();
	var minutes = datetime.getMinutes();
	var second = datetime.getSeconds();

	if (month < 10) {
		month = "0" + month;
	}
	if (date < 10) {
		date = "0" + date;
	}
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minutes < 10) {
		minutes = "0" + minutes;
	}
	if (second < 10) {
		second = "0" + second;
	}

	var time = year + "-" + month + "-" + date + " " + hour + ":" + minutes
			+ ":" + second; // 2009-06-12 17:18:05
	return time;
}

function urlParams(type){
	var params = "";
	var sign="";
	$("header").find("input,select").each(function(i,n){
		if(type==1&&i==0){
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




function urlParamsByHeader(type,headerId){
	var params = "";
	var sign="";
	$("#"+headerId).find("input,select").each(function(i,n){
		if(type==1&&i==0){
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

function cleanpar(){
	$("header").find("input,select").each(function(){
		if(!$(this).is(":hidden")){
			$(this).val("");
		}
	});
   $(".filter-option").each(function(){
	   $(this).text("--请选择--");
   });
}

//function cleanpar(){
//	$("#select_bid_condition").find("input,select").each(function(){
//		if(!$(this).is(":hidden")){
//			$(this).val("");
//		}
//	});
//   $(".filter-option").each(function(){
//	   $(this).text("--请选择--");
//   });
//}

//form表单验证
function valid(formId) {
	var validate_all = $(formId).validate({
		errorElement : 'span',
		errorClass : 'help-block',
		focusInvalid : false,
		ignore : '',
		rules : {
		},
		invalidHandler : function(event, validator) {
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		success : function(label) {
			label.closest('.form-group').removeClass('has-error');
		},
		submitHandler : function(form) {
			return false;
		}
	});
	return $(formId).valid();
}


function validNotHidden(formId) {
	var validate_all = $(formId).validate({
		errorElement : 'span',
		errorClass : 'help-block',
		focusInvalid : false,
		ignore : ":hidden",
		rules : {
		},
		invalidHandler : function(event, validator) {
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		success : function(label) {
			label.closest('.form-group').removeClass('has-error');
		},
		submitHandler : function(form) {
			return false;
		}
	});
	return $(formId).valid();
}


//清除表单验证样式
function removeValid(formId,consoleKey){
//	$(".has-error").removeClass("has-error");
	$("#"+formId).find(".has-error").removeClass("has-error");
	$("#"+formId).find(".help-block").remove();
	if(null !=consoleKey && consoleKey !=''){
		$("#"+formId).find("input,select,textarea").val("");
	}
}

//js对象赋值防止被覆盖
function cloneObj(obj) {
	  var newObj = {};
	  for(var prop in obj) {
	    newObj[prop] = obj[prop];
	  }
	  return newObj;
	}


//用于生成uuid
function S4() {
    return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
    return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4());
}

function btnList(){
	
	var btn = $('#btnList',parent.document).val();
	if(btn !='' && btn !='undefined' &&btn !=undefined){
		var btnList = btn.split(",");
		for(var i=0;i<=btnList.length-1;i++){
			$("button[showname="+btnList[i]+"]").show();
		}
	}
	$("button[showname]").click(function(){
		$("form").each(function(i,n){
			$(n).find(".has-error").removeClass("has-error");
			$(n).find(".help-block").remove();
//			$(n).find("input,select,textarea").val("");
		});
	});
}

function reloadForm(h1,h2){
	//隐藏域元素
	 var html = $("#"+h2+"[name=\'h1:"+h1+"\']").html();
	 if(html == undefined){
		 $("#"+h2).prop("name","h1:"+h1).empty().append($("#"+h1).html());
	 }else{
		 $("#"+h1).empty().append($("#"+h2).html());
	 }
}

/**
 * 
 * @param str 要判断字符串的大小
 * @param length 设定字符串大小
 */
function stringLength(str,length){

	var isOutLength = false;
	
	 if (typeof str != "string"){  
		 str += "";  
	    }
	var strlength = str.replace(/[^\x00-\xff]/g,"01").length;
	if(length > strlength || length == strlength){
		
		//正则表达式只能输入英文和中文
		 var pattern = new RegExp("^[A-Za-z\u4e00-\u9fa5]+$");
		 if(str != "" && str != null){    
		        if(pattern.test(str)){    
		        	isOutLength = true;	 
		        }else{
		        	isOutLength = false;	
		        }    
		    }
	}
	return isOutLength;
}


/**
 * 验证手机号
 * @param str
 * @returns {Boolean}
 */
function isPoneAvailable(str) {  
    var myreg=  /^(1[3-9]\d{9})$/;  
    if (!myreg.test(str)) {  
        return false;  
    } else {  
        return true;  
    }  
}

/**
 * 文本编辑换行替换
 * @param str
 * @returns
 */
function regExpEnter(str){
	if(str != null && str !="" && str !='undefined' && str !=undefined){
		return str.replace(/\n/g,'<br>');
	}else{
		return "";
	}
}
/**
 * 
 * 文本域显示换行去除换行符
 * @param str
 * @returns
 */
function regExpEnterShow(str){
	if(str != null && str !="" && str !='undefined' && str !=undefined){
		re = new RegExp("<br>","g");
		return str.replace(re,"\n");
	}else{
		return "";
	}
	
}

/**
 * 字符串去除换行和空格
 * @param str
 * @returns
 */
function regEnter(str){
	
	if(str != null && str !="" && str !='undefined' && str !=undefined){
		return str.replace(/[\r\n]/g,"").replace(/\ +/g,"");
	}else{
		return "";
	}
	
}

/**
 * 
 * 验证非负正整数
 * @param str
 * @returns
 */
function isDNumber(numbers){
	
	if(numbers != null && numbers !="" && numbers !='undefined' && numbers !=undefined){
		var reg = /^\d+$/;
		if (!reg.test(numbers)) {
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
	
}

/**
 * 
 * 验证非零非负正整数
 * @param str
 * @returns
 */
function isNotZeroDNumber(numbers){
	
	var reg = /^\+?[1-9][0-9]*$/;
	
	if(numbers != null && numbers !="" && numbers !='undefined' && numbers !=undefined){
		if (!reg.test(numbers)) {
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
}


/**
 * 验证手机号码格式
 * @param mobile
 * @returns {Boolean}
 */
function regMobile(mobile){
	
	var reg = /^1[3|4|5|8][0-9]\d{8}$/;
	
	var phone =  /^(1[3-9]\d{9})$/;
    var tel = /^([0-9]{3,4})?[0-9]{7,8}$/;
	if(mobile != null && mobile !="" && mobile !='undefined' && mobile !=undefined){
		if (reg.test(mobile) || phone.test(mobile) || tel.test(mobile)) {
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}

/**
 * 验证是否是数字或字母或两者组合
 * @param str
 * @returns {Boolean}
 */
function isNumberOrChristcross(str){
	
	var reg = /^[0-9a-zA-Z]+$/;
	if(str != null && str !="" && str !='undefined' && str !=undefined){
		if (reg.test(str)) {
			
			return true;
		}else{
			return false;	
		}
	}
	return false;
}

/**
 * 验证是否是数字保留小数点两位
 * @param str
 * @returns {Boolean}
 */
function isFloatTwo(str){
	
	var reg = /^[0-9]+([.]\d{1,2})?$/;
	if(str != null && str !="" && str !='undefined' && str !=undefined){
		if (reg.test(str)) {
			
			return true;
		}else{
			return false;	
		}
	}
	return false;
}




/**
 * 输入内容为小数位不超过2位的数值
 */
function isNumTwo(str) {
	var reg = /^\d{0,8}\.{0,1}(\d{1,2})?$/;
	if (str != null && str != "" && str != 'undefined') {
		if (reg.test(str)) {
			return true;
		} else {
			return false;
		}
	}

	return false;
}

/**
 * 输入内容小数位不超过3位的数值，取值范围【0,100】
 * @param str
 * @returns {Boolean}
 */
function isNumRate(str) {
	var reg = /^\d{0,8}\.{0,1}(\d{1,3})?$/
		if (reg.test(str)) {
			if(str<0||str>100){
				return false;
			}else{
				return true;
			}
		} else {
			return false;
		}

}



function isVersionNum(str) {
    var reg = /^([1-9]\d|[1-9])(\.([1-9]\d|\d)){2}$/;
		if (!reg.test(str)) {
			return	true;
			
		} else {
			return false;
			
		}

}
function formatNumber(num, precision, separator) {
    var parts;
    // 判断是否为数字
    if (!isNaN(parseFloat(num)) && isFinite(num)) {
        // 把类似 .5, 5. 之类的数据转化成0.5, 5, 为数据精度处理做准, 至于为什么
        // 不在判断中直接写 if (!isNaN(num = parseFloat(num)) && isFinite(num))
        // 是因为parseFloat有一个奇怪的精度问题, 比如 parseFloat(12312312.1234567119)
        // 的值变成了 12312312.123456713
        num = Number(num);
        // 处理小数点位数
        num = (typeof precision !== 'undefined' ? num.toFixed(precision) : num).toString();
        // 分离数字的小数部分和整数部分
        parts = num.split('.');
        // 整数部分加[separator]分隔, 借用一个著名的正则表达式
        parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ','));

        return parts.join('.');
    }
    return NaN;
}


/**
 * 浮点数
 * @param oInput
 */
function CheckInputIntFloat(oInput) {
	///^\d{0,8}\.{0,1}(\d{1,2})?$/
	if ('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/, '')) {
		oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? ''
				: oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
	}
}

/**
 *
 * @param tableId
 */
function bootstrapTableRefresh(tableId){

    var pageNumber = $("#"+tableId).bootstrapTable('getOptions').pageNumber;//第几页
    var pageSize = $("#"+tableId).bootstrapTable('getOptions').pageSize;//每页显示多少行
    var totalRows = $("#"+tableId).bootstrapTable('getOptions').totalRows;//总行数

    var totalpages = parseInt(totalRows/pageSize);//获取总页数
    var yuRow = totalRows%pageSize;//获取余数
    if(yuRow > 0){
        totalpages = totalpages + 1;
    }
    //判断是否是最后一页,获取最后一页的行数
    if(totalpages == pageNumber && yuRow == 1 && totalpages > 1){
        //刷新上一页的列表数据
        $("#"+tableId).bootstrapTable('refreshOptions',{pageNumber:totalpages-1});
    } else{
        $("#"+tableId).bootstrapTable('refresh');
    }
}

/**
 * 判断字段是否为空
 * @param param 字段参数
 */
function checkIsEmpty(param){

    if(param == null || param == "" || param == undefined || param == 'undefined'){
        return false;
    }else{
    	return true;
	}
}

function isGteZero(param) {
    var parnt = /^[1-9]\d*(\.\d+)?$/;
    if (!parnt.exec(param)) {
        return false;
    } else{
    	return true;
	}
}

//计算两个日期天数差的函数，通用
function DateDiff(sDate1, sDate2) {  //sDate1和sDate2是yyyy-MM-dd格式
    var aDate, oDate1, oDate2, iDays;
    aDate = sDate1.split("-");
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式
    aDate = sDate2.split("-");
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
    return iDays;  //返回相差天数
}


/**
 * οnkeyup事件
 * input输入框匹配两位小数的金额，超过部分或者其他非数字和小数点字符不显示，小数点只能输入一次，第二次不能输入
 * @param obj
 */
function checkInputMoney(obj)
{ 
  if(obj.value.length==1){
	  obj.value=obj.value.replace(/[^0-9]/g,'')
  }else{
	  
	  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");//只允许输入一个小数点
	  
	  obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数

  }     
}


/**
 *  input只能输入正整数
 * @param obj
 */
function checkInputPositiveInteger(obj)
{   
	 if(obj.value.length==1){
     obj.value=obj.value.replace(/[^0-9]/g,'')
 }else{
     obj.value=obj.value.replace(/\D/g,'')
 }
}
