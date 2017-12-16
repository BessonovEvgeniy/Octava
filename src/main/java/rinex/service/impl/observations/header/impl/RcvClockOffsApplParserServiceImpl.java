package rinex.service.impl.observations.header.impl;

import org.springframework.stereotype.Service;
import rinex.model.observations.header.impl.RcvClockOffsAppl;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("RCV CLOCK OFFS APPL")
public class RcvClockOffsApplParserServiceImpl implements HeaderLabelParserService<RcvClockOffsAppl> {

    private Pattern pattern = Pattern.compile("(\\d{1,6})*RCV CLOCK OFFS APPL ");

    @Override
    public RcvClockOffsAppl parse(String line) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            boolean receiverOffset = matcher.group(1).equals(1);
            return new RcvClockOffsAppl(receiverOffset);
        }
        return RcvClockOffsAppl.NULL;
    }
}
