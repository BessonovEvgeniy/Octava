package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data  class AntType implements HeaderLabel {

    private String ant;
    private String type;

    @Override
    public boolean parse(String line) {

        Matcher matcher = Pattern.compile("(.{20})(.{20})(.{20})(ANT # / TYPE)").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            ant = matcher.group(1).trim();
            type = matcher.group(2).trim();
        } else {
            ant = null;
            type = null;
        }
        return isFind;
    }
}
