package com.test.page;

import com.test.log.LogDemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.test.main.StartMonitor;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;

/**
 * 20200427:开始搞界面
 */
public class MainActivity {
    private final static Logger logger = Logger.getLogger(LogDemo.class);

    public static void main(String[] args) {
        JFrame jFrame = new DropTargetFrame();
        jFrame.setTitle("PerformanceMonitor--by sai");
        jFrame.setPreferredSize(new Dimension(1500, 1000));
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("closing……");
                System.exit(0);
            }
        });
        JPanel topPanle = new JPanel();
        topPanle.add(new DevicePackPanel().getDevicePackPanel());
        topPanle.add(new DeviceInfoPanel().getDeviceInfoPanel());
        jFrame.add(topPanle);
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BorderLayout());
        westPanel.add(new MonkeyPanel().getMonkeyPanel(), BorderLayout.NORTH);
        westPanel.add(new LogPanel().getLogPanel(), BorderLayout.SOUTH);
        jFrame.add(westPanel);
//        jFrame.add(new PerforPanel().getPerforPanel(), BorderLayout.NORTH);

        StartMonitor startMonitor = new StartMonitor(jFrame);
        startMonitor.run();

        jFrame.setVisible(true);

        //如何在前端添加性能监测折线图？使用多线程来抓性能数据似乎再此不适用，因为需要实时数据，将瞬时数据直接给到前端来显示，而不是收集一个list来return
//        StartMonitor monitor=new StartMonitor(DeviceAndPack.packagename,DeviceAndPack.deivceid);
//        Thread t1=new Thread(monitor);
//        t1.start();
    }
}
