package com.seven.team01.vo;

import java.sql.Date;

public class ReplyVO {	    
	private int rGCode;   //goods번호
	private int rNum; 	    //댓글 번호
	private String rUId;   //회원 아이디
	private String rCont; //댓글 내용
	 private Date rDate; //작성 날짜
	 private int rPnum;   //원글 번호
	 private int rDepth;  //댓글 들여쓰기
	 
	public int getrPnum() {
		return rPnum;
	}
	public void setrPnum(int rPnum) {
		this.rPnum = rPnum;
	}
	public int getrDepth() {
		return rDepth;
	}
	public void setrDepth(int rDepth) {
		this.rDepth = rDepth;
	}
	public int getrGCode() {
		return rGCode;
	}
	public void setrGCode(int rGCode) {
		this.rGCode = rGCode;
	}
	public int getrNum() {
		return rNum;
	}
	public void setrNum(int rNum) {
		this.rNum = rNum;
	}
	public String getrUId() {
		return rUId;
	}
	public void setrUId(String rUId) {
		this.rUId = rUId;
	}
	public String getrCont() {
		return rCont;
	}
	public void setrCont(String rCont) {
		this.rCont = rCont;
	}
	public Date getrDate() {
		return rDate;
	}
	public void setrDate(Date rDate) {
		this.rDate = rDate;
	}
	
	@Override
	public String toString() {
		return "ReplyVO [rGCode=" + rGCode + ", rNum=" + rNum + ", rUId=" + rUId + ", rCont=" + rCont + ", rDate="
				+ rDate + ", rPnum=" + rPnum + ", rDepth=" + rDepth + "]";
	}
	 
}
