package com.test.main;

import org.apache.log4j.Logger;

import java.util.ResourceBundle;

public class Test {
    private static Logger logger= Logger.getLogger(Test.class);

    public static void main(String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        System.out.println(resourceBundle.getString("screenShotPath"));
    }
}
