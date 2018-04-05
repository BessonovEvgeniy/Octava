package utils.time;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeUtils {

    public static int[] createIntTimeArray(int from, int to) {
        return IntStream.rangeClosed(from, to).toArray();
    }

    public static double[] createDoubleTimeArray(int from, int to) {
        return IntStream.rangeClosed(from, to).mapToDouble(i -> i).toArray();
    }

    public static List<Integer> createTimeVector(int from, int to) {
        return IntStream.rangeClosed(from, to).boxed().collect(Collectors.toList());
    }

    public static List<Integer> intervalBetween(List<Integer> col1, List<Integer> col2) {
        if (CollectionUtils.isEmpty(col1) || CollectionUtils.isEmpty(col2) && col1.size() != col2.size()) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> resultCollection = new ArrayList<>();

        for (int i = 0; i < col1.size(); i++) {
            resultCollection.add(i, col1.get(i) - col2.get(i) + 1);
        }

        return resultCollection;
    }
}
