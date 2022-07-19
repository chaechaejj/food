<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${type}</title>
</head>
<style>
.error{
    color:red;
    font-weight: 700px;
    font-size: 11px;
    height: 2px;
}

</style>
<body>
<h3>회원가입</h3>
<form id=frmAdduser method=post action="usersign">
	<c:if test="${type == 'userSign' }">
		<input type="hidden" id=m_type name=m_type value="3">
	</c:if>
	<c:if test="${type == 'bossSign' }">
		<input type="hidden" id=m_type name=m_type value="2">
	</c:if>
	<table>
		<tr>
			<td>id</td>
			<td>
				<input type="text" id=userid name=userid>
				<input type=button id=idoverlap value='중복확인'>
				<p id=idError class=error></p>
				<input type=hidden id=check>
			</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>
				<input type="password" id=userpwd name=userpwd maxlength=14>
				<p id=pwdError class=error></p>
			</td>
		</tr>
		<tr>
			<td>비밀번호확인</td>
			<td>
				<input type="password" id=userpwd2 maxlength=14>
				<p id=pwdError2 class=error></p>
			</td>
		</tr>
		<tr>
			<td>이름</td>
			<td>
				<input type="text" id=username name=username maxlength=15>
				<p id=nameError class=error></p>
			</td>
		</tr>
		<tr>
			<td>연락처</td>
			<td>
				<input type="text" id=usermobile name=usermobile maxlength=11  placeholder="010부터 입력하세요">
				<p id=mobileError class=error></p>
			</td>
		</tr>
		<tr>
			<td>e-mail</td>
			<td>
				<input type="text" id=useremail name=useremail>
				<p id=emailError class=error></p>
			</td>
		</tr>
		<tr>
			<td rowspan="3">주소</td>
			<td>
				<input type="text" id=postcode name=postcode placeholder="우편번호" style="width:80px">&nbsp;
				<input type="button" id=btnAddress value="우편번호찾기">
			</td>
		</tr>
		<tr>
			<td>
				<input type="text" id=address name=address placeholder="주소" readonly>
			</td>
		</tr>
		<tr>
			<td>
				<input type="text" id=detailAddress name=detailAddress placeholder="상세주소">
				<input type="text" id=extraAddress name=extraAddress placeholder="참고항목" readonly>
				<p id=addressError class=error></p>
			</td>
		</tr>

	</table>
	<input type=submit id=btnSign value='가입'>&nbsp;
	<a href=signin><input type=button value='뒤로가기'></a>
</form>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
$(document)

//mail 유효성검사
.on('blur','#useremail',function(){
	var mailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	var mailTest=mailRegex.test($('#useremail').val());
	if(!mailTest){
		emailError.innerHTML='메일주소를 확인해 주세요';
		$('#useremail').focus();
		return false;
	}else{
		emailError.innerHTML='';
	}
})

//휴대폰번호 유효성검사
.on('blur','#usermobile',function(){
	if($('#usermobile').val() == '' || $('#usermobile').val().length != 11 || isNaN($('#usermobile').val())){
		mobileError.innerHTML='휴대폰 번호를 정확히 입력해 주세요';
		$('#usermobile').focus();
		return false;
	}else{
		mobileError.innerHTML='';
	}
})

//이름 유효성검사
.on('blur','#username',function(){
	var nameRegex = /^[가-힣]{2,15}$/; 
	var nameTest=nameRegex.test($('#username').val());
	if(!nameTest){
		nameError.innerHTML='이름은 한글로만 작성해주세요';
		$('#username').focus();
		return false;
	}else{
		nameError.innerHTML='';
	}
})

//비밀번호 대소문자,숫자 사용하여 5~14자 체크
.on('blur','#userpwd',function(){
	var pwdRegex = /^[a-zA-z0-9]{5,14}$/;
	var passWordTest=pwdRegex.test($('#userpwd').val());
	if(passWordTest){
		pwdError.style.color='blue';
		pwdError.innerHTML='조건에 맞는 비밀번호입니다';
	}else if(!passWordTest){
		pwdError.style.color='red';
		pwdError.innerHTML='비밀번호는 영문 대소문자와 숫자를 사용하여 5~14자로 작성해 주세요';
		$('#userpwd').focus();
		return false;
	}
})

//id 중복체크, 유효성 검사
.on('click','#idoverlap',function(){
	var idRegex = /^[a-zA-z0-9]{3,14}$/;
	var idTest=idRegex.test($('#userid').val());
	
	if(!idTest){
		idError.innerHTML='ID는 영문 대소문자와 숫자를 사용하여 3~14자로 작성해 주세요';
		$('#userid').focus();
		return false;
	}else{
		$.ajax({
			url:'idoverlap',
			data:{userid:$('#userid').val()},
			dataType:'json',
			type:'get',
			success:function(data){
				if(data == 1){ //아이디가 중복일때
					idError.innerHTML='이미 존재하는 ID입니다.';
					$('#check').val('');
					$('#userid').focus();
				}else{ //중복이 아닐시
					idError.style.color='blue';
					idError.innerHTML='사용가능한 ID입니다.';
					$('#check').val($('#userid').val());
					$('#userpwd').focus();
				}
			}
		})
		idError.style.color='red';
	}
})

//form 태그에 빈칸이 있을 경우 경고
.on('click','#btnSign',function(){
	$('#pwdError, #pwdError2, #nameError, #addressError, #mobileError,#emailError').text('');
	
	
	if($('#check').val() != $('#userid').val()){
		alert('ID 중복확인을 해주세요!!!');
		return false;
	}else if($('#userpwd2').val()==''){
		pwdError2.innerHTML='비밀번호 확인을 입력해 주세요';
		$('#userpwd2').focus();
		return false;
	}else if($('#username').val()==''){
		nameError.innerHTML='이름를 입력해 주세요';
		$('#username').focus();
		return false;
	}else if($('#useremail').val()==''){
		emailError.innerHTML='이메일을 입력해 주세요';
		$('#useremail').focus();
		return false;
	}else if($('#postcode,#address,#detailAddress').val()=='' || $('#postcode').val()=='' || $('#address').val()=='' || $('#detailAddress').val()==''){
		addressError.innerHTML='주소를 입력해 주세요';
		return false;
	}else if($('#userpwd').val()!=$('#userpwd2').val()){
		pwdError2.innerHTML='비밀번호가 동일하지 않습니다';
		$('#userpwd').focus();
		return false;
	}
	return true;
})

//우편번호, 우편번호찾기 눌럿을시 API 실행
.on('click','#btnAddress',function(){
	roadMap();
})
.on('click','#postcode',function(){
	roadMap();
})
.on('keyup','#postcode',function(){
	roadMap();
})

//주소 찾기 API 함수
function roadMap(){
	 new daum.Postcode({
	        oncomplete: function(data) {
	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var addr = ''; // 주소 변수
	            var extraAddr = ''; // 참고항목 변수

	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                addr = data.roadAddress;
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                addr = data.jibunAddress;
	            }

	            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	            if(data.userSelectedType === 'R'){
	                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있고, 공동주택일 경우 추가한다.
	                if(data.buildingName !== '' && data.apartment === 'Y'){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                if(extraAddr !== ''){
	                    extraAddr = ' (' + extraAddr + ')';
	                }
	                $('#extraAddress').val(extraAddr);
	            
	            } else {
	            	$('#extraAddress').val('');
	            }

	            $('#postcode').val(data.zonecode);
	            $('#address').val(addr);

	            $('#detailAddress').focus();
	        }
	    }).open();
}
</script>
</html>