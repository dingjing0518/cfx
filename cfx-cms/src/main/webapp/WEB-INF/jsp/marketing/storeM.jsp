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
<script src="./pageJs/marketing/storeM.js"></script>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">营销中心</a></li>
			<li class="active">店铺管理</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading" style="margin-bottom:10px;">
								<label class="col-lg-3 col-lg-2"><span>店铺名称：</span> <input class="form-controlgoods input-append " type="text" name="name"   value="" /></label>	
								<label class="col-lg-3 col-lg-2"><span>开启状态：</span> <select class="selectpicker bla bla bli " name="isOpen"><option value="">--请选择--</option><option value="1">开启</option><option value="2">关闭</option></select></label>
			                    <label class="col-lg-3 col-lg-2"><span>营业时间：</span> <input class="form-controlgoods input-append date form_datetime" type="text" name="startTimeBegin"   value="" readonly /></label>
			                     <label class="col-lg-3 col-lg-2"><span>至</span> <input class="form-controlgoods input-append date form_datetime" type="text" name="starteTimeEnd"   value="" readonly /></label>
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(1)"> 搜索</button>
			      <button class="btn btn-primary" id="btn" onclick="searchTabs(2)"> 清除</button>
		             </header>
				<div class="panel-body">
					<section class="panel">
                        <div class="panel-bodycommon">
                            <div class="adv-table editable-table">
                                <div class="tab-pane active" id="all">
                                </div>
                                <div class="tab-pane" id="verify">
                                </div>
                                <div class="tab-pane" id="verifydone">
                                </div>
                                <div class="tab-pane" id="unverify">
                                </div>
                                <table id="storeId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<div class="modal fade" id="enditStoreId" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="z-index: 1051">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">店铺编辑</h4>
				</div>
				<div class="modal-body">
				<form  id="dataForm">
					<!-- 树形 -->
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">交易时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  autocomplete="off" id="companyName" name="name" required="true"/>
							<input type="hidden" id="pk" name="pk" />
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">联系人</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control"  autocomplete="off" id="contacts" name="contacts" /> 
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">联系电话</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control"  autocomplete="off" id="contactsTel" name="contactsTel"/> 
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">库存设置</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control"  autocomplete="off" id="contactsTel" name="contactsTel"/> 
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">店铺状态</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control"  autocomplete="off" id="contactsTel" name="contactsTel"/> 
						</div>
					</div>
					<div class="form-group col-ms-12"
						style="height: 30px; display: block;">
						<label for="inputEmail3" class="col-sm-2 control-label"
							style="text-align: left;">是否开店前显示价格</label>
						<div class="col-sm-10">
						    <input type="text" class="form-control"  autocomplete="off" id="contactsTel" name="contactsTel"/> 
						</div>
					</div>
					<div class="form-group col-ms-12" style="height:100%;width:100%; display: block;">
							<img id="ecUrl" class="imgC" title="1"
								onclick="op(this.src)" />
					</div>
					<div class="form-group col-ms-12"
						style="height:100%;width:100%; display: block;">
						<label for="inputEmail3" class="col-sm-12 control-label"
							style="text-align: left;">上传企业认证授权书</label>
						<div class="">
							<div class="">
							<form enctype="multipart/form-data">
	                        <input type="file" name="file"  onchange="fileChange(this,'ecUrl');" />
			                </form>
						</div>
						</div>
					</div>
				</form>
				</div>

				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="quxiao" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>	
	<!-- 图片放上去显示js -->
	<script type="text/javascript">
		function showImg1(){
		document.getElementById("wxImg1").style.display='block';
		}
		function hideImg1(){
		document.getElementById("wxImg1").style.display='none';
		}
	</script>
	<!-- 汉化inputfile-->
	 <script>
	 var now=new Date();
	 var strTime=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();
		$(function(){
			$('.bigpicture').bigic();
		});
	    $(".form_datetime").datetimepicker({
	      format: "hh:ii",
	      startView:1,
	      autoclose: true,
	      todayBtn: true,
	      language:'zh-CN',
	      startDate:strTime,
	      pickerPosition:"bottom-right"
	    });
    </script>
    <script
		src="./pageJs/include/third-party/fancybox/jquery.fancybox-1.3.1.pack.js"></script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>