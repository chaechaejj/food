<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>found password</title>
</head>
<body>

<p>고객님의 비밀번호는 아래와 같습니다.</p>
<p style='color:red;'>${fdto.m_pwd}</p>
<input type=button id=btnReturn value="로그인하러가기">
</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>
$(document)
.on('click','#btnReturn',function(){
	document.location="login";
})
</script>
</html>