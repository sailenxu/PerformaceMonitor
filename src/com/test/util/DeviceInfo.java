package com.test.util;

import java.util.List;

public class DeviceInfo {
	private AdbUtil adbUtil = new AdbUtil();
	private CmdTool cmdTool=new CmdTool();
	private String deviceId;
	
	public DeviceInfo(String deviceId) {
		// TODO Auto-generated constructor stub
		this.deviceId=deviceId;
	}
	/***
	 * 获取设备的产品型号
	 * @param deviceId
	 * @return
	 */
	public String getOs(){
		String os="";
		if (deviceId!=null&&deviceId!="") {
			
			List<String> list=cmdTool.getListByCmd(adbUtil.getAdbPath()+" -s "+deviceId+" shell getprop ro.product.model");
			for(String s:list){
				if (s!=null&&s!="") {
					if (s.contains("=")) {
						String[] osSplit=s.split("=");
						os=osSplit[1];
						break;
					}
				}
			}
		}
		if (os.equals("")||os==null) {
			os="sorry，没获取到……";
		}
		return os;
	}
	/***
	 * 获取手机分辨率
	 * @param deviceId
	 * @return
	 */
	public String getDp(){
		String dp="";
		if (deviceId!=null&&deviceId!="") {
			List<String> list=cmdTool.getListByCmd(adbUtil.getAdbPath()+" -s "+deviceId+" shell dumpsys window|findstr init");
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
	 * @param deviceId
	 * @return
	 */
	public String getOsVersionCode(){
		String versionCode="";
		if (deviceId!=null&&deviceId!="") {
			List<String> list=cmdTool.getListByCmd(adbUtil.getAdbPath()+" -s "+deviceId+" shell getprop ro.build.version.release");
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
	 * @param deviceId
	 * @return
	 */
	public String getIMEI(){
		String imei="";
		if (deviceId!=null&&deviceId!="") {
			List<String> list=cmdTool.getListByCmd(adbUtil.getAdbPath()+" -s "+deviceId+" shell dumpsys iphonesubinfo");
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
}
