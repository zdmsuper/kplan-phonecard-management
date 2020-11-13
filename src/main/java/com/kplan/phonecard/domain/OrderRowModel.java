package com.kplan.phonecard.domain;


public class OrderRowModel {
    private String phone;
	private String userName;
	private String userId;
	private String address;
	private String procductCode;
	private String procductName;
	private String districtCode;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProcductCode() {
		return procductCode;
	}
	public void setProcductCode(String procductCode) {
		this.procductCode = procductCode;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getProcductName() {
		return procductName;
	}
	public void setProcductName(String procductName) {
		this.procductName = procductName;
	}
	
	
}
