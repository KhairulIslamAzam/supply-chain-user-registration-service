package com.bs23.domain.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DEALER")
public class Dealer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "DEALER_NAME")
    private String companyName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CONTACT_NO")
    private String contactNo;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "CONTACT_PERSON")
    private String contactPerson;

    @Column(name = "CONTACT_PERSON_DESIGNATION")
    private String contactPersonDesignation;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "NID")
    private String nid;

    @Column(name = "TIN")
    private String tin;

    @Lob
    @Column(name = "NID_IMAGE")
    private String nidImageBase64;

    @Lob
    @Column(name = "TIN_IMAGE")
    private String tinImageBase64;
}
