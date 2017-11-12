package rinex.model.observations.header;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@Component("RCV CLOCK OFFS APPL")
public class RcvClockOffsAppl extends AbstractHeaderLabel {

    public RcvClockOffsAppl() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("\\d{1,6}");
    }

    @Override
    public Boolean parse(String line) throws RinexHeaderException {
        return null;
    }
}
