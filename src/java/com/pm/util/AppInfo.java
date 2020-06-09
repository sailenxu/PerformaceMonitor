package com.pm.util;

import com.pm.log.LogDemo;
import com.pm.perfordata.DeviceAndPack;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/***
 * 获取指定应用的性能占用数据
 * @author xusai
 *
 */
public class AppInfo {
	private final static Logger logger = Logger.getLogger(LogDemo.class);
	private AdbUtil adbUtil=new AdbUtil();
	//增加单例模式，减少对象初始化
	private static AppInfo appInfo = new AppInfo();
	public static AppInfo getAppInfo() {
		return appInfo;
	}
	private int currentData=0;
	private String currentPack = DeviceAndPack.packagename;
	/***
	 * 获取指定设备的指定应用的cpu瞬时占用情况
	 * @return
	 * adb -s Q5S5T19529000632 shell dumpsys cpuinfo|findstr com.jingdong.app.mall
	 */
	public double getAPPCPU(){
		double cpu=0;
		if (DeviceAndPack.deivceid!=null&&DeviceAndPack.packagename!=null) {
			List<String> result = adbUtil.getListByADB("adb -s " + DeviceAndPack.deivceid + " shell top -o ARGS -o %CPU -n 1|findstr " + DeviceAndPack.packagename);
			//增加判空，可能获取到的结果为空
			if (result.size() != 0 && result != null) {
				if (!result.get(0).equals("") && result.get(0) != null) {
					String cpuValueString = result.get(0);//取到第一行1mcom.jingdong.app.mall       65.5
					//进行解析，按照%分割
					cpu = Double.valueOf(cpuValueString.split("\\s+")[1]);//拿到13%，转为int保存
				}
			}
		}
		return cpu;
	}
	/***
	 * 获取指定应用的men瞬时占用情况
	 * @return
	 */
	public int getAPPMem(){
		int mem=0;
		if (DeviceAndPack.deivceid!=null&&DeviceAndPack.packagename!=null) {
			List<String> result = adbUtil.getListByADB("adb -s " + DeviceAndPack.deivceid + " shell dumpsys meminfo -s " + DeviceAndPack.packagename);
			//可能获取到的结果为空，或进程不存在
			if (result.size() != 0 && result != null) {
				for (String ss : result) {
					if (ss.contains("TOTAL")) {
						mem = Integer.parseInt(ss.split("\\s+")[1]) / 1024;
//					System.out.println(mem);
					}
				}
			}
		}
		return mem;
	}
	public void runMonkey(boolean isIgnoreCrashes, boolean isIgnoreTimeouts,int throttle, int count) throws IOException {
		if (DeviceAndPack.deivceid!=null&&DeviceAndPack.packagename!=null) {
			String monkeyCommand = "adb -s " + DeviceAndPack.deivceid + " shell monkey -p " + DeviceAndPack.packagename + " -v -v --throttle "+throttle+(isIgnoreCrashes?" --ignore-crashes ":" ")+(isIgnoreTimeouts?" --ignore-timeouts ":" ")+count;
			logger.info(monkeyCommand);
			adbUtil.runADBNoRequest(monkeyCommand);
//			BufferedReader br = new CmdTool().getBRByCmd(monkeyCommand);
//			while (br.readLine() != null) {
//				logger.info(br.readLine());
//			}
		}else {
			logger.info("device or package is error");
		}
	}
	/**
	 * 清除apk缓存
	 */
	public void clearAPK(){
		if (DeviceAndPack.deivceid!=null&&DeviceAndPack.packagename!=null) {
			String command = "adb -s " + DeviceAndPack.deivceid + " shell pm clear " + DeviceAndPack.packagename;
			logger.info("adb command:" + command);
			adbUtil.runADBNoRequest(command);
//		runADB("adb -s "+DeviceAndPack.deivceid+" shell pm clear "+packagename);
		}else {
			logger.info("device or package is error");
		}
	}
	public void stopAPP(){
		if (DeviceAndPack.deivceid!=null&&DeviceAndPack.packagename!=null) {
			String command = "adb -s " + DeviceAndPack.deivceid + " shell am force-stop " + DeviceAndPack.packagename;
			logger.info("adb command:" + command);
			adbUtil.runADBNoRequest(command);
		}else {
			logger.info("device is error");
		}
	}
	//卸载apk
	public void uninstallAPK(){
		if (DeviceAndPack.deivceid!=null&&DeviceAndPack.packagename!=null) {
			String command = "adb -s " + DeviceAndPack.deivceid + " uninstall " + DeviceAndPack.packagename;
			logger.info("adb command:" + command);
			adbUtil.getListByADB(command);
//		runADB("adb -s "+DeviceAndPack.deivceid+" uninstall "+packagename);
		}else {
			logger.info("device is error");
		}
	}
	//没有设备和没有进程时，都返回0
	public int getPid(){
		int pid=0;
		if (DeviceAndPack.deivceid!=null&&DeviceAndPack.packagename!=null) {
			String command = "adb -s " + DeviceAndPack.deivceid + " shell ps -o PID -o NAME|findstr " + DeviceAndPack.packagename;
			List<String> result = adbUtil.getListByADB(command);
			if (result.size() > 0) {
				for(String ss:result){
					if (ss.split("\\s+")[1].equals(DeviceAndPack.packagename)){
						pid = Integer.parseInt(ss.split("\\s+")[0]);
					}
				}
			}
		}
		return pid;
	}
	//获取pid的当前流量
	public Integer getCurrentData(){
		long data = 0;
		if (getPid()!=0){
			String command = "adb -s "+DeviceAndPack.deivceid+" shell cat /proc/"+getPid()+"/net/dev|findstr wlan0";
			String result = adbUtil.getStringByADB(command);
//			System.out.println(result.split("\\s+")[2]+":::::"+result.split("\\s+")[10]);
			String[] value = result.split("\\s+");
			if (value.length>10) {
				data = Long.parseLong(result.split("\\s+")[2])+Long.parseLong(result.split("\\s+")[10]);
//				data = Integer.parseInt(result.split("\\s+")[2]) + Integer.parseInt(result.split("\\s+")[10]);
			}
		}else {
			return null;
		}
		return (int)(data/1024);
	}
	//获取当前流量和上次流量的差值
	public int getData(){
		int data = 0;
		Integer ccdata = getCurrentData();//有可能为空
		//没有设备时，获取到的packagename为null，增加判空
		if (currentPack!=null && ccdata!=null) {
			if (currentPack.equals(DeviceAndPack.packagename)) {
				if (currentData != 0) {
					if (ccdata != 0) {
						data = ccdata - currentData;
					}
				} else {
					currentData = ccdata;
				}
			} else {
				currentPack = DeviceAndPack.packagename;
				currentData = ccdata;
			}
		}
		return data;
	}
//	public static void main(String[] args) {
//		DeviceAndPack deviceAndPack = new DeviceAndPack();
//		AppInfo appInfo = new AppInfo();
//		deviceAndPack.setDeivceid("Q5S5T19529000632");
//		deviceAndPack.setPackagename("com.cleanmaster.mguard_cn");
//		System.out.println(appInfo.getData());
////		System.out.println(cpuValue);
//	}
}
