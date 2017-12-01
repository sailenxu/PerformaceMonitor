package com.test.data;

import java.util.ArrayList;
import java.util.List;

import com.test.entity.CPUInfo;
import com.test.entity.MemInfo;

public class MonitorData {
	/***
	 * 存储cpu数据的集合
	 * @return
	 */
	public static List<CPUInfo> getCPUinfolist(){
		List<CPUInfo> cpuInfos=new ArrayList<CPUInfo>();
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
