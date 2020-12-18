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
<script src="./pageJs/economics/report/transactionPriceIndex.js"></script>
<style>
	@media (max-width:1600px){
		label.col-lg-2.col-lg-3.resposive {
		    margin-right: 210px;
		}
	}
</style>
</head>
<body class="sticky-header">
<style>
</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li>数据管理</li>
			<li class="active">成交价格指数</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						<label class="col-lg-2 col-lg-3"><span>品名：</span> <input class="form-controlgoods input-append " type="text" name="goodsInfo"   value="" /></label>
						<label class="col-lg-2 col-lg-3"><span>采购商名称：</span> <input class="form-controlgoods input-append " type="text" name="purchaserName"   value="" /></label>
						<label class="col-lg-2 col-lg-3"><span>到期时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="repaymentTimeStart"   value=""  readonly /></label>
	                    <label class="col-lg-2 col-lg-3"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="repaymentTimeEnd"   value="" readonly /></label>
						<label class="col-lg-2 col-lg-3 resposive"><span>借据编号：</span> <input class="form-controlgoods input-append " type="text" name="loanNumber"   value="" /></label>
						<button class="btn btn-primary" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-warning" onclick="searchTabs(2)"> 清除</button>
			      <a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="EM_REPORT_TRANS_PRICE_INDEX_BTN_EXPORT" class="btn btn-primary"
						data-toggle="modal"
						onclick="javascript:exportPriceProduct(); ">导出</button>
				</a>
				<button class="btn btn-warning" onclick="backToBasePrice()">基础价格维护</button>
					<input type="hidden" name="isConfirm" id="isConfirm"/>
             	</header>
				<div class="panel-body">
					<section class="panel">
						<header class="panel-heading custom-tab ">
							<ul class="nav nav-tabs">
								<li class="active">
									<a href="#all" data-toggle="tab" onclick="findStatus(0);">全部</a>
								</li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="findStatus(2);">已确认</a>
								</li>
								<li class="">
									<a href="#off-shelve" data-toggle="tab" onclick="findStatus(1);">未确认</a>
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
					<h4 class="modal-title" id="myModalLabel">确认</h4>
				</div>
				
				<div class="modal-body">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataForm">
						<input type="hidden" id="id" name="id" />
						<input type="hidden" id="dueofpayMountHidden" name="dueofpayMountHidden" />

						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">温馨提示：</label>
							<div class="col-sm-10 control-label" style = "padding-right: 196px">
								是否确认该笔借款单对应的商品补交保证金！
							</div>
						</div>
						
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">本次缴纳保证金金额：</label>
							<div class="col-sm-10 control-label">
								<input class="form-control" type="text" maxlength="8" name="dueofpayMount" id="dueofpayMount" required="true"/>
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