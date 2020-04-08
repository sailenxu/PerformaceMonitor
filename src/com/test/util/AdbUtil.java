package com.test.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 由于adb执行时，首次启动服务有启动信息，使用adb时需要把启动信息过滤掉
 * @author sai
 *
 */
public class AdbUtil {
	private CmdTool cmdUtil = new CmdTool();
	
	/**
	 * 执行adb指令，过滤启动信息,返回list集合
	 * @param zhiling
	 * @return
	 */
	public List<String> getListByADB(String zhiling){
		List<String> adblist=new ArrayList<String>();
		for(String s:cmdUtil.getListByCmd(zhiling)){
			if (s.contains("daemon")) {
				continue;
			}else {
				adblist.add(s);
			}
		}
		return adblist;
	}
	/**
	 * 获取当前路径，使用当前路径下的adb
	 * @return
	 */
	final public String getAdbPath(){
		String adbpath = System.getProperty("user.dir")+"\\adb.exe";
		return adbpath;
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		AdbUtil adbUtil=new AdbUtil();
//		for(String s:adbUtil.getListByADB("adb devices")){
//			System.out.println(s+"6666");
//		}
//	}

}
