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
<script src="./pageJs/operation/addnews.js"></script>
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
#isShowSelectId span:first-child+span .btn-group.check.open ul.multiselect-container.dropdown-menu {
    display: none;
}
</style>
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">资讯中心</a></li>
			<li class="active">新增文章</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
			<section class="panel">
               <!-- <div class="blog-tags">
					新增文章
					<div class="pull-right tag-social" style="margin-bottom: 5px;">
						<a href="#">
							<button id="editable-sample_new" class="btn btn-primary"
								data-toggle="modal" onclick="javascript:OpenNews( ); ">文章列表</button>
						</a>
					</div>
				</div> -->
				<header class="panel-heading" style="margin-bottom:10px;"> <a href="#" style="text-decoration: none;">
					<button id="editable-sample_new" style="display:none;" showname="OPER_INFO_ADDART_COMMON_BTN_LIST" class="btn btn-primary" data-toggle="modal" data-target="#myModal1" onclick="javascript:OpenNews(); ">文章列表</button>
				    </a>
		        </header>
		        <form id="dataForm">
		        
				<div class="panel-body" id="unseen"> <input type="hidden" id="pk"
					name="pk" value="${news.pk}" />
				<table class="table table-bordered table-striped table-condensed">
					<tbody>
						
						<tr>
							<td class="col-sm-1" style="text-align: right;">新闻标题:</td>
							<td class="col-sm-11"><input type="text"
								class="form-control" style="width: 40%;float: left;" name="title" id="title"
								value="${news.title}" required="true" /></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">所属分类:</td>
						<%-- 	<input type="hidden" id="newsCategoryPk" value="${news.newsCategoryPk}" /> --%>
							<td class="col-sm-11" id="isShowSelectId">
						 
							<c:choose>
							    <c:when test="${news.newsCategoryPk!=null}">
							    <select id="categoryPId" multiple="multiple" onchange="changeCategory();">
							      <c:forEach items="${categorys}" var="m">
										<c:if test="${m.flag==1}">
											<option selected="selected" value="${m.parentId}">${m.pName} </option>
										</c:if>
										<c:if test="${m.flag==null}">
											<option   value="${m.parentId}">${m.pName}</option>
										</c:if>
									</c:forEach>
									</select>
							    </c:when>
							    <c:otherwise>
									<select id="categoryPId" multiple="multiple" onchange="changeCategory();">
										<c:forEach items="${sysCategory}" var="m">
											<c:if test="${m.pId==news.catePID}">
												<option selected="selected" value="${m.pId}">${m.pName}</option>
											</c:if>
											<c:if test="${m.pId!=news.catePID}">
												<option value="${m.pId}">${m.pName }</option>
											</c:if>
										</c:forEach>
									</select>
							    </c:otherwise>
							</c:choose>
					 	<c:if test="${news.pk!=null}">
									<select id="categoryPk" multiple="multiple" >
										   <c:forEach items="${categorys}" var="m">
										<c:if test="${m.flag==1}">
										    <c:forEach items="${m.categorys}" var="n" >
										     <c:if test="${n.flag==1 }">
										     	<option selected="selected" value="${n.pk}">${n.name}</option>
										     </c:if>
										    <c:if test="${n.flag==null}">
										     	<option  value="${n.pk}">${n.name}</option>
										     </c:if>

										    </c:forEach>
											
										</c:if>
									
									</c:forEach>

									</select>
								</c:if> <c:if test="${news.pk==null}">
									<select id="categoryPk" multiple="multiple" >
									</select>
								</c:if>    
								
							</td>

						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">关键词:</td>
							<td class="col-sm-11"><input type="text" id="keyword"
								value="${news.keyword}" class="form-control"
								style="width: 40%; float: left;" name="keyWord" />
									<p style="display: inline; padding-left: 10px; line-height: 34px;">多关键词之间用"|"隔开</p></td>
										
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">文章来源:</td>
							<td class="col-sm-11"><input type="text" id="newSource"
								class="form-control" value="${news.newSource}"
								style="width: 40%; float: left;" required="true" name="newSource" /></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">摘要:</td>
							<td class="col-sm-11"><textarea name="post[post_excerpt]"
									id="newAbstrsct" class="form-control" style="height: 50px;">${news.newAbstrsct}</textarea></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">缩略图:</td>
							<td class="col-sm-11"><input id="PURL_flag" type="hidden" />
							    <img src="${news.url}" alt="" id="url" style="display: none;"/>
								<input id="file" class="file" name="file" type="file" data-min-file-count="1"/></td>
						</tr>
						<tr>
							<td class="col-sm-1" style="text-align: right;">推荐位置:</td>
								<td class="col-sm-11"><input id="PURL_flag" type="hidden" />
									<input type="checkbox" value="1" name="recommendPosition" ${news.recommendPosition==1 ? 'checked' : '' or news.recommendPosition==3 ? 'checked' : ''}>首页资讯模块</input>&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" value="2" name="recommendPosition" ${news.recommendPosition==2 ? 'checked' : '' or news.recommendPosition==3 ? 'checked' : ''}>行情中心模块</input>
								</td>
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
					<div class="pull-right tag-social">
							<button type="button" style="display:none;" showname="OPER_INFO_ADDART_COMMON_BTN_SAVE" class="btn btn-primary" data-dismiss="modal"
								onclick="submit(1)">保存</button>
							<button type="button" style="display:none;" showname="OPER_INFO_ADDART_COMMON_BTN_PUBLISH" class="btn btn-success" data-dismiss="modal"
								onclick="submit(2)">发布</button>
					</div>
				</div>
				</section>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		btnList();
	    $('#categoryPId').multiselect();
        $('#categoryPk').multiselect();
    </script>
	<script type="text/javascript">
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
            //预览图片的设置
			initialPreview: [ "<img src='" + $("#url").attr("src") + "' class='file-preview-image'>"]
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
			var a = '${news.content}';
			ue.setContent(a);
		});
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