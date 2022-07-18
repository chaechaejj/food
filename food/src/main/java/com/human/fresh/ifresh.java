package com.human.fresh;

import java.util.ArrayList;

import com.human.fresh.findDTO;

public interface ifresh {
//	findDTO findpwd(String userid, String name, String email);
//	findDTO find_auth(String email);
	
	ArrayList<stypeVO> s_type();
	void insertStore(String m_id, String s_name, String post, String s_address, String s_detailaddress,
					String s_extraaddress, String s_bs_num, String s_mobile,int s_type, String s_img);
	void insertmenu(String s_seq, String name, int price, String ex, String img, String cal);
	ArrayList<menuVO> selectMenulit(String s_se);
	
}
