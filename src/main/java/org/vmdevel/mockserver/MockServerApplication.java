package org.vmdevel.mockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MockServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockServerApplication.class, args);
    }

    /*@Bean
    CommandLineRunner init(HarReaderService harReaderService, MockService mockService) {


        return args -> {
            List<MockModel> mockModels = harReaderService.processHar(harEntry ->
                    new MockModel(harEntry.getRequest().getMethod(), harEntry.getRequest().getUrl(),
                            MockModel.MockContent.builder()
                                    .mimeType(harEntry.getResponse().getContent().getMimeType())
                                    .text(harEntry.getResponse().getContent().getText())
                                    .build()
                    ));

            mockService.loadMocks(mockModels);
        };
    }*/
}
