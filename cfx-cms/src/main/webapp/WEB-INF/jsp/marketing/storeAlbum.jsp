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
<script src="./pageJs/marketing/storeAlbum.js"></script>
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
.images span {
    line-height: 20px;
    text-align: center;
    color: #fff;
}
.pull-left {
    display: none;
}
.images {
    position: relative;
    width: 200px;
    margin: auto;
}
a.images {
    position: absolute;
    width: 20px;
    height: 20px;
    display: inline-block;
    background: rgba(0,0,0,0.5);
    top: -7px;
    right: -7px;
    border-radius: 50%;
    text-align: center;
}
p.time {
    text-align: center;
    margin-top:10px;
}
img.bigpicture {
    width: 200px;
    height: 200px;
    display: block;
    margin: auto;
}
th{display:none!important;}
.table-striped>tbody>tr:nth-child(odd)>td, .table-striped>tbody>tr:nth-child(odd)>th{background-color:#fff;}
table#storeAlbumId {
    border: none!important;
}
.table-bordered>thead>tr>th, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td{border:none;}
.fixed-table-container{border:none;}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">运营中心</a></li>
			<li><a href="javascript:;" onclick="toStore()">店铺管理</a></li>
			<li class="active">公司相册</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
			<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;"> 
				<div class="form-group col-ms-12"
						style="height:100%;width:100%; display: block;">
						<label for="inputEmail3" class="col-sm-12 control-label"
							style="text-align: left;">上传图片到公司相册</label>
						<div class="">
							<div class="">
							<form enctype="multipart/form-data">
	                        <input type="file" name="file"  onchange="fileChange(this,'url');" />
			                </form>
			                <input  type="hidden" id = "fileUpdateLoad" />
						</div>
						</div>
					</div>
				<a href="#" style="text-decoration: none;">
					<button id="updatePics" class="btn btn-primary" data-toggle="modal" data-target="#myModal1" onclick="javascript:updatePic(); ">上传图片</button>
				    </a>
		        </header>
		        <form id="dataForm">
				<div class="panel-body" id="unseen">
				<input type="hidden" id="storePk" name="storePk" value="${storePk}" />
				<table class="table table-bordered table-striped table-condensed" id="storeAlbumId">
					
				</table>

				</div>
		        </form>
				</section>
			</div>
		</div>
	</div>
	
	<script>
	
	
	function toStore(){
		window.location.href = getRootPath() + "/storeM.do";
	}
	
	function startUploading(self,key){
		var formData = new FormData();
		formData.append( "file", $(self).prev()[0].files[0]);
		$.ajax({
		    url: getRootPath() + "/uploadPhoto.do",
		    dataType: "json",
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			 $("#"+key).attr("src",res.data);
		}).fail(function(res) {
			
		});
	}
	function fileChange(self,pk){
		$("#updatePics").attr('disabled',true);
		var formData = new FormData();
		formData.append( "file", $(self)[0].files[0]);
		$.ajax({
		    url: getRootPath() + "/uploadPhoto.do",
		    dataType: "json",
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(res) {
			if (res.data.indexOf(".jpg") != -1|| res.data.indexOf(".png") != -1	|| res.data.indexOf(".jpeg") != -1) {
				//$("#" + pk).attr("src", res.data);
				$("#fileUpdateLoad").val(res.data);
				$("#updatePics").attr('disabled',false);
			} else {
				var index = res.data.lastIndexOf("\/");
				res.data = res.data.substring(index + 1,
						res.data.length);
				var json = $.parseJSON(res.data);
				new $.flavr({
					modal : false,
					content : json.msg
				});
			}
		}).fail(function(res) {
		});
	}
	</script>
</body>
</html>