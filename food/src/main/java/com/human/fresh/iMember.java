package com.human.fresh;

import java.util.ArrayList;

public interface iMember {
	
	int idOverlap(String m_id);
	void addMember(String m_id,
			String m_pwd,
			String m_name,
			String mobile,
			int m_type,
			String m_postcode,
			String m_address,
			String m_detailAddress,
			String m_extraAddress,
			String m_email);
	void addDelivery(String m_id,
			String m_address,
			String m_postcode,
			String m_detailaddress,
			String m_extraaddress,
			String d_name,
			String d_mobile);
	
	int checkId(String m_id,
			String m_pwd);
	int checkType(String m_id,
			String m_pwd);
	
	mDTO userList(String m_id);
	int cntAddress(String m_id);
	void updatePwd(String m_pwd,
			String m_id);
	void updateLogin(String m_name,
			String m_mobile,
			String m_email,
			String m_id);
	
	void delInformation(String m_id);
	void delDelivery(String m_id);
	
	ArrayList<mDTO> deliveryList(String m_id);
	void upDelivery(String m_postcode,
			String m_address,
			String m_detailaddress,
			String m_extraaddress,
			String m_id);
	mDTO seList(int d_seqno);
	void upDeliveryList(String m_postcode,
			String m_address,
			String m_detailaddress,
			String m_extraaddress,
			String d_name,
			String d_moblie,
			int d_seqno);
	void delSe(int d_seqno);
}
