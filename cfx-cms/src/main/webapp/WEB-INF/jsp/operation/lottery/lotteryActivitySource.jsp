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
<script src="./pageJs/operation/lottery/lotteryActivitySource.js"></script>
<!-- 页面内容导航开始-->
</head>
<body class="sticky-header">
	<div class="page-heading">
		<ul class="breadcrumb">
			<li><a href="#">会员活动</a></li>
			<li class="active">活动来源</li>
		</ul>
	</div>
	<!-- 页面内容导航结束-->
	<!--主体内容开始-->
	<div class="wrapper">
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
				 <header class="panel-heading" style="margin-bottom:10px;">
					     <label class="col-sm-6 col-md-4 col-lg-3"><span>&nbsp;是否启用：</span> 
			 				<select  name="isVisable" class="selectpicker bla bla bli">
					          	 	<option value="">--请选择--</option>
					            	<option  value="1">启用</option>
					            	<option  value="2">禁用</option>
			   				</select>
			   			</label>
			   			<!-- <label class="col-sm-6 col-md-4 col-lg-3"><span>活动时间：</span> <input class="form-controlgoods input-append date form_datetime" type="text" name="startTimeStr"   value="" readonly /></label>
			           	<label class="col-sm-6 col-md-4 col-lg-3"><span>至：</span> <input class="form-controlgoods input-append date form_datetime" type="text" name="endTimeStr"   value="" readonly /></label> -->
			           	
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(1);"> 搜索</button>
			             <button class="btn btn-primary" id="btn" onclick="SearchClean(2);"> 清除</button>
			             <button class="btn btn-primary" id="btn" onclick="insertLottery();">新增来源</button>
		            	<input type="hidden" name="activityPk" value=${activityPk } /> 
		             </header>
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
                                <table id="lotteryActivitySourceId" style="width: 100%"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
	</div>
	<!--编辑来源-->
	<div class="modal fade" id="editActivitySourceRuleId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">编辑活动来源</h4>
				</div>
				<div class="modal-body">
				<form  id="reasonForm">
				<div class="form-group col-ms-12"
							style="height: 30px; display: block;">
							<label for="inputEmail3" class="col-sm-2 control-label"
								style="text-align: left;">活动来源:</label>
							<div class="col-sm-10">
							<select  id="sourcePk" name="sourcePk" class="bla bla bli"  style="width:280px;margin-left:24px" data-live-search="true" required="true">
				          	 		<option value="">--请选择--</option>
					          	 	<c:forEach items="${sourceList }" var="m">
					            	<option value="${m.pk }">${m.name }</option>
				            		</c:forEach>
		   					</select>
							</div>
						</div>
				</form>
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="qx" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="submit();">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 活动规则子页面 -->
	<div class="modal fade" id="b2bLotteryRuleId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="col-sm-10">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">活动规则</h4>
				</div>
				<input type="hidden" id="templatePk"/>
				<input type="hidden" id="sourcePkId"/>
				<section class="panel">
					<header class="panel-heading" > 
					<button id="editable-sample_new" class="btn btn-primary btn-new"
									data-toggle="modal" onclick="javascript:insertLotteryRule();">
									新增规则 <i class="fa fa-plus"></i>
								</button>
						<input type="hidden" id="bidStatus" name="bidStatus" value=""/>
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
                                <table id="lotteryRuleTableId" style="width: 100%;height:60%;"></table>
                            </div>
                        </div>
                    </section>
				</div>
				</section>
			</div>
		</div>
		
		<!--编辑活动规则-->
	<div class="modal fade" id="editLotteryRuleId" tabindex="-1" role="dialog"
		aria-labelledby="noAuditModalLabel" style="z-index: 1050">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noAuditModalLabel">编辑活动规则</h4>
				</div>
				<div class="modal-body">
				<form  id="ruleForm">
				<input type="hidden" id="activitySourcePk"/>
					<div id="lotterySourceTemplate">
						</div>
				</form>
				</div>
				<div class="modal-footer" style="margin-top:-15px;">
					<button type="button" class="btn btn-default" id="qxRule" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="saveLotteryRule();">保存</button>
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
	    
	    $(".form_datetimeStamp").datetimepicker({
		      format: "yyyy-mm-dd hh:ii",
		      language:'zh-CN',
		      startDate:strTime,
		      pickerPosition:"bottom-right"
		    });
	    
	    //文本编辑器
	   /*  var ue = UE.getEditor('editor');
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
		} */
    </script>
    <script src="./pageJs/include/form-dateday.js"></script>
</body>
</html>