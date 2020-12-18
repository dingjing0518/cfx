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
<script src="./pageJs/yarn/goodsmgr/goodsManage.js"></script>
<style>
.ylfc .bootstrap-select{float:left!important;margin-left:60px!important;}
@media (max-width:1635px){
.ylfc .bootstrap-select{margin-left:54px!important;}
}
@media (max-width:1600px){
.ylfc .bootstrap-select {margin-left: 0px!important;}
.responsivedate{margin-left: 0px!important;}
}

</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">商品管理</a></li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-lg-3 col-lg-2"><span>&nbsp;店铺：</span> 
					         <input type="text"  name="storeName" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
					     </label>
			            <label class="col-lg-3 col-lg-2"><span>公司名称：</span> 
			                 <input type="text"  name="companyName" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
			            </label>
			            <label class="col-lg-3 col-lg-2"><span>厂家品牌：</span> 
				            <select name="brandPk" class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${goodsBrandList }" var="m">
					            	<option value="${m.pk}">${m.brandName }</option>
					           	    </c:forEach>  
			   				</select>
		    			</label>
					     <label class="col-lg-3 col-lg-2"><span>工艺：</span> 	
						    <select id="technologyPk" name="technologyPk"  class="selectpicker bla bla bli" data-live-search="true">
 
							   <option value="">--请选择--</option>
								<c:forEach items="${technologyList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>  	
					    </label>					   
					    <label class="col-lg-3 col-lg-2 ylfc"><span>原料一级：</span>  
                            <select name="rawMaterialParentPk" id = "rawMaterialParentPk" class="selectpicker bla bla bli"   data-live-search="true" onchange="changeSecondMaterial()">
                                   <option value="">--请选择--</option>
                                    <c:forEach items="${materialList }" var="m">
                                    <option value="${m.pk}">${m.name }</option>
                                    </c:forEach>  
                             </select>      
                        </label>
                        <label class="col-lg-3 col-lg-2 ylfc"><span>原料二级：</span>  
                            <select name="rawMaterialPk" id ="rawSecondMaterialPk" class="selectpicker bla bla bli"   data-live-search="true" >
                                   <option value="">--请选择--</option>
                                    
                             </select>      
                        </label>
	                    <label class="col-lg-3 col-lg-2"><span>规格：</span> 
                             <select id="specPk" name="specPk"  class="selectpicker bla bla bli" data-live-search="true" >
							   <option value="">--请选择--</option>
								<c:forEach items="${sxSpecList}" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>  	

                        </label>
                        <label class="col-lg-3 col-lg-2"><span>物料名称：</span> 
                             <input type="text"  name="materialName" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
                        </label>
                          <label class="col-lg-2 col-lg-3"><span>商品类型：</span> 
                            <select name="type" class="selectpicker bla bla bli"   data-live-search="true">
                                       <option value="">--请选择--</option> 
                                       <option value="normal">正常</option> 
                                       <option value="sale">特卖</option> 
                                       <option value="presale">预售</option>
                                        <option value="auction">竞拍</option> 
                                       <option value="binding">拼团</option>
                            </select>    
                        </label>
                        <label class="col-lg-2 col-lg-3"><span>是否显示：</span>
                            <select name="isNewProduct" class="selectpicker bla bla bli"   data-live-search="true">
                                       <option value="">--请选择--</option> 
                                       <option value="0">是</option>
                                       <option value="1">否</option>
                            </select>    
                        </label>
                       
                          <label class="col-lg-3 col-lg-2" style="z-index:99;"><span>上架时间：</span> 
                            <input   class="form-controlgoods input-append date form-dateday" type="text" name="upStartTime"   value="" readonly>
                         </label>
                        <label class="col-lg-3 col-lg-2" style="z-index:99;"><span style="width:60px;display:inline-block">至</span> 
                            <input style="float: left;margin-left: 60px;"    class="form-controlgoods input-append date form-dateday responsivedate" type="text" name="upEndTime"   value="" readonly>
                        </label>
                        <label class="col-lg-2 col-lg-3"><span>是否混纺：</span>
                            <select name="isBlend" class="selectpicker bla bla bli"   data-live-search="true">
                                       <option value="">--请选择--</option> 
                                       <option value="1">是</option>
                                       <option value="2">否</option>
                            </select>    
                        </label> 

			     		 <label class="col-lg-3 col-lg-2"><span>更新时间：</span>
					         <input name="updateStartTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
					     </label>
			     		<label class="col-lg-3 col-lg-2" style="z-index:99;"><span>至</span> 
			     			<input name="updateEndTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
			     		</label>  
					       <input type="hidden" id="isUpdown" name="isUpdown"></input>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			            <button style="display:none;" showname="YARN_GOODS_MG_BTN_EXPORT" class="btn btn-default dropdown-toggle" onclick="excel();" data-toggle="dropdown"><span id ="loadingExport">导出 </span></button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <header class="panel-heading custom-tab ">
                            <ul class="nav nav-tabs">
                            
                                <li class="active">
                                    <a href="#all" data-toggle="tab" onclick="findMarri(0);">全部</a>
                                </li>
                                <li class="">
                                    <a href="#verify" data-toggle="tab" onclick="findMarri(1);">待上架</a>
                                </li>
                                <li class="">
                                    <a href="#verifydone" data-toggle="tab" onclick="findMarri(2);">上架</a>
                                </li>
                                <li class="">
                                    <a href="#unverify" data-toggle="tab" onclick="findMarri(3);">下架</a>
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
                                  <table id="goodsUpperId" style="width: 100%"></table>
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
		    	searchClean();  
		       }  
		} 
</script>
<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>