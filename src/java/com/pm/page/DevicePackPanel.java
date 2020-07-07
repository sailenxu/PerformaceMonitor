package com.pm.page;

import com.pm.log.LogDemo;
import com.pm.main.StartMonitor;
import com.pm.perfordata.DeviceAndPack;
import com.pm.util.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;

public class DevicePackPanel {
    private final static Logger logger = Logger.getLogger(LogDemo.class);
    private JPanel devicePanel=new JPanel();

    private JLabel device=new JLabel("device：");
    private JComboBox deviceJComeboBox=new JComboBox();
    private JButton deviceRefreshButton=new JButton("刷新");
    private JButton screenshotButton = new JButton("截图");
    private JButton screenRecordButton = new JButton("录屏");
    private JButton clearLogcat = new JButton("清除logcat");
    private JLabel packagename=new JLabel("package：");
    private JComboBox packJComboBox=new JComboBox();
    private JButton packRefreshButton=new JButton("刷新");
    private JButton currentPackButton = new JButton("当前应用");
    private JButton clearCachButton = new JButton("清除缓存");
    private JButton stopAPP = new JButton("结束进程");
    private JButton uninstallButton = new JButton("卸    载");
    private JButton startMonitor = new JButton("开始采集");
    private JButton endMonitor = new JButton("停止采集");

