package com.pm.page;

import com.pm.log.LogDemo;

import javax.swing.*;
import java.awt.*;

public class LogPanel {
    private JPanel logPanel;
    private JTextArea logta;
    private JScrollPane logsp;
    private int width;
    private int height;
    public LogPanel(int width, int height){
        logPanel=new JPanel();
        logta = new JTextArea();
        logsp = new JScrollPane(logta);
        this.width = width;
        this.height = height;
    }

    public JPanel getLogPanel(){
//        logta.setColumns(67);
//        logta.setRows(45);
//        logPanel.setSize(width, height);
        System.out.println(width+"widdddd:"+height);
        logsp.setPreferredSize(new Dimension(width, height));
        logsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        LogDemo logDemoFrame = new LogDemo(logta, logsp);
        logDemoFrame.initLog();
        logPanel.add(logsp);
        return logPanel;
    }
}
