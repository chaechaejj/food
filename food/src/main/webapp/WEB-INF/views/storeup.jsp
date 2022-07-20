<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가게 등록</title>
</head>
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


<div class="store-box">
	<div class ="name-box">
		<span><h3>가게등록하기</h3></span>
		<input type=text id=m_id value="${userinfo}">
	</div>
	<p>가게 이름</p><input type=text id=storename />
	<p>가게 주소</p>
		<input type="text" id=postcode name=postcode placeholder="우편번호" style="width:80px">&nbsp;
		<input type="button" id=btnAddress value="우편번호찾기"><br>
		<input type="text" id=address name=address placeholder="주소" readonly><br>
		<input type="text" id=detailAddress name=detailAddress placeholder="상세주소">
		<input type="text" id=extraAddress name=extraAddress placeholder="참고항목" readonly>

	<p>사업자등록번호</p><input tpe=text id=num />
	<p>가게 전화번호</p><input tpe=text id=tele />
	<p>메뉴타입</p><select id=menutype></select>
	<p>가게 대표 이미지</p>
	<input type=file id=s_img><br><br>
	<input type=submit id=btnUp value="등록하기">
</div>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
$(document)
.ready(function(){
	loadstype();
})

//가게 등록하기
.on('click','#btnUp',function(){
	let sid="master"; /* 변경하기 */
	let sname=$('#storename').val();
	let post=$('#postcode').val();
	let saddress=$('#address').val();
	let sdetail=$('#detailAddress').val();
	let sextra=$('#extraAddress').val();
	let snum=$('#num').val();
	let stel=$('#tele').val();
	let smenu=$('#menutype option:selected').val();
	let simg=$('#s_img').val();
	
	console.log("sid=["+sid+"], sname=["+sname+"], post["+post+"], saddress=["+saddress
				+"], sdetail=["+sdetail+"], sextra=["+sextra+"], snum=["+snum+"], stel=["+stel
				+"], smenu=["+smenu+"]"+"simg=["+simg+"]");
	$.ajax({
		url:'store', type:'post', dataType:'json',
		data:{sid:sid, sname:sname, post:post, saddress:saddress, sdetail:sdetail, 
			  sextra:sextra, snum:snum, stel:stel, smenu:smenu, simg:simg},
		success: function(){
			sid=$('#m_id').val('');
			sname=$('#storename').val('');
			post=$('#postcode').val('');
			saddress=$('#address').val('');
			sdetail=$('#detailAddress').val('');
			sextra=$('#extraAddress').val('');
			snum=$('#num').val('');
			stel=$('#tele').val('');
			smenu=$('#menutype option:selected').remove();
			simg=$('#s_img').val('');
			/* 메인화면으로 이동하기 */		
		}
	})
})

.on('click','#btnAddress',function(){
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


//메뉴타입선택
function loadstype(){
	$.ajax({
		url:'mtp', data:'', dataType:'json', type:'post',
		success: function(data){
			$('#menutype').empty();
			$('#menutype').append('<option value=0></option>');
			for(let i=0;i<data.length;i++){
				console.log(data);
				let jo=data[i];
				let str='<option value='+jo['s_type']+'>'+jo['type_name']+'</option>';
				$('#menutype').append(str);
			}
		}
	})
}

//가게 등록화면 보여주기 (수정버튼 만들기/ 메뉴등록 버튼 만들기)
function loadStorelist(){
	
	
	
}

</script>

</html>