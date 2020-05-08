package com.test.main;

import com.test.util.AdbUtil;
import com.test.util.CmdTool;

import java.io.BufferedReader;
import java.io.IOException;

public class Monkey {
    private String packName;
    private String device;
    private CmdTool cmdTool = new CmdTool();
    public Monkey(String packName,String device){
        this.packName = packName;
        this.device = device;
    }
    public BufferedReader monkeyTest(String thrtime, String cishu) throws IOException {
        BufferedReader br = cmdTool.getBRByCmd("adb -s "+device+" shell monkey -p "+packName+" -v -v --throttle "+thrtime+" "+cishu);
        return br;
    }

//    public static void main(String[] args) throws IOException {
//        Monkey monkey=new Monkey("com.jingdong.app.mall", "Q5S5T19529000632");
//        monkey.monkeyTest("1000", "30");
//    }

}
