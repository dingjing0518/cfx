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
<script src="./pageJs/withdrawalsRecord.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">资金管理</a></li>
			<li class="active">提现记录</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
								<label class="col-sm-6 col-md-4 col-lg-3"><span>公司名称：</span> <input class="form-controlgoods input-append " type="text" name="companyName"   value="" onkeydown="entersearch()"/></label>	
								<label class="col-sm-6 col-md-4 col-lg-3"><span>虚拟账户：</span> <input class="form-controlgoods input-append " type="text" name="transactionAccount"   value="" onkeydown="entersearch()"/></label>	
			                    <label class="col-sm-6 col-md-4 col-lg-3"><span>申请时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeBegin"   value="" readonly /></label>
			                     <label class="col-sm-6 col-md-4 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly /></label>
								<label class="col-sm-6 col-md-4 col-lg-3"><span>提现状态：</span> <select class="selectpicker bla bla bli " name="status"><option value="">--请选择--</option><option value="1">成功</option><option value="2">失败</option></select></label>	
			      				 <input type="hidden" id="creditStatus" name="creditStatus" value="0"/>	
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="adv-table editable-table">
                                <div class="tab-pane active" id="all">
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="withdrawalsRecord" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!-- 图片放上去显示js -->
	<script type="text/javascript">
		function showImg1(){
		document.getElementById("wxImg1").style.display='block';
		}
		function hideImg1(){
		document.getElementById("wxImg1").style.display='none';
		}
	</script>
	<!-- 汉化inputfile-->
	 <script>
		$(function(){
			$('.bigpicture').bigic();
		});
    </script>
    <script>
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	searchTabs(this);  
	       }  
	} 
</script>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>