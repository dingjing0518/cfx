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
<script src="./pageJs/economics/economicsCustomerAudit.js"></script>
<style>
.modal-backdrop.in{z-index:100;}
.modal{z-index:1000;}
.bootstrap-select > .dropdown-toggle{float:none;}
.col-sm-2 {
    padding-left: 15px;
    padding-right: 15px;
}
.control-label p{display:inline-block;padding-right:10px;}
.control-label div{display:inline-block;padding-right:20px;position:relative;}
.control-label .blueword{color:#0d78e3;}
.control-label em{position: absolute;top: 0px;left: 260px;font-weight:normal;}
.form-controlgoods{float:left;width:130px;}
@media (max-width:1600px){
	.responsive{
		height:auto;
	}
	.responsive .control-label p{
		width: 160px;
		line-height: 20px;
		margin-bottom: 10px;
		padding-top: 7px;
	}
	.responsive input.responsive{
		height: 34px;
	}
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">金融中心</a></li>
			<li class="active">审批管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<div class="panel-body">
					<section class="panel">
					<form id="" style="margin-top:15px;">
						<!-- 固定三行 -->
						<div class="col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">公司名称：</label>
							<label class="control-label" id="companyName">
							<div>
								<p>江苏德润化纤股份有限公司</p>
								<p>联系人：张先生</p>
							</div>
							<div>
							    <p>手机号码：13444445678</p>
								<p>得分：90分</p>
								<p><a class="blueword" data-toggle="modal" data-target="#financialistId" onclick="javascript:editCompay('');">明细</a></p>
							</div>
							</label>
						</div>
						<div class="col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">申请产品：</label>
							<label class="control-label" id="companyName">
							<div>
								<p>化纤白条</p>
							</div>
							<div>
							    <p>申请时间：2018.09.12 09:34</p>
							</div>
							</label>
						</div>
						<div class="col-ms-12 responsive" style="height: 30px; display: block;margin-bottom:10px;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">审批产品：</label>
							<label class="control-label" id="companyName">
							<div>
								<p>化纤贷</p>
							</div>
							<div>
							    <p>建议化纤贷额度：
									<input type="text" class="form-control" name=""  id="" required="true" style="margin-left:120px;margin-top: -25px;position:absolute;width:130px">
									<em style="">w</em>
							    </p>
							</div>
							</label>
						</div>
						<div class="col-ms-12" style="height: 30px; display: block;margin-bottom:10px;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">审批产品：</label>
							<label class="control-label" id="companyName">
							<div>
								<p>化纤白条</p>
							</div>
							<div style="margin-left:-12px;">
							    <p>建议化纤白条额度：
									<input type="text" class="form-control" name=""  id="" required="true" style="margin-left:120px;margin-top: -25px;position:absolute;width:130px">
									<em style="">w</em>
							    </p>
							</div>
							</label>
						</div>
						<div class="col-ms-12" style="height: 30px; display: block;margin-bottom:10px;">
						<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">注册时间：</label>
							<div class="col-lg-3 col-lg-2" style="padding:0;"><input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"   value="" readonly /></div>
				            <div class="col-lg-3 col-lg-2" style="padding:0;"><span style="float: left;margin-left: -60px;line-height: 34px;">至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"   value="" readonly /></div>
					    </div>
					    <div class="col-ms-12" style="display: block;height:120px;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料说明</label>
							<label class="col-sm-10 control-label" style="padding:0;width:500px;">
								<textarea style="resize: none;height:100px;" class="form-control" id="remarks" name="remarks" contenteditable="plaintext-only"></textarea>
							</label>
						</div>
						<div class="col-ms-12" style="display: block;text-align:center;margin-bottom:30px;height:40px;line-height:40px;">
						<label class="col-sm-10 control-label">
						   <button type="button" class="btn btn-primary" onclick="submitRemarks();">通过</button>
						   <button type="button" class="btn btn-primary" onclick="submitRemarks();">驳回</button>
						</label>
						</div>
					</form>
                    </section>
				</div>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li>
                                    <a href="#all" data-toggle="tab" onclick="findPurchaser(0);">审批明细</a>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <table id="purchaserId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="financialistId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width:1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">得分明细</h4>
				</div>
				<div class="modal-body">
			      <!-- kaishi -->
			         <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="changeTitles1(1);">企业经营管理</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="changeTitles2(2);">线下交易数据</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="changeTitles3(3);">线上交易数据</a>
                                </li>
                                <li class="">
                                    <a href="#shouxin" data-toggle="tab" onclick="changeTitles4(4);">授信管理</a>
                                </li>
                                <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="changeTitles5(5);">担保管理</a>
                                </li>
                                <li class="">
                                    <a href="#information" data-toggle="tab" onclick="changeTitles6(6);">补充资料</a>
                                </li>
                            </ul>
                        </header>               
                        <div class="panel-bodycommon">                       
                            <div class="tab-content">
                            <!-- 11 -->
                                <div class="tab-pane active" id="all">
                                 <div class="col-ms-12" style="height: 30px; display: block;">
									<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料文档：</label>
									<label class="control-label" id="companyName">
									<div>
									    <p>资料pdf</p>
										<p class="blueword">下载</p>
									</div>
									</label>
								</div>
								<div class="col-ms-12" style="height: 30px; display: block;">
									<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料说明：</label>
									<label class="control-label" id="companyName">
									<div>
									    <p>企业经营资料管理说明</p>
									</div>
									</label>
								</div>
                                </div>
                                
                                <!-- 22 -->
                                <div class="tab-pane" id="verify">
                              
                                </div>
                                <!-- 33 -->
                                <div class="tab-pane" id="verifydone">
                                 
                                </div>
                                <!-- 44 -->
                                <div class="tab-pane" id="shouxin">
                                
                                </div>
                                <!-- 55 -->
                                <div class="tab-pane" id="unverify">
                               
                                </div>
                                <!-- 66 -->
                                <div class="tab-pane" id="information">
                               
                                </div>
                            </div>
                        </div>
			      <!-- jieshu -->
				</div>
				<!-- <div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div> -->
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