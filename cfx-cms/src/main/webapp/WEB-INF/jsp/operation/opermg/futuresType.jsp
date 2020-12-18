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
<script src="./pageJs/operation/opermg/futuresType.js"></script>
<style>

</style>

<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a  href="#"  onclick="javascript:openFuturesPage('');">运营管理>首页显示管理>期货管理</a></li>
			<li class="active">品种管理</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
						<header class="panel-heading" style="margin-bottom:10px;">
			            <label class="col-lg-3 col-lg-2"><span>期货品种：</span> 
			           		<input type="text"  name="name" class="form-controlgoods" aria-controls="dynamic-table"  onkeydown="entersearch()">
			            </label>	
			           <label class="col-lg-3 col-lg-2"><span>修改时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"   value="" readonly /></label>
			           <label class="col-lg-3 col-lg-2"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"   value="" readonly /></label>
			           <input type = "hidden" id = "block"  name="block" value="${modelType}"/>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
		             	<button id="editable-sample_new" style="display:none;" showname="OPER_MG_HOMESHOW_FUTURES_VARI_BTN_ADD"  class="btn btn-primary"
							data-toggle="modal" onclick="javascript:addFuturesType('');">
							新增
						</button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                  <table id="futuresTypeId" style="width: 100%"></table>
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
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">期货品种：</label>
							<div class="col-sm-5" style="width:233px;">
								<input type="text" class="form-control" id="name"  name="name" placeholder="请输入期货品种名称" required="true" maxlength="100"/>
							</div>
						</div>
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">首页显示</label> 
							<div class="col-sm-5">	
								<label>显示<input name="isHome" type="radio" value="1" checked='checked' style="margin-left:3px;" /></label>
								<label>不显示<input name="isHome" type="radio" value="2" style="margin-left:3px;"/></label>
						    </div>
						</div>
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;" id = "currencyUnitDiv">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">货币单位：</label>
							<div class="col-sm-5" style="width:233px;">
								<input type="text" class="form-control" id="currencyUnit"  name="currencyUnit"  maxlength="10"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;" id = "unitDiv">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">单位：</label>
							<div class="col-sm-5" style="width:233px;">
								<input type="text" class="form-control" id="unit"  name="unit"  maxlength="10"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">排序：</label>
							<div class="col-sm-5" style="width:233px;">
								<input type="text" class="form-control" id="sort"  name="sort" placeholder="数字越大越靠前" required="true" />
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