<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메뉴 등록</title>
</head>
<style>
#menuin-box{
	background:skyblue;
	
}
#list-box{
	background:yellow;
	/* margin:10px auto; */
/*  	display:flex;
	flex-wrap:wrap;
	flex:none; */
}
/* #list-box >span >h3{
	padding: 0 100px 0 100px;
}
 */
#table-box{
	background:pink;
	overflow:scroll;
	width:400px;
	height:400px;
	margin:auto;
}
#tbl, tr, td{
	border-collapse:collapse;
	border:1px solid brown;
}
</style>
<body>
<div id="menuin-box" style="float:left;width:400px;height:500px">
<form action="menu" method="post" enctype="multipart/form-data">
	<span><h3>메뉴 등록하기</h3></span>
	<input type=text id=s_seq name=s_seq value="${s_seq}"> <!-- 가게시퀀스 -->
	<input type=text id="m_seq" name="m_seq" value="0">
	<p>메뉴이름</p><input type=text id=menuname name=menuname >
	<p>가격</p><input type=number id=menuprice name=menuprice min=0 val=0 >원
	<p>칼로리</p><input type=text id=menukcal name=menukcal min=0 >kcal
	<p>설명</p><input type=text id=menuex name=menuex >
	<p>메뉴이미지</p>
	<input type=file id=m_img name=file multiple="multiple">&nbsp;&nbsp;
	<img id="view"><br><br>
	<input type=submit id=btnIn value="추가">&nbsp;&nbsp;
	<input type=button id=btnReset value="비우기">&nbsp;&nbsp;
	<input type=button id=btnDelete value="삭제">
</form>
</div>


<div id="list-box" style="float:left;width:400px;height:500px;">
<span><h3>메뉴목록</h3></span>
	<div id="table-box">
		<input type=text id="s_seq2" value="${s_seq}">
		<table id="tbl"></table>
	</div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>
$(document)
.ready(function(){
	loadmenulist();
})
//비우기 버튼
.on('click','#btnReset',function(){
	$('#menuname').val('');
	$('#menuprice').val('');
	$('#menukcal').val('');
	$('#menuex').val('');
	$('#m_img').val('');
	const img=document.getElementById("view");
	img.src="";
	$('#btnIn').val('추가');
	$('#m_seq').val(0);
	
})

//수정 전 데이터 불러오기
.on('click','#btnSel',function(){
	var m_seq=$(this).parent().find('input:eq(1)').val();
	var s_seq=$('#s_seq').val();
	$('#m_seq').val(m_seq);
	$.ajax({
		url:'update', type:'post', dataType:'json',
		data:{s_seq:s_seq, m_seq:m_seq},
		success: function(data){
			$('#btnIn').val('수정');
			let jo=data[0];
			$('#menuname').val(jo['m_name']);
			$('#menuprice').val(jo['m_price']);
			$('#menukcal').val(jo['m_cal']);
			$('#menuex').val(jo['m_ex']);
			const img=document.getElementById("view");
				img.src="/test/"+jo['m_img'];
		}
	})
})

//메뉴삭제하기
.on('click','#btnDelete',function(){
	//console.log("delete=>s_seq["+$('#s_seq').val()+"], m_seq["+$('#m_seq').val()+"]");
	let s_seq=$('#s_seq').val();
	let m_seq=$('#m_seq').val();
	
	if(!confirm("선택한 메뉴를 삭제하시겠습니까?")) return false;
	document.location="del?m_seq="+m_seq+"&s_seq="+s_seq;
	loadmenulist();
	$('#btnReset').trigger('click');
})


//메뉴목록 리스트 보여주기
function loadmenulist(){
	let s_seq=$('#s_seq').val();
	$.ajax({
		url:'mls', type: 'post', dataType:'json', data:{s_seq:s_seq},
		success: function(data){
			$('#s_seq').val(s_seq);
			$('#s_seq2').val(s_seq);
			$('#tbl').empty();
			let str='';
			if(data.length==0){
				$('#tbl').text("메뉴를 등록하세요");
			}else{
				for(let i=0;i<data.length;i++){
					let jo=data[i];
					str+="<tr><td rowspan=4><img src='/test/"
						+jo['m_img']+"'></td><td>메뉴이름</td><td>"+jo['m_name']
						+"</td><td>가격</td><td>"+jo['m_price']
						+"원</td><td rowspan=4><input type=button id=btnSel value=선택><input type=hidden id=mse value='"+jo['m_seq']
						+"'></td></tr><tr><td>칼로리</td><td colspan=3 align=left>"
						+jo['m_cal']+"kcal</td></tr><tr><td colspan=4 align=left>설명</td></tr><tr><td colspan=4>"
						+jo['m_ex']+"</td></tr>";
				}
				$('#tbl').append(str);
			}
		}
	})
}
</script>
</html>