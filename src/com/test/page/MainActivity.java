package com.test.page;

import com.test.main.StartMonitor;
import com.test.perfordata.DeviceAndPack;
import com.test.util.DeviceInfo;
import com.test.util.DevicesInfos;
import com.test.util.InfoByDevice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 20200427:开始搞界面
 */
public class MainActivity {

    /**
     * device下拉框初始化
     * @param deviceJComeboBox
     * @param packJComboBox
     */
    public void initDeviceComboBox(JComboBox deviceJComeboBox, JComboBox packJComboBox, JLabel dpJlabel){
        DevicesInfos devicesInfos = new DevicesInfos();
        String[] devicesArray=devicesInfos.getDevicesArray();
        deviceJComeboBox.setModel(new DefaultComboBoxModel<>(devicesArray));
        if (devicesArray.length>1) {
            new DeviceAndPack().setDeivceid(devicesArray[0]);
            refreshPackCombobox(packJComboBox);
            refreshDp(dpJlabel);
            deviceJComeboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == e.SELECTED) {
                        //选择设备后，需要给device赋值
                        new DeviceAndPack().setDeivceid(String.valueOf(deviceJComeboBox.getSelectedItem()));
                        System.out.println(DeviceAndPack.deivceid+":::::::::::");
                        //并且要强制让package下拉框刷新，来获取最新的packages
                        refreshPackCombobox(packJComboBox);
                        refreshDp(dpJlabel);
                    }
                }
            });
        }else if(devicesArray.length==1){
            new DeviceAndPack().setDeivceid(devicesArray[0]);
            //刷新pack下拉框
            refreshPackCombobox(packJComboBox);
        }else{
            System.out.println("device is exception");
        }
    }

    /**
     * 设备下拉框刷新
     * @param deviceCombobox
     * @param packJComboBox
     */
    public void refreshDeviceCombobox(JComboBox deviceCombobox, JComboBox packJComboBox, JLabel dpJlabel){
        System.out.println("refresh device");
        initDeviceComboBox(deviceCombobox, packJComboBox, dpJlabel);
    }
    /**
     * 刷新包名下拉框
     * @param packJComboBox
     */
    public void refreshPackCombobox(JComboBox packJComboBox){
        packJComboBox.removeAllItems();
        packJComboBox.setModel(new DefaultComboBoxModel<>(new InfoByDevice().getAllPack()));
    }
    public void refreshBrand(JLabel brand){
        brand.setText(new DeviceInfo().getBrand());
    }
    /**
     * 分辨率刷新
     * @param fenbianlv
     */
    public void refreshDp(JLabel fenbianlv){
        fenbianlv.setText(new DeviceInfo().getDp());
    }

    /**
     * 包名下拉框初始化
     * @return
     */
    public JComboBox packComboBox(){
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
                    dp.setPackagename(String.valueOf(jComboBox.getSelectedItem()));
                    System.out.println(dp.deivceid+"::::"+DeviceAndPack.packagename);
                }
            }
        });
        return jComboBox;
    }

    public static void main(String[] args) {
        MainActivity mainActivity = new MainActivity();
        JLabel pinpai = new JLabel("品牌：");
        JLabel brand = new JLabel();
        JLabel fenbian=new JLabel("分辨率：");
        JLabel dpJlabel=new JLabel();

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

        JButton refreshButton=new JButton("刷新");

        //添加设备选择
        JLabel device=new JLabel("device：");
        JComboBox packJComboBox=mainActivity.packComboBox();
        JComboBox deviceJComeboBox = new JComboBox();
        mainActivity.initDeviceComboBox(deviceJComeboBox, packJComboBox, dpJlabel);
        //刷新按钮添加监听
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainActivity.refreshDeviceCombobox(deviceJComeboBox, packJComboBox, dpJlabel);
                mainActivity.refreshPackCombobox(packJComboBox);
                mainActivity.refreshBrand(brand);
                mainActivity.refreshDp(dpJlabel);
            }
        });



        panel.add(device);
        panel.add(deviceJComeboBox);
        panel.add(refreshButton);
        //添加包名选择
        JLabel packagename=new JLabel("package：");
        panel.add(packagename);
        panel.add(packJComboBox);


        JPanel jp2 = new JPanel();
        jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp2.setBackground(Color.GRAY);

        jp2.add(pinpai);
        jp2.add(brand);
        jp2.add(fenbian);
        jp2.add(dpJlabel);

        jFrame.add(panel);
        jFrame.add(jp2);
        jFrame.setVisible(true);

        //如何在前端添加性能监测折线图？使用多线程来抓性能数据似乎再此不适用，因为需要实时数据，将瞬时数据直接给到前端来显示，而不是收集一个list来return
        StartMonitor monitor=new StartMonitor(DeviceAndPack.packagename,DeviceAndPack.deivceid);
        Thread t1=new Thread(monitor);
        t1.start();
    }
}
