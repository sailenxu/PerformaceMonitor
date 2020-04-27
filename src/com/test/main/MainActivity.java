package com.test.main;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 20200427:开始搞界面
 */
public class MainActivity {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("PerformanceMonitor--by sai");
        jFrame.setVisible(true);
        jFrame.setSize(500, 500);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("closing……");
                System.exit(0);
            }
        });
    }
}
