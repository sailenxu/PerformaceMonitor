package com.test.main;

import com.test.perfordata.DeviceAndPack;
import com.test.util.AppInfo;
import com.test.util.DevicesInfos;
import com.test.util.InfoByDevice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * 20200427:开始搞界面
 */
public class MainActivity {
    public static void main(String[] args) {
        DeviceAndPack deviceAndPack = new DeviceAndPack();

        JFrame jFrame = new JFrame("PerformanceMonitor--by sai");

        jFrame.setSize(1500, 1000);
        jFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("closing……");
                System.exit(0);
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 500, 500));
        panel.setBackground(Color.YELLOW);
//        JButton bt1=new JButton("TEST");
//        panel.add(bt1);

        //添加设备选择
        JLabel device=new JLabel("device：");
        panel.add(device);
        DevicesInfos devicesInfos = new DevicesInfos();
        String[] packBoxValue=devicesInfos.getDevicesArray();
        JComboBox packCombo = new JComboBox(packBoxValue);
        packCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange()==e.SELECTED){
                    System.out.println("12322131");
                }
            }
        });


        panel.add(packCombo);

        //添加包名选择
        InfoByDevice info=new InfoByDevice("Q5S5T19529000632");
        String[] comboValue=info.getAllPack();

        JLabel label=new JLabel("包名：");
        panel.add(label);
        JComboBox jComboBox = new JComboBox(comboValue);
        JList jList=new JList();
        JScrollPane jp=new JScrollPane(jList);
        jp.setPreferredSize(new Dimension(100, 200));

        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    //选择包名后，就可以监听应用信息
                    System.out.println("package::::" + jComboBox.getSelectedItem());
                    deviceAndPack.setPackagename(String.valueOf(jComboBox.getSelectedItem()));
                }
            }
        });

        panel.add(jComboBox);

        jFrame.add(panel);

        jFrame.setVisible(true);
    }
}
