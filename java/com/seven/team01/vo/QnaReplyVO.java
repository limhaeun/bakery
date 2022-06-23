package com.seven.team01.vo;

import java.util.Date;

public class QnaReplyVO {
	  int qrNum;
	  String qrContent; 
	  Date qrRegdate;
	  int qNum;
	public int getQrNum() {
		return qrNum;
	}
	public void setQrNum(int qrNum) {
		this.qrNum = qrNum;
	}
	public String getQrContent() {
		return qrContent;
	}
	public void setQrContent(String qrContent) {
		this.qrContent = qrContent;
	}
	public Date getQrRegdate() {
		return qrRegdate;
	}
	public void setQrRegdate(Date qrRegdate) {
		this.qrRegdate = qrRegdate;
	}
	public int getqNum() {
		return qNum;
	}
	public void setqNum(int qNum) {
		this.qNum = qNum;
	}
	@Override
	public String toString() {
		return "QnaReplyVO [qrNum=" + qrNum + ", qrContent=" + qrContent + ", qrRegdate=" + qrRegdate + ", qNum=" + qNum
				+ "]";
	}
	  
	  
}
