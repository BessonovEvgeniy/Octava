package rinex.service.Impl.observations.rinex.rinexImpl.header;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@Service
public @Data class AntennaDelta extends AbstractCoordinatesHeaderLabel {

    public AntennaDelta() {
        init();
    }

    @PostConstruct
    protected void init() {
        stringPattern = Strings.repeat("(\\d{1,14}\\.\\d{1,4})",3) + "(APPROX POSITION XYZ)";
        pattern = Pattern.compile(stringPattern);
    }
}
