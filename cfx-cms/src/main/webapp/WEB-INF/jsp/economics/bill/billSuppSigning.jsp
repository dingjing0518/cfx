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
    <script src="./pageJs/economics/bill/billSuppSigning.js"></script>
  
</head>
<body class="sticky-header">
<div class="page-heading">
    <ul class="breadcrumb">
        <li><a href="#">票据管理</a></li>
        <li class="active">供应商签约管理</li>
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
                    <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
                    <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
                    <a href="#" style="text-decoration: none;">
                    <button id="editable-sample_new" showname="EM_BILL_SUPPSIGNING_BTN_ADD" class="btn btn-primary"  data-toggle="modal" onclick="javascript:addCompany(''); ">新增</button>
                     </a> 
                </header>
                <div class="panel-body">
                    <section class="panel">
                       <!--  <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findPurchaser(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(1);">未审核</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findPurchaser(3);">审核失败</a>
                                </li>
                            </ul>
                        </header> -->
                        <div class="panel-bodycommon">
                            <div class="tab-content">
                                <table id="billSuppSigningId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
                </div>
            </section>
        </div>
    </div>
</div>


	<div class="modal fade" id="companyModel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑</h4>
				</div>
				<div class="modal-body">
				<form   id="dataForm">
							<input type="hidden" id="pk" name="pk" />
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-4 control-label"
							style="text-align: left;">公司名称</label>
							<div class="col-sm-8">
						<select   id="companyPk" name="companyPk" class="selectpicker bla bla bli"   data-live-search="true" required="true">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${supCompanyList}" var="m">
					            	<option  value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   			</select>
			   			</div>
					</div>
					
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
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
            searchTabs(this);
        }
    }
</script>
<script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>