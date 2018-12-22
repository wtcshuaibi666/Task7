<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>修改页面</title>

    <script type="text/javascript">
        function updateUser() {
            var form = document.forms[0];
            form.action = "<%=basePath %>updateUser";
            form.method = "post";
            form.submit();
        }
    </script>
</head>
<body>
<div style="text-align: center">
    <form action="${pageContext.request.contextPath}/updateUser" name="form" method="post">
        <%--<input hidden="hidden" name="_method" value="post"/>--%>
            <input type="hidden" name="id" value="${user.id }" />
            账号： <input type="text" placeholder="请输入账号" name="username" value="${user.username}" >
       密码：<input type="text" placeholder="请输入密码" name="password" value="${user.password}">
       电话：<input type="text" placeholder="请输入电话号码" name="phone" value="${user.phone}" >
        邮箱：<input type="text" placeholder="请输入邮箱" name="email" value="${user.email}" >
        <%--照片:<input type="text" placeholder="请添加照片" name="picture" value="${user.picture}">--%>

            <input type="button" value="编辑" onclick="updateUser()" />
    </form>
</div>
</body>
</html>