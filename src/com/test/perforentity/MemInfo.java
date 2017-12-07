package com.test.perforentity;

public class MemInfo {
	private String time;
	private int memValue;
	
	public MemInfo(String time,int memValue) {
		// TODO Auto-generated constructor stub
		this.time=time;
		this.memValue=memValue;
	}
	
	public String time(){
		return time;
	}
	public int memValue(){
		return memValue;
	}
}
