package com.pm.main;

import java.awt.*;

public class Test {
    private static Test test=new Test();
    public static Test getTest(){
        return test;
    }
    public void aa(){
        System.out.println("aa");
    }
}
class ccc{
    public static void main(String[] args) {
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        System.out.println(width+":::"+height);
    }
}