package com.test.page;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ChartPanel {
    private List<Double> seriesData=new ArrayList<Double>();
    private XYChart chart;
    private SwingWrapperBase swingWrapper;

    public void xchart(double data, JFrame jFrame){
        seriesData.add(data);
        if (swingWrapper==null) {
            chart = new XYChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
//        chart.getStyler().setYAxisMin((double) -10);
//        chart.getStyler().setYAxisMax((double) 10);
            chart.addSeries("okk", null, seriesData);
//        series.setMarker(SeriesMarkers.NONE);
            swingWrapper = new SwingWrapperBase(chart);
            swingWrapper.displayChart(jFrame);
        }else{
            chart.updateXYSeries("okk", null, seriesData, null);
            swingWrapper.repaintChart();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame jFrame = new JFrame("charttttt");
        ChartPanel xChartPanel=new ChartPanel();
//        xChartPanel.xchart(1);
        for (int i=1;i<50; i++){
            xChartPanel.xchart(i,jFrame);
            Thread.sleep(1000);
        }
        jFrame.setVisible(true);
    }
}
