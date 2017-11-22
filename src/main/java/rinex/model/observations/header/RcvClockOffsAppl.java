package rinex.model.observations.header;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component("RCV CLOCK OFFS APPL")
public class RcvClockOffsAppl implements HeaderLabel {

    private Pattern pattern = Pattern.compile("\\d{1,6}");

    @Override
    public boolean parse(String line) {
        return false;
    }
}
