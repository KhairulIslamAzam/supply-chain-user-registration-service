package com.bs23.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity(name = "USER_PASSWORD_HISTORY")
public class UserPasswordHistory extends BaseEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_TOKEN")
    private String customerToken;

    @Column(name = "CUSTOMER_ID")
    private long customerId;

    @Column(name = "USER_TYPE")
    private int userType;

    @Column(name = "PASSWORD_CHANGE_BY")
    private int passwordChangeBy;

    @Column(name = "OLD_PASSWORD")
    private String oldPassword;

    @Column(name = "NEW_PASSWORD")
    private String newPassword;

    @Column(name = "CHANGE_DATE")
    private Date changeDate;

    @Column(name = "IS_SUCCESSFUL")
    private Boolean isSuccessful;
}
