package com.pm.util;

import com.pm.perfordata.DeviceAndPack;

import java.util.ResourceBundle;

public class ScreenRecord implements Runnable {
    long screenName = System.currentTimeMillis();
    @Override
    public void run() {
        if (DeviceAndPack.deivceid!=null){
            String command = "adb -s " + DeviceAndPack.deivceid + " shell screenrecord /sdcard/"+String.valueOf(screenName)+".mp4";
            System.out.println(command);
            AdbUtil.getAdbUtil().runADBNoRequest(command);
        }
    }
    //获取mp4，并删除手机本地的文件
    public void getRecord(){
        AdbUtil.getAdbUtil().runADBNoRequest("adb -s "+DeviceAndPack.deivceid+" pull /sdcard/"+String.valueOf(screenName)+".mp4 "+ ResourceBundle.getBundle("config").getString("screenShotPath"));
        AdbUtil.getAdbUtil().runADBNoRequest("adb -s "+DeviceAndPack.deivceid+" shell rm /sdcard/"+String.valueOf(screenName)+".mp4");
    }
}
