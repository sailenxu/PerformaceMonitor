import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JLabel;

public class LabelLogAppender extends LogAppender {
    private JLabel label;

    public LabelLogAppender(JLabel label) throws IOException {
        super("label");
        this.label = label;
    }
    @Override
    public void run() {
        // 不间断地扫描输入流
        Scanner scanner = new Scanner(reader);
        // 将扫描到的字符流显示在指定的JLabel上
        while (scanner.hasNextLine()) {
            try {
                //睡眠
                Thread.sleep(100);
                String line = scanner.nextLine();
                label.setText(line);
                line = null;
            } catch (Exception ex) {
                //异常信息不作处理
            }
        }
    }
}
