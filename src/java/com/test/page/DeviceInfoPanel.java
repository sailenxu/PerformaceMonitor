package com.test.page;

import com.test.util.DeviceInfo;

import javax.swing.*;
import java.awt.*;

public class DeviceInfoPanel {
    private static JPanel deviceInfoPanel=new JPanel();
    private JLabel brand=new JLabel("品牌：");
    private static JLabel brandJlabel=new JLabel();
    private JLabel model=new JLabel("型号：");
    private static JLabel modelJlabel=new JLabel();
    private JLabel dp=new JLabel("分辨率：");
    private static JLabel dpJlabel=new JLabel();
    private JLabel os = new JLabel("android:");
    private static JLabel osJlabel = new JLabel();

    public JPanel getDeviceInfoPanel() {
        deviceInfoPanel.setBorder(BorderFactory.createTitledBorder("device information"));
        deviceInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        deviceInfoPanel.setBackground(Color.GRAY);

        brandJlabel.setText(DeviceInfo.getDeviceInfo().getBrand());
        modelJlabel.setText(DeviceInfo.getDeviceInfo().getModel());
        dpJlabel.setText(DeviceInfo.getDeviceInfo().getDp());
        osJlabel.setText(DeviceInfo.getDeviceInfo().getOsVersionCode());

        deviceInfoPanel.add(brand);
        deviceInfoPanel.add(brandJlabel);
        deviceInfoPanel.add(model);
        deviceInfoPanel.add(modelJlabel);
        deviceInfoPanel.add(dp);
        deviceInfoPanel.add(dpJlabel);
        deviceInfoPanel.add(os);
        deviceInfoPanel.add(osJlabel);
        return deviceInfoPanel;
    }
    public void refreshDeviceInfoPanel(){
        brandJlabel.setText(DeviceInfo.getDeviceInfo().getBrand());
        modelJlabel.setText(DeviceInfo.getDeviceInfo().getModel());
        dpJlabel.setText(DeviceInfo.getDeviceInfo().getDp());
        osJlabel.setText(DeviceInfo.getDeviceInfo().getOsVersionCode());
    }
}
