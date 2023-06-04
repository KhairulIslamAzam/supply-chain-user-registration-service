package com.bs23.service;

import com.bs23.common.utils.DateTimeUtils;
import com.bs23.domain.entity.UserPasswordHistory;
import com.bs23.repository.UserPasswordHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPasswordHistoryService extends BaseService{
    private final UserPasswordHistoryRepository repository;

    public boolean isLoginPasswordExpired(Long customerId) {
        UserPasswordHistory passwordHistory = repository.findFirstByIdOrderByChangeDateDesc(customerId);
        if(passwordHistory == null) return false;

        if(DateTimeUtils.getDateDifferenceInDays(passwordHistory.getChangeDate(), new Date()) <= 10)
            return false;

        return true;
    }

    public Optional<String> getPasswordExpirationDate(Long customerId) {
        UserPasswordHistory passwordHistory = repository.findFirstByIdOrderByChangeDateDesc(customerId);
        if(passwordHistory == null) return Optional.empty();
        Date passwordExpirationDate = DateTimeUtils.addDay(passwordHistory.getChangeDate(), 10);
        String formattedDate = DateTimeUtils.formatDate(passwordExpirationDate, DateTimeUtils.APP_DATE_FORMAT);
        return Optional.ofNullable(formattedDate);
    }
}
