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
<script src="./pageJs/yarn/opermg/yarnPriceTrend.js"></script>
<style>
.modal-backdrop.in{z-index:100;}
.modal{z-index:1000;}
.bootstrap-select > .dropdown-toggle{float:none;}
.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn){float:left;}
label span{width:90px;}
.form-controlgoods{float:left;}
.open .dropdown-menu.open {z-index: 100001!important;}
@media screen and (max-width: 1990px){
	.bootstrap-select > .dropdown-toggle {width: 104px;}
}
@media (max-width:1440px){
.form-controlgoods{width:90px!important;}
.bootstrap-select > .dropdown-toggle{width:90px;}
}
@media (max-width:1360px){
.form-controlgoods{width:80px!important;}
.bootstrap-select > .dropdown-toggle{width:80px;}
}
.panel-heading .bootstrap-select .dropdown-toggle:focus {
    outline: none!important;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营管理</a></li>
			<li class="active">首页显示管理</li>
			<li class="active">>价格趋势</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
				  <label class="col-lg-2 col-lg-3"><span>工艺：</span>
				            <select  name="technologyName" class="selectpicker bla bla bli"   data-live-search="true">
								   <option value="">--请选择--</option>
									<c:forEach items="${technologyList }" var="m">
									<option value="${m.pk}">${m.name }</option>
									</c:forEach>  
							 </select>  	
					    </label>
					    
				<label class="col-lg-2 col-lg-3"><span>原料一级：</span>
 
						    <select id="firstMaterialName" name="firstMaterialName"  class="selectpicker bla bla bli" data-live-search="true" onchange="changeFirstMaterial(this.value)">
 
							   <option value="">--请选择--</option>
								<c:forEach items="${firstMaterialList }" var="m">
								<option value="${m.pk}">${m.name }</option>
							
								</c:forEach>  
							 </select>  	
					    </label>
				<label class="col-lg-2 col-lg-3"><span>原料二级：</span>

						 <select id="secondMaterialName" name="secondMaterialName"  class="selectpicker bla bla bli" data-live-search="true">

							 <option value="">--请选择--</option>
						 </select>
					 </label>
				<label class="col-lg-2 col-lg-3"><span>厂区品牌：</span>
		                	<select id="brandName" name="brandName" class="selectpicker bla bla bli"   data-live-search="true" onchange="changeSpec()">
								   <option value="">--请选择--</option>
									<c:forEach items="${goodsBrandList }" var="m">
									<option value="${m.brandName}">${m.brandName}</option>
									</c:forEach>  
							</select>  	
		                 </label>
			   			<label class="col-lg-2 col-lg-3"><span>平台更新时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="platformUpdateTimeStart" id="platformUpdateTimeStart"  value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span style="width:40px;">至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="platformUpdateTimeEnd"  id="platformUpdateTimeEnd"  value="" readonly /></label>
			            
			            <label class="col-lg-2 col-lg-3"><span>修改时间：</span> <input style="width:104px;position: relative;z-index: 10;" class="form-controlgoods input-append date form-dateday" type="text" name="updateTimeStart" id="updateTimeStart"  value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span >至</span> <input style="width:104px;position: relative;z-index: 10;" class="form-controlgoods input-append date form-dateday" type="text" name="updateTimeEnd"  id="updateTimeEnd"  value="" readonly /></label>
			           	
			           	<label class="col-lg-2 col-lg-3"><span>日期：</span> <input style="width:104px;position: relative;z-index: 10;" class="form-controlgoods input-append date form-dateday" type="text" name="dateStart" id="dateStart"  value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3"><span>至</span> <input style="width:104px;position: relative;z-index: 10;" class="form-controlgoods input-append date form-dateday" type="text" name="dateEnd"  id="dateEnd"  value="" readonly /></label>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button  style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_BTN_EDITGOODS" class="btn btn-primary" id="btn" onclick="editGoods();">编辑商品</button>
			             <button  style="display:none;" showname="YARN_MG_HOMESHOW_PRICE_BTN_EXPORT" class="btn btn-primary" id="btn" onclick="exportPrice();">导出</button>
		              <input type="hidden" name="isShowHome"/>
					  <input type="hidden" name="block" id="block" value="sx"/>
		             </header>
		             <!--  -->
				<div class="panel-body">
					<section class="panel">
					 <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findTablet('');">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findTablet(1);">显示</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findTablet(2);">不显示</a>
                                </li>
                               
                            </ul>
                        </header>
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
                                <table id="priceTrendTable" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
		
	<div class="modal fade" id="priceTrendHistory" tabindex="-1" role="dialog"
		aria-labelledby="modelLabel">
		<div class="modal-dialog" role="document"
			style="width: 80%; height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modelLabel">历史数据</h4>
				</div>
				<header class="panel-heading" style="margin-bottom:10px;">
				<label class="col-lg-3 col-lg-3"><span>日期：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="dateHistoryStart"   value="" readonly /></label>
			           	<label class="col-lg-3 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="dateHistoryEnd"  value="" readonly /></label>
			           <label class="col-lg-3 col-lg-3"><span>是否显示：</span> 
	                    <select name="isHome" class="selectpicker bla bla bli"   data-live-search="true">
								   <option value="">--请选择--</option> 
								   <option value="1">显示</option> 
								   <option value="2">不显示</option> 
					    </select>  	 
	                    </label>
			            <button class="btn btn-primary" id="btn" onclick="SearchCleanHistory(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchCleanHistory(2);"> 清除</button>
				<input type="hidden" id="movementPk" name="movementPk"/>
				</header>
				<div class="modal-body">
					<table id="childs" style="width: 100%"></table>
				</div>

			</div>
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
					<input type="hidden" id="pk"/>
					<input type="hidden" id="goodsPk"/>
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
							<input type="text" class="form-control" id="price" name="price" style="width: 342px;" required="true" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">日期</label>
						<div class="col-sm-9">
						    <input type="text" class="form-controlgoods input-append date form-dateday" style="width: 342px;float: left;" id="date" name="date" readonly="readonly" required="true"/> 
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">排序</label>
						<div class="col-sm-9">
						    <input type="text" class="form-controlgoods input-append" style="width: 342px;float: left;" id="sort" name="sort"/> 
						</div>
					</div>
					
					<table id="creditBankId" style="width: 100%"></table>
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
	
	<div class="modal fade" id="editPriceTrendHistory" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document" style="width: 1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myPriceTrendHistoryModalLabel">编辑</h4>
					<input type="hidden" id="goodsPkHistory"/>
					<input type="hidden" id="isShow"/>
					<input type="hidden" id="isInsertHistory"/>
					<input type="hidden" id="movementPkHistory"/>
				</div>
				<div class="modal-body">
				<form   id="dataPriceTrendHistoryForm">
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">商品信息</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="goodsInfoHistory" name="goodsInfoHistory" style="width: 342px;" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">上架价格</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="tonPriceHistory" name="tonPriceHistory" style="width: 342px;" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">对应价格</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="priceHistory" name="priceHistory" style="width: 342px;" required="true"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">日期</label>
						<div class="col-sm-9">
						<input id="dateHistory" name="dateHistory" style="width: 100px!important;float: none;margin-left:0px" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
						</div>
					</div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiaoHistory" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submitHistory();">保存</button>
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