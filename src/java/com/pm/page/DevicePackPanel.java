package com.pm.page;

import com.pm.log.LogDemo;
import com.pm.perfordata.DeviceAndPack;
import com.pm.util.AppInfo;
import com.pm.util.DeviceInfo;
import com.pm.util.DevicesInfos;
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

    private JLabel device=new JLabel("device��");
    private JComboBox deviceJComeboBox=new JComboBox();
    private JButton deviceRefreshButton=new JButton("ˢ��");
    private JButton screenshotButton = new JButton("��ͼ");
    private JButton clearLogcat = new JButton("���logcat");
    private JLabel packagename=new JLabel("package��");
    private JComboBox packJComboBox=new JComboBox();
    private JButton packRefreshButton=new JButton("ˢ��");
    private JButton currentPackButton = new JButton("��ǰӦ��");
    private JButton clearCachButton = new JButton("�������");
    private JButton stopAPP = new JButton("��������");
    private JButton uninstallButton = new JButton("ж    ��");

    public JPanel getDevicePackPanel(){
//        devicePanel.setPreferredSize(new Dimension(1300, 50));
        devicePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        devicePanel.setBackground(Color.YELLOW);

        initDeviceComboBox();
        initPackComboBox();
        //deviceˢ�°�ť���Ӽ���
        deviceRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshDeviceCombobox(deviceJComeboBox, packJComboBox);
                refreshPackCombobox();
                new DeviceInfoPanel().refreshDeviceInfoPanel();
            }
        });
        //��ͼ��ť����
        screenshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("��ͼ��������");
                DeviceInfo.getDeviceInfo().screenShot();
            }
        });
        //���logcat��ť����
        clearLogcat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("���"+DeviceAndPack.deivceid+":logcat");
                DeviceInfo.getDeviceInfo().clearLogcat();
            }
        });
        //packageˢ�°�ť����
        packRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshPackCombobox();
            }
        });
        currentPackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String current = DeviceInfo.getDeviceInfo().getCurrentPack();
                if (!current.equals("")) {
                    setDefaultPack(current);
                }
                logger.info("ѡ��ǰ������");
            }
        });
        //�建�水ť����
        clearCachButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //���cm����
                logger.info("������桭������"+DeviceAndPack.packagename);
                AppInfo.getAppInfo().clearAPK();
            }
        });
        stopAPP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("�������̣�"+DeviceAndPack.packagename);
                AppInfo.getAppInfo().stopAPP();
            }
        });
        //ж�ذ�ť������ж�غ�pack������Ҫˢ��
        uninstallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("ж�ء�������"+DeviceAndPack.packagename);
                AppInfo.getAppInfo().clearAPK();
                AppInfo.getAppInfo().uninstallAPK();
                refreshPackCombobox();
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
        devicePanel.add(stopAPP);
        devicePanel.add(uninstallButton);
        return devicePanel;
    }
    /**
     * device�������ʼ��
     */
    public void initDeviceComboBox(){
        DevicesInfos devicesInfos = new DevicesInfos();
        String[] devicesArray=devicesInfos.getDevicesArray();
        deviceJComeboBox.setModel(new DefaultComboBoxModel(devicesArray));
        if (devicesArray.length>1) {
            logger.info("�ж���豸��Ĭ��ѡ��"+devicesArray[0]);
            DeviceAndPack.getDeviceAndPack().setDeivceid(devicesArray[0]);
            refreshPackCombobox();
            new DeviceInfoPanel().refreshDeviceInfoPanel();
            deviceJComeboBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == e.SELECTED) {
                        logger.info("ѡ���豸:"+String.valueOf(deviceJComeboBox.getSelectedItem()));
                        //ѡ���豸����Ҫ��device��ֵ
                        DeviceAndPack.getDeviceAndPack().setDeivceid(String.valueOf(deviceJComeboBox.getSelectedItem()));
                        System.out.println(DeviceAndPack.deivceid+":::::::::::");
                        //����Ҫǿ����package������ˢ�£�����ȡ���µ�packages
                        refreshPackCombobox();
                        new DeviceInfoPanel().refreshDeviceInfoPanel();
                    }
                }
            });
        }else if(devicesArray.length==1){
            logger.info("����һ���豸��ѡ�У�"+devicesArray[0]);
            DeviceAndPack.getDeviceAndPack().setDeivceid(devicesArray[0]);
            //ˢ��pack������
            refreshPackCombobox();
            new DeviceInfoPanel().refreshDeviceInfoPanel();
        }else{
            //û���豸
            logger.info("û���豸��������");
        }
    }
    /**
     * �豸������ˢ��
     * @param deviceCombobox
     * @param packJComboBox
     */
    public void refreshDeviceCombobox(JComboBox deviceCombobox, JComboBox packJComboBox){
        logger.info("ˢ���豸�б�");
        System.out.println("refresh device");
//        initDeviceComboBox(deviceCombobox, packJComboBox);
        String[] devicesArray=new DevicesInfos().getDevicesArray();
        System.out.println("devicearray:"+devicesArray.length);
        deviceCombobox.removeAllItems();
        if (devicesArray.length>0){
            deviceCombobox.setModel(new DefaultComboBoxModel(devicesArray));
            DeviceAndPack.getDeviceAndPack().setDeivceid(devicesArray[0]);
        }else {
            logger.info("û���豸��������");
            DeviceAndPack.getDeviceAndPack().setDeivceid("");
        }
    }
    /**
     * ˢ�°���������
     */
    public void refreshPackCombobox(){
        //��cmʱ��Ĭ��ѡ��cm
        logger.info("ˢ���Ѱ�װ�����б�");
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
                logger.info("δ��װ"+ResourceBundle.getBundle("config").getString("defaultPackage")+"��Ĭ��ѡ�а�����"+comboValue[0]);
                DeviceAndPack.getDeviceAndPack().setPackagename(comboValue[0]);
            }
        }
    }
    private void setDefaultPack(String packagename){
        logger.info("����Ĭ�ϰ�����"+packagename);
        packJComboBox.setSelectedItem(packagename);
        DeviceAndPack.getDeviceAndPack().setPackagename(packagename);
        logger.info("Ĭ�ϰ�������������"+DeviceAndPack.packagename);
    }
    /**
     * �����������ʼ��
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
                    //ѡ������󣬾Ϳ��Լ���Ӧ����Ϣ
                    DeviceAndPack dp=DeviceAndPack.getDeviceAndPack();
                    dp.setPackagename(String.valueOf(packJComboBox.getSelectedItem()));
                    logger.info("ѡ�������"+DeviceAndPack.packagename);
                    System.out.println(dp.deivceid+"::::"+DeviceAndPack.packagename);
                }
            }
        });
    }
}