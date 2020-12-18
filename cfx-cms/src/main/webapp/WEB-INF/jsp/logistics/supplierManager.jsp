<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="./pageJs/include/third-party/fancybox/fancybox.css" rel="stylesheet" type="text/css" />
    <title>内部管理系统</title>
    <c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
    </c:import>
    <script src="pageJs/logistics/supplier.js"></script>
    <script src="pageJs/logistics/common.js"></script>
</head>
<body class="sticky-header">
<style>
    .imgC {
        width: 80px;
        height: 80px;
        background: url(./style/images/bgbg.png) no-repeat;
        background-size: 100%;
    }
</style>
<div class="page-heading">
    <ul class="breadcrumb">
        <li><a href="#">物流供应商管理</a></li>
    </ul>
</div>

<!-- 页面内容导航结束-->
<!--主体内容开始-->
<div class="wrapper">
    <div class="row">
        <div class="col-sm-12">
            <section class="panel">
                <header class="panel-heading" style="margin-bottom:10px;">
                    <label class="col-lg-3 col-lg-2"><span>公司名称：</span> <input
                            class="form-controlgoods input-append " type="text" name="name" value=""
                            onkeydown="entersearch()"/></label>
                    <label class="col-lg-3 col-lg-2"><span>手机号码：</span> <input
                            class="form-controlgoods input-append " type="text" name="mobile" value=""
                            onkeydown="entersearch()"/></label>
                    <label class="col-lg-3 col-lg-2"><span>更新时间：</span> <input
                            class="form-controlgoods input-append form-dateday form-dateday_start" type="text" name="updateStartTime"
                            value="" readonly/></label>
                    <label class="col-lg-3 col-lg-2"><span>至:</span> <input
                            class="form-controlgoods input-append form-dateday form-dateday_end" type="text" name="updateEndTime"
                            value="" readonly/></label>
                    <input type="hidden" id="isVisable" name="isVisable" value=" "/>
                    <input type="hidden" name="parentPk" value="-1"/>
                    <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
                    <button class="btn btn-warning" id="btn" onclick="searchTabs(2)"> 清除</button>
                    <button style="display:none;"  class="btn btn-success dropdown-toggle" id="btn"  showname="LG_SUPPLIER_BTN_ADD"  onclick="addLogictics()"> 新增公司</button>
                	
                </header>
             
                <div class="panel-body">
                    <section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findSupplier('');">全部</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findSupplier(1);">正常</a>
                                </li>
                                <li class="">
                                    <a href="#off-shelve" data-toggle="tab" onclick="findSupplier(2);">终止合作</a>
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
                                <table id="supplierDataId" style="width: 100%"></table>

                            </div>
                        </div>
                    </section>
                </div>
            </section>
        </div>
    </div>
</div>
<div class="modal fade" id="editLogisticsCompanyId" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" style="z-index: 1051">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">物流供应商信息编辑</h4>
            </div>
            <div class="modal-body">
                <form id="dataForm">
                    <!-- 树形 -->
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;">
                        <label for="companyName" class="col-sm-2 control-label"
                               style="text-align: left;">公司名称</label>
                        <div class="col-sm-10">
                            <input type="text"  class="form-control" id="companyName" name="name" maxlength="20"/>
                            <input type="hidden" id="pk" name="pk"/>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;">
                        <label for="contacts" class="col-sm-2 control-label"
                               style="text-align: left;">联系人</label>
                        <div class="col-sm-10">
                            <input type="text"  class="form-control" id="contacts" name="contacts"/>
                        </div>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height: 30px; display: block;">
                        <label for="mobile" class="col-sm-2 control-label"
                               style="text-align: left;">手机号码</label>
                        <div class="col-sm-10">
                            <input type="text"  class="form-control" id="mobile"
                                   name="mobile"/>
                        </div>
                    </div>


                    <div class="form-group col-ms-12" style="height:100%;width:100%; display: block;">
                        <img id="blUrl" class="imgC" title="营业执照" onclick="op(this.src)"/>
                    </div>
                    <div class="form-group col-ms-12"
                         style="height:100%;width:100%; display: block;">
                        <label for="inputLicense" class="col-sm-12 control-label"
                               style="text-align: left;">上传营业执照</label>
                        <div class="">
                            <form enctype="multipart/form-data">
                                <input type="file" id="inputLicense" name="file" onchange="fileChange(this,'blUrl');"/>

                            </form>
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
    	searchTabs();  
       }  
} 	
</script>
<!-- 图片放上去显示js -->
<script type="text/javascript">
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	        searchTabs();  
	       }  
	}   
    function op(c_url) {
        if (c_url.indexOf(".jpg") != -1
            || c_url.indexOf(".png") != -1
            || c_url.indexOf(".jpeg") != -1) {//判断是否存在图片
            window.open(c_url);
        }
    }

    //图像默认图片
    function errorImg(img) {
        img.src = "./style/images/bgbg.png";
        img.onerror = null;
    };
    
    $(function () {
        $('.bigpicture').bigic();
    });

 
	
/*     function startUploading(self, key) {
        var formData = new FormData();
        formData.append("file", $(self).prev()[0].files[0]);
        $.ajax({
            url: getRootPath() + "/uploadPhoto.do",
            dataType: "json",
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function (res) {
            $("#" + key).attr("src", res.data);
        }).fail(function (res) {

        });
    } */
    
    function fileChange(self, pk) {
        var formData = new FormData();
        formData.append("file", $(self)[0].files[0]);
        $.ajax({
            url: getRootPath() + "/uploadPhoto.do",
            dataType: "json",
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function (res) {
            $("#" + pk).attr("src", res.data);
        }).fail(function (res) {
        });
    }
</script>
<script src="pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
<script src="pageJs/include/form-dateday.js"></script>
</body>
</html>