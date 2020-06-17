package com.pm.util;

import com.pm.log.LogDemo;
import com.pm.perfordata.DeviceAndPack;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
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
	private int firstData=0;
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
			List<String> result = adbUtil.getListByADB("adb -s " + DeviceAndPack.deivceid + " shell dumpsys meminfo -s " + getPid());
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
			String[] value = result.split("\\s+");
			if (value.length>10) {
				data = Long.parseLong(result.split("\\s+")[2])+Long.parseLong(result.split("\\s+")[10]);
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
				if (firstData != 0) {
					if (ccdata != 0) {
						data = ccdata - firstData;
					}
				} else {
					firstData = ccdata;
				}
			} else {
				currentPack = DeviceAndPack.packagename;
				firstData = ccdata;
			}
		}
		return data;
	}
	//获取当前包名
	public String getCurrentPack(){
		String pac="";
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			String command = "adb -s "+ DeviceAndPack.deivceid + " shell dumpsys window|findstr mCurrentFocus";
			logger.info(command);
			String result = adbUtil.getStringByADB(command);
			logger.info(result);
			//mCurrentFocus=Window{65019b9 u0 com.huawei.android.launcher/com.huawei.android.launcher.unihome.UniHomeLauncher}
			if (result!=null && !result.equals("") && !result.contains("null")){
				String pack = result.trim().split("\\s+")[2];
				if (!pack.contains("StatusBar")){
					pac = pack.split("\\/")[0];
				}else {
					logger.info("可能锁屏了");
				}
			}

		}
		return pac;
	}
	public List<String> getAllFPS(){
		List<String> allFPS=new ArrayList<String>();
		if (DeviceAndPack.deivceid!=null&&DeviceAndPack.packagename!=null) {
			String command = "adb -s " + DeviceAndPack.deivceid + " shell dumpsys gfxinfo " + DeviceAndPack.packagename;
//			logger.info("adb command:" + command);
			List<String> result = adbUtil.getListByADB(command);
			//返回的绘制每一行代表一帧，获取所有行表示这次绘制的总帧数
			int start = 0;
			int mid = 0;
			int end = 0;
			String currentActivity = getCurrentActivity();
			if (!currentActivity.equals("")) {
				for (int i = 0; i < result.size(); i++) {
					if (result.get(i).contains("Profile data in ms:")) {
						start = i;
					} else if (result.get(i).contains("View hierarchy:")) {
						end = i;
					} else if (result.get(i).contains(currentActivity)) {
						if (mid == 0) {
							mid = i;
						}
					}
				}
				if (end>0 && mid >0) {
					if (end - start > 3) {
						if (end - mid > 2) {
							for (int i = mid + 2; i < end; i++) {
								allFPS.add(result.get(i));
							}
						}
					}
				}
			}
		}
		return allFPS;
	}
	public int getFPS(){
		int fps=0;
		List<String> all = getAllFPS();
		int frame_count= all.size();
		int jank_count = 0;
		int vsync_overtime = 0;
		if (all.size()>0) {
			for (String eve : all) {
				String[] time_block = eve.split("\\s+");
				try {
					float render_time = Float.parseFloat(time_block[0]) + Float.parseFloat(time_block[1]) + Float.parseFloat(time_block[2]) + Float.parseFloat(time_block[3]);
					if (render_time > 16.67) {
						jank_count++;
						if (render_time % 16.67 == 0) {
							vsync_overtime = vsync_overtime + (int) (render_time / 16.67) - 1;
						} else {
							vsync_overtime = vsync_overtime + (int) (render_time / 16.67);
						}
					}
				}catch (Exception e){
					continue;
				}
			}
			fps= (int) (frame_count * 60 / (frame_count + vsync_overtime));
		}
		return fps;
	}
	//获取当前activity
	public String getCurrentActivity(){
		String activity="";
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			String command = "adb -s "+ DeviceAndPack.deivceid + " shell dumpsys window|findstr mCurrentFocus";
			String result = adbUtil.getStringByADB(command);
			//mCurrentFocus=Window{65019b9 u0 com.huawei.android.launcher/com.huawei.android.launcher.unihome.UniHomeLauncher}
			if (StringTool.stringIsNotNull(result)){
				//有时获取到的activity为空mCurrentFocus=null
				if (!result.contains("null")) {
					String pack = result.trim().split("\\s+")[2];
					if (!pack.contains("StatusBar") && !pack.contains("PopupWindow") && !pack.contains("null")) {
						try {
							activity = pack.split("\\/")[1];
							activity = activity.substring(0, activity.length() - 1);
						}catch (Exception e){
							return activity;
						}
					}
				}
			}
		}
		return activity;
	}
//	public static void main(String[] args) {
//		DeviceAndPack deviceAndPack = new DeviceAndPack();
//		deviceAndPack.setDeivceid("Q5S5T19529000632");
//		deviceAndPack.setPackagename("com.tencent.mm");
//		AppInfo appInfo = new AppInfo();
////		appInfo.getAllFPS();
//		while(true) {
//			System.out.println(appInfo.getAllFPS().size());
//			try {
//				Thread.sleep(1000);
//			}catch (Exception e){}
//		}
//	}
}
