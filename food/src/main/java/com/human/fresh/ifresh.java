package com.human.fresh;

import java.util.ArrayList;

import com.human.fresh.findDTO;

public interface ifresh {
	//메뉴타입 리스트
	ArrayList<stypeVO> s_type();
	//가게등록하기
	void insertStore(String m_id, String s_name, String post, String s_address, String s_detailaddress,
					String s_extraaddress, String s_bs_num, String s_mobile,int s_type, String s_img);
	//메뉴등록하기
	void insertmenu(String s_seq, String name, int price, String ex, String img, String cal);
	//메뉴 선택 detail
	ArrayList<menuVO> selectMenulit(String s_se);
	//메뉴 수정하기
	menuVO updateMenuList(int m_seq, String s_seq);
	void modifyMenu(String name, int price, String ex, String img, String cal, int m_seq, String s_seq);
	//메뉴 삭제하기
	void deleteMenu(int m_seq, String s_seq);
	//가게 존재 여부(1,0)
   int cntStore(String m_id);
   //가게 정보 조회
   storeVO selStore(String m_id);

}
