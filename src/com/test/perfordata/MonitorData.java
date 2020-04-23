package com.test.perfordata;

import java.util.ArrayList;
import java.util.List;

import com.test.perforentity.CPUInfo;
import com.test.perforentity.MemInfo;

public class MonitorData {
	/***
	 * 存储cpu数据的集合
	 * @return
	 */
	public static List<CPUInfo> cpuInfos = new ArrayList<CPUInfo>();
	public static List<CPUInfo> getCPUinfolist(){
		return cpuInfos;
	}
	/***
	 * 存储mem数据的集合
	 * @return
	 */
	public static List<MemInfo> getMeminfolist(){
		List<MemInfo> memInfos=new ArrayList<MemInfo>();
		return memInfos;
	}
	
}
