package com.human.fresh;

import java.util.ArrayList;

import com.human.fresh.findDTO;

public interface ifresh {
//	findDTO findpwd(String userid, String name, String email);
//	findDTO find_auth(String email);
	
	ArrayList<stypeVO> s_type();
	void isertStore(String m_id, String s_name, String s_address, String s_bs_num, String s_mobile,
				int s_type, String s_img);
	
}
