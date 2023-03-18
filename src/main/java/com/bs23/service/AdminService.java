package com.bs23.service;

import com.bs23.common.enums.UserStatusEnum;
import com.bs23.common.exceptions.RecordNotFoundException;
import com.bs23.common.utils.CommonEntityToDto;
import com.bs23.common.utils.DateTimeUtils;
import com.bs23.domain.dto.UserDto;
import com.bs23.domain.entity.UserPasswordHistory;
import com.bs23.domain.entity.UserRegistration;
import com.bs23.domain.request.AuthenticationRequest;
import com.bs23.domain.response.TokenResponse;
import com.bs23.repository.UserPasswordHistoryRepository;
import com.bs23.repository.UserRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements UserService {

    private final UserRegistrationRepository userRegistrationRepository;
    private final UserPasswordHistoryRepository userPasswordHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUserByUserName(String userName) {
        Optional<UserRegistration> userRegistration = userRegistrationRepository.findByUserName(userName);
        if (userRegistration.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return CommonEntityToDto.entityToDto(userRegistration, UserDto.class);
    }

    @Override
    public TokenResponse authenticateUser(AuthenticationRequest request) {
        UserRegistration userRegistration = userRegistrationRepository.findByUserName(request.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), userRegistration.getPassword())) {
            throw new RecordNotFoundException("Password Not match");
        }
        return null;
    }

    @Override
    public UserDto getActiveUserByUserId(Long userId) {
        return null;
    }

    private TokenResponse authenticate(AuthenticationRequest request, UserRegistration userRegistration) {

        if (UserStatusEnum.isUserActive(userRegistration.getStatus())) {
            return managedValidLogin(request, userRegistration);
        }

        return null;
    }

    private TokenResponse managedValidLogin(AuthenticationRequest request, UserRegistration userRegistration) {

        boolean isLogin = UserStatusEnum.isUserPasswordExpired(userRegistration.getStatus()) || isPasswordExpired(userRegistration.getId());
        return null;
    }

    private boolean isPasswordExpired(Long id) {
        UserPasswordHistory userPasswordHistory = userPasswordHistoryRepository.findFirstByIdOrderByChangeDateDesc(id);
        if (ObjectUtils.isEmpty(userPasswordHistory)) {
            return false;
        }

        return DateTimeUtils.getDateDifferenceInDays(userPasswordHistory.getChangeDate(), DateUtils.addDays(new Date(), 10)) > 0;
    }
}
