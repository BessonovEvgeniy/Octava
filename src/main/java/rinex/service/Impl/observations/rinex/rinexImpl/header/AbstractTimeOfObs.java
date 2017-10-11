package rinex.service.Impl.observations.rinex.rinexImpl.header;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class AbstractTimeOfObs extends AbstractDateHeaderLabel {

    private String system;

    private SimpleDateFormat timeOfObs = new SimpleDateFormat("yyyy MM dd HH mm ss.SSSSSSS");

    public AbstractTimeOfObs() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile(".*" +
                "(\\d{4}.{4,5}"  +
                Strings.repeat("\\d{1,2}.{4,5}", 4) +
                "\\d{1,2}\\.\\d{7}).{3,5}"+
                "(\\w{3}).*TIME OF FIRST OBS");
    }

    @Override
    public Boolean parse(String line) throws RinexHeaderException {

        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        system = isFind ? matcher.group(2) : "";
        if (isFind) {
            try {
                String clearedDate = matcher.group(1).trim().replaceAll("\\s{2,7}"," ");
                timeOfObs.parse(clearedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RinexHeaderException("Can't parse line to simple format date.");
            }
        }
        return isFind;
    }
}
