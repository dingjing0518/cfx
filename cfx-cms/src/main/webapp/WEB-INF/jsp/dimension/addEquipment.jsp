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
    <script src="./pageJs/dimension/equipment.js"></script>
         <style>
        #dtblock1 .help-block{
            margin-right: 585px;
		    position: absolute;
		    left: 750px;
		    top:45px;
        }
        #dtblock2 .help-block{
            position: absolute;
		    left: 750px;
		    top: 45px;
        }
        #dtblock3 .help-block{
            position: absolute;
		    left: 750px;
            top: 19/0px;
        }
   .bootstrap-select > .dropdown-toggle{
     width:180px;
   }
   .bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn){
     float:left;
   }
    </style>
    <!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
<div class="page-heading">
    <ul class="breadcrumb">
        <li> 金融中心 /</li>
        <li> 维度管理 /</li>
        <li> 企业经营管理 /</li>
        <li class="active">新增设备管理</li>
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
                                                               name="pk" value="${equipment.pk}"/>
                        <table class="table table-bordered table-striped table-condensed">
                            <tbody>

                            <tr>
                                <td class="col-sm-1" style="text-align: right;">设备类型:</td>
                                <td class="col-sm-8">
                                    <select name="category"  id="category" class="selectpicker bla bla bli"    >

                                        <option value="">--请选择--</option>
                                        <option value="喷水织机" ${equipment.category=='喷水织机'?'selected':''}>喷水织机</option>
                                        <option value="经编" ${equipment.category=='经编'?'selected':''}>经编</option>
                                        <option value="圆机" ${equipment.category=='圆机'?'selected':''}>圆机</option>

                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-sm-1" style="text-align: right;">设备权属:</td>
                                <td class="col-sm-8">
                                    <select name="ownership" id="ownership" class="selectpicker bla bla bli"    >

                                        <option value="">--请选择--</option>
                                        <option value="自有" ${equipment.ownership=='自有'?'selected':''}>自有</option>
                                        <option value="租赁" ${equipment.ownership=='租赁'?'selected':''}>租赁</option>
                                        <option value="自有+租赁" ${equipment.ownership=='自有+租赁'?'selected':''}>自有+租赁</option>

                                    </select>
                                </td>
                            </tr>
                            <tr style="position:relative">
                                <td class="col-sm-1" style="text-align: right;">设备台数:</td>
                                <td class="col-sm-8" style="position:relative;"><div id="dtblock1"><input type="text"
                                                            class="form-control" style="width: 180px;float: left;"
                                                            name="minNumber" id="minNumber" maxlength="8"
                                                            value="${equipment.minNumber}" required="true"/></div>
                                    <span style="margin-left: 20px;"> &lt;设备台数&le;</span>
                                    <div id="dtblock2">
                                    <input type="text"
                                           class="form-control" style="width: 180px;margin-top: -30px;margin-left: 300px;"
                                           name="maxNumber" id="maxNumber" maxlength="8"
                                           value="${equipment.maxNumber}" required="true"/></div>
                                </td>
                            </tr>

                            <tr style="position:relative">
                                <td class="col-sm-1" style="text-align: right;">设备年限:</td>
                                <td class="col-sm-8" style="position:relative;"><div id="dtblock1"><input type="text"
                                                            class="form-control" style="width:180px;float: left;"
                                                            name="minAges" id="minAges" maxlength="8"
                                                            value="${equipment.minAges}" required="true"/></div>
                                    <span style="margin-left: 20px;"> &lt;设备年限&le;</span>
                                    <div id="dtblock2">
                                    <input type="text"
                                           class="form-control" style="width: 180px;margin-top: -30px;margin-left: 300px;"
                                           name="maxAges" id="maxAges" maxlength="8"
                                           value="${equipment.maxAges}" required="true"/></div>
                                </td>
                            </tr>

                            <tr id="dtblock3">
                                <td class="col-sm-1" style="text-align: right;">得分:</td>
                                <td class="col-sm-5" style="position:relative">
                                    <input type="text"
                                           class="form-control" style="width: 40%; "
                                           name="score" id="score"
                                           value="${equipment.score}" maxlength="8" required="true"/>
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