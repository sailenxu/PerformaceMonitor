package com.test.util;

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
        List<String> result= adbUtil.getListByADB(command);
        String[] packages=new String[result.size()];
        for (int i=0; i<result.size(); i++){
            if (result.get(i)!=null&&!result.get(i).equals("")) {
                packages[i] = result.get(i).split(":")[1];
            }
        }
        return packages;
    }
}
