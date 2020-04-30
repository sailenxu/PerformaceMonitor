package com.test.util;

import com.sun.org.apache.regexp.internal.RE;
import com.test.perfordata.DeviceAndPack;

import java.util.List;

public class InfoByDevice {
    private AdbUtil adbUtil=new AdbUtil();
    /**
     * 获取设备已安装app
     * @return
     */
    public String[] getAllPack(){
        String command="adb -s "+ DeviceAndPack.deivceid +" shell pm list package";
        System.out.println(command);
        List<String> result= adbUtil.getListByADB(command);
        System.out.println("size:::"+result.size());
        String[] packages=new String[result.size()];
        for (int i=0; i<result.size(); i++){
            if (result.get(i)!=null&&!result.get(i).equals("")) {
                System.out.println(result.get(i) + "++++");
                System.out.println(result.get(i).split(":").length + ":length");
                packages[i] = result.get(i).split(":")[1];
            }
        }
        System.out.println("length:::"+packages.length);
        return packages;
    }
}
