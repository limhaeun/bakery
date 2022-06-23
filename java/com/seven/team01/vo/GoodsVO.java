package com.seven.team01.vo;

import java.sql.Date;

import javax.annotation.Resource;

import org.springframework.web.multipart.MultipartFile;

public class GoodsVO {
	private int gCode;
	private String gName;
	private int gPrice;
	private String gCateCode;
	private Date gDate;
	private int gCount;
	private String gImg;
	private String gDes;
	private MultipartFile gFile;
	private int grCount;
	
	
	

	
	public int getGrCount() {
		return grCount;
	}
	public void setGrCount(int grCount) {
		this.grCount = grCount;
	}
	public MultipartFile getgFile() {
		return gFile;
	}
	public void setgFile(MultipartFile gFile) {
		this.gFile = gFile;
	}
	public int getgCode() {
		return gCode;
	}
	public void setgCode(int gCode) {
		this.gCode = gCode;
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

	
	public String getgCateCode() {
		return gCateCode;
	}
	public void setgCateCode(String gCateCode) {
		this.gCateCode = gCateCode;
	}
	public Date getgDate() {
		return gDate;
	}
	public void setgDate(Date gDate) {
		this.gDate = gDate;
	}
	public int getgCount() {
		return gCount;
	}
	public void setgCount(int gCount) {
		this.gCount = gCount;
	}
	
	public String getgImg() {
		return gImg;
	}
	public void setgImg(String gImg) {
		this.gImg = gImg;
	}
	public String getgDes() {
		return gDes;
	}
	public void setgDes(String gDes) {
		this.gDes = gDes;
	}
	
	@Override
	public String toString() {
		return "GoodsVO [gCode=" + gCode + ", gName=" + gName + ", gPrice=" + gPrice + ", gCateCode=" + gCateCode
				+ ", gDate=" + gDate + ", gCount=" + gCount + ", gImg=" + gImg + ", gDes=" + gDes + ", gFile=" + gFile
				+ ", grCount=" + grCount + "]";
	}
	
	

	

	
	
}
