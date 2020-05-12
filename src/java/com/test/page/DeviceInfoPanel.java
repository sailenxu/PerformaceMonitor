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

    public JPanel getDeviceInfoPanel() {
        deviceInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        deviceInfoPanel.setBackground(Color.GRAY);

        brandJlabel.setText(new DeviceInfo().getBrand());
        modelJlabel.setText(new DeviceInfo().getModel());
        dpJlabel.setText(new DeviceInfo().getDp());

        deviceInfoPanel.add(brand);
        deviceInfoPanel.add(brandJlabel);
        deviceInfoPanel.add(model);
        deviceInfoPanel.add(modelJlabel);
        deviceInfoPanel.add(dp);
        deviceInfoPanel.add(dpJlabel);
        return deviceInfoPanel;
    }
    public void refreshDeviceInfoPanel(){
        brandJlabel.setText(new DeviceInfo().getBrand());
        modelJlabel.setText(new DeviceInfo().getModel());
        dpJlabel.setText(new DeviceInfo().getDp());
    }
}
