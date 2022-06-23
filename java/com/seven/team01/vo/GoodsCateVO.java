package com.seven.team01.vo;

public class GoodsCateVO {
	private String cateName;
	private String cateCode;
	private String cateCodeRef;
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCateCode() {
		return cateCode;
	}
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	public String getCateCodeRef() {
		return cateCodeRef;
	}
	public void setCateCodeRef(String cateCodeRef) {
		this.cateCodeRef = cateCodeRef;
	}
	@Override
	public String toString() {
		return "GoodsCategory [cateName=" + cateName + ", cateCode=" + cateCode + ", cateCodeRef=" + cateCodeRef + "]";
	}
}
