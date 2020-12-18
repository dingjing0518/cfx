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
<script src="./pageJs/dimension/buildProperty.js"></script>
<style>
#dtblock1 .help-block {
	margin-right: 585px;
	position: absolute;
	left: 650px;
	top: 45px;
}

#dtblock2 .help-block {
	position: absolute;
	left: 650px;
	top: 45px;
}

#dtblock3 .help-block {
	position: absolute;
	left: 750px;
	top: 19/0px;
}

.bootstrap-select>.dropdown-toggle {
	width: 180px;
}
.bootstrap-select
:not
 
(
[
class
*=
"col-"
]
 
)
:not
 
(
[
class
*=
"form-control"
]
 
)
:not

	
(
.input-group-btn
 
){
float
:
 
left
;


}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>金融中心 /</li>
			<li>维度管理 /</li>
			<li>授信管理 /</li>
			<li class="active">房地产抵押金额</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading"
					style="margin-bottom:10px;"> </header>
				<form id="dataForm">
					<div class="panel-body" id="unseen">
						<input type="hidden" id="pk" name="pk" value="${economics.pk}" />
						<table class="table table-bordered table-striped table-condensed">
							<tbody>
								<tr style="position: relative">
									<td class="col-sm-1" style="text-align: right;">房地产抵押金额:</td>
									<td class="col-sm-8" style="position: relative;">
										<div id="dtblock1">
											<input type="text" class="form-control"
												style="width: 180px; float: left;" name="minMortgage"
												id="minMortgage" value="${economics.minMortgage}"
												required="true" />
										</div>
										  <div style="width: 30px;display: inline-block;margin-left: 10px;">w</div> 
										<span style="margin-left: -14px;"> &lt;房地产抵押金额&le;</span>
										<div id="dtblock2">
											<input type="text" class="form-control"
												style="width: 180px; margin-top: -30px; margin-left: 340px;"
												name="maxMortgage" id="maxMortgage"
												value="${economics.maxMortgage}" required="true" />
												<div style="width: 30px;display: inline-block;margin-left: 530px;margin-top: -34px;">w</div>
												
										</div>
									</td>
								</tr>
								<tr style="position: relative">
									<td class="col-sm-1" style="text-align: right;">房地产抵押金额/授信总金额:</td>
									<td class="col-sm-8" style="position: relative;"><div
											id="dtblock1">
											<input type="text" class="form-control"
												style="width: 180px; float: left;" name="minTotalMortgage"
												id="minTotalMortgage" value="${economics.minTotalMortgage}"
												required="true" />
												
										</div>
										 <div style="width: 30px;display: inline-block;margin-left: 10px;">%</div>  
										<span style="margin-left: -14px;">
											&lt;房地产抵押金额/授信总金额&le;</span>
										<div id="dtblock2">
											<input type="text" class="form-control"
												style="width: 180px; margin-top: -30px; margin-left: 420px;"
												name="maxTotalMortgage" id="maxTotalMortgage"
												value="${economics.maxTotalMortgage}" required="true" />
												<div style="width: 30px;display: inline-block;margin-left: 610px;margin-top: -34px;">%</div>
										</div></td>
								</tr>
								<tr id="dtblock3">
									<td class="col-sm-1" style="text-align: right;">得分:</td>
									<td class="col-sm-5"><input type="text"
										class="form-control" style="width: 40%;" name="score"
										id="score" value="${economics.score}" maxlength="8"
										required="true" /></td>
								</tr>
							</tbody>
						</table>
					</div>


				</form>
				<div class="btn-tags">
					<div class="pull-right tag-social" style="padding-right: 10px;">
						<!--    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							onclick="submit(1)">保存</button>
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>

</body>
</html>