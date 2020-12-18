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
</head>
<body class="sticky-header">
<!-- 页面内容导航开始-->
<div class="page-heading">
	<h3>管理中心</h3>
	<ul class="breadcrumb">
		<li><a href="#">管理中心</a></li>
		<li class="active">账户管理</li>
	</ul>
</div>
<!-- 页面内容导航结束-->

 <!--主体内容开始-->
        <div class="wrapper">
        <div class="protrail">
            <!-- BEGIN PROFILE CONTENT -->
                            <div class="profile-content">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="portlet light ">
                                            <div class="portlet-body">
                                                <div class="tab-content">
                                                    <!-- PERSONAL INFO TAB -->
                                                    <div class="tab-pane active" id="tab_1_1">
                                                        <form role="form" action="#">
                                                         <div class="form-group">
                                                                <label class="control-label">登录账号</label>
                                                                <input type="text" value="${adto.account }" class="form-control"  readonly="readonly"/> </div>
                                                     <!--         <div class="form-group">
                                                                <label class="control-label">名字</label>
                                                                <input type="text" value="${adto.name }" class="form-control" readonly="readonly"/> </div>
                                                            <div class="form-group">
                                                                <label class="control-label">电话号码</label>
                                                                <input type="text" value="${adto.mobile }" class="form-control" readonly="readonly" /> </div>
                                                           <div class="form-group">
                                                                <label class="control-label">权限</label>
                                                                <select class="form-control">
                                                                  <option>运营组</option>
                                                                  <option>管理组</option>
                                                                </select></div> -->
                                                            <div class="form-group">
                                                                <label class="control-label">请输入新密码</label>
                                                               <input type='password' id="password1" name="password1" placeholder="必须在6-18位之间" class="form-control"/><div style="display: inline" id="tip2"></div></div>
                                                            <div class="form-group">
                                                                <label class="control-label">请重复输入密码</label>
                                                                <input type='password' id="password2" name="password2" placeholder="重复新密码" class="form-control"/><div style="display: inline" id="tip3"> </div></div>
                                                            <a class="save" href="javascript:;" style="text-decoration:none;">
                                                                  <button class="btn btn-success" id="btn">
                                                                     	 保存
                                                                  </button>
                                                            </a>
                                                            <a class="save" href="javascript:;">
                                                                  <button class="btn btn-default">
                                                                     	 取消
                                                                  </button>
                                                            </a>
                                                        </form>
                                                    </div>
                                                    <!-- END PERSONAL INFO TAB -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- END PROFILE CONTENT -->
                </div>
        </div>
        
        <!--主题内容结束-->
        <!--重复验证密码js-->
<script>
    $(document).ready(function(){  
    	getRootPath();
          $("#password1").blur(function(){
              var num=$("#password1").val().length;
              if(num<6){
                   $("#tip2").html("<font color=\"red\" size=\"2\"> 密码不在6-16位之间</font>");                
              }
              else if(num>18){
                   $("#tip2").html("<font color=\"red\" size=\"2\"> 密码不在6-16位之间</font>");                 
              }
              else{
                  $("#tip2").html("<font color=\"green\" size=\"2\">可以使用</font>");                
              }
          }) ;
          $("#password2").blur(function(){
              var tmp=$("#password1").val();
              var num=$("#password2").val().length;
              if($("#password2").val()!=tmp){
                  $("#tip3").html("<font color=\"red\" size=\"2\">  输入不一致</font>");                 
              }
              else{
                  if(num>=6&&num<=18){
                      $("#tip3").html("<font color=\"green\" size=\"2\">  输入一致</font>");                    
                  }                 
                  else{
                      $("#tip3").html("<font color=\"red\" size=\"2\">  无效密码</font>");                           
                  }                
              }
          });
              $("#btn").click(function(){
                  var flag=true;
                  var pk="${adto.pk}";
                  var pass=$("#password1").val();
                  var pass2=$("#password2").val();
                  var num1=$("#password1").val().length;
                  var num2=$("#password2").val().length;
                  if(num1!=num2||num1<6||num2<6||num1>18||num2>18||pass!=pass2){
                      flag=false;
                  }
                  else{
                      flag=true;
                  }
                  if(flag){                   
                  $.ajax({
                	  url : './editPassowrdyet.do',
                      data:{'pk' : pk,'password':hex_md5(pass)},
                      success:function(e){
                    	  window.location=getRootPath()+"/doLogout.do";
                                                      
                         }
                  });
              }
              else{
                  
                  $("#tip4").show().html("<font color=\"red\" size=\"3\">  Please follow the tips!</font>");
              }
              });                  
        });
 
 
        </script>
        </body>
        </html>