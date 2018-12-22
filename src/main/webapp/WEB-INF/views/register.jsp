<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/19
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册界面</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-3.31.min.js"></script>
    <script type="text/javascript">
        //为用户名文本框添加这个方法，在文本框失去焦点以后运行
        //就是当用户填写完用户名后，进行下一步填写的时候，异步执行这个方法
        function checkUsername(){
            //获取用户名文本框对象
            var username = document.getElementsByName("username")[0].value;
            //获取提示信息span对象
            var span1 = document.getElementsByTagName("span")[0];
            if(username == ""){
                span1.innerHTML = "用户名不能为空！";
                return;
            }

            //创建请求对象
            var xmlHttp = new XMLHttpRequest();

            //设置请求状态变化时触发的事件
            xmlHttp.onreadystatechange = function(){
                //如果请求状态码为4，说明请求已经完成，响应已经就绪
                if(xmlHttp.readyState == 4){
                    //响应状态码为200，响应完成
                    if(xmlHttp.status == 200){
                        //获取服务器返回的信息
                        var data = xmlHttp.responseText;
                        //判断返回的信息，输出响应的提示信息
                        if(data == 0){
                            //如果返回0，说明用户名不重复，可以使用
                            span1.innerHTML = "用户名可以使用！";
                        }else{
                            //说明用户名重复，不能使用
                            span1.innerHTML = "用户名已经被注册！";
                        }
                    }
                }
            }
            //创建连接
            xmlHttp.open("GET","/web14_test1/RegisterAJAXServlet?username="+username,true);

            //发送请求
            xmlHttp.send();

        }
    </script>
    <script type="text/javascript">
        function sendBtn(phone) {
            var phone = document.form.phone.value;
            var url = '${pageContext.request.contextPath }/message?phone=' + phone;
            $.ajax({
                url: url,
                type: 'GET',
            });
        }
    </script>
</head>
<body>
<div style="text-align: center">
    <form action="${pageContext.request.contextPath }/register" name="form" method="post">

            <table border="1">
                <tr>
                    <td>用户名</td>
                    <td><input type="text" name="username" onblur="checkUsername()"  placeholder="输入账号名"></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input type="password" name="password" placeholder="密码"></td>
                </tr>
                <tr>
                    <td>手机号</td>
                    <td><input id="phone" name="phone"  type="text"  placeholder="请输入手机号" required="required"></td>
                </tr>
                <tr>
                    <td><button type="button" id="code" onclick="sendBtn()">获取验证码</button></td>
                    <td><input id="sdk" name="verifyCode"  type="text" placeholder="请输入短信验证码"></td>
                </tr>
                <tr>
                    <td><input type="submit" value="注册"></td>
                    <td><input type="reset" value="重置"/></td>

                </tr>
            </table>
    </form>
</div>
</body>
</html>