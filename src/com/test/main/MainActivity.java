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

    public JComboBox packComboBox(){
        String deviceAndPack = DeviceAndPack.deivceid;
        System.out.println(deviceAndPack);
        String[] comboValue=new InfoByDevice(deviceAndPack).getAllPack();

        JComboBox jComboBox = new JComboBox(comboValue);
        JList jList=new JList();
        JScrollPane jp=new JScrollPane(jList);
        jp.setPreferredSize(new Dimension(100, 200));

        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    //选择包名后，就可以监听应用信息
                    DeviceAndPack dp=new DeviceAndPack();
                    dp.setDeivceid(deviceAndPack);
                    dp.setPackagename(String.valueOf(jComboBox.getSelectedItem()));
                    System.out.println(DeviceAndPack.deivceid+"::::"+DeviceAndPack.packagename);
                }
            }
        });

        return jComboBox;
    }

    public static void main(String[] args) {
        MainActivity mainActivity = new MainActivity();
        DeviceAndPack deviceAndPack = new DeviceAndPack();

        JFrame jFrame = new JFrame("PerformanceMonitor--by sai");

        jFrame.setSize(1500, 1000);
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("closing……");
                System.exit(0);
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.YELLOW);
//        JButton bt1=new JButton("TEST");
//        panel.add(bt1);

        //添加设备选择
        JLabel device=new JLabel("device：");
        panel.add(device);
        DevicesInfos devicesInfos = new DevicesInfos();
        String[] packBoxValue=devicesInfos.getDevicesArray();
        JComboBox packCombo = new JComboBox(packBoxValue);
        if (packBoxValue.length>1) {
            packCombo.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == e.SELECTED) {
                        //选择设备后，需要给device赋值
                        deviceAndPack.setDeivceid(String.valueOf(packCombo.getSelectedItem()));
                        //并且要强制让package下拉框刷新，来获取最新的packages
                    }
                }
            });
        }else if(packBoxValue.length==1){
            deviceAndPack.setDeivceid(packBoxValue[0]);
            System.out.println("111111111:"+deviceAndPack.getDeivceid());
        }else{
            System.out.println("device is exception");
        }
        panel.add(packCombo);

        //添加包名选择
        JComboBox pack=mainActivity.packComboBox();
        panel.add(pack);

        jFrame.add(panel);

        jFrame.setVisible(true);
    }
}
