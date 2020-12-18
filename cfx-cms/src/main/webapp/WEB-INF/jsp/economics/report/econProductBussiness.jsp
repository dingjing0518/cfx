<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>供应商金融产品交易</title>
<c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
</c:import>
<script src="./pageJs/economics/report/econProductBussiness.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">数据管理>供应商金融产品交易</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading">
			           <label class="col-lg-3 col-lg-2"><span>年份：</span> 
						<input name="years" id="years" class="form-controlgoods input-append date form-dateyear" type="text" value="${years }" readonly> </label>
						<label class="col-sm-6 col-md-4 col-lg-3"><span>银行名称：</span>
							<select class="form-controlgoods input-append" id="selectBank" style="width: 180px;">
								<c:forEach items="${bankList}" var="banks">
									<option value="${banks.pk}" ${banks.pk == bankPk?'selected':''}>${banks.bankName}</option>
								</c:forEach>
							</select></label>

						<button class="btn btn-primary" onclick="SearchClean(1)"> 搜索</button>
			         <button showname="EM_REPORT_PROBUSS_BTN_EXPORT" style="display:none;"
					class="btn btn-primary"
					onclick="downloadRF();" data-toggle="dropdown">导出</button>
					<input type="hidden" value="${bankPk }" name="bankPk" id="bankPk" />
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
                                 <table id="econProductBussiness" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>