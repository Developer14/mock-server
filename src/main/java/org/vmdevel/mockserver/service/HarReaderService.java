package org.vmdevel.mockserver.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.vmdevel.mockserver.model.HarModel;
import org.vmdevel.mockserver.model.MockModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HarReaderService implements InitializingBean {

    private ObjectMapper objectMapper;

    public List<MockModel> processHar(Function<HarModel.HarEntry, MockModel> mapFunction) throws IOException {

        Resource resource = new ClassPathResource("dms-dsr.vmorales.cl_Archive_[25-10-12 22-27-35].har");
        File file = resource.getFile();
        HarModel harModel = objectMapper.readValue(file, HarModel.class);

        log.info(harModel.toString());

        List<MockModel> requestSet = harModel.getLog().getEntries()
                .stream()
                .map(mapFunction)
//                .map(HarModel.HarEntry::getRequest)
//                .map(HarModel.HarRequest::getUrl)
//                .map(s ->
//                        s.replaceAll("https://dms-dsr.vmorales.cl", "")
//                                .replaceAll("https://auth-dms-dsr.vmorales.cl", ""))
                .collect(Collectors.toList());

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
