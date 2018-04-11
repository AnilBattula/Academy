package com.vv.test.automation.api.pojoclasses;

public class Cabin {
	
	String id;
	String groupBlockFlag,guaranteeFlag;
	public String getGuaranteeFlag() {
		return guaranteeFlag;
	}
	public void setGuaranteeFlag(String guaranteeFlag) {
		this.guaranteeFlag = guaranteeFlag;
	}
	String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupBlockFlag() {
		return groupBlockFlag;
	}
	public void setGroupBlockFlag(String groupBlockFlag) {
		this.groupBlockFlag = groupBlockFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
