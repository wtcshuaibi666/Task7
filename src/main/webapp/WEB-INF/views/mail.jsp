<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/19
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>mail</title>
</head>
<body>
<div style="text-align: center">
    <form action="${pageContext.request.contextPath }/sendMail" name="form" method ="post">
        <div class="form-group">
            <label for="username">用户名:</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="输入用户名"/>
        </div>
        <div class="form-group">
            <label for="mail">邮箱名:</label>
            <input type="text" class="form-control" id="mail" name="email" placeholder="输入邮箱号名"/>
        </div>
        <div class="form-group">
            <input type="submit"value="提交"/>
        </div>
    </form>
</div>
</body>
</html>
