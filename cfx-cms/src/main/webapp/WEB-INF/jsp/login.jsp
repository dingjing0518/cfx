<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>CMS管理系统</title>
<link href="./style/css/style.css" rel="stylesheet" type="text/css"
	media="all" />
<link rel="stylesheet" type="text/css" href="./style/css/login.css" />
<script type="text/javascript">
	if (window != top)
		top.location.href = location.href;
</script>
<script type="text/javascript" src="./pageJs/include/md5.js"></script>
<script type="text/javascript"
	src="./pageJs/include/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="./pageJs/tooltips.js"></script>
<script src="./pageJs/include/jquery.cookie.js" type="text/javascript"></script>
<script type="text/javascript">
	//支持Enter键登录
	document.onkeydown = function(e) {
		if (!e)
			e = window.event;
		if ((e.keyCode || e.which) == 13) {
			var obtnLogin = document.getElementById("submit_btn")
			obtnLogin.focus();
		}
	}

	$(function() {
		//获取cookie的值
		var username = $.cookie('username');
		var password = $.cookie('password');
	
		 //将获取的值填充入输入框中
		$('#username').val(username);
		$('#password').val(password); 
		if(username != null && username != '' && password != null && password != ''){//选中保存秘密的复选框
			$("#remember").attr('checked',true);
		}
		 //提交表单
		$('#submit_btn').click(function() {
			show_loading();
			checkenter();
			if ($('.username').val() == '') {
				show_err_msg('账号还没填呢！');
				$('.username').focus();
			} else if ($('.password').val() == '') {
				show_err_msg('请输入您的密码');
				$('.password').focus();
			} else {
				var  pa=	$('#password').val();
				if(pa.length==32){
					$('#password').val(pa);
				}else{
					$('#password').val(hex_md5($('#password').val()));
				}
				
				$('#login_form').submit();
			}

		});
	});
	function checkenter( ){
	
		if($("#remember").is(":checked")){
		var usern=	$("#username").val();
		var  pa=	$('#password').val();
		if(pa.length==32){
			
			var userp= pa;
		}else{
			var userp=hex_md5(pa);
		}
		
		//	var userp= $('#password').val();
			 //存储一个带7天期限的cookie
			$.cookie("rmbUser", "true", { expires: 7 });
			$.cookie("username", usern, { expires: 7 });
			$.cookie("password", userp, { expires: 7 });
		} else {
			$.cookie("rmbUser", "false", { expire: -1 });
			$.cookie("username", "", { expires: -1 });
			$.cookie("password", "", { expires: -1 });
		}
		}
</script>
<style>
        input:-webkit-autofill {
            -webkit-box-shadow:0 0 0 100px #f0eef0 inset; 
            -webkit-text-fill-color: #666;
        }
        input:-webkit-autofill:focus {
            -webkit-box-shadow:0 0 0 100px #f0eef0 inset;
            -webkit-text-fill-color: #666;
        }
</style>
</head>
<body>
	<div class="message warning">
		<div class="inset"> 
			<div class="login-head">
				<h1>CMS管理系统</h1>
				<div class="alert-close"></div>
			</div>
			<form action="./home.do" method="post" id="login_form">
			<input id="j_flag" name="j_flag" type="hidden"/>
				<li><input type="text" value="" class="mobile ipt" placeholder="请输入您的账号"
					id="username" name="j_username"
					onfocus="javascript:if(this.value=='请输入您的账号')this.value='';"><a
						href="#" class=" icon user"></a><span class="tooltip"></span></li>
				<li><input type="password" value="" class="password ipt"  placeholder="请输入您的密码"
					id="password" name="j_password"
					onfocus="javascript:if(this.value=='请输入您的密码')this.value='';">
						<a href="#" class="icon lock"></a></li>
				<div class="clear"></div>
				<div class="submit">
					<button id="submit_btn" type="button">登录</button>
					<p class="line">
						<input type="checkbox" id="remember" value=""  /> <label
							for="remember">记住我</label>
					</p>
				</div>
			</form>
		</div>
	</div>
	<div class="clear"></div>

	<!-- <div class="footer">
		<p>Copyright &copy; 2017.</p>
	</div> -->
</body>
</html>
