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
<script src="./pageJs/operation/goodsmg/seedvariety.js"></script>
<style>
#seedvariety .help-block{
    margin-top: 0px;
    float: right;
    margin-left: 500px;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营中心</a></li>
			<li class="active">子品种</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;">
				  <label class="col-lg-3 col-lg-2"><span>品种：</span> 	
						    <select  name="parentPk" class="selectpicker bla bla bli" data-live-search="true" onkeydown="entersearch()" >
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>  	
					    </label>
					    <!-- <label class="col-lg-3 col-lg-2"><span>子品种：</span> 	
						    <select id="seedvarietyPk" class="selectpicker bla bla bli"   data-live-search="true">
							   <option value="">--请选择--</option>
								 
							 </select>  	
					    </label> -->
					       <label class="col-lg-3 col-lg-2"><span>&nbsp;子品种：</span> 
					         <input type="text"   name="name" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()"/>
					     </label>   
					     <label class="col-lg-3 col-lg-2"><span>&nbsp;是否显示：</span> 
					          <select name="isVisable" class="selectpicker bla bla bli"    >
					          <option value="">--请选择--</option>
					         <option value="1">启用</option>
					          <option value="2"> 禁用</option>
					            
					          </select>
					     </label>
					     <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			     </header>
				<div class="panel-body">
					<div class="adv-table editable-table ">
						<div class="clearfix">
							<div class="btn-group" style="margin-bottom: 20px; width: 100%">
								<!-- 模态框 -->
								<button id="editable-sample_new" style="display:none;" showname="OPER_GOODS_SETTINGS_SEEDVARI_BTN_ADD" class="btn btn-primary btn-new"
									data-toggle="modal" onclick="javascript:edit();">
									新增<i class="fa fa-plus"></i>
								</button>
								<table id="seedvarietyId" style="width: 100%"></table>
							</div>
						</div>
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editSeedvarietyId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">编辑子品种</h4>
				</div>

				<div class="modal-body">
				<form id="dataForm">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
					<input type="hidden"  id="hiddenPartentPk" />
					<div class="form-group col-ms-12"
					     style="height: 30px;line-height:30px; display: block;">
						<label for="inputPassword3" class="col-sm-2 control-label">品种</label>
						<div class="col-sm-10">
						  <div class="form-controlselect" id="seedvariety">
						    <select  name="parentPk" id="parentPk" class="form-controlselect selectpicker bla bla bli"   data-live-search="true"    required="true"  >
						  	<option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>
							 </select>  	
							<%-- <select class="form-control" name="parentPk" id="parentPk">
								<c:forEach items="${list }" var="m">
									<option value="${m.pk}">${m.name }</option>
								</c:forEach>
							</select> --%>
						  </div>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">子品种</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="name" required="true" maxlength="50"/>
						</div>
					</div>
					
					<div class="form-group col-ms-12"
						style="height: 30px; line-height:30px;display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">排序</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="sort" maxlength="8"/>
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px;line-height:30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">是否禁用</label>
					    <div class="col-sm-5">
						 <label>启用<input name="isVisable" type="radio" value="1" style="margin-left:3px;"/></label>
						 <label>禁用<input name="isVisable" type="radio" value="2" style="margin-left:3px;"/>
						 </div>
						</label>
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

</body>
</html>
<script>
function entersearch(){  
    var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
    	SearchClean();  
       }  
} 
</script>