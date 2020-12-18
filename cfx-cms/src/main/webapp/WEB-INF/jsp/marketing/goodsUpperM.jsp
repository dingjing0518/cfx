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
<script src="./pageJs/marketing/goodsUpperM.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">商品管理</a></li>
			<li class="active">商品上下架</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel"> <header class="panel-heading"
					style="margin-bottom:10px;"> <label
					class="col-lg-3 col-lg-2"><span>&nbsp;店铺：</span> <input
					type="text" name="storeName" class="form-controlgoods"
					aria-controls="dynamic-table" onkeydown="entersearch()"></label> <label
					class="col-lg-3 col-lg-2"><span>公司名称：</span> <input
					type="text" name="companyName" class="form-controlgoods"
					aria-controls="dynamic-table" onkeydown="entersearch()"></label> <label
					class="col-lg-3 col-lg-2"><span>厂家品牌：</span> <select
					name="brandName" class="selectpicker bla bla bli"
					data-live-search="true">
						<option value="">--请选择--</option>
						<c:forEach items="${brandList }" var="m">
							<option value="${m.brandName}">${m.brandName }</option>
						</c:forEach>
				</select> </label> <label class="col-lg-3 col-lg-2"><span>品名：</span> <select
					name="productPk" class="selectpicker bla bla bli"
					data-live-search="true">
						<option value="">--请选择--</option>
						<c:forEach items="${productList }" var="m">
							<option value="${m.pk}">${m.name }</option>
						</c:forEach>
				</select> </label> <label class="col-lg-3 col-lg-2"><span>品种：</span> <select
					id="varietiesPk" name="varietiesPk"
					class="selectpicker bla bla bli" data-live-search="true"
					onchange="changeVari()">

						<option value="">--请选择--</option>
						<c:forEach items="${varietiesList }" var="m">
							<option value="${m.pk}">${m.name }</option>
						</c:forEach>
				</select> </label> <label class="col-lg-3 col-lg-2"><span>子品种：</span> <select
					id="seedvarietyPk" name="seedvarietyPk"
					class="selectpicker bla bla bli" data-live-search="true">

						<option value="">--请选择--</option>
				</select> </label> 
				 <label
					class="col-lg-3 col-lg-2"><span>创建时间：</span> <input
					name="insertStartTime"
					class="form-controlgoods input-append date form-dateday"
					type="text" value="" readonly></label> <label
					class="col-lg-3 col-lg-2"><span>至</span> <input
					name="insertEndTime"
					class="form-controlgoods input-append date form-dateday"
					type="text" value="" readonly></label> <label
					class="col-lg-3 col-lg-2"><span>更新时间：</span> <input
					name="updateStartTime"
					class="form-controlgoods input-append date form-dateday"
					type="text" value="" style="position: relative;z-index: 9;" readonly></label> <label
					class="col-lg-3 col-lg-2"><span>至</span> <input
					name="updateEndTime"
					class="form-controlgoods input-append date form-dateday"
					type="text" value="" style="position: relative;z-index: 9;" readonly></label> 
					<label
					class="col-lg-3 col-lg-2"><span>&nbsp;批号：</span> <input
					type="text" name="batchNumber" class="form-controlgoods"
					aria-controls="dynamic-table" style="position: relative;z-index: 9;" onkeydown="entersearch()"></label> 
					<label class="col-lg-3 col-lg-2"><span>规格大类：</span> <select
					id="specPk" name="specPk" class="selectpicker bla bla bli"
					data-live-search="true" onchange="changeSpec()">
						<option value="">--请选择--</option>
						<c:forEach items="${specList }" var="m">
							<option value="${m.pk}">${m.name}</option>
						</c:forEach>
					</select> </label> 
					<label class="col-lg-3 col-lg-2"><span>规格系列：</span> <select
						id="seriesPk" name="seriesPk" class="selectpicker bla bla bli"
						data-live-search="true">
							<option value="">--请选择--</option>
					</select> </label>
					<label
					class="col-lg-3 col-lg-2"><span>上架时间：</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="upStartTime" value="" readonly></label> <label
					class="col-lg-3 col-lg-2"><span>至</span> <input
					class="form-controlgoods input-append date form-dateday"
					type="text" name="upEndTime" value="" readonly></label> <!--  <label class="col-lg-3 col-lg-2"><span>审核时间：</span> 
			                <input id="auditStartTime" class="form-controlgoods input-append date form_datetime" type="text" name="starttime"   value="" readonly>
			             </label>
			            <label class="col-lg-3 col-lg-2"><span>至</span> 
			            	<input id="auditEndTime"  class="form-controlgoods input-append date form_datetime" type="text" name="starttime"   value="" readonly>
			            </label> --> <input type="hidden" id="isUpdown"
					name="isUpdown"></input>
				<button class="btn btn-primary" id="btn" onclick="SearchClean(1);">
					搜索</button>
				<button class="btn btn-primary" id="btn" onclick="SearchClean(2);">
					清除</button>
				<button style="display: none;" showname="MARKET_GOODSSTATISTIC_BTN_EXPORT"
					class="btn btn-default dropdown-toggle" onclick="excel();"
					data-toggle="dropdown">
					<span id="loadingExportGoodsData">导出</span>
				</button>
				</header>
				<div class="panel-body">
					<section class="panel"> <header
						class="panel-heading custom-tab ">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#all" data-toggle="tab"
							onclick="findMarri(0);">全部</a></li>
						<li class=""><a href="#verify" data-toggle="tab"
							onclick="findMarri(1);">待上架</a></li>
						<li class=""><a href="#verifydone" data-toggle="tab"
							onclick="findMarri(2);">上架</a></li>
						<li class=""><a href="#unverify" data-toggle="tab"
							onclick="findMarri(3);">下架</a></li>
					</ul>
					</header>
					<div class="panel-bodycommon">
						<div class="tab-content">
							<div class="tab-pane active" id="all">
								<!-- <table class="table table-striped table-hover table-bordered" id="editable-sample">
                <thead>
                <tr>
                    <th>操作</th>
                    <th>品名</th>
                    <th>品种</th>
                    <th>规格大类</th>
                    <th>规格系列</th>
                    <th>店铺</th>
                    <th>商品名称</th>            
                    <th>挂牌价（元/吨）</th>
                    <th>商品编号</th>
                    <th>重量（千克）</th>
                    <th>厂区名称 </th>
                    <th>仓库名称</th>
                    <th>更新时间</th>
                    <th>上架时间</th>
                    <th>下架时间</th>
                    <th>缺货登记 </th>
                    <th>运费承担方</th>
                    <th>是否全新</th>
                    <th>成本价</th>
                    <th>市场价</th>
                    <th>挂牌价</th>
                    <th>总重量</th>
                    <th>审核数量</th>
                    <th>等级名称</th>
                    <th>特价商品开始时间</th>
                    <th>特价商品结束时间</th>
                    <th>支持议价</th>
                    <th>特价商品</th>
                    <th>冻结库存</th>
                    <th>冻结重量</th>
                    <th>备注</th>  
                </tr>
                </thead>
                <tbody>
                <tr class="">
                    <td>
                    <a class="save" href="javascript:;" style="text-decoration:none;">
                        <button id="editable-sample_new" class="btn btn-warning" data-toggle="modal" data-target="#myModal">
                                                                                   审核
                        </button>
                    </a>
                    <a class="save" href="javascript:;" style="text-decoration:none;">
                        <button id="editable-sample_new" class="btn btn-default" data-toggle="modal" data-target="#myModal">
                                                                                  未 审核
                        </button>
                    </a>
                    &nbsp;&nbsp;&nbsp;
                    <a class="delete" href="javascript:;" style="text-decoration:none;">
                    <button type="button" class="btn btn btn-danger">推荐</button></a>
                    <a class="delete" href="javascript:;" style="text-decoration:none;">
                    <button type="button" class="btn btn btn-default">未推荐</button></a>
                    </td>
                    <td>POY</td>
                    <td>50dtex/99f </td>
                    <td>50D</td>
                    <td>50D</td>
                    <td>老纪化纤店铺</td>
                    <td>普通商品测试购买</td>            
                    <td>32000</td>
                    <td>ASDER456</td>
                    <td>3</td>
                    <td>盛泽厂区</td>
                    <td>盛泽仓库</td>
                    <td>2017-03-08 11:15:11</td>
                    <td>2017-03-08 11:15:15</td>
                    <td>2017-03-08 16:15:11</td>
                    <td>缺 </td>
                    <td>会员自费</td>
                    <td>是</td>
                    <td>23000</td>
                    <td>30000</td>
                    <td>52吨</td>
                    <td>1</td>
                    <td>A级</td>
                    <td>2017-03-08 11:15:11</td>
                    <td>2017-03-08 11:15:11</td>
                    <td>否</td>
                    <td>是</td>
                    <td>1.222</td>
                    <td>0.22</td>
                    <td>1</td>
                    <td>此批次为特价商品，请参考</td> 
                </tr> 
                </tbody>
                </table> -->
							</div>

							<div class="tab-pane" id="verify"></div>
							<div class="tab-pane" id="verifydone"></div>
							<div class="tab-pane" id="unverify"></div>
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
		function entersearch() {
			var event = window.event || arguments.callee.caller.arguments[0];
			if (event.keyCode == 13) {
				searchClean();
			}
		}
	</script>
	<script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>