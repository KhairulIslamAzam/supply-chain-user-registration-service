package com.bs23.controller;

import com.bs23.domain.dto.DealerDto;
import com.bs23.domain.response.ApiResponse;
import com.bs23.helper.enums.ResponseMessages;
import com.bs23.helper.utils.ResponseUtils;
import com.bs23.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dealer")
public class DealerController {

    private final DealerService dealerService;

    @PostMapping(value = "/save")
    public ResponseEntity<ApiResponse<DealerDto>> subscribeDealer(@RequestBody DealerDto request) {
        return ResponseUtils.createHttpResponse(HttpStatus.OK, ResponseMessages.OPERATION_SUCCESSFUL.getKey(), dealerService.subscribeDealer(request));
    }

    @GetMapping(value = "/{dealerId}")
    public ResponseEntity<ApiResponse<DealerDto>> findDealerById(@PathVariable("dealerId") Long id) {
        DealerDto response = dealerService.findDealerById(id);
        return ResponseUtils.createHttpResponse(HttpStatus.OK, ResponseMessages.OPERATION_SUCCESSFUL.getKey(), response);
    }

    @DeleteMapping(value = "/{dealerId}")
    public ResponseEntity<ApiResponse<Void>> unsubscribeDealer(@PathVariable("dealerId") Long id) {
        dealerService.unsubscribeDealer(id);
        return ResponseUtils.createHttpResponse(HttpStatus.OK, ResponseMessages.OPERATION_SUCCESSFUL.getKey());
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ApiResponse<Void>> updateDealerById(@RequestBody DealerDto request) {
        dealerService.updateDealerById(request);
        return ResponseUtils.createHttpResponse(HttpStatus.OK, ResponseMessages.OPERATION_SUCCESSFUL.getKey());
    }
}
