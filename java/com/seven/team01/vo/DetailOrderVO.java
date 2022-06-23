package com.seven.team01.vo;

public class DetailOrderVO {
	int dNum;
	String oId;
	int dgCode;
	String dgName;
	int dgPrice;
	int dAmount;
	private String dgImg; //이미지 추가
	
	public int getdNum() {
		return dNum;
	}
	public void setdNum(int dNum) {
		this.dNum = dNum;
	}
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	public int getDgCode() {
		return dgCode;
	}
	public void setDgCode(int dgCode) {
		this.dgCode = dgCode;
	}
	public String getDgName() {
		return dgName;
	}
	public void setDgName(String dgName) {
		this.dgName = dgName;
	}
	public int getDgPrice() {
		return dgPrice;
	}
	public void setDgPrice(int dgPrice) {
		this.dgPrice = dgPrice;
	}
	public int getdAmount() {
		return dAmount;
	}
	public void setdAmount(int dAmount) {
		this.dAmount = dAmount;
	}
	public String getDgImg() {
		return dgImg;
	}
	public void setDgImg(String dgImg) {
		this.dgImg = dgImg;
	}
	@Override
	public String toString() {
		return "DetailOrderVO [dNum=" + dNum + ", oId=" + oId + ", dgCode=" + dgCode + ", dgName=" + dgName
				+ ", dgPrice=" + dgPrice + ", dAmount=" + dAmount + ", dgImg=" + dgImg + "]";
	}
	


}
