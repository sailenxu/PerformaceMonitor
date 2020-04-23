package com.test.util;

import java.util.List;

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
	 * adb -s Q5S5T19529000632 shell dumpsys cpuinfo|findstr com.jingdong.app.mall
	 */
	public double getAPPCPU(){
		double cpu=0;
		AdbUtil adbUtil = new AdbUtil();
		List<String> result = adbUtil.getListByADB("adb -s "+device+" shell dumpsys cpuinfo|findstr "+packName);
		//增加判空，可能获取到的结果为空
		if (result.size()!=0&&result!=null){
			if (!result.get(0).equals("")&&result.get(0)!=null){
				String cpuValueString = result.get(0);//取到第一行13% 16464/com.jingdong.app.mall: 9.2% user + 4% kernel / faults: 10176 minor 15 major
				//进行解析，按照%分割
				cpu = Double.valueOf(cpuValueString.split("%")[0]);//拿到13%，转为int保存
			}
		}
		System.out.println("cpu:"+cpu);
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

//	public static void main(String[] args) {
//		AppInfo appInfo=new AppInfo("com.jingdong.app.mall", "Q5S5T19529000632");
//		int cpuValue = appInfo.getAPPCPU();
//		System.out.println(cpuValue);
//	}
}
