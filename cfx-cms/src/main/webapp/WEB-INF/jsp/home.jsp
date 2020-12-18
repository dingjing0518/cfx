<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>CMS管理系统</title>
<link rel="stylesheet" href="./style/css/home.css" type="text/css" />
</head>
<body class="bgscale">
	 <div id="containerscale">
	 <c:forEach items="${list }" var="m" varStatus="status">	
        <div class="imgscale" id="img-${ status.index + 1}">
            <button class="btn" style="background:url(${m.image });" onclick="toManage('${m.url }')"></button>
        </div>
        </c:forEach>
</div>
	
	<div class="clearfix"></div>
	
	<script src="./style/global/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="./style/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="./style/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="./style/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	
	<script src="./style/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="./style/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
	<script src="./style/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
	<script src="./style/global/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<script src="./style/pages/scripts/login-4.min.js" type="text/javascript"></script>
	
	<script>
	
	function toManage(url){
		window.location.href = url;
	}
		var addRippleEffect = function (e) {
		    var target = e.target;
		    if (target.className.toLowerCase() !== 'btn') return false;
		    var rect = target.getBoundingClientRect();
		    var ripple = target.querySelector('.ripple');
		    if (!ripple) {
		        ripple = document.createElement('span');
		        ripple.className = 'ripple';
		        ripple.style.height = ripple.style.width = Math.max(rect.width, rect.height) + 'px';
		        target.appendChild(ripple);
		    }
		    ripple.classList.remove('show');
		    var top = e.pageY - rect.top - ripple.offsetHeight / 2 - document.body.scrollTop;
		    var left = e.pageX - rect.left - ripple.offsetWidth / 2 - document.body.scrollLeft;
		    ripple.style.top = top + 'px';
		    ripple.style.left = left + 'px';
		    ripple.classList.add('show');
		    return false;
		};
		document.addEventListener('click', addRippleEffect, false);
</script>
</body>
</html>