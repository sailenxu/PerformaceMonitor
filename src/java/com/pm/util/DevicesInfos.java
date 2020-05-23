package com.pm.util;

import java.util.ArrayList;
import java.util.List;

public class DevicesInfos {

	/**
	 * 获得所有连接的设备id
	 * @return 返回设备id的数组
	 */
	public List<String> getDevicesID(){
		List<String> deviceList=new ArrayList<String>();
		List<String> resultList=new AdbUtil().getListByADB("adb devices");
		for(int i=1;i<resultList.size();i++){
			if (resultList.get(i)!=null&&resultList.get(i)!="") {
				System.out.println(resultList.get(i));
				String[] result = resultList.get(i).split("\t");
				if (result[1].equals("device")) {
					String device = result[0];
					deviceList.add(device);
				} else if (result[1].equals("offline")) {
					System.out.println(result[0] + "设备离线，请重新插拔！！！");
				} else {
					System.out.println("设备还是有问题，自己检查吧！！！");
				}
			}
			else {
				System.out.println("没有设备！！！");
			}
		}
		return deviceList;
	}
	public String[] getDevicesArray(){
		List<String> result = getDevicesID();
		String[] devices=new String[result.size()];
		for (int i=0; i<result.size(); i++){
			devices[i]=result.get(i);
		}
		return devices;
	}
}
