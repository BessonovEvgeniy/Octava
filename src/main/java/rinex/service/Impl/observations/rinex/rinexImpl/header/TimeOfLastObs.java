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
public @Data class TimeOfLastObs extends AbstractTimeOfObs {}
