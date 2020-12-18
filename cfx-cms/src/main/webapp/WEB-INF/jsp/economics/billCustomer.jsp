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
    <script src="./pageJs/economics/billCustomer.js"></script>
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
        <li><a href="#">票据管理</a></li>
        <li class="active">票据客户管理</li>
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

                    <input type="hidden" id="status" name="status" value=""/>
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
                              <!--   <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(1);">未审核</a>
                                </li> -->
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(3);">审核失败</a>
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
                                <table id="billCustomerId" style="width: 100%"></table>
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
                <h4 class="modal-title" id="myModalLabel1">票据编辑</h4>
            </div>
            <div class="modal-body" style="max-height:480px;overflow-y: auto;">

                <form   id="dataForm" style="display: none;background:#f4fdfa;padding:10px;margin-bottom:10px;border:1px solid #f2f2f2;position: relative;display: block;">

                    <input type="hidden" id="companyName" name="companyName"/><!--  -->
                    <input type="hidden" id="contacts" name="contacts" />
                     <input type="hidden" id="contactsTel" name="contactsTel" />
                    <input type="hidden" id="shotName" name="shotName" />
                    <input type="hidden" id="billCustGoodsPk" name="billCustGoodsPk" />

                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;line-height:30px;">
                        <label for="billGoodsName" class="col-sm-4 control-label"
                               style="text-align: right;">票据产品</label>
                        <div class="col-sm-8" style="position:relative;">
                            <input type="text" class="form-control" style="width: 158px; float:left;" id="billGoodsName" name="billGoodsName" required="true" readonly="readonly"/>
                        </div>
                    </div>

                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;line-height:30px;">
                        <label for="shotNameGoods" class="col-sm-4 control-label"
                               style="text-align: right;">英文简称</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="shotNameGoods" name="shotNameGoods" style="width: 158px;" required="true" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;line-height:30px;">
                        <label for="editcompanyName" class="col-sm-4 control-label"
                               style="text-align: right;">公司名称 </label>
                        <div class="col-sm-8" style="position:relative;" id="dtblock1">
                            <p> <input type="text" class="form-control" id="editcompanyName" name="editcompanyName" style="width: 158px; " readonly="readonly"/> </p>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;line-height:30px;">
                        <label for="editcontacts" class="col-sm-4 control-label"
                               style="text-align: right;">申请人 </label>
                        <div class="col-sm-8">
                            <p> <input type="text" class="form-control" id="editcontacts" name="editcontacts" style="width: 158px; " readonly="readonly"/> </p>
                        </div>
                    </div>
                    
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;line-height:30px;">
                        <label for="editcontactsTel" class="col-sm-4 control-label"
                               style="text-align: right;">电话号码</label>
                        <div class="col-sm-8" style="position:relative;" id="dtblock2">
                            <input type="text" class="form-control" style="width: 158px; float:left;" id="editcontactsTel" name="editcontactsTel" readonly="readonly"/>
                        </div>
                    </div>
                        <div class="form-group col-ms-12"
                             style="height: 30px; display: block;float:left;width:50%;line-height:30px;" id="accountDiv">
                            <label for="account" class="col-sm-4 control-label"
                                   style="text-align: right;">电票账号</label>
                            <div class="col-sm-8" style="position:relative;">
                                <input type="text" class="form-control" style="width: 158px; " id="account" name="account" />
                            </div>
                        </div>
                         <div class="form-group col-ms-12"
                             style="height: 30px; display: block;float:left;width:45%;margin-left:10px;line-height:30px;" id="bankNameDiv">
                            <label for="bankName" class="col-sm-4 control-label"
                                   style="text-align: right;">行名</label>
                            <div class="col-sm-8" style="position:relative;" >
                                <input type="text" class="form-control" style="width: 158px; float:left;" id="bankName" name="bankName"/>
                            </div>
                        </div>

                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:45%;margin-left:10px;line-height:30px;" id="bankNoDiv">
                        <label for="bankNo" class="col-sm-4 control-label"
                               style="text-align: right;">行号</label>
                        <div class="col-sm-8" style="position:relative;" >
                            <input type="text" class="form-control" style="width: 158px; float:left;" id="bankNo" name="bankNo"/>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;line-height:30px;" id ="platformAmountDiv">
                        <label for="account" class="col-sm-4 control-label"
                               style="text-align: right;">平台额度</label>
                        <div class="col-sm-8" style="position:relative;">
                            <input type="text" class="form-control" style="width: 158px;float:left " id="platformAmount" name="platformAmount"  />元
                        </div>
                    </div>

                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;float:left;width:50%;line-height:30px;" id ="useAmountDiv">
                        <label for="account" class="col-sm-4 control-label"
                               style="text-align: right;margin-left:-5px;">已使用额度</label>
                        <div class="col-sm-8" style="position:relative;">
                            <input type="text" class="form-control" style="width: 158px; " id="useAmount" name="useAmount"  readonly="readonly" />
                        </div>
                    </div>
                    <div class="modal-footer form-group col-ms-12" style="height: 30px;display: block;width: 46%;float:right;border-top: none;margin-top: 0px;padding-top: 0px;padding-left: 12px;height: 53px;">
                        <button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal" style="float: left;">取消</button>
                        <span type="button" class="btn btn-primary" onclick="submit();" style="float:left;">保存</span>
                    </div>
                </form>
                <table id="billGoodsId" style="width: 100%"></table>
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
    
  
    
</script>
<script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>