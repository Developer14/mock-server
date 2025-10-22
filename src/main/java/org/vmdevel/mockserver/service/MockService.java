package org.vmdevel.mockserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vmdevel.mockserver.model.RequestModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class MockService {

    private static final String MOCK_PREFIX = "/mock";

    private Set<RequestModel> requestModels;

    public void loadMocks(Set<RequestModel> requestModels) {
        this.requestModels = requestModels;
    }

    public String handleMockCall(HttpServletRequest request) {
        log.info("Request URI => {}", request.getRequestURI());
        log.info("Query string => {}", request.getQueryString());

        String requestUri = request.getRequestURI().substring(MOCK_PREFIX.length());
        String requestUriWithParams = Objects.nonNull(request.getQueryString())
                ? String.format("%s?%s", requestUri, request.getQueryString())
                : requestUri;

        return requestModels.stream()
                .filter(requestModel -> requestModel.getUrl().getPath().equals(requestUriWithParams))
                .findFirst().map(requestModel -> requestModel.getContent().getText()).orElse("{}");
    }
}
