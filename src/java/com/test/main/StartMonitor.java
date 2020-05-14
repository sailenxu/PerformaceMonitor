package com.test.main;

import java.util.Date;
import java.util.List;

import com.test.perfordata.DeviceAndPack;
import com.test.perfordata.MonitorData;
import com.test.perforentity.CPUInfo;
import com.test.perforentity.MemInfo;
import com.test.util.AppInfo;
/***
 * 开始性能监听，循环获取性能数据
 * @author xusaisai
 *
 */
public class StartMonitor implements Runnable {
	private boolean running;

	public void run() {
		long time=System.currentTimeMillis();
		running=true;
		//循环获取性能数据，直到线程停止，每次各项取一个值，添加到集合中
		while (running) {

			//获取cpu并存储数据
			CPUInfo cpuInfo=new CPUInfo(String.valueOf(time), AppInfo.getAppInfo().getAPPCPU());
			MonitorData.getCPUinfolist().add(cpuInfo);
			//获取内存并存储数据
			MemInfo memInfo=new MemInfo(String.valueOf(time), AppInfo.getAppInfo().getAPPMem());
			MonitorData.getMeminfolist().add(memInfo);
			//增加延时
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

