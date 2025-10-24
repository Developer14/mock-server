package org.vmdevel.mockserver.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vmdevel.mockserver.model.RequestModel;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestListWrapper {

    private List<RequestModel> requestModels;
    /*private List<String> reqPaths;*/

    /*@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReqPath {

        private String path;
    }*/
}
