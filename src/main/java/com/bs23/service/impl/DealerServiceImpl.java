package com.bs23.service.impl;

import com.bs23.domain.dto.DealerCbsInfo;
import com.bs23.domain.entity.Dealer;
import com.bs23.domain.dto.DealerDto;
import com.bs23.domain.response.ApiResponse;
import com.bs23.helper.enums.CbsResponseCode;
import com.bs23.helper.enums.DealerActiveStatus;
import com.bs23.helper.enums.ResponseMessages;
import com.bs23.helper.exceptions.CbsConnectionTimeOutException;
import com.bs23.helper.exceptions.RecordAlreadyFoundException;
import com.bs23.helper.exceptions.RecordNotFoundException;
import com.bs23.helper.exceptions.ResultNofFoundException;
import com.bs23.helper.mapper.DealerMapper;
import com.bs23.helper.utils.PasswordGenerator;
import com.bs23.repository.DealerRepository;
import com.bs23.rest.client.CoreFimiFeignClient;
import com.bs23.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService {

    private final DealerMapper dealerMapper;
    private final DealerRepository dealerRepository;
    private final CoreFimiFeignClient coreFimiFeignClient;


    @Override
    public DealerDto subscribeDealer(DealerDto dto) {

        Dealer dealer = dealerMapper.toEntity(dto);
        DealerCbsInfo cbsResult = this.coreApi(dealer);
        if (ObjectUtils.isEmpty(cbsResult)) {
            throw new ResultNofFoundException(ResponseMessages.OPERATION_FAILED.getKey());
        }

        Optional<Dealer> dealerDbResult = this.dealerRepository.findByUserName(dealer.getUserName());
        if (dealerDbResult.isPresent()) {
            throw new RecordAlreadyFoundException(ResponseMessages.OPERATION_FAILED.getKey());
        }

        dealerRepository.save(this.initiateDealerHistory(dealer));
        return dto;
    }

    @Override
    public List<DealerDto> findAll() {

        return dealerRepository.findAll()
                .stream()
                .map(dealerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DealerDto findDealerById(Long id) {

        Optional<Dealer> dealer = dealerRepository.findById(id);
        if (dealer.isEmpty())
            throw new RecordNotFoundException(ResponseMessages.RECORD_NOT_FOUND.getKey());

        return dealerMapper.toDto(dealer.get());
    }

    @Override
    public DealerDto unsubscribeDealer(Long id) {


        Optional<Dealer> dealer = dealerRepository.findById(id);
        if (dealer.isEmpty())
            throw new RecordNotFoundException(ResponseMessages.RECORD_NOT_FOUND.getKey());

        dealer.get().setStatus(DealerActiveStatus.IN_ACTIVE.getStatus());
        Dealer unsubscribeDealer = dealerRepository.save(dealer.get());

        return dealerMapper.toDto(unsubscribeDealer);
    }

    @Override
    public DealerDto updateDealerById(DealerDto request) {

        Optional<Dealer> dealer = dealerRepository.findById(request.getId());
        if (dealer.isEmpty())
            throw new RecordNotFoundException(ResponseMessages.RECORD_NOT_FOUND.getKey());


        Dealer updateDealer = dealerRepository.save(dealerMapper.toEntity(request));
        return dealerMapper.toDto(updateDealer);
    }

    private DealerCbsInfo coreApi(Dealer request) {

        try {
            ApiResponse<DealerCbsInfo> coreCbsResponse;
            coreCbsResponse = coreFimiFeignClient.getDealerCbsInfo(request.getUserName());
            if (coreCbsResponse.getResponseCode() == CbsResponseCode.SUCCESS.getStatus()) {
                return coreCbsResponse.getData();
            } else if (ObjectUtils.isEmpty(coreCbsResponse)) {
                throw new ResultNofFoundException(ResponseMessages.RESULT_NOT_FOUND.getKey());
            } else if (coreCbsResponse.getResponseCode() == CbsResponseCode.NOT_FOUND.getStatus()) {
                throw new RecordNotFoundException(ResponseMessages.RECORD_NOT_FOUND.getKey());
            } else if (coreCbsResponse.getResponseCode() == CbsResponseCode.FAILED.getStatus()) {
                throw new ResultNofFoundException(ResponseMessages.OPERATION_FAILED.getKey());
            }
        } catch (Exception e) {
            throw new CbsConnectionTimeOutException(ResponseMessages.CONNECTION_OUT.getKey());
        }
        return null;
    }

    private Dealer initiateDealerHistory(Dealer entity) {
        String password = PasswordGenerator.generatePassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        entity.setCreatedDate(new Date());
        entity.setPassword(encoder.encode(password));
        return entity;
    }
}
