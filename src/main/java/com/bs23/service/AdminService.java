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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService extends BaseService implements UserService {

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
        return authenticate();
    }

    private boolean isPasswordExpired(Long id) {
        UserPasswordHistory userPasswordHistory = userPasswordHistoryRepository.findFirstByIdOrderByChangeDateDesc(id);
        if (ObjectUtils.isEmpty(userPasswordHistory)) {
            return false;
        }

        return DateTimeUtils.getDateDifferenceInDays(userPasswordHistory.getChangeDate(), DateUtils.addDays(new Date(), 10)) > 0;
    }

    private TokenResponse authenticate(AuthenticationRequest request, Customer customer) {

//        if (CustomerStatus.isAccountAccessAble(customer.getCustomerStatus())) {
//            return manageValidLogin(customer, countryId, countryCode, request);
//        } else if (CustomerStatus.isCustomerTemporarilyBlocked(customer.getCustomerStatus())) {
//            boolean customerPinExist = integrationService.isCustomerPinExist(String.valueOf(customer.getId()));
//            boolean isDeviceBindingNeeded = customerPinExist && checkDeviceBinding(request);
//
//            if (!(customer.getCustomerTempBlockDate() != null
//                    && customer.getCustomerTempUnblockDate() != null))
//                throw new CustomAuthenticationException(getMessage(ResponseMessages.LOGIN_FAILED));
//
//            if (isCustomerNotEligibleToUnblock(customer.getCustomerTempUnblockDate()))
//                throw new CustomAuthenticationException(getMessage(ResponseMessages.USER_TEMPORARILY_LOCKED));
//
//            Integer integer = setCustomerStatus(customer.getId(), customer.getCustomerStatus());
//            if (integer != null) customer.setCustomerStatus(integer);
//
//            UserJwtPayload jwtPayload = prepareJwtPayload(customer, CustomerRole.ROLE_USER, countryCode, isDeviceBindingNeeded);
//            String token = getCustomerToken(jwtPayload);
//            saveToRedis(customer.getUserName(), token);
//            saveToken(token, customer.getId(), countryId, request.getDeviceInfo());
//
//            customer.setBadLoginAttempt(0);
//            customer.setCustomerTempBlockDate(null);
//            customer.setCustomerTempUnblockDate(null);
//            updateCustomer(customer);
//
//            String passwordExpiryDate = getPasswordExpiryDate(customer);
//
//            return prepareTokenResponse(customer, token, passwordExpiryDate, countryCode, false, isDeviceBindingNeeded);
//        } else if (CustomerStatus.isCustomerPasswordExpired(customer.getCustomerStatus())) {
//            throw new PasswordExpiredException(getMessage(ResponseMessages.PASSWORD_EXPIRED));
//        } else if (CustomerStatus.isCustomerEligibleForForcePasswordChange(customer.getCustomerStatus())) {
//            throw new ForcePasswordChangeException(getMessage(ResponseMessages.FORCE_PASSWORD_CHANGE_EXCEPTION));
//        } else if (CustomerStatus.isAccountRestricted(customer.getCustomerStatus())) {
//            throw new AccountRestrictedException(getMessage(ResponseMessages.ACCOUNT_RESTRICTED));
//        } else if (CustomerStatus.isAccountClosed(customer.getCustomerStatus())) {
//            throw new AccountClosedException(getMessage(ResponseMessages.ACCOUNT_CLOSED));
//        }
//        throw new CustomAuthenticationException(getMessage(ResponseMessages.AUTHENTICATION_FAILED));
    }

    private TokenResponse manageValidLogin(Customer customer,
                                           Long countryId,
                                           String countryCode,
                                           AuthenticationRequest request) {

//        boolean customerPinExist = integrationService.isCustomerPinExist(String.valueOf(customer.getId()));
//        boolean isDeviceBindingNeeded = customerPinExist && checkDeviceBinding(request);
//        boolean isLoginPassword = customer.getCustomerStatus().equals(CustomerStatus.PASSWORD_EXPIRED.getCode()) || isLoginPasswordExpired(customer);
//
//        UserJwtPayload jwtPayload = prepareJwtPayload(customer, CustomerRole.ROLE_USER, countryCode, isDeviceBindingNeeded);
//        String token = getCustomerToken(jwtPayload);
//        saveToRedis(customer.getUserName(), token);
//        saveToken(token, customer.getId(), countryId, request.getDeviceInfo());
//
//        if (customer.getBadLoginAttempt() > 0)
//            updateBadLICountToZero(customer);
//
//        createCustomerLoginLogoutHistory(token, customer, request.getDeviceInfo());
//        String passwordExpiryDate = getPasswordExpiryDate(customer);

        return prepareTokenResponse(customer, token, passwordExpiryDate, countryCode, isLoginPassword, isDeviceBindingNeeded);
    }

    private UserJwtPayload prepareJwtPayload(Customer customer, CustomerRole role, String countryCode, boolean isDeviceBindingNeeded) {
        return UserJwtPayload
                .builder()
                .userName(customer.getUserName())
                .countryId(customer.getCountryId())
                .countryCode(countryCode)
                .userType(UserTypeEnum.CUSTOMER.getCode())
                .status(isDeviceBindingNeeded ? CustomerStatus.DEVICE_BINDING.getCode() : customer.getCustomerStatus())
                .userRole(role.getRoleCode())
                .build();
    }

    private String getPasswordExpiryDate(Customer customer) {
        Optional<String> passwordExpiryDateOpt = customerPasswordHistoryService.getPasswordExpirationDate(customer.getId(), customer.getCountryId());
        return passwordExpiryDateOpt.isPresent() ? passwordExpiryDateOpt.get() : StringUtils.EMPTY;
    }

    private TokenResponse prepareTokenResponse(Customer customer, String token, String passwordExpiryDate, String countryCode, Boolean isLoginPasswordExpired, boolean isDeviceBindingNeeded) {
//        String customerFullName = Objects.nonNull(customer.getFirstName()) ? customer
//                .getFirstName()
//                .concat(StringUtils.SPACE)
//                .concat(Objects.nonNull(customer.getLastName()) ? customer.getLastName() : StringUtils.EMPTY) : StringUtils.EMPTY;
//
//        return TokenResponse.builder()
//                .userName(customer.getUserName())
//                .userFUllName(customerFullName)
//                .passwordExpiryDate(passwordExpiryDate)
//                .email(customer.getEmail())
//                .token(token)
//                .accountStatus(
//                        Boolean.TRUE.equals(isLoginPasswordExpired) ? CustomerStatus.PASSWORD_EXPIRED.getCode() : isDeviceBindingNeeded ? CustomerStatus.DEVICE_BINDING.getCode() : customer.getCustomerStatus()
//                )
//                .idType(customer.getIdType())
//                .idNo(customer.getIdNo())
//                .image(CommonUtils.getImageContentUrl(
//                        baseUrl,
//                        ImageContentFeatureEnum.PROFILE_IMAGE.getCode(),
//                        CommonUtils.encodeUrl(CryptoUtility.encrypt(customer.getRemitterId(), imageSecretKey))))
//                .countryCode(countryCode)
//                .build();
    }

    private UserJwtPayload prepareJwtPayload(Customer customer, CustomerRole role, String countryCode, boolean isDeviceBindingNeeded) {
//        return UserJwtPayload
//                .builder()
//                .userName(customer.getUserName())
//                .countryId(customer.getCountryId())
//                .countryCode(countryCode)
//                .userType(UserTypeEnum.CUSTOMER.getCode())
//                .status(isDeviceBindingNeeded ? CustomerStatus.DEVICE_BINDING.getCode() : customer.getCustomerStatus())
//                .userRole(role.getRoleCode())
//                .build();
    }
}
