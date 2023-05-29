package com.bs23.service;
import com.bs23.common.enums.ResponseMessageEnum;
import com.bs23.common.enums.UserStatusEnum;
import com.bs23.common.enums.UserTypeEnum;
import com.bs23.common.exceptions.ServiceNotFoundException;
import com.bs23.common.utils.ApplicationContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFactory extends BaseService{
     public UserService getUserDetailsByType(int userType) {
         if(UserTypeEnum.ADMIN.getCode() == userType){
             return ApplicationContextHolder.getContext().getBean(AdminService.class);
         }else if(UserTypeEnum.DEALER.getCode() == userType){
             return ApplicationContextHolder.getContext().getBean(DealerService.class);
         }else if(UserTypeEnum.PRODUCT_MANAGER.getCode() == userType){
             return ApplicationContextHolder.getContext().getBean(ProductManagerService.class);
         } else if (UserTypeEnum.INVENTORY_MANAGER.getCode() == userType) {
             return ApplicationContextHolder.getContext().getBean(InventoryManagerService.class);
         }else{
             throw  new ServiceNotFoundException(ResponseMessageEnum.SERVICE_NOT_FOUND_EXP.getText());
         }
     }
}
