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
<script src="./pageJs/economics/report/priceIndex.js"></script>
</head>
<body class="sticky-header">
<style>
</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li>数据管理</li>
			<li class="active">基础价格指数维护</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<label class="col-lg-2 col-lg-3"><span>品名：</span> <input class="form-controlgoods input-append " type="text" name="productName"   value="" /></label>
						<label class="col-lg-2 col-lg-3"><span>维护日期：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime"   value=""  readonly /></label>
	                    <label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"   value="" readonly /></label>
					 	<button class="btn btn-primary" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-warning" onclick="searchTabs(2)"> 清除</button>
			      <a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="EM_REPORT_PRICE_INDEX_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal"
						onclick="javascript:editGoods('',''); ">新增</button>
				</a>
					<button class="btn btn-warning" onclick="backToPriceIndex()"> 返回成交价格指数</button>
					<input type="hidden" name="isVisable" id="isVisable"/>
             	</header>
				<div class="panel-body">
					<section class="panel">
						<header class="panel-heading custom-tab ">
							<ul class="nav nav-tabs">
								<li class="active">
									<a href="#all" data-toggle="tab" onclick="findStatus(0);">全部</a>
								</li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="findStatus(1);">启用</a>
								</li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="findStatus(2);">禁用</a>
								</li>
							</ul>
						</header>
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
                                <table id="priceIndexId" style="width: 100%"></table>
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
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">产品类型</label>
							<div class="col-sm-10">
								<select class="form-control" name="productPk" id="productPk" required="true">
									<option value="">--请选择--</option>
									<c:forEach items="${productList}" var="product">
									<option value="${product.pk}">${product.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">价格指数</label>
							<div class="col-sm-10">
								<input class="form-control" type="text" onkeyup="format_input_nPlus(this,8)" maxlength="8" name="priceIndex" id="priceIndex" required="true"/>
							</div>
						</div>
					</form>
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
	<script type="text/javascript">
        //输入数字为正整数,和零
        function format_input_nPlus(obj,num) {

            //obj.value = obj.value.replace(/^[0]+[1-9]*$/gi, '')

            obj.value=obj.value.replace(/\D/g,'')

        }

	</script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>