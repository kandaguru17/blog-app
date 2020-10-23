package io.westpac;

import io.westpac.utils.AppUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppUtilTest {

    private AppUtils appUtils;

    private static final String URI = "http://localhost:8090";

    @BeforeEach
    public void setUp() {
        appUtils = new AppUtils();
    }

    @Test
    public void generateUrlWithPageAttributesTest() {
        String s = appUtils.generateUrlWithPageAttributes(URI, 12, 1);
        assertThat(s).isEqualTo(URI + "?_limit=12&_start=12");
    }

    @Test
    public void generateUrlWithNullAttributesTest() {
        String s = appUtils.generateUrlWithPageAttributes(URI, 12, 1);
        assertThrows(IllegalArgumentException.class, () -> appUtils.generateUrlWithPageAttributes(null, 12, 1));
    }
}
