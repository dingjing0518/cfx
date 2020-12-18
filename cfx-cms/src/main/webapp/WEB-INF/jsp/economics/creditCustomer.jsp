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
    <script src="./pageJs/economics/creditCustomer.js"></script>
     <script src="./pageJs/include/common.js"></script>
   <style>
    #dtblock1 .help-block {
	position: absolute;
    left: 200px;
    top: 30px;
	}
	#dtblock2 .help-block {
	position: absolute;
    left: 200px;
    top: 30px;
	}
	#dtblock3 .help-block {
	position: absolute;
	left: 360px;
    top: 30px;
	}
	.edit-content {
    position: relative;
}
.edit-content ul {
    padding-left: 0px;
    list-style: none;
    display:table;
    margin:0px;
}
.edit-content ul li {
    display:table-cell;
    padding: 10px 10px;
    vertical-align: middle;
    text-align:center;
    
}
.edit-content ul.left li {
    width: 190px;
    
}
.edit-content ul.left {
    position: relative;
    display: table;
    min-height:60px;
    border-top:1px solid #e5e5e5;
}
.edit-content ul.left:after{
content: "";
    height: 100%;
    position: absolute;
    top: 0px;
    width: 1px;
    background: #e5e5e5;
    right: -220px;

}
.edit-content ul.left:before{
content: "";
    height: 100%;
    position: absolute;
    top: 0px;
    width: 1px;
    background: #e5e5e5;
    right:0px;

}
.edit-content ul.right {
    position: absolute;
    width:568px;
    right: 0px;
    top: 0;
    border-top: 1px solid #e5e5e5;
    height: 100%;
    display: inline-block;
}
.edit-content ul.right li {
    width: 400px;
    padding: 10px 50px;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    -webkit-transform: translateY(-50%);
    -moz-transform: translateY(-50%);
    -ms-transform: translateY(-50%);
    -o-transform: translateY(-50%);
}
.edit-content ul.right label.control-label {
    float: left;
}
.edit-content ul.right label.control-label {
    float: left;
}
.edit-content ul.right input {
    float: left;
    width: 220px;
   margin:0px 14px;
}
.edit-content ul.top li:first-child {
    width: 200px;
    text-align: center;
    border-right: 1px solid #e5e5e5;
}
.edit-content ul.right li:first-child+li {
    right: 0px;
    width:348px;
}
.edit-content ul.right li:first-child {
    width: 220px;
    paddig:0px 10px;
}
ul.edit-top {
    padding: 0px;
    list-style: none;
    margin-bottom:0px;
}
.edit-top li {
    display: inline-block;
    height: 40px;
    line-height: 40px;
    font-weight: bold;
    text-align: center;
    width: 190px;
    border-right: 1px solid #e5e5e5;
    margin-bottom: 0px;
}
.edit-top li:first-child+li+li {
    width: 335px;
    border-right: none;
}
.edit-top li:first-child+li {
    width: 217px;
}
.edit-content ul.right li:first-child {
    width: 220px;
    padding:0px 10px;
} 
#dataForm{
*zoom:1;
}
#dataForm:after{
	display:table;
	clear:both;
	content:"";
}
.hzsj span.help-block {
    position: absolute;
    left: 220px!important;
    top: 35px!important;
}
.sxed span.help-block span {
    margin-top: 0px!important;
}
@media screen and (min-width: 1500px) and (max-width: 1600px){      
   	#creditCustomer .modal-body{
	    max-height: 500px!important;
	}
}
@media screen and (max-width: 1440px) and (min-width: 1366px){
	#creditCustomer .modal-body{
		max-height: 430px!important;
	}
}
@media screen and (min-width: 1400px) and (max-width: 1440px){      
    #creditCustomer .modal-body{
		max-height: 430px!important;
	}
}
@media screen and (max-width: 1336px) and (min-width: 1200px){
	#creditCustomer .modal-body{
		max-height: 430px!important;
	}
}
@media screen and (min-width: 1200px) and (max-width: 1336px){
	#creditCustomer .modal-body{
		max-height: 360px!important;
	}
}

	</style>
