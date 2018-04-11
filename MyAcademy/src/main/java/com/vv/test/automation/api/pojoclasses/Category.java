package com.vv.test.automation.api.pojoclasses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
	
	String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuaranteeFlag() {
		return guaranteeFlag;
	}
	public void setGuaranteeFlag(String guaranteeFlag) {
		this.guaranteeFlag = guaranteeFlag;
	}

	private String guaranteeFlag;
	private List<Cabin> cabins;
	public List<Cabin> getCabins() {
		return cabins;
	}
	public void setCabins(List<Cabin> cabins) {
		this.cabins = cabins;
	}
	

}
