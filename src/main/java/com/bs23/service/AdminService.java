package com.bs23.service;

import com.bs23.common.enums.UserAuthenticationException;
import com.bs23.common.enums.UserStatusEnum;
import com.bs23.common.enums.UserTypeEnum;
import com.bs23.common.exceptions.RecordNotFoundException;
import com.bs23.common.utils.CommonEntityToDto;
import com.bs23.common.utils.DateTimeUtils;
import com.bs23.domain.common.UserJwtPayload;
import com.bs23.domain.dto.UserDto;
import com.bs23.domain.entity.UserPasswordHistory;
import com.bs23.domain.entity.UserRegistration;
import com.bs23.domain.entity.UserToken;
import com.bs23.domain.request.AuthenticationRequest;
import com.bs23.domain.response.TokenResponse;
import com.bs23.repository.UserRegistrationRepository;
import com.bs23.repository.UserTokenRepository;
import com.bs23.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService extends BaseService implements UserService {

    private final UserRegistrationRepository userRegistrationRepository;
    private final UserPasswordHistoryService passwordHistoryService;
    private final UserTokenRepository userTokenRepository;
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
        return authenticate(request, userRegistration);
    }

    @Override
    public UserDto getActiveUserByUserId(Long userId) {
        return null;
    }

//    private TokenResponse authenticate(AuthenticationRequest request, UserRegistration userRegistration) {
//
//        if (UserStatusEnum.isUserActive(userRegistration.getStatus())) {
//            return managedValidLogin(request, userRegistration);
//        }
//
//        return null;
//    }

    private TokenResponse managedValidLogin(AuthenticationRequest request, UserRegistration userRegistration) {

        boolean isLogin = UserStatusEnum.isUserPasswordExpired(userRegistration.getStatus()) || isPasswordExpired(userRegistration.getId());
//        return authenticate();
    }

    private boolean isPasswordExpired(Long id) {
        UserPasswordHistory userPasswordHistory = passwordHistoryService.findFirstByIdOrderByChangeDateDesc(id);
        if (ObjectUtils.isEmpty(userPasswordHistory)) {
            return false;
        }

        return DateTimeUtils.getDateDifferenceInDays(userPasswordHistory.getChangeDate(), DateUtils.addDays(new Date(), 10)) > 0;
    }

    private TokenResponse authenticate(AuthenticationRequest request, UserRegistration user) {

        if (UserStatusEnum.isAccountAccessAble(user.getStatus())) {
            return manageValidLogin(user, request);
        } else if (UserStatusEnum.isCustomerTemporarilyBlocked(user.getStatus())) {
//            boolean customerPinExist = integrationService.isCustomerPinExist(String.valueOf(user.getId()));
//            boolean isDeviceBindingNeeded = customerPinExist && checkDeviceBinding(request);

            if (!(user.getStatus() != null
                    && user.getTempBlockDate() != null))
                throw new UserAuthenticationException("login failed");

            if (isCustomerNotEligibleToUnblock(user.getTempUnblockDate()))
                throw new UserAuthenticationException("user temporary locked");

            Integer integer = setCustomerStatus(user.getId(), user.getCustomerStatus());
            if (integer != null) user.setCustomerStatus(integer);

            UserJwtPayload jwtPayload = prepareJwtPayload(user, CustomerRole.ROLE_USER, countryCode, isDeviceBindingNeeded);
            String token = getCustomerToken(jwtPayload);
            saveToRedis(user.getUserName(), token);
            saveToken(token, user.getId(), countryId, request.getDeviceInfo());

            user.setBadLoginAttempt(0);
            user.setCustomerTempBlockDate(null);
            user.setCustomerTempUnblockDate(null);
            updateCustomer(user);

            String passwordExpiryDate = getPasswordExpiryDate(user);

            return prepareTokenResponse(user, token, passwordExpiryDate, countryCode, false, isDeviceBindingNeeded);
        } else if (UserStatusEnum.isCustomerPasswordExpired(user.getCustomerStatus())) {
            throw new PasswordExpiredException(getMessage(ResponseMessages.PASSWORD_EXPIRED));
        } else if (CustomerStatus.isCustomerEligibleForForcePasswordChange(user.getCustomerStatus())) {
            throw new ForcePasswordChangeException(getMessage(ResponseMessages.FORCE_PASSWORD_CHANGE_EXCEPTION));
        } else if (CustomerStatus.isAccountRestricted(user.getCustomerStatus())) {
            throw new AccountRestrictedException(getMessage(ResponseMessages.ACCOUNT_RESTRICTED));
        } else if (CustomerStatus.isAccountClosed(user.getCustomerStatus())) {
            throw new AccountClosedException(getMessage(ResponseMessages.ACCOUNT_CLOSED));
        }
        throw new CustomAuthenticationException(getMessage(ResponseMessages.AUTHENTICATION_FAILED));
    }

    private boolean isCustomerNotEligibleToUnblock(Date tempUnblockDate) {
        return !isCustomerEligibleToUnblock(tempUnblockDate);
    }
    private boolean isCustomerEligibleToUnblock(Date tempUnblockDate) {
        return tempUnblockDate.compareTo(new Date()) < 1;
    }


    private TokenResponse manageValidLogin(UserRegistration user,
                                           AuthenticationRequest request) {

        boolean isLoginPassword = user.getStatus().equals(UserStatusEnum.PASSWORD_EXPIRED.getCode()) || isLoginPasswordExpired(user);

        UserJwtPayload jwtPayload = prepareJwtPayload(user, UserTypeEnum.ADMIN);
        String token = getCustomerToken(jwtPayload);

        saveToken(token, user.getId());

        if (user.getBadLoginAttempt() > 0)
            updateBadLICountToZero(user);

        createCustomerLoginLogoutHistory(token, user, request.getDeviceInfo());
        String passwordExpiryDate = getPasswordExpiryDate(user);

        return prepareTokenResponse(user, token, passwordExpiryDate, countryCode, isLoginPassword, isDeviceBindingNeeded);
    }

    private boolean isLoginPasswordExpired(UserRegistration customer) {
        if (passwordHistoryService.isLoginPasswordExpired(customer.getId())) {
            customer.setStatus(UserStatusEnum.PASSWORD_EXPIRED.getCode());
            updateCustomer(customer);
            return true;
        }
        return false;
    }

    private UserJwtPayload prepareJwtPayload(UserRegistration user, UserTypeEnum role) {
        return UserJwtPayload
                .builder()
                .userName(user.getUserName())
                .userType(UserTypeEnum.ADMIN.getCode())
                .userRole(role.getCode())
                .build();
    }
    private void updateBadLICountToZero(UserRegistration user) {
        user.setBadLoginAttempt(0);
        updateCustomer(user);
    }
    

    private String getPasswordExpiryDate(UserRegistration user) {
        Optional<String> passwordExpiryDateOpt = passwordHistoryService.getPasswordExpirationDate(user.getId());
        return passwordExpiryDateOpt.isPresent() ? passwordExpiryDateOpt.get() : StringUtils.EMPTY;
    }

    private void createCustomerLoginLogoutHistory(String token,
                                                  UserRegistration user,
                                                  DeviceInfo deviceInfo) {
        BaseEntityDto baseEntityDto = BaseEntityDto
                .builder()
                .createdBy(user.getId())
                .updatedBy(user.getId())
                .createdDate(new Date())
                .updatedDate(new Date())
                .lastUpdatedDate(new Date())
                .build();
        customerLoginLogoutHistoryService.createCustomerLoginLogoutHistory(token, user, deviceInfo, baseEntityDto);
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

//    private UserJwtPayload prepareJwtPayload(Customer customer, CustomerRole role, String countryCode, boolean isDeviceBindingNeeded) {
////        return UserJwtPayload
////                .builder()
////                .userName(customer.getUserName())
////                .countryId(customer.getCountryId())
////                .countryCode(countryCode)
////                .userType(UserTypeEnum.CUSTOMER.getCode())
////                .status(isDeviceBindingNeeded ? CustomerStatus.DEVICE_BINDING.getCode() : customer.getCustomerStatus())
////                .userRole(role.getRoleCode())
////                .build();
//    }

    private void updateCustomer(UserRegistration customer) {
        userRegistrationRepository.save(customer);
    }

    private String getCustomerToken(UserJwtPayload payload) {
        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> customerData = mapper.convertValue(payload, Map.class);
        claims.putAll(customerData);
        return JWTUtils.generateToken(claims, payload.getUserName(), jwtExpiryTime, jwtSecretKey);
    }

    private void saveToken(String token,
                           Long customerId) {

        userTokenRepository.deleteCustomerToken(customerId);

        Integer expiryMinute = Integer.parseInt(jwtExpiryTime);
        Date expireOn = new Date(System.currentTimeMillis() + DateTimeUtils.minuteToMillis(expiryMinute));

        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setExpireOn(expireOn);
        userToken.setCreatedDate(getCurrentDate());

        String remoteIpAddress = getRemoteIPAddress();
        userToken.setRequestIp(remoteIpAddress);

        userToken.setCreatedDate(getCurrentDate());
        userToken.setUpdatedDate(getCurrentDate());

        userTokenRepository.save(userToken);

    }
}
