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
<script src="./pageJs/operation/version.js"></script>
<style>
.selectclose.open .open>.dropdown-menu {
    display: none;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
    <div class="page-heading">
        <ul class="breadcrumb">
            <li><a href="#">APP管理</a></li>
            <li class="active">APP版本管理</li>
        </ul>
    </div>
    <!-- 页面内容导航结束-->
    <!--主体内容开始-->
    <div class="wrapper">
        <div class="row">
            <div class="col-sm-12">
                <section class="panel"> <header class="panel-heading"
                    style="margin-bottom:10px;"> 
                    
                       <label class="col-lg-3 col-lg-2"><span>&nbsp;终端：</span>
                        <select name="terminal" class="selectpicker bla bla bli">
                            <option value="">--全部--</option>
                            <option value="1">安卓</option>
                            <option value="2">IOS</option>
                        </select> 
                    </label>
                    <label class="col-lg-3 col-lg-2"><span>版本号：</span> 
                         <input class="form-controlgoods input-append " type="text" name="version"   value=""/>
                    </label>
                    <label class="col-lg-3 col-lg-2"><span>版本地址：</span> 
                        <input class="form-controlgoods input-append " type="text" name="downloadUrl"   value=""/>
                    </label>
                    <label class="col-lg-3 col-lg-2"><span>时间：</span> 
                        <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeStart"   value="" readonly />
                    </label>
                    <label class="col-lg-3 col-lg-2"><span>至：</span>
                        <input class="form-controlgoods input-append date form-dateday" type="text" name="insertTimeEnd"   value="" readonly />
                    </label>
                         <button class="btn btn-primary" id="btn" onclick="searchTabs(1);"> 搜索</button>
                         <button class="btn btn-primary" id="btn" onclick="searchTabs(2);"> 清除</button>
                    <a href="#" style="text-decoration: none;">
                    <button id="editable-sample_new" style="display:none;" showname="OPER_APP_VERSION_BTN_ADD" class="btn btn-primary"  data-toggle="modal" onclick="javascript:editVersion(''); ">新增</button>
                     </a> 
                </header>
                <div class="panel-body">
                    <section class="panel">
                    <div class="panel-body">
                        <div class="tab-content">
                            <div class="tab-pane active" id="all">
                            </div>
                            <div class="tab-pane" id="verify"></div>
                            <table id="versionId" style="width: 100%"></table>
                        </div>
                    </div>
                    </section>
                </div>
                </section>
            </div>
        </div>
    </div>
   
    <!-- 新建分类model -->
    <div class="modal fade" id="editVersionId" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">编辑版本管理</h4>
                </div>
                <div class="modal-body">
                    <!-- 开始 -->
                    <form class="form-horizontal" id="dataForm">
                        <input type="hidden" id="pk" name="pk" />
                         <div class="form-group">
                                <label for="inputEmail3" class="col-sm-2 control-label">终端</label>
                                <div class="col-sm-4" >
                                    <select class="form-control" id = "terminal" name="terminal" required="true">
                                        <option value="">--请选择--</option>
			                            <option value="1">安卓</option>
			                            <option value="2">IOS</option>
			                        </select> 
                                </div>  
                        </div>
                       
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">版本号</label>
                            <div class="col-sm-4">
                                <input class="form-control" name="version" id="version" required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">版本名称</label>
                            <div class="col-sm-4">
                                <input class="form-control" name="versionName" id="versionName" required="true">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">版本地址</label>
                            <div class="col-sm-4">
                                <input class="form-control" name="downloadUrl" id="downloadUrl" required="true"  maxlength="300" >
                            </div>
                        </div>
                        
                      <div class="form-group col-ms-12">
                            <label for="inputEmail3" class="col-sm-2 control-label">说明</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" name="remark" id="remark" value="" style="height: 60px;"  maxlength="50" ></textarea>
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
    <!-- 新建分类model 结束-->
  
     <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
     <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>