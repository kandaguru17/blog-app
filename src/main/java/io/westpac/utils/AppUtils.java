package io.westpac.utils;

import org.springframework.stereotype.Component;

@Component
public class AppUtils {

    public String generateUrlWithPageAttributes(String uri, Integer limit, Integer offset) {
        return uri + "?_limit=" + limit + "&_start=" + (offset * limit);
    }
}
