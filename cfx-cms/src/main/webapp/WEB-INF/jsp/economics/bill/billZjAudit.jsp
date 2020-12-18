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
<script src="./pageJs/economics/bill/billAudit.js"></script>
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
@media (maxi-width:1366px){
	input.responsive{
		width:100px!important;
	}
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">票据管理</a></li>
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
					<input type="hidden" id="econCustmerPk" value="${economicsCustDto.pk }"/>
					<input type="hidden" id="taskId" value="${taskId}"/>
					<input type="hidden" id="processInstanceId" value="${economicsCustDto.processInstanceId}"/>
						<!-- 固定三行 -->
						<div class="col-sm-12" style="height: 30px; display: block;">
						<div class="row">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">公司名称：</label>
							<label class="control-label" id="companyName">
							<div>
								<p>${economicsCustDto.companyName }</p>
								<p>联系人：${economicsCustDto.contacts }</p>
							</div>
							<div>
							    <p>手机号码：${economicsCustDto.contactsTel }</p>
								
								<!-- <p><a class="blueword" data-toggle="modal" data-target="#financialistId" onclick="javascript:searchScoreDetial();">明细</a></p> -->
							</div>
							</label>
							</div>
						</div>
						<div class="col-sm-12" style="height: 30px; display: block;">
							<div class="row">
								<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">申请产品：</label>
								<label class="control-label" id="companyName">
								<div>
									<p id = "goodsName">${economicsCustDto.goodsName }</p>
								</div>
								<div>
								    <p>申请时间：<fmt:formatDate value="${economicsCustDto.insertTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
								</div>
								</label>
								</div>
						</div>
						<!------ 加载申请产品 --------->
					  <c:forEach items="${economicsCustDto.cusgoodsDtos}" var="dimen" > 
							<div class="col-sm-12 responsive" style="height: 40px; display: block;margin-bottom:10px;position:relative">
							<div class="row">
								<div class="col-sm-6">
									<div class="row">
										<label for="inputEmail3" class="col-sm-4 control-label" style="text-align: right;line-height:34px;">审批产品：</label>
										<div  style="float:left;">
											<label class="control-label" style="line-height:34px;"><p>${dimen.goodsName}</p></label>
										</div>
										
										<c:if test="${dimen.goodsShotName eq 'tx'}">
											<input type="hidden" name="productName" value="${dimen.goodsName} " />
											<input type="hidden" name="billGoodsPk" value="${dimen.pk} " />
		     								<div  style="margin-left:12px;float:left">
												<p style="float: left;line-height: 34px;">建议额度：</p>
													<input type="text" class="form-control responsive"  onkeyup="format_input_num(this)"   maxlength="15" name="limit" required="true" style="float:left;width:130px" value= "<fmt:formatNumber value="${dimen.suggestAmount}" pattern="##.##"/>">
													<em style="line-height: 34px;padding-left: 4px;">w</em>
											</div>
										</c:if>
									</div>
								</div>
							</div>
							</div>
				          </c:forEach>   
						 <div class="col-sm-12" style="display: block;height:120px;">
						 <div class="row">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: right;">资料说明&nbsp;&nbsp;</label>
							<label class="col-sm-10 control-label" style="padding:0;width:500px;">
								<textarea style="resize: none;height:100px;" class="form-control" id="remarks" name="remarks" contenteditable="plaintext-only" maxlength="40"></textarea>
							</label>
							</div>
						</div>
						<div class="col-sm-12" style="display: block;text-align:center;margin-bottom:30px;height:40px;line-height:40px;">
						   <button type="button" class="btn btn-primary" onclick="submitAudit(1);">通过</button>
						   <button type="button" class="btn btn-primary" onclick="submitAudit(2);">驳回</button>
						</div>
						</form>
                    </section>
				</div>
					
					</form>
                    </section>
				</div>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading">
                            <ul class="nav nav-tabs">
                                <li style="line-height: 34px;background: #eee; width: 100%;padding-left: 10px;"> 审批明细</li>
                            </ul>
                        </header>
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <table id="economicsCustId" style="width: 100%"></table>
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
	
	 function format_input_num(obj){
		 if(obj.value.length==1){
	            obj.value=obj.value.replace(/[^0-9]/g,'')
	        }else{
	            obj.value=obj.value.replace(/\D/g,'')
	        }
	    }
</script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>