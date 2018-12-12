<%--
  Created by IntelliJ IDEA.
  User: linfan
  Date: 2018/10/24
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link href="../css/shop.css" type="text/css" rel="stylesheet" />
    <link rel="shortcut icon" href=../images/favicon.ico type=image/x-icon>
    <link href="../skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
    <link href="../css/Sellerber.css" type="text/css" rel="stylesheet" />
    <link href="../css/bkg_ui.css" type="text/css" rel="stylesheet" />
    <link href="../font/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../js/jquery-3.3.1.js"></script>
    <script src="../js/layer/layer.js" type="text/javascript"></script>
    <script src="../js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../js/Sellerber.js" type="text/javascript"></script>
    <script src="../js/shopFrame.js" type="text/javascript"></script>
    <script type="text/javascript" src="../js/jquery.cookie.js"></script>
    <script type="text/javascript" src="../js/jquery.validate.js"></script>
    <title>用户注册</title>
</head>

<body class="login-layout Reg_log_style">
<div class="login-container">
    <div class="center"> <img src="../images/logo.png" /></div>
    <div class="space-6"></div>
    <div class="position-relative">
        <div id="login-box" class="login-box widget-box no-border visible">
            <div class="login-main">
                <div class="clearfix">
                    <div class="login_icon"><img src="../images/login_img.png" /></div>
                    <form  id="registerFormId" action="register" method="post" style=" width:300px; float:right; margin-right:50px;">
                        <input type="hidden" name="method" value="register"/>
                        <h4 class="title_name"><img src="../images/login_register.png" /></h4>
                        <fieldset>
                            <ul>

                                <span style="color: red"><label for="usernameId" style="display: none" generated="true" class="error"></label></span>
                                <li class="frame_style">
                                    <label class="user_icon"></label>
                                    <input name="username" id="usernameId" data-name="用户名" type="text" placeholder="请输入用户名"/>
                                    <i>用户名</i>
                                </li>

                                <span style="color: red"><label for="passwordId" style="display: none" generated="true" class="error"></label></span>
                                <li class="frame_style">
                                    <label class="password_icon"></label>
                                    <input name="password" id="passwordId" data-name="密码" type="password"   placeholder="请输入密码" />
                                    <i>密码</i>
                                </li>

                                <span style="color: red"><label for="passwordId2" style="display: none" generated="true" class="error"></label></span>
                                <li class="frame_style">
                                    <label class="password_icon"></label>
                                    <input name="password2" id="passwordId2" data-name="确认密码" type="password"  placeholder="请重复输入密码"/>
                                    <i>确认密码</i>
                                </li>

                                <span style="color: red"><label for="nameId" style="display: none" generated="true" class="error"></label></span>
                                <li class="frame_style">
                                    <label class="Codes_icon"></label>
                                    <input name="name" id="nameId" type="text" data-name="姓名"   placeholder="请输入姓名" id="name"/>
                                    <i>姓名</i></li>
                            </ul>
                            <div class="space"></div>
                            <div class="clearfix" style="text-align: center;float: left">
                                <span>已有账号？<a href="login" Style="color:red">立即登录<a></span>
                            </div>
                            <div class="clearfix" style="text-align: center">
                               <%-- <button type="button" style="margin: auto;clear:both" class="login_btn" id="login_btn"
                                        onclick="return registersubmit()"> 注&nbsp;册
                                </button>--%>
                                <input type="submit" style="margin: auto;clear:both" class="login_btn" value="注&nbsp;册"/>
                                <input type="hidden" id="hidden_id" value="<%= request.getAttribute("msg") %>">
                            </div>

                            <div class="space-4"></div>
                        </fieldset>
                    </form>
                </div>
                <div class="social-or-login center">
                    <span class="bigger-110">通知</span>
                </div>

                <div class="social-login ">
                    为了更好的体验性，本网站系统不再对IE8（含IE8）以下浏览器支持，请见谅。
                </div>
            </div><!-- /login-main -->


            <!-- /widget-body -->
        </div><!-- /login-box -->
    </div><!-- /position-relative -->
</div>
</div>
<div class="loginbm">版权所有  2018  <a href="#"></a> </div><strong></strong><br><br>
</body>
</html>
<script type="text/javascript">
    $(function(){
        //表示当前的表单 交给validate插件验证
        // $("#empForm").validate({ key : {key:value,key:{key:value,key:value}} , key:value , key: value });
        // rules 规则  messages 信息
        $("#registerFormId").validate({
            rules:{
                //里面的代码格式  key 表示校验谁  value表示校验规则的名
                username:{
                    remote:{
                        url:"${pageContext.request.contextPath}/register?method=isUserName", // 要请求的url地址
                        type: "post", // 请求方式 post
                        dataType: "json", // 数据类型 json
                        data: {
                            username: function() {
                                return $("#usernameId").val(); // 要携带的参数
                            }
                        },
                        dataFilter: function (data, type) { //过滤返回结果
                            if (data == "true")
                                return true;  //true代表用户名还未存在
                            else
                                return false; //false代表用户名已经存在
                        }
                },
                    required:true ,   //多个规则情况下  key叫 规则名称  value 规则取值
                    rangelength:[3,12]
                },
                password:{
                    required : true,
                    rangelength:[6,12]
                },
                password2:{
                    required : true,
                    equalTo:"#passwordId"
                },
                name:{
                    required : true,
                    rangelength:[1,6]
                }
            },
            messages:{
                username:{
                    remote:"用户名已存在",
                    required:"用户名不能为空",
                    rangelength:"用户名的长度要在3到12之间"
                },
                password:{
                    required:"密码不能为空",
                    rangelength:"密码的长度要在6到12之间"
                },
                password2:{
                    required:"重复密码不能为空",
                    equalTo:"两次密码不符"
                },
                name:{
                    required:"姓名不能为空",
                    rangelength:"姓名的长度要在3到12之间"
                }

            }
        });
    })
</script>

