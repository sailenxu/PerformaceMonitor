package com.pm.util;

import com.pm.log.LogDemo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 由于adb执行时，首次启动服务有启动信息，使用adb时需要把启动信息过滤掉
 * @author sai
 *
 */
public class AdbUtil {
	private final static Logger logger = Logger.getLogger(LogDemo.class);
	private CmdTool cmdUtil = new CmdTool();
	/**
	 * 执行adb指令，过滤启动信息,返回list集合
	 * @param zhiling
	 * @return
	 */
	public List<String> getListByADB(String zhiling){
//		logger.info("adb command:"+zhiling);
		List<String> adblist=new ArrayList<String>();
		for(String s:cmdUtil.getListByCmd(zhiling)){
			if (s!=null&&!s.equals("")) {//去掉换行，有的手机会将换行返回
				if (s.contains("daemon")) {
					continue;
				} else {
					adblist.add(s.trim());
				}
			}
		}
		return adblist;
	}
	public String getStringByADB(String command){
		return cmdUtil.getStringByCmd(command);
	}
	/**
	 * 获取当前路径，使用当前路径下的adb
	 * @return
	 */
	final public String getAdbPath(){
		String adbpath = System.getProperty("user.dir")+"\\adb.exe";
		return adbpath;
	}

	/**
	 * 执行adb命令
	 * @param zhiling
	 */
	public void runADBNoRequest(String zhiling){
		cmdUtil.singleCmd(zhiling);
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		AdbUtil adbUtil=new AdbUtil();
//		for(String s:adbUtil.getListByADB("adb devices")){
//			System.out.println(s+"6666");
//		}
//	}

}
