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
    <script src="./pageJs/dimension/offlineScale.js"></script>
    <style>
        .help-block {
            margin-right: 845px;
            margin-top: 30px;
        }
    </style>
    <!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
<div class="page-heading">
    <ul class="breadcrumb">
        <li> 金融中心 /</li>
        <li> 维度管理 /</li>
        <li> 线下数据 /</li>
        <li class="active">新增规模</li>
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
                                                               name="pk" value="${offlineScale.pk}"/>
                        <table class="table table-bordered table-striped table-condensed">
                            <tbody>
                            <tr>
                                <td class="col-sm-1" style="text-align: right;">销售规模:</td>
                                <td class="col-sm-8"><input type="text"
                                                            class="form-control" style="width: 20%;float: left;"
                                                            name="minNumber" id="minNumber" maxlength="8"
                                                            value="${offlineScale.minNumber}" required="true"/>
                                    <span> &lt;销售规模&le;</span>
                                    <input type="text"
                                           class="form-control" style="width: 20%; "
                                           name="maxNumber" id="maxNumber" maxlength="8"
                                           value="${offlineScale.maxNumber}" required="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-sm-1" style="text-align: right;">得分:</td>
                                <td class="col-sm-5">
                                    <input type="text"
                                           class="form-control" style="width: 40%; "
                                           name="score" id="score"
                                           value="${offlineScale.score}" maxlength="8" required="true"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                </form>
                <div class="btn-tags">
                    <div class="pull-right tag-social"  style="padding-right: 10px;">
                        <!--    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
                        <button type="button" class="btn btn-primary" data-dismiss="modal"style="width: 40%;"
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