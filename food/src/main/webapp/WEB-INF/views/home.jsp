<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<style>
a:hover{
	cursor:pointer; 
}
</style>
<body>
<c:if test="${userinfo == '' }">
	<p align=right><a href='login'>로그인</a>&nbsp;<a href='signin'>회원가입</a></p>
</c:if>
<c:if test="${userinfo != '' }">
	<c:if test="${userType == '손님' }">
		<p align=right><a onclick=location.href='signUp'>${userinfo} 님🍮</a> &nbsp;<a href='logout'>로그아웃</a></p>
	</c:if>
	<c:if test="${userType == '사장님' }">
		<p align=right><a onclick=location.href='signUp'>${userinfo} 님👩🏻‍🍳</a> &nbsp;<a href='logout'>로그아웃</a></p>
	</c:if>
</c:if>

</body>
</html>
