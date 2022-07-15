<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>
<form id=frmLogin method=post action='user_check'>
<p>아이디:<input type=text name=userid></p>
<p>비밀번호:<input type=password name=pwd></p>
<input type=submit value='로그인'>&nbsp;<input type=button id=btnFind value='비밀번호 찾기'>
</form>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>
$(document)
.on('click','#btnFind',function(){
	document.location="f_pw";
})
</script>
</html>