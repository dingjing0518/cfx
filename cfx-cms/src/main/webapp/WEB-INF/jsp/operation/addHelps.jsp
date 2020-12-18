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
<script src="./pageJs/operation/addhelps.js"></script>
<style >
	.help-block {
	margin-right:845px;
	margin-top:30px;
	}
</style>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">资讯中心</a></li>
			<li class="active">新增帮助</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
			<section class="panel">
				<header class="panel-heading" style="margin-bottom:10px;"> 
				<a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" class="btn btn-primary" data-toggle="modal" data-target="#myModal1" onclick="javascript:OpenNews(); ">帮助列表</button>
				    </a>
		        </header>
		        <form id="dataForm">
		        
				<div class="panel-body" id="unseen"> <input type="hidden" id="pk"
					name="pk" value="${helps.pk}" />
				<table class="table table-bordered table-striped table-condensed">
					<tbody>
						<tr>
							<td class="col-sm-1" style="text-align: right;">放置终端:</td>
							<td class="col-sm-11">
							<label  style="font-weight: normal;"><input name="showPlace" type="checkbox" value="1" ${helps.showPlace.indexOf("1")>-1?'checked':''} style="margin-left:3px;" />PC端</label>
							<label style="font-weight: normal;"><input name="showPlace" type="checkbox" value="2" ${helps.showPlace.indexOf("2")>-1?'checked':''} style="margin-left:3px;" />WAP端</label>
							<label style="font-weight: normal;"><input name="showPlace" type="checkbox" value="3" ${helps.showPlace.indexOf("3")>-1?'checked':''} style="margin-left:3px;"/>APP端</label>
							</td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">帮助标题:</td>
							<td class="col-sm-11"><input type="text"
								class="form-control" style="width: 40%;float: left;" name="title" id="title"
								value="${helps.title}" required="true" /></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">所属分类:</td>
							<td class="col-sm-11">
							<select class="form-control col-sm-3"
								style="width: 20%;" id="helpCategoryPk" required="true" name=helpCategoryPk>
									<option value="">--请选择--</option>
									<c:forEach items="${sysHelpsCategory}" var="m">
											<option value="${m.pk}" ${m.pk==helps.helpCategoryPk?'selected':''}>${m.name }</option>
									</c:forEach>
							</select> 
						</tr>
						
						<tr>
							<td class="col-sm-1" style="text-align: right;">是否展示首页:</td>
							<td class="col-sm-11">
							<select class="form-control col-sm-3"
								style="width: 20%;" id="showType" required="true" name=showType>
									<option value="">--请选择--</option>
									<option value="1" ${helps.showType==1?'selected':''}>是</option>
									<option value="2" ${helps.showType==2?'selected':''}>否</option>
							</select> 
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">排序:</td>
							<td class="col-sm-11"><input type="text"
								class="form-control" style="width: 40%;float: left;" name="sort" id="sort"
								value="${helps.sort}" required="true" /></td>
						</tr>
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
					<div class="pull-right tag-social"">
						<!--    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							onclick="submit(1)">保存</button>
						<button type="button" class="btn btn-success" data-dismiss="modal"
							onclick="submit(2)">发布</button>
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
		/* $("#file").fileinput('refresh', {
             uploadExtraData: { id: id },
             initialPreview: [ //预览图片的设置
                 "<img src='" + $("#url").attr("src") + "' class='file-preview-image'>",
             ],
         });		
				 */
		//这是提交完成后的回调函数    
		$("#file").on("fileuploaded", function(event, data, previewId, index) {
			$("#url").attr("src",data.response.data);
		});
		$('#file').on('filesuccessremove', function(event, id) {
			/* if ($("#PURL_flag").val() == 0) {
				$("#tupian").val('');
			} else {
				$("#tupian").val($('#PURL')[0].src);
			} */

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
			var a = '${helps.content}';
			ue.setContent(a);
		});
		/* var picUrl = '${news.url}';
		$("#PURL").attr("src", picUrl);
		$('#tupian').val(picUrl); */
		/* if (picUrl == null || picUrl == '') {
			$("#PURL_flag").val(0);
		} else {
			$("#PURL_flag").val(1);
		} */
		var tim = "${news.estiTime}";
		if (tim != "") {
			tim = tim.substring(0, tim.length - 5);
			$("#estimatedTime").val(tim);
		}

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