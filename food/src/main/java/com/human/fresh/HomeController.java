package com.human.fresh;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	

	@RequestMapping("/login")
	public String doLogin() {
		return "login";
	}
	
	//가게 등록 jsp
	@RequestMapping("/s_up")
	public String doS_up() {
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
	public String doStore(@RequestParam("sid") String m_id, @RequestParam("sname") String s_name, 
						@RequestParam("post") String postcode, @RequestParam("saddress") String s_address,  
						@RequestParam("sdetail") String detailAddress, @RequestParam("sextra") String extraAddress,
						@RequestParam("snum") String s_num, @RequestParam("stel") String s_mobile, 
						@RequestParam("smenu") int s_type, @RequestParam("simg") String s_img) {
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		ifresh.insertStore(m_id, s_name, postcode, s_address, detailAddress, extraAddress, s_num, s_mobile, s_type, s_img);
		
		return "0";
	}
	
	//메뉴등록 jsp
	@RequestMapping("/m_up")
	public String doMup() {
		return "menuup";
	}
	
	//메뉴등록
	@ResponseBody
	@RequestMapping(value="/menu", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String doMenu(@RequestParam("s_seq") String s_seq, @RequestParam("m_name") String m_name,
						@RequestParam("m_price") int m_price, @RequestParam("m_ex") String m_ex,
						@RequestParam("m_img") String m_img, @RequestParam("m_cal") String m_cal) {
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		ifresh.insertmenu(s_seq, m_name, m_price, m_ex, m_img, m_cal);
		
		return "0";
	}
	
	
	//메뉴리스트 불러오기
	@ResponseBody
	@RequestMapping(value="/mls", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String doMls(@RequestParam("s_seq") String s_se, Model model) {
		ifresh ifresh=sqlSession.getMapper(ifresh.class);
		ArrayList<menuVO> armenu=ifresh.selectMenulit(s_se);
		System.out.println("s_list.size=["+armenu.size()+"]");
		
		JSONArray ja=new JSONArray();
		for(int i=0;i<armenu.size();i++) {
			menuVO list=armenu.get(i);
			JSONObject jo=new JSONObject();
			jo.put("m_seq", list.getMenu_seqno());
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
	
	
	//메뉴 수정하기
	@ResponseBody
	@RequestMapping(value="/menumdf", produces="application/json;charset=UTF-8")
	public String doMenuModify() {
		
		return "";
	}
	
	
	//메뉴 삭제하기
	@ResponseBody
	@RequestMapping(value="/menudlt", produces="application/json;charset=UTF-8")
	public String doMenuDelete() {
		
		return "";
	}
}
