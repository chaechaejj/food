<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
p{
   padding: 0 25px 0 0;
}
div{
   height:50px;
}
#storeIntro,#storeIntro *{
   border:1px solid red;
}
footer{
   background-color: darksalmon;
   position : fixed;
   width:100%;
   bottom : 0;
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
<c:if test="${userType == '손님' }">
   <div onclick=location.href='deliveryUp'>
      <p>배송지 관리 (총 ${cnt }건)</p>
      <p>현재 주소지: ${mdto.m_address } ${mdto.m_extraAddress}, ${mdto.m_detailAddress }</p>
   </div>
</c:if>
<c:if test="${userType == '사장님' }">
   <div>
      <c:if test="${cntStore == 0}">
         <a>등록된 가게가 없습니다. 가게를 등록해 주세요.</a><br>
         <input type=button id=btn_s value="가게등록하기" method='get' onclick="location.href='s_up'"><br>
      </c:if>
      <c:if test="${cntStore == 1}">
         <table id="storeIntro">
            <tr>
               <td rowspan=4><c:if test="${sVO.s_img==null}">이미지를 넣어주세요 </c:if>${sVO.s_img}</td>
               <td>가게명:</td><td>${sVO.s_name}</td>
            </tr>
            <tr>
               <td>전화번호:</td><td>${sVO.s_mobile }</td>
            </tr>
            <tr>
               <td>가게주소:</td><td>${sVO.s_address} ${sVO.s_extraaddress}</td>
            </tr>
         </table>
      </c:if>
   </div>
</c:if>
<footer>
   <p align=right><a href="/fresh">돌아가기</a>&nbsp;&nbsp;&nbsp;<a id=delInformation>회원탈퇴</a></p>
</footer>

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