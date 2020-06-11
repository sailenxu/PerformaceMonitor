package com.pm.perfordata;

public class DeviceAndPack {
    //增加单例模式
    private static DeviceAndPack deviceAndPack = new DeviceAndPack();
    public static DeviceAndPack getDeviceAndPack() {
        return deviceAndPack;
    }

    public static String deivceid;
    public static String packagename;
    public static boolean isWriteExcel=false;
    public void setDeivceid(String deivceid) {
        this.deivceid = deivceid;
    }
    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }
    public void setIsWriteExcel(boolean isWriteExcel){
        this.isWriteExcel = isWriteExcel;
    }
}
