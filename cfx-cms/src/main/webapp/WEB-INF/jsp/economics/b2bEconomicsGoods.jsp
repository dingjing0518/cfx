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
<script src="./pageJs/economics/b2bEconomicsGoods.js"></script>
</head>
<body class="sticky-header">
<style>
	.storecheck{
	    width: 7px;
	    height: 20px;
	    background: #333;
	    background-color: rgba(0,0,0,0.7);
	       bottom: -5px;
  		margin-left: 5px;
	}
	.deleteStore{
		font-size: 12px;
	    line-height: 0px;
	    margin-top: 10px;
	    margin-left: -4px;
	    color: #fff;
	}
</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">产品管理</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<label class="col-lg-2 col-lg-3"><span>产品名称：</span> <input class="form-controlgoods input-append " type="text" name="name"   value="" onkeydown="entersearch()"/></label>						
						<label class="col-lg-2 col-lg-3"><span>处理时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"   value="" onkeydown="entersearch()"  readonly /></label>
	                    <label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"   value="" readonly /></label>
		                <label class="col-lg-2 col-lg-3"><span>处理状态：</span>
						 <select class="selectpicker bla bla bli" name="status" style="width: 200px;" >
							<option value="">--请选择--</option>
							<option value="1">启用</option>
						  	<option value="2">禁用</option>
						 </select>
					 	</label>	
					 	<button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
			      <a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="EM_PRO_BTN_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal"
						onclick="javascript:editGoods('',''); ">新增产品</button>
				</a> 
             	</header>
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
                                <table id="ecGoodsId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
<!-- ===================================== -->
<!-- 新建分类model -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑产品</h4>
				</div>
				
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="pk" name="pk" />
						<input type="hidden" id="storeInfoPks"/>	
						<input type="hidden" id="storeInfoName"/>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">产品类型</label>
							<div class="col-sm-10">
								<select class="form-control" name="goodsType" id="goodsType" required="true">
									<option value="">--请选择--</option>
									<option value="1">化纤白条</option>
									<option value="2">化纤贷</option>
									<option value="3">白条委托贷款</option>
									<option value="4">化纤贷委托贷款</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">产品名称</label>
							<div class="col-sm-10">
								<input class="form-control" type="text" name="name" id="name" required="true"/>
							</div>
						</div>
						<div class="form-group"style="margin-top: 6px;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="width: auto;left: -5px;">化纤支付比例</label> 
							<div class="col-sm-5">	
							<label>1<input name="proportion" type="radio" value="1.0" checked='checked' style="margin-left:3px;" /></label>
								<label>0.7<input name="proportion" type="radio" value="0.7"  style="margin-left:3px;" /></label>
								<label>0.8<input name="proportion" type="radio" value="0.8" style="margin-left:3px;"/></label>
						    </div>
						</div>
					</form>
				
				<div class="form-group"style="height: 30px; display: block;" id = "storeDiv">
					<!-- <label  class="col-lg-2 col-lg-3" style="width: auto;"><span>化纤支付比例</span>
					<div class=" col-sm-2 storecheck">
						<p class="deleteStore" onclick="deletePicture();">X</p>
					</div></label>  -->
				</div>
					<div class="col-sm-12">
						<label ><span>客户名称</span>
					 	<input placeholder = "请输入供应商店铺名称" style = "margin-left: 30px;" type="text" autocomplete="off" name="storeName" id = "storeName" value="" />
					 	</label> 
						<button class="btn btn-primary" id="btn" onclick="searchTabsName(1)"> 搜索</button>
					</div>
					<table id="stores" style="width: 100%"></table>
					<!-- 结束 -->
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
	    	searchTabs();  
	       }  
	} 	
	
    </script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>