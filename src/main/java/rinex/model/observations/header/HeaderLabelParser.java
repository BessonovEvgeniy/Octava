package rinex.model.observations.header;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public @Data class HeaderLabelParser {

    private List<String> params = new ArrayList<>();

    public boolean parseThreeParams(Pattern pattern, String line) {
        Matcher matcher = pattern.matcher(line);
        parseTwoParams(matcher);
        return parseOneParam(matcher, 3);
    }

    public boolean parseTwoParams(Pattern pattern, String line) {
        Matcher matcher = pattern.matcher(line);
        return parseTwoParams(matcher);
    }

    public boolean parseOneParam(Pattern pattern, String line) {
        Matcher matcher = pattern.matcher(line);
        return parseOneParam(matcher, 1);
    }

    public List<Double> getDoubleParams() {
        return params.stream().map(param -> Double.parseDouble(param)).collect(Collectors.toList());
    }

    public List<Integer> getIntegerParams() {
        return params.stream().map(param -> Integer.parseInt(param)).collect(Collectors.toList());
    }

    private boolean parseTwoParams(Matcher matcher) {
        boolean isFind = matcher.find();

        if (isFind) {
            params.add(matcher.group(1).trim());
            params.add(matcher.group(2).trim());
        }
        return isFind;
    }

    private boolean parseOneParam(Matcher matcher, int group) {
        boolean isFind = matcher.find();

        if (isFind) {
            params.add(matcher.group(group).trim());
        }
        return isFind;
    }
}
