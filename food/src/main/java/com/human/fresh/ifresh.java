package com.human.fresh;

import java.util.ArrayList;

import com.human.fresh.findDTO;

public interface ifresh {
	//�޴�Ÿ�� ����Ʈ
	ArrayList<stypeVO> s_type();
	//���Ե���ϱ�
	void insertStore(String m_id, String s_name, String post, String s_address, String s_detailaddress,
					String s_extraaddress, String s_bs_num, String s_mobile,int s_type, String s_img);
	//�޴�����ϱ�
	void insertmenu(String s_seq, String name, int price, String ex, String img, String cal);
	//�޴� ���� detail
	ArrayList<menuVO> selectMenulit(String s_se);
	//�޴� �����ϱ�
	menuVO updateMenuList(int m_seq, String s_seq);
	void modifyMenu(String name, int price, String ex, String img, String cal, int m_seq, String s_seq);
	//�޴� �����ϱ�
	void deleteMenu(int m_seq, String s_seq);
	//���� ���� ����(1,0)
   int cntStore(String m_id);
   //���� ���� ��ȸ
   storeVO selStore(String m_id);

}
