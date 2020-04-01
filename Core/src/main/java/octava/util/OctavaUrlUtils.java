package octava.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public final class OctavaUrlUtils {

    public static String getOriginUrl(final HttpServletRequest request) {
        final String originalUrl = request.getHeader("Referer").replace(request.getHeader("origin"), StringUtils.EMPTY);

        return originalUrl.replace("\\?.*", StringUtils.EMPTY);
    }

}
