<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="./style/css/manage.css" rel="stylesheet" type="text/css" />
<style>
.nav .open>a, .nav .open>a:hover, .nav .open>a:focus{
    background-color: #1b293f;
    border-color: #428bca;
}
.page-header{
	background: #444d58!important;
}
.page-header  .hor-menu .navbar-nav>li>a, .page-header  .hor-menu .navbar-nav>li>a>i {
    color: #BCC2CB;
}
.nav>li>a:focus, .nav>li>a:hover, .nav>li>a:active{
color:#fff!important;
}
.hor-menu ul li {
    float: left;
}
.page-header .page-header-menu .hor-menu .navbar-nav>li .dropdown-menu li>a{
 padding:10px 25px!important;
}
@media (max-width: 991px){
    ul.nav.navbar-nav {
	    margin: 0px;
	}
	.page-header{
	height: auto!important;
	}
.page-header .page-header-menu{
background: #444d58!important;
padding:0px!important;
display:block!important;
}
.page-header .page-header-menu .hor-menu .navbar-nav>li {
    float: left!important;
}
.page-header .page-header-menu .hor-menu .navbar-nav>li:first-child>a{
border-radius:0px;
}
.page-container-bg-solid .page-header .page-header-menu .hor-menu .navbar-nav{
background: #444d58!important;
}	
.page-container-bg-solid .page-header .page-header-menu .hor-menu .navbar-nav>li>a {
    background: 0 0!important;
    color: #BCC2CB!important;
}
.page-header .page-header-menu .hor-menu .navbar-nav>li{
 border-bottom:none;
}
.page-header .page-header-menu .hor-menu .navbar-nav>li{
border-bottom:none!important;
}
.page-header .page-header-menu .hor-menu .navbar-nav>li.menu-dropdown:hover>.dropdown-menu ,
.page-header .page-header-menu .hor-menu .navbar-nav>li.menu-dropdown:hover>li.dropdown-submenu >.dropdown-menu{
    display: block;
}
.dropdown-submenu:hover>.dropdown-menu{
display:block!important;
}
.page-header .page-header-menu .hor-menu .navbar-nav .dropdown-menu{
position: absolute!important;
}
.page-header .top-menu, .page-header .top-menu .navbar-nav > li.dropdown-user .dropdown-toggle, .page-header-fixed-mobile .page-header .top-menu{
background-color:transparent!important;
}
.page-header .page-header-menu .hor-menu .navbar-nav .dropdown-menu{
	position: absolute!important;
}
.page-header{
padding-bottom:0px!important;
}
.page-header .page-header-menu .hor-menu .navbar-nav li>a>.arrow:after{
content: "\f105"!important;
display:none!important;
}
.page-header .page-header-menu .hor-menu .navbar-nav li.dropdown-submenu >a>.arrow:after{
display:block!important;
}
}
.hor-menu .nav a {
    font-size: 14px;
    font-weight: 400;
    padding: 16px 18px 15px;
    color: #BCC2CB;
    display: inline-block;
}
.hor-menu .nav a:hover{
	color: #fff;
    background: #55616f!important;
    text-decoration: none;
}
.page-header .page-header-menu .hor-menu .navbar-nav>li .dropdown-menu li:hover>a {
    color: #ced5de;
    background: #5d6b7a!important;
}
</style>
<title>CMS管理系统</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<script type="text/javascript">
	function openModule(pk, url) {
		if($.trim(url) != ""){
			$("#modelusId").attr("src", url + "?pk=" + pk);
		}
		/* $.ajax({
			type : "POST",
			url : url,
			dataType : "html",
			data : {},
			success : function(data) {
				document.getElementById("modelusId").innerHTML = data;
			}
		}); */
	}
	
