package com.bs23.domain.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DealerDto {

    private Long id;
    private String userName;
    private String companyName;
    private String address;
    private String contactNo;
    private String email;
    private String city;
    private String zipCode;
    private String contactPerson;
    private String contactPersonDesignation;
    private Integer status;
    private String nid;
    private String tin;
    private String nidImage;
    private String tinImage;
}
