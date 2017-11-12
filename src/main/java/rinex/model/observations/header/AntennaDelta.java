package rinex.model.observations.header;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Component("ANTENNA: DELTA H/E/N")
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
