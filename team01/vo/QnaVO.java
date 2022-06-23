package com.seven.team01.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class QnaVO {
	private int qNum;
	private String qTitle;
    private String  qContent;
    private Date qDate;
    private String quId; 
    private String qImg;
    private int qState;
    private MultipartFile qFile;
    
    
 
	public MultipartFile getqFile() {
		return qFile;
	}
	public void setqFile(MultipartFile qFile) {
		this.qFile = qFile;
	}
	public int getqNum() {
		return qNum;
	}
	public void setqNum(int qNum) {
		this.qNum = qNum;
	}
	public String getqTitle() {
		return qTitle;
	}
	public void setqTitle(String qTitle) {
		this.qTitle = qTitle;
	}
	public String getqContent() {
		return qContent;
	}
	public void setqContent(String qContent) {
		this.qContent = qContent;
	}
	public Date getqDate() {
		return qDate;
	}
	public void setqDate(Date qDate) {
		this.qDate = qDate;
	}
	public String getQuId() {
		return quId;
	}
	public void setQuId(String quId) {
		this.quId = quId;
	}
	public String getqImg() {
		return qImg;
	}
	public void setqImg(String qImg) {
		this.qImg = qImg;
	}
	
	public int getqState() {
		return qState;
	}
	public void setqState(int qState) {
		this.qState = qState;
	}
	@Override
	public String toString() {
		return "QnaVO [qNum=" + qNum + ", qTitle=" + qTitle + ", qContent=" + qContent + ", qDate=" + qDate + ", quId="
				+ quId + ", qImg=" + qImg + ", qState=" + qState + ", qFile=" + qFile + "]";
	}
    
    
}
