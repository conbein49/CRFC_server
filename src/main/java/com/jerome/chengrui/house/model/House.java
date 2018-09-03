package com.jerome.chengrui.house.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class House {

	private long houseID;
	private byte[] houseImg;
	private String houseLocal;
	private String houseDescribe;
	private int mztype; //0->整组；1->合租
	private String houseLayout;
	private int zffkfs; //0->月付；1->年付
	private int houselc;//楼层

	@JsonCreator
	public House(@JsonProperty("houseID") long houseID) {
		this.houseID = houseID;
	}

	public long getHouseID() {
		return houseID;
	}
	public void setHouseID(long houseID) {
		this.houseID = houseID;
	}
	public byte[] getHouseImg() {
		return houseImg;
	}
	public void setHouseImg(byte[] houseImg) {
		this.houseImg = houseImg;
	}
	public String getHouseLocal() {
		return houseLocal;
	}
	public void setHouseLocal(String houseLocal) {
		this.houseLocal = houseLocal;
	}
	public String getHouseDescribe() {
		return houseDescribe;
	}
	public void setHouseDescribe(String houseDescribe) {
		this.houseDescribe = houseDescribe;
	}
	public int getMztype() {
		return mztype;
	}
	public void setMztype(int mztype) {
		this.mztype = mztype;
	}
	public String getHouseLayout() {
		return houseLayout;
	}
	public void setHouseLayout(String houseLayout) {
		this.houseLayout = houseLayout;
	}
	public int getZffkfs() {
		return zffkfs;
	}
	public void setZffkfs(int zffkfs) {
		this.zffkfs = zffkfs;
	}
	public int getHouselc() {
		return houselc;
	}
	public void setHouselc(int houselc) {
		this.houselc = houselc;
	}
	
	
	
}
