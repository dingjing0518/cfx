/*
 * 手机号码验证
 */
function isMobile(tel) {
    var telreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0,3,6,7,8])|(14[5,7]))+\d{8})$/;
    if (!telreg.test(tel))
        return false;
    else
        return true;
}

/**
 * 浮点数
 * @param oInput
 */
function CheckInputIntFloat(oInput) {
	if ('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/, '')) {
		oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? ''
				: oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
	}
}


/**
 * 是否存在
 * @param name
 * @returns
 */
function checkNull(name){
	if(name==undefined){
		return "";	
	}else{
		return name;
	}
}

/**
 * 检查是否是数字
 * @param name
 * @returns {*}
 */
function checkNum(name){
	if(name==undefined){
		return 0;	
	}else{
		return name;
	}
}

/**
 * 时间查询条件清理
 */
function cleanDate(){
	$('.form-dateday_start').datetimepicker('setEndDate','');
	$('.form-dateday_end').datetimepicker('setStartDate',''); 
	$('.form-dateday_startcopy').datetimepicker('setEndDate','');
	$('.form-dateday_endcopy').datetimepicker('setStartDate',''); 
}


