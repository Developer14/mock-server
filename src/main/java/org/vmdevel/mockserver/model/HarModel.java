package org.vmdevel.mockserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HarModel {

    private Log log;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Log {
        private String version;
        private List<HarEntry> entries;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HarEntry {
        private String startedDateTime;
        private HarRequest request;
        private HarResponse response;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HarRequest {
        private String method;
        private String url;
        private List<HarHeader> headers;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HarHeader {
        private String name;
        private String value;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HarResponse {
        private Integer status;
        private List<HarHeader> headers;
        private HarContent content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HarContent {
        private String mimeType;
        private Long size;
        private String text;
    }
}

