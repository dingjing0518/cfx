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
<!-- 页面内容导航开始-->
<script src="./pageJs/logistics/logisticsRoute.js"></script>
<script src="./pageJs/logistics/common.js"></script>

</head>
<body class="sticky-header">
	<style>
	 .x-input  {
	      font-size: 14px;
	    }
	 .companyNameClass .bootstrap-select > .dropdown-toggle{
	   width:180px;
	 }
	</style>
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">线路管理</a></li>
		</ul>
	</div>
	
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
						 <label class="col-lg-2 col-lg-3"><span>物流供应商：</span> 	
				            <select id = "companyPk"  name="companyPk" class="selectpicker bla bla bli"   data-live-search="true">
								   <option value="">--请选择--</option>
									<c:forEach items="${lgCompanyList }" var="m">
									<option value="${m.pk}">${m.name }</option>
									</c:forEach>  
							 </select>
					    </label>
						<label class="col-lg-2 col-lg-3"><span>始发地：</span> <input class="form-controlgoods input-append " type="text" id = "fromAddress" name="fromAddress"    value=""  aria-controls="dynamic-table"  onkeydown="entersearch()"/></label>	
						<label class="col-lg-2 col-lg-3"><span>目的地：</span> <input class="form-controlgoods input-append " type="text" id ="toAddress" name="toAddress"   value="" aria-controls="dynamic-table"  onkeydown="entersearch()"/></label>	
      				   	<label class="col-lg-2 col-lg-3">
      				   	<span>最新更新时间</span>
      				   	 <input class="form-controlgoods input-append form-dateday form-dateday_start" type="text"  id= "fromUpdateTime"  name="fromUpdateTime"    value="" readonly /></label>
                     	<label class="col-lg-2 col-lg-3">
                     	<span>至</span> 
                     	<input class="form-controlgoods input-append form-dateday form-dateday_end" type="text" id ="toUpdateTime"  name="toUpdateTime"   value="" readonly /></label>
                     	<label class="col-lg-2 col-lg-3"><span>是否启用</span><select class="selectpicker bla bla bli "  id ="status" name="status">
							<option value="">--请选择--</option>
							<option value="0">未启用</option>
							<option value="1">已启用</option></select>
						</label>
						
      				 	 <button class="btn btn-primary" id="btn" onclick="SearchTabs(1)"> 搜索</button>
			             <button class="btn btn-warning" id="btn" onclick="SearchTabs(2)"> 清除</button>
			             <button style="display:none;" showname="LG_ROUTE_BTN_EXPORT" class="btn btn-success dropdown-toggle" onclick="exportRoute();" data-toggle="dropdown">导出 </button>
			             <button style="display:none;" showname="LG_ROUTE_BTN_IMPORT" class="btn btn-success dropdown-toggle" onclick="importRoute();" data-toggle="modal">导入</button>
			             
		             </header>
		             
					<div class="panel-body">
						<div class="adv-table editable-table ">
							<div class="clearfix">
								<div class="btn-group" style="margin-bottom: 20px; width: 100%">
									<!-- 模态框 -->
									<button  id="editable-sample_new"  style="display:none;"  showname="LG_ROUTE_BTN_ADD" class="btn btn-primary btn-new"
										data-toggle="modal" onclick="javascript:addLogisticsRoute();">新增 <i class="fa fa-plus"></i>
									</button>
									<table id="logisticsRouteId" style="width: 100%"></table>
								</div>
							</div>
						</div>
					</div>
					
			</section>
			</div>
		</div>
	</div>
