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
<script src="./pageJs/operation/lottery/lotteryCouponUseRule.js"></script>
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
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">活动管理</a></li>
			<li class="active">
			优惠券使用规则
			</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
			<section class="panel">
			
		        <form id="dataForm">
		        <input type="hidden" id="pk" value="${couponUseRule.id}"/>
				<div class="panel-body" id="unseen">
				<table class="table table-bordered table-striped table-condensed" >
					<tbody>
						<tr>
							<td class="col-sm-1" style="text-align: right;" >正文内容:</td>
							<td class="col-sm-11"><script id="editor" type="text/plain" style="width:100%;height:250px;" name="editor" required="true"></script></td>
						</tr>
						</div>
					</tbody>
				</table>
				</div>
		        
		        </form>
				<div class="btn-tags">
					<div class="pull-center tag-social">
						<center><button type="button" class="btn btn-primary" data-dismiss="modal"
							onclick="submit(1)">保存</button></center>
					<!-- 	<button type="button" class="btn btn-success" data-dismiss="modal"
							onclick="submit(2)">发布</button> -->
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
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
			                  "<img src='" + $("#url").attr("src") + "' class='file-preview-image'>",
			              ],
		});
		
		//这是提交完成后的回调函数    
		$("#file").on("fileuploaded", function(event, data, previewId, index) {
			$("#url").attr("src",data.response.data);
		});
		$('#file').on('filesuccessremove', function(event, id) {
		
		});
	</script>
	<script>
		$(".form_datetime").datetimepicker({
			format : "yyyy-mm-dd hh:ii",
			autoclose : true,
			todayBtn : true,
			language : 'zh-CN',
			pickerPosition : "top-left"
		});
	</script>
	<script type="text/javascript">
		var ue = UE.getEditor('editor');
		ue.ready(function() {
			var a = '${couponUseRule.ruleDetail}';
			ue.setContent(a);
		});
		function isFocus(e) {
			alert(UE.getEditor('editor').isFocus());
			UE.dom.domUtils.preventDefault(e)
		}
		function setblur(e) {
			UE.getEditor('editor').blur();
			UE.dom.domUtils.preventDefault(e)
		}
		function insertHtml() {
			var value = prompt('插入html代码', '');
			UE.getEditor('editor').execCommand('insertHtml', value)
		}
		function createEditor() {
			enableBtn();
			UE.getEditor('editor');
		}

		function setFocus() {
			UE.getEditor('editor').focus();
		}
		function deleteEditor() {
			disableBtn();
			UE.getEditor('editor').destroy();
		}
		function disableBtn(str) {
			var div = document.getElementById('btns');
			var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
			for ( var i = 0, btn; btn = btns[i++];) {
				if (btn.id == str) {
					UE.dom.domUtils.removeAttributes(btn, [ "disabled" ]);
				} else {
					btn.setAttribute("disabled", "true");
				}
			}
		}
	</script> 
</body>
</html>