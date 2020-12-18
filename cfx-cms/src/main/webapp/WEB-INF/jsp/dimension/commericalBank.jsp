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
    <script src="./pageJs/dimension/commericalBank.js"></script>
    <style>
        .bootstrap-select > .dropdown-toggle{float:none;}
    </style>
    <style>
        .bootstrap-select > .dropdown-toggle{float:right;}
    </style>
    <!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
<div class="page-heading">
    <ul class="breadcrumb">
        <li> 金融中心 /</li>
        <li> 维度管理 /</li>
        <li> 授信管理 /</li>
        <li class="active">国有商业银行授信数</li>
    </ul>
</div>
<!--主体内容开始-->
<div class="wrapper">
    <div class="row">
        <div class="col-sm-12">
            <section class="panel">
                <header class="panel-heading" style="margin-bottom:10px;">
              	  <label class="col-lg-2 col-lg-3"><span>新增时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertStartTime"   value="" readonly /></label>
                    <label class="col-lg-2 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="insertEndTime"   value="" readonly /></label>
                    <label class="col-lg-2 col-lg-3"><span>状态:</span> 
	                    <select class="form-controlgoods input-append " name="open" style="width: 110px;">
		                    <option value="">全部</option>
		                    <option value="1">启用</option>
		                    <option value="2">禁用</option>
	                    </select>
                    </label>
                    <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
                    <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>  
					<button id="editable-sample_new"  showname="RF_CREDIT_GOODS_ADD" class="btn btn-primary"
						data-toggle="modal" data-target="#myModal1"
						onclick="javascript:edit('',''); ">新增</button>
				</a> 
                </header>
                <div class="panel-body">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
                                    <table id="commericalBankId" style="width: 100%"></table>
                                </div>
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
            searchTabs();
        }
    }
</script>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>