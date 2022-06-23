package com.seven.team01.vo;

import java.sql.Date;
//OrdersVO + DetailOrderVO + StateOrderVO
public class OrderDetailStateVO {
	String oId;
	String ouId;
	String oName;
	String oreceiveName;
	String oPhone;
	String oAddr1;
	String oAddr2;
	String oAddr3;
	String oPay;
	int oTotal;
	
	int dNum;
	int dgCode;
	String dgName;
	int dgPrice;
	int dAmount;

	Date oDate;
	Date sDate;
	String sState;
	
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	public String getOuId() {
		return ouId;
	}
	public void setOuId(String ouId) {
		this.ouId = ouId;
	}
	public String getoName() {
		return oName;
	}
	public void setoName(String oName) {
		this.oName = oName;
	}
	public String getOreceiveName() {
		return oreceiveName;
	}
	public void setOreceiveName(String oreceiveName) {
		this.oreceiveName = oreceiveName;
	}
	public String getoPhone() {
		return oPhone;
	}
	public void setoPhone(String oPhone) {
		this.oPhone = oPhone;
	}
	public String getoAddr1() {
		return oAddr1;
	}
	public void setoAddr1(String oAddr1) {
		this.oAddr1 = oAddr1;
	}
	public String getoAddr2() {
		return oAddr2;
	}
	public void setoAddr2(String oAddr2) {
		this.oAddr2 = oAddr2;
	}
	public String getoAddr3() {
		return oAddr3;
	}
	public void setoAddr3(String oAddr3) {
		this.oAddr3 = oAddr3;
	}
	public String getoPay() {
		return oPay;
	}
	public void setoPay(String oPay) {
		this.oPay = oPay;
	}
	public int getoTotal() {
		return oTotal;
	}
	public void setoTotal(int oTotal) {
		this.oTotal = oTotal;
	}
	public int getdNum() {
		return dNum;
	}
	public void setdNum(int dNum) {
		this.dNum = dNum;
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
	public Date getoDate() {
		return oDate;
	}
	public void setoDate(Date oDate) {
		this.oDate = oDate;
	}
	public Date getsDate() {
		return sDate;
	}
	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}
	public String getsState() {
		return sState;
	}
	public void setsState(String sState) {
		this.sState = sState;
	}
	
	@Override
	public String toString() {
		return "OrderDetailStateVO [oId=" + oId + ", ouId=" + ouId + ", oName=" + oName + ", oreceiveName="
				+ oreceiveName + ", oPhone=" + oPhone + ", oAddr1=" + oAddr1 + ", oAddr2=" + oAddr2 + ", oAddr3="
				+ oAddr3 + ", oPay=" + oPay + ", oTotal=" + oTotal + ", dNum=" + dNum + ", dgCode=" + dgCode
				+ ", dgName=" + dgName + ", dgPrice=" + dgPrice + ", dAmount=" + dAmount + ", oDate=" + oDate
				+ ", sDate=" + sDate + ", sState=" + sState + "]";
	}	
}
