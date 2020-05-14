package com.test.main;

import org.apache.log4j.Logger;

import java.util.ResourceBundle;

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
        Test.getTest().aa();
    }
}