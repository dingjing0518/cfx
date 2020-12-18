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
<script src="./pageJs/operation/opermg/futures.js"></script>
<style>
.btn-group.bootstrap-select.bla.bli {
    position: relative;
}
.btn-group.bootstrap-select.bla.bli .help-block span {
    position: absolute;
    top: 7px;
    margin-top: 0px!important;
    left: 110px;
}
@media (max-width:1520px){
label.col-lg-3.col-lg-2{width:16%;}

}
@media (max-width:1430px){
label.col-lg-3.col-lg-2{width:16%;}
.form-controlgoods{width:90px;}
}
@media (max-width:1352px){
.form-controlgoods{width:80px;}
.bootstrap-select > .dropdown-toggle{width:100px;}
}
.dropdown-menu.open.none {
	display: none;
}
</style>

<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>运营管理>首页显示管理></li>
			<li class="active">期货管理</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
						<header class="panel-heading" style="margin-bottom:10px;">
			            <label class="col-lg-3 col-lg-2"><span>期货品种：</span> 
			            <select name="futuresPk" class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">全部</option>
					            	<c:forEach items="${futuresTypeList}" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
			            </label>	
			           <label class="col-lg-3 col-lg-2"><span>时间（收盘）：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="closingStartTime"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="closingEndTime"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>修改时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"   value="" readonly /></label>
			            <input type = "hidden" id = "block"  name="block" value="${modelType}"/>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
		             	<button  style="display:none;" showname="OPER_MG_HOMESHOW_FUTURES_BTN_ADD" id="editable-sample_new"   class="btn btn-primary"
							data-toggle="modal" onclick="javascript:addFutures('');">
							新增
						</button>
						<button  style="display:none;" showname="OPER_MG_HOMESHOW_FUTURES_BTN_VARI" id="editable-sample_new" class="btn btn-primary"
							data-toggle="modal" onclick="javascript:returnFuturesType('');">
							品种管理
						</button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                  <table id="futuresId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
		<div class="modal fade" id="editModel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">新增</h4>
				</div>

				<div class="modal-body">
			<form id="dateForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" value =""/>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">期货品种：</label>
							<div  style="float: left;margin-left: 15px;">
							<select  id="futuresType" name="futuresType" class="selectpicker bla bla bli"  data-live-search="true" required="true" style="float:left;" >
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${futuresTypeList}" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
			   				</div>
						</div>
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">单价：</label>
							<div class="col-sm-5" style="width:233px;">
								<input type="text" class="form-control" id="price" placeholder="当前期货品种收盘价"  name="price" required="true" />
							</div>
						</div>
						
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-3 control-label"
								style="text-align: left;">时间（收盘）：</label>
							<div class="col-sm-5">
								<input class="form-control input-append date form-dateday" type="text" name="date" id="date"  required="true"  value="" readonly />
							</div>
						</div>
	                 
						</form>
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

    <script src="./pageJs/include/form-dateday.js"></script>
</body>

</html>