</head>
<body class="sticky-header">
<div class="page-heading">
    <ul class="breadcrumb">
        <li><a href="#">授信管理</a></li>
        <li class="active">授信额度管理</li>
    </ul>
</div>
<!-- 页面内容导航结束-->

<!--主体内容开始-->
<div class="wrapper">
    <div class="row">
        <div class="col-sm-12">
            <section class="panel">
                <header class="panel-heading" style="margin-bottom:10px;">
                    <label class="col-lg-2 col-lg-3"><span>公司名称：</span> <input class="form-controlgoods input-append " type="text" name="companyName"   value="" onkeydown="entersearch()"/></label>
                   <!-- <label class="col-lg-2 col-lg-3">
                   			<span>是否启用：</span> 
                   			<select class="selectpicker bla bla bli " name="isVisable">
                   			<option value="">--请选择--</option>
                   			<option value="1">启用</option><option value="2">禁用</option>
                   			</select>
                   		</label> -->

                    <input type="hidden" id="creditStatus" name="creditStatus" value=""/>
                    <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
                    <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
                </header>
                <div class="panel-body">
                    <section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findPurchaser(0);">全部</a>
                                </li>
                               	<li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(1);">待审核</a>
                                </li> 
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(3);">审核不通过</a>
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
                                <table id="creditCustomerId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
                </div>
            </section>
        </div>
    </div>
</div>

<div class="modal fade" id="nullId" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" style="z-index: 1050;">
</div>

