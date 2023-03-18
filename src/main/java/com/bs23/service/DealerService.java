package com.bs23.service;

import com.bs23.domain.dto.DealerDto;

import java.util.List;

public interface DealerService {
    DealerDto subscribeDealer(DealerDto dto);

    List<DealerDto> findAll();

    DealerDto findDealerById(Long id);

    DealerDto unsubscribeDealer(Long id);

    DealerDto updateDealerById(DealerDto dto);
}
