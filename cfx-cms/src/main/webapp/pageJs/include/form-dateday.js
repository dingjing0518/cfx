$(".form-dateday").datetimepicker({
	      format: "yyyy-mm-dd",
	      autoclose: true,
	      todayBtn: true,
	      startView:'month',
	      minView:'month',
	      language:'zh-CN',
	      pickerPosition:"bottom-right"
	    });
$(".form-dateday-ymd").datetimepicker({
    format: "yyyymmdd",
      autoclose: true,
      todayBtn: true,
      startView:'month',
      minView:'month',
      language:'zh-CN',
      pickerPosition:"bottom-right"
  });
$(".form-datemonth").datetimepicker({
	format: 'yyyy-mm',
	 weekStart: 1,
    autoclose: true,
    startView: 3,
    minView: 3,
    forceParse: false,
    language: 'zh-CN',
    pickerPosition:"bottom-right"
  });
$(".form-dateyear").datetimepicker({
	startView: 'decade',  
    minView: 'decade',  
    format: 'yyyy',  
    maxViewMode: 2,  
    minViewMode:2,  
    autoclose: true  ,
    pickerPosition:"bottom-right"
  });

$(".form-dateNowyear").datetimepicker({
	startView: 'decade',  
    minView: 'decade',  
    format: 'yyyy',  
    maxViewMode: 2,  
    minViewMode:2,  
    autoclose: true  ,
    pickerPosition:"bottom-right",
    initialDate:new Date(),
    endDate : new Date()
  });

Date.prototype.Format = function (fmt) { //author: meizz
	  var o = {
	    "M+": this.getMonth() + 1, //月份
	    "d+": this.getDate(), //日
	    "h+": this.getHours(), //小时
	    "m+": this.getMinutes(), //分
	    "s+": this.getSeconds(), //秒
	    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
	    "S": this.getMilliseconds() //毫秒
	  };
	  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	  for (var k in o)
	  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	  return fmt;
	}