package utils.matrix;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;

public class MatrixToolSet {

    public static RealMatrix ones(int rows, int columns) {
        double[][] array = new double[rows][columns];
        for(double[] vector : array) {
            Arrays.fill(vector, 1.0d);
        }
        return new Array2DRowRealMatrix(array);
    }

    public static RealMatrix zeros(int rows, int columns) {
        double[][] array = new double[rows][columns];
        return new Array2DRowRealMatrix(array);
    }
}
