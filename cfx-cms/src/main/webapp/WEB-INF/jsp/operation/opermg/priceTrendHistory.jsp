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
<script src="./pageJs/operation/opermg/priceTrendHistory.js"></script>
<style>
.modal-backdrop.in{z-index:100;}
.modal{z-index:1000;}
.bootstrap-select > .dropdown-toggle{float:none;}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营管理</a></li>
			<li class="active">>首页显示管理</li>
			<li class="active">>价格趋势历史数据</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">	
			   			<label class="col-lg-2 col-lg-3"><span>日期：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="dateHistoryStart" id="dateHistoryStart"  value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="dateHistoryEnd"  id="dateHistoryEnd"  value="" readonly /></label>
		            	<label class="col-lg-2 col-lg-3"><span>是否显示：</span> 
	                    <select name="isHome" class="selectpicker bla bla bli"   data-live-search="true">
								   <option value="">--请选择--</option> 
								   <option value="1">显示</option> 
								   <option value="2">不显示</option> 
					    </select>  	 
	                    </label>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button  style="display:none;" showname="OPER_MG_HOMESHOW_PRICEHIST_BTN_EXPORT" class="btn btn-primary" id="btn" onclick="exportHistory();">导出</button>
		             </header>
		             <!--  -->
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
                                <table id="priceTrendHistoryTable" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
		
		<div class="modal fade" id="editPriceTrend" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document" style="width: 1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑</h4>
					<input type="hidden" id="goodsPk"/>
					<input type="hidden" id="movementPk"/>
					<input type="hidden" id="isInsert"/>
					<input type="hidden" id="isShow"/>
					
				</div>
				<div class="modal-body">
				<form   id="dataForm">
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">商品信息</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="goodsInfo" name="goodsInfo" style="width: 342px;" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">上架价格</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="tonPrice" name="tonPrice" style="width: 342px;" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">对应价格</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="price" name="price" style="width: 342px;" required="true"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">日期</label>
						<div class="col-sm-9">
							<input id="date" name="date"  class="form-controlgoods input-append date form-dateday" type="text"  style="width: 342px;float: left;"   value="" readonly>
						</div>
					</div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
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