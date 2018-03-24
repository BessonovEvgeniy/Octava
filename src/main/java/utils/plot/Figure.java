package utils.plot;

import lombok.Data;
import org.apache.commons.math3.linear.RealMatrix;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import ppa.model.observation.ReceiverDataModel;
import utils.time.Sections;
import utils.time.TimeUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public @Data class Figure {

    @Autowired
    private ExecutorService executorService;

    private String title = "";
    private String xLabel = "";
    private String yLabel = "";

    private List<String> series = new LinkedList<>();
    private List<Integer> time = new ArrayList<>();

    private boolean fullScreen = true;
    private boolean skipZeroData = true;

    private int widthDefaultScreenPixels = 800;
    private int heightDefaultScreenPixels = 600;
    private int seriesCounter = 0;

    private XYChart chart;

    public Figure() {
        init();
    }

    public Figure(String xLabel, String yLabel) {
        this();
        this.xLabel = xLabel;
        this.yLabel = yLabel;
    }

    public Figure(String title, String xLabel, String yLabel) {
        this(xLabel, yLabel);
        this.title = title;
    }

    private void init() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(10);
        }
    }

    private void beforePlot() {
        if (chart == null) {
            if (fullScreen) {
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();
                widthDefaultScreenPixels = widthDefaultScreenPixels > width ? widthDefaultScreenPixels : width;
                heightDefaultScreenPixels = heightDefaultScreenPixels > height ? heightDefaultScreenPixels : height;
            }

            chart = new XYChartBuilder().width(widthDefaultScreenPixels).height(heightDefaultScreenPixels).title(title).xAxisTitle(xLabel).yAxisTitle(yLabel).build();
        }
    }

    public void plot(RealMatrix matrix) {
        beforePlot();
        plot(TimeUtils.createTimeVector(0,matrix.getRowDimension()), matrix);
    }

    public void plot(List<Integer> time, RealMatrix data) {
        this.time = time;
        beforePlot();
        Sections sections = new Sections(data, ReceiverDataModel.ObservationMode.STATIC_MODE);
        Sections.SectionsData sectionData = sections.findSections();

        List<Integer> tn = sectionData.getTn();
        List<Integer> tk = sectionData.getTk();
        List<Integer> NSect = sectionData.getNSect();

        for (int i = 0; i < NSect.size(); i++) {
            Integer start = tn.get(i);
            Integer stop = tk.get(i);
            Integer sv = NSect.get(i);

            double[] vector = data.getSubMatrix(start, stop, sv, sv).getColumn(0);
            int series = i;
            executorService.submit(() -> plot(series, vector));
        }

        new SwingWrapper<>(chart).displayChart();
    }

    private void plot(int series, double[] x, double[] y) {
        if (skipZeroData) {
            boolean noZeros = Arrays.stream(y).anyMatch(value -> value != 0.0d);
            if (noZeros) {
                synchronized (chart) {
                    chart.addSeries("line " + series, x, y);
                }
            }
        }
    }

    private void plot(int series, double[] y) {
        if (skipZeroData) {
            boolean noZeros = Arrays.stream(y).anyMatch(value -> value != 0.0d);
            if (noZeros) {
                synchronized (chart) {
                    chart.addSeries("line " + series, y);
                }
            }
        }
    }

    public static void main(String[] args) {
        Figure figure = new Figure();
        double[] zeroArray = new double[]{0,0,0,0,0,0,0};
        double[] nonZeroArray = new double[]{0,0,0,1.0,0,0,0};
        figure.plot(1, zeroArray);
        figure.plot(1, nonZeroArray);
    }
}
