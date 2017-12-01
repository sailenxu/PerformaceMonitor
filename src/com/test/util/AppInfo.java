package com.test.util;
/***
 * 获取指定应用的性能占用数据
 * @author xusaisai
 *
 */
public class AppInfo {
	private String packName;
	private String device;

	public AppInfo(String packName,String device) {
		this.packName=packName;
		this.device=device;
	}
	/***
	 * 获取指定设备的指定应用的cpu瞬时占用情况
	 * @param packName 包名
	 * @param device 设备udid
	 * @return
	 */
	public static int getAPPCPU(){
		int cpu=0;
		
		System.out.println();
		return cpu;
	}
	public static int getAPPMem(){
		int mem=0;
		
		return mem;
	}
}
