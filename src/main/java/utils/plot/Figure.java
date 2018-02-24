package utils.plot;

import Jama.Matrix;
import lombok.Data;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public @Data class Figure {

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

    public Figure() {}

    public Figure(String xLabel, String yLabel) {
        this.xLabel = xLabel;
        this.yLabel = yLabel;
    }

    public Figure(String title, String xLabel, String yLabel) {
        this(xLabel, yLabel);
        this.title = title;
    }

    private void init() {

    }

    public void plotRows(Matrix matrix) {
        plot(matrix, true);
    }

    public void plot(Matrix matrix, boolean column) {

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

        if (column) {
            for (int i = 0; i < x; i++) {
                double[] vector = matrix.getMatrix(0, y - 1, i, i).getRowPackedCopy();
                if (skipZeroData) {
                    boolean zerosOnly = Arrays.stream(vector).anyMatch(value -> value != 0.0d);
                    if (zerosOnly) {
                        plot(vector);
                    }
                }
            }
        }
        new SwingWrapper<XYChart>(chart).displayChart();
    }

    private void plot(double[] y) {
        plot(IntStream.range(0, y.length).mapToDouble(i -> i).toArray(), y, "line " + seriesCounter);
        seriesCounter++;
    }

    private void plot(double[] x, double[] y, String seriesLabel) {
        chart.addSeries(seriesLabel, x, y);
    }
}
