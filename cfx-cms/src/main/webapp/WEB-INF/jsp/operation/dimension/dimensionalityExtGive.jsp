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
<script src="./pageJs/operation/dimension/dimensionalityExtGive.js"></script>
<style>
.modal-backdrop.in{z-index:100;}
.modal{z-index:1000;}
.bootstrap-select > .dropdown-toggle{float:none;}
.open .dropdown-menu.open {z-index: 100001!important;}
@media screen and (max-width: 1990px){
.bootstrap-select > .dropdown-toggle {
    width: 104px;
}
}
.fixed-table-footer td:first-child,.fixed-table-footer td:first-child+td,
.fixed-table-footer td:first-child+td+td,.fixed-table-footer td:first-child+td+td+td,
.fixed-table-footer td:first-child+td+td+td+td,.fixed-table-footer td:first-child+td+td+td+td+td,
.fixed-table-footer td:first-child+td+td+td+td+td+td+td+td+td+td+td+td,
.fixed-table-footer td:first-child+td+td+td+td+td+td+td+td+td+td+td,
.fixed-table-footer td:first-child+td+td+td+td+td+td+td+td+td,
.fixed-table-footer td:first-child+td+td+td+td+td+td+td+td+td+td{
    padding: 0px!important;
    display: none!important;
}
.bootstrap-table .fixed-table-footer .table{
width: 100%!important;
    display: table;
    margin: auto;
    text-align: center;
    background-color: #f9f9f9;
}
.bootstrap-table .fixed-table-footer .table td {
    padding: 0px 15px!important;
    display: inline-block;
    height: 31px!important;
    border-top:none;
}
.modal {
    z-index: 10000;
}
.fixed-table-body td.bs-checkbox {
    padding: 12px 10px!important;
}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>会员体系</li>
			<li class="active">额外奖励赠送</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;" id="ewaijiangli">
				  <input type="hidden" id="type" name="type"  value ="2"/>
				 <label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>公司名称</span> <input class="form-controlgoods input-append " type="text" name="companyName" id="companyName"  value="" onkeydown="entersearch()"/></label>	
			   			
			   			<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>电话</span> <input class="form-controlgoods input-append " type="text" name="contactsTel" id="contactsTel"  value="" onkeydown="entersearch()"/></label>	
					      <label class="col-lg-2 col-lg-3"><span>&nbsp;维度：</span> 
			 				<select  name="dimenType" id="dimenType" class="selectpicker bla bla bli" onchange="changeDimenSelect()">
					          	 	<option value="">--请选择--</option>
					          	 	<c:forEach items="${allDimenDtoList }" var="dimen">
					          	 	<option  value="${dimen.type }">${dimen.name }</option>
					          	 	</c:forEach>
			   				</select>
			   			</label>
			   			<label class="col-lg-2 col-lg-3"><span>&nbsp;类型：</span> 
			 				<select  name="dimenCategory" id="dimenCategory" class="selectpicker bla bla bli" >
					          	 	<option value="">--请选择--</option>
					          	 
			   				</select>
			   			</label>
			   			
			   				
							<label class="col-lg-2 col-lg-3"><span>&nbsp;赠送周期：</span> 
							
							<select  id="periodType" name="periodType" class="selectpicker bla bla bli" >
		   					<option value=''>--请选择--</option>
		   					<option  value="1">一次赠送</option>
					            	<option  value="2">每天</option>
					            	<option  value="3">每周</option>
		   					</select>
							</label>
						
						<label class="col-lg-2 col-lg-3"><span>&nbsp;是否启用：</span> 
			 				<select  name="isVisable" id="isVisable" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">启用</option>
					            	<option  value="2">禁用</option>
			   				</select>
			   			</label>
			   				<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>更新时间：</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateStartTime" id="updateStartTime"  value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3" style="padding:0px 7px;"><span>至</span> <input class="form-controlgoods input-append date form-dateday" type="text" name="updateEndTime"  id="updateEndTime"  value="" readonly /></label>
			           	<label class="col-lg-2 col-lg-3" ><span>订单号：</span> <input style="width:104px;position: relative;z-index: 10000;" class="form-controlgoods input-append " type="text" name="orderNumber" id="orderNumber"  value=""/></label>
			             <button style="margin-left:18px;position: relative;z-index: 10000;" class="btn btn-primary" id="btn" onclick="SearchClean(1);" style="margin-left:18px;"> 搜索</button>
			             <button style="position: relative;z-index: 10000;" class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button style="display:none;position: relative;z-index: 10000;" showname="OPER_MSS_EXGIVE_BTN_EXPORT" class="btn btn-primary" id="btn" onclick="exportPresent();">导出</button>
		             </header>
		             <!--  -->
				<div class="panel-body">
					<section class="panel">
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
                                <table id="dimensionalityManageIdEx" style="width: 100%"></table>
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
    	SearchClean();  
       }  
} 
</script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>