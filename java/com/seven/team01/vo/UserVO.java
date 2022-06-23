package com.seven.team01.vo;

import java.util.Date;


import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

public class UserVO {
	private String uId;
	private String uPwd;
	private String uName;
	private String uEmail;
	private String uPhone;
	private String uAddr1;
	private String uAddr2;
	private String uAddr3;
	private Date   uRegdate;
	private int    adminYN;
	
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuPwd() {
		return uPwd;
	}
	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuEmail() {
		return uEmail;
	}
	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public String getuAddr1() {
		return uAddr1;
	}
	public void setuAddr1(String uAddr1) {
		this.uAddr1 = uAddr1;
	}
	public String getuAddr2() {
		return uAddr2;
	}
	public void setuAddr2(String uAddr2) {
		this.uAddr2 = uAddr2;
	}
	public String getuAddr3() {
		return uAddr3;
	}
	public void setuAddr3(String uAddr3) {
		this.uAddr3 = uAddr3;
	}
	public Date getuRegdate() {
		return uRegdate;
	}
	public void setuRegdate(Date uRegdate) {
		this.uRegdate = uRegdate;
	}
	public int getAdminYN() {
		return adminYN;
	}
	public void setAdminYN(int adminYN) {
		this.adminYN = adminYN;
	}
	@Override
	public String toString() {
		return "UserVO [uId=" + uId + ", uPwd=" + uPwd + ", uName=" + uName + ", uEmail=" + uEmail + ", uPhone="
				+ uPhone + ", uAddr1=" + uAddr1 + ", uAddr2=" + uAddr2 + ", uAddr3=" + uAddr3 + ", uRegdate=" + uRegdate
				+ ", adminYN=" + adminYN + "]";
	}
}
