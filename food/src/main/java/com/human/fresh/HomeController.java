package com.human.fresh;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.human.fresh.findDTO;
import com.human.fresh.ifresh;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private SqlSession sqlSession;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		return "home";
//	}
	
//	@RequestMapping("/login")
//	public String doLogin() {
//		return "login";
//	}
	
	//가게 등록 jsp
	@RequestMapping("/s_up")
	public String doS_up(HttpServletRequest req, Model model) {
		HttpSession session=req.getSession();
		model.addAttribute("userinfo",session.getAttribute("userid"));
		return "storeup";
	}
	
	//메뉴타입 select option
	@ResponseBody
	@RequestMapping(value="/mtp", produces="application/json;charset=UTF-8")
	public String doMtp() {
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		ArrayList<stypeVO> arsvo=ifresh.s_type();
		System.out.println("s_type.size=["+arsvo.size()+"]");
		
		JSONArray ja=new JSONArray();
		for(int i=0;i<arsvo.size();i++) {
			stypeVO list=arsvo.get(i);
			JSONObject jo=new JSONObject();
			jo.put("s_type", list.getS_type());
			jo.put("type_name", list.getType_name());
			ja.add(jo);
		}
		System.out.println("ja.toJSONString()="+ja.toJSONString());
		return ja.toJSONString();
	}
	
	//가게 등록
	@ResponseBody
	@RequestMapping(value="/store", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String doStore(HttpServletRequest req) {
		String m_id=req.getParameter("sid");
		String s_name=req.getParameter("sname");
		String postcode=req.getParameter("post");
		String s_address=req.getParameter("saddress");
		String detailAddress=req.getParameter("sdetail");
		String extraAddress=req.getParameter("sextra");
		String s_num=req.getParameter("snum");
		String s_mobile=req.getParameter("stel");
		int s_type=Integer.parseInt(req.getParameter("smenu"));
		String s_img=req.getParameter("simg");
		
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		ifresh.insertStore(m_id, s_name, postcode, s_address, detailAddress, extraAddress, s_num, s_mobile, s_type, s_img);
		
		return "0";
	}
	
	//메뉴등록 jsp
	@RequestMapping("/m_up")
	public String doMup() {
		//가게시퀀스 가져오기
		return "menuup";
	}
	
	//메뉴등록 & 수정
	@RequestMapping(value="/menu")
	public String doMenu(HttpServletRequest req, MultipartHttpServletRequest mreq) {
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		String name=req.getParameter("menuname");
		int price=Integer.parseInt(req.getParameter("menuprice"));
		String ex=req.getParameter("menuex");
		String img=req.getParameter("m_img");
		String cal=req.getParameter("menukcal");
		int m_seq=Integer.parseInt(req.getParameter("m_seq"));
		String s_seq=req.getParameter("s_seq");
		MultipartFile file=mreq.getFile("file");
		
		String upLoadDirectory= "C:/Users/admin/git/repository/food/src/main/webapp/resources/test";
		System.out.println("file="+file+"menuname="+name);
		  
		String uploadFileName = file.getOriginalFilename();
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1); //문자열 자르기
		UUID uuid=UUID.randomUUID(); //랜덤이름생성
		uploadFileName=uuid.toString() + "_" + uploadFileName; //랜덤이름_업로드파일명
		File f= new File(upLoadDirectory,uploadFileName);
		try {
			if(m_seq==0) {
				ifresh.insertmenu(s_seq, name, price, ex, uploadFileName, cal);
				file.transferTo(f); //파일 폴더에 저장
			}else if(m_seq!=0){
				ifresh.modifyMenu(name, price, ex, uploadFileName, cal, m_seq, s_seq);
				file.transferTo(f); //파일 폴더에 저장
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return "redirect:/m_up";
	}
	
	//이미지 업로드 jsp
	@RequestMapping("/test")
	public String doTest() {
		return "upload2";
	}
	
	//이미지 업로드 함수
	@RequestMapping(value="/img", method=RequestMethod.POST)
	public String doImg() {
		return "upload";
	}
	
	//메뉴리스트 불러오기
	@ResponseBody
	@RequestMapping(value="/mls", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String doMls(@RequestParam("s_seq") String s_se, Model model) {
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		ArrayList<menuVO> armenu=ifresh.selectMenulit(s_se);
		System.out.println("menu_list.size=["+armenu.size()+"]");
		
		JSONArray ja=new JSONArray();
		for(int i=0;i<armenu.size();i++) {
			menuVO list=armenu.get(i);
			JSONObject jo=new JSONObject();
			jo.put("m_seq", list.getMenu_seqno());
			jo.put("s_seq", list.getS_se());
			jo.put("m_img", list.getMenu_img());
			jo.put("m_name", list.getMenu_name());
			jo.put("m_price", list.getMenu_price());
			jo.put("m_cal", list.getMenu_cal());
			jo.put("m_ex", list.getMenu_ex());
			ja.add(jo);
		}
		System.out.println("ja.s_list()="+ja.toJSONString());
		return ja.toJSONString();
	}
	
	
	//메뉴수정을 위한 detail
	@ResponseBody
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String doMenuUpdate(HttpServletRequest req, Model model) {
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		int m_seq=Integer.parseInt(req.getParameter("m_seq"));
		String s_seq=req.getParameter("s_seq");
		menuVO mvo=ifresh.updateMenuList(m_seq, s_seq);
		
		JSONArray ja=new JSONArray();
		JSONObject jo=new JSONObject();
		jo.put("m_img", mvo.getMenu_img());
		jo.put("m_name", mvo.getMenu_name());
		jo.put("m_price", mvo.getMenu_price());
		jo.put("m_cal", mvo.getMenu_cal());
		jo.put("m_ex", mvo.getMenu_ex());
		ja.add(jo);

		System.out.println("ja.s_mvo()="+ja.toJSONString());
		return ja.toJSONString();
	}
	
	//메뉴 삭제하기
	@RequestMapping(value="/del")
	public String doMenuDelete(@RequestParam("m_seq") int m_seq, @RequestParam("s_seq") String s_seq) {
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		ifresh.deleteMenu(m_seq, s_seq);
		System.out.println("delete_mseq="+m_seq+", sseq="+s_seq);
		return "redirect:/m_up";
	}
	
	

	

	
	
}
