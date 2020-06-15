package com.pm.main;

import com.pm.util.ExcelDeal;

import java.util.ResourceBundle;

/***
 * 性能数据处理
 * @author xusai
 *
 */
public class DealData {
	private static DealData dealData= new DealData();
	public static DealData getInstance(){
		return dealData;
	}
	private ExcelDeal excelDeal=new ExcelDeal(ResourceBundle.getBundle("config").getString("performancePath"));
	public void writeExcel(int hang, double cpu, int mem, double fps, int data){
		if (excelDeal.isExitFile()){
			write(hang,cpu,mem,fps,data);
		}else {
			try {
				excelDeal.createXlsx();
				write(hang,cpu,mem,fps,data);
			}catch (Exception e){}
		}
	}
	private void write(int hang,double cpu, int mem, double fps, int data){
		String time = excelDeal.getStringDate();
		try {
			excelDeal.writeToXLSX(hang, 0, time);
			excelDeal.writeToXLSX(hang, 1, cpu);
			excelDeal.writeToXLSX(hang, 2, mem);
			excelDeal.writeToXLSX(hang, 3, fps);
			excelDeal.writeToXLSX(hang, 4, data);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 启动线程，让子线程跑30s，结束线程
	 * 只有线程结束后才能获得数据信息，没有实时性，需要达到边获取边输出的形式：目前加sleep是为了让主线程暂停，如果不加的话主线程结束，子线程也就获取结束了，桌面级应用主线程不会结束
	 * push
	 * @param args
	 */
	public static void main(String[] args) {
		String packName="com.jingdong.app.mall";
		String device="Q5S5T19529000632";

//		com.pm.main.StartMonitor monitor=new com.pm.main.StartMonitor();
//		Thread t1=new Thread(monitor);
//		t1.start();
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		t1.stop();
		// TODO Auto-generated method stub
		//打印获取到的cpu数据
//		List<CPUInfo> cpuInfos=MonitorData.getCPUinfolist();
//		System.out.println(cpuInfos.size()+"cpuuuuuuuuuuuuuuu");
//		for(CPUInfo info:cpuInfos){
//			System.out.println(info.cpuValue());
//		}
	}

}
