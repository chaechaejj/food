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
}
#table-box{
	background:pink;
	overflow:scroll;
	width:300px;
	height:400px;
}
#tbl, tr, td{
	border-collapse:collapse;
	border:1px solid brown;
}
</style>
<body>
<div id="menuin-box" style="float:left;width:400px;height:500px">
	<span><h3>메뉴 등록하기</h3></span>
	<input type=hidden id=s_seq value="${store_seq}"> <!-- 가게시퀀스 -->
	<p>메뉴이름</p><input type=text id=memuname />
	<p>가격</p><input type=number id=menuprice min=0 val=0 />원
	<p>칼로리</p><input type=number id=menukcal min=0 />kcal
	<p>설명</p><input tpe=text id=menuex />
	<p>메뉴이미지</p><input type=file id=m_img><br><br>
	<input type=submit id=btnIn value="추가">&nbsp;&nbsp;
	<input type=button id=btnReset value="비우기">&nbsp;&nbsp;
	<input type=button id=btnDelete value="삭제">
</div>


<div id="list-box" style="float:left;width:400px;height:500px;">
<span><h3>메뉴목록</h3></span>
	<div id="table-box">
		<input type=hidden id="hdseq" value="${s_seq}" />
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

.on('click','#btnReset',function(){
	$('#memuname').val('');
	$('#menuprice').val('');
	$('#menukcal').val('');
	$('#menuex').val('');
	$('#m_img').val('');
});

.on('click','#btnDelete',function(){
	$.ajax({
		url:'d_menu', type:'post', dataType:'text', data:{}
	})
})


//메뉴 등록
.on('click','#btnIn',function(){
	let s_seq= '23';     //$('#s_seq').val(); /* 가게 시퀀스 */
	let m_name=$('#memuname').val();
	let m_price=$('#menuprice').val();
	let m_ex=$('#menuex').val();
	let m_img=$('#m_img').val();
	let m_cal=$('#menukcal').val();
	
	console.log("s_seq["+s_seq+"], m_name["+m_name+"], m_price["+m_price+"], m_ex["+m_ex+"], m_img["+m_img+"], m_cal["+m_cal+"]");
	if($('#btnIn').val()=="추가"){
		$.ajax({
			url:'menu', type:'post', dataType:'json',
			data:{s_seq:s_seq, m_name:m_name, m_price:m_price, m_ex:m_ex, m_img:m_img, m_cal:m_cal},
			success: function(){
				loadmenulist();
				$('#btnReset').trigger('click');
			}
		})
	}else if($('#btnIn').val()=="수정"){
		$.ajax({
			url:'menumdf', type:'post', dataType:'json',
			data:{s_seq:s_seq, m_name:m_name, m_price:m_price, m_ex:m_ex, m_img:m_img, m_cal:m_cal},
			success: function(){
				loadmenulist();
				$('#btnReset').trigger('click');
			}
		})
	}
});

//메뉴목록 리스트 보여주기
function loadmenulist(){
	let s_seq='23';    //$('#hdseq').val();
	$.ajax({
		url:'mls', type: 'post', dataType:'json', data:{s_seq:s_seq},
		success: function(data){
			$('#tbl').empty();
			let str='';
			for(let i=0;i<data.length;i++){
				let jo=data[i];
				str+="<tr><td rowspan=4 value="+jo['m_seq']+">"+jo['m_img']+"</td><td>메뉴이름</td><td>"+jo['m_name']
						+"</td><td>가격</td><td>"+jo['m_price']
						+"원</td></tr><tr><td>칼로리</td><td colspan=3 align=left>"
						+jo['m_cal']+"kcal</td></tr><tr><td colspan=4 align=left>설명</td></tr><tr><td colspan=4>"
						+jo['m_ex']+"</td></tr>";
			}
			$('#tbl').append(str);
				
		}
	})
}


</script>
</html>