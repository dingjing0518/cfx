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
<script src="./pageJs/yarn/opermg/editYarnPriceTrendGoods.js"></script>
<style>
.modal-backdrop.in{z-index:100;}
.modal{z-index:1000;}
.bootstrap-select > .dropdown-toggle{float:none;}
@media (max-width:1500px){
.panel-heading label.col-lg-3.col-lg-4 {
    width: 30%;
}
}
@media (max-width:1280px){
.panel-heading label.col-lg-3.col-lg-4 {
    width: 40%;
}
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
		<li><a href="#">运营管理</a></li>
			<li class="active">首页显示管理</li>
			<li class="active">><a  href="#"  onclick="openFutures();">价格趋势</a></li>
			<li class="active">>编辑商品</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel" style="width:48%;margin-right:2%;float:left;">
				 <header class="panel-heading" style="margin-bottom:10px;">
				 <label class="col-lg-3 col-lg-4" style="padding:0px 7px;"><span>商品信息</span> <input class="form-controlgoods input-append " type="text" name="b2bGoodsInfo" id="meno"  value=""/></label>
			   		<button class="btn btn-primary" id="btn" onclick="SearchCleanGoods(1);"> 搜索</button>
			      <button class="btn btn-primary" id="btn" onclick="SearchCleanGoods(2);"> 清除</button>
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
                                <table id="addGoodsInfo" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
				
				<section class="panel" style="float:left;width:50%;">
				 <header class="panel-heading" style="margin-bottom:10px;">
				 <label class="col-lg-3 col-lg-4" style="padding:0px 7px;"><span>商品信息</span> <input class="form-controlgoods input-append " type="text" name="goodsInfo" id="goodsInfo"  value=""/></label>	
			     <button class="btn btn-primary" id="btn" onclick="SearchCleanPrice(1);"> 搜索</button>
			     <button class="btn btn-primary" id="btn" onclick="SearchCleanPrice(2);"> 清除</button>
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
                                <table id="priceTrendGoodsInfo" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				
				</section>
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
/**
 * 跳转到价格趋势列表
 */
function  openFutures(){
    window.location = getRootPath() + "/priceTrend.do?modelType=2";
}
</script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>