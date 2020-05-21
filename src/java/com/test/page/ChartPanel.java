package com.test.page;

import com.sun.javaws.util.JfxHelper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChartPanel {
    private List<Double> seriesData=new ArrayList<Double>();
    private XYChart chart;
    private SwingWrapperBase swingWrapper;
    private JFrame jFrame;
    private JPanel jPanel;
    private int size = 100;//默认100条数据刷新一次
    private String xTitle;
    private String yTitle;
    public ChartPanel(JFrame jFrame, String xTitle, String yTitle, int size){
        jPanel = new JPanel();
        this.jFrame=jFrame;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.size=size;
    }
    public <T extends Chart> JPanel getPanel(double data){
        seriesData.add(data);
        chart = new XYChartBuilder().xAxisTitle(xTitle).yAxisTitle(yTitle).width(500).height(400).build();
        chart.addSeries("okk", null, seriesData);
        swingWrapper = new SwingWrapperBase(chart);
        org.knowm.xchart.XChartPanel<T> chartPanel = swingWrapper.getGet();
        jPanel.add(chartPanel);
        return jPanel;
    }
    public void rePaint(double data){
        if (seriesData.size() == this.size){
            seriesData.remove(0);
        }
        seriesData.add(data);
        chart.updateXYSeries("okk", null, seriesData, null);
        swingWrapper.repaintChart();
    }
    public static void main(String[] args) throws InterruptedException {
        JFrame jFrame = new JFrame("charttttt");
        ChartPanel xChartPanel=new ChartPanel(jFrame,"x", "y", 20);
//        xChartPanel.xchart(1);
        for (int i=1;i<50; i++){
            Thread.sleep(1000);
        }
        jFrame.setVisible(true);
    }
}