<div class="modal fade" id="creditCustomer" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" style="z-index: 1050;overflow-y: hidden!important;">
    <div class="modal-dialog" role="document" style="width: 1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">授信额度编辑</h4>
            </div>
            <div class="modal-body" style="max-height:480px;overflow-y: auto;">
                <form   id="dataForm" style="display: none;background:#f4fdfa;padding:10px;margin-bottom:10px;border:1px solid #f2f2f2;position: relative;display: block;">

                    <input type="hidden" class="form-control" id="creditbankPk" name="creditbankPk"/><!--  -->
                    <input type="hidden" id="creditGoodsPk" name="creditGoodsPk" />
                     <input type="hidden" id="goodsType" name="goodsType" />
                     <input type="hidden" id="isExistBankCreditTime" name="isExistBankCreditTime" />
                     <input type="hidden" id="economicsBankCompanyPk" name="economicsBankCompanyPk" />

                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="economicsGoodsName" class="col-sm-3 control-label"
                               style="text-align: left;">金融产品</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="economicsGoodsName" name="economicsGoodsName" style="width: 342px;" required="true" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px">
                        <label for="platformAmount" class="col-sm-4 control-label"
                               style="text-align: left;">平台额度 </label>
                        <div class="col-sm-8" style="position:relative;" id="dtblock1">
                            <p> <input type="text" class="form-control" id="platformAmount" name="platformAmount" style="width: 150px; " oninput= "changeBankContractAmount(this.value)" /> </p>
                        </div>
                    </div>

                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="pledgeAmount" class="col-sm-3 control-label"
                               style="text-align: left;">银行授信额度 </label>
                        <div class="col-sm-9">
                            <p> <input type="text" class="form-control" id="bankContractAmount" name="bankContractAmount" style="width: 342px; " readonly="readonly"/> </p>
                        </div>
                    </div>
                    
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="creditStartTime" class="col-sm-4 control-label"
                               style="text-align: left;">平台授信开始时间</label>
                        <div class="col-sm-8" style="position:relative;" id="dtblock2">
                            <input type="text" class="form-controlgoods input-append date form_datetime" style="width: 158px; float:left;" id="creditStartTime" name="creditStartTime"   />
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="creditEndTime" class="col-sm-3 control-label"
                               style="text-align: left;">平台授信结束时间</label>
                        <div class="col-sm-9" style="position:relative;" id="dtblock3">
                            <input type="text" class="form-control input-append date form_datetime" style="width: 342px; " id="creditEndTime" name="creditEndTime"   />
                        </div>
                    </div>
                    
					 <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="creditStartTime" class="col-sm-4 control-label"
                               style="text-align: left;">银行授信开始时间</label>
                        <div class="col-sm-8" style="position:relative;" id="dtblock2">
                            <input type="text" class="form-control" style="width: 158px; float:left;" id="bankCreditStartTime" name="bankCreditStartTime" readonly="readonly"   />
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="creditEndTime" class="col-sm-3 control-label"
                               style="text-align: left;">银行授信结束时间</label>
                        <div class="col-sm-9" style="position:relative;" id="dtblock3">
                            <input type="text" class="form-control " style="width: 342px; " id="bankCreditEndTime" name="bankCreditEndTime"   readonly="readonly"  />
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="suggestAmount" class="col-sm-4 control-label"
                               style="text-align: left;">建议额度 </label>
                        <div class="col-sm-8" style="line-height:30px;">
                            <p><input type="text" class="form-control goods input-append" style="width: 158px;float: left;" id="suggestAmount" name="suggestAmount" readonly="readonly"/><em>w</em></p>
                        </div>
                    </div>
  					<div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="bankAvailableAmount" class="col-sm-3 control-label"
                               style="text-align: left;">授信可用额度 </label>
                        <div class="col-sm-9">
                            <p> <input type="text" class="form-control" id="bankAvailableAmount" name="bankAvailableAmount" style="width: 342px; " readonly="readonly"/> </p>
                        </div>
                    </div>
                    
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="totalRate" class="col-sm-4 control-label"
                               style="text-align: left;">总利率</label>
                        <div class="col-sm-8" style="line-height:30px;">
                            <p> <input type="text" class="form-control" id="totalRate" name="totalRate" style="width: 158px;float: left;" oninput = "javascript:CheckInputIntFloat(this);" /> <em>%</em></p>
                        </div>
                    </div>
                 
                   <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="term" class="col-sm-3 control-label"
                               style="text-align: left;">期限</label>
                        <div class="col-sm-9">
                            <p style="width: 342px;" class="select-content"> 
                   			<input type="text" class="form-control" id="term" name="term" style="width: 158px;float: left;"" />
                            <em style="line-height: 34px;float:left;margin-left:5px;font-style: inherit;">天</em></p>
                        </div>
                    </div> 
                    
                     <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="bankRate" class="col-sm-4 control-label"
                               style="text-align: left;">银行利率</label>
                         <div class="col-sm-8" style="line-height:30px;">
                            <p> <input type="text" class="form-control" id="bankRate" name="bankRate" style="width: 158px;float: left;"  oninput = "javascript:CheckInputIntFloat(this);"/> <em>%</em></p>
                        </div>
                    </div>
                  
					<div class="form-group col-ms-12" style="height: 30px; display: block;float:left;width:50%;margin-right:30px;" id = "sevenRateDiv">
                        <label for="sevenRate" class="col-sm-3 control-label"
                               style="text-align: left;">平台7日利率 </label>
                        <div class="col-sm-9">
                            <p style="width:342px;"> <input type="text" class="form-control" id="sevenRate" name="bankAvailableAmount" style="width: 308px;float:left "  oninput = "javascript:CheckInputIntFloat(this);"/><em style="line-height: 34px;float: left;margin-left:5px;font-style: inherit;" >%</em></p>
                        </div>
                    </div>
                    
                    <div class="modal-footer form-group col-ms-12" style="height: 30px;display: block;width: 46%;float:right;border-top: none;margin-top: 0px;padding-top: 0px;padding-left: 12px;height: 53px;">
                        <button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal" style="float: left;">取消</button>
                        <span type="button" class="btn btn-primary" onclick="submit();" style="float:left;">保存</span>
                    </div>
                </form>

                <table id="creditGoodsId" style="width: 100%"></table>
            </div>
			
        </div>
    </div>
