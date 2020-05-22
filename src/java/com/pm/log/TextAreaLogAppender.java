package com.pm.log;

import java.io.IOException;
import java.util.Scanner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAreaLogAppender extends LogAppender {
    private JTextArea textArea;
    private JScrollPane scroll;

    /**
     * 默认的构造
     * @param textArea 记录器名称，该记录器输出的日志信息将被截取并输出到指定的JTextArea组件
     * @param scroll JTextArea组件使用的滚动面板，因为在JTextArea中输出日志时，默认会使垂直滚动条自动向下滚动，若不需要此功能，此参数可省略
     * @throws IOException
     */
    public TextAreaLogAppender(JTextArea textArea, JScrollPane scroll) throws IOException {
        super("textArea");
        this.textArea = textArea;
        this.scroll = scroll;
    }

    @Override
    public void run() {
        // 不间断地扫描输入流
        Scanner scanner = new Scanner(reader);
        // 将扫描到的字符流输出到指定的JTextArea组件
        while (scanner.hasNextLine()) {
            try {
                //睡眠
                sleep(100);
                String line = scanner.nextLine();
                textArea.append(line);
                textArea.append("\n");
                line = null;
                //使垂直滚动条自动向下滚动
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
            } catch (Exception ex) {
                //异常不做处理
            }
        }
    }
}
