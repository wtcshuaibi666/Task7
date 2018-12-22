<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
<h1 style="text-align: center">错误信息界面</h1>
<c:if test="${allErrors!=null&& allErrors.size()>0}" >
    <c:forEach items="${allErrors}" var="error">
        ${error.defaultMessage}<br/>
    </c:forEach>
</c:if>
<a href="<%=basePath%>/form">返回用户界面</a>
</body>
</html>