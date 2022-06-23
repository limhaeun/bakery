package com.seven.team01.vo;

public class CartVO {

	private int cId;
	private int gCode;
	private String uId;
	private int cAmount;
	
	private int num;
	private String gName;
	private int gPrice;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getgCode() {
		return gCode;
	}
	public void setgCode(int gCode) {
		this.gCode = gCode;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public int getcAmount() {
		return cAmount;
	}
	public void setcAmount(int cAmount) {
		this.cAmount = cAmount;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getgName() {
		return gName;
	}
	public void setgName(String gName) {
		this.gName = gName;
	}
	public int getgPrice() {
		return gPrice;
	}
	public void setgPrice(int gPrice) {
		this.gPrice = gPrice;
	}
	
	
	@Override
	public String toString() {
		return "CartVO [cId=" + cId + ", gCode=" + gCode + ", uId=" + uId + ", cAmount=" + cAmount + ", num=" + num
				+ ", gName=" + gName + ", gPrice=" + gPrice + "]";
	}
	
	
}
