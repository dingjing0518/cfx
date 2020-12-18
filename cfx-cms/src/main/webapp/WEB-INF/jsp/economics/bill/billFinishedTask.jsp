<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="./pageJs/include/third-party/fancybox/fancybox.css" rel="stylesheet"
	type="text/css" />
<title>内部管理系统</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<!-- 页面内容导航开始-->
<script src="./pageJs/economics/bill/billFinishedTask.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
        <li> 票据管理 /</li>
        <li> 审批管理 /</li>
        <li class="active">审批通过</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<!-- <header class="panel-heading" style="margin-bottom:10px;">
             		</header> -->
				<div class="panel-body">
					<section class="panel">
                       
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
                                <table id="customerTaskId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
<!-- ===================================== -->
<div class="modal fade" id="editId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">批注列表</h4>
				</div>

				<div class="modal-body">
					<section class="panel">
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
		                                <table id="histroyId" style="width: 100%"></table>
		                            </div>
		                        </div>
		                    </section>
				</div>
				
			</div>
		</div>
	</div>


	<script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	searchTabs();  
	       }  
	} 	
	</script>
	
	 </script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>