package com.pm.page;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.Chart;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SwingWrapperBase<T extends Chart> extends SwingWrapper {
    private final List<XChartPanel<T>> chartPanels = new ArrayList();
    private List<T> charts = new ArrayList();
    private boolean isCentered = true;

    public SwingWrapperBase(T chart){
        super(chart);
        this.charts.add(chart);
    }
    public void displayChart(final JFrame jFrame){
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    jFrame.setDefaultCloseOperation(3);
                    XChartPanel<T> chartPanel = new XChartPanel((Chart) SwingWrapperBase.this.charts.get(0));
                    SwingWrapperBase.this.chartPanels.add(chartPanel);
                    chartPanel.setPreferredSize(new Dimension(400, 400));
                    jFrame.add(chartPanel);
                    jFrame.pack();
                    if (SwingWrapperBase.this.isCentered) {
                        jFrame.setLocationRelativeTo((Component)null);
                    }
                    jFrame.setVisible(true);
                }
            });
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        } catch (InvocationTargetException var4) {
            var4.printStackTrace();
        }
    }
    public XChartPanel<T> getGet(){
        final XChartPanel<T>[] chartPanel = new XChartPanel[]{new XChartPanel(SwingWrapperBase.this.charts.get(0))};
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
//                    frame.setDefaultCloseOperation(3);
                    chartPanel[0] = new XChartPanel((Chart)SwingWrapperBase.this.charts.get(0));
                    SwingWrapperBase.this.chartPanels.add(chartPanel[0]);
//                    frame.add(chartPanel);
//                    frame.pack();
//                    if (SwingWrapperBase.this.isCentered) {
//                        frame.setLocationRelativeTo((Component)null);
//                    }
//                    frame.setVisible(true);
                }
            });
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        } catch (InvocationTargetException var4) {
            var4.printStackTrace();
        }
        return chartPanel[0];
    }
    public void repaintChart() {
        this.repaintChart(0);
    }
    public void repaintChart(int index) {
        ((XChartPanel)this.chartPanels.get(index)).revalidate();
        ((XChartPanel)this.chartPanels.get(index)).repaint();
    }
}