<!--------------- 编辑模态框 -------------------------->
<div class="modal fade" id="editLogisticsRouteId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" >
		<div class="modal-dialog" role="document" style="width: 790px;height:750px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">编辑线路</h4>
				</div>
				<div class="modal-body">
				<form  id="dataForm">
				<input type="hidden" class="form-control" id="pk" />
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">模板名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="name" name="name" required="true"  maxlength="90" />
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">物流供应商</label>
						<div style="float: left;margin-left: 15px;" class="companyNameClass">
						
						<select  id="companyName" class="selectpicker bla bla bli"  data-live-search="true" >
				          	 	<option value="">--请选择--</option>
				            	<c:forEach items="${lgCompanyList}" var="m">
				            	<option value="${m.pk}">${m.name }</option>
				           	    </c:forEach>  
		   				</select>
						</div>
					</div>
				
					<div class="form-group col-ms-12"style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">是否启用</label> 
						<div class="col-sm-10" >
							<label>是<input name="isstatus" type="radio" value="1"  style="margin-left:3px;"/></label> 
							<label>否<input name="isstatus" type="radio" value="0" style="margin-left:3px;"/></label>
						</div>
					</div>
					
					<div class="form-group col-ms-12"style="height: 30px; display: block;">
				  	<label for="inputEmail3" class="col-sm-2 control-label"style="text-align: left;">品名</label> 	
				  	<div style="float: left;margin-left: 15px;" class="companyNameClass">
			            <select id="productName" class="selectpicker bla bla bli"   data-live-search="true" >
							   <option value="">--请选择--</option>
								<c:forEach items="${productList }" var="m">
								<option value="${m.pk}">${m.name }</option>
								</c:forEach>  
						 </select>  
					</div>	
				    </div>
				    
				   <div class="form-group col-ms-12"style="height: 30px; display: block;">
				  	<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">等级</label> 	
					  	<div style="float: left;margin-left: 15px;" class="companyNameClass">
				            <select id="grade" class="selectpicker bla bla bli"   data-live-search="true">
								   <option value="">--请选择--</option>
									<c:forEach items="${gradeList}" var="m">
									<option value="${m.pk}">${m.name }</option>
									</c:forEach>  
							 </select>  
						</div>	
				    </div>
				    
				     <div class="form-group col-ms-12"style="height: 30px; display: block;">
				     	<label for="inputEmail3" class="col-sm-2 control-label"style="text-align: left;">最低起送价</label> 	
			     		<div class="col-sm-10">
	                      <input class="x-input x_ipt st1" type="text" name="minweight" value=""  style="width:92px;"  onkeyup="javascript:CheckInputIntFloat(this);"/>吨以下，
	                      <input class="x-input x_ipt st2" type="text" name="logisticsprice" value="" style="width:70px;"
	                       onkeyup="javascript:CheckInputIntFloat(this);"/>元&nbsp&nbsp&nbsp内部结算价：  
	                      <input class="x-input x_ipt st3" type="text" name="inlogisticsprice" value="" style="width:70px;"
	                       onkeyup="javascript:CheckInputIntFloat(this);"/>元
                 		</div>
                  	</div>
	         
                  	<div class="form-group col-ms-12"style="height: 30px; display: block;">
	                  	<label for="inputEmail3" class="col-sm-2 control-label"style="text-align: left;">平台公布价</label> 	
	                  	<div class="col-sm-10">
	                  	<input name = "pricePk[]" type="text" style = "display:none" > </input>
	                      <input class="x-input x_ipt st1" type="text" name="showstartweight[]" value="" style="width:92px"  
	                      onkeyup="javascript:CheckInputIntFloat(this);"/>
	                      -<input class="x-input x_ipt st2" type="text" name="showendweight[]" value="" style="width:92px;margin-right:5px"
	                        onkeyup="javascript:CheckInputIntFloat(this);"/>吨，   
	                      <input class="x-input x_ipt st3" type="text" name="showlogisticsprice[]" value="" style="width:70px;"
	                      onkeyup="javascript:CheckInputIntFloat(this);"/>元/吨&nbsp&nbsp&nbsp内部结算价： 
	                      <input class="x-input x_ipt st4" type="text" name="showinlogisticsprice[]" value=""  style="width:70px;"
	                       onkeyup="javascript:CheckInputIntFloat(this);"/>元/吨
	                       <button class="btn btn-primary"  type="button" onclick="addPrice()" style = "margin-left: 7px;">新增</button>
	                      </div> 
	                  </div>
	                  <!-- 新增价格 -->
	                  <div id="addPriceDiv"></div>
		                 <!--  <div class="form-group col-ms-12"style="height: 30px; display: block;">
		                  	<label for="inputEmail3" class="col-sm-2 control-label"style="text-align: left;"></label> 	
		                  	<div class="col-sm-10">
		                      <input class="x-input x_ipt st1" type="text" name="showstartweight[]" value="" placeholder="" style="width:50px;">
		                      -<input class="x-input x_ipt st2" type="text" name="showendweight[]" value="" placeholder="" style="width:50px;margin-right:5px">吨   
		                      <input class="x-input x_ipt st3" type="text" name="showlogisticsprice[]" value="" placeholder="" style="width:50px;">元&nbsp&nbsp&nbsp内部结算价： 
		                      <input class="x-input x_ipt st4" type="text" name="showinlogisticsprice[]" value="" placeholder="" style="width:50px;">元
		                      </div> 
		                  </div>      -->                   
						<div class="form-group col-ms-12" style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label" style="text-align: left;">地区</label>
							<div class="col-sm-10" style="margin-left:-15px;">
								<div class="col-sm-3">
									<select class="form-control col-sm-3" id="province" name="province" onchange="changeCity(this);">
									<option value="">--省--</option>
								 	<c:forEach var="c" items="${province}">
											<option value="${c.pk }">${c.name}</option>
										</c:forEach>
									</select>	
								</div>
								<div class="col-sm-3">
									<select class="form-control col-sm-3 " id="city" name="city" onchange="changeArea(this);">
									<option value="">--市--</option></select>
								</div>
								<div class="col-sm-3">
									<select class="form-control col-sm-3" id="area" name="area" onchange="changeTown(this);">
									<option value="">--区--</option></select>
								</div>
								<div class="col-sm-3">
									<select class="form-control col-sm-3" id="town" name="town"  >
									<option value="">--镇--</option></select>
								</div>
							</div>
						</div>	
					
						<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">线路目的地</label>
							<div class="col-sm-10" style="margin-left:-15px;">
								<div class="col-sm-3">
									<select class="form-control col-sm-3" id="toProvince" name="toProvince" onchange="changeToCity(this);">
									<option value="">--省--</option>
								 	<c:forEach var="c" items="${province}">
											<option value="${c.pk }">${c.name}</option>
										</c:forEach>
									</select>	
								</div>
								<div class="col-sm-3">
									<select class="form-control col-sm-3" id="toCity" name="toCity" onchange="changeToArea(this);"><option value="">--市--</option></select>
								</div>
								<div class="col-sm-3">
									<select class="form-control col-sm-3" id="toArea" name="toArea" onchange="changeToTown(this);"><option value="">--区--</option></select>
								</div>
								<div class="col-sm-3">
									<select class="form-control col-sm-3" id="toTown" name="toTown" ><option value="">--镇--</option></select>
								</div>
							</div>
						</div>  
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao1" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!---------------------- END -->
	<!-- ------------------导入线路 ---------------->
	<div class="modal fade" id="importRouteFile" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" style="z-index: 1051">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">导入线路</h4>
            </div>
            <div class="modal-body">
                <form id="dataFormFile">
                    <!-- 树形 -->
                    <div class="form-group col-ms-12"
                         style="height:100%;width:100%; display: block;">
                        <label for="routeFile" class="col-sm-12 control-label"
                               style="text-align: left;">选择导入文件</label>
                        <div class="">
                            <form enctype="multipart/form-data">
                                <input type="file" id="routeFile" name="file" onchange="fileChange(this,'blUrl');"/>
 								
                            </form>
                        </div>
                    </div>
					<input type="hidden" class="form-control" id="route"  required="true"/>
                </form>
            </div>
				
            <div class="modal-footer" style="margin-top:-15px;">
                <button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="submitFile();">确定</button>
            </div>
        </div>
    </div>
</div>
	<!--------- END ------------>
	<script type="text/javascript">
	function entersearch(){  
	    var event = window.event || arguments.callee.caller.arguments[0];  
	    if (event.keyCode == 13)  
	       {  
	    	SearchClean();  
	       }  
	} 
	  $(window).on('load', function () {  
		  
          $('.selectpicker').selectpicker({  
              'selectedText': 'cat'  
          });  

          // $('.selectpicker').selectpicker('hide');  
      }); 
	  
	   function fileChange(self, pk) {
	        var formData = new FormData();
	        formData.append("upfile", $(self)[0].files[0]);
	        $.ajax({
	            url: getRootPath() + "/uploadDoc.do",
	            dataType: "json",
	            type: 'POST',
	            cache: false,
	            data: formData,
	            processData: false,
	            contentType: false
	        }).done(function (res) {
	        	$("#route").val(res.url);
	            //$("#" + pk).attr("src", res.data);
	        }).fail(function (res) {
	        });
	    } 

	 </script>
	 <script src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
	 <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>