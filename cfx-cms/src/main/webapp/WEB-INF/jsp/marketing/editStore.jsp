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
<script src="./pageJs/include/common.js"></script>
<!-- 页面内容导航开始-->
<style>
.btn-group.open .dropdown-toggle {
    height: 34px;
    line-height: 20px;
}
.dropdown-menu {  
  position: absolute;  
  top: 103%;  
  left: 0;  
  z-index: 1000;  
  display: none;  
  float: left;  
  list-style: none;  
  text-shadow: none;  
  max-height: 200px; 
  overflow-y:scroll;
  padding: 5px;  
  margin:0px;  
  font-size: 14px;  
  font-family: "Segoe UI",Helvetica, Arial, sans-serif;  
  border: 1px solid #ddd;  
}  
.file-preview-image{
max-width:100%;
height:auto;
}
.file-preview-frame{
width:26%;
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li>运营中心</li>
			<li><a href="javascript:;" onclick="toStore()">店铺管理</a></li>
			<li class="active">编辑</li>
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
		        
				<div class="panel-body" id="unseen"> <input type="hidden" id="storePk"
					name="storePk" value="${storePk}" />
				<table class="table table-bordered table-striped table-condensed">
					<tbody>
						<tr>
							<td class="col-sm-1" style="text-align: right;">店铺交易时间:</td>
							<td class="col-sm-11"><input type="text"
								class="form-control" style="width: 40%;float: left;" name="title" id="title"
								value="${store.sometimes}" required="true" readonly="readonly"/></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">联系人:</td>
							<td class="col-sm-11"><input type="text"
								class="form-control" style="width: 40%;float: left;" name="contacts" id="contacts"
								value="${store.contacts}" required="true" /></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">手机号码:</td>
							<td class="col-sm-11"><input type="text"
								class="form-control" style="width: 40%;float: left;" name="contactsTel" id="contactsTel"
								value="${store.contactsTel}" required="true" /></td>
						</tr>
						
						<tr>
							<td class="col-sm-1" style="text-align: right;">库存设置:</td>
							<c:if test="${store.upperWeight == null || store.upperWeight == '' }">
								<td class="col-sm-11"><input type="text"
									class="form-control" value=""
									style="width: 40%; float: left;" required="true" name=upperWeight readonly="readonly"/></td>
							</c:if>
							<c:if test="${store.upperWeight != null && store.upperWeight != '' }">
								<td class="col-sm-11"><input type="text"
									class="form-control" value="商品库存大于 ${store.weight} 吨时显示有货"
									style="width: 40%; float: left;" required="true" name="upperWeight" readonly="readonly"/></td>
							</c:if>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">店铺状态:</td>
							<td class="col-sm-11"><input type="text" id="newSource"
								class="form-control" value="${store.isOpen == 1?'开启':'关闭'}"
								style="width: 40%; float: left;" required="true" name="newSource" readonly="readonly"/></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">是否开店前显示价格:</td>
							<td class="col-sm-11"><input type="text" id="newSource"
								class="form-control" value="${store.showPricebfOpen == 1?'显示':'不显示'}"
								style="width: 40%; float: left;" required="true" name="newSource" readonly="readonly"/></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">公司简介:</td>
							<td class="col-sm-11"><textarea name="introduce"
									id="introduce" class="form-control" style="height: 50px;">${store.introduce}</textarea></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">店铺公告:</td>
							<td class="col-sm-11"><input type="text" id="shopNotices"
								class="form-control" value="${store.shopNotices}"
								style="width: 40%; float: left;" name="shopNotices" /></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">店铺QQ:</td>
							<td class="col-sm-11"><input type="text" id="qq"
								class="form-control" value="${store.qq}"
								style="width: 40%; float: left;" required="true" name="qq" /></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">店铺LOGO:</td>
							<td class="col-sm-11"><input id="PURL_flag" type="hidden" />
							    <img src="${store.logoSettings}" alt="" id="logoSettings" style="display: none;"/>
								<input id="file" class="file" name="file" type="file" data-min-file-count="1"/></td>
						</tr>
						</div>
					</tbody>
				</table>

				</div>
		        
		        </form>
				<div class="btn-tags">
					<div class="pull-right tag-social"">
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							onclick="submit()">保存</button>
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	    $('#categoryPId').multiselect();
        $('#categoryPk').multiselect();
    </script>
	<script>
		$("#file").fileinput({
			language : 'zh',
			uploadUrl : getRootPath() + "/uploadPhoto.do", // you must set a valid URL here else you will get an error  
			allowedFileExtensions : [ 'xls', 'jpg', 'png', 'gif' ],
			maxFileCount : 3, //同时最多上传3个文件  
			showUpload : true,//是否显示上传按钮  
			showRemove : false,//是否显示删除按钮  
			showCaption : false,
			//allowedFileTypes: ['image', 'video', 'flash'],  这是允许的文件类型 跟上面的后缀名还不是一回事  
			//这是文件名替换  
			slugCallback : function(filename) {
				return filename.replace('(', '_').replace(']', '_');
			},
			initialPreview: [ //预览图片的设置
			                  "<img src='" + $("#logoSettings").attr("src") + "' class='file-preview-image'>",
			              ],
		});
		//这是提交完成后的回调函数    
		$("#file").on("fileuploaded", function(event, data, previewId, index) {
			$("#logoSettings").attr("src",data.response.data);
		});
		$('#file').on('filesuccessremove', function(event, id) {
			$('#logoSettings').attr("src","");
			
			/* if ($("#PURL_flag").val() == "") {
				$("#logoSettings").val('');
			} else {
				$("#logoSettings").val($('#PURL_flag')[0].src);
			} */ 
		});
	</script>
	<script>
	
	function toStore(){
		window.location.href = getRootPath() + "/storeM.do";
	}
	function submit(){
		
		var storePk = $("#storePk").val();
		var contacts = $("#contacts").val();
		var contactsTel = $("#contactsTel").val();
		var introduce = $("#introduce").val();
		var shopNotices = $("#shopNotices").val();
		var logoSettings = $('#logoSettings').attr("src");
		if(contactsTel != null && contactsTel != '' && regMobile(contactsTel) == false){
			new $.flavr({
				modal : false,
				content : "手机号格式不正确!"
			});
			return;
		}
		var qq = $("#qq").val();
		if(qq != null && qq != "" && qq.length > 21){
			new $.flavr({
				modal : false,
				content : "QQ长度不能大于20个字符！"
			});
			return;
		}
			$.ajax({
				type : 'POST',
				url : "./updateStore.do",
				data : {
					pk:storePk,
					contacts:contacts,
					contactsTel:contactsTel,
					introduce:introduce,
					shopNotices:shopNotices,
					logoSettings:logoSettings,
					qq:qq
				},
				dataType : 'json',
				async : true,
				success : function(data) {
					if(data.success){
						new $.flavr({
							modal : false,
							content : data.msg
						});
						setTimeout(function(){
							window.location=getRootPath()+"/storeM.do";
						},2000);
						
					}else{
						new $.flavr({
							modal : false,
							content : data.msg
						});
					}
				}
			});
	}

	</script>
</body>
</html>