package utils.plot;

import Jama.Matrix;
import lombok.Data;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
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

    public void plot(Matrix matrix) {
        long startTime = System.currentTimeMillis();
        if (fullScreen) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();
            widthDefaultScreenPixels = widthDefaultScreenPixels > width ? widthDefaultScreenPixels : width;
            heightDefaultScreenPixels = heightDefaultScreenPixels > height ? heightDefaultScreenPixels : height;
        }

        chart = new XYChartBuilder().width(widthDefaultScreenPixels).height(heightDefaultScreenPixels).title(title).xAxisTitle(xLabel).yAxisTitle(yLabel).build();

        int y = matrix.getRowDimension();
        int x = matrix.getColumnDimension();

        for (int i = 0; i < x; i++) {
            double[][] array2D = matrix.getMatrix(0, y - 1, i, i).getArray();
            double[] vector = Arrays.stream(array2D).flatMapToDouble(v -> Arrays.stream(v)).toArray();
            int series = i;
            executorService.submit(() -> plot(series, vector));
        }

        new SwingWrapper<>(chart).displayChart();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
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
