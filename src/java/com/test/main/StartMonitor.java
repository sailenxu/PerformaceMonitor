package com.test.main;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.test.page.ChartPanel;
import com.test.perfordata.DeviceAndPack;
import com.test.perfordata.MonitorData;
import com.test.perforentity.CPUInfo;
import com.test.perforentity.MemInfo;
import com.test.util.AppInfo;

import javax.swing.*;

/***
 * 开始性能监听，循环获取性能数据
 * @author xusaisai
 *
 */
public class StartMonitor implements Runnable {
	private boolean running;
	private JFrame jFrame;
	private JPanel perJPanel;
	private ChartPanel cpuChart;
	private ChartPanel memChart;
	private ChartPanel fpsChart;
	private ChartPanel dataChart;
	private JPanel cpuJPanel;
	private JPanel memJPanel;
	private JPanel fpsJPanel;
	private JPanel dataJPanel;
	public StartMonitor(JFrame jFrame){
		this.jFrame = jFrame;
		perJPanel = new JPanel();
		perJPanel.setPreferredSize(new Dimension(1000, 800));
		perJPanel.setLayout(new GridLayout(2,2));
		cpuChart=new ChartPanel(jFrame,"","cpu",100);
		memChart = new ChartPanel(jFrame,"", "memory", 100);
		fpsChart = new ChartPanel(jFrame, "", "fps", 100);
		dataChart = new ChartPanel(jFrame, "", "data", 100);
	}

	public void run() {
		long time=System.currentTimeMillis();
		boolean flag = true;
		running=true;
		//循环获取性能数据，直到线程停止，每次各项取一个值，添加到集合中
		while (running) {
			if (flag){
				cpuJPanel = cpuChart.getPanel(AppInfo.getAppInfo().getAPPCPU());
				memJPanel = memChart.getPanel(AppInfo.getAppInfo().getAPPMem());
				fpsJPanel = fpsChart.getPanel(AppInfo.getAppInfo().getAPPCPU());
				dataJPanel = dataChart.getPanel(AppInfo.getAppInfo().getAPPMem());
				perJPanel.add(cpuJPanel);
				perJPanel.add(memJPanel);
				perJPanel.add(fpsJPanel);
				perJPanel.add(dataJPanel);
				jFrame.add(perJPanel);
				jFrame.pack();
				jFrame.setVisible(true);
				flag = false;
			}else{
				cpuChart.rePaint(AppInfo.getAppInfo().getAPPCPU());
				memChart.rePaint(AppInfo.getAppInfo().getAPPMem());
				fpsChart.rePaint(AppInfo.getAppInfo().getAPPCPU());
				dataChart.rePaint(AppInfo.getAppInfo().getAPPMem());
			}
//			//获取cpu并存储数据
//			CPUInfo cpuInfo=new CPUInfo(String.valueOf(time), AppInfo.getAppInfo().getAPPCPU());
//			MonitorData.getCPUinfolist().add(cpuInfo);
//			//获取内存并存储数据
//			MemInfo memInfo=new MemInfo(String.valueOf(time), AppInfo.getAppInfo().getAPPMem());
//			MonitorData.getMeminfolist().add(memInfo);
//			double cpu = AppInfo.getAppInfo().getAPPCPU();
//			System.out.println(cpu);
//			cpuChart.xchart(cpu);
//			cpuJPanel = cpuChart.getXchartPanel(cpu);

//			double mem = AppInfo.getAppInfo().getAPPMem();
//			memChart.xchart(mem);
//			memJPanel = memChart.getXchartPanel(cpu);

//			double fps = AppInfo.getAppInfo().getAPPCPU();
//			fpsChart.xchart(fps);
//			fpsJPanel = fpsChart.getXchartPanel(cpu);

//			double data = AppInfo.getAppInfo().getAPPMem();
//			dataChart.xchart(data);

//			jFrame.add(cpuJPanel);
//			jFrame.add(memJPanel);
//			jFrame.add(fpsJPanel);

			//增加延时
			try {
				Thread.sleep(Long.parseLong(ResourceBundle.getBundle("config").getString("monitorTime")));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

