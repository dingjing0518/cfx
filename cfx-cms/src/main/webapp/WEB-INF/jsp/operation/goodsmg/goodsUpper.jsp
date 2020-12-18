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
<script src="./pageJs/operation/goodsmg/goodsUpper.js"></script>
<style>
.btn-group.bootstrap-select.bla.bli,input.form-controlgoods {
    float: left;
}
label span {
    width: 100px;
}
.bootstrap-select.btn-group .dropdown-menu li a span.text{width:auto;}
@media (max-width:1600px){
label span {
    width: 60px;
}
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
				            <select name="brandName" class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${goodsBrandList }" var="m">
					            	<option value="${m.brandName}">${m.brandName }</option>
					           	    </c:forEach>  
			   				</select>
		    			</label>
			            <label class="col-lg-3 col-lg-2"><span>品名：</span> 	
				            <select name="productPk" class="selectpicker bla bla bli"   data-live-search="true">
								   <option value="">--请选择--</option>
									<c:forEach items="${productList }" var="m">
									<option value="${m.pk}">${m.name }</option>
									</c:forEach>  
							 </select>  	
					    </label>
					     <label class="col-lg-3 col-lg-2"><span>品种：</span> 	
						    <select id="varietiesPk" name="varietiesPk"  class="selectpicker bla bla bli" data-live-search="true" onchange="changeVari()">
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>  	
					    </label>
					    <label class="col-lg-3 col-lg-2"><span>子品种：</span> 	
						    <select id="seedvarietyPk"  name="seedvarietyPk" class="selectpicker bla bla bli" data-live-search="true">
							   <option value="">--请选择--</option>
							 </select>  	
					    </label>
	                    
					     <label class="col-lg-3 col-lg-2"><span>创建时间：</span>
					         <input name="insertStartTime" class="form-controlgoods input-append date form-dateday" type="text"   value="" readonly>
					     </label>
			     		<label class="col-lg-3 col-lg-2"><span>至</span> 
			     			<input name="insertEndTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
			     		</label>
			     		
			     		 <label class="col-lg-3 col-lg-2"><span>规格大类：</span> 
		                	<select id="specPk" name="specPk" class="selectpicker bla bla bli"   data-live-search="true" onchange="changeSpec()">
								   <option value="">--请选择--</option>
									<c:forEach items="${specList }" var="m">
									<option value="${m.pk}">${m.name}</option>
									</c:forEach>  
							</select>  	
		                 </label>
	                    <label class="col-lg-3 col-lg-2"><span>规格系列：</span> 
		                    <select id="seriesPk" name="seriesPk" class="selectpicker bla bla bli"   data-live-search="true">
									   <option value="">--请选择--</option>
						    </select>  	
	                    </label>
	                     <label class="col-lg-2 col-lg-3"><span>商品类型：</span> 
                            <select id="type" name="type" class="selectpicker bla bla bli"   data-live-search="true">
                                       <option value="">--请选择--</option> 
                                       <option value="normal">正常</option> 
                                       <option value="sale">特卖</option> 
                                       <option value="presale">预售</option>
                                        <option value="auction">竞拍</option> 
                                       <option value="binding">拼团</option>
                            </select>    
                        </label>
                        <label class="col-lg-2 col-lg-3"><span>是否显示：</span>
                            <select id="isNewProduct" name="isNewProduct" class="selectpicker bla bla bli"   data-live-search="true">
                                       <option value="">--请选择--</option> 
                                       <option value="0">是</option>
                                       <option value="1">否</option>
                            </select>    
                        </label>
			             <label class="col-lg-3 col-lg-2"><span>上架时间：</span> 
			                <input   class="form-controlgoods input-append date form-dateday" type="text" name="upStartTime"   value="" readonly>
			             </label>
			            <label class="col-lg-3 col-lg-2"><span>至</span> 
			            	<input    class="form-controlgoods input-append date form-dateday" type="text" name="upEndTime"   value="" readonly>
			            </label>
			             <label class="col-lg-3 col-lg-2"><span>&nbsp;批号：</span> 
					         <input type="text"  name="batchNumber" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
					     </label>
			            <label class="col-lg-3 col-lg-2"><span>更新时间：</span>
                             <input name="updateStartTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
                         </label>
                        <label class="col-lg-3 col-lg-2"><span>至</span> 
                            <input name="updateEndTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
                        </label>
					       <input type="hidden" id="isUpdown" name="isUpdown" />

			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			            <button style="display:none;" showname="OPER_GOODS_UPANDDOWN_BTN_EXPORT" class="btn btn-default dropdown-toggle" onclick="excel();" data-toggle="dropdown" ><span id = "loadingExport">导出 </span></button>
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