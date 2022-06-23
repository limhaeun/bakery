package com.seven.team01.vo;

import java.sql.Date;

public class StateOrderVO {
	String oId;
	int dNum;
	Date oDate;
	Date sDate;
	String sState;

	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}

	public int getdNum() {
		return dNum;
	}

	public void setdNum(int dNum) {
		this.dNum = dNum;
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
		return "StateOrderVO [oId=" + oId + ", dNum=" + dNum + ", oDate=" + oDate + ", sDate=" + sDate + ", sState="
				+ sState + "]";
	}

}
