package com.test.util;
/***
 * 获取指定应用的性能占用数据
 * @author xusai
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
	 * @return
	 */
	public static int getAPPCPU(){
		int cpu=0;

		return cpu;
	}
	/***
	 * 获取指定应用的men瞬时占用情况
	 * @return
	 */
	public static int getAPPMem(){
		int mem=0;
		
		return mem;
	}

	public static void main(String[] args) {
		int cpuValue = AppInfo.getAPPCPU();
		System.out.println(cpuValue);
	}
}
