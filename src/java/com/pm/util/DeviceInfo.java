package com.pm.util;

import com.pm.log.LogDemo;
import com.pm.perfordata.DeviceAndPack;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.ResourceBundle;

public class DeviceInfo {
	private final static Logger logger = Logger.getLogger(LogDemo.class);
	private AdbUtil adbUtil = new AdbUtil();
	private CmdTool cmdTool=new CmdTool();
	private static DeviceInfo deviceInfo = new DeviceInfo();
	//增加单例模式，减少对象的初始化
	public static DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}
	/**
	 * 获取手机品牌
	 * @return
	 */
	public String getBrand() {
		String brand = "";
		if (DeviceAndPack.deivceid != null && !DeviceAndPack.deivceid.equals("") ) {
			List<String> list = cmdTool.getListByCmd("adb -s " + DeviceAndPack.deivceid + " shell getprop ro.product.brand");
			for (String s : list) {
				if (s != null && s != "") {
					brand = s;
					break;
				}
			}
		}
		if (brand.equals("") || brand == null) {
			brand = "sorry，没获取到……";
		}
		return brand;
	}
	/***
	 * 获取设备的产品型号
	 * @return
	 */
	public String getModel(){
		String model="";
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			List<String> list=cmdTool.getListByCmd("adb -s "+DeviceAndPack.deivceid+" shell getprop ro.product.model");
			for(String s:list){
				if (s!=null&&s!="") {
					model=s;
					break;
				}
			}
		}
		if (model.equals("")||model==null) {
			model="sorry，没获取到……";
		}
		return model;
	}
	/***
	 * 获取手机分辨率
	 * @return
	 */
	public String getDp(){
		String dp="";
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			List<String> list=cmdTool.getListByCmd("adb -s "+DeviceAndPack.deivceid+" shell dumpsys window|findstr init");
			for(String s:list){
				if(s!=null&&s!=""){
					if (s.contains("init")) {
						String[] split=s.split(" ");
						for (int i = 0; i < split.length; i++) {
							if (split[i].contains("init")) {
								String[] init=split[i].split("=");
								dp=init[1];
								break;
							}
						}
						break;
					}
				}
			}
		}
		if (dp.equals("")||dp==null) {
			dp="sorry，没获取到……";
		}
		return dp;
	}
	/***
	 * 获取手机系统版本
	 * @return
	 */
	public String getOsVersionCode(){
		String versionCode="";
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			List<String> list=cmdTool.getListByCmd("adb -s "+DeviceAndPack.deivceid+" shell getprop ro.build.version.release");
			for(String s:list){
				if (s!=null&&s!="") {
					versionCode=s;
					break;
				}
			}
			if (versionCode.equals("")||versionCode==null) {
				versionCode="sorry，没获取到……";
			}
		}
		return versionCode;
	}
	/***
	 * 获取手机imei
	 * @return
	 */
	public String getIMEI(){
		String imei="";
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			List<String> list=cmdTool.getListByCmd("adb -s "+DeviceAndPack.deivceid+" shell dumpsys iphonesubinfo");
			for(String s:list){
				if(s!=null&&s!=""){
					if (s.contains("Device")) {
						String[] ime=s.split("=");
						imei=ime[1];
						break;
					}
				}
			}
			if (imei.equals("")||imei==null) {
				imei="sorry，没获取到……";
			}
		}
		return imei;
	}
	/**
	 * 安装apk
	 * @param path
	 */
	public void installAPK(String path){
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			String command = "adb -s " + DeviceAndPack.deivceid + " install -r " + path;
			logger.info("adb command:" + command);
			adbUtil.runADBNoRequest(command);
//		runADB("adb -s "+ DeviceAndPack.deivceid+" install -r "+path);
		}
	}
	public void clearLogcat(){
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			String command = "adb -s " + DeviceAndPack.deivceid + " shell logcat -c";
			logger.info("adb command:" + command);
			adbUtil.runADBNoRequest(command);
		}
	}
	/**
	 * 获取设备已安装app
	 * @return
	 */
	public String[] getAllPack(){
		String[] packages = null;
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			String command="adb -s "+ DeviceAndPack.deivceid +" shell pm list package";
			List<String> result= adbUtil.getListByADB(command);
			packages=new String[result.size()];
			for (int i=0; i<result.size(); i++){
				if (result.get(i)!=null&&!result.get(i).equals("")) {
					packages[i] = result.get(i).split(":")[1];
				}
			}
		}else {
			logger.info("设备有问题……");
		}
		return packages;
	}

	/**
	 * 截图步骤：adb执行截图，将手机里的pull到电脑，删除手机中图片
	 */
	public void screenShot() {
		if (DeviceAndPack.deivceid!=null && !DeviceAndPack.deivceid.equals("")) {
			long screenName = System.currentTimeMillis();
			String screenCommand = "adb -s "+DeviceAndPack.deivceid+" shell screencap -p /sdcard/"+String.valueOf(screenName)+".png";
			logger.info(screenCommand);
			logger.info(adbUtil.getStringByADB(screenCommand));
			adbUtil.runADBNoRequest("adb -s "+DeviceAndPack.deivceid+" pull /sdcard/"+String.valueOf(screenName)+".png "+ ResourceBundle.getBundle("config").getString("screenShotPath"));
			adbUtil.runADBNoRequest("adb -s "+DeviceAndPack.deivceid+" shell rm /sdcard/"+String.valueOf(screenName)+".png");
		}
	}
}
