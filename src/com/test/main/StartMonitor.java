package com.test.main;

import java.util.Date;

import com.test.data.MonitorData;
import com.test.entity.CPUInfo;
import com.test.entity.MemInfo;
import com.test.util.AppInfo;
/***
 * 开始性能监听，循环获取性能数据
 * @author xusaisai
 *
 */
public class StartMonitor implements Runnable {
	private long sleepTime;
	private boolean running;
	private String packName;
	private String device;
	
	public StartMonitor(String packName,String device){
		this.packName=packName;
		this.device=device;
	}
	
	
	public void run() {
		long time=System.currentTimeMillis();
		running=true;
		while (running) {
			//获取cpu并存储数据
			AppInfo appInfo=new AppInfo(packName, device);
			CPUInfo cpuInfo=new CPUInfo(String.valueOf(time), appInfo.getAPPCPU());
			MonitorData.getCPUinfolist().add(cpuInfo);
			//获取内存并存储数据
			MemInfo memInfo=new MemInfo(String.valueOf(time), AppInfo.getAPPMem());
			MonitorData.getMeminfolist().add(memInfo);
			//增加延时
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

