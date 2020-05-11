package com.test.page;

import com.test.log.LogDemo;
import com.test.main.StartMonitor;
import com.test.perfordata.DeviceAndPack;
import com.test.util.AdbUtil;
import com.test.util.DeviceInfo;
import com.test.util.DevicesInfos;
import com.test.util.InfoByDevice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.apache.log4j.Logger;

/**
 * 20200427:开始搞界面
 */
public class MainActivity {
    private final static Logger logger = Logger.getLogger(LogDemo.class);

    /**
     * device下拉框初始化
     * @param deviceJComeboBox
     * @param packJComboBox
     */
    public void initDeviceComboBox(final JComboBox deviceJComeboBox, final JComboBox packJComboBox){
        DevicesInfos devicesInfos = new DevicesInfos();
        String[] devicesArray=devicesInfos.getDevicesArray();
        deviceJComeboBox.setModel(new DefaultComboBoxModel(devicesArray));
        if (devicesArray.length>1) {
            logger.info("有多个设备，默认选中"+devicesArray[0]);
            new DeviceAndPack().setDeivceid(devicesArray[0]);
            refreshPackCombobox(packJComboBox);
            new DeviceInfoPanel().refreshDeviceInfoPanel();
            deviceJComeboBox.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == e.SELECTED) {
                        logger.info("选择设备:"+String.valueOf(deviceJComeboBox.getSelectedItem()));
                        //选择设备后，需要给device赋值
                        new DeviceAndPack().setDeivceid(String.valueOf(deviceJComeboBox.getSelectedItem()));
                        System.out.println(DeviceAndPack.deivceid+":::::::::::");
                        //并且要强制让package下拉框刷新，来获取最新的packages
                        refreshPackCombobox(packJComboBox);
                        new DeviceInfoPanel().refreshDeviceInfoPanel();
                    }
                }
            });
        }else if(devicesArray.length==1){
            logger.info("检测出一个设备并选中："+devicesArray[0]);
            new DeviceAndPack().setDeivceid(devicesArray[0]);
            //刷新pack下拉框
            refreshPackCombobox(packJComboBox);
        }else{
            //没有设备
            logger.info("没有设备…………");
//            System.out.println("device is exception");
//            new DeviceAndPack().setDeivceid("");
        }
    }

    /**
     * 设备下拉框刷新
     * @param deviceCombobox
     * @param packJComboBox
     */
    public void refreshDeviceCombobox(JComboBox deviceCombobox, JComboBox packJComboBox){
        logger.info("刷新设备列表");
        System.out.println("refresh device");
        initDeviceComboBox(deviceCombobox, packJComboBox);
    }
    /**
     * 刷新包名下拉框
     * @param packJComboBox
     */
    public void refreshPackCombobox(JComboBox packJComboBox){
        logger.info("刷新已安装软件列表");
        packJComboBox.removeAllItems();
        packJComboBox.setModel(new DefaultComboBoxModel(new InfoByDevice().getAllPack()));
    }

    /**
     * 包名下拉框初始化
     * @return
     */
    public JComboBox packComboBox(){
        String[] comboValue=new InfoByDevice().getAllPack();

        final JComboBox jComboBox = new JComboBox(comboValue);
        JList jList=new JList();
        JScrollPane jp=new JScrollPane(jList);
        jp.setPreferredSize(new Dimension(100, 200));

        jComboBox.addItemListener(new ItemListener() {
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
        final MainActivity mainActivity = new MainActivity();
        final DeviceInfoPanel deviceInfoPanel=new DeviceInfoPanel();
        JPanel devicePanel=deviceInfoPanel.getDeviceInfoPanel();

        JFrame jFrame = new DropTargetFrame();
        jFrame.setTitle("PerformanceMonitor--by sai");
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

        JButton deviceRefreshButton=new JButton("刷新");
        JButton packRefreshButton=new JButton("刷新");
        JButton clearCach = new JButton("清除cm缓存");
        JButton uninstallCM = new JButton("卸载cm");
        //添加设备选择
        JLabel device=new JLabel("device：");
        final JComboBox packJComboBox=mainActivity.packComboBox();
        final JComboBox deviceJComeboBox = new JComboBox();
        mainActivity.initDeviceComboBox(deviceJComeboBox, packJComboBox);
        //刷新按钮添加监听
        deviceRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainActivity.refreshDeviceCombobox(deviceJComeboBox, packJComboBox);
                mainActivity.refreshPackCombobox(packJComboBox);
                deviceInfoPanel.refreshDeviceInfoPanel();
            }
        });
        //package刷新按钮监听
        packRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainActivity.refreshPackCombobox(packJComboBox);
            }
        });
        
        panel.add(device);
        panel.add(deviceJComeboBox);
        panel.add(deviceRefreshButton);
        //添加包名选择
        JLabel packagename=new JLabel("package：");
        panel.add(packagename);
        panel.add(packJComboBox);

        panel.add(packRefreshButton);

        clearCach.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //清除cm缓存
                new AdbUtil().clearAPK("com.cleanmaster.mguard_cn");
            }
        });
        uninstallCM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdbUtil().clearAPK("com.cleanmaster.mguard_cn");
                new AdbUtil().uninstallAPK("com.cleanmaster.mguard_cn");
            }
        });
        panel.add(clearCach);
        panel.add(uninstallCM);

        jFrame.add(panel);
        jFrame.add(devicePanel);

        JPanel logJPanel = new JPanel();
        JTextArea logta = new JTextArea();
        JScrollPane logsp = new JScrollPane(logta);
        logta.setColumns(40);
        logta.setRows(15);
        logsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        LogDemo logDemoFrame = new LogDemo(logta, logsp);
        logDemoFrame.initLog();
        logJPanel.add(logsp);


        jFrame.add(logJPanel);
        jFrame.setVisible(true);

        //如何在前端添加性能监测折线图？使用多线程来抓性能数据似乎再此不适用，因为需要实时数据，将瞬时数据直接给到前端来显示，而不是收集一个list来return
//        StartMonitor monitor=new StartMonitor(DeviceAndPack.packagename,DeviceAndPack.deivceid);
//        Thread t1=new Thread(monitor);
//        t1.start();
    }
}
