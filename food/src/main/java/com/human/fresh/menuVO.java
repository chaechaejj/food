package com.human.fresh;

public class menuVO { //메뉴등록insert
	private int menu_seqno;
	private String s_se;
	private String menu_name;
	private int menu_price;
	private String menu_ex;
	private String menu_img;
	private String menu_cal;
	
	public menuVO() {
	}

	public int getMenu_seqno() {
		return menu_seqno;
	}

	public void setMenu_seqno(int menu_seqno) {
		this.menu_seqno = menu_seqno;
	}

	public String getS_se() {
		return s_se;
	}

	public void setS_se(String s_se) {
		this.s_se = s_se;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public int getMenu_price() {
		return menu_price;
	}

	public void setMenu_price(int menu_price) {
		this.menu_price = menu_price;
	}

	public String getMenu_ex() {
		return menu_ex;
	}

	public void setMenu_ex(String menu_ex) {
		this.menu_ex = menu_ex;
	}

	public String getMenu_img() {
		return menu_img;
	}

	public void setMenu_img(String menu_img) {
		this.menu_img = menu_img;
	}

	public String getMenu_cal() {
		return menu_cal;
	}

	public void setMenu_cal(String menu_cal) {
		this.menu_cal = menu_cal;
	}

}
