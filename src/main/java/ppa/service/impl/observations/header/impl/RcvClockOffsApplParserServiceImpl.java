package ppa.service.impl.observations.header.impl;

import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.RcvClockOffsAppl;
import ppa.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("RCV CLOCK OFFS APPL")
public class RcvClockOffsApplParserServiceImpl implements HeaderLabelParserService<RcvClockOffsAppl> {

    private Pattern pattern = Pattern.compile("(.{1,60})RCV CLOCK OFFS APPL ");

    @Override
    public RcvClockOffsAppl parse(String line) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            boolean receiverOffset = Ints.tryParse(matcher.group(1).trim()).equals(1);
            return new RcvClockOffsAppl(receiverOffset);
        }
        return RcvClockOffsAppl.NULL;
    }
}
