<%--
  Created by IntelliJ IDEA.
  User: Mps
  Date: 2018/3/5
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<style>
    body {
        background: url("${pageContext.request.contextPath}/img/bg.jpeg") center no-repeat;;
    }
</style>
<body >

<div>
        <table id="table">
            <tr>
                <td>用户名：</td>
                <td><input id="username" type="text" name="username"></td>
            </tr>
            <tr>
                <td></td>
                <td> <p id="userbox" > </p></td>
            </tr>
            <tr>
                <td>密&nbsp;&nbsp;码：</td>
                <td><input id="password" type="password" name="password"></td>
            </tr>
            <tr>
                <td></td>
                <td> <p id="msgbox" > </p></td>
            </tr>
            <tr>
                <td><button id="loginbtn" >登录</button></td>
                <td><button id="resetbtn"  >重置</button></td>
            </tr>
        </table>

</div>
</body>
<script>
    $(document).ready(function(){
        $("#loginbtn").click(function(){
          var username= $("#username").val();
          var password=$("#password").val();
          if(username==''&& username ==null){
            $("#userbox").text("请输入用户名");
              return;
          }else{
              $("#userbox").hide();

          }
          if(password==null && password==""){
              $("#msgbox").text("请输入密码");
              return;
          }else{
              $("#msgbox").hide();

          }

            $.ajax({
                url:"${pageContext.request.contextPath}/user/login.do",
                type: "POST",
                data:{"username":username,"password":password},
                success:function(result){
                  var message=  JSON.parse(result);
                    if(message.code==200){
                        window.open("${pageContext.request.contextPath}/user/tostuMag.do");
                    }else {
                        $("#msgbox").text(message.msg);
                        $("#msgbox").show();
                    }
                }});
        });
    });
    $("#resetbtn").click(function () {
        $("#username").val("");
        $("#password").val("");
        $("#msgbox").hide();
    })

</script>
</html>
