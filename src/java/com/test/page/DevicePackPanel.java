package com.test.page;

import com.test.log.LogDemo;
import com.test.perfordata.DeviceAndPack;
import com.test.util.AppInfo;
import com.test.util.DeviceInfo;
import com.test.util.DevicesInfos;
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
    private JButton clearLogcat = new JButton("清除logcat");
    private JLabel packagename=new JLabel("package：");
    private JComboBox packJComboBox=new JComboBox();
    private JButton packRefreshButton=new JButton("刷新");
    private JButton clearCachButton = new JButton("清除缓存");
    private JButton uninstallButton = new JButton("卸    载");

    public JPanel getDevicePackPanel(){
        devicePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        devicePanel.setBackground(Color.YELLOW);

        initDeviceComboBox(deviceJComeboBox, packJComboBox);
        initPackComboBox(packJComboBox);
        //device刷新按钮添加监听
        deviceRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshDeviceCombobox(deviceJComeboBox, packJComboBox);
                refreshPackCombobox(packJComboBox);
                new DeviceInfoPanel().refreshDeviceInfoPanel();
            }
        });
        //截图按钮监听
        screenshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("截图…………");
                new DeviceInfo().screenShot();
            }
        });
        //清除logcat按钮监听
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
                refreshPackCombobox(packJComboBox);
            }
        });
        //清缓存按钮监听
        clearCachButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //清除cm缓存
                logger.info("清除缓存…………"+DeviceAndPack.packagename);
                new AppInfo().clearAPK();
            }
        });
        //卸载按钮监听，卸载后pack下拉框要刷新
        uninstallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("卸载…………"+DeviceAndPack.packagename);
                new AppInfo().clearAPK();
                new AppInfo().uninstallAPK();
                refreshPackCombobox(packJComboBox);
            }
        });

        devicePanel.add(device);
        devicePanel.add(deviceJComeboBox);
        devicePanel.add(deviceRefreshButton);
        devicePanel.add(screenshotButton);
        devicePanel.add(clearLogcat);
        devicePanel.add(packagename);
        devicePanel.add(packJComboBox);
        devicePanel.add(packRefreshButton);
        devicePanel.add(clearCachButton);
        devicePanel.add(uninstallButton);
        return devicePanel;
    }
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
                if (s.contains(ResourceBundle.getBundle("config").getString("defaultPackage"))) {
                    flag = true;
                }
            }
            System.out.println("flag::::+" + flag);
            packJComboBox.setModel(new DefaultComboBoxModel(comboValue));
            if (flag) {
                logger.info("设置默认包名："+ResourceBundle.getBundle("config").getString("defaultPackage"));
                packJComboBox.setSelectedItem(ResourceBundle.getBundle("config").getString("defaultPackage"));
            }else {
                logger.info("未安装"+ResourceBundle.getBundle("config").getString("defaultPackage")+"，默认选中包名："+comboValue[0]);
                new DeviceAndPack().setPackagename(comboValue[0]);
            }
        }
    }
    /**
     * 包名下拉框初始化
     * @return
     */
    public void initPackComboBox(final JComboBox packJComboBox){
        JList jList=new JList();
        JScrollPane jp=new JScrollPane(jList);
        jp.setPreferredSize(new Dimension(100, 200));
        packJComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    //选择包名后，就可以监听应用信息
                    DeviceAndPack dp=new DeviceAndPack();
                    dp.setPackagename(String.valueOf(packJComboBox.getSelectedItem()));
                    logger.info("选择包名："+DeviceAndPack.packagename);
                    System.out.println(dp.deivceid+"::::"+DeviceAndPack.packagename);
                }
            }
        });
    }
}
