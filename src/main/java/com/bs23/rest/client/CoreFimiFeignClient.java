package com.bs23.rest.client;

import com.bs23.domain.dto.DealerCbsInfo;
import com.bs23.domain.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "core-app-api", url = "http://localhost:8088/api/v1/cbs")
public interface CoreFimiFeignClient {

    @GetMapping("/dealer/{userName}")
    ApiResponse<DealerCbsInfo> getDealerCbsInfo(@PathVariable("userName") String userName);
}
