package com.test.page;

import com.test.log.LogDemo;
import com.test.util.AppInfo;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MonkeyPanel {
    private final static Logger logger = Logger.getLogger(LogDemo.class);
    private JPanel monkeyPanel=new JPanel();
    private JButton monkeyJButton = new JButton("执行monkey");
    private JLabel countLabel = new JLabel("count(*):");
    private JTextField count = new JTextField(8);
    private JCheckBox ignoreCrash=new JCheckBox("ignorecrashes");
    private JCheckBox ignoreTimeouts = new JCheckBox("ignoretimeouts");
    private JLabel throttleLabel = new JLabel("throttle(*):");
    private JTextField throttle = new JTextField(8);

    public JPanel getMonkeyPanel(){
        monkeyPanel.setPreferredSize(new Dimension(900,70));
        monkeyPanel.setBorder(BorderFactory.createTitledBorder("monkey"));
        monkeyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (count.getText()!=null && !count.getText().equals("") && throttle.getText()!=null && !throttle.getText().equals("")){
                        if (Integer.parseInt(count.getText())>0 && Integer.parseInt(throttle.getText())>0){
//                            if (ignoreCrash.isSelected())
                            AppInfo.getAppInfo().runMonkey(ignoreCrash.isSelected(), ignoreTimeouts.isSelected(), Integer.parseInt(throttle.getText()), Integer.parseInt(count.getText()));
                        }else {
                            logger.info("输入不正确!!");
                        }
                    }else {
                        logger.info("输入throttle或执行次数!!!");
                    }

                } catch (IOException ioException) {
                    logger.error(ioException);
                }
            }
        });
        monkeyPanel.add(ignoreCrash);
        monkeyPanel.add(ignoreTimeouts);
        monkeyPanel.add(throttleLabel);
        monkeyPanel.add(throttle);
        monkeyPanel.add(countLabel);
        monkeyPanel.add(count);
        monkeyPanel.add(monkeyJButton);
        return monkeyPanel;
    }
}
