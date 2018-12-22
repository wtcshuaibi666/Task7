<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/19
  Time: 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
<div class="from-group">
    <h1 style="text-align: center">人员信息</h1>
    <hr/>
    <form method="post" name="form">
        <div class="form-group">
            <label for="username">账号名:</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="输入账号名"/>
        </div>
        <div class="form-group">
            <label for="password">密码:</label>
            <input type="text" class="form-control" id="password" name="password" placeholder="输入姓名"/>
        </div>

        <%--<div class="form-group">--%>
            <%--<label for="desire">立愿:</label>--%>
            <%--<input type="text" class="form-control" id="desire" name="userWords" placeholder="立愿"/>--%>
        <%--</div>--%>
        <div>
            <input type="text" name="phone" id="phone" placeholder="请输入手机号码" required="required"/>
            <button type="button" onclick="sendBtn()">获取验证码</button>
            <input type="text"  id="verifyCode" placeholder="请输入验证码" required="required">
        </div>

        <div class="form-group">
            <input type="submit" id="_submit" value="提交"/>
        </div>
    </form>
</div>
</body>
</html>>
