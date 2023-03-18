package com.bs23.domain.request;

import com.bs23.utils.SensitiveDataSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
public class AuthenticationRequest implements Serializable {
    @NotEmpty
    private String userName;
    @NotEmpty
    @JsonSerialize(using = SensitiveDataSerializer.class)
    private String password;
    @NotEmpty
    private int userType;
}
