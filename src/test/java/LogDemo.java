import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogDemo extends JFrame {
    private JLabel logLabel;
    private JScrollPane logScrollPane;
    private JTextArea logTextArea;
    private final static Log log = LogFactory.getLog(LogDemo.class);

    public LogDemo() {
        logLabel = new javax.swing.JLabel();
        logScrollPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        logLabel.setText(" ");

        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        logScrollPane.setViewportView(logTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(logLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE).addContainerGap()).addComponent(logScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(logLabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)));
        pack();
    }

    public void initLog() {
        try {
            Thread t1, t2;
            t1 = new LabelLogAppender(logLabel);
            t2 = new TextAreaLogAppender(logTextArea, logScrollPane);
            t1.start();
            t2.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "绑定日志输出组件错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] s) {
        LogDemo logDemoFrame = new LogDemo();
        logDemoFrame.initLog();
        logDemoFrame.setVisible(true);
        for (int i = 0; i < 1000; i++) {
            log.info("测试日志输出:" + i);
        }
    }
}
