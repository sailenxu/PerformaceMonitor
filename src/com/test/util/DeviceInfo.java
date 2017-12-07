package com.test.util;

import java.util.List;

public class DeviceInfo {
	private AdbUtil adbUtil = new AdbUtil();
	/***
	 * 获取设备的产品型号
	 * @param deviceId
	 * @return
	 */
	public String getOs(String deviceId){
		String os="";
		if (deviceId!=null&&deviceId!="") {
			
			CmdTool cmd=new CmdTool();
			List<String> list=cmd.getListByCmd(adbUtil.getAdbPath()+" -s "+deviceId+" shell getprop ro.product.model");
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
}
