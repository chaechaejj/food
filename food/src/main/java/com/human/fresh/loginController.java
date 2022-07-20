package com.human.fresh;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class loginController {
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		
		if(session.getAttribute("userid")==null) {
			model.addAttribute("userinfo","");
		}else { //�α��� ���� ��
			model.addAttribute("userinfo",session.getAttribute("userid"));
			model.addAttribute("userType",session.getAttribute("userType"));
		}
		return "home";
	}
	
	@RequestMapping("/dvListup")
	public String dvList(@RequestParam("se") int se, Model model) {
		iMember ime=sqlSession.getMapper(iMember.class);
		mDTO selList = ime.seList(se);
		model.addAttribute("list",selList);
		return "dvList";
	}
	@RequestMapping("/dvList")
	public String dvList(Model model) {
		model.addAttribute("list","");
		return "dvList";
	}
	
	//����� ����
	@RequestMapping("/delSe")
	public String delSe(@RequestParam("se") int se) {
		iMember ime=sqlSession.getMapper(iMember.class);
		ime.delSe(se);
		return "redirect:/deliveryUp";
	}
	
	//����� ����
	@RequestMapping("/upDelivery")
	public String upDelivery(@RequestParam("newname") String newname,
			@RequestParam("newmobile") String newmobile,
			@RequestParam("postcode") String postcode,
			@RequestParam("address") String address,
			@RequestParam("detailAddress") String detailaddress,
			@RequestParam("extraAddress") String extraaddress,
			@RequestParam("checked") String checked,
			@RequestParam("se") int se,
			HttpServletRequest request) {
		HttpSession session=request.getSession();
		String m_id=(String)session.getAttribute("userid");
		iMember ime=sqlSession.getMapper(iMember.class);
		
		if(checked.equals("Y")) { //�⺻����� ����+����� ���
			ime.upDeliveryList(postcode, address, detailaddress, extraaddress, newname,newmobile, se);
			ime.upDelivery(postcode, address, detailaddress, extraaddress,m_id);
		}else { //����� ���
			ime.upDeliveryList(postcode, address, detailaddress, extraaddress, newname,newmobile, se);
		}
		
		return "redirect:/dvList";
	}
	
	//����� �߰�
	@RequestMapping("/addDelivery")
	public String addDelivery(@RequestParam("newname") String newname,
			@RequestParam("newmobile") String newmobile,
			@RequestParam("postcode") String postcode,
			@RequestParam("address") String address,
			@RequestParam("detailAddress") String detailaddress,
			@RequestParam("extraAddress") String extraaddress,
			@RequestParam("checked") String checked,
			HttpServletRequest request) {
		HttpSession session=request.getSession();
		String m_id=(String)session.getAttribute("userid");
		
		iMember ime=sqlSession.getMapper(iMember.class);
		
		if(checked.equals("Y")) { //�⺻����� ����+����� ���
			ime.addDelivery(m_id, address, postcode, detailaddress, extraaddress, newname, newmobile);
			ime.upDelivery(postcode, address, detailaddress, extraaddress,m_id);
		}else { //����� ���
			ime.addDelivery(m_id, address, postcode, detailaddress, extraaddress, newname, newmobile);
		}
		
		return "redirect:/dvList";
	}
	
	//����� ��ȸ,�߰�,����,���� â ����
	@RequestMapping("/deliveryUp")
	public String doDeliveryUp(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		mDTO mdto=ime.userList((String)session.getAttribute("userid"));
		model.addAttribute("mdto",mdto);
		
		ArrayList<mDTO> list=ime.deliveryList((String)session.getAttribute("userid"));
		model.addAttribute("list",list);
		
		return "deliveryUp";
	}
	
	//ȸ��Ż��
	@RequestMapping("/delInformation")
	public String delInformation(HttpServletRequest request) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		ime.delInformation((String)session.getAttribute("userid"));
		ime.delDelivery((String)session.getAttribute("userid"));
		//if ������ ���� Ż���Ͻ� ȸ������,����ּ�,��������,�޴���� ���̺� ����
		//�Ϲ��̿��� Ż���Ͻ� ȸ������,����ּ� ���̺� ���� 
		
		session.invalidate(); //������ִ� ������ �� ����
		
		return "redirect:/";
	}
		
	//�α������� ����
	@RequestMapping(value="/informationUpdate", method=RequestMethod.POST)
	public String information(@RequestParam("username") String name,
			@RequestParam("usermobile") String mobile,
			@RequestParam("useremail") String email,
			HttpServletRequest request) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		ime.updateLogin(name,mobile,email,(String)session.getAttribute("userid"));
		
		return "redirect:/signUp";
	}
	
	//�α������� ����
	@RequestMapping("/informationUp")
	public String doInformationUp(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		mDTO mdto=ime.userList((String)session.getAttribute("userid"));
		model.addAttribute("mdto",mdto);
		
		return "informationUp";
	}
	
	//��й�ȣ ����
	@ResponseBody
	@RequestMapping(value="/pwdNew", method=RequestMethod.POST)
	public String pwdNew(@RequestParam("pwd") String pwd,
			HttpServletRequest request) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		ime.updatePwd(pwd, (String)session.getAttribute("userid"));
		
		return Integer.toString(1);
	}
	
	//���������� �� ��й�ȣ Ȯ�� ����
	@ResponseBody
	@RequestMapping(value = "/pwd_check", method = RequestMethod.POST)
	public String doPwdCheck(@RequestParam("pwd") String pwd,
			HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();

		iMember ime=sqlSession.getMapper(iMember.class);
			
		mDTO mdto=ime.userList((String)session.getAttribute("userid"));
		int check=ime.checkId((String)session.getAttribute("userid"), pwd);
			
		return Integer.toString(check);
	}
	
	//��й�ȣ ���� ����
	@RequestMapping("/checkpwd")
	public String doCheckPwd(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		mDTO mdto=ime.userList((String)session.getAttribute("userid"));
		model.addAttribute("mdto",mdto);
		
		return "checkpwd";
	}
	
	//���������� ����
	@RequestMapping("/signUp")
	public String dosignUp(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		mDTO mdto=ime.userList((String)session.getAttribute("userid"));
		model.addAttribute("mdto",mdto);
		
		int cnt=ime.cntAddress((String)session.getAttribute("userid"));
		model.addAttribute("cnt",cnt);
		
	      ifresh ifr=sqlSession.getMapper(ifresh.class);
	      int cntStore = ifr.cntStore((String)session.getAttribute("userid"));
	      model.addAttribute("cntStore",cntStore);

	      storeVO sVO = ifr.selStore((String)session.getAttribute("userid"));
	      model.addAttribute("sVO",sVO);
	      
	      model.addAttribute("userType",session.getAttribute("userType"));
		//System.out.println(mdto.m_name);
		return "signUp";
	}
	
	//���̵�,��й�ȣ �´��� Ȯ��
	@RequestMapping(value="/user_check",method = RequestMethod.POST)
	public String doUserCheck(@RequestParam("id") String id,
			@RequestParam("pwd") String pwd,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		int check=ime.checkId(id, pwd);
		
		if(check == 1) {
			int checkType=ime.checkType(id, pwd);
			if(checkType == 3) {
				session.setAttribute("userType", "�մ�");
			}else if(checkType == 2) {
				session.setAttribute("userType", "�����");
			}
			session.setAttribute("userid", id);
			//session.setAttribute("username", name);
		}else {
			model.addAttribute("ch","<h5>��ϵ��� ���� �����Դϴ�</h5>");
			return "login";
		}
		return "redirect:/";
	}
	
	//�α���â ����
	@RequestMapping("/login")
	public String doLogin() {
		return "login";
	}
	
	//�α׾ƿ�
	@RequestMapping("/logout")
	public String doLogout(HttpServletRequest req) {
		HttpSession session=req.getSession();
		session.invalidate(); //������ִ� ������ �� ����
		return "redirect:/";
	}
	
	//ȸ������
	@RequestMapping(value = "/usersign", method = RequestMethod.POST)
	public String usersign(@RequestParam("userid") String id,
			@RequestParam("userpwd") String pwd,
			@RequestParam("username") String name,
			@RequestParam("postcode") String postcode,
			@RequestParam("address") String address,
			@RequestParam("detailAddress") String detailaddress,
			@RequestParam("extraAddress") String extraaddress,
			@RequestParam("usermobile") String mobile,
			@RequestParam("useremail") String mail,
			@RequestParam("m_type") int type) {
		
		iMember ime=sqlSession.getMapper(iMember.class);
		
		ime.addMember(id,pwd,name,mobile,type,postcode,address,detailaddress,extraaddress,mail);
		ime.addDelivery(id, address, postcode, detailaddress, extraaddress,name,mobile);
		
		return "redirect:/login";
	}
	
	//���̵� �ߺ�Ȯ��
	@ResponseBody
	@RequestMapping(value = "/idoverlap")
	public String idOverlap(@RequestParam("userid") String id) {
		iMember ime=sqlSession.getMapper(iMember.class);
		
		int check=ime.idOverlap(id);
		return Integer.toString(check);
	}
	
	//ȸ������â ����
	@RequestMapping("/userSign")
	public String userSign(@RequestParam("Sign") String type, Model model) {
		model.addAttribute("type",type);
		return "userSign";
	}
	
	//����,�մ� ���� ȸ������
	@RequestMapping("/signin")
	public String doSignin() {
		return "signin";
	}
	
	
	
}
