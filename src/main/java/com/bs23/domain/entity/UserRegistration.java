package com.bs23.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "USER_REGISTRATION")
public class UserRegistration extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "USER_NAME", nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "DEALER_NAME")
    private String companyName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "CONTACT_NO")
    private String contactNo;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "NID")
    private String nid;
    @Lob
    @Column(name = "NID_IMAGE")
    private String nidImageBase64;
    @Column(name = "temp_block_date")
    private Date tempBlockDate;
    @Column(name = "temp_unblock_date")
    private Date tempUnblockDate;
    @Column(name = "BAD_LI_ATTEMPT")
    private Integer badLoginAttempt;
}