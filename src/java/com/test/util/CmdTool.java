package com.test.util;

import com.test.log.LogDemo;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/***
 * cmd命令执行类
 * @author xusai
 *packagename：com.xstore.sevenfresh
 */
public class CmdTool {
	private final static Logger logger = Logger.getLogger(LogDemo.class);
	/***
	 * 执行cmd命令，不获取返回值
	 * @param cmdString cmd命令
	 */
	public static void singleCmd(String cmdString){
		Runtime r=Runtime.getRuntime();
		try {
			logger.info("cmd command:"+cmdString);
			Process process=r.exec("cmd /c "+cmdString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/***
	 * 执行cmd命令并将结果返回list集合
	 * @param cmdString cmd命令
	 * @return
	 */
	public static List<String> getListByCmd(String cmdString){
		List<String> list=new ArrayList<String>();
		try {
			Process process=Runtime.getRuntime().exec("cmd /c "+cmdString);
			logger.info("cmd command:"+cmdString);
			InputStream in=process.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			String lineString=null;
			while (StringTool.replaceBlank((lineString=reader.readLine()))!=null) {
				list.add(lineString.trim());
			}
			logger.info("result:"+list);
			process.waitFor();
			process.destroy();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	/***
	 * 执行cmd命令并将结果返回string类型
	 * @param cmdString
	 * @return
	 */
	public String getStringByCmd(String cmdString){
		String getString="";
		try {
			Process process =Runtime.getRuntime().exec("cmd /c "+cmdString);
			logger.info("cmd command:"+cmdString);
			InputStream in=process.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			String line=null;
			while ((line=reader.readLine())!=null) {
				getString=getString+line+"\n";
			}
			logger.info(getString);
			process.waitFor();
			process.destroy();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return getString;
	}

	/**
	 * 执行cmd命令，返回bufferreader，将结果流返回，实时获取结果
	 * @param cmdString
	 * @return
	 */
	public BufferedReader getBRByCmd(String cmdString) throws IOException {
		Process process =Runtime.getRuntime().exec("cmd /c "+cmdString);
		logger.info("cmd command:"+cmdString);
		InputStream in=process.getInputStream();
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		return reader;
	}
}
