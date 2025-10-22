package org.vmdevel.mockserver.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@ToString
@Data
public class RequestModel {

    private static final Pattern DOMAIN_PATTERN =
            Pattern.compile("(?:https?://)(?:www.)?([a-zA-Z0-9-]+\\.)+([a-zA-Z0-9]{2,6})+(?::\\d{4})?");

    private String method;
    private MockUrl url;
    private MockContent content;

    public RequestModel(String method, String url, MockContent content) {
        this.method = method;
        this.url = parseUrl(url);
        this.content = content;
    }

    private MockUrl parseUrl(String url) {
        log.debug("parsing url {}", url);

        Matcher matcher = DOMAIN_PATTERN.matcher(url);
        if(!matcher.find())
            throw new IllegalArgumentException("invalid url");

        return new MockUrl(matcher.group(), url.substring(matcher.group().length()));
    }

    @ToString
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MockContent {
        private String mimeType;
        private String text;
    }

    @ToString
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MockUrl {
        private String domain;
        private String path;
    }

}
