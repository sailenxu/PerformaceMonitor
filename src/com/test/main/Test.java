package com.test.main;

import com.test.perfordata.DeviceAndPack;

public class Test {
    public void test1(){
        DeviceAndPack dp = new DeviceAndPack();
        dp.setDeivceid("123");
    }
    public void test2(){
        DeviceAndPack dp2 = new DeviceAndPack();
        dp2.setPackagename("packagename");
    }
    public static void main(String[] args) {
        Test t=new Test();
        t.test1();
        t.test2();
        System.out.println(DeviceAndPack.deivceid+":::"+DeviceAndPack.packagename);
    }
}
