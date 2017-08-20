package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@Service
public @Data class ApproxPos extends AbstractCoordinatesHeaderLabel {

    public ApproxPos() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,6}" +
                "([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,6}" +
                "([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,18}" +
                "APPROX POSITION XYZ");
    }
}
