package com.pm.perforentity;

import com.pm.main.DealData;
import com.pm.page.ChartPanel;
import com.pm.perfordata.DeviceAndPack;
import com.pm.util.AppInfo;

import javax.swing.*;

public class FPSinfo implements Runnable {
    private ChartPanel fpsChart;
    private JPanel fpsJPanel;
    private JPanel perJPanel;
    private JFrame jFrame;

    public FPSinfo(JFrame jFrame,JPanel perJPanel, int x, int y, int width, int height){
        this.perJPanel = perJPanel;
        this.jFrame = jFrame;
        fpsChart = new ChartPanel(jFrame, "", "fps",(int)(width*0.5), (int)(height*0.5), 100);
    }
    @Override
    public void run() {
        boolean flag = true;
        while (true){
            int fps = AppInfo.getAppInfo().getFPS();
            if (flag){
                fpsJPanel = fpsChart.getPanel(fps);
                perJPanel.add(fpsJPanel);
                jFrame.add(perJPanel);
                jFrame.setVisible(true);
                flag = false;
            }else {
                fpsChart.rePaint(fps);
            }
            try{
                Thread.sleep(1000);
            }catch (Exception e){}
        }
    }
}
