package org.vmdevel.mockserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vmdevel.mockserver.model.MockModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class MockService {

    private List<MockModel> mockModels;

    public void loadMocks(List<MockModel> mockModels) {
        this.mockModels = mockModels;
    }

    public String handleMockCall(HttpServletRequest request) {
        log.info(request.getRequestURI());
        return mockModels.stream()
                .filter(mockModel -> mockModel.getMockPath().equals(request.getRequestURI()))
                .findFirst().map(mockModel -> mockModel.getContent().getText()).orElse("{}");
    }
}
