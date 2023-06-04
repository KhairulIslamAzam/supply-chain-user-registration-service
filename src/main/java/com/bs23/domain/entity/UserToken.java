package com.bs23.domain.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;import java.util.Date;

@Getter
@Setter
@Table
@Entity(name = "USER_TOKEN")
public class UserToken extends BaseEntity implements Serializable {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "TOKEN")
  private String token;
  @Column(name = "EXPIRE_ON")
  private Date expireOn;

  @Column(name = "REQUEST_IP")
  private String requestIp;

  @Column(name = "isLogOut")
  private boolean isLogOut = Boolean.FALSE;

  @Column(name = "SAVE_DATE")
  private Date saveDate;
}
