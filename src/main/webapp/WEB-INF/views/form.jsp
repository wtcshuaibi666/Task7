<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/19
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>表格</title>
</head>
<body>


<table cellpadding="0" cellspacing="0" border="1"
       style=" border-collapse: collapse;min-height: 300px;width: 1800px;text-align: center;">
    <tr>
        <td colspan="20" align="left">
            <a href="${pageContext.request.contextPath}/updateImage">添加图片</a>
        </td>
    </tr>

    <tr><td>序号</td>
        <td>账号</td>
        <td>密码</td>
        <td>电话号码</td>
        <td>邮件</td>
        <td>相片</td>
        <td>修改</td>
        <td>验证邮箱</td>
        <td>删除</td>    </tr>
    <c:forEach items="${user}" var="user" varStatus="i">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.phone}</td>
            <td>${user.email}</td>
            <td>${user.picture}</td>

            <td><a href="${pageContext.request.contextPath}/getUser/${user.id}"
                   class="btn btn-info btn-sm">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                修改</a>
            </td>
            <td><a href="${pageContext.request.contextPath}/user/${user.id}/profileMail"
                   class="btn btn-info btn-sm">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                验证邮箱</a>
            </td>

            <td><form action="${pageContext.request.contextPath}/delUser/${user.id}" method="post">
                <input type="hidden" name="_method" value="DELETE">
                <input TYPE="submit" value="删除"></form>
            </td>

        </tr>
    </c:forEach>
</table>
</body>
</html>