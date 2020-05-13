package com.test.page;

import com.test.log.LogDemo;
import com.test.main.StartMonitor;
import com.test.perfordata.DeviceAndPack;
import com.test.util.*;

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
                    System.out.println("eeeeee:"+e.getStateChange());
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
            new DeviceInfoPanel().refreshDeviceInfoPanel();
        }else{
            //没有设备
            logger.info("没有设备…………");
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
//        initDeviceComboBox(deviceCombobox, packJComboBox);
        String[] devicesArray=new DevicesInfos().getDevicesArray();
        System.out.println("devicearray:"+devicesArray.length);
        deviceCombobox.removeAllItems();
        if (devicesArray.length>0){
            deviceCombobox.setModel(new DefaultComboBoxModel(devicesArray));
            new DeviceAndPack().setDeivceid(devicesArray[0]);
            refreshPackCombobox(packJComboBox);

        }else {
            logger.info("没有设备…………");
            new DeviceAndPack().setDeivceid("");
        }
    }
    /**
     * 刷新包名下拉框
     * @param packJComboBox
     */
    public void refreshPackCombobox(JComboBox packJComboBox){
        //有cm时，默认选中cm
        logger.info("刷新已安装软件列表");
        String[] comboValue=new DeviceInfo().getAllPack();
        packJComboBox.removeAllItems();
        if (comboValue!=null) {
            boolean flag = false;
            for (String s : comboValue) {
                if (s.contains("com.cleanmaster.mguard_cn")) {
                    flag = true;
                }
            }
            System.out.println("flag::::+" + flag);
            packJComboBox.setModel(new DefaultComboBoxModel(comboValue));
            if (flag) {
                logger.info("设置默认包名：com.cleanmaster.mguard_cn");
                packJComboBox.setSelectedItem("com.cleanmaster.mguard_cn");
            }else {
                logger.info("未安装com.cleanmaster.mguard_cn，默认选中包名："+comboValue[0]);
                new DeviceAndPack().setPackagename(comboValue[0]);
            }
        }
    }
    /**
     * 包名下拉框初始化
     * @return
     */
    public JComboBox packComboBox(){
        final JComboBox jComboBox = new JComboBox();
        JList jList=new JList();
        JScrollPane jp=new JScrollPane(jList);
        jp.setPreferredSize(new Dimension(100, 200));
        jComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    //选择包名后，就可以监听应用信息
                    DeviceAndPack dp=new DeviceAndPack();
                    dp.setPackagename(String.valueOf(jComboBox.getSelectedItem()));
                    logger.info("选择包名："+DeviceAndPack.packagename);
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
        JButton screenshotButton = new JButton("截图");
        JButton clearLogcat = new JButton("清除logcat");
        JButton packRefreshButton=new JButton("刷新");
        JButton clearCach = new JButton("清除缓存");
        JButton uninstallCM = new JButton("卸    载");
        //添加设备选择
        JLabel device=new JLabel("device：");
        final JComboBox packJComboBox=mainActivity.packComboBox();
        final JComboBox deviceJComeboBox = new JComboBox();
        mainActivity.initDeviceComboBox(deviceJComeboBox, packJComboBox);
        //device刷新按钮添加监听
        deviceRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainActivity.refreshDeviceCombobox(deviceJComeboBox, packJComboBox);
                mainActivity.refreshPackCombobox(packJComboBox);
                deviceInfoPanel.refreshDeviceInfoPanel();
            }
        });
        //截图按钮监听
        screenshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeviceInfo().screenShot();
            }
        });
        clearLogcat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("清除"+DeviceAndPack.deivceid+":logcat");
                new DeviceInfo().clearLogcat();
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
        panel.add(screenshotButton);
        panel.add(clearLogcat);
        //添加包名选择
        JLabel packagename=new JLabel("package：");
        panel.add(packagename);
        panel.add(packJComboBox);

        panel.add(packRefreshButton);
        //清缓存按钮监听
        clearCach.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //清除cm缓存
                logger.info("清除缓存…………"+DeviceAndPack.packagename);
                new AppInfo().clearAPK();
            }
        });
        //卸载按钮监听，卸载后pack下拉框要刷新
        uninstallCM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("卸载…………"+DeviceAndPack.packagename);
                new AppInfo().clearAPK();
                new AppInfo().uninstallAPK();
                mainActivity.refreshPackCombobox(packJComboBox);
            }
        });
        panel.add(clearCach);
        panel.add(uninstallCM);

        jFrame.add(panel);
        jFrame.add(devicePanel);
        //添加monkey执行panel
        JPanel monkeyJPanel = new MonkeyPanel().getMonkeyPanel();
        jFrame.add(monkeyJPanel);

        //添加日志打印，可以显示log信息
        JPanel logJPanel = new JPanel();
        JTextArea logta = new JTextArea();
        JScrollPane logsp = new JScrollPane(logta);
        logta.setColumns(80);
        logta.setRows(30);
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
