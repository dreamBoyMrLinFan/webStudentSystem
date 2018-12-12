<%--
  Created by IntelliJ IDEA.
  User: linfan
  Date: 2018/10/17
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="shortcut icon" href=../images/favicon.ico type=image/x-icon>
    <link href="../css/shop.css" type="text/css" rel="stylesheet"/>
    <link href="../skin/default/skin.css" rel="stylesheet" type="text/css" id="skin"/>
    <link href="../css/Sellerber.css" type="text/css" rel="stylesheet"/>
    <link href="../css/bkg_ui.css" type="text/css" rel="stylesheet"/>
    <link href="../font/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <script src="../js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="../js/layer/layer.js" type="text/javascript"></script>
    <script src="../js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../js/Sellerber.js" type="text/javascript"></script>
    <script src="../js/shopFrame.js" type="text/javascript"></script>
    <script type="text/javascript" src="../js/jquery.cookie.js"></script>
    <script type="text/javascript" src="../js/jquery.validate.js"></script>
    <title>用户登录</title>
    <style>
        .code {
            font-family: Arial;
            font-style: italic;
            font-weight: bold;
            border: 0;
            letter-spacing: 3px;
            color: blue;
            width: 79PX;
            height: 38px;
        }
    </style>
</head>

<body class="login-layout Reg_log_style">
<div class="login-container">
    <div class="center"><img src="../images/logo.png"/></div>
    <div class="space-6"></div>
    <div class="position-relative">
        <div id="login-box" class="login-box widget-box no-border visible">
            <div class="login-main">
                <div class="clearfix">
                    <div class="login_icon"><img src="../images/login_img.png"/></div>
                    <form id="form_id" action="login" method="post"
                          style=" width:300px; float:right; margin-right:50px;">
                        <h4 class="title_name"><img src="../images/login_title.png"/></h4>
                        <fieldset>
                            <ul>
                                <span style="color: red"><label for="usernameId" id="errorUserAndPwd" style="display: none" generated="true"
                                                                class="error"></label></span>
                                <li class="frame_style">
                                    <label class="user_icon"></label>
                                    <input type="text" placeholder="请输入用户名"  name="username"
                                           id="usernameId" value="${username}"/><i>用户名</i>
                                    <!--<span id="user" style="color: green;">不能输入非法字符</span>-->
                                </li>
                                <span style="color: red"><label for="userpwd" style="display: none" generated="true"
                                                                class="error"></label></span>
                                <li class="frame_style">
                                    <label class="password_icon"></label>
                                    <input type="password" placeholder="请输入密码"  name="password"
                                           id="userpwd" value="${password}"/><i>密码</i>
                                </li>
                                <span id="errorLoginId" style="color: red">${errorLogin}</span>
                                <span style="color: red"><label for="Codes_text" style="display: none" generated="true"
                                                                class="error"></label></span>
                                <li class="frame_style"><label class="Codes_icon"></label><input name="code" type="text"
                                                                                                 placeholder="请输入验证码"
                                                                                                 value="${code}"
                                                                                                 id="Codes_text"/><i>验证码</i>
                                    <div class="Codes_region">
                                        <img src="checkCodeServlet" width="100%" height="38px"
                                             onclick="checkImg(this)"/>
                                    </div>
                                    <%-- <input type="hidden" id="hidden_id"
                                            value='<%=  request.getSession().getAttribute("msg") %>'/>--%>

                                    <input type="hidden" name="method" id="hidden_id"
                                           value="login"/> <%-- 使用el表达式 去获取session域中的值 --%>
                                </li>

                            </ul>
                            <div class="space"></div>
                            <div class="clearfix" style="float: right">
                                <span>还没有账号？<a href="register" Style="color:red">立即注册<a></span>
                            </div>
                            <div class="clearfix">
                                <label class="inline">
                                    <input type="checkbox" name="autoLogin" value="yes" class="ace">
                                    <span class="lbl">自动登陆</span>
                                </label>
                            </div>
                            <br>
                            <div class="clearfix">
                                <input type="submit" style="margin: auto;clear:both" class="login_btn"
                                       value="登&nbsp;陆"/>
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
<div class="loginbm">版权所有 2018 <a href=""></a></div>
<strong></strong><br><br>
</body>
</html>
<script type="text/javascript">


    /*当获取焦点的时候把登陆错误信息不显示*/
    $("#usernameId").focus(function () {
        $("#errorLoginId").css("display","none");
    });
    $("#userpwd").focus(function () {
        $("#errorLoginId").css("display","none");
    });
    $("#Codes_text").focus(function () {
        $("#errorLoginId").css("display","none");
    });

    $(function () {
        //表示当前的表单 交给validate插件验证
        // $("#empForm").validate({ key : {key:value,key:{key:value,key:value}} , key:value , key: value });
        // rules 规则  messages 信息
        $("#form_id").validate({
            rules: {
                //里面的代码格式  key 表示校验谁  value表示校验规则的名
                username: {
                    required: true,   //多个规则情况下  key叫 规则名称  value 规则取值
                    rangelength: [3, 12]
                },
                password: {
                    required: true,
                    rangelength: [6, 12]
                },
                code: {
                    remote: {
                        url: "${pageContext.request.contextPath}/login?method=isCode", // 要请求的url地址
                        type: "post", // 请求方式 post
                        dataType: "json", // 数据类型 json
                        data: {
                            code: function () {
                                return $("#Codes_text").val(); // 要携带的参数
                            }
                        },
                        dataFilter: function (data, type) { //过滤返回结果
                            if (data == "false")
                                return false;  //false代表验证码错误
                            else
                                return true; // true代表验证码正确
                        }
                    },
                    required: true,
                }
            },
            messages: {
                username: {
                    required: "用户名不能为空",
                    rangelength: "用户名的长度要在3到12之间"
                },
                password: {
                    required: "密码不能为空",
                    rangelength: "密码的长度要在6到12之间"
                },
                code: {
                    remote: "验证码错误",
                    required: "验证码不能为空"
                }

            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    })

    /*点击更行验证码图片*/
    function checkImg(obj) {
        obj.src = "checkCodeServlet?xxx" + new Date().getTime();
    }

    /*window.onload = function () {
        var hidden_id = document.getElementById("hidden_id");
        var value = hidden_id.value;
        if (value != null) {
            layer.alert(value, {title: '提示框', icon: 0});
        }
    };*/


</script>
