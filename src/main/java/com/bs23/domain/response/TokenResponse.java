package com.bs23.domain.response;


import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Jacksonized
@Builder
public class TokenResponse implements Serializable {
}
