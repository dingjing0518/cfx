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
<script src="./pageJs/logistics/lgGoodsLocation.js"></script>
<script src="./pageJs/logistics/common.js"></script>

</head>
<body class="sticky-header">
	<style>
	 .x-input  {
	      font-size: 14px;
	    }
	 .companyNameClass .bootstrap-select > .dropdown-toggle{
	   width:180px;
	 }
	</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#"  onclick="javascript:openLgNormalOrder('');">订单管理>正常订单</a></li>
			<li class="active">货物所在地</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
							<input type="hidden" class="form-control" id="orderPk" name= "orderPk"  value = "${dto.orderPk}"/>
					
							<!-- 模态框 -->
							<button style="display:none;" id="editable-sample_new" showname="LG_ORDER_NORMAL_BTN_GOODSLOC_ADD" class="btn btn-primary btn-new"
								data-toggle="modal" onclick="javascript:add();">新增 <i class="fa fa-plus"></i>
							</button>
		             </header>
					<div class="panel-body">
						<div class="adv-table editable-table ">
							<div class="clearfix">
								<table id="logisticsTrackingId" style="width: 100%"></table>
							</div>
						</div>
					</div>
					</section>
			</div>
		</div>
	</div>
	
<!--------------- 编辑模态框 -------------------------->
<div class="modal fade" id="editGoodsLocationId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" >
		<div class="modal-dialog" role="document" style="width: 750px;height:750px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑货物所在地</h4>
				</div>
				<div class="modal-body">
				<form  id="dataForm">
					<input type="hidden" class="form-control" id="pk" />
					<div class="form-group col-ms-12" style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">货物所在地：</label>
						<div class="col-sm-10" style="margin-left:-15px;">
							<div class="col-sm-3" >
								<select class="form-control col-sm-3" id="provincePk" name="provincePk" onchange="changeCity(this);" >
								<option value="">--省--</option>
							 	<c:forEach var="c" items="${province}">
										<option value="${c.pk }">${c.name}</option>
									</c:forEach>
								</select>	
							</div>
							<div class="col-sm-3">
								<select class="form-control col-sm-3 " id="cityPk" name="cityPk" onchange="changeArea(this);">
								<option value="">--市--</option></select>
							</div>
							<div class="col-sm-3">
								<select class="form-control col-sm-3" id="areaPk" name="areaPk" >
								<option value="">--区--</option></select>
							</div>
						</div>
					</div>	
					
					<div class="form-group col-ms-12" style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">预计送达时间：</label>
						<div class="col-sm-10" style = "right: 412px;">
								<input class="form-controlgoods input-append date form-datehour" type="text" name="arrivalTime"  id = "arrivalTime"  value="" required="true" style = "width: 150px;" />					
						</div>
					</div>	
					<div class="form-group col-ms-12"	style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">备注：</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="meno" id="meno" value="" style="height: 60px;"  maxlength="100"></textarea>
							</div>
					</div> 
				</form>
				</div>

				<div class="modal-footer" >
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!---------------------- END -->
	
	<script type="text/javascript">
	$(".form-datehour").datetimepicker({
	      format: "yyyy-mm-dd hh:ii",
	      forceParse: false,
	      startView:'day',
	      minView:'hour',
	      language:'zh-CN',
	      pickerPosition:"bottom-right"
	    });
	
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	SearchClean();  
	       }  
	} 
	  $(window).on('load', function () {  
          $('.selectpicker').selectpicker({  
              'selectedText': 'cat'  
          });  

          // $('.selectpicker').selectpicker('hide');  
      }); 
	  

	 </script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>