    public JPanel getDevicePackPanel(){
//        devicePanel.setPreferredSize(new Dimension(1300, 50));
        devicePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        devicePanel.setBackground(Color.YELLOW);

        initDeviceComboBox();
        initPackComboBox();
        //device刷新按钮添加监听
        deviceRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshDeviceCombobox(deviceJComeboBox, packJComboBox);
                refreshPackCombobox();
                new DeviceInfoPanel().refreshDeviceInfoPanel();
            }
        });
        //截图按钮监听
        screenshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("截图…………");
                DeviceInfo.getDeviceInfo().screenShot();
            }
        });
        screenRecordButton.addActionListener(new ActionListener() {
            private boolean isRecording=false;
            ScreenRecord screenRecord = new ScreenRecord();
            Thread screenRecordThread = new Thread(screenRecord);
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRecording){
                    screenRecordThread.stop();
                    screenRecord.getRecord();
                    screenRecordButton.setText("录屏");
                    logger.info(screenRecordThread.getState());
                }else {
                    logger.info("录屏开始，默认3min");
                    screenRecordButton.setText("停止");
                    isRecording = true;
                    //录屏需要开启一个线程，结束时将线程stop
                    screenRecordThread.start();
                }
            }
        });
        //清除logcat按钮监听
        clearLogcat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("清除"+DeviceAndPack.deivceid+":logcat");
                DeviceInfo.getDeviceInfo().clearLogcat();
            }
        });
        //package刷新按钮监听
        packRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshPackCombobox();
            }
        });
        //当前包名按钮监听
        currentPackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String current = AppInfo.getAppInfo().getCurrentPack();
                logger.info("current:::"+current);
                if (!current.equals("")) {
                    setDefaultPack(current);
                }
            }
        });
        //清缓存按钮监听
        clearCachButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //清除cm缓存
                logger.info("清除缓存…………"+DeviceAndPack.packagename);
                AppInfo.getAppInfo().clearAPK();
            }
        });
        stopAPP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("结束进程："+DeviceAndPack.packagename);
                AppInfo.getAppInfo().stopAPP();
            }
        });
        //卸载按钮监听，卸载后pack下拉框要刷新
        uninstallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("卸载…………"+DeviceAndPack.packagename);
                AppInfo.getAppInfo().clearAPK();
                AppInfo.getAppInfo().uninstallAPK();
                refreshPackCombobox();
            }
        });
        startMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("开始采集性能数据……");
                DeviceAndPack.getDeviceAndPack().setIsWriteExcel(true);
            }
        });
        endMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("停止采集");
                DeviceAndPack.getDeviceAndPack().setIsWriteExcel(false);
                StartMonitor.setHang();//重置为0
                //停止后绘制折线图
                ExcelDeal excelDeal = new ExcelDeal(ResourceBundle.getBundle("config").getString("performancePath"));
                if (excelDeal.isExitFile()){
                    excelDeal.xchart("cpu", 0, 1, 6, 0);
                    excelDeal.xchart("mem",0, 2,6, 20);
                    excelDeal.xchart("fps",0, 3, 6, 40);
                    excelDeal.xchart("data",0, 4, 6, 60);
                }
            }
        });
        devicePanel.add(device);
        devicePanel.add(deviceJComeboBox);
        devicePanel.add(deviceRefreshButton);
        devicePanel.add(screenshotButton);
        devicePanel.add(screenRecordButton);
        devicePanel.add(clearLogcat);
        devicePanel.add(packagename);
        devicePanel.add(packJComboBox);
        devicePanel.add(packRefreshButton);
        devicePanel.add(currentPackButton);
        devicePanel.add(clearCachButton);
        devicePanel.add(stopAPP);
        devicePanel.add(uninstallButton);
        devicePanel.add(startMonitor);
        devicePanel.add(endMonitor);
        return devicePanel;
    }
    /**
     * device下拉框初始化
     */
    public void initDeviceComboBox(){
        DevicesInfos devicesInfos = new DevicesInfos();
        String[] devicesArray=devicesInfos.getDevicesArray();
        deviceJComeboBox.setModel(new DefaultComboBoxModel(devicesArray));
        deviceJComeboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == e.SELECTED) {
                    logger.info("选择设备:"+String.valueOf(deviceJComeboBox.getSelectedItem()));
                    //选择设备后，需要给device赋值
                    DeviceAndPack.getDeviceAndPack().setDeivceid(String.valueOf(deviceJComeboBox.getSelectedItem()));
                    //并且要强制让package下拉框刷新，来获取最新的packages
                    refreshPackCombobox();
                    new DeviceInfoPanel().refreshDeviceInfoPanel();
                }
            }
        });
        if (devicesArray.length>1) {
            logger.info("有多个设备，默认选中"+devicesArray[0]);
            DeviceAndPack.getDeviceAndPack().setDeivceid(devicesArray[0]);
            refreshPackCombobox();
            new DeviceInfoPanel().refreshDeviceInfoPanel();
        }else if(devicesArray.length==1){
            logger.info("检测出一个设备并选中："+devicesArray[0]);
            DeviceAndPack.getDeviceAndPack().setDeivceid(devicesArray[0]);
            //刷新pack下拉框
            refreshPackCombobox();
            new DeviceInfoPanel().refreshDeviceInfoPanel();
        }else{
            //没有设备
            logger.info("没有设备…………");
            DeviceAndPack.getDeviceAndPack().setDeivceid(null);
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
            DeviceAndPack.getDeviceAndPack().setDeivceid(devicesArray[0]);
        }else {
            logger.info("没有设备…………");
            DeviceAndPack.getDeviceAndPack().setDeivceid(null);
        }
    }
    /**
     * 刷新包名下拉框
     */
    public void refreshPackCombobox(){
        //有cm时，默认选中cm
        logger.info("刷新已安装软件列表");
        String[] comboValue=DeviceInfo.getDeviceInfo().getAllPack();
        packJComboBox.removeAllItems();
        if (comboValue!=null) {
            packJComboBox.setModel(new DefaultComboBoxModel(comboValue));
            boolean flag = false;
            for (String s : comboValue) {
                if (s.contains(ResourceBundle.getBundle("config").getString("defaultPackage"))) {
                    flag = true;
                }
            }
            if (flag) {
                setDefaultPack(ResourceBundle.getBundle("config").getString("defaultPackage"));
            }else {
                logger.info("未安装"+ResourceBundle.getBundle("config").getString("defaultPackage")+"，默认选中包名："+comboValue[0]);
                DeviceAndPack.getDeviceAndPack().setPackagename(comboValue[0]);
            }
        }
    }
    private void setDefaultPack(String packagename){
        logger.info("设置默认包名："+packagename);
        packJComboBox.setSelectedItem(packagename);
        DeviceAndPack.getDeviceAndPack().setPackagename(packagename);
        logger.info("默认包名：：：：："+DeviceAndPack.packagename);
    }
    /**
     * 包名下拉框初始化
     * @return
     */
    public void initPackComboBox(){
        JList jList=new JList();
        JScrollPane jp=new JScrollPane(jList);
        jp.setPreferredSize(new Dimension(100, 200));
        System.out.println("cpkkkkk:"+DeviceAndPack.packagename);
        packJComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    //选择包名后，就可以监听应用信息
                    DeviceAndPack dp=DeviceAndPack.getDeviceAndPack();
                    dp.setPackagename(String.valueOf(packJComboBox.getSelectedItem()));
                    logger.info("选择包名："+DeviceAndPack.packagename);
                    System.out.println(dp.deivceid+"::::"+DeviceAndPack.packagename);
                }
            }
        });
    }
}
