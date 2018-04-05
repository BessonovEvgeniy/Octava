package utils.time;

import org.apache.commons.lang.ArrayUtils;

import java.util.LinkedList;
import java.util.List;

public class Filters {

    public static List<Integer> find(double[] vector) {
        return findAllExcept(vector, 0.0);
    }

    public static List<Integer> findAllExcept(double[] vector, double value) {
        List<Integer> indexes = new LinkedList<>();
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] != value) {
                indexes.add(i);
            }
        }
        return indexes;
    }


    public static List<Integer> findLessThan(double[] vector, double value) {
        List<Integer> indexes = new LinkedList<>();
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] < value) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public static List<Integer> findLessThan(List<Integer> vector, double value) {
        return findLessThan(vector.stream().mapToDouble(d -> d).toArray(), value);
    }

    public static List<Integer> findGreaterThan(double[] vector, double value) {
        List<Integer> indexes = new LinkedList<>();

        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > value) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public static List<Integer> findGreaterThan(List<Integer> vector, double value) {
        return findGreaterThan(vector.stream().mapToDouble(d -> d).toArray(), value);
    }

    public static double[] diff(double[] vector) {
        if (ArrayUtils.isNotEmpty(vector) && vector.length > 1) {
            int length = vector.length;
            double[] diff = new double[length];
            for (int i = 0; i < length - 1; i++) {
                diff[i] = vector[i+1] - vector[i];
            }
            return diff;
        }
        return new double[0];
    }

    public static double[] diff(List<Integer> vector) {
        return diff(vector.stream().mapToDouble(d -> d).toArray());
    }
}
