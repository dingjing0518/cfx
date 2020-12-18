<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>内部管理系统</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">求购管理</a></li>
			<li class="active">求购跟踪</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
			            <label><span>&nbsp;采购单号：</span> <input type="text" class="form-controlgoods" aria-controls="dynamic-table"></label>
			            <label><span>会员用户名：</span> <input type="text" class="form-controlgoods" aria-controls="dynamic-table"></label>
			            <label><span>公司名称：</span> <input type="text" class="form-controlgoods" aria-controls="dynamic-table"></label>
			            <label><span>联系人：</span> <input type="text" class="form-controlgoods" aria-controls="dynamic-table"></label>
			            <span class="tools pull-right">
			             </span>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab">已中标</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab">未中标</a>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
                                   <table class="table table-striped table-hover table-bordered" id="editable-sample">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>操作</th> 
                    <th>交易凭证</th>   
                    <th>采购需求</th>
                    <th>状态</th>  
                    <th>订单号</th>  
                    <th>会员账户</th>
                    <th>联系人</th>
                    <th>电话</th>
                    <th>公司名称</th>                  
                </tr>
                </thead>
                <tbody>
                <tr class="">
                    <td>01</td>
                    <td>
                    <a class="save" href="javascript:;" style="text-decoration:none;">
                        <button id="editable-sample_new" class="btn btn-warning" data-toggle="modal" data-target="#myModal">
                                                                                    中标
                        </button>
                    </a>
                    <!-- <a class="save" href="javascript:;" style="text-decoration:none;">
                        <button id="editable-sample_new" class="btn btn-default" data-toggle="modal" data-target="#myModal">
                                                                                  未 审核
                        </button>
                    </a> -->
                    </td>
                    <td><img class="bigpicture" src="style/images/timg.jpg" alt="">
                    </td>   
                    <td>化纤，30kg,A级，等很多信化纤，30kg,A级</td>
                    <td>已中标</td>  
                    <td>2017031600125</td>  
                    <td>antata</td>
                    <td>孙子</td>
                    <td>15152525258</td>
                    <td>苏州化纤汇</td>      
                </tr> 
                </tbody>
                </table>
                </div>
                                <div class="tab-pane active" id="all">
                                <table id="marrieddeal_oldId" style="width: 100%"></table>
    
                                </div>
                                <div class="tab-pane" id="verify">
                                    2
                                </div>
                                <div class="tab-pane" id="verifydone">
                                    3
                                </div>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<script>
	$(function(){
		$('img').bigic();
	});
    </script> 
</body>
</html>