<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Find password</title>
</head>
<style>
table{border-collapse:collapse;		
	  border:1px solid;}
#userid,#findname,#findmobile{text-decoration:none;}
</style>
<body>
<form id=frmFind method=post action='pw_auth'>
	<h3>비밀번호 찾기</h3>
	<p>아이디<input type=text id=userid name=userid></p>
	<p>이름<input type=text id=name name=name></p>
	<p>이메일<input type=email name=email id=email required="/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)?$/i"></p>
	<input type=submit id=findit value='찾기'>
	<input type=button id=btncancle value='취소/돌아가기'>
</form>

</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>
$(document)
.on('click','#btncancle',function(){
	document.location="login";
})

.on('click','#findit',function(){
	if($('#userid').val()=='' || $('#findname').val()=='' || $('#findmobile').val()==''){
		alert('정보를 입력 하세요');
		return false;
	}else{
		document.location="f_pw2";
		return true;
	}
	
})
</script>
</html>