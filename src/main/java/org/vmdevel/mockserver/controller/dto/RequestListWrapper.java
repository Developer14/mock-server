package org.vmdevel.mockserver.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vmdevel.mockserver.model.RequestModel;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestListWrapper {

    private Set<RequestModel> requestModels;
}
