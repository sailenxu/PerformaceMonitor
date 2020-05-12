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
    private JButton monkeyJButton = new JButton("Ö´ÐÐmonkey");

    public JPanel getMonkeyPanel(){
        monkeyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AppInfo().runMonkey();
                } catch (IOException ioException) {
                    logger.error(ioException);
                }
            }
        });

        monkeyPanel.add(monkeyJButton);
        return monkeyPanel;
    }
}
