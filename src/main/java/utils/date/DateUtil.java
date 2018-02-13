package utils.date;

import com.google.common.base.Strings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;

public final class DateUtil {

    private static final Map<String, String> DATE_FORMAT_REGEXPS = new LinkedHashMap<String, String>() {{
        put("^\\d{4}\\d{1,2}\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyyMMdd HH:mm:ss");
        put("^\\d{1,2}-[a-z]{3}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MMM-yyyy HH:mm");
        put("^\\d{1,2}-[a-z]{3}-\\d{2}\\s\\d{1,2}:\\d{2}$", "dd-MMM-yy HH:mm");
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
    }};

    private static final Map<String, DateTimeFormatter> DATE_SHORT_FORMAT_REGEXPS = initDateShortFormatRegexps();

    private static final Map<String, String> OBS_FORMAT_REGEXPS = new LinkedHashMap<String, String>() {{
            put("^" + Strings.repeat("\\s*\\d{1,2}", 5) + "\\s*\\d{1,2}.\\d{7}$", "yy MM dd HH mm ss.SSSSSSS");
    }};


    private static final Map<String, DateTimeFormatter> initDateShortFormatRegexps() {
        List<String> shortFormats = Arrays.asList(
                "dd-MMM-yy HH:mm"
        );
        Map<String, DateTimeFormatter> dateShortFormatRegexps = new HashMap<>();

        for (String shortFormat : shortFormats) {
            if (dateShortFormatRegexps.get(shortFormat) == null) {

                dateShortFormatRegexps.put(shortFormat, new DateTimeFormatterBuilder()
                        .parseCaseInsensitive().appendPattern(shortFormat)
                        .toFormatter(Locale.ENGLISH));
            }
        }
        return  dateShortFormatRegexps;
    }

    private DateUtil() {}

    public static String determineDateFormat(String dateString) {
        return determineDateFormat(DATE_FORMAT_REGEXPS, dateString);
    }

    public static String determineDateFormat(Map<String, String> regexps, String dateString) {
        for (String regexp : regexps.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return regexps.get(regexp);
            }
        }
        return null; // Unknown format.
    }

    public static LocalDateTime parseObsToLocalDateTime(String dateString) throws Exception {
        dateString = dateString.trim().replace("  "," 0");
        String format = determineDateFormat(OBS_FORMAT_REGEXPS, dateString);
        if (format == null) {
            throw new UnknownFormatConversionException("Can't determine Date Format for: " + dateString);
        }
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime parseToLocalDateTime(String date) {
        date = date.toLowerCase().replaceAll("utc","").trim();
        String format = determineDateFormat(date);
        LocalDateTime local;

        try {
            local = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e) {
            DateTimeFormatter formatter = DATE_SHORT_FORMAT_REGEXPS.get(format);
            if (formatter == null) {
                throw new UnknownFormatConversionException(format);
            }
            local = LocalDateTime.parse(date, formatter);

            if (local.getYear() > 2090) {
                throw new IllegalStateException("Try to use yyyy year format.");
            }
        }
        return local;
    }
}
