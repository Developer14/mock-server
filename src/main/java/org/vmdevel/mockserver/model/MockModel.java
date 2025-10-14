package org.vmdevel.mockserver.model;

import lombok.*;

@ToString
@Data
public class MockModel {

    private String method;
    private String mockPath;
    private MockContent content;

    public MockModel(String method, String mockPath, MockContent content) {
        this.method = method;
        this.mockPath = parsePath(mockPath);
        this.content = content;
    }

    private String parsePath(String path) {
        int dotIndex = path.lastIndexOf(".");
        int pathStartIndex = path.substring(dotIndex).indexOf("/");
        return path.substring(pathStartIndex + dotIndex);
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

}
