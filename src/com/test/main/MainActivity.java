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
        String[] comboValue=new InfoByDevice().getAllPack();

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
        String[] devicesArray=devicesInfos.getDevicesArray();
        JComboBox packCombo = new JComboBox(devicesArray);


        panel.add(packCombo);

        //添加包名选择
        JLabel packagename=new JLabel("device：");
        panel.add(packagename);
        JComboBox packJComboBox=mainActivity.packComboBox();
        panel.add(packJComboBox);



        if (devicesArray.length>1) {
            packCombo.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == e.SELECTED) {
                        //选择设备后，需要给device赋值
                        new DeviceAndPack().setDeivceid(String.valueOf(packCombo.getSelectedItem()));
                        System.out.println(DeviceAndPack.deivceid+":::::::::::");
                        //并且要强制让package下拉框刷新，来获取最新的packages
                        packJComboBox.removeAllItems();
                        packJComboBox.setModel(new DefaultComboBoxModel<>(new InfoByDevice().getAllPack()));
                    }
                }
            });
        }else if(devicesArray.length==1){
            new DeviceAndPack().setDeivceid(devicesArray[0]);
        }else{
            System.out.println("device is exception");
        }


        jFrame.add(panel);

        jFrame.setVisible(true);
    }
}
