package org.vmdevel.mockserver.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.vmdevel.mockserver.model.HarModel;
import org.vmdevel.mockserver.model.RequestModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HarReaderService implements InitializingBean {

    private ObjectMapper objectMapper;

    public Set<RequestModel> processHar(InputStream inputStream, Function<HarModel.HarEntry, RequestModel> mapFunction) throws IOException {

        HarModel harModel = objectMapper.readValue(inputStream, HarModel.class);

        log.info(harModel.toString());

        Set<RequestModel> requestSet = harModel.getLog().getEntries()
                .stream()
                .map(mapFunction)
                .collect(Collectors.toSet());

        requestSet.forEach(System.out::println);

        return requestSet;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
