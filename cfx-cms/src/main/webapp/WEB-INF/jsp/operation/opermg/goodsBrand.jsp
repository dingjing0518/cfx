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
<script src="./pageJs/operation/opermg/goodsBrand.js"></script>
<style>
#dataForm .bootstrap-select > .dropdown-toggle{
  width:180px;
}</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营管理</a></li>
			<li class="active">厂家品牌</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-lg-3 col-lg-2"><span>&nbsp;品牌名称：</span> 
					     	<input type="hidden" id="auditStatus" name="auditStatus" value=""/>
					     	<input type="text"  name="brandName" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">	
					     </label>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			              <button style="display:none;" showname="OPER_MG_BRAND_BTN_ADD" class="btn btn-primary" id="btn" onclick="editGoodsBrand();"> 新增</button>
			            
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findGoodsbrand(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findGoodsbrand(1);">待审核</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findGoodsbrand(2);">审核通过</a>
                                </li>
                                <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findGoodsbrand(3);">审核不通过</a>
                                </li>
                            </ul>
                        </header>
                        <div class="bootstrap-table" style="margin-top:10px;">
                            <div class="tab-content">
                                <div class="tab-pane active" id="all">
    
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="goodsBrandId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editGoodsBrand" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">添加品牌</h4>
				</div>

				<div class="modal-body">
			<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">店铺名称</label>
							<div class="col-sm-5">
							<select   id="storePk" class="selectpicker bla bla bli"   data-live-search="true" required="true">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${storeList}" var="m">
					            	<option  value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">品牌</label>
							<div class="col-sm-5">
							    <input type="text" class="form-control" id="brandName" name="brandName" style="width:180px;margin-left:24px" required="true" />
							</div>
						</div>
						  <div class="form-group col-ms-12" style="height: 30px; display: block;" >
                            <label for="inputEmail3" class="col-sm-2 control-label"
                                style="text-align: left;">品牌简称</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="brandShortName" name="brandShortName" style="width:180px;margin-left:24px" />
                            </div>
                        </div>
						 <!-- 编辑页面结束 -->
						</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="quxiao"
						data-dismiss="modal">取消</button>
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
    	SearchClean();  
       }  
} 
</script>
	<!-- 
    <div class="demo-block" id="demo-confirm">
                    <div class="demo-actions">
                        <button type="button" class="btn btn-danger btn-demo">删除</button>
                    </div>
    </div>
   
	<script type="text/javascript">
	 $(document).ready(function(){
         $('#demo-confirm .demo-actions .btn-demo').on('click', function(){
             new $.flavr({
                 content: '确定删除吗?',
                 dialog: 'confirm',
                 onConfirm:function(){
                     alert('删除');
                 },
                 onCancel:function(){

                     alert('取消');
                 }
             });
         });
 });    

</script> -->
</body>
</html>