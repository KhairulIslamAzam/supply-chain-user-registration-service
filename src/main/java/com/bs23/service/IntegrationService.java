package com.bs23.service;

import com.bs23.rest.client.MetaFeignClient;import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegrationService {
     private final MetaFeignClient metaFeignClient;

}
