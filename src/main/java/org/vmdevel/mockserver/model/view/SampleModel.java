package org.vmdevel.mockserver.model.view;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SampleModel {

    private String label;
    private MultipartFile harFile;
}