</div>

<!-- 是否短信通知 -->
	
	<div class="modal fade" id='isMessageForm' tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" style="z-index: 1050">
    <div class="modal-dialog" role="document" style="width: 1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">短信通知</h4>
            </div>
            <div class="modal-body">
                <form id="creditApplyDataForm">
                    <input type="hidden" id="companyPk"/>
                    <!-- 树形 -->
                     <div class="form-group col-ms-12" style="height: 30px; display: block">
							<label for="inputEmail3" class="col-sm-3 control-label"
                               style="text-align: left;">是否短信通知</label>
							<div class="col-sm-9">
								<label><input name="isMessage" type="radio" value="1" checked='checked' style="margin-left:3px;" />是</label>
								<label><input name="isMessage" type="radio" value="2" style="margin-left:3px;"/>否</label>
							</div>
					</div>
                </form>
            </div>
            <div class="modal-footer" style="margin-top:-15px;">
                <button type="button" class="btn btn-default" id="quxiaoIsMess" data-dismiss="modal">取消</button>
                <button type="button"  id="buttonIsVisableId" class="btn btn-primary" onclick="updateCreditSatuts('',2);">保存</button>
            </div>
        </div>
    </div>
</div>
	
		<!-- 新建分类model -->
	<div class="modal fade" id="editCustomerNumber" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">银行客户号</h4>
				</div>
				<div class="modal-body" style="padding:0px;">
					<!-- 开始 -->
					<form class="form-horizontal" id="dataCustomerForm" style="    margin: 20px;border: 1px solid #e5e5e5;">
						<input type="hidden" name="creditPk" id="creditPk"/>
						<ul class="edit-top">
						<li>金融产品</li>
						<li>银行名称</li>
						<li>银行客户号</li>
						</ul>
						<div id ="customerNumberDiv" ></div>

					</form>
					<!-- 结束 -->
				</div>
				<div class="modal-footer" style="margin-top:0px;text-align: center;border-top:none;padding-bottom:40px;">
					
					<button type="button" class="btn btn-primary" onclick="submitCustomer();" >确定</button>
					<button type="button" class="btn btn-default" id="quxiaoone"
						data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>

<!-- 票据申请model -->
<div class="modal fade" id='creditBillApply' tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" style="z-index: 1050">
    <div class="modal-dialog" role="document" style="width: 1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">证书申请</h4>
            </div>
            <div class="modal-body">
                <form id="creditApplyDataForm">
                    <input type="hidden" id="pk"/>
                    <!-- 树形 -->
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;">
                        <label for="inputEmail3" class="col-sm-3 control-label"
                               style="text-align: left;">纳税人性质</label>
                        <div class="col-sm-9">
                            <select name="taxNature" id="taxNature" style="width: 342px;" class="form-control">
                                <option value="">--请选择--</option>
                                <option value="0">小规模纳税人</option>
                                <option value="1">一般纳税人</option>
                                <option value="2">转登记</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;">
                        <label for="inputEmail3" class="col-sm-3 control-label"
                               style="text-align: left;">主管税务机关名称</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="taxAuthorityName" name="taxAuthorityName" style="width: 342px;" required="true" />	<!--  -->
                            <input type="hidden" id="taxAuthorityCode"/>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;">
                        <label for="inputEmail3" class="col-sm-3 control-label"
                               style="text-align: left;">申请证书委托文件</label>
                        <div class="col-sm-9">
                            <input class="form-control"  style ="float: left;margin-right: 10px;width: 342px;" type="file" name="delegateCertURL" id="delegateCertURL" style="width: 342px;" required="true"
                           />
                            <span style="line-height: 34px;">是否已上传：<span id="isUpdate"></span></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer" style="margin-top:-15px;">
                <button type="button" class="btn btn-default" id="quxiao1" data-dismiss="modal">取消</button>
                <button type="button"  id="buttonIsVisableId" class="btn btn-primary" onclick="creditBillSubmit();">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 客户申请 -->
