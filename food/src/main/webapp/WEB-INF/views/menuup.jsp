<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴 등록</title>
</head>
<body>
<div class="menuin-box" style="float:left;width:300px;height:500px">
	<span><h3>메뉴 등록하기</h3></span>
	<input type=hidden value="${store_seq}"> <!-- 가게시퀀스 -->
	<p>메뉴이름</p><input type=text id=memuname />
	<p>가격</p><input type=text id=menuprice />
	<p>설명</p><input tpe=text id=menuex />
	<p>메뉴이미지</p><input type=file id=s_img><br><br>
	<input type=submit id=btnIn value="추가">&nbsp;&nbsp;
	<input type=button id=btnReset value="비우기">&nbsp;&nbsp;
	<input type=button id=btnDelete value="삭제">
</div>

<div class="list-box" style="float:left;width:300px;height:500px">
<span><h3>메뉴목록</h3></span>
<select id="menuList" size=20 style="width:300px;"></select>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>




</script>
</html>