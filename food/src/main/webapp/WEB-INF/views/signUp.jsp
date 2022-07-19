<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>signUpdate</title>
</head>
<style>
.error{
    color:red;
    font-weight: 700px;
    font-size: 11px;
    height: 2px;
}
div:hover{
	cursor:pointer; 
}
div{
	height:50px;
}

</style>
<body>

<h3>계정 정보</h3>
<div onclick=location.href='checkpwd'>
	<p>${mdto.m_name } 님</p>
</div>
<div onclick=location.href='informationUp'>
	<p>로그인 정보</p>
</div>
<div onclick=location.href='deliveryUp'>
	<p>배송지 관리 (총 ${cnt }건)</p>
	<p>현재 주소지: ${mdto.m_address } ${mdto.m_extraAddress}, ${mdto.m_detailAddress }</p>
</div>
<div>
	<p align=right><a id=delInformation>회원탈퇴</a></p>
</div>

</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
$(document)
.on('click','#delInformation',function(){
	answer=confirm("정말 탈퇴하시겠습니까??");
	if(answer){
		location.href='delInformation';
	}
})
</script>
</html>