package com.jerome.chengrui.house.model;

public enum MZType {

	ENTIRE(0),
	SHARED(1);

	private int value;
	private MZType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
