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
<script src="./pageJs/operation/goodsmg/goods.js"></script>
<style>
#dataForm .bootstrap-select > .dropdown-toggle{
  width:180px;
}
#goodsDataFormId .bootstrap-select > .dropdown-toggle{
width:180px;}
#dianpu .help-block{margin-top:38px;}
#pinpai .help-block{margin-top:38px;}
#changqu .help-block{margin-top:38px;}
#goodsType .help-block{margin-top:38px;}
#companyNameClass .help-block{margin-top:38px;}
#cangku .help-block{margin-top:38px;}
.threep .help-block{margin-left:30px;}
</style>

<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">商品管理</a></li>
			<li class="active">商品管理</li>
		</ul>
	</div>
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-lg-2 col-lg-3"><span>&nbsp;店铺：</span> 
					         <input type="text"  name="storeName" class="form-controlgoods" aria-controls="dynamic-table"  onkeydown="entersearch()">
					     </label>
			            <label class="col-lg-2 col-lg-3"><span>公司名称：</span> 
			                 <input type="text"  name="companyName" class="form-controlgoods" aria-controls="dynamic-table" onkeydown="entersearch()">
			            </label>
			            <label class="col-lg-2 col-lg-3"><span>厂家品牌：</span> 
				            <select name="brandName" class="selectpicker bla bla bli"   data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${goodsBrandList }" var="m">
					            	<option value="${m.brandName}">${m.brandName }</option>
					           	    </c:forEach>  
			   				</select>
		    			</label>
			            <label class="col-lg-2 col-lg-3"><span>品名：</span> 	
				            <select name="productName" class="selectpicker bla bla bli"   data-live-search="true">
								   <option value="">--请选择--</option>
									<c:forEach items="${productList }" var="m">
									<option value="${m.name}">${m.name }</option>
									</c:forEach>  
							 </select>  	
					    </label>
					    <label class="col-lg-2 col-lg-3"><span>品种：</span> 	
 
						    <select id="varietiesPk" name="varietiesPk"  class="selectpicker bla bla bli" data-live-search="true" onchange="changeVari()">
 
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>  	
					    </label>
					    <label class="col-lg-2 col-lg-3"><span>子品种：</span> 	
 
						    <select id="seedvarietyPk"  name="seedvarietyPk" class="selectpicker bla bla bli" data-live-search="true">
 
							   <option value="">--请选择--</option>
							 </select>  	
					    </label>
                        <label class="col-lg-2 col-lg-3"><span>创建时间：</span>
					         <input name="insertStartTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
					     </label>
			     		<label class="col-lg-2 col-lg-3"><span>至</span> 
			     			<input name="insertEndTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
			     		</label>
		                <label class="col-lg-2 col-lg-3"><span>规格大类：</span> 
		                	<select id="specPk" name="specPk" class="selectpicker bla bla bli"   data-live-search="true" onchange="changeSpec()">
								   <option value="">--请选择--</option>
									<c:forEach items="${specList }" var="m">
									<option value="${m.pk}">${m.name}</option>
									</c:forEach>  
							</select>  	
		                 </label>
	                    <label class="col-lg-2 col-lg-3"><span>规格系列：</span> 
		                    <select id="seriesPk" name="seriesPk" class="selectpicker bla bla bli"   data-live-search="true">
									   <option value="">--请选择--</option>
										 
						    </select>  	
	                    </label>
	                    <label class="col-lg-2 col-lg-3"><span>商品类型：</span> 
		                    <select id="type" name="type" class="selectpicker bla bla bli"   data-live-search="true">
									   <option value="">--请选择--</option> 
									   <option value="normal">正常</option> 
									  <!--  <option value="auction">竞拍</option>  -->
									   <option value="sale">特卖</option> 
									   <option value="preslae">预售</option> 
									  <!--  <option value="rare">紧货</option>  -->
						    </select>  	 
	                    </label>
	                    <label class="col-lg-2 col-lg-3"><span>是否显示：</span>
		                    <select id="isNewProduct" name="isNewProduct" class="selectpicker bla bla bli"   data-live-search="true">
									   <option value="">--请选择--</option> 
									   <option value="0">是</option>
									   <option value="1">否</option>
						    </select>  	 
	                    </label>
	                    
			            <label class="col-lg-2 col-lg-3"><span>更新时间：</span>
					         <input name="updateStartTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
					     </label>
			     		<label class="col-lg-2 col-lg-3"><span>至</span> 
			     			<input name="updateEndTime" class="form-controlgoods input-append date form-dateday" type="text"    value="" readonly>
			     		</label>
			     		<%--<label class="col-lg-2 col-lg-3"><span>价格趋势：</span>
		                    <select id="isHome" name="isHome" class="selectpicker bla bla bli"   data-live-search="true">
									   <option value="">--请选择--</option> 
									   <option value="1">显示</option> 
									   <option value="2">不显示</option> 
						    </select>  	 
	                    </label>--%>
			            <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             	<!--  <button id="editable-sample_new" style="display:none;" showname="BTN_ADDGOODS"  class="btn btn-primary"
									data-toggle="modal" onclick="javascript:edit('');">
									新增
								</button>-->
						<!-- 	<button id="editable-sample_index" style="display:none;" showname="BTN_ADDINDEX" class="btn btn-primary"
									data-toggle="modal" onclick="javascript:addIndex('');">
									更新索引
								</button> -->
			            
		             </header>
				<div class="panel-body">
					<section class="panel">
                        
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
                                  <table id="goodsId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
		<div class="modal fade" id="editGoods" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalLabel">编辑商品</h4>
				</div>

				<div class="modal-body">
			<form id="goodsDataFormId">
					<!-- 编辑页面开始 -->
					<input type="hidden" class="form-control" id="pk" />
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">商品名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="name"   required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">公司名称</label>
							<div style="float: left;margin-left: 15px;" id="companyNameClass">
							<select  id="companyPID" name="companyPID" class="selectpicker bla bla bli" onchange="changeCompay()"   data-live-search="true" required="true" style="float:left;">
					          	 	<option value="null">--请选择--</option>
					            	<c:forEach items="${companyList}" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
			   				</div>
							<div class="col-sm-5" >
							<select class="form-control col-sm-3" id="companyPk" name="companyPk" style="display:none;" ><!-- onchange="changeChildCompany();" -->
							</select>
							</div>
						</div>
							<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">店铺</label>
							<div class="col-sm-5" style="width: 83.3%;float: right;" id="dianpu">
							<select class="form-control col-sm-3" id="storePk" name="storePk" onchange="changePlant();" required="true">
									<option value="">--请选择店铺--</option>
							</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">厂区</label>
							<div class="col-sm-5" style="width:210px;" id="changqu">
							<select class="form-control col-sm-3" id="plantPk" name="plantPk" onchange="changeWare();" required="true">
									<option value="">--请选择厂区--</option> 
							</select>
							</div>
							<div class="col-sm-5" style="width:210px;" id="cangku" required="true">
							<select class="form-control col-sm-3" id="warePk" name="warePk">
									<option value="">--请选择仓库--</option>
							</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">挂牌价</label>
							<div class="col-sm-5" style="width:210px;">
								<input type="text" class="form-control" id="price"  name="price" required="true"/>
							</div>
							<div class="col-sm-5" style="width:210px;" id="cangku" required="true">
							(元/千克)
							</div>
						</div>
						
						<div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">商品类型</label>
							<div style="float: left;margin-left: 15px;" id="goodsType">
							  <select id="editType" name="editType" id="editType" class="selectpicker bla bla bli"   data-live-search="true" required="true">
					          	 	<option value="">--请选择--</option>
					            	<!--  <option value="rare">紧货</option> -->
					            	 <option value="preslae">预售</option>
					            	<!--  <option value="auction">竞拍</option> -->
					            	  <option value="normal">正常</option>
					            	   <option value="sale">特卖</option>
			   				</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">品牌</label>
							<div class="col-sm-5" style="width:210px;" id="pinpai">
							<select class="form-control col-sm-3" id="brandPk" name="brandPk" data-live-search="true" required="true">
									<option value="">--请选择--</option> 
							</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">品名</label>
							<div style="float: left;margin-left: 15px;" class="threep">
							  <select id="productPk" name="productPk" class="selectpicker bla bla bli"  required="true" data-live-search="true" onchange="hiddenSpec()">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${productList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">规格</label>
							<div class="col-sm-5" style="width:210px;">
								<input type="text" class="form-control" id="specifications"  name="specifications" required="true"/>
							</div>
						</div>
							<div id="productSpec" class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">规格大类</label>
							<div style="float: left;margin-left: 15px;">
							  <select id="addSpecPk" name="addSpecPk" class="selectpicker bla bla bli" data-live-search="true" onchange="AddChangeSpec()">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${specList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
							<div class="col-sm-5" style="margin-left:-22px;">
							<select class="selectpicker bla bla bli" id="addSeriesPk" data-live-search="true" style="display:none;">
									<option value="">--请选择规格系列--</option>
							</select>
							</div>
						</div>
						 <div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">等级</label>
							<div style="float: left;margin-left: 15px;" class="threep">
							  <select id="gradePk" name="gradePk" class="selectpicker bla bla bli" required="true"  data-live-search="true">
					          	 	<option value="">--请选择--</option>
					            	<c:forEach items="${gradeList }" var="m">
					            	<option value="${m.pk}">${m.name }</option>
					           	    </c:forEach>  
			   				</select>
							</div>
						</div>
	                   <div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">品种</label>
							<div style="float: left;margin-left: 15px;" class="threep">
							   <select id="addVarietiesPk"  name="addVarietiesPk"  class="selectpicker bla bla bli" data-live-search="true" required="true" onchange="addChangeVari()">
							   <option value="">--请选择--</option>
								<c:forEach items="${varietiesList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
							 </select>  	
							</div>
							<div class="col-sm-5"  style="margin-left:-22px;">
							  <select id="addSeedvarietyPk" class="selectpicker bla bla bli" data-live-search="true" style="display:none;">
							   <option value="">--请选择子品种--</option>
							 </select>  
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">批号</label>
							<div class="col-sm-5" style="width:210px;">
								<input type="text" class="form-control" id="batchNumber" name="batchNumber"  required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">箱重</label>
							<div class="col-sm-5" style="width:210px;">
								<input type="text" class="form-control" id="tareWeight"  name="tareWeight" required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">总箱数</label>
							<div class="col-sm-5" style="width:210px;">
								<input type="text" class="form-control" id="totalBoxes"  name="totalBoxes" required="true"/>
							</div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">总库存</label>
							<div class="col-sm-5" style="width:210px;">
								<input type="text" class="form-control" id="totalWeight" name="totalWeight" required="true"/>
							</div>
						</div>

						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">是否禁用</label> 
							<div class="col-sm-5">	
								<label>启用<input name="isVisable" type="radio" value="1" checked='checked' style="margin-left:3px;" /></label>
								<label>禁用<input name="isVisable" type="radio" value="2" style="margin-left:3px;"/></label>
						    </div>
						</div>
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">用途描述</label>
							<div class="col-sm-5">
								<textarea class="form-control" id="purposeDescribe" style="width:260px;"></textarea>
							</div>
						</div>
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
<script>  
        $(window).on('load', function () {  
  
            $('.selectpicker').selectpicker({  
                'selectedText': 'cat'  
            });  
  
            // $('.selectpicker').selectpicker('hide');  
        });  
    </script>  
    <script src="./pageJs/include/form-dateday.js"></script>
</body>

</html>