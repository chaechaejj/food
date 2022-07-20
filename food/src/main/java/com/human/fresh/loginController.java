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
		}else { //로그인 성공 후
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
	
	//배송지 삭제
	@RequestMapping("/delSe")
	public String delSe(@RequestParam("se") int se) {
		iMember ime=sqlSession.getMapper(iMember.class);
		ime.delSe(se);
		return "redirect:/deliveryUp";
	}
	
	//배송지 수정
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
		
		if(checked.equals("Y")) { //기본배송지 변경+배송지 등록
			ime.upDeliveryList(postcode, address, detailaddress, extraaddress, newname,newmobile, se);
			ime.upDelivery(postcode, address, detailaddress, extraaddress,m_id);
		}else { //배송지 등록
			ime.upDeliveryList(postcode, address, detailaddress, extraaddress, newname,newmobile, se);
		}
		
		return "redirect:/dvList";
	}
	
	//배송지 추가
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
		
		if(checked.equals("Y")) { //기본배송지 변경+배송지 등록
			ime.addDelivery(m_id, address, postcode, detailaddress, extraaddress, newname, newmobile);
			ime.upDelivery(postcode, address, detailaddress, extraaddress,m_id);
		}else { //배송지 등록
			ime.addDelivery(m_id, address, postcode, detailaddress, extraaddress, newname, newmobile);
		}
		
		return "redirect:/dvList";
	}
	
	//배송지 조회,추가,수정,삭제 창 열기
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
	
	//회원탈퇴
	@RequestMapping("/delInformation")
	public String delInformation(HttpServletRequest request) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		ime.delInformation((String)session.getAttribute("userid"));
		ime.delDelivery((String)session.getAttribute("userid"));
		//if 문으로 사장 탈퇴일시 회원정보,배달주소,가게정보,메뉴목록 테이블 삭제
		//일반이용자 탈퇴일시 회원정보,배달주소 테이블 삭제 
		
		session.invalidate(); //저장되있던 정보들 다 삭제
		
		return "redirect:/";
	}
		
	//로그인정보 수정
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
	
	//로그인정보 열기
	@RequestMapping("/informationUp")
	public String doInformationUp(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		mDTO mdto=ime.userList((String)session.getAttribute("userid"));
		model.addAttribute("mdto",mdto);
		
		return "informationUp";
	}
	
	//비밀번호 변경
	@ResponseBody
	@RequestMapping(value="/pwdNew", method=RequestMethod.POST)
	public String pwdNew(@RequestParam("pwd") String pwd,
			HttpServletRequest request) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		ime.updatePwd(pwd, (String)session.getAttribute("userid"));
		
		return Integer.toString(1);
	}
	
	//내정보수정 전 비밀번호 확인 열기
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
	
	//비밀번호 변경 열기
	@RequestMapping("/checkpwd")
	public String doCheckPwd(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		
		iMember ime=sqlSession.getMapper(iMember.class);
		mDTO mdto=ime.userList((String)session.getAttribute("userid"));
		model.addAttribute("mdto",mdto);
		
		return "checkpwd";
	}
	
	//내정보수정 열기
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
	
	//아이디,비밀번호 맞는지 확인
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
				session.setAttribute("userType", "손님");
			}else if(checkType == 2) {
				session.setAttribute("userType", "사장님");
			}
			session.setAttribute("userid", id);
			//session.setAttribute("username", name);
		}else {
			model.addAttribute("ch","<h5>등록되지 않은 계정입니다</h5>");
			return "login";
		}
		return "redirect:/";
	}
	
	//로그인창 열기
	@RequestMapping("/login")
	public String doLogin() {
		return "login";
	}
	
	//로그아웃
	@RequestMapping("/logout")
	public String doLogout(HttpServletRequest req) {
		HttpSession session=req.getSession();
		session.invalidate(); //저장되있던 정보들 다 삭제
		return "redirect:/";
	}
	
	//회원가입
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
	
	//아이디 중복확인
	@ResponseBody
	@RequestMapping(value = "/idoverlap")
	public String idOverlap(@RequestParam("userid") String id) {
		iMember ime=sqlSession.getMapper(iMember.class);
		
		int check=ime.idOverlap(id);
		return Integer.toString(check);
	}
	
	//회원가입창 열기
	@RequestMapping("/userSign")
	public String userSign(@RequestParam("Sign") String type, Model model) {
		model.addAttribute("type",type);
		return "userSign";
	}
	
	//사장,손님 구분 회원가입
	@RequestMapping("/signin")
	public String doSignin() {
		return "signin";
	}
	
	
	
}