</script>
</head>
<body class="page-container-bg-solid page-boxed">
	<!-- 头部开始 -->
	<div class="page-header">
		<div class="page-header-menu">
			<div class="container">
				<div class="topbar">
				<input type="hidden" name="btnList" id="btnList" value="${btnList }">
				<!-- 头部右侧设置开始 -->
				<div class="top-menu">
					<ul class="nav navbar-nav pull-right">
						<!-- BEGIN USER LOGIN DROPDOWN -->
						<li class="dropdown dropdown-user dropdown-dark"><a
							href="javascript:;" class="dropdown-toggle"
							data-toggle="dropdown" data-hover="dropdown"
							data-close-others="true" style="color: #fff;">${adto.name }
						</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<li><a href="javascript:;"
									onclick="javascript:openModule('setting','./setting.do');"><i
										class="icon-lock"></i>设置</a></li>
								<li><a href="./doLogout.do"><i class="icon-key"></i>退出</a>
								</li>
							</ul></li>
					</ul>
				</div>
				<%-- 头部右侧设置结束 --%>
				<%-- 头部左侧导航开始 --%>
				<div class="hor-menu  ">
					<ul class="nav navbar-nav">
						<li class="menu-dropdown classic-menu-dropdown"
							style="line-height: 50px; color: #fff; font-size: 18px; margin-right: 15px;">
							<a href="./home.do">CMS管理系统</a>
						</li>
						<li class="menu-dropdown classic-menu-dropdown active"><a
							href="javascript:;" onclick="openModule('index','./index.do');">
								首页 <span class="arrow"></span>
						</a></li>

						<%-- 循环取值 --%>
						<c:forEach items="${list }" var="d" varStatus="status">

							<c:choose>
								<c:when test="${null==d.children}">
									<c:choose>
										<c:when test="${null!=d.url&&''!=d.url }">
											<li class="menu-dropdown mega-menu-dropdown">
												<%-- 第一层，没有下级目录 --%> <a href="javascript:;"
												onclick="openModule('${d.id}','${d.url}');" id="${d.id}">
													${d.text } <span class="arrow"></span>
											</a>
											</li>
										</c:when>
										<c:otherwise>
											<a href="javascript:;" id="${d.id}"><span>${d.text
													}</span></a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<li class="menu-dropdown classic-menu-dropdown ">
										<%-- 一层，有下级目录 --%> <c:choose>
											<c:when test="${null!=d.url&&''!=d.url }">
												<a href="javascript:;"
													onclick="openModule('${d.id}','${d.url}');" id="${d.id}">
													${d.text } <span class="arrow"></span>
												</a>
											</c:when>
											<c:otherwise>
												<a href="javascript:;" id="${d.id}"><span>${d.text
														}</span></a>
											</c:otherwise>
										</c:choose>
										<ul class="dropdown-menu pull-left">
											<c:forEach items="${d.children }" var="c">
												<c:choose>
													<%-- 第二层，如果没有第三层，走次判断 --%>
													<c:when test="${null==c.children}">
														<c:choose>
															<c:when test="${null!=c.url&&''!=c.url }">
																<li class=" "><a href="javascript:;"
																	class="nav-link "
																	onclick="openModule('${c.id}','${c.url}');"
																	id="${c.id}">${c.text } </a></li>
															</c:when>
															<c:otherwise>
																<li class=" "><a href="javascript:;"
																	class="nav-link " id="${c.id}">${c.text } </a></li>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<%-- 第二层，如果有第三次，走次判断--%>
														<li class="dropdown-submenu "><a href="javascript:;"
															class="nav-link nav-toggle "
															onclick="openModule('${c.id}','${c.url}');" id="${c.id}">
																${c.text } <span class="arrow"></span>
														</a>
															<ul class="dropdown-menu">
																<%-- 循环第三层 --%>
																<c:forEach items="${c.children }" var="s">
																	<li class=" "><a href="javascript:;"
																		class="nav-link "
																		onclick="openModule('${s.id}','${s.url}');"
																		id="${s.id}"> ${s.text } </a></li>
																</c:forEach>
															</ul></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</div>
				<!-- 头部左侧导航结束 -->
				</div>
			</div>
		</div>
	</div>
	<iframe src="./index.do" class="frame" scrolling="auto" frameborder="0"
		id="modelusId"></iframe>
	<!-- <div id="modelusId"></div> -->
	<script>
		$(function() {
			$(".navbar-nav li").click(function() {
				$('.navbar-nav li').removeClass("active");
				$(this).addClass("active");
			});
		})
	</script>
</body>
</html>