package com.test.page;

import com.test.log.LogDemo;

import javax.swing.*;

public class LogPanel {
    private JPanel logPanel=new JPanel();
    private JTextArea logta = new JTextArea();
    private JScrollPane logsp = new JScrollPane(logta);

    public JPanel getLogPanel(){
        logta.setColumns(67);
        logta.setRows(45);
//        logta.set(logWidth, logHeight);
        logsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        LogDemo logDemoFrame = new LogDemo(logta, logsp);
        logDemoFrame.initLog();
        logPanel.add(logsp);
        return logPanel;
    }
}
