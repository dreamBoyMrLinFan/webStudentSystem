<%--
  Created by IntelliJ IDEA.
  User: linfan
  Date: 2018/10/18
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="shortcut icon" href=../images/favicon.ico type=image/x-icon>
    <link href="../css/shop.css" type="text/css" rel="stylesheet"/>
    <link href="../css/Sellerber.css" type="text/css" rel="stylesheet"/>
    <link href="../css/bkg_ui.css" type="text/css" rel="stylesheet"/>
    <link href="../font/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <script src="../js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../js/jquery.cookie.js"></script>
    <script src="../js/shopFrame.js" type="text/javascript"></script>
    <script src="../js/Sellerber.js" type="text/javascript"></script>
    <script src="../js/layer/layer.js" type="text/javascript"></script>
    <script src="../js/laydate/laydate.js" type="text/javascript"></script>
    <script type="text/javascript" src="../js/proTree.js"></script>
    <title>学生信息管理</title>
</head>
<!--[if lt IE 9]>
<script src="../js/html5shiv.js"></script>
<script src="../js/respond.min.js"></script>
<script src="../js/css3-mediaqueries.js" type="text/javascript"></script>
<![endif]-->
<body>
<div class="margin" id="page_style">
    <c:if test="${fn:length(pageStudent.list) >= 1}">
    <div class="operation clearfix same_module mb15">
        <form action="studentList" id="student_form" method="post">
            <ul class="choice_search">
                <li class="clearfix col-xs-2 col-lg-2 col-ms-3 "><label class="label_name ">学生名字：</label><input
                        name="nameSo" placeholder="请输入学生姓名" value="${findName}" type="text"
                        class="form-control col-xs-6 col-lg-5"/></li>
                <button class="btn button_btn bg-deep-blue " onclick="student_listSubmit()" type="button"><i
                        class="fa  fa-search"></i>&nbsp;搜索
                </button>
            </ul>
        </form>
    </div>
    </c:if>
