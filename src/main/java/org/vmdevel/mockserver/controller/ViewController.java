package org.vmdevel.mockserver.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.vmdevel.mockserver.controller.dto.RequestListWrapper;
import org.vmdevel.mockserver.model.RequestModel;
import org.vmdevel.mockserver.model.view.SampleModel;
import org.vmdevel.mockserver.service.HarReaderService;
import org.vmdevel.mockserver.service.MockService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Controller
public class ViewController {

    private final HarReaderService harReaderService;
    private final MockService mockService;

    @GetMapping("/web")
    public String landing(SampleModel sampleModel) {
        return "web";
    }

    @PostMapping(value = "/upload")
    public String uploadHar(@RequestParam("harFile") MultipartFile multipartFile, final ModelMap model) throws IOException {
        log.info("multipartFile name: {}", multipartFile.getName());
        log.info("multipartFile original name: {}", multipartFile.getOriginalFilename());

        Set<RequestModel> requestModels = harReaderService.processHar(multipartFile.getInputStream(), harEntry ->
                new RequestModel(harEntry.getRequest().getMethod(), harEntry.getRequest().getUrl(),
                        RequestModel.MockContent.builder()
                                .mimeType(harEntry.getResponse().getContent().getMimeType())
                                .text(harEntry.getResponse().getContent().getText())
                                .build()
                ));

        /*model.addAttribute("theHarFile", multipartFile.getOriginalFilename());
        model.addAttribute("requestListWrapper", RequestListWrapper.builder()
                        .requestModels(new ArrayList<>(requestModels))
                .build());*/

        model.addAttribute("state", "newFile");
        this.setModel(model, multipartFile.getOriginalFilename(), RequestListWrapper.builder()
                .requestModels(new ArrayList<>(requestModels))
                .build());

        return "mocks";
    }

    @PostMapping(value = "/load")
    public String loadMocks(
            @ModelAttribute RequestListWrapper requestListWrapper,
            @RequestParam(value = "action") String action, ModelMap modelMap) {

        log.info("action invoked >>> {}", action);
        if (action.equals("update")) {
            log.info("updating mock requests...");
            setModel(modelMap, "nn", requestListWrapper);
            modelMap.addAttribute("state", "pendingChanges");
        }else if (action.equals("load")) {
            log.info("loading mock requests: {}", requestListWrapper);
            modelMap.addAttribute("state", "loaded");
            mockService.loadMocks(new HashSet<>(requestListWrapper.getRequestModels()));
        }

        return "mocks";
    }

    private void setModel(ModelMap model, String harFileName, RequestListWrapper requestListWrapper) {

        model.addAttribute("theHarFile", harFileName);
        model.addAttribute("requestListWrapper", requestListWrapper);
    }
}
