//package rinex.service.impl.rinex.impl;
//
//import org.springframework.stereotype.Service;
//import rinex.model.observations.header.HeaderLabel;
//import rinex.service.impl.observations.header.HeaderLabelParserService;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//public class ObservationRinexRecordParserServiceImpl implements HeaderLabelParserService {
//
//    @Override
//    public ObservationRinexRecord parse(String line) {
//        line = " 05  3 24 13 10";
//        Pattern epochTime = Pattern.compile("^( \\d{1,2}){5}");
//        Matcher matcher = epochTime.matcher(line);
//
//        if (matcher.find()) {
//            String data = matcher.group();
//        }
//
//        return false;
//    }
//}
