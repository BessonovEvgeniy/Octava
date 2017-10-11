package rinex.service.Impl.observations.rinex.rinexImpl.header;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@Service
public class RcvClockOffsAppl extends AbstractHeaderLabel {

    public RcvClockOffsAppl() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("\\d{1,6}");
    }
}
