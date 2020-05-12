package com.test.util;

import com.test.log.LogDemo;
import com.test.perfordata.DeviceAndPack;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * ����adbִ��ʱ���״�����������������Ϣ��ʹ��adbʱ��Ҫ��������Ϣ���˵�
 * @author sai
 *
 */
public class AdbUtil {
	private final static Logger logger = Logger.getLogger(LogDemo.class);
	private CmdTool cmdUtil = new CmdTool();
	/**
	 * ִ��adbָ�����������Ϣ,����list����
	 * @param zhiling
	 * @return
	 */
	public List<String> getListByADB(String zhiling){
		logger.info("adb command:"+zhiling);
		List<String> adblist=new ArrayList<String>();
		for(String s:cmdUtil.getListByCmd(zhiling)){
			if (s!=null&&!s.equals("")) {//ȥ�����У��е��ֻ��Ὣ���з���
				if (s.contains("daemon")) {
					continue;
				} else {
					adblist.add(s.trim());
				}
			}
		}
		return adblist;
	}
	/**
	 * ��ȡ��ǰ·����ʹ�õ�ǰ·���µ�adb
	 * @return
	 */
	final public String getAdbPath(){
		String adbpath = System.getProperty("user.dir")+"\\adb.exe";
		return adbpath;
	}

	/**
	 * ִ��adb����
	 * @param zhiling
	 */
	public void runADB(String zhiling){
		cmdUtil.singleCmd(zhiling);
	}

	/**
	 * ���apk����
	 * @param packagename
	 */
	public void clearAPK(String packagename){
		String command = "adb -s "+DeviceAndPack.deivceid+" shell pm clear "+packagename;
		logger.info("adb command:"+command);
		runADB(command);
//		runADB("adb -s "+DeviceAndPack.deivceid+" shell pm clear "+packagename);
	}
	/**
	 * ��װapk
	 * @param path
	 */
	public void installAPK(String path){
		String command = "adb -s "+ DeviceAndPack.deivceid+" install -r "+path;
		logger.info("adb command:"+command);
		runADB(command);
//		runADB("adb -s "+ DeviceAndPack.deivceid+" install -r "+path);
	}
	public void uninstallAPK(String packagename){
		String command = "adb -s "+DeviceAndPack.deivceid+" uninstall "+packagename;
		logger.info("adb command:"+command);
		runADB(command);
//		runADB("adb -s "+DeviceAndPack.deivceid+" uninstall "+packagename);
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		AdbUtil adbUtil=new AdbUtil();
//		for(String s:adbUtil.getListByADB("adb devices")){
//			System.out.println(s+"6666");
//		}
//	}

}