</div>
</div>
</div>
<div class="bkg_List_style list_Exhibition list_show padding15">
    <div class="bkg_List_operation clearfix searchs_style">
        <ul class="bkg_List_Button_operation">
            <li class="btn btn-Dark-success"><a href="javascrpt:void()" onclick="member_add()" class="btn_add">添加学生</a></li>
            <c:if test="${fn:length(pageStudent.list) >= 1}">
                <li class="btn btn-danger"><a href="javascrpt:void()" class="btn_add" onclick="deleteAll()">批量删除</a></li>
            </c:if>
            <li class="btn btn-Dark-success"><a href="${pageContext.request.contextPath}/modelServlet?method=outModeStudent"  class="btn_add">下载模板</a></li>
            <li class="btn btn-Dark-success">
                <form action="${pageContext.request.contextPath}/modelServlet?method=addModeStudent" id="formFileUp" enctype="multipart/form-data" method="post">
                    <a href="javascrpt:void()" onclick="addStudentMode();" class="btn_add">
                        批量上传
                        <input type="file" class="btn_add" onchange="fileUp();" id="studentMode"  name="studentMode" style="display: none"/>
                    </a>

                </form>
            </li>
            <li class="btn btn-Dark-success"><a href="${pageContext.request.contextPath}/modelServlet?method=outAllStudent" class="btn_add">导出学生信息</a></li>
        </ul>
    </div>
    <div class="datalist_show">
        <div class="bkg_List clearfix datatable_height confirm">
                <c:choose>
                    <c:when test="${fn:length(pageStudent.list) >= 1}">
                        <table class="table  table_list table_striped table-bordered">
                            <thead>
                            <tr>
                                <th width="40"><label><input type="checkbox" id="daleteAll" onclick="quanxuan()"><span
                                        class="lbl"></span></label></th>
                                <th>学号</th>
                                <th>学生姓名</th>
                                <th>性别</th>
                                <th>年龄</th>
                                <th>编辑</th>
                            </tr>
                            </thead>
                            <tbody>
                        <c:forEach items="${pageStudent.list}" var="student">
                            <tr>
                                <td><label><input type="checkbox" class="delete" value="${student.stuNo}"><span
                                        class="lbl"></span></label></td>
                                <td>${student.stuNo}</td>
                                <td>${student.name}</td>
                                <c:choose>
                                    <c:when test="${student.sex == 1}">
                                        <td>男</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>女</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>${student.age}</td>
                                <td class="td-manage">
                                    <a title="编辑" onclick="member_edit('${student.stuNo}')" href="javascript:;"
                                       class="btn btn-xs btn-info">编辑</a>
                                    <a title="删除" href="javascript:;" onclick="member_del('${student.stuNo}')"
                                       class="btn btn-xs btn-delete">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    </c:when>
                <c:otherwise>
                <div style="width: 100%;text-align:center" >
                    <h1 style="color: red;margin: auto">当前没有学生信息！请添加......</h1>
                </div>
            </c:otherwise>

    </c:choose>
            <!-- 分页 -->
    <c:if test="${fn:length(pageStudent.list) >= 1}">
            <div class="row">
                <div class="col-sm-6">
                    <div class="dataTables_info" id="sample-table_info" role="status" aria-live="polite">共 ${pageStudent.totalRecord}
                        条
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="dataTables_paginate paging_bootstrap" id="sample-table_paginate">

                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${pageStudent.pageNumber > 1}">
                                    <li class="prev"><a href="${pageContext.request.contextPath}/studentList?pageNumber=${pageStudent.pageNumber-1}">上一页</a></li>
                                </c:when>
                                <c:otherwise>
                                <li class="prev disabled"><a href="#">上一页</a></li>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach begin="1" end="${pageStudent.totalPage}" var="i">
                                <c:choose>
                                    <c:when test="${pageStudent.pageNumber == i}">
                                        <li class="active"><a href="${pageContext.request.contextPath}/studentList?pageNumber=${i}">${i}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="${pageContext.request.contextPath}/studentList?pageNumber=${i}">${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${pageStudent.pageNumber < pageStudent.totalPage}">
                                    <li class="next"><a href="${pageContext.request.contextPath}/studentList?pageNumber=${pageStudent.pageNumber+1}">下一页</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="next disabled"><a href="${pageContext.request.contextPath}/studentList?pageNumber=${pageStudent.pageNumber+1}">下一页</a></li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
    </c:if>
        </div>
    </div>
</div>
</div>
</div>
</div>
<!--修改用户图层-->
<div class="add_menber" id="edit_menber_style" style="display:none">
<form action="${pageContext.request.contextPath}/studentList?method=updateStudentById" id="updateForm" method="post">
    <ul class=" page-content">
        <li><label class="label_name">学&nbsp;&nbsp;&nbsp;号：</label><span class="add_name">
            <input  value="" readonly='readonly'  id="stuNo" name="stuNo" type="text"  class="text_add"/></span>
            <div class="prompt r_f"></div>
        </li>

        <li><label class="label_name">学生姓名：</label><span class="add_name">
            <input name="name" id="name" type="text" class="text_add"/></span>
            <div class="prompt r_f"></div>
        </li>

        <li><label class="label_name">学生年龄：</label><span class="add_name">
            <input name="age" id="age" type="text" class="text_add"/></span>
            <div class="prompt r_f"></div>
        </li>


        <li><label class="label_name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
            <label class="label_name" style="margin: 5px auto"><input name="sex" type="radio" id="sex1"  value="1" checked="checked" class="ace"><span class="lbl">男</span></label>&nbsp;&nbsp;&nbsp;
            <label class="label_name" style="margin: 5px auto"><input name="sex" type="radio" id="sex0"  value="0" class="ace"><span class="lbl">女</span></label>&nbsp;&nbsp;&nbsp;
        </li>
    </ul>
</form>
</div>

<%--这是新增学生信息的页面--%>
<div class="add_menber" id="add_menber_style" style="display:none">
    <form action="${pageContext.request.contextPath}/studentList?method=insertStudent" id="insertFomr" method="post">
        <ul class=" page-content">
            <li><label class="label_name">学&nbsp;&nbsp;&nbsp;号：</label><span class="add_name"><input value=""  name="stuNo"
                                                                                                     type="text"
                                                                                                     class="text_add" onblur="findStudentById(this);"/></span>
                <div class="prompt r_f"></div>
            </li>
            <li><label class="label_name">学生姓名：</label><span class="add_name"><input name="name"  type="text"
                                                                                     class="text_add"/></span>
                <div class="prompt r_f"></div>
            </li>
            <li><label class="label_name">学生年龄：</label><span class="add_name"><input name="age"  type="text"
                                                                                     class="text_add"/></span>
                <div class="prompt r_f"></div>
            </li>
            <li><label class="label_name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
                <label class="label_name" style="margin: 5px auto"><input  type="radio" name="sex"  value="1" checked="checked" class="ace"><span class="lbl">男</span></label>&nbsp;&nbsp;&nbsp;
                <label class="label_name" style="margin: 5px auto"><input  type="radio" name="sex" value="0" class="ace"><span class="lbl">女</span></label>&nbsp;&nbsp;&nbsp;
            </li>
        </ul>
    </form>
</div>

</body>
</html>
<script>



    function addStudentMode() {
        document.getElementById("studentMode").click();
    }


    function fileUp() {
        $("#formFileUp").submit();
    }

    var flag = false;
    /*当学生学号输入完毕之后触发事件*/
    function findStudentById(stuNo) {
        flag = false;
        var stuVal = stuNo.value;
        $.post(
            "${pageContext.request.contextPath}/studentList?method=findStudentById",
            "id="+stuVal,
            function(data){
                var len=0;
                if(Boolean(data)){
                    for(i in data)len++;
                }
                if (len > 3) {
                    layer.alert(data, {title: '提示框', icon: 0});
                    flag = true;
                }

            },
            "json"
        );


    }


    function member_add() {
        layer.open({
            type: 1,
            title: '添加用户信息',
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: ['800px', ''],
            content: $('#add_menber_style'),
            btn: ['提交', '取消'],
            yes: function (index, layero) {
                var num = 0;
                var str = "";
                $("#add_menber_style input[type$='text']").each(function (n) {

                    if ($(this).val() == "") {
                        var name = $(this).attr("name");
                        if ("stuNo" == name) {
                            layer.alert("学号不能为空!", {
                                title: '提示框',
                                icon: 0,
                            });
                        }
                        if ("name" == name) {
                            layer.alert("学生姓名不能为空!", {
                                title: '提示框',
                                icon: 0,
                            });
                        }
                        if ("age" == name) {
                            layer.alert("年龄不能为空!", {
                                title: '提示框',
                                icon: 0,
                            });
                        }

                        /*layer.alert(str += "" + $(this).attr("name") + "不能为空！\r\n", {
                            title: '提示框',
                            icon: 0,
                        });*/
                        num++;

                        return false;
                    }
                });
                if (flag) {
                    layer.alert("学生学号重复", {
                        title: '提示框',
                        icon: 0,
                    });
                    return false;
                }
                if (num > 0) {
                    return false;
                }
                else {
                    layer.alert('添加成功！', {
                        title: '提示框',
                        icon: 1,
                    });
                    layer.close(index);
                    document.getElementById("insertFomr").submit();

                }
            }
        });
    }


    function deleteByStuNo(id) {
        window.location.href = "${pageContext.request.contextPath}/studentList?method=delStudent&id=" + id;
    }
    /*删除全部选中的内容*/
    function deleteAll() {
        var deleteDocument = document.getElementsByClassName("delete");
        var flag = false;
        for (var index = 0; index < deleteDocument.length; index++) {
            if (deleteDocument[index].checked) {
                flag = true;
                break;
            }
        }
        if (flag) {
            var temp = "";
            /*window.location.href ="/deleteAll";*/
            layer.confirm('确认要删除选中的内容吗？', function (index) {
                for (var index = 0; index < deleteDocument.length;index++) {
                    if (deleteDocument[index].checked) {
                        var id = deleteDocument[index].value;
                        temp+= id + "-";
                    }
                }
                //temp.substring(0,temp.length-1);
                deleteByStuNo(temp);
            });
        } else {
            layer.alert("请选择一项要删除的内容！", {title: '提示框', icon: 0});
        }
    }

    /*全选或全不选*/
    function quanxuan() {
        var deleteAll = document.getElementById("daleteAll");
        var deleteDocument = document.getElementsByClassName("delete");
        for (var index = 0; index < deleteDocument.length; index++) {
            deleteDocument[index].checked = deleteAll.checked;
        }
    }

    /*表单提交*/
    function student_listSubmit() {
        var student_form = document.getElementById("student_form"); // 搜索
        student_form.submit();
    }


    //设置内页框架布局
    $(function () {
        $("#Sellerber").frame({
            float: 'left',
            color_btn: '.skin_select',
            Sellerber_menu: '.list_content',
            page_content: '.list_show',//内容
            datalist: ".datatable_height",//数据列表高度取值
            header: 65,//顶部高度
            mwidth: 200,//菜单栏宽度

        });
    });

    //树状图插件
    $(".tree").ProTree({
        arr: arr,//数据
        simIcon: "fa fa-file-text-o",//单个标题字体图标 不传默认glyphicon-file
        mouIconOpen: "fa fa-folder-open",//含多个标题的打开字体图标  不传默认glyphicon-folder-open
        mouIconClose: "fa fa-folder",//含多个标题的关闭的字体图标  不传默认glyphicon-folder-close

    })

    function userinfo(id) {
        layer.open({
            type: 1,
            title: '用户信息',
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: ['600px', ''],
            content: $('#userinfo_style'),
        })
    }

    /*用户-编辑*/
    /*用户-编辑*/
    var stuNo = "";
    var name = "";
    var age = "";
    var sex = "";
    function member_edit(id) {
        $.ajaxSettings.async = false;
        $.get(
            "${pageContext.request.contextPath}/studentList?method=updateStudent",
            "id="+id,
            function(data){
                stuNo = data.stuNo;
                name = data.name;
                age = data.age;
                sex = data.sex;
            },
            "json"
        );



        document.getElementById("stuNo").value = stuNo;
        document.getElementById("name").value = name;
        document.getElementById("age").value = age;
        // document.getElementById("sex").value = student.sex;
        if (sex == "1") {
            document.getElementById("sex1").checked = true;
        }else {
            document.getElementById("sex0").checked = true;
        }
        layer.open({
            type: 1,
            title: '修改用户信息',
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: ['800px', ''],
            content: $('#edit_menber_style'),
            btn: ['提交', '取消'],
            yes: function (index, layero) {
                var num = 0;
                var str = "";
                $("#edit_menber_style input[type$='text']").each(function (n) {
                    if ($(this).val() == "") {

                        layer.alert(str += "" + $(this).attr("name") + "不能为空！\r\n", {
                            title: '提示框',
                            icon: 0,
                        });
                        num++;
                        return false;
                    }
                });
                if (num > 0) {
                    return false;
                }
                else {
                    layer.alert('修改成功！', {
                        title: '提示框',
                        icon: 1,
                    });
                    layer.close(index);
                    document.getElementById("updateForm").submit();
                }
            }
        });
    }

    /*用户-删除*/
    function member_del(id) {
        layer.confirm('确认要删除吗？', function (index) {
            deleteByStuNo(id);
        });
    }

</script>

