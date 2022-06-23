<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
response.setStatus(200);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>오류원인</h1>
<%=exception.getMessage() %>
<h2>오류 스택</h2>
<c:forEach items="${ ex.getStackTrace()}" var="stackmsg">
<c:out value="${stackmsg}"></c:out><br>
</c:forEach>

</body>
</html>