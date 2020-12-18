<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>CMS管理系统</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
</head>
<body class="sticky-header">

	<section> <!-- 左侧开始--> <!-- 页面内容导航开始-->
	<div class="page-heading">
		<h3>首页</h3>
	</div>
	<!-- 页面内容导航结束--> <!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="wrapper-content">
				<p>
					欢迎您，
					<code>${adto.name }</code>
					，
				</p>
				<p>登录CMS管理系统！</p>
			</div>
		</div>
	</div>
	<!--主题内容结束--> </section>
 
</body>
</html>