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
<style type="">
.bootstrap-select{
float:left!important;
}
</style>
<!-- 页面内容导航开始-->
<script src="./pageJs/economics/bill/billSupplierAccount.js"></script>
</head>
<body class="sticky-header">
<style>
	.ui-autocomplete {
	    max-height: 200px;
	    max-width: 342px;
	    overflow-y: auto;
	    background-color:#FFFFFF;
	    /* 防止水平滚动条 */
	    overflow-x: hidden;
  }
	</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>票据管理/客户管理/供应商管理</li>
			<li class="active">账户</li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header id = "header" class="panel-heading" > 
					     <input type="hidden" id="companyPk" name="companyPk"  value ="${companyPk}"/>
			              <button style="display:none;" showname="EM_BILL_SUPPSIGNING_ACCOUNT_BTN_ADD" class="btn btn-primary" id="btn" onclick="addBank()"> 新增账户</button>
				</header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <table id="supplierBankId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade" id='supplierBankModel' tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document" style="width: 1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">实体账户编辑</h4>
				</div>
				<div class="modal-body">
				<form id="creditDataForm">
					<input type="hidden" id="pk" name="pk" />
					<input type="hidden" id="bankclscode" name="bankclscode" />
 					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">公司名称</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="companyPk" name="companyPk" value = "${companyName}"style="width: 342px;" readonly="readonly"/>
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label" 
							style="text-align: left;">银行名称</label>
							<div class="col-sm-9">
						 <select   name="ecbankPk"  id = "ecbankPk" style="float:left;" class="selectpicker bla bla bli"   data-live-search="true">
					   		<option value="">--请选择--</option>
					   		<c:forEach items="${bankList}" var="list">
					   			<option value="${list.pk}">${list.bankName}</option>
					   		</c:forEach>
					   </select>
					   </div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">开户行名称</label>
						<div class="col-sm-9">
						<input type="text" class="form-control" id="creditBankName" name="creditBankName" style="width: 342px;" onblur="isCreditBankName();" required="true" />	<!--  -->
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">开户行行号</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="creditBankNo" name="creditBankNo" style="width: 342px;" required="true" readonly="readonly"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-3 control-label"
							style="text-align: left;">银行卡号</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="creditBankAccount" name="creditBankAccount" style="width: 342px;" required="true"/>
						</div>
					</div>
				</form>
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button"  id="buttonIsVisableId" class="btn btn-primary" onclick="creditSubmit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<script>
	
	$("#creditBankName").autocomplete({  
		 minLength: 4,
       source: function( request, response ) {  
           $.ajax({  
                url :  getRootPath() + "/getSysBankNameCode.do",  
                type : "post",  
                dataType : "json",  
                data : {creditBankName:$("#creditBankName").val()},  
               success: function( data ) { 
                     response( $.map( data, function( item ) {  
                           return {  
                             label: item.bankname,  
                             value: item.bankno  
                           }  
                     }));  
               }  
          });  
       }, 
       focus: function( event, ui ) {  
           $("#creditBankName").val( ui.item.label );  
           $("#creditBankNo").val( ui.item.value );  
             return false;  
           },
       select: function( event, ui ) {
           $("#creditBankName").val( ui.item.label );  
           $("#creditBankNo").val( ui.item.value );  
           return false;  
       }  
    });  
	</script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>