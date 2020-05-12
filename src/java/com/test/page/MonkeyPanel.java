package com.test.page;

import com.test.log.LogDemo;
import com.test.util.AppInfo;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MonkeyPanel {
    private final static Logger logger = Logger.getLogger(LogDemo.class);
    private JPanel monkeyPanel=new JPanel();
    private JButton monkeyJButton = new JButton("执行monkey");
    private JLabel countLabel = new JLabel("次数：");
    private JTextField count = new JTextField(8);

    public JPanel getMonkeyPanel(){
        monkeyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (count.getText()!=null&&!count.getText().equals("")){
                        if (Integer.parseInt(count.getText())>0){
                            new AppInfo().runMonkey(Integer.parseInt(count.getText()));
                        }else {
                            logger.info("执行次数输入不正确!!");
                        }
                    }else {
                        logger.info("输入执行次数!!!");
                    }

                } catch (IOException ioException) {
                    logger.error(ioException);
                }
            }
        });
        monkeyPanel.add(countLabel);
        monkeyPanel.add(count);
        monkeyPanel.add(monkeyJButton);
        return monkeyPanel;
    }
}
