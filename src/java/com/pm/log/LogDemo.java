package com.pm.log;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;

public class LogDemo {
    private JScrollPane logScrollPane;
    private JTextArea logTextArea;
    private final static Logger logger = Logger.getLogger(LogDemo.class);

    public LogDemo(JTextArea logTextArea, JScrollPane logScrollPane) {
        this.logScrollPane = logScrollPane;
        this.logTextArea = logTextArea;
    }

    public void initLog() {
        try {
            Thread t2;
            t2 = new TextAreaLogAppender(logTextArea, logScrollPane);
            t2.start();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, e, "绑定日志输出组件错误", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public static void main(String[] s) {
//        JFrame jFrame = new JFrame();
//        jFrame.setSize(500, 500);
//        JTextArea logta = new JTextArea();
//        JScrollPane logsp = new JScrollPane(logta);
//        logta.setColumns(20);
//        logta.setRows(5);
//        logsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        LogDemo logDemoFrame = new LogDemo(logta, logsp);
//        logDemoFrame.initLog();
//        jFrame.add(logsp);
//        jFrame.setVisible(true);
//        for (int i = 0; i < 1000; i++) {
////            log.info("测试日志输出:" + i);
//            logger.info("inoffffoof"+i);
//        }
//    }
}
