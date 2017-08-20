package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@Service
public @Data class AntennaDelta extends AbstractCoordinatesHeaderLabel {

    public AntennaDelta(){
        init();
    }

    @PostConstruct
    protected void init() {
        pattern = Pattern.compile("(\\d{1,14}\\.\\d{1,4})" +
                                  "(\\d{1,14}\\.\\d{1,4})" +
                                  "(\\d{1,14}\\.\\d{1,4})" +
                                  "(APPROX POSITION XYZ)");
    }
}
