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
<script src="./pageJs/operation/lottery/lotteryMaterialSetting.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">会员活动</a></li>
			<li class="active">实物设置</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-sm-6 col-md-4 col-lg-3"><span>实物名称：</span> <input class="form-controlgoods input-append " type="text" name="name"   value="" /></label>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button style="display:none;" showname="OPER_ACTIVITY_MATERIAL_BTN_ADD" class="btn btn-primary" id="btn" onclick="insertLotteryMaterial();">新增</button>
		            	
		             </header>
				<div class="panel-body">
					<section class="panel">
                        
                        <div class="bootstrap-table" style="margin-top:10px;">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
    
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="lotteryCouponTableId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editLotteryMaterialId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">实物编辑</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">实物名称:</label>
							<div class="col-sm-10">
							<input type="text" class="form-control" id="name" name="name" style="width:280px;margin-left:24px" required="true" />
							</div>
						</div>
						 <!-- 编辑页面结束 -->
						</form>
						<form id="hideForm" style="display: none;"></form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	    <script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	SearchClean();  
       }  
} 
</script>
<script>
	 var now=new Date();
	 var strTime=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();
		$(function(){
			$('.bigpicture').bigic();
		});
	    $(".form_datetime").datetimepicker({
	      format: "hh:ii",
	      startView:1,
	      autoclose: true,
	      todayBtn: true,
	      language:'zh-CN',
	      startDate:strTime,
	      pickerPosition:"bottom-right"
	    });
    </script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>