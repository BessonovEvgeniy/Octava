package octava.service.impl.observations.header;

import com.google.common.primitives.Ints;
import octava.model.observation.header.impl.RcvClockOffsAppl;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("RCV CLOCK OFFS APPL")
public class RcvClockOffsApplParserServiceImpl implements HeaderLabelParserService<RcvClockOffsAppl> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})RCV CLOCK OFFS APPL ");

    @Override
    public RcvClockOffsAppl parse(final String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final boolean receiverOffset = Ints.tryParse(matcher.group(1).trim()).equals(1);
            return new RcvClockOffsAppl(receiverOffset);
        }
        return RcvClockOffsAppl.NULL;
    }
}