<div class="modal fade" id=customertApplyId tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" style="z-index: 1050;overflow-y: hidden!important;">
    <div class="modal-dialog" role="document" style="width: 1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">客户申请</h4>
            </div>
            <div class="modal-body" style="max-height:480px;overflow-y: auto;">
                <form   id="dataFormApply" style="display: none;background:#f4fdfa;padding:10px;margin-bottom:10px;border:1px solid #f2f2f2;position: relative;display: block;">
    				 <input type="hidden" name = "pk" id="creditEditPk"/>
   				 	 <input type="hidden" name = "companyPk" id="companyEditPk"/>
    				 <input type="hidden" name = "customerNumber" id="customerNumberEdit"/>
   					 <input type="hidden" name = "creditNumber" id="creditNumberEdit"/>
					 <input type="hidden" name = "userId" id="userIdEdit"/>
					  <input type="hidden" name = "totalAmount" id="totalAmountEdit"/>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="hpcnt" class="col-sm-6 control-label"
                               style="text-align: left;">上一年流动资金周转次数：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="hpcnt" name="hpcnt" style="width: 205px;" maxlength="10" onkeyup = "checkInputPositiveInteger(this)" required="true" />
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px">
                        <label for="prchAmount" class="col-sm-6 control-label"
                               style="text-align: left;">累计采购金额：</label>
                        <div class="col-sm-6" style="position:relative;" id="dtblock1">
                            <p> <input type="text" class="form-control" id="prchAmount" name="prchAmount"  onkeyup= "checkInputMoney(this)"  maxlength="16" required="true" /> </p>
                        </div>
                    </div>

                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="prchCount" class="col-sm-6 control-label"
                               style="text-align: left;">累计采购次数：</label>
                        <div class="col-sm-6">
                            <p> <input type="text" class="form-control" id="prchCount" name="prchCount"   maxlength="19"  onkeyup = "checkInputPositiveInteger(this)"  required="true"/> </p>
                        </div>
                    </div>
                    
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="rcName" class="col-sm-6 control-label"
                               style="text-align: left;">实控人姓名：</label>
                        <div class="col-sm-6" style="position:relative;" id="dtblock2">
                            <input type="text" class="form-control form-controlgoods " style=" float:left;" id="rcName" name="rcName"  maxlength="100"  required="true" />
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="rcDocType" class="col-sm-6 control-label"
                               style="text-align: left;">实控人证件类型：</label>
                        <div class="col-sm-6" >
                          <select  id="rcDocType" name="rcDocType"  class="form-control" required="true">
                                <option value="">--请选择--</option>
                                <option value="1010">身份证</option>
                                <option value="2010">营业执照</option>
                                <option value="2020">组织机构代码</option>
                                <option value="2030">机构信用代码</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="rcDocNumber" class="col-sm-6 control-label"
                               style="text-align: left;">实控人证件号码： </label>
                        <div class="col-sm-6"  style="position:relative;" id="dtblock2">
                            <p> <input type="text" class="form-control" id="rcDocNumber" name="rcDocNumber"   maxlength="120" required="true"/> </p>
                        </div>
                    </div>
                    
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="rcDocMobile" class="col-sm-6 control-label"
                               style="text-align: left;">实控人手机号：</label>
                        <div class="col-sm-6">
                            <p> <input type="text" class="form-control" id="rcDocMobile" name="rcDocMobile" style="float: left;"  maxlength="50" onkeyup = "javascript:CheckInputIntFloat(this);" /></p>
                        </div>
                    </div>
                    
					 <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="taxAmount" class="col-sm-6 control-label"
                               style="text-align: left;">纳税金额：</label>
                        <div class="col-sm-6" style="position:relative;" id="dtblock2">
                            <input type="text" class="form-control" style="float:left;" id="taxAmount" name="taxAmount"  onkeyup= "checkInputMoney(this)" maxlength="20" required="true"  />
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="coopStartDate" class="col-sm-6 control-label"
                               style="text-align: left;">合作开始时间：</label>
                        <div class="col-sm-6 hzsj" style="position:relative;" id="dtblock3">
                            <input type="text" class="form-control form-controlgoods form-dateday-ymd"  id="coopStartDate" name="coopStartDate"   required="true"   />
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;">
                        <label for="creditAmount" class="col-sm-6 control-label"
                               style="text-align: left;">授信申请额度：</label>
                        <div class="col-sm-6 sxed" style="line-height:30px;">
                            <input type="text" class="form-control goods input-append" style="float: left;" id="creditAmount" name="creditAmount" onkeyup= "checkInputMoney(this)"  maxlength="20" required="true" />
                        </div>
                    </div>
  					<div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;">
                        <label for="loanTrem" class="col-sm-6 control-label"
                               style="text-align: left;">贷款期限最大值：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="loanTrem" name="loanTrem"   maxlength="5" required="true" onkeyup = "checkInputPositiveInteger(this)"  />
                        </div>
                    </div>
                    
                    <div class="modal-footer form-group col-ms-12" style="height: 30px;display: block;width: 46%;float:right;border-top: none;margin-top: 0px;padding-top: 0px;padding-left: 12px;height: 53px;">
                        <button type="button" class="btn btn-default" id="quxiaoApply" data-dismiss="modal" style="float: left;">取消</button>
                        <span type="button" class="btn btn-primary" onclick="submitApply();" style="float:left;">保存</span>
                    </div>
                    <div class="clearfix"></div>
                </form>

            </div>
			
        </div>
    </div>
