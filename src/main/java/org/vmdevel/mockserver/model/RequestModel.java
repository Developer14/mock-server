package org.vmdevel.mockserver.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestModel {

    private static final Pattern DOMAIN_PATTERN =
            Pattern.compile("(?:https?://)(?:www.)?([a-zA-Z0-9-]+\\.)+([a-zA-Z0-9]{2,6})+(?::\\d{4})?");
    private static final Pattern LOCALHOST_PATTERN =
            Pattern.compile("(?:https?://localhost)(?::\\d{4})?");

    private String uuid;
    private String method;
    private MockUrl url;
    private MockContent content;

    public RequestModel(String method, String url, MockContent content) {
        this.uuid = UUID.randomUUID().toString();
        this.method = method;
        this.url = parseUrl(url);
        this.content = content;
    }

    private MockUrl parseUrl(String url) {
        log.info("parsing url {}", url);

        Matcher matcher = DOMAIN_PATTERN.matcher(url);
        if(!matcher.find()) {
            matcher = LOCALHOST_PATTERN.matcher(url);
            if(!matcher.find()) {
                throw new IllegalArgumentException("invalid url");
            }
        }

        return new MockUrl(matcher.group(), url.substring(matcher.group().length()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RequestModel that = (RequestModel) o;
        return Objects.equals(method, that.method) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, url);
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
