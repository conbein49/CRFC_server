package com.jerome.chengrui.house.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	@JsonProperty("openid")
	private String openID;
	@JsonProperty("session_key")
	private String sessionKey;
	@JsonProperty("unionid")
	private String unionID;

	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getUnionID() {
		return unionID;
	}
	public void setUnionID(String unionID) {
		this.unionID = unionID;
	}
	
}