</div>

<!-- 图片放上去显示js -->
<script type="text/javascript">
    function showImg1(){
        document.getElementById("wxImg1").style.display='block';
    }
    function hideImg1(){
        document.getElementById("wxImg1").style.display='none';
    }
</script>
<!-- 汉化inputfile-->
<script>
    $(function(){
        $('.bigpicture').bigic();

    });
</script>
<script>
    function entersearch(){
        var event = window.event || arguments.callee.caller.arguments[0];
        if (event.keyCode == 13)
        {
            searchTabs(this);
        }
    }

    $("#creditStartTime").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        autoclose: true,
        todayBtn: true,
        language:'zh-CN',
        pickerPosition:"bottom-right"
    }).on('changeDate',function(ev){
    	            var  type=$("#isExistBankCreditTime").val();
    	            if(type==2){
    					$("#bankCreditStartTime").val($("#creditStartTime").val());
    				}
    	        }); 
  
    

    $("#creditEndTime").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        autoclose: true,
        todayBtn: true,
        language:'zh-CN',
        pickerPosition:"bottom-right"
    }).on('changeDate',function(ev){
    	            var  type=$("#isExistBankCreditTime").val();
    	            if(type==2){
    					$("#bankCreditEndTime").val($("#creditEndTime").val());
    				}
    	        }); 
  
    function changeBankContractAmount(amount){
    	if($("#isExistBankAmount").val()==2){
    		$("#bankContractAmount").val(amount);
    	}   	
    }

    $("#taxAuthorityName").autocomplete({
        minLength: 3,
        source: function( request, response ) {
            $.ajax({
                url :  getRootPath() + "/getB2bTaxAuthorityCode.do",
                type : "post",
                dataType : "json",
                data : {taxAuthorityName:$("#taxAuthorityName").val()},
                success: function( data ) {
                    response( $.map( data, function( item ) {
                        return {
                            label: item.taxAuthorityName,
                            value: item.taxAuthorityCode
                        }
                    }));
                }
            });
        },
        focus: function( event, ui ) {
            $("#taxAuthorityName").val( ui.item.label );
            $("#taxAuthorityCode").val( ui.item.value );
            return false;
        },
        select: function( event, ui ) {
            $("#taxAuthorityName").val( ui.item.label );
            $("#taxAuthorityCode").val( ui.item.value );
            return false;
        }
    });
 


</script>
<script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>