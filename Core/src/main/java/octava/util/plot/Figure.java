package octava.util.plot;

import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import lombok.Data;
import octava.model.observation.ReceiverDataModel;
import octava.model.observation.header.impl.ObsType;
import octava.util.time.SectionFinder;
import octava.util.time.TimeUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public @Data class Figure {

    private String title = "";
    private String xLabel = "";
    private String yLabel = "";

    private List<String> series = new LinkedList<>();
    private double[] time;

    private boolean fullScreen;
    private boolean skipZeroData = true;

    private int widthDefaultScreenPixels = 800;
    private int heightDefaultScreenPixels = 600;
    private int seriesCounter = 0;
    private int delT = 1;

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

    public void plot(List<Integer> time, RealMatrix matrix) {
        double[] timeArray = time.stream().mapToDouble(i -> i).toArray();
        plot(timeArray, matrix);
    }

    public void plot(double[] time, RealMatrix data) {
        beforePlot();
        this.time = time;

        int sv = data.getColumnDimension();

        for (int i = 0; i < sv; i++) {
            double[] vector = data.getSubMatrix(0, time.length, sv, sv).getColumn(0);
            int series = i;
            plot(series, time, vector);
        }

        display();
    }

    public void display() {
        new SwingWrapper<>(chart).displayChart();
    }

    public void plot(ReceiverDataModel rdm, ObsType type) {
        beforePlot() ;
        SectionFinder.SectionsData sectionData = rdm.getSections();
        RealMatrix matrix = rdm.getObsByType(type).getFullSizeMatrix();

        List<Integer> tn = sectionData.getTn();
        List<Integer> tk = sectionData.getTk();
        List<Integer> NSect = sectionData.getNSect();

        for (int i = 0; i < NSect.size(); i++) {
            Integer start = tn.get(i);
            Integer stop = tk.get(i);
            Integer sv = NSect.get(i);

            double[] vector = matrix.getSubMatrix(start, stop, sv, sv).getColumn(0);
            int series = i;
            plot(series, TimeUtils.createDoubleTimeArray(start, stop), vector);
        }
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

        beforePlot();
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
        figure.display();
    }
}
