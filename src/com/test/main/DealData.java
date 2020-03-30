package com.test.main;

import java.util.List;

import com.test.perfordata.MonitorData;
import com.test.perforentity.CPUInfo;

/***
 * 性能数据处理
 * @author xusaisai
 *
 */
public class DealData {
	
	/**
	 * 启动线程，让子线程跑30s，结束线程
	 * 这是啥啊？都忘了……
	 * @param args
	 */
	public static void main(String[] args) {
		String packName="";
		String device="";

		StartMonitor monitor=new StartMonitor(packName,device);
		Thread t1=new Thread(monitor);
		t1.start();
		try {
			Thread.sleep(30*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.stop();
		// TODO Auto-generated method stub
		//打印获取到的cpu数据
		List<CPUInfo> cpuInfos=MonitorData.getCPUinfolist();
		for(CPUInfo info:cpuInfos){
			System.out.println(info.cpuValue());
		}
	}

}
