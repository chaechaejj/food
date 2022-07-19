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
	
	//���� ��� jsp
	@RequestMapping("/s_up")
	public String doS_up() {
		return "storeup";
	}
	
	//�޴�Ÿ�� select option
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
	
	//���� ���
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
	
	//�޴���� jsp
	@RequestMapping("/m_up")
	public String doMup() {
		return "menuup";
	}
	
	//�޴����
	
	@RequestMapping(value="/menu")
	public String doMenu(@RequestParam("s_seq") String s_seq, @RequestParam("memuname") String m_name,
						@RequestParam("menuprice") int m_price, @RequestParam("menuex") String m_ex, 
						@RequestParam("menukcal") String m_cal, @RequestParam("file") MultipartFile file) {
		  String upLoadDirectory= "C:/Users/admin/git/repository/food/src/main/webapp/resources/test";
		  
	      System.out.println("file="+file+"menuname="+m_name);
	      
	      String uploadFileName = file.getOriginalFilename();
	      uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1); //���ڿ� �ڸ���
	      
	      UUID uuid=UUID.randomUUID(); //�����̸�����
	      uploadFileName=uuid.toString() + "_" + uploadFileName; //�����̸�_���ε����ϸ�
	      File f= new File(upLoadDirectory,uploadFileName);
	      try {
			ifresh ifresh=sqlSession.getMapper(ifresh.class);
			ifresh.insertmenu(s_seq, m_name, m_price, m_ex, uploadFileName, m_cal);
	        file.transferTo(f); //���� ������ ����
	     } catch (IllegalStateException e) {
	        e.printStackTrace();
	     } catch (IOException e) {
	        e.printStackTrace();
	     } 
	     return "redirect:/m_up";
	}
	
//	//�̹��� ���� �� ���� �̸� �����
//	@RequestMapping("/doit")
//	   public String doit(@RequestParam("file") MultipartFile file) {
//		String upLoadDirectory= "C:/Users/admin/git/repository/food/src/main/webapp/resources/test";
//	      
//	      String uploadFileName = file.getOriginalFilename();
//	      uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1); //���ڿ� �ڸ���
//	      
//	      UUID uuid=UUID.randomUUID(); //�����̸�����
//	      uploadFileName=uuid.toString() + "_" + uploadFileName; //�����̸�_���ε����ϸ�
//	      File f= new File(upLoadDirectory,uploadFileName);
//	      try {
//	         //��� ���� sql�� ����
//	         file.transferTo(f); //���� ������ ����
//	      } catch (IllegalStateException e) {
//	         e.printStackTrace();
//	      } catch (IOException e) {
//	         e.printStackTrace();
//	      } 
//	      return "redirect:/upload";
//	   }
	
	
	
	
	//�޴�����Ʈ �ҷ�����
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
	
	
	//�޴� �����ϱ�
	@ResponseBody
	@RequestMapping(value="/menumdf", produces="application/json;charset=UTF-8")
	public String doMenuModify() {
		
		return "";
	}
	
	
	//�޴� �����ϱ�
	@ResponseBody
	@RequestMapping(value="/menudlt", produces="application/json;charset=UTF-8")
	public String doMenuDelete() {
		
		return "";
	}
	
	
	//�̹��� ���ε� jsp
	@RequestMapping("/test")
	public String doTest() {
		
		return "upload2";
	}
	
	//�̹��� ���ε� �Լ�
	@RequestMapping(value="/img", method=RequestMethod.POST)
	public String doImg() {
		
		return "upload";
	}
	

	
	
}
