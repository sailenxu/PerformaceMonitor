package com.test.main;

import java.util.List;

import com.test.perfordata.MonitorData;
import com.test.perforentity.CPUInfo;

/***
 * 性能数据处理
 * @author xusai
 *
 */
public class DealData {
	
	/**
	 * 启动线程，让子线程跑30s，结束线程
	 * 只有线程结束后才能获得数据信息，没有实时性，需要达到边获取边输出的形式：目前加sleep是为了让主线程暂停，如果不加的话主线程结束，子线程也就获取结束了，桌面级应用主线程不会结束
	 * push
	 * @param args
	 */
	public static void main(String[] args) {
		String packName="com.jingdong.app.mall";
		String device="Q5S5T19529000632";

		com.test.main.StartMonitor monitor=new com.test.main.StartMonitor(packName,device);
		Thread t1=new Thread(monitor);
		t1.start();
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.stop();
		// TODO Auto-generated method stub
		//打印获取到的cpu数据
//		List<CPUInfo> cpuInfos=MonitorData.getCPUinfolist();
//		System.out.println(cpuInfos.size()+"cpuuuuuuuuuuuuuuu");
//		for(CPUInfo info:cpuInfos){
//			System.out.println(info.cpuValue());
//		}
	}

}
