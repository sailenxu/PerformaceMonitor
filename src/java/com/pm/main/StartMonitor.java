package com.pm.main;

import java.awt.*;
import java.util.ResourceBundle;

import com.pm.page.ChartPanel;
import com.pm.perfordata.DeviceAndPack;
import com.pm.perforentity.FPSinfo;
import com.pm.util.AppInfo;
import com.pm.util.ExcelDeal;

import javax.swing.*;

/***
 * 开始性能监听，循环获取性能数据
 * @author xusaisai
 *
 */
public class StartMonitor implements Runnable {
	private static int hang;
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
	public StartMonitor(JFrame jFrame,int x, int y, int width, int height){
		this.jFrame = jFrame;
		perJPanel = new JPanel();
		perJPanel.setSize(width, height);
		perJPanel.setLocation(x, y);
		perJPanel.setLayout(new GridLayout(2,2));
		cpuChart=new ChartPanel(jFrame,"","cpu(%)",(int)(width*0.5), (int)(height*0.5),100);
		memChart = new ChartPanel(jFrame,"", "memory(mb)",(int)(width*0.5), (int)(height*0.5), 100);
		fpsChart = new ChartPanel(jFrame, "", "fps",(int)(width*0.5), (int)(height*0.5), 100);
		dataChart = new ChartPanel(jFrame, "", "data(kb)",(int)(width*0.5), (int)(height*0.5), 100);
//		new FPSinfo(jFrame,perJPanel,x,y,width,height).run();
	}

	public void run() {

		boolean flag = true;
		running=true;
		//循环获取性能数据，直到线程停止，每次各项取一个值，添加到集合中
		while (running) {
			double cpu = AppInfo.getAppInfo().getAPPCPU();
			System.out.println("cpu:"+cpu);
			int mem = AppInfo.getAppInfo().getAPPMem();
			System.out.println("mem:"+mem);
			double fps = AppInfo.getAppInfo().getFPS();
			System.out.println("fps:"+fps);
			int data = AppInfo.getAppInfo().getData();
			System.out.println("data:"+data);
			if (flag){
				cpuJPanel = cpuChart.getPanel(cpu);
				memJPanel = memChart.getPanel(mem);
				fpsJPanel = fpsChart.getPanel(fps);
				dataJPanel = dataChart.getPanel(data);
				perJPanel.add(cpuJPanel);
				perJPanel.add(memJPanel);
				perJPanel.add(fpsJPanel);
				perJPanel.add(dataJPanel);
				jFrame.add(perJPanel);
				jFrame.setVisible(true);
				flag = false;
			}else{
				cpuChart.rePaint(cpu);
				memChart.rePaint(mem);
				fpsChart.rePaint(fps);
				dataChart.rePaint(data);
				//判断是否需要写入excel
				if (DeviceAndPack.isWriteExcel){
					DealData.getInstance().writeExcel(hang, cpu, mem, fps, data);
					hang++;
				}
			}
		}
	}
	public static void setHang(){
		hang = 0;
	}
}

