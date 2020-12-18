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
<script src="./pageJs/marketing/c2b/marrieddeal.js"></script>
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
			           <!--  <label><span>&nbsp;采购单号：</span> <input type="text" class="form-controlgoods" aria-controls="dynamic-table"></label>
			            <label><span>会员用户名：</span> <input type="text" class="form-controlgoods" aria-controls="dynamic-table"></label>
			            <label><span>联系人：</span> <input type="text" class="form-controlgoods" aria-controls="dynamic-table"></label>
			            <label><span>发布时间：</span> <input type="text" class="form-controlgoods" aria-controls="dynamic-table"></label> -->
			            <span class="tools pull-right">
			             </span>
			             <input type="hidden" id="status" name="status" value=""/>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findMarri(3);">全部</a>
                                </li>
                               <!--  <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findMarri(1);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findMarri(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(3);">审核不通过</a>
                                </li> -->
                                  <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(1);">洽谈中</a>
                                </li>
                               <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(0);">过期</a>
                                </li>
                               
                                 <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(2);">成交</a>
                                </li>  
                            </ul>
                        </header>
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
               					 </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="marrieddealId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="editMarrId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">更新状态</h4>
				</div>
				<div class="modal-body" id="txm">
				  <input id="marrieddealId" type="hidden"/>
					
					  <label><input name="status" type="radio" value="0" />过期</label> 
					<label><input name="status" type="radio" value="1" />洽谈中 </label> 
					<label><input name="status" type="radio" value="2" />成交</label>  
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>