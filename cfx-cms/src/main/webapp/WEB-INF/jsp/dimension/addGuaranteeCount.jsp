<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/jsp/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>内部管理系统</title>
    <c:import url="/WEB-INF/jsp/include/htmlHead.jsp">
    </c:import>
    <script src="./pageJs/dimension/guaranteeCount.js"></script>
    <style>
        #dtblock1 .help-block{
            margin-right: 585px;
		    position: absolute;
		    left: 600px;
		    top: 40px;
        }
        #dtblock2 .help-block{
            position: absolute;
		    left: 600px;
		    top: 40px;
        }
        #dtblock3 .help-block{
            position: absolute;
		    left: 600px;
            top: 40px;
        }
    </style>
    <!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
<div class="page-heading">
    <ul class="breadcrumb">
        <li> 金融中心 /</li>
        <li> 维度管理 /</li>
        <li> 担保管理 /</li>
        <li class="active">新增担保户数</li>
    </ul>
</div>
<!-- 页面内容导航结束-->
<!--主体内容开始-->
<div class="wrapper">
    <div class="row">
        <div class="col-sm-12">
            <section class="panel">
                <header class="panel-heading" style="margin-bottom:10px;">

                </header>
                <form id="dataForm">

                    <div class="panel-body" id="unseen"><input type="hidden" id="pk"
                                                               name="pk" value="${guaranteeCount.pk}"/>
                        <table class="table table-bordered table-striped table-condensed">
                            <tbody>
                            <tr style="position:relative">
                                <td class="col-sm-1" style="text-align: right;">担保户数:</td>
                                <td class="col-sm-8" style="position:relative;"><div id="dtblock1"><input type="text"
                                                            class="form-control" style="width: 180px;float: left;"
                                                            name="minNumber" id="minNumber" maxlength="8"
                                                            value="${guaranteeCount.minNumber}" required="true"/></div>
                                    <span style="margin-left: 20px;"> &lt;担保户数&le;</span>
                                    <div id="dtblock2">
                                    <input type="text"
                                           class="form-control" style="width: 180px;margin-top: -30px;margin-left: 300px;"
                                           name="maxNumber" id="maxNumber" maxlength="8"
                                           value="${guaranteeCount.maxNumber}" required="true"/></div>
                                </td>
                            </tr>
                            <tr id="dtblock3">
                                <td class="col-sm-1" style="text-align: right;">得分:</td>
                                <td class="col-sm-5" style="position:relative">
                                    <input type="text"
                                           class="form-control" style="width: 40%; "
                                           name="score" id="score"
                                           value="${guaranteeCount.score}" maxlength="8" required="true"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                </form>
                <div class="btn-tags">
                    <div class="pull-right tag-social"  style="padding-right: 10px;">
                        <!--    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
                        <button type="button" class="btn btn-primary" data-dismiss="modal"
                                onclick="submit(1)">保存
                        </button>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

</body>
</html>