<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가게 등록</title>
</head>
<body>
<div class="store-box">
	<div class ="name-box">
		<span><h3>가게등록하기</h3></span>
		<input type=hidden id=m_id value="${userinfo}">
	</div>
	<p>가게 이름</p><input type=text id=storename />
	<p>가게 주소</p><input type=text id=address />
	<p>사업자등록번호</p><input tpe=text id=num />
	<p>가게 전화번호</p><input tpe=text id=tele />
	<p>메뉴타입</p><select id=menutype></select>
	<p>가게 대표 이미지</p>
	<input type=file id=s_img><br><br>
	<input type=submit id=btnUp value="등록하기">
</div>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>
$(document)
.ready(function(){
	loadstype();
})

//가게 등록하기
.on('click','#btnUp',function(){
	let sid="master"; /* 변경하기 */
	let sname=$('#storename').val();
	let saddress=$('#address').val();
	let snum=$('#num').val();
	let stel=$('#tele').val();
	let smenu=$('#menutype option:selected').val();
	let simg=$('#s_img').val();
	
	console.log("sid=["+sid+"]"+"sname=["+sname+"]"+"saddress=["+saddress+"]"+"snum=["+snum+"]"+
			"stel=["+stel+"]"+"smenu=["+smenu+"]"+"simg=["+simg+"]");
	$.ajax({
		url:'store', type:'post', dataType:'json',
		data:{sid:sid, sname:sname, saddress:saddress, snum:snum, stel:stel, 
				smenu:smenu, simg:simg},
		success: function(){
					
		}
	})
})

//메뉴타입선택
function loadstype(){
	$.ajax({
		url:'mls', data:'', dataType:'json', type:'post',
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
</script>

